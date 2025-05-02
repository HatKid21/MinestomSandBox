package org.example.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerChangeHeldSlotEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.tag.Tag;
import org.example.weapons.AmmoManager;
import org.example.weapons.RangedWeapon;
import org.example.weapons.Weapon;
import org.example.weapons.WeaponRegistry;

public class WeaponTookInMainHandEvent {

    public WeaponTookInMainHandEvent(){
        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
        handler.addListener(PlayerChangeHeldSlotEvent.class, event ->{
            Player player = event.getPlayer();
            ItemStack item = event.getItemInNewSlot();
            if (item.hasTag(Tag.String("weapon_id"))){
                String weaponId = item.getTag(Tag.String("weapon_id"));
                Weapon weapon = WeaponRegistry.getWeaponById(weaponId);
                if (weapon instanceof RangedWeapon){
                    player.setExp(1);
                    player.setLevel(AmmoManager.getAmmo(player,weaponId));
                }
            } else{
                player.setExp(0);
                player.setLevel(0);
            }
        });
    }

}
