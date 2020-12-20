package com.talhanation.smallships.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.talhanation.smallships.Main;
import com.talhanation.smallships.client.model.ModelSailBoat;
import com.talhanation.smallships.entities.SailBoatEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class RenderEntitySailBoat extends EntityRenderer<SailBoatEntity>{
    protected static final ResourceLocation[] SAILBOAT_TEXTURES = new ResourceLocation[]{
            new ResourceLocation(Main.MOD_ID,"textures/entity/oak_sailboat.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/spruce_sailboat.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/brich_sailboat.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/jungle_sailboat.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/acacia_sailboat.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/dark_oak_sailboat.png")};
    public RenderEntitySailBoat(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize = 1.5F;
    }
    private final ModelSailBoat model = new ModelSailBoat<>();

    public void render(SailBoatEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, 0.375D, 0.0D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
        float f = entityIn.getTimeSinceHit() - partialTicks;
        float f1 = entityIn.getDamageTaken() - partialTicks;
        if (f1 < 0.0F)
            f1 = 0.0F;
        if (f > 0.0F)
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(MathHelper.sin(f) * f * f1 / 10.0F * entityIn.getForwardDirection()));
        float f2 = entityIn.getRockingAngle(partialTicks);
        if (!MathHelper.epsilonEquals(f2, 0.0F))
            matrixStackIn.rotate(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), entityIn.getRockingAngle(partialTicks), true));
        matrixStackIn.scale(-1.5F, -1.5F, 1.5F);
        matrixStackIn.translate(0.5D, -1.0D,-1.0D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(0F));
        this.model.setRotationAngles(entityIn, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.model.getRenderType(getEntityTexture(entityIn)));
        this.model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public ResourceLocation getEntityTexture(SailBoatEntity entity) {
        return SAILBOAT_TEXTURES[entity.getBoatType().ordinal()];
    }

}
