package org.example.utils;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.timer.Task;
import net.minestom.server.timer.TaskSchedule;
import org.example.creatures.EnemyCreature;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class Generator {

    private final int delaySeconds;
    private final Supplier<EnemyCreature> supplier;
    private final Pos pos;
    private final Instance instance;

    private EnemyCreature currentEnemy;
    private Task monitoringTask;

    public Generator(Instance instance, Supplier<EnemyCreature> creature, int delaySeconds, Pos pos) {
        this.supplier = creature;
        this.delaySeconds = delaySeconds;
        this.pos = pos;
        this.instance = instance;
        instance.setBlock(pos, Block.AIR);
        spawnNewEnemy();
        startGenerating();
    }

    private void startGenerating() {

        monitoringTask = MinecraftServer.getSchedulerManager().submitTask(() -> {
            if (currentEnemy != null && currentEnemy.isRemoved()) {
                currentEnemy = null;

                AtomicInteger iterations = new AtomicInteger();

                MinecraftServer.getSchedulerManager().submitTask(() -> {
                    if (iterations.get() == 1) {
                        spawnNewEnemy();
                        return TaskSchedule.stop();
                    }
                    iterations.getAndIncrement();
                    return TaskSchedule.seconds(delaySeconds);
                });
            }
            return TaskSchedule.tick(5);
        });

    }

    private void spawnNewEnemy() {
        EnemyCreature newEnemy = supplier.get();
        if (!Objects.requireNonNull(instance.getChunkAt(pos)).isLoaded()) {
            instance.loadChunk(pos);
        }
        newEnemy.setInstance(instance, pos).thenAccept(v -> {
            this.currentEnemy = newEnemy;

        }).exceptionally(e -> {
            System.out.println(e.getMessage());

            return null;
        });
    }

    public void stopGenerating() {
        if (monitoringTask != null) {
            monitoringTask.cancel();
            monitoringTask = null;
        }
        if (currentEnemy != null && !currentEnemy.isRemoved()) {
            currentEnemy.remove();
            currentEnemy = null;
        }
    }

}
