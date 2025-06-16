package org.example.player;

import org.example.weapons.RangedWeapon;
import org.example.weapons.WeaponRegistry;

import java.util.HashMap;
import java.util.Map;

public class AmmoModule {

    private final Map<String ,Integer> AMMO = new HashMap<>();

    public int get(String weaponId){
        return AMMO.get(weaponId);
    }

    public void add(String weaponId, int amount){
        RangedWeapon rangedWeapon = (RangedWeapon) WeaponRegistry.getWeaponById(weaponId);
        int maxAmmoSize = rangedWeapon.getMaxAmmoSize();
        int currentAmmo = AMMO.getOrDefault(weaponId,0);
        AMMO.put(weaponId, Math.min(maxAmmoSize,currentAmmo + amount));
    }

    public boolean consume(String weaponId, int amount){
        int currentAmmo = AMMO.getOrDefault(weaponId, 0);
        if (currentAmmo >= amount) {
            AMMO.put(weaponId, currentAmmo - amount);
            return true;
        }
        return false;

    }

}
