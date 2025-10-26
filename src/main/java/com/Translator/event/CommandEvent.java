package com.Translator.event;

import com.Translator.config.ModConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommandEvent {
    // 处理 .ts 命令（切换翻译开关）
    @SubscribeEvent
    public void onServerChat(ServerChatEvent event) {
        String msg = event.message.trim();
        if (msg.startsWith(".ts ")) {
            event.setCanceled(true); // 取消原命令发送
            handleCommand(event.player, msg.substring(4).split(" "));
        }
    }

    private void handleCommand(EntityPlayer player, String[] args) {
        if (args.length != 2) {
            player.addChatMessage(new ChatComponentText("§c用法：.ts <msg|me> <on|off>"));
            player.addChatMessage(new ChatComponentText("§7msg=他人消息翻译，me=自己消息翻译"));
            return;
        }

        boolean isOn = args[1].equalsIgnoreCase("on");
        if (args[0].equalsIgnoreCase("msg")) {
            ModConfig.translateOthers = isOn;
            player.addChatMessage(new ChatComponentText("§a他人消息翻译已" + (isOn ? "开启" : "关闭")));
        } else if (args[0].equalsIgnoreCase("me")) {
            ModConfig.translateSelf = isOn;
            player.addChatMessage(new ChatComponentText("§a自己消息翻译已" + (isOn ? "开启" : "关闭")));
        } else {
            player.addChatMessage(new ChatComponentText("§c参数错误！第一个参数只能是 msg 或 me"));
        }
    }
}
