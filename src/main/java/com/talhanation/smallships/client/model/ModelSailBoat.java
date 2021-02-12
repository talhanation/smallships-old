package com.talhanation.smallships.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.talhanation.smallships.entities.CogEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * BoatModel - Either Mojang or a mod author (Taken From Memory)
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class ModelSailBoat<T extends CogEntity> extends EntityModel<T> {
    public ModelRenderer botom_1;
    public ModelRenderer side_1;
    public ModelRenderer side_3;
    public ModelRenderer botom_2;
    public ModelRenderer botom_3;
    public ModelRenderer botom_4;
    public ModelRenderer side_4;
    public ModelRenderer side_5;
    public ModelRenderer side_6;
    public ModelRenderer side_7;
    public ModelRenderer side_8;
    public ModelRenderer side_9;
    public ModelRenderer side_10;
    public ModelRenderer side_2;
    public ModelRenderer botom_1_1;
    public ModelRenderer botom_5;
    public ModelRenderer botom_6;
    public ModelRenderer mast_1;
    public ModelRenderer botom_7;
    public ModelRenderer botom_8;
    public ModelRenderer wool_0;
    public ModelRenderer seil_1_1;
    public ModelRenderer seil_2_1;
    public ModelRenderer seil_3_1;
    public ModelRenderer mast_2;
    public ModelRenderer side_10_1;
    public ModelRenderer side_11;
    public ModelRenderer side_12;
    public ModelRenderer side_13;
    public ModelRenderer mast_3;
    public ModelRenderer wool_1;
    public ModelRenderer wool_2;
    public ModelRenderer wool_3;
    public ModelRenderer wool_4;
    public ModelRenderer wool_5;
    public ModelRenderer wool_6;
    public ModelRenderer wool_7;
    public ModelRenderer seil_1_2;
    public ModelRenderer seil_1_3;
    public ModelRenderer seil_1_4;
    public ModelRenderer seil_1_4_1;
    public ModelRenderer seil_1_4_2;
    public ModelRenderer seil_2_2;
    public ModelRenderer seil_2_3;
    public ModelRenderer seil_2_4;
    public ModelRenderer seil_3_2;
    public ModelRenderer seil_3_3;
    public ModelRenderer seil_3_4;


    public ModelSailBoat() {

        this.textureWidth = 128;
        this.textureHeight = 64;
        this.seil_2_1 = new ModelRenderer(this, 0, 0);
        this.seil_2_1.setRotationPoint(45.0F, -2.0F, 7.5F);
        this.seil_2_1.setTextureOffset(99, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(seil_2_1, 1.5707963267948966F, 0.0F, -0.5759586531581287F);
        this.botom_4 = new ModelRenderer(this, 0, 0);
        this.botom_4.setRotationPoint(28.0F, 3.0F, 17.0F);
        this.botom_4.addBox(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(botom_4, 1.5707963267948966F, 0.0F, 0.0F);
        this.side_10 = new ModelRenderer(this, 0, 27);
        this.side_10.setRotationPoint(-17.0F, 4.0F, 7.0F);
        this.side_10.addBox(-8.0F, -7.0F, -1.0F, 16.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(side_10, 0.0F, 1.5707963267948966F, 0.0F);
        this.seil_2_2 = new ModelRenderer(this, 0, 0);
        this.seil_2_2.setRotationPoint(0.0F, 0.0F, 14.0F);
        this.seil_2_2.setTextureOffset(99, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.seil_1_4_2 = new ModelRenderer(this, 0, 0);
        this.seil_1_4_2.setRotationPoint(0.0F, 0.0F, 14.0F);
        this.seil_1_4_2.setTextureOffset(106, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.seil_3_2 = new ModelRenderer(this, 0, 0);
        this.seil_3_2.setRotationPoint(0.0F, 0.0F, 14.0F);
        this.seil_3_2.setTextureOffset(99, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.wool_0 = new ModelRenderer(this, 96, 0);
        this.wool_0.setRotationPoint(16.0F, -42.0F, -24.0F);
        this.wool_0.addBox(0.0F, 0.0F, 0.0F, 3.0F, 5.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.seil_2_3 = new ModelRenderer(this, 0, 0);
        this.seil_2_3.setRotationPoint(0.0F, 0.0F, 14.0F);
        this.seil_2_3.setTextureOffset(99, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.seil_3_3 = new ModelRenderer(this, 0, 0);
        this.seil_3_3.setRotationPoint(0.0F, 0.0F, 14.0F);
        this.seil_3_3.setTextureOffset(100, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.botom_2 = new ModelRenderer(this, 0, 0);
        this.botom_2.setRotationPoint(28.0F, 3.0F, 1.0F);
        this.botom_2.addBox(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(botom_2, 1.5707963267948966F, 0.0F, 0.0F);
        this.side_4 = new ModelRenderer(this, 0, 35);
        this.side_4.setRotationPoint(28.0F, 4.0F, -9.0F);
        this.side_4.addBox(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(side_4, 0.0F, 3.141592653589793F, 0.0F);
        this.seil_1_4 = new ModelRenderer(this, 0, 0);
        this.seil_1_4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.seil_1_4.setTextureOffset(100, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.mast_3 = new ModelRenderer(this, 10, 0);
        this.mast_3.setRotationPoint(0.0F, -18.0F, 0.0F);
        this.mast_3.addBox(0.0F, 0.0F, 0.0F, 3.0F, 18.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.mast_1 = new ModelRenderer(this, 10, 0);
        this.mast_1.setRotationPoint(12.0F, -14.0F, 6.0F);
        this.mast_1.addBox(0.0F, 0.0F, 0.0F, 3.0F, 18.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.wool_7 = new ModelRenderer(this, 96, 0);
        this.wool_7.setRotationPoint(0.0F, 0.0F, 8.0F);
        this.wool_7.addBox(0.0F, 0.0F, 0.0F, 3.0F, 5.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.side_2 = new ModelRenderer(this, 0, 35);
        this.side_2.setRotationPoint(0.0F, 4.0F, -9.0F);
        this.side_2.addBox(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(side_2, 0.0F, 3.141592653589793F, 0.0F);
        this.side_11 = new ModelRenderer(this, 0, 27);
        this.side_11.setRotationPoint(3.0F, -23.0F, 10.0F);
        this.side_11.addBox(-8.0F, -7.0F, -1.0F, 16.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(side_11, 0.0F, 1.5707963267948966F, 0.0F);
        this.side_13 = new ModelRenderer(this, 0, 27);
        this.side_13.setRotationPoint(3.0F, -23.0F, 26.0F);
        this.side_13.addBox(-8.0F, -7.0F, -1.0F, 16.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(side_13, 0.0F, 1.5707963267948966F, 0.0F);
        this.wool_4 = new ModelRenderer(this, 96, 0);
        this.wool_4.setRotationPoint(0.0F, 0.0F, 8.0F);
        this.wool_4.addBox(0.0F, 0.0F, 0.0F, 3.0F, 5.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.seil_1_1 = new ModelRenderer(this, 0, 0);
        this.seil_1_1.setRotationPoint(12.5F, -2.0F, -9.0F);
        this.seil_1_1.setTextureOffset(99, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(seil_1_1, 1.2566370614359172F, 0.0F, 0.0F);
        this.mast_2 = new ModelRenderer(this, 10, 0);
        this.mast_2.setRotationPoint(0.0F, -18.0F, 0.0F);
        this.mast_2.addBox(0.0F, 0.0F, 0.0F, 3.0F, 18.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.botom_1_1 = new ModelRenderer(this, 0, 0);
        this.botom_1_1.setRotationPoint(0.0F, 3.0F, 1.0F);
        this.botom_1_1.addBox(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(botom_1_1, 1.5707963267948966F, 0.0F, 0.0F);
        this.seil_1_2 = new ModelRenderer(this, 0, 0);
        this.seil_1_2.setRotationPoint(0.0F, 0.0F, 14.0F);
        this.seil_1_2.setTextureOffset(99, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.botom_8 = new ModelRenderer(this, 0, 0);
        this.botom_8.setRotationPoint(26.0F, 9.0F, 9.0F);
        this.botom_8.addBox(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F, 0.0F, 4.0F, 0.0F);
        this.setRotateAngle(botom_8, 1.5707963267948966F, 0.0F, 0.0F);
        this.side_5 = new ModelRenderer(this, 0, 43);
        this.side_5.setRotationPoint(28.0F, 4.0F, 24.0F);
        this.side_5.addBox(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.botom_6 = new ModelRenderer(this, 0, 0);
        this.botom_6.setRotationPoint(26.0F, 6.0F, 9.0F);
        this.botom_6.addBox(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F, 0.0F, 4.0F, 0.0F);
        this.setRotateAngle(botom_6, 1.5707963267948966F, 0.0F, 0.0F);
        this.botom_3 = new ModelRenderer(this, 0, 0);
        this.botom_3.setRotationPoint(0.0F, 3.0F, 17.0F);
        this.botom_3.addBox(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(botom_3, 1.5707963267948966F, 0.0F, 0.0F);
        this.wool_6 = new ModelRenderer(this, 96, 0);
        this.wool_6.setRotationPoint(0.0F, 0.0F, 8.0F);
        this.wool_6.addBox(0.0F, 0.0F, 0.0F, 3.0F, 5.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.wool_1 = new ModelRenderer(this, 96, 0);
        this.wool_1.setRotationPoint(0.0F, 0.0F, 8.0F);
        this.wool_1.addBox(0.0F, 0.0F, 0.0F, 3.0F, 5.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.wool_3 = new ModelRenderer(this, 96, 0);
        this.wool_3.setRotationPoint(0.0F, 0.0F, 8.0F);
        this.wool_3.addBox(0.0F, 0.0F, 0.0F, 3.0F, 5.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.side_10_1 = new ModelRenderer(this, 0, 27);
        this.side_10_1.setRotationPoint(3.0F, -23.0F, -6.0F);
        this.side_10_1.addBox(-8.0F, -7.0F, -1.0F, 16.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(side_10_1, 0.0F, 1.5707963267948966F, 0.0F);
        this.side_3 = new ModelRenderer(this, 0, 43);
        this.side_3.setRotationPoint(0.0F, 4.0F, 24.0F);
        this.side_3.addBox(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.botom_7 = new ModelRenderer(this, 0, 0);
        this.botom_7.setRotationPoint(1.0F, 9.0F, 9.0F);
        this.botom_7.addBox(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F, 0.0F, 4.0F, 0.0F);
        this.setRotateAngle(botom_7, 1.5707963267948966F, 0.0F, 0.0F);
        this.seil_3_1 = new ModelRenderer(this, 0, 0);
        this.seil_3_1.setRotationPoint(12.5F, -2.0F, 23.0F);
        this.seil_3_1.setTextureOffset(99, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(seil_3_1, 1.876228945893904F, 0.0F, 0.0F);
        this.side_9 = new ModelRenderer(this, 0, 27);
        this.side_9.setRotationPoint(-15.0F, 4.0F, 16.0F);
        this.side_9.addBox(-8.0F, -7.0F, -1.0F, 16.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(side_9, 0.0F, 1.5707963267948966F, 0.0F);
        this.botom_5 = new ModelRenderer(this, 0, 0);
        this.botom_5.setRotationPoint(1.0F, 6.0F, 9.0F);
        this.botom_5.addBox(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F, 0.0F, 4.0F, 0.0F);
        this.setRotateAngle(botom_5, 1.5707963267948966F, 0.0F, 0.0F);
        this.side_7 = new ModelRenderer(this, 0, 27);
        this.side_7.setRotationPoint(43.0F, 4.0F, 0.0F);
        this.side_7.addBox(-8.0F, -7.0F, -1.0F, 16.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(side_7, 0.0F, 1.5707963267948966F, 0.0F);
        this.side_12 = new ModelRenderer(this, 0, 27);
        this.side_12.setRotationPoint(3.0F, -23.0F, -22.0F);
        this.side_12.addBox(-8.0F, -7.0F, -1.0F, 16.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(side_12, 0.0F, 1.5707963267948966F, 0.0F);
        this.seil_2_4 = new ModelRenderer(this, 0, 0);
        this.seil_2_4.setRotationPoint(0.0F, 0.0F, 14.0F);
        this.seil_2_4.setTextureOffset(100, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.side_1 = new ModelRenderer(this, 0, 27);
        this.side_1.setRotationPoint(45.0F, 4.0F, 7.0F);
        this.side_1.addBox(-8.0F, -7.0F, -1.0F, 16.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(side_1, 0.0F, 1.5707963267948966F, 0.0F);
        this.seil_1_3 = new ModelRenderer(this, 0, 0);
        this.seil_1_3.setRotationPoint(0.0F, 0.0F, 14.0F);
        this.seil_1_3.setTextureOffset(100, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.seil_1_4_1 = new ModelRenderer(this, 0, 0);
        this.seil_1_4_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.seil_1_4_1.setTextureOffset(98, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.side_8 = new ModelRenderer(this, 0, 27);
        this.side_8.setRotationPoint(-15.0F, 4.0F, 0.0F);
        this.side_8.addBox(-8.0F, -7.0F, -1.0F, 16.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(side_8, 0.0F, 1.5707963267948966F, 0.0F);
        this.wool_2 = new ModelRenderer(this, 96, 0);
        this.wool_2.setRotationPoint(0.0F, 0.0F, 8.0F);
        this.wool_2.addBox(0.0F, 0.0F, 0.0F, 3.0F, 5.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.botom_1 = new ModelRenderer(this, 10, -8);
        this.botom_1.setRotationPoint(-8.0F, 12.0F, 14.0F);
        this.botom_1.addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(botom_1, 0.0F, 1.5707963267948966F, 0.0F);
        this.seil_3_4 = new ModelRenderer(this, 0, 0);
        this.seil_3_4.setRotationPoint(0.0F, 0.0F, 14.0F);
        this.seil_3_4.setTextureOffset(106, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.side_6 = new ModelRenderer(this, 0, 27);
        this.side_6.setRotationPoint(43.0F, 4.0F, 16.0F);
        this.side_6.addBox(-8.0F, -7.0F, -1.0F, 16.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(side_6, 0.0F, 1.5707963267948966F, 0.0F);
        this.wool_5 = new ModelRenderer(this, 96, 0);
        this.wool_5.setRotationPoint(0.0F, 0.0F, 8.0F);
        this.wool_5.addBox(0.0F, 0.0F, 0.0F, 3.0F, 5.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.botom_1.addChild(this.seil_2_1);
        this.botom_1.addChild(this.botom_4);
        this.botom_1.addChild(this.side_10);
        this.seil_2_1.addChild(this.seil_2_2);
        this.seil_1_4_1.addChild(this.seil_1_4_2);
        this.seil_3_1.addChild(this.seil_3_2);
        this.botom_1.addChild(this.wool_0);
        this.seil_2_2.addChild(this.seil_2_3);
        this.seil_3_2.addChild(this.seil_3_3);
        this.botom_1.addChild(this.botom_2);
        this.botom_1.addChild(this.side_4);
        this.seil_1_3.addChild(this.seil_1_4);
        this.mast_2.addChild(this.mast_3);
        this.botom_1.addChild(this.mast_1);
        this.wool_6.addChild(this.wool_7);
        this.botom_1.addChild(this.side_2);
        this.mast_1.addChild(this.side_11);
        this.mast_1.addChild(this.side_13);
        this.wool_3.addChild(this.wool_4);
        this.botom_1.addChild(this.seil_1_1);
        this.mast_1.addChild(this.mast_2);
        this.botom_1.addChild(this.botom_1_1);
        this.seil_1_1.addChild(this.seil_1_2);
        this.botom_1.addChild(this.botom_8);
        this.botom_1.addChild(this.side_5);
        this.botom_1.addChild(this.botom_6);
        this.botom_1.addChild(this.botom_3);
        this.wool_5.addChild(this.wool_6);
        this.wool_0.addChild(this.wool_1);
        this.wool_2.addChild(this.wool_3);
        this.mast_1.addChild(this.side_10_1);
        this.botom_1.addChild(this.side_3);
        this.botom_1.addChild(this.botom_7);
        this.botom_1.addChild(this.seil_3_1);
        this.botom_1.addChild(this.side_9);
        this.botom_1.addChild(this.botom_5);
        this.botom_1.addChild(this.side_7);
        this.mast_1.addChild(this.side_12);
        this.seil_2_3.addChild(this.seil_2_4);
        this.botom_1.addChild(this.side_1);
        this.seil_1_2.addChild(this.seil_1_3);
        this.seil_1_4.addChild(this.seil_1_4_1);
        this.botom_1.addChild(this.side_8);
        this.wool_1.addChild(this.wool_2);
        this.seil_3_3.addChild(this.seil_3_4);
        this.botom_1.addChild(this.side_6);
        this.wool_4.addChild(this.wool_5);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.botom_1).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {


    }



    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

}
