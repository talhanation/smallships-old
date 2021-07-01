package com.talhanation.smallships.inventory;

import com.talhanation.smallships.entities.sailboats.AbstractDhowEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class AbstractDhowContainer extends Container {

    protected final ItemStackHandler dhowInv;

    protected final AbstractDhowEntity dhow;

    public AbstractDhowContainer(ContainerType<?> type, int id, AbstractDhowEntity dhow) {
        super(type, id);
        this.dhow = dhow;
        this.dhowInv = dhow.inventory;
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return (this.dhow.isAlive() && this.dhow.distanceTo((Entity)playerIn) < 8.0F);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            if (index < this.dhowInv.getSlots()) {
                if (!moveItemStackTo(itemstack1, this.dhowInv.getSlots(), this.slots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!moveItemStackTo(itemstack1, 0, this.dhowInv.getSlots(), false)) {
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

