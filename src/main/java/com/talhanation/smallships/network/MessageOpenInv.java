package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.TNBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageOpenInv implements Message<MessageOpenInv> {

    public MessageOpenInv(){

    }

    public MessageOpenInv(PlayerEntity player){

    }

    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }


    public void executeServerSide(NetworkEvent.Context context) {
        Entity riding = context.getSender().getRidingEntity();
        if (!(riding instanceof TNBoatEntity))
            return;
        TNBoatEntity boat = (TNBoatEntity) riding;
        if (context.getSender() == (boat.getDriver())) {
            boat.openContainer(context.getSender());
        }
    }


    public MessageOpenInv fromBytes(PacketBuffer paramPacketBuffer) {
        return null;
    }


    public void toBytes(PacketBuffer paramPacketBuffer) {

    }
}
