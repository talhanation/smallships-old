package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractSailBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

import java.io.IOException;
import java.util.function.Supplier;

public class MessageSailState implements Message<MessageSailState> {
    private boolean state;


    public MessageSailState() {
    }

    public MessageSailState(boolean state){
        this.state = state;
    }

    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    public void executeServerSide(NetworkEvent.Context context) {
        Entity riding = context.getSender().getRidingEntity();
        if (!(riding instanceof AbstractSailBoatEntity))
            return;
        AbstractSailBoatEntity sailboat = (AbstractSailBoatEntity) riding;
        if (context.getSender() == (sailboat.getDriver()))
            if (sailboat.bindingToggled) {
                sailboat.SailState = true;
            }
            else sailboat.SailState = false;
    }

    public MessageSailState fromBytes(PacketBuffer buf) {
        this.state = buf.readBoolean();
        return this;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(this.state);
    }

}