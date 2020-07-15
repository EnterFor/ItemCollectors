package com.supermartijn642.itemcollectors.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

/**
 * Created 7/8/2020 by SuperMartijn642
 */
public class WhitelistButton extends AbstractButton {

    private final ResourceLocation BUTTONS = new ResourceLocation("itemcollectors", "textures/blacklist_button.png");

    public boolean white = true;
    private final Runnable onPress;

    public WhitelistButton(int x, int y, Runnable onPress){
        super(x, y, 20, 20, new StringTextComponent(""));
        this.onPress = onPress;
    }

    public void update(boolean white){
        this.white = white;
    }

    @Override
    public void func_230930_b_(){
        this.onPress.run();
    }

    @Override
    public void func_230431_b_(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks){
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(BUTTONS);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.field_230695_q_);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        drawTexture(this.field_230690_l_, this.field_230691_m_, this.white ? 0 : 20, (this.field_230693_o_ ? this.field_230692_n_ ? 1 : 0 : 2) * 20);
        this.func_230441_a_(matrixStack, minecraft, mouseX, mouseY);
    }

    private static void drawTexture(int x, int y, int textureX, int textureY){
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y + 20, 0).tex(textureX / 40f, (textureY + 20) / 60f).endVertex();
        bufferbuilder.pos(x + 20, y + 20, 0).tex((textureX + 20) / 40f, (textureY + 20) / 60f).endVertex();
        bufferbuilder.pos(x + 20, y, 0).tex((textureX + 20) / 40f, textureY / 60f).endVertex();
        bufferbuilder.pos(x, y, 0).tex(textureX / 40f, textureY / 60f).endVertex();
        bufferbuilder.finishDrawing();
        RenderSystem.enableAlphaTest();
        WorldVertexBufferUploader.draw(bufferbuilder);
    }
}
