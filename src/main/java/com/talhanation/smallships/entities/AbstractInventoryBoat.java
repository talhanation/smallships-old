package com.talhanation.smallships.entities;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.network.MessageOpenInv;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.util.Mth.*;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public abstract class AbstractInventoryBoat extends TNBoatEntity {
    private LazyOptional<ItemStackHandler> itemHandler = LazyOptional.of(() -> this.inventory);
    public ItemStackHandler inventory = initInventory();

    public AbstractInventoryBoat(EntityType<? extends TNBoatEntity> type, Level world) {
        super(type, world);
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
    public void onInvPressed(Player player){
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

    public void openContainer(Player player){

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
        AABB boundingBox = getBoundingBox();
        double offset = 0.75D;
        BlockPos start = new BlockPos(boundingBox.minX - offset, boundingBox.minY - offset, boundingBox.minZ - offset);
        BlockPos end = new BlockPos(boundingBox.maxX + offset, boundingBox.maxY + offset, boundingBox.maxZ + offset);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        boolean hasBroken = false;
        if (level.hasChunksAt(start, end)) {
            for (int i = start.getX(); i <= end.getX(); ++i) {
                for (int j = start.getY(); j <= end.getY(); ++j) {
                    for (int k = start.getZ(); k <= end.getZ(); ++k) {
                        pos.set(i, j, k);
                        BlockState blockstate = level.getBlockState(pos);
                        if (blockstate.getBlock() instanceof WaterlilyBlock) {
                            level.destroyBlock(pos, true);
                            hasBroken = true;
                        }
                    }
                }
            }
        }
        if (hasBroken) {
            level.playSound(null, getX(), getY(), getZ(), SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1F, 0.9F + 0.2F * random.nextFloat());
        }
    }

    protected abstract ItemStackHandler initInventory();

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket((Entity) this);
    }
}
