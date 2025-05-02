package org.example.creatures;

import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public abstract class EnemyCreature extends EntityCreature {


    public EnemyCreature(@NotNull EntityType entityType) {
        super(entityType);
    }

    public abstract void onHit(double damage, double knockback, Vec direction);

}
