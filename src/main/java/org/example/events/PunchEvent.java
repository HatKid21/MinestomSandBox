package org.example.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.attribute.Attribute;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.entity.EntityAttackEvent;

import org.example.carrier.CarrierManager;

public class PunchEvent {

    private static final double forceFactor = 10;

    public PunchEvent() {
        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
        handler.addListener(EntityAttackEvent.class, event -> {
            Entity entity = event.getEntity();
            if (entity instanceof Player player) {
                Vec direction = player.getPosition().direction().normalize();
                if (CarrierManager.getItem(player) != null) {
                    Entity item = CarrierManager.getItem(player);
                    CarrierManager.removeItem(player);
                    player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).setBaseValue(3);
                    item.setNoGravity(false);
                    item.setGlowing(false);
                    item.setVelocity(direction.mul(forceFactor));
                }
            }
        });
    }

}
