package org.example.player;

import org.jetbrains.annotations.NotNull;

public class CurrencyModule {

    private float currency;
    private final CustomPlayer player;

    public CurrencyModule(@NotNull CustomPlayer player){
        this.player = player;
    }

    public void add(float amount){
        currency += amount;
        player.updateScoreboard();
    }

    public float get(){
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
