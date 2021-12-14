package kelvin.mite.crafting;

public interface MiteCookingRecipe {

    int getInputCount();
    int getOutputCount();
    void setInputCount(int inputCount);
    void setOutputCount(int outputCount);
}
