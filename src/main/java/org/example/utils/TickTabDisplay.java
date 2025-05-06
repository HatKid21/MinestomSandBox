package org.example.utils;

import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.timer.Task;
import net.minestom.server.timer.TaskSchedule;

public class TickTabDisplay {

    private Component header = Component.text("Minestom Server");
    private Component footer = Component.text("Waiting for data...");

    private Task taskMonitor;

    public void start() {
        taskMonitor = MinecraftServer.getSchedulerManager().submitTask(() -> {
            for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                player.sendPlayerListHeaderAndFooter(header, footer);
            }
            return TaskSchedule.seconds(2);
        });
    }

    public void stop() {
        if (taskMonitor != null && taskMonitor.isAlive()) {
            taskMonitor.cancel();
            taskMonitor = null;
        }
    }

    public void setFooter(Component footer) {
        this.footer = footer;
    }

    public void setHeader(Component header) {
        this.header = header;
    }
}
