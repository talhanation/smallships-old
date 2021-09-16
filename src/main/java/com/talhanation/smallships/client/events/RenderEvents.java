package com.talhanation.smallships.client.events;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.config.SmallShipsConfig;
import com.talhanation.smallships.entities.AbstractSailBoat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class RenderEvents {

    private static final ResourceLocation PLANE_INFO_TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/gui/plane_info.png");
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
    /*
    //THIRDPERSON SCREEN
    public void renderPlaneInfo(MatrixStack matrixStack, AbstractShipDamage plane) {
        matrixStack.pushPose();

        mc.getTextureManager().bind(PLANE_INFO_TEXTURE);

        int texWidth = 110;
        int texHeight = 90;

        int height = mc.getWindow().getGuiScaledHeight();
        int width = mc.getWindow().getGuiScaledWidth();

        float scale =1; // SmallShipsConfig.CONFIG.ShipInfoScale.get().floatValue();
        matrixStack.scale(scale, scale, 1F);
        matrixStack.translate(-width, -height, 0D);
        matrixStack.translate(((double) width) * (1D / scale), ((double) height * (1D / scale)), 0D);

        int padding = 3;
        int yStart = height - texHeight - padding;
        int xStart = width - texWidth - padding;

        mc.gui.blit(matrixStack, xStart, yStart, 0, 0, texWidth, texHeight);

        FontRenderer font = mc.gui.getFont();

        Function<Integer, Integer> heightFunc = integer -> yStart + 8 + (font.lineHeight + 2) * integer;

        font.draw(matrixStack, new TranslationTextComponent("tooltip.plane.speed", SmallShipsConfig.planeInfoSpeedType.get().getTextComponent(plane.getDeltaMovement().length())).getVisualOrderText(), xStart + 7, heightFunc.apply(0), 0);
        font.draw(matrixStack, new TranslationTextComponent("tooltip.plane.sailstate", String.valueOf(Math.round(plane.getSailState() * 100F))).getVisualOrderText(), xStart + 7, heightFunc.apply(2), 0);
        //font.draw(matrixStack, new TranslationTextComponent("tooltip.plane.waterbiome", String.valueOf(Math.round(plane.getY()))).getVisualOrderText(), xStart + 7, heightFunc.apply(3), 0);
        //font.draw(matrixStack, new TranslationTextComponent("tooltip.plane.fuel", String.valueOf(plane.getFuel())).getVisualOrderText(), xStart + 7, heightFunc.apply(5), 0);
        font.draw(matrixStack, new TranslationTextComponent("tooltip.plane.damage", String.valueOf(MathUtils.round(plane.getShipDamage(), 2))).getVisualOrderText(), xStart + 7, heightFunc.apply(4), 0);

        matrixStack.popPose();
    }
    */
}
