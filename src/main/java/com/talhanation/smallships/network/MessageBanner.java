package com.talhanation.smallships.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageBanner implements Message<MessageBanner> {

    private boolean banner;

    public MessageBanner() {
    }

    public MessageBanner(boolean banner) {
        this.banner = banner;
    }

    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    public void executeServerSide(NetworkEvent.Context context) {
        //AbstractBannerUser.setHasBanner(banner);
    }

    public MessageBanner fromBytes(PacketBuffer buf) {
        this.banner = buf.readBoolean();
        return this;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(this.banner);
    }

}