package com.talhanation.smallships.init;

import com.talhanation.smallships.Main;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundInit {

    public static final DeferredRegister<SoundEvent> SOUNDS =  DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Main.MOD_ID);

    public static final RegistryObject<SoundEvent> ENTITY_CAMEL_AMBIENT = SOUNDS.register("entity.camel.ambient",
            () -> new SoundEvent(new ResourceLocation(Main.MOD_ID,"entity.camel.ambient")));

    public static final RegistryObject<SoundEvent> ENTITY_CAMEL_HURT = SOUNDS.register("entity.camel.hurt",
            () -> new SoundEvent(new ResourceLocation(Main.MOD_ID,"entity.camel.hurt")));

    public static final RegistryObject<SoundEvent> ENTITY_CAMEL_DEATH = SOUNDS.register("entity.camel.death",
            () -> new SoundEvent(new ResourceLocation(Main.MOD_ID,"entity.camel.death")));

    public static final RegistryObject<SoundEvent> ENTITY_CAMEL_ANGRY = SOUNDS.register("entity.camel.angry",
            () -> new SoundEvent(new ResourceLocation(Main.MOD_ID,"entity.camel.angry")));

    public static final RegistryObject<SoundEvent> ENTITY_CAMEL_AMBIENT_BABY = SOUNDS.register("entity.camel.ambient.baby",
            () -> new SoundEvent(new ResourceLocation(Main.MOD_ID,"entity.camel.ambient.baby")));

    public static final RegistryObject<SoundEvent> ENTITY_CAMEL_HURT_BABY = SOUNDS.register("entity.camel.hurt.baby",
            () -> new SoundEvent(new ResourceLocation(Main.MOD_ID,"entity.camel.hurt.baby")));

    public static final RegistryObject<SoundEvent> ENTITY_CAMEL_DEATH_BABY = SOUNDS.register("entity.camel.death.baby",
            () -> new SoundEvent(new ResourceLocation(Main.MOD_ID,"entity.camel.death.baby")));

    public static final RegistryObject<SoundEvent> ENTITY_CAMEL_ANGRY_BABY = SOUNDS.register("entity.camel.angry.baby",
            () -> new SoundEvent(new ResourceLocation(Main.MOD_ID,"entity.camel.angry.baby")));
}
