package com.talhanation.smallships.util;

import com.talhanation.smallships.entities.AbstractCogEntity;
import net.minecraftforge.items.ItemStackHandler;

public class SailBoatItemStackHandler<T extends AbstractCogEntity> extends ItemStackHandler {
    protected final T sailboat;

    public SailBoatItemStackHandler(int slots, T sailboat) {
        super(slots);
        this.sailboat = sailboat;
    }

}