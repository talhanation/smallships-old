package com.talhanation.smallships.client.events;

import com.talhanation.smallships.network.MessageDismount;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;

public class PlayerEvents {

    @SubscribeEvent
    public void onInteractwithPassenger(PlayerInteractEvent.EntityInteract event) {
        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity playerEntity =  minecraft.player;
        UUID passenger_uuid = event.getTarget().getUUID();
        BlockPos passenger_pos = event.getPos();
        //passenger.stopRiding();
        new MessageDismount(playerEntity, passenger_uuid, passenger_pos);




    /*
    @SubscribeEvent
    public void onInteractwithShip(PlayerInteractEvent.EntityInteract event) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientPlayerEntity clientPlayerEntity = minecraft.player;
    }
    */
    }
}
