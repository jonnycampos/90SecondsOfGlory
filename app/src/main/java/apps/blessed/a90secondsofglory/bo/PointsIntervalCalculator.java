package apps.blessed.a90secondsofglory.bo;

/**
 * Created by jacam on 16/11/2018.
 */

public class PointsIntervalCalculator {

    private int points;

    private int fromNumberOfShapes;
    private int toNumberOfShapes;

    private int fromSeconds;
    private int toSeconds;

    public PointsIntervalCalculator() {
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getFromNumberOfShapes() {
        return fromNumberOfShapes;
    }

    public void setFromNumberOfShapes(int fromNumberOfShapes) {
        this.fromNumberOfShapes = fromNumberOfShapes;
    }

    public int getToNumberOfShapes() {
        return toNumberOfShapes;
    }

    public void setToNumberOfShapes(int toNumberOfShapes) {
        this.toNumberOfShapes = toNumberOfShapes;
    }

    public int getFromSeconds() {
        return fromSeconds;
    }

    public void setFromSeconds(int fromSeconds) {
        this.fromSeconds = fromSeconds;
    }

    public int getToSeconds() {
        return toSeconds;
    }

    public void setToSeconds(int toSeconds) {
        this.toSeconds = toSeconds;
    }
}


