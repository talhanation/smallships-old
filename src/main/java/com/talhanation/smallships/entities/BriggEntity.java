package com.talhanation.smallships.entities;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.init.ModEntityTypes;
import com.talhanation.smallships.inventory.BriggContainer;
import com.talhanation.smallships.items.ModItems;
import com.talhanation.smallships.network.MessageOpenInv;
import com.talhanation.smallships.util.BriggItemStackHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
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
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.items.ItemStackHandler;

public class BriggEntity extends AbstractBriggEntity {

    public boolean Cargo_0;
    public boolean Cargo_1;
    public boolean Cargo_2;
    public boolean Cargo_3;

   private static final DataParameter<Integer> CARGO = EntityDataManager.createKey(AbstractBriggEntity.class, DataSerializers.VARINT);

    public BriggEntity(EntityType<? extends AbstractBriggEntity> entityType, World worldIn) {
        super(entityType, worldIn);
    }

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

        return (ItemStackHandler)new BriggItemStackHandler<BriggEntity>(54, this) {
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
                ((BriggEntity)this.brigg).getDataManager().set(BriggEntity.CARGO, Integer.valueOf(sigma));
            }
        };
    }

    public BriggEntity(World worldIn, double x, double y, double z) {
        this((EntityType<? extends AbstractBriggEntity>) ModEntityTypes.BRIGG_ENTITY.get(), worldIn);
        setPosition(x, y, z);
        setMotion(Vector3d.ZERO);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    public BriggEntity(FMLPlayMessages.SpawnEntity spawnEntity, World worldIn) {
        this((EntityType<? extends AbstractBriggEntity>) ModEntityTypes.BRIGG_ENTITY.get(), worldIn);
    }


    public ActionResultType processInitialInteract(PlayerEntity player, Hand hand) {
        if (player.isSecondaryUseActive()) {
            if (this.isBeingRidden() && !(getControllingPassenger() instanceof PlayerEntity)){
                    this.removePassengers();
                    this.passengerwaittime = 200;
            }
            else {
                if (!(getControllingPassenger() instanceof PlayerEntity) ) {
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


    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
            super.handleStatusUpdate(id);
    }

    public double getMountedYOffset() {
        return 1.0D;
    }

    public void updatePassenger(Entity passenger) {
        if (isPassenger(passenger)) {
            double f = -1.5F;
            double d = 0.75F;
            float f1 = (float) ((this.removed ? 0.02D : getMountedYOffset()) + passenger.getYOffset());
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
        if (passenger instanceof AnimalEntity)
            d = (float)(d -0.15D);
        Vector3d vector3d = (new Vector3d((double)f, 0.0D, 0.0D + d)).rotateYaw(-this.rotationYaw * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
        passenger.setPosition(this.getPosX() + vector3d.x, this.getPosY() + (double)f1, + this.getPosZ() + vector3d.z);
        passenger.rotationYaw += this.deltaRotation;
        passenger.setRotationYawHead(passenger.getRotationYawHead() + this.deltaRotation);
        applyYawToEntity(passenger);
        }

    }

    public int getCargo() {
        return ((Integer)this.dataManager.get(CARGO)).intValue();
    }

    @Override
    public void openContainer(PlayerEntity player) {
            player.openContainer(new SimpleNamedContainerProvider((id, inv, plyr) -> new BriggContainer(id, inv, this),
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


    protected boolean canFitPassenger(Entity passenger) {
        return (getPassengers().size() < 10);
    }


}
