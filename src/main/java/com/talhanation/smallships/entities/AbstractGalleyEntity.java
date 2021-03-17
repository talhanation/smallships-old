package com.talhanation.smallships.entities;

import com.talhanation.smallships.init.SoundInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.*;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.client.CSteerBoatPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;

public abstract class AbstractGalleyEntity extends BoatEntity {

    private static final DataParameter<Boolean> LEFT_PADDLE = EntityDataManager.createKey(AbstractGalleyEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> RIGHT_PADDLE = EntityDataManager.createKey(AbstractGalleyEntity.class, DataSerializers.BOOLEAN);
    protected abstract ItemStackHandler initInventory();
    private LazyOptional<ItemStackHandler> itemHandler = LazyOptional.of(() -> this.inventory);
    private final float[] paddlePositions = new float[2];
    public float momentum;
    public float outOfControlTicks;
    public float deltaRotation;
    private int lerpSteps;
    private double lerpX;
    public boolean removed;
    private double lerpY;
    private double lerpZ;
    private double lerpYaw;
    private double lerpPitch;
    public boolean leftInputDown;
    public boolean rightInputDown;
    private boolean forwardInputDown;
    private boolean backInputDown;
    private double waterLevel;
    private float boatGlide;
    private AbstractGalleyEntity.Status status;
    private AbstractGalleyEntity.Status previousStatus;
    private double lastYd;
    private boolean rocking;
    private boolean downwards;
    private float rockingIntensity;
    private float rockingAngle;
    private float prevRockingAngle;
    public ItemStackHandler inventory = initInventory();
    public int passengerwaittime;
    public int controltimer;
    public float passengerfaktor;
    public int playFirstSailSoundcounter;
    public int playLastSailSoundcounter;
    public boolean leftsteer;
    public boolean rightsteer;
    public int sailtick;
    private boolean bindingToggled;
    private boolean bindingDownOnLast;

    public AbstractGalleyEntity(EntityType<? extends AbstractGalleyEntity> entityType, World worldIn) {
        super(entityType, worldIn);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(LEFT_PADDLE, false);
        this.dataManager.register(RIGHT_PADDLE, false);
    }

    public void tick() {

        passengerwaittime--;
        playFirstSailSoundcounter--;
        playLastSailSoundcounter--;
        sailtick--;
        this.previousStatus = this.status;
        this.status = this.getBoatStatus();
        if (this.status != BoatEntity.Status.UNDER_WATER && this.status != BoatEntity.Status.UNDER_FLOWING_WATER) {
            this.outOfControlTicks = 0.0F;
        } else {
            ++this.outOfControlTicks;
        }

        if (!this.world.isRemote && this.outOfControlTicks >= 60.0F) {
            this.removePassengers();
        }

        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }

        super.tick();
        this.tickLerp();
        if (this.canPassengerSteer()) {
            if (this.getPassengers().isEmpty() || !(this.getPassengers().get(0) instanceof PlayerEntity)) {
                this.setPaddleState(false, false);
            }

            this.updateMotion();
            if (this.world.isRemote) {
                this.controlBoat();
                this.world.sendPacketToServer(new CSteerBoatPacket(this.getPaddleState(0), this.getPaddleState(1)));
            }

            this.move(MoverType.SELF, this.getMotion());
        } else {
            this.setMotion(Vector3d.ZERO);
        }

        for(int i = 0; i <= 1; ++i) {
            if (this.getPaddleState(i)) {
                if (!this.isSilent() && (double)(this.paddlePositions[i] % ((float)Math.PI * 2F)) <= (double)((float)Math.PI / 4F) && ((double)this.paddlePositions[i] + (double)((float)Math.PI / 8F)) % (double)((float)Math.PI * 2F) >= (double)((float)Math.PI / 4F)) {
                    SoundEvent soundevent = this.getPaddleSound();
                    if (soundevent != null) {
                        Vector3d vector3d = this.getLook(1.0F);
                        double d0 = i == 1 ? -vector3d.z : vector3d.z;
                        double d1 = i == 1 ? vector3d.x : -vector3d.x;
                        this.world.playSound((PlayerEntity)null, this.getPosX() + d0, this.getPosY(), this.getPosZ() + d1, soundevent, this.getSoundCategory(), 1.0F, 0.8F + 0.4F * this.rand.nextFloat());
                        this.world.playSound((PlayerEntity)null, this.getPosX() + d0, this.getPosY(), this.getPosZ() + d1, soundevent, this.getSoundCategory(), 1.0F, 0.2F + 0.4F * this.rand.nextFloat());
                        this.world.playSound((PlayerEntity)null, this.getPosX() + d0, this.getPosY(), this.getPosZ() + d1, soundevent, this.getSoundCategory(), 1.0F, 0.3F + 0.4F * this.rand.nextFloat());
                        this.world.playSound((PlayerEntity)null, this.getPosX() + d0, this.getPosY(), this.getPosZ() + d1, soundevent, this.getSoundCategory(), 1.0F, 0.6F + 0.4F * this.rand.nextFloat());
                    }
                }

                this.paddlePositions[i] = (float)((double)this.paddlePositions[i] + (double)((float)Math.PI / 8F));
            } else {
                this.paddlePositions[i] = 0.0F;
            }
        }
        if (playFirstSailSoundcounter == 0) {
            this.world.playSound(this.getPosX(), this.getPosY(),this.getPosZ(), SoundInit.SHIP_SAIL_0.get(), this.getSoundCategory(), 10.0F, 0.8F + 0.4F * this.rand.nextFloat(), false);
        }

        if (playLastSailSoundcounter == 0) {
            this.world.playSound(this.getPosX(), this.getPosY(),this.getPosZ(), SoundInit.SHIP_SAIL_1.get(), this.getSoundCategory(), 8.0F, 0.8F + 0.4F * this.rand.nextFloat(), false);
        }

        this.doBlockCollisions();
        List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().grow((double) 0.2F, (double) -0.01F, (double) 0.2F), EntityPredicates.pushableBy(this));
        if (!list.isEmpty()) {
            boolean flag = !this.world.isRemote && !(this.getControllingPassenger() instanceof PlayerEntity);

            for (int j = 0; j < list.size(); ++j) {
                Entity entity = list.get(j);
                if (!entity.isPassenger(this)) {
                    if (flag && this.getPassengers().size() < 8 && !entity.isPassenger() && entity.getWidth() < this.getWidth() && entity instanceof LivingEntity && !(entity instanceof WaterMobEntity) && !(entity instanceof PlayerEntity)) {
                        if (passengerwaittime < 0) {
                            entity.startRiding(this);
                        }
                    } else {
                        this.applyEntityCollision(entity);
                    }
                }
            }
        }

    }
    public void tickLerp() {
        if (this.canPassengerSteer()) {
            this.lerpSteps = 0;
            this.setPacketCoordinates(this.getPosX(), this.getPosY(), this.getPosZ());
        }

        if (this.lerpSteps > 0) {
            double d0 = this.getPosX() + (this.lerpX - this.getPosX()) / (double) this.lerpSteps;
            double d1 = this.getPosY() + (this.lerpY - this.getPosY()) / (double) this.lerpSteps;
            double d2 = this.getPosZ() + (this.lerpZ - this.getPosZ()) / (double) this.lerpSteps;
            double d3 = MathHelper.wrapDegrees(this.lerpYaw - (double) this.rotationYaw);
            this.rotationYaw = (float) ((double) this.rotationYaw + d3 / (double) this.lerpSteps);
            this.rotationPitch = (float) ((double) this.rotationPitch + (this.lerpPitch - (double) this.rotationPitch) / (double) this.lerpSteps);
            --this.lerpSteps;
            this.setPosition(d0, d1, d2);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
    }

    public BoatEntity.Status getBoatStatus() {
        BoatEntity.Status boatentity$status = getUnderwaterStatus();
        if (boatentity$status != null) {
            this.waterLevel = (getBoundingBox()).maxY;
            return boatentity$status;
        }
        if (checkInWater())
            return BoatEntity.Status.IN_WATER;
        float f = getBoatGlide();
        if (f > 0.0F) {
            this.boatGlide = 0F;
            return BoatEntity.Status.ON_LAND;
        }
        return BoatEntity.Status.IN_AIR;
    }

    public void updateMotion() {
        double d0 = -0.03D; // down/up moment
        double d1 = hasNoGravity() ? 0.0D : d0;
        double d2 = 0.0D;  //
        this.momentum = 1.0F;
        this.passengerfaktor = 0;
        if (this.previousStatus == BoatEntity.Status.IN_AIR && this.status != BoatEntity.Status.IN_AIR && this.status != BoatEntity.Status.ON_LAND) {
            this.waterLevel = (getBoundingBox()).minY + getHeight();
            setPosition(getPosX(), (getWaterLevelAbove() - getHeight()) + 0.101D, getPosZ());
            setMotion(getMotion().mul(10.0D, 0.0D, 10.0D));
            this.lastYd = 0.0D;
            this.status = BoatEntity.Status.IN_WATER;
        } else {
            if (this.status == BoatEntity.Status.IN_WATER) {
                d2 = (this.waterLevel - (getBoundingBox()).minY + 0.1D) / getHeight();
                this.momentum = 0.9F * 1.0F;
            } else if (this.status == BoatEntity.Status.UNDER_FLOWING_WATER) {
                d1 = -7.0E-4D;
                this.momentum = 0.9F;
            } else if (this.status == BoatEntity.Status.UNDER_WATER) {
                d2 = 0.009999999776482582D;
                this.momentum = 0.45F;
            } else if (this.status == BoatEntity.Status.IN_AIR) {
                this.momentum = 0.9F;
            } else if (this.status == BoatEntity.Status.ON_LAND) {
                this.momentum = this.boatGlide * 0.001F;
                if (getControllingPassenger() instanceof net.minecraft.entity.player.PlayerEntity)
                    this.boatGlide /= 1.0F;
            }
            Vector3d vec3d = getMotion();
            setMotion(vec3d.x * (this.momentum - this.passengerfaktor), vec3d.y + d1, vec3d.z * (this.momentum - this.passengerfaktor));
            this.deltaRotation *= (this.momentum - this.passengerfaktor) *0.5;
            if (d2 > 0.0D) {
                Vector3d vec3d1 = getMotion();
                setMotion(vec3d1.x, (vec3d1.y + d2 * 0.06D) * 0.75D, vec3d1.z);
            }
        }
    }

    public double getMountedYOffset() {
        return 0.75D;
    }

    protected void controlBoat() {
        if (this.isBeingRidden()) {
            float f = 0.0F;
            if (this.leftInputDown ) {
                --this.deltaRotation;
            }
            if (this.rightInputDown) {
                ++this.deltaRotation;
            }
            if (this.rightInputDown != this.leftInputDown && !this.forwardInputDown && !this.backInputDown) {
                f += 0.005F;
            }
            this.rotationYaw += this.deltaRotation;

            if (this.isSailDown()) {
                 f += 0.04F;
                 if (this.forwardInputDown) {
                     f += 0.06F; // speed
                 }
             }
            if (this.backInputDown) {
                f -= 0.005F;
            }

            if (this.forwardInputDown && !this.isSailDown()) {
                f += 0.045F; // speed
            }

            this.setMotion(this.getMotion().add((double)(MathHelper.sin(-this.rotationYaw * ((float)Math.PI / 180F)) * f), 0.0D, (double)(MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180F)) * f)));
            this.setPaddleState(this.rightInputDown && !this.leftInputDown || this.forwardInputDown, this.leftInputDown && !this.rightInputDown || this.forwardInputDown);

        }
    }

    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket((Entity) this);
    }

    @Nullable
    private BoatEntity.Status getUnderwaterStatus() {
        AxisAlignedBB axisalignedbb = this.getBoundingBox();
        double d0 = axisalignedbb.maxY + 0.001D;
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.ceil(axisalignedbb.maxX);
        int k = MathHelper.floor(axisalignedbb.maxY);
        int l = MathHelper.ceil(d0);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.ceil(axisalignedbb.maxZ);
        boolean flag = false;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for (int k1 = i; k1 < j; ++k1) {
            for (int l1 = k; l1 < l; ++l1) {
                for (int i2 = i1; i2 < j1; ++i2) {
                    blockpos$mutable.setPos(k1, l1, i2);
                    FluidState fluidstate = this.world.getFluidState(blockpos$mutable);
                    if (fluidstate.isTagged(FluidTags.WATER) && d0 < (double) ((float) blockpos$mutable.getY() + fluidstate.getActualHeight(this.world, blockpos$mutable))) {
                        if (!fluidstate.isSource()) {
                            return BoatEntity.Status.UNDER_FLOWING_WATER;
                        }

                        flag = true;
                    }
                }
            }
        }

        return flag ? BoatEntity.Status.UNDER_WATER : null;
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
                    blockpos$mutable.setPos(k1, l1, i2);
                    FluidState fluidstate = this.world.getFluidState(blockpos$mutable);
                    if (fluidstate.isTagged(FluidTags.WATER)) {
                        float f = (float) l1 + fluidstate.getActualHeight(this.world, blockpos$mutable);
                        this.waterLevel = Math.max((double) f, this.waterLevel);
                        flag |= axisalignedbb.minY < (double) f;
                    }
                }
            }
        }

        return flag;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    /**
     * Applies a velocity to the entities, to push them away from eachother.
     */
    @Override
    public void applyEntityCollision(Entity entityIn) {
        if (entityIn instanceof BoatEntity) {
            if (entityIn.getBoundingBox().minY < this.getBoundingBox().maxY) {
                super.applyEntityCollision(entityIn);
            }
        } else if (entityIn.getBoundingBox().minY <= this.getBoundingBox().minY && ! (entityIn instanceof WaterMobEntity)) {
            super.applyEntityCollision(entityIn);
        }
    }

    //inventory
    public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
        if (inventorySlot >= 0 && inventorySlot < this.inventory.getSlots()) {
            this.inventory.setStackInSlot(inventorySlot, itemStackIn);
            return true;
        }
        return false;
    }

    public void onDestroyedAndDoDrops(DamageSource source) {
        for (int i = 0; i < this.inventory.getSlots(); i++)
            InventoryHelper.spawnItemStack(this.world, getPosX(), getPosY(), getPosZ(), this.inventory.getStackInSlot(i));
    }

    protected void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.inventory.deserializeNBT(compound.getCompound("Items"));
    }

    protected void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.put("Items", (INBT)this.inventory.serializeNBT());

    }

    public void remove(boolean keepData) {
        super.remove(keepData);
        if (!keepData && this.itemHandler != null) {
            this.itemHandler.invalidate();
            this.itemHandler = null;
        }
    }
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (isAlive() && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && this.itemHandler != null)
            return this.itemHandler.cast();
        return super.getCapability(capability, facing);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (isInvulnerableTo(source))
            return false;
        if (!this.world.isRemote && isAlive()) {
            if (source == DamageSource.CACTUS)
                return false;
            if (source instanceof net.minecraft.util.IndirectEntityDamageSource && source.getTrueSource() != null && isPassenger(source.getTrueSource()))
                return false;
            setForwardDirection(-getForwardDirection());
            setTimeSinceHit(3);
            setDamageTaken(getDamageTaken() + amount * 10.0F);
            boolean flag = (source.getTrueSource() instanceof PlayerEntity && ((PlayerEntity)source.getTrueSource()).abilities.isCreativeMode);
            if (flag || getDamageTaken() > 600.0F) {
                onDestroyed(source, flag);
                remove();
            }
            return true;
        }
        return false;
    }

    public void onDestroyed(DamageSource source, boolean byCreativePlayer) {
        if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            if (!byCreativePlayer)
                this.entityDropItem(this.getItemBoat());
            onDestroyedAndDoDrops(source);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void updateInputs(boolean leftInputDown, boolean rightInputDown, boolean forwardInputDown, boolean backInputDown) {
        this.leftInputDown = leftInputDown;
        this.rightInputDown = rightInputDown;
        this.forwardInputDown = forwardInputDown;
        this.backInputDown = backInputDown;
    }
    public boolean isSailDown() {
        final Minecraft mc = Minecraft.getInstance();
        final KeyBinding binding = mc.gameSettings.keyBindSprint;
        boolean flag = this.getControllingPassenger() instanceof net.minecraft.entity.player.PlayerEntity;

        if (flag){
            if (binding.isPressed()) {
                bindingDownOnLast = true;
            } else if (!binding.isPressed()&& bindingDownOnLast && !bindingToggled ) {
                bindingDownOnLast = false;
                bindingToggled = true;
                sailtick = 10;
                this.playFirstSailSoundcounter = 2;
            } else if (!binding.isPressed() && bindingDownOnLast && bindingToggled && sailtick <= 0) {
                bindingDownOnLast = false;
                bindingToggled = false;
                this.playLastSailSoundcounter = 2;
            }
            if (this.bindingToggled) {
                return true;
            }
            return false;
        }
        return false;
    }

    public float WaveMotion(){
        float wavestr = 2.0F;
        if (world.isRaining()) return 1.5F * wavestr;
        else return wavestr;
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);

    }

    @Nullable
    protected SoundEvent getPaddleSound() {
        switch(this.getBoatStatus()) {
            case IN_WATER:
            case UNDER_WATER:
            case UNDER_FLOWING_WATER:
                return SoundEvents.ENTITY_BOAT_PADDLE_WATER;
            case ON_LAND:
                return SoundEvents.ENTITY_BOAT_PADDLE_LAND;
            case IN_AIR:
            default:
                return null;
        }
    }

    public void setPaddleState(boolean left, boolean right) {
        this.dataManager.set(LEFT_PADDLE, left);
        this.dataManager.set(RIGHT_PADDLE, right);
    }

    @OnlyIn(Dist.CLIENT)
    public float getRowingTime(int side, float limbSwing) {
        return this.getPaddleState(side) ? (float)MathHelper.clampedLerp((double)this.paddlePositions[side] - (double)((float)Math.PI / 8F), (double)this.paddlePositions[side], (double)limbSwing) : 0.0F;
    }

    public boolean getPaddleState(int side) {
        return this.dataManager.<Boolean>get(side == 0 ? LEFT_PADDLE : RIGHT_PADDLE) && this.getControllingPassenger() != null;
    }

    @Override
    public Vector3d func_230268_c_(final LivingEntity rider) {
        for (final float angle : rider.getPrimaryHand() == HandSide.RIGHT ? new float[] { 90.0F, -90.0F } : new float[] { -90.0F, 90.0F }) {
            final Vector3d pos = this.dismount(func_233559_a_(this.getWidth(), rider.getWidth(), this.rotationYaw + angle), rider);
            if (pos != null) return pos;
        }
        return this.getPositionVec();
    }

    private Vector3d dismount(final Vector3d dir, LivingEntity rider) {
        final double x = this.getPosX() + dir.x;
        final double y = this.getBoundingBox().minY;
        final double z = this.getPosZ() + dir.z;
        final double limit = this.getBoundingBox().maxY + 0.75D;
        final BlockPos.Mutable blockPos = new BlockPos.Mutable();
        for (final Pose pose : rider.getAvailablePoses()) {
            blockPos.setPos(x, y, z);
            while (blockPos.getY() < limit) {
                final double ground = this.world.func_242403_h(blockPos);
                if (blockPos.getY() + ground > limit) break;
                if (TransportationHelper.func_234630_a_(ground)) {
                    final Vector3d pos = new Vector3d(x, blockPos.getY() + ground, z);
                    if (TransportationHelper.func_234631_a_(this.world, rider, rider.getPoseAABB(pose).offset(pos))) {
                        rider.setPose(pose);
                        return pos;
                    }
                }
                blockPos.move(Direction.UP);
            }
        }
        return null;
    }

}