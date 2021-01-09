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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
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
            openContainer(player);
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        if (isBeingRidden())
            return ActionResultType.PASS;
        if (!this.world.isRemote)
            return player.startRiding(this) ? ActionResultType.CONSUME : ActionResultType.PASS;
        return ActionResultType.SUCCESS;
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
            super.handleStatusUpdate(id);
    }

    public void updatePassenger(Entity passenger) {
        if (isPassenger(passenger)) {
            Vector3d forward = getLookVec();
            Vector3d origin = new Vector3d(0.0D, getMountedYOffset(), 0.0625D);
            Vector3d pos = origin.add(forward.scale(-0.68D));
            passenger.setPosition(getPosX() + pos.x, getPosY() + pos.y - 0.1D + passenger.getYOffset(), getPosZ() + pos.z);
            passenger.setRenderYawOffset(this.rotationYaw + 180.0F);
            float f2 = MathHelper.wrapDegrees(passenger.rotationYaw - this.rotationYaw + 180.0F);
            float f1 = MathHelper.clamp(f2, -105.0F, 105.0F);
            passenger.prevRotationYaw += f1 - f2;
            passenger.rotationYaw += f1 - f2;
            passenger.setRotationYawHead(passenger.rotationYaw);
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
}
