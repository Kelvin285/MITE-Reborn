package kelvin.fiveminsurvival.main.gui;


import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;

import kelvin.fiveminsurvival.init.ItemRegistry;
import kelvin.fiveminsurvival.main.crafting.CraftingIngredient;
import kelvin.fiveminsurvival.main.crafting.CraftingIngredients;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MITECraftingScreen extends ContainerScreen<WorkbenchContainer> implements IRecipeShownListener {
   private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/crafting_table.png");
   private static final ResourceLocation RECIPE_BUTTON_TEXTURE = new ResourceLocation("textures/gui/recipe_button.png");
   public static final ResourceLocation INVENTORY_EXTRAS = new ResourceLocation("fiveminsurvival:textures/gui/container/crafting_table.png");
   
   private final RecipeBookGui recipeBookGui = new RecipeBookGui();
   private boolean widthTooNarrow;

   public int toolbench = CraftingIngredient.FLINT_CRAFTING_TABLE;
   
   //90, 35 (arrow position)
   //178, 5 (arrow location)
   //22, 16 (arrow size)
   
   //178, 25 (lock location)
   //120, 31 (lock position)
   //24, 24 (lock size)
   
   int[] arrow_position = {90, 35}, arrow_location = {178, 5}, arrow_size = {22, 16};
   int[] lock_position = {120, 31}, lock_location = {178, 25}, lock_size = {24, 24};
   
   public MITECraftingScreen(PlayerEntity player, int toolbench) {
      super((WorkbenchContainer) player.openContainer, player.inventory, new TranslationTextComponent("container.crafting"));
      this.toolbench = toolbench;
   }
   
   public MITECraftingScreen(WorkbenchContainer container, PlayerEntity player, int toolbench) {
	      super(container, player.inventory, new TranslationTextComponent("container.crafting"));
	      this.toolbench = toolbench;
	   }
   
   

   protected void init() {
      super.init();
      this.widthTooNarrow = this.width < 379;
      this.recipeBookGui.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.container);
      this.guiLeft = this.recipeBookGui.updateScreenPosition(this.widthTooNarrow, this.width, this.xSize);
      this.children.add(this.recipeBookGui);
      this.setFocusedDefault(this.recipeBookGui);
      this.addButton(new ImageButton(this.guiLeft + 5, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, (p_214076_1_) -> {
         this.recipeBookGui.initSearchBar(this.widthTooNarrow);
         this.recipeBookGui.toggleVisibility();
         this.guiLeft = this.recipeBookGui.updateScreenPosition(this.widthTooNarrow, this.width, this.xSize);
         ((ImageButton)p_214076_1_).setPosition(this.guiLeft + 5, this.height / 2 - 49);
      }));
   }

   public void tick() {
      super.tick();
      this.recipeBookGui.tick();
      this.craftingTick();
   }

   public void render(MatrixStack stack, int p_render_1_, int p_render_2_, float p_render_3_) {
      this.renderBackground(stack);
      if (this.recipeBookGui.isVisible() && this.widthTooNarrow) {
         this.drawGuiContainerBackgroundLayer(stack, p_render_3_, p_render_1_, p_render_2_);
         this.recipeBookGui.render(stack, p_render_1_, p_render_2_, p_render_3_);
      } else {
         this.recipeBookGui.render(stack, p_render_1_, p_render_2_, p_render_3_);
         super.render(stack, p_render_1_, p_render_2_, p_render_3_);
         this.recipeBookGui.func_230477_a_(stack, this.guiLeft, this.guiTop, true, p_render_3_); //renderGhostRecipe
      }

      this.renderHoveredTooltip(stack, p_render_1_, p_render_2_);
      this.recipeBookGui.func_238924_c_(stack, this.guiLeft, this.guiTop, p_render_1_, p_render_2_); //renderTooltip
      this.setListenerDefault(this.recipeBookGui);
      
      this.OldMouseX = (float)p_render_1_;
      this.OldMouseY = (float)p_render_2_;
      
      if (this.getSlotUnderMouse() instanceof CraftingResultSlot) {
    	  
    	  if (!this.canCraft) {
	    	  if (this.crafting) {
	    		  List<ITextProperties> str = new ArrayList<>();
	    		  str.add(new StringTextComponent(((int)(this.maxCraftTimer) - (int)(this.craftTimer)) / 20 + " seconds left"));
            	  str.add(new StringTextComponent("Wait until the item is finished crafting!"));
	        	  renderToolTip(stack, str, (int)this.OldMouseX, (int)this.OldMouseY, Minecraft.getInstance().fontRenderer);
	    	  } else {
	    		  List<ITextProperties> str = new ArrayList<>();
	        	  str.add( new StringTextComponent("LOCKED!"));
	        	  str.add(new StringTextComponent("Higher tier table required!"));
	        	  
	        	  renderToolTip(stack, str, (int)this.OldMouseX, (int)this.OldMouseY, Minecraft.getInstance().fontRenderer);
	
	    	  }
    	  }
      }
   }

   /**
    * Draw the foreground layer for the GuiContainer (everything in front of the items)
    */
   protected void drawGuiContainerForegroundLayer(MatrixStack stack, int mouseX, int mouseY) {
      this.font.drawString(stack, this.title.getString(), 28.0F, 6.0F, 4210752);
      this.font.drawString(stack, this.playerInventory.getDisplayName().getString(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);
      
      this.minecraft.getTextureManager().bindTexture(INVENTORY_EXTRAS);
      //18x17
      if (this.craftTimer > 0 && this.maxCraftTimer > 0 && this.crafting)
      this.blit(stack, this.arrow_position[0], this.arrow_position[1], this.arrow_location[0], this.arrow_location[1], (int)((double)this.arrow_size[0] * (this.craftTimer) / (this.maxCraftTimer)), this.arrow_size[1]);
      GlStateManager.disableDepthTest();
      int i = 8;
      if (!this.canCraft) {
          if (this.crafting) {
              this.blit(stack, this.lock_position[0] + 8, this.lock_position[1], this.lock_location[0] + 8, this.lock_location[1], 8 - (int)((double)8 * (this.craftTimer) / (this.maxCraftTimer)), 24);
          } else {
              this.blit(stack, this.lock_position[0], this.lock_position[1], this.lock_location[0], this.lock_location[1], 24, 24);
          }
          
      }
      GlStateManager.enableDepthTest();
      this.minecraft.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
   }

   /**
    * Draws the background layer of this container (behind the items).
    */
   protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
      GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.minecraft.getTextureManager().bindTexture(CRAFTING_TABLE_GUI_TEXTURES);
      int i = this.guiLeft;
      int j = (this.height - this.ySize) / 2;
      this.blit(stack, i, j, 0, 0, this.xSize, this.ySize);
   }

   protected boolean isPointInRegion(int p_195359_1_, int p_195359_2_, int p_195359_3_, int p_195359_4_, double p_195359_5_, double p_195359_7_) {
      return (!this.widthTooNarrow || !this.recipeBookGui.isVisible()) && super.isPointInRegion(p_195359_1_, p_195359_2_, p_195359_3_, p_195359_4_, p_195359_5_, p_195359_7_);
   }

   double mouseX, mouseY;
   double OldMouseX, OldMouseY;
   
   public void mouseMoved(double x, double y) {
	     this.mouseX = x;
	     this.mouseY = y;
	   }
   
   public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
      if (this.recipeBookGui.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_)) {
         return true;
      } else {
         return this.widthTooNarrow && this.recipeBookGui.isVisible() || super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
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
   public static int getDifficultyForCrafting(Slot slotIn, List<Slot> inventorySlots) {
	   int difficulty = 3 * 20;
	   int difficulty2 = 0;
	   for (int i = 1; i < 10; i++) {
		   Slot slot = inventorySlots.get(i);
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
	   Slot slot = inventorySlots.get(0);
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
	   
	   if (difficulty2 > 100) {
		   difficulty2 = (int) (Math.pow((double)difficulty2 - 100, 0.8) + 100);
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
	   for (int i = 1; i < 10; i++) {
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
	   return difficulty <= this.toolbench;
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
	   if (slotId > 0 && slotId < 10) {
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
			   this.maxCraftTimer = getDifficultyForCrafting(slotIn, this.container.inventorySlots);
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

   public void recipesUpdated() {
      this.recipeBookGui.recipesUpdated();
   }

   public void onClose() {
      this.recipeBookGui.removed();
      super.onClose();
   }

	@Override
	public RecipeBookGui getRecipeGui() {
		return this.recipeBookGui;
	}
}