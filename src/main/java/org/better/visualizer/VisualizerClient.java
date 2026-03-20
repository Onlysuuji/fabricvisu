package org.better.visualizer;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public final class VisualizerClient implements ClientModInitializer {
    private static final Item[] SUPPORTED_ITEMS = {
            Items.DIAMOND_SWORD,
            Items.IRON_SWORD,
            Items.GOLDEN_SWORD,
            Items.DIAMOND_PICKAXE,
            Items.IRON_PICKAXE,
            Items.GOLDEN_PICKAXE,
            Items.DIAMOND_SHOVEL,
            Items.IRON_SHOVEL,
            Items.GOLDEN_SHOVEL,
            Items.BOW,
            Items.ENCHANTED_BOOK,
            Items.LEATHER_HELMET,
            Items.LEATHER_CHESTPLATE,
            Items.LEATHER_LEGGINGS,
            Items.LEATHER_BOOTS,
            Items.CHAINMAIL_HELMET,
            Items.CHAINMAIL_CHESTPLATE,
            Items.CHAINMAIL_LEGGINGS,
            Items.CHAINMAIL_BOOTS,
            Items.IRON_HELMET,
            Items.IRON_CHESTPLATE,
            Items.IRON_LEGGINGS,
            Items.IRON_BOOTS,
            Items.GOLDEN_HELMET,
            Items.GOLDEN_CHESTPLATE,
            Items.GOLDEN_LEGGINGS,
            Items.GOLDEN_BOOTS,
            Items.DIAMOND_HELMET,
            Items.DIAMOND_CHESTPLATE,
            Items.DIAMOND_LEGGINGS,
            Items.DIAMOND_BOOTS,
            Items.NETHERITE_HELMET,
            Items.NETHERITE_CHESTPLATE,
            Items.NETHERITE_LEGGINGS,
            Items.NETHERITE_BOOTS,
            Items.TURTLE_HELMET
    };

    @Override
    public void onInitializeClient() {
        Visualizer.LOGGER.info("Registering enchantment badge item properties");
        for (Item item : SUPPORTED_ITEMS) {
            registerBadgeProperties(item);
        }
    }

    private static void registerBadgeProperties(Item item) {
        ItemProperties.register(
                item,
                ResourceLocation.fromNamespaceAndPath(Visualizer.MOD_ID, "left_badge_level"),
                (stack, level, entity, seed) -> EnchantmentBadgeUtil.getLeftBadgeLevel(stack, level)
        );
        ItemProperties.register(
                item,
                ResourceLocation.fromNamespaceAndPath(Visualizer.MOD_ID, "right_badge_level"),
                (stack, level, entity, seed) -> EnchantmentBadgeUtil.getRightBadgeLevel(stack, level)
        );
        ItemProperties.register(
                item,
                ResourceLocation.fromNamespaceAndPath(Visualizer.MOD_ID, "top_left_badge_level"),
                (stack, level, entity, seed) -> EnchantmentBadgeUtil.getTopLeftBadgeLevel(stack, level)
        );
        ItemProperties.register(
                item,
                ResourceLocation.fromNamespaceAndPath(Visualizer.MOD_ID, "top_right_badge_level"),
                (stack, level, entity, seed) -> EnchantmentBadgeUtil.getTopRightBadgeLevel(stack, level)
        );
    }
}
