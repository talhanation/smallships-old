package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractInventoryBoat;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class MessageOpenInv implements Message<MessageOpenInv> {

    public MessageOpenInv(){

    }

    public MessageOpenInv(Player player){

    }

    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }


    public void executeServerSide(NetworkEvent.Context context) {
        Entity e = (Entity) context.getSender().getVehicle();
        if (e instanceof AbstractInventoryBoat)
            ((AbstractInventoryBoat)e).openContainer(context.getSender());
    }


    public MessageOpenInv fromBytes(FriendlyByteBuf paramPacketBuffer) {
        return this;
    }


    public void toBytes(FriendlyByteBuf paramPacketBuffer) {

    }
}
