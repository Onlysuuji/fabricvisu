package org.better.visualizer.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;

public final class EnchantmentBadgeUtil {
    private EnchantmentBadgeUtil() {
    }

    public static int getLeftBadgeLevel(ItemStack stack, ClientWorld world) {
        ClientWorld actualWorld = resolveWorld(world);
        if (actualWorld == null) {
            return 0;
        }

        if (isSupportedSword(stack)) {
            return clamp(getEnchantLevel(stack, actualWorld, Enchantments.SHARPNESS), 0, 5);
        }

        if (isSupportedTool(stack)) {
            return clamp(getEnchantLevel(stack, actualWorld, Enchantments.EFFICIENCY), 0, 5);
        }

        if (isSupportedBow(stack)) {
            return clamp(getEnchantLevel(stack, actualWorld, Enchantments.POWER), 0, 5);
        }

        if (isSupportedBook(stack)) {
            return clamp(getStoredBookEnchantLevel(stack, actualWorld, Enchantments.SHARPNESS), 0, 5);
        }

        if (isSupportedArmor(stack)) {
            return clamp(getEnchantLevel(stack, actualWorld, Enchantments.PROTECTION), 0, 4);
        }

        return 0;
    }

    public static int getRightBadgeLevel(ItemStack stack, ClientWorld world) {
        ClientWorld actualWorld = resolveWorld(world);
        if (actualWorld == null) {
            return 0;
        }

        if (isSupportedSword(stack)) {
            return clamp(getEnchantLevel(stack, actualWorld, Enchantments.FIRE_ASPECT), 0, 2);
        }

        if (isSupportedTool(stack)) {
            return clamp(getEnchantLevel(stack, actualWorld, Enchantments.FORTUNE), 0, 3);
        }

        if (isSupportedBow(stack)) {
            return clamp(getEnchantLevel(stack, actualWorld, Enchantments.FLAME), 0, 1);
        }

        if (isSupportedBook(stack)) {
            return clamp(getStoredBookEnchantLevel(stack, actualWorld, Enchantments.PROTECTION), 0, 5);
        }

        if (isSupportedArmor(stack)) {
            return clamp(getEnchantLevel(stack, actualWorld, Enchantments.UNBREAKING), 0, 3);
        }

        return 0;
    }

    public static int getTopLeftBadgeLevel(ItemStack stack, ClientWorld world) {
        ClientWorld actualWorld = resolveWorld(world);
        if (actualWorld == null) {
            return 0;
        }

        if (isSupportedSword(stack)) {
            return clamp(getEnchantLevel(stack, actualWorld, Enchantments.KNOCKBACK), 0, 5);
        }

        if (isSupportedBow(stack)) {
            return clamp(getEnchantLevel(stack, actualWorld, Enchantments.PUNCH), 0, 5);
        }

        if (isSupportedBook(stack)) {
            return clamp(getStoredBookEnchantLevel(stack, actualWorld, Enchantments.EFFICIENCY), 0, 5);
        }

        return 0;
    }

    public static int getTopRightBadgeLevel(ItemStack stack, ClientWorld world) {
        ClientWorld actualWorld = resolveWorld(world);
        if (actualWorld == null) {
            return 0;
        }

        if (isSupportedBook(stack)) {
            return clamp(getStoredBookEnchantLevel(stack, actualWorld, Enchantments.FIRE_ASPECT), 0, 2);
        }

        return 0;
    }

    public static int getProtectionArmorLevel(ItemStack stack, ClientWorld world) {
        ClientWorld actualWorld = resolveWorld(world);
        if (actualWorld == null) {
            return 0;
        }

        return clamp(getEnchantLevel(stack, actualWorld, Enchantments.PROTECTION), 0, 4);
    }

    private static ClientWorld resolveWorld(ClientWorld world) {
        return world != null ? world : MinecraftClient.getInstance().world;
    }

    private static boolean isSupportedSword(ItemStack stack) {
        return stack.isOf(Items.DIAMOND_SWORD)
                || stack.isOf(Items.IRON_SWORD)
                || stack.isOf(Items.GOLDEN_SWORD);
    }

    private static boolean isSupportedTool(ItemStack stack) {
        return stack.isOf(Items.DIAMOND_PICKAXE)
                || stack.isOf(Items.IRON_PICKAXE)
                || stack.isOf(Items.GOLDEN_PICKAXE)
                || stack.isOf(Items.DIAMOND_SHOVEL)
                || stack.isOf(Items.IRON_SHOVEL)
                || stack.isOf(Items.GOLDEN_SHOVEL);
    }

    private static boolean isSupportedBow(ItemStack stack) {
        return stack.isOf(Items.BOW);
    }

    private static boolean isSupportedBook(ItemStack stack) {
        return stack.isOf(Items.ENCHANTED_BOOK);
    }

    private static boolean isSupportedArmor(ItemStack stack) {
        return stack.isOf(Items.IRON_HELMET)
                || stack.isOf(Items.IRON_CHESTPLATE)
                || stack.isOf(Items.IRON_LEGGINGS)
                || stack.isOf(Items.IRON_BOOTS)
                || stack.isOf(Items.DIAMOND_HELMET)
                || stack.isOf(Items.DIAMOND_CHESTPLATE)
                || stack.isOf(Items.DIAMOND_LEGGINGS)
                || stack.isOf(Items.DIAMOND_BOOTS);
    }

    private static int getEnchantLevel(ItemStack stack, ClientWorld world, RegistryKey<Enchantment> enchantmentKey) {
        RegistryEntry<Enchantment> enchantment = world.getRegistryManager()
                .getWrapperOrThrow(RegistryKeys.ENCHANTMENT)
                .getOrThrow(enchantmentKey);
        return EnchantmentHelper.getLevel(enchantment, stack);
    }

    private static int getStoredBookEnchantLevel(ItemStack stack, ClientWorld world, RegistryKey<Enchantment> enchantmentKey) {
        RegistryEntry<Enchantment> enchantment = world.getRegistryManager()
                .getWrapperOrThrow(RegistryKeys.ENCHANTMENT)
                .getOrThrow(enchantmentKey);
        ItemEnchantmentsComponent stored = stack.getOrDefault(
                DataComponentTypes.STORED_ENCHANTMENTS,
                ItemEnchantmentsComponent.DEFAULT
        );
        return stored.getLevel(enchantment);
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }
}
