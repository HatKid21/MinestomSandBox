package org.example.powerup;

public class PowerUpConfig {

    private String powerUpName;
    private double chance;

    PowerUpConfig(String powerUpName, double chance){
        this.chance = chance;
        this.powerUpName = powerUpName;
    }

    public String getPowerUpName() {
        return powerUpName;
    }

    public double getChance() {
        return chance;
    }
}
