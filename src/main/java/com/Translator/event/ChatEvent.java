package com.Translator.event;

import com.Translator.translator.Translator;
import com.Translator.config.ModConfig;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatEvent {
    // 处理他人消息英译中
    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        if (event.type != 0) return; // 只处理玩家聊天，排除系统消息
        if (ModConfig.translateOthers) {
            String translated = Translator.translateEnToZh(event.message.getUnformattedText());
            event.message = new ChatComponentText(translated);
        }
    }
}
