package com.talhanation.smallships.entities.versiontwo;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public abstract class AbstractDamageShip extends AbstractShip {

    private static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(AbstractDamageShip.class, DataSerializers.FLOAT);

    public AbstractDamageShip(EntityType type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void tick() {
        super.tick();

        if (isInLava()) {
            setShipDamage(getShipDamage() + 1F);
        }

        handleParticles();
    }

    @Override
    public Vector3d[] getPlayerOffsets() {
        return new Vector3d[0];
    }

    protected void handleParticles() {
        if (!world.isRemote) {
            return;
        }

       // if (!((EntityPlaneSoundBase) this).isStarted()) {
       //     return;
       // }

        Vector3d lookVec = getLookVec().normalize().scale(1.5D);
        double offX = lookVec.x;
        double offY = lookVec.y;
        double offZ = lookVec.z;

        float damage = getShipDamage();

        float chance = Math.max(damage - 25F, 0) / 100F;

        if (rand.nextFloat() < chance) {
            spawnParticle(ParticleTypes.LARGE_SMOKE, offX, offY, offZ);
        }
    }

    private void spawnParticle(IParticleData particleTypes, double offX, double offY, double offZ, double random) {
        world.addParticle(particleTypes,
                getPosX() + offX + (rand.nextDouble() * random - random / 2D),
                getPosY() + offY + (rand.nextDouble() * random - random / 2D),
                getPosZ() + offZ + (rand.nextDouble() * random - random / 2D),
                0D, 0D, 0D);
    }

    private void spawnParticle(IParticleData particleTypes, double offX, double offY, double offZ) {
        spawnParticle(particleTypes, offX, offY, offZ, 1D);
    }

    @Override
    public void damageShip(double damage, boolean horizontal) {
        super.damageShip(damage, horizontal);
        setShipDamage((float) (getShipDamage() + damage));
    }

    @Override
    public float getPlayerScaleFactor() {
        return 0;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (isInvulnerable()) {
            return false;
        }

        if (world.isRemote || !isAlive()) {
            return false;
        }

        if (!(source.getImmediateSource() instanceof PlayerEntity)) {
            return false;
        }
        PlayerEntity player = (PlayerEntity) source.getImmediateSource();

        if (player == null) {
            return false;
        }

        if (isPassenger(player)) {
            return false;
        }

        if (player.abilities.isCreativeMode) {
            if (player.isSneaking()) {
                //destroyShip(source, player);
                return true;
            }
        }

        ItemStack heldItem = player.getHeldItemMainhand();
        if (heldItem.getItem().equals(Blocks.OAK_PLANKS) && (heldItem.getMaxDamage() - heldItem.getDamage()) >= 512) {
            heldItem.damageItem(512, player, playerEntity -> {
            });
            //destroyShip(source, player);
        }

        return false;
    }

  /*  public void destroyShip(DamageSource source, PlayerEntity player) {
        IInventory inventory = ((AbstractShipInventory) this).getInventory();
        InventoryHelper.dropInventoryItems(world, getPosition(), inventory);
        inventory.clear();

        LootTable loottable = this.world.getServer().getLootTableManager().getLootTableFromLocation(getLootTable());

        LootContext.Builder context = versiontwo LootContext.Builder((ServerWorld) world)
                .withParameter(LootParameters.field_237457_g_, getPositionVec())
                .withParameter(LootParameters.THIS_ENTITY, this)
                .withParameter(LootParameters.DAMAGE_SOURCE, source)
                .withParameter(LootParameters.KILLER_ENTITY, player)
                .withParameter(LootParameters.DIRECT_KILLER_ENTITY, player);
        loottable.generate(context.build(LootParameterSets.ENTITY), this::entityDropItem);

        remove();
    }

*/

    @Override
    protected void registerData() {
        super.registerData();
        dataManager.register(DAMAGE, 0F);
    }

    public float getShipDamage() {
        return dataManager.get(DAMAGE);
    }

    public void setShipDamage(float damage) {
        dataManager.set(DAMAGE, damage);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        setShipDamage(compound.getFloat("Damage"));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putFloat("Damage", getShipDamage());
    }

}

