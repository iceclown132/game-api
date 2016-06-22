package org.spartan.core.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.spartan.core.controller.model.EquipmentController;
import org.spartan.core.event.item.ItemClicked;
import org.spartan.model.entity.item.Item;
import org.spartan.model.entity.item.ItemDefinition;
import org.spartan.model.entity.player.Player;

public class EquipmentControllerTest {

	public static final int DAGGER = 1205;
	public static final int SHIELD = 1171;
	public static final int SWORD = 1307;
	public static final int WHIP = 4151;
	
	private final Player player = new Player();
	private final EquipmentController controller = new EquipmentController();

	@BeforeClass
	public static void initializeContainer() throws Exception {
		ItemDefinition.initialize(null);
	}
	
	@Test
	public void test_inventory() {
		System.out.println("-------- TESTING INVENTORY ---------");
		player.getUserInterface().getInventory().add(new Item(SWORD, 1));
		System.out.println(player.getUserInterface().getInventory());
		player.getUserInterface().getInventory().remove(new Item(SWORD, 1));
		System.out.println(player.getUserInterface().getInventory());
		
		player.getUserInterface().getInventory().clear();
	}
	
	@Test
	public void test_wield() {
		System.out.println("-------- TESTING BASIC WIELDING  ---------");
		player.getUserInterface().getInventory().add(new Item(SWORD, 1));
		player.getUserInterface().getInventory().add(new Item(SHIELD, 1));
		player.getUserInterface().getInventory().add(new Item(DAGGER, 1));
		
		System.out.println(">>>> BEFORE");
		System.out.println(player.getUserInterface().getInventory());
		System.out.println(player.getUserInterface().getEquipment());

		controller.on_equip(clickItem(DAGGER), player);
		controller.on_equip(clickItem(SHIELD), player);
		
		System.out.println(">>>> AFTER");
		System.out.println(player.getUserInterface().getInventory());
		System.out.println(player.getUserInterface().getEquipment());

		player.getUserInterface().getInventory().clear();
		player.getUserInterface().getEquipment().clear();
	}
	
	@Test
	public void test_wield_2h_then_shield() {
		System.out.println("-------- TESTING 2H SHIELD SWITCH  ---------");
		player.getUserInterface().getInventory().add(new Item(SWORD, 1));
		player.getUserInterface().getInventory().add(new Item(SHIELD, 1));
		player.getUserInterface().getInventory().add(new Item(DAGGER, 1));
		
		System.out.println(">>>> BEFORE");
		System.out.println(player.getUserInterface().getInventory());
		System.out.println(player.getUserInterface().getEquipment());

		controller.on_equip(clickItem(SWORD), player);
		controller.on_equip(clickItem(SHIELD), player);
		
		System.out.println(">>>> AFTER");
		System.out.println(player.getUserInterface().getInventory());
		System.out.println(player.getUserInterface().getEquipment());

		player.getUserInterface().getInventory().clear();
		player.getUserInterface().getEquipment().clear();
	}
	
	@Test
	public void test_wield_dual_2h_switch() {
		System.out.println("-------- TESTING DUAL WIELD INTO 2H SWITCH ---------");
		player.getUserInterface().getInventory().add(new Item(SWORD, 1));
		player.getUserInterface().getInventory().add(new Item(SHIELD, 1));
		player.getUserInterface().getInventory().add(new Item(DAGGER, 1));
		
		System.out.println(">>>> BEFORE");
		System.out.println(player.getUserInterface().getInventory());
		System.out.println(player.getUserInterface().getEquipment());

		controller.on_equip(clickItem(DAGGER), player);
		controller.on_equip(clickItem(SHIELD), player);
		
		controller.on_equip(clickItem(SWORD), player);
		
		System.out.println(">>>> AFTER");
		System.out.println(player.getUserInterface().getInventory());
		System.out.println(player.getUserInterface().getEquipment());

		player.getUserInterface().getInventory().clear();
		player.getUserInterface().getEquipment().clear();
	}
	
	@Test
	public void test_wield_dual_2h_switch_no_space() {
		System.out.println("-------- TESTING DUAL WIELD INTO SHIELD SWITCH NO INVENTORY SPACE ---------");
		player.getUserInterface().getInventory().add(new Item(SWORD, 1));
		player.getUserInterface().getInventory().add(new Item(SHIELD, 1));
		player.getUserInterface().getInventory().add(new Item(DAGGER, 1));
		
		System.out.println(">>>> BEFORE");
		System.out.println(player.getUserInterface().getInventory());
		System.out.println(player.getUserInterface().getEquipment());

		controller.on_equip(clickItem(DAGGER), player);
		controller.on_equip(clickItem(SHIELD), player);
		
		player.getUserInterface().getInventory().add(new Item(DAGGER, 5));
		
		controller.on_equip(clickItem(SWORD), player);
		
		System.out.println(">>>> AFTER");
		System.out.println(player.getUserInterface().getInventory());
		System.out.println(player.getUserInterface().getEquipment());

		player.getUserInterface().getInventory().clear();
		player.getUserInterface().getEquipment().clear();
	}

	private ItemClicked clickItem(int item) {
		ItemClicked event = new ItemClicked();
		event.setInterfaceId(3214);
		event.setOption(ItemClicked.ITEM_EQUIPPED);
		event.setSlot(player.getUserInterface().getInventory().indexOf(new Item(item, 1)));
		event.setItemId(item);
		return event;
	}

}
