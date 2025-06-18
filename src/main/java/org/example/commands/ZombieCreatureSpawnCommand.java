package org.example.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import org.example.Server;
import org.example.enemy.Enemy;
import org.example.enemy.EnemyFactory;

public class ZombieCreatureSpawnCommand extends Command {

    private final EnemyFactory factory;


    public ZombieCreatureSpawnCommand() {
        super("zombie");

        this.factory = Server.getEnemyFactory();

        setDefaultExecutor((sender, context) -> {
            Player player = (Player) sender;
            Instance instance = player.getInstance();
            Enemy zombie = factory.createEnemy("zombie");
            zombie.setInstance(instance, player.getPosition());
            player.sendMessage("Zombie has successfully created");
        });

        var numberArgument = ArgumentType.Integer("amount");

        addSyntax((sender, context) -> {
            int amount = context.get("amount");
            Player player = (Player) sender;
            Instance instance = player.getInstance();
            for (int i = 0; i < amount; i++) {
                Enemy enemy = factory.createEnemy("zombie");
                enemy.setInstance(instance, player.getPosition());
            }
            player.sendMessage(amount + "zombies has successfully created");
        }, numberArgument);

    }

}
