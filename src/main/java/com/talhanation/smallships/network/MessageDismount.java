package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.TNBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;

public class MessageDismount implements Message<MessageDismount> {

    private UUID uuid;
    private Entity passenger;

    public MessageDismount() {
    }

    public MessageDismount(PlayerEntity player, Entity passenger) {
        this.uuid = player.getUUID();
        this.passenger = passenger;
    }

    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    public void executeServerSide(NetworkEvent.Context context) {
        if (!context.getSender().getUUID().equals(this.uuid))
            return;
        if (passenger.getVehicle() instanceof TNBoatEntity){
            passenger.stopRiding();
        }

    }

    public MessageDismount fromBytes(PacketBuffer buf) {
        this.uuid = buf.readUUID();
        return this;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeUUID(this.uuid);

    }

}