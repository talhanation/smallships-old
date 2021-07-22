package com.talhanation.smallships.network;

import com.talhanation.smallships.entities.TNBoatEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;

public class MessageDismount implements Message<MessageDismount> {

    private UUID uuid;
    private UUID passenger_uuid;
    private BlockPos pos;

    public MessageDismount(){

    }

    public MessageDismount(PlayerEntity player, UUID passenger_uuid, BlockPos pos) {
        this.uuid = player.getUUID();
        this.passenger_uuid = passenger_uuid;
        this.pos = pos;
    }

    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    public void executeServerSide(NetworkEvent.Context context) {
        PlayerEntity playerEntity = context.getSender();
        World world = playerEntity.level;
        BlockPos blockpos = this.pos;

        dismountpassenger(playerEntity, world, blockpos);

        /*
        //Entity passenger = context.getSender().getLevel().getEntity(passenger_uuid);

        Entity passenger = context.getSender().getLevel().getEntity(passenger_uuid);

        if (!context.getSender().getUUID().equals(this.uuid)) {
            return;
        }

        if (passenger.getUUID().equals(passenger_uuid)) {
            if (passenger.getVehicle() instanceof TNBoatEntity) {

                passenger.stopRiding();

            }
        }
*/
    }

    public static ActionResultType dismountpassenger(PlayerEntity playerEntity, World world, BlockPos blockPos) {
        boolean flag = false;
        int i = blockPos.getX();
        int j = blockPos.getY();
        int k = blockPos.getZ();

        for(LivingEntity passenger : world.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB((double)i - 7.0D, (double)j - 7.0D, (double)k - 7.0D, (double)i + 7.0D, (double)j + 7.0D, (double)k + 7.0D))) {
            if (passenger.getVehicle() instanceof TNBoatEntity) {
                passenger.stopRiding();
                flag = true;
            }
        }

        return flag ? ActionResultType.SUCCESS : ActionResultType.PASS;
    }


    public MessageDismount fromBytes(PacketBuffer buf) {
        this.uuid = buf.readUUID();
        this.passenger_uuid = buf.readUUID();
        this.pos = buf.readBlockPos();
        return this;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeUUID(this.uuid);
        buf.writeUUID(this.passenger_uuid);
        buf.writeBlockPos(this.pos);
    }

}