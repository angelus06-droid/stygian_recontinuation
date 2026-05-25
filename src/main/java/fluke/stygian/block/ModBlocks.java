package fluke.stygian.block;

import fluke.stygian.CreativeTabStygian;
import fluke.stygian.block.fluid.ModBlockFluidClassic;
import fluke.stygian.block.fluid.ModFluids;
import fluke.stygian.block.item.*;
import fluke.stygian.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModBlocks {
	@GameRegistry.ObjectHolder(BlockEndLog.REG_NAME)
    public static BlockEndLog endLog;

	@GameRegistry.ObjectHolder(BlockEndPlanks.REG_NAME)
    public static BlockEndPlanks endPlanks;

	@GameRegistry.ObjectHolder(BlockEndLogStairs.REG_NAME)
	public static BlockEndLogStairs endLogStairs;

	@GameRegistry.ObjectHolder(BlockEndPlanksSlab.REG_NAME)
	public static BlockEndPlanksSlab endPlanksSlab;

	public static BlockEndPlanksSlab endPlanksDoubleSlab;

	@GameRegistry.ObjectHolder(BlockEndFence.REG_NAME)
	public static BlockEndFence endFence;

	@GameRegistry.ObjectHolder(BlockEndFenceGate.REG_NAME)
	public static BlockEndFenceGate endFenceGate;

	@GameRegistry.ObjectHolder(BlockEndDoor.REG_NAME)
	public static BlockEndDoor endDoor;

	public static Item endDoorItem;

	@GameRegistry.ObjectHolder(BlockEndAstroLog.REG_NAME)
	public static BlockEndAstroLog endAstroLog;

	@GameRegistry.ObjectHolder(BlockEndAstroPlanks.REG_NAME)
	public static BlockEndAstroPlanks endAstroPlanks;

	@GameRegistry.ObjectHolder(BlockEndAstroStairs.REG_NAME)
	public static BlockEndAstroStairs endAstroStairs;

	@GameRegistry.ObjectHolder(BlockEndAstroPlanksSlab.REG_NAME)
	public static BlockEndAstroPlanksSlab endAstroPlanksSlab;

	public static BlockEndAstroPlanksSlab endAstroPlanksDoubleSlab;

	@GameRegistry.ObjectHolder(BlockEndAstroFence.REG_NAME)
	public static BlockEndAstroFence endAstroFence;

	@GameRegistry.ObjectHolder(BlockEndAstroFenceGate.REG_NAME)
	public static BlockEndAstroFenceGate endAstroFenceGate;

	@GameRegistry.ObjectHolder(BlockEndAstroDoor.REG_NAME)
	public static BlockEndAstroDoor endAstroDoor;

	public static Item endAstroDoorItem;

	@GameRegistry.ObjectHolder(BlockEndViscousLog.REG_NAME)
	public static BlockEndViscousLog endViscousLog;

	@GameRegistry.ObjectHolder(BlockEndViscousPlanks.REG_NAME)
	public static BlockEndViscousPlanks endViscousPlanks;

	@GameRegistry.ObjectHolder(BlockEndViscousStairs.REG_NAME)
	public static BlockEndViscousStairs endViscousStairs;

	@GameRegistry.ObjectHolder(BlockEndViscousPlanksSlab.REG_NAME)
	public static BlockEndViscousPlanksSlab endViscousPlanksSlab;

	public static BlockEndViscousPlanksSlab endViscousPlanksDoubleSlab;

	@GameRegistry.ObjectHolder(BlockEndViscousFence.REG_NAME)
	public static BlockEndViscousFence endViscousFence;

	@GameRegistry.ObjectHolder(BlockEndViscousFenceGate.REG_NAME)
	public static BlockEndViscousFenceGate endViscousFenceGate;

	@GameRegistry.ObjectHolder(BlockEndViscousDoor.REG_NAME)
	public static BlockEndViscousDoor endViscousDoor;

	public static Item endViscousDoorItem;

	@GameRegistry.ObjectHolder(BlockEndLeaves.REG_NAME)
    public static BlockEndLeaves endLeaves;

	@GameRegistry.ObjectHolder(BlockEndAstroLeaves.REG_NAME)
	public static BlockEndAstroLeaves endAstroLeaves;

	@GameRegistry.ObjectHolder(BlockEndViscousLeaves.REG_NAME)
	public static BlockEndViscousLeaves endViscousLeaves;

	@GameRegistry.ObjectHolder(BlockEndGrass.REG_NAME)
    public static BlockEndGrass endGrass;

	@GameRegistry.ObjectHolder(BlockEndLunarGrass.REG_NAME)
    public static BlockEndLunarGrass endLunarGrass;

	@GameRegistry.ObjectHolder(BlockEndSnareGrass.REG_NAME)
	public static BlockEndSnareGrass endSnareGrass;

	@GameRegistry.ObjectHolder(BlockEndSand.REG_NAME)
	public static BlockEndSand endSand;

	@GameRegistry.ObjectHolder(ItemEndApple.REG_NAME)
	public static ItemEndApple endApple = new ItemEndApple();

	@GameRegistry.ObjectHolder(ItemEndObsidianBrick.REG_NAME)
	public static ItemEndObsidianBrick endObsidianBrick = new ItemEndObsidianBrick();

	@GameRegistry.ObjectHolder(ItemEndBoneMeal.REG_NAME)
	public static ItemEndBoneMeal endBoneMeal = new ItemEndBoneMeal();

	@GameRegistry.ObjectHolder(ItemEndBone.REG_NAME)
	public static ItemEndBone endBone = new ItemEndBone();

	@GameRegistry.ObjectHolder(ItemBlastlingBall.REG_NAME)
	public static ItemBlastlingBall endBlastlingBall = new ItemBlastlingBall();

	@GameRegistry.ObjectHolder(ItemViscousBall.REG_NAME)
	public static ItemViscousBall endViscousBall = new ItemViscousBall();

	@GameRegistry.ObjectHolder(ItemAncientEssence.REG_NAME)
	public static ItemAncientEssence endAncientEssence = new ItemAncientEssence();

	@GameRegistry.ObjectHolder(ItemLunarEssence.REG_NAME)
	public static ItemLunarEssence endLunarEssence = new ItemLunarEssence();

	@GameRegistry.ObjectHolder(ItemLunarFeather.REG_NAME)
	public static ItemLunarFeather endLunarFeather = new ItemLunarFeather();

	@GameRegistry.ObjectHolder(ItemLunarCrumbs.REG_NAME)
	public static ItemLunarCrumbs endLunarCrumbs = new ItemLunarCrumbs();

	@GameRegistry.ObjectHolder(ItemSupaikaBone.REG_NAME)
	public static ItemSupaikaBone endSupaikaBone = new ItemSupaikaBone();

	@GameRegistry.ObjectHolder(BlockEndTallGrass.REG_NAME)
	public static BlockEndTallGrass endTallGrass;

	@GameRegistry.ObjectHolder(BlockEndGlowPlant.REG_NAME)
    public static BlockEndGlowPlant endGlowPlant;

	@GameRegistry.ObjectHolder(BlockEndDragonOrchid.REG_NAME)
	public static BlockEndDragonOrchid endDragonOrchid;

	@GameRegistry.ObjectHolder(BlockEndWyrmRoot.REG_NAME)
	public static BlockEndWyrmRoot endWyrmRoots;

	@GameRegistry.ObjectHolder(BlockEndLunarTallGrass.REG_NAME)
	public static BlockEndLunarTallGrass endLunarTallGrass;

	@GameRegistry.ObjectHolder(BlockEndLightFlower.REG_NAME)
    public static BlockEndLightFlower endLightFlower;

	@GameRegistry.ObjectHolder(BlockEndMoonshine.REG_NAME)
	public static BlockEndMoonshine endMoonshine;

	@GameRegistry.ObjectHolder(BlockEndDeadGrass.REG_NAME)
	public static BlockEndDeadGrass endDeadGrass;

	@GameRegistry.ObjectHolder(BlockEndDeadBush.REG_NAME)
	public static BlockEndDeadBush endDeadBush;

	@GameRegistry.ObjectHolder(BlockEndAcidDeadBush.REG_NAME)
	public static BlockEndAcidDeadBush endAcidDeadBush;

	@GameRegistry.ObjectHolder(BlockEndDeadHangingRoot.REG_NAME)
	public static BlockEndDeadHangingRoot endDeadHangingRoot;

	@GameRegistry.ObjectHolder(BlockEndShiren.REG_NAME)
	public static BlockEndShiren endShiren;

	@GameRegistry.ObjectHolder(BlockEndSnareTallGrass.REG_NAME)
	public static BlockEndSnareTallGrass endSnareTallGrass;

    @GameRegistry.ObjectHolder(BlockEndSnareBells.REG_NAME)
    public static BlockEndSnareBells endSnareBells;

    @GameRegistry.ObjectHolder(BlockEndCell.REG_NAME)
    public static BlockEndCell endCell;

	@GameRegistry.ObjectHolder(BlockEndCanopySapling.REG_NAME)
    public static BlockEndCanopySapling endCanopySapling;

	@GameRegistry.ObjectHolder(BlockEndSmallCanopySapling.REG_NAME)
	public static BlockEndSmallCanopySapling endSmallCanopySapling;

	@GameRegistry.ObjectHolder(BlockEndAstroSapling.REG_NAME)
	public static BlockEndAstroSapling endAstroSapling;

	@GameRegistry.ObjectHolder(BlockEndViscousSapling.REG_NAME)
	public static BlockEndViscousSapling endViscousSapling;

	@GameRegistry.ObjectHolder(BlockEndVine.REG_NAME)
    public static BlockEndVine endVine;

	@GameRegistry.ObjectHolder(BlockEnderObsidian.REG_NAME)
    public static BlockEnderObsidian endObsidian;

	@GameRegistry.ObjectHolder(BlockEnderObsidianBrick.REG_NAME)
	public static BlockEnderObsidianBrick enderObsidianBrick;

	@GameRegistry.ObjectHolder(BlockEndDeadDirt.REG_NAME)
	public static BlockEndDeadDirt endDeadDirt;

	@GameRegistry.ObjectHolder(BlockEndDeadStone.REG_NAME)
	public static BlockEndDeadStone endDeadStone;

	@GameRegistry.ObjectHolder(BlockEndDeadCoalOre.REG_NAME)
	public static BlockEndDeadCoalOre endDeadCoalOre;

	@GameRegistry.ObjectHolder(BlockEndDeadCobblestone.REG_NAME)
	public static BlockEndDeadCobblestone endDeadCobblestone;

	@GameRegistry.ObjectHolder(BlockEndDeadStoneBricks.REG_NAME)
	public static BlockEndDeadStoneBricks endDeadStoneBricks;

	@GameRegistry.ObjectHolder(BlockEndMoonRock.REG_NAME)
	public static BlockEndMoonRock endMoonRock;

	@GameRegistry.ObjectHolder(BlockEndPolishedMoonRock.REG_NAME)
	public static BlockEndPolishedMoonRock endPolishedMoonRock;

	@GameRegistry.ObjectHolder(BlockEndMagma.REG_NAME)
    public static BlockEndMagma endMagma;

	@GameRegistry.ObjectHolder(BlockEndCactus.REG_NAME)
    public static BlockEndCactus endCactus;

	@GameRegistry.ObjectHolder(BlockEndBlueCactus.REG_NAME)
	public static BlockEndBlueCactus endBlueCactus;

	@GameRegistry.ObjectHolder("endacid")
    public static ModBlockFluidClassic endAcid;
	public static CreativeTabs STYGIAN_TAB = new CreativeTabStygian("stygian_tab");
	private static Object modelState;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		ModFluids.registerFluids();
		IForgeRegistry<Block> reggy = event.getRegistry();
		endLog = new BlockEndLog();
		reggy.register(endLog);
		endPlanks = new BlockEndPlanks();
		reggy.register(endPlanks);
		endLogStairs = new BlockEndLogStairs(endPlanks.getDefaultState());
		reggy.register(endLogStairs);
		endPlanksSlab = new BlockEndPlanksSlab(BlockEndPlanksSlab.REG_NAME) {
			@Override
			public boolean isDouble() { return false; }
		};
		reggy.register(endPlanksSlab);

		endPlanksDoubleSlab = new BlockEndPlanksSlab(BlockEndPlanksSlab.REG_NAME + "_double") {
			@Override
			public boolean isDouble() { return true; }
		};
		reggy.register(endPlanksDoubleSlab);
		endFence = new BlockEndFence();
		reggy.register(endFence);
		endFenceGate = new BlockEndFenceGate();
		reggy.register(endFenceGate);
		endDoor = new BlockEndDoor();
		reggy.register(endDoor);
		endAstroLog = new BlockEndAstroLog();
		reggy.register(endAstroLog);
		endAstroPlanks = new BlockEndAstroPlanks();
		reggy.register(endAstroPlanks);
		endAstroStairs = new BlockEndAstroStairs(endAstroPlanks.getDefaultState());
		reggy.register(endAstroStairs);
		endAstroPlanksSlab = new BlockEndAstroPlanksSlab(BlockEndAstroPlanksSlab.REG_NAME) {
			@Override
			public boolean isDouble() { return false; }
		};
		reggy.register(endAstroPlanksSlab);

		endAstroPlanksDoubleSlab = new BlockEndAstroPlanksSlab(BlockEndAstroPlanksSlab.REG_NAME + "_double") {
			@Override
			public boolean isDouble() { return true; }
		};
		reggy.register(endAstroPlanksDoubleSlab);
		endAstroFence = new BlockEndAstroFence();
		reggy.register(endAstroFence);
		endAstroFenceGate = new BlockEndAstroFenceGate();
		reggy.register(endAstroFenceGate);
		endAstroDoor = new BlockEndAstroDoor();
		reggy.register(endAstroDoor);
		endViscousLog = new BlockEndViscousLog();
		reggy.register(endViscousLog);
		endViscousPlanks = new BlockEndViscousPlanks();
		reggy.register(endViscousPlanks);
		endViscousStairs = new BlockEndViscousStairs(endViscousPlanks.getDefaultState());
		reggy.register(endViscousStairs);
		endViscousPlanksSlab = new BlockEndViscousPlanksSlab(BlockEndViscousPlanksSlab.REG_NAME) {
			@Override
			public boolean isDouble() { return false; }
		};
		reggy.register(endViscousPlanksSlab);

		endViscousPlanksDoubleSlab = new BlockEndViscousPlanksSlab(BlockEndViscousPlanksSlab.REG_NAME + "_double") {
			@Override
			public boolean isDouble() { return true; }
		};
		reggy.register(endViscousPlanksDoubleSlab);
		endViscousFence = new BlockEndViscousFence();
		reggy.register(endViscousFence);
		endViscousFenceGate = new BlockEndViscousFenceGate();
		reggy.register(endViscousFenceGate);
		endViscousDoor = new BlockEndViscousDoor();
		reggy.register(endViscousDoor);
		reggy.register(new BlockEndLeaves());
		reggy.register(new BlockEndAstroLeaves());
		reggy.register(new BlockEndViscousLeaves());
		reggy.register(new BlockEndGrass());
		reggy.register(new BlockEndLunarGrass());
		reggy.register(new BlockEndSnareGrass());
		reggy.register(new BlockEndSand());
		reggy.register(new BlockEndTallGrass());
		reggy.register(new BlockEndGlowPlant());
		reggy.register(new BlockEndDragonOrchid());
		reggy.register(new BlockEndWyrmRoot());
		reggy.register(new BlockEndLunarTallGrass());
		reggy.register(new BlockEndLightFlower());
		reggy.register(new BlockEndMoonshine());
		reggy.register(new BlockEndDeadGrass());
		reggy.register(new BlockEndDeadBush());
		reggy.register(new BlockEndAcidDeadBush());
		reggy.register(new BlockEndDeadHangingRoot());
		reggy.register(new BlockEndShiren());
		reggy.register(new BlockEndSnareTallGrass());
        reggy.register(new BlockEndSnareBells());
        reggy.register(new BlockEndCell());
		reggy.register(new BlockEndCanopySapling());
		reggy.register(new BlockEndSmallCanopySapling());
		reggy.register(new BlockEndAstroSapling());
		reggy.register(new BlockEndViscousSapling());
		reggy.register(new BlockEndVine());
		reggy.register(new BlockEnderObsidian());
		reggy.register(new BlockEnderObsidianBrick());
		reggy.register(new BlockEndDeadDirt());
		reggy.register(new BlockEndDeadStone());
		reggy.register(new BlockEndDeadCoalOre());
		reggy.register(new BlockEndDeadCobblestone());
		reggy.register(new BlockEndDeadStoneBricks());
		reggy.register(new BlockEndMoonRock());
		reggy.register(new BlockEndPolishedMoonRock());
		reggy.register(new BlockEndMagma());
		reggy.register(new BlockEndCactus());
		reggy.register(new BlockEndBlueCactus());
		reggy.register(new ModBlockFluidClassic(ModFluids.ACID, Material.WATER).setRegistryName("endacid").setTranslationKey("stygian:endacid"));// new MaterialLiquid(MapColor.PURPLE)).setRegistryName("endacid").setUnlocalizedName("stygian:endacid"));
	}

	@SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reggy = event.getRegistry();
		///Wyrmwood///
		Item slabItem = new net.minecraft.item.ItemSlab(endPlanksSlab, endPlanksSlab, endPlanksDoubleSlab)
				.setRegistryName(endPlanksSlab.getRegistryName());
		endDoorItem = new net.minecraft.item.ItemDoor(ModBlocks.endDoor)
				.setRegistryName(ModBlocks.endDoor.getRegistryName())
				.setTranslationKey(ModBlocks.endDoor.getTranslationKey());
		///
		///LunarGlow///
		Item astroSlabItem = new net.minecraft.item.ItemSlab(endAstroPlanksSlab, endAstroPlanksSlab, endAstroPlanksDoubleSlab)
				.setRegistryName(endAstroPlanksSlab.getRegistryName());
		endAstroDoorItem = new net.minecraft.item.ItemDoor(ModBlocks.endAstroDoor)
				.setRegistryName(ModBlocks.endAstroDoor.getRegistryName())
				.setTranslationKey(ModBlocks.endAstroDoor.getTranslationKey());
		///
		///Bacterial///
		Item viscousSlabItem = new net.minecraft.item.ItemSlab(endViscousPlanksSlab, endViscousPlanksSlab, endViscousPlanksDoubleSlab)
				.setRegistryName(endViscousPlanksSlab.getRegistryName());
		endViscousDoorItem = new net.minecraft.item.ItemDoor(ModBlocks.endViscousDoor)
				.setRegistryName(ModBlocks.endViscousDoor.getRegistryName())
				.setTranslationKey(ModBlocks.endViscousDoor.getTranslationKey());
		///
		Item[] itemsToRegister = {
				///Wyrmwood///
				new ItemBlock(ModBlocks.endLog).setRegistryName(ModBlocks.endLog.getRegistryName()),
				new ItemBlock(ModBlocks.endPlanks).setRegistryName(ModBlocks.endPlanks.getRegistryName()),
				new ItemBlock(ModBlocks.endLogStairs).setRegistryName(ModBlocks.endLogStairs.getRegistryName()),
				slabItem,
				new ItemBlock(ModBlocks.endFence).setRegistryName(ModBlocks.endFence.getRegistryName()),
				new ItemBlock(ModBlocks.endFenceGate).setRegistryName(ModBlocks.endFenceGate.getRegistryName()),
				endDoorItem,
				///
				///LunarGlow///
				new ItemBlock(ModBlocks.endAstroLog).setRegistryName(ModBlocks.endAstroLog.getRegistryName()),
				new ItemBlock(ModBlocks.endAstroPlanks).setRegistryName(ModBlocks.endAstroPlanks.getRegistryName()),
				new ItemBlock(ModBlocks.endAstroStairs).setRegistryName(ModBlocks.endAstroStairs.getRegistryName()),
				astroSlabItem,
				new ItemBlock(ModBlocks.endAstroFence).setRegistryName(ModBlocks.endAstroFence.getRegistryName()),
				new ItemBlock(ModBlocks.endAstroFenceGate).setRegistryName(ModBlocks.endAstroFenceGate.getRegistryName()),
				endAstroDoorItem,
				///
				///Bacterial///
				new ItemBlock(ModBlocks.endViscousLog).setRegistryName(ModBlocks.endViscousLog.getRegistryName()),
				new ItemBlock(ModBlocks.endViscousPlanks).setRegistryName(ModBlocks.endViscousPlanks.getRegistryName()),
				new ItemBlock(ModBlocks.endViscousStairs).setRegistryName(ModBlocks.endViscousStairs.getRegistryName()),
				viscousSlabItem,
				new ItemBlock(ModBlocks.endViscousFence).setRegistryName(ModBlocks.endViscousFence.getRegistryName()),
				new ItemBlock(ModBlocks.endViscousFenceGate).setRegistryName(ModBlocks.endViscousFenceGate.getRegistryName()),
				endViscousDoorItem,
				///
				new ItemBlock(ModBlocks.endLeaves).setRegistryName(ModBlocks.endLeaves.getRegistryName()),
				new ItemBlock(ModBlocks.endAstroLeaves).setRegistryName(ModBlocks.endAstroLeaves.getRegistryName()),
				new ItemBlock(ModBlocks.endViscousLeaves).setRegistryName(ModBlocks.endViscousLeaves.getRegistryName()),
				new ItemBlock(ModBlocks.endGrass).setRegistryName(ModBlocks.endGrass.getRegistryName()),
				new ItemBlock(ModBlocks.endLunarGrass).setRegistryName(ModBlocks.endLunarGrass.getRegistryName()),
				new ItemBlock(ModBlocks.endSnareGrass).setRegistryName(ModBlocks.endSnareGrass.getRegistryName()),
				new ItemBlock(ModBlocks.endSand).setRegistryName(ModBlocks.endSand.getRegistryName()),
				new ItemBlock(ModBlocks.endTallGrass).setRegistryName(ModBlocks.endTallGrass.getRegistryName()),
				new ItemBlock(ModBlocks.endGlowPlant).setRegistryName(ModBlocks.endGlowPlant.getRegistryName()),
				new ItemBlock(ModBlocks.endDragonOrchid).setRegistryName(ModBlocks.endDragonOrchid.getRegistryName()),
				new ItemBlock(ModBlocks.endWyrmRoots).setRegistryName(ModBlocks.endWyrmRoots.getRegistryName()),
				new ItemBlock(ModBlocks.endLunarTallGrass).setRegistryName(ModBlocks.endLunarTallGrass.getRegistryName()),
				new ItemBlock(ModBlocks.endLightFlower).setRegistryName(ModBlocks.endLightFlower.getRegistryName()),
				new ItemBlock(ModBlocks.endMoonshine).setRegistryName(ModBlocks.endMoonshine.getRegistryName()),
				new ItemBlock(ModBlocks.endDeadGrass).setRegistryName(ModBlocks.endDeadGrass.getRegistryName()),
				new ItemBlock(ModBlocks.endDeadBush).setRegistryName(ModBlocks.endDeadBush.getRegistryName()),
				new ItemBlock(ModBlocks.endAcidDeadBush).setRegistryName(ModBlocks.endAcidDeadBush.getRegistryName()),
				new ItemBlock(ModBlocks.endDeadHangingRoot).setRegistryName(ModBlocks.endDeadHangingRoot.getRegistryName()),
				new ItemBlock(ModBlocks.endShiren).setRegistryName(ModBlocks.endShiren.getRegistryName()),
				new ItemBlock(ModBlocks.endSnareTallGrass).setRegistryName(ModBlocks.endSnareTallGrass.getRegistryName()),
                new ItemBlock(ModBlocks.endSnareBells).setRegistryName(ModBlocks.endSnareBells.getRegistryName()),
                new ItemBlock(ModBlocks.endCell).setRegistryName(ModBlocks.endCell.getRegistryName()),
				new ItemBlock(ModBlocks.endCanopySapling).setRegistryName(ModBlocks.endCanopySapling.getRegistryName()),
				new ItemBlock(ModBlocks.endSmallCanopySapling).setRegistryName(ModBlocks.endSmallCanopySapling.getRegistryName()),
				new ItemBlock(ModBlocks.endAstroSapling).setRegistryName(ModBlocks.endAstroSapling.getRegistryName()),
				new ItemBlock(ModBlocks.endViscousSapling).setRegistryName(ModBlocks.endViscousSapling.getRegistryName()),
				new ItemBlock(ModBlocks.endVine).setRegistryName(ModBlocks.endVine.getRegistryName()),
				new ItemBlock(ModBlocks.endObsidian).setRegistryName(ModBlocks.endObsidian.getRegistryName()),
				new ItemBlock(ModBlocks.enderObsidianBrick).setRegistryName(ModBlocks.enderObsidianBrick.getRegistryName()),
				new ItemBlock(ModBlocks.endDeadDirt).setRegistryName(ModBlocks.endDeadDirt.getRegistryName()),
				new ItemBlock(ModBlocks.endDeadStone).setRegistryName(ModBlocks.endDeadStone.getRegistryName()),
				new ItemBlock(ModBlocks.endDeadCoalOre).setRegistryName(ModBlocks.endDeadCoalOre.getRegistryName()),
				new ItemBlock(ModBlocks.endDeadCobblestone).setRegistryName(ModBlocks.endDeadCobblestone.getRegistryName()),
				new ItemBlock(ModBlocks.endDeadStoneBricks).setRegistryName(ModBlocks.endDeadStoneBricks.getRegistryName()),
				new ItemBlock(ModBlocks.endMoonRock).setRegistryName(ModBlocks.endMoonRock.getRegistryName()),
				new ItemBlock(ModBlocks.endPolishedMoonRock).setRegistryName(ModBlocks.endPolishedMoonRock.getRegistryName()),
				new ItemBlock(ModBlocks.endMagma).setRegistryName(ModBlocks.endMagma.getRegistryName()),
				new ItemBlock(ModBlocks.endCactus).setRegistryName(ModBlocks.endCactus.getRegistryName()),
				new ItemBlock(ModBlocks.endBlueCactus).setRegistryName(ModBlocks.endBlueCactus.getRegistryName()),
				new ItemBlock(ModBlocks.endAcid).setRegistryName("endacid"),
				ModBlocks.endObsidianBrick,
				ModBlocks.endBoneMeal,
				ModBlocks.endBone,
				ModBlocks.endBlastlingBall,
				ModBlocks.endViscousBall,
				ModBlocks.endAncientEssence,
				ModBlocks.endLunarEssence,
				ModBlocks.endLunarFeather,
				ModBlocks.endApple,
				ModBlocks.endLunarCrumbs,
				ModBlocks.endSupaikaBone,
		};

		for (Item item : itemsToRegister) {
			reggy.register(item);
			if (item instanceof ItemBlock) {
				((ItemBlock) item).getBlock().setCreativeTab(STYGIAN_TAB);
			} else {
				item.setCreativeTab(STYGIAN_TAB);
			}
		}

		OreDictionary.registerOre("logWood", endLog);
		OreDictionary.registerOre("logWood", endAstroLog);
		OreDictionary.registerOre("logWood", endViscousLog);		
		OreDictionary.registerOre("plankWood", endPlanks);
		OreDictionary.registerOre("plankWood", endAstroPlanks);
		OreDictionary.registerOre("plankWood", endViscousPlanks);		
		OreDictionary.registerOre("treeLeaves", endLeaves);
		OreDictionary.registerOre("treeLeaves", endAstroLeaves);
		OreDictionary.registerOre("treeLeaves", endViscousLeaves);		
		OreDictionary.registerOre("cobblestone", endDeadCobblestone);
		OreDictionary.registerOre("dyeBlack", endBoneMeal);
		OreDictionary.registerOre("dye", endBoneMeal);
	}

	@SideOnly(Side.CLIENT)
    public static void initModels() {
		endLog.initModel();
		endPlanks.initModel();
		endLogStairs.initModel();
		endPlanksSlab.initModel();
		endFence.initModel();
		endFenceGate.initModel();
		endDoor.initModel();
		endAstroLog.initModel();
		endAstroPlanks.initModel();
		endAstroStairs.initModel();
		endAstroPlanksSlab.initModel();
		endAstroFence.initModel();
		endAstroFenceGate.initModel();
		endAstroDoor.initModel();
		endViscousLog.initModel();
		endViscousPlanks.initModel();
		endViscousStairs.initModel();
		endViscousPlanksSlab.initModel();
		endViscousFence.initModel();
		endViscousFenceGate.initModel();
		endViscousDoor.initModel();
		endLeaves.initModel();
		endAstroLeaves.initModel();
		endViscousLeaves.initModel();
		endGrass.initModel();
		endLunarGrass.initModel();
		endSnareGrass.initModel();
		endSand.initModel();
		endObsidian.initModel();
		enderObsidianBrick.initModel();
		endObsidianBrick.initModel();
		endBoneMeal.initModel();
		endBone.initModel();
		endBlastlingBall.initModel();
		endViscousBall.initModel();
		endAncientEssence.initModel();
		endLunarEssence.initModel();
		endLunarFeather.initModel();
		endApple.initModel();
		endLunarCrumbs.initModel();
		endSupaikaBone.initModel();
		endTallGrass.initModel();
		endGlowPlant.initModel();
		endDragonOrchid.initModel();
		endWyrmRoots.initModel();
		endLunarTallGrass.initModel();
		endLightFlower.initModel();
		endMoonshine.initModel();
		endDeadGrass.initModel();
		endDeadBush.initModel();
		endAcidDeadBush.initModel();
		endDeadHangingRoot.initModel();
		endShiren.initModel();
		endSnareTallGrass.initModel();
        endSnareBells.initModel();
        endCell.initModel();
		endCanopySapling.initModel();
		endSmallCanopySapling.initModel();
		endAstroSapling.initModel();
		endViscousSapling.initModel();
		endVine.initModel();
		endObsidian.initModel();
		endDeadDirt.initModel();
		endDeadStone.initModel();
		endDeadCoalOre.initModel();
		endDeadCobblestone.initModel();
		endDeadStoneBricks.initModel();
		endMoonRock.initModel();
		endPolishedMoonRock.initModel();
		endMagma.initModel();
		endCactus.initModel();
		endBlueCactus.initModel();
		endAcid.initModel();
	}
	@SideOnly(Side.CLIENT)
	public static void initCreativeTab() {
		STYGIAN_TAB = new CreativeTabStygian("stygian_tab");
	}
}

