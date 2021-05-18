package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractBannerUser;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageHasBanner implements Message<MessageHasBanner> {

    private boolean hasbanner;
    private AbstractBannerUser abstractBannerUser;
    public MessageHasBanner() {
    }

    public MessageHasBanner(boolean hasbanner) {
        this.hasbanner = hasbanner;
    }

    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    public void executeServerSide(NetworkEvent.Context context) {
        abstractBannerUser.setHasBanner(true);
    }

    public MessageHasBanner fromBytes(PacketBuffer buf) {
        this.hasbanner = buf.readBoolean();
        return this;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(this.hasbanner);
    }

}