package projecthds.advtweakery;

import com.blamejared.mtlib.utils.BaseAction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import zmaster587.libVulpes.interfaces.IRecipe;
import zmaster587.libVulpes.recipe.RecipesMachine;

import java.util.List;

public class ImplBaseAction {

    public static class ImplBaseActionAdd extends BaseAction {
        private Class<?> recipeClass;
        private Object[] inputs;
        private Object[] outputs;
        private int time;
        private int power;

        public ImplBaseActionAdd(Class recipeClass, Object[] outputs, int time, int power, Object[] inputs) {
            super(recipeClass.getName());
            this.recipeClass = recipeClass;
            this.outputs = outputs;
            this.time = time;
            this.power = power;
            this.inputs = inputs;
        }


        @Override
        public void apply() {
            RecipesMachine.getInstance().addRecipe(recipeClass, outputs, time, power, inputs);
        }
    }

    public static class ImplBaseActionRemove extends BaseAction {
        private Class<?> recipeClass;
        private Object[] outputs;

        public ImplBaseActionRemove(Class recipeClass, Object[] outputs) {
            super(recipeClass.getName());
            this.recipeClass = recipeClass;
            this.outputs = outputs;
        }

        @Override
        public void apply() {
            List<IRecipe> recipeList = RecipesMachine.getInstance().getRecipes(recipeClass);

            for (IRecipe recipe : recipeList) {
                List<ItemStack> itemOutputs = null;
                List<FluidStack> fluidOutputs = null;
                for (Object obj : outputs) {
                    if (obj instanceof FluidStack) {
                        fluidOutputs.add((FluidStack) obj);
                    } else itemOutputs.add((ItemStack) obj);
                }

                IRecipe inRecipe = new IRecipe() {
                    @Override
                    public List<ItemStack> getOutput() {return itemOutputs;}
                    @Override
                    public List<FluidStack> getFluidOutputs() {return fluidOutputs;}
                    @Override
                    public List<List<ItemStack>> getIngredients() {return null;}
                    @Override
                    public List<FluidStack> getFluidIngredients() {return null;}
                    @Override
                    public int getTime() {return 0;}
                    @Override
                    public int getPower() {return 0;}
                    @Override
                    public String getOreDictString(int i) {return null;}
                };

                if (Utils.matchRecipe(recipe, inRecipe)) {
                    recipeList.remove(recipe);
                }
            }
        }
    }

    public static class ImplBaseActionAll extends BaseAction {
        private Class recipeClass;

        public ImplBaseActionAll(Class recipeClass) {
            super(recipeClass.getName());
            this.recipeClass = recipeClass;
        }

        @Override
        public void apply() {
            RecipesMachine.getInstance().clearRecipes(recipeClass);
        }
    }

}
