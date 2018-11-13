package com.Krevik.Dimension;


import java.util.ArrayList;
import java.util.Random;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;


import com.Krevik.Main.FunctionHelper;
import com.Krevik.Main.KCore;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL21;

public class RenderMysticSky extends IRenderHandler {


    private static final ResourceLocation MOON_PHASES_TEXTURES = new ResourceLocation("mystic:textures/environment/moon_phases.png");
    private static final ResourceLocation SUN_TEXTURES = new ResourceLocation("mystic:textures/environment/sun.png");
    private static final ResourceLocation STAR_TEXTURES = new ResourceLocation("mystic:textures/environment/star.png");
    private static final ResourceLocation NEBULA_TEXTURES = new ResourceLocation("mystic:textures/environment/nebula.png");


    public RenderMysticSky()
    {
    	
    }
    
    private int[] constantLight = new int[3000];
    
    private ArrayList<FallingStar> fallingStarsList = new ArrayList();
    private Nebula nebula = null;

    FunctionHelper helper = KCore.instance.functionHelper;
    @Override
    @SideOnly(Side.CLIENT)
    public void render(float partialTicks, WorldClient world, Minecraft mc) {


        
        GlStateManager.disableTexture2D();
        Vec3d vec3d = world.getSkyColor(mc.getRenderViewEntity(), partialTicks);
        float f = (float)vec3d.x;
        float f1 = (float)vec3d.y;
        float f2 = (float)vec3d.z;


        
        GlStateManager.color(f, f1, f2);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.depthMask(false);
        GlStateManager.enableFog();

        GlStateManager.disableFog();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderHelper.disableStandardItemLighting();
        
        //stars
        if(world.getWorldTime()>13000&&world.getWorldTime()<=23000) {
            for (int x = 0; x < 3000; x++) {
                if (constantLight[x] >= 255) {
                    constantLight[x] -= helper.getRandomInteger(0, 8);
                } else if (constantLight[x] <= 0) {
                    constantLight[x] = helper.getRandomInteger(0, 256);
                } else {
                    constantLight[x] += (helper.getRandomInteger(0, 8) - helper.getRandomInteger(0, 8));
                }
            }
            GlStateManager.pushMatrix();
            Random random = new Random(10842L);
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            for (int i = 0; i < 3000; ++i) {
                double d0 = (double) (random.nextFloat() * 2.0F - 1.0F);
                double d1 = (double) (random.nextFloat() * 2.0F - 1.0F);
                double d2 = (double) (random.nextFloat() * 2.0F - 1.0F);
                double d33 = (double) (0.15F + random.nextFloat() * 0.1F);
                double d4 = d0 * d0 + d1 * d1 + d2 * d2;

                if (d4 < 1.0D && d4 > 0.01D) {
                    d4 = 1.0D / Math.sqrt(d4);
                    d0 = d0 * d4;
                    d1 = d1 * d4;
                    d2 = d2 * d4;
                    double d5 = d0 * 100.0D;
                    double d6 = d1 * 100.0D;
                    double d7 = d2 * 100.0D;
                    double d8 = Math.atan2(d0, d2);
                    double d9 = Math.sin(d8);
                    double d10 = Math.cos(d8);
                    double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
                    double d12 = Math.sin(d11);
                    double d13 = Math.cos(d11);
                    double d14 = random.nextDouble() * Math.PI * 2.0D;
                    double d15 = Math.sin(d14);
                    double d16 = Math.cos(d14);

                    for (int j = 0; j < 4; ++j) {
                        Vector4d color = new Vector4d(helper.getRandomInteger(10842L, 66, 137), helper.getRandomInteger(10842L, 65, 244), helper.getRandomInteger(10842L, 229, 244), constantLight[i]);
                        double d18 = (double) ((j & 2) - 1) * d33;
                        double d19 = (double) ((j + 1 & 2) - 1) * d33;
                        double d21 = d18 * d16 - d19 * d15;
                        double d22 = d19 * d16 + d18 * d15;
                        double d23 = d21 * d12 + 0.0D * d13;
                        double d24 = 0.0D * d12 - d21 * d13;
                        double d25 = d24 * d9 - d22 * d10;
                        double d26 = d22 * d9 + d24 * d10;
                        bufferbuilder.pos(d5 + d25, d6 + d23, d7 + d26).color((int) color.x, (int) color.y, (int) color.z, (int) color.w).endVertex();

                    }
                }
            }

            tessellator.draw();
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            if (helper.random.nextInt(100) == 0) {
                random = new Random();
                //generate falling star
                double d0 = (double) (random.nextFloat() * 6.0F - 2F);
                double d1 = (double) (random.nextFloat() * 6.0F - 2F);
                double d2 = (double) (random.nextFloat() * 6.0F - 2F);
                double d33 = (double) (0.15F + random.nextFloat() * 0.1F);
                double d4 = d0 * d0 + d1 * d1 + d2 * d2;

                d4 = 1.0D / Math.sqrt(d4);
                d0 = d0 * d4;
                d1 = d1 * d4;
                d2 = d2 * d4;
                double d5 = d0 * 60.0D;
                double d6 = d1 * 60.0D;
                double d7 = d2 * 60.0D;
                double d8 = Math.atan2(d0, d2);
                double d9 = Math.sin(d8);
                double d10 = Math.cos(d8);
                double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
                double d12 = Math.sin(d11);
                double d13 = Math.cos(d11);
                double d14 = random.nextDouble() * Math.PI * 2.0D;
                double d15 = Math.sin(d14);
                double d16 = Math.cos(d14);

                //for (int j = 0; j < 4; ++j)
                //{
                double d18 = (double) ((0 & 2) - 1) * d33;
                double d19 = (double) ((1 + 1 & 2) - 1) * d33;
                double d21 = d18 * d16 - d19 * d15;
                double d22 = d19 * d16 + d18 * d15;
                double d23 = d21 * d12 + 0.0D * d13;
                double d24 = 0.0D * d12 - d21 * d13;
                double d25 = d24 * d9 - d22 * d10;
                double d26 = d22 * d9 + d24 * d10;
                FallingStar star = new FallingStar(this.fallingStarsList.size(), d5 + d25, d6 + d23, d7 + d26, -0.5F + random.nextFloat(), -0.5F + random.nextFloat(), -0.5F + random.nextFloat(), new Random().nextLong());
                this.fallingStarsList.add(star);
                //bufferbuilder.pos(d5 + d25, d6 + d23, d7 + d26).color(244, 238, 66, 200).endVertex();
                // }

            }
            //operate existing falling stars
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            for (int x = 0; x < this.fallingStarsList.size(); x++) {
                if (this.fallingStarsList.get(x) != null) {
                    FallingStar star = this.fallingStarsList.get(x);
                    star.update();
                    Long starSeed = star.getSeed();

                    random = new Random(starSeed);
                    double d0 = (double) (random.nextFloat() * 2.0F - 1.0F);
                    double d1 = (double) (random.nextFloat() * 2.0F - 1.0F);
                    double d2 = (double) (random.nextFloat() * 2.0F - 1.0F);
                    double d33 = (double) (0.15F + random.nextFloat() * 0.1F);
                    double d4 = d0 * d0 + d1 * d1 + d2 * d2;
                    d4 = 1.0D;
                    d0 = d0 * d4;
                    d1 = d1 * d4;
                    d2 = d2 * d4;
                    double d5 = d0 * 100.0D;
                    double d6 = d1 * 100.0D;
                    double d7 = d2 * 100.0D;
                    double d8 = Math.atan2(d0, d2);
                    double d9 = Math.sin(d8);
                    double d10 = Math.cos(d8);
                    double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
                    double d12 = Math.sin(d11);
                    double d13 = Math.cos(d11);
                    double d14 = random.nextDouble() * Math.PI * 2.0D;
                    double d15 = Math.sin(d14);
                    double d16 = Math.cos(d14);


                    int trailSteps = 200;
                    for (int cc = 1; cc <= trailSteps; cc++) {
                        for (int j = 0; j < 4; ++j) {
                            //
                            double d18 = (double) ((j & 2) - 1) * d33;
                            double d19 = (double) ((j + 1 & 2) - 1) * d33;
                            double d21 = d18 * d16 - d19 * d15;
                            double d22 = d19 * d16 + d18 * d15;
                            double d23 = d21 * d12 + 0.0D * d13;
                            double d24 = 0.0D * d12 - d21 * d13;
                            double d25 = d24 * d9 - d22 * d10;
                            double d26 = d22 * d9 + d24 * d10;
                            bufferbuilder.pos(star.getPos().x + d5 + d25 - (star.getMotion().x * cc * 0.07), star.getPos().y + d6 + d23 - (star.getMotion().y * cc * 0.07), star.getPos().z + d7 + d26 - (star.getMotion().z * cc * 0.07)).color(168, 244, 244, 200 - cc).endVertex();
                        }
                    }
			                	/*Vec3d vec1 = new Vec3d(star.getPos().x-0.5,star.getPos().y,star.getPos().z);
			                	Vec3d vec2 = new Vec3d(star.getPos().x,star.getPos().y+0.5,star.getPos().z);
			                	Vec3d vec3 = new Vec3d(star.getPos().x+0.5,star.getPos().y,star.getPos().z);
			                	Vec3d vec4 = new Vec3d(star.getPos().x,star.getPos().y-0.5,star.getPos().z);
	
			        		bufferbuilder.pos(vec1.x, vec1.y, vec1.z).color(244, 238, 66, 200).endVertex();
			        		bufferbuilder.pos(vec2.x, vec2.y, vec2.z).color(244, 238, 66, 200).endVertex();
			        		bufferbuilder.pos(vec3.x, vec3.y, vec3.z).color(244, 238, 66, 200).endVertex();
			        		bufferbuilder.pos(vec4.x, vec4.y, vec4.z).color(244, 238, 66, 200).endVertex();*/
		        		
	                	/*vec1 = new Vec3d(star.getPos().x-1,star.getPos().y,star.getPos().z);
	                	vec2 = new Vec3d(star.getPos().x,star.getPos().y+1,star.getPos().z);
	                	vec3 = new Vec3d(star.getPos().x+1,star.getPos().y,star.getPos().z);
	                	vec4 = new Vec3d(star.getPos().x,star.getPos().y-1,star.getPos().z);

	                	bufferbuilder.pos(vec1.x, vec1.y, vec1.z).color(244, 238, 66, 100).endVertex();
	                	bufferbuilder.pos(vec2.x, vec2.y, vec2.z).color(244, 238, 66, 100).endVertex();
	                	bufferbuilder.pos(vec3.x, vec3.y, vec3.z).color(244, 238, 66, 100).endVertex();
	                	bufferbuilder.pos(vec4.x, vec4.y, vec4.z).color(244, 238, 66, 100).endVertex();*/


                    if (helper.random.nextInt(1500) == 0 || fallingStarsList.size() > 30) {
                        this.fallingStarsList.remove(x);
                    }
                }

            }

            tessellator.draw();

            GlStateManager.popMatrix();

        }
        
        //stars end


        
        float f17 = 20.0F;
        
        float[] afloat = world.provider.calcSunriseSunsetColors(world.getCelestialAngle(partialTicks), partialTicks);

        if (afloat != null)
        {
            GlStateManager.disableTexture2D();
            GlStateManager.shadeModel(7425);
            GlStateManager.pushMatrix();
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            float f6 = afloat[0];
            float f7 = afloat[1];
            float f8 = afloat[2];

            //pass !=2
                float f9 = (f6 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
                float f10 = (f6 * 30.0F + f7 * 70.0F) / 100.0F;
                float f11 = (f6 * 30.0F + f8 * 70.0F) / 100.0F;
                f6 = f9;
                f7 = f10;
                f8 = f11;

            bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
            bufferbuilder.pos(0.0D, 100.0D, 0.0D).color(f6, f7, f8, afloat[3]).endVertex();
            int l1 = 16;

            for (int j2 = 0; j2 <= 16; ++j2)
            {
                float f21 = (float)j2 * ((float)Math.PI * 2F) / 16.0F;
                float f12 = MathHelper.sin(f21);
                float f13 = MathHelper.cos(f21);
                bufferbuilder.pos((double)(f12 * 120.0F), (double)(f13 * 120.0F), (double)(-f13 * 40.0F * afloat[3])).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
            }

            tessellator.draw();
            GlStateManager.popMatrix();
            GlStateManager.shadeModel(7424);
        }



        GlStateManager.enableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.pushMatrix();
        float f16 = 1.0F - world.getRainStrength(partialTicks);
        GlStateManager.color(1.0F, 1.0F, 1.0F, f16);
        GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);

        /*nebula start
        GL11.glDepthRange(0,5);
        mc.renderEngine.bindTexture(NEBULA_TEXTURES);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        if(nebula==null){
            nebula = new Nebula(122886L);
        }else {
            Random random = new Random(nebula.getSeed());
            double x = (double) (random.nextFloat() * 2.0F - 1.0F);
            double y = (double) (random.nextFloat() * 2.0F - 1.0F);
            double z = (double) (random.nextFloat() * 2.0F - 1.0F);
            double d4 = x * x + y * y + z * z;
            double size = (double) 300;
            if (d4 < 1.0D && d4 > 0.01D) {

                d4 = 1.0D / Math.sqrt(d4);
                x = x * d4;
                y = y * d4;
                z = z * d4;
                double d5 = x * 100.0D;
                double d6 = y * 100.0D;
                double d7 = z * 100.0D;
                double d8 = Math.atan2(x, z);
                double d9 = Math.sin(d8);
                double d10 = Math.cos(d8);
                double d11 = Math.atan2(Math.sqrt(x * x + z * z), y);
                double d12 = Math.sin(d11);
                double d13 = Math.cos(d11);
                double d14 = random.nextDouble() * Math.PI * 2.0D;
                double d15 = Math.sin(d14);
                double d16 = Math.cos(d14);
                int u = 0;
                int v = 0;
                for (int j = 0; j < 4; ++j) {
                    if (j == 0) {
                        u = 0;
                        v = 0;
                    }
                    if (j == 1) {
                        u = 1;
                        v = 0;
                    }
                    if (j == 2) {
                        u = 1;
                        v = 1;
                    }
                    if (j == 3) {
                        u = 0;
                        v = 1;
                    }
                    double d18 = (double) ((j & 2) - 1) * size;
                    double d19 = (double) ((j + 1 & 2) - 1) * size;
                    double d21 = d18 * d16 - d19 * d15;
                    double d22 = d19 * d16 + d18 * d15;
                    double d23 = d21 * d12 + 0.0D * d13;
                    double d24 = 0.0D * d12 - d21 * d13;
                    double d25 = d24 * d9 - d22 * d10;
                    double d26 = d22 * d9 + d24 * d10;
                    bufferbuilder.pos(d5 + d25, d6 + d23, d7 + d26).tex(u, v).endVertex();
                }
            }
        }
        tessellator.draw();
        //nebula end
        */


        GlStateManager.rotate(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
        f17 = 30.0F;
        //sun start
        mc.renderEngine.bindTexture(SUN_TEXTURES);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(-f17), 100.0D, (double)(-f17)).tex(0.0D, 0.0D).endVertex();
        bufferbuilder.pos((double)f17, 100.0D, (double)(-f17)).tex(1.0D, 0.0D).endVertex();
        bufferbuilder.pos((double)f17, 100.0D, (double)f17).tex(1.0D, 1.0D).endVertex();
        bufferbuilder.pos((double)(-f17), 100.0D, (double)f17).tex(0.0D, 1.0D).endVertex();
        tessellator.draw();
        //sun end

        /*int R=100;int G=100;int B=20;int Alpha=255;
        for(int c=10;c<=30;c++){
            Alpha=255-c*8;
            Vector4d color = new Vector4d(R,G,B,Alpha);
            DrawCircle(f17,-f17,c,100,color,tessellator);
        }*/

        

        //moon start
        f17 = 20.0F;
        mc.renderEngine.bindTexture(MOON_PHASES_TEXTURES);
        int k1 = world.getMoonPhase();
        int i2 = k1 % 4;
        int k2 = k1 / 4 % 2;
        float f22 = (float)(i2 + 0) / 4.0F;
        float f23 = (float)(k2 + 0) / 2.0F;
        float f24 = (float)(i2 + 1) / 4.0F;
        float f14 = (float)(k2 + 1) / 2.0F;
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(-f17), -100.0D, (double)f17).tex((double)f24, (double)f14).endVertex();
        bufferbuilder.pos((double)f17, -100.0D, (double)f17).tex((double)f22, (double)f14).endVertex();
        bufferbuilder.pos((double)f17, -100.0D, (double)(-f17)).tex((double)f22, (double)f23).endVertex();
        bufferbuilder.pos((double)(-f17), -100.0D, (double)(-f17)).tex((double)f24, (double)f23).endVertex();
        tessellator.draw();
        //moon end
        
