package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.TNBoatEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

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
        Entity riding = context.getSender().getVehicle();
        if (!(riding instanceof TNBoatEntity))
            return;
        TNBoatEntity boat = (TNBoatEntity) riding;
        if (context.getSender() == (boat.getDriver())) {
            boat.setPaddleState(left, right);
        }
    }

    public MessagePaddleState fromBytes(FriendlyByteBuf buf) {
        this.left = buf.readBoolean();
        this.right = buf.readBoolean();
        return this;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(this.left);
        buf.writeBoolean(this.right);
    }

}
