package org.example.economy;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import org.example.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CurrencyManager {

    private static final Map<UUID,Integer> PLAYER_CURRENCY_MAP = new HashMap<>();

    public static void registerPlayer(@NotNull Player player){
        PLAYER_CURRENCY_MAP.putIfAbsent(player.getUuid(), 0);
        updatePlayerScoreboardCurrency(player);
    }

    public static void unregisterPlayer(@NotNull Player player){
        PLAYER_CURRENCY_MAP.remove(player.getUuid());
    }

    public static int getPlayerCurrency(@NotNull Player player){
        if (!PLAYER_CURRENCY_MAP.containsKey(player.getUuid())){
            registerPlayer(player);
        }
        return PLAYER_CURRENCY_MAP.get(player.getUuid());
    }

    public static void addCurrency(@NotNull Player player, int amount){
        PLAYER_CURRENCY_MAP.compute(player.getUuid(), (k, currentCurrency) -> currentCurrency + amount);
        updatePlayerScoreboardCurrency(player);
    }

    public static boolean consumeCurrency(@NotNull Player player, int amount){
        int currentCurrency = PLAYER_CURRENCY_MAP.get(player.getUuid());
        if (currentCurrency >= amount){
            PLAYER_CURRENCY_MAP.compute(player.getUuid(),(k, currency) -> currency - amount);
            updatePlayerScoreboardCurrency(player);
            return true;
        }
        return false;
    }

    private static void updatePlayerScoreboardCurrency(Player player){
        Scoreboard scoreboard = Scoreboard.getScoreboard(player);
        Component text = Component.text("Currency : " + getPlayerCurrency(player));
        scoreboard.putLine(1, text);
    }

}
