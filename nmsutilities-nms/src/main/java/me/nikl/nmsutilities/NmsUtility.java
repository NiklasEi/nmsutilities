package me.nikl.nmsutilities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

/**
 * Created by niklas on 10/17/16.
 *
 * interface for nms utils
 */
public interface NmsUtility {

    void updateInventoryTitle(Player player, String newTitle);

    void sendJSON(Player player, String json);

    void sendJSON(Player player, Collection<String> json);

    void sendJSON(Collection<Player> players, String json);

    void sendJSON(Collection<Player> players, Collection<String> json);

    void sendTitle(Player player, String title, String subTitle);

    void sendActionbar(Player player, String message);

    void sendListHeader(Player player, String header);

    void sendListFooter(Player player, String footer);

    ItemStack removeGlow(ItemStack item);

    ItemStack addGlow(ItemStack item);
}
