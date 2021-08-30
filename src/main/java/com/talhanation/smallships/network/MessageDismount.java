package com.talhanation.smallships.network;

import com.talhanation.smallships.client.events.PlayerEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.UUID;

public class MessageDismount implements Message<MessageDismount> {

    private UUID passenger;

    public MessageDismount(){
    }

    public MessageDismount(UUID passenger) {
        this.passenger = passenger;
    }

    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    public void executeServerSide(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        player.level.getEntitiesOfClass(Entity.class, player.getBoundingBox()
                .inflate(16.0D), v -> v
                .getUUID()
                .equals(this.passenger))
                .stream()
                .filter(Entity::isAlive)
                .findAny()
                .ifPresent(passenger -> PlayerEvents.dismountPassenger(passenger, player));

    }

    public MessageDismount fromBytes(FriendlyByteBuf buf) {
        this.passenger = buf.readUUID();
        return this;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(this.passenger);
    }

}