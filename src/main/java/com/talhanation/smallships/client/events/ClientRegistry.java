package com.talhanation.smallships.client.events;

import net.minecraft.client.settings.KeyBinding;

public class ClientRegistry {

    public static KeyBinding registerKeyBinding(String name, String category, int keyCode) {
        KeyBinding keyBinding = new KeyBinding(name, keyCode, category);
        net.minecraftforge.fml.client.registry.ClientRegistry.registerKeyBinding(keyBinding);
        return keyBinding;
    }

}