package apps.blessed.a90secondsofglory.bo;

/**
 * Created by jacam on 08/11/2018.
 */

public final class ColorGame {


    private String name;

    private int colorCode;


    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    @Override
    public boolean equals(Object obj) {
        return ((ColorGame)obj).getName().equals(this.getName());
    }
}
