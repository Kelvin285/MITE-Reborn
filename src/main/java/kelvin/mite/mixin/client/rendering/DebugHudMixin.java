package kelvin.mite.mixin.client.rendering;

import com.google.common.base.Strings;
import kelvin.mite.blocks.MiteFarmlandBlock;
import kelvin.mite.blocks.entity.CropBlockEntity;
import kelvin.mite.blocks.entity.FarmlandBlockEntity;
import kelvin.mite.blocks.properties.MiteBlockProperties;
import kelvin.mite.main.Mite;
import kelvin.mite.main.resources.MiteHungerManager;
import kelvin.mite.main.resources.SaveableVec3;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mixin(DebugHud.class)
public class DebugHudMixin extends DrawableHelper {
    @Shadow
    private MinecraftClient client;
    @Shadow
    private TextRenderer textRenderer;
    @Shadow
    private HitResult blockHit;
    @Shadow
    private HitResult fluidHit;
    @Shadow
    private ChunkPos pos;
    @Shadow
    private WorldChunk chunk;

    public void renderLeftText(MatrixStack matrices) {
        List<String> list = new ArrayList<String>();
        if (client.player != null) {

            list.add("XYZ: " + Math.floor(client.player.getX() * 10) / 10.0f + ", " + Math.floor(client.player.getY() * 10) / 10.0f + ", " + Math.floor(client.player.getZ() * 10) / 10.0f);
            list.add("");
        }
        if (blockHit != null && blockHit instanceof BlockHitResult) {
            BlockHitResult bhit = (BlockHitResult)blockHit;
            BlockState state = client.world.getBlockState(bhit.getBlockPos());

            list.add("block: " + client.world.getBlockState(bhit.getBlockPos()).getBlock().toString());


            list.add("");

            BlockEntity block_entity = client.world.getBlockEntity(bhit.getBlockPos());

            BlockEntity down_entity = client.world.getBlockEntity(bhit.getBlockPos().down());

            FarmlandBlockEntity farmland = null;
            if (down_entity instanceof FarmlandBlockEntity && state.getBlock() instanceof CropBlock) {
                farmland = (FarmlandBlockEntity) down_entity;
            } else if (block_entity instanceof FarmlandBlockEntity) {
                farmland = (FarmlandBlockEntity) block_entity;
            }


            CropBlockEntity crop = null;
            if (block_entity instanceof CropBlockEntity)
            {
                crop = (CropBlockEntity)block_entity;

            }

            if (crop != null) {
                list.add("crops");
                list.add("-------------------");
                if (state.getBlock() instanceof CropBlock) {
                    if (state.get(MiteBlockProperties.BLIGHTED) == true) {
                        list.add("withered");
                    }
                }
                if (crop.age == crop.max_age) {
                    list.add("ready to harvest");
                } else {
                    list.add("days until grown: " + crop.GetDaysUntilGrown());
                }
                if (crop.IsHot()) {
                    list.add("growth stunted due to heat.");
                }
                else if (crop.IsCold()) {
                    list.add("growth stunted due to cold.");
                }
                if (client.world.getBaseLightLevel(bhit.getBlockPos(), 0) <= 9) {
                    list.add("too dark!");
                }
                list.add("-------------------");
            }
            if (farmland != null) {
                list.add("soil");
                list.add("-------------------");
                list.add("nitrogen: " + (int)farmland.nitrogen);
                list.add("phosphorus: " + (int)farmland.phosphorus);
                list.add("potassium: " + (int)farmland.potassium);
                list.add("-------------------");
            }
            list.add("");
        }

        if (client.player != null) {
            MiteHungerManager hungerManager = (MiteHungerManager)((PlayerEntity)client.player).getHungerManager();
            list.add("Nutrients");
            list.add("-----------");
            list.add("fruits: " + (int)hungerManager.getSaturation(MiteHungerManager.HungerCategory.FRUITS));
            list.add("vegetables: " + (int)hungerManager.getSaturation(MiteHungerManager.HungerCategory.VEGETABLES));
            list.add("grains: " + (int)hungerManager.getSaturation(MiteHungerManager.HungerCategory.GRAIN));
            list.add("dairy: " + (int)hungerManager.getSaturation(MiteHungerManager.HungerCategory.DAIRY));
            list.add("protein: " + (int)hungerManager.getSaturation(MiteHungerManager.HungerCategory.PROTEIN));
            list.add("");
        }

        list.add("");
        boolean bl = this.client.getServer() != null;
        String var10001 = this.client.options.debugProfilerEnabled ? "visible" : "hidden";
        //list.add("Debug: Pie [shift]: " + var10001 + (bl ? " FPS + TPS" : " FPS") + " [alt]: " + (this.client.options.debugTpsEnabled ? "visible" : "hidden"));
        //list.add("For help: press F3 + Q");

        for(int i = 0; i < list.size(); ++i) {
            String string = (String)list.get(i);
            if (!Strings.isNullOrEmpty(string)) {
                Objects.requireNonNull(this.textRenderer);
                int j = 9;
                int k = this.textRenderer.getWidth(string);
                int m = 2 + j * i;
                fill(matrices, 1, m - 1, 2 + k + 1, m + j - 1, -1873784752);
                this.textRenderer.draw(matrices, string, 2.0F, (float)m, 14737632);
            }
        }

    }

    public void renderRightText(MatrixStack matrices) {

    }
}
