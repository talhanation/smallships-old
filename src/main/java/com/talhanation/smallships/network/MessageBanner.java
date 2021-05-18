package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractBannerUser;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageBanner implements Message<MessageBanner> {
    ItemStack banner;
    AbstractBannerUser abstractBannerUser;

    public MessageBanner() {
    }

    public MessageBanner(ItemStack banner) {
        this.banner = banner;
    }


    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    public void executeServerSide(NetworkEvent.Context context) {
        abstractBannerUser.setBanner(this.banner);
    }


    public MessageBanner fromBytes(PacketBuffer buf) {
        banner = buf.readItem();
        return this;
    }


    public void toBytes(PacketBuffer paramPacketBuffer) {

    }
}
