package com.talhanation.smallships.entities;

import com.talhanation.smallships.init.ModEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CogEntityParts extends Entity {

    private static final DataParameter<Boolean> TAIL = EntityDataManager.defineId(CogEntityParts.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> BODYINDEX = EntityDataManager.defineId(CogEntityParts.class, DataSerializers.INT);
    private static final DataParameter<Optional<UUID>> PARENT_UUID = EntityDataManager.defineId(CogEntityParts.class, DataSerializers.OPTIONAL_UUID);
    public EntitySize multipartSize;
    protected float radius;
    protected float angleYaw;
    protected float offsetY;
    protected float damageMultiplier = 1;

    public CogEntityParts(EntityType<? extends CogEntityParts> entityType, World worldIn) {
        super(entityType, worldIn);
        multipartSize = entityType.getDimensions();
    }

    public CogEntityParts(RegistryObject<EntityType<CogEntityParts>> cogPart, Entity partParent, float radius, int angleYaw, int offsetY) {
        super(ModEntityTypes.COG_PART.get(), partParent.level);
        this.setParent(partParent);
        this.radius = radius;
        this.angleYaw = (angleYaw + 90.0F) * ((float) Math.PI / 180.0F);
        this.offsetY = offsetY;
    }


    public boolean startRiding(Entity entityIn) {
        if(!(entityIn instanceof AbstractMinecartEntity || entityIn instanceof BoatEntity)){
            return super.startRiding(entityIn);
        }
        return false;
    }


    @Override
    public net.minecraft.entity.Entity getEntity() {
        return this;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(PARENT_UUID, Optional.empty());
        this.entityData.define(TAIL, false);
        this.entityData.define(BODYINDEX, 0);
    }

    ////////////////////////////////////SAVE////////////////////////////////////

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        if (nbt.hasUUID("ParentUUID")) {
            this.setParentId(nbt.getUUID("ParentUUID"));
        }
        this.setTail(nbt.getBoolean("TailPart"));
        this.setBodyIndex(nbt.getInt("BodyIndex"));
        this.angleYaw = nbt.getFloat("PartAngle");
        this.radius = nbt.getFloat("PartRadius");
    }



    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        if (this.getParentId() != null) {
            nbt.putUUID("ParentUUID", this.getParentId());
        }
        nbt.putBoolean("TailPart", isTail());
        nbt.putInt("BodyIndex", getBodyIndex());
        nbt.putFloat("PartAngle", angleYaw);
        nbt.putFloat("PartRadius", radius);
    }

    ////////////////////////////////////TICK////////////////////////////////////

    @Override
    public void tick() {
        Entity parent = getParent();
        if (parent != null) {
            if (this.tickCount > 20) {
                refreshDimensions();
                this.setNoGravity(true);

                double d1 = parent.xOld + this.radius * Math.cos(parent.xRotO * (Math.PI / 180.0F) + this.angleYaw);
                double d2 = parent.yOld + this.offsetY;
                double d3 = parent.zOld + this.radius * Math.sin(parent.xRotO * (Math.PI / 180.0F) + this.angleYaw);
                this.setPos(d1, d2, d3);

                double f0 = parent.getX() - this.getX();
                double f1 = parent.getY() - this.getY();
                double f2 = parent.getZ() - this.getZ();
                float f3 = -((float) (MathHelper.atan2(f1, MathHelper.sqrt(f0 * f0 + f2 * f2)) * (double) (180F / (float) Math.PI)));
                this.yRot = this.limitAngle(this.yRot, f3, 5.0F);
                this.markHurt();
                this.xRot = parent.xRotO;
                this.offsetY = this.yRotO;
                /*
                if (parent instanceof LivingEntity) {
                    if(!world.isRemote && (((LivingEntity) parent).hurtTime > 0 || ((LivingEntity) parent).deathTime > 0)){
                        AlexsMobs.sendMSGToAll(new MessageHurtMultipart(this.getEntityId(), parent.getEntityId(), 0));
                        this.hurtTime = ((LivingEntity) parent).hurtTime;
                        this.deathTime = ((LivingEntity) parent).deathTime;
                    }
                }

                */
                this.collideWithNearbyEntities();
                if (parent.removed) {
                    this.remove();
                }
            }
        }
        super.tick();

    }

    ////////////////////////////////////GET////////////////////////////////////

    @Nullable
    public UUID getParentId() {
        return this.entityData.get(PARENT_UUID).orElse(null);
    }

    public Entity getParent() {
        UUID id = getParentId();
        if (id != null && !level.isClientSide) {
            return ((ServerWorld) level).getEntity(id);
        }
        return null;
    }

    public int getBodyIndex() {
        return this.entityData.get(BODYINDEX);
    }

    ////////////////////////////////////SET////////////////////////////////////

    public void setParentId(@Nullable UUID uniqueId) {
        this.entityData.set(PARENT_UUID, Optional.ofNullable(uniqueId));
    }

    public void setInitialPartPos(Entity parent) {
        this.setPos(parent.xOld + this.radius * Math.cos(parent.yRot * (Math.PI / 180.0F) + this.angleYaw), parent.yOld + this.offsetY, parent.zOld + this.radius * Math.sin(parent.yRot * (Math.PI / 180.0F) + this.angleYaw));
    }

    public void setParent(Entity entity) {
        this.setParentId(entity.getUUID());
    }

    public void setBodyIndex(int index) {
        this.entityData.set(BODYINDEX, index);
    }

    public void setTail(boolean tail) {
        this.entityData.set(TAIL, tail);
    }

    ///////////////////////////////////BOOL////////////////////////////////////

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    public boolean isTail() {
        return this.entityData.get(TAIL);
    }

    public boolean shouldNotExist() {
        Entity parent = getParent();
        return !parent.isAlive();
    }

    public boolean shouldContinuePersisting() {
        return isAddedToWorld() || this.removed;
    }

    ///////////////////////////////////OTHER FUNCTIONS////////////////////////////////////

    public void collideWithNearbyEntities() {
        List<Entity> entities = this.level.getEntities(this, this.getBoundingBox().inflate(0.20000000298023224D, 0.0D, 0.20000000298023224D));
        Entity parent = this.getParent();
        if (parent != null) {
            entities.stream().filter(entity -> entity != parent && !(entity instanceof CogEntityParts) && entity.isPushable()).forEach(entity -> entity.canCollideWith(parent));

        }
    }
    @Override
    public ActionResultType interact(PlayerEntity player, Hand hand) {
        Entity parent = getParent();

        return parent != null ? parent.interact(player, hand) : ActionResultType.PASS;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket((Entity) this);
    }

    /*
    public void onAttackedFromServer(LivingEntity parent, float damage) {
        if (parent.deathTime > 0) {
            this.deathTime = parent.deathTime;
        }
        if (parent.hurtTime > 0) {
            this.hurtTime = parent.hurtTime;
        }
    }

     */

    protected float limitAngle(float sourceAngle, float targetAngle, float maximumChange) {
        float f = MathHelper.wrapDegrees(targetAngle - sourceAngle);
        if (f > maximumChange) {
            f = maximumChange;
        }

        if (f < -maximumChange) {
            f = -maximumChange;
        }

        float f1 = sourceAngle + f;
        if (f1 < 0.0F) {
            f1 += 360.0F;
        } else if (f1 > 360.0F) {
            f1 -= 360.0F;
        }

        return f1;
    }
}
