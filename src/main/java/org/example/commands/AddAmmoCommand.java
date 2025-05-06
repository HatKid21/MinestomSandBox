package org.example.commands;

import net.kyori.adventure.text.Component;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import org.example.player.CustomPlayer;

public class AddAmmoCommand extends Command {

    public AddAmmoCommand(){
        super("ammo");

        setDefaultExecutor((sender, context) ->{
            Player player = (Player) sender;
            player.sendMessage("Usage: /ammo <weapon_id> <amount>");
        });

        var weaponIdArgument = ArgumentType.String("weapon_id");
        var amountArgument = ArgumentType.Integer("amount");

        addSyntax((sender,context) ->{
            String weaponID = context.get(weaponIdArgument);
            int amount = context.get(amountArgument);
            CustomPlayer customPlayer = (CustomPlayer) sender;
            customPlayer.addAmmo(weaponID,amount);
            customPlayer.sendMessage(Component.text(amount + " ammo has added to " + weaponID));
        },weaponIdArgument,amountArgument);


    }

}
