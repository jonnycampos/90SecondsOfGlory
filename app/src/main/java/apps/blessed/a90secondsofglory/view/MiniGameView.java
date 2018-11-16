package apps.blessed.a90secondsofglory.view;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;


import com.easyandroidanimations.library.AnimationListener;
import com.easyandroidanimations.library.ExplodeAnimation;
import com.easyandroidanimations.library.FlipHorizontalAnimation;
import com.easyandroidanimations.library.FoldAnimation;
import com.easyandroidanimations.library.FoldLayout;
import com.easyandroidanimations.library.ParallelAnimator;
import com.easyandroidanimations.library.ParallelAnimatorListener;
import com.easyandroidanimations.library.ScaleInAnimation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import apps.blessed.a90secondsofglory.FullGameState;
import apps.blessed.a90secondsofglory.R;
import apps.blessed.a90secondsofglory.animation.MiniGameViewAnimation;
import apps.blessed.a90secondsofglory.bo.ColorGame;
import apps.blessed.a90secondsofglory.bo.GameMetric;
import apps.blessed.a90secondsofglory.bo.ShapeGame;
import apps.blessed.a90secondsofglory.shape.MiniGameButton;
import apps.blessed.a90secondsofglory.shape.MiniGameProgressBar;
import apps.blessed.a90secondsofglory.utils.UtilResources;



/**
 * Created by jacam on 30/09/2018.
 */

public class MiniGameView extends AppCompatImageView {


    private int colorBorder;
    private int colorBackground;


    private int leftRelative;
    private int rightRelative;
    private int topRelative;
    private int bottomRelative;

    private String positionHorizontal;
    private String positionVertical;

    private int MIN_SHAPES = 5;
    private int MAX_SHAPES = 20;

    // defines paint and canvas
    private Paint drawPaint;

    // Figures to be drawn
    private List<ShapeGame> shapeList;

    // Colors to be used
    private List<ColorGame> colorList;


    // Drawable list of the mini game when the calculation is over
    private List<Drawable> shapesInGame;

    // Buttons with answers
    private List<MiniGameButton> buttons;

    //Progress bar is a drawable
    private MiniGameProgressBar progressBar;

    //Question of the game
    private String question;
    private ShapeGame shapeQuestion;
    private ColorGame colorQuestion;

    //Correct Answer
    private int correctAnswer;

    //Local timer
    MiniGameCountDownTimer timer;

    //Metrics to return at the end of the minigame
    GameMetric metric;

    //Time the minigame is running
    int millisecs;

    //Boolean to check if the game was answered yet or not
    boolean answered;

    public int getColorBorder() {
        return colorBorder;
    }

    public void setColorBorder(int colorBorder) {
        this.colorBorder = colorBorder;
    }

    public int getColorBackground() {
        return colorBackground;
    }

    public void setColorBackground(int colorBackground) {
        this.colorBackground = colorBackground;
    }

    public int getLeftRelative() {
        return leftRelative;
    }

    public void setLeftRelative(int leftRelative) {
        this.leftRelative = leftRelative;
    }

    public int getRightRelative() {
        return rightRelative;
    }

    public void setRightRelative(int rightRelative) {
        this.rightRelative = rightRelative;
    }

    public int getTopRelative() {
        return topRelative;
    }

    public void setTopRelative(int topRelative) {
        this.topRelative = topRelative;
    }

    public int getBottomRelative() {
        return bottomRelative;
    }

    public void setBottomRelative(int bottomRelative) {
        this.bottomRelative = bottomRelative;
    }

    public String getPositionHorizontal() {
        return positionHorizontal;
    }

    public void setPositionHorizontal(String positionHorizontal) {
        this.positionHorizontal = positionHorizontal;
    }

    public String getPositionVertical() {
        return positionVertical;
    }

    public void setPositionVertical(String positionVertical) {
        this.positionVertical = positionVertical;
    }



