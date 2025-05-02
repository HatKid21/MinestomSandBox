package org.example.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.entity.EntityAttackEvent;
import net.minestom.server.event.player.PlayerHandAnimationEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.particle.Particle;
import net.minestom.server.tag.Tag;
import org.example.weapons.AmmoManager;
import org.example.weapons.RangedWeapon;
import org.example.weapons.Weapon;
import org.example.weapons.WeaponRegistry;

public class ReloadEvent {

    public ReloadEvent(){
        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
        handler.addListener(PlayerHandAnimationEvent.class, event ->{
            Player player = event.getPlayer();
            ItemStack itemStack = player.getItemInMainHand();
            Tag<String> weaponIdTag = Tag.String("weapon_id");
            if (itemStack.hasTag(weaponIdTag)){
                Weapon weapon = WeaponRegistry.getWeaponById(itemStack.getTag(weaponIdTag));
                if (weapon instanceof RangedWeapon rangedWeapon){
                    rangedWeapon.reload(player);
                }
            }
        });
    }


}
