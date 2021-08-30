package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractSailBoat;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

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
        Entity entity = context.getSender();
        AbstractSailBoat sailBoat = (AbstractSailBoat) entity;
            //sailBoat.setRampState(rampState);
    }

    public MessageRampState fromBytes(FriendlyByteBuf buf) {
        this.rampState = buf.readBoolean();
        return this;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(this.rampState);
    }

}