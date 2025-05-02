package org.example.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.collision.CollisionUtils;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.attribute.Attribute;
import net.minestom.server.entity.attribute.AttributeModifier;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.entity.EntityAttackEvent;
import net.minestom.server.event.item.PlayerBeginItemUseEvent;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import net.minestom.server.item.ItemStack;
import org.example.carrierFeature.CarrierManager;

import java.util.Objects;

public class PickUpEvent {

    public PickUpEvent(){
        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();

        handler.addListener(PlayerEntityInteractEvent.class, event ->{
            Player player = event.getPlayer();
            Entity entity = event.getTarget().getVehicle();
            Point interactPosition = event.getInteractPosition();
            if (entity != null && player.getItemInMainHand().equals(ItemStack.AIR)){
                if (interactPosition.y() < 0.6){
                    if (CarrierManager.getItem(player) != null){
                        player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).setBaseValue(3);
                        entity.setGlowing(false);
                        CarrierManager.removeItem(player);
                    } else{
                        player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).setBaseValue(10);
                        entity.setGlowing(true);
                        CarrierManager.setItem(player, Objects.requireNonNull(entity));
                    }
                }

            }
        });
    }

}
