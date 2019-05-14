package mod.krevik.world.gen;

import mod.krevik.KCore;
import mod.krevik.block.plants.BlockMysticBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenMysticMultiGrass extends WorldGenerator
{
    private BlockMysticBush grassToGen;
    Random random = new Random();
    public WorldGenMysticMultiGrass(BlockMysticBush blockToGen)
    {
        grassToGen=blockToGen;
    }
    
    public WorldGenMysticMultiGrass()
    {
        grassToGen=KCore.MysticMultiGrass;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
    	BlockPos tmp=position;
    	int Y=position.getY();
    	int X=position.getX();
    	int Z=position.getZ();
    	int height=random.nextInt(10)+1;
	   	if(worldIn.getBlockState(new BlockPos(X,Y-1,Z))==KCore.KatharianGrass.getDefaultState()&&worldIn.isAirBlock(new BlockPos(X,Y,Z))){
	    	for(int c=0;c<height;c++) {
	    		BlockPos target=new BlockPos(X,Y+c,Z);
	    		setBlockAndNotifyAdequately(worldIn,target, grassToGen.getDefaultState());
	    	}
	   	}
    	return true;
    }
    protected void setBlockAndNotifyAdequately(World worldIn, BlockPos pos, IBlockState state)
    {
            worldIn.setBlockState(pos, state, 2);
    }

}