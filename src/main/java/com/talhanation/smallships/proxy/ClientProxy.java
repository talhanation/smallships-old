package com.talhanation.smallships.proxy;

import com.talhanation.smallships.client.events.KeyEvents;
import com.talhanation.smallships.client.events.PlayerEvents;
import com.talhanation.smallships.client.events.RenderEvents;
import com.talhanation.smallships.client.render.*;
import com.talhanation.smallships.entities.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{
    public void preinit(FMLPreInitializationEvent event) {
        super.preinit(event);
        RenderingRegistry.registerEntityRenderingHandler(CogEntity.class, new RenderEntityCog());
        RenderingRegistry.registerEntityRenderingHandler(BriggEntity.class, new RenderEntityBrigg());
        RenderingRegistry.registerEntityRenderingHandler(GalleyEntity.class, new RenderEntityGalley());
        RenderingRegistry.registerEntityRenderingHandler(WarGalleyEntity.class, new RenderEntityWarGalley());
        RenderingRegistry.registerEntityRenderingHandler(DhowEntity.class, new RenderEntityDhow());
        RenderingRegistry.registerEntityRenderingHandler(DrakkarEntity.class, new RenderEntityDrakkar());
        RenderingRegistry.registerEntityRenderingHandler(RowBoatEntity.class, new RenderEntityRowBoat());
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
        MinecraftForge.EVENT_BUS.register(new KeyEvents());
        MinecraftForge.EVENT_BUS.register(new RenderEvents());
        MinecraftForge.EVENT_BUS.register(new PlayerEvents());
    }

    public void postinit(FMLPostInitializationEvent event) {
        super.postinit(event);
    }
}
