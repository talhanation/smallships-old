package com.talhanation.smallships.entities.projectile;


import java.util.UUID;
import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class SmallShipsProjectile extends Entity {
    private UUID ownerUUID;
    private int ownerNetworkId;
    private boolean leftOwner;

    public SmallShipsProjectile(EntityType<? extends SmallShipsProjectile>  type, World world) {
        super(type, world);
    }


    public void setOwner(@Nullable Entity entity) {
        if (entity != null) {
            this.ownerUUID = entity.getUUID();
            this.ownerNetworkId = entity.getId();
        }
    }

    @Nullable
    public Entity getOwner() {
        if (this.ownerUUID != null && this.level instanceof ServerWorld) {
            return ((ServerWorld)this.level).getEntity(this.ownerUUID);
        } else {
            return this.ownerNetworkId != 0 ? this.level.getEntity(this.ownerNetworkId) : null;
        }
    }

    protected void addAdditionalSaveData(CompoundNBT nbt) {
        if (this.ownerUUID != null) {
            nbt.putUUID("Owner", this.ownerUUID);
        }

        if (this.leftOwner) {
            nbt.putBoolean("LeftOwner", true);
        }

    }

    protected void readAdditionalSaveData(CompoundNBT nbt) {
        if (nbt.hasUUID("Owner")) {
            this.ownerUUID = nbt.getUUID("Owner");
        }

        this.leftOwner = nbt.getBoolean("LeftOwner");
    }

    public void tick() {
        if (!this.leftOwner) {
            this.leftOwner = this.checkLeftOwner();
        }

        super.tick();
    }

    private boolean checkLeftOwner() {
        Entity entity = this.getOwner();
        if (entity != null) {
            for(Entity entity1 : this.level.getEntities(this, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), (p_234613_0_) -> {
                return !p_234613_0_.isSpectator() && p_234613_0_.isPickable();
            })) {
                if (entity1.getRootVehicle() == entity.getRootVehicle()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void shoot(double d1, double d2, double d3, float f1, float f2) {
        Vector3d vector3d = (new Vector3d(d1, d2, d3)).normalize().add(this.random.nextGaussian() * (double)0.0075F * (double)f1, this.random.nextGaussian() * (double)0.0075F * (double)f1, this.random.nextGaussian() * (double)0.0075F * (double)f1).scale((double)f1);
        this.setDeltaMovement(vector3d);
        float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
        this.yRot = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI));
        this.xRot = (float)(MathHelper.atan2(vector3d.y, (double)f) * (double)(180F / (float)Math.PI));
        this.yRotO = this.yRot;
        this.xRotO = this.xRot;
    }


    protected void onHit(RayTraceResult p_70227_1_) {
        RayTraceResult.Type raytraceresult$type = p_70227_1_.getType();
        if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
            this.onHitEntity((EntityRayTraceResult)p_70227_1_);
        } else if (raytraceresult$type == RayTraceResult.Type.BLOCK) {
            this.onHitBlock((BlockRayTraceResult)p_70227_1_);
        }

    }

    protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
    }

    protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
        BlockState blockstate = this.level.getBlockState(p_230299_1_.getBlockPos());
        //blockstate.onProjectileHit(this.level, blockstate, p_230299_1_, );
    }

    @OnlyIn(Dist.CLIENT)
    public void lerpMotion(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
        this.setDeltaMovement(p_70016_1_, p_70016_3_, p_70016_5_);
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            float f = MathHelper.sqrt(p_70016_1_ * p_70016_1_ + p_70016_5_ * p_70016_5_);
            this.xRot = (float)(MathHelper.atan2(p_70016_3_, (double)f) * (double)(180F / (float)Math.PI));
            this.yRot = (float)(MathHelper.atan2(p_70016_1_, p_70016_5_) * (double)(180F / (float)Math.PI));
            this.xRotO = this.xRot;
            this.yRotO = this.yRot;
            this.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, this.xRot);
        }

    }

    protected boolean canHitEntity(Entity entity) {
        if (!entity.isSpectator() && entity.isAlive() && entity.isPickable()) {
            Entity owner = this.getOwner();
            return owner == null || this.leftOwner || !entity.isPassengerOfSameVehicle(entity);
        } else {
            return false;
        }
    }

    protected void updateRotation() {
        Vector3d vector3d = this.getDeltaMovement();
        float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
        this.xRot = lerpRotation(this.xRotO, (float)(MathHelper.atan2(vector3d.y, (double)f) * (double)(180F / (float)Math.PI)));
        this.yRot = lerpRotation(this.yRotO, (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI)));
    }

    protected static float lerpRotation(float x, float y) {
        while(y - x < -180.0F) {
            x -= 360.0F;
        }

        while(y - x >= 180.0F) {
            x += 360.0F;
        }

        return MathHelper.lerp(0.2F, x, y);
    }
}