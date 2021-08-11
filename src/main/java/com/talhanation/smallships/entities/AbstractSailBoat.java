package com.talhanation.smallships.entities;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.config.SmallShipsConfig;
import com.talhanation.smallships.init.SoundInit;
import com.talhanation.smallships.network.MessageIsForward;
import com.talhanation.smallships.network.MessageSailState;
import com.talhanation.smallships.network.MessageSteerState;
import com.talhanation.smallships.proxy.CommonProxy;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public abstract class AbstractSailBoat extends AbstractInventoryBoat {
    private static final DataParameter<Boolean> IS_LEFT = EntityDataManager.createKey(AbstractSailBoat.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_RIGHT = EntityDataManager.createKey(AbstractSailBoat.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> SAIL_STATE = EntityDataManager.createKey(AbstractSailBoat.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> IS_FORWARD = EntityDataManager.createKey(AbstractSailBoat.class, DataSerializers.BOOLEAN);

    public AbstractSailBoat(World world) {
        super(world);
    }
    private double waterLevel;

    ////////////////////////////////////TICK////////////////////////////////////

    @Override
    public void tick() {
        super.tick();

        if (getIsForward()){
            if (this.checkInWater())
                Watersplash();
        }

        if (this.canPassengerSteer()) {
            if (this.world.isRemote) {
                this.sendSteerStateToServer();
                this.sendIsForwardToServer();
            }
        }


        if ((this.getControllingPassenger() == null ||!(this.getControllingPassenger() instanceof EntityPlayer) )) {
            setSailState(0);
            setIsForward(false);
        }



        if (SmallShipsConfig.WaterMobFlee) {
            double radius = 15.0D;
            List<EntityLiving> list = this.world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(this.posX - radius, this.posY - radius, this.posZ - radius, this.posX + radius, this.posY + radius, this.posZ + radius));
            for (EntityLiving ent : list)
                fleeEntity(ent);
    }


        if (this.getIsForward()) {
            this.knockBack(this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
            this.knockBack(this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
        }


    }

    public void tickLerp(){}

    ////////////////////////////////////REGISTER////////////////////////////////////

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.dataManager.register(IS_LEFT, false);
        this.dataManager.register(IS_RIGHT, false);
        this.dataManager.register(IS_FORWARD, false);
        this.dataManager.register(SAIL_STATE, 0);
    }

    ////////////////////////////////////GET////////////////////////////////////

    public boolean getSteerState(int side) {
        return this.dataManager.<Boolean>get(side == 0 ? IS_LEFT : IS_RIGHT) && this.getControllingPassenger() != null;
    }

    public Integer getSailState() {
        return dataManager.get(SAIL_STATE);
    }

    public boolean getIsForward() {
        return dataManager.get(IS_FORWARD);
    }

    ////////////////////////////////////SET////////////////////////////////////


    public void setSailState(int state) {
        if (state != getSailState()) {
            playSailSound(state);
            dataManager.set(SAIL_STATE, state);
        }

    }

    public void setSteerState(boolean left, boolean right){
        this.dataManager.set(IS_LEFT, left);
        this.dataManager.set(IS_RIGHT, right);
    }

    public void setIsForward(boolean forward){
        this.dataManager.set(IS_FORWARD, forward);
        if(getSailState() != 0)
            this.dataManager.set(IS_FORWARD,true);

    }

    ////////////////////////////////////SERVER////////////////////////////////////

    public void sendSailStateToServer(int state) {
        if (world.isRemote) {
            CommonProxy.simpleNetworkWrapper.sendToServer(new MessageSailState(state));
        }
    }

    public void sendSteerStateToServer(){
        CommonProxy.simpleNetworkWrapper.sendToServer(new MessageSteerState(this.getSteerState(0), this.getSteerState(1)));
    }

    public void sendIsForwardToServer(){
        CommonProxy.simpleNetworkWrapper.sendToServer(new MessageIsForward(this.getIsForward()));
    }


    ////////////////////////////////////SOUND////////////////////////////////////

    public void playSailSound(int state) {
        if (state != 0) {
            SoundInit.playSound(SoundInit.SHIP_SAIL_0, this.world,  getPosition(), null ,SoundCategory.NEUTRAL, 15.0F);
        } else {
            SoundInit.playSound(SoundInit.SHIP_SAIL_1, this.world,  getPosition(), null ,SoundCategory.NEUTRAL, 15.0F); }
    }

    ////////////////////////////////////ON FUNCTIONS////////////////////////////////////
    @Override
    public void onKeyPressed() {
        int state = getSailState();

        if (state != 4){
            state = 4;

        }else{
            state = 0;
        }

        sendSailStateToServer(state);
    }

    @Override
    public void onKeyLowerPressed() {
        int state = getSailState();

        if (state != 4){
            state++;

        }

        sendSailStateToServer(state);
    }

    @Override
    public void onKeyHigherPressed() {
        int state = getSailState();

        if (state != 0){
            state--;

        }
        sendSailStateToServer(state);
    }

    ////////////////////////////////////OTHER FUNCTIONS////////////////////////////////////

    public void fleeEntity(EntityLiving entity) {
        double fleeDistance = 10.0D;
        Vec3d vecCar = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d vecEntity = new Vec3d(entity.posX, entity.posY, entity.posZ);
        Vec3d fleeDir = vecEntity.subtract(vecCar);
        fleeDir = fleeDir.normalize();
        Vec3d fleePos = new Vec3d(vecEntity.x + fleeDir.x * fleeDistance, vecEntity.y + fleeDir.y * fleeDistance, vecEntity.z + fleeDir.z * fleeDistance);
        entity.getNavigator().tryMoveToXYZ(fleePos.x, fleePos.y, fleePos.z, 2.5D);
    }

    private void knockBack(List<Entity> entities) {
        double d0 = (this.getEntityBoundingBox().minX + this.getEntityBoundingBox().maxX) / 2.0D;
        double d1 = (this.getEntityBoundingBox().minZ + this.getEntityBoundingBox().maxZ) / 2.0D;

        for(Entity entity : entities) {
            if (entity instanceof EntityLivingBase) {
                double d2 = entity.getPosition().getX() - d0;
                double d3 = entity.getPosition().getZ() - d1;
                double d4 = Math.max(d2 * d2 + d3 * d3, 0.1D);
                entity.addVelocity(d2 / d4 * 0.4D, (double)0.0F, d3 / d4 * 0.4D);
            }
        }

    }

    private boolean checkInWater()
    {
        AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.ceil(axisalignedbb.maxX);
        int k = MathHelper.floor(axisalignedbb.minY);
        int l = MathHelper.ceil(axisalignedbb.minY + 0.001D);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.ceil(axisalignedbb.maxZ);
        boolean flag = false;
        this.waterLevel = Double.MIN_VALUE;
        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

        try
        {
            for (int k1 = i; k1 < j; ++k1)
            {
                for (int l1 = k; l1 < l; ++l1)
                {
                    for (int i2 = i1; i2 < j1; ++i2)
                    {
                        blockpos$pooledmutableblockpos.setPos(k1, l1, i2);
                        IBlockState iblockstate = this.world.getBlockState(blockpos$pooledmutableblockpos);

                        Boolean result = iblockstate.getBlock().isAABBInsideMaterial(world, blockpos$pooledmutableblockpos, axisalignedbb, Material.WATER);
                        if (result != null) {
                            if (!result) continue;

                            float f = iblockstate.getBlock().getBlockLiquidHeight(world, blockpos$pooledmutableblockpos, iblockstate, Material.WATER) + blockpos$pooledmutableblockpos.getY();
                            this.waterLevel = Math.max((double)f, this.waterLevel);
                            flag |= axisalignedbb.minY < (double)f;
                        }

                        if (iblockstate.getMaterial() == Material.WATER)
                        {
                            float f = BlockLiquid.getLiquidHeight(iblockstate, this.world, blockpos$pooledmutableblockpos);
                            this.waterLevel = Math.max((double)f, this.waterLevel);
                            flag |= axisalignedbb.minY < (double)f;
                        }
                    }
                }
            }
        }
        finally
        {
            blockpos$pooledmutableblockpos.release();
        }

        return flag;
    }

}