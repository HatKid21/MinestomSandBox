package org.example.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentBoolean;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.number.ArgumentDouble;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.block.Block;
import org.example.planet.Planet;

public class PlanetCreationCommand extends Command {

    public PlanetCreationCommand(){
        super("planet");

        setDefaultExecutor((sender, context) ->{
            Player player = (Player) sender;
            Planet planet = new Planet(Block.DIAMOND_BLOCK,1,1,true);
            Pos pos = player.getPosition().withYaw(0).withPitch(0);
            planet.spawn(player.getInstance(),pos);
            player.sendMessage(String.format("Planet with mass %.2f and size %.2f has spawned",planet.getMass(),planet.getSize()));
        });

        ArgumentDouble massArgument = ArgumentType.Double("mass");
        ArgumentDouble sizeArgument = ArgumentType.Double("size");
        ArgumentBoolean movableArgument = ArgumentType.Boolean("movable");

        addSyntax((sender,context) ->{
            double mass = context.get("mass");
            double size = context.get("size");
            boolean movable = context.get("movable");
            Player player = (Player) sender;
            Planet planet = new Planet(Block.DIAMOND_BLOCK,mass,size,movable);
            Pos pos = player.getPosition().withYaw(0).withPitch(0);
            planet.spawn(player.getInstance(),pos);
            player.sendMessage(String.format("Planet with mass %.2f and size %.2f and movable = %b has spawned",mass,size,movable));
        },massArgument,sizeArgument,movableArgument);

    }

}
