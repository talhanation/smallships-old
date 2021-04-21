package com.talhanation.smallships.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.talhanation.smallships.entities.RowBoatEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelRowBoat - Either Mojang or a mod author (Taken From Memory)
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class ModelRowBoat<T extends RowBoatEntity> extends EntityModel<T> {
    public ModelRenderer RowBoat;
    public ModelRenderer Cargo_1;
    public ModelRenderer Cargo_0;
    public ModelRenderer ruder0_1;
    public ModelRenderer ruder_l;
    public ModelRenderer ruder_r;
    public ModelRenderer ruder0_2;

    public ModelRowBoat() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.ruder_r = new ModelRenderer(this, 62, 20);
        this.ruder_r.setRotationPoint(10.0F, -9.0F, -9.0F);
        this.ruder_r.addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F, 0.0F, 0.0F, 0.0F);
        this.ruder_r.addBox(-0.2F, -3.0F, 12.0F, 1.0F, 6.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ruder_r, -0.6544984694978736F, 2.4813345656024053F, 0.19634954084936207F);
        this.RowBoat = new ModelRenderer(this, 0, 0);
        this.RowBoat.setRotationPoint(0.0F, 16.0F, 14.0F);
        this.RowBoat.setTextureOffset(0, 8).addBox(0.0F, 0.0F, -9.0F, 19.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(0, 43).addBox(0.0F, -6.0F, -11.0F, 14.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(0, 35).addBox(14.0F, -6.0F, -11.0F, 14.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(0, 43).addBox(14.0F, -6.0F, 9.0F, 14.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(0, 43).addBox(0.0F, -6.0F, 9.0F, 14.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(2, 42).addBox(-12.0F, -7.0F, -9.0F, 12.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(2, 42).addBox(-12.0F, -7.0F, 7.0F, 12.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(3, 15).addBox(40.0F, -8.0F, -7.0F, 2.0F, 8.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(2, 42).addBox(28.0F, -7.0F, 7.0F, 12.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(2, 42).addBox(28.0F, -7.0F, -9.0F, 12.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(3, 15).addBox(-14.0F, -8.0F, -7.0F, 2.0F, 8.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(0, 7).addBox(3.0F, 3.0F, -7.0F, 22.0F, 4.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(0, 1).addBox(25.0F, 3.0F, -4.5F, 11.0F, 3.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(0, 1).addBox(-7.0F, 3.0F, -4.5F, 11.0F, 3.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(10, 0).addBox(-17.0F, -12.0F, -2.0F, 3.0F, 10.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(10, 0).addBox(42.0F, -12.0F, -2.0F, 3.0F, 10.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(0, 8).addBox(0.0F, 0.0F, -3.0F, 19.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(0, 8).addBox(0.0F, 0.0F, 3.0F, 19.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(0, 8).addBox(19.0F, 0.0F, -9.0F, 21.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(0, 8).addBox(19.0F, 0.0F, -3.0F, 21.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(0, 8).addBox(19.0F, 0.0F, 3.0F, 21.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(0, 8).addBox(-12.0F, 0.0F, 3.0F, 12.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(0, 8).addBox(-12.0F, 0.0F, -3.0F, 12.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.setTextureOffset(0, 8).addBox(-12.0F, 0.0F, -9.0F, 12.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.addBox(3.0F, 3.0F, 0.0F, 22.0F, 4.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(RowBoat, -0.0F, 1.5707963267948966F, 0.0F);
        this.Cargo_1 = new ModelRenderer(this, 0, 0);
        this.Cargo_1.setRotationPoint(31.2F, -8.0F, -6.0F);
        this.Cargo_1.setTextureOffset(96, 38).addBox(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.Cargo_1.setTextureOffset(30, 55).addBox(-3.2F, 3.0F, 0.0F, 3.0F, 5.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.Cargo_1.setTextureOffset(31, 56).addBox(4.4F, 3.0F, 8.0F, 3.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.Cargo_1.addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.ruder0_2 = new ModelRenderer(this, 0, 0);
        this.ruder0_2.setRotationPoint(0.0F, 7.0F, -7.0F);
        this.ruder0_2.setTextureOffset(62, 0).addBox(-1.0F, 0.0F, -8.0F, 2.0F, 2.0F, 18.0F, 0.0F, 0.0F, 0.0F);
        this.ruder0_2.setTextureOffset(62, 0).addBox(-0.8F, -3.0F, 15.0F, 1.0F, 6.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.ruder0_2.setTextureOffset(74, 32).addBox(-1.0F, 0.0F, 10.0F, 2.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ruder0_2, 0.05235987755982988F, 0.12217304763960307F, -0.45378560551852565F);
        this.Cargo_0 = new ModelRenderer(this, 0, 0);
        this.Cargo_0.mirror = true;
        this.Cargo_0.setRotationPoint(-11.2F, -16.8F, 2.0F);
        this.Cargo_0.setTextureOffset(96, 38).addBox(0.0F, 9.0F, 0.0F, 8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.Cargo_0.setTextureOffset(31, 56).addBox(-3.0F, 13.0F, 1.0F, 3.0F, 5.0F, 3.0F, 0.0F, -1.0F, 0.0F);
        this.Cargo_0.setTextureOffset(31, 56).addBox(1.0F, 12.0F, 8.3F, 3.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Cargo_0, 0.0F, 1.5707963267948966F, 0.0F);
        this.ruder0_1 = new ModelRenderer(this, 0, 0);
        this.ruder0_1.setRotationPoint(12.0F, -7.0F, -9.0F);
        this.ruder0_1.setTextureOffset(62, 0).addBox(-1.0F, 0.0F, -8.0F, 2.0F, 2.0F, 18.0F, 0.0F, 0.0F, 0.0F);
        this.ruder0_1.setTextureOffset(62, 20).addBox(-0.2F, -3.0F, 15.0F, 1.0F, 6.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.ruder0_1.setTextureOffset(74, 32).addBox(-1.0F, 0.0F, 10.0F, 2.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ruder0_1, 0.7127924691776453F, 0.26459191193917925F, 1.602910321115726F);
        this.ruder_l = new ModelRenderer(this, 62, 0);
        this.ruder_l.setRotationPoint(10.0F, -9.0F, 9.0F);
        this.ruder_l.addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F, 0.0F, 0.0F, 0.0F);
        this.ruder_l.addBox(-0.8F, -3.0F, 12.0F, 1.0F, 6.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ruder_l, -0.6544984694978736F, 0.6602580879873879F, 0.19634954084936207F);
        this.RowBoat.addChild(this.ruder_r);
        this.RowBoat.addChild(this.Cargo_1);
        this.ruder0_1.addChild(this.ruder0_2);
        this.RowBoat.addChild(this.Cargo_0);
        this.RowBoat.addChild(this.ruder0_1);
        this.RowBoat.addChild(this.ruder_l);
    }


    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.RowBoat).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entityIn.isBeingRidden()){
            this.ruder0_1.showModel = true;
            this.ruder0_2.showModel = true;

            this.ruder_r.showModel = false;
            this.ruder_l.showModel = false;
        }
        else {
            this.ruder0_1.showModel = false;
            this.ruder0_2.showModel = false;

            this.ruder_r.showModel = true;
            this.ruder_l.showModel = true;
        }

        this.Cargo_1.showModel = entityIn.Cargo_0;
        this.Cargo_0.showModel = entityIn.Cargo_1;

        this.paddels(entityIn, 0, limbSwing);
        this.paddels(entityIn, 1, limbSwing);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    protected void paddels(RowBoatEntity galleyEntity, int side, float limbSwing) {
        float f = galleyEntity.getRowingTime(side, limbSwing);

        this.ruder_l.rotateAngleX = (float) MathHelper.clampedLerp((double)(-(float)Math.PI / 3F), (double)-0.2617994F, (double)((MathHelper.sin(-f) + 1.0F) / 2.0F));
        this.ruder_l.rotateAngleY = (float)MathHelper.clampedLerp((double)(-(float)Math.PI / 4F), (double)((float)Math.PI / 4F), (double)((MathHelper.sin(-f + 1.0F) + 1.0F) / 2.0F));
        if (side == 0) {
            this.ruder_r.rotateAngleX = (float)MathHelper.clampedLerp((double)(-(float)Math.PI / 3F), (double)-0.2617994F, (double)((MathHelper.sin(-f) + 1.0F) / 2.0F));
            this.ruder_r.rotateAngleY = (float)Math.PI - (float)MathHelper.clampedLerp((double)(-(float)Math.PI / 4F), (double)((float)Math.PI / 4F), (double)((MathHelper.sin(-f + 1.0F) + 1.0F) / 2.0F));
            }
    }
}
