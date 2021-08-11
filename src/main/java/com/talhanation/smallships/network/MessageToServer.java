package com.talhanation.smallships.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Objects;

public abstract class MessageToServer<T extends IMessage> implements IMessage, IMessageHandler<T, IMessage> {
    public IMessage onMessage(final T message, MessageContext ctx) {
        if (ctx.side.equals(Side.SERVER)) {
            final EntityPlayerMP player = (ctx.getServerHandler()).player;
            Objects.requireNonNull(player.getServer()).addScheduledTask(new Runnable() {
                public void run() {
                    MessageToServer.this.execute(player, message);
                }
            });
        }
        return null;
    }

    public abstract void execute(EntityPlayer paramEntityPlayer, T paramT);
}
