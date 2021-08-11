package com.talhanation.smallships.init;

import com.talhanation.smallships.Main;


import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SoundInit {

    public static SoundEvent SHIP_SAIL_0 = registerSound("sail_0");
    public static SoundEvent SHIP_SAIL_1 = registerSound("sail_1");


    public static SoundEvent registerSound(String soundName) {
        SoundEvent event = new SoundEvent(new ResourceLocation(Main.MOD_ID, soundName));
        event.setRegistryName(new ResourceLocation(Main.MOD_ID, soundName));
        return event;
    }

    public static void playSound(SoundEvent evt, World world, BlockPos pos, EntityPlayer entity, SoundCategory category, float volume) {
        playSound(evt, world, pos, entity, category, volume, 1.0F);
    }

    public static void playSound(SoundEvent evt, World world, BlockPos pos, EntityPlayer entity, SoundCategory category, float volume, float pitch) {
        if (entity != null) {
            world.playSound(entity, pos, evt, category, volume, pitch);
        } else if (!world.isRemote) {
            world.playSound(entity, pos, evt, category, volume, pitch);
        }
    }

    public static void playSound(SoundEvent evt, World world, BlockPos pos, EntityPlayer entity, SoundCategory category) {
        playSound(evt, world, pos, entity, category, 0.15F);
    }

    @SideOnly(Side.CLIENT)
    public static void playSoundLoop(MovingSound loop, World world) {
        if (world.isRemote)
            Minecraft.getMinecraft().getSoundHandler().playSound((ISound)loop);
    }
}
