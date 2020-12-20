package com.talhanation.smallships.items;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.entities.SailBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


@SuppressWarnings("deprecation")
public class ModItems {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

    public static final RegistryObject<Item> OAK_SAILBOAT_ITEM = ITEMS.register("oak_sail_boat", () ->
            new SailBoatItem(SailBoatEntity.Type.OAK, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> SPRUCE_SAILBOAT_ITEM = ITEMS.register("spruce_sail_boat", () ->
            new SailBoatItem(SailBoatEntity.Type.SPRUCE, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> BRICH_SAILBOAT_ITEM = ITEMS.register("brich_sail_boat", () ->
            new SailBoatItem(SailBoatEntity.Type.BIRCH, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> JUNGLE_SAILBOAT_ITEM = ITEMS.register("jungle_sail_boat", () ->
            new SailBoatItem(SailBoatEntity.Type.JUNGLE, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> ACACIA_SAILBOAT_ITEM = ITEMS.register("acacia_sail_boat", () ->
            new SailBoatItem(SailBoatEntity.Type.ACACIA, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> DARK_OAK_SAILBOAT_ITEM = ITEMS.register("dark_oak_sail_boat", () ->
            new SailBoatItem(SailBoatEntity.Type.ACACIA, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));



}

