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

    public MessageBanner() {
    }

    public MessageBanner(ItemStack banner) {
        this.banner = banner;
    }

    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    public void executeServerSide(NetworkEvent.Context context) {

    }
    /*
    private void setBanner(PlayerEntity player, ItemStack banner) {
        ItemStack stack = player.getItemInHand(Hand.MAIN_HAND);
        if (stack.getItem() instanceof BannerItem) {
            abstractBannerUser.setBanner(banner.copy());
        } else {
            stack = player.getItemInHand(Hand.OFF_HAND);
            if (stack.getItem() instanceof BannerItem) {
                abstractBannerUser.setBanner(banner);
            }
        }
    }
*/
    public MessageBanner fromBytes(PacketBuffer buf) {
        banner = buf.readItem().getStack();
        return this;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeItemStack(this.banner, false);
    }
}
