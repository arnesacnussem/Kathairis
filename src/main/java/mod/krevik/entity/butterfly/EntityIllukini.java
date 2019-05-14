package mod.krevik.entity.butterfly;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityIllukini extends EntityBasicButterfly
{

    public EntityIllukini(World worldIn)
    {
        super(worldIn);
        this.setSize(0.6F, 0.5F);
        this.setIsBirdSitting(true);
        this.experienceValue=1;
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        this.setVariant(ButterflyType.ILLUKINI.getMetadata());
        return super.onInitialSpawn(difficulty, livingdata);
    }
}