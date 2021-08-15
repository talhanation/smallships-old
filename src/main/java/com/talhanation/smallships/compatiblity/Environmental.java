package com.talhanation.smallships.compatiblity;

import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ObjectHolder;

public class Environmental {
    public static boolean isInstalled() {
        return (ModList.get() != null && ModList.get().getModContainerById("environmental").isPresent());
    }

    @ObjectHolder("environmental:wisteria_boat")
    public static final Item WISTERIA_BOAT = null;

    @ObjectHolder("environmental:willow_boat")
    public static final Item WILLOW_BOAT = null;

    @ObjectHolder("environmental:cherry_boat")
    public static final Item CHERRY_BOAT = null;

}

