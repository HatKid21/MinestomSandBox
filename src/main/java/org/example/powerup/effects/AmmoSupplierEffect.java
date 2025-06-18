package org.example.powerup.effects;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.network.ConnectionManager;
import org.example.Server;
import org.example.player.CustomPlayer;
import org.example.weapons.RangedWeapon;
import org.example.weapons.Weapon;
import org.example.weapons.WeaponFactory;

import java.util.Collection;

public class AmmoSupplierEffect implements PowerUpEffect {

    public void apply() {
        WeaponFactory weaponFactory = Server.getWeaponFactory();

        ConnectionManager connectionManager = MinecraftServer.getConnectionManager();
        Collection<Player> players = connectionManager.getOnlinePlayers();
        for (Player player : players) {
            CustomPlayer customPlayer = (CustomPlayer) player;
            for (String weaponID : weaponFactory.getWEAPONS().keySet()) {
                Weapon weapon = weaponFactory.getWeapon(weaponID);
                if (weapon instanceof RangedWeapon rangedWeapon) {
                    int maxAmmoSize = rangedWeapon.getMaxAmmoSize();
                    int dif = maxAmmoSize - customPlayer.getAmmoModule().get(weaponID);
                    customPlayer.getAmmoModule().add(weaponID, dif);
                }
            }
            Title title = Title.title(Component.text("Ammo refresh!"), Component.empty());
            player.showTitle(title);
        }
    }

}
