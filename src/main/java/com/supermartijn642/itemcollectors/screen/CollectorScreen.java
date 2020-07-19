package com.supermartijn642.itemcollectors.screen;

import com.supermartijn642.itemcollectors.CollectorTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * Created 7/15/2020 by SuperMartijn642
 */
public abstract class CollectorScreen<T extends CollectorContainer> extends GuiContainer {

    protected final T container;
    protected final String title;

    public CollectorScreen(T container, String title){
        super(container);
        this.container = container;
        this.title = I18n.format(title + ".name");

        this.xSize = container.width;
        this.ySize = container.height;
    }

    @Override
    public void initGui(){
        super.initGui();

        CollectorTile tile = this.container.getTileOrClose();
        if(tile != null)
            this.addButtons(tile);
    }

    protected abstract void addButtons(CollectorTile tile);

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        CollectorTile tile = this.container.getTileOrClose();
        if(tile != null)
            this.drawToolTips(tile, mouseX, mouseY);
    }

    protected abstract void drawToolTips(CollectorTile tile, int mouseX, int mouseY);

    @Override
    public void updateScreen(){
        CollectorTile tile = this.container.getTileOrClose();
        if(tile == null)
            return;

        super.updateScreen();
        this.tick(tile);
    }

    protected abstract void tick(CollectorTile tile);

    protected abstract String getBackground();

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("itemcollectors", "textures/" + this.getBackground()));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        CollectorTile tile = this.container.getTileOrClose();
        if(tile != null)
            this.drawText(tile);
    }

    protected abstract void drawText(CollectorTile tile);

    public void drawCenteredString(String s, int x, int y){
        this.fontRenderer.drawString(s, this.guiLeft + x - this.fontRenderer.getStringWidth(s) / 2, this.guiTop + y, 4210752);
    }

    public void drawCenteredString(ITextComponent text, int x, int y){
        String s = text.getFormattedText();
        this.fontRenderer.drawString(s, this.guiLeft + x - this.fontRenderer.getStringWidth(s) / 2, this.guiTop + y, 4210752);
    }

    public void drawString(ITextComponent text, int x, int y){
        String s = text.getFormattedText();
        this.fontRenderer.drawString(s, this.guiLeft + x, this.guiTop + y, 4210752);
    }

    public void renderToolTip(boolean translate, String string, int x, int y){
        super.drawHoveringText(translate ? new TextComponentTranslation(string).getFormattedText() : string, x, y);
    }

    @Override
    protected void actionPerformed(GuiButton button){
        if(button instanceof Pressable)
            ((Pressable)button).onPress();
    }
}
