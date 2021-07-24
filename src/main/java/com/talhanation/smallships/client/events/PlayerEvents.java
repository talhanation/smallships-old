package com.talhanation.smallships.client.events;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.entities.TNBoatEntity;
import com.talhanation.smallships.network.MessageDismount;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerEvents {

    @SubscribeEvent
    public void onInteractwithPassenger(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget().getVehicle() instanceof TNBoatEntity)) {
            return;
        }

        Entity passenger = event.getTarget();
        PlayerEntity player = event.getPlayer();

        if (!player.isShiftKeyDown()) {
            return;
        }
        Main.SIMPLE_CHANNEL.sendToServer(new MessageDismount(passenger.getUUID()));
        //dismountPassenger(passenger, player);
        event.setCancellationResult(ActionResultType.SUCCESS);
        event.setCanceled(true);

    }

    public static void dismountPassenger(Entity passenger, PlayerEntity player) {
        if (!(passenger instanceof PlayerEntity)){
            passenger.stopRiding();
        }
    }

    public static void MountPassenger(Entity passenger, TNBoatEntity ship, PlayerEntity player) {
        if (!(passenger instanceof PlayerEntity)){
            passenger.startRiding(ship);
        }
    }
}
