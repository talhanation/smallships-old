package com.talhanation.smallships.entities;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.network.MessageShootCannon;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;


public abstract class AbstractShipCannon extends AbstractShipDamage {
    private static final DataParameter<Integer> CANNON_COUNT = EntityDataManager.defineId(AbstractShipCannon.class, DataSerializers.INT);

    public AbstractShipCannon(EntityType<? extends TNBoatEntity> type, World world) {
        super(type, world);

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CANNON_COUNT, 0);
    }

    ////////////////////////////////////TICK////////////////////////////////////

    @Override
    public void tick() {
        super.tick();

    }

    ////////////////////////////////////SAVE////////////////////////////////////

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("CannonCount", getCannonCount());
    }



    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        setCannonCount(nbt.getInt("CannonCount"));
    }

    ////////////////////////////////////GET////////////////////////////////////

    public int getCannonCount(){
        return entityData.get(CANNON_COUNT);
    }

    public Vector3d getShootSide() {
        return null;
    }

    ////////////////////////////////////SET////////////////////////////////////

    public void setCannonCount(int count){
        entityData.set(CANNON_COUNT, count);
    }

    ////////////////////////////////////ON FUNCTIONS////////////////////////////////////

    public void onCannonKeyPressed(){
        Main.SIMPLE_CHANNEL.sendToServer(new MessageShootCannon(true));
    }

    public void shootCannon(boolean s) {
        Vector3d vector3d = this.getDriver().getViewVector(1F);
        double par1 = 4.0D;
        double par2 = 1D;

        double d2 = this.getX() + vector3d.x * par1;
        double d3 = par2 + this.getY(par2);
        double d4 = this.getZ() + vector3d.z * par1;


        SmallFireballEntity fireballentity = new SmallFireballEntity(this.level, this.getDriver(), d2, d3, d4);
        //fireballentity.explosionPower = 2;
        fireballentity.setPos(this.getX() + vector3d.x * par1, this.getY(par2) + par2, fireballentity.getZ() + vector3d.z * par1);
        this.level.addFreshEntity(fireballentity);

    }

    ////////////////////////////////////PARTICLE FUNCTIONS////////////////////////////////////



    ////////////////////////////////////OTHER FUNCTIONS////////////////////////////////////



}
