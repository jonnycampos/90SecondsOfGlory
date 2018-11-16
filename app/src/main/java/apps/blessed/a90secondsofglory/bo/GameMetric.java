package apps.blessed.a90secondsofglory.bo;

/**
 * Created by jacam on 16/11/2018.
 */

public class GameMetric {
    private int milliSecs;
    private boolean answered;
    private boolean rigthAnswer;
    private int numberOfShapes;
    private int numberOfColors;
    private boolean colorInTheQuestion;
    public int points;

    public GameMetric() {
    }

    public int getMilliSecs() {
        return milliSecs;
    }

    public void setMilliSecs(int milliSecs) {
        this.milliSecs = milliSecs;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public boolean isRigthAnswer() {
        return rigthAnswer;
    }

    public void setRigthAnswer(boolean rigthAnswer) {
        this.rigthAnswer = rigthAnswer;
    }

    public int getNumberOfShapes() {
        return numberOfShapes;
    }

    public void setNumberOfShapes(int numberOfShapes) {
        this.numberOfShapes = numberOfShapes;
    }

    public int getNumberOfColors() {
        return numberOfColors;
    }

    public void setNumberOfColors(int numberOfColors) {
        this.numberOfColors = numberOfColors;
    }

    public boolean isColorInTheQuestion() {
        return colorInTheQuestion;
    }

    public void setColorInTheQuestion(boolean colorInTheQuestion) {
        this.colorInTheQuestion = colorInTheQuestion;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
