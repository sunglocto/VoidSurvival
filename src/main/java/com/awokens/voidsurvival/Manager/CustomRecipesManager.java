package com.awokens.voidsurvival.Manager;

import com.awokens.voidsurvival.VoidSurvival;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.buttons.SGButtonListener;
import com.samjakob.spigui.menu.SGMenu;
import io.papermc.paper.potion.PotionMix;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomRecipesManager {

    private final VoidSurvival plugin;
    private final Map<NamespacedKey, ShapedRecipe> recipes;

    private final Map<ItemStack, List<Material>> craftIngredients;

    private final Map<ItemStack, Material> furnaceIngredients;

    private final Map<ItemStack, Material> potionMixIngreidnets;

    private final SGMenu RecipeMenu;

    public CustomRecipesManager(VoidSurvival plugin) {
        this.plugin = plugin;
        this.recipes = new HashMap<>();
        this.craftIngredients = new HashMap<>();
        this.furnaceIngredients = new HashMap<>();
        this.potionMixIngreidnets = new HashMap<>();
        NamespacedKey key = new NamespacedKey(plugin, "potion_of_luck");

        ItemStack luckPotion = new ItemStack(Material.POTION);
        PotionMeta luckPotionItemMeta = (PotionMeta) luckPotion.getItemMeta();
        luckPotionItemMeta.setBasePotionType(PotionType.LUCK);
        luckPotion.setItemMeta(luckPotionItemMeta);

        ItemStack awkwardPotion = new ItemStack(Material.POTION);
        PotionMeta awkwardPotionItemMeta = (PotionMeta) awkwardPotion.getItemMeta();
        awkwardPotionItemMeta.setBasePotionType(PotionType.AWKWARD);
        awkwardPotion.setItemMeta(awkwardPotionItemMeta);

        RecipeChoice input = new RecipeChoice.ExactChoice(awkwardPotion);
        RecipeChoice ingredient = new RecipeChoice.MaterialChoice(Material.TROPICAL_FISH);

        PotionMix potionMix = new PotionMix(key,
            luckPotion, input, ingredient
        );
        potionMixIngreidnets.put(luckPotion, Material.TROPICAL_FISH);
        Bukkit.getPotionBrewer().addPotionMix(potionMix);

        addFurnaceRecipe("steak", new ItemStack(Material.COOKED_BEEF), Material.ROTTEN_FLESH, 1.0f, 140);
        addFurnaceRecipe("golden_helmet_nuggets", new ItemStack(Material.GOLD_NUGGET, 5), Material.GOLDEN_HELMET, 1.0F, 140);
        addFurnaceRecipe("golden_chestplate_nuggets", new ItemStack(Material.GOLD_NUGGET, 8), Material.GOLDEN_CHESTPLATE, 1.0F, 140);
        addFurnaceRecipe("golden_leggings_nuggets", new ItemStack(Material.GOLD_NUGGET, 7), Material.GOLDEN_LEGGINGS, 1.0F, 140);
        addFurnaceRecipe("golden_boots_nuggets", new ItemStack(Material.GOLD_NUGGET, 4), Material.GOLDEN_BOOTS, 1.0F, 140);
        addFurnaceRecipe("blackstone", new ItemStack(Material.BLACKSTONE), Material.COBBLED_DEEPSLATE, 1.0F, 280);


        Bukkit.removeRecipe(new NamespacedKey("minecraft", "ender_eye"));

        addShapedRecipe("ender_eye",
                new ItemStack(Material.ENDER_EYE),
                List.of(
                        Material.AIR, Material.AIR, Material.BLAZE_POWDER,
                        Material.AIR, Material.HEART_OF_THE_SEA, Material.AIR,
                        Material.ENDER_PEARL, Material.AIR, Material.AIR
                ));

        addShapedRecipe("end_portal_frame",
                new ItemStack(Material.END_PORTAL_FRAME),
                List.of(
                        Material.PRISMARINE_SHARD, Material.PRISMARINE_SHARD, Material.PRISMARINE_SHARD,
                        Material.OXIDIZED_COPPER, Material.REINFORCED_DEEPSLATE, Material.OXIDIZED_COPPER,
                        Material.REINFORCED_DEEPSLATE,  Material.OXIDIZED_COPPER, Material.REINFORCED_DEEPSLATE
                ));

        addShapedRecipe("reinforced_deepslate",
                new ItemStack(Material.REINFORCED_DEEPSLATE),
                List.of(
                        Material.IRON_INGOT, Material.COBBLED_DEEPSLATE, Material.IRON_INGOT,
                        Material.IRON_INGOT, Material.COBBLED_DEEPSLATE, Material.IRON_INGOT,
                        Material.IRON_INGOT, Material.COBBLED_DEEPSLATE, Material.IRON_INGOT
                ));

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
                        Material.GOLD_BLOCK, Material.APPLE, Material.GOLD_BLOCK,
                        Material.GOLD_BLOCK, Material.GOLD_BLOCK, Material.GOLD_BLOCK
                ));

        addShapelessRecipe("string_shapeless",
                new ItemStack(Material.STRING, 4),
                List.of(Material.WHITE_WOOL));
        addShapedRecipe("string",
                new ItemStack(Material.STRING, 4),
                List.of(
                        Material.WHITE_WOOL, Material.AIR, Material.AIR,
                        Material.AIR, Material.AIR, Material.AIR,
                        Material.AIR, Material.AIR, Material.AIR
                ));

        addShapedRecipe("soul_soil",
                new ItemStack(Material.SOUL_SOIL),
                List.of(
                        Material.COARSE_DIRT, Material.COARSE_DIRT, Material.COARSE_DIRT,
                        Material.SOUL_SAND, Material.MUD, Material.COARSE_DIRT,
                        Material.MUD, Material.SOUL_SAND, Material.COARSE_DIRT
                ));

        addShapelessRecipe("tuff_reversed",
                new ItemStack(Material.TUFF, 9),
                List.of(Material.COBBLED_DEEPSLATE));

        addShapelessRecipe("cobblestone_reversed",
                new ItemStack(Material.COBBLESTONE, 9),
                List.of(Material.TUFF));


        SGMenu furnaceMenu = plugin.spiGUI().create("&8Furnace Recipes", 5);
        SGMenu craftingMenu = plugin.spiGUI().create("&8Crafting Recipes", 5);
        SGMenu potionMenu = plugin.spiGUI().create("&8Potion Recipes", 5);


        ItemStack item = new ItemStack(Material.RED_DYE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(MiniMessage.miniMessage().deserialize(
                "<red>Go back"
        ).decoration(TextDecoration.ITALIC, false));

        item.setItemMeta(meta);

        SGButtonListener listener = event -> {
            Player player = (Player) event.getWhoClicked();
            player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 1.0F, 1.0F);
            player.openInventory(this.getRecipeMenu());
        };

        SGButton back = new SGButton(item).withListener(listener);

        this.RecipeMenu = plugin.spiGUI().create("&8Recipes", 3);

        for (ItemStack result : craftIngredients.keySet()) {
            List<Material> materials = craftIngredients.get(result);

            SGButtonListener revealRecipe = event -> {
                // open new gui with the recipe of the result

                int[] craftingSlots = { 10, 11, 12, 19, 20, 21, 28, 29, 30 };
                SGMenu revealRevealMenu = plugin.spiGUI().create("&8Crafting Recipe", 5);

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

                revealRevealMenu.setButton(40, back);
                revealRevealMenu.setButton(24, new SGButton(result));

                Player player = (Player) event.getWhoClicked();
                player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 1.0F, 1.0F);
                player.openInventory(revealRevealMenu.getInventory());
            };
            SGButton button = new SGButton(result).withListener(revealRecipe);
            craftingMenu.addButton(button);
        }
        for (ItemStack result : furnaceIngredients.keySet()) {
            Material material = furnaceIngredients.get(result);

            SGButtonListener revealRecipe = event -> {

                int[] furnaceSlots = { 10, 11, 12, 19, 20, 21, 28, 29, 30 };
                SGMenu revealRevealMenu = plugin.spiGUI().create("&8Furnace Recipe", 5);

                for (int i = 0; i < 45; i++) {
                    revealRevealMenu.setButton(i, new SGButton(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
                }

                for (int furnaceSlot : furnaceSlots) {
                    revealRevealMenu.setButton(furnaceSlot, new SGButton(new ItemStack(Material.GRAY_STAINED_GLASS_PANE)));
                }

                revealRevealMenu.setButton(40, back);
                revealRevealMenu.setButton(24, new SGButton(result));
                revealRevealMenu.setButton(20, new SGButton(new ItemStack(material)));

                Player player = (Player) event.getWhoClicked();
                player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 1.0F, 1.0F);
                player.openInventory(revealRevealMenu.getInventory());
            };
            SGButton button = new SGButton(result).withListener(revealRecipe);
            furnaceMenu.addButton(button);
        }
        for (ItemStack result : potionMixIngreidnets.keySet()) {
            Material material  = potionMixIngreidnets.get(result);


            SGButtonListener revealRecipe = event -> {

                int[] foregroundSlots = { 10, 19, 28 };
                SGMenu revealRevealMenu = plugin.spiGUI().create("&8Potion Recipe", 5);

                for (int i = 0; i < 45; i++) {
                    revealRevealMenu.setButton(i, new SGButton(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
                }

                for (int foregroundSlot : foregroundSlots) {
                    for (int i = foregroundSlot; i < foregroundSlot + 7; i++) {
                        revealRevealMenu.setButton(i, new SGButton(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
                    }
                }

                revealRevealMenu.setButton(40, back);
                revealRevealMenu.setButton(20, new SGButton(awkwardPotion));
                revealRevealMenu.setButton(24, new SGButton(result));
                revealRevealMenu.setButton(22, new SGButton(new ItemStack(material)));

                Player player = (Player) event.getWhoClicked();
                player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 1.0F, 1.0F);

                player.openInventory(revealRevealMenu.getInventory());
            };
            SGButton button = new SGButton(result).withListener(revealRecipe);
            potionMenu.addButton(button);
        }

        ItemStack furnace = new ItemStack(Material.FURNACE);
        ItemMeta furnaceMeta = furnace.getItemMeta();
        furnaceMeta.displayName(MiniMessage.miniMessage().deserialize(
                "<yellow>View Furnace Recipes"
        ).decoration(TextDecoration.ITALIC, false));
        furnace.setItemMeta(furnaceMeta);

        ItemStack brewingStand = new ItemStack(Material.BREWING_STAND);
        ItemMeta brewingStandItemMeta = brewingStand.getItemMeta();
        brewingStandItemMeta.displayName(MiniMessage.miniMessage().deserialize(
                "<yellow>View Potion Recipes"
        ).decoration(TextDecoration.ITALIC, false));
        brewingStand.setItemMeta(brewingStandItemMeta);

        ItemStack craftingTable = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta craftingTableMeta = craftingTable.getItemMeta();
        craftingTableMeta.displayName(MiniMessage.miniMessage().deserialize(
                "<yellow>View Crafting Recipes"
        ).decoration(TextDecoration.ITALIC, false));
        craftingTable.setItemMeta(craftingTableMeta);



        this.RecipeMenu.setButton(12, new SGButton(craftingTable)
                .withListener(event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 1.0F, 1.0F);
                    player.openInventory(craftingMenu.getInventory());
                }));
        this.RecipeMenu.setButton(13, new SGButton(brewingStand)
                .withListener(event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 1.0F, 1.0F);
                    player.openInventory(potionMenu.getInventory());
                }));
        this.RecipeMenu.setButton(14, new SGButton(furnace)
                .withListener(event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 1.0F, 1.0F);
                    player.openInventory(furnaceMenu.getInventory());
                }));
        craftingMenu.setButton(40, back);
        potionMenu.setButton(40, back);
        furnaceMenu.setButton(40, back);

    }

    public Inventory getRecipeMenu() {
        return this.RecipeMenu.getInventory();
    }


    public void addShapedRecipe(String keyName, ItemStack result, List<Material> materials) {


        if (materials.size() != 9) {
            throw new RuntimeException("This shaped recipe " + keyName + " requires 9 materials, but only found " + materials.size());
        }

        craftIngredients.put(result, materials);

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

    @SuppressWarnings("unused")
    public void addFurnaceRecipe(String keyName, ItemStack result, Material input, float experience, int cookingTime) {

        furnaceIngredients.put(result, input);
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        FurnaceRecipe recipe = new FurnaceRecipe(key, result, input, experience, cookingTime);
        Bukkit.addRecipe(recipe);
    }

    public void addShapelessRecipe(String keyName, ItemStack result, List<Material> materials) {

        if (materials.size() > 9) {
            throw new RuntimeException("This shapeless recipe " + keyName + " requires 9 materials, but only found " + materials.size());
        }

        NamespacedKey key = new NamespacedKey(plugin, keyName);

        // Create and configure ShapelessRecipe
        ShapelessRecipe recipe = new ShapelessRecipe(key, result);

        for (Material material : materials) {
            recipe.addIngredient(material);
        }

        // Register recipe
        Bukkit.addRecipe(recipe);
    }

    @SuppressWarnings("unused")
    public void removeRecipe(String keyName) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        ShapedRecipe removedRecipe = recipes.remove(key);
        if (removedRecipe != null) {
            Bukkit.removeRecipe(key);
        }
    }
}
