package com.talhanation.smallships.util;

import com.talhanation.smallships.entities.sailboats.AbstractGalleyEntity;
import net.minecraftforge.items.ItemStackHandler;

public class GalleyItemStackHandler <T extends AbstractGalleyEntity> extends ItemStackHandler {
    protected final T galley;

    public GalleyItemStackHandler(int slots, T galley) {
        super(slots);
        this.galley = galley;
    }

}