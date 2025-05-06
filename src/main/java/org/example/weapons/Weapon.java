package org.example.weapons;

import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.item.Material;
import net.minestom.server.sound.SoundEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class Weapon {

    private final double damage;
    private final Material material;
    private final double knockback;
    private final double attackSpeed;
    private final String weaponID;
    private final SoundEvent sound;
    private final float pitch;

    private Runnable task;
    protected boolean isAttackDelay = false;

    public Material getMaterial() {
        return material;
    }

    public float getPitch() {
        return pitch;
    }

    public SoundEvent getSound() {
        return sound;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public double getKnockback() {
        return knockback;
    }

    public double getDamage() {
        return damage;
    }

    public String getWeaponID() {
        return weaponID;
    }

    protected void delayBetweenAttack() {
        if (isAttackDelay) {
            return;
        }
        isAttackDelay = true;

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        task = () -> {
            isAttackDelay = false;
        };

        executorService.schedule(task, (long) (attackSpeed * 1000), TimeUnit.MILLISECONDS);

    }

    public abstract void onUse(Player player, PlayerUseItemEvent event);

    protected Weapon(Builder<?> builder) {
        this.material = builder.material;
        this.knockback = builder.knockback;
        this.damage = builder.damage;
        this.weaponID = builder.weaponID;
        this.sound = builder.sound;
        this.pitch = builder.pitch;
        this.attackSpeed = builder.attackSpeed;
    }

    public static abstract class Builder<T extends Builder<T>> {
        protected double damage = 1;
        protected Material material;
        protected double knockback = 1;
        private String weaponID;
        private SoundEvent sound;
        private float pitch;
        private double attackSpeed = 1;

        public T setSound(SoundEvent sound) {
            this.sound = sound;
            return self();
        }

        public T setAttackSpeed(double attackSpeed) {
            this.attackSpeed = attackSpeed;
            return self();
        }

        public T setPitch(float pitch) {
            this.pitch = pitch;
            return self();
        }

        public Builder(Material material) {
            this.material = material;
        }

        public T setWeaponId(String weaponID) {
            this.weaponID = weaponID;
            return self();
        }

        public T setKnockback(double knockback) {
            this.knockback = knockback;
            return self();
        }

        public T setDamage(double damage) {
            this.damage = damage;
            return self();
        }

        public abstract Weapon build();

        protected abstract T self();

    }

}
