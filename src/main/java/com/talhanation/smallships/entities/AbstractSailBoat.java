package com.talhanation.smallships.entities;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.blocks.ModBlocks;
import com.talhanation.smallships.config.SmallShipsConfig;
import com.talhanation.smallships.init.SoundInit;
import com.talhanation.smallships.network.MessageIsForward;
import com.talhanation.smallships.network.MessageLantern;
import com.talhanation.smallships.network.MessageSailState;
import com.talhanation.smallships.network.MessageSteerState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IceBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public abstract class AbstractSailBoat extends AbstractInventoryBoat {
    private static final DataParameter<Boolean> IS_LEFT = EntityDataManager.defineId(AbstractSailBoat.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_RIGHT = EntityDataManager.defineId(AbstractSailBoat.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> SAIL_STATE = EntityDataManager.defineId(AbstractSailBoat.class, DataSerializers.INT);
    private static final DataParameter<Integer> LANTERN_COUNT = EntityDataManager.defineId(AbstractSailBoat.class, DataSerializers.INT);
    private static final DataParameter<Boolean> LANTERN_LIT = EntityDataManager.defineId(AbstractSailBoat.class, DataSerializers.BOOLEAN);
    private static final DataParameter<String>  SAIL_COLOR = EntityDataManager.defineId(AbstractSailBoat.class, DataSerializers.STRING);
    private static final DataParameter<Boolean> IS_FORWARD = EntityDataManager.defineId(AbstractSailBoat.class, DataSerializers.BOOLEAN);

    public AbstractSailBoat(EntityType<? extends TNBoatEntity> type, World world) {
        super(type, world);
    }
    private double waterLevel;
    private float steerRotation;
    private BlockPos newlightBlock;
    private BlockPos oldlightBlock;

    ////////////////////////////////////TICK////////////////////////////////////

    @Override
    public void tick() {
        super.tick();
        lanternLight();
        if (getIsForward()){
            if (this.checkInWater())
                Watersplash();
        }

        if (this.isControlledByLocalInstance()) {
            if (this.level.isClientSide) {
                this.sendSteerStateToServer();
                this.sendIsForwardToServer();
            }
        }


        if ((this.getControllingPassenger() == null ||!(this.getControllingPassenger() instanceof PlayerEntity) )) {
            setSailState(0);
            setIsForward(false);
        }


        if (SmallShipsConfig.WaterMobFlee.get()) {
            double radius = 15.0D;
            List<WaterMobEntity> list1 = this.level.getEntitiesOfClass(WaterMobEntity.class, new AxisAlignedBB(getX() - radius, getY() - radius, getZ() - radius, getX() + radius, getY() + radius, getZ() + radius));
            for (WaterMobEntity ent : list1)
                fleeEntity(ent);
        }


        if (this.getIsForward()) {
            this.knockBack(this.level.getEntities(this, this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D).move(0.0D, -2.0D, 0.0D), EntityPredicates.NO_CREATIVE_OR_SPECTATOR));
            this.knockBack(this.level.getEntities(this, this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D).move(0.0D, -2.0D, 0.0D), EntityPredicates.NO_CREATIVE_OR_SPECTATOR));
        }

        if (this.getSteerState(0)) steerRotation += getSteerRotationAmount();
        else if (this.getSteerState(1)) steerRotation -= getSteerRotationAmount();
        else steerRotation = getSteerRotationAmount();
    }

    public void tickLerp(){}

    ////////////////////////////////////REGISTER////////////////////////////////////

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_LEFT, false);
        this.entityData.define(IS_RIGHT, false);
        this.entityData.define(IS_FORWARD, false);
        this.entityData.define(SAIL_STATE, 0);
        this.entityData.define(LANTERN_LIT, false);
        this.entityData.define(LANTERN_COUNT, 0);
        this.entityData.define(SAIL_COLOR, "white");
    }
    ////////////////////////////////////SAVE DATA////////////////////////////////////

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putString("SailColor", getSailColor());
        nbt.putBoolean("LanternLit", getLanternState());
        nbt.putInt("LanternCount", getLanternCount());
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        this.setSailColor(nbt.getString("SailColor"));
        this.setLanternState(nbt.getBoolean("LanternLit"));
        this.setLanternCount(nbt.getInt("LanternCount"));
    }

    ////////////////////////////////////GET////////////////////////////////////

    public int getMaxLanternCount(){
        return 2;
    }

    public int getLanternCount() {
        return this.entityData.get(LANTERN_COUNT);
    }

    public Boolean getLanternState() {
        return this.entityData.get(LANTERN_LIT);
    }

    public String getSailColor() {
        return this.entityData.get(SAIL_COLOR);
    }

    public float getSteerRotation(float partialTicks) {
        return steerRotation + getSteerRotationAmount() * partialTicks;
    }

    public float getSteerRotationAmount() {
        return 180F ;
    }

    public boolean getSteerState(int side) {
        return this.entityData.<Boolean>get(side == 0 ? IS_LEFT : IS_RIGHT) && this.getControllingPassenger() != null;
    }

    public Integer getSailState() {
        return entityData.get(SAIL_STATE);
    }

    public boolean getIsForward() {
        return entityData.get(IS_FORWARD);
    }

    ////////////////////////////////////SET////////////////////////////////////

    public void setLanternCount(int count) {
        entityData.set(LANTERN_COUNT, count);
    }

    public void setLanternState(boolean bool) {
        entityData.set(LANTERN_LIT, !bool);
    }

    public void setSailColor(String color) {
        entityData.set(SAIL_COLOR, color);
    }

    public void setSailState(int state) {
        if (state != getSailState()) {
            playSailSound(state);
            entityData.set(SAIL_STATE, state);
        }
    }

    public void setSteerState(boolean left, boolean right){
        this.entityData.set(IS_LEFT, left);
        this.entityData.set(IS_RIGHT, right);
    }

    public void setIsForward(boolean forward){
        this.entityData.set(IS_FORWARD, forward);
        if(getSailState() != 0)
            this.entityData.set(IS_FORWARD,true);
    }

    ////////////////////////////////////SERVER////////////////////////////////////


    public void sendSailStateToServer(int state) {
        if (level.isClientSide) {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageSailState(state));
        }
    }

    public void sendSteerStateToServer(){
        Main.SIMPLE_CHANNEL.sendToServer(new MessageSteerState(this.getSteerState(0), this.getSteerState(1)));
    }

    public void sendIsForwardToServer(){
        Main.SIMPLE_CHANNEL.sendToServer(new MessageIsForward(this.getIsForward()));
    }

    public void sendLanternToServer(boolean isLit){
        Main.SIMPLE_CHANNEL.sendToServer(new MessageLantern(isLit));
    }

    ////////////////////////////////////SOUND////////////////////////////////////

    public void playSailSound(int state) {
        if (state != 0) {
            this.level.playSound(null, this.getX(), this.getY() + 4 , this.getZ(), SoundInit.SHIP_SAIL_0.get(), this.getSoundSource(), 15.0F, 0.8F + 0.4F * this.random.nextFloat());
        } else {
            this.level.playSound(null, this.getX(), this.getY() + 4, this.getZ(), SoundInit.SHIP_SAIL_1.get(), this.getSoundSource(), 10.0F, 0.8F + 0.4F * this.random.nextFloat());
        }
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

    @Override
    public void onLanternPressed() {
        boolean isLit = getLanternState();
        sendLanternToServer(isLit);
    }

    public void onInteractionWithDye(PlayerEntity player, DyeColor dyeColor, ItemStack itemStack) {
        String color = dyeColor.getName();
        setSailColor(color);
        if (!player.isCreative()) {
            itemStack.shrink(1);
        }
    }

    public void onInteractionWithLantern(PlayerEntity player,ItemStack itemStack) {
        int count = this.getLanternCount();
        count++;
        setLanternCount(count);
        if (!player.isCreative()) {
            itemStack.shrink(1);
        }
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

    private boolean checkInWater() {
        AxisAlignedBB axisalignedbb = this.getBoundingBox();
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.ceil(axisalignedbb.maxX);
        int k = MathHelper.floor(axisalignedbb.minY);
        int l = MathHelper.ceil(axisalignedbb.minY + 0.001D);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.ceil(axisalignedbb.maxZ);
        boolean flag = false;
        this.waterLevel = Double.MIN_VALUE;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for (int k1 = i; k1 < j; ++k1) {
            for (int l1 = k; l1 < l; ++l1) {
                for (int i2 = i1; i2 < j1; ++i2) {
                    blockpos$mutable.set(k1, l1, i2);
                    FluidState fluidstate = this.level.getFluidState(blockpos$mutable);
                    if (fluidstate.is(FluidTags.WATER)) {
                        float f = (float) l1 + fluidstate.getHeight(this.level, blockpos$mutable);
                        this.waterLevel = Math.max((double) f, this.waterLevel);
                        flag |= axisalignedbb.minY < (double) f;
                    }
                }
            }
        }

        return flag;
    }

    public boolean hasLantern(){
        return getLanternCount() != 0;
    }

    private void lanternLight(){
        if(hasLantern()){

            BlockPos onPos = this.getOnPos();
            this.newlightBlock = new BlockPos(onPos.getX(), onPos.getY() + 2, onPos.getZ());
            BlockState newLightPosBlockState = this.level.getBlockState(newlightBlock);
            Block newLightPosBlock = newLightPosBlockState.getBlock();

            if (getLanternState()) {

                if (newLightPosBlock == Blocks.AIR && newLightPosBlock != ModBlocks.LANTERN_LIGHT.get()) {
                        this.level.setBlock(this.newlightBlock, ModBlocks.LANTERN_LIGHT.get().defaultBlockState(), 3);
                        removeOldLanternLight(oldlightBlock, newlightBlock);
                        oldlightBlock = newlightBlock;
                }

            }

            if (!getLanternState()) {
                if (oldlightBlock != null) {
                    BlockState oldLightPosBlockState = this.level.getBlockState(oldlightBlock);
                    Block oldLightPosBlock = oldLightPosBlockState.getBlock();

                    if (oldLightPosBlock == ModBlocks.LANTERN_LIGHT.get()) {
                        this.level.removeBlock(oldlightBlock, false);
                    }
                }

                if (newLightPosBlock == ModBlocks.LANTERN_LIGHT.get()) {
                    this.level.removeBlock(newlightBlock, false);
                }

            }
        }

    }


    private void removeOldLanternLight(BlockPos oldPos, BlockPos newPos){
        if (oldPos != newPos && oldPos != null){
            this.level.removeBlock(oldPos, false);
        }
    }


    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket((Entity) this);
    }

}