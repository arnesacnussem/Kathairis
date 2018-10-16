package com.Krevik.Blocks;

import com.Krevik.Dimension.KetherDataStorage;
import com.Krevik.Main.KCore;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockLayeredSand extends BaseBlock {

    public static final PropertyInteger LAYERS = PropertyInteger.create("layers", 1, 8);
    protected static final AxisAlignedBB[] SAND_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};

    public BlockLayeredSand(String Name, CreativeTabs tab){
        super(Name,Material.SAND,tab,1f,1f,SoundType.SAND);
        this.setDefaultState(this.blockState.getBaseState().withProperty(LAYERS, Integer.valueOf(1)));
        this.setTickRandomly(true);
    }
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SAND_AABB[((Integer)state.getValue(LAYERS)).intValue()];
    }

    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return ((Integer)worldIn.getBlockState(pos).getValue(LAYERS)).intValue() < 5;
    }

    public boolean isTopSolid(IBlockState state)
    {
        return ((Integer)state.getValue(LAYERS)).intValue() == 8;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        int i = ((Integer)blockState.getValue(LAYERS)).intValue() - 1;
        float f = 0.125F;
        AxisAlignedBB axisalignedbb = blockState.getBoundingBox(worldIn, pos);
        return new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.maxX, (double)((float)i * 0.125F), axisalignedbb.maxZ);
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos.down());
        Block block = iblockstate.getBlock();

        if (block != Blocks.ICE && block != Blocks.PACKED_ICE && block != Blocks.BARRIER)
        {
            BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP);
            return blockfaceshape == BlockFaceShape.SOLID || iblockstate.getBlock().isLeaves(iblockstate, worldIn, pos.down()) || block == this && ((Integer)iblockstate.getValue(LAYERS)).intValue() == 8;
        }
        else
        {
            return false;
        }
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        this.checkAndDropBlock(worldIn, pos, state);
    }

    private boolean checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!this.canPlaceBlockAt(worldIn, pos))
        {
            worldIn.setBlockToAir(pos);
            return false;
        }
        else
        {
            return true;
        }
    }

    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockToAir(pos);
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(KCore.Layered_Sand.getDefaultState().getBlock());
    }

    public int quantityDropped(Random random)
    {
        return 1;
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        /*if (worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > 11)
        {
            worldIn.setBlockToAir(pos);
        }*/
        KetherDataStorage data = KCore.data.getDataInstance(worldIn);
        if(data!=null){
            if(data.getIsSandstorm()){
                if(rand.nextInt(10)==0){
                    int actualLayers=getLayers(state);
                    if(actualLayers<8){
                        worldIn.setBlockState(pos,KCore.Layered_Sand.getDefaultState().withProperty(BlockLayeredSand.LAYERS,actualLayers+1),2);
                    }
                }
            }
        }
        giveSandToNeighbours(state,worldIn,pos);

    }

    //TODO FINISH
    private void giveSandToNeighbours(IBlockState thisState, World world, BlockPos pos){
        int layers = getLayers(thisState);
        if(layers>2){
            IBlockState state1 = KCore.Layered_Sand.getDefaultState().withProperty(BlockLayeredSand.LAYERS,1);
            IBlockState state2 = KCore.Layered_Sand.getDefaultState().withProperty(BlockLayeredSand.LAYERS,2);
            IBlockState state3 = KCore.Layered_Sand.getDefaultState().withProperty(BlockLayeredSand.LAYERS,3);
            IBlockState state4 = KCore.Layered_Sand.getDefaultState().withProperty(BlockLayeredSand.LAYERS,4);
            IBlockState state5 = KCore.Layered_Sand.getDefaultState().withProperty(BlockLayeredSand.LAYERS,5);
            BlockPos pos1 = pos.down().east();
            IBlockState actualState1 = world.getBlockState(pos1);
            if(actualState1==state1||actualState1==state2||actualState1==state3||actualState1==state4||actualState1==state5){
                if(getLayers(thisState)>getLayers(actualState1)) {
                    world.setBlockState(pos1, actualState1.withProperty(LAYERS, getLayers(actualState1) + 1), 2);
                    world.setBlockState(pos, thisState.withProperty(LAYERS, getLayers(thisState) - 1), 2);
                }
            }
            pos1 = pos.down().west();
            actualState1 = world.getBlockState(pos1);
            if(actualState1==state1||actualState1==state2||actualState1==state3||actualState1==state4||actualState1==state5){
                if(getLayers(thisState)>getLayers(actualState1)) {
                    world.setBlockState(pos1, actualState1.withProperty(LAYERS, getLayers(actualState1) + 1), 2);
                    world.setBlockState(pos, thisState.withProperty(LAYERS, getLayers(thisState) - 1), 2);
                }
            }
            pos1 = pos.down().south();
            actualState1 = world.getBlockState(pos1);
            if(actualState1==state1||actualState1==state2||actualState1==state3||actualState1==state4||actualState1==state5){
                if(getLayers(thisState)>getLayers(actualState1)) {
                    world.setBlockState(pos1, actualState1.withProperty(LAYERS, getLayers(actualState1) + 1), 2);
                    world.setBlockState(pos, thisState.withProperty(LAYERS, getLayers(thisState) - 1), 2);
                }
            }
            pos1 = pos.down().north();
            actualState1 = world.getBlockState(pos1);
            if(actualState1==state1||actualState1==state2||actualState1==state3||actualState1==state4||actualState1==state5){
                if(getLayers(thisState)>getLayers(actualState1)) {
                    world.setBlockState(pos1, actualState1.withProperty(LAYERS, getLayers(actualState1) + 1), 2);
                    world.setBlockState(pos, thisState.withProperty(LAYERS, getLayers(thisState) - 1), 2);
                }
            }
            pos1 = pos.south();
            actualState1 = world.getBlockState(pos1);
            if(actualState1==state1||actualState1==state2||actualState1==state3||actualState1==state4||actualState1==state5){
                if(getLayers(thisState)>getLayers(actualState1)) {
                    world.setBlockState(pos1, actualState1.withProperty(LAYERS, getLayers(actualState1) + 1), 2);
                    world.setBlockState(pos, thisState.withProperty(LAYERS, getLayers(thisState) - 1), 2);
                }
            }
            pos1 = pos.north();
            actualState1 = world.getBlockState(pos1);
            if(actualState1==state1||actualState1==state2||actualState1==state3||actualState1==state4||actualState1==state5){
                if(getLayers(thisState)>getLayers(actualState1)) {
                    world.setBlockState(pos1, actualState1.withProperty(LAYERS, getLayers(actualState1) + 1), 2);
                    world.setBlockState(pos, thisState.withProperty(LAYERS, getLayers(thisState) - 1), 2);
                }
            }
            pos1 = pos.east();
            actualState1 = world.getBlockState(pos1);
            if(actualState1==state1||actualState1==state2||actualState1==state3||actualState1==state4||actualState1==state5){
                if(getLayers(thisState)>getLayers(actualState1)) {
                    world.setBlockState(pos1, actualState1.withProperty(LAYERS, getLayers(actualState1) + 1), 2);
                    world.setBlockState(pos, thisState.withProperty(LAYERS, getLayers(thisState) - 1), 2);
                }
            }
            pos1 = pos.west();
            actualState1 = world.getBlockState(pos1);
            if(actualState1==state1||actualState1==state2||actualState1==state3||actualState1==state4||actualState1==state5){
                if(getLayers(thisState)>getLayers(actualState1)) {
                    world.setBlockState(pos1, actualState1.withProperty(LAYERS, getLayers(actualState1) + 1), 2);
                    world.setBlockState(pos, thisState.withProperty(LAYERS, getLayers(thisState) - 1), 2);
                }
            }
        }
    }

    protected int getLayers(IBlockState state)
    {
        return ((Integer)state.getValue(this.getAgeProperty())).intValue();
    }
    protected PropertyInteger getAgeProperty()
    {
        return LAYERS;
    }


    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        if (side == EnumFacing.UP)
        {
            return true;
        }
        else
        {
            IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
            return iblockstate.getBlock() == this && ((Integer)iblockstate.getValue(LAYERS)).intValue() >= ((Integer)blockState.getValue(LAYERS)).intValue() ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
        }
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(LAYERS, Integer.valueOf((meta & 7) + 1));
    }

    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
        return ((Integer)worldIn.getBlockState(pos).getValue(LAYERS)).intValue() == 1;
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((Integer)state.getValue(LAYERS)).intValue() - 1;
    }

    @Override public int quantityDropped(IBlockState state, int fortune, Random random){ return ((Integer)state.getValue(LAYERS)) + 1; }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {LAYERS});
    }
}
