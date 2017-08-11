package edu.cnm.bootcamp.david.medmanager.helpers;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import edu.cnm.bootcamp.david.medmanager.R;
import edu.cnm.bootcamp.david.medmanager.activities.SchedulerActivity;
import edu.cnm.bootcamp.david.medmanager.entities.Schedule;

/**
 * Created by davem on 8/10/2017.
 */

public class ScheduleAdapter extends ArrayAdapter <Schedule>{


    public ScheduleAdapter(@NonNull Context context, @NonNull List<Schedule> schedules) {
        super(context, R.layout.schedule_layout, schedules);
    }

    @Nullable
    @Override
    public View getView (int position, @Nullable View convert, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.schedule_layout, null);
        TextView view = (TextView)layout.findViewById(R.id.medicationName);
        Schedule schedule = getItem(position);
        view.setText(schedule.getName());
        view = (TextView)layout.findViewById(R.id.scheduleDosage);
        view.setText(schedule.getDosage());
        view = (TextView)layout.findViewById(R.id.scheduleTime);
        view.setText(schedule.getTime());
       return layout;

    }
}
