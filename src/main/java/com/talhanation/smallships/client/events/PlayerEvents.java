package com.talhanation.smallships.client.events;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.entities.TNBoatEntity;
import com.talhanation.smallships.network.MessageDismount;e;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerEvents {

    @SubscribeEvent
    public void onInteractwithPassenger(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget().getVehicle() instanceof TNBoatEntity) ) {
            return;
        }

        Entity passenger = event.getTarget();
        Player player = event.getPlayer();

        if (!player.isShiftKeyDown()) {
            return;
        }
        Main.SIMPLE_CHANNEL.sendToServer(new MessageDismount(passenger.getUUID()));
        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);

    }

    public static void dismountPassenger(Entity passenger, Player player) {
        if (!(passenger instanceof Player)){
            passenger.stopRiding();
        }
    }

}
