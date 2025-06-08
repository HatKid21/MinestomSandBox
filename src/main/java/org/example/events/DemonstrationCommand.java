package org.example.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.timer.SchedulerManager;
import net.minestom.server.timer.TaskSchedule;
import org.example.planet.Planet;

public class DemonstrationCommand extends Command {

    private static final SchedulerManager scheduler = MinecraftServer.getSchedulerManager();

    public DemonstrationCommand(){
        super("test");

        setDefaultExecutor((sender,executor) ->{
            Player player = (Player) sender;
            player.setNoGravity(true);
            player.setVelocity(Vec.ZERO);
            Pos viewerPosition = player.getPosition().add(new Pos(0,100,0)).withYaw(0).withPitch(0);
            player.teleport(viewerPosition);
            Planet sun = new Planet(Block.GLOWSTONE,3,3330, false);
            Planet earth = new Planet(Block.GRASS_BLOCK,1,1,true);
            Pos sunPosition = viewerPosition.add(-sun.getSize()/2,-50,-sun.getSize()/2);
            Pos earthPosition = viewerPosition.add(10- earth.getSize()/2,-50,-earth.getSize()/2);
            scheduler.scheduleTask(spawn(sun,player.getInstance(),sunPosition,Vec.ZERO), TaskSchedule.seconds(3),TaskSchedule.stop());
            scheduler.scheduleTask(spawn(earth,player.getInstance(),earthPosition,new Vec(-15,0,15)),TaskSchedule.seconds(6),TaskSchedule.stop());
        });

    }

    private Runnable spawn(Planet planet,Instance instance, Pos pos, Vec velocity){
        return () ->{
            planet.spawn(instance,pos);
            planet.setVelocity(velocity);
        };
    }

}
