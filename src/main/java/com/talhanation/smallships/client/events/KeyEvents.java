package com.talhanation.smallships.client.events;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.entities.AbstractInventoryBoat;
import com.talhanation.smallships.entities.AbstractSailBoat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
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
        if (!(riding instanceof AbstractSailBoat)){
            return;
        }
        AbstractSailBoat sailBoat = (AbstractSailBoat) riding;
        if (clientPlayerEntity.equals(sailBoat.getDriver())) {
            if (Main.SAIL_KEY.isPressed()) {
                sailBoat.onSprintPressed();
            }
        }
        Entity riding2 = clientPlayerEntity.getRidingEntity();
        if (!(riding2 instanceof AbstractInventoryBoat)) {
            return;
        }
        AbstractInventoryBoat invBoat = (AbstractInventoryBoat) riding2;
        if (invBoat.getPassengers().contains(clientPlayerEntity)) {
            if (Main.INV_KEY.isPressed()) {
                invBoat.onInvPressed(clientPlayerEntity);
            }
        }
    }
}
