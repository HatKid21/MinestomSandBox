package org.example.utils;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import org.example.creatures.EnemyCreature;
import org.example.creatures.ZombieCreature;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class GeneratorManager {

    public static final Set<Generator> GENERATORS = new HashSet<>();

    public static void init(Instance instance) {
        Pos centerPos = new Pos(0, 0, 0);
        int radiusChunks = 10;

        int centerX = centerPos.chunkX();
        int centerZ = centerPos.chunkZ();

        for (int x = -radiusChunks; x < radiusChunks; x++) {
            for (int z = -radiusChunks; z < radiusChunks; z++) {
                int chunkX = centerX + x;
                int chunkZ = centerZ + z;

                CompletableFuture<Chunk> chunkFuture = instance.loadChunk(chunkX, chunkZ);

                chunkFuture.thenAccept(GeneratorManager::proceed).exceptionally(e -> {
                    System.out.println(e.getMessage());
                    return null;
                });

            }
        }

    }

    private static void proceed(Chunk chunk) {
        int chunkMinX = 0;
        int chunkMaxX = 16;
        int chunkMinZ = 0;
        int chunkMaxZ = 16;

        int worldMin = -64;
        int worldMax = 320;

        for (int x = chunkMinX; x < chunkMaxX; x++) {
            for (int y = worldMin; y < worldMax; y++) {
                for (int z = chunkMinZ; z < chunkMaxZ; z++) {
                    Block block = chunk.getBlock(new Pos(x, y, z));
                    if (block.equals(Block.EMERALD_BLOCK)) {
                        double worldX = chunk.getChunkX() * 16 + x + 0.5;
                        double worldZ = chunk.getChunkZ() * 16 + z + 0.5;

                        Pos globalPosition = new Pos(worldX, y, worldZ);
                        globalPosition = globalPosition.withYaw(180);
                        Supplier<EnemyCreature> supplier = ZombieCreature::new;
                        Generator generator = new Generator(chunk.getInstance(), supplier, 20, globalPosition);
                        GENERATORS.add(generator);
//                        System.out.println("Generator added at (" + globalPosition.x() + " " + globalPosition.y() + " " + globalPosition.z() + ")");
                    }
                }
            }
        }

    }


}
