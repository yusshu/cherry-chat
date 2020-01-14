package me.yushust.cherrychat.formatting;

import me.yushust.cherrychat.exceptions.UnimplementedException;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultFormatter extends AbstractFormatter {

    private Chat chatManager;
    public VaultFormatter() {
        RegisteredServiceProvider<Chat> chatService = Bukkit.getServicesManager().getRegistration(Chat.class);
        if(chatService == null) {
            throw new UnimplementedException(
                "[CherryChat] No chat format provider is placed on the server, try installing: LuckPerms, PermissionsEx, DroxPerms"
            );
        } else {
            Chat chat = chatService.getProvider();
            if(chat == null) {
                throw new UnimplementedException(
                    "[CherryChat] No chat format provider is placed on the server, try installing: LuckPerms, PermissionsEx, DroxPerms"
                );
            }
            this.chatManager = chat;
        }
    }

    @Override
    public String format(Message message) {
        return String.format(
                Formatter.getDefaultFormat(),
                message.getSender().getName(),
                setPlaceholders(
                        message.getSender(),
                        message.getMessage()
                )
        );
    }

    @Override
    public String setPlaceholders(Player player, String text) {
        return text
                .replace("%prefix%", chatManager.getPlayerPrefix(player))
                .replace("%suffix%", chatManager.getPlayerSuffix(player))
                .replace("%group%", chatManager.getPlayerGroups(player)[0])
                .replace("%name%", player.getName())
                .replace("%displayname%", player.getDisplayName());
    }
}
