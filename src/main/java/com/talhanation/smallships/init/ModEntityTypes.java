package com.talhanation.smallships.init;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.entities.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;



public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MOD_ID);

    public static final RegistryObject<EntityType<CogEntity>> COG_ENTITY = ENTITY_TYPES.register("cog",
            () -> EntityType.Builder.<CogEntity>create(CogEntity::new, EntityClassification.MISC)
                    .size(3.5F, 1.25F)
                    .trackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "cog").toString()));

    public static final RegistryObject<EntityType<GalleyEntity>> GALLEY_ENTITY = ENTITY_TYPES.register("galley",
            () -> EntityType.Builder.<GalleyEntity>create(GalleyEntity::new, EntityClassification.MISC)
                    .size(4.0F, 1.25F)
                    .trackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "galley").toString()));

    public static final RegistryObject<EntityType<TNBoatEntity>> TNBOAT = ENTITY_TYPES.register("tnboat",
            () -> EntityType.Builder.<TNBoatEntity>create(TNBoatEntity::new, EntityClassification.MISC)
                    .trackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "tnboat").toString()));

    public static final RegistryObject<EntityType<WarGalleyEntity>> WAR_GALLEY_ENTITY = ENTITY_TYPES.register("war_galley",
            () -> EntityType.Builder.<WarGalleyEntity>create(WarGalleyEntity::new, EntityClassification.MISC)
                    .size(5.5F, 1.50F)
                    .trackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "war_galley").toString()));

    public static final RegistryObject<EntityType<DrakkarEntity>> DRAKKAR_ENTITY = ENTITY_TYPES.register("drakkar",
            () -> EntityType.Builder.<DrakkarEntity>create(DrakkarEntity::new, EntityClassification.MISC)
                    .size(4.0F, 1.50F)
                    .trackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "drakkar").toString()));

    public static final RegistryObject<EntityType<RowBoatEntity>> ROWBOAT_ENTITY = ENTITY_TYPES.register("rowboat",
            () -> EntityType.Builder.<RowBoatEntity>create(RowBoatEntity::new, EntityClassification.MISC)
                    .size(2.75F, 1.25F)
                    .trackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "rowboat").toString()));

    public static final RegistryObject<EntityType<BriggEntity>> BRIGG_ENTITY = ENTITY_TYPES.register("brigg",
            () -> EntityType.Builder.<BriggEntity>create(BriggEntity::new, EntityClassification.MISC)
                    .size(5.5F, 1.25F)
                    .trackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "brigg").toString()));
}