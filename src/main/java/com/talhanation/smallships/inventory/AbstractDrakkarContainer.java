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

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return (this.drakkar.isAlive() && this.drakkar.distanceTo((Entity)playerIn) < 8.0F);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            if (index < this.drakkarInv.getSlots()) {
                if (!moveItemStackTo(itemstack1, this.drakkarInv.getSlots(), this.slots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!moveItemStackTo(itemstack1, 0, this.drakkarInv.getSlots(), false)) {
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

