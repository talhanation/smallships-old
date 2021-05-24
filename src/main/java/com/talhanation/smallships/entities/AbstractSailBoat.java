package com.talhanation.smallships.entities;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.config.SmallShipsConfig;
import com.talhanation.smallships.init.SoundInit;
import com.talhanation.smallships.network.MessageSailState;
import com.talhanation.smallships.network.MessageSteerState;
import net.minecraft.block.BlockState;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public abstract class AbstractSailBoat extends AbstractInventoryBoat {
    private static final DataParameter<Boolean> IS_LEFT = EntityDataManager.defineId(AbstractSailBoat.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_RIGHT = EntityDataManager.defineId(AbstractSailBoat.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SAIL_STATE = EntityDataManager.defineId(AbstractSailBoat.class, DataSerializers.BOOLEAN);

    public AbstractSailBoat(EntityType<? extends TNBoatEntity> type, World world) {
        super(type, world);
    }

    ////////////////////////////////////TICK////////////////////////////////////

    @Override
    public void tick() {
        super.tick();

        if (this.isControlledByLocalInstance()) {
            if (this.level.isClientSide) {
                this.sendSteerStateToServer();
            }
        }

        if ((this.getControllingPassenger() == null ||!(this.getControllingPassenger() instanceof PlayerEntity) )&& getSailState()) {
            setSailState(false);
        }

        breakLily();

        if (SmallShipsConfig.WaterMobFlee.get()) {
            double radius = 15.0D;
            List<WaterMobEntity> list1 = this.level.getEntitiesOfClass(WaterMobEntity.class, new AxisAlignedBB(getX() - radius, getY() - radius, getZ() - radius, getX() + radius, getY() + radius, getZ() + radius));
            for (WaterMobEntity ent : list1)
                fleeEntity(ent);
        }


        if (!level.isClientSide && this.getSailState() || this.forwardInputDown) {
            this.knockBack(this.level.getEntities(this, this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D).move(0.0D, -2.0D, 0.0D), EntityPredicates.NO_CREATIVE_OR_SPECTATOR));
            this.knockBack(this.level.getEntities(this, this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D).move(0.0D, -2.0D, 0.0D), EntityPredicates.NO_CREATIVE_OR_SPECTATOR));

        }
    }

    public void tickLerp() {
    }

    ////////////////////////////////////REGISTER////////////////////////////////////

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_LEFT, false);
        this.entityData.define(IS_RIGHT, false);
        this.entityData.define(SAIL_STATE, false);
    }

    ////////////////////////////////////GET////////////////////////////////////

    public boolean getSteerState(int side) {
        return this.entityData.<Boolean>get(side == 0 ? IS_LEFT : IS_RIGHT) && this.getControllingPassenger() != null;
    }

    public boolean getSailState() {
        return entityData.get(SAIL_STATE);
    }

    ////////////////////////////////////SET////////////////////////////////////


    public void setSailState(boolean state) {
        if (state != getSailState()) {
            playSailSound(state);
            entityData.set(SAIL_STATE, state);
        }
    }

    public void setSteerState(boolean left, boolean right){
        this.entityData.set(IS_LEFT, left);
        this.entityData.set(IS_RIGHT, right);
    }

    ////////////////////////////////////SERVER////////////////////////////////////

    public void sendSailStateToServer(boolean state) {
        if (level.isClientSide) {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageSailState(state));
        }
    }

    public void sendSteerStateToServer(){
        Main.SIMPLE_CHANNEL.sendToServer(new MessageSteerState(this.getSteerState(0), this.getSteerState(1)));
    }

    ////////////////////////////////////SOUND////////////////////////////////////

    public void playSailSound(boolean state) {
        if (state) {
            this.level.playSound(null, this.getX(), this.getY() + 4 , this.getZ(), SoundInit.SHIP_SAIL_0.get(), this.getSoundSource(), 15.0F, 0.8F + 0.4F * this.random.nextFloat());
        } else {
            this.level.playSound(null, this.getX(), this.getY() + 4, this.getZ(), SoundInit.SHIP_SAIL_1.get(), this.getSoundSource(), 10.0F, 0.8F + 0.4F * this.random.nextFloat());
        }
    }

    ////////////////////////////////////ON FUNCTIONS////////////////////////////////////
    @Override
    public void onSprintPressed() {
        sendSailStateToServer(!getSailState());
    }

    ////////////////////////////////////OTHER FUNCTIONS////////////////////////////////////

    public void fleeEntity(MobEntity entity) {
        double fleeDistance = 10.0D;
        Vector3d vecBoat = new Vector3d(getX(), getY(), getZ());
        Vector3d vecEntity = new Vector3d(entity.getX(), entity.getY(), entity.getZ());
        Vector3d fleeDir = vecEntity.subtract(vecBoat);
        fleeDir = fleeDir.normalize();
        Vector3d fleePos = new Vector3d(vecEntity.x + fleeDir.x * fleeDistance, vecEntity.y + fleeDir.y * fleeDistance, vecEntity.z + fleeDir.z * fleeDistance);
        entity.getNavigation().moveTo(fleePos.x, fleePos.y, fleePos.z, 10.0D);
    }

    private void breakLily() {
        AxisAlignedBB boundingBox = getBoundingBox();
        double offset = 0.75D;
        BlockPos start = new BlockPos(boundingBox.minX - offset, boundingBox.minY - offset, boundingBox.minZ - offset);
        BlockPos end = new BlockPos(boundingBox.maxX + offset, boundingBox.maxY + offset, boundingBox.maxZ + offset);
        BlockPos.Mutable pos = new BlockPos.Mutable();
        boolean hasBroken = false;
        if (level.hasChunksAt(start, end)) {
            for (int i = start.getX(); i <= end.getX(); ++i) {
                for (int j = start.getY(); j <= end.getY(); ++j) {
                    for (int k = start.getZ(); k <= end.getZ(); ++k) {
                        pos.set(i, j, k);
                        BlockState blockstate = level.getBlockState(pos);
                        if (blockstate.getBlock() instanceof LilyPadBlock) {
                            level.destroyBlock(pos, true);
                            hasBroken = true;
                        }
                    }
                }
            }
        }
        if (hasBroken) {
            level.playSound(null, getX(), getY(), getZ(), SoundEvents.CROP_BREAK, SoundCategory.BLOCKS, 1F, 0.9F + 0.2F * random.nextFloat());
        }
    }

    private void knockBack(List<Entity> entities) {
        double d0 = (this.getBoundingBox().minX + this.getBoundingBox().maxX) / 2.0D;
        double d1 = (this.getBoundingBox().minZ + this.getBoundingBox().maxZ) / 2.0D;

        for(Entity entity : entities) {
            if (entity instanceof LivingEntity) {
                double d2 = entity.getX() - d0;
                double d3 = entity.getZ() - d1;
                double d4 = Math.max(d2 * d2 + d3 * d3, 0.1D);
                entity.push(d2 / d4 * 0.4D, (double)0.0F, d3 / d4 * 0.4D);
            }
        }

    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket((Entity) this);
    }

}