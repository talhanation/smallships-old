package com.talhanation.smallships.items.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.talhanation.smallships.client.render.RenderEntityCog;
import com.talhanation.smallships.client.render.obj.ModelCog;
import com.talhanation.smallships.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IReloadableResourceManager;;

public class ShipItemRenderer extends ItemStackTileEntityRenderer{

    private Minecraft minecraft;
    private ModelCog renderer;

    public ShipItemRenderer(){
        minecraft = Minecraft.getInstance();
    }

    @Override
    public void renderByItem(ItemStack itemStackIn, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (renderer == null) {
            renderer = new ModelCog((EntityRendererManager) minecraft.getResourceManager());
        }
        //renderer.render(ModItems.SHIP_ITEM.getCogEntity(type,  ), 0F, 1F, matrixStackIn, bufferIn, combinedLightIn);
    }
}
