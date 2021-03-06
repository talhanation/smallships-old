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

    public boolean canInteractWith(PlayerEntity playerIn) {
        return (this.galley.isAlive() && this.galley.getDistance((Entity)playerIn) < 8.0F);
    }

    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            if (index < this.galleyInv.getSlots()) {
                if (!mergeItemStack(itemstack1, this.galleyInv.getSlots(), this.inventorySlots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!mergeItemStack(itemstack1, 0, this.galleyInv.getSlots(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
    }
}

