package org.example.scoreboard;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.scoreboard.Sidebar;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class    Scoreboard extends Sidebar {

    private final static Map<UUID, Scoreboard> SCOREBOARDS = new ConcurrentHashMap<>();

    public Scoreboard(Player player) {
        super(Component.text("Minestom sandbox"));

        putEmptyLine(0);
        putEmptyLine(15);

        addViewer(player);

    }

    public void putLine(int line, Component text){
        if (getLine(String.valueOf(line)) == null){
            createLine(new ScoreboardLine(String.valueOf(line),text,line,NumberFormat.blank()));
        } else{
            updateLineContent(String.valueOf(line),text);
        }
    }

    public void putEmptyLine(int line){
        putLine(line,Component.empty());
    }

    public void removeLine(int line){
        removeLine(String.valueOf(line));
    }

    public void clearScoreboard(){
        for(ScoreboardLine line : getLines()){
            if (line.getLine() == 0 || line.getLine() == 15) continue;
            removeLine(line.getLine());
        }
    }

    public static Scoreboard getScoreboard(Player player){
        return SCOREBOARDS.computeIfAbsent(player.getUuid(), key -> new Scoreboard(player));
    }

    public static void removeScoreboard(Player player){
        SCOREBOARDS.remove(player.getUuid());
    }

}
