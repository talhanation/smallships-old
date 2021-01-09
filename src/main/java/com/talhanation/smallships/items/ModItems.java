package com.talhanation.smallships.items;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.entities.AbstractSailBoatEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


@SuppressWarnings("deprecation")
public class ModItems {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

    public static final RegistryObject<Item> OAK_COG_ITEM = ITEMS.register("oak_cog", () ->
            new CogItem(AbstractSailBoatEntity.Type.OAK, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> SPRUCE_COG_ITEM = ITEMS.register("spruce_cog", () ->
            new CogItem(AbstractSailBoatEntity.Type.SPRUCE, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> BIRCH_COG_ITEM = ITEMS.register("birch_cog", () ->
            new CogItem(AbstractSailBoatEntity.Type.BIRCH, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> JUNGLE_COG_ITEM = ITEMS.register("jungle_cog", () ->
            new CogItem(AbstractSailBoatEntity.Type.JUNGLE, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> ACACIA_COG_ITEM = ITEMS.register("acacia_cog", () ->
            new CogItem(AbstractSailBoatEntity.Type.ACACIA, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> DARK_OAK_COG_ITEM = ITEMS.register("dark_oak_cog", () ->
            new CogItem(AbstractSailBoatEntity.Type.DARK_OAK, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

}

