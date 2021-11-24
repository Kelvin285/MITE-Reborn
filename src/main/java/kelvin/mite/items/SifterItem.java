package kelvin.mite.items;

import kelvin.mite.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.GravelBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SifterItem extends ToolItem implements Vanishable {
    public SifterItem(Settings settings) {
        super(SurvivalItemTier.WOOD_SWORD, settings);
    }
    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return SurvivalItemTier.WOOD_SWORD.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient() && stack.getCooldown() <= 0 && user.isTouchingWaterOrRain()) {
            ServerPlayerEntity player = (ServerPlayerEntity)user;
            PlayerInventory inventory = player.getInventory();
            for (var slot : inventory.main) {
                if (slot != null) {
                    if (slot.getItem() instanceof BlockItem) {
                        BlockItem item = (BlockItem)slot.getItem();
                        Block block = item.getBlock();
                        if (block instanceof GravelBlock) {
                            slot.decrement(1);
                            DropGravelItem(world, player.getPos());
                            break;
                        }
                    }
                }
            }

            if (stack.damage(1, world.getRandom(), player)) {
                world.playSound(player, player.getBlockPos(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);
                player.sendToolBreakStatus(hand);
                stack.decrement(1);
            }
            stack.setCooldown(20);
            return TypedActionResult.success(stack);
        }
        return TypedActionResult.fail(stack);
    }
    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {

        return stack;
    }

    private void DropGravelItem(World world, Vec3d pos) {
        // flint chip = 1/20
        // flint = 1/40
        // copper = 1/100
        // silver = 1/100
        // iron = 1/300
        // gold = 1/500
        // obsidian = 1/1000
        // mithril = 1/1000
        // adamantium = 1/10000
        Item drop = Items.AIR;

        Item adamantium = Items.AIR;
        Item obsidian = Items.AIR;
        Item mithril = Items.AIR;

        if (world.random.nextInt(10000) == 0) {
            // adamantium nugget
            drop = adamantium;
        }
        else if (world.random.nextInt(1000) == 0) {
            if (Math.random() <= 0.5f) {
                // obsidian shard
                drop = obsidian;
            } else {
                // mithril nugget
                drop = mithril;
            }
        }
        else if (world.random.nextInt(500) == 0) {
            drop = Items.GOLD_NUGGET;
        }
        else if (world.random.nextInt(350) == 0) {
            drop = Items.IRON_NUGGET;
        }
        else if (world.random.nextInt(100) == 0) {
            if (Math.random() <= 0.5f) {
                drop = ItemRegistry.SILVER_NUGGET;
            } else {
                drop = ItemRegistry.COPPER_NUGGET;
            }
        }
        else if (world.random.nextInt(40) == 0) {
            drop = Items.FLINT;
        }
        else if (world.random.nextInt(15) == 0) {
            drop = ItemRegistry.FLINT_SHARD;
        }

        if (drop != Items.AIR) {
            world.spawnEntity(new ItemEntity(world, pos.x, pos.y, pos.z, new ItemStack(drop)));
        }
    }

}
