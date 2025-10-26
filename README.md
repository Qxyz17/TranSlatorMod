# Minecraft 1.8.9 聊天翻译模组（Chat Translator Mod）

一款用于 Minecraft 1.8.9 的本地聊天翻译模组，支持中英文双向翻译，无需联网，适配国际服游玩场景。


## 功能特性
1. **双向翻译**
   - 自动翻译他人发送的英文消息（英译中）
   - 自动翻译自己发送的中文消息（中译英，输入时不处理，按下Enter后翻译发送）
2. **灵活控制**
   - 命令 `.ts msg on/off`：切换他人消息翻译
   - 命令 `.ts me on/off`：切换自己消息翻译
3. **格式友好**
   - 他人消息：`<玩家名> 原始英文 (翻译中文)`
   - 自己消息：`<你的ID> 原始中文 (翻译英文)`（仅本地可见，服务器接收翻译后英文）
4. **本地词库**
   - 基于 CC-CEDICT 开源词库，无需联网
   - 支持手动扩展词库（`assets/translator/lang/` 目录下）


## 安装方法
1. 确保 Minecraft 已安装 Forge 1.8.9
2. 下载模组 Jar 文件（[Releases](https://github.com/你的用户名/TranslatorMod/releases) 页面）
3. 将 Jar 文件放入 `.minecraft/mods` 目录
4. 启动游戏，在模组列表中确认“Chat Translator”已启用


## 词库扩展
1. 找到模组 Jar 文件，用压缩软件打开
2. 进入 `assets/translator/lang/` 目录
3. 编辑 `en_to_zh.txt`（英译中）或 `zh_to_en.txt`（中译英）
   - 格式：`关键词\t翻译结果`（每行一条，Tab 分隔）
   - 示例：`T-shirt\tT恤`、`U盘\tUSB flash drive`
4. 保存后重启游戏生效


## 命令说明
| 命令               | 功能描述                     |
|--------------------|------------------------------|
| `.ts msg on`       | 开启他人消息英译中           |
| `.ts msg off`      | 关闭他人消息英译中           |
| `.ts me on`        | 开启自己消息中译英           |
| `.ts me off`       | 关闭自己消息中译英           |
| `.ts help`         | 查看命令帮助（可选扩展）     |


## 开发指南
### 环境准备
1. 安装 JDK 8（Minecraft 1.8.9 依赖）
2. 克隆本仓库：`git clone https://github.com/你的用户名/TranslatorMod.git`
3. 进入项目目录，执行 `gradlew genSources eclipse`（生成 IDE 项目）
4. 用 IntelliJ IDEA 或 Eclipse 打开项目

### 编译模组
- 执行 `gradlew build`，编译后的 Jar 文件在 `build/libs/` 目录下


## 致谢
- 词库基于 [CC-CEDICT](https://www.mdbg.net/chinese/dictionary?page=cc-cedict) 开源项目
- 基于 Minecraft Forge 1.8.9 开发


## 许可证
[MIT License](LICENSE)
