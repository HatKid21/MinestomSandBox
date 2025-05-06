package org.example.player;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.network.player.GameProfile;
import net.minestom.server.network.player.PlayerConnection;
import org.example.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CustomPlayer extends Player {

    private int currency = 0;
    private final Map<String, Integer> AMMO = new HashMap<>();
    private final Scoreboard scoreboard = new Scoreboard(this);

    public CustomPlayer(@NotNull PlayerConnection playerConnection, @NotNull GameProfile gameProfile) {
        super(playerConnection, gameProfile);
    }

    public int getCurrency() {
        return currency;
    }

    public void addCurrency(int value) {
        currency += value;
        updateScoreboard();
    }

    private void updateScoreboard() {
        scoreboard.putLine(1, Component.text("Currency : " + currency));
    }

    public void consumeCurrency(int value) {
        currency -= value;
        updateScoreboard();
    }

    public void addAmmo(String weaponId, int value) {
        AMMO.merge(weaponId, value, Integer::sum);
    }

    public boolean consumeAmmo(String weaponId, int value) {
        int currentAmmo = AMMO.getOrDefault(weaponId, 0);
        if (currentAmmo >= value) {
            AMMO.put(weaponId, currentAmmo - value);
            return true;
        }
        return false;
    }

    public int getAmmo(String weaponId) {
        return AMMO.get(weaponId);
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

}
