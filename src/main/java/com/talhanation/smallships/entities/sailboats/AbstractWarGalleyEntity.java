package com.talhanation.smallships.entities.sailboats;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.config.SmallShipsConfig;
import com.talhanation.smallships.entities.AbstractBannerUser;
import com.talhanation.smallships.network.MessagePaddleState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;

public abstract class AbstractWarGalleyEntity extends AbstractBannerUser {
   private final float[] paddlePositions = new float[2];
    public float momentum;
    public float outOfControlTicks;
    public float deltaRotation;
    public boolean removed;
    private double waterLevel;
    private float boatGlide;
    public boolean leftInputDown;
    public boolean rightInputDown;
    public boolean forwardInputDown;
    public boolean backInputDown;
    private Status status;
    private Status previousStatus;
    public ItemStackHandler inventory = initInventory();
    public int passengerwaittime;
    public float passengerfaktor;

    public AbstractWarGalleyEntity(EntityType<? extends AbstractWarGalleyEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public void tick() {
        passengerwaittime--;

        this.previousStatus = this.status;
        this.status = this.getBoatStatus();
        if (this.status != Status.UNDER_WATER && this.status != Status.UNDER_FLOWING_WATER) {
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

        super.tick();
        this.tickLerp();
        if (this.isControlledByLocalInstance()) {
            if (this.getPassengers().isEmpty() || !(this.getPassengers().get(0) instanceof Player)) {
                this.setPaddleState(false, false);
            }
            this.updateMotion();
            if (this.level.isClientSide) {
                this.controlBoat();
                Main.SIMPLE_CHANNEL.sendToServer(new MessagePaddleState(this.getPaddleState(0), this.getPaddleState(1)));
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
        } else {
            this.setDeltaMovement(Vec3.ZERO);
        }


        for(int i = 0; i <= 1; ++i) {
            if (this.getPaddleState(i)) {
                if (!this.isSilent() && (double)(this.paddlePositions[i] % ((float)Math.PI * 2F)) <= (double)((float)Math.PI / 4F) && ((double)this.paddlePositions[i] + (double)((float)Math.PI / 8F)) % (double)((float)Math.PI * 2F) >= (double)((float)Math.PI / 4F)) {
                    SoundEvent soundevent = this.getPaddleSound();
                    if (soundevent != null) {
                        Vec3 vector3d = this.getViewVector(1.0F);
                        double d0 = i == 1 ? -vector3d.z : vector3d.z;
                        double d1 = i == 1 ? vector3d.x : -vector3d.x;
                        this.level.playSound((Player)null, this.getX() + d0, this.getY(), this.getZ() + d1, soundevent, this.getSoundSource(), 1.0F, 0.8F + 0.4F * this.random.nextFloat());
                        this.level.playSound((Player)null, this.getX() + d0, this.getY(), this.getZ() + d1, soundevent, this.getSoundSource(), 1.0F, 0.2F + 0.4F * this.random.nextFloat());
                        this.level.playSound((Player)null, this.getX() + d0, this.getY(), this.getZ() + d1, soundevent, this.getSoundSource(), 1.0F, 0.3F + 0.4F * this.random.nextFloat());
                        this.level.playSound((Player)null, this.getX() + d0, this.getY(), this.getZ() + d1, soundevent, this.getSoundSource(), 1.0F, 0.6F + 0.4F * this.random.nextFloat());
                        this.level.playSound((Player)null, this.getX() + d0, this.getY(), this.getZ() + d1, soundevent, this.getSoundSource(), 1.0F, 0.8F + 0.4F * this.random.nextFloat());
                        this.level.playSound((Player)null, this.getX() + d0, this.getY(), this.getZ() + d1, soundevent, this.getSoundSource(), 1.0F, 0.2F + 0.4F * this.random.nextFloat());
                    }
                }

                this.paddlePositions[i] = (float) ((double) this.paddlePositions[i] + (double) ((float) Math.PI / 8F));
            } else {
                this.paddlePositions[i] = 0.0F;
            }
        }

        if (getSailState() != 0 && this.getBoatStatus().equals(Status.IN_WATER) && this.getControllingPassenger() instanceof Player && SmallShipsConfig.PlaySwimmSound.get()) {
            this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_SWIM, this.getSoundSource(), 0.05F, 0.8F + 0.4F * this.random.nextFloat());

        }
        this.checkInsideBlocks();
        List<Entity> list = this.level.getEntities(this, this.getBoundingBox().inflate((double) 0.2F, (double) -0.01F, (double) 0.2F), EntitySelector.pushableBy(this));
        if (!list.isEmpty()) {
            boolean flag = !this.level.isClientSide && !(this.getControllingPassenger() instanceof Player);

            for (int j = 0; j < list.size(); ++j) {
                Entity entity = list.get(j);
                if (!entity.hasPassenger(this)) {
                    if (flag && this.getPassengers().size() < 16 && !entity.isPassenger() && entity.getBbWidth() < this.getBbWidth() && entity instanceof LivingEntity && !(entity instanceof WaterAnimal) && !(entity instanceof Player)) {
                        if (passengerwaittime < 0) {
                            entity.startRiding(this);
                        }
                    } else {
                        this.push(entity);
                    }
                }
            }
        }

    }

    @Override
    public void Watersplash(){
        super.Watersplash();
        Vec3 vector3d = this.getViewVector(0.0F);
        float f0 = Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * 1.2F;
        float f1 = Mth.sin(this.getYRot() * ((float)Math.PI / 180F)) * 1.2F;
        float f1_1 = Mth.sin(this.getYRot() * ((float)Math.PI / 180F)) * 1.8F;
        float f0_1 = Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * 1.8F;

        float f2 =  4.0F - this.random.nextFloat() * 0.7F;
        float f2_ =  -3.3F - this.random.nextFloat() * 0.7F;
        float x = 0;
        for (int i = 0; i < 4; ++i) {
            this.level.addParticle(ParticleTypes.DOLPHIN, this.getX() - vector3d.x * (double) f2 + (double) f0, this.getY() - vector3d.y + 0.5D, this.getZ() - vector3d.z * (double) f2 + (double) f1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.DOLPHIN, this.getX() - vector3d.x * (double) f2 - (double) f0, this.getY() - vector3d.y + 0.5D, this.getZ() - vector3d.z * (double) f2 - (double) f1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.DOLPHIN, this.getX() - vector3d.x * (double) f2 + (double) f0, this.getY() - vector3d.y + 0.5D, this.getZ() - vector3d.z * (double) f2 + (double) f1 * 1.1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.DOLPHIN, this.getX() - vector3d.x * (double) f2 - (double) f0, this.getY() - vector3d.y + 0.5D, this.getZ() - vector3d.z * (double) f2 - (double) f1 * 1.1, 0.0D, 0.0D, 0.0D);

            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2 + (double) f0, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) f2 + (double) f1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2 - (double) f0, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) f2 - (double) f1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2 + (double) f0, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) f2 + (double) f1 * 1.1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2 - (double) f0, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) f2 - (double) f1 * 1.1, 0.0D, 0.0D, 0.0D);

            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2_ + (double) f0_1, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) f2_ + (double) f1_1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2_ - (double) f0_1, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) f2_ - (double) f1_1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2_ + (double) f0_1, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) f2_ + (double) f1_1 * 1.1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2_ - (double) f0_1, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) f2_ - (double) f1_1 * 1.1, 0.0D, 0.0D, 0.0D);

            this.level.addParticle(ParticleTypes.BUBBLE, this.getX() - vector3d.x * (double) f2_ + (double) f0_1, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) (f2_ - x) + (double) f1_1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.BUBBLE, this.getX() - vector3d.x * (double) f2_ - (double) f0_1, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) (f2_ - x) - (double) f1_1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.BUBBLE, this.getX() - vector3d.x * (double) f2_ + (double) f0_1, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) (f2_ - x) + (double) f1_1 * 1.1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.BUBBLE, this.getX() - vector3d.x * (double) f2_ - (double) f0_1, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) (f2_ - x) - (double) f1_1 * 1.1, 0.0D, 0.0D, 0.0D);

        }
    }
    @Override
    public void tickLerp() {
    }

    public Status getBoatStatus() {
        Status boatentity$status = getUnderwaterStatus();
        if (boatentity$status != null) {
            this.waterLevel = (getBoundingBox()).maxY;
            return boatentity$status;
        }
        if (checkInWater())
            return Status.IN_WATER;
        float f = getBoatGlide();
        if (f > 0.0F) {
            this.boatGlide = 0F;
            return Status.ON_LAND;
        }
        return Status.IN_AIR;
    }

    public void updateMotion() {
        double d0 = -0.03D; // down/up moment
        double d1 = isNoGravity() ? 0.0D : d0;
        double d2 = 0.0D;  //
        double WarGalleyTurnFactor = SmallShipsConfig.WarGalleyTurnFactor.get();

        this.momentum = 1.0F;
        if (this.getPassengers().size() == 4) this.passengerfaktor = 0.0125F;
        if (this.getPassengers().size() == 6) this.passengerfaktor = 0.025F;
        if (this.getPassengers().size() == 10) this.passengerfaktor = 0.05F;
        if (this.getPassengers().size() == 12) this.passengerfaktor = 0.075F;
        if (this.getPassengers().size() > 12) this.passengerfaktor = 0.15F;
        if (this.previousStatus == Status.IN_AIR && this.status != Status.IN_AIR && this.status != Status.ON_LAND) {
            this.waterLevel = (getBoundingBox()).minY + getBbHeight();
            setPos(getX(), (getWaterLevelAbove() - getBbHeight()) + 0.101D, getZ());
            setDeltaMovement(getDeltaMovement().multiply(10.0D, 0.0D, 10.0D));
            this.status = Status.IN_WATER;
        } else {
            if (this.status == Status.IN_WATER) {
                d2 = (this.waterLevel - (getBoundingBox()).minY + 0.1D) / getBbHeight();
                this.momentum = 0.9F;
            } else if (this.status == Status.UNDER_FLOWING_WATER) {
                d1 = -7.0E-4D;
                this.momentum = 0.9F;
            } else if (this.status == Status.UNDER_WATER) {
                d2 = 0.009999999776482582D;
                this.momentum = 0.45F;
            } else if (this.status == Status.IN_AIR) {
                this.momentum = 0.9F;
            } else if (this.status == Status.ON_LAND) {
                this.momentum = this.boatGlide * 0.001F;
                if (getControllingPassenger() instanceof Player)
                    this.boatGlide /= 1.0F;
            }
            Vec3 vec3d = getDeltaMovement();
            setDeltaMovement(vec3d.x * (this.momentum - this.passengerfaktor), vec3d.y + d1, vec3d.z * (this.momentum - this.passengerfaktor));
            this.deltaRotation *= (this.momentum - this.passengerfaktor) * WarGalleyTurnFactor;

            if (d2 > 0.0D) {
                Vec3 vec3d1 = getDeltaMovement();
                setDeltaMovement(vec3d1.x, (vec3d1.y + d2 * 0.06D) * 0.75D, vec3d1.z);
            }
        }
    }

    protected void controlBoat() {
        double WarGalleySpeedFactor = SmallShipsConfig.WarGalleySpeedFactor.get();
        int sailstate = getSailState();
        if (this.isVehicle()) {
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
            this.setYRot(this.getYRot() + this.deltaRotation);

            if (sailstate != 0) {
                switch (sailstate){
                    case 1:
                        f += (0.04F * WarGalleySpeedFactor * 1/4);
                        if (this.forwardInputDown) {
                            f += (0.02F * WarGalleySpeedFactor); // speed
                        }
                        break;
                    case 2:
                        f += (0.04F * WarGalleySpeedFactor * 2/4);
                        if (this.forwardInputDown) {
                            f += (0.02F * WarGalleySpeedFactor); // speed
                        }
                        break;
                    case 3:
                        f += (0.04F * WarGalleySpeedFactor * 3/4);
                        if (this.forwardInputDown) {
                            f += (0.02F * WarGalleySpeedFactor); // speed
                        }
                        break;
                    case 4:
                        f += (0.04F * WarGalleySpeedFactor * 1);
                        if (this.forwardInputDown) {
                            f += (0.02F * WarGalleySpeedFactor); // speed
                        }
                        break;
                }

            }

            if (this.backInputDown) {
                f -= (0.005F * WarGalleySpeedFactor);
            }

            if (this.forwardInputDown &&  sailstate == 0) {
                f += (0.04F * WarGalleySpeedFactor); // speed
            }

            this.setDeltaMovement(this.getDeltaMovement().add((double)(Mth.sin(-this.getYRot() * ((float)Math.PI / 180F)) * f), 0.0D, (double)(Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * f)));
            this.setPaddleState(this.rightInputDown && !this.leftInputDown || this.forwardInputDown, this.leftInputDown && !this.rightInputDown || this.forwardInputDown);
            this.setIsForward(this.forwardInputDown);
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket((Entity) this);
    }

    @Nullable
    private Status getUnderwaterStatus() {
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
                            return Status.UNDER_FLOWING_WATER;
                        }

                        flag = true;
                    }
                }
            }
        }

        return flag ? Status.UNDER_WATER : null;
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

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void push(Entity entityIn) {
       super.push(entityIn);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        double WarGalleyHealth = SmallShipsConfig.WarGalleyHealth.get();
        if (isInvulnerableTo(source))
            return false;
        if (!this.level.isClientSide && isAlive()) {
            if (source == DamageSource.CACTUS)
                return false;
            if (source instanceof IndirectEntityDamageSource && source.getEntity() != null && hasPassenger(source.getEntity()))
                return false;
            setForwardDirection(-getForwardDirection());
            setTimeSinceHit(3);
            setDamageTaken(getDamageTaken() + amount * 10.0F);
            boolean flag = (source.getEntity() instanceof Player && ((Player) source.getEntity()).getAbilities().instabuild);
            if (flag || getDamageTaken() > WarGalleyHealth) {
                onDestroyed(source, flag);
                dropBanner();
                remove(false);
            }
            return true;
        }
        return false;
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
    }

    @OnlyIn(Dist.CLIENT)
    public float getRowingTime(int side, float limbSwing) {
        return this.getPaddleState(side) ? (float)Mth.clampedLerp((double)this.paddlePositions[side] - (double)((float)Math.PI / 8F), (double)this.paddlePositions[side], (double)limbSwing) : 0.0F;
    }

    public void setPaddleState(boolean left, boolean right) {
        super.setPaddleState(left, right);
    }

    @Nullable

    protected SoundEvent getPaddleSound() {
        switch(this.getBoatStatus()) {
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

    @Override
    public Vec3 getDismountLocationForPassenger(final LivingEntity rider) {
       return super.getDismountLocationForPassenger(rider);
    }

    @Override
    public Player getDriver() {
        return super.getDriver();
    }

    @OnlyIn(Dist.CLIENT)
    public void updateInputs(boolean leftInputDown, boolean rightInputDown, boolean forwardInputDown, boolean backInputDown) {
        this.leftInputDown = leftInputDown;
        this.rightInputDown = rightInputDown;
        this.forwardInputDown = forwardInputDown;
        this.backInputDown = backInputDown;
    }
}