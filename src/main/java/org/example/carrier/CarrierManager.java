package org.example.carrier;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CarrierManager {

    private static final Map<UUID, Entity> uuidItemStackMap = new HashMap<>();

    private static void registerCarrier(Player player) {
        UUID uuid = player.getUuid();
        uuidItemStackMap.put(uuid, null);
    }

    public static void unregisterCarrier(Player player) {
        UUID uuid = player.getUuid();
        uuidItemStackMap.remove(uuid);
    }

    public static void removeItem(Player player) {
        uuidItemStackMap.get(player.getUuid()).setNoGravity(false);
        uuidItemStackMap.put(player.getUuid(), null);
    }

    public static Entity getItem(Player player) {
        if (!uuidItemStackMap.containsKey(player.getUuid())) {
            registerCarrier(player);
        }
        return uuidItemStackMap.get(player.getUuid());
    }

    public static void setItem(Player player, Entity item) {
        item.setNoGravity(true);
        uuidItemStackMap.put(player.getUuid(), item);
    }

}
