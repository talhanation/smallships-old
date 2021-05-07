package com.talhanation.smallships.inventory;

import com.talhanation.smallships.entities.AbstractBriggEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public final class BriggContainer extends AbstractBriggContainer{
    public BriggContainer(int id, PlayerInventory playerInv, AbstractBriggEntity briggEntity) {
        super(ContainerType.GENERIC_9x6, id, briggEntity);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 9; j++)
                addSlot((Slot)new SlotItemHandler((IItemHandler)this.briggInv, j + i * 9, 8 + j * 18, 18 + i * 18));
        }
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 9; l++)
                addSlot(new Slot((IInventory)playerInv, l + k * 9 + 9, 8 + l * 18, 103 + k * 18 + 36));
        }
        for (int x = 0; x < 9; x++)
            addSlot(new Slot((IInventory)playerInv, x, 8 + x * 18, 197));
    }
}