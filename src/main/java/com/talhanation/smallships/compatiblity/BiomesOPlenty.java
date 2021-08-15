package com.talhanation.smallships.compatiblity;

import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ObjectHolder;

public class BiomesOPlenty {

    public static boolean isInstalled() {
        return (ModList.get() != null && ModList.get().getModContainerById("biomesoplenty").isPresent());
    }

    @ObjectHolder("biomesoplenty:cherry_boat")
    public static final Item CHERRY_BOAT = null;

    @ObjectHolder("biomesoplenty:dead_boat")
    public static final Item DEAD_BOAT = null;

    @ObjectHolder("biomesoplenty:fir_boat")
    public static final Item FIR_BOAT = null;

    @ObjectHolder("biomesoplenty:hellbark_boat")
    public static final Item HELLBARK_BOAT = null;

    @ObjectHolder("biomesoplenty:jacaranda_boat")
    public static final Item JACARANDA_BOAT = null;

    @ObjectHolder("biomesoplenty:magic_boat")
    public static final Item MAGIC_BOAT = null;

    @ObjectHolder("biomesoplenty:mahogany_boat")
    public static final Item MAHOGANY_BOAT = null;

    @ObjectHolder("biomesoplenty:palm_boat")
    public static final Item PALM_BOAT = null;

    @ObjectHolder("biomesoplenty:redwood_boat")
    public static final Item REDWOOD_BOAT = null;

    @ObjectHolder("biomesoplenty:umbran_boat")
    public static final Item UMBRAN_BOAT = null;

    @ObjectHolder("biomesoplenty:willow_boat")
    public static final Item WILLOW_BOAT = null;


}
