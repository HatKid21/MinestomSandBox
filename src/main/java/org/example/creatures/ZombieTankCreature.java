package org.example.creatures;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.EquipmentSlot;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.ai.goal.MeleeAttackGoal;
import net.minestom.server.entity.ai.goal.RandomStrollGoal;
import net.minestom.server.entity.ai.target.ClosestEntityTarget;
import net.minestom.server.entity.ai.target.LastEntityDamagerTarget;
import net.minestom.server.entity.attribute.Attribute;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.utils.time.TimeUnit;

import java.util.List;

public class ZombieTankCreature extends EnemyCreature {


    public ZombieTankCreature() {
        super(EntityType.ZOMBIE);
        setEquipment(EquipmentSlot.CHESTPLATE, ItemStack.of(Material.IRON_CHESTPLATE));
        setEquipment(EquipmentSlot.LEGGINGS, ItemStack.of(Material.IRON_LEGGINGS));
        setEquipment(EquipmentSlot.BOOTS, ItemStack.of(Material.IRON_BOOTS));
        getAttribute(Attribute.MAX_HEALTH).setBaseValue(50);
        setHealth(50);
        getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.06);
        setCustomNameVisible(true);
        setCustomName(Component.text(getHealth()));

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
}
