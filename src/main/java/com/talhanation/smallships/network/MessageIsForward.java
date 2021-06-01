package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractSailBoat;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

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

    public MessageIsForward fromBytes(PacketBuffer buf) {
        this.forward = buf.readBoolean();
        return this;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(this.forward);
    }

}
