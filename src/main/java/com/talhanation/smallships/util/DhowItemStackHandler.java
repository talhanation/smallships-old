package com.talhanation.smallships.util;

import com.talhanation.smallships.entities.AbstractDhowEntity;
import net.minecraftforge.items.ItemStackHandler;

public class DhowItemStackHandler<T extends AbstractDhowEntity> extends ItemStackHandler {
    protected final T dhow;

    public DhowItemStackHandler(int slots, T dhow) {
        super(slots);
        this.dhow = dhow;
    }

}