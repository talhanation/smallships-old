package com.talhanation.smallships.inventory;

import com.talhanation.smallships.entities.sailboats.AbstractBriggEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class AbstractBriggContainer extends Container {

    protected final ItemStackHandler briggInv;

    protected final AbstractBriggEntity brigg;

    public AbstractBriggContainer(ContainerType<?> type, int id, AbstractBriggEntity brigg) {
        super(type, id);
        this.brigg = brigg;
        this.briggInv = brigg.inventory;
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return (this.brigg.isAlive() && this.brigg.distanceTo((Entity)playerIn) < 8.0F);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            if (index < this.briggInv.getSlots()) {
                if (!moveItemStackTo(itemstack1, this.briggInv.getSlots(), this.slots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!moveItemStackTo(itemstack1, 0, this.briggInv.getSlots(), false)) {
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
    public void removed(Player playerIn) {
        super.removed(playerIn);
    }
}

