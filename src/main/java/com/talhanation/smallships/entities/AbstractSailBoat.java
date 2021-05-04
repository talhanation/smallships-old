package com.talhanation.smallships.entities;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.config.SmallShipsConfig;
import com.talhanation.smallships.init.SoundInit;
import com.talhanation.smallships.network.MessageOpenInv;
import com.talhanation.smallships.network.MessageSailState;
import com.talhanation.smallships.network.MessageSteerState;
import net.minecraft.block.BlockState;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public abstract class AbstractSailBoat extends AbstractInventoryBoat {
    private static final DataParameter<Boolean> IS_LEFT = EntityDataManager.createKey(AbstractSailBoat.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_RIGHT = EntityDataManager.createKey(AbstractSailBoat.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SAIL_STATE = EntityDataManager.createKey(AbstractSailBoat.class, DataSerializers.BOOLEAN);

    public AbstractSailBoat(EntityType<? extends TNBoatEntity> type, World world) {
        super(type, world);
    }

    ////////////////////////////////////TICK////////////////////////////////////

    public void tick() {
        super.tick();

        if (this.canPassengerSteer()) {
            if (this.world.isRemote) {
                this.sendSteerStateToServer();
            }
        }

        if ((this.getControllingPassenger() == null ||!(this.getControllingPassenger() instanceof PlayerEntity) )&& getSailState()) {
            setSailState(false);
        }

        breakLily();

        if (SmallShipsConfig.WaterMobFlee.get()) {
            double radius = 15.0D;
            List<WaterMobEntity> list1 = this.world.getEntitiesWithinAABB(WaterMobEntity.class, new AxisAlignedBB(getPosX() - radius, getPosY() - radius, getPosZ() - radius, getPosX() + radius, getPosY() + radius, getPosZ() + radius));
            for (WaterMobEntity ent : list1)
                fleeEntity(ent);
        }

    }

    public void tickLerp() {
    }

    ////////////////////////////////////REGISTER////////////////////////////////////

    protected void registerData() {
    super.registerData();
        this.dataManager.register(IS_LEFT, false);
        this.dataManager.register(IS_RIGHT, false);
        this.dataManager.register(SAIL_STATE, false);
    }

    ////////////////////////////////////GET////////////////////////////////////

    public boolean getSteerState(int side) {
        return this.dataManager.<Boolean>get(side == 0 ? IS_LEFT : IS_RIGHT) && this.getControllingPassenger() != null;
    }

    public boolean getSailState() {
        return dataManager.get(SAIL_STATE);
    }

    ////////////////////////////////////SET////////////////////////////////////


    public void setSailState(boolean state) {
        if (state != getSailState()) {
            playSailSound(state);
            dataManager.set(SAIL_STATE, state);
        }
    }

    public void setSteerState(boolean left, boolean right){
        this.dataManager.set(IS_LEFT, left);
        this.dataManager.set(IS_RIGHT, right);
    }

    ////////////////////////////////////SERVER////////////////////////////////////

    public void sendSailStateToServer(boolean state) {
        if (world.isRemote) {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageSailState(state));
        }
    }

    public void sendSteerStateToServer(){
        Main.SIMPLE_CHANNEL.sendToServer(new MessageSteerState(this.getSteerState(0), this.getSteerState(1)));
    }

    ////////////////////////////////////SOUND////////////////////////////////////

    public void playSailSound(boolean state) {
        if (state) {
            this.world.playSound(null, this.getPosX(), this.getPosY() + 4 , this.getPosZ(), SoundInit.SHIP_SAIL_0.get(), this.getSoundCategory(), 15.0F, 0.8F + 0.4F * this.rand.nextFloat());
        } else {
            this.world.playSound(null, this.getPosX(), this.getPosY() + 4, this.getPosZ(), SoundInit.SHIP_SAIL_1.get(), this.getSoundCategory(), 10.0F, 0.8F + 0.4F * this.rand.nextFloat());
        }
    }

    ////////////////////////////////////ON FUNCTIONS////////////////////////////////////

    public void onSprintPressed() {
        sendSailStateToServer(!getSailState());
    }

    ////////////////////////////////////OTHER FUNCTIONS////////////////////////////////////

    public float WaveMotion() {
        float wavestr = 2.0F;
        if (world.isRaining()) return 1.5F * wavestr;
        else return wavestr;
    }

    public void fleeEntity(MobEntity entity) {
        double fleeDistance = 10.0D;
        Vector3d vecBoat = new Vector3d(getPosX(), getPosY(), getPosZ());
        Vector3d vecEntity = new Vector3d(entity.getPosX(), entity.getPosY(), entity.getPosZ());
        Vector3d fleeDir = vecEntity.subtract(vecBoat);
        fleeDir = fleeDir.normalize();
        Vector3d fleePos = new Vector3d(vecEntity.x + fleeDir.x * fleeDistance, vecEntity.y + fleeDir.y * fleeDistance, vecEntity.z + fleeDir.z * fleeDistance);
        entity.getNavigator().tryMoveToXYZ(fleePos.x, fleePos.y, fleePos.z, 10.0D);
    }

    private void breakLily() {
        AxisAlignedBB boundingBox = getBoundingBox();
        double offset = 0.75D;
        BlockPos start = new BlockPos(boundingBox.minX - offset, boundingBox.minY - offset, boundingBox.minZ - offset);
        BlockPos end = new BlockPos(boundingBox.maxX + offset, boundingBox.maxY + offset, boundingBox.maxZ + offset);
        BlockPos.Mutable pos = new BlockPos.Mutable();
        boolean hasBroken = false;
        if (world.isAreaLoaded(start, end)) {
            for (int i = start.getX(); i <= end.getX(); ++i) {
                for (int j = start.getY(); j <= end.getY(); ++j) {
                    for (int k = start.getZ(); k <= end.getZ(); ++k) {
                        pos.setPos(i, j, k);
                        BlockState blockstate = world.getBlockState(pos);
                        if (blockstate.getBlock() instanceof LilyPadBlock) {
                            world.destroyBlock(pos, true);
                            hasBroken = true;
                        }
                    }
                }
            }
        }
        if (hasBroken) {
            world.playSound(null, getPosX(), getPosY(), getPosZ(), SoundEvents.BLOCK_CROP_BREAK, SoundCategory.BLOCKS, 1F, 0.9F + 0.2F * rand.nextFloat());
        }
    }

    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket((Entity) this);
    }

}