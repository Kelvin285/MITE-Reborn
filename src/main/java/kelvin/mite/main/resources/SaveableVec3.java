package kelvin.mite.main.resources;

import java.io.Serializable;

public class SaveableVec3 implements Serializable  {
    public int x, y, z;
    public SaveableVec3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SaveableVec3) {
            SaveableVec3 pos = (SaveableVec3) o;

            return x == pos.x && y == pos.y && z == pos.z;
        }
        return false;
    }
}
