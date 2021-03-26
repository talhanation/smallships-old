package com.talhanation.smallships.network;

import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

import java.io.IOException;

public class CSailStatePacket implements IPacket<IServerPlayNetHandler> {
    private boolean up;
    private boolean down;
    public CSailStatePacket() {
    }

    public CSailStatePacket(boolean up, boolean down) {
        this.up = up;
        this.down = down;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.up = buf.readBoolean();
        this.down = buf.readBoolean();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeBoolean(this.up);
        buf.writeBoolean(this.down);
    }

    @Override
    public void processPacket(IServerPlayNetHandler handler) {
    }

    @Override
    public boolean shouldSkipErrors() {
        return false;
    }

    public boolean getUp() {
        return this.up;
    }

    public boolean getDown() {
        return this.down;
    }


}
