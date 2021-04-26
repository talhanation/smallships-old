package com.talhanation.smallships.inventory;

import com.talhanation.smallships.entities.AbstractBriggEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class AbstractBriggContainer extends Container {

    protected final ItemStackHandler briggInv;

    protected final AbstractBriggEntity brigg;

    public AbstractBriggContainer(ContainerType<?> type, int id, AbstractBriggEntity brigg) {
        super(type, id);
        this.brigg = brigg;
        this.briggInv = brigg.inventory;
    }

    public boolean canInteractWith(PlayerEntity playerIn) {
        return (this.brigg.isAlive() && this.brigg.getDistance((Entity)playerIn) < 8.0F);
    }

    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            if (index < this.briggInv.getSlots()) {
                if (!mergeItemStack(itemstack1, this.briggInv.getSlots(), this.inventorySlots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!mergeItemStack(itemstack1, 0, this.briggInv.getSlots(), false)) {
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

