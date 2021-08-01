package com.talhanation.smallships.client.events;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.entities.AbstractBannerUser;
import com.talhanation.smallships.entities.TNBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BannerEvents{

    @SubscribeEvent
    public void onInteractwithShip(PlayerInteractEvent.EntityInteract event) {
        Entity target = event.getTarget();
        PlayerEntity player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand(Hand.MAIN_HAND);

        if (!(target instanceof TNBoatEntity) ) {
            return;
        }
        if (!player.isShiftKeyDown()) {
            return;
        }
        if (!(itemInHand.getItem() instanceof BannerItem)){
            return;
        }

        //Main.SIMPLE_CHANNEL.sendToServer(new MessageBanner(itemInHand, target.getUUID()));
        event.setCancellationResult(ActionResultType.SUCCESS);
        event.setCanceled(true);

    }

    public static void onEventInteract(ItemStack itemstack, AbstractBannerUser banneruser, PlayerEntity playerEntity) {
        banneruser.setBanner(itemstack);


    }


}