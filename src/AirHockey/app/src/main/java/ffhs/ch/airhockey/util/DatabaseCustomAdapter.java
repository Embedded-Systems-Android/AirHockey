package ffhs.ch.airhockey.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ffhs.ch.airhockey.R;
import ffhs.ch.airhockey.database.Score;

/**
 * Created by Sandro on 30.05.2017.
 *
 * CustomAdapter which is used to correctly show both columns "name" and "score" from Database
 * in the ListView
 */

public class DatabaseCustomAdapter extends ArrayAdapter<Score> {

    public DatabaseCustomAdapter(Context context, ArrayList<Score> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Score scores = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_view_layout, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.list_name);
        TextView score = (TextView) convertView.findViewById(R.id.list_score);
        // Populate the data into the template view using the data object
        name.setText(scores.getName());
        score.setText(scores.getScore());
        // Return the completed view to render on screen
        return convertView;
    }
}




