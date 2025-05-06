package org.example.creatures;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.ai.goal.MeleeAttackGoal;
import net.minestom.server.entity.ai.goal.RandomStrollGoal;
import net.minestom.server.entity.ai.target.ClosestEntityTarget;
import net.minestom.server.entity.ai.target.LastEntityDamagerTarget;
import net.minestom.server.entity.attribute.Attribute;
import net.minestom.server.item.Material;
import net.minestom.server.utils.time.TimeUnit;
import org.example.powerup.PowerUp;
import org.example.powerup.effects.AmmoSupplierEffect;

import java.util.List;

public class ZombieCreature extends EnemyCreature {

    public ZombieCreature() {
        super(EntityType.ZOMBIE);
        getAttribute(Attribute.MAX_HEALTH).setBaseValue(20);
        setHealth(20);
        setCustomNameVisible(true);
        setCustomName(Component.text(getHealth()));
        getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.1);
        addAIGroup(
                List.of(
                        new MeleeAttackGoal(this, 1.6, 20, TimeUnit.SERVER_TICK),
                        new RandomStrollGoal(this, 20)
                ),
                List.of(new LastEntityDamagerTarget(this, 10),
                        new ClosestEntityTarget(this, 10, entity -> entity instanceof Player)
                )
        );

    }

    @Override
    public void remove() {
        PowerUp powerUp = new PowerUp(Material.BONE, new AmmoSupplierEffect(), Component.text("Ammo"));
        powerUp.setInstance(getInstance(), getPosition().add(0, 1, 0));
        super.remove();
    }
}
