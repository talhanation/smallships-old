package com.talhanation.smallships.entities.projectile;

import com.talhanation.smallships.Main;
import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class CannonBallEntity extends ProjectileItemEntity {
    public CannonBallEntity(EntityType<CannonBallEntity> entityType, World world) {
        super(entityType, world);
    }

    public CannonBallEntity(FMLPlayMessages.SpawnEntity spawnEntity, World worldIn) {
        this(Main.CANNON_BALL, worldIn);

    }

    public CannonBallEntity(double x, double y, double z, World world) {
        super(Main.CANNON_BALL, x, y, z, world);
    }

    public CannonBallEntity(LivingEntity player, World world) {
        super((Main.CANNON_BALL), player, world);
    }

    protected Item getDefaultItem() {
        return null;
    }

    public void tick() {
        super.tick();
        Vector3d vector3d = this.getDeltaMovement();
        RayTraceResult raytraceresult = ProjectileHelper.getHitResult(this, this::canHitEntity);
        if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
            this.onHit(raytraceresult);
        }

        double d0 = this.getX() + vector3d.x;
        double d1 = this.getY() + vector3d.y;
        double d2 = this.getZ() + vector3d.z;
        this.updateRotation();
        float f = 0.99F;
        float f1 = 0.06F;
        if (this.level.getBlockStates(this.getBoundingBox()).noneMatch(AbstractBlock.AbstractBlockState::isAir)) {
            this.remove();
        } else if (this.isInWaterOrBubble()) {
            this.remove();
        } else {
            this.setDeltaMovement(vector3d.scale((double)0.99F));
            if (!this.isNoGravity()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, (double)-0.06F, 0.0D));
            }

            this.setPos(d0, d1, d2);
        }

        for(int i = 0; i < 7; ++i) {
            double d01 = 0.4D + 0.1D * (double)i;
            level.addParticle(ParticleTypes.HEART, d0,d1, d2, d0 * d01, d1, d2 * d01);
        }
    }

    protected void onHitEntity(EntityRayTraceResult entityRayTraceResult) {
        super.onHitEntity(entityRayTraceResult);
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity) {
            entityRayTraceResult.getEntity().hurt(DamageSource.indirectMobAttack(this, (LivingEntity)entity).setProjectile(), 2.0F);
            entityRayTraceResult.getEntity().stopRiding();
            //entityRayTraceResult.getEntity().level.playLocalSound();
        }

    }

    protected void onHitBlock(BlockRayTraceResult blockRayTraceResult) {
        super.onHitBlock(blockRayTraceResult);
        if (!this.level.isClientSide) {
            this.remove();
            //blockRayTraceResult.getBlockPos().
        }

    }

    protected void defineSynchedData() {
    }

    public IPacket<?> getAddEntityPacket() {
        return new SSpawnObjectPacket(this);
    }

}


