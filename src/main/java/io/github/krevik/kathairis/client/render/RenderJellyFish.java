package io.github.krevik.kathairis.client.render;

import io.github.krevik.kathairis.client.model.ModelJellyFish;
import io.github.krevik.kathairis.entity.EntityJellyFish;
import io.github.krevik.kathairis.util.TextureLocationsRef;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class RenderJellyFish extends RenderLiving<EntityJellyFish>
{
    public static final Factory FACTORY = new Factory();

    public RenderJellyFish(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelJellyFish(), 0.25F);

    }


    @Override
    protected ResourceLocation getEntityTexture(EntityJellyFish entity)
    {
        return TextureLocationsRef.JellyFishLoc;
    }
    
    public static class Factory implements IRenderFactory<EntityJellyFish> {

        @Override
        public Render<? super EntityJellyFish> createRenderFor(RenderManager manager) {

            return new RenderJellyFish(manager);

        }

    }

    @Override
    protected void applyRotations(EntityJellyFish entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }
    
}
