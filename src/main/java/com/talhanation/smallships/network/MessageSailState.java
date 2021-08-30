package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractSailBoat;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class MessageSailState implements Message<MessageSailState> {

    private int state;

    public MessageSailState() {
    }

    public MessageSailState(int state) {
        this.state = state;
    }

    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    public void executeServerSide(NetworkEvent.Context context) {
        Entity riding = context.getSender().getVehicle();
        if (!(riding instanceof AbstractSailBoat))
            return;
        AbstractSailBoat sailboat = (AbstractSailBoat) riding;
        if (context.getSender() == (sailboat.getDriver())) {
            sailboat.setSailState(state);
        }
    }

    public MessageSailState fromBytes(FriendlyByteBuf buf) {
        this.state = buf.readInt();
        return this;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.state);
    }

}