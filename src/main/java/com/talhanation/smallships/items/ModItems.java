package com.talhanation.smallships.items;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.entities.AbstractDrakkarEntity;
import com.talhanation.smallships.entities.AbstractGalleyEntity;
import com.talhanation.smallships.entities.AbstractSailBoatEntity;
import com.talhanation.smallships.entities.AbstractWarGalleyEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

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


    public static final RegistryObject<Item> SAIL_ITEM = ITEMS.register("sail_item", () ->
            new Item((new Item.Properties().maxStackSize(1).group(ItemGroup.TRANSPORTATION))));


    public static final RegistryObject<Item> OAK_GALLEY_ITEM = ITEMS.register("oak_galley", () ->
            new GalleyItem(AbstractGalleyEntity.Type.OAK, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> SPRUCE_GALLEY_ITEM = ITEMS.register("spruce_galley", () ->
            new GalleyItem(AbstractGalleyEntity.Type.SPRUCE, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> BIRCH_GALLEY_ITEM = ITEMS.register("birch_galley", () ->
            new GalleyItem(AbstractGalleyEntity.Type.BIRCH, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> JUNGLE_GALLEY_ITEM = ITEMS.register("jungle_galley", () ->
            new GalleyItem(AbstractGalleyEntity.Type.JUNGLE, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> ACACIA_GALLEY_ITEM = ITEMS.register("acacia_galley", () ->
            new GalleyItem(AbstractGalleyEntity.Type.ACACIA, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> DARK_OAK_GALLEY_ITEM = ITEMS.register("dark_oak_galley", () ->
            new GalleyItem(AbstractGalleyEntity.Type.DARK_OAK, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));


    public static final RegistryObject<Item> OAK_WAR_GALLEY_ITEM = ITEMS.register("oak_war_galley", () ->
            new WarGalleyItem(AbstractWarGalleyEntity.Type.OAK, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> SPRUCE_WAR_GALLEY_ITEM = ITEMS.register("spruce_war_galley", () ->
            new WarGalleyItem(AbstractWarGalleyEntity.Type.SPRUCE, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> BIRCH_WAR_GALLEY_ITEM = ITEMS.register("birch_war_galley", () ->
            new WarGalleyItem(AbstractWarGalleyEntity.Type.BIRCH, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> JUNGLE_WAR_GALLEY_ITEM = ITEMS.register("jungle_war_galley", () ->
            new WarGalleyItem(AbstractWarGalleyEntity.Type.JUNGLE, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> ACACIA_WAR_GALLEY_ITEM = ITEMS.register("acacia_war_galley", () ->
            new WarGalleyItem(AbstractWarGalleyEntity.Type.ACACIA, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> DARK_OAK_WAR_GALLEY_ITEM = ITEMS.register("dark_oak_war_galley", () ->
            new WarGalleyItem(AbstractWarGalleyEntity.Type.DARK_OAK, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));


    public static final RegistryObject<Item> OAK_DRAKKAR_ITEM = ITEMS.register("oak_drakkar", () ->
            new DrakkarItem(AbstractDrakkarEntity.Type.OAK, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> SPRUCE_DRAKKAR_ITEM = ITEMS.register("spruce_drakkar", () ->
            new DrakkarItem(AbstractDrakkarEntity.Type.SPRUCE, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> BIRCH_DRAKKAR_ITEM = ITEMS.register("birch_drakkar", () ->
            new DrakkarItem(AbstractDrakkarEntity.Type.BIRCH, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> JUNGLE_DRAKKAR_ITEM = ITEMS.register("jungle_drakkar", () ->
            new DrakkarItem(AbstractDrakkarEntity.Type.JUNGLE, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> ACACIA_DRAKKAR_ITEM = ITEMS.register("acacia_drakkar", () ->
            new DrakkarItem(AbstractDrakkarEntity.Type.ACACIA, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> DARK_OAK_DRAKKAR_ITEM = ITEMS.register("dark_oak_drakkar", () ->
            new DrakkarItem(AbstractDrakkarEntity.Type.DARK_OAK, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));

}

