package io.github.krevik.kathairis.entity.ai;

import com.google.common.base.Predicate;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

public class EntityAITargetSpecified<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T>
{
    private final EntityCreature tameable;

    public EntityAITargetSpecified(EntityCreature entityIn, Class<T> classTarget, boolean checkSight, Predicate<? super T > targetSelector)
    {
        super(entityIn, classTarget, 10, checkSight, false, targetSelector);
        this.tameable = entityIn;
    }

    @Override
    public boolean shouldExecute()
    {
        return super.shouldExecute();
    }
}