package org.hackillinois.app2017.Schedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.hackillinois.app2017.Events.Event;
import org.hackillinois.app2017.Events.EventManager;
import org.hackillinois.app2017.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EventListFragment extends Fragment {

    private ArrayList<Event> events;

    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        events = EventManager.getInstance().getEvents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_list_layout, parent, false);
        unbinder = ButterKnife.bind(this, view);

        Bundle extras = getArguments();
        int whichDay = extras.getInt("day");
        Log.i("Which Day", whichDay + "");
        switch (whichDay) {
            case 0:
                events = getFridayEvents();
                break;
            case 1:
                events = getSaturdayEvents();
                break;
            case 2:
                events = getSundayEvents();
                break;
            default:
                events = getFridayEvents();
                break;
        }

        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter mAdapter = new ScheduleAdapter(events);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), 1);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }

    private ArrayList<Event> getFridayEvents() {
        ArrayList<Event> toReturn = new ArrayList<>();

        for (Event e : events) {
            if (e.getStartDay() == 5) {
                toReturn.add(e);
            }
        }

        return toReturn;
    }

    private ArrayList<Event> getSaturdayEvents() {
        ArrayList<Event> toReturn = new ArrayList<>();

        for (Event e : events) {
            if (e.getStartDay() == 6) {
                toReturn.add(e);
            }
        }

        return toReturn;
    }

    private ArrayList<Event> getSundayEvents() {
        ArrayList<Event> toReturn = new ArrayList<>();

        for (Event e : events) {
            if (e.getStartDay() == 0) {
                toReturn.add(e);
            }
        }

        return toReturn;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
