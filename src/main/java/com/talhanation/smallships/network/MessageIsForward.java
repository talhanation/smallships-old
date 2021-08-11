package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractSailBoat;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class MessageIsForward extends MessageToServer<MessageIsForward> {

    private boolean forward;

    public MessageIsForward(boolean forward) {
        this.forward = forward;

    }

    public void execute(EntityPlayer player, MessageIsForward message) {
        Entity vehicle = player.getRidingEntity();
        if (!(vehicle instanceof AbstractSailBoat))
            return;
        AbstractSailBoat sailBoat = (AbstractSailBoat)vehicle;
        if (player.equals(sailBoat.getDriver()))
            sailBoat.setIsForward(forward);
    }

    public void fromBytes(ByteBuf buf) {
        this.forward = buf.readBoolean();

    }

    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.forward);
    }

}
