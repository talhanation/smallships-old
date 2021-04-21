package com.talhanation.smallships.util;

import com.talhanation.smallships.entities.AbstractRowBoatEntity;
import net.minecraftforge.items.ItemStackHandler;

public class RowBoatItemStackHandler<T extends AbstractRowBoatEntity> extends ItemStackHandler {
    protected final T rowboat;

    public RowBoatItemStackHandler(int slots, T rowboat) {
        super(slots);
        this.rowboat = rowboat;
    }

}