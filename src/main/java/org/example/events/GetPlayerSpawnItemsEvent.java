package org.example.events;

import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerSpawnEvent;
import org.example.weapons.AmmoManager;
import org.example.weapons.WeaponRegistry;

public class GetPlayerSpawnItemsEvent {

    public GetPlayerSpawnItemsEvent(){
        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
        handler.addListener(PlayerSpawnEvent.class, event ->{
            Player player = event.getPlayer();
            AmmoManager.onPlayerJoin(player);
            AmmoManager.addAmmo(player,"pistol",100);
            AmmoManager.addAmmo(player,"sniper",100);
            AmmoManager.addAmmo(player,"shotgun",100);
            player.getInventory().addItemStack(WeaponRegistry.getWeaponItem("pistol"));
            player.getInventory().addItemStack(WeaponRegistry.getWeaponItem("sniper"));
            player.getInventory().addItemStack(WeaponRegistry.getWeaponItem("shotgun"));
        });
    }

}
