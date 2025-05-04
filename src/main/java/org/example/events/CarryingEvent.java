package org.example.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerTickEndEvent;
import org.example.carrier.CarrierManager;


public class CarryingEvent {

    private static final double distanceFactor = 3.2;

    public CarryingEvent(){
        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
        handler.addListener(PlayerTickEndEvent.class, event ->{
            Player player = event.getPlayer();
            if (CarrierManager.getItem(player) != null){
                Entity entity = CarrierManager.getItem(player);
                Pos playerPos = player.getPosition().add(0,player.getEyeHeight(),0);

                entity.teleport(playerPos.add(playerPos.direction().normalize().mul(distanceFactor)));
            }
        });
    }

}
