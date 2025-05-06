package org.example.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerSpawnEvent;
import org.example.player.CustomPlayer;
import org.example.weapons.RangedWeapon;
import org.example.weapons.WeaponRegistry;

public class GetPlayerSpawnItemsEvent {

    public GetPlayerSpawnItemsEvent() {
        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
        handler.addListener(PlayerSpawnEvent.class, event -> {
            CustomPlayer player = (CustomPlayer) event.getPlayer();
            RangedWeapon pistol = (RangedWeapon) WeaponRegistry.getWeaponById("pistol");
            RangedWeapon sniper = (RangedWeapon) WeaponRegistry.getWeaponById("sniper");
            RangedWeapon shotgun = (RangedWeapon) WeaponRegistry.getWeaponById("shotgun");
            player.addAmmo(pistol.getWeaponID(), pistol.getMaxAmmoSize());
            player.addAmmo(sniper.getWeaponID(), sniper.getMaxAmmoSize());
            player.addAmmo(shotgun.getWeaponID(), shotgun.getMaxAmmoSize());
            player.getInventory().addItemStack(WeaponRegistry.getWeaponItem(pistol.getWeaponID()));
            player.getInventory().addItemStack(WeaponRegistry.getWeaponItem(sniper.getWeaponID()));
            player.getInventory().addItemStack(WeaponRegistry.getWeaponItem(shotgun.getWeaponID()));
        });
    }

}
