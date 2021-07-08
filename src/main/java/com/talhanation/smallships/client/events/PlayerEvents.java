package com.talhanation.smallships.client.events;

import com.talhanation.smallships.entities.TNBoatEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerEvents {

    @SubscribeEvent
    public void onInteractwithPassenger(PlayerInteractEvent.EntityInteract event) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientPlayerEntity clientPlayerEntity = minecraft.player;
        Entity passenger = event.getTarget();
        Entity vehicle = passenger.getVehicle();

        if (clientPlayerEntity == null)
            return;


        if ((vehicle instanceof TNBoatEntity)) {
            if (clientPlayerEntity.isShiftKeyDown())
                passenger.stopRiding();
            }

    }

}
