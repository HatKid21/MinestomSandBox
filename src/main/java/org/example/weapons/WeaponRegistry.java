package org.example.weapons;

import net.kyori.adventure.text.Component;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.sound.SoundEvent;
import net.minestom.server.tag.Tag;

import java.util.HashMap;
import java.util.Map;

public class WeaponRegistry {

    public static final Map<String, Weapon> WEAPONS = new HashMap<>();

    private static void registerWeapon(Weapon weapon) {
        WEAPONS.put(weapon.getWeaponID(), weapon);
    }

    public static Weapon getWeaponById(String id) {
        return WEAPONS.get(id);
    }

    public static ItemStack getWeaponItem(String weaponID) {
        Weapon weapon = getWeaponById(weaponID);
        ItemStack weaponItem;
        if (weapon instanceof RangedWeapon rangedWeapon) {
            String weaponName = weaponID.substring(0, 1).toUpperCase() + weaponID.substring(1);
            weaponItem = ItemStack.builder(rangedWeapon.getMaterial())
                    .set(Tag.String("weapon_id"), rangedWeapon.getWeaponID())
                    .set(Tag.Integer("ammo"), rangedWeapon.getMagazine())
                    .maxStackSize(rangedWeapon.getMagazine())
                    .amount(rangedWeapon.getMagazine())
                    .customName(Component.text(weaponName))
                    .build();
        } else {
            weaponItem = ItemStack.builder(Material.DEAD_BUSH)
                    .customName(Component.text("Ops. What i'm doing here??"))
                    .build();
        }
        return weaponItem;
    }

    public static void init() {

        RangedWeapon pistol = new RangedWeapon.Builder(Material.WOODEN_HOE)
                .setSound(SoundEvent.ENTITY_FIREWORK_ROCKET_BLAST)
                .setPitch(1.8f)
                .setWeaponId("pistol")
                .setDamage(2)
                .setKnockback(1.1)
                .setMagazine(10)
                .setSpreadFactor(0.02)
                .setReloadTime(2.3)
                .setRange(30)
                .setTracer(true)
                .setAttackSpeed(0.5)
                .build();
        RangedWeapon sniper = new RangedWeapon.Builder(Material.WOODEN_SHOVEL)
                .setSound(SoundEvent.ENTITY_FIREWORK_ROCKET_LARGE_BLAST)
                .setPitch(2f)
                .setWeaponId("sniper")
                .setDamage(20)
                .setKnockback(3)
                .setReloadTime(4.3)
                .setMagazine(5)
                .setSpreadFactor(0.005)
                .setPiercing(5)
                .setTracer(true)
                .setRange(50)
                .setAttackSpeed(1.2)
                .build();
        RangedWeapon shotgun = new RangedWeapon.Builder(Material.IRON_HOE)
                .setSound(SoundEvent.ENTITY_GENERIC_EXPLODE)
                .setPitch(2)
                .setWeaponId("shotgun")
                .setDamage(1)
                .setKnockback(5)
                .setReloadTime(3.6)
                .setTracer(true)
                .setMagazine(6)
                .setRange(15)
                .setBulletsPerShot(20)
                .setSpreadFactor(0.1)
                .setAttackSpeed(1.9)
                .build();

        registerWeapon(pistol);
        registerWeapon(sniper);
        registerWeapon(shotgun);
    }

}
