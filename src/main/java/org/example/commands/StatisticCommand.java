package org.example.commands;

import net.kyori.adventure.text.Component;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import org.example.weapons.AmmoManager;

public class StatisticCommand extends Command {

    public StatisticCommand(){
        super("stat");

        setDefaultExecutor((sender, context) ->{
            Player player = (Player) sender;
            player.sendMessage(Component.text(AmmoManager.getAmmo(player,"pistol")));
        });

    }

}
