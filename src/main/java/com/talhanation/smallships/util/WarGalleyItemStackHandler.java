package com.talhanation.smallships.util;

import com.talhanation.smallships.entities.AbstractWarGalleyEntity;
import net.minecraftforge.items.ItemStackHandler;

public class WarGalleyItemStackHandler<T extends AbstractWarGalleyEntity> extends ItemStackHandler {
    protected final T wargalley;

    public WarGalleyItemStackHandler(int slots, T wargalley) {
        super(slots);
        this.wargalley = wargalley;
    }

}