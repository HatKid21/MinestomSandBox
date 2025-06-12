package org.example.enemy;

import org.example.powerup.PowerUpConfig;

import java.util.List;

public class EnemyConfig {

    private String name;
    private String entityType;
    private String helmet;
    private String chestplate;
    private String leggings;
    private String boots;
    private float health;
    private float speed;
    private String aiStrategyType;
    private List<PowerUpConfig> powerUpConfigList;



    public String getName() {
        return name;
    }

    public String getEntityType() {
        return entityType;
    }

    public float getSpeed() {
        return speed;
    }

    public float getHealth() {
        return health;
    }

    public String getAiStrategyType() {
        return aiStrategyType;
    }

    public String getBoots() {
        return boots;
    }

    public String getChestplate() {
        return chestplate;
    }

    public String getHelmet() {
        return helmet;
    }

    public String getLeggings() {
        return leggings;
    }
}
