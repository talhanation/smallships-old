package com.talhanation.smallships.entities;

import com.google.common.collect.Lists;
import com.talhanation.smallships.Main;
import com.talhanation.smallships.compatiblity.BiomesOPlenty;
import com.talhanation.smallships.compatiblity.Environmental;
import com.talhanation.smallships.compatiblity.LordOfTheRingsMod;
import com.talhanation.smallships.network.MessagePaddleState;
import net.minecraft.BlockUtil;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public abstract class TNBoatEntity extends Entity {
    private static final EntityDataAccessor<Integer> TIME_SINCE_HIT = SynchedEntityData.defineId(TNBoatEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> FORWARD_DIRECTION = SynchedEntityData.defineId(TNBoatEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DAMAGE_TAKEN = SynchedEntityData.defineId(TNBoatEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> BOAT_TYPE = SynchedEntityData.defineId(TNBoatEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> LEFT_PADDLE = SynchedEntityData.defineId(TNBoatEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RIGHT_PADDLE = SynchedEntityData.defineId(TNBoatEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ROCKING_TICKS = SynchedEntityData.defineId(TNBoatEntity.class, EntityDataSerializers.INT);
    private final float[] paddlePositions = new float[2];
    private final List<Entity> passengers = Lists.newArrayList();
    private float momentum;
    private float outOfControlTicks;
    private float deltaRotation;
    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYaw;
    private double lerpPitch;
    public boolean leftInputDown;
    public boolean rightInputDown;
    public boolean forwardInputDown;
    public boolean backInputDown;
    private double waterLevel;
    private float boatGlide;
    private TNBoatEntity.Status status;
    private TNBoatEntity.Status previousStatus;
    private double lastYd;
    private boolean rocking;
    private boolean downwards;
    private float rockingIntensity;
    private float rockingAngle;
    private float prevRockingAngle;
    private float waveAngle;
    private float prevWaveAngle;

    public TNBoatEntity(EntityType<? extends TNBoatEntity> type, Level world) {
        super(type, world);
        this.blocksBuilding = true;
    }


    public void Watersplash() {

    }

    public void onKeyPressed() {

    }
    public void onKeyLowerPressed() {

    }
    public void onKeyHigherPressed() {

    }

    public void onInvPressed(Player player) {

    }



    @Override
    protected float getEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return sizeIn.height;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(TIME_SINCE_HIT, 0);
        this.entityData.define(FORWARD_DIRECTION, 1);
        this.entityData.define(DAMAGE_TAKEN, 0.0F);
        this.entityData.define(BOAT_TYPE, TNBoatEntity.Type.OAK.ordinal());
        this.entityData.define(LEFT_PADDLE, false);
        this.entityData.define(RIGHT_PADDLE, false);
        this.entityData.define(ROCKING_TICKS, 0);
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return canVehicleCollide(this, entity);
    }

    public static boolean canVehicleCollide(Entity entity0, Entity entity) {
        return (entity.canBeCollidedWith() || entity.isPushable()) && !entity0.isPassengerOfSameVehicle(entity);
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected Vec3 getRelativePortalPosition(Direction.Axis axis, BlockUtil.FoundRectangle result) {
        return LivingEntity.resetForwardDirectionOfRelativePortalPosition(super.getRelativePortalPosition(axis, result));
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    @Override
    public double getPassengersRidingOffset() {
        return -0.1D;
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        return super.hurt(source, amount);
    }

    @Override
    public void onAboveBubbleCol(boolean downwards) {
        if (!this.level.isClientSide) {
            this.rocking = true;
            this.downwards = downwards;
            if (this.getRockingTicks() == 0) {
                this.setRockingTicks(60);
            }
        }

        this.level.addParticle(ParticleTypes.SPLASH, this.getX() + (double) this.random.nextFloat(), this.getY() + 0.7D, this.getZ() + (double) this.random.nextFloat(), 0.0D, 0.0D, 0.0D);
        if (this.random.nextInt(20) == 0) {
            this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), this.getSwimSplashSound(), this.getSoundSource(), 1.0F, 0.8F + 0.4F * this.random.nextFloat(), false);
        }

    }

    /**
     * Applies a velocity to the entities, to push them away from eachother.
     */
    @Override
    public void push(Entity entityIn) {
        if (entityIn instanceof Boat || entityIn instanceof TNBoatEntity) {
            if (entityIn.getBoundingBox().minY < this.getBoundingBox().maxY) {
                super.push(entityIn);
            }
        } else if (entityIn.getBoundingBox().minY <= this.getBoundingBox().minY) {
            super.push(entityIn);
        }

    }

    public Item getItemBoat() {
        switch (this.getBoatType()) {
            case OAK:
            default:
                return Items.OAK_BOAT;
            case SPRUCE:
                return Items.SPRUCE_BOAT;
            case BIRCH:
                return Items.BIRCH_BOAT;
            case JUNGLE:
                return Items.JUNGLE_BOAT;
            case ACACIA:
                return Items.ACACIA_BOAT;
            case DARK_OAK:
                return Items.DARK_OAK_BOAT;

            ///////////////BOP//////////////
            case BOP_CHERRY:
                return BiomesOPlenty.CHERRY_BOAT;
            case BOP_DEAD:
                return BiomesOPlenty.DEAD_BOAT;
            case BOP_FIR:
                return BiomesOPlenty.FIR_BOAT;
            case BOP_HELLBARK:
                return BiomesOPlenty.HELLBARK_BOAT;
            case BOP_JACARANDA:
                return BiomesOPlenty.JACARANDA_BOAT;
            case BOP_MAGIC:
                return BiomesOPlenty.MAGIC_BOAT;
            case BOP_MAHOGANY:
                return BiomesOPlenty.MAHOGANY_BOAT;
            case BOP_PALM:
                return BiomesOPlenty.PALM_BOAT;
            case BOP_REDWOOD:
                return BiomesOPlenty.REDWOOD_BOAT;
            case BOP_UMBRAN:
                return BiomesOPlenty.UMBRAN_BOAT;
            case BOP_WILLOW:
                return BiomesOPlenty.WILLOW_BOAT;

            ///////////////LOTR//////////////////

            case LOTR_APPLE:
                return LordOfTheRingsMod.APPLE_BOAT;
            case LOTR_ASPEN:
                return LordOfTheRingsMod.ASPEN_BOAT;
            case LOTR_BEECH:
                return LordOfTheRingsMod.BEECH_BOAT;
            case LOTR_CEDAR:
                return LordOfTheRingsMod.CEDAR_BOAT;
            case LOTR_CHARRED:
                return LordOfTheRingsMod.CHARRED_BOAT;
            case LOTR_CHERRY:
                return LordOfTheRingsMod.CHERRY_BOAT;
            case LOTR_CYPRESS:
                return LordOfTheRingsMod.CYPRESS_BOAT;
            case LOTR_FIR:
                return LordOfTheRingsMod.FIR_BOAT;
            case LOTR_GREEN_OAK:
                return LordOfTheRingsMod.GREEN_OAK_BOAT;
            case LOTR_HOLLY:
                return LordOfTheRingsMod.HOLLY_BOAT;
            case LOTR_LAIRELOSSE:
                return LordOfTheRingsMod.LAIRELOSSE_BOAT;
            case LOTR_LARCH:
                return LordOfTheRingsMod.LARCH_BOAT;
            case LOTR_LEBETHRON:
                return LordOfTheRingsMod.LEBETHRON_BOAT;
            case LOTR_MALLORN:
                return LordOfTheRingsMod.MALLORN_BOAT;
            case LOTR_MAPLE:
                return LordOfTheRingsMod.MAPLE_BOAT;
            case LOTR_MIRK_OAK:
                return LordOfTheRingsMod.MIRK_OAK_BOAT;
            case LOTR_PEAR:
                return LordOfTheRingsMod.PEAR_BOAT;
            case LOTR_PINE:
                return LordOfTheRingsMod.PINE_BOAT;
            case LOTR_ROTTEN:
                return LordOfTheRingsMod.ROTTEN_BOAT;

            ///////////////ENVI//////////////////

            case ENVI_CHERRY:
                return Environmental.CHERRY_BOAT;
            case ENVI_WILLOW:
                return Environmental.WILLOW_BOAT;
            case ENVI_WISTERIA:
                return Environmental.WISTERIA_BOAT;
        }
    }

    /**
     * Setups the entity to do the hurt animation. Only used by packets in multiplayer.
     */
    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateHurt() {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0F);
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    @Override
    public boolean canBeCollidedWith() {
        return !this.isRemoved();
    }

    /**
     * Sets a target for the client to interpolate towards over the next few ticks
     */
    @OnlyIn(Dist.CLIENT)
    @Override
    public void lerpTo(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpYaw = (double) yaw;
        this.lerpPitch = (double) pitch;
        this.lerpSteps = 10;
    }

    /**
     * Gets the horizontal facing direction of this Entity, adjusted to take specially-treated entity types into account.
     */
    @Override
    public Direction getMotionDirection() {
        return this.getDirection().getClockWise();
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        this.previousStatus = this.status;
        this.status = this.getBoatStatus();
        if (this.status != TNBoatEntity.Status.UNDER_WATER && this.status != TNBoatEntity.Status.UNDER_FLOWING_WATER) {
            this.outOfControlTicks = 0.0F;
        } else {
            ++this.outOfControlTicks;
        }

        if (!this.level.isClientSide && this.outOfControlTicks >= 60.0F) {
            this.ejectPassengers();
        }

        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }

        if (this.getDamageTaken() > 0.0F) {
            this.setDamageTaken(this.getDamageTaken() - 1.0F);
        }
        super.tick();
        this.tickLerp();
        if (this.isControlledByLocalInstance()) {
            if (this.getPassengers().isEmpty() || !(this.getPassengers().get(0) instanceof Player)) {
                this.setPaddleState(false, false);
            }

            this.floatBoat();
            if (this.level.isClientSide) {
                this.controlBoat();
                Main.SIMPLE_CHANNEL.sendToServer(new MessagePaddleState(this.getPaddleState(0), this.getPaddleState(1)));
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
        } else {
            this.setDeltaMovement(Vec3.ZERO);
        }

        this.tickBubbleColumn();

        for (int i = 0; i <= 1; ++i) {
            if (this.getPaddleState(i)) {
                if (!this.isSilent() && (double) (this.paddlePositions[i] % ((float) Math.PI * 2F)) <= (double) ((float) Math.PI / 4F) && ((double) this.paddlePositions[i] + (double) ((float) Math.PI / 8F)) % (double) ((float) Math.PI * 2F) >= (double) ((float) Math.PI / 4F)) {
                    SoundEvent soundevent = this.getPaddleSound();
                    if (soundevent != null) {
                        Vec3 vector3d = this.getViewVector(1.0F);
                        double d0 = i == 1 ? -vector3d.z : vector3d.z;
                        double d1 = i == 1 ? vector3d.x : -vector3d.x;
                        this.level.playSound((Player) null, this.getX() + d0, this.getY(), this.getZ() + d1, soundevent, this.getSoundSource(), 1.0F, 0.8F + 0.4F * this.random.nextFloat());
                    }
                }

                this.paddlePositions[i] = (float) ((double) this.paddlePositions[i] + (double) ((float) Math.PI / 8F));
            } else {
                this.paddlePositions[i] = 0.0F;
            }
        }

        this.checkInsideBlocks();
        List<Entity> list = this.level.getEntities(this, this.getBoundingBox().inflate((double) 0.2F, (double) -0.01F, (double) 0.2F), EntitySelector.pushableBy(this));
        if (!list.isEmpty()) {
            boolean flag = !this.level.isClientSide && !(this.getControllingPassenger() instanceof Player);

            for (int j = 0; j < list.size(); ++j) {
                Entity entity = list.get(j);
                if (!entity.hasPassenger(this)) {
                    if (flag && this.getPassengers().size() < 2 && !entity.isPassenger() && entity.getBbWidth() < this.getBbWidth() && entity instanceof LivingEntity && !(entity instanceof WaterAnimal) && !(entity instanceof Player)) {
                        entity.startRiding(this);
                    } else {
                        this.push(entity);
                    }
                }
            }
        }

        if (level.isClientSide) {
            //updateClientControls();
        }

    }

    public float getWaveFactor() {
        return level.isRaining() ? 3F : 1.125F;
    }

    public float getWaveSpeed() {
        return level.isRaining() ? 0.15F : 0.06F;
    }

    public float getWaveAngle(float partialTicks) {
        return Mth.lerp(partialTicks, this.prevWaveAngle, this.waveAngle);
    }

    private void tickBubbleColumn() {
        if (this.level.isClientSide) {
            int i = this.getRockingTicks();
            if (i > 0) {
                this.rockingIntensity += 0.05F;
            } else {
                this.rockingIntensity -= 0.1F;
            }

            this.rockingIntensity = Mth.clamp(this.rockingIntensity, 0.0F, 1.0F);
            this.prevRockingAngle = this.rockingAngle;
            this.rockingAngle = 10.0F * (float) Math.sin((double) (0.5F * (float) this.level.getGameTime())) * this.rockingIntensity;

            this.prevWaveAngle = this.waveAngle;
            this.waveAngle = (float) Math.sin(getWaveSpeed() * (float) tickCount) * getWaveFactor();
        } else {
            if (!this.rocking) {
                this.setRockingTicks(0);
            }

            int k = this.getRockingTicks();
            if (k > 0) {
                --k;
                this.setRockingTicks(k);
                int j = 60 - k - 1;
                if (j > 0 && k == 0) {
                    this.setRockingTicks(0);
                    Vec3 vector3d = this.getDeltaMovement();
                    if (this.downwards) {
                        this.setDeltaMovement(vector3d.add(0.0D, -0.7D, 0.0D));
                        this.ejectPassengers();
                    } else {
                        this.setDeltaMovement(vector3d.x, this.hasPassenger((p_150274_) -> {
                            return p_150274_ instanceof Player;}) ? 2.7D : 0.6D, vector3d.z);
                    }
                }

                this.rocking = false;
            }
        }

    }

    @Nullable
    protected SoundEvent getPaddleSound() {
        switch (this.getBoatStatus()) {
            case IN_WATER:
            case UNDER_WATER:
            case UNDER_FLOWING_WATER:
                return SoundEvents.BOAT_PADDLE_WATER;
            case ON_LAND:
                return SoundEvents.BOAT_PADDLE_LAND;
            case IN_AIR:
            default:
                return null;
        }
    }

    private void tickLerp() {
        if (this.isControlledByLocalInstance()) {
            this.lerpSteps = 0;
            this.setPacketCoordinates(this.getX(), this.getY(), this.getZ());
        }

        if (this.lerpSteps > 0) {
            double d0 = this.getX() + (this.lerpX - this.getX()) / (double)this.lerpSteps;
            double d1 = this.getY() + (this.lerpY - this.getY()) / (double)this.lerpSteps;
            double d2 = this.getZ() + (this.lerpZ - this.getZ()) / (double)this.lerpSteps;
            double d3 = Mth.wrapDegrees(this.lerpYaw - (double)this.getYRot());
            this.setYRot(this.getYRot() + (float)d3 / (float)this.lerpSteps);
            this.setXRot(this.getXRot() + (float)(this.lerpPitch - (double)this.getXRot()) / (float)this.lerpSteps);
            --this.lerpSteps;
            this.setPos(d0, d1, d2);
            this.setRot(this.getYRot(), this.getXRot());
        }
    }

    public void setPaddleState(boolean left, boolean right) {
        this.entityData.set(LEFT_PADDLE, left);
        this.entityData.set(RIGHT_PADDLE, right);
    }

    @OnlyIn(Dist.CLIENT)
    public float getRowingTime(int side, float limbSwing) {
        return this.getPaddleState(side) ? (float) Mth.clampedLerp((double) this.paddlePositions[side] - (double) ((float) Math.PI / 8F), (double) this.paddlePositions[side], (double) limbSwing) : 0.0F;
    }

    /**
     * Determines whether the boat is in water, gliding on land, or in air
     */
    private TNBoatEntity.Status getBoatStatus() {
        TNBoatEntity.Status boatentity$status = this.getUnderwaterStatus();
        if (boatentity$status != null) {
            this.waterLevel = this.getBoundingBox().maxY;
            return boatentity$status;
        } else if (this.checkInWater()) {
            return TNBoatEntity.Status.IN_WATER;
        } else {
            float f = this.getBoatGlide();
            if (f > 0.0F) {
                this.boatGlide = f;
                return TNBoatEntity.Status.ON_LAND;
            } else {
                return TNBoatEntity.Status.IN_AIR;
            }
        }
    }

    public float getWaterLevelAbove() {
        AABB axisalignedbb = this.getBoundingBox();
        int i = Mth.floor(axisalignedbb.minX);
        int j = Mth.ceil(axisalignedbb.maxX);
        int k = Mth.floor(axisalignedbb.maxY);
        int l = Mth.ceil(axisalignedbb.maxY - this.lastYd);
        int i1 = Mth.floor(axisalignedbb.minZ);
        int j1 = Mth.ceil(axisalignedbb.maxZ);
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

        label39:
        for (int k1 = k; k1 < l; ++k1) {
            float f = 0.0F;

            for (int l1 = i; l1 < j; ++l1) {
                for (int i2 = i1; i2 < j1; ++i2) {
                    blockpos$mutable.set(l1, k1, i2);
                    FluidState fluidstate = this.level.getFluidState(blockpos$mutable);
                    if (fluidstate.is(FluidTags.WATER)) {
                        f = Math.max(f, fluidstate.getHeight(this.level, blockpos$mutable));
                    }

                    if (f >= 1.0F) {
                        continue label39;
                    }
                }
            }

            if (f < 1.0F) {
                return (float) blockpos$mutable.getY() + f;
            }
        }

        return (float) (l + 1);
    }

    /**
     * Decides how much the boat should be gliding on the land (based on any slippery blocks)
     */
    public float getBoatGlide() {
        AABB axisalignedbb = this.getBoundingBox();
        AABB axisalignedbb1 = new AABB(axisalignedbb.minX, axisalignedbb.minY - 0.001D, axisalignedbb.minZ, axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        int i = Mth.floor(axisalignedbb1.minX) - 1;
        int j = Mth.ceil(axisalignedbb1.maxX) + 1;
        int k = Mth.floor(axisalignedbb1.minY) - 1;
        int l = Mth.ceil(axisalignedbb1.maxY) + 1;
        int i1 = Mth.floor(axisalignedbb1.minZ) - 1;
        int j1 = Mth.ceil(axisalignedbb1.maxZ) + 1;
        VoxelShape voxelshape = Shapes.create(axisalignedbb1);
        float f = 0.0F;
        int k1 = 0;
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

        for (int l1 = i; l1 < j; ++l1) {
            for (int i2 = i1; i2 < j1; ++i2) {
                int j2 = (l1 != i && l1 != j - 1 ? 0 : 1) + (i2 != i1 && i2 != j1 - 1 ? 0 : 1);
                if (j2 != 2) {
                    for (int k2 = k; k2 < l; ++k2) {
                        if (j2 <= 0 || k2 != k && k2 != l - 1) {
                            blockpos$mutable.set(l1, k2, i2);
                            BlockState blockstate = this.level.getBlockState(blockpos$mutable);
                            if (!(blockstate.getBlock() instanceof WaterlilyBlock) && Shapes.joinIsNotEmpty(blockstate.getCollisionShape(this.level, blockpos$mutable).move((double) l1, (double) k2, (double) i2), voxelshape, BooleanOp.AND)) {
                                f += blockstate.getFriction(this.level, blockpos$mutable, this);
                                ++k1;
                            }
                        }
                    }
                }
            }
        }

        return f / (float) k1;
    }

    private boolean checkInWater() {
        AABB axisalignedbb = this.getBoundingBox();
        int i = Mth.floor(axisalignedbb.minX);
        int j = Mth.ceil(axisalignedbb.maxX);
        int k = Mth.floor(axisalignedbb.minY);
        int l = Mth.ceil(axisalignedbb.minY + 0.001D);
        int i1 = Mth.floor(axisalignedbb.minZ);
        int j1 = Mth.ceil(axisalignedbb.maxZ);
        boolean flag = false;
        this.waterLevel = Double.MIN_VALUE;
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

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

    /**
     * Decides whether the boat is currently underwater.
     */
    @Nullable
    private TNBoatEntity.Status getUnderwaterStatus() {
        AABB axisalignedbb = this.getBoundingBox();
        double d0 = axisalignedbb.maxY + 0.001D;
        int i = Mth.floor(axisalignedbb.minX);
        int j = Mth.ceil(axisalignedbb.maxX);
        int k = Mth.floor(axisalignedbb.maxY);
        int l = Mth.ceil(d0);
        int i1 = Mth.floor(axisalignedbb.minZ);
        int j1 = Mth.ceil(axisalignedbb.maxZ);
        boolean flag = false;
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

        for (int k1 = i; k1 < j; ++k1) {
            for (int l1 = k; l1 < l; ++l1) {
                for (int i2 = i1; i2 < j1; ++i2) {
                    blockpos$mutable.set(k1, l1, i2);
                    FluidState fluidstate = this.level.getFluidState(blockpos$mutable);
                    if (fluidstate.is(FluidTags.WATER) && d0 < (double) ((float) blockpos$mutable.getY() + fluidstate.getHeight(this.level, blockpos$mutable))) {
                        if (!fluidstate.isSource()) {
                            return TNBoatEntity.Status.UNDER_FLOWING_WATER;
                        }

                        flag = true;
                    }
                }
            }
        }

        return flag ? TNBoatEntity.Status.UNDER_WATER : null;
    }

    /**
     * Update the boat's speed, based on momentum.
     */
    private void floatBoat() {
        double d0 = (double) -0.04F;
        double d1 = this.isNoGravity() ? 0.0D : (double) -0.04F;
        double d2 = 0.0D;
        this.momentum = 0.05F;
        if (this.previousStatus == TNBoatEntity.Status.IN_AIR && this.status != TNBoatEntity.Status.IN_AIR && this.status != TNBoatEntity.Status.ON_LAND) {
            this.waterLevel = this.getY(1.0D);
            this.setPos(this.getX(), (double) (this.getWaterLevelAbove() - this.getBbHeight()) + 0.101D, this.getZ());
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D));
            this.lastYd = 0.0D;
            this.status = TNBoatEntity.Status.IN_WATER;
        } else {
            if (this.status == TNBoatEntity.Status.IN_WATER) {
                d2 = (this.waterLevel - this.getY()) / (double) this.getBbHeight();
                this.momentum = 0.9F;
            } else if (this.status == TNBoatEntity.Status.UNDER_FLOWING_WATER) {
                d1 = -7.0E-4D;
                this.momentum = 0.9F;
            } else if (this.status == TNBoatEntity.Status.UNDER_WATER) {
                d2 = (double) 0.01F;
                this.momentum = 0.45F;
            } else if (this.status == TNBoatEntity.Status.IN_AIR) {
                this.momentum = 0.9F;
            } else if (this.status == TNBoatEntity.Status.ON_LAND) {
                this.momentum = this.boatGlide;
                if (this.getControllingPassenger() instanceof Player) {
                    this.boatGlide /= 2.0F;
                }
            }

            Vec3 vector3d = this.getDeltaMovement();
            this.setDeltaMovement(vector3d.x * (double) this.momentum, vector3d.y + d1, vector3d.z * (double) this.momentum);
            this.deltaRotation *= this.momentum;
            if (d2 > 0.0D) {
                Vec3 vector3d1 = this.getDeltaMovement();
                this.setDeltaMovement(vector3d1.x, (vector3d1.y + d2 * 0.06153846016296973D) * 0.75D, vector3d1.z);
            }
        }

    }

    private void controlBoat() {
        if (this.isVehicle()) {
            float f = 0.0F;
            if (this.leftInputDown) {
                --this.deltaRotation;
            }

            if (this.rightInputDown) {
                ++this.deltaRotation;
            }

            if (this.rightInputDown != this.leftInputDown && !this.forwardInputDown && !this.backInputDown) {
                f += 0.005F;
            }

            this.setYRot(this.getYRot() + this.deltaRotation);
            if (this.forwardInputDown) {
                f += 0.04F;
            }

            if (this.backInputDown) {
                f -= 0.005F;
            }

            this.setDeltaMovement(this.getDeltaMovement().add((double) (Mth.sin(-this.getYRot() * ((float) Math.PI / 180F)) * f), 0.0D, (double) (Mth.cos(this.getYRot() * ((float) Math.PI / 180F)) * f)));
            this.setPaddleState(this.rightInputDown && !this.leftInputDown || this.forwardInputDown, this.leftInputDown && !this.rightInputDown || this.forwardInputDown);
        }
    }

    @Override
    public void positionRider(Entity p_38379_) {
        if (this.hasPassenger(p_38379_)) {
            float f = 0.0F;
            float f1 = (float)((this.isRemoved() ? (double)0.01F : this.getPassengersRidingOffset()) + p_38379_.getMyRidingOffset());
            if (this.getPassengers().size() > 1) {
                int i = this.getPassengers().indexOf(p_38379_);
                if (i == 0) {
                    f = 0.2F;
                } else {
                    f = -0.6F;
                }

                if (p_38379_ instanceof Animal) {
                    f = (float)((double)f + 0.2D);
                }
            }

            Vec3 vec3 = (new Vec3((double)f, 0.0D, 0.0D)).yRot(-this.getYRot() * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
            p_38379_.setPos(this.getX() + vec3.x, this.getY() + (double)f1, this.getZ() + vec3.z);
            p_38379_.setYRot(p_38379_.getYRot() + this.deltaRotation);
            p_38379_.setYHeadRot(p_38379_.getYHeadRot() + this.deltaRotation);
            this.clampRotation(p_38379_);
            if (p_38379_ instanceof Animal && this.getPassengers().size() > 1) {
                int j = p_38379_.getId() % 2 == 0 ? 90 : 270;
                p_38379_.setYBodyRot(((Animal)p_38379_).yBodyRot + (float)j);
                p_38379_.setYHeadRot(p_38379_.getYHeadRot() + (float)j);
            }

        }
    }

    /**
     * Applies this boat's yaw to the given entity. Used to update the orientation of its passenger.
     */
    protected void clampRotation(Entity p_38322_) {
        p_38322_.setYBodyRot(this.getYRot());
        float f = Mth.wrapDegrees(p_38322_.getYRot() - this.getYRot());
        float f1 = Mth.clamp(f, -105.0F, 105.0F);
        p_38322_.yRotO += f1 - f;
        p_38322_.setYRot(p_38322_.getYRot() + f1 - f);
        p_38322_.setYHeadRot(p_38322_.getYRot());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("Type", 8)) {
            this.setBoatType(TNBoatEntity.Type.getTypeFromString(compound.getString("Type")));
        }

    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (player.isSecondaryUseActive()) {
            return InteractionResult.PASS;
        } else if (this.outOfControlTicks < 60.0F) {
            if (!this.level.isClientSide) {
                return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
            } else {
                return InteractionResult.SUCCESS;
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
        this.lastYd = this.getDeltaMovement().y;
        if (!this.isPassenger()) {
            if (onGroundIn) {
                if (this.fallDistance > 3.0F) {
                    if (this.status != TNBoatEntity.Status.ON_LAND) {
                        this.fallDistance = 0.0F;
                        return;
                    }

                    this.causeFallDamage(this.fallDistance, 1.0F, DamageSource.FALL);
                    if (!this.level.isClientSide && !this.isRemoved()) {
                        this.kill();
                        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            for (int i = 0; i < 3; ++i) {
                                //this.entityDropItem(this.getBoatType().asPlank());
                            }

                            for (int j = 0; j < 2; ++j) {
                                this.spawnAtLocation(Items.STICK);
                            }
                        }
                    }
                }

                this.fallDistance = 0.0F;
            } else if (!this.level.getFluidState(this.blockPosition().below()).is(FluidTags.WATER) && y < 0.0D) {
                this.fallDistance = (float) ((double) this.fallDistance - y);
            }

        }
    }

    public boolean getPaddleState(int side) {
        return this.entityData.<Boolean>get(side == 0 ? LEFT_PADDLE : RIGHT_PADDLE) && this.getControllingPassenger() != null;
    }

    /**
     * Sets the damage taken from the last hit.
     */
    public void setDamageTaken(float damageTaken) {
        this.entityData.set(DAMAGE_TAKEN, damageTaken);
    }

    /**
     * Gets the damage taken from the last hit.
     */
    public float getDamageTaken() {
        return this.entityData.get(DAMAGE_TAKEN);
    }

    /**
     * Sets the time to count down from since the last time entity was hit.
     */
    public void setTimeSinceHit(int timeSinceHit) {
        this.entityData.set(TIME_SINCE_HIT, timeSinceHit);
    }

    /**
     * Gets the time since the last hit.
     */
    public int getTimeSinceHit() {
        return this.entityData.get(TIME_SINCE_HIT);
    }

    private void setRockingTicks(int ticks) {
        this.entityData.set(ROCKING_TICKS, ticks);
    }

    private int getRockingTicks() {
        return this.entityData.get(ROCKING_TICKS);
    }

    @OnlyIn(Dist.CLIENT)
    public float getRockingAngle(float partialTicks) {
        return Mth.lerp(partialTicks, this.prevRockingAngle, this.rockingAngle);
    }

    /**
     * Sets the forward direction of the entity.
     */
    public void setForwardDirection(int forwardDirection) {
        this.entityData.set(FORWARD_DIRECTION, forwardDirection);
    }

    /**
     * Gets the forward direction of the entity.
     */
    public int getForwardDirection() {
        return this.entityData.get(FORWARD_DIRECTION);
    }

    public void setBoatType(TNBoatEntity.Type boatType) {
        this.entityData.set(BOAT_TYPE, boatType.ordinal());
    }

    public TNBoatEntity.Type getBoatType() {
        return TNBoatEntity.Type.byId(this.entityData.get(BOAT_TYPE));
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().size() < 2 && !this.isEyeInFluid(FluidTags.WATER);
    }

    /**
     * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
     * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
     */
    @Nullable
    @Override
    public Entity getControllingPassenger() {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }
    /*
    @OnlyIn(Dist.CLIENT)
    public void updateClientControls() {
        Player player = Minecraft.getInstance().player;
        this.setInputs(player.input.left, player.input.right, player.input.up, player.input.down);

    }
    */

    @OnlyIn(Dist.CLIENT)
    public void setInputs(boolean leftInputDown, boolean rightInputDown, boolean forwardInputDown, boolean backInputDown) {
        this.leftInputDown = leftInputDown;
        this.rightInputDown = rightInputDown;
        this.forwardInputDown = forwardInputDown;
        this.backInputDown = backInputDown;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    public boolean canSwim() {
        return this.status == TNBoatEntity.Status.UNDER_WATER || this.status == TNBoatEntity.Status.UNDER_FLOWING_WATER;
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        if (this.isControlledByLocalInstance() && this.lerpSteps > 0) {
            this.lerpSteps = 0;
            this.absMoveTo(this.lerpX, this.lerpY, this.lerpZ, (float) this.lerpYaw, (float) this.lerpPitch);
        }
    }

    public enum Status {
        IN_WATER,
        UNDER_WATER,
        UNDER_FLOWING_WATER,
        ON_LAND,
        IN_AIR
    }

    public enum Type {
        OAK("oak"),
        SPRUCE("spruce"),
        BIRCH("birch"),
        JUNGLE("jungle"),
        ACACIA("acacia"),
        DARK_OAK("dark_oak"),
        //BOP
        BOP_CHERRY("bop_cherry"),
        BOP_DEAD("bop_cherry"),
        BOP_FIR("bop_fir"),
        BOP_HELLBARK("bop_hellbark"),
        BOP_JACARANDA("bop_jacaranda"),
        BOP_MAGIC("bop_magic"),
        BOP_MAHOGANY("bop_mahogany"),
        BOP_PALM("bop_palm"),
        BOP_REDWOOD("bop_redwood"),
        BOP_UMBRAN("bop_umbran"),
        BOP_WILLOW("bop_willow"),
        //LOTR
        LOTR_APPLE("lotr_apple"),
        LOTR_ASPEN("lotr_aspen"),
        LOTR_BEECH("lotr_beech"),
        LOTR_CEDAR("lotr_cedar"),
        LOTR_CHARRED("lotr_charred"),
        LOTR_CHERRY("lotr_cherry"),
        LOTR_CYPRESS("lotr_cypress"),
        LOTR_FIR("lotr_fir"),
        LOTR_GREEN_OAK("lotr_green_oak"),
        LOTR_HOLLY("lotr_holly"),
        LOTR_LAIRELOSSE("lotr_lairelosse"),
        LOTR_LARCH("lotr_larch"),
        LOTR_LEBETHRON("lotr_lebethron"),
        LOTR_MALLORN("lotr_mallorn"),
        LOTR_MAPLE("lotr_maple"),
        LOTR_MIRK_OAK("lotr_mirk_oak"),
        LOTR_PEAR("lotr_pear"),
        LOTR_PINE("lotr_pine"),
        LOTR_ROTTEN("lotr_rotten"),
        //ENVI
        ENVI_CHERRY("envi_cherry"),
        ENVI_WISTERIA("envi_wisteria"),
        ENVI_WILLOW("envi_willow");


        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }


        public String toString() {
            return this.name;
        }

        /**
         * Get a boat type by it's enum ordinal
         */
        public static TNBoatEntity.Type byId(int id) {
            TNBoatEntity.Type[] aboatentity$type = values();
            if (id < 0 || id >= aboatentity$type.length) {
                id = 0;
            }

            return aboatentity$type[id];
        }

        public static TNBoatEntity.Type getTypeFromString(String nameIn) {
            TNBoatEntity.Type[] aboatentity$type = values();

            for (int i = 0; i < aboatentity$type.length; ++i) {
                if (aboatentity$type[i].getName().equals(nameIn)) {
                    return aboatentity$type[i];
                }
            }

            return aboatentity$type[0];
        }
    }

    @Override
    public Vec3 getDismountLocationForPassenger(final LivingEntity rider) {
        rider.getMainArm();
        for (final float angle : new float[]{-90.0F, 90.0F}) {
            final Vec3 pos = this.removeVehicle(getCollisionHorizontalEscapeVector(this.getBbWidth(), rider.getBbWidth(), this.getYRot() + angle), rider);
            if (pos != null) return pos;
        }
        return this.position();
    }

    private Vec3 removeVehicle(final Vec3 dir, LivingEntity rider) {
        final double x = this.getX() + dir.x;
        final double y = this.getBoundingBox().minY;
        final double z = this.getZ() + dir.z;
        final double limit = this.getBoundingBox().maxY + 0.75D;
        final BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
        for (final Pose pose : rider.getDismountPoses()) {
            blockPos.set(x, y, z);
            while (blockPos.getY() < limit) {
                final double ground = this.level.getBlockFloorHeight(blockPos);
                if (blockPos.getY() + ground > limit) break;
                if (DismountHelper.isBlockFloorValid(ground)) {
                    final Vec3 pos = new Vec3(x, blockPos.getY() + ground, z);
                    if (DismountHelper.canDismountTo(this.level, rider, rider.getLocalBoundsForPose(pose).move(pos))) {
                        rider.setPose(pose);
                        return pos;
                    }
                }
                blockPos.move(Direction.UP);
            }
        }
        return null;
    }

    public Player getDriver() {
        List<Entity> passengers = getPassengers();
        if (passengers.size() <= 0)
            return null;
        if (passengers.get(0) instanceof Player)
            return (Player) passengers.get(0);
        return null;
    }

}
