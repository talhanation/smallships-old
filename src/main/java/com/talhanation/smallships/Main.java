package com.talhanation.smallships;

import com.talhanation.smallships.client.events.ClientRegistry;
import com.talhanation.smallships.client.events.KeyEvents;
import com.talhanation.smallships.client.events.RenderEvents;
import com.talhanation.smallships.config.SmallShipsConfig;
import com.talhanation.smallships.init.ModEntityTypes;
import com.talhanation.smallships.init.SoundInit;
import com.talhanation.smallships.items.ModItems;
import com.talhanation.smallships.network.*;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod("smallships")
public class Main {
    public static final String MOD_ID = "smallships";
    public static SimpleChannel SIMPLE_CHANNEL;
    public static KeyBinding SAIL_KEY;
    public static KeyBinding INV_KEY;
    public static KeyBinding DISMOUNT_KEY;

    public Main() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SmallShipsConfig.CONFIG);
        SmallShipsConfig.loadConfig(SmallShipsConfig.CONFIG, FMLPaths.CONFIGDIR.get().resolve("smallships-common.toml"));

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        SoundInit.SOUNDS.register(modEventBus);
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntityTypes.ENTITY_TYPES.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(Main.this::clientSetup));
    }

    private void setup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        SIMPLE_CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation("smallships", "default"), () -> "1.0.0", s -> true, s -> true);

        SIMPLE_CHANNEL.registerMessage(0, MessagePaddleState.class, (msg, buf) -> msg.toBytes(buf),
                buf -> (new MessagePaddleState()).fromBytes(buf),
                (msg, fun) -> msg.executeServerSide(fun.get()));

        SIMPLE_CHANNEL.registerMessage(1, MessageSailState.class, (msg, buf) -> msg.toBytes(buf),
                buf -> (new MessageSailState()).fromBytes(buf),
                (msg, fun) -> msg.executeServerSide(fun.get()));

        SIMPLE_CHANNEL.registerMessage(2, MessageSteerState.class, (msg, buf) -> msg.toBytes(buf),
                buf -> (new MessageSteerState()).fromBytes(buf),
                (msg, fun) -> msg.executeServerSide(fun.get()));

        SIMPLE_CHANNEL.registerMessage(3, MessageOpenInv.class, (msg, buf) -> msg.toBytes(buf),
                buf -> (new MessageOpenInv()).fromBytes(buf),
                (msg, fun) -> msg.executeServerSide(fun.get()));

        SIMPLE_CHANNEL.registerMessage(4, MessageIsForward.class, (msg, buf) -> msg.toBytes(buf),
                buf -> (new MessageIsForward()).fromBytes(buf),
                (msg, fun) -> msg.executeServerSide(fun.get()));
    }



    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void clientSetup(FMLClientSetupEvent event) {

        MinecraftForge.EVENT_BUS.register(new RenderEvents());

        MinecraftForge.EVENT_BUS.register(new KeyEvents());
        SAIL_KEY = ClientRegistry.registerKeyBinding("key.ship_sail", "category.smallships", 82);
        INV_KEY = ClientRegistry.registerKeyBinding("key.ship_inventory", "category.smallships", 73);
        //DISMOUNT_KEY = ClientRegistry.registerKeyBinding("key.dismount_mobs", "category.smallships", 0);
    }

}
