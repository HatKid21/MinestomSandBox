package org.example.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.attribute.Attribute;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.entity.EntityAttackEvent;
import org.example.enemy.Enemy;
import org.example.player.CustomPlayer;


public class EnemyAttackEvent {

    public EnemyAttackEvent(){
        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
        handler.addListener(EntityAttackEvent.class, event -> {
            Entity entity = event.getEntity();
            Entity target = event.getTarget();
            if (entity instanceof Enemy enemy && target instanceof CustomPlayer player){
                player.damage(DamageType.PLAYER_ATTACK,(float)enemy.getAttribute(Attribute.ATTACK_DAMAGE).getValue());
            }
        });
    }

}
