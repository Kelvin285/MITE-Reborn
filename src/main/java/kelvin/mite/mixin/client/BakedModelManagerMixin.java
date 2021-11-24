package kelvin.mite.mixin.client;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import kelvin.mite.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

@Mixin(BakedModelManager.class)
public class BakedModelManagerMixin {
	@Shadow
	private Map<Identifier, BakedModel> models;
	
	private boolean add_models = false;
	@Inject(at = @At("HEAD"), method = "getModel", cancellable = true)
	public void getModel(ModelIdentifier id, CallbackInfoReturnable<BakedModel> info) {
		if (!add_models) {
			add_models = true;
			for (BlockState state : BlockRegistry.models.keySet()) {
				models.put(BlockModels.getModelId(state), BlockRegistry.models.get(state));
			}
		}
		//return (BakedModel) this.models.getOrDefault(id, this.missingModel);
	}
}
