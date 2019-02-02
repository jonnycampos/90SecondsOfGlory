package apps.blessed.a90secondsofglory;

import java.util.ArrayList;
import java.util.List;

import apps.blessed.a90secondsofglory.bo.GameMetric;

/**
 * Created by jacam on 16/11/2018.
 */

public class FullGameState {

    private int points;
    private List<GameMetric> gameMetricList;
    public String name;
    public String province;
    public String date;

    private static final FullGameState ourInstance = new FullGameState();

    public static FullGameState getInstance() {
        return ourInstance;
    }

    private FullGameState() {
        gameMetricList = new ArrayList<GameMetric>();
        points = 0;
    }

    public void addMetric(GameMetric gameMetric) {
        gameMetricList.add(gameMetric);
    }


    public void addPoints(int newPoints) {

        points += newPoints;
    }

    public int getPoints() {
        return points;
    }

    public List<GameMetric> getGameMetricList() {
        return gameMetricList;
    }

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
}
