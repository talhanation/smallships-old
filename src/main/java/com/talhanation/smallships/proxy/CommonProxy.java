package com.talhanation.smallships.proxy;

import java.io.File;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.entities.*;
import com.talhanation.smallships.network.*;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {
    public static SimpleNetworkWrapper simpleNetworkWrapper;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    public void preinit(FMLPreInitializationEvent event) {
        simpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("smallships");

        simpleNetworkWrapper.registerMessage(MessagePaddleState.class, MessagePaddleState.class, 0, Side.SERVER);
        simpleNetworkWrapper.registerMessage(MessageSailState.class, MessageSailState.class, 1, Side.SERVER);
        simpleNetworkWrapper.registerMessage(MessageSteerState.class, MessageSteerState.class, 2, Side.SERVER);
        simpleNetworkWrapper.registerMessage(MessageOpenInv.class, MessageOpenInv.class, 3, Side.SERVER);
        simpleNetworkWrapper.registerMessage(MessageIsForward.class,MessageIsForward.class, 4, Side.SERVER);
        simpleNetworkWrapper.registerMessage(MessageDismount.class,  MessageDismount.class, 5, Side.SERVER);


        //DataSerializers.registerSerializer(DataSerializerItemList.ITEM_LIST);
        try {
            File configFolder = new File(event.getModConfigurationDirectory(), "smallships");
            //configFolder.mkdirs();
            //Config.init(configFolder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init(FMLInitializationEvent event) {
        EntityRegistry.registerModEntity(new ResourceLocation(Main.MOD_ID, "cog"), CogEntity.class, "cog", 9801,
                Main.instance(), 64, 1, true);

        EntityRegistry.registerModEntity(new ResourceLocation(Main.MOD_ID,  "galley"), GalleyEntity.class, "galley", 9802,
                Main.instance(), 64, 1, true);

        EntityRegistry.registerModEntity(new ResourceLocation(Main.MOD_ID, "war_galley"), WarGalleyEntity.class, "war_galley", 9803,
                Main.instance(), 64, 1, true);

        EntityRegistry.registerModEntity(new ResourceLocation(Main.MOD_ID, "drakkar"), DrakkarEntity.class, "drakkar", 9804,
                Main.instance(), 64, 1, true);

        EntityRegistry.registerModEntity(new ResourceLocation(Main.MOD_ID,  "brigg"), BriggEntity.class, "brigg", 9805,
                Main.instance(), 64, 1, true);

        EntityRegistry.registerModEntity(new ResourceLocation(Main.MOD_ID, , "rowboat"), RowBoatEntity.class, "rowboat", 9806,
                Main.instance(), 64, 1, true);

        EntityRegistry.registerModEntity(new ResourceLocation(Main.MOD_ID,  "dhow"), DhowEntity.class, "dhow", 9807,
                Main.instance(), 64, 1, true);

    }

    public void postinit(FMLPostInitializationEvent event) {
        //Config.postInit();
    }
}