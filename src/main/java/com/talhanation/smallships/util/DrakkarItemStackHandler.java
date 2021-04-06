package com.talhanation.smallships.util;

import com.talhanation.smallships.entities.AbstractDrakkarEntity;
import net.minecraftforge.items.ItemStackHandler;

public class DrakkarItemStackHandler<T extends AbstractDrakkarEntity> extends ItemStackHandler {
    protected final T drakkar;

    public DrakkarItemStackHandler(int slots, T drakkar) {
        super(slots);
        this.drakkar = drakkar;
    }

}