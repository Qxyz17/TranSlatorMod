package com.translator.event;

import com.translator.config.ModConfig;
import com.translator.translator.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatEvent {
    // 处理自己发送的消息（中译英）
    @SubscribeEvent
    public void onChatSent(ClientChatEvent event) {
        if (!ModConfig.translateSelf) return;
        String original = event.message;
        String translated = Translator.translateZhToEn(original);
        event.message = translated; // 替换发送到服务器的消息

        // 本地显示“原始内容 (翻译内容)”
        String playerName = Minecraft.getMinecraft().thePlayer.getName();
        String displayText = String.format("<%s> %s (%s)", playerName, original, translated);
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(displayText));
    }

    // 处理他人发送的消息（英译中，保留原有逻辑）
    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        if (event.type != 0 || !ModConfig.translateOthers) return;
        String translated = Translator.translateEnToZh(event.message.getUnformattedText());
        event.message = new ChatComponentText(translated);
    }
}
