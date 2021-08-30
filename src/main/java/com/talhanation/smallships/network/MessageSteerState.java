package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractSailBoat;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class MessageSteerState implements Message<MessageSteerState> {

    private boolean left;
    private boolean right;

    public MessageSteerState() {
    }

    public MessageSteerState(boolean left, boolean right) {
        this.left = left;
        this.right = right;
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
            sailBoat.setSteerState(left, right);
        }
    }

    public MessageSteerState fromBytes(FriendlyByteBuf buf) {
        this.left = buf.readBoolean();
        this.right = buf.readBoolean();
        return this;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(this.left);
        buf.writeBoolean(this.right);
    }

}
