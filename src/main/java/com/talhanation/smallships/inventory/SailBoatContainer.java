package com.talhanation.smallships.inventory;

import com.talhanation.smallships.entities.AbstractSailBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class SailBoatContainer extends Container {

    protected final ItemStackHandler sailboatInv;

    protected final AbstractSailBoatEntity sailboat;

    public SailBoatContainer(ContainerType<?> type, int id, AbstractSailBoatEntity sailboat) {
        super(type, id);
        this.sailboat = sailboat;
        this.sailboatInv = sailboat.inventory;
    }

    public boolean canInteractWith(PlayerEntity playerIn) {
        return (this.sailboat.isAlive() && this.sailboat.getDistance((Entity)playerIn) < 8.0F);
    }

    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            if (index < this.sailboatInv.getSlots()) {
                if (!mergeItemStack(itemstack1, this.sailboatInv.getSlots(), this.inventorySlots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!mergeItemStack(itemstack1, 0, this.sailboatInv.getSlots(), false)) {
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

