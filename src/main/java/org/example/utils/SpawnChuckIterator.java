package org.example.utils;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockFace;
import org.example.Server;

import java.util.concurrent.CompletableFuture;

public class SpawnChuckIterator {

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

                chunkFuture.thenAccept(SpawnChuckIterator::proceed).exceptionally(e -> {
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
                        Server.getGeneratorManager().proceed(chunk,x,y,z);
//                        System.out.println("Generator added at (" + globalPosition.x() + " " + globalPosition.y() + " " + globalPosition.z() + ")");
                    } else if (block.equals(Block.REDSTONE_BLOCK)){
                        Server.getBarricadeManager().proceed(chunk,x,y,z, BlockFace.SOUTH);
                    } else if (block.equals(Block.DIAMOND_BLOCK)){
                        Server.getBarricadeManager().proceed(chunk,x,y,z, BlockFace.WEST);
                    }
                }
            }
        }

    }

}
