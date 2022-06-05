package com.aizistral.onetimeloot;

import com.aizistral.onetimeloot.handlers.ConfigHandler;
import com.aizistral.onetimeloot.handlers.LootEventHandler;
import com.aizistral.onetimeloot.handlers.OneTimeLootModifier;
import com.aizistral.onetimeloot.handlers.SuperpositionHandler;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;

@Mod(OneTimeLoot.MODID)
public class OneTimeLoot {
	public static final String MODID = "onetimeloot";

	public OneTimeLoot() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onLoadComplete);

		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new LootEventHandler());

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.getConfig(), "OneTimeLoot.toml");
	}

	private void onLoadComplete(FMLLoadCompleteEvent event) {
		ConfigHandler.getLootList();
	}

	private void setup(FMLCommonSetupEvent event) {
		// NO-OP
	}

	private void doClientStuff(FMLClientSetupEvent event) {
		// NO-OP
	}

	private void enqueueIMC(InterModEnqueueEvent event) {
		// NO-OP
	}

	private void processIMC(InterModProcessEvent event) {
		// NO-OP
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MODID)
	public static class RegistryEvents {

		@SubscribeEvent
		public static void registerLootModifiers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
			event.getRegistry().register(new OneTimeLootModifier.Serializer().setRegistryName(new ResourceLocation(MODID, "one_time_loot_modifier")));
		}

		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			// NO-OP
		}

		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			// NO-OP
		}

	}

}
