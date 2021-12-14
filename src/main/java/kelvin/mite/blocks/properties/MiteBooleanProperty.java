package kelvin.mite.blocks.properties;

import com.google.common.collect.ImmutableSet;
import net.minecraft.state.property.BooleanProperty;

import java.util.Collection;

public class MiteBooleanProperty extends BooleanProperty {
    private final ImmutableSet<Boolean> values = ImmutableSet.of(false, true);

    public MiteBooleanProperty(String name) {
        super(name);
    }

    public Collection<Boolean> getValues() {

        return this.values;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object instanceof BooleanProperty && super.equals(object)) {
            MiteBooleanProperty booleanProperty = (MiteBooleanProperty)object;
            return this.values.equals(booleanProperty.values);
        } else {
            return false;
        }
    }

    public int computeHashCode() {
        return 31 * super.computeHashCode() + this.values.hashCode();
    }

}
