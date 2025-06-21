package org.example.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
        player.sendMessage(Component.text("Coins +" + amount, NamedTextColor.GOLD));
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
