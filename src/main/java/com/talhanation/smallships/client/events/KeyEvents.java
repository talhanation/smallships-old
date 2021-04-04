package com.talhanation.smallships.client.events;

import com.talhanation.smallships.entities.TNBoatEntity;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KeyEvents {

    private final Minecraft mc;

    public KeyEvents() {
        mc = Minecraft.getInstance();
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (mc.player == null || !(mc.player.getRidingEntity() instanceof TNBoatEntity)) {
            return;
        }

        if (!mc.gameSettings.keyBindSprint.isPressed()) {
            return;
        }

        TNBoatEntity boat = (TNBoatEntity) mc.player.getRidingEntity();
        boat.onSprintPressed();
    }

}
