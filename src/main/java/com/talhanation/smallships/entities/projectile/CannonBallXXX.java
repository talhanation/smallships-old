package com.talhanation.smallships.entities.projectile;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.entities.AbstractShipCannon;
import com.talhanation.smallships.init.ModEntityTypes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;

class CannonBall {}/*
        extends SmallShipsProjectile {

    public CannonBall(EntityType<? extends SmallShipsProjectile> cannonBall, World world) {
        super(cannonBall,world);
    }

    @OnlyIn(Dist.CLIENT)
    public CannonBall(World world, double d1, double d2, double d3, double d1p, double d2p, double d3p) {
        this(Main.CANNON_BALL, world);
        this.setOwner(null);
        this.setPos(d1, d2, d3);
        for(int i = 0; i < 7; ++i) {
            double d0 = 0.4D + 0.1D * (double)i;
            world.addParticle(ParticleTypes.SMOKE, d1, d2, d3, d1p * d0, d2p, d3p * d0);
        }

        for(int i = 0; i < 3; ++i) {
            world.addParticle(ParticleTypes.HEART, d1, d2, d3, d1p, d2p, d3p);
        }

        this.setDeltaMovement(d1p, d2p, d3p);
    }

    public CannonBall(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(Main.CANNON_BALL, world);
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
    }

    protected void onHitEntity(EntityRayTraceResult entityRayTraceResult) {
        super.onHitEntity(entityRayTraceResult);
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity) {
            entityRayTraceResult.getEntity().hurt(DamageSource.indirectMobAttack(this, (LivingEntity)entity).setProjectile(), 1.0F);
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
 */

