package com.talhanation.smallships.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.talhanation.smallships.Main;
import com.talhanation.smallships.client.render.RenderBanner;
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
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class AbstractBannerUser extends AbstractSailBoat {
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

    public void scanBanner(ItemStack itemInSlot){
        if(itemInSlot.getItem() instanceof BannerItem){
            setBanner(itemInSlot);
        } else setBannerNull();
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

    public void setBanner(ItemStack banner) {
            //playBannerSound(!banner.isEmpty());
            this.banner = banner.copy();
            this.banner.setCount(1);
    }

    public void setBannerNull() {
        this.banner = null;
    }


    ////////////////////////////////////SERVER////////////////////////////////////

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

        if (!this.level.isClientSide()) {
            if (!playerEntity.isCreative()) banner.shrink(1);
            return true;
        }
        return false;
    }

    ////////////////////////////////////OTHER FUNCTIONS////////////////////////////////////

    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer ,int packedLight, float partialTicks) {
        if (!banner.isEmpty())
        RenderBanner.renderBanner(this, partialTicks, matrixStack, buffer, this.banner, packedLight, BannerTileEntityRenderer.makeFlag());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void dropBanner() {
        if (!banner.isEmpty()) {
            this.spawnAtLocation(banner);
        }else return;
    }
}
