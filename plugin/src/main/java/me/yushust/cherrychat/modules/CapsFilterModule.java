package me.yushust.cherrychat.modules;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.CherryChatPlugin;
import me.yushust.cherrychat.api.bukkit.event.AsyncUserChatEvent;
import me.yushust.cherrychat.api.bukkit.intercept.MessageInterceptor;
import me.yushust.cherrychat.api.bukkit.util.Configuration;
import me.yushust.cherrychat.util.Texts;

@Getter
public class CapsFilterModule implements MessageInterceptor {

    private CherryChatPlugin plugin;
    private String moduleName = "caps-filter";
    private boolean capitalizeFirst;
    private int minCapitalizedChars;

    public CapsFilterModule(CherryChatPlugin plugin) {
        Configuration config = plugin.getConfig();
        this.plugin = plugin;
        this.capitalizeFirst = config.getBoolean("capitalize-first-letter");
        this.minCapitalizedChars = config.getInt("min-capitalized-chars");
    }

    @Override
    public void onChat(AsyncUserChatEvent event) {
        if(event.isCancelled()) return;

        String message = event.getMessage();

        message = Texts.toLowerCase(
                message,
                minCapitalizedChars
        );

        if(capitalizeFirst) {
            message = Texts.capitalizeFirst(message);
        }

        event.setMessage(message);
    }
}
