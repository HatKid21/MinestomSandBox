package org.example.player;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.attribute.Attribute;
import net.minestom.server.network.player.GameProfile;
import net.minestom.server.network.player.PlayerConnection;
import net.minestom.server.timer.Task;
import net.minestom.server.timer.TaskSchedule;
import org.jetbrains.annotations.NotNull;

public class RegenerativePlayer extends Player {

    private final float healingPower = 1;
    private final long regenerationDelay = 5;
    private boolean regeneration;
    private Task regenerationDelayTask;
    private Task regenerationTask;

    private final Runnable regenerationLogic = () ->{
        if(regeneration){
            float currentHealth = getHealth();
            float maxHealth = (float) getAttribute(Attribute.MAX_HEALTH).getValue();
            if (currentHealth < maxHealth){
                setHealth(Math.min(currentHealth + healingPower,maxHealth));
            } else{
                stopRegeneration();
            }
        } else{
            stopRegeneration();
        }
    };

    private final Runnable regenerationDelayLogic = () ->{
        regeneration = true;
        regenerationTask = MinecraftServer.getSchedulerManager().scheduleTask(regenerationLogic, TaskSchedule.nextTick(),TaskSchedule.tick(20));
    };

    public RegenerativePlayer(@NotNull PlayerConnection playerConnection, @NotNull GameProfile gameProfile) {
        super(playerConnection, gameProfile);
    }

    public void stopRegeneration(){
        regeneration = false;
        if (regenerationTask != null && regenerationTask.isAlive()){
            regenerationTask.cancel();
            regenerationTask = null;
        }

        if (regenerationDelayTask != null && regenerationDelayTask.isAlive()){
            regenerationDelayTask.cancel();
            regenerationDelayTask = null;
        }
    }

    public void startRegenerationDelay(){
        stopRegeneration();
        regenerationDelayTask = MinecraftServer.getSchedulerManager().scheduleTask(regenerationDelayLogic,TaskSchedule.seconds(regenerationDelay),TaskSchedule.stop());
    }

}
