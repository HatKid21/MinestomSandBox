package org.example.weapons;

import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;

import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.network.packet.server.play.SoundEffectPacket;
import net.minestom.server.tag.Tag;
import org.example.creatures.EnemyCreature;

import org.example.player.CustomPlayer;
import org.example.utils.Ray;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RangedWeapon extends Weapon implements Reloadable {

    private final int maxAmmoSize;
    private final int magazine;
    private final double reloadTime;
    private final double spreadFactor;
    private final boolean tracer;
    private final double range;
    private final int piercing;
    private final int bulletsPerShot;
    private final int currencyPerShot;

    private boolean isReloading = false;

    private RangedWeapon(Builder builder) {
        super(builder);
        this.spreadFactor = builder.spreadFactor;
        this.reloadTime = builder.reloadTime;
        this.magazine = builder.magazine;
        this.tracer = builder.tracer;
        this.range = builder.range;
        this.piercing = builder.piercing;
        this.bulletsPerShot = builder.bulletsPerShot;
        this.currencyPerShot = builder.currencyPerShot;
        this.maxAmmoSize = builder.maxAmmoSize;
    }

    public int getMagazine() {
        return magazine;
    }

    public int getMaxAmmoSize() {
        return maxAmmoSize;
    }

    public void reload(Player player) {

        CustomPlayer customPlayer = (CustomPlayer) player;

        if (isReloading) {
            return;
        }
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        byte weaponHeldSlot = player.getHeldSlot();

        Tag<String> weaponIdTag = Tag.String("weapon_id");
        Tag<Integer> ammoTag = Tag.Integer("ammo");
        ItemStack item = player.getInventory().getItemStack(weaponHeldSlot);
        RangedWeapon weapon = (RangedWeapon) WeaponRegistry.getWeaponById(item.getTag(weaponIdTag));

        if (weapon.getMagazine() == item.getTag(ammoTag)) {
            return;
        }

        Runnable task = () -> {
            String weaponID = item.getTag(weaponIdTag);
            int currentAmmo = item.getTag(ammoTag);
            int dif = magazine - currentAmmo;
            int currentSavedAmmo = customPlayer.getAmmo(weaponID);
            if (customPlayer.consumeAmmo(weaponID, dif)) {
                ItemStack updatedItem = item.withTag(ammoTag, magazine).withAmount(magazine);
                player.getInventory().setItemStack(weaponHeldSlot, updatedItem);
            } else if (((CustomPlayer) player).consumeAmmo(weaponID, currentSavedAmmo)) {
                ItemStack updatedItem = item.withTag(ammoTag, currentSavedAmmo).withAmount(currentAmmo + currentSavedAmmo);
                player.getInventory().setItemStack(weaponHeldSlot, updatedItem);
            }

            updatePlayerAmmo(player, weaponHeldSlot);
            isReloading = false;
        };

        player.sendActionBar(Component.text("Reloading..."));
        isReloading = true;
        scheduledExecutorService.schedule(task, (long) reloadTime * 1000, TimeUnit.MILLISECONDS);


    }

    private void updatePlayerAmmo(Player player, byte slot) {
        ItemStack itemStack = player.getInventory().getItemStack(slot);
        CustomPlayer customPlayer = (CustomPlayer) player;
        if (itemStack.hasTag(Tag.String("weapon_id"))) {
            String weaponId = itemStack.getTag(Tag.String("weapon_id"));
            int currentAmmo = customPlayer.getAmmo(weaponId);
            player.setLevel(currentAmmo);
        }
    }

    protected void consumeAmmo(Player player, ItemStack item, int currentAmmo) {
        Tag<Integer> ammoTag = Tag.Integer("ammo");
        int newAmmo = currentAmmo - 1;

        ItemStack updatedWeapon;

        if (newAmmo == 0) {
            updatedWeapon = item.withTag(ammoTag, newAmmo).withAmount(1);
        } else {
            updatedWeapon = item.withTag(ammoTag, newAmmo).withAmount(newAmmo);
        }

        player.setItemInMainHand(updatedWeapon);

    }

    protected Vec spread(Vec direction) {
        Vec temp = new Vec(0, 1, 0);
        Vec orto1 = direction.cross(temp).normalize();
        Vec orto2 = direction.cross(orto1).normalize();
        Random random = new Random();
        double dx = random.nextDouble(-spreadFactor, spreadFactor);
        double dy = random.nextDouble(-spreadFactor, spreadFactor);
        orto1 = orto1.mul(dx);
        orto2 = orto2.mul(dy);
        return direction.add(orto2).add(orto1).normalize();
    }

    @Override
    public void onUse(Player player, PlayerUseItemEvent event) {
        CustomPlayer customPlayer = (CustomPlayer) player;
        if (isReloading) {
            return;
        }

        if (isAttackDelay) {
            return;
        }

        if (magazine != 0) {
            delayBetweenAttack();
        }


        Instance instance = player.getInstance();
        Pos playerPos = player.getPosition().add(0, player.getEyeHeight(), 0);
        Vec direction = playerPos.direction();
        playerPos = playerPos.add(direction.mul(1.5));
        ItemStack item = event.getItemStack();

        SoundEffectPacket shotSound = null;

        if (getSound() != null) {
            shotSound = new SoundEffectPacket(
                    getSound(), Sound.Source.MASTER, playerPos, 1, getPitch(), 5
            );
        }

        Tag<Integer> ammoTag = Tag.Integer("ammo");
        int currentAmmo = item.getTag(ammoTag);

        if (currentAmmo > 0) {
            if (shotSound != null) {
                player.sendPacket(shotSound);
            }
            for (int bullet = 0; bullet < bulletsPerShot; bullet++) {
                direction = spread(direction);
                Ray ray = new Ray(playerPos, direction, instance, range);
                ray.setPiercingAmount(piercing);
                ray.setTracer(tracer);
                Collection<Entity> targets = ray.launch();

                for (Entity target : targets) {
                    if (target instanceof EnemyCreature enemy) {
                        customPlayer.addCurrency(currencyPerShot);
                        enemy.onHit(getDamage(), getKnockback(), direction);
                    }
                }
            }

            consumeAmmo(player, item, currentAmmo);

        } else {
            reload(player);
        }
    }


    public static class Builder extends Weapon.Builder<Builder> {

        private int maxAmmoSize = 1;
        private int magazine;
        private double reloadTime;
        private double spreadFactor;
        private boolean tracer;
        protected double range;

        private int currencyPerShot = 1;
        private int piercing = 1;
        private int bulletsPerShot = 1;

        protected Builder(Material material) {
            super(material);
        }

        public Builder setRange(double range) {
            this.range = range;
            return self();
        }

        public Builder setMaxAmmoSize(int size) {
            this.maxAmmoSize = size;
            return self();
        }

        public Builder setPiercing(int piercing) {
            this.piercing = piercing;
            return self();
        }

        public Builder setCurrencyPerShot(int currency) {
            currencyPerShot = currency;
            return self();
        }

        public Builder setTracer(boolean tracer) {
            this.tracer = tracer;
            return self();
        }

        public Builder setBulletsPerShot(int bullets) {
            this.bulletsPerShot = bullets;
            return self();
        }

        @Override
        public RangedWeapon build() {

            return new RangedWeapon(this);
        }

        @Override
        protected Builder self() {
            return this;
        }

        protected Builder setMagazine(int magazine) {
            this.magazine = magazine;
            return self();
        }

        protected Builder setReloadTime(double reloadTime) {
            this.reloadTime = reloadTime;
            return self();
        }

        protected Builder setSpreadFactor(double spreadFactor) {
            this.spreadFactor = spreadFactor;
            return self();
        }

    }

}
