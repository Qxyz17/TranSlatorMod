package com.Translator.event;

import com.Translator.translator.Translator;
import com.Translator.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
// 注释掉不存在的 ClientChatSentEvent 导入
// import net.minecraftforge.client.event.ClientChatSentEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommandEvent {
    // 处理 .ts 命令（切换翻译开关）
    @SubscribeEvent
    public void onCommand(ServerChatEvent event) {
        String message = event.message;
        if (message.startsWith(".ts ")) {
            event.setCanceled(true);  // 取消命令本身发送
            String[] args = message.substring(4).trim().split(" ");
            handleCommand(event.player, args);
        }
    }

    private void handleCommand(EntityPlayer player, String[] args) {
        if (args.length != 2) {
            player.addChatMessage(new ChatComponentText("§c用法: .ts <msg|me> <on|off>"));
            player.addChatMessage(new ChatComponentText("§7msg: 他人消息翻译 | me: 自己消息翻译"));
            return;
        }

        String target = args[0].toLowerCase();
        boolean state = args[1].equalsIgnoreCase("on");

        if (target.equals("msg")) {
            ModConfig.updateConfig(true, state);
            player.addChatMessage(new ChatComponentText("§a他人消息翻译已" + (state ? "开启" : "关闭")));
        } else if (target.equals("me")) {
            ModConfig.updateConfig(false, state);
            player.addChatMessage(new ChatComponentText("§a自己消息翻译已" + (state ? "开启" : "关闭")));
        } else {
            player.addChatMessage(new ChatComponentText("§c参数错误! 请使用 msg 或 me"));
        }
    }
}

public class ChatEvent {
    // 处理他人消息（英译中）
    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        if (event.type != 0) return; // 仅处理玩家聊天消息

        String originalText = event.message.getUnformattedText();
        // 过滤系统消息，只处理玩家发送的消息（格式：<玩家名> 内容）
        if (originalText.startsWith("<") && originalText.contains("> ")) {
            String translatedText = Translator.translateEnToZh(originalText);
            event.message = new ChatComponentText(translatedText);
        }
    }

    // 注释掉 ClientChatSentEvent 相关代码（1.8.9 不支持）
    // @SubscribeEvent
    // public void onChatSent(ClientChatSentEvent event) {
    //     if (!ModConfig.translateSelf) return;
    //     String original = event.message;
    //     String translated = Translator.translateZhToEn(original);
    //     event.message = translated;
    //     String playerName = Minecraft.getMinecraft().thePlayer.getName();
    //     String displayText = String.format("<%s> %s (%s)", playerName, original, translated);
    //     Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(displayText));
    // }
}
