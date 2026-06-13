package com.rabimi.fakehax;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import java.awt.Color;

public class fakehax implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // 画面描画のイベントに登録
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            
            // プレイヤーがワールドに入っていない時や、F1キーでHUDが非表示の時は描画しない
            if (client.player == null || client.options.hudHidden) {
                return;
            }

            TextRenderer textRenderer = client.textRenderer;
            
            // 画面の横幅（右上の基準点を見つけるために必要）
            int scaledWidth = client.getWindow().getScaledWidth();

            // --- 表示したい文字列のリスト（チート風） ---
            String[] cheatLines = {
                "§c§lMarlow Client §7v8.1.0", // 赤色・太字のクライアント名
                "§fFPS: §a" + MinecraftClient.getCurrentFps(), // FPS表示
                "§fModules: §7[§aFly§7] [§aKillAura§7] [§aESP§7]", // 有効化されてる風の機能
                "§fTarget: §4None" // ターゲット
            };

            // 右上から下に並べて描画していく
            int yOffset = 5; // 上からの最初の隙間
            for (String line : cheatLines) {
                // 文字列の幅を計算して、右端からぴったり収まるようにX座標を調整
                int textWidth = textRenderer.getWidth(line);
                int x = scaledWidth - textWidth - 5; // 右端から5ピクセルあける

                // 画面に描画 (drawContext を使用)
                // 引数: フォント, 文字列, X座標, Y座標, 色(RGB)
                drawContext.drawText(textRenderer, line, x, yOffset, Color.WHITE.getRGB(), true);

                // 次の行のためにY座標を下げる（フォントの高さ + 行間）
                yOffset += textRenderer.fontHeight + 2;
            }
        });
    }
}
