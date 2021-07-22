package com.talhanation.smallships.client.events;

import com.talhanation.smallships.entities.TNBoatEntity;
import com.talhanation.smallships.network.MessageDismount;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;

public class PlayerEvents {

    @SubscribeEvent
    public void onInteractwithPassenger(PlayerInteractEvent.EntityInteract event) {
        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity clientPlayerEntity = (PlayerEntity) minecraft.player.getEntity();
        int passenger = event.getTarget().getId();

            //passenger.stopRiding();
            new MessageDismount(clientPlayerEntity, passenger);




    /*
    @SubscribeEvent
    public void onInteractwithShip(PlayerInteractEvent.EntityInteract event) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientPlayerEntity clientPlayerEntity = minecraft.player;
    }
    */
    }
}
