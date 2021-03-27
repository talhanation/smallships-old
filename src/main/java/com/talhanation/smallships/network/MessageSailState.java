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
    private boolean up;
    private boolean down;

    public MessageSailState() {
    }

    public MessageSailState(boolean up, boolean down){
        this.up = up;
        this.down = down;
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
                sailboat.setSailState(false, true);
            }
            else sailboat.setSailState(true, false);
    }

    public MessageSailState fromBytes(PacketBuffer buf) {
        this.up = buf.readBoolean();
        this.down = buf.readBoolean();
        return this;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(this.up);
        buf.writeBoolean(this.down);
    }

}