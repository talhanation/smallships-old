package com.talhanation.smallships.client.render;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.talhanation.smallships.Main;
import com.talhanation.smallships.client.model.ModelBrigg;
import com.talhanation.smallships.config.SmallShipsConfig;
import com.talhanation.smallships.entities.BriggEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.client.model.b3d.B3DModel;

public class RenderEntityBrigg extends EntityRenderer<BriggEntity>{
    private static final ResourceLocation[] BRIGG_TEXTURES = new ResourceLocation[]{
            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/oak_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/spruce_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/birch_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/jungle_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/acacia_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/dark_oak_cog.png"),

            //BOP//
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_cherry_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_dead_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_fir_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_hellbark_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_jacaranda_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_magic_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_mahogany_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_palm_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_redwood_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_umbran_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_willow_cog.png"),

            //LOTR//
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_apple_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_aspen_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_beech_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_cedar_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_charred_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_cherry_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_cypress_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_fir_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_green_oak_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_holly_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_lairelosse_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_larch_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_lebethron_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_mallorn_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_maple_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_mirk_oak_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_pear_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_pine_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_rotten_cog.png"),

            //ENVI//

            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/envi/cog/envi_cherry_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/envi/cog/envi_wisteria_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/envi/cog/envi_willow_cog.png")

    };

    private final ModelBrigg model = new ModelBrigg<>();

    public RenderEntityBrigg(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
        this.shadowRadius = 2.0F;
    }

    @Override
    public void render(BriggEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.0D, 0.1D, 0.0D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
        float f = entityIn.getTimeSinceHit() - partialTicks;
        float f1 = entityIn.getDamageTaken() - partialTicks;
        if (f1 < 0.0F)
            f1 = 0.0F;
        if (f > 0.0F)
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * entityIn.getForwardDirection()));

        if (SmallShipsConfig.MakeWaveAnimation.get()) {
            float waveAngle = entityIn.getWaveAngle(partialTicks);
            if (!Mth.equal(waveAngle, 0F)) {
                matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(waveAngle));
            }
        }
        float f2 = entityIn.getRockingAngle(partialTicks);
        if (!Mth.equal(f2, 0.0F))
            matrixStackIn.mulPose(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), entityIn.getRockingAngle(partialTicks), true));
        matrixStackIn.scale(-1.3F, -1.3F, 1.3F);
        matrixStackIn.translate(0.0D, -1.4D,0.0D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(0F));
        this.model.setupAnim(entityIn, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(this.model.renderType(getTextureLocation(entityIn)));
        this.model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        entityIn.renderBanner(matrixStackIn,bufferIn,packedLightIn,partialTicks);

        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(BriggEntity entity) {
        return BRIGG_TEXTURES[entity.getBoatType().ordinal()];
    }

}
