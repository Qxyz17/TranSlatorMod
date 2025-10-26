package com.translator.event;

import com.translator.TranslatorMod;
import com.translator.config.ModConfig;
import com.translator.translator.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.ClientChatSentEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatEvent {
    // 处理收到的他人消息（英译中）
    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        if (event.type != 0) return;  // 只处理玩家聊天（type=0）

        String original = event.message.getUnformattedText();
        // 过滤命令消息（如 /say）
        if (original.startsWith("<") && original.contains("> ")) {
            String translated = Translator.translateEnToZh(original);
            event.message = new ChatComponentText(translated);
        }
    }

    // 处理自己发送的消息（中译英）
    @SubscribeEvent
    public void onChatSent(ClientChatSentEvent event) {
        if (!ModConfig.translateSelf) return;

        String original = event.message;
        String translated = Translator.translateZhToEn(original);

        // 替换发送内容为翻译后的英文
        event.message = translated;

        // 本地显示原始中文+翻译结果（格式：<自己> 中文 (英文)）
        String playerName = Minecraft.getMinecraft().thePlayer.getName();
        String displayText = String.format("<%s> %s (%s)", playerName, original, translated);
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(displayText));
    }
}