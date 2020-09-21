package kelvin.fiveminsurvival.main.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import kelvin.fiveminsurvival.init.ItemRegistry;
import kelvin.fiveminsurvival.main.crafting.CraftingIngredient;
import kelvin.fiveminsurvival.main.crafting.CraftingIngredients;
import net.java.games.input.Keyboard;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;

@OnlyIn(Dist.CLIENT)
public class MITEInventoryScreen extends DisplayEffectsScreen<PlayerContainer> implements IRecipeShownListener {
   private static final ResourceLocation RECIPE_BUTTON_TEXTURE = new ResourceLocation("textures/gui/recipe_button.png");
   public static final ResourceLocation INVENTORY_EXTRAS = new ResourceLocation("fiveminsurvival:textures/gui/container/inventory.png");

   public static final int[] arrow_position = {134, 27}, arrow_location = {178, 39}, lock_location = {178, 57}, lock_position = {154, 28};
   
   
   private float oldMouseX;
   private float oldMouseY;
   private final RecipeBookGui recipeBookGui = new RecipeBookGui();
   private boolean field_212353_B;
   private boolean widthTooNarrow;
   private boolean buttonClicked;

   public MITEInventoryScreen(PlayerEntity player) {
      super(player.container, player.inventory, new TranslationTextComponent("container.crafting"));
      if (!player.isCreative()) {
	      try {
		      Field container = ObfuscationReflectionHelper.findField(PlayerEntity.class, "field_71069_bz"); //container
		      container.set(player, new MITEPlayerContainer(player.inventory, player.getEntityWorld().isRemote, player));
	      }catch (Exception e) {
	    	  e.printStackTrace();
	      }
      } else {
    	  try {
		      Field container = ObfuscationReflectionHelper.findField(PlayerEntity.class, "field_71069_bz"); //container
		      container.set(player, new PlayerContainer(player.inventory, player.getEntityWorld().isRemote, player));
	      }catch (Exception e) {
	    	  e.printStackTrace();
	      }
      }
      this.passEvents = true;
   }

   public void tick() {
      if (this.minecraft.playerController.isInCreativeMode()) {
         this.minecraft.displayGuiScreen(new CreativeScreen(this.minecraft.player));
      } else {
         this.recipeBookGui.tick();
      }
      this.craftingTick();
   }

