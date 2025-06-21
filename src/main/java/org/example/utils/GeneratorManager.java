package org.example.utils;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Chunk;

import java.util.HashSet;
import java.util.Set;

public class GeneratorManager {

    public final Set<Generator> GENERATORS = new HashSet<>();

    public void proceed(Chunk chunk, int x, int y, int z){
        double worldX = chunk.getChunkX() * 16 + x + 0.5;
        double worldZ = chunk.getChunkZ() * 16 + z + 0.5;

        Pos globalPosition = new Pos(worldX, y, worldZ);
        globalPosition = globalPosition.withYaw(180);
        String enemyName = "zombie";
        Generator generator = new Generator(chunk.getInstance(), enemyName, 20, globalPosition);
        GENERATORS.add(generator);
    }


}
