package com.talhanation.smallships.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.talhanation.smallships.Main;
import com.talhanation.smallships.client.model.ModelGalley;
import com.talhanation.smallships.entities.GalleyEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class RenderEntityGalley extends EntityRenderer<GalleyEntity>{
    private static final ResourceLocation[] GALLEY_TEXTURES = new ResourceLocation[]{
            new ResourceLocation(Main.MOD_ID,"textures/entity/galley/oak_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/galley/spruce_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/galley/birch_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/galley/jungle_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/galley/acacia_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/galley/dark_oak_galley.png")};

    private final ModelGalley model = new ModelGalley<>();

    public RenderEntityGalley(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize = 1.6F;
    }


    public void render(GalleyEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, 0.1D, 0.0D);
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
        matrixStackIn.scale(-1.2F, -1.2F, 1.2F);
        matrixStackIn.translate(0.0D, -1.8D,0.0D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(0F));
        this.model.setRotationAngles(entityIn, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.model.getRenderType(getEntityTexture(entityIn)));
        this.model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public ResourceLocation getEntityTexture(GalleyEntity entity) {
        return GALLEY_TEXTURES[entity.getBoatType().ordinal()];
    }

}
