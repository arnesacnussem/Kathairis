package io.github.krevik.kathairis.block;

import io.github.krevik.kathairis.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.*;
import net.minecraft.world.biome.SunflowerPlainsBiome;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockForestCandle extends BlockKathairisPlant {
    public static final EnumProperty<BlockForestCandle.EnumType> VARIANT = EnumProperty.create("variant", BlockForestCandle.EnumType.class);
    public BlockForestCandle() {
        super(Block.Properties.create(Material.PLANTS).sound(SoundType.PLANT).hardnessAndResistance(0).tickRandomly().doesNotBlockMovement().lightValue(7));
        this.setDefaultState(this.stateContainer.getBaseState().with(VARIANT, BlockForestCandle.EnumType.BOTTOM));
    }
    @Override
    public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        if(state.get(VARIANT)==BlockForestCandle.EnumType.BOTTOM){
            return VoxelShapes.create(0,0,0,1,2,1);
        }else{
            return VoxelShapes.create(0,-1,0,1,1,1);
        }
    }

    @Override
    public boolean isValidPosition(IBlockState state, IWorldReaderBase worldIn, BlockPos pos) {
        if(state== ModBlocks.FOREST_CANDLE.getDefaultState().with(VARIANT, BlockForestCandle.EnumType.UPPER)){
            return worldIn.getBlockState(pos.down())==ModBlocks.FOREST_CANDLE.getDefaultState();
        }else{
            boolean b3 = this.isValidGround(worldIn.getBlockState(pos.down()),worldIn,pos.down());
            return b3&&(worldIn.isAirBlock(pos.up())||worldIn.getBlockState(pos.up())== ModBlocks.FOREST_CANDLE.getDefaultState().with(VARIANT, BlockForestCandle.EnumType.UPPER));
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block p_189540_4_, BlockPos p_189540_5_) {
        if (!isValidPosition(state, world, pos)) {
            if(world.getBlockState(pos.down()).getBlock()==this){
                world.destroyBlock(pos.down(),false);
                world.destroyBlock(pos,false);
            }
            if(world.getBlockState(pos.up()).getBlock()==this){
                world.removeBlock(pos.up());
                world.destroyBlock(pos,false);
            }
        }else{
            if(state==ModBlocks.FOREST_CANDLE.getDefaultState()){
                if(world.getBlockState(pos.up())!=ModBlocks.FOREST_CANDLE.getDefaultState().with(VARIANT, BlockForestCandle.EnumType.UPPER)&&world.getBlockState(pos.up()).getBlock()!=Blocks.AIR){
                    world.removeBlock(pos);
                }
            }else{
                if(world.getBlockState(pos.down())!=ModBlocks.FOREST_CANDLE.getDefaultState()){
                    world.removeBlock(pos);
                }
            }

        }
    }



    @Override
    public void onNeighborChange(IBlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {

    }

    @Override
    public IBlockState updatePostPlacement(IBlockState stateIn, EnumFacing facing, IBlockState facingState, IWorld wo, BlockPos pos, BlockPos facingPos) {
        if(stateIn==ModBlocks.FOREST_CANDLE.getDefaultState()) {
            if (wo.isAirBlock(pos.up())) {
                wo.setBlockState(pos.up(), ModBlocks.FOREST_CANDLE.getDefaultState().with(VARIANT, BlockForestCandle.EnumType.UPPER), 2);
            }
        }
        return super.updatePostPlacement(stateIn, facing, facingState, wo, pos, facingPos);
    }

    @Override
    public void onBlockPlacedBy(World wo, BlockPos pos, IBlockState state, @Nullable EntityLivingBase p_180633_4_, ItemStack p_180633_5_) {
        if(state==ModBlocks.FOREST_CANDLE.getDefaultState()) {
            if (wo.isAirBlock(pos.up())) {
                wo.setBlockState(pos.up(), ModBlocks.FOREST_CANDLE.getDefaultState().with(VARIANT, BlockForestCandle.EnumType.UPPER));
            }
        }
        super.onBlockPlacedBy(wo, pos, state, p_180633_4_, p_180633_5_);
    }

    @Override
    public void onBlockAdded(IBlockState p_196259_1_, World wo, BlockPos pos, IBlockState p_196259_4_) {
        if(p_196259_1_==ModBlocks.FOREST_CANDLE.getDefaultState()) {
            if (wo.isAirBlock(pos.up())) {
                wo.setBlockState(pos.up(), ModBlocks.FOREST_CANDLE.getDefaultState().with(VARIANT, BlockForestCandle.EnumType.UPPER));
            }
        }
        super.onBlockAdded(p_196259_1_, wo, pos, p_196259_4_);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        if(world.getBlockState(pos.down()).getBlock()==this){
            world.destroyBlock(pos.down(),true);
        }
        if(world.getBlockState(pos.up()).getBlock()==this){
            world.destroyBlock(pos.up(),false);
        }
        super.onBlockHarvested(world,pos,state,player);
    }

    @Override
    public int getItemsToDropCount(IBlockState state, int p_196251_2_, World p_196251_3_, BlockPos p_196251_4_, Random p_196251_5_) {
        if(state==ModBlocks.FOREST_CANDLE.getDefaultState()){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> p_206840_1_) {
        super.fillStateContainer(p_206840_1_);
        p_206840_1_.add(VARIANT);
    }
    public enum EnumType implements IStringSerializable
    {
        BOTTOM(0,"bottom"),
        UPPER(1,"upper");

        private static final BlockForestCandle.EnumType[] META_LOOKUP = new BlockForestCandle.EnumType[values().length];
        private final int meta;
        private final String name;

        EnumType(int p_i46384_3_, String Name)
        {
            this.meta = p_i46384_3_;
            name=Name;
        }

        public int getMetadata()
        {
            return this.meta;
        }

        public static BlockForestCandle.EnumType byMetadata(int meta)
        {
            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }

        static
        {
            for (BlockForestCandle.EnumType blockstone$enumtype : values())
            {
                META_LOOKUP[blockstone$enumtype.getMetadata()] = blockstone$enumtype;
            }
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
