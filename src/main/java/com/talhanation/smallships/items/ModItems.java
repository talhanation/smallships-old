package com.talhanation.smallships.items;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.compatiblity.BiomesOPlenty;
import com.talhanation.smallships.compatiblity.LordOfTheRingsMod;
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

    public static final RegistryObject<Item> OAK_ROWBOAT_ITEM =         createRowBoat("oak", AbstractDrakkarEntity.Type.OAK, true);
    public static final RegistryObject<Item> SPRUCE_ROWBOAT_ITEM =      createRowBoat("spruce", AbstractDrakkarEntity.Type.SPRUCE, true);
    public static final RegistryObject<Item> BIRCH_ROWBOAT_ITEM =       createRowBoat("birch", AbstractDrakkarEntity.Type.BIRCH, true);
    public static final RegistryObject<Item> JUNGLE_ROWBOAT_ITEM =      createRowBoat("jungle", AbstractDrakkarEntity.Type.JUNGLE, true);
    public static final RegistryObject<Item> ACACIA_ROWBOAT_ITEM =      createRowBoat("acacia", AbstractDrakkarEntity.Type.ACACIA, true);
    public static final RegistryObject<Item> DARK_OAK_ROWBOAT_ITEM =    createRowBoat("dark_oak", AbstractDrakkarEntity.Type.DARK_OAK, true);

            //BOP
    public static final RegistryObject<Item> BOP_CHERRY_COG_ITEM =      createCog("bop_cherry", AbstractSailBoatEntity.Type.BOP_CHERRY, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_CHERRY_GALLEY_ITEM =      createGalley("bop_cherry", AbstractGalleyEntity.Type.BOP_CHERRY, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_CHERRY_WAR_GALLEY_ITEM =      createWarGalley("bop_cherry", AbstractWarGalleyEntity.Type.BOP_CHERRY, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_CHERRY_DRAKKAR_ITEM =      createDrakkar("bop_cherry", AbstractDrakkarEntity.Type.BOP_CHERRY, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_CHERRY_ROWBOAT_ITEM =      createRowBoat("bop_cherry", AbstractRowBoatEntity.Type.BOP_CHERRY, BiomesOPlenty.isInstalled());

    public static final RegistryObject<Item> BOP_DEAD_COG_ITEM =      createCog("bop_dead", AbstractSailBoatEntity.Type.BOP_DEAD, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_DEAD_GALLEY_ITEM =      createGalley("bop_dead", AbstractGalleyEntity.Type.BOP_DEAD, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_DEAD_WAR_GALLEY_ITEM =      createWarGalley("bop_dead", AbstractWarGalleyEntity.Type.BOP_DEAD, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_DEAD_DRAKKAR_ITEM =      createDrakkar("bop_dead", AbstractDrakkarEntity.Type.BOP_DEAD, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_DEAD_ROWBOAT_ITEM =      createRowBoat("bop_dead", AbstractRowBoatEntity.Type.BOP_DEAD, BiomesOPlenty.isInstalled());

    public static final RegistryObject<Item> BOP_FIR_COG_ITEM =      createCog("bop_fir", AbstractSailBoatEntity.Type.BOP_FIR, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_FIR_GALLEY_ITEM =      createGalley("bop_fir", AbstractGalleyEntity.Type.BOP_FIR, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_FIR_WAR_GALLEY_ITEM =      createWarGalley("bop_fir", AbstractWarGalleyEntity.Type.BOP_FIR, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_FIR_DRAKKAR_ITEM =      createDrakkar("bop_fir", AbstractDrakkarEntity.Type.BOP_FIR, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_FIR_ROWBOAT_ITEM =      createRowBoat("bop_fir", AbstractRowBoatEntity.Type.BOP_FIR, BiomesOPlenty.isInstalled());

    public static final RegistryObject<Item> BOP_HELLBARK_COG_ITEM =      createCog("bop_hellbark", AbstractSailBoatEntity.Type.BOP_HELLBARK, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_HELLBARK_GALLEY_ITEM =      createGalley("bop_hellbark", AbstractGalleyEntity.Type.BOP_HELLBARK, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_HELLBARK_WAR_GALLEY_ITEM =      createWarGalley("bop_hellbark", AbstractWarGalleyEntity.Type.BOP_HELLBARK, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_HELLBARK_DRAKKAR_ITEM =      createDrakkar("bop_hellbark", AbstractDrakkarEntity.Type.BOP_HELLBARK, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_HELLBARK_ROWBOAT_ITEM =      createRowBoat("bop_hellbark", AbstractRowBoatEntity.Type.BOP_HELLBARK, BiomesOPlenty.isInstalled());

    public static final RegistryObject<Item> BOP_JACARANDA_COG_ITEM =      createCog("bop_jacaranda", AbstractSailBoatEntity.Type.BOP_JACARANDA, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_JACARANDA_GALLEY_ITEM =      createGalley("bop_jacaranda", AbstractGalleyEntity.Type.BOP_JACARANDA, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_JACARANDA_WAR_GALLEY_ITEM =      createWarGalley("bop_jacaranda", AbstractWarGalleyEntity.Type.BOP_JACARANDA, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_JACARANDA_DRAKKAR_ITEM =      createDrakkar("bop_jacaranda", AbstractDrakkarEntity.Type.BOP_JACARANDA, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_JACARANDA_ROWBOAT_ITEM =      createRowBoat("bop_jacaranda", AbstractRowBoatEntity.Type.BOP_JACARANDA, BiomesOPlenty.isInstalled());

    public static final RegistryObject<Item> BOP_MAGIC_COG_ITEM =      createCog("bop_magic", AbstractSailBoatEntity.Type.BOP_MAGIC, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_MAGIC_GALLEY_ITEM =      createGalley("bop_magic", AbstractGalleyEntity.Type.BOP_MAGIC, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_MAGIC_WAR_GALLEY_ITEM =      createWarGalley("bop_magic", AbstractWarGalleyEntity.Type.BOP_MAGIC, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_MAGIC_DRAKKAR_ITEM =      createDrakkar("bop_magic", AbstractDrakkarEntity.Type.BOP_MAGIC, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_MAGIC_ROWBOAT_ITEM =      createRowBoat("bop_magic", AbstractRowBoatEntity.Type.BOP_MAGIC, BiomesOPlenty.isInstalled());

    public static final RegistryObject<Item> BOP_MAHOGANY_COG_ITEM =      createCog("bop_mahogany", AbstractSailBoatEntity.Type.BOP_MAHOGANY, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_MAHOGANY_GALLEY_ITEM =      createGalley("bop_mahogany", AbstractGalleyEntity.Type.BOP_MAHOGANY, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_MAHOGANY_WAR_GALLEY_ITEM =      createWarGalley("bop_mahogany", AbstractWarGalleyEntity.Type.BOP_MAHOGANY, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_MAHOGANY_DRAKKAR_ITEM =      createDrakkar("bop_mahogany", AbstractDrakkarEntity.Type.BOP_MAHOGANY, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_MAHOGANY_ROWBOAT_ITEM =      createRowBoat("bop_mahogany", AbstractRowBoatEntity.Type.BOP_MAHOGANY, BiomesOPlenty.isInstalled());

    public static final RegistryObject<Item> BOP_PALM_COG_ITEM =      createCog("bop_palm", AbstractSailBoatEntity.Type.BOP_PALM, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_PALM_GALLEY_ITEM =      createGalley("bop_palm", AbstractGalleyEntity.Type.BOP_PALM, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_PALM_WAR_GALLEY_ITEM =      createWarGalley("bop_palm", AbstractWarGalleyEntity.Type.BOP_PALM, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_PALM_DRAKKAR_ITEM =      createDrakkar("bop_palm", AbstractDrakkarEntity.Type.BOP_PALM, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_PALM_ROWBOAT_ITEM =      createRowBoat("bop_palm", AbstractRowBoatEntity.Type.BOP_PALM, BiomesOPlenty.isInstalled());

    public static final RegistryObject<Item> BOP_REDWOOD_COG_ITEM =      createCog("bop_redwood", AbstractSailBoatEntity.Type.BOP_REDWOOD, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_REDWOOD_GALLEY_ITEM =      createGalley("bop_redwood", AbstractGalleyEntity.Type.BOP_REDWOOD, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_REDWOOD_WAR_GALLEY_ITEM =      createWarGalley("bop_redwood", AbstractWarGalleyEntity.Type.BOP_REDWOOD, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_REDWOOD_DRAKKAR_ITEM =      createDrakkar("bop_redwood", AbstractDrakkarEntity.Type.BOP_REDWOOD, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_REDWOOD_ROWBOAT_ITEM =      createRowBoat("bop_redwood", AbstractRowBoatEntity.Type.BOP_REDWOOD, BiomesOPlenty.isInstalled());

    public static final RegistryObject<Item> BOP_UMBRAN_COG_ITEM =      createCog("bop_umbran", AbstractSailBoatEntity.Type.BOP_UMBRAN, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_UMBRAN_GALLEY_ITEM =      createGalley("bop_umbran", AbstractGalleyEntity.Type.BOP_UMBRAN, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_UMBRAN_WAR_GALLEY_ITEM =      createWarGalley("bop_umbran", AbstractWarGalleyEntity.Type.BOP_UMBRAN, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_UMBRAN_DRAKKAR_ITEM =      createDrakkar("bop_umbran", AbstractDrakkarEntity.Type.BOP_UMBRAN, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_UMBRAN_ROWBOAT_ITEM =      createRowBoat("bop_umbran", AbstractRowBoatEntity.Type.BOP_UMBRAN, BiomesOPlenty.isInstalled());

    public static final RegistryObject<Item> BOP_WILLOW_COG_ITEM =      createCog("bop_willow", AbstractSailBoatEntity.Type.BOP_WILLOW, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_WILLOW_GALLEY_ITEM =      createGalley("bop_willow", AbstractGalleyEntity.Type.BOP_WILLOW, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_WILLOW_WAR_GALLEY_ITEM =      createWarGalley("bop_willow", AbstractWarGalleyEntity.Type.BOP_WILLOW, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_WILLOW_DRAKKAR_ITEM =      createDrakkar("bop_willow", AbstractDrakkarEntity.Type.BOP_WILLOW, BiomesOPlenty.isInstalled());
    public static final RegistryObject<Item> BOP_WILLOW_ROWBOAT_ITEM =      createRowBoat("bop_willow", AbstractRowBoatEntity.Type.BOP_WILLOW, BiomesOPlenty.isInstalled());


        //LOTR
    public static final RegistryObject<Item> LOTR_APPLE_COG_ITEM =      createCog("lotr_apple", AbstractSailBoatEntity.Type.LOTR_APPLE, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_APPLE_GALLEY_ITEM =      createGalley("lotr_apple", AbstractGalleyEntity.Type.LOTR_APPLE, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_APPLE_WAR_GALLEY_ITEM =      createWarGalley("lotr_apple", AbstractWarGalleyEntity.Type.LOTR_APPLE, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_APPLE_DRAKKAR_ITEM =      createDrakkar("lotr_apple", AbstractDrakkarEntity.Type.LOTR_APPLE, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_APPLE_ROWBOAT_ITEM =      createRowBoat("lotr_apple", AbstractRowBoatEntity.Type.LOTR_APPLE, LordOfTheRingsMod.isInstalled());

    public static final RegistryObject<Item> LOTR_ASPEN_COG_ITEM =      createCog("lotr_aspen", AbstractSailBoatEntity.Type.LOTR_ASPEN, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_ASPEN_GALLEY_ITEM =      createGalley("lotr_aspen", AbstractGalleyEntity.Type.LOTR_ASPEN, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_ASPEN_WAR_GALLEY_ITEM =      createWarGalley("lotr_aspen", AbstractWarGalleyEntity.Type.LOTR_ASPEN, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_ASPEN_DRAKKAR_ITEM =      createDrakkar("lotr_aspen", AbstractDrakkarEntity.Type.LOTR_ASPEN, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_ASPEN_ROWBOAT_ITEM =      createRowBoat("lotr_aspen", AbstractRowBoatEntity.Type.LOTR_ASPEN, LordOfTheRingsMod.isInstalled());

    public static final RegistryObject<Item> LOTR_BEECH_COG_ITEM =      createCog("lotr_beech", AbstractSailBoatEntity.Type.LOTR_BEECH, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_BEECH_GALLEY_ITEM =      createGalley("lotr_beech", AbstractGalleyEntity.Type.LOTR_BEECH, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_BEECH_WAR_GALLEY_ITEM =      createWarGalley("lotr_beech", AbstractWarGalleyEntity.Type.LOTR_BEECH, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_BEECH_DRAKKAR_ITEM =      createDrakkar("lotr_beech", AbstractDrakkarEntity.Type.LOTR_BEECH, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_BEECH_ROWBOAT_ITEM =      createRowBoat("lotr_beech", AbstractRowBoatEntity.Type.LOTR_BEECH, LordOfTheRingsMod.isInstalled());

    public static final RegistryObject<Item> LOTR_CEDAR_COG_ITEM =      createCog("lotr_cedar", AbstractSailBoatEntity.Type.LOTR_CEDAR, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_CEDAR_GALLEY_ITEM =      createGalley("lotr_cedar", AbstractGalleyEntity.Type.LOTR_CEDAR, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_CEDAR_WAR_GALLEY_ITEM =      createWarGalley("lotr_cedar", AbstractWarGalleyEntity.Type.LOTR_CEDAR, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_CEDAR_DRAKKAR_ITEM =      createDrakkar("lotr_cedar", AbstractDrakkarEntity.Type.LOTR_CEDAR, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_CEDAR_ROWBOAT_ITEM =      createRowBoat("lotr_cedar", AbstractRowBoatEntity.Type.LOTR_CEDAR, LordOfTheRingsMod.isInstalled());

    public static final RegistryObject<Item> LOTR_CHARRED_COG_ITEM =      createCog("lotr_charred", AbstractSailBoatEntity.Type.LOTR_CHARRED, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_CHARRED_GALLEY_ITEM =      createGalley("lotr_charred", AbstractGalleyEntity.Type.LOTR_CHARRED, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_CHARRED_WAR_GALLEY_ITEM =      createWarGalley("lotr_charred", AbstractWarGalleyEntity.Type.LOTR_CHARRED, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_CHARRED_DRAKKAR_ITEM =      createDrakkar("lotr_charred", AbstractDrakkarEntity.Type.LOTR_CHARRED, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_CHARRED_ROWBOAT_ITEM =      createRowBoat("lotr_charred", AbstractRowBoatEntity.Type.LOTR_CHARRED, LordOfTheRingsMod.isInstalled());

    public static final RegistryObject<Item> LOTR_CHERRY_COG_ITEM =      createCog("lotr_cherry", AbstractSailBoatEntity.Type.LOTR_CHERRY, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_CHERRY_GALLEY_ITEM =      createGalley("lotr_cherry", AbstractGalleyEntity.Type.LOTR_CHERRY, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_CHERRY_WAR_GALLEY_ITEM =      createWarGalley("lotr_cherry", AbstractWarGalleyEntity.Type.LOTR_CHERRY, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_CHERRY_DRAKKAR_ITEM =      createDrakkar("lotr_cherry", AbstractDrakkarEntity.Type.LOTR_CHERRY, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_CHERRY_ROWBOAT_ITEM =      createRowBoat("lotr_cherry", AbstractRowBoatEntity.Type.LOTR_CHERRY, LordOfTheRingsMod.isInstalled());

    public static final RegistryObject<Item> LOTR_CYPRESS_COG_ITEM =      createCog("lotr_cypress", AbstractSailBoatEntity.Type.LOTR_CYPRESS, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_CYPRESS_GALLEY_ITEM =      createGalley("lotr_cypress", AbstractGalleyEntity.Type.LOTR_CYPRESS, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_CYPRESS_WAR_GALLEY_ITEM =      createWarGalley("lotr_cypress", AbstractWarGalleyEntity.Type.LOTR_CYPRESS, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_CYPRESS_DRAKKAR_ITEM =      createDrakkar("lotr_cypress", AbstractDrakkarEntity.Type.LOTR_CYPRESS, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_CYPRESS_ROWBOAT_ITEM =      createRowBoat("lotr_cypress", AbstractRowBoatEntity.Type.LOTR_CYPRESS, LordOfTheRingsMod.isInstalled());

    public static final RegistryObject<Item> LOTR_FIR_COG_ITEM =      createCog("lotr_fir", AbstractSailBoatEntity.Type.LOTR_FIR, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_FIR_GALLEY_ITEM =      createGalley("lotr_fir", AbstractGalleyEntity.Type.LOTR_FIR, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_FIR_WAR_GALLEY_ITEM =      createWarGalley("lotr_fir", AbstractWarGalleyEntity.Type.LOTR_FIR, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_FIR_DRAKKAR_ITEM =      createDrakkar("lotr_fir", AbstractDrakkarEntity.Type.LOTR_FIR, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_FIR_ROWBOAT_ITEM =      createRowBoat("lotr_fir", AbstractRowBoatEntity.Type.LOTR_FIR, LordOfTheRingsMod.isInstalled());

    public static final RegistryObject<Item> LOTR_GREEN_OAK_COG_ITEM =      createCog("lotr_green_oak", AbstractSailBoatEntity.Type.LOTR_GREEN_OAK, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_GREEN_OAK_GALLEY_ITEM =      createGalley("lotr_green_oak", AbstractGalleyEntity.Type.LOTR_GREEN_OAK, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_GREEN_OAK_WAR_GALLEY_ITEM =      createWarGalley("lotr_green_oak", AbstractWarGalleyEntity.Type.LOTR_GREEN_OAK, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_GREEN_OAK_DRAKKAR_ITEM =      createDrakkar("lotr_green_oak", AbstractDrakkarEntity.Type.LOTR_GREEN_OAK, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_GREEN_OAK_ROWBOAT_ITEM =      createRowBoat("lotr_green_oak", AbstractRowBoatEntity.Type.LOTR_GREEN_OAK, LordOfTheRingsMod.isInstalled());

    public static final RegistryObject<Item> LOTR_HOLLY_COG_ITEM =      createCog("lotr_holly", AbstractSailBoatEntity.Type.LOTR_HOLLY, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_HOLLY_GALLEY_ITEM =      createGalley("lotr_holly", AbstractGalleyEntity.Type.LOTR_HOLLY, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_HOLLY_WAR_GALLEY_ITEM =      createWarGalley("lotr_holly", AbstractWarGalleyEntity.Type.LOTR_HOLLY, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_HOLLY_DRAKKAR_ITEM =      createDrakkar("lotr_holly", AbstractDrakkarEntity.Type.LOTR_HOLLY, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_HOLLY_ROWBOAT_ITEM =      createRowBoat("lotr_holly", AbstractRowBoatEntity.Type.LOTR_HOLLY, LordOfTheRingsMod.isInstalled());

    public static final RegistryObject<Item> LOTR_LAIRELOSSE_COG_ITEM =      createCog("lotr_lairelosse", AbstractSailBoatEntity.Type.LOTR_LAIRELOSSE, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_LAIRELOSSE_GALLEY_ITEM =      createGalley("lotr_lairelosse", AbstractGalleyEntity.Type.LOTR_LAIRELOSSE, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_LAIRELOSSE_WAR_GALLEY_ITEM =      createWarGalley("lotr_lairelosse", AbstractWarGalleyEntity.Type.LOTR_LAIRELOSSE, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_LAIRELOSSE_DRAKKAR_ITEM =      createDrakkar("lotr_lairelosse", AbstractDrakkarEntity.Type.LOTR_LAIRELOSSE, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_LAIRELOSSE_ROWBOAT_ITEM =      createRowBoat("lotr_lairelosse", AbstractRowBoatEntity.Type.LOTR_LAIRELOSSE, LordOfTheRingsMod.isInstalled());

    public static final RegistryObject<Item> LOTR_LARCH_COG_ITEM =      createCog("lotr_larch", AbstractSailBoatEntity.Type.LOTR_LARCH, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_LARCH_GALLEY_ITEM =      createGalley("lotr_larch", AbstractGalleyEntity.Type.LOTR_LARCH, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_LARCH_WAR_GALLEY_ITEM =      createWarGalley("lotr_larch", AbstractWarGalleyEntity.Type.LOTR_LARCH, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_LARCH_DRAKKAR_ITEM =      createDrakkar("lotr_larch", AbstractDrakkarEntity.Type.LOTR_LARCH, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_LARCH_ROWBOAT_ITEM =      createRowBoat("lotr_larch", AbstractRowBoatEntity.Type.LOTR_LARCH, LordOfTheRingsMod.isInstalled());

    public static final RegistryObject<Item> LOTR_LEBETHRON_COG_ITEM =      createCog("lotr_lebethron", AbstractSailBoatEntity.Type.LOTR_LEBETHRON, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_LEBETHRON_GALLEY_ITEM =      createGalley("lotr_lebethron", AbstractGalleyEntity.Type.LOTR_LEBETHRON, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_LEBETHRON_WAR_GALLEY_ITEM =      createWarGalley("lotr_lebethron", AbstractWarGalleyEntity.Type.LOTR_LEBETHRON, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_LEBETHRON_DRAKKAR_ITEM =      createDrakkar("lotr_lebethron", AbstractDrakkarEntity.Type.LOTR_LEBETHRON, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_LEBETHRON_ROWBOAT_ITEM =      createRowBoat("lotr_lebethron", AbstractRowBoatEntity.Type.LOTR_LEBETHRON, LordOfTheRingsMod.isInstalled());

    public static final RegistryObject<Item> LOTR_MALLORN_COG_ITEM =      createCog("lotr_mallorn", AbstractSailBoatEntity.Type.LOTR_MALLORN, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_MALLORN_GALLEY_ITEM =      createGalley("lotr_mallorn", AbstractGalleyEntity.Type.LOTR_MALLORN, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_MALLORN_WAR_GALLEY_ITEM =      createWarGalley("lotr_mallorn", AbstractWarGalleyEntity.Type.LOTR_MALLORN, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_MALLORN_DRAKKAR_ITEM =      createDrakkar("lotr_mallorn", AbstractDrakkarEntity.Type.LOTR_MALLORN, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_MALLORN_ROWBOAT_ITEM =      createRowBoat("lotr_mallorn", AbstractRowBoatEntity.Type.LOTR_MALLORN, LordOfTheRingsMod.isInstalled());

    public static final RegistryObject<Item> LOTR_MAPLE_COG_ITEM =      createCog("lotr_maple", AbstractSailBoatEntity.Type.LOTR_MAPLE, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_MAPLE_GALLEY_ITEM =      createGalley("lotr_maple", AbstractGalleyEntity.Type.LOTR_MAPLE, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_MAPLE_WAR_GALLEY_ITEM =      createWarGalley("lotr_maple", AbstractWarGalleyEntity.Type.LOTR_MAPLE, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_MAPLE_DRAKKAR_ITEM =      createDrakkar("lotr_maple", AbstractDrakkarEntity.Type.LOTR_MAPLE, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_MAPLE_ROWBOAT_ITEM =      createRowBoat("lotr_maple", AbstractRowBoatEntity.Type.LOTR_MAPLE, LordOfTheRingsMod.isInstalled());

    public static final RegistryObject<Item> LOTR_MIRK_OAK_COG_ITEM =      createCog("lotr_mirk_oak", AbstractSailBoatEntity.Type.LOTR_MIRK_OAK, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_MIRK_OAK_GALLEY_ITEM =      createGalley("lotr_mirk_oak", AbstractGalleyEntity.Type.LOTR_MIRK_OAK, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_MIRK_OAK_WAR_GALLEY_ITEM =      createWarGalley("lotr_mirk_oak", AbstractWarGalleyEntity.Type.LOTR_MIRK_OAK, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_MIRK_OAK_DRAKKAR_ITEM =      createDrakkar("lotr_mirk_oak", AbstractDrakkarEntity.Type.LOTR_MIRK_OAK, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_MIRK_OAK_ROWBOAT_ITEM =      createRowBoat("lotr_mirk_oak", AbstractRowBoatEntity.Type.LOTR_MIRK_OAK, LordOfTheRingsMod.isInstalled());

    public static final RegistryObject<Item> LOTR_PEAR_COG_ITEM =      createCog("lotr_pear", AbstractSailBoatEntity.Type.LOTR_PEAR, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_PEAR_GALLEY_ITEM =      createGalley("lotr_pear", AbstractGalleyEntity.Type.LOTR_PEAR, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_PEAR_WAR_GALLEY_ITEM =      createWarGalley("lotr_pear", AbstractWarGalleyEntity.Type.LOTR_PEAR, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_PEAR_DRAKKAR_ITEM =      createDrakkar("lotr_pear", AbstractDrakkarEntity.Type.LOTR_PEAR, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_PEAR_ROWBOAT_ITEM =      createRowBoat("lotr_pear", AbstractRowBoatEntity.Type.LOTR_PEAR, LordOfTheRingsMod.isInstalled());

    public static final RegistryObject<Item> LOTR_PINE_COG_ITEM =      createCog("lotr_pine", AbstractSailBoatEntity.Type.LOTR_PINE, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_PINE_GALLEY_ITEM =      createGalley("lotr_pine", AbstractGalleyEntity.Type.LOTR_PINE, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_PINE_WAR_GALLEY_ITEM =      createWarGalley("lotr_pine", AbstractWarGalleyEntity.Type.LOTR_PINE, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_PINE_DRAKKAR_ITEM =      createDrakkar("lotr_pine", AbstractDrakkarEntity.Type.LOTR_PINE, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_PINE_ROWBOAT_ITEM =      createRowBoat("lotr_pine", AbstractRowBoatEntity.Type.LOTR_PINE, LordOfTheRingsMod.isInstalled());

    public static final RegistryObject<Item> LOTR_ROTTEN_COG_ITEM =      createCog("lotr_rotten", AbstractSailBoatEntity.Type.LOTR_ROTTEN, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_ROTTEN_GALLEY_ITEM =      createGalley("lotr_rotten", AbstractGalleyEntity.Type.LOTR_ROTTEN, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_ROTTEN_WAR_GALLEY_ITEM =      createWarGalley("lotr_rotten", AbstractWarGalleyEntity.Type.LOTR_ROTTEN, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_ROTTEN_DRAKKAR_ITEM =      createDrakkar("lotr_rotten", AbstractDrakkarEntity.Type.LOTR_ROTTEN, LordOfTheRingsMod.isInstalled());
    public static final RegistryObject<Item> LOTR_ROTTEN_ROWBOAT_ITEM =      createRowBoat("lotr_rotten", AbstractRowBoatEntity.Type.LOTR_ROTTEN, LordOfTheRingsMod.isInstalled());
















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

    private static RegistryObject<Item> createRowBoat(String name, TNBoatEntity.Type type, boolean compatiblity ){
        return ITEMS.register(name + "_rowboat", () -> new RowBoatItem(type, (new Item.Properties()).maxStackSize(1).group(compatiblity ? ItemGroup.TRANSPORTATION : null)));
    }
}

