package org.example.player;

import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.timer.Task;
import net.minestom.server.timer.TaskSchedule;
import org.example.Server;
import org.example.utils.Barricade;

public class BarricadeModule {

    private final CustomPlayer customPlayer;

    private final Runnable repairingLogic;
    private Task repairingTask;

    private boolean isRepairing;

    public BarricadeModule(CustomPlayer customPlayer){
        this.customPlayer = customPlayer;

        this.repairingLogic = () ->{
            if (!isRepairing){
                customPlayer.sendMessage(Component.text("Repairing was interrupted"));
                stopRepairing();
                return;
            }
            Barricade barricade = Server.getBarricadeManager().getNearbyBarricade(customPlayer.getPosition());
            if (barricade == null){
                stopRepairing();
                return;
            }
            int durability = barricade.getDurability();
            if (durability == 6){
                stopRepairing();
                return;
            }
            barricade.repair();
            customPlayer.getCurrencyModule().add(10);
            if (durability == 5){
                customPlayer.sendMessage(Component.text("Barricade has successfully repaired"));
            }
        };

    }

    public void stopRepairing(){
        isRepairing = false;
        if (repairingTask != null && repairingTask.isAlive()){
            repairingTask.cancel();
            repairingTask = null;
        }
    }

    public void startRepairing(){
        stopRepairing();
        isRepairing = true;
        repairingTask = MinecraftServer.getSchedulerManager().scheduleTask(repairingLogic, TaskSchedule.millis(500), TaskSchedule.seconds(1));
    }

}
