package com.talhanation.smallships.network;

import com.talhanation.smallships.client.events.PlayerEvents;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

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
        ServerPlayerEntity player = context.getSender();
        player.level.getEntitiesOfClass(Entity.class, player.getBoundingBox()
                .inflate(8.0D), v -> v
                .getUUID()
                .equals(this.passenger))
                .stream()
                .filter(Entity::isAlive)
                .findAny()
                .ifPresent(passenger -> PlayerEvents.dismountPassenger(passenger, (PlayerEntity)player));

    }

    public MessageDismount fromBytes(PacketBuffer buf) {
        this.passenger = buf.readUUID();
        return this;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeUUID(this.passenger);
    }

}