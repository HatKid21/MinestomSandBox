package org.example.utils;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockFace;

public class Barricade {

    private final Pos center;
    private final BlockFace orientation;
    private byte durability;
    private final Instance instance;
    private final boolean[][] barricadeState = new boolean[3][2];
    private static final Block BARRICADE_BLOCK = Block.OAK_SLAB.withProperty("type","top");
    private final Pos controlPos;

    public Barricade(Instance instance,Pos center, BlockFace orientation){
        this.center = center;
        this.instance = instance;
        this.orientation = orientation;
        durability = 6;
        if (orientation.equals(BlockFace.WEST) || orientation.equals(BlockFace.EAST)){
            controlPos = new Pos(0,0,1);
        } else{
            controlPos = new Pos(1,0,0);
        }
        init();
    }

    private void init(){
        for (int d = -1; d < 2; d++) {
            for (int y = 0; y < 2; y++) {
                Pos pos = center.add(controlPos.mul(d)).add(0,y,0);
                barricadeState[d+1][y] = true;
                instance.setBlock(pos, BARRICADE_BLOCK);
            }
        }
    }

    public byte getDurability() {
        return durability;
    }

    public void hit(){
        if (durability - 1 < 0){
            return;
        }
        durability--;
        for (int d = 0; d < 3; d++) {
            for (int y = 0; y < 2; y++) {
                boolean state = barricadeState[d][y];
                if (state){
                    barricadeState[d][y] = false;
                    instance.setBlock(center.add(controlPos.mul(d-1).add(0,y,0)),Block.AIR);
                    return;
                }
            }
        }
    }

    public void repair(){
        if (durability + 1 > 6){
            return;
        }
        durability++;
        for (int d = 0; d < 3; d++) {
            for (int y = 0; y < 2; y++) {
                boolean state = barricadeState[d][y];
                if (!state){
                    barricadeState[d][y] = true;
                    instance.setBlock(center.add(controlPos.mul(d-1).add(0,y,0)),BARRICADE_BLOCK);
                    return;
                }
            }
        }
    }

    public Pos getCenter() {
        return center;
    }

    public BlockFace getOrientation() {
        return orientation;
    }

}
