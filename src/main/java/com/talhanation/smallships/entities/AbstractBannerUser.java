package com.talhanation.smallships.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.talhanation.smallships.Main;
import com.talhanation.smallships.client.render.RenderBanner;
import com.talhanation.smallships.network.MessageBanner;
import com.talhanation.smallships.network.MessageHasBanner;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.BannerTileEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.UUID;

public abstract class AbstractBannerUser extends AbstractSailBoat {
    private static final DataParameter<Boolean> HAS_BANNER = EntityDataManager.defineId(AbstractSailBoat.class, DataSerializers.BOOLEAN);
    public static final DataParameter<ItemStack> BANNER = EntityDataManager.defineId(AbstractBannerUser.class, DataSerializers.ITEM_STACK);

    public ItemStack banner;
    private float bannerWaveAngle;
    private float prevBannerWaveAngle;

    public AbstractBannerUser(EntityType<? extends TNBoatEntity> type, World world) {
        super(type, world);
        this.banner = Items.WHITE_BANNER.getDefaultInstance();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_BANNER, false);

    }

    ////////////////////////////////////TICK////////////////////////////////////

    @Override
    public void tick() {
        super.tick();
        //for banner wave
        this.prevBannerWaveAngle = this.bannerWaveAngle;
        this.bannerWaveAngle = (float) Math.sin(getBannerWaveSpeed() * (float) tickCount) * getBannerWaveFactor();

    }

    ////////////////////////////////////REGISTER////////////////////////////////////

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("banner", banner.serializeNBT());
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        final INBT banner = nbt.get("banner");
        if (banner instanceof CompoundNBT) {
            this.banner = ItemStack.of((CompoundNBT) banner);
        }
    }

    ////////////////////////////////////GET////////////////////////////////////

    public boolean getHasBanner() {
        return entityData.get(HAS_BANNER);
    }

    public float getBannerWaveFactor() {
        return level.isRaining() ? 4.5F : 3.0F;
    }

    public float getBannerWaveSpeed() {
        return level.isRaining() ? 0.55F : 0.25F;
    }

    public float getBannerWaveAngle(float partialTicks) {
        return MathHelper.lerp(partialTicks, this.prevBannerWaveAngle, this.bannerWaveAngle);
    }

    ////////////////////////////////////SET////////////////////////////////////

    public void setHasBanner(boolean hasbanner) {
        if (hasbanner != getHasBanner()) {
            playBannerSound(hasbanner);
            entityData.set(HAS_BANNER, true);
        }
    }

    public void setBanner(ItemStack banner) {
            this.banner = banner.copy();
            this.banner.setCount(1);
    }

    ////////////////////////////////////SERVER////////////////////////////////////

    public void sendHasBannerToServer(boolean hasbanner, AbstractBannerUser abstractBannerUser){
        Main.SIMPLE_CHANNEL.sendToServer(new MessageHasBanner(hasbanner, abstractBannerUser));
    }

    public void sendBannerToServer(ItemStack itemStack){
        Main.SIMPLE_CHANNEL.sendToServer(new MessageBanner(itemStack, this.getUUID()));
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        CompoundNBT bannerNBT = compound.getCompound("banner");
        this.deserializeNBT(bannerNBT);

    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.put("banner",serializeNBT());
    }

    ////////////////////////////////////SOUND////////////////////////////////////

    public void playBannerSound(boolean hasbanner) {
        if (hasbanner) {
            this.level.playSound(null, this.getX(), this.getY() + 4 , this.getZ(),SoundEvents.WOOL_HIT, this.getSoundSource(), 15.0F, 0.8F + 0.4F * this.random.nextFloat());
        }
    }

    ////////////////////////////////////ON FUNCTIONS////////////////////////////////////

    public boolean onInteractionWithBanner(ItemStack banner, PlayerEntity playerEntity, AbstractBannerUser abstractBannerUser) {
        setBanner(banner); //replace with sendBannerToServer(itemStack);
        setHasBanner(true);//replace with sendHasBannerToServer(true, abstractBannerUser);

        if (!this.level.isClientSide()) {
            if (!playerEntity.isCreative()) banner.shrink(1);
            return true;
        }
        return false;
    }

    ////////////////////////////////////OTHER FUNCTIONS////////////////////////////////////

    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer ,int packedLight, float partialTicks) {
        RenderBanner.renderBanner(this, partialTicks, matrixStack, buffer, banner, packedLight, BannerTileEntityRenderer.makeFlag());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void dropBanner() {
        if (getHasBanner())
        this.spawnAtLocation(banner);
    }
}
