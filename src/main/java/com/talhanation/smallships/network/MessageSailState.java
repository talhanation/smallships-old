package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractSailBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

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
            sailboat.setSailState(state);
        if (sailboat.getSailState()){
            sailboat.playFirstSailSoundcounter = 2;
        }else
            sailboat.playLastSailSoundcounter = 2;
    }

    public MessageSailState fromBytes(PacketBuffer buf) {
        this.state = buf.readBoolean();
        return this;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(this.state);
    }

}