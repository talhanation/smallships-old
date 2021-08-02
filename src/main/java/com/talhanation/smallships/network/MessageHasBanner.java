package com.talhanation.smallships.network;
import com.talhanation.smallships.entities.AbstractBannerUser;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;


public class MessageHasBanner implements Message<MessageHasBanner> {

    private boolean hasbanner;
    private ItemStack banner;
    private UUID abstractBannerUser;

    public MessageHasBanner() {
    }

    public MessageHasBanner(boolean hasbanner, UUID abstractBannerUser, ItemStack banner) {
        this.banner = banner;
        this.hasbanner = hasbanner;
        this.abstractBannerUser = abstractBannerUser;

    }

    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    public void executeServerSide(NetworkEvent.Context context) {
        ServerPlayerEntity player = context.getSender();
        player.level.getEntitiesOfClass(AbstractBannerUser.class, player.getBoundingBox()
                        .inflate(16.0D), v -> v
                        .getUUID()
                        .equals(this.abstractBannerUser))
                .stream()
                .findAny()
                .ifPresent(abstractBannerUser -> abstractBannerUser.setHasBanner(true, banner));

    }

    public MessageHasBanner fromBytes(PacketBuffer buf) {
        this.banner = buf.readItem().getStack();
        this.hasbanner = buf.readBoolean();
        this.abstractBannerUser = buf.readUUID();
        return this;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeItemStack(this.banner, false);
        buf.writeBoolean(this.hasbanner);
        buf.writeUUID(this.abstractBannerUser);
    }

}