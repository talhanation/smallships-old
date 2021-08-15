package com.talhanation.smallships.client.events;


import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class ClientRegistry {

    public static KeyMapping registerKeyBinding(String name, String category, int keyCode) {
        KeyMapping keyBinding = new KeyMapping(name, keyCode, category);
        net.minecraftforge.fmlclient.registry.ClientRegistry.registerKeyBinding(keyBinding);
        return keyBinding;
    }
}