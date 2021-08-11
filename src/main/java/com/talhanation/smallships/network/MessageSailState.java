package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractSailBoat;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class MessageSailState extends MessageToServer<MessageSailState> {

    private int state;

    public MessageSailState(int state) {
        this.state = state;
    }

    public void execute(EntityPlayer player, MessageSailState message) {
        Entity vehicle = player.getRidingEntity();
        if (!(vehicle instanceof AbstractSailBoat))
            return;
        AbstractSailBoat ship = (AbstractSailBoat)vehicle;
        if (player.equals(ship.getDriver()))
            ship.setSailState(state);
    }

    public void fromBytes(ByteBuf buf) {
        this.state = buf.readInt();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.state);
    }

}