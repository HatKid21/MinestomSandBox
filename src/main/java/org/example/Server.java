package org.example;

import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.*;
import net.minestom.server.entity.metadata.EntityMeta;
import net.minestom.server.entity.metadata.display.ItemDisplayMeta;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;

import net.minestom.server.event.server.ServerTickMonitorEvent;
import net.minestom.server.instance.*;
import net.minestom.server.instance.anvil.AnvilLoader;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.monitoring.TickMonitor;
import org.example.commands.AddAmmoCommand;
import org.example.commands.EntitySpawnCommand;
import org.example.commands.StatisticCommand;
import org.example.commands.ZombieCreatureSpawnCommand;
import org.example.events.*;
import org.example.player.CustomPlayer;
import org.example.utils.GeneratorManager;
import org.example.utils.TickTabDisplay;
import org.example.weapons.WeaponRegistry;

import java.nio.file.Path;
import java.nio.file.Paths;


public class Server {

    public static InstanceContainer instance;
    public static TickTabDisplay tabDisplay = new TickTabDisplay();

    public static void main(String[] args) {
        MinecraftServer server = MinecraftServer.init();
        MinecraftServer.getConnectionManager().setPlayerProvider(CustomPlayer::new);

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();

        Path woldPath = Paths.get("instances/world");

        IChunkLoader chunkLoader = new AnvilLoader(woldPath);

        instance = instanceManager.createInstanceContainer();
        instance.setChunkLoader(chunkLoader);

//        instance = MinecraftServer.getInstanceManager().createInstanceContainer();
//        instance.setGenerator(unit -> unit.modifier().fillHeight(0,40, Block.GRASS_BLOCK));

        GlobalEventHandler eventHandler = MinecraftServer.getGlobalEventHandler();

        eventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            Player player = event.getPlayer();
            event.setSpawningInstance(instance);
            player.setRespawnPoint(new Pos(0, 56, 1));
        });

        eventHandler.addListener(ServerTickMonitorEvent.class, event -> {
            TickMonitor tickMonitor = event.getTickMonitor();
            double TPS = 1000 / tickMonitor.getTickTime();
            TPS = Math.min(TPS, 20);
            String formattedTps = String.format("%.2f", TPS);
            Component footer = Component.text("TPS: " + formattedTps);
            tabDisplay.setFooter(footer);
        });

        eventHandler.addListener(PlayerSpawnEvent.class, event -> {
            Player player = event.getPlayer();
            player.setGameMode(GameMode.SURVIVAL);
            Entity entity = new Entity(EntityType.ITEM_DISPLAY);
            Entity entity1 = new Entity(EntityType.ARMOR_STAND);
            EntityMeta entityMeta = entity1.getEntityMeta();
            entityMeta.setInvisible(true);
            entity1.setInstance(instance, player.getPosition());
            ItemDisplayMeta meta = (ItemDisplayMeta) entity.getEntityMeta();
            meta.setItemStack(ItemStack.of(Material.GOLD_INGOT));
            entity.setInstance(instance, player.getPosition());
            entity.addPassenger(entity1);
            CustomPlayer customPlayer = (CustomPlayer) player;
            customPlayer.getScoreboard().addViewer(player);
        });

        registerEvents();
        registerCommands();
        WeaponRegistry.init();

        instance.setChunkSupplier(LightingChunk::new);

        server.start("0.0.0.0", 25565);
        GeneratorManager.init(instance);
        tabDisplay.start();
    }

    public static void registerCommands() {
        CommandManager commandManager = MinecraftServer.getCommandManager();
        commandManager.register(new EntitySpawnCommand());
        commandManager.register(new ZombieCreatureSpawnCommand());
        commandManager.register(new StatisticCommand());
        commandManager.register(new AddAmmoCommand());
    }

    public static void registerEvents() {
        new PickUpEvent();
        new CarryingEvent();
        new PlayerWeaponHandlerEvent();
        new PunchEvent();
        new GetPlayerSpawnItemsEvent();
        new MusicEvent();
    }

}