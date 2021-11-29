package kelvin.mite.mixin.structures;

import kelvin.mite.registry.ItemRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTables;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.structure.*;
import net.minecraft.structure.processor.BlockRotStructureProcessor;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.Random;

@Mixin(DesertTempleGenerator.class)
public abstract class DesertTempleGeneratorMixin extends ShiftableStructurePiece {

    protected DesertTempleGeneratorMixin(StructurePieceType type, int x, int y, int z, int width, int height, int depth, Direction orientation) {
        super(type, x, y, z, width, height, depth, orientation);
    }

    /*
    @Inject(at = @At("HEAD"), method = "generate", cancellable = true)
    public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        if (pos.isWithinDistance(Vec3i.ZERO, 2500)) {
            info.setReturnValue(false);
        } else
        {
            if (random.nextBoolean()) {
                info.setReturnValue(generateNewTemple(world, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, pos));
            }
        }
    }
    */

    @Override
    public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pos) {
        if (!world.isClient()) {
            Identifier structureName = new Identifier("mite:desert_temple");

            ServerWorld server = world.toServerWorld();

            StructureManager structureManager = server.getStructureManager();

            Optional optional2;
            try {
                optional2 = structureManager.getStructure(structureName);
            } catch (InvalidIdentifierException var6) {
                return;
            }

            Structure structure = (Structure)optional2.get();

            BlockPos blockPos = pos;

            Vec3i vec3i = structure.getSize();

            BlockMirror mirror = BlockMirror.NONE;
            BlockRotation rotation = BlockRotation.NONE;

            Vec3i offset = new Vec3i(0, 4, 0);

            StructurePlacementData structurePlacementData = (new StructurePlacementData()).setMirror(mirror).setRotation(rotation).setIgnoreEntities(true);
            /*
            if (this.integrity < 1.0F) {
                structurePlacementData.clearProcessors().addProcessor(new BlockRotStructureProcessor(MathHelper.clamp(this.integrity, 0.0F, 1.0F))).setRandom(createRandom(this.seed));
            }
             */

            BlockPos blockPos2 = blockPos.add(offset);
            structure.place(world, blockPos2, blockPos2, structurePlacementData, new Random(world.getSeed()), 2);

            //corner 1: 0x, 0y, 3z
            for (int h = -1; h >= -11; h--) {
                addBlock(world, Blocks.CUT_SANDSTONE.getDefaultState(), 0, h, 3);
                addBlock(world, Blocks.CUT_SANDSTONE.getDefaultState(), 0, h, 18);
                addBlock(world, Blocks.CUT_SANDSTONE.getDefaultState(), 22, h, 18);
                addBlock(world, Blocks.CUT_SANDSTONE.getDefaultState(), 22, h, 3);
                for (int x = 1; x <= 21; x++) {
                    for (int z = 4; z <= 17; z++) {
                        if (h > -9) {
                            if (x >= 10 && x <= 12 && z >= 6 && z <= 8) {
                                addBlock(world, Blocks.AIR.getDefaultState(), x, h, z);
                                continue;
                            }
                            if (x == 11 && (z == 6 || z == 8)) {
                                addBlock(world, Blocks.AIR.getDefaultState(), x, h, z);
                                continue;
                            }
                            if ((x == 10 || x == 12) && z == 7) {
                                addBlock(world, Blocks.AIR.getDefaultState(), x, h, z);
                                continue;
                            }
                        }
                        addBlock(world, Blocks.CUT_SANDSTONE.getDefaultState(), x, h, z);
                    }
                }
            }

            //trap floor = -9

            for (int x = 10; x <= 12; x++) {
                for (int z = 6; z <= 8; z++) {
                    addBlock(world, Blocks.STONE_PRESSURE_PLATE.getDefaultState(), x, -8, z);
                    addBlock(world, Blocks.TNT.getDefaultState(), x, -10, z);
                }
            }
            //protected boolean addChest(ServerWorldAccess world, BlockBox boundingBox, Random random, BlockPos pos, Identifier lootTableId, @Nullable BlockState block) {
            /*

            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ChestBlockEntity) {
                ((ChestBlockEntity)blockEntity).setLootTable(lootTableId, random.nextLong());
            }
             */
            BlockPos[] chest_pos = {
                    this.getOffsetPos(13, -8, 7),
                    this.getOffsetPos(9, -8, 7),
                    this.getOffsetPos(11, -8, 5),
                    this.getOffsetPos(11, -8, 9)
            };
            addBlock(world, Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.WEST), 13, -8, 7); // CHEST FACING EAST

            addBlock(world, Blocks.AIR.getDefaultState(), 13, -7, 7);
            addBlock(world, Blocks.AIR.getDefaultState(), 13, -6, 7);

            addBlock(world, Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.EAST), 9, -8, 7); // CHEST FACING WEST
            addBlock(world, Blocks.AIR.getDefaultState(), 9, -7, 7);
            addBlock(world, Blocks.AIR.getDefaultState(), 9, -6, 7);

            addBlock(world, Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.NORTH), 11, -8, 5); // CHEST FACING NORTH
            addBlock(world, Blocks.AIR.getDefaultState(), 11, -7, 5);
            addBlock(world, Blocks.AIR.getDefaultState(), 11, -6, 5);

            addBlock(world, Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH), 11, -8, 9); // CHEST FACING SOUTH
            addBlock(world, Blocks.AIR.getDefaultState(), 11, -7, 9);
            addBlock(world, Blocks.AIR.getDefaultState(), 11, -6, 9);

            for (BlockPos bpos : chest_pos) {
                BlockEntity blockEntity = world.getBlockEntity(bpos);
                if (blockEntity instanceof ChestBlockEntity) {
                    ((ChestBlockEntity)blockEntity).setLootTable(LootTables.DESERT_PYRAMID_CHEST, random.nextLong());
                }
            }

            //corner 2: 0x, 0y, 18z
            //corner 3: 22x, 0y, 18z
            //corner 4: 22x, 0y, 3z
            //floor: 1x, 0y, 1z -> 21x, 0y, 17z

            //center chest = 11x, 1y, 7z

            BlockEntity blockEntity = world.getBlockEntity(this.getOffsetPos(11, 1, 7));
            if (blockEntity instanceof TrappedChestBlockEntity) {
                TrappedChestBlockEntity chest = ((TrappedChestBlockEntity)blockEntity);
                chest.setStack(random.nextInt(chest.size()), new ItemStack(ItemRegistry.FLINT_SHARD, random.nextInt(4) + 1));
                chest.setStack(random.nextInt(chest.size()), new ItemStack(ItemRegistry.SALAD));
                chest.setStack(random.nextInt(chest.size()), new ItemStack(Items.PUMPKIN_SEEDS, random.nextInt(3) + 1));
                chest.setStack(random.nextInt(chest.size()), new ItemStack(Items.TORCH, random.nextInt(2) + 1));
                chest.setStack(random.nextInt(chest.size()), new ItemStack(ItemRegistry.COPPER_NUGGET, random.nextInt(3) + 1));
            }

            //redstone dust: 11x, -1y, 7z
            this.addBlock(world, Blocks.REDSTONE_WIRE.getDefaultState(), 11, -1, 7);
            //tnt: 11x, -2y, 7z
            this.addBlock(world, Blocks.TNT.getDefaultState(), 11, -2, 7);

            //north ladder: 11x, -2y, 6z
            this.addBlock(world, Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.NORTH), 11, -2, 6);
            //west ladder: 10x, -2y, 7z
            this.addBlock(world, Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.WEST), 10, -2, 7);

            //south ladder: 11x, -2y, 8z
            this.addBlock(world, Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.SOUTH), 11, -2, 8);

            //east ladder: 12x, -2y, 7z
            this.addBlock(world, Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.EAST), 12, -2, 7);

            //north ladder: 11x, -1y, 5z
            this.addBlock(world, Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.NORTH), 11, -1, 5);

            //north ladder: 10x, -1y, 6z
            this.addBlock(world, Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.NORTH), 10, -1, 6);

            //north ladder: 12x, -1y, 6z
            this.addBlock(world, Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.NORTH), 12, -1, 6);

            //red sand: 11x, -1y, 6z
            this.addBlock(world, Blocks.RED_SAND.getDefaultState(), 11, -1, 6);
            //red sand: 10x, -1y, 7z
            this.addBlock(world, Blocks.RED_SAND.getDefaultState(), 10, -1, 7);
            //red sand: 12x, -1y, 7z
            this.addBlock(world, Blocks.RED_SAND.getDefaultState(), 12, -1, 7);
            //red sand: 11x, -1y, 8z
            this.addBlock(world, Blocks.RED_SAND.getDefaultState(), 11, -1, 8);

            //south ladder: 11x, -1y, 9z
            this.addBlock(world, Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.SOUTH), 11, -1, 9);

            //south ladder: 10x, -1y, 8z
            this.addBlock(world, Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.SOUTH), 10, -1, 8);

            //south ladder: 12x, -1y, 8z
            this.addBlock(world, Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.SOUTH), 12, -1, 8);

            //west ladder: 9x, -1y, 7z
            this.addBlock(world, Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.WEST), 9, -1, 7);

            //east ladder: 13x, -1y, 7z
            this.addBlock(world, Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.EAST), 13, -1, 7);


        }
    }

    public void addBlock(StructureWorldAccess world, BlockState block, int x, int y, int z) {
        BlockPos blockPos = this.offsetPos(x + 10, y + 4, z + 10);
        world.setBlockState(blockPos, block, 2);
    }

    public BlockPos.Mutable getOffsetPos(int x, int y, int z) {
        x += 10;
        y += 4;
        z += 10;
        return new BlockPos.Mutable(this.applyXTransform(x, z), this.applyYTransform(y), this.applyZTransform(x, z));
    }
}