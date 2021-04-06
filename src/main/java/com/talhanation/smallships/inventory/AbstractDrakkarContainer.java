package com.talhanation.smallships.inventory;

import com.talhanation.smallships.entities.AbstractDrakkarEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class AbstractDrakkarContainer extends Container {

    protected final ItemStackHandler drakkarInv;
    protected final AbstractDrakkarEntity drakkar;

    public AbstractDrakkarContainer(ContainerType<?> type, int id, AbstractDrakkarEntity drakkar) {
        super(type, id);
        this.drakkar = drakkar;
        this.drakkarInv = drakkar.inventory;
    }

    public boolean canInteractWith(PlayerEntity playerIn) {
        return (this.drakkar.isAlive() && this.drakkar.getDistance((Entity)playerIn) < 8.0F);
    }

    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            if (index < this.drakkarInv.getSlots()) {
                if (!mergeItemStack(itemstack1, this.drakkarInv.getSlots(), this.inventorySlots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!mergeItemStack(itemstack1, 0, this.drakkarInv.getSlots(), false)) {
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

