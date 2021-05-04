package com.talhanation.smallships.util;

import com.talhanation.smallships.entities.AbstractCogEntity;
import net.minecraftforge.items.ItemStackHandler;

public class CogItemStackHandler<T extends AbstractCogEntity> extends ItemStackHandler {
    protected final T cog;

    public CogItemStackHandler(int slots, T cog) {
        super(slots);
        this.cog = cog;
    }

}