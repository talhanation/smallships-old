package com.talhanation.smallships.items;

import com.talhanation.smallships.entities.CogEntity;
import com.talhanation.smallships.entities.CogEntity;
import com.talhanation.smallships.items.render.ShipItemRenderer;
import de.maxhenkel.corelib.CachedMap;
import de.maxhenkel.corelib.item.ItemUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ShipItem extends Item {

    private final CachedMap<ItemStack, CogEntity> cachedCog;

    public ShipItem() {
        super(new Item.Properties().stacksTo(1).setISTER(() -> ShipItemRenderer::new));

        cachedCog = new CachedMap<>(10_000, ItemUtils.ITEM_COMPARATOR);
    }
/*
    public CogEntity getCogEntity(World world, ItemStack stack) {
        return ;
    }

 */

}
