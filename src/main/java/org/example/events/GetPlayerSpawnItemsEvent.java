package org.example.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerSpawnEvent;
import org.example.player.CustomPlayer;
import org.example.weapons.WeaponRegistry;

public class GetPlayerSpawnItemsEvent {

    public GetPlayerSpawnItemsEvent(){
        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
        handler.addListener(PlayerSpawnEvent.class, event ->{
            CustomPlayer player = (CustomPlayer)event.getPlayer();
            player.addAmmo("pistol",100);
            player.addAmmo("sniper",100);
            player.addAmmo("shotgun",100);
            player.getInventory().addItemStack(WeaponRegistry.getWeaponItem("pistol"));
            player.getInventory().addItemStack(WeaponRegistry.getWeaponItem("sniper"));
            player.getInventory().addItemStack(WeaponRegistry.getWeaponItem("shotgun"));
        });
    }

}
