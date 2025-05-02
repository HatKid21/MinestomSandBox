package org.example.commands;

import net.kyori.adventure.text.Component;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import org.example.weapons.AmmoManager;

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
            Player player = (Player) sender;
            AmmoManager.addAmmo(player,weaponID,amount);
            player.sendMessage(Component.text(amount + " ammo has added to " + weaponID));
        },weaponIdArgument,amountArgument);


    }

}