        f17 = 20.0F;


        
 
        
        GlStateManager.disableTexture2D();
        float f15 = world.getStarBrightness(partialTicks) * f16;

        if (f15 > 0.0F)
        {
            GlStateManager.color(f15, f15, f15, f15);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableFog();
        
        GlStateManager.popMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.color(0.0F, 0.0F, 0.0F);
        double d3 = mc.player.getPositionEyes(partialTicks).y - world.getHorizon();

        if (d3 < 0.0D)
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 12.0F, 0.0F);

            GlStateManager.popMatrix();
            float f18 = 1.0F;
            float f19 = -((float)(d3 + 65.0D));
            float f20 = -1.0F;
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            bufferbuilder.pos(-1.0D, (double)f19, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(1.0D, (double)f19, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(1.0D, (double)f19, -1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(-1.0D, (double)f19, -1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(1.0D, (double)f19, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(1.0D, (double)f19, -1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(-1.0D, (double)f19, -1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(-1.0D, (double)f19, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
            tessellator.draw();
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, -((float)(d3 - 16.0D)), 0.0F);
        GlStateManager.popMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);

    }

    void DrawCircle(float cx, float cy, float r, int num_segments, Vector4d color,Tessellator tessellator)
    {
        BufferBuilder builder=tessellator.getBuffer();
        builder.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);

        for(int ii = 0; ii < num_segments; ii++)
        {
            float theta = 2.0f * 3.1415926f *(float)ii / (float)(num_segments);

            float x = r * MathHelper.cos(theta);//calculate the x component
            float y = r * MathHelper.sin(theta);//calculate the y component

            builder.pos(x+cx,100D,y+cy).color((float)color.x,(float)color.y,(float)color.z,(float)color.w).endVertex();
        }
        tessellator.draw();
    }
            
    
}