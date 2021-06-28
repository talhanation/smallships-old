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
            () -> EntityType.Builder.<CogEntity>of(CogEntity::new, EntityClassification.MISC)
                    .sized(3.5F, 1.25F)
                    .clientTrackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "cog").toString()));

    public static final RegistryObject<EntityType<GalleyEntity>> GALLEY_ENTITY = ENTITY_TYPES.register("galley",
            () -> EntityType.Builder.<GalleyEntity>of(GalleyEntity::new, EntityClassification.MISC)
                    .sized(4.0F, 1.25F)
                    .clientTrackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "galley").toString()));

    public static final RegistryObject<EntityType<WarGalleyEntity>> WAR_GALLEY_ENTITY = ENTITY_TYPES.register("war_galley",
            () -> EntityType.Builder.<WarGalleyEntity>of(WarGalleyEntity::new, EntityClassification.MISC)
                    .sized(5.5F, 1.50F)
                    .clientTrackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "war_galley").toString()));

    public static final RegistryObject<EntityType<DrakkarEntity>> DRAKKAR_ENTITY = ENTITY_TYPES.register("drakkar",
            () -> EntityType.Builder.<DrakkarEntity>of(DrakkarEntity::new, EntityClassification.MISC)
                    .sized(4.0F, 1.50F)
                    .clientTrackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "drakkar").toString()));

    public static final RegistryObject<EntityType<RowBoatEntity>> ROWBOAT_ENTITY = ENTITY_TYPES.register("rowboat",
            () -> EntityType.Builder.<RowBoatEntity>of(RowBoatEntity::new, EntityClassification.MISC)
                    .sized(2.75F, 1.25F)
                    .clientTrackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "rowboat").toString()));

    public static final RegistryObject<EntityType<BriggEntity>> BRIGG_ENTITY = ENTITY_TYPES.register("brigg",
            () -> EntityType.Builder.<BriggEntity>of(BriggEntity::new, EntityClassification.MISC)
                    .sized(5.5F, 1.25F)
                    .clientTrackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "brigg").toString()));

    public static final RegistryObject<EntityType<DhowEntity>> DHOW_ENTITY = ENTITY_TYPES.register("dhow",
            () -> EntityType.Builder.<DhowEntity>of(DhowEntity::new, EntityClassification.MISC)
                    .sized(5.5F, 1.25F)
                    .clientTrackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "dhow").toString()));
}