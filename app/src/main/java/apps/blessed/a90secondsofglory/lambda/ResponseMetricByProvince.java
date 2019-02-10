package apps.blessed.a90secondsofglory.lambda;

/**
 * Created by jacam on 13/01/2019.
 */

public class ResponseMetricByProvince {

    String name;
    int points;
    String province;
    int position;
    boolean currentGame;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(boolean currentGame) {
        this.currentGame = currentGame;
    }
}


