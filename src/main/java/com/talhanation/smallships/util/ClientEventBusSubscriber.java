package com.talhanation.smallships.util;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.client.render.*;
import com.talhanation.smallships.init.ModEntityTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD , value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void clientsetup(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ModEntityTypes.COG_ENTITY.get(), RenderEntityCog::new);
        event.registerEntityRenderer(ModEntityTypes.GALLEY_ENTITY.get(), RenderEntityGalley::new);
        event.registerEntityRenderer(ModEntityTypes.WAR_GALLEY_ENTITY.get(), RenderEntityWarGalley::new);
        event.registerEntityRenderer(ModEntityTypes.DRAKKAR_ENTITY.get(), RenderEntityDrakkar::new);
        event.registerEntityRenderer(ModEntityTypes.ROWBOAT_ENTITY.get(), RenderEntityRowBoat::new);
        event.registerEntityRenderer(ModEntityTypes.BRIGG_ENTITY.get(), RenderEntityBrigg::new);
        event.registerEntityRenderer(ModEntityTypes.DHOW_ENTITY.get(), RenderEntityDhow::new);
    }

}
