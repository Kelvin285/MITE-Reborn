package kelvin.mite.mixin.client;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.hit.HitResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.mojang.datafixers.util.Pair;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import kelvin.mite.items.DaggerItem;
import kelvin.mite.items.HatchetItem;
import kelvin.mite.items.KnifeItem;
import kelvin.mite.items.MiteMattockItem;
import kelvin.mite.items.MiteWarhammerItem;
import kelvin.mite.items.ScytheItem;
import kelvin.mite.items.SpearItem;
import kelvin.mite.registry.ItemRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.TridentItem;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
	
	@Shadow
	private static  Logger LOGGER = LogManager.getLogger();
	@Shadow
	private  MinecraftClient client;
	@Shadow
	private  ClientPlayNetworkHandler networkHandler;
	@Shadow
	private BlockPos currentBreakingPos = new BlockPos(-1, -1, -1);
	@Shadow
	private ItemStack selectedStack;
	@Shadow
	private float currentBreakingProgress;
	@Shadow
	private float blockBreakingSoundCooldown;
	@Shadow
	private int blockBreakingCooldown;
	@Shadow
	private boolean breakingBlock;
	@Shadow
	private GameMode gameMode;
	@Shadow
	private GameMode previousGameMode;
	@Shadow
	private  Object2ObjectLinkedOpenHashMap<Pair<BlockPos, Action>, Vec3d> unacknowledgedPlayerActions;
	@Shadow
	private static  int MAX_UNACKNOWLEDGED_PLAYER_ACTIONS = 50;
	@Shadow
	private int lastSelectedSlot;
	
	private static float block_reach = 2.75F;
	private static float entity_reach = 1.5F;

	private float current_reach = block_reach;

	@Shadow
	private void syncSelectedSlot() {

	}


	public float getReachDistance() {
		if (this.gameMode.isCreative()) {
			return 5.0F;
		}
		float reach = current_reach;

		ItemStack selectedStack = null;

		if (client.player != null) {

			selectedStack = client.player.getMainHandStack();
		}
		if (selectedStack != null) {
			Item item = selectedStack.getItem();
			if (item == Items.STICK) reach += 0.5F;
			if (item == ItemRegistry.BRANCH) reach += 1.0F;
			else if (item == Items.BONE)reach += 0.5f;
			else if (item == ItemRegistry.WOODEN_CLUB) reach += 0.5f;
			else if (item == ItemRegistry.WOODEN_CUDGEL)reach += 0.25f;
			else if (item instanceof DaggerItem) reach += 0.5f;
			else if (item instanceof KnifeItem) reach += 0.25f;
			else if (item instanceof HatchetItem) reach += 0.5f;
			else if (item instanceof MiteWarhammerItem) reach += 0.75f;
			else if (item instanceof MiteMattockItem) reach += 0.75f;
			else if (item instanceof ScytheItem) reach += 1.0f;
			else if (item instanceof SpearItem) reach += 1.25f;
			else if (item instanceof TridentItem) reach += 1.25f;
			else if (item instanceof ShearsItem) reach += 0.5f;
			else if (item instanceof ShovelItem) reach += 0.75f;
			else if (item instanceof PickaxeItem) reach += 0.75f;
			else if (item instanceof AxeItem) reach += 0.75f;
			else if (item instanceof SwordItem) reach += 0.75f;
			else if (item instanceof HoeItem) reach += 0.75f;
			
		}
		return reach;
	}
}
