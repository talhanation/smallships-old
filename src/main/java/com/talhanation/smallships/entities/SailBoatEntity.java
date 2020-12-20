package com.talhanation.smallships.entities;

import com.talhanation.smallships.init.ModEntityTypes;
import com.talhanation.smallships.items.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class SailBoatEntity extends BoatEntity {

    public SailBoatEntity(EntityType<? extends SailBoatEntity> type, World world) {
        super(type, world);
    }

    public SailBoatEntity(World worldIn, double x, double y, double z) {
        this((EntityType<? extends SailBoatEntity>) ModEntityTypes.SAILBOAT_ENTITY.get(), worldIn);
        setPosition(x, y, z);
        setMotion(Vector3d.ZERO);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    public double getMountedYOffset() {
        return 0.0D;
    }

    public Item getItemBoat() {
        switch(this.getBoatType()) {
            case OAK:
            default:
                return ModItems.OAK_SAILBOAT_ITEM.get();
            case SPRUCE:
                return ModItems.SPRUCE_SAILBOAT_ITEM.get();
            case BIRCH:
                return ModItems.BRICH_SAILBOAT_ITEM.get();
            case JUNGLE:
                return ModItems.JUNGLE_SAILBOAT_ITEM.get();
            case ACACIA:
                return ModItems.ACACIA_SAILBOAT_ITEM.get();
            case DARK_OAK:
                return ModItems.DARK_OAK_SAILBOAT_ITEM.get();
        }
    }
}