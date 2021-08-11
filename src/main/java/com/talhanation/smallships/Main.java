package com.talhanation.smallships;

import com.talhanation.smallships.proxy.CommonProxy;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = "smallships", version = "1.10.0", acceptedMinecraftVersions = "[1.12.2]")
public class Main {
    public static final String MOD_ID = "smallships";
    public static final String VERSION = "1.10.0";
    public static final String MC_VERSION = "[1.12.2]";

    public static KeyBinding DISMOUNT_KEY;

    @Mod.Instance
    private static Main instance;

    @SidedProxy(clientSide = "ClientProxy", serverSide = "CommonProxy")
    public static CommonProxy proxy;


    public Main() {

        instance = this;

    }


    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        proxy.preinit(event);
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        proxy.postinit(event);
    }

    public static Main instance() {
        return instance;
    }


}
