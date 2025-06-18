package org.example.weapons;

public class WeaponConfig {

    private String name;
    private String material;
    private String type;
    private String weaponId;
    private String sound;
    private float pitch;
    private double damage;
    private double knockback;
    private double spreadFactor;
    private double reloadTime;
    private double range;
    private double attackSpeed;
    private double currencyPerBullet;
    private int magazine;
    private int maxAmmoSize;
    private boolean tracer;
    private int piercing = 1;
    private int bulletsPerShot = 1;

    public int getBulletsPerShot() {
        return bulletsPerShot;
    }

    public int getPiercing() {
        return piercing;
    }

    public String getName() {
        return name;
    }

    public boolean isTracer() {
        return tracer;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public String getMaterial() {
        return material;
    }

    public int getMaxAmmoSize() {
        return maxAmmoSize;
    }

    public int getMagazine() {
        return magazine;
    }

    public double getKnockback() {
        return knockback;
    }

    public String getWeaponId() {
        return weaponId;
    }

    public double getDamage() {
        return damage;
    }

    public double getCurrencyPerBullet() {
        return currencyPerBullet;
    }

    public float getPitch() {
        return pitch;
    }

    public double getRange() {
        return range;
    }

    public double getReloadTime() {
        return reloadTime;
    }

    public double getSpreadFactor() {
        return spreadFactor;
    }

    public String getSound() {
        return sound;
    }

    public String getType() {
        return type;
    }
}
