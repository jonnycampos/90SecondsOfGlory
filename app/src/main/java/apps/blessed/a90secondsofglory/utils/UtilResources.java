package apps.blessed.a90secondsofglory.utils;

import android.content.Context;
import android.content.res.TypedArray;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import apps.blessed.a90secondsofglory.R;
import apps.blessed.a90secondsofglory.bo.ColorGame;
import apps.blessed.a90secondsofglory.bo.GameMetric;
import apps.blessed.a90secondsofglory.bo.PointsIntervalCalculator;
import apps.blessed.a90secondsofglory.bo.ShapeGame;

/**
 * Created by jacam on 08/11/2018.
 */

public final class UtilResources {


    /**
     * Get from resource file shapes/figures that may be drawn in a game
     * @param context Current context
     * @return List of shapes/figures
     */
    public static List<ShapeGame> getGameShapes(Context context) {

        List<ShapeGame> array = new ArrayList<ShapeGame>();



        Class<R.array> res = R.array.class;
        Field field;
        int counter = 1;

        do {
            try {
                field = res.getField("shape" + "_" + counter);
                TypedArray a = context.getResources().obtainTypedArray(field.getInt(null));
                ShapeGame shape = new ShapeGame();
                shape.setName(a.getString(0));
                shape.setResourceName(a.getString(1));
                Field f = R.drawable.class.getField(shape.getResourceName());
                shape.setResource(f.getInt(f));
                array.add(shape);
                counter++;
            } catch (Exception e) {
                field = null;
            }
        } while (field != null);



        return array;

    }




    /**
     * Get from resource file colors that may be drawn in a game
     * @param context Current context
     * @return List of colors
     */
    public static List<ColorGame> getGameColors(Context context) {

        List<ColorGame> array = new ArrayList<ColorGame>();



        Class<R.array> res = R.array.class;
        Field field;
        int counter = 1;

        do {
            try {
                field = res.getField("color" + "_" + counter);
                TypedArray a = context.getResources().obtainTypedArray(field.getInt(null));
                ColorGame color = new ColorGame();
                color.setName(a.getString(0));
                color.setColorCode(a.getInt(1,0));
                array.add(color);
                counter++;
            } catch (Exception e) {
                field = null;
            }

        } while (field != null);



        return array;

    }

    /**
     * Return the rules needed to calculate points
     * @param context To resolve resources
     * @return List of intervals in pointrules.xml as list of PointsIntervalCalulator
     */
    public static List<PointsIntervalCalculator> getPointsCalculatorList(Context context) {
        List<PointsIntervalCalculator> array = new ArrayList<PointsIntervalCalculator>();



        Class<R.array> res = R.array.class;
        Field field;
        int counter = 0;

        do {
            try {
                field = res.getField("interval" + "_" + counter);
                TypedArray a = context.getResources().obtainTypedArray(field.getInt(null));
                PointsIntervalCalculator interval = new PointsIntervalCalculator();
                //Seconds format x-y
                String secondsInterval = a.getString(0);
                StringTokenizer st = new StringTokenizer(secondsInterval,"-");
                interval.setFromSeconds(Integer.parseInt(st.nextToken()));
                interval.setToSeconds(Integer.parseInt(st.nextToken()));
                //Number of shapes format x-y
                String shapesInterval = a.getString(1);
                st = new StringTokenizer(shapesInterval,"-");
                interval.setFromNumberOfShapes(Integer.parseInt(st.nextToken()));
                interval.setToNumberOfShapes(Integer.parseInt(st.nextToken()));
                //Points
                interval.setPoints(a.getInt(2,0));
                array.add(interval);
                counter++;
            } catch (Exception e) {
                field = null;
            }

        } while (field != null);



        return array;

    }


    /**
     * Get some random values from a list
     * @param list Initial list
     * @param n Number of object to pick
     * @return New List with random values and length = n
     */
    public static List pickNRandom(List<?> list, int n) {
        Collections.shuffle(list);
        return list.subList(0,n);
    }


    /**
     * Given the result at the end of a minigame calculate points that applies
     * @param metric Game result
     * @param context To resolve resources
     * @return Points as int
     */
    public static int calculatePoints(GameMetric metric, Context context) {
        if (!metric.isAnswered()) {
            return context.getResources().getInteger(R.integer.pointsNoAnswer);
        }

        //If it was answered
        if (!metric.isRigthAnswer()) {
            return context.getResources().getInteger(R.integer.pointsWrongAnswer);
        }

        List<PointsIntervalCalculator> pointsInterval = getPointsCalculatorList(context);
        for (PointsIntervalCalculator pointInterval:pointsInterval) {
            if (metric.getMilliSecs()>=pointInterval.getFromSeconds()*1000 &&
                    metric.getMilliSecs()<=pointInterval.getToSeconds()*1000 &&
                    metric.getNumberOfShapes() >= pointInterval.getFromNumberOfShapes() &&
                    metric.getNumberOfShapes() <= pointInterval.getToNumberOfShapes())
                    {
                        return pointInterval.getPoints();

            }
        }
        return 0;
    }


}



