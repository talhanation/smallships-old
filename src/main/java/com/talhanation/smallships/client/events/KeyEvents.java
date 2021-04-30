package com.talhanation.smallships.client.events;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.entities.TNBoatEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KeyEvents {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientPlayerEntity clientPlayerEntity = minecraft.player;
        if (clientPlayerEntity == null)
            return;

        Entity riding = clientPlayerEntity.getRidingEntity();
        if (!(riding instanceof TNBoatEntity)) {
            return;
        }
        TNBoatEntity boat = (TNBoatEntity) riding;
        if (clientPlayerEntity.equals(boat.getDriver())) {
            if (Main.SAIL_KEY.isPressed()) {
                boat.onSprintPressed();
            }
        }

        if (boat.getPassengers().contains(clientPlayerEntity)) {
            if (Main.INV_KEY.isPressed()) {
                boat.onInvPressed(clientPlayerEntity);
            }
        }
    }
}
