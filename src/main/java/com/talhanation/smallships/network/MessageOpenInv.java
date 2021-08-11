package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractInventoryBoat;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public class MessageOpenInv extends MessageToServer<MessageOpenInv> {
    private boolean pressed;
    private UUID uuid;


    public MessageOpenInv(EntityPlayer player){
        this.uuid = new UUID(0L, 0L);
        this.pressed = false;

    }

    public void execute(EntityPlayer player, MessageOpenInv message) {
        if (!message.pressed)
            return;
        if (!player.getUniqueID().equals(message.uuid)) {
            System.out.println("---------UUID was not the same-----------");
            return;
        }
        Entity vehicle = player.getRidingEntity();
        if (!(vehicle instanceof AbstractInventoryBoat))
            return;
        AbstractInventoryBoat ship = (AbstractInventoryBoat)vehicle;
        if (player.equals(ship.getDriver()))
            ship.openContainer(player);
    }


    public void fromBytes(ByteBuf buf) {
        this.pressed = buf.readBoolean();
        long l1 = buf.readLong();
        long l2 = buf.readLong();
        this.uuid = new UUID(l1, l2);
    }

    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.pressed);
        buf.writeLong(this.uuid.getMostSignificantBits());
        buf.writeLong(this.uuid.getLeastSignificantBits());
    }
}
