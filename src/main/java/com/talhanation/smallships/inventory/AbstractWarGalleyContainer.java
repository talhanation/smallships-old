package com.talhanation.smallships.inventory;

import com.talhanation.smallships.entities.AbstractWarGalleyEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class AbstractWarGalleyContainer extends Container {

    protected final ItemStackHandler wargalleyInv;
    protected final AbstractWarGalleyEntity wargalley;

    public AbstractWarGalleyContainer(ContainerType<?> type, int id, AbstractWarGalleyEntity wargalley) {
        super(type, id);
        this.wargalley = wargalley;
        this.wargalleyInv = wargalley.inventory;
    }

    public boolean canInteractWith(PlayerEntity playerIn) {
        return (this.wargalley.isAlive() && this.wargalley.getDistance((Entity)playerIn) < 8.0F);
    }

    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            if (index < this.wargalleyInv.getSlots()) {
                if (!mergeItemStack(itemstack1, this.wargalleyInv.getSlots(), this.inventorySlots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!mergeItemStack(itemstack1, 0, this.wargalleyInv.getSlots(), false)) {
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

