package com.Krevik.Blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.Krevik.Main.KCore;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLuminescentGnarl extends BaseBlock{
	
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public BlockLuminescentGnarl(String Name, Material material, CreativeTabs tab, float hardness1, float resistance,
			SoundType soundType) {
		super(Name, material, tab, hardness1, resistance, soundType);
		this.setLightLevel(4);
        this.setTickRandomly(true);

	}
	
	   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	    {
	        if (!this.canBlockStay(worldIn, pos, state))
	        {
	            this.dropBlock(worldIn, pos, state);
	        }
	    }
	   
	   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
	    {
		   if(worldIn.isBlockFullCube(pos.north())&&worldIn.getBlockState(pos)==
				   KCore.LuminescentGnarl.getDefaultState().withProperty(BlockLuminescentGnarl.FACING, EnumFacing.SOUTH)) {
			   return true;
		   }
		   else if(worldIn.isBlockFullCube(pos.south())&&worldIn.getBlockState(pos)==
				   KCore.LuminescentGnarl.getDefaultState().withProperty(BlockLuminescentGnarl.FACING, EnumFacing.NORTH)) {
			   return true;
		   }
		   else if(worldIn.isBlockFullCube(pos.east())&&worldIn.getBlockState(pos)==
				   KCore.LuminescentGnarl.getDefaultState().withProperty(BlockLuminescentGnarl.FACING, EnumFacing.WEST)) {
			   return true;
		   }
		   else if(worldIn.isBlockFullCube(pos.west())&&worldIn.getBlockState(pos)==
				   KCore.LuminescentGnarl.getDefaultState().withProperty(BlockLuminescentGnarl.FACING, EnumFacing.EAST)) {
			   return true;
		   }else {
			   return false;
		   }
	    }
	   
	   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	    {
	        if (!this.canBlockStay(worldIn, pos, state))
	        {
	            this.dropBlock(worldIn, pos, state);
	        }
	    }

	    private void dropBlock(World worldIn, BlockPos pos, IBlockState state)
	    {
	        worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
	        this.dropBlockAsItem(worldIn, pos, state, 0);
	    }
	
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
    		return FULL_BLOCK_AABB;
    }
    
    
    
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        EnumFacing enumfacing = EnumFacing.fromAngle((double)placer.rotationYaw).getOpposite();
        worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        this.updateTick(worldIn, pos, state, RANDOM);
    }
    

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
    	return null;
    }
	
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
    }
    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

}
