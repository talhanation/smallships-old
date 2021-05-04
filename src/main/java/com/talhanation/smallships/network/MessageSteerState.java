package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractSailBoat;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

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
        Entity riding = context.getSender().getRidingEntity();
        if (!(riding instanceof AbstractSailBoat))
            return;
        AbstractSailBoat sailBoat = (AbstractSailBoat) riding;
        if (context.getSender() == (sailBoat.getDriver())) {
            sailBoat.setSteerState(left, right);
        }
    }

    public MessageSteerState fromBytes(PacketBuffer buf) {
        this.left = buf.readBoolean();
        this.right = buf.readBoolean();
        return this;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(this.left);
        buf.writeBoolean(this.right);
    }

}
