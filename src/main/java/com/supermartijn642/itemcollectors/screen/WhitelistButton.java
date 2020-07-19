package com.supermartijn642.itemcollectors.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

/**
 * Created 7/8/2020 by SuperMartijn642
 */
public class WhitelistButton extends GuiButton implements Pressable {

    private final ResourceLocation BUTTONS = new ResourceLocation("itemcollectors", "textures/blacklist_button.png");

    public boolean white = true;
    private final Runnable onPress;

    public WhitelistButton(int buttonId, int x, int y, Runnable onPress){
        super(buttonId, x, y, 20, 20, "");
        this.onPress = onPress;
    }

    public void update(boolean white){
        this.white = white;
    }

    @Override
    public void onPress(){
        this.onPress.run();
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks){
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        Minecraft.getMinecraft().getTextureManager().bindTexture(BUTTONS);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        drawTexture(this.x, this.y, this.white ? 0 : 20, (this.enabled ? this.hovered ? 1 : 0 : 2) * 20);
    }

    private static void drawTexture(int x, int y, int textureX, int textureY){
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y + 20, 0).tex(textureX / 40f, (textureY + 20) / 60f).endVertex();
        bufferbuilder.pos(x + 20, y + 20, 0).tex((textureX + 20) / 40f, (textureY + 20) / 60f).endVertex();
        bufferbuilder.pos(x + 20, y, 0).tex((textureX + 20) / 40f, textureY / 60f).endVertex();
        bufferbuilder.pos(x, y, 0).tex(textureX / 40f, textureY / 60f).endVertex();
        tessellator.draw();
    }
}
