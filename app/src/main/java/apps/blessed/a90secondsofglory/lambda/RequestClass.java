package apps.blessed.a90secondsofglory.lambda;

import apps.blessed.a90secondsofglory.bo.GameMetric;

/**
 * Created by jacam on 13/01/2019.
 */

public class RequestClass {
    String name;
    String province;
    int points;
    String date;
    GameMetric[] gameMetrics;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public GameMetric[] getGameMetrics() {
        return gameMetrics;
    }

    public void setGameMetrics(GameMetric[] gameMetrics) {
        this.gameMetrics = gameMetrics;
    }
}
