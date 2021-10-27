package com.talhanation.smallships.entities.projectile;

import com.talhanation.smallships.entities.AbstractShipCannon;
import com.talhanation.smallships.init.ModEntityTypes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
/*
public class CannonBall extends ProjectileEntity {

    public CannonBall(EntityType<? extends CannonBall> type, World world) {
        super(type, world);
    }

    public CannonBall(World world, AbstractShipCannon p_i47273_2_) {
        this(ModEntityTypes.CANNON_BALL, world);
        super.setOwner(p_i47273_2_.getDriver());
        this.setPos(p_i47273_2_.getX() - (double)(p_i47273_2_.getBbWidth() + 1.0F) * 0.5D * (double) MathHelper.sin(p_i47273_2_.yBodyRot * ((float)Math.PI / 180F)), p_i47273_2_.getEyeY() - (double)0.1F, p_i47273_2_.getZ() + (double)(p_i47273_2_.getBbWidth() + 1.0F) * 0.5D * (double)MathHelper.cos(p_i47273_2_.yBodyRot * ((float)Math.PI / 180F)));
    }

    @OnlyIn(Dist.CLIENT)
    public CannonBall(World p_i47274_1_, double p_i47274_2_, double p_i47274_4_, double p_i47274_6_, double p_i47274_8_, double p_i47274_10_, double p_i47274_12_) {
        this(ModEntityTypes.CANNON_BALL, p_i47274_1_);
        this.setPos(p_i47274_2_, p_i47274_4_, p_i47274_6_);

        for(int i = 0; i < 7; ++i) {
            double d0 = 0.4D + 0.1D * (double)i;
            p_i47274_1_.addParticle(ParticleTypes.SPIT, p_i47274_2_, p_i47274_4_, p_i47274_6_, p_i47274_8_ * d0, p_i47274_10_, p_i47274_12_ * d0);
        }

        this.setDeltaMovement(p_i47274_8_, p_i47274_10_, p_i47274_12_);
    }

    public CannonBall(RegistryObject<EntityType<CannonBall>> cannonBall, World world) {
        super();
    }


    public void tick() {
        super.tick();
        Vector3d vector3d = this.getDeltaMovement();
        RayTraceResult raytraceresult = ProjectileHelper.getHitResult(this, this::canHitEntity);
        if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
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

    protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
        super.onHitEntity(p_213868_1_);
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity) {
            p_213868_1_.getEntity().hurt(DamageSource.indirectMobAttack(this, (LivingEntity)entity).setProjectile(), 1.0F);
        }

    }

    protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
        super.onHitBlock(p_230299_1_);
        if (!this.level.isClientSide) {
            this.remove();
        }

    }

    protected void defineSynchedData() {
    }

    public IPacket<?> getAddEntityPacket() {
        return new SSpawnObjectPacket(this);
    }
}
*/