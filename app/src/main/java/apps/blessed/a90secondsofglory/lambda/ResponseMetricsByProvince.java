package apps.blessed.a90secondsofglory.lambda;

/**
 * Created by jacam on 13/01/2019.
 */

public class ResponseMetricsByProvince {

    ResponseMetricByProvince[] Items;
    int Count;

    public ResponseMetricByProvince[] getItems() {
        return Items;
    }

    public void setItems(ResponseMetricByProvince[] items) {
        Items = items;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }



}


