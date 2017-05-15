package eyeq.lightspray;

import eyeq.lightspray.item.ItemLightSpray;
import eyeq.util.client.model.UModelCreator;
import eyeq.util.client.model.UModelLoader;
import eyeq.util.client.model.gson.ItemmodelJsonFactory;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.resource.ULanguageCreator;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import eyeq.util.oredict.UOreDictionary;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static eyeq.lightspray.LightSpray.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
@Mod.EventBusSubscriber
public class LightSpray {
    public static final String MOD_ID = "eyeq_lightspray";

    @Mod.Instance(MOD_ID)
    public static LightSpray instance;

    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static Item sprayEmpty;
    public static Item sprayRedstone;
    public static Item sprayBlaze;
    public static Item sprayGlowstone;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        addRecipes();
        if(event.getSide().isServer()) {
            return;
        }
        renderItemModels();
        createFiles();
    }

    @SubscribeEvent
    protected static void registerItems(RegistryEvent.Register<Item> event) {
        sprayEmpty = new Item().setUnlocalizedName("spratEmpty").setCreativeTab(CreativeTabs.TOOLS);
        sprayRedstone = new ItemLightSpray(7).setUnlocalizedName("sprayRedstone");
        sprayBlaze = new ItemLightSpray(14).setUnlocalizedName("sprayBlaze");
        sprayGlowstone = new ItemLightSpray(15).setUnlocalizedName("sprayGlowstone");

        GameRegistry.register(sprayEmpty, resource.createResourceLocation("spray_empty"));
        GameRegistry.register(sprayRedstone, resource.createResourceLocation("spray_redstone"));
        GameRegistry.register(sprayBlaze, resource.createResourceLocation("spray_blaze"));
        GameRegistry.register(sprayGlowstone, resource.createResourceLocation("spray_growstone"));
    }

    public static void addRecipeSpray(Item spray, Object input) {
        List<Object> inputs = new ArrayList<>();
        inputs.add(sprayEmpty);
        for(int i = 1; i < 9; i++) {
            inputs.add(input);
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(spray, 1, (8 - i) * 8), inputs.toArray(new Object[0])));
        }
        final int MAX = 64;
        for(int i = 0; i < MAX - 8; i++) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(spray, 1, i), new ItemStack(spray, 1, i + 8), input));
        }
        for(int i = 0; i < MAX - 16; i++) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(spray, 1, i), new ItemStack(spray, 1, i + 16), input, input));
        }
        for(int i = 0; i < MAX - 24; i++) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(spray, 1, i), new ItemStack(spray, 1, i + 24), input, input, input));
        }
        for(int i = 0; i < MAX - 32; i++) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(spray, 1, i), new ItemStack(spray, 1, i + 32), input, input, input, input));
        }
        for(int i = 0; i < MAX - 40; i++) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(spray, 1, i), new ItemStack(spray, 1, i + 40), input, input, input, input, input));
        }
        for(int i = 0; i < MAX - 48; i++) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(spray, 1, i), new ItemStack(spray, 1, i + 48), input, input, input, input, input, input));
        }
        for(int i = 0; i < MAX - 56; i++) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(spray, 1, i), new ItemStack(spray, 1, i + 56), input, input, input, input, input, input, input));
        }
    }

    public static void addRecipes() {
        GameRegistry.addRecipe(new ShapedOreRecipe((sprayEmpty), "IB", "II", "II",
                'I', UOreDictionary.OREDICT_IRON_INGOT, 'B', Blocks.STONE_BUTTON));
        addRecipeSpray(sprayRedstone, UOreDictionary.OREDICT_REDSTONE);
        addRecipeSpray(sprayBlaze, Items.BLAZE_POWDER);
        addRecipeSpray(sprayGlowstone, UOreDictionary.OREDICT_GLOWSTONE_DUST);
    }

    @SideOnly(Side.CLIENT)
    public static void renderItemModels() {
        UModelLoader.setCustomModelResourceLocation(sprayEmpty);
        UModelLoader.setCustomModelResourceLocation(sprayRedstone);
        UModelLoader.setCustomModelResourceLocation(sprayBlaze);
        UModelLoader.setCustomModelResourceLocation(sprayGlowstone);
    }

    public static void createFiles() {
        File project = new File("../1.11.2-LightSpray");

        LanguageResourceManager language = new LanguageResourceManager();

        language.register(LanguageResourceManager.EN_US, sprayEmpty, "Spray Can");
        language.register(LanguageResourceManager.JA_JP, sprayEmpty, "スプレー缶");
        language.register(LanguageResourceManager.EN_US, sprayRedstone, "Redstone Dust Spray");
        language.register(LanguageResourceManager.JA_JP, sprayRedstone, "レッドストーンダストスプレー");
        language.register(LanguageResourceManager.EN_US, sprayBlaze, "Blaze Powder Spray");
        language.register(LanguageResourceManager.JA_JP, sprayBlaze, "ブレイズパウダースプレー");
        language.register(LanguageResourceManager.EN_US, sprayGlowstone, "Glowstone Dust Spray");
        language.register(LanguageResourceManager.JA_JP, sprayGlowstone, "グロウストーンスプレー");

        ULanguageCreator.createLanguage(project, MOD_ID, language);

        UModelCreator.createItemJson(project, sprayEmpty, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, sprayRedstone, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, sprayBlaze, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, sprayGlowstone, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
    }
}
