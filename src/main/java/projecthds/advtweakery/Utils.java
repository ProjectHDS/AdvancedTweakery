package projecthds.advtweakery;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IWeightedIngredient;
import crafttweaker.api.item.WeightedItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.liquid.WeightedLiquidStack;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.api.oredict.WeightedOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import zmaster587.libVulpes.interfaces.IRecipe;
import zmaster587.libVulpes.recipe.NumberedOreDictStack;
import zmaster587.libVulpes.recipe.RecipesMachine;
import zmaster587.libVulpes.recipe.RecipesMachine.ChanceFluidStack;
import zmaster587.libVulpes.recipe.RecipesMachine.ChanceItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Utils {

    public static Object[] toObjects(IWeightedIngredient[] iWeightedIngredients) {
        if (iWeightedIngredients == null) return null;
        else {
            Object[] output = new Object[iWeightedIngredients.length];
            for (int i =0; i < iWeightedIngredients.length; i++) {
                if (iWeightedIngredients[i] != null) {
                    output[i] = toObject(iWeightedIngredients[i]);
                } else output[i] = "";
            }
            return output;
        }
    }

    public static Object toObject(IWeightedIngredient iWeightedIngredient) {
        if (iWeightedIngredient == null) return null;
        else {
            if (iWeightedIngredient instanceof WeightedOreDictEntry) {
                return toString((WeightedOreDictEntry) iWeightedIngredient);
            } else if (iWeightedIngredient instanceof WeightedItemStack) {
                return toStack((WeightedItemStack) iWeightedIngredient);
            } else if (iWeightedIngredient instanceof WeightedLiquidStack) {
                return toFluid((WeightedLiquidStack) iWeightedIngredient);
            } else {
                return null;
            }
        }
    }

    public static String toString(WeightedOreDictEntry entry) {
        return entry.getEntry().getName();
    }

    public static ItemStack toStack(WeightedItemStack stack) {
        if (stack == null) return null;
        else {
            Object internal = stack.getStack().getInternal();
            if (!(internal instanceof ItemStack)) {
                return null;
            } else {
                return (ItemStack) internal;
            }
        }
    }

    public static FluidStack toFluid(WeightedLiquidStack fluid) {
        if (fluid == null) return null;
        else {
            Object internal = fluid.getStack().getInternal();
            if (!(internal instanceof FluidStack)) {
                return null;
            } else {
                return (FluidStack) internal;
            }
        }
    }

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

    public static Object[] convertFromCT(Object[] input) {
        if (input != null && input.length > 0) {
            ArrayList<FluidStack> vanillaFluids = new ArrayList<>();
            ArrayList<ItemStack> vanillaItems = new ArrayList<>();
            ArrayList<NumberedOreDictStack> oreDicts = new ArrayList<>();
            Object[] returnObj = input;
            int objIndex = input.length;

            int i;
            for(i = 0; i < objIndex; ++i) {
                Object element = returnObj[i];
                if (element != null) {
                    if (isLiquid(element)) {
                        FluidStack liquidStack = getLiquid(element);
                        if (liquidStack != null) {
                            vanillaFluids.add(liquidStack);
                        }
                    } else if (isItem(element)) {
                        ItemStack itemStack = getItem(element);
                        if (itemStack != null) {
                            vanillaItems.add(itemStack);
                        }
                    } else if (isOreDict(element)) {
                        NumberedOreDictStack oreStack = getOreDict(element);
                        if (oreStack != null) {
                            oreDicts.add(oreStack);
                        }
                    }
                }
            }

            returnObj = new Object[vanillaFluids.size() + vanillaItems.size() + oreDicts.size()];
            objIndex = 0;

            for(i = 0; i < vanillaFluids.size(); ++i) {
                returnObj[objIndex] = vanillaFluids.get(i);
                ++objIndex;
            }

            for(i = 0; i < vanillaItems.size(); ++i) {
                returnObj[objIndex] = vanillaItems.get(i);
                ++objIndex;
            }

            for(i = 0; i < oreDicts.size(); ++i) {
                returnObj[objIndex] = oreDicts.get(i);
                ++objIndex;
            }

            return returnObj;
        } else {
            return new Object[0];
        }
    }

    private static boolean isItem(Object ctObj) {
        if (!(ctObj instanceof IItemStack) && !(ctObj instanceof ItemStack)) {
            if (!(ctObj instanceof IIngredient)) {
                return false;
            } else {
                return ((IIngredient)ctObj).getInternal() instanceof IItemStack || ((IIngredient)ctObj).getInternal() instanceof ItemStack;
            }
        } else {
            return true;
        }
    }

    protected static boolean isLiquid(Object ctObj) {
        if (!(ctObj instanceof ILiquidStack) && !(ctObj instanceof FluidStack)) {
            if (!(ctObj instanceof IIngredient)) {
                return false;
            } else {
                return ((IIngredient)ctObj).getInternal() instanceof ILiquidStack || ((IIngredient)ctObj).getInternal() instanceof FluidStack;
            }
        } else {
            return true;
        }
    }

    private static boolean isOreDict(Object ctObj) {
        return ctObj instanceof IIngredient ? ((IIngredient)ctObj).getInternal() instanceof IOreDictEntry : ctObj instanceof IOreDictEntry;
    }

    private static ItemStack getItem(Object obj) {
        if (isItem(obj)) {
            if (obj instanceof ItemStack) {
                return (ItemStack)obj;
            } else if (obj instanceof IIngredient) {
                return ((IIngredient)obj).getInternal() instanceof ItemStack ? (ItemStack)((IIngredient)obj).getInternal() : toVanilla((IItemStack)((IIngredient)obj).getInternal());
            } else {
                return toVanilla((IItemStack)obj);
            }
        } else {
            return ItemStack.EMPTY;
        }
    }

    private static FluidStack getLiquid(Object obj) {
        if (isLiquid(obj)) {
            if (obj instanceof FluidStack) {
                return (FluidStack)obj;
            } else if (obj instanceof IIngredient) {
                return ((IIngredient)obj).getInternal() instanceof FluidStack ? (FluidStack)((IIngredient)obj).getInternal() : toVanilla((ILiquidStack)((IIngredient)obj).getInternal());
            } else {
                return toVanilla((ILiquidStack)obj);
            }
        } else {
            return null;
        }
    }

    private static NumberedOreDictStack getOreDict(Object obj) {
        if (obj instanceof IIngredient && isOreDict(obj)) {
            IIngredient ingredient = (IIngredient)obj;
            Object type = ingredient.getInternal();
            if (type instanceof IOreDictEntry) {
                return toVanilla((IOreDictEntry)type, ingredient.getAmount());
            }
        }

        return obj instanceof IOreDictEntry ? toVanilla((IOreDictEntry)obj, ((IOreDictEntry)obj).getAmount()) : null;
    }

    private static NumberedOreDictStack toVanilla(IOreDictEntry oreDict, int amount) {
        return new NumberedOreDictStack(oreDict.getName(), amount);
    }

    private static ItemStack toVanilla(IItemStack item) {
        return InputHelper.toStack(item);
    }

    private static FluidStack toVanilla(ILiquidStack liquid) {
        return InputHelper.toFluid(liquid);
    }
}
