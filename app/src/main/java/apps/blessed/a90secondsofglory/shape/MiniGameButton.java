package apps.blessed.a90secondsofglory.shape;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import apps.blessed.a90secondsofglory.R;
import apps.blessed.a90secondsofglory.utils.UtilResources;

/**
 * Created by jacam on 04/11/2018.
 */

public class MiniGameButton {

    //The rounded rectangle touchable
    private RectF rectangle;

    //The text inside the button
    private String text;

    //Relative positions of the area of the button
    private int positionLeftButton;
    private int positionRightButton;
    private int positionTopButton;
    private int positionBottomButton;

    //Default values if no provided
    private int DEFAULT_COLOR = Color.RED;
    private Paint.Style DEFAULT_STYLE = Paint.Style.FILL;
    private int DEFAULT_TEXT_COLOR = Color.WHITE;
    private int DEFAULT_STROKE_COLOR = Color.WHITE;


    //Some values to draw the button
    private int color = DEFAULT_COLOR;
    private Paint.Style style = DEFAULT_STYLE;
    private int textColor = DEFAULT_TEXT_COLOR;
    private int strokeColor = DEFAULT_STROKE_COLOR;

    private Context context = null;

    public MiniGameButton(Context context, String text, int positionLeftButton, int positionTopButton, int positionRightButton, int positionBottomButton) {
        this.context = context;
        this.positionLeftButton = positionLeftButton;
        this.positionRightButton = positionRightButton;
        this.positionTopButton = positionTopButton;
        this.positionBottomButton = positionBottomButton;
        this.rectangle = new RectF(positionLeftButton, positionTopButton, positionRightButton, positionBottomButton);;
        this.setText(text);
    }



    public MiniGameButton(RectF rectangle, String text) {
        this.rectangle = rectangle;
        this.text = text;
    }


    /**
     * Paint this button instance
     * @param canvas Canvas where to paint
     * @param paint With this parameters
     */
    public void paintButton(Canvas canvas, Paint paint) {

        Integer textSize = UtilResources.getRealSize(context.getResources().getInteger(R.integer.mg_buttonTextSize), context.getResources().getDisplayMetrics());
        Integer stroke = UtilResources.getRealSize(context.getResources().getInteger(R.integer.mg_buttonStroke), context.getResources().getDisplayMetrics());


        paint.setStrokeWidth(stroke);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getStrokeColor());
        canvas.drawRoundRect(rectangle, 10, 10, paint);

        paint.setStyle(getStyle());
        paint.setColor(getColor());
        canvas.drawRoundRect(rectangle, 10, 10, paint);

        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.CENTER);
        int width = (int)rectangle.width();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getTextColor());
        int numOfChars = paint.breakText(getText(),true,width,null);
        int start = (getText().length()-numOfChars)/2;
        Integer textVerticalPadding = UtilResources.getRealSize(context.getResources().getInteger(R.integer.mg_buttonTextPaddingVertical), context.getResources().getDisplayMetrics());
        canvas.drawText(getText(),start,start+numOfChars,rectangle.centerX(),rectangle.centerY()+textVerticalPadding,paint);

    }
    
    
    
    
    public RectF getRectangle() {
        return rectangle;
    }

    public void setRectangle(RectF rectangle) {
        this.rectangle = rectangle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPositionLeftButton() {
        return positionLeftButton;
    }

    public void setPositionLeftButton(int positionLeftButton) {
        this.positionLeftButton = positionLeftButton;
    }

    public int getPositionRightButton() {
        return positionRightButton;
    }

    public void setPositionRightButton(int positionRightButton) {
        this.positionRightButton = positionRightButton;
    }

    public int getPositionTopButton() {
        return positionTopButton;
    }

    public void setPositionTopButton(int positionTopButton) {
        this.positionTopButton = positionTopButton;
    }

    public int getPositionBottomButton() {
        return positionBottomButton;
    }

    public void setPositionBottomButton(int positionBottomButton) {
        this.positionBottomButton = positionBottomButton;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Paint.Style getStyle() {
        return style;
    }

    public void setStyle(Paint.Style style) {
        this.style = style;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }
}
