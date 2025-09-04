package de.aboutcloud.randomise.util;

import de.aboutcloud.randomise.Randomise;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

public class LanguageUtil {

    public static Locale getLocale(Randomise instance, Player player) {
        AtomicReference<Locale> locale = new AtomicReference<>(Locale.forLanguageTag("en-EN")); // default fallback

        try {
            instance.getDatabaseService().withTransaction(instance, c -> {
                try (var ps = c.prepareStatement("SELECT locale FROM randomise WHERE uuid = ?")) {
                    ps.setString(1, player.getUniqueId().toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        locale.set(Locale.forLanguageTag(rs.getString("locale")));
                    }
                }
            });
        } catch (Exception exception) {
            instance.getLogger().warning("Select failed: " + exception.getMessage());
        }

        return locale.get();
    }


}
