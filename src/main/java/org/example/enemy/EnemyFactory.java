package org.example.enemy;

import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.ai.EntityAIGroupBuilder;
import net.minestom.server.entity.ai.goal.MeleeAttackGoal;
import net.minestom.server.entity.ai.goal.RandomStrollGoal;
import net.minestom.server.entity.ai.target.ClosestEntityTarget;
import net.minestom.server.entity.ai.target.LastEntityDamagerTarget;
import net.minestom.server.entity.attribute.Attribute;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.utils.time.TimeUnit;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnemyFactory {

    private static final Logger LOGGER = Logger.getLogger(EnemyFactory.class.getName());

    private static final Map<String, EnemyConfig> configMap = new HashMap<>();

    private static final Gson gson = new Gson();

    public EnemyFactory() {

    }

    public Enemy createEnemy(String name) {
        if (configMap.containsKey(name)) {
            return getEnemyFromConfig(configMap.get(name));
        }
        String path = "enemy/" + name + ".json";
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
            assert inputStream != null;
            Reader reader = new InputStreamReader(inputStream);
            EnemyConfig enemyConfig = gson.fromJson(reader, EnemyConfig.class);
            configMap.put(name, enemyConfig);
            return getEnemyFromConfig(enemyConfig);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Resource folder reading error", e);
        }
        return null;
    }


    private Enemy getEnemyFromConfig(EnemyConfig enemyConfig) {
        String entityType = enemyConfig.getEntityType();
        String name = enemyConfig.getName();
        float health = enemyConfig.getHealth();
        float speed = enemyConfig.getSpeed();
        Enemy enemy = new Enemy(EntityType.fromKey(entityType));
        enemy.getAttribute(Attribute.MAX_HEALTH).setBaseValue(health);
        enemy.setHealth(health);
        enemy.setCustomNameVisible(true);
        enemy.setCustomName(Component.text(health));
        enemy.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(speed);
        enemy.setName(name);

        proceedEquipment(enemy, enemyConfig);
        proceedAi(enemy, enemyConfig);

        return enemy;
    }

    private void proceedAi(Enemy enemy, EnemyConfig enemyConfig) {
        String aiStrategyType = enemyConfig.getAiStrategyType();
        if (aiStrategyType.equals("MeleeAttackAi")) {
            EntityAIGroupBuilder builder = new EntityAIGroupBuilder();
            builder.addGoalSelector(new MeleeAttackGoal(enemy, 1.6, 20, TimeUnit.SERVER_TICK));
            builder.addGoalSelector(new RandomStrollGoal(enemy, 20));
            builder.addTargetSelector(new LastEntityDamagerTarget(enemy, 10));
            builder.addTargetSelector(new ClosestEntityTarget(enemy, 10, entity -> entity instanceof Player));
            enemy.addAIGroup(builder.build());
        }
    }

    private void proceedEquipment(Enemy enemy, EnemyConfig enemyConfig) {

        String helmet = enemyConfig.getHelmet();
        String chestplate = enemyConfig.getChestplate();
        String leggings = enemyConfig.getLeggings();
        String boots = enemyConfig.getBoots();

        if (helmet != null) {
            enemy.setHelmet(ItemStack.of(Objects.requireNonNull(Material.fromKey(helmet))));
        }
        if (chestplate != null) {
            enemy.setChestplate(ItemStack.of(Objects.requireNonNull(Material.fromKey(chestplate))));
        }
        if (leggings != null) {
            enemy.setLeggings(ItemStack.of(Objects.requireNonNull(Material.fromKey(leggings))));
        }
        if (boots != null) {
            enemy.setBoots(ItemStack.of(Objects.requireNonNull(Material.fromKey(boots))));
        }
    }


}
