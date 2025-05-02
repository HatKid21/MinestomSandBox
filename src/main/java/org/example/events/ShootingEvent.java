package org.example.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerSwapItemEvent;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.ItemStack;
import net.minestom.server.tag.Tag;
import org.example.utils.Ray;
import org.example.weapons.AmmoManager;
import org.example.weapons.RangedWeapon;
import org.example.weapons.Weapon;
import org.example.weapons.WeaponRegistry;

public class ShootingEvent {

    private static final double step = 0.1d;

    public ShootingEvent(){
        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
        handler.addListener(PlayerUseItemEvent.class, event ->{
            Player player = event.getPlayer();
            ItemStack itemStack = event.getItemStack();
            if (itemStack.hasTag(Tag.String("weapon_id"))){
                Weapon weapon = WeaponRegistry.getWeaponById(itemStack.getTag(Tag.String("weapon_id")));
                weapon.onUse(player,event);
            }
        });
    }

}
