package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractSailBoat;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class MessageIsForward implements Message<MessageIsForward> {

    private boolean forward;

    public MessageIsForward() {
    }

    public MessageIsForward(boolean forward) {
        this.forward = forward;

    }

    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    public void executeServerSide(NetworkEvent.Context context) {
        Entity riding = context.getSender().getVehicle();
        if (!(riding instanceof AbstractSailBoat))
            return;
        AbstractSailBoat sailBoat = (AbstractSailBoat) riding;
        if (context.getSender() == (sailBoat.getDriver())) {
            sailBoat.setIsForward(forward);
        }
    }

    public MessageIsForward fromBytes(FriendlyByteBuf buf) {
        this.forward = buf.readBoolean();
        return this;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(this.forward);
    }

}
