package ballistix.registers;

import static ballistix.registers.BallistixBlocks.blockMissileSilo;
import static ballistix.registers.UnifiedBallistixRegister.getSafeBlock;
import static electrodynamics.registers.UnifiedElectrodynamicsRegister.supplier;

import java.util.HashMap;

import ballistix.References;
import ballistix.common.block.subtype.SubtypeBlast;
import ballistix.common.item.ItemDefuser;
import ballistix.common.item.ItemGrenade;
import ballistix.common.item.ItemLaserDesignator;
import ballistix.common.item.ItemMinecart;
import ballistix.common.item.ItemRadarGun;
import ballistix.common.item.ItemRocketLauncher;
import ballistix.common.item.ItemScanner;
import ballistix.common.item.ItemTracker;
import electrodynamics.api.ISubtype;
import electrodynamics.common.blockitem.BlockItemDescriptable;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BallistixItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.ID);

	public static final HashMap<ISubtype, RegistryObject<Item>> SUBTYPEITEMREGISTER_MAPPINGS = new HashMap<>();
	public static final HashMap<ISubtype, RegistryObject<Item>> SUBTYPEMINECARTMAPPINGS = new HashMap<>();

	public static final RegistryObject<Item> ITEM_DUSTPOISON = ITEMS.register("dustpoison", supplier(() -> new Item(new Item.Properties().tab(References.BALLISTIXTAB))));
	public static final RegistryObject<Item> ITEM_MISSILECLOSERANGE = ITEMS.register("missilecloserange", supplier(() -> new Item(new Item.Properties().tab(References.BALLISTIXTAB))));
	public static final RegistryObject<Item> ITEM_MISSILEMEDIUMRANGE = ITEMS.register("missilemediumrange", supplier(() -> new Item(new Item.Properties().tab(References.BALLISTIXTAB))));
	public static final RegistryObject<Item> ITEM_MISSILELONGRANGE = ITEMS.register("missilelongrange", supplier(() -> new Item(new Item.Properties().tab(References.BALLISTIXTAB))));
	public static final RegistryObject<Item> ITEM_ROCKETLAUNCHER = ITEMS.register("rocketlauncher", supplier(ItemRocketLauncher::new));
	public static final RegistryObject<Item> ITEM_RADARGUN = ITEMS.register("radargun", supplier(ItemRadarGun::new));
	public static final RegistryObject<Item> ITEM_TRACKER = ITEMS.register("tracker", supplier(ItemTracker::new));
	public static final RegistryObject<Item> ITEM_SCANNER = ITEMS.register("scanner", supplier(ItemScanner::new));
	public static final RegistryObject<Item> ITEM_LASERDESIGNATOR = ITEMS.register("laserdesignator", supplier(ItemLaserDesignator::new));
	public static final RegistryObject<Item> ITEM_DEFUSER = ITEMS.register("defuser", supplier(ItemDefuser::new));

	static {
		for (SubtypeBlast subtype : SubtypeBlast.values()) {
			ITEMS.register(subtype.tag(), supplier(() -> new BlockItemDescriptable(() -> getSafeBlock(subtype), new Item.Properties().tab(References.BALLISTIXTAB)), subtype));
		}
		for (SubtypeBlast subtype : SubtypeBlast.values()) {
			if (subtype.hasGrenade) {
				SUBTYPEITEMREGISTER_MAPPINGS.put(subtype, ITEMS.register("grenade" + subtype.tag(), supplier(() -> new ItemGrenade(subtype), subtype)));
			}
		}
		for (SubtypeBlast subtype : SubtypeBlast.values()) {
			if (subtype.hasMinecart) {
				SUBTYPEMINECARTMAPPINGS.put(subtype, ITEMS.register("minecart" + subtype.tag(), supplier(() -> new ItemMinecart(subtype))));
			}
		}
		ITEMS.register("missilesilo", supplier(() -> new BlockItemDescriptable(() -> blockMissileSilo, new Item.Properties().tab(References.BALLISTIXTAB))));
	}
}
