package me.nikl.nmsutilities;

import java.util.Collection;

import net.minecraft.network.chat.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by niklas
 */
public class NmsUtility_1_20_R1 implements NmsUtility {
    @Override
    public void updateInventoryTitle(Player player, String newTitle) {
        final ServerPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        final ClientboundOpenScreenPacket packet = new ClientboundOpenScreenPacket(entityPlayer.containerMenu.containerId,
                WindowType_1_20_R1.guessBySlots(entityPlayer.containerMenu.getBukkitView().getTopInventory().getSize()).getType()
                , Component.Serializer.fromJson("{\"text\": \""
                + ChatColor.translateAlternateColorCodes('&', newTitle + "\"}")));

        final ClientboundContainerSetContentPacket contentPacket = new ClientboundContainerSetContentPacket(entityPlayer.containerMenu.containerId, 0,
                entityPlayer.containerMenu.getItems(), entityPlayer.getMainHandItem());
        entityPlayer.connection.send(packet);
        entityPlayer.connection.send(contentPacket);
    }

    @Override
    public void sendJSON(Player player, String json) {
        final ChatType.Bound chatType = ChatType.bind(ChatType.CHAT, ((CraftPlayer) player).getHandle().level().registryAccess(), Component.Serializer.fromJson("{\"text\": \""
                + ChatColor.translateAlternateColorCodes('&', player.getDisplayName() + "\"}")));
        ((CraftPlayer) player).getHandle().sendChatMessage(OutgoingChatMessage.create(PlayerChatMessage.system(ChatColor.translateAlternateColorCodes('&',json))), true, chatType);
    }

    @Override
    public void sendJSON(Player player, Collection<String> json) {
        for (final String message : json) {
            sendJSON(player, message);
        }
    }

    @Override
    public void sendJSON(Collection<Player> players, String json) {
        for (final Player player : players) {
            sendJSON(player, json);
        }
    }

    @Override
    public void sendJSON(Collection<Player> players, Collection<String> json) {
        for (final String message : json) {
            sendJSON(players, message);
        }
    }

    @Override
    public void sendTitle(Player player, String title, String subTitle, int durationTicks) {
        if (title != null) {
            final Component comp = Component.Serializer.fromJson("{\"text\": \""
                    + ChatColor.translateAlternateColorCodes('&', title + "\"}"));
            final ClientboundSetTitleTextPacket packet = new ClientboundSetTitleTextPacket(comp);
            ((CraftPlayer) player).getHandle().connection.send(packet);
        }
        if (subTitle != null) {
            final Component comp = Component.Serializer.fromJson("{\"text\": \""
                    + ChatColor.translateAlternateColorCodes('&', subTitle + "\"}"));
            final ClientboundSetSubtitleTextPacket packet = new ClientboundSetSubtitleTextPacket(comp);
            ((CraftPlayer) player).getHandle().connection.send(packet);
        }
        final ClientboundSetTitlesAnimationPacket length = new ClientboundSetTitlesAnimationPacket(10, durationTicks, 10);
        ((CraftPlayer) player).getHandle().connection.send(length);
    }

    @Override
    public void sendActionbar(Player player, String message) {
        final Component comp = Component.Serializer.fromJson("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message + "\"}"));
        final ClientboundSetActionBarTextPacket packet = new ClientboundSetActionBarTextPacket(comp);
        ((CraftPlayer) player).getHandle().connection.send(packet);
    }

    @Override
    public void sendList(Player player, String header, String footer) {
        final Component headerComponent = header == null ? null : Component.Serializer.fromJson("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', header + "\"}"));
        final Component footerComponent = footer == null ? null : Component.Serializer.fromJson("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', footer + "\"}"));
        final ClientboundTabListPacket packet = new ClientboundTabListPacket(headerComponent, footerComponent);
        ((CraftPlayer) player).getHandle().connection.send(packet);
    }

    @Override
    public void sendListFooter(Player player, String footer) {
        Bukkit.getConsoleSender().sendMessage("From MC 1.17 on, please use the method 'sendList' instead of 'sendListFooter'/'sendListHeader'");
    }

    @Override
    public void sendListHeader(Player player, String header) {
        Bukkit.getConsoleSender().sendMessage("From MC 1.17 on, please use the method 'sendList' instead of 'sendListFooter'/'sendListHeader'");
    }

    @Override
    public org.bukkit.inventory.ItemStack removeGlow(org.bukkit.inventory.ItemStack item) {
        if (item == null) {
            return null;
        }
        final ItemMeta meta = item.getItemMeta();
        for (final Enchantment enchantment : Enchantment.values()) {
            meta.removeEnchant(enchantment);
        }
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public org.bukkit.inventory.ItemStack addGlow(org.bukkit.inventory.ItemStack item) {
        if (item == null) {
            return null;
        }
        item.addUnsafeEnchantment(Enchantment.LUCK, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }
}
