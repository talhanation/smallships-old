package com.talhanation.smallships.blocks;

import com.talhanation.smallships.Main;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);

    public static final RegistryObject<Block> LANTERN_LIGHT = BLOCKS.register("lantern_light",
            () -> new BlockLanternLight(AbstractBlock.Properties.copy(Blocks.AIR)
                    .noCollission()
                    .air()
                    .lightLevel((toInt) -> {return 15;})
                    .noDrops()
                    .noOcclusion()
            ));

    }

