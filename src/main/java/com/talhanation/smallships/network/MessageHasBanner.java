package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractBannerUser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;


public class MessageHasBanner implements Message<MessageHasBanner> {

    private boolean hasbanner;
    private AbstractBannerUser abstractBannerUser;
    public MessageHasBanner() {
    }

    public MessageHasBanner(boolean hasbanner, AbstractBannerUser abstractBannerUser) {
        this.hasbanner = hasbanner;
        this.abstractBannerUser = abstractBannerUser;

    }

    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    public void executeServerSide(NetworkEvent.Context context) {
        this.abstractBannerUser.setHasBanner(true);
    }

/*
    private static void setHasBanner(PlayerEntity player,AbstractBannerUser bannerUser) {
        ItemStack stack = player.getItemInHand(Hand.MAIN_HAND);
        if (stack.getItem() instanceof BannerItem) {
            bannerUser.setHasBanner(true);
        } else {
            stack = player.getItemInHand(Hand.OFF_HAND);
            if (stack.getItem() instanceof BannerItem) {
                bannerUser.setHasBanner(true);
            }
        }
    }
*/
    public MessageHasBanner fromBytes(PacketBuffer buf) {
        this.hasbanner = buf.readBoolean();
        return this;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(this.hasbanner);
    }

}