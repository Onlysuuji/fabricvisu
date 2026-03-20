package org.better.visualizer.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.better.visualizer.Visualizer;

public final class VisualizerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerBadgeProperties(Items.DIAMOND_SWORD);
        registerBadgeProperties(Items.IRON_SWORD);
        registerBadgeProperties(Items.GOLDEN_SWORD);

        registerBadgeProperties(Items.DIAMOND_PICKAXE);
        registerBadgeProperties(Items.IRON_PICKAXE);
        registerBadgeProperties(Items.GOLDEN_PICKAXE);

        registerBadgeProperties(Items.DIAMOND_SHOVEL);
        registerBadgeProperties(Items.IRON_SHOVEL);
        registerBadgeProperties(Items.GOLDEN_SHOVEL);

        registerBadgeProperties(Items.BOW);
        registerBadgeProperties(Items.ENCHANTED_BOOK);

        registerBadgeProperties(Items.LEATHER_HELMET);
        registerBadgeProperties(Items.LEATHER_CHESTPLATE);
        registerBadgeProperties(Items.LEATHER_LEGGINGS);
        registerBadgeProperties(Items.LEATHER_BOOTS);

        registerBadgeProperties(Items.CHAINMAIL_HELMET);
        registerBadgeProperties(Items.CHAINMAIL_CHESTPLATE);
        registerBadgeProperties(Items.CHAINMAIL_LEGGINGS);
        registerBadgeProperties(Items.CHAINMAIL_BOOTS);

        registerBadgeProperties(Items.IRON_HELMET);
        registerBadgeProperties(Items.IRON_CHESTPLATE);
        registerBadgeProperties(Items.IRON_LEGGINGS);
        registerBadgeProperties(Items.IRON_BOOTS);

        registerBadgeProperties(Items.GOLDEN_HELMET);
        registerBadgeProperties(Items.GOLDEN_CHESTPLATE);
        registerBadgeProperties(Items.GOLDEN_LEGGINGS);
        registerBadgeProperties(Items.GOLDEN_BOOTS);

        registerBadgeProperties(Items.DIAMOND_HELMET);
        registerBadgeProperties(Items.DIAMOND_CHESTPLATE);
        registerBadgeProperties(Items.DIAMOND_LEGGINGS);
        registerBadgeProperties(Items.DIAMOND_BOOTS);

        registerBadgeProperties(Items.NETHERITE_HELMET);
        registerBadgeProperties(Items.NETHERITE_CHESTPLATE);
        registerBadgeProperties(Items.NETHERITE_LEGGINGS);
        registerBadgeProperties(Items.NETHERITE_BOOTS);

        registerBadgeProperties(Items.TURTLE_HELMET);

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof PlayerEntityRenderer playerEntityRenderer) {
                registrationHelper.register(new ProtectionArmorFeatureRenderer(
                        playerEntityRenderer,
                        MinecraftClient.getInstance().getEntityModelLoader()
                ));
            }
        });
    }

    private static void registerBadgeProperties(Item item) {
        ModelPredicateProviderRegistry.register(
                item,
                Identifier.of(Visualizer.MOD_ID, "left_badge_level"),
                (stack, world, entity, seed) -> EnchantmentBadgeUtil.getLeftBadgeLevel(stack, world)
        );

        ModelPredicateProviderRegistry.register(
                item,
                Identifier.of(Visualizer.MOD_ID, "right_badge_level"),
                (stack, world, entity, seed) -> EnchantmentBadgeUtil.getRightBadgeLevel(stack, world)
        );

        ModelPredicateProviderRegistry.register(
                item,
                Identifier.of(Visualizer.MOD_ID, "top_left_badge_level"),
                (stack, world, entity, seed) -> EnchantmentBadgeUtil.getTopLeftBadgeLevel(stack, world)
        );

        ModelPredicateProviderRegistry.register(
                item,
                Identifier.of(Visualizer.MOD_ID, "top_right_badge_level"),
                (stack, world, entity, seed) -> EnchantmentBadgeUtil.getTopRightBadgeLevel(stack, world)
        );
    }
}
