package com.talhanation.smallships.entities;

import com.talhanation.smallships.entities.sailboats.AbstractWarGalleyEntity;
import com.talhanation.smallships.init.ModEntityTypes;
import com.talhanation.smallships.items.ModItems;
import com.talhanation.smallships.util.WarGalleyItemStackHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemStackHandler;

public class WarGalleyEntity extends AbstractWarGalleyEntity {
    public boolean Cargo_0 = true;
    public boolean Cargo_1 = true;

    private static final EntityDataAccessor<Integer> CARGO = SynchedEntityData.defineId(AbstractWarGalleyEntity.class, EntityDataSerializers.INT);

    public WarGalleyEntity(EntityType<? extends AbstractWarGalleyEntity> entityType, Level worldIn) {
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
                this.passengerwaittime = 300;
            }
            else {
                if (!(getControllingPassenger() instanceof Player)) {
                   openContainer(player);
                } return InteractionResult.sidedSuccess(this.level.isClientSide);
            } return InteractionResult.PASS;
        } else if (this.outOfControlTicks < 60.0F) {
            if (!this.level.isClientSide) {
                return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;

            } else {
                return InteractionResult.SUCCESS;
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    public WarGalleyEntity(Level worldIn, double x, double y, double z) {
        this((EntityType<? extends AbstractWarGalleyEntity>) ModEntityTypes.WAR_GALLEY_ENTITY.get(), worldIn);
        setPos(x, y, z);
        setDeltaMovement(Vec3.ZERO);
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
        return 0.85D;
    }

    @Override
    public void positionRider(Entity passenger) {
        if (hasPassenger(passenger)) {
            float f = -3.25F; //driver x pos
            float d = 0.0F;   //driver z pos
            float f1 = (float) ((this.removed ? 0.02D : getPassengersRidingOffset()) + passenger.getMyRidingOffset());
            if (getPassengers().size() == 2) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -3.25F;
                    d = 0.0F;
                } else {
                    f =  0.5F;
                    d =  0.0F;
                }
            }
            else if (getPassengers().size() == 3) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -3.25F;
                    d = 0.0F;
                } else if (i == 1) {
                    f =  0.5F;
                    d =  0.0F;
                } else {
                    f = -2.0F;
                    d = -1.0F;
                }
            }
            else if (getPassengers().size() == 4) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -3.25F;
                    d = 0.0F;
                } else if (i == 1) {
                    f =  0.5F;
                    d =  0.0F;
                } else if (i == 2) {
                    f = -2.0F;
                    d = -1.0F;
                } else {
                    f = -2.0F;
                    d =  1.0F;
                }
            }
            else if (getPassengers().size() == 5) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -3.25F;
                    d = 0.0F;
                } else if (i == 1) {
                    f =  0.5F;
                    d =  0.0F;
                } else if (i == 2) {
                    f = -2.0F;
                    d = -1.0F;
                } else if (i == 3) {
                    f = -2.0F;
                    d =  1.0F;
                } else {
                    f = -1.0F;
                    d = -1.0F;
                }
            }
            else if (getPassengers().size() == 6) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -3.25F;
                    d = 0.0F;
                } else if (i == 1) {
                    f =  0.5F;
                    d =  0.0F;
                } else if (i == 2) {
                    f = -2.0F;
                    d = -1.0F;
                } else if (i == 3) {
                    f = -2.0F;
                    d =  1.0F;
                } else if(i == 4){
                    f = -1.0F;
                    d = -1.0F;
                } else {
                    f =  -1.0F;
                    d =   1.0F;
                }
            }
            else if (getPassengers().size() == 7) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -3.25F;
                    d = 0.0F;
                } else if (i == 1) {
                    f =  0.5F;
                    d =  0.0F;
                } else if (i == 2) {
                    f = -2.0F;
                    d = -1.0F;
                } else if (i == 3) {
                    f = -2.0F;
                    d =  1.0F;
                } else if (i == 4) {
                    f = -1.0F;
                    d = -1.0F;
                } else if (i == 5) {
                    f =  -1.0F;
                    d =   1.0F;
                } else {
                    f =  0.0F;
                    d =  -1.0F;
                }
            }
            else if (getPassengers().size() == 8) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -3.25F;
                    d = 0.0F;
                } else if (i == 1) {
                    f =  0.5F;
                    d =  0.0F;
                } else if (i == 2) {
                    f = -2.0F;
                    d = -1.0F;
                } else if (i == 3) {
                    f = -2.0F;
                    d =  1.0F;
                } else if (i == 4) {
                    f = -1.0F;
                    d = -1.0F;
                } else if (i == 5) {
                    f =  -1.0F;
                    d =   1.0F;
                } else if (i == 6){
                    f =  0.0F;
                    d =  -1.0F;
                }else {
                    f =  0.0F;
                    d =  1.0F;
                }
            }
            else if (getPassengers().size() == 9) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -3.25F;
                    d = 0.0F;
                } else if (i == 1) {
                    f =  0.5F;
                    d =  0.0F;
                } else if (i == 2) {
                    f = -2.0F;
                    d = -1.0F;
                } else if (i == 3) {
                    f = -2.0F;
                    d =  1.0F;
                } else if (i == 4) {
                    f = -1.0F;
                    d = -1.0F;
                } else if (i == 5) {
                    f =  -1.0F;
                    d =   1.0F;
                } else if (i == 6) {
                    f =  0.0F;
                    d =  -1.0F;
                } else if (i == 7) {
                    f =  0.0F;
                    d =  1.0F;
                } else{
                    f =  1.0F;
                    d =  -1.0F;
                }
            }
            else if (getPassengers().size() == 10) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -3.25F;
                    d = 0.0F;
                } else if (i == 1) {
                    f =  0.5F;
                    d =  0.0F;
                } else if (i == 2) {
                    f = -2.0F;
                    d = -1.0F;
                } else if (i == 3) {
                    f = -2.0F;
                    d =  1.0F;
                } else if (i == 4) {
                    f = -1.0F;
                    d = -1.0F;
                } else if (i == 5) {
                    f =  -1.0F;
                    d =   1.0F;
                } else if (i == 6) {
                    f =  0.0F;
                    d =  -1.0F;
                } else if (i == 7) {
                    f =  0.0F;
                    d =  1.0F;
                } else if (i == 8){
                    f =  1.0F;
                    d =  -1.0F;
                } else{
                    f =  1.0F;
                    d =  1.0F;
                }
            }
            else if (getPassengers().size() == 11) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -3.25F;
                    d = 0.0F;
                } else if (i == 1) {
                    f =  0.5F;
                    d =  0.0F;
                } else if (i == 2) {
                    f = -2.0F;
                    d = -1.0F;
                } else if (i == 3) {
                    f = -2.0F;
                    d =  1.0F;
                } else if (i == 4) {
                    f = -1.0F;
                    d = -1.0F;
                } else if (i == 5) {
                    f =  -1.0F;
                    d =   1.0F;
                } else if (i == 6) {
                    f =  0.0F;
                    d =  -1.0F;
                } else if (i == 7) {
                    f =  0.0F;
                    d =  1.0F;
                } else if (i == 8){
                    f =  1.0F;
                    d =  -1.0F;
                } else if (i == 9){
                    f =  1.0F;
                    d =  1.0F;
                }else{
                    f =  2.0F;
                    d = -1.0F;
                }
            }
            else if (getPassengers().size() == 12) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -3.25F;
                    d = 0.0F;
                } else if (i == 1) {
                    f =  0.5F;
                    d =  0.0F;
                } else if (i == 2) {
                    f = -2.0F;
                    d = -1.0F;
                } else if (i == 3) {
                    f = -2.0F;
                    d =  1.0F;
                } else if (i == 4) {
                    f = -1.0F;
                    d = -1.0F;
                } else if (i == 5) {
                    f =  -1.0F;
                    d =   1.0F;
                } else if (i == 6) {
                    f =  0.0F;
                    d =  -1.0F;
                } else if (i == 7) {
                    f =  0.0F;
                    d =  1.0F;
                } else if (i == 8){
                    f =  1.0F;
                    d =  -1.0F;
                } else if (i == 9){
                    f =  1.0F;
                    d =  1.0F;
                }else if (i == 10){
                    f =  2.0F;
                    d = -1.0F;
                }else{
                    f =  2.0F;
                    d =  1.0F;
                }
            }
            else if (getPassengers().size() == 13) {
                int i = getPassengers().indexOf(passenger);
                if  (i == 0) {
                    f = -3.25F;
                    d = 0.0F;
                } else if (i == 1) {
                    f =  0.5F;
                    d =  0.0F;
                } else if (i == 2) {
                    f = -2.0F;
                    d = -1.0F;
                } else if (i == 3) {
                    f = -2.0F;
                    d =  1.0F;
                } else if (i == 4) {
                    f = -1.0F;
                    d = -1.0F;
                } else if (i == 5) {
                    f =  -1.0F;
                    d =   1.0F;
                } else if (i == 6) {
                    f =  0.0F;
                    d =  -1.0F;
                } else if (i == 7) {
                    f =  0.0F;
                    d =  1.0F;
                } else if (i == 8){
                    f =  1.0F;
                    d =  -1.0F;
                } else if (i == 9){
                    f =  1.0F;
                    d =  1.0F;
                }else if (i == 10){
                    f =  2.0F;
                    d = -1.0F;
                }else if (i == 11){
                    f =  2.0F;
                    d =  1.0F;
                }else{
                    f = 3.25F;
                    d = 0.0F;
                }
            }
            else if (getPassengers().size() == 14) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -3.25F;
                    d = 0.0F;
                } else if (i == 1) {
                    f =  0.5F;
                    d =  0.0F;
                } else if (i == 2) {
                    f = -2.0F;
                    d = -1.0F;
                } else if (i == 3) {
                    f = -2.0F;
                    d =  1.0F;
                } else if (i == 4) {
                    f = -1.0F;
                    d = -1.0F;
                } else if (i == 5) {
                    f =  -1.0F;
                    d =   1.0F;
                } else if (i == 6) {
                    f =  0.0F;
                    d =  -1.0F;
                } else if (i == 7) {
                    f =  0.0F;
                    d =  1.0F;
                } else if (i == 8){
                    f =  1.0F;
                    d =  -1.0F;
                } else if (i == 9){
                    f =  1.0F;
                    d =  1.0F;
                }else if (i == 10){
                    f =  2.0F;
                    d = -1.0F;
                }else if (i == 11){
                    f =  2.0F;
                    d =  1.0F;
                }else if (i == 12){
                    f = 3.0F;
                    d = 0.0F;
                }else{
                    f = -1.25F;
                    d =  0.0F;
                }
            }
            else if (getPassengers().size() == 15) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -3.25F;
                    d = 0.0F;
                } else if (i == 1) {
                    f =  0.5F;
                    d =  0.0F;
                } else if (i == 2) {
                    f = -2.0F;
                    d = -1.0F;
                } else if (i == 3) {
                    f = -2.0F;
                    d =  1.0F;
                } else if (i == 4) {
                    f = -1.0F;
                    d = -1.0F;
                } else if (i == 5) {
                    f =  -1.0F;
                    d =   1.0F;
                } else if (i == 6) {
                    f =  0.0F;
                    d =  -1.0F;
                } else if (i == 7) {
                    f =  0.0F;
                    d =  1.0F;
                } else if (i == 8){
                    f =  1.0F;
                    d =  -1.0F;
                } else if (i == 9){
                    f =  1.0F;
                    d =  1.0F;
                }else if (i == 10){
                    f =  2.0F;
                    d = -1.0F;
                }else if (i == 11){
                    f =  2.0F;
                    d =  1.0F;
                }else if (i == 12){
                    f = 3.25F;
                    d = 0.0F;
                }else if (i == 13){
                    f = -1.25F;
                    d =  0.0F;
                }else {
                    f =  1.5F;
                    d =  0.0F;
                }
            }
            else if (getPassengers().size() == 16) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -3.25F;
                    d = 0.0F;
                } else if (i == 1) {
                    f =  0.5F;
                    d =  0.0F;
                } else if (i == 2) {
                    f = -2.0F;
                    d = -1.0F;
                } else if (i == 3) {
                    f = -2.0F;
                    d =  1.0F;
                } else if (i == 4) {
                    f = -1.0F;
                    d = -1.0F;
                } else if (i == 5) {
                    f =  -1.0F;
                    d =   1.0F;
                } else if (i == 6) {
                    f =  0.0F;
                    d =  -1.0F;
                } else if (i == 7) {
                    f =  0.0F;
                    d =  1.0F;
                } else if (i == 8){
                    f =  1.0F;
                    d =  -1.0F;
                } else if (i == 9){
                    f =  1.0F;
                    d =  1.0F;
                }else if (i == 10){
                    f =  2.0F;
                    d = -1.0F;
                }else if (i == 11){
                    f =  2.0F;
                    d =  1.0F;
                }else if (i == 12){
                    f = 3.25F;
                    d = 0.0F;
                }else if (i == 13){
                    f = -1.5F;
                    d =  0.0F;
                }else if (i == 14) {
                    f =  1.5F;
                    d =  0.0F;
                } else {
                    f = -0.5F;
                    d =  0.0F;
                }
            }
            else if (getPassengers().size() == 16) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -3.25F;
                    d = 0.0F;
                } else if (i == 1) {
                    f =  0.5F;
                    d =  0.0F;
                } else if (i == 2) {
                    f = -2.0F;
                    d = -1.0F;
                } else if (i == 3) {
                    f = -2.0F;
                    d =  1.0F;
                } else if (i == 4) {
                    f = -1.0F;
                    d = -1.0F;
                } else if (i == 5) {
                    f =  -1.0F;
                    d =   1.0F;
                } else if (i == 6) {
                    f =  0.0F;
                    d =  -1.0F;
                } else if (i == 7) {
                    f =  0.0F;
                    d =  1.0F;
                } else if (i == 8) {
                    f =  1.0F;
                    d =  -1.0F;
                } else if (i == 9) {
                    f =  1.0F;
                    d =  1.0F;
                } else if (i == 10) {
                    f =  2.0F;
                    d = -1.0F;
                } else if (i == 11) {
                    f =  2.0F;
                    d =  1.0F;
                } else if (i == 12) {
                    f = 3.25F;
                    d = 0.0F;
                } else if (i == 13) {
                    f = -1.25F;
                    d =  0.0F;
                } else if (i == 14) {
                    f =  1.5F;
                    d =  0.0F;
                } else if (i == 15) {
                    f = -0.5F;
                    d =  0.0F;
                } else {
                    f =  0.5F;
                    d =  0.0F;
                }
            }
            Vec3 vector3d = (new Vec3((double) f, 0.0D, d)).yRot(-this.getYRot() * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
            passenger.setPos(this.getX() + vector3d.x, this.getY() + (double) f1, +this.getZ() + vector3d.z);
            passenger.setYRot(passenger.getYRot() + this.deltaRotation);
            clampRotation(passenger);
        }
    }

    public Item getItemBoat() {
        switch (this.getBoatType()) {
            case OAK:
            default:
                return ModItems.OAK_WAR_GALLEY_ITEM.get();
            case SPRUCE:
                return ModItems.SPRUCE_WAR_GALLEY_ITEM.get();
            case BIRCH:
                return ModItems.BIRCH_WAR_GALLEY_ITEM.get();
            case JUNGLE:
                return ModItems.JUNGLE_WAR_GALLEY_ITEM.get();
            case ACACIA:
                return ModItems.ACACIA_WAR_GALLEY_ITEM.get();
            case DARK_OAK:
                return ModItems.DARK_OAK_WAR_GALLEY_ITEM.get();

                //BOP
            case BOP_CHERRY:
                return ModItems.BOP_CHERRY_WAR_GALLEY_ITEM.get();

            case BOP_DEAD:
                return ModItems.BOP_DEAD_WAR_GALLEY_ITEM.get();
            case BOP_FIR:
                return ModItems.BOP_FIR_WAR_GALLEY_ITEM.get();
            case BOP_HELLBARK:
                return ModItems.BOP_HELLBARK_WAR_GALLEY_ITEM.get();
            case BOP_JACARANDA:
                return ModItems.BOP_JACARANDA_WAR_GALLEY_ITEM.get();
            case BOP_MAGIC:
                return ModItems.BOP_MAGIC_WAR_GALLEY_ITEM.get();
            case BOP_MAHOGANY:
                return ModItems.BOP_MAHOGANY_WAR_GALLEY_ITEM.get();
            case BOP_PALM:
                return ModItems.BOP_PALM_WAR_GALLEY_ITEM.get();
            case BOP_REDWOOD:
                return ModItems.BOP_REDWOOD_WAR_GALLEY_ITEM.get();
            case BOP_UMBRAN:
                return ModItems.BOP_UMBRAN_WAR_GALLEY_ITEM.get();
            case BOP_WILLOW:
                return ModItems.BOP_WILLOW_WAR_GALLEY_ITEM.get();

            //LOTR
            case LOTR_APPLE:
                return ModItems.LOTR_APPLE_WAR_GALLEY_ITEM.get();
            case LOTR_ASPEN:
                return ModItems.LOTR_ASPEN_WAR_GALLEY_ITEM.get();
            case LOTR_BEECH:
                return ModItems.LOTR_BEECH_WAR_GALLEY_ITEM.get();
            case LOTR_CEDAR:
                return ModItems.LOTR_CEDAR_WAR_GALLEY_ITEM.get();
            case LOTR_CHERRY:
                return ModItems.LOTR_CHERRY_WAR_GALLEY_ITEM.get();
            case LOTR_CHARRED:
                return ModItems.LOTR_CHARRED_WAR_GALLEY_ITEM.get();
            case LOTR_CYPRESS:
                return ModItems.LOTR_CYPRESS_WAR_GALLEY_ITEM.get();
            case LOTR_FIR:
                return ModItems.LOTR_FIR_WAR_GALLEY_ITEM.get();
            case LOTR_GREEN_OAK:
                return ModItems.LOTR_GREEN_OAK_WAR_GALLEY_ITEM.get();
            case LOTR_HOLLY:
                return ModItems.LOTR_HOLLY_WAR_GALLEY_ITEM.get();
            case LOTR_LAIRELOSSE:
                return ModItems.LOTR_LAIRELOSSE_WAR_GALLEY_ITEM.get();
            case LOTR_LARCH:
                return ModItems.LOTR_LARCH_WAR_GALLEY_ITEM.get();
            case LOTR_LEBETHRON:
                return ModItems.LOTR_LEBETHRON_WAR_GALLEY_ITEM.get();
            case LOTR_MALLORN:
                return ModItems.LOTR_MALLORN_WAR_GALLEY_ITEM.get();
            case LOTR_MAPLE:
                return ModItems.LOTR_MAPLE_WAR_GALLEY_ITEM.get();
            case LOTR_MIRK_OAK:
                return ModItems.LOTR_MIRK_OAK_WAR_GALLEY_ITEM.get();
            case LOTR_PEAR:
                return ModItems.LOTR_PEAR_WAR_GALLEY_ITEM.get();
            case LOTR_PINE:
                return ModItems.LOTR_PINE_WAR_GALLEY_ITEM.get();
            case LOTR_ROTTEN:
                return ModItems.LOTR_ROTTEN_WAR_GALLEY_ITEM.get();

            //ENVI//
            case ENVI_CHERRY:
                return ModItems.ENVI_CHERRY_WAR_GALLEY_ITEM.get();
            case ENVI_WILLOW:
                return ModItems.ENVI_WILLOW_WAR_GALLEY_ITEM.get();
            case ENVI_WISTERIA:
                return ModItems.ENVI_WISTERIA_WAR_GALLEY_ITEM.get();

        }

    }

    protected ItemStackHandler initInventory() {
        return new WarGalleyItemStackHandler<WarGalleyEntity>(18, this) {
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
                (this.wargalley).getEntityData().set(WarGalleyEntity.CARGO, Integer.valueOf(sigma));
            }
        };
    }

    public int getCargo() {
        return this.entityData.get(CARGO);
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

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return (getPassengers().size() < 16);
    }

}
