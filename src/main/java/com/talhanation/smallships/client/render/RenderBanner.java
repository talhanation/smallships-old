package com.talhanation.smallships.client.render;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Pair;
import com.talhanation.smallships.entities.AbstractBannerUser;
import com.talhanation.smallships.init.ModEntityTypes;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.BannerTileEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BannerItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.util.math.vector.Vector3f;

import java.util.List;

public class RenderBanner {
    private static final BannerTileEntity BANNER_TE = new BannerTileEntity();

    public static void renderBanner(AbstractBannerUser abstractBannerUser, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, ItemStack banner, int packedLight, ModelRenderer modelRenderer) {

        if (!banner.isEmpty() && abstractBannerUser.getHasBanner()) {
            matrixStackIn.pushPose();
            EntityType<?> entityType = abstractBannerUser.getType();

            //banner pos for ships!
            if (entityType == ModEntityTypes.COG_ENTITY.get()) {
                matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90.0F));
                matrixStackIn.translate(-3.0D, -0.5D, 0.0D);
            } else {
                matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90.0F));
                matrixStackIn.translate(0.7D, 2.35D, 0.05D);
                if (entityType == ModEntityTypes.BRIGG_ENTITY.get())
                    matrixStackIn.translate(0.0D, 1.1D, 0.0D);
            }
            matrixStackIn.scale(0.6F, 0.6F, 0.6F);

            BannerItem item = (BannerItem)banner.getItem();
            BANNER_TE.fromItem(banner, item.getColor());

            //banner waveing here

            /*
            float f2 = partialTicks + bannerUser.ticksExisted;
            float r = 0.05F * MathHelper.cos(f2 / 5.0F) * 180.0F;
            r += banner.prevRotation - MathUtil.lerpAngle(partialTicks, bannerUser.prevRotationYaw, bannerUser.rotationYaw);
            r = (float) (r + MathUtil.lerpAngle(partialTicks, MathUtil.wrapSubtractDegrees(banner.rotation, banner.prevRotation), 0.0f));

            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(r));
            */

            // getting banner pattern here?

            List<Pair<BannerPattern, DyeColor>> list = BANNER_TE.getPatterns();
            BannerTileEntityRenderer.renderPatterns(matrixStackIn, bufferIn, packedLight, OverlayTexture.NO_OVERLAY, modelRenderer, ModelBakery.BANNER_BASE, true, list);
            matrixStackIn.popPose();
        }
    }
}