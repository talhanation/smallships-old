package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractSailBoat;
import com.talhanation.smallships.entities.TNBoatEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

public class MessagePaddleState extends MessageToServer<MessagePaddleState> {

    private boolean left;
    private boolean right;

    public MessagePaddleState(boolean left, boolean right) {
        this.left = left;
        this.right = right;
    }

    public void execute(EntityPlayer player, MessagePaddleState paramT) {
        Entity vehicle = player.getRidingEntity();
        if (!(vehicle instanceof AbstractSailBoat))
            return;
        AbstractSailBoat sailBoat = (AbstractSailBoat)vehicle;
        if (player.equals(sailBoat.getDriver()))
            sailBoat.setPaddleState(left, right);
    }

    public void fromBytes(ByteBuf buf) {
        this.left = buf.readBoolean();
        this.right = buf.readBoolean();

    }

    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.left);
        buf.writeBoolean(this.right);
    }

}
