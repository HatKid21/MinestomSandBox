package org.example.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerSpawnEvent;
import org.example.Server;
import org.example.player.CustomPlayer;
import org.example.weapons.RangedWeapon;
import org.example.weapons.WeaponFactory;

public class GetPlayerSpawnItemsEvent {

    private final WeaponFactory weaponFactory;

    public GetPlayerSpawnItemsEvent() {
        this.weaponFactory = Server.getWeaponFactory();
        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
        handler.addListener(PlayerSpawnEvent.class, event -> {
            CustomPlayer player = (CustomPlayer) event.getPlayer();
            RangedWeapon pistol = (RangedWeapon) weaponFactory.getWeapon("pistol");
            RangedWeapon sniper = (RangedWeapon) weaponFactory.getWeapon("sniper");
            RangedWeapon shotgun = (RangedWeapon) weaponFactory.getWeapon("shotgun");
            player.getAmmoModule().add(pistol.getWeaponID(), pistol.getMaxAmmoSize());
            player.getAmmoModule().add(sniper.getWeaponID(), sniper.getMaxAmmoSize());
            player.getAmmoModule().add(shotgun.getWeaponID(), shotgun.getMaxAmmoSize());
            player.getInventory().addItemStack(weaponFactory.getWeaponItem(pistol.getWeaponID()));
            player.getInventory().addItemStack(weaponFactory.getWeaponItem(sniper.getWeaponID()));
            player.getInventory().addItemStack(weaponFactory.getWeaponItem(shotgun.getWeaponID()));
        });
    }

}
