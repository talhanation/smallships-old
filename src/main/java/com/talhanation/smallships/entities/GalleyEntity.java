package com.talhanation.smallships.entities;

import com.talhanation.smallships.init.ModEntityTypes;
import com.talhanation.smallships.inventory.GalleyContainer;
import com.talhanation.smallships.items.ModItems;
import com.talhanation.smallships.util.GalleyItemStackHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemStackHandler;

public class GalleyEntity extends AbstractGalleyEntity {
    public boolean Cargo_0 = true;
    public boolean Cargo_1 = true;

    private static final DataParameter<Integer> CARGO = EntityDataManager.createKey(AbstractGalleyEntity.class, DataSerializers.VARINT);

    public GalleyEntity(EntityType<? extends AbstractGalleyEntity> entityType, World worldIn) {
        super(entityType, worldIn);
    }

    public void tick(){
        super.tick();
        this.getCargo();
        if (0 < this.getCargo()) Cargo_0 = true;
        else Cargo_0 = false;
        if (1 < this.getCargo()) Cargo_1 = true;
        else Cargo_1 = false;

    }


    public ActionResultType processInitialInteract(PlayerEntity player, Hand hand) {
        if (player.isSecondaryUseActive()) {
            if (this.isBeingRidden() && !(getControllingPassenger() instanceof net.minecraft.entity.player.PlayerEntity)){
                this.removePassengers();
                this.passengerwaittime = 200;
            }
            else {
                if (!(getControllingPassenger() instanceof net.minecraft.entity.player.PlayerEntity)) {
                   openContainer(player);
                } return ActionResultType.func_233537_a_(this.world.isRemote);
            } return ActionResultType.PASS;
        } else if (this.outOfControlTicks < 60.0F) {
            if (!this.world.isRemote) {
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
        setPosition(x, y, z);
        setMotion(Vector3d.ZERO);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        super.handleStatusUpdate(id);
    }

    public double getMountedYOffset() {
        return 0.75D;
    }

    public void updatePassenger(Entity passenger) {
        if (isPassenger(passenger)) {
            float f = -2.75F; //driver x pos
            float d = 0.0F;   //driver z pos
            float f1 = (float) ((this.removed ? 0.02D : getMountedYOffset()) + passenger.getYOffset());
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
            Vector3d vector3d = (new Vector3d((double) f, 0.0D, d)).rotateYaw(-this.rotationYaw * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
            passenger.setPosition(this.getPosX() + vector3d.x, this.getPosY() + (double) f1, +this.getPosZ() + vector3d.z);
            passenger.rotationYaw += this.deltaRotation;
            applyYawToEntity(passenger);
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
        }
    }

    protected ItemStackHandler initInventory() {
        return (ItemStackHandler)new GalleyItemStackHandler<GalleyEntity>(9, this) {
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
                ((GalleyEntity)this.galley).getDataManager().set(GalleyEntity.CARGO, Integer.valueOf(sigma));
            }
        };
    }

    public int getCargo() {
        return ((Integer)this.dataManager.get(CARGO)).intValue();
    }

    public void openContainer(PlayerEntity player) {
        player.openContainer((INamedContainerProvider)new SimpleNamedContainerProvider((id, inv, plyr) -> new GalleyContainer(id, inv, this),

                getDisplayName()));
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(CARGO, Integer.valueOf(0));
    }

    protected void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.dataManager.set(CARGO, Integer.valueOf(compound.getInt("Cargo")));
    }

    protected void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Cargo", ((Integer)this.dataManager.get(CARGO)).intValue());
    }

    protected boolean canFitPassenger(Entity passenger) {
        return (getPassengers().size() < 9);
    }
}
