package apps.blessed.a90secondsofglory.utils;

import android.content.Context;
import android.content.res.TypedArray;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import apps.blessed.a90secondsofglory.R;
import apps.blessed.a90secondsofglory.bo.ColorGame;
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
     * Get some random values from a list
     * @param list Initial list
     * @param n Number of object to pick
     * @return New List with random values and length = n
     */
    public static List pickNRandom(List<?> list, int n) {
        Collections.shuffle(list);
        return list.subList(0,n);
    }



}



