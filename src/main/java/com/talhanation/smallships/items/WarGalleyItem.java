package com.talhanation.smallships.items;

import com.talhanation.smallships.entities.TNBoatEntity;
import com.talhanation.smallships.entities.WarGalleyEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;


public class WarGalleyItem extends Item {
    private static final Predicate<Entity> X = EntitySelector.NO_SPECTATORS.and(Entity::canBeCollidedWith);
    private final WarGalleyEntity.Type type;

    public WarGalleyItem(WarGalleyEntity.Type typeIn, Properties properties) {
        super(properties);
        this.type = typeIn;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        HitResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.ANY);
        if (raytraceresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            Vec3 vector3d = playerIn.getViewVector(1.0F);
            double d0 = 5.0D;
            List<Entity> list = worldIn.getEntities(playerIn, playerIn.getBoundingBox().expandTowards(vector3d.scale(5.0D)).inflate(1.0D), X);
            if (!list.isEmpty()) {
                Vec3 vector3d1 = playerIn.getEyePosition(1.0F);

                for(Entity entity : list) {
                    AABB axisalignedbb = entity.getBoundingBox().inflate((double)entity.getPickRadius());
                    if (axisalignedbb.contains(vector3d1)) {
                        return InteractionResultHolder.pass(itemstack);
                    }
                }
            }

            if (raytraceresult.getType() == HitResult.Type.BLOCK) {
                WarGalleyEntity boatentity = new WarGalleyEntity(worldIn, raytraceresult.getLocation().x, raytraceresult.getLocation().y, raytraceresult.getLocation().z);
                boatentity.setBoatType(this.type);
                boatentity.setYRot(playerIn.getYRot() + 90F);
                if (!worldIn.noCollision(boatentity, boatentity.getBoundingBox().inflate(-0.1D))) {
                    return InteractionResultHolder.fail(itemstack);
                } else {
                    if (!worldIn.isClientSide) {
                        worldIn.addFreshEntity(boatentity);
                        if (boatentity.getBoatStatus().equals(TNBoatEntity.Status.IN_WATER)) {
                            worldIn.playSound(null, boatentity.getX(), boatentity.getY(), boatentity.getZ(), SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.BLOCKS, 0.75F, 0.8F);
                            worldIn.playSound(null, boatentity.getX(), boatentity.getY(), boatentity.getZ(), SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.BLOCKS, 0.9F, 0.8F);
                            worldIn.playSound(null, boatentity.getX(), boatentity.getY(), boatentity.getZ(), SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundSource.BLOCKS, 0.85F, 0.8F);
                        }
                        if (!playerIn.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                    }

                    playerIn.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());
                }
            } else {
                return InteractionResultHolder.pass(itemstack);
            }
        }
    }

}