   protected void init() {
      if (this.minecraft.playerController.isInCreativeMode()) {
         this.minecraft.displayGuiScreen(new CreativeScreen(this.minecraft.player));
      } else {
         super.init();
         this.widthTooNarrow = this.width < 379;
         this.recipeBookGui.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.container);
         this.field_212353_B = true;
         this.guiLeft = this.recipeBookGui.updateScreenPosition(this.widthTooNarrow, this.width, this.xSize);
         this.children.add(this.recipeBookGui);
         this.setFocusedDefault(this.recipeBookGui);
         this.addButton(new ImageButton(this.guiLeft + 104, this.height / 2 - 22, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, (p_214086_1_) -> {
            this.recipeBookGui.initSearchBar(this.widthTooNarrow);
            this.recipeBookGui.toggleVisibility();
            this.guiLeft = this.recipeBookGui.updateScreenPosition(this.widthTooNarrow, this.width, this.xSize);
            ((ImageButton)p_214086_1_).setPosition(this.guiLeft + 104, this.height / 2 - 22);
            this.buttonClicked = true;
         }));
      }
   }

   /**
    * Draw the foreground layer for the GuiContainer (everything in front of the items)
    */
   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
      this.font.drawString(this.title.getFormattedText(), 97.0F, 8.0F, 4210752);
   }

   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
      this.renderBackground();
      this.hasActivePotionEffects = !this.recipeBookGui.isVisible();
      if (this.recipeBookGui.isVisible() && this.widthTooNarrow) {
         this.drawGuiContainerBackgroundLayer(p_render_3_, p_render_1_, p_render_2_);
         this.recipeBookGui.render(p_render_1_, p_render_2_, p_render_3_);
      } else {
         this.recipeBookGui.render(p_render_1_, p_render_2_, p_render_3_);
         super.render(p_render_1_, p_render_2_, p_render_3_);
         this.recipeBookGui.renderGhostRecipe(this.guiLeft, this.guiTop, false, p_render_3_);
      }

      this.renderHoveredToolTip(p_render_1_, p_render_2_);
      this.recipeBookGui.renderTooltip(this.guiLeft, this.guiTop, p_render_1_, p_render_2_);
      this.oldMouseX = (float)p_render_1_;
      this.oldMouseY = (float)p_render_2_;
      this.func_212932_b(this.recipeBookGui);
      
      this.minecraft.getTextureManager().bindTexture(INVENTORY_EXTRAS);
      //18x17
      if (this.craftTimer > 0 && this.maxCraftTimer > 0 && this.crafting)
      this.blit(this.guiLeft + arrow_position[0], this.guiTop + arrow_position[1], arrow_location[0], arrow_location[1], (int)(18.0 * (this.craftTimer) / (this.maxCraftTimer)), 17);
      GlStateManager.disableDepthTest();
      if (!this.canCraft) {
    	  if (this.crafting) {
              this.blit(this.guiLeft + lock_position[0] + 4, this.guiTop + lock_position[1], lock_location[0] + 4, lock_location[1], 8 - (int)(8.0 * (this.craftTimer) / (this.maxCraftTimer)), 17);
    	  } else {
              this.blit(this.guiLeft + lock_position[0], this.guiTop + lock_position[1], lock_location[0], lock_location[1], 18, 17);
    	  }
          if (this.getSlotUnderMouse() instanceof CraftingResultSlot) {
        	  if (crafting) {
        		  ArrayList<String> str = new ArrayList<>();
            	  str.add(((int)(this.maxCraftTimer) - (int)(this.craftTimer)) / 20 + " seconds left");
            	  str.add("Wait until the item is finished crafting!");
            	  renderTooltip(str, (int)this.oldMouseX, (int)this.oldMouseY, Minecraft.getInstance().fontRenderer);
        	  } else {
        		  ArrayList<String> str = new ArrayList<>();
            	  str.add("LOCKED!");
            	  str.add("Higher tier table required!");
            	  renderTooltip(str, (int)this.oldMouseX, (int)this.oldMouseY, Minecraft.getInstance().fontRenderer);
        	  }
        	  
          }
      }
      GlStateManager.enableDepthTest();
      this.minecraft.getTextureManager().bindTexture(INVENTORY_BACKGROUND);

   }
   
   /**
    * Draws the background layer of this container (behind the items).
    */
   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
      GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.minecraft.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
      int i = this.guiLeft;
      int j = this.guiTop;
      this.blit(i, j, 0, 0, this.xSize, this.ySize);
      drawEntityOnScreen(i + 51, j + 75, 30, (float)(i + 51) - this.oldMouseX, (float)(j + 75 - 50) - this.oldMouseY, this.minecraft.player);
   }

   /**
    * Draws an entity on the screen looking toward the cursor.
    */
   public static void drawEntityOnScreen(int p_228187_0_, int p_228187_1_, int p_228187_2_, float p_228187_3_, float p_228187_4_, LivingEntity p_228187_5_) {
	      float f = (float)Math.atan(p_228187_3_ / 40.0F);
	      float f1 = (float)Math.atan(p_228187_4_ / 40.0F);
	      RenderSystem.pushMatrix();
	      RenderSystem.translatef((float)p_228187_0_, (float)p_228187_1_, 1050.0F);
	      RenderSystem.scalef(1.0F, 1.0F, -1.0F);
	      MatrixStack matrixstack = new MatrixStack();
	      matrixstack.translate(0.0D, 0.0D, 1000.0D);
	      matrixstack.scale((float)p_228187_2_, (float)p_228187_2_, (float)p_228187_2_);
	      Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
	      Quaternion quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F);
	      quaternion.multiply(quaternion1);
	      matrixstack.rotate(quaternion);
	      float f2 = p_228187_5_.renderYawOffset;
	      float f3 = p_228187_5_.rotationYaw;
	      float f4 = p_228187_5_.rotationPitch;
	      float f5 = p_228187_5_.prevRotationYawHead;
	      float f6 = p_228187_5_.rotationYawHead;
	      p_228187_5_.renderYawOffset = 180.0F + f * 20.0F;
	      p_228187_5_.rotationYaw = 180.0F + f * 40.0F;
	      p_228187_5_.rotationPitch = -f1 * 20.0F;
	      p_228187_5_.rotationYawHead = p_228187_5_.rotationYaw;
	      p_228187_5_.prevRotationYawHead = p_228187_5_.rotationYaw;
	      EntityRendererManager entityrenderermanager = Minecraft.getInstance().getRenderManager();
	      quaternion1.conjugate();
	      entityrenderermanager.setCameraOrientation(quaternion1);
	      entityrenderermanager.setRenderShadow(false);
	      IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
	      entityrenderermanager.renderEntityStatic(p_228187_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixstack, irendertypebuffer$impl, 15728880);
	      irendertypebuffer$impl.finish();
	      entityrenderermanager.setRenderShadow(true);
	      p_228187_5_.renderYawOffset = f2;
	      p_228187_5_.rotationYaw = f3;
	      p_228187_5_.rotationPitch = f4;
	      p_228187_5_.prevRotationYawHead = f5;
	      p_228187_5_.rotationYawHead = f6;
	      RenderSystem.popMatrix();
	   }

   protected boolean isPointInRegion(int p_195359_1_, int p_195359_2_, int p_195359_3_, int p_195359_4_, double p_195359_5_, double p_195359_7_) {
      return (!this.widthTooNarrow || !this.recipeBookGui.isVisible()) && super.isPointInRegion(p_195359_1_, p_195359_2_, p_195359_3_, p_195359_4_, p_195359_5_, p_195359_7_);
   }

   public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
	   
      if (this.recipeBookGui.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_)) {
         return true;
      } else {
         return (!this.widthTooNarrow || !this.recipeBookGui.isVisible()) && super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
      }
   }

   public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
      if (this.buttonClicked) {
         this.buttonClicked = false;
         return true;
      } else {
         return super.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
      }
   }

   protected boolean hasClickedOutside(double p_195361_1_, double p_195361_3_, int p_195361_5_, int p_195361_6_, int p_195361_7_) {
      boolean flag = p_195361_1_ < (double)p_195361_5_ || p_195361_3_ < (double)p_195361_6_ || p_195361_1_ >= (double)(p_195361_5_ + this.xSize) || p_195361_3_ >= (double)(p_195361_6_ + this.ySize);
      return this.recipeBookGui.func_195604_a(p_195361_1_, p_195361_3_, this.guiLeft, this.guiTop, this.xSize, this.ySize, p_195361_7_) && flag;
   }

   
   protected Slot craftSlot;
   protected int craftSlotId;
   protected int clickButton;
   protected ClickType clickType = ClickType.SWAP;
   protected double craftTimer = 0;
   protected double maxCraftTimer = 0;
   protected boolean crafting = false;
   protected String craftingMessage;
   protected boolean canCraft = false;
   protected long lastTick = 0;
   protected int getDifficultyForCrafting(Slot slotIn) {
	   int difficulty = 3 * 20;
	   int difficulty2 = 0;
	   for (int i = 1; i < 5; i++) {
		   Slot slot = this.container.inventorySlots.get(i);
		   if (slot.getHasStack()) {
			   ItemStack stack = slot.getStack();
			   Item item = stack.getItem();
			   if (item != null) {
				   boolean hasDifficulty = false;
				   if (CraftingIngredients.ingredients.containsKey(item)) {
					   CraftingIngredient ingredient = CraftingIngredients.ingredients.get(item);
					   difficulty2 += ingredient.craftingTime;
					   hasDifficulty = true;
				   }
				   if (!hasDifficulty)
				   if (item instanceof BlockItem) {
					   Material mat = ((BlockItem)item).getBlock().getDefaultState().getMaterial();
					   if (CraftingIngredients.block_ingredients.containsKey(mat)) {
						   CraftingIngredient ingredient = CraftingIngredients.block_ingredients.get(mat);
						   difficulty2 += ingredient.craftingTime;
					   }
				   }
			   }
		   }
	   }
	   Slot slot = this.container.inventorySlots.get(0);
	   if (slot.getHasStack()) {
		   Item item = slot.getStack().getItem();
		   if (item == ItemRegistry.COPPER_CRAFTING_TABLE.get() ||
				   item == ItemRegistry.SILVER_CRAFTING_TABLE.get() ||
				   item == ItemRegistry.GOLD_CRAFTING_TABLE.get() || 
				   item == ItemRegistry.IRON_CRAFTING_TABLE.get() ||
				   item == ItemRegistry.MITHRIL_CRAFTING_TABLE.get() ||
				   item == ItemRegistry.ANCIENT_METAL_CRAFTING_TABLE.get() ||
				   item == ItemRegistry.ADAMANTIUM_CRAFTING_TABLE.get())
			   difficulty2 --;
		   if (item == ItemRegistry.OBSIDIAN_CRAFTING_TABLE.get()) difficulty2 = 0;
	   }
	   
	   if (difficulty2 > 0) return difficulty2;
	   return difficulty;
   }
   
   protected boolean canCraft() {
	   int difficulty = 0;
	   this.craftSlot = this.container.getSlot(0);
	   if (this.craftSlot != null) {
		   if (this.craftSlot.getHasStack()) {
			   ItemStack stack = this.craftSlot.getStack();
			   Item item = stack.getItem();
			   if (item instanceof BlockItem) {
				   BlockItem bi = (BlockItem)item;
				   Block block = bi.getBlock();
				   if (block == Blocks.OAK_PLANKS ||
						   block == Blocks.ACACIA_PLANKS
						   || block == Blocks.BIRCH_PLANKS
						   || block == Blocks.DARK_OAK_PLANKS
						   || block == Blocks.JUNGLE_PLANKS
						   || block == Blocks.SPRUCE_PLANKS
						   ) {
					   difficulty = 1;
				   }
			   }
				   
		   }
	   }
	   for (int i = 1; i < 5; i++) {
		   Slot slot = this.container.inventorySlots.get(i);
		   if (slot.getHasStack()) {
			   ItemStack stack = slot.getStack();
			   Item item = stack.getItem();
			   if (item != null) {
				   boolean hasDifficulty = false;
				   if (CraftingIngredients.ingredients.containsKey(item)) {
					   CraftingIngredient ingredient = CraftingIngredients.ingredients.get(item);
					   if (ingredient.workbench > difficulty) {
						   difficulty = ingredient.workbench;
						   hasDifficulty = true;
					   }
					   
				   }
				   if (!hasDifficulty)
				   if (item instanceof BlockItem) {
					   Material mat = ((BlockItem)item).getBlock().getDefaultState().getMaterial();
					   if (CraftingIngredients.block_ingredients.containsKey(mat)) {
						   CraftingIngredient ingredient = CraftingIngredients.block_ingredients.get(mat);
						   if (ingredient.workbench > difficulty) {
							   difficulty = ingredient.workbench;
						   }
					   }
				   }
			   }
		   }
	   }
	   return difficulty == 0;
   }
   
   protected void craftingTick() {
	   this.canCraft = this.canCraft();
	   if (Minecraft.getInstance().world.getGameTime() > lastTick) {
		   if (this.crafting) {

			   if (this.craftTimer < maxCraftTimer) {
				   this.craftTimer += 1 + Minecraft.getInstance().player.experienceLevel * 0.01f;
				   this.canCraft = false;
			   } else {
				   this.canCraft = true;
			   }
		   }
		   lastTick = Minecraft.getInstance().world.getGameTime();
	   }
	   
   }
   
   /**
    * Called when the mouse is clicked over a slot or outside the gui.
    */
   protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
	   if (slotId > 0 && slotId < 5) {
		   this.crafting = false;
		   this.craftTimer = 0;
	   }
	   if (slotId == 0) {
		   if (type == ClickType.QUICK_MOVE) return;
		   this.craftSlot = slotIn;
		   if (!canCraft) {
			   return;
		   }
		   if (!crafting) {
			   this.craftSlot = slotIn;
			   this.craftSlotId = slotId;
			   this.clickButton = mouseButton;
			   this.maxCraftTimer = getDifficultyForCrafting(slotIn);
			   this.crafting = true;
		   } else {
			   if (craftTimer >= maxCraftTimer) {
				   super.handleMouseClick(slotIn, slotId, mouseButton, type);
				   this.recipeBookGui.slotClicked(slotIn);
				   this.crafting = false;
				   this.craftTimer = 0;
			   }
		   }
		   
		   //handle clicking the crafting slot
	   } else {
		   super.handleMouseClick(slotIn, slotId, mouseButton, type);
		   this.recipeBookGui.slotClicked(slotIn);
	   }
   }
   
   public boolean keyPressed(int key, int a, int b) {
	   if (key == 81 && hoveredSlot != null && hoveredSlot.getStack() != null) {
		   hoveredSlot.slotNumber = hoveredSlot.getSlotIndex();
		   this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.getSlotIndex(), hasControlDown() ? 1 : 0, ClickType.THROW);
		   return true;
	   }
	   return super.keyPressed(key, a, b);
   }

   public void recipesUpdated() {
      this.recipeBookGui.recipesUpdated();
   }

   public void removed() {
      if (this.field_212353_B) {
         this.recipeBookGui.removed();
      }

      super.removed();
   }

   public RecipeBookGui getRecipeGui() {
      return this.recipeBookGui;
   }
}