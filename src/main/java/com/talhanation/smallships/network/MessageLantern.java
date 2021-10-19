package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.AbstractSailBoat;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageLantern implements Message<MessageLantern> {

    private boolean isLit;

    public MessageLantern() {
    }

    public MessageLantern(boolean isLit) {
        this.isLit = isLit;

    }

    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    public void executeServerSide(NetworkEvent.Context context) {
        Entity riding = context.getSender().getVehicle();
        if (!(riding instanceof AbstractSailBoat))
            return;
        AbstractSailBoat sailBoat = (AbstractSailBoat) riding;
        if (context.getSender() == (sailBoat.getDriver())) {
            sailBoat.setLanternState(isLit);
        }
    }

    public MessageLantern fromBytes(PacketBuffer buf) {
        this.isLit = buf.readBoolean();
        return this;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(this.isLit);
    }

}
