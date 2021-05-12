package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractSailBoat;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageRampState implements Message<MessageRampState> {

    private boolean rampState;

    public MessageRampState() {
    }

    public MessageRampState(boolean rampState) {
        this.rampState = rampState;
    }

    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    public void executeServerSide(NetworkEvent.Context context) {
        Entity entity = context.getSender().getEntity();
        AbstractSailBoat sailBoat = (AbstractSailBoat) entity;
            sailBoat.setRampState(rampState);
    }

    public MessageRampState fromBytes(PacketBuffer buf) {
        this.rampState = buf.readBoolean();
        return this;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(this.rampState);
    }

}