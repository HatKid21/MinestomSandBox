package org.example.powerup;

import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.collision.BoundingBox;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.display.ItemDisplayMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.timer.TaskSchedule;
import org.example.powerup.effects.IPowerUpEffect;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class PowerUp extends Entity {

    private static final Collection<PowerUp> POWER_UPS = new ArrayList<>();

    private final IPowerUpEffect powerUpEffect;

    public PowerUp(Material material, IPowerUpEffect powerUpEffect, Component displayName) {
        super(EntityType.ITEM_DISPLAY);
        setCustomName(displayName);
        setCustomNameVisible(true);
        BoundingBox boundingBox = new BoundingBox(new Vec(-1, -1, -1), new Vec(1, 1, 1));
        setBoundingBox(boundingBox);
        ItemDisplayMeta itemDisplayMeta = (ItemDisplayMeta) getEntityMeta();
        itemDisplayMeta.setItemStack(ItemStack.of(material));
        setNoGravity(true);
        this.powerUpEffect = powerUpEffect;
        POWER_UPS.add(this);
    }

    public void applyEffect() {
        powerUpEffect.apply();
    }

    public static Collection<PowerUp> getPowerUps() {
        return POWER_UPS;
    }

    @Override
    public CompletableFuture<Void> setInstance(@NotNull Instance instance, @NotNull Pos spawnPosition) {

        MinecraftServer.getSchedulerManager().submitTask(() -> {
            Collection<Entity> entities = instance.getNearbyEntities(getPosition(), 2);

            for (Entity entity : entities) {
                if (entity instanceof Player player) {
                    if (boundingBox.intersectEntity(getPosition(), player)) {
                        applyEffect();
                        remove();
                        return TaskSchedule.stop();
                    }
                }
            }

            return TaskSchedule.tick(20);
        });

        return super.setInstance(instance, spawnPosition.withPitch(0).withYaw(0));
    }
}
