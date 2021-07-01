package com.talhanation.smallships.inventory;

import com.talhanation.smallships.entities.sailboats.AbstractGalleyEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public final class GalleyContainer extends AbstractGalleyContainer {

    public GalleyContainer(int id, PlayerInventory playerInv, AbstractGalleyEntity galleyEntity) {
        super(ContainerType.GENERIC_9x1, id, galleyEntity);

        for (int i = 0; i < 1; i++) {// row/reihe
            for (int j = 0; j < 9; j++)
                addSlot((Slot)new SlotItemHandler((IItemHandler)this.galleyInv, j + i * 9, 8 + j * 18, 18 + i * 18));
        }
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 9; l++)
                addSlot(new Slot((IInventory)playerInv, l + k * 9 + 9, 8 + l * 18, 103 + k * 18 + 36));
        }
        for (int x = 0; x < 9; x++)
            addSlot(new Slot((IInventory)playerInv, x, 8 + x * 18, 197));
    }
}