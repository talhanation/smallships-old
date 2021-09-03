package com.talhanation.smallships.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.talhanation.smallships.entities.RowBoatEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelRowBoat - Either Mojang or a mod author (Taken From Memory)
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class ModelRowBoat<T extends RowBoatEntity> extends EntityModel<T> {
    public ModelPart RowBoat;
    public ModelPart Cargo_1;
    public ModelPart Cargo_0;
    public ModelPart ruder0_1;
    public ModelPart ruder_l;
    public ModelPart ruder_r;
    public ModelPart ruder0_2;

    public ModelRowBoat() {
        this.texWidth = 128;
        this.texHeight = 64;
        this.ruder_r = new ModelPart(this, 62, 20);
        this.ruder_r.setPos(10.0F, -9.0F, -9.0F);
        this.ruder_r.addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F, 0.0F, 0.0F, 0.0F);
        this.ruder_r.addBox(-0.2F, -3.0F, 12.0F, 1.0F, 6.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ruder_r, -0.6544984694978736F, 2.4813345656024053F, 0.19634954084936207F);
        this.RowBoat = new ModelPart(this, 0, 0);
        this.RowBoat.setPos(0.0F, 16.0F, 14.0F);
        this.RowBoat.texOffs(0, 8).addBox(0.0F, 0.0F, -9.0F, 19.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(0, 43).addBox(0.0F, -6.0F, -11.0F, 14.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(0, 35).addBox(14.0F, -6.0F, -11.0F, 14.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(0, 43).addBox(14.0F, -6.0F, 9.0F, 14.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(0, 43).addBox(0.0F, -6.0F, 9.0F, 14.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(2, 42).addBox(-12.0F, -7.0F, -9.0F, 12.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(2, 42).addBox(-12.0F, -7.0F, 7.0F, 12.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(3, 15).addBox(40.0F, -8.0F, -7.0F, 2.0F, 8.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(2, 42).addBox(28.0F, -7.0F, 7.0F, 12.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(2, 42).addBox(28.0F, -7.0F, -9.0F, 12.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(3, 15).addBox(-14.0F, -8.0F, -7.0F, 2.0F, 8.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(0, 7).addBox(3.0F, 3.0F, -7.0F, 22.0F, 4.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(0, 1).addBox(25.0F, 3.0F, -4.5F, 11.0F, 3.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(0, 1).addBox(-7.0F, 3.0F, -4.5F, 11.0F, 3.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(10, 0).addBox(-17.0F, -12.0F, -2.0F, 3.0F, 10.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(10, 0).addBox(42.0F, -12.0F, -2.0F, 3.0F, 10.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(0, 8).addBox(0.0F, 0.0F, -3.0F, 19.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(0, 8).addBox(0.0F, 0.0F, 3.0F, 19.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(0, 8).addBox(19.0F, 0.0F, -9.0F, 21.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(0, 8).addBox(19.0F, 0.0F, -3.0F, 21.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(0, 8).addBox(19.0F, 0.0F, 3.0F, 21.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(0, 8).addBox(-12.0F, 0.0F, 3.0F, 12.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(0, 8).addBox(-12.0F, 0.0F, -3.0F, 12.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.texOffs(0, 8).addBox(-12.0F, 0.0F, -9.0F, 12.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.RowBoat.addBox(3.0F, 3.0F, 0.0F, 22.0F, 4.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(RowBoat, -0.0F, 1.5707963267948966F, 0.0F);
        this.Cargo_1 = new ModelPart(this, 0, 0);
        this.Cargo_1.setPos(31.2F, -8.0F, -6.0F);
        this.Cargo_1.texOffs(96, 38).addBox(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.Cargo_1.texOffs(30, 55).addBox(-3.2F, 3.0F, 0.0F, 3.0F, 5.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.Cargo_1.texOffs(31, 56).addBox(4.4F, 3.0F, 8.0F, 3.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.Cargo_1.addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.ruder0_2 = new ModelPart(this, 0, 0);
        this.ruder0_2.setPos(0.0F, 7.0F, -7.0F);
        this.ruder0_2.texOffs(62, 0).addBox(-1.0F, 0.0F, -8.0F, 2.0F, 2.0F, 18.0F, 0.0F, 0.0F, 0.0F);
        this.ruder0_2.texOffs(62, 0).addBox(-0.8F, -3.0F, 15.0F, 1.0F, 6.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.ruder0_2.texOffs(74, 32).addBox(-1.0F, 0.0F, 10.0F, 2.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ruder0_2, 0.05235987755982988F, 0.12217304763960307F, -0.45378560551852565F);
        this.Cargo_0 = new ModelPart(this, 0, 0);
        this.Cargo_0.mirror = true;
        this.Cargo_0.setPos(-11.2F, -16.8F, 2.0F);
        this.Cargo_0.texOffs(96, 38).addBox(0.0F, 9.0F, 0.0F, 8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.Cargo_0.texOffs(31, 56).addBox(-3.0F, 13.0F, 1.0F, 3.0F, 5.0F, 3.0F, 0.0F, -1.0F, 0.0F);
        this.Cargo_0.texOffs(31, 56).addBox(1.0F, 12.0F, 8.3F, 3.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Cargo_0, 0.0F, 1.5707963267948966F, 0.0F);
        this.ruder0_1 = new ModelPart(this, 0, 0);
        this.ruder0_1.setPos(12.0F, -7.0F, -9.0F);
        this.ruder0_1.texOffs(62, 0).addBox(-1.0F, 0.0F, -8.0F, 2.0F, 2.0F, 18.0F, 0.0F, 0.0F, 0.0F);
        this.ruder0_1.texOffs(62, 20).addBox(-0.2F, -3.0F, 15.0F, 1.0F, 6.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.ruder0_1.texOffs(74, 32).addBox(-1.0F, 0.0F, 10.0F, 2.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ruder0_1, 0.7127924691776453F, 0.26459191193917925F, 1.602910321115726F);
        this.ruder_l = new ModelPart(this, 62, 0);
        this.ruder_l.setPos(10.0F, -9.0F, 9.0F);
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
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.RowBoat).forEach((ModelPart) -> {
            ModelPart.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entityIn.isVehicle()){
            this.ruder0_1.visible = true;
            this.ruder0_2.visible = true;

            this.ruder_r.visible = false;
            this.ruder_l.visible = false;
        }
        else {
            this.ruder0_1.visible = false;
            this.ruder0_2.visible = false;

            this.ruder_r.visible = true;
            this.ruder_l.visible = true;
        }

        this.Cargo_1.visible = entityIn.Cargo_0;
        this.Cargo_0.visible = entityIn.Cargo_1;

        this.paddels(entityIn, 0, limbSwing);
        this.paddels(entityIn, 1, limbSwing);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelPart modelPart, float x, float y, float z) {
        modelPart.xRot = x;
        modelPart.yRot = y;
        modelPart.zRot = z;
    }

    protected void paddels(RowBoatEntity galleyEntity, int side, float limbSwing) {
        float f = galleyEntity.getRowingTime(side, limbSwing);

        this.ruder_r.xRot = (float) Mth.clampedLerp((double)(-(float)Math.PI / 3F), (double)-0.2617994F, (double)((Mth.sin(-f) + 1.0F) / 2.0F));
        this.ruder_r.yRot = (float)Math.PI - (float)Mth.clampedLerp((double)(-(float)Math.PI / 4F), (double)((float)Math.PI / 4F), (double)((Mth.sin(-f + 1.0F) + 1.0F) / 2.0F));
        if (side == 0) {
            this.ruder_l.xRot = (float) Mth.clampedLerp((double)(-(float)Math.PI / 3F), (double)-0.2617994F, (double)((Mth.sin(-f) + 1.0F) / 2.0F));
            this.ruder_l.yRot = (float)Mth.clampedLerp((double)(-(float)Math.PI / 4F), (double)((float)Math.PI / 4F), (double)((Mth.sin(-f + 1.0F) + 1.0F) / 2.0F));
            }
    }
}
