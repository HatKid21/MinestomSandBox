package org.example.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.instance.InstanceTickEvent;
import org.example.planet.Planet;

import java.util.List;

public class PlanetEvent {

    public PlanetEvent(){
        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
        handler.addListener(InstanceTickEvent.class, event ->{
            List<Planet> existedPlanets = Planet.getExistedPlanets();
            for (int i = 0; i < existedPlanets.size(); i++) {
                for (int j = i+1; j < existedPlanets.size(); j++) {
                    Planet planet1 = existedPlanets.get(i);
                    Planet planet2 = existedPlanets.get(j);
                    double distance = planet1.getCenter().distance(planet2.getCenter());
                    double radiusSum = planet1.getSize() / 2 + planet2.getSize() / 2;
                    if (distance <=100 && distance >= radiusSum) {
                        double force = 5 * (planet1.getMass() * planet2.getMass()) / (distance * distance);
                        Vec vec = Vec.fromPoint(planet1.getCenter().sub(planet2.getCenter())).normalize();
                        planet1.addVelocity(vec.mul(force / planet1.getMass() * 0.05).neg());
                        planet2.addVelocity(vec.mul(force / planet2.getMass() * 0.05));
                    }
//                    } else if(distance < radiusSum){
//                        double force = 0.05*(planet1.getMass() * planet2.getMass()) / (distance*distance);
//                        Vec vec = Vec.fromPoint(planet1.getCenter().sub(planet2.getCenter())).normalize();
//                        planet1.addVelocity(vec.mul(force/planet1.getMass()));
//                        planet2.addVelocity(vec.mul(force/planet2.getMass()).neg());
//                    }
                }
            }
        });
    }

}
