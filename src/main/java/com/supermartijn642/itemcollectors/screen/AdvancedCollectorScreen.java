package com.supermartijn642.itemcollectors.screen;

import com.supermartijn642.core.TextComponents;
import com.supermartijn642.core.gui.ScreenUtils;
import com.supermartijn642.core.gui.TileEntityBaseContainerScreen;
import com.supermartijn642.itemcollectors.CollectorTile;
import com.supermartijn642.itemcollectors.ItemCollectors;
import com.supermartijn642.itemcollectors.packet.*;
import net.minecraft.util.ResourceLocation;

/**
 * Created 7/15/2020 by SuperMartijn642
 */
public class AdvancedCollectorScreen extends TileEntityBaseContainerScreen<CollectorTile,AdvancedCollectorContainer> {

    private static final ResourceLocation BACKGROUND = new ResourceLocation("itemcollectors", "textures/filter_screen.png");

    private WhitelistButton whitelistButton;
    private DurabilityButton durabilityButton;
    private ShowAreaButton showAreaButton;

    public AdvancedCollectorScreen(AdvancedCollectorContainer container){
        super(container, TextComponents.empty().get());
    }

    @Override
    protected int sizeX(CollectorTile tile){
        return 224;
    }

    @Override
    protected int sizeY(CollectorTile tile){
        return 206;
    }

    @Override
    protected void addWidgets(CollectorTile tile){
        this.addWidget(new ArrowButton(30, 37, false, () -> ItemCollectors.CHANNEL.sendToServer(new PacketIncreaseXRange(this.menu.getTilePos()))));
        this.addWidget(new ArrowButton(30, 63, true, () -> ItemCollectors.CHANNEL.sendToServer(new PacketDecreaseXRange(this.menu.getTilePos()))));
        this.addWidget(new ArrowButton(73, 37, false, () -> ItemCollectors.CHANNEL.sendToServer(new PacketIncreaseYRange(this.menu.getTilePos()))));
        this.addWidget(new ArrowButton(73, 63, true, () -> ItemCollectors.CHANNEL.sendToServer(new PacketDecreaseYRange(this.menu.getTilePos()))));
        this.addWidget(new ArrowButton(116, 37, false, () -> ItemCollectors.CHANNEL.sendToServer(new PacketIncreaseZRange(this.menu.getTilePos()))));
        this.addWidget(new ArrowButton(116, 63, true, () -> ItemCollectors.CHANNEL.sendToServer(new PacketDecreaseZRange(this.menu.getTilePos()))));
        this.whitelistButton = this.addWidget(new WhitelistButton(175, 88, () -> ItemCollectors.CHANNEL.sendToServer(new PacketToggleWhitelist(this.menu.getTilePos()))));
        this.whitelistButton.update(tile.filterWhitelist);
        this.durabilityButton = this.addWidget(new DurabilityButton(197, 88, () -> ItemCollectors.CHANNEL.sendToServer(new PacketToggleDurability(this.menu.getTilePos()))));
        this.durabilityButton.update(tile.filterDurability);
        this.showAreaButton = this.addWidget(new ShowAreaButton(160, 45, () -> ItemCollectors.CHANNEL.sendToServer(new PacketToggleShowArea(this.menu.getTilePos()))));
        this.showAreaButton.update(tile.showArea);
    }

    @Override
    protected void tick(CollectorTile tile){
        this.whitelistButton.update(tile.filterWhitelist);
        this.durabilityButton.update(tile.filterDurability);
        this.showAreaButton.update(tile.showArea);
    }

    @Override
    protected void renderBackground(int mouseX, int mouseY, CollectorTile tile){
        ScreenUtils.bindTexture(BACKGROUND);
        ScreenUtils.drawTexture(0, 0, this.sizeX(), this.sizeY());
    }

    @Override
    protected void renderForeground(int mouseX, int mouseY, CollectorTile tile){
        ScreenUtils.drawCenteredString(TextComponents.blockState(tile.getBlockState()).get(), this.sizeX() / 2f, 6);
        ScreenUtils.drawString(this.inventory.getDisplayName(), 32, 112);

        ScreenUtils.drawString(TextComponents.translation("gui.itemcollectors.basic_collector.range",
            (tile.rangeX * 2 + 1), (tile.rangeY * 2 + 1), (tile.rangeZ * 2 + 1)).get(), 8, 26);
        ScreenUtils.drawCenteredString(TextComponents.string("x:").get(), 25, 51);
        ScreenUtils.drawCenteredString(TextComponents.string("" + tile.rangeX).get(), 39, 52);
        ScreenUtils.drawCenteredString(TextComponents.string("y:").get(), 68, 51);
        ScreenUtils.drawCenteredString(TextComponents.string("" + tile.rangeY).get(), 82, 52);
        ScreenUtils.drawCenteredString(TextComponents.string("z:").get(), 111, 51);
        ScreenUtils.drawCenteredString(TextComponents.string("" + tile.rangeZ).get(), 125, 52);
        ScreenUtils.drawString(TextComponents.translation("gui.itemcollectors.advanced_collector.filter").get(), 8, 78);
    }
}
