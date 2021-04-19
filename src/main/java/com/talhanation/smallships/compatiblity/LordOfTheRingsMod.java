package com.talhanation.smallships.compatiblity;

import net.minecraft.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ObjectHolder;

public class LordOfTheRingsMod {

    public static boolean isInstalled() {
        return (ModList.get() != null && ModList.get().getModContainerById("lotr").isPresent());
    }

    @ObjectHolder("lotr:apple_boat")
    public static final Item APPLE_BOAT = null;

    @ObjectHolder("lotr:aspen_boat")
    public static final Item ASPEN_BOAT = null;

    @ObjectHolder("lotr:beech_boat")
    public static final Item BEECH_BOAT = null;

    @ObjectHolder("lotr:cedar_boat")
    public static final Item CEDAR_BOAT = null;

    @ObjectHolder("lotr:charred_boat")
    public static final Item CHARRED_BOAT = null;

    @ObjectHolder("lotr:cherry_boat")
    public static final Item CHERRY_BOAT = null;

    @ObjectHolder("lotr:cypress_boat")
    public static final Item CYPRESS_BOAT = null;

    @ObjectHolder("lotr:fir_boat")
    public static final Item FIR_BOAT = null;

    @ObjectHolder("lotr:green_oak_boat")
    public static final Item GREEN_OAK_BOAT = null;

    @ObjectHolder("lotr:holly_boat")
    public static final Item HOLLY_BOAT = null;

    @ObjectHolder("lotr:lairelosse_boat")
    public static final Item LAIRELOSSE_BOAT = null;

    @ObjectHolder("lotr:larch_boat")
    public static final Item LARCH_BOAT = null;

    @ObjectHolder("lotr:lebethron_boat")
    public static final Item LEBETHRON_BOAT = null;

    @ObjectHolder("lotr:mallorn_boat")
    public static final Item MALLORN_BOAT = null;

    @ObjectHolder("lotr:maple_boat")
    public static final Item MAPLE_BOAT = null;

    @ObjectHolder("lotr:mirk_oak_boat")
    public static final Item MIRK_OAK_BOAT = null;

    @ObjectHolder("lotr:pear_boat")
    public static final Item PEAR_BOAT = null;

    @ObjectHolder("lotr:pear_boat")
    public static final Item PINE_BOAT = null;

    @ObjectHolder("lotr:rotten_boat")
    public static final Item ROTTEN_BOAT = null;

}


