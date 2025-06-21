package org.example.player;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.network.player.GameProfile;
import net.minestom.server.network.player.PlayerConnection;
import org.example.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

public class CustomPlayer extends Player {

    private final RegenerationModule regenerationModule;
    private final AmmoModule ammoModule;
    private final CurrencyModule currencyModule;
    private final Scoreboard scoreboard;
    private final BarricadeModule barricadeModule;

    public CustomPlayer(@NotNull PlayerConnection playerConnection, @NotNull GameProfile gameProfile) {
        super(playerConnection, gameProfile);
        this.currencyModule = new CurrencyModule(this);
        this.ammoModule = new AmmoModule();
        this.regenerationModule = new RegenerationModule(this);
        this.scoreboard = new Scoreboard(this);
        this.barricadeModule = new BarricadeModule(this);
    }

    public BarricadeModule getBarricadeModule() {
        return barricadeModule;
    }

    public RegenerationModule getRegenerationModule() {
        return regenerationModule;
    }

    public CurrencyModule getCurrencyModule() {
        return currencyModule;
    }

    public AmmoModule getAmmoModule() {
        return ammoModule;
    }

    public void updateScoreboard() {
        scoreboard.putLine(1, Component.text("Currency : " + currencyModule.get()));
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

}
