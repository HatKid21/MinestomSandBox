package org.example.player;

import org.jetbrains.annotations.NotNull;

public class CurrencyModule {

    private double currency;
    private final CustomPlayer player;

    public CurrencyModule(@NotNull CustomPlayer player){
        this.player = player;
    }

    public void add(double amount){
        currency += amount;
        player.updateScoreboard();
    }

    public double get(){
        return currency;
    }

    public boolean consume(float amount){
        if (currency < amount){
            return false;
        }
        currency -= amount;
        return true;
    }

}
