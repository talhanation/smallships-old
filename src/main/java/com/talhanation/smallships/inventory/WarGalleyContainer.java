package com.talhanation.smallships.inventory;

import com.talhanation.smallships.entities.AbstractWarGalleyEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public final class WarGalleyContainer extends AbstractWarGalleyContainer {

    public WarGalleyContainer(int id, PlayerInventory playerInv, AbstractWarGalleyEntity wargalleyEntity) {
        super(ContainerType.GENERIC_9X2, id, wargalleyEntity);

        for (int i = 0; i < 2; i++) {// row/reihe
            for (int j = 0; j < 9; j++)
                addSlot((Slot)new SlotItemHandler((IItemHandler)this.wargalleyInv, j + i * 9, 8 + j * 18, 18 + i * 18));
        }
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 9; l++)
                addSlot(new Slot((IInventory)playerInv, l + k * 9 + 9, 8 + l * 18, 103 + k * 18 + 36));
        }
        for (int x = 0; x < 9; x++)
            addSlot(new Slot((IInventory)playerInv, x, 8 + x * 18, 197));
    }
}