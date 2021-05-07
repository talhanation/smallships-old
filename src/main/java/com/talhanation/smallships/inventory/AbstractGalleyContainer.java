package com.talhanation.smallships.inventory;

import com.talhanation.smallships.entities.AbstractGalleyEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class AbstractGalleyContainer extends Container {

    protected final ItemStackHandler galleyInv;
    protected final AbstractGalleyEntity galley;

    public AbstractGalleyContainer(ContainerType<?> type, int id, AbstractGalleyEntity galley) {
        super(type, id);
        this.galley = galley;
        this.galleyInv = galley.inventory;
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return (this.galley.isAlive() && this.galley.distanceTo((Entity)playerIn) < 8.0F);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            if (index < this.galleyInv.getSlots()) {
                if (!moveItemStackTo(itemstack1, this.galleyInv.getSlots(), this.slots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!moveItemStackTo(itemstack1, 0, this.galleyInv.getSlots(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }

    @Override
    public void removed(PlayerEntity playerIn) {
        super.removed(playerIn);
    }
}

