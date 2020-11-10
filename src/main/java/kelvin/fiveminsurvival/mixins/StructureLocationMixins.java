package kelvin.fiveminsurvival.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import kelvin.fiveminsurvival.game.world.Seasons;
import kelvin.fiveminsurvival.game.world.WorldStateHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.BastionRemantsStructure;
import net.minecraft.world.gen.feature.structure.BuriedTreasureStructure;
import net.minecraft.world.gen.feature.structure.DesertPyramidStructure;
import net.minecraft.world.gen.feature.structure.IglooStructure;
import net.minecraft.world.gen.feature.structure.JunglePyramidStructure;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.feature.structure.OceanMonumentStructure;
import net.minecraft.world.gen.feature.structure.OceanRuinStructure;
import net.minecraft.world.gen.feature.structure.PillagerOutpostStructure;
import net.minecraft.world.gen.feature.structure.RuinedPortalStructure;
import net.minecraft.world.gen.feature.structure.ShipwreckStructure;
import net.minecraft.world.gen.feature.structure.StrongholdStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.VillageStructure;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

@Mixin(Structure.class)
public class StructureLocationMixins {

//func_236388_a_
	@Inject(method = "func_236388_a_", at = @At("HEAD"), cancellable = true, remap = false)
	public void func_236388_a_(IWorldReader world, StructureManager manager, BlockPos pos, int i, boolean j, long k, StructureSeparationSettings settings, CallbackInfoReturnable<BlockPos> info) {
		boolean cancel = true;		
		
		
		if (world instanceof World) {
			World worldIn = (World)world;
			
			if (ClassEquals(VillageStructure.class) || ClassEquals(PillagerOutpostStructure.class)) { 
				if (WorldStateHolder.get(worldIn).FoundAllVanillaCrops() || Seasons.getTrueMonth(worldIn.getDayTime() / 24000L) > 6 ||
						pos.getX() > 7000 || pos.getZ() > 7000) {
					cancel = false;
				}
			} else {
				if (ClassEquals(OceanMonumentStructure.class) ||
						ClassEquals(ShipwreckStructure.class) ||
						ClassEquals(OceanRuinStructure.class)) {
					if (ClassEquals(ShipwreckStructure.class) || ClassEquals(OceanRuinStructure.class)) {
						if (Math.abs(pos.getX() % 1857) <= 250 && Math.abs(pos.getZ() % 1857) < 250 && (pos.getX() > 2000 || pos.getZ() > 2000)) {
							cancel = false;
						}
					} else
					if (WorldStateHolder.get(worldIn).prismarine_found == true) {
						cancel = false;
					}
				} else if (ClassEquals(StrongholdStructure.class)) {
					if (WorldStateHolder.get(worldIn).ender_eye_crafted == true) {
						cancel = false;
					}
				} else if (ClassEquals(BastionRemantsStructure.class)) {
					if (WorldStateHolder.get(worldIn).netherite_found == true || WorldStateHolder.get(worldIn).blaze_rod_found == true) {
						cancel = false;
					}
				} else if (ClassEquals(RuinedPortalStructure.class)) { 
					if (WorldStateHolder.get(worldIn).obsidian_block) {
						cancel = false;
					}
				} else if (ClassEquals(DesertPyramidStructure.class) ||
						ClassEquals(JunglePyramidStructure.class) ||
						ClassEquals(BuriedTreasureStructure.class) ||
						ClassEquals(MineshaftStructure.class) ||
						ClassEquals(IglooStructure.class)) {
					if (pos.getX() > 1500 || pos.getZ() > 1500) {
						cancel = false;
					}
				} else {
					cancel = false;
				}
			}
			
		}
		if (cancel) {
			info.setReturnValue(null);
			info.cancel();
		}
	}
	
	public boolean ClassEquals(Class<?> c) {
		return c.getCanonicalName().equals(getClass().getCanonicalName());
	}
}
