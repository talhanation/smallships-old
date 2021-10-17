package com.talhanation.smallships.client.render.obj;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.talhanation.smallships.config.SmallShipsConfig;
import com.talhanation.smallships.entities.AbstractBannerUser;
import de.maxhenkel.corelib.client.obj.OBJEntityRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public abstract class AbstractShipModel<T extends AbstractBannerUser> extends OBJEntityRenderer<T> {

    public AbstractShipModel(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(T ship, float yRot, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light) {
        matrixStack.pushPose();
        if (SmallShipsConfig.MakeWaveAnimation.get()) {
            float waveAngle = ship.getWaveAngle(partialTicks);
            if (!MathHelper.equal(waveAngle, 0F)) {
                matrixStack.mulPose(Vector3f.ZP.rotationDegrees(waveAngle));
            }
        }



        ship.renderBanner(matrixStack,buffer,light,partialTicks);

        matrixStack.popPose();
        super.render(ship, yRot, partialTicks, matrixStack, buffer, light);
        /*


        if (ship.hasCustomName()) {
            String name = trimName(ship.getCustomName().getString(), 0.02F, 1F);
            drawName(ship, name, matrixStack, buffer, partialTicks, yRot, light, true);
            drawName(ship, name, matrixStack, buffer, partialTicks, yRot, light, false);
        }

         */
    }

    protected String trimName(String name, float textScale, float maxLength) {
        while (getFont().width(name) * textScale > maxLength) {
            name = name.substring(0, name.length() - 1);
        }
        return name;
    }

    protected void drawName(T ship, String txt, MatrixStack matrixStack, IRenderTypeBuffer buffer, float partialTicks, float yRot, int light, boolean left) {
        matrixStack.pushPose();
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(-yRot));
        float xRot = ship.xRotO + (ship.xRot - ship.xRotO) * partialTicks;
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(xRot));
        RenderSystem.color4f(1F, 1F, 1F, 1F);
        matrixStack.scale(1F, -1F, 1F);

        translateName(ship, matrixStack, left);

        int textWidth = getFont().width(txt);
        float textScale = 0.02F;

        matrixStack.translate(-(textScale * textWidth) / 2.0F, 0F, 0F);

        matrixStack.scale(textScale, textScale, textScale);

        getFont().drawInBatch(txt, 0F, 0F, 0xFFFFFF, false, matrixStack.last().pose(), buffer, false, 0, light);

        matrixStack.popPose();
    }

    protected abstract void translateName(T ship, MatrixStack matrixStack, boolean left);

    //public static String getSailColor(T ship, MatrixStack matrixStack);


}