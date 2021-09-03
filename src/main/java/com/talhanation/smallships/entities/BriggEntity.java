package com.talhanation.smallships.entities;

import com.talhanation.smallships.container.AbstractShipContainer;
import com.talhanation.smallships.entities.sailboats.AbstractBriggEntity;
import com.talhanation.smallships.init.ModEntityTypes;
import com.talhanation.smallships.items.ModItems;
import com.talhanation.smallships.util.BriggItemStackHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.network.FMLPlayMessages;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import javax.xml.crypto.Data;

public class BriggEntity extends AbstractBriggEntity {

    public boolean Cargo_0;
    public boolean Cargo_1;
    public boolean Cargo_2;
    public boolean Cargo_3;

   private static final EntityDataAccessor<Integer> CARGO = SynchedEntityData.defineId(AbstractBriggEntity.class, EntityDataSerializers.INT);

    public BriggEntity(EntityType<? extends AbstractBriggEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
    }

    @Override
    public void tick(){
        super.tick();

        this.getCargo();
        if (0 < this.getCargo()) Cargo_0 = true;
        else Cargo_0 = false;
        if (1 < this.getCargo()) Cargo_1 = true;
        else Cargo_1 = false;
        if (2 < this.getCargo()) Cargo_2 = true;
        else Cargo_2 = false;
        if (3 < this.getCargo()) Cargo_3 = true;
        else Cargo_3 = false;

    }

    protected ItemStackHandler initInventory() {

        return new BriggItemStackHandler<BriggEntity>(54, this) {
            protected void onContentsChanged(int slot) {
                int sigma, tempload = 0;
                for (int i = 0; i < getSlots(); i++) {
                    if (!getStackInSlot(i).isEmpty())
                        tempload++;
                }
                if (tempload > 31) {
                    sigma = 4;
                } else if (tempload > 16) {
                    sigma = 3;
                } else if (tempload > 8) {
                    sigma = 2;
                } else if (tempload > 3) {
                    sigma = 1;
                } else {
                    sigma = 0;
                }
                (this.brigg).getEntityData().set(BriggEntity.CARGO, sigma);
            }
        };
    }

    public BriggEntity(Level worldIn, double x, double y, double z) {
        this(ModEntityTypes.BRIGG_ENTITY.get(), worldIn);
        setPos(x, y, z);
        setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    public BriggEntity(FMLPlayMessages.SpawnEntity spawnEntity, Level worldIn) {
        this(ModEntityTypes.BRIGG_ENTITY.get(), worldIn);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);

        if (itemInHand.getItem() instanceof BannerItem){
           onInteractionWithBanner(itemInHand,player);
           return InteractionResult.SUCCESS;
        }

        else if (itemInHand.getItem() instanceof ShearsItem){
            if (this.getHasBanner()){
                onInteractionWithShears(player);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }

        else if (player.isSecondaryUseActive()) {
            if (this.isVehicle() && !(getControllingPassenger() instanceof Player)){
                    this.ejectPassengers();
                    this.passengerwaittime = 200;
            }
            else {
                if (!(getControllingPassenger() instanceof Player) ) {
                    openContainer(player);
                } return InteractionResult.sidedSuccess(this.level.isClientSide);
            } return InteractionResult.PASS;
        }

        else if (this.outOfControlTicks < 60.0F) {
            if (!this.level.isClientSide) {
                return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;

            } else {
                return InteractionResult.SUCCESS;
            }
        }
        else
            return InteractionResult.PASS;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleEntityEvent(byte id) {
            super.handleEntityEvent(id);
    }

    @Override
    public double getPassengersRidingOffset() {
        return 1.0D;
    }

    @Override
    public void positionRider(Entity passenger) {
        if (hasPassenger(passenger)) {
            double f = -1.5F;
            double d = 0.75F;
            float f1 = (float) ((this.removed ? 0.02D : getPassengersRidingOffset()) + passenger.getMyRidingOffset());
            if (getPassengers().size() == 2) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -1.5F;
                    d = 0.75F;
                } else {
                    f = -1.5F;
                    d = -0.75F;
                }
            } else if (getPassengers().size() == 3) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -1.5F;
                    d = 0.75F;
                } else if (i == 1) {
                    f = -1.5F;
                    d = -0.75F;
                } else {
                    f = -0.5F;
                    d = -0.75F;
                }
            }else if (getPassengers().size() == 4) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -1.5F;
                    d = 0.75F;
                } else if (i == 1) {
                    f = -1.5F;
                    d = -0.75F;
                } else if (i == 2) {
                    f = -0.5F;
                    d = -0.75F;
                } else {
                    f = -0.5F;
                    d = 0.75F;
                }
            }else if (getPassengers().size() == 5) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -1.5F;
                    d = 0.75F;
                } else if (i == 1) {
                    f = -1.5F;
                    d = -0.75F;
                } else if (i == 2) {
                    f = -0.5F;
                    d = -0.75F;
                } else if (i == 3) {
                    f = -0.5F;
                    d = 0.75F;
                } else {
                    f = 0.5F;
                    d = -0.75F;
                }
            }else if (getPassengers().size() == 6) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -1.5F;
                    d = 0.75F;
                } else if (i == 1) {
                    f = -1.5F;
                    d = -0.75F;
                } else if (i == 2) {
                    f = -0.5F;
                    d = -0.75F;
                } else if (i == 3) {
                    f = -0.5F;
                    d = 0.75F;
                } else if(i == 4){
                    f = 0.5F;
                    d = -0.75F;
                } else {
                    f = 0.5F;
                    d = 0.75F;
                }
            }else if (getPassengers().size() == 7) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -1.5F;
                    d = 0.75F;
                } else if (i == 1) {
                    f = -1.5F;
                    d = -0.75F;
                } else if (i == 2) {
                    f = -0.5F;
                    d = -0.75F;
                } else if (i == 3) {
                    f = -0.5F;
                    d = 0.75F;
                } else if (i == 4) {
                    f = 0.5F;
                    d = -0.75F;
                } else if (i == 5) {
                    f = 0.5F;
                    d = 0.75F;
                } else {
                    f = 1.5F;
                    d = 0.75F;
                }
            }else if (getPassengers().size() == 8) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -1.5F;
                    d = 0.75F;
                } else if (i == 1) {
                    f = -1.5F;
                    d = -0.75F;
                } else if (i == 2) {
                    f = -0.5F;
                    d = -0.75F;
                } else if (i == 3) {
                    f = -0.5F;
                    d = 0.75F;
                } else if (i == 4) {
                    f = 0.5F;
                    d = -0.75F;
                } else if (i == 5) {
                    f = 0.5F;
                    d = 0.75F;
                } else if (i == 6){
                    f = 1.5F;
                    d = -0.75F;
                }else {
                    f = 1.5F;
                    d = 0.75F;
                }
            }else if (getPassengers().size() == 9) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -1.5F;
                    d = 0.75F;
                } else if (i == 1) {
                    f = -1.5F;
                    d = -0.75F;
                } else if (i == 2) {
                    f = -0.5F;
                    d = -0.75F;
                } else if (i == 3) {
                    f = -0.5F;
                    d = 0.75F;
                } else if (i == 4) {
                    f = 0.5F;
                    d = -0.75F;
                } else if (i == 5) {
                    f = 0.5F;
                    d = 0.75F;
                } else if (i == 6){
                    f = 1.5F;
                    d = -0.75F;
                }else if (i == 7){
                    f = 1.5F;
                    d = 0.75F;
                }else {
                    f = 2.75F;
                    d = 0.0F;
                }
            }else if (getPassengers().size() == 10) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -1.5F;
                    d = 0.75F;
                } else if (i == 1) {
                    f = -1.5F;
                    d = -0.75F;
                } else if (i == 2) {
                    f = -0.5F;
                    d = -0.75F;
                } else if (i == 3) {
                    f = -0.5F;
                    d = 0.75F;
                } else if (i == 4) {
                    f = 0.5F;
                    d = -0.75F;
                } else if (i == 5) {
                    f = 0.5F;
                    d = 0.75F;
                } else if (i == 6){
                    f = 1.5F;
                    d = -0.75F;
                }else if (i == 7){
                    f = 1.5F;
                    d = 0.75F;
                }else if (i == 8){
                    f = 2.75F;
                    d = 0.5F;
                }else {
                    f = 2.75F;
                    d = -0.5F;
                }
            }
            f = f - 0.5;
        if (passenger instanceof Animal)
            d = (float)(d -0.15D);
        Vec3 vector3d = (new Vec3((double)f, 0.0D, 0.0D + d)).yRot(-this.getYRot() * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
        passenger.setPos(this.getX() + vector3d.x, this.getY() + (double)f1, + this.getZ() + vector3d.z);
        passenger.setYRot(passenger.getYRot() + this.deltaRotation);
        passenger.setYHeadRot(passenger.getYHeadRot() + this.deltaRotation);
        clampRotation(passenger);
        }

    }

    public int getCargo() {
        return this.entityData.get(CARGO);
    }

    @Override
    public void openContainer(Player player) {
            player.openMenu(new MenuProvider(){
                @Override
                public Component getDisplayName() {
                    return this.getDisplayName();
                }

                @Nullable
                @Override
                public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player player) {
                    return new AbstractShipContainer(i,BriggEntity.this, playerInventory);
                }
            });
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CARGO, 0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(CARGO, compound.getInt("Cargo"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Cargo", this.entityData.get(CARGO));
    }

    public Item getItemBoat() {
        switch (this.getBoatType()) {
            case OAK:
            default:
                return ModItems.OAK_BRIGG_ITEM.get();
            case SPRUCE:
                return ModItems.SPRUCE_BRIGG_ITEM.get();
            case BIRCH:
                return ModItems.BIRCH_BRIGG_ITEM.get();
            case JUNGLE:
                return ModItems.JUNGLE_BRIGG_ITEM.get();
            case ACACIA:
                return ModItems.ACACIA_BRIGG_ITEM.get();
            case DARK_OAK:
                return ModItems.DARK_OAK_BRIGG_ITEM.get();

            //BOP
            case BOP_CHERRY:
                return ModItems.BOP_CHERRY_BRIGG_ITEM.get();
            case BOP_DEAD:
                return ModItems.BOP_DEAD_BRIGG_ITEM.get();
            case BOP_FIR:
                return ModItems.BOP_FIR_BRIGG_ITEM.get();
            case BOP_HELLBARK:
                return ModItems.BOP_HELLBARK_BRIGG_ITEM.get();
            case BOP_JACARANDA:
                return ModItems.BOP_JACARANDA_BRIGG_ITEM.get();
            case BOP_MAGIC:
                return ModItems.BOP_MAGIC_BRIGG_ITEM.get();
            case BOP_MAHOGANY:
                return ModItems.BOP_MAHOGANY_BRIGG_ITEM.get();
            case BOP_PALM:
                return ModItems.BOP_PALM_BRIGG_ITEM.get();
            case BOP_REDWOOD:
                return ModItems.BOP_REDWOOD_BRIGG_ITEM.get();
            case BOP_UMBRAN:
                return ModItems.BOP_UMBRAN_BRIGG_ITEM.get();
            case BOP_WILLOW:
                return ModItems.BOP_WILLOW_BRIGG_ITEM.get();
            //LOTR
            case LOTR_APPLE:
                return ModItems.LOTR_APPLE_BRIGG_ITEM.get();
            case LOTR_ASPEN:
                return ModItems.LOTR_ASPEN_BRIGG_ITEM.get();
            case LOTR_BEECH:
                return ModItems.LOTR_BEECH_BRIGG_ITEM.get();
            case LOTR_CEDAR:
                return ModItems.LOTR_CEDAR_BRIGG_ITEM.get();
            case LOTR_CHERRY:
                return ModItems.LOTR_CHERRY_BRIGG_ITEM.get();
            case LOTR_CHARRED:
                return ModItems.LOTR_CHARRED_BRIGG_ITEM.get();
            case LOTR_CYPRESS:
                return ModItems.LOTR_CYPRESS_BRIGG_ITEM.get();
            case LOTR_FIR:
                return ModItems.LOTR_FIR_BRIGG_ITEM.get();
            case LOTR_GREEN_OAK:
                return ModItems.LOTR_GREEN_OAK_BRIGG_ITEM.get();
            case LOTR_HOLLY:
                return ModItems.LOTR_HOLLY_BRIGG_ITEM.get();
            case LOTR_LAIRELOSSE:
                return ModItems.LOTR_LAIRELOSSE_BRIGG_ITEM.get();
            case LOTR_LARCH:
                return ModItems.LOTR_LARCH_BRIGG_ITEM.get();
            case LOTR_LEBETHRON:
                return ModItems.LOTR_LEBETHRON_BRIGG_ITEM.get();
            case LOTR_MALLORN:
                return ModItems.LOTR_MALLORN_BRIGG_ITEM.get();
            case LOTR_MAPLE:
                return ModItems.LOTR_MAPLE_BRIGG_ITEM.get();
            case LOTR_MIRK_OAK:
                return ModItems.LOTR_MIRK_OAK_BRIGG_ITEM.get();
            case LOTR_PEAR:
                return ModItems.LOTR_PEAR_BRIGG_ITEM.get();
            case LOTR_PINE:
                return ModItems.LOTR_PINE_BRIGG_ITEM.get();
            case LOTR_ROTTEN:
                return ModItems.LOTR_ROTTEN_BRIGG_ITEM.get();

            //ENVI//
            case ENVI_CHERRY:
                return ModItems.ENVI_CHERRY_BRIGG_ITEM.get();
            case ENVI_WILLOW:
                return ModItems.ENVI_WILLOW_BRIGG_ITEM.get();
            case ENVI_WISTERIA:
                return ModItems.ENVI_WISTERIA_BRIGG_ITEM.get();
        }
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return (getPassengers().size() < 10);
    }

}
