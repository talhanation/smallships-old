package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.TNBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessagePaddleState implements Message<MessagePaddleState> {

    private boolean left;
    private boolean right;

    public MessagePaddleState() {
    }

    public MessagePaddleState(boolean left, boolean right) {
        this.left = left;
        this.right = right;
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
            boat.setPaddleState(left, right);
        }
    }

    public MessagePaddleState fromBytes(PacketBuffer buf) {
        this.left = buf.readBoolean();
        this.right = buf.readBoolean();
        return this;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(this.left);
        buf.writeBoolean(this.right);
    }

}
