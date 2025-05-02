package org.example.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;

public class EntitySpawnCommand extends Command {
    public EntitySpawnCommand() {
        super("spawn");

        setDefaultExecutor(((commandSender, commandContext) -> {
            commandSender.sendMessage("Usage : /spawn <amount>");
        }));

        var entityArgument = ArgumentType.EntityType("entity");
        var amountArgument = ArgumentType.Integer("amount");

        entityArgument.setCallback(((sender, e) -> {
            sender.sendMessage("There is no such entity in minecraft ");
        }));

        addSyntax((sender, context) -> {
            EntityType entityType = context.get("entity");
            Player player = (Player) sender;
            Instance instance = player.getInstance();
            Entity entity = new Entity(entityType);
            entity.setInstance(instance, player.getPosition());
        }, entityArgument);


        addSyntax((sender, context) -> {
            EntityType entityType = context.get("entity");
            int amount = context.get("amount");
            Player player = (Player) sender;
            Instance instance = player.getInstance();
            for (int i = 0; i < amount; i++) {
                Entity entity = new Entity(entityType);
                entity.setInstance(instance, player.getPosition());
            }
        }, entityArgument, amountArgument);

    }
}
