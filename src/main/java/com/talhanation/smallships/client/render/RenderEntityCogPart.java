package com.talhanation.smallships.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.talhanation.smallships.Main;
import com.talhanation.smallships.client.model.ModelCog;
import com.talhanation.smallships.entities.CogEntityParts;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;

import net.minecraft.util.ResourceLocation;

public class RenderEntityCogPart extends EntityRenderer<CogEntityParts>{
    private static final ResourceLocation[] COG_TEXTURES = new ResourceLocation[]{
            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/oak_cog.png"),

    };

    private final ModelCog model = new ModelCog<>();

    public RenderEntityCogPart(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowRadius = 1.0F;
    }

    @Override
    public void render(CogEntityParts entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(CogEntityParts entity) {
        return COG_TEXTURES[0];
    }

}
