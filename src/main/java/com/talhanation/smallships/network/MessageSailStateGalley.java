package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractGalleyEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageSailStateGalley implements Message<MessageSailStateGalley> {

    private boolean gstate;

    public MessageSailStateGalley() {
    }

    public MessageSailStateGalley(boolean gstate) {
        this.gstate = gstate;
    }

    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    public void executeServerSide(NetworkEvent.Context context) {
        Entity riding = context.getSender().getRidingEntity();
        if (!(riding instanceof AbstractGalleyEntity))
            return;
        AbstractGalleyEntity gelley = (AbstractGalleyEntity) riding;
        if (context.getSender() == (gelley.getDriver())) {
            gelley.setSailState(gstate);
        }
    }

    public MessageSailStateGalley fromBytes(PacketBuffer buf) {
        this.gstate = buf.readBoolean();
        return this;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(this.gstate);
    }

}