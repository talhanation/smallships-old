package com.talhanation.smallships.entities;

import com.talhanation.smallships.entities.sailboats.AbstractGalleyEntity;
import com.talhanation.smallships.inventory.GalleyContainer;
import com.talhanation.smallships.items.ModItems;
import com.talhanation.smallships.util.GalleyItemStackHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BannerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemStackHandler;

public class GalleyEntity extends AbstractGalleyEntity {
    public boolean Cargo_0 = true;
    public boolean Cargo_1 = true;

    private static final DataParameter<Integer> CARGO = EntityDataManager.defineId(AbstractGalleyEntity.class, DataSerializers.INT);

    public GalleyEntity(EntityType<? extends AbstractGalleyEntity> entityType, World worldIn) {
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

    }

    @Override
    public ActionResultType interact(PlayerEntity player, Hand hand) {

        ItemStack itemInHand = player.getItemInHand(hand);
        if (itemInHand.getItem() instanceof BannerItem){
            if (onInteractionWithBanner(itemInHand, player))
                return ActionResultType.SUCCESS;
            return ActionResultType.CONSUME;
        }
        else if (player.isSecondaryUseActive()) {
            if (this.isVehicle() && !(getControllingPassenger() instanceof net.minecraft.entity.player.PlayerEntity)){
                this.ejectPassengers();
                this.passengerwaittime = 200;
            }
            else {
                if (!(getControllingPassenger() instanceof net.minecraft.entity.player.PlayerEntity)) {
                   openContainer(player);
                } return ActionResultType.sidedSuccess(this.level.isClientSide);
            } return ActionResultType.PASS;
        } else if (this.outOfControlTicks < 60.0F) {
            if (!this.level.isClientSide) {
                return player.startRiding(this) ? ActionResultType.CONSUME : ActionResultType.PASS;

            } else {
                return ActionResultType.SUCCESS;
            }
        } else {
            return ActionResultType.PASS;
        }
    }

    public GalleyEntity(World worldIn, double x, double y, double z) {
        this((EntityType<? extends AbstractGalleyEntity>) ModEntityTypes.GALLEY_ENTITY.get(), worldIn);
        setPos(x, y, z);
        setDeltaMovement(Vector3d.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0.75D;
    }

    @Override
    public void positionRider(Entity passenger) {
        if (hasPassenger(passenger)) {
            float f = -2.75F; //driver x pos
            float d = 0.0F;   //driver z pos
            float f1 = (float) ((this.removed ? 0.02D : getPassengersRidingOffset()) + passenger.getMyRidingOffset());
            if (getPassengers().size() == 2) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -2.75F;
                    d = 0.0F;
                } else {
                    f = -1.5F;
                    d = -0.75F;
                }
            } else if (getPassengers().size() == 3) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -2.75F;
                    d = 0.0F;
                } else if (i == 1) {
                    f = -1.5F;
                    d = -0.75F;
                } else {
                    f = -1.5F;
                    d = 0.75F;
                }
            }else if (getPassengers().size() == 4) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -2.75F;
                    d = 0.0F;
                } else if (i == 1) {
                    f = -1.5F;
                    d = -0.75F;
                } else if (i == 2) {
                    f = -1.5F;
                    d = 0.75F;
                } else {
                    f = 0.0F;
                    d = 0.75F;
                }
            }else if (getPassengers().size() == 5) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -2.75F;
                    d = 0.0F;
                } else if (i == 1) {
                    f = -1.5F;
                    d = -0.75F;
                } else if (i == 2) {
                    f = -1.5F;
                    d = 0.75F;
                } else if (i == 3) {
                    f = 0.0F;
                    d = 0.75F;
                } else {
                    f = 0.0F;
                    d = -0.75F;
                }
            }else if (getPassengers().size() == 6) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -2.75F;
                    d = 0.0F;
                } else if (i == 1) {
                    f = -1.5F;
                    d = -0.75F;
                } else if (i == 2) {
                    f = -1.5F;
                    d = 0.75F;
                } else if (i == 3) {
                    f = 0.0F;
                    d = 0.75F;
                } else if(i == 4){
                    f = 0.0F;
                    d = -0.75F;
                } else {
                    f = 1.5F;
                    d = -0.75F;
                }
            }else if (getPassengers().size() == 7) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -2.75F;
                    d = 0.0F;
                } else if (i == 1) {
                    f = -1.5F;
                    d = -0.75F;
                } else if (i == 2) {
                    f = -1.5F;
                    d = 0.75F;
                } else if (i == 3) {
                    f = 0.0F;
                    d = 0.75F;
                } else if (i == 4) {
                    f = 0.0F;
                    d = -0.75F;
                } else if (i == 5) {
                    f = 1.5F;
                    d = -0.75F;
                } else {
                    f = 1.5F;
                    d = 0.75F;
                }
            }else if (getPassengers().size() == 8) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -2.75F;
                    d = 0.0F;
                } else if (i == 1) {
                    f = -1.5F;
                    d = -0.75F;
                } else if (i == 2) {
                    f = -1.5F;
                    d = 0.75F;
                } else if (i == 3) {
                    f = 0.0F;
                    d = 0.75F;
                } else if (i == 4) {
                    f = 0.0F;
                    d = -0.75F;
                } else if (i == 5) {
                    f = 1.5F;
                    d = -0.75F;
                } else if (i == 6){
                    f = 1.5F;
                    d = 0.75F;
                }else {
                    f = 2.25F;
                    d = 0.0F;
                }
            } else if (getPassengers().size() == 9) {
                int i = getPassengers().indexOf(passenger);
                if        (i == 0) {
                    f = -2.75F;
                    d = 0.0F;
                } else if (i == 1) {
                    f = -1.25F;
                    d = -0.75F;
                } else if (i == 2) {
                    f = -1.25F;
                    d = 0.75F;
                } else if (i == 3) {
                    f = -0.25F;
                    d = 0.75F;
                } else if (i == 4) {
                    f = -0.25F;
                    d = -0.75F;
                } else if (i == 5) {
                    f = 1.25F;
                    d = -0.75F;
                } else if (i == 6) {
                    f = 1.25F;
                    d = 0.75F;
                } else if (i == 7) {
                    f = 2.25F;
                    d = 0.75F;
                } else{
                    f = 2.25F;
                    d = -0.75F;
                }
            }
            Vector3d vector3d = (new Vector3d((double) f, 0.0D, d)).yRot(-this.yRot * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
            passenger.setPos(this.getX() + vector3d.x, this.getY() + (double) f1, +this.getZ() + vector3d.z);
            passenger.yRot += this.deltaRotation;
            clampRotation(passenger);
        }
    }

    public Item getItemBoat() {
        switch (this.getBoatType()) {
            case OAK:
            default:
                return ModItems.OAK_GALLEY_ITEM.get();
            case SPRUCE:
                return ModItems.SPRUCE_GALLEY_ITEM.get();
            case BIRCH:
                return ModItems.BIRCH_GALLEY_ITEM.get();
            case JUNGLE:
                return ModItems.JUNGLE_GALLEY_ITEM.get();
            case ACACIA:
                return ModItems.ACACIA_GALLEY_ITEM.get();
            case DARK_OAK:
                return ModItems.DARK_OAK_GALLEY_ITEM.get();
            //BOP
            case BOP_CHERRY:
                return ModItems.BOP_CHERRY_GALLEY_ITEM.get();
            case BOP_DEAD:
                return ModItems.BOP_DEAD_GALLEY_ITEM.get();
            case BOP_FIR:
                return ModItems.BOP_FIR_GALLEY_ITEM.get();
            case BOP_HELLBARK:
                return ModItems.BOP_HELLBARK_GALLEY_ITEM.get();
            case BOP_JACARANDA:
                return ModItems.BOP_JACARANDA_GALLEY_ITEM.get();
            case BOP_MAGIC:
                return ModItems.BOP_MAGIC_GALLEY_ITEM.get();
            case BOP_MAHOGANY:
                return ModItems.BOP_MAHOGANY_GALLEY_ITEM.get();
            case BOP_PALM:
                return ModItems.BOP_PALM_GALLEY_ITEM.get();
            case BOP_REDWOOD:
                return ModItems.BOP_REDWOOD_GALLEY_ITEM.get();
            case BOP_UMBRAN:
                return ModItems.BOP_UMBRAN_GALLEY_ITEM.get();
            case BOP_WILLOW:
                return ModItems.BOP_WILLOW_GALLEY_ITEM.get();
            //LOTR
            case LOTR_APPLE:
                return ModItems.LOTR_APPLE_GALLEY_ITEM.get();
            case LOTR_ASPEN:
                return ModItems.LOTR_ASPEN_GALLEY_ITEM.get();
            case LOTR_BEECH:
                return ModItems.LOTR_BEECH_GALLEY_ITEM.get();
            case LOTR_CEDAR:
                return ModItems.LOTR_CEDAR_GALLEY_ITEM.get();
            case LOTR_CHERRY:
                return ModItems.LOTR_CHERRY_GALLEY_ITEM.get();
            case LOTR_CHARRED:
                return ModItems.LOTR_CHARRED_GALLEY_ITEM.get();
            case LOTR_CYPRESS:
                return ModItems.LOTR_CYPRESS_GALLEY_ITEM.get();
            case LOTR_FIR:
                return ModItems.LOTR_FIR_GALLEY_ITEM.get();
            case LOTR_GREEN_OAK:
                return ModItems.LOTR_GREEN_OAK_GALLEY_ITEM.get();
            case LOTR_HOLLY:
                return ModItems.LOTR_HOLLY_GALLEY_ITEM.get();
            case LOTR_LAIRELOSSE:
                return ModItems.LOTR_LAIRELOSSE_GALLEY_ITEM.get();
            case LOTR_LARCH:
                return ModItems.LOTR_LARCH_GALLEY_ITEM.get();
            case LOTR_LEBETHRON:
                return ModItems.LOTR_LEBETHRON_GALLEY_ITEM.get();
            case LOTR_MALLORN:
                return ModItems.LOTR_MALLORN_GALLEY_ITEM.get();
            case LOTR_MAPLE:
                return ModItems.LOTR_MAPLE_GALLEY_ITEM.get();
            case LOTR_MIRK_OAK:
                return ModItems.LOTR_MIRK_OAK_GALLEY_ITEM.get();
            case LOTR_PEAR:
                return ModItems.LOTR_PEAR_GALLEY_ITEM.get();
            case LOTR_PINE:
                return ModItems.LOTR_PINE_GALLEY_ITEM.get();
            case LOTR_ROTTEN:
                return ModItems.LOTR_ROTTEN_GALLEY_ITEM.get();

            //ENVI//
            case ENVI_CHERRY:
                return ModItems.ENVI_CHERRY_GALLEY_ITEM.get();
            case ENVI_WILLOW:
                return ModItems.ENVI_WILLOW_GALLEY_ITEM.get();
            case ENVI_WISTERIA:
                return ModItems.ENVI_WISTERIA_GALLEY_ITEM.get();
        }

    }

    protected ItemStackHandler initInventory() {
        return new GalleyItemStackHandler<GalleyEntity>(9, this) {
            protected void onContentsChanged(int slot) {
                int sigma, tempload = 0;
                for (int i = 0; i < getSlots(); i++) {
                    if (!getStackInSlot(i).isEmpty())
                        tempload++;
                }
                if (tempload > 7) {
                    sigma = 2;
                } else if (tempload > 3) {
                    sigma = 1;
                } else {
                    sigma = 0;
                }
                (this.galley).getEntityData().set(GalleyEntity.CARGO, Integer.valueOf(sigma));
            }
        };
    }

    public int getCargo() {
        return this.entityData.get(CARGO);
    }

    public void openContainer(PlayerEntity player) {
        player.openMenu(new SimpleNamedContainerProvider((id, inv, plyr) -> new GalleyContainer(id, inv, this), getDisplayName()));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CARGO, 0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(CARGO, compound.getInt("Cargo"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Cargo", (Integer) this.entityData.get(CARGO));
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return (getPassengers().size() < 9);
    }
}
