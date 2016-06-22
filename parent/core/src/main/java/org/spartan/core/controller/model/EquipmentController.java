package org.spartan.core.controller.model;

import org.spartan.cdi.event.Observes;
import org.spartan.cdi.scope.service.Service;
import org.spartan.core.event.item.ItemClicked;
import org.spartan.model.entity.item.EquipmentType;
import org.spartan.model.entity.item.Item;
import org.spartan.model.entity.player.Player;
import org.spartan.model.entity.player.ui.Equipment;
import org.spartan.model.entity.player.ui.Inventory;

@Service
public class EquipmentController {

	/**
	 * contains the logic about equiping items
	 * @param event
	 */
	public void on_equip(@Observes ItemClicked event, Player player) {
		try {
			/*
			 * Equipping an item. Checks the player's inventory to make sure the item exists
			 */
			if (event.getOption() == ItemClicked.ITEM_EQUIPPED && event.getInterfaceId() == Inventory.INTERFACE_ID) {
				Item item = player.getUserInterface().getInventory().get(event.getSlot());
				if (item.getId() == event.getItemId() && item.getDefinition().isWieldable()) {
					equip(item, item.getDefinition().getEquipmentType(), player.getUserInterface().getInventory(), player.getUserInterface().getEquipment(), event.getSlot());
				}
			}
	
			/*
			 * Unequipping an item. Checks the player's equipment to make sure the item exists.
			 */
			else if (event.getOption() == ItemClicked.ITEM_UNEQUIPPED && event.getInterfaceId() == Equipment.INTERFACE_ID) {
				Item item = player.getUserInterface().getEquipment().get(event.getSlot());
				if (item.getId() == event.getItemId()) {
					unequip(item, player.getUserInterface().getInventory(), player.getUserInterface().getEquipment());
				}
			}
		} catch (Exception ex) {
//			player.getOutputStream().write(new TextMessage(ex.getMessage()));
			ex.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param item
	 * @param inventory
	 * @param equipment
	 */
	private final void equip(Item item, EquipmentType type, Inventory inventory, Equipment equipment, int slot) {
		/*
		 * If the item is the same as the one wielded, add it to the stack of items
		 */
		if (item.getDefinition().isStackable() && equipment.get(type.getSlot()).getId() == item.getId()) {
			equipment.add(item);
		}
		
		/*
		 * Items need to be replaced
		 */
		else {
			/*
			 * If the weapon is 2h and the player is wearing something in the
			 * off-hand slot AND is wearing something in the main hand slot make
			 * sure there is room for an extra item to be unequiped
			 */
			if (item.getDefinition().isTwoHanded() && equipment.get(EquipmentType.WEAPON.getSlot()) != null
					&& equipment.get(EquipmentType.SHIELD.getSlot()) != null && !inventory.hasRemaining()) {
				throw new EquipException("You don't have enough free inventory space to do that.");
			}
			
			/*
			 * Remove the item from the player's inventory
			 */
			inventory.set(Item.NULL, slot);
			
			/*
			 * If an item is already equipped, remove it.
			 */
			if (equipment.get(type.getSlot()) != Item.NULL) {
				unequip(equipment.get(type.getSlot()), inventory, equipment);
			}
			
			/*
			 * If the player is attempting to wear a shield but is holding a 2 handed
			 * weapon, remove it from the player's equipment
			 */
			if (type == EquipmentType.SHIELD && equipment.get(EquipmentType.WEAPON.getSlot()).getDefinition().isTwoHanded()) {
				unequip(equipment.get(EquipmentType.WEAPON.getSlot()), inventory, equipment);
			}
			
			/*
			 * If the player is attempting to wear a shield but is holding a 2 handed
			 * weapon, remove it from the player's equipment
			 */
			if (equipment.get(EquipmentType.WEAPON.getSlot()).getDefinition().isTwoHanded() && equipment.get(EquipmentType.SHIELD.getSlot()) != null) {
				unequip(equipment.get(EquipmentType.SHIELD.getSlot()), inventory, equipment);
			}
			
			/*
			 * Set the equipment at the correct place
			 */
			equipment.set(item, type.getSlot());
		}
	}
	
	/**
	 * 
	 * @param item
	 */
	private final void unequip(Item item, Inventory inventory, Equipment equipment) {
		if (!inventory.hasRemaining())
			throw new EquipException("You don't have enough free inventory space to do that.");
		
		/*
		 * Add the item to the inventory
		 */
		inventory.add(item);
		
		/*
		 * Remove the item from the equipment
		 */
		equipment.set(Item.NULL, item.getDefinition().getEquipmentType().getSlot());
	}
	
	/**
	 * 
	 * @author brock
	 *
	 */
	@SuppressWarnings("serial")
	private static class EquipException extends RuntimeException {

		/**
		 * @param message
		 */
		public EquipException(String message) {
			super(message);
		}
		
	}

}
