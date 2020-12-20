package com.talhanation.smallships.entities;

import com.talhanation.smallships.init.ModEntityTypes;
import com.talhanation.smallships.items.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class SailBoatEntity extends BoatEntity {
    private float momentum;
    private float outOfControlTicks;
    private float deltaRotation;
    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYaw;
    private double lerpPitch;
    private boolean leftInputDown;
    private boolean rightInputDown;
    private boolean forwardInputDown;
    private boolean backInputDown;
    private double waterLevel;
    private float boatGlide;
    private SailBoatEntity.Status status;
    private SailBoatEntity.Status previousStatus;
    private double lastYd;
    private boolean rocking;
    private boolean downwards;
    private float rockingIntensity;
    private float rockingAngle;
    private float prevRockingAngle;

    public SailBoatEntity(EntityType<? extends SailBoatEntity> entityType, World worldIn) {
        super(entityType, worldIn);
    }

    public SailBoatEntity(World worldIn, double x, double y, double z) {
        this((EntityType<? extends SailBoatEntity>) ModEntityTypes.SAILBOAT_ENTITY.get(), worldIn);
        setPosition(x, y, z);
        setMotion(Vector3d.ZERO);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    public SailBoatEntity(FMLPlayMessages.SpawnEntity spawnEntity, World worldIn) {
        this((EntityType<? extends SailBoatEntity>)ModEntityTypes.SAILBOAT_ENTITY.get(), worldIn);
    }

    public double getMountedYOffset() {
        return 0.3D;
    }

    public void tick() {
        super.tick();
        if (getPassengers().size() > 1) {
            Vector3d motion = getMotion();
            double newY = motion.y - 0.035D;
            setMotion(motion.x, newY, motion.z);
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
            this.boatGlide = 1.0F;
            return BoatEntity.Status.ON_LAND;
        }
        return BoatEntity.Status.IN_AIR;
    }

    public void updateMotion() {
        double d0 = -0.03999999910593033D;
        double d1 = hasNoGravity() ? 0.0D : d0;
        double d2 = 0.0D;
        this.momentum = 0.05F;
        if (this.previousStatus == BoatEntity.Status.IN_AIR && this.status != BoatEntity.Status.IN_AIR && this.status != BoatEntity.Status.ON_LAND) {
            this.waterLevel = (getBoundingBox()).minY + getHeight();
            setPosition(getPosX(), (getWaterLevelAbove() - getHeight()) + 0.101D, getPosZ());
            setMotion(getMotion().mul(1.0D, 0.0D, 1.0D));
            this.lastYd = 0.0D;
            this.status = BoatEntity.Status.IN_WATER;
        } else {
            if (this.status == BoatEntity.Status.IN_WATER) {
                d2 = (this.waterLevel - (getBoundingBox()).minY + 0.1D) / getHeight();
                this.momentum = 0.9F;
            } else if (this.status == BoatEntity.Status.UNDER_FLOWING_WATER) {
                d1 = -7.0E-4D;
                this.momentum = 0.9F;
            } else if (this.status == BoatEntity.Status.UNDER_WATER) {
                d2 = 0.009999999776482582D;
                this.momentum = 0.45F;
            } else if (this.status == BoatEntity.Status.IN_AIR) {
                this.momentum = 0.9F;
            } else if (this.status == BoatEntity.Status.ON_LAND) {
                this.momentum = this.boatGlide;
                if (getControllingPassenger() instanceof net.minecraft.entity.player.PlayerEntity)
                    this.boatGlide /= 2.0F;
            }
            Vector3d vec3d = getMotion();
            setMotion(vec3d.x * this.momentum, vec3d.y + d1, vec3d.z * this.momentum);
            this.deltaRotation *= this.momentum;
            if (d2 > 0.0D) {
                Vector3d vec3d1 = getMotion();
                setMotion(vec3d1.x, (vec3d1.y + d2 * 0.06153846016296973D) * 0.75D, vec3d1.z);
            }
        }
    }

    public void controlBoat() {
        if (isBeingRidden()) {
            float f = 0.0F;
            if (this.leftInputDown)
                this.deltaRotation = (float)(this.deltaRotation - 1.0D);
            if (this.rightInputDown)
                this.deltaRotation = (float)(this.deltaRotation + 1.0D);
            if (this.rightInputDown != this.leftInputDown && !this.forwardInputDown && !this.backInputDown)
                f += 0.005F;
            this.rotationYaw += this.deltaRotation;
            if (this.forwardInputDown)
                f = (float)(f + 0.03999999910593033D);
            if (this.backInputDown)
                f = (float)(f - 0.004999999888241291D);
            setMotion(getMotion().add((MathHelper.sin(-this.rotationYaw * 0.017453292F) * f), 0.0D, (MathHelper.cos(this.rotationYaw * 0.017453292F) * f)));
            setPaddleState(((this.rightInputDown && !this.leftInputDown) || this.forwardInputDown), ((this.leftInputDown && !this.rightInputDown) || this.forwardInputDown));
        }
    }


    public Item getItemBoat() {
        switch(this.getBoatType()) {
            case OAK:
            default:
                return ModItems.OAK_SAILBOAT_ITEM.get();
            case SPRUCE:
                return ModItems.SPRUCE_SAILBOAT_ITEM.get();
            case BIRCH:
                return ModItems.BRICH_SAILBOAT_ITEM.get();
            case JUNGLE:
                return ModItems.JUNGLE_SAILBOAT_ITEM.get();
            case ACACIA:
                return ModItems.ACACIA_SAILBOAT_ITEM.get();
            case DARK_OAK:
                return ModItems.DARK_OAK_SAILBOAT_ITEM.get();
        }
    }

    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket((Entity)this);
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

        for(int k1 = i; k1 < j; ++k1) {
            for(int l1 = k; l1 < l; ++l1) {
                for(int i2 = i1; i2 < j1; ++i2) {
                    blockpos$mutable.setPos(k1, l1, i2);
                    FluidState fluidstate = this.world.getFluidState(blockpos$mutable);
                    if (fluidstate.isTagged(FluidTags.WATER) && d0 < (double)((float)blockpos$mutable.getY() + fluidstate.getActualHeight(this.world, blockpos$mutable))) {
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

        for(int k1 = i; k1 < j; ++k1) {
            for(int l1 = k; l1 < l; ++l1) {
                for(int i2 = i1; i2 < j1; ++i2) {
                    blockpos$mutable.setPos(k1, l1, i2);
                    FluidState fluidstate = this.world.getFluidState(blockpos$mutable);
                    if (fluidstate.isTagged(FluidTags.WATER)) {
                        float f = (float)l1 + fluidstate.getActualHeight(this.world, blockpos$mutable);
                        this.waterLevel = Math.max((double)f, this.waterLevel);
                        flag |= axisalignedbb.minY < (double)f;
                    }
                }
            }
        }

        return flag;
    }
}
