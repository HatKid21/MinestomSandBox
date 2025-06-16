package org.example.player;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.attribute.Attribute;
import net.minestom.server.timer.Task;
import net.minestom.server.timer.TaskSchedule;
import org.jetbrains.annotations.NotNull;

public class RegenerationModule {

    private final float healingPower = 1;
    private final long regenerationDelay = 5;
    private boolean regeneration;
    private Task regenerationDelayTask;
    private Task regenerationTask;
    private final Player player;

    private final Runnable regenerationLogic;
    private final Runnable regenerationDelayLogic;

    public RegenerationModule(@NotNull Player player){
        this.player = player;
        this.regenerationLogic = () ->{
            if(regeneration){
                float currentHealth = player.getHealth();
                float maxHealth = (float) player.getAttribute(Attribute.MAX_HEALTH).getValue();
                if (currentHealth < maxHealth){
                    player.setHealth(Math.min(currentHealth + healingPower,maxHealth));
                } else{
                    stopRegeneration();
                }
            } else{
                stopRegeneration();
            }
        };

        this.regenerationDelayLogic = () ->{
            regeneration = true;
            regenerationTask = MinecraftServer.getSchedulerManager().scheduleTask(regenerationLogic, TaskSchedule.nextTick(),TaskSchedule.tick(20));
        };

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
