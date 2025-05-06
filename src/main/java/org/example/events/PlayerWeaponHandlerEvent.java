package org.example.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerChangeHeldSlotEvent;
import net.minestom.server.event.player.PlayerHandAnimationEvent;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.tag.Tag;
import org.example.player.CustomPlayer;
import org.example.weapons.RangedWeapon;
import org.example.weapons.Weapon;
import org.example.weapons.WeaponRegistry;

public class PlayerWeaponHandlerEvent {

    public PlayerWeaponHandlerEvent() {
        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
        handler.addListener(PlayerHandAnimationEvent.class, event -> {
            Player player = event.getPlayer();
            ItemStack itemStack = player.getItemInMainHand();
            Tag<String> weaponIdTag = Tag.String("weapon_id");
            if (itemStack.hasTag(weaponIdTag)) {
                Weapon weapon = WeaponRegistry.getWeaponById(itemStack.getTag(weaponIdTag));
                if (weapon instanceof RangedWeapon rangedWeapon) {
                    rangedWeapon.reload(player);
                }
            }
        });

        handler.addListener(PlayerChangeHeldSlotEvent.class, event -> {
            CustomPlayer player = (CustomPlayer) event.getPlayer();
            ItemStack item = event.getItemInNewSlot();
            if (item.hasTag(Tag.String("weapon_id"))) {
                String weaponId = item.getTag(Tag.String("weapon_id"));
                Weapon weapon = WeaponRegistry.getWeaponById(weaponId);
                if (weapon instanceof RangedWeapon) {
                    player.setExp(1);
                    player.setLevel(player.getAmmo(weaponId));
                }
            } else {
                player.setExp(0);
                player.setLevel(0);
            }
        });

        handler.addListener(PlayerUseItemEvent.class, event -> {
            Player player = event.getPlayer();
            ItemStack itemStack = event.getItemStack();
            if (itemStack.hasTag(Tag.String("weapon_id"))) {
                Weapon weapon = WeaponRegistry.getWeaponById(itemStack.getTag(Tag.String("weapon_id")));
                weapon.onUse(player, event);
            }
        });


    }

}
