package com.talhanation.smallships.entities;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.talhanation.smallships.init.ModEntityTypes;
import com.talhanation.smallships.inventory.CogContainer;
import com.talhanation.smallships.items.ModItems;
import com.talhanation.smallships.util.SailBoatItemStackHandler;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class CogEntity extends AbstractSailBoatEntity {


    private static final ImmutableList<DataParameter<ItemStack>> CARGO = ImmutableList.of(
            EntityDataManager.createKey(CogEntity.class, DataSerializers.ITEMSTACK),
            EntityDataManager.createKey(CogEntity.class, DataSerializers.ITEMSTACK),
            EntityDataManager.createKey(CogEntity.class, DataSerializers.ITEMSTACK),
            EntityDataManager.createKey(CogEntity.class, DataSerializers.ITEMSTACK));

    public CogEntity(EntityType<? extends AbstractSailBoatEntity> entityType, World worldIn) {
        super(entityType, worldIn);
    }

    protected ItemStackHandler initInventory() {
        return (ItemStackHandler)new SailBoatItemStackHandler<CogEntity>(54, this) {
            protected void onLoad() {
                super.onLoad();
                onContentsChanged(0);
            }

            @Override
            protected void onContentsChanged(final int slot) {
                final Object2IntMap<Item> totals = new Object2IntLinkedOpenHashMap<>();
                final Object2ObjectMap<Item, ItemStack> stacks = new Object2ObjectOpenHashMap<>();
                for (int i = 0; i < this.getSlots(); i++) {
                    final ItemStack stack = this.getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        totals.mergeInt(stack.getItem(), 1, Integer::sum);
                        stacks.putIfAbsent(stack.getItem(), stack);
                    }
                }
                Iterator<Object2IntMap.Entry<Item>> topTotals = totals.object2IntEntrySet().stream().sorted(Comparator.<Object2IntMap.Entry<Item>>comparingInt(e ->
                        (e.getKey() instanceof net.minecraft.item.BlockItem) ? 0 : 1).thenComparingInt(e -> -e.getIntValue())).limit(CogEntity.CARGO.size()).iterator();
                ItemStack[] items = new ItemStack[CogEntity.CARGO.size()];
                Arrays.fill((Object[])items, ItemStack.EMPTY);
                int forth = getSlots() / CogEntity.CARGO.size();
                for (int pos = 0; topTotals.hasNext() && pos < CogEntity.CARGO.size(); ) {
                    Object2IntMap.Entry<Item> entry = topTotals.next();
                    int count = Math.max(1, (entry.getIntValue() + forth / 2) / forth);
                    for (int n = 1; n <= count && pos < CogEntity.CARGO.size(); n++) {
                        ItemStack stack = ((ItemStack)stacks.getOrDefault(entry.getKey(), ItemStack.EMPTY)).copy();
                        stack.setCount(Math.min(stack.getMaxStackSize(), entry.getIntValue() / n));
                        items[pos++] = stack;
                    }
                }
                for (int j = 0; j < CogEntity.CARGO.size(); j++)
                    ((CogEntity)this.sailboat).getDataManager().set((DataParameter)CogEntity.CARGO.get(j), items[j]);
            }
        };
    }

    public CogEntity(World worldIn, double x, double y, double z) {
        this((EntityType<? extends AbstractSailBoatEntity>) ModEntityTypes.COG_ENTITY.get(), worldIn);
        setPosition(x, y, z);
        setMotion(Vector3d.ZERO);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    public CogEntity(FMLPlayMessages.SpawnEntity spawnEntity, World worldIn) {
        this((EntityType<? extends AbstractSailBoatEntity>) ModEntityTypes.COG_ENTITY.get(), worldIn);
    }

    public ActionResultType processInitialInteract(PlayerEntity player, Hand hand) {
        if (player.isSecondaryUseActive()) {
            if (this.isBeingRidden())
                this.removePassengers();
            else
                openContainer(player);
            return ActionResultType.func_233537_a_(this.world.isRemote);
        } else if (this.outOfControlTicks < 60.0F) {
            if (!this.world.isRemote) {
                return player.startRiding(this) ? ActionResultType.CONSUME : ActionResultType.PASS;
            } else {
                return ActionResultType.SUCCESS;
            }
        } else {
            return ActionResultType.PASS;
        }
    }


    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
            super.handleStatusUpdate(id);
    }

    public void updatePassenger(Entity passenger) {
        if (isPassenger(passenger)) {
            float f = -1.75F; //driver x pos
            float d = 0.0F;   //driver z pos
            float f1 = (float) ((this.removed ? 0.02D : getMountedYOffset()) + passenger.getYOffset());
            if (getPassengers().size() == 2) {
                int i = getPassengers().indexOf(passenger);
            if (i == 0) {

                f = -1.75F;
                d = 0.0F;
            } else {
                f = 1.25F;
                d = 0.0F;
                }
            } else if (getPassengers().size() == 3) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -1.75F;
                    d = 0.0F;
                } else if (i == 1) {
                    f = 1.25F;
                    d = 0.9F;
                } else {
                    f = 1.25F;
                    d = -0.90F;
                }
            }else if (getPassengers().size() == 4) {
                int i = getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = -1.75F;
                    d = 0.0F;
                } else if (i == 1) {
                    f =  1.25F;
                    d = -0.90F;
                } else if (i == 2) {
                    f = 1.25F;
                    d = 0.90F;
                } else {
                    f = 0.45F;
                    d = 0F;
                }
            } else if (getPassengers().size() == 5) {
                int i = getPassengers().indexOf(passenger);
                Entity firstpassenger = getPassengers().get(0);
                boolean flag = (firstpassenger instanceof net.minecraft.entity.player.PlayerEntity);
                if (i == 0) { // and flag?
                    f = -1.75F;
                    d = 0.0F;
                } else if (i == 1) {
                    f =  1.25F;
                    d = -0.90F;
                } else if (i == 2) {
                    f = 1.25F;
                    d = 0.90F;
                } else if (i == 3){
                    f =  0.45F;
                    d = 0.90F;
                } else {
                    f =  0.45F;
                    d = -0.90F;
                }
            }
            if (passenger instanceof AnimalEntity)
                d = (float)(d -0.15D);
            Vector3d vector3d = (new Vector3d((double)f, 0.0D, 0.0D + d)).rotateYaw(-this.rotationYaw * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
            passenger.setPosition(this.getPosX() + vector3d.x, this.getPosY() + (double)f1, + this.getPosZ() + vector3d.z);
            passenger.rotationYaw += this.deltaRotation;
            passenger.setRotationYawHead(passenger.getRotationYawHead() + this.deltaRotation);
            applyYawToEntity(passenger);
            if (passenger instanceof AnimalEntity && getPassengers().size() > 1) {
                int j = (passenger.getEntityId() % 2 == 0) ? 90 : 270;
                passenger.setRenderYawOffset(((AnimalEntity)passenger).renderYawOffset + j);
                passenger.setRotationYawHead(passenger.getRotationYawHead() + j);
            }
        }
    }


    public NonNullList<ItemStack> getCargo() {
        NonNullList<ItemStack> cargo = NonNullList.withSize(CARGO.size(), ItemStack.EMPTY);
        for (int i = 0; i < CARGO.size(); i++)
            cargo.set(i, this.dataManager.get((DataParameter<ItemStack>) CARGO.get(i)));
        return cargo;
    }

    protected void registerData() {
        super.registerData();
        for (UnmodifiableIterator<DataParameter<ItemStack>> unmodifiableIterator = CARGO.iterator(); unmodifiableIterator.hasNext(); ) {
            DataParameter<ItemStack> parameter = unmodifiableIterator.next();
            this.dataManager.register(parameter, ItemStack.EMPTY);
        }

    }

    public void openContainer(PlayerEntity player) {
        if (!this.world.isRemote)
            player.openContainer((INamedContainerProvider)new SimpleNamedContainerProvider((id, inv, plyr) -> new CogContainer(id, inv, this),

                    getDisplayName()));
    }

    public Item getItemBoat() {
        switch (this.getBoatType()) {
            case OAK:
            default:
                return ModItems.OAK_COG_ITEM.get();
            case SPRUCE:
                return ModItems.SPRUCE_COG_ITEM.get();
            case BIRCH:
                return ModItems.BIRCH_COG_ITEM.get();
            case JUNGLE:
                return ModItems.JUNGLE_COG_ITEM.get();
            case ACACIA:
                return ModItems.ACACIA_COG_ITEM.get();
            case DARK_OAK:
                return ModItems.DARK_OAK_COG_ITEM.get();
        }
    }

    protected boolean canFitPassenger(Entity passenger) {
        return (getPassengers().size() < 5);
    }




}
