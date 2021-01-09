package com.talhanation.smallships.util;

import com.talhanation.smallships.entities.AbstractSailBoatEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

public class SailBoatItemStackHandler<T extends AbstractSailBoatEntity> extends ItemStackHandler {
    protected final T sailboat;

    public SailBoatItemStackHandler(int slots, T sailboat) {
        super(slots);
        this.sailboat = sailboat;
    }

    public void deserializeNBT(CompoundNBT nbt) {
        nbt.remove("Size");
        super.deserializeNBT(nbt);
    }
}