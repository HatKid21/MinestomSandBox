package org.example.enemy;

import net.kyori.adventure.text.Component;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.registry.DynamicRegistry;
import org.jetbrains.annotations.NotNull;

public class Enemy extends EntityCreature {

    private String name;

    Enemy(@NotNull EntityType entityType) {
        super(entityType);
    }
    //TODO make this class the only class that represents the custom enemy and make a builder for making different mobs

    public void onHit(double damage, double knockback, Vec direction) {
        float currentHealth = getHealth();
        setHealth(currentHealth - (float) damage);
        setVelocity(direction.mul(knockback));
        setCustomName(Component.text(getHealth()));
    }

    public void setName(String name) {
        this.name = name;
    }
}
