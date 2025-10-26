package com.translator.event;

import com.translator.config.ModConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.ServerChatEvent;
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