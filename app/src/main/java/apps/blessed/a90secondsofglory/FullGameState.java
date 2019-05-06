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
    public int position;
    public String status;

    public static String STATUS_PAUSED = "STATUS_PAUSED";
    public static String STATUS_TIME_OVER = "STATUS_TIME_OVER";
    public static String STATUS_GAME = "STATUS_GAME";


    private static final FullGameState ourInstance = new FullGameState();

    public static FullGameState getInstance() {
        return ourInstance;
    }

    private FullGameState() {
        gameMetricList = new ArrayList<GameMetric>();
        points = 0;
    }

    public synchronized void addMetric(GameMetric gameMetric) {
        gameMetricList.add(gameMetric);
    }


    public synchronized void addPoints(int newPoints) {

        points += newPoints;
        if (points < 0) {
            points = 0;
        }
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isPlayingGame() {
        return getStatus().equals(FullGameState.STATUS_GAME);
    }
}
