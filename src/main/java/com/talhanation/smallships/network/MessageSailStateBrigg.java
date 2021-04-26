package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractBriggEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageSailStateBrigg implements Message<MessageSailStateBrigg> {

    private boolean statebrigg;

    public MessageSailStateBrigg() {
    }

    public MessageSailStateBrigg(boolean statebrigg) {
        this.statebrigg = statebrigg;
    }

    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    public void executeServerSide(NetworkEvent.Context context) {
        Entity riding = context.getSender().getRidingEntity();
        if (!(riding instanceof AbstractBriggEntity))
            return;
        AbstractBriggEntity sailboat = (AbstractBriggEntity) riding;
        if (context.getSender() == (sailboat.getDriver())) {
            sailboat.setSailState(statebrigg);
        }
    }

    public MessageSailStateBrigg fromBytes(PacketBuffer buf) {
        this.statebrigg = buf.readBoolean();
        return this;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(this.statebrigg);
    }

}