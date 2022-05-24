package projecthds.advtweakery;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import zmaster587.libVulpes.interfaces.IRecipe;
import zmaster587.libVulpes.recipe.RecipesMachine;
import zmaster587.libVulpes.recipe.RecipesMachine.ChanceFluidStack;
import zmaster587.libVulpes.recipe.RecipesMachine.ChanceItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Utils {

  public static ChanceItemStack toChanceStack(ItemStack stack) {
    return new ChanceItemStack(stack, 1.0f);
  }

  public static ChanceFluidStack toChanceStack(FluidStack stack) {
    return new ChanceFluidStack(stack, 1.0f);
  }

  public static HashMap<String, List<?>> inputConvert(Object[] inputs) {
    return ingredientProcess(inputs);
  }

  public static HashMap<String, List<?>> outputConvert(Object[] outputs) {
    return ingredientProcess(outputs);
  }

  public static Object[] mapExtract(HashMap<String, List<?>> inMap) {
    List<ChanceItemStack> items = (List<ChanceItemStack>) inMap.get("items");
    List<ChanceFluidStack> fluids = (List<ChanceFluidStack>) inMap.get("fluids");
    List<String> oreDicts = (List<String>) inMap.get("ores");
    List<Object> objects = new LinkedList<>();
    objects.addAll(items);
    objects.addAll(fluids);
    objects.addAll(oreDicts);
    return objects.toArray();
  }

  private static HashMap<String, List<?>> ingredientProcess(Object[] outputs) {
    List<ChanceItemStack> items = new LinkedList<>();
    List<ChanceFluidStack> fluids = new LinkedList<>();
    List<String> oreDicts = new LinkedList<>();
    HashMap<String, List<?>> outputMap = new HashMap<>();
    for (Object output : outputs) {
      if (output != null) {
        if (output instanceof String) oreDicts.add((String) output);
        if (output instanceof ItemStack) items.add(toChanceStack((ItemStack) output));
        if (output instanceof FluidStack) fluids.add(toChanceStack((FluidStack) output));
      }
    }
    outputMap.put("fluids", fluids);
    outputMap.put("items", items);
    outputMap.put("ores", oreDicts);
    return outputMap;
    }

  public static List<?> getRecipesForMachine(Class<?> machineClass) {
    try {
      Class.forName(machineClass.getName());
      List<?> recipeList = RecipesMachine.getInstance().getRecipes(machineClass);
      if (recipeList == null) {
        throw new ClassNotFoundException(
            "Machine Recipe Class " + machineClass + " not found in AR Recipe Registry");
      } else {
        return recipeList;
      }
    } catch (ClassNotFoundException var2) {
      var2.printStackTrace();
      return new ArrayList<>();
    }
    }

    public static boolean matchRecipe(IRecipe recipeOne, IRecipe recipeTwo) {
    if (recipeOne == null || recipeTwo == null) return false;

        if (recipeOne == recipeTwo) return true;

        return recipeOne.getOutput() == recipeTwo.getOutput()
                && recipeOne.getFluidOutputs() == recipeTwo.getFluidOutputs();
    }
}
