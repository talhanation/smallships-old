package com.talhanation.smallships.util;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.client.render.RenderEntitySailBoat;
import com.talhanation.smallships.init.ModEntityTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD , value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void clientsetup(FMLClientSetupEvent event){
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SAILBOAT_ENTITY.get(), RenderEntitySailBoat::new);
    }

}
