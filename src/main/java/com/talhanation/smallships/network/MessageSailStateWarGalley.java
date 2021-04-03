package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractWarGalleyEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageSailStateWarGalley implements Message<MessageSailStateWarGalley> {

    private boolean wargstate;

    public MessageSailStateWarGalley() {
    }

    public MessageSailStateWarGalley(boolean wargstate) {
        this.wargstate = wargstate;
    }

    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    public void executeServerSide(NetworkEvent.Context context) {
        Entity riding = context.getSender().getRidingEntity();
        if (!(riding instanceof AbstractWarGalleyEntity))
            return;
        AbstractWarGalleyEntity gelley = (AbstractWarGalleyEntity) riding;
        if (context.getSender() == (gelley.getDriver())) {
            gelley.setSailState(wargstate);
        }
    }

    public MessageSailStateWarGalley fromBytes(PacketBuffer buf) {
        this.wargstate = buf.readBoolean();
        return this;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(this.wargstate);
    }

}