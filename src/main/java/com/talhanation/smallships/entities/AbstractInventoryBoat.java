package com.talhanation.smallships.entities;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.network.MessageOpenInv;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;

public abstract class AbstractInventoryBoat extends TNBoatEntity{
    protected Container inventory;


    public AbstractInventoryBoat(EntityType<? extends TNBoatEntity> type, Level world) {
        super(type, world);
        inventory = new SimpleContainer(54);
    }
    ////////////////////////////////////TICK////////////////////////////////////

    @Override
    public void tick() {
        super.tick();
        breakLily();
    }

    ////////////////////////////////////GET////////////////////////////////////

    public Container getInventory() {
        return inventory;
    }
    ////////////////////////////////////ADDITIONAL////////////////////////////////////

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        readInventory(compound, "Inventory", inventory);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        saveInventory(compound, "Inventory", inventory);
    }

    ////////////////////////////////////ON FUNCTIONS////////////////////////////////////
    @Override
    public void onInvPressed(Player player){
        Main.SIMPLE_CHANNEL.sendToServer(new MessageOpenInv(player));
    }

    public void onDestroyed(DamageSource source, boolean byCreativePlayer) {
        Containers.dropContents(level, blockPosition(), inventory);
        inventory.clearContent();
    }

    ////////////////////////////////////OTHER FUNCTIONS////////////////////////////////////



    public void openContainer(Player player){

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


    public static void saveInventory(CompoundTag compound, String name, Container inv) {
        ListTag tagList = new ListTag();

        for (int i = 0; i < inv.getContainerSize(); i++) {
            if (!inv.getItem(i).isEmpty()) {
                CompoundTag slot = new CompoundTag();
                slot.putInt("Slot", i);
                inv.getItem(i).save(slot);
                tagList.add(slot);
            }
        }

        compound.put(name, tagList);
    }

    public static void readInventory(CompoundTag compound, String name, Container inv) {
        if (!compound.contains(name)) {
            return;
        }

        ListTag tagList = compound.getList(name, 10);

        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag slot = tagList.getCompound(i);
            int j = slot.getInt("Slot");

            if (j >= 0 && j < inv.getContainerSize()) {
                inv.setItem(j, ItemStack.of(slot));
            }
        }
    }

}
