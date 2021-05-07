package com.talhanation.smallships.inventory;

import com.talhanation.smallships.entities.AbstractRowBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class AbstractRowBoatContainer extends Container {

    protected final ItemStackHandler rowboatInv;
    protected final AbstractRowBoatEntity rowboat;

    public AbstractRowBoatContainer(ContainerType<?> type, int id, AbstractRowBoatEntity rowboat) {
        super(type, id);
        this.rowboat = rowboat;
        this.rowboatInv = rowboat.inventory;
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return (this.rowboat.isAlive() && this.rowboat.distanceTo((Entity)playerIn) < 8.0F);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            if (index < this.rowboatInv.getSlots()) {
                if (!moveItemStackTo(itemstack1, this.rowboatInv.getSlots(), this.slots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!moveItemStackTo(itemstack1, 0, this.rowboatInv.getSlots(), false)) {
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

