package org.example.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.entity.EntityDamageEvent;
import org.example.player.CustomPlayer;

public class DamageEvent {

    public DamageEvent(){
        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
        handler.addListener(EntityDamageEvent.class, event ->{
            LivingEntity entity = event.getEntity();
            if (entity instanceof CustomPlayer player){
                player.startRegenerationDelay();
            }
        });

    }

}
