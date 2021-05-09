package com.talhanation.smallships.client.events;

import com.talhanation.smallships.config.SmallShipsConfig;
import com.talhanation.smallships.entities.AbstractSailBoat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

@OnlyIn(Dist.CLIENT)
public class RenderEvents {

    private Minecraft mc;
    private AbstractSailBoat lastVehicle;

    public RenderEvents() {
        mc = Minecraft.getInstance();
    }

    @SubscribeEvent
    public void onRender(EntityViewRenderEvent.CameraSetup evt) {
        if (getShip() != null && !mc.options.getCameraType().isFirstPerson()) {
            evt.getInfo().move(-evt.getInfo().getMaxZoom(SmallShipsConfig.ShipZoom.get() - 4D), 0D, 0D);
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

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent evt) {
        if (evt.side.equals(LogicalSide.SERVER)) {
            return;
        }

        if (!evt.player.equals(mc.player)) {
            return;
        }

        AbstractSailBoat vehicle = getShip();

        if (vehicle != null && lastVehicle == null) {
            setThirdPerson(true);
        } else if (vehicle == null && lastVehicle != null) {
            setThirdPerson(false);
        }
        lastVehicle = vehicle;
    }

    private void setThirdPerson(boolean third) {
        if (!SmallShipsConfig.EnterThirdPerson.get()) {
            return;
        }

        if (third) {
            mc.options.setCameraType(PointOfView.THIRD_PERSON_BACK);
        } else {
            mc.options.setCameraType(PointOfView.FIRST_PERSON);
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
