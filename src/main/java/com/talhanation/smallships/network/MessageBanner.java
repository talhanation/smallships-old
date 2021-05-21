package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractBannerUser;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageBanner implements Message<MessageBanner> {
    ItemStack banner;
    AbstractBannerUser abstractBannerUser;

    public MessageBanner() {
    }

    public MessageBanner(ItemStack banner, AbstractBannerUser abstractBannerUser) {
        this.banner = banner;
        this.abstractBannerUser = abstractBannerUser;
    }

    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    public void executeServerSide(NetworkEvent.Context context) {
        setBanner((PlayerEntity) context.getSender(), banner);
    }

    private void setBanner(PlayerEntity player, ItemStack banner) {
        ItemStack stack = player.getItemInHand(Hand.MAIN_HAND);
        if (stack.getItem() instanceof BannerItem) {
            abstractBannerUser.setBanner(banner);
        } else {
            stack = player.getItemInHand(Hand.OFF_HAND);
            if (stack.getItem() instanceof BannerItem) {
                abstractBannerUser.setBanner(banner);
            }
        }
    }

    public MessageBanner fromBytes(PacketBuffer buf) {
        banner = buf.readItem();
        return this;
    }

    public void toBytes(PacketBuffer paramPacketBuffer) {
    }
}
