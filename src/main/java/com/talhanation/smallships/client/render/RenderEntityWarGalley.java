package com.talhanation.smallships.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.talhanation.smallships.Main;
import com.talhanation.smallships.client.model.ModelWarGalley;
import com.talhanation.smallships.entities.WarGalleyEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class RenderEntityWarGalley extends EntityRenderer<WarGalleyEntity>{
    private static final ResourceLocation[] WAR_GALLEY_TEXTURES = new ResourceLocation[]{
            new ResourceLocation(Main.MOD_ID,"textures/entity/galley/oak_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/galley/spruce_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/galley/birch_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/galley/jungle_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/galley/acacia_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/galley/dark_oak_galley.png"),

            //BOP//
            new ResourceLocation(Main.MOD_ID, "textures/entity/mod/bop/galley/bop_cherry_galley.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/mod/bop/galley/bop_dead_galley.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/mod/bop/galley/bop_fir_galley.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/mod/bop/galley/bop_hellbark_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/galley/bop_jacaranda_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/galley/bop_magic_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/galley/bop_mahogany_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/galley/bop_palm_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/galley/bop_redwood_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/galley/bop_umbran_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/galley/bop_willow_galley.png"),

            //LOTR//
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/galley/lotr_apple_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/galley/lotr_aspen_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/galley/lotr_beech_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/galley/lotr_cedar_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/galley/lotr_charred_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/galley/lotr_cherry_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/galley/lotr_cypress_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/galley/lotr_fir_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/galley/lotr_green_oak_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/galley/lotr_holly_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/galley/lotr_lairelosse_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/galley/lotr_larch_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/galley/lotr_lebethron_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/galley/lotr_mallorn_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/galley/lotr_maple_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/galley/lotr_mirk_oak_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/galley/lotr_pear_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/galley/lotr_pine_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/galley/lotr_rotten_galley.png"),

            //ENVI//
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/envi/galley/envi_cherry_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/envi/gelley/envi_wisteria_galley.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/envi/gelley/envi_willow_galley.png")

    };

    private final ModelWarGalley model = new ModelWarGalley<>();

    public RenderEntityWarGalley(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize = 1.6F;
    }


    public void render(WarGalleyEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
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

    public ResourceLocation getEntityTexture(WarGalleyEntity entity) {
        return WAR_GALLEY_TEXTURES[entity.getBoatType().ordinal()];
    }

}
