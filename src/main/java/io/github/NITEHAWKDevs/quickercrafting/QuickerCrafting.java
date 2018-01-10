package io.github.NITEHAWKDevs.quickercrafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class QuickerCrafting extends JavaPlugin {

	@Override
	public void onEnable() {



	}

	@Override
	public void onDisable() {



	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(cmd.getName().equalsIgnoreCase("craft")) {

			if(!(sender instanceof Player)) {

				sender.sendMessage("This command is only usable by players!");
				return false;

			} else {

				Player player = (Player) sender;

				return craft(player, args);

			}

		}

		return false;

	}

	@SuppressWarnings({ "null", "unused" })
	private boolean craft(Player player, String[] args) {

		Inventory inv = player.getInventory();

		if(args.length < 2) {

			Material itemName = Material.getMaterial(Integer.parseInt(args[0]));

			if(itemName == null) {

				player.sendMessage(args[0] + " is not a valid item!");
				return false;

			} else {

				ItemStack finalItem = new ItemStack(itemName);

				List<Recipe> recipes = Bukkit.getRecipesFor(finalItem);

				if(recipes.size() == 0) {

					player.sendMessage(finalItem.getType() + " has no recipes!");
					return true;

				}

				for(int i = 0; i < recipes.size(); i++) {

					Boolean crafted = null;
					Map<ItemStack, Integer> items = new HashMap<ItemStack, Integer>();
					List<ItemStack> removed = new ArrayList<ItemStack>();

					Recipe recipe = recipes.get(i);

					if(recipe instanceof ShapedRecipe) {

						ShapedRecipe sr = (ShapedRecipe)recipe;


						for(ItemStack ingredient : sr.getIngredientMap().values()) {

							if(items.isEmpty() || items.keySet().contains(ingredient) == false) {

								items.put(ingredient, 1);

							} else {

								int amount = items.get(ingredient);

								amount += 1;

								items.put(ingredient, amount);
	
							}

						}

						for(ItemStack ingredient : items.keySet()) {

							if(inv.contains(ingredient.getTypeId())) {

								int totalAmount = 0;

								int returnAmount = 0;

								int neededAmount = items.get(ingredient);

								for(ItemStack invItem : inv.all(ingredient.getTypeId()).values()) {

									totalAmount += invItem.getAmount();
									inv.remove(invItem);

								}

								if(totalAmount >= neededAmount) {

									returnAmount = totalAmount - neededAmount;

									while(returnAmount > 64) {

										ItemStack returnItem = new ItemStack(ingredient.getType(), 64);

										inv.addItem(returnItem);

										returnAmount -= 64;

									}

									if(returnAmount > 0) {

										ItemStack returnItem = new ItemStack(ingredient.getType(), returnAmount);
										
										inv.addItem(returnItem);

										returnAmount = 0;

										crafted = true;

									}

									ItemStack removedItem = new ItemStack(ingredient.getType(), neededAmount);

									removed.add(removedItem);
									
									crafted = true;

								} else {

									crafted = false;
									break;

								}



							} else {

								crafted = false;
								break;

							}

						}

						if(crafted == true) {

							inv.addItem(finalItem);

							player.sendMessage("You crafted 1 " + finalItem.getType());

							break;

						} else {

							for(ItemStack item : removed) {

								inv.addItem(item);

							}

							player.sendMessage("You do not have the ingredients to craft " + finalItem.getType());
						}

					} else if(recipe instanceof ShapelessRecipe) {

						ShapelessRecipe shr = (ShapelessRecipe)recipe;

						for(ItemStack ingredient : shr.getIngredientList()) {

							if(items.isEmpty() || items.keySet().contains(ingredient) == false) {

								items.put(ingredient, 1);

							} else {

								int amount = items.get(ingredient);

								amount += 1;

								items.put(ingredient, amount);
	
							}

						}

						for(ItemStack ingredient : items.keySet()) {

							if(inv.contains(ingredient.getTypeId())) {

								int totalAmount = 0;

								int returnAmount = 0;

								int neededAmount = items.get(ingredient);

								for(ItemStack invItem : inv.all(ingredient.getTypeId()).values()) {

									totalAmount += invItem.getAmount();
									inv.remove(invItem);

								}

								if(totalAmount >= neededAmount) {

									returnAmount = totalAmount - neededAmount;

									while(returnAmount > 64) {

										ItemStack returnItem = new ItemStack(ingredient.getType(), 64);

										inv.addItem(returnItem);

										returnAmount -= 64;

									}

									if(returnAmount > 0) {

										ItemStack returnItem = new ItemStack(ingredient.getType(), returnAmount);
										
										inv.addItem(returnItem);

										returnAmount = 0;

										crafted = true;

									}

									ItemStack removedItem = new ItemStack(ingredient.getType(), neededAmount);

									removed.add(removedItem);
									
									crafted = true;

								} else {

									crafted = false;
									break;

								}



							} else {

								crafted = false;
								break;

							}

						}

						if(crafted == true) {

							inv.addItem(finalItem);

							player.sendMessage("You crafted 1 " + finalItem.getType());

							break;

						} else {

							for(ItemStack item : removed) {

								inv.addItem(item);

							}

							player.sendMessage("You do not have the ingredients to craft " + finalItem.getType());
						}

					} else if(recipe instanceof FurnaceRecipe) {

						player.sendMessage("Item must be crafted in a crafting table to use this command!");

					}

				}

				return true;

			}

		} else if(args.length < 1 | args.length > 2) {

			player.sendMessage("Too little or too many arguments! Please try again");
			return false;

		}

		return true;

	}

}
