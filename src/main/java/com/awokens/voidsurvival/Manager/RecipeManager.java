package com.awokens.voidsurvival.Manager;

import com.awokens.voidsurvival.VoidSurvival;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.buttons.SGButtonListener;
import com.samjakob.spigui.menu.SGMenu;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeManager {

    private final Plugin plugin;
    private final Map<NamespacedKey, ShapedRecipe> recipes;

    private final Map<ItemStack, List<Material>> ingredients;

    private SGMenu RecipeMenu;

    public RecipeManager(Plugin plugin) {
        this.plugin = plugin;
        this.recipes = new HashMap<>();
        this.ingredients = new HashMap<>();

        addShapedRecipe("tuff",
                new ItemStack(Material.TUFF),
                List.of(
                        Material.COBBLESTONE, Material.COBBLESTONE, Material.COBBLESTONE,
                        Material.COBBLESTONE, Material.COBBLESTONE, Material.COBBLESTONE,
                        Material.COBBLESTONE, Material.COBBLESTONE,Material.COBBLESTONE
                ));
        addShapedRecipe("cobbled_deepslate",
                new ItemStack(Material.COBBLED_DEEPSLATE),
                List.of(
                        Material.TUFF, Material.TUFF, Material.TUFF,
                        Material.TUFF, Material.TUFF, Material.TUFF,
                        Material.TUFF, Material.TUFF, Material.TUFF
                ));

        addShapedRecipe("bundle",
                new ItemStack(Material.BUNDLE),
                List.of(
                        Material.LEATHER, Material.STRING, Material.LEATHER,
                        Material.LEATHER, Material.STRING, Material.LEATHER,
                        Material.STRING, Material.LEATHER, Material.STRING
                ));

        addShapedRecipe("enchanted_golden_apple",
                new ItemStack(Material.ENCHANTED_GOLDEN_APPLE),
                List.of(
                        Material.GOLD_BLOCK, Material.GOLD_BLOCK, Material.GOLD_BLOCK,
                        Material.GOLD_BLOCK, Material.GOLD_BLOCK, Material.GOLD_BLOCK,
                        Material.GOLD_BLOCK, Material.GOLD_BLOCK, Material.GOLD_BLOCK
                ));

        this.RecipeMenu = VoidSurvival.getSpiGUI().create("&8Recipes", 6);

        for (ItemStack result : ingredients.keySet()) {
            List<Material> materials = ingredients.get(result);

            SGButtonListener revealRecipe = event -> {
                // open new gui with the recipe of the result

                int[] craftingSlots = { 10, 11, 12, 19, 20, 21, 28, 29, 30 };
                SGMenu revealRevealMenu = VoidSurvival.getSpiGUI().create("&8Recipe", 5);

                for (int i = 0; i < 45; i++) {
                    revealRevealMenu.setButton(i, new SGButton(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
                }
                
                for (int i = 0; i < materials.size(); i++) {
                    revealRevealMenu.setButton(
                            craftingSlots[i],
                            new SGButton(new ItemStack(materials.get(i)))
                    );
                    revealRevealMenu.setButton(craftingSlots[i] + 4, new SGButton(new ItemStack(Material.GRAY_STAINED_GLASS_PANE)));
                }

                ItemStack item = new ItemStack(Material.RED_DYE);
                ItemMeta meta = item.getItemMeta();
                meta.displayName(MiniMessage.miniMessage().deserialize(
                        "<red>Go back"
                ).decoration(TextDecoration.ITALIC, false));

                item.setItemMeta(meta);

                SGButtonListener listener = event1 -> {
                    event1.getWhoClicked().openInventory(this.getRecipeMenu());
                };

                SGButton back = new SGButton(item).withListener(listener);

                revealRevealMenu.setButton(40, back);
                revealRevealMenu.setButton(24, new SGButton(result));

                event.getWhoClicked().openInventory(revealRevealMenu.getInventory());
            };
            SGButton button = new SGButton(result).withListener(revealRecipe);
            this.RecipeMenu.addButton(button);
        }
    }

    public Inventory getRecipeMenu() {
        return this.RecipeMenu.getInventory();
    }

    public Map<NamespacedKey, ShapedRecipe> getRecipes() {
        return recipes;
    }

    public void addShapedRecipe(String keyName, ItemStack result, List<Material> materials) {


        if (materials.size() != 9) {
            throw new RuntimeException("This shaped recipe " + keyName + " requires 9 materials, but only found " + materials.size());
        }

        ingredients.put(result, materials);

        NamespacedKey key = new NamespacedKey(plugin, keyName);

        // Create and configure ShapedRecipe
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        // Assign characters to materials and reuse characters for the same material
        Map<Material, Character> materialMap = new HashMap<>();
        StringBuilder shapeBuilder = new StringBuilder();
        char[] keys = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I' };
        int index = 0;
        for (Material material : materials) {
            Character keyChar = materialMap.get(material);
            if (keyChar == null) {
                keyChar = keys[index++];
                materialMap.put(material, keyChar);
            }
            shapeBuilder.append(keyChar);
        }

        // Set the shape
        String shapeString = shapeBuilder.toString();
        int width = 1;
        int height = shapeString.length();
        if (height > 3) {
            width = (int) Math.ceil((double) height / 3);
        }
        String[] shape = new String[height / width];
        for (int i = 0; i < shape.length; i++) {
            shape[i] = shapeString.substring(i * width, Math.min((i + 1) * width, shapeString.length()));
        }
        recipe.shape(shape);

        // Set ingredients
        for (Map.Entry<Material, Character> entry : materialMap.entrySet()) {
            recipe.setIngredient(entry.getValue(), entry.getKey());
        }

        // Register recipe
        recipes.put(key, recipe);
        Bukkit.addRecipe(recipe);
    }

    public void addShapelessRecipe(String keyName, ItemStack result, List<Material> materials) {

        NamespacedKey key = new NamespacedKey(plugin, keyName);

        // Create and configure ShapelessRecipe
        ShapelessRecipe recipe = new ShapelessRecipe(key, result);

        // Assign characters to materials and reuse characters for the same material
        Map<Material, Integer> materialMap = new HashMap<>();
        for (Material material : materials) {
            Integer count = materialMap.get(material);
            if (count == null) {
                count = 1;
            } else {
                count++;
            }
            materialMap.put(material, count);
            recipe.addIngredient(count, material);
        }

        // Register recipe
        Bukkit.addRecipe(recipe);
    }

    public void removeRecipe(String keyName) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        ShapedRecipe removedRecipe = recipes.remove(key);
        if (removedRecipe != null) {
            Bukkit.removeRecipe(key);
        }
    }
}
