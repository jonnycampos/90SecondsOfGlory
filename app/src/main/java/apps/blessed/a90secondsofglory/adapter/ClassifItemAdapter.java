package apps.blessed.a90secondsofglory.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import apps.blessed.a90secondsofglory.R;
import apps.blessed.a90secondsofglory.lambda.ResponseMetricByProvince;

/**
 * Array adapter for items in the classification
 */
public class ClassifItemAdapter extends ArrayAdapter<ResponseMetricByProvince> {
    public ClassifItemAdapter(Context context, List<ResponseMetricByProvince> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_classif_row,
                    parent,
                    false);
        }

        // Referencias UI.
        TextView positionText = (TextView) convertView.findViewById(R.id.classif_list_position);
        TextView name = (TextView) convertView.findViewById(R.id.classif_list_name);
        TextView points = (TextView) convertView.findViewById(R.id.classif_list_points);

        // Lead actual.
        ResponseMetricByProvince metric = getItem(position);

        if (metric.isCurrentGame()) {
            positionText.setTextColor(Color.YELLOW);
            name.setTextColor(Color.YELLOW);
            points.setTextColor(Color.YELLOW);
        } else {
            positionText.setTextColor(Color.WHITE);
            name.setTextColor(Color.WHITE);
            points.setTextColor(Color.WHITE);
        }

        // Setup.
        if (metric.getPosition() == 0) {
            positionText.setText(String.valueOf(position + 1) + ".");
        } else {
            positionText.setText(metric.getPosition() + ".");
        }
        name.setText(metric.getName());
        points.setText(String.valueOf(metric.getPoints()));

        return convertView;
    }
}
