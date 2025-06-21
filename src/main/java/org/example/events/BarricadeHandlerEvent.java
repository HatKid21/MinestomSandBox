package org.example.events;


import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerStartSneakingEvent;
import net.minestom.server.event.player.PlayerStopSneakingEvent;
import net.minestom.server.event.player.PlayerSwapItemEvent;
import org.example.Server;
import org.example.player.BarricadeModule;
import org.example.player.CustomPlayer;
import org.example.utils.Barricade;

public class BarricadeHandlerEvent {

    public BarricadeHandlerEvent(){
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        globalEventHandler.addListener(PlayerStartSneakingEvent.class, event -> {
            Player player = event.getPlayer();
            if (player instanceof CustomPlayer customPlayer){
                BarricadeModule barricadeModule = customPlayer.getBarricadeModule();
                barricadeModule.startRepairing();
            }
        });

        globalEventHandler.addListener(PlayerStopSneakingEvent.class, event ->{
            Player player = event.getPlayer();
            if (player instanceof  CustomPlayer customPlayer){
                BarricadeModule barricadeModule = customPlayer.getBarricadeModule();
                barricadeModule.stopRepairing();
            }
        });

        globalEventHandler.addListener(PlayerSwapItemEvent.class, event ->{
            Player player = event.getPlayer();
            Pos playerPos = player.getPosition();
            Barricade barricade = Server.getBarricadeManager().getNearbyBarricade(playerPos);
            if (barricade == null){
                return;
            }
            barricade.hit();
        });
    }

}
