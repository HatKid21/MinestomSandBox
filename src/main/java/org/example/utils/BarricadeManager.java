package org.example.utils;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.block.BlockFace;

import java.util.HashSet;
import java.util.Set;

public class BarricadeManager {

    private final Set<Barricade> barricadeSet = new HashSet<>();

    public void proceed(Chunk chunk, int x, int y, int z, BlockFace direction){
        Pos globalPos = new Pos(chunk.getChunkX() * 16 + x,y,chunk.getChunkZ() * 16 + z);
        Barricade barricade = new Barricade(chunk.getInstance(),globalPos, direction);
        barricadeSet.add(barricade);
    }

    public Barricade getNearbyBarricade(Pos pos){
        for (Barricade barricade : barricadeSet){
            Pos barricadeCenter = barricade.getCenter();
            if (barricadeCenter.distance(pos) <= 2){
                return barricade;
            }
        }
        return null;
    }

    public Set<Barricade> getBarricadeSet() {
        return barricadeSet;
    }
}
