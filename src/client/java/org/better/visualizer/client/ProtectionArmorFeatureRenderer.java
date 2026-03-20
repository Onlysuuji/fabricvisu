package org.better.visualizer.client;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.better.visualizer.Visualizer;

public final class ProtectionArmorFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    private static final int MAX_PROTECTION_LEVEL = 4;

    private final BipedEntityModel<AbstractClientPlayerEntity> innerModel;
    private final BipedEntityModel<AbstractClientPlayerEntity> outerModel;

    public ProtectionArmorFeatureRenderer(
            FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context,
            EntityModelLoader modelLoader
    ) {
        super(context);
        this.innerModel = new BipedEntityModel<>(modelLoader.getModelPart(EntityModelLayers.PLAYER_INNER_ARMOR));
        this.outerModel = new BipedEntityModel<>(modelLoader.getModelPart(EntityModelLayers.PLAYER_OUTER_ARMOR));
    }

    @Override
    public void render(
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            AbstractClientPlayerEntity player,
            float limbAngle,
            float limbDistance,
            float tickDelta,
            float animationProgress,
            float headYaw,
            float headPitch
    ) {
        if (!(player.getWorld() instanceof ClientWorld)) {
            return;
        }

        renderPiece(matrices, vertexConsumers, light, player, EquipmentSlot.HEAD);
        renderPiece(matrices, vertexConsumers, light, player, EquipmentSlot.CHEST);
        renderPiece(matrices, vertexConsumers, light, player, EquipmentSlot.LEGS);
        renderPiece(matrices, vertexConsumers, light, player, EquipmentSlot.FEET);
    }

    private void renderPiece(
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            AbstractClientPlayerEntity player,
            EquipmentSlot slot
    ) {
        ItemStack stack = player.getEquippedStack(slot);
        if (stack.isEmpty()) {
            return;
        }

        String material = getSupportedMaterial(stack);
        if (material == null) {
            return;
        }

        int protection = EnchantmentBadgeUtil.getProtectionArmorLevel(stack, (ClientWorld) player.getWorld());
        if (protection <= 0) {
            return;
        }

        BipedEntityModel<AbstractClientPlayerEntity> armorModel = usesInnerModel(slot) ? innerModel : outerModel;
        Identifier texture = getTexture(material, slot, Math.min(protection, MAX_PROTECTION_LEVEL));

        this.getContextModel().copyBipedStateTo(armorModel);
        setVisible(armorModel, slot);
        ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel, texture);
    }

    private static boolean usesInnerModel(EquipmentSlot slot) {
        return slot == EquipmentSlot.LEGS;
    }

    private static String getSupportedMaterial(ItemStack stack) {
        if (stack.isOf(Items.IRON_HELMET)
                || stack.isOf(Items.IRON_CHESTPLATE)
                || stack.isOf(Items.IRON_LEGGINGS)
                || stack.isOf(Items.IRON_BOOTS)) {
            return "iron";
        }

        if (stack.isOf(Items.DIAMOND_HELMET)
                || stack.isOf(Items.DIAMOND_CHESTPLATE)
                || stack.isOf(Items.DIAMOND_LEGGINGS)
                || stack.isOf(Items.DIAMOND_BOOTS)) {
            return "diamond";
        }

        return null;
    }

    private static Identifier getTexture(String material, EquipmentSlot slot, int protectionLevel) {
        int layer = slot == EquipmentSlot.LEGS ? 2 : 1;
        return Identifier.of(
                Visualizer.MOD_ID,
                "textures/models/armor/" + material + "_protection_" + protectionLevel + "_layer_" + layer + ".png"
        );
    }

    private static void setVisible(BipedEntityModel<?> model, EquipmentSlot slot) {
        model.setVisible(false);

        switch (slot) {
            case HEAD -> {
                model.head.visible = true;
                model.hat.visible = true;
            }
            case CHEST -> {
                model.body.visible = true;
                model.rightArm.visible = true;
                model.leftArm.visible = true;
            }
            case LEGS -> {
                model.body.visible = true;
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
            }
            case FEET -> {
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
            }
            default -> {
            }
        }
    }
}
