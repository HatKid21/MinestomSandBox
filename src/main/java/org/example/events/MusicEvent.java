package org.example.events;

import net.kyori.adventure.sound.Sound;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerStartSneakingEvent;
import net.minestom.server.network.packet.server.play.SoundEffectPacket;
import net.minestom.server.sound.SoundEvent;

public class MusicEvent {

    public MusicEvent(){
        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
        handler.addListener(PlayerStartSneakingEvent.class, event ->{
            SoundEffectPacket soundEffectPacket = new SoundEffectPacket(SoundEvent.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, Sound.Source.MASTER, event.getPlayer().getPosition(),3,2f ,5);
            event.getPlayer().sendPacket(soundEffectPacket);

        });
    }

}
