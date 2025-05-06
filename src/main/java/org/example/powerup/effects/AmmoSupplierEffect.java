package org.example.powerup.effects;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.network.ConnectionManager;
import org.example.player.CustomPlayer;
import org.example.weapons.RangedWeapon;
import org.example.weapons.Weapon;
import org.example.weapons.WeaponRegistry;

import java.util.Collection;

public class AmmoSupplierEffect implements IPowerUpEffect {

    public void apply() {
        ConnectionManager connectionManager = MinecraftServer.getConnectionManager();
        Collection<Player> players = connectionManager.getOnlinePlayers();
        for (Player player : players) {
            CustomPlayer customPlayer = (CustomPlayer) player;
            for (String weaponID : WeaponRegistry.getWEAPONS().keySet()) {
                Weapon weapon = WeaponRegistry.getWeaponById(weaponID);
                if (weapon instanceof RangedWeapon rangedWeapon) {
                    int maxAmmoSize = rangedWeapon.getMaxAmmoSize();
                    int dif = maxAmmoSize - customPlayer.getAmmo(weaponID);
                    customPlayer.addAmmo(weaponID, dif);
                }
            }
            Title title = Title.title(Component.text("Ammo refresh!"), Component.empty());
            player.showTitle(title);
        }
    }

}
