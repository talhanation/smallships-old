package com.talhanation.smallships.util;

import com.talhanation.smallships.entities.sailboats.AbstractBriggEntity;
import net.minecraftforge.items.ItemStackHandler;

public class BriggItemStackHandler<T extends AbstractBriggEntity> extends ItemStackHandler {
    protected final T brigg;

    public BriggItemStackHandler(int slots, T brigg) {
        super(slots);
        this.brigg = brigg;
    }

}