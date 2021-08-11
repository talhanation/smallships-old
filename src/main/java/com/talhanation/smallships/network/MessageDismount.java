package com.talhanation.smallships.network;

import com.talhanation.smallships.client.events.PlayerEvents;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;


import java.util.UUID;

public class MessageDismount extends MessageToServer<MessageDismount> {

    private UUID uuid;

    public MessageDismount(UUID passenger) {
        this.uuid = passenger;
    }


    public void execute(EntityPlayer player, MessageDismount message) {
        AxisAlignedBB box = player.getCollisionBoundingBox();
        player.world.getEntitiesWithinAABB(Entity.class, box.intersect(box))
                .stream()
                .filter(Entity::isRiding)
                .findAny()
                .ifPresent(passenger -> PlayerEvents.dismountPassenger(passenger, player));

    }

    public void fromBytes(ByteBuf buf) {
        long l1 = buf.readLong();
        long l2 = buf.readLong();
        this.uuid = new UUID(l1, l2);

    }

    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.uuid.getMostSignificantBits());
        buf.writeLong(this.uuid.getLeastSignificantBits());
    }

}