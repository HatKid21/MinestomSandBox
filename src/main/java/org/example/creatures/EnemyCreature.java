package org.example.creatures;

import net.kyori.adventure.text.Component;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class EnemyCreature extends EntityCreature {


    public EnemyCreature(@NotNull EntityType entityType) {
        super(entityType);
    }

    public void onHit(double damage, double knockback, Vec direction){
        float currentHealth = getHealth();
        setHealth(currentHealth - (float) damage);
        setVelocity(direction.mul(knockback));
        setCustomName(Component.text(getHealth()));
    }

}
