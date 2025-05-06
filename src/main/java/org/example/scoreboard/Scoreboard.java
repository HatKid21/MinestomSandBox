package org.example.scoreboard;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.scoreboard.Sidebar;

public class Scoreboard extends Sidebar {

    public Scoreboard(Player player) {
        super(Component.text("Minestom sandbox"));

        putEmptyLine(0);
        putEmptyLine(15);

    }

    public void putLine(int line, Component text) {
        if (getLine(String.valueOf(line)) == null) {
            createLine(new ScoreboardLine(String.valueOf(line), text, line, NumberFormat.blank()));
        } else {
            updateLineContent(String.valueOf(line), text);
        }
    }

    public void putEmptyLine(int line) {
        putLine(line, Component.empty());
    }

    public void removeLine(int line) {
        removeLine(String.valueOf(line));
    }

    public void clearScoreboard() {
        for (ScoreboardLine line : getLines()) {
            if (line.getLine() == 0 || line.getLine() == 15) continue;
            removeLine(line.getLine());
        }
    }

}
