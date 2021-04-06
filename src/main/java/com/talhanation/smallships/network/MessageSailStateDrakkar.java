package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractDrakkarEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageSailStateDrakkar implements Message<MessageSailStateDrakkar> {

    private boolean drstate;

    public MessageSailStateDrakkar() {
    }

    public MessageSailStateDrakkar(boolean drstate) {
        this.drstate = drstate;
    }

    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    public void executeServerSide(NetworkEvent.Context context) {
        Entity riding = context.getSender().getRidingEntity();
        if (!(riding instanceof AbstractDrakkarEntity))
            return;
        AbstractDrakkarEntity drakkar = (AbstractDrakkarEntity) riding;
        if (context.getSender() == (drakkar.getDriver())) {
            drakkar.setSailState(drstate);
        }
    }

    public MessageSailStateDrakkar fromBytes(PacketBuffer buf) {
        this.drstate = buf.readBoolean();
        return this;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(this.drstate);
    }

}