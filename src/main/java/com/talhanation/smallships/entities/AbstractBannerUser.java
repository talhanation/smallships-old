package com.talhanation.smallships.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.talhanation.smallships.Main;
import com.talhanation.smallships.client.render.RenderBanner;
import com.talhanation.smallships.network.MessageBanner;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.BannerTileEntityRenderer;
import net.minecraft.entity.Entity;
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
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class AbstractBannerUser extends AbstractSailBoat {
    private static final DataParameter<Boolean> HAS_BANNER = EntityDataManager.defineId(AbstractSailBoat.class, DataSerializers.BOOLEAN);
    public ItemStack banner;
    private float bannerWaveAngle;
    private float prevBannerWaveAngle;
    protected AbstractSailBoat abstractSailBoat;


    public AbstractBannerUser(EntityType<? extends TNBoatEntity> type, World world) {
        super(type, world);
        this.banner = Items.WHITE_BANNER.getDefaultInstance();
        this.abstractSailBoat = abstractSailBoat;

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
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_BANNER, false);

    }

    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("banner", this.banner.serializeNBT());
        return compoundNBT;
    }

    public void deserializeNBT(CompoundNBT nbt) {
        INBT banner = nbt.get("banner");
        if (banner instanceof CompoundNBT)
            this.banner = ItemStack.of((CompoundNBT)banner);
    }

    ////////////////////////////////////GET////////////////////////////////////

    public boolean getHasBanner() {
        return entityData.get(HAS_BANNER);
    }

    public float getBannerWaveFactor() {
        return level.isRaining() ? 2.5F : 1.0F;
    }

    public float getBannerWaveSpeed() {
        return level.isRaining() ? 0.75F : 0.35F;
    }

    public float getBannerWaveAngle(float partialTicks) {
        return MathHelper.lerp(partialTicks, this.prevBannerWaveAngle, this.bannerWaveAngle);
    }


    ////////////////////////////////////SET////////////////////////////////////


    public void setHasBanner(boolean banner) {
        if (banner != getSailState()) {
            playBannerSound(banner);
            entityData.set(HAS_BANNER, banner);
        }
    }

    ////////////////////////////////////SERVER////////////////////////////////////

    public void sendHasBannerToServer(boolean banner){
        Main.SIMPLE_CHANNEL.sendToServer(new MessageBanner(banner));
    }


    ////////////////////////////////////SOUND////////////////////////////////////

    public void playBannerSound(boolean banner) {
        if (banner) {
            this.level.playSound(null, this.getX(), this.getY() + 4 , this.getZ(),SoundEvents.WOOL_HIT, this.getSoundSource(), 15.0F, 0.8F + 0.4F * this.random.nextFloat());
        }
    }

    ////////////////////////////////////ON FUNCTIONS////////////////////////////////////

    public boolean isBanner(PlayerEntity playerEntity, ItemStack itemStack) {
        onInteraction(itemStack, playerEntity);
        if (!this.level.isClientSide()){
            setHasBanner(true);// replace later with sendHasBannerToServer(true);
            if (!playerEntity.isCreative())
                itemStack.shrink(1);
            return true;
        }
        else
            return false;
    }

    public void onInteraction(ItemStack itemStack, PlayerEntity playerEntity) {
        if (itemStack.getItem() instanceof BannerItem) {
            this.banner = itemStack.copy();
            this.banner.setCount(1);
        }
    }

    ////////////////////////////////////OTHER FUNCTIONS////////////////////////////////////

    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer ,int packedLight, float partialTicks) {
        RenderBanner.renderBanner(this, partialTicks, matrixStack, buffer, this.banner, packedLight, BannerTileEntityRenderer.makeFlag());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void dropBanner() {
        //this.abstractSailBoat.spawnAtLocation(this.banner); //does not work
    }
}
