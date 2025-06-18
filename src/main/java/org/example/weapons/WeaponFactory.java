package org.example.weapons;

import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.sound.SoundEvent;
import net.minestom.server.tag.Tag;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeaponFactory {

    private static final Logger LOGGER = Logger.getLogger(WeaponFactory.class.getName());

    private static final Gson gson = new Gson();

    private final Map<String, Weapon> WEAPONS = new HashMap<>();

    public WeaponFactory(){

    }

    public Map<String, Weapon> getWEAPONS() {
        return WEAPONS;
    }

    public ItemStack getWeaponItem(String weaponId){
        Weapon weapon = getWeapon(weaponId);
        if (weapon == null){
            return ItemStack.of(Material.DEAD_BUSH).withCustomName(Component.text("Something went wrong"));
        }

        ItemStack itemStack = null;

        String weaponName = weaponId.substring(0, 1).toUpperCase() + weaponId.substring(1);

        if (weapon instanceof RangedWeapon rangedWeapon){
            itemStack = ItemStack.builder(rangedWeapon.getMaterial())
                    .customName(Component.text(weaponName))
                    .amount(rangedWeapon.getMagazine())
                    .maxStackSize(rangedWeapon.getMagazine())
                    .set(Tag.String("weapon_id"), rangedWeapon.getWeaponID())
                    .set(Tag.Integer("ammo"), rangedWeapon.getMagazine())
                    .build();
        }
        return itemStack;
    }

    public Weapon getWeapon(String weaponId){
        if (WEAPONS.containsKey(weaponId)){
            return WEAPONS.get(weaponId);
        }

        String path = "weapon/ranged/" + weaponId + ".json";

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)){
            assert inputStream != null;
            Reader reader = new InputStreamReader(inputStream);
            WeaponConfig weaponConfig = gson.fromJson(reader, WeaponConfig.class);
            Weapon weapon = getWeaponFromConfig(weaponConfig);
            WEAPONS.put(weaponId,weapon);
            return weapon;
        } catch (IOException e){
            LOGGER.log(Level.SEVERE, "Error while reading data", e);
        }
        return null;
    }

    private Weapon getWeaponFromConfig(WeaponConfig weaponConfig){
        String type = weaponConfig.getType();
        String weaponId = weaponConfig.getWeaponId();
        double damage = weaponConfig.getDamage();
        double attackSpeed = weaponConfig.getAttackSpeed();
        double knockback = weaponConfig.getKnockback();
        String material = weaponConfig.getMaterial();
        Weapon weapon = null;
        if (type.equals("ranged")){
            int piercing = weaponConfig.getPiercing();
            int bulletsPerShot = weaponConfig.getBulletsPerShot();
            double spreadFactor = weaponConfig.getSpreadFactor();
            double range = weaponConfig.getRange();
            double reloadTime = weaponConfig.getReloadTime();
            double currencyPerBullet = weaponConfig.getCurrencyPerBullet();
            int maxAmmoSize = weaponConfig.getMaxAmmoSize();
            boolean tracer = weaponConfig.isTracer();
            int magazine = weaponConfig.getMagazine();
            String sound = weaponConfig.getSound();
            float pitch = weaponConfig.getPitch();
            weapon = new RangedWeapon.Builder(Material.fromKey(material))
            .setWeaponId(weaponId)
            .setAttackSpeed(attackSpeed)
            .setDamage(damage)
            .setPiercing(piercing)
            .setMagazine(magazine)
            .setMaxAmmoSize(maxAmmoSize)
            .setBulletsPerShot(bulletsPerShot)
            .setTracer(tracer)
            .setCurrencyPerBullet(currencyPerBullet)
            .setReloadTime(reloadTime)
            .setRange(range)
            .setSpreadFactor(spreadFactor)
            .setKnockback(knockback)
            .setSound(SoundEvent.fromKey(sound))
            .setPitch(pitch)
                    .build();
        }
        return weapon;
    }


}
