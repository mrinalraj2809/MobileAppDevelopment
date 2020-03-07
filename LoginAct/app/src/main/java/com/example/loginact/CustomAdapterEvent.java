package com.example.loginact;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CustomAdapterEvent extends ArrayAdapter {
    Context context;
    int resource;
    List<Event> lst;
    LayoutInflater layoutInflater;
    FirebaseDatabase firebaseDatabase;

    public CustomAdapterEvent(Context context, int resource, List<Event> lst) {
        super(context, resource, lst);
        this.context = context;
        this.resource = resource;
        this.lst = lst;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Holder holder;
        if (convertView == null) {
            holder = new Holder();
            LayoutInflater view = layoutInflater.from(context);
            convertView = view.inflate(resource, null, false);
            holder.agenda = (TextView) convertView.findViewById(R.id.textAgenda);
            holder.dateOfEvent = convertView.findViewById(R.id.textViewDate);
            holder.timeOfEvent = convertView.findViewById(R.id.textViewTime);
            holder.needsOfEvent = convertView.findViewById(R.id.textNeeds);
            holder.reminder = convertView.findViewById(R.id.buttonReminder);
            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();

        }
        Event ci = lst.get(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(context, "you have selected"+lst.get(position).getStaffname(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, CalendarActivity.class);
                String sAgenda = lst.get(position).getAgenda();
                String sDateofEvent = lst.get(position).getDateOfEvent();
                String sTimeOfEvent = lst.get(position).getTimeOfEvent();
                String sNeedsOfEvent = lst.get(position).getNeedsOfEvent();


                Bundle bundle = new Bundle();
                bundle.putString("agenda", sAgenda);
                bundle.putString("timeOfEvent", sDateofEvent);
                bundle.putString("dateOfEvent", sTimeOfEvent);
                bundle.putString("needsOfEvent", sNeedsOfEvent);

                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
        holder.agenda.setText("Agenda:" + ci.agenda);
        holder.needsOfEvent.setText("Needs : " + ci.needsOfEvent);
        holder.dateOfEvent.setText("Date " + ci.dateOfEvent);
        holder.timeOfEvent.setText("Time " + ci.timeOfEvent);


        return convertView;


    }


    class Holder {
        TextView dateOfEvent;
        TextView agenda;
        TextView timeOfEvent;
        TextView needsOfEvent;
        TextView eventUrl;
        Button reminder;
    }
}



