package com.talhanation.smallships.client.events;

import com.talhanation.smallships.entities.TNBoatEntity;
import com.talhanation.smallships.network.MessageDismount;
import com.talhanation.smallships.proxy.CommonProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerEvents {

    @SubscribeEvent
    public void onInteractwithPassenger(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget().getRidingEntity() instanceof TNBoatEntity) ) {
            return;
        }

        Entity passenger = event.getTarget();
        EntityPlayer player = event.getEntityPlayer();

        if (!player.isSneaking()) {
            return;
        }
        CommonProxy.simpleNetworkWrapper.sendToServer(new MessageDismount(passenger.getUniqueID()));
        event.setCancellationResult(EnumActionResult.SUCCESS);
        event.setCanceled(true);

    }

    public static void dismountPassenger(Entity passenger, EntityPlayer player) {
        if (!(passenger instanceof EntityPlayer)){
            passenger.dismountRidingEntity();
        }
    }

}
