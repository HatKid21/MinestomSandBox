package org.example.commands;

import net.kyori.adventure.text.Component;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import org.example.player.CustomPlayer;

public class StatisticCommand extends Command {

    public StatisticCommand(){
        super("stat");

        setDefaultExecutor((sender, context) ->{
            CustomPlayer player = (CustomPlayer) sender;
            player.sendMessage(Component.text(player.getAmmo("pistol")));
        });

    }

}
