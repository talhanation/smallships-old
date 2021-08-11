package com.talhanation.smallships.entities;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.network.MessageOpenInv;
import net.minecraft.block.BlockState;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public abstract class AbstractInventoryBoat extends TNBoatEntity {
    private LazyOptional<ItemStackHandler> itemHandler = LazyOptional.of(() -> this.inventory);
    public ItemStackHandler inventory = initInventory();

    public AbstractInventoryBoat(World world) {
        super(world);
    }

    ////////////////////////////////////TICK////////////////////////////////////

    @Override
    public void tick() {
        super.tick();
        breakLily();
    }

    ////////////////////////////////////REGISTER////////////////////////////////////

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    ////////////////////////////////////GET////////////////////////////////////

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (isAlive() && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && this.itemHandler != null)
            return this.itemHandler.cast();
        return super.getCapability(capability, facing);
    }

    ////////////////////////////////////ADDITIONAL////////////////////////////////////

    @Override
    protected void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.inventory.deserializeNBT(compound.getCompound("Items"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.put("Items", this.inventory.serializeNBT());
    }

    ////////////////////////////////////ON FUNCTIONS////////////////////////////////////
    @Override
    public void onInvPressed(EntityPlayer player){
        Main.SIMPLE_CHANNEL.sendToServer(new MessageOpenInv(player));
    }

    public void onDestroyed(DamageSource source, boolean byCreativePlayer) {
        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            if (!byCreativePlayer)
                this.spawnAtLocation(this.getItemBoat());
            onDestroyedAndDoDrops(source);
        }
    }

    public void onDestroyedAndDoDrops(DamageSource source) {
        for (int i = 0; i < this.inventory.getSlots(); i++)
            InventoryHelper.dropItemStack(this.level, getX(), getY(), getZ(), this.inventory.getStackInSlot(i));
    }

    ////////////////////////////////////OTHER FUNCTIONS////////////////////////////////////

    public void openContainer(EntityPlayer player){

    }

    @Override
    public boolean setSlot(int inventorySlot, ItemStack itemStackIn) {
        if (inventorySlot >= 0 && inventorySlot < this.inventory.getSlots()) {
            this.inventory.setStackInSlot(inventorySlot, itemStackIn);
            return true;
        }
        return false;
    }

    @Override
    public void remove(boolean keepData) {
        super.remove(keepData);
        if (!keepData && this.itemHandler != null) {
            this.itemHandler.invalidate();
            this.itemHandler = null;
        }
    }

    private void breakLily() {
        AxisAlignedBB boundingBox = getBoundingBox();
        double offset = 0.75D;
        BlockPos start = new BlockPos(boundingBox.minX - offset, boundingBox.minY - offset, boundingBox.minZ - offset);
        BlockPos end = new BlockPos(boundingBox.maxX + offset, boundingBox.maxY + offset, boundingBox.maxZ + offset);
        BlockPos.Mutable pos = new BlockPos.Mutable();
        boolean hasBroken = false;
        if (level.hasChunksAt(start, end)) {
            for (int i = start.getX(); i <= end.getX(); ++i) {
                for (int j = start.getY(); j <= end.getY(); ++j) {
                    for (int k = start.getZ(); k <= end.getZ(); ++k) {
                        pos.set(i, j, k);
                        BlockState blockstate = level.getBlockState(pos);
                        if (blockstate.getBlock() instanceof LilyPadBlock) {
                            level.destroyBlock(pos, true);
                            hasBroken = true;
                        }
                    }
                }
            }
        }
        if (hasBroken) {
            level.playSound(null, getX(), getY(), getZ(), SoundEvents.CROP_BREAK, SoundCategory.BLOCKS, 1F, 0.9F + 0.2F * random.nextFloat());
        }
    }

    protected abstract ItemStackHandler initInventory();

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket((Entity) this);
    }
}