    public List<MiniGameButton> getButtons() {
        return buttons;
    }

    public void setButtons(List<MiniGameButton> buttons) {
        this.buttons = buttons;
    }

    public MiniGameProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(MiniGameProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public MiniGameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setFocusable(true);
        setFocusableInTouchMode(true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MiniGameView);

        this.setLeftRelative(0);
        this.setTopRelative(0);
        this.setRightRelative(Resources.getSystem().getDisplayMetrics().widthPixels / 2);
        this.setBottomRelative(Resources.getSystem().getDisplayMetrics().heightPixels / 2);

        this.setPositionHorizontal(a.getString(R.styleable.MiniGameView_positionHorizontal));
        this.setPositionVertical(a.getString(R.styleable.MiniGameView_positionVertical));

        //Metrics to return at the end of the minigame
        GameMetric metric = new GameMetric();

        setupPaint();
    }

    // Setup paint with color and stroke styles
    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setAntiAlias(true);

        answered = false;
        metric = new GameMetric();


        //Generate game type
        generateGameValues();

        //Create random body once
        shapesInGame = createBody();

        //Create all buttons once
        buttons = createButtons();

        //Create the progress bar
        progressBar = createProgressBar();

        //Start timer
        timer = new MiniGameCountDownTimer(8500,1, this);
        timer.start();


    }

    private void generateGameValues() {
        List<ShapeGame> allShapes = UtilResources.getGameShapes(getContext());
        List<ColorGame> allColors = UtilResources.getGameColors(getContext());

        //Figures to draw
        Random randomFigures = new Random();
        int numberFiguresToDraw = randomFigures.nextInt(allShapes.size())+1;
        shapeList = UtilResources.pickNRandom(allShapes, numberFiguresToDraw);
        metric.setNumberOfShapes(shapeList.size());

        //Colors to draw
        Random randomColors = new Random();
        int numberColorsToDraw = randomColors.nextInt(allColors.size())+1;
        colorList = UtilResources.pickNRandom(allColors, numberColorsToDraw);
        metric.setNumberOfColors(colorList.size());

        //Question
        Random randomFigure = new Random();
        shapeQuestion = shapeList.get((randomFigure.nextInt(shapeList.size())));
        boolean questionWithColor  = new Random().nextBoolean();
        question = shapeQuestion.getName() + "s";
        if (questionWithColor) {
            colorQuestion = colorList.get((randomFigure.nextInt(colorList.size())));
            question = question + " color " + colorQuestion.getName();
        }
        metric.setColorInTheQuestion(questionWithColor);
    }




    /**
     * Create the progress bar initiated given the bounds
     * @return
     */
    private MiniGameProgressBar createProgressBar() {
        int left;
        int right;
        int top;
        int bottom;

        if (getPositionVertical().equals("l")) {
            left = (((getRightRelative() - getLeftRelative()) / 4) * 3) + 100;
            right = (getRightRelative() - getLeftRelative()) - 100;
        } else {
            left = 100;
            right = ((getRightRelative() - getLeftRelative()) / 4) - 100;
        }

        if (getPositionHorizontal().equals("t")) {
            top = 70;
            bottom = ((getBottomRelative() - getTopRelative()) / 2) - 50;
        } else {
            top = ((getBottomRelative() - getTopRelative()) / 2) + 20;
            bottom = (getBottomRelative() - getTopRelative()) - 100;
        }

        MiniGameProgressBar progressBar = new MiniGameProgressBar(left,right,top,bottom);


        MiniGameViewAnimation animation = new MiniGameViewAnimation(this, progressBar.getTop());
        animation.setDuration(8500);
        this.startAnimation(animation);
        return progressBar;
    }


    /**
     * Create the list of buttons with the answers. They are touchable
     * @return List of MiniGameButton object
     */
    private List<MiniGameButton> createButtons() {

        int positionLeftButton;
        int positionRightButton;
        int positionTopButton;
        int positionBottomButton;
        int buttonLength;
        int buttonHeight;
        int separation = 20;
        ArrayList<MiniGameButton> buttonsList = new ArrayList<MiniGameButton>();


        //Length of the button will be 1/5
        buttonLength = (getRightRelative() - getLeftRelative()) / 5;
        if (getPositionVertical().equals("l")) {
            //Starts at left
            positionLeftButton = 50;
            positionRightButton = positionLeftButton + buttonLength;
        } else {
            //Starts at 1/4, ends at right
            positionLeftButton = ((getRightRelative() - getLeftRelative()) / 4) + 50;
            positionRightButton = positionLeftButton + buttonLength;
        }

        //We calculate winner button and incorrect answers
        int winnerButtonIndex = new Random().nextInt(3);




        //Height of the button will be around 1/8
        buttonHeight = (getBottomRelative() - getTopRelative()) / 9;
        positionTopButton = ((getBottomRelative() - getTopRelative()) * 3) / 4;
        positionBottomButton = positionTopButton + buttonHeight;

        Integer otherAnswer = null;

        //Button 1
        String buttonText = buttonAnswer(0,winnerButtonIndex, correctAnswer, otherAnswer);
        MiniGameButton button1 = new MiniGameButton(buttonText, positionLeftButton, positionTopButton, positionRightButton, positionBottomButton);
        buttonsList.add(button1);

        //Button 2
        if (Integer.parseInt(button1.getText()) != correctAnswer) {
            otherAnswer = Integer.parseInt(button1.getText());
        }
        buttonText = buttonAnswer(1,winnerButtonIndex, correctAnswer, otherAnswer);
        positionLeftButton = positionLeftButton + separation + buttonLength;
        positionRightButton = positionRightButton + separation + buttonLength;
        MiniGameButton button2 = new MiniGameButton(buttonText, positionLeftButton, positionTopButton, positionRightButton, positionBottomButton);
        buttonsList.add(button2);

        //Button 3
        if (otherAnswer == null) {
            otherAnswer = Integer.getInteger(button2.getText());
        }
        buttonText = buttonAnswer(2,winnerButtonIndex, correctAnswer, otherAnswer);
        positionLeftButton = positionLeftButton + separation + buttonLength;
        positionRightButton = positionRightButton + separation + buttonLength;
        MiniGameButton button3 = new MiniGameButton(buttonText, positionLeftButton, positionTopButton, positionRightButton, positionBottomButton);
        buttonsList.add(button3);

        return buttonsList;

    }



    private String buttonAnswer(int i, int winnerButtonIndex, int correctAnswer, Integer otherAnswer) {
        if (i == winnerButtonIndex)
            return String.valueOf(correctAnswer);

        if (otherAnswer == null) {
            boolean greaterThanCorrectAnswer = new Random().nextBoolean();
            if (greaterThanCorrectAnswer)
                return String.valueOf(correctAnswer + 1);
            else
                return String.valueOf(correctAnswer - 1);
        }

        if (otherAnswer.intValue() > correctAnswer)
            return String.valueOf(correctAnswer - 1);
        else
            return String.valueOf(correctAnswer + 1);
    }


    /**
     * Create all shapes to be part of the game
     * @return List of shapes
     */
    private List<Drawable> createBody() {
        int positionLeftBody;
        int positionRightBody;
        int positionTopBody;
        int positionBottomBody;
        int sizeShape = 50;
        correctAnswer = 0;
        ArrayList<Drawable> list = new ArrayList<Drawable>();
        if (getPositionVertical().equals("l")) {
            //Starts at left, ends 2/3 length
            positionLeftBody = 50;
            positionRightBody = (((getRightRelative() - getLeftRelative()) * 2) / 3) - sizeShape;
        } else {
            //Starts at 1/3, ends at right
            positionLeftBody = ((getRightRelative() - getLeftRelative())  / 3) - 50;
            positionRightBody = getRightRelative() - sizeShape - 50;
        }

        //Starts at 2/5. Ends at 4/5
        positionTopBody = (((getBottomRelative() - getTopRelative()) * 1) / 5);
        positionBottomBody = (((getBottomRelative() - getTopRelative()) * 3) / 5);

        Random randomVertical = new Random();
        Random randomHorizontal = new Random();
        Random randomColor = new Random();
        Random randomShape = new Random();
        //Calculate num of shapes to draw
        int numShapes = new Random().nextInt(MAX_SHAPES - MIN_SHAPES) + MIN_SHAPES;
        int i = 0;
        while (i < numShapes) {
            //Where the shape will be located
            int x = randomVertical.nextInt(positionRightBody - positionLeftBody) + positionLeftBody;
            int y = randomHorizontal.nextInt(positionBottomBody - positionTopBody) + positionTopBody;

            //If there is no a shape in the same place
            if (!shapeOverlapped(x,y,list,sizeShape)) {
                int shapeIndex = randomShape.nextInt(shapeList.size());
                ShapeGame shape = shapeList.get(shapeIndex);
                Drawable d = getResources().getDrawable(shape.getResource());
                int colorIndex = randomColor.nextInt(colorList.size());
                ColorGame color = colorList.get(colorIndex);
                DrawableCompat.setTint(d, color.getColorCode());
                d.setBounds(x, y, x + sizeShape, y + sizeShape);
                list.add(d);
                i++;

                //Here we will calculate the correct answer
                if (shape.equals(shapeQuestion)) {
                    if (colorQuestion == null || (colorQuestion != null && color.equals(colorQuestion))) {
                        correctAnswer++;
                    }
                }
            }
        }

        return list;

    }


    /**
     * Check if one figure would overlap an existing one
     * @param x Of the left top pixel
     * @param y Of the left top pixel
     * @param list Drawable list already calculated
     * @param size Size of the drawable
     * @return
     */
    private boolean shapeOverlapped(int x, int y, ArrayList<Drawable> list, int size) {

        for (Drawable d:list) {
            if (((y > d.getBounds().bottom) || (y + size < d.getBounds().top))
                ||
               ((x + size < d.getBounds().left) || (x > d.getBounds().right)))
            {
                //Keep trying
            } else {
                return true;
            }
        }
        return false;
    }



    @Override
    protected void onDraw(Canvas canvas) {
       //Top - Question
       drawQuestion(canvas, question);

       //Middle - Body
       drawBody(canvas);

       //Bottom - Touchable answers
       drawButtons(canvas);

       //Progress bar
       drawProgressBar(canvas);
    }


    /**
     * Draw the progress bar in the screen
     * @param canvas
     */
    private void drawProgressBar(Canvas canvas) {
        progressBar.draw(canvas);


    }


    /**
     * Draw buttons already created
     * @param canvas Canvas where to draw buttons in
     */
    private void drawButtons(Canvas canvas) {
        for (MiniGameButton button : buttons) {
            button.paintButton(canvas, drawPaint);
        }
    }



    /**
     * Space to get the answer
     * @param canvas Where the pictures are drawn
     */
    private void drawBody(Canvas canvas) {

        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeWidth(5);

        for (Drawable d:shapesInGame) {
            d.draw(canvas);
        }

    }




    /**
     * Draw the question in top of the mini game view
     * @param canvas Canvas to draw in
     * @param question Text to draw in top of the view
     */
    private void drawQuestion(Canvas canvas, String question) {
        drawPaint.setColor(Color.WHITE);
        drawPaint.setTextSize(50);
        drawPaint.setStyle(Paint.Style.FILL);
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.oswald);
        drawPaint.setTypeface(typeface);
        if (getPositionVertical().equals("l")) {
            drawPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(question,getLeftRelative() + 50,getTopRelative() + 80,drawPaint);
        } else {
            drawPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(question,getRightRelative() - 50,getTopRelative() + 80,drawPaint);
        }
    }


    public boolean onTouchEvent(MotionEvent event) {

        int eventAction = event.getAction();

        int x = (int) event.getX();
        int y = (int) event.getY();

        int pressedButton = -1;
        switch (eventAction) {

            case MotionEvent.ACTION_DOWN:
                metric.setAnswered(true);
                metric.setMilliSecs(millisecs);
                //Pressed button 1
                if (buttons.get(0).getRectangle().contains(x,y)) {
                    pressedButton = 0;
                } else if (buttons.get(1).getRectangle().contains(x,y)) {
                    pressedButton = 1;
                } else if (buttons.get(2).getRectangle().contains(x,y)) {
                    pressedButton = 2;
                }
                if (pressedButton != -1) {
                    //Check the correct answer
                    if (Integer.parseInt(buttons.get(pressedButton).getText()) == correctAnswer) {
                        //If correct: Calculate points.Add points
                        this.restartView();
                        metric.setRigthAnswer(true);

                    } else {
                        //If false: Calculate negative points. Add points
                        this.restartView();
                        metric.setRigthAnswer(false);
                    }
                    //End instance and create a new one
                }
                break;
            }

        return true;
    }


    public void restartView(){
        //Closing the execution before starting up again
        timer.cancel();
        metric.setPoints(calculatePoints(metric));
        FullGameState.getInstance().addMetric(metric);
        FullGameState.getInstance().addPoints(metric.getPoints());



        //Bye bye
        final View view = this;


        ParallelAnimator parallelAnimator = new ParallelAnimator()
                .add(new ScaleInAnimation(view))
                .add(new FlipHorizontalAnimation(view))
                .setDuration(500);
        parallelAnimator.setListener(new ParallelAnimatorListener() {
            @Override
            public void onAnimationEnd(ParallelAnimator animation) {
                view.setVisibility(View.GONE);
                invalidate();
                setupPaint();
                view.setVisibility(View.VISIBLE);
            }
        });
        parallelAnimator.animate();


//        FoldAnimation foldAnimation = new FoldAnimation(view)
//                .setInterpolator(new DecelerateInterpolator())
//                .setDuration(500)
//                .setNumOfFolds(7)
//                .setOrientation(FoldLayout.Orientation.VERTICAL)
//                .setListener(new AnimationListener() {
//                    @Override
//                    public void onAnimationEnd(com.easyandroidanimations.library.Animation animation) {
//                        view.setVisibility(View.GONE);
//                        invalidate();
//                        setupPaint();
//                        view.setVisibility(View.VISIBLE);
//                    }
//                });
//        foldAnimation.animate();


        Animation startFadeOutAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out_anim);
        startFadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
                invalidate();
                setupPaint();
                view.setVisibility(View.VISIBLE);
            }

        });

        //this.startAnimation(explodeAnimation);
    }


    /**
     * Points calculation for a given game
     * @param metric Values like speed, right or wrong...
     * @return Number of points: It may be negative
     */
    private int calculatePoints(GameMetric metric) {
        if (!metric.isAnswered()) {
            //Return a fix value
        }

        //If it was answered
        if (!metric.isRigthAnswer()) {
            //Return a fix value
        }

        //If it was answered right: two variables. Speed and difficulty



    }

    public class MiniGameCountDownTimer extends CountDownTimer {

        MiniGameView view;

        public MiniGameCountDownTimer(long millisInFuture, long countDownInterval, MiniGameView view) {
            super(millisInFuture, countDownInterval);
            this.view = view;

        }

        @Override
        public void onFinish() {
            //Bye bye
            if (!answered) {
                view.restartView();
            }

        }

        @Override
        public void onTick(long millisUntilFinished) {
            millisecs ++;

        }
    }
}
