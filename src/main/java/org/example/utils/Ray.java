package org.example.utils;

import net.kyori.adventure.text.Component;
import net.minestom.server.collision.BoundingBox;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.packet.server.play.ParticlePacket;
import net.minestom.server.particle.Particle;
import org.example.creatures.EnemyCreature;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Ray {

    private final double range;
    private final Instance instance;
    private final Pos startingPosition;
    private final Vec direction;
    private static final double step = 0.1;
    private static final BoundingBox boundingBox = new BoundingBox(new Vec(-0.1, -0.1, -0.1), new Vec(0.1, 0.1, 0.1));

    private int numberOfPiercing;
    private boolean tracer;

    public Ray(Pos source, Vec direction, Instance instance, double range) {
        this.startingPosition = source;
        this.direction = direction.normalize();
        this.instance = instance;
        tracer = false;
        numberOfPiercing = 1;
        this.range = range;
    }

    public void setPiercingAmount(int count) {
        numberOfPiercing = count;
    }

    public Collection<Entity> launch() {
        Collection<Entity> hitTargets = new HashSet<>();
        Particle particle = Particle.CRIT;
        Pos position = startingPosition;
        Collection<Entity> entities = instance.getNearbyEntities(startingPosition, range);
        Entity collisionTarget;
        for (int i = 0; i < range / step; i++) {

            if (numberOfPiercing == hitTargets.size()) {
                break;
            }

            if (!instance.getBlock(position).equals(Block.AIR)) {
                break;
            }
            if (tracer) {
                ParticlePacket particlePacket = new ParticlePacket(particle, position, new Pos(0, 0, 0), 0, 1);
                instance.sendGroupedPacket(particlePacket);
            }

            collisionTarget = collisionCheck(entities, position);

            if (collisionTarget != null) {
                if (hitTargets.contains(collisionTarget)) {
                    position = position.add(direction.mul(step));
                    continue;
                }
                if (collisionTarget instanceof EnemyCreature){
                    hitTargets.add(collisionTarget);
//                    instance.sendMessage(Component.text(collisionTarget.getEntityType().key().value()));
                }
            }

            position = position.add(direction.mul(step));
        }
        return hitTargets;
    }

    private Entity collisionCheck(Collection<Entity> entities, Pos currentPos) {
        for (Entity entity : entities) {
            if (boundingBox.intersectEntity(currentPos, entity)) {
                return entity;
            }
        }
        return null;
    }

    public void setTracer(boolean tracer) {
        this.tracer = tracer;
    }

}
