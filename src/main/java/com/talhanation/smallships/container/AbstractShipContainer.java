package com.talhanation.smallships.container;

import com.talhanation.smallships.entities.AbstractInventoryBoat;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

public class AbstractShipContainer extends AbstractContainer{

    AbstractInventoryBoat ship;

    public AbstractShipContainer(int id, AbstractInventoryBoat ship, Inventory playerInventroy) {
        super(MenuType.GENERIC_9x3, id, ship.getInventory(), playerInventroy);
        this.ship = ship;

        int rows = getRows();

        for (int j = 0; j < rows; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new Slot(ship.getInventory(), k + j * 9, 8 + k * 18, 72 + j * 18));
            }
        }
        addPlayerInventorySlots();
    }

    public int getRows(){
        return ship.getInventory().getContainerSize() / 9;
    }

    @Override
    public int getInvOffset(){
        return 56;
    }

    public AbstractInventoryBoat getShip(){
        return ship;
    }
}
