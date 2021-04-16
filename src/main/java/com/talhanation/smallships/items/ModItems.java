package com.talhanation.smallships.items;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.compatiblity.BiomesOPlenty;
import com.talhanation.smallships.entities.*;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

    public static final RegistryObject<Item> OAK_COG_ITEM =             createCog("oak", AbstractSailBoatEntity.Type.OAK, true);
    public static final RegistryObject<Item> SPRUCE_COG_ITEM =          createCog("spruce", AbstractSailBoatEntity.Type.SPRUCE, true);
    public static final RegistryObject<Item> BIRCH_COG_ITEM =           createCog("birch", AbstractSailBoatEntity.Type.BIRCH, true);
    public static final RegistryObject<Item> JUNGLE_COG_ITEM =          createCog("jungle", AbstractSailBoatEntity.Type.JUNGLE, true);
    public static final RegistryObject<Item> ACACIA_COG_ITEM =          createCog("acacia", AbstractSailBoatEntity.Type.ACACIA, true);
    public static final RegistryObject<Item> DARK_OAK_COG_ITEM =        createCog("dark_oak", AbstractSailBoatEntity.Type.DARK_OAK, true);

    public static final RegistryObject<Item> SAIL_ITEM = ITEMS.register("sail_item", () -> new Item((new Item.Properties().maxStackSize(1).group(ItemGroup.TRANSPORTATION))));

    public static final RegistryObject<Item> OAK_GALLEY_ITEM =          createGalley("oak", AbstractGalleyEntity.Type.OAK, true);
    public static final RegistryObject<Item> SPRUCE_GALLEY_ITEM =       createGalley("spruce", AbstractGalleyEntity.Type.SPRUCE, true);
    public static final RegistryObject<Item> BIRCH_GALLEY_ITEM =        createGalley("birch", AbstractGalleyEntity.Type.BIRCH, true);
    public static final RegistryObject<Item> JUNGLE_GALLEY_ITEM =       createGalley("jungle", AbstractGalleyEntity.Type.JUNGLE, true);
    public static final RegistryObject<Item> ACACIA_GALLEY_ITEM =       createGalley("acacia", AbstractGalleyEntity.Type.ACACIA, true);
    public static final RegistryObject<Item> DARK_OAK_GALLEY_ITEM =     createGalley("dark_oak", AbstractGalleyEntity.Type.DARK_OAK, true);


    public static final RegistryObject<Item> OAK_WAR_GALLEY_ITEM =      createWarGalley("oak", AbstractWarGalleyEntity.Type.OAK, true);
    public static final RegistryObject<Item> SPRUCE_WAR_GALLEY_ITEM =   createWarGalley("spruce", AbstractWarGalleyEntity.Type.SPRUCE, true);
    public static final RegistryObject<Item> BIRCH_WAR_GALLEY_ITEM =    createWarGalley("birch", AbstractWarGalleyEntity.Type.BIRCH, true);
    public static final RegistryObject<Item> JUNGLE_WAR_GALLEY_ITEM =   createWarGalley("jungle", AbstractWarGalleyEntity.Type.JUNGLE, true);
    public static final RegistryObject<Item> ACACIA_WAR_GALLEY_ITEM =   createWarGalley("acacia", AbstractWarGalleyEntity.Type.ACACIA, true);
    public static final RegistryObject<Item> DARK_OAK_WAR_GALLEY_ITEM = createWarGalley("dark_oak", AbstractWarGalleyEntity.Type.DARK_OAK, true);

    public static final RegistryObject<Item> OAK_DRAKKAR_ITEM =         createDrakkar("oak", AbstractDrakkarEntity.Type.OAK, true);
    public static final RegistryObject<Item> SPRUCE_DRAKKAR_ITEM =      createDrakkar("spruce", AbstractDrakkarEntity.Type.SPRUCE, true);
    public static final RegistryObject<Item> BIRCH_DRAKKAR_ITEM =       createDrakkar("birch", AbstractDrakkarEntity.Type.BIRCH, true);
    public static final RegistryObject<Item> JUNGLE_DRAKKAR_ITEM =      createDrakkar("jungle", AbstractDrakkarEntity.Type.JUNGLE, true);
    public static final RegistryObject<Item> ACACIA_DRAKKAR_ITEM =      createDrakkar("acacia", AbstractDrakkarEntity.Type.ACACIA, true);
    public static final RegistryObject<Item> DARK_OAK_DRAKKAR_ITEM =    createDrakkar("dark_oak", AbstractDrakkarEntity.Type.DARK_OAK, true);


            //BOP
    public static final RegistryObject<Item> BOP_CHERRY_COG_ITEM =      createCog("bop_cherry", AbstractSailBoatEntity.Type.BOP_CHERRY, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_CHERRY_GALLEY_ITEM =      createGalley("bop_cherry", AbstractGalleyEntity.Type.BOP_CHERRY, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_CHERRY_WAR_GALLEY_ITEM =      createWarGalley("bop_cherry", AbstractWarGalleyEntity.Type.BOP_CHERRY, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_CHERRY_DRAKKAR_ITEM =      createDrakkar("bop_cherry", AbstractDrakkarEntity.Type.BOP_CHERRY, BiomesOPlenty.isInstalled());

    public static final RegistryObject<Item> BOP_DEAD_COG_ITEM =      createCog("bop_dead", AbstractSailBoatEntity.Type.BOP_DEAD, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_DEAD_GALLEY_ITEM =      createGalley("bop_dead", AbstractGalleyEntity.Type.BOP_DEAD, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_DEAD_WAR_GALLEY_ITEM =      createWarGalley("bop_dead", AbstractWarGalleyEntity.Type.BOP_DEAD, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_DEAD_DRAKKAR_ITEM =      createDrakkar("bop_dead", AbstractDrakkarEntity.Type.BOP_DEAD, BiomesOPlenty.isInstalled());

    public static final RegistryObject<Item> BOP_FIR_COG_ITEM =      createCog("bop_fir", AbstractSailBoatEntity.Type.BOP_FIR, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_FIR_GALLEY_ITEM =      createGalley("bop_fir", AbstractGalleyEntity.Type.BOP_FIR, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_FIR_WAR_GALLEY_ITEM =      createWarGalley("bop_fir", AbstractWarGalleyEntity.Type.BOP_FIR, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_FIR_DRAKKAR_ITEM =      createDrakkar("bop_fir", AbstractDrakkarEntity.Type.BOP_FIR, BiomesOPlenty.isInstalled());

    public static final RegistryObject<Item> BOP_HELLBARK_COG_ITEM =      createCog("bop_hellbark", AbstractSailBoatEntity.Type.BOP_HELLBARK, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_HELLBARK_GALLEY_ITEM =      createGalley("bop_hellbark", AbstractGalleyEntity.Type.BOP_HELLBARK, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_HELLBARK_WAR_GALLEY_ITEM =      createWarGalley("bop_hellbark", AbstractWarGalleyEntity.Type.BOP_HELLBARK, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_HELLBARK_DRAKKAR_ITEM =      createDrakkar("bop_hellbark", AbstractDrakkarEntity.Type.BOP_HELLBARK, BiomesOPlenty.isInstalled());

    public static final RegistryObject<Item> BOP_JACARANDA_COG_ITEM =      createCog("bop_jacaranda", AbstractSailBoatEntity.Type.BOP_JACARANDA, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_JACARANDA_GALLEY_ITEM =      createGalley("bop_jacaranda", AbstractGalleyEntity.Type.BOP_JACARANDA, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_JACARANDA_WAR_GALLEY_ITEM =      createWarGalley("bop_jacaranda", AbstractWarGalleyEntity.Type.BOP_JACARANDA, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_JACARANDA_DRAKKAR_ITEM =      createDrakkar("bop_jacaranda", AbstractDrakkarEntity.Type.BOP_JACARANDA, BiomesOPlenty.isInstalled());

    public static final RegistryObject<Item> BOP_MAGIC_COG_ITEM =      createCog("bop_magic", AbstractSailBoatEntity.Type.BOP_MAGIC, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_MAGIC_GALLEY_ITEM =      createGalley("bop_magic", AbstractGalleyEntity.Type.BOP_MAGIC, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_MAGIC_WAR_GALLEY_ITEM =      createWarGalley("bop_magic", AbstractWarGalleyEntity.Type.BOP_MAGIC, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_MAGIC_DRAKKAR_ITEM =      createDrakkar("bop_magic", AbstractDrakkarEntity.Type.BOP_MAGIC, BiomesOPlenty.isInstalled());

    public static final RegistryObject<Item> BOP_MAHOGANY_COG_ITEM =      createCog("bop_mahogany", AbstractSailBoatEntity.Type.BOP_MAHOGANY, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_MAHOGANY_GALLEY_ITEM =      createGalley("bop_mahogany", AbstractGalleyEntity.Type.BOP_MAHOGANY, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_MAHOGANY_WAR_GALLEY_ITEM =      createWarGalley("bop_mahogany", AbstractWarGalleyEntity.Type.BOP_MAHOGANY, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_MAHOGANY_DRAKKAR_ITEM =      createDrakkar("bop_mahogany", AbstractDrakkarEntity.Type.BOP_MAHOGANY, BiomesOPlenty.isInstalled());

    public static final RegistryObject<Item> BOP_PALM_COG_ITEM =      createCog("bop_palm", AbstractSailBoatEntity.Type.BOP_PALM, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_PALM_GALLEY_ITEM =      createGalley("bop_palm", AbstractGalleyEntity.Type.BOP_PALM, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_PALM_WAR_GALLEY_ITEM =      createWarGalley("bop_palm", AbstractWarGalleyEntity.Type.BOP_PALM, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_PALM_DRAKKAR_ITEM =      createDrakkar("bop_palm", AbstractDrakkarEntity.Type.BOP_PALM, BiomesOPlenty.isInstalled());

    public static final RegistryObject<Item> BOP_REDWOOD_COG_ITEM =      createCog("bop_redwood", AbstractSailBoatEntity.Type.BOP_REDWOOD, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_REDWOOD_GALLEY_ITEM =      createGalley("bop_redwood", AbstractGalleyEntity.Type.BOP_REDWOOD, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_REDWOOD_WAR_GALLEY_ITEM =      createWarGalley("bop_redwood", AbstractWarGalleyEntity.Type.BOP_REDWOOD, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_REDWOOD_DRAKKAR_ITEM =      createDrakkar("bop_redwood", AbstractDrakkarEntity.Type.BOP_REDWOOD, BiomesOPlenty.isInstalled());

    public static final RegistryObject<Item> BOP_UMBRAN_COG_ITEM =      createCog("bop_umbran", AbstractSailBoatEntity.Type.BOP_UMBRAN, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_UMBRAN_GALLEY_ITEM =      createGalley("bop_umbran", AbstractGalleyEntity.Type.BOP_UMBRAN, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_UMBRAN_WAR_GALLEY_ITEM =      createWarGalley("bop_umbran", AbstractWarGalleyEntity.Type.BOP_UMBRAN, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_UMBRAN_DRAKKAR_ITEM =      createDrakkar("bop_umbran", AbstractDrakkarEntity.Type.BOP_UMBRAN, BiomesOPlenty.isInstalled());

    public static final RegistryObject<Item> BOP_WILLOW_COG_ITEM =      createCog("bop_willow", AbstractSailBoatEntity.Type.BOP_WILLOW, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_WILLOW_GALLEY_ITEM =      createGalley("bop_willow", AbstractGalleyEntity.Type.BOP_WILLOW, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_WILLOW_WAR_GALLEY_ITEM =      createWarGalley("bop_willow", AbstractWarGalleyEntity.Type.BOP_WILLOW, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_WILLOW_DRAKKAR_ITEM =      createDrakkar("bop_willow", AbstractDrakkarEntity.Type.BOP_WILLOW, BiomesOPlenty.isInstalled());




















    private static RegistryObject<Item> createCog(String name, TNBoatEntity.Type type, boolean compatiblity ){
        return ITEMS.register(name + "_cog", () -> new CogItem(type, (new Item.Properties()).maxStackSize(1).group(compatiblity ? ItemGroup.TRANSPORTATION : null)));
    }

    private static RegistryObject<Item> createGalley(String name, TNBoatEntity.Type type, boolean compatiblity ){
        return ITEMS.register(name + "_galley", () -> new GalleyItem(type, (new Item.Properties()).maxStackSize(1).group(compatiblity ? ItemGroup.TRANSPORTATION : null)));
    }

    private static RegistryObject<Item> createWarGalley(String name, TNBoatEntity.Type type, boolean compatiblity ){
        return ITEMS.register(name + "_war_galley", () -> new WarGalleyItem(type, (new Item.Properties()).maxStackSize(1).group(compatiblity ? ItemGroup.TRANSPORTATION : null)));
    }

    private static RegistryObject<Item> createDrakkar(String name, TNBoatEntity.Type type, boolean compatiblity ){
        return ITEMS.register(name + "_drakkar", () -> new DrakkarItem(type, (new Item.Properties()).maxStackSize(1).group(compatiblity ? ItemGroup.TRANSPORTATION : null)));
    }
}

