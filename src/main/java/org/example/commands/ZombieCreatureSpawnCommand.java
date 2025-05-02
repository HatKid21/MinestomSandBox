package org.example.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import org.example.creatures.ZombieCreature;

public class ZombieCreatureSpawnCommand extends Command {

    public ZombieCreatureSpawnCommand(){
        super("zombie");

        setDefaultExecutor((sender,context) ->{
            Player player = (Player) sender;
            Instance instance = player.getInstance();
            ZombieCreature zombieCreature = new ZombieCreature();
            zombieCreature.setInstance(instance,player.getPosition());
            player.sendMessage("Zombie has successfully created");
        });

        var numberArgument = ArgumentType.Integer("amount");

        addSyntax((sender,context) ->{
            int amount = context.get("amount");
            Player player = (Player) sender;
            Instance instance = player.getInstance();
            for (int i = 0; i < amount; i++) {
                ZombieCreature zombieCreature = new ZombieCreature();
                zombieCreature.setInstance(instance,player.getPosition());
            }
            player.sendMessage(amount + "zombies has successfully created");
        },numberArgument);

    }

}
