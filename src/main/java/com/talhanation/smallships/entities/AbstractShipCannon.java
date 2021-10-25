package com.talhanation.smallships.entities;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.network.MessageShootCannon;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
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


    public Vector3d getShootVector(String x){
        switch (x){
            case "east":
                return new Vector3d(1,0,0);

            case "west":
                return new Vector3d(-1,0,0);

            case "north":
                return new Vector3d(0,0,-1);

            case "south":
                return new Vector3d(0,0,1);
        }
        return null;
    }

    public void shootCannon(boolean s) {
        Vector3d vector3d = this.getForward().yRot(3.14F/2);
        float speed = 5F;
        double par1 = 4.0D;
        double fall = 0D;
        /*
        double d1 = this.getDriver().getX() - this.getX();
        double d2 = this.getDriver().getY(0.75) - this.getY();
        double d3 = this.getDriver().getZ() - this.getZ();
         */
        double d1 = this.getX();
        double d2 = this.getY() + 2.5;
        double d3 = this.getZ();

        LlamaSpitEntity fireballentity = new LlamaSpitEntity(this.level, d1, d2, d3,  d1, d2, d3);
        fireballentity.shoot(vector3d.x(), vector3d.y(), vector3d.z(), speed, 10);
        this.level.addFreshEntity(fireballentity);

    }

    ////////////////////////////////////PARTICLE FUNCTIONS////////////////////////////////////



    ////////////////////////////////////OTHER FUNCTIONS////////////////////////////////////



}
