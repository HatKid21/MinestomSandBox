package org.example.weapons;

import net.minestom.server.entity.Player;
import net.minestom.server.tag.Tag;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AmmoManager {

    private static final Map<UUID, Map<String, Integer>> playerAmmo = new HashMap<>();

    public static int getAmmo(Player player, String weaponID) {
        if (playerAmmo.get(player.getUuid()) == null){
            return 0;
        }
        return playerAmmo.get(player.getUuid()).get(weaponID);
    }

    public static void addAmmo(Player player, String weaponID, int amount) {
        playerAmmo.computeIfAbsent(player.getUuid(), k ->
                new HashMap<>()).merge(weaponID, amount, Integer::sum);
    }

    public static boolean consumeAmmo(Player player, String weaponID, int amount) {
        Map<String, Integer> ammoMap = playerAmmo.get(player.getUuid());
        if (ammoMap == null) {
            return false;
        }

        int currentAmmo = ammoMap.getOrDefault(weaponID, 0);
        if (currentAmmo == 0) {
            return false;
        }
        if (currentAmmo >= amount) {
            ammoMap.put(weaponID, currentAmmo - amount);
            return true;
        }
        return false;

    }

    public static void onPlayerJoin(Player player) {
        playerAmmo.computeIfAbsent(player.getUuid(), k -> new HashMap<>());
    }

    public static void onPlayerLeave(Player player) {
        playerAmmo.remove(player.getUuid());
    }

}
