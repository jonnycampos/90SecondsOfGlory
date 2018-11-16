package apps.blessed.a90secondsofglory.bo;

/**
 * Created by jacam on 08/11/2018.
 */

public class ShapeGame {

    private String name;

    private String resourceName;

    private int resource;


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return ((ShapeGame)obj).getName().equals(this.getName());
    }
}
