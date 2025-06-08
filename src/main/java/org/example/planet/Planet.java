package org.example.planet;

import net.minestom.server.collision.BoundingBox;
import net.minestom.server.coordinate.Pos;

import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.metadata.display.BlockDisplayMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.packet.server.play.ParticlePacket;
import net.minestom.server.particle.Particle;

import java.util.ArrayList;
import java.util.List;


public class Planet extends Entity {

    private final double mass;
    private final Block block;
    private final double size;
    private final boolean movable;

    private static final List<Planet> existedPlanets = new ArrayList<>();

    public Planet(Block block, double size, double mass,boolean movable) {
        super(EntityType.BLOCK_DISPLAY);
        this.block = block;
        this.size = size;
        this.mass = mass;
        this.movable = movable;
    }
    public void addVelocity(Vec velocity){
        if (movable){
            setVelocity(getVelocity().add(velocity));
        }
    }

    public void spawn(Instance instance, Pos position) {
        BlockDisplayMeta meta = (BlockDisplayMeta) getEntityMeta();
        meta.setBlockState(block);
        meta.setScale(new Vec(size, size, size));
        meta.setPosRotInterpolationDuration(1);
        meta.setTransformationInterpolationDuration(1);
        setNoGravity(true);
        setInstance(instance, position);
        existedPlanets.add(this);
    }

    @Override
    protected void movementTick() {
        ParticlePacket particlePacket = new ParticlePacket(Particle.CRIT, getCenter(), new Pos(0, 0, 0), 0, 1);
        getInstance().sendGroupedPacket(particlePacket);
        super.movementTick();

    }


    public Pos getCenter(){
        return getPosition().add(new Pos(size/2,size/2,size/2));
    }

    public void remove(Entity entity){
        if (entity instanceof Planet){
            existedPlanets.remove(entity);
            entity.remove();
        }
    }

    public static List<Planet> getExistedPlanets() {
        return existedPlanets;
    }

    public Block getBlock() {
        return block;
    }

    public double getMass() {
        return mass;
    }

    public double getSize() {
        return size;
    }

    public boolean isMovable() {
        return movable;
    }
}
