package com.talhanation.smallships.client.events;

import com.talhanation.smallships.config.SmallShipsConfig;
import com.talhanation.smallships.entities.AbstractSailBoat;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class RenderEvents {
    private Minecraft mc;

    public RenderEvents() {
        mc = Minecraft.getInstance();
    }

    public void onRender(EntityViewRenderEvent.CameraSetup evt) {
        if (getShip() != null && !mc.options.getCameraType().isFirstPerson()) {
            evt.getInfo().move(-evt.getInfo().getMaxZoom(SmallShipsConfig.ShipZoom.get()) + 4D, 0D, 0D);
        }
    }

    @SubscribeEvent
    public void onRender(InputEvent.MouseScrollEvent evt) {
        if (getShip() != null && !mc.options.getCameraType().isFirstPerson()) {
            SmallShipsConfig.ShipZoom.set(Math.max(1D, Math.min(20D, SmallShipsConfig.ShipZoom.get() - evt.getScrollDelta())));
            SmallShipsConfig.ShipZoom.save();
            evt.setCanceled(true);
        }
    }

    private AbstractSailBoat getShip() {
        Entity e = mc.player.getVehicle();
        if (e instanceof AbstractSailBoat) {
            return (AbstractSailBoat) e;
        }
        return null;
    }

}
