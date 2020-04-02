package com.example.collegeevent.Lokesh;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.view.ViewGroup;

import com.example.collegeevent.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter_LifecycleAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Group extends Fragment implements View.OnClickListener{
//FloatingActionButton floatingActionButton;
DatabaseReference dbref,groupref;
RecyclerView recyclerView,recyclerView1;
FirebaseRecyclerOptions<Group_List> glist;
FirebaseRecyclerAdapter<Group_List,Groups_ViewHolder> adapter;
//ArrayAdapter<String> adapter;
//ArrayList<Group_List> list_of_groups;
//Groups_Adapter groups_adapter;
    public Fragment_Group() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fragment__group, container, false);
//        floatingActionButton= view.findViewById(R.id.create);
//        floatingActionButton.setOnClickListener(this);
        setHasOptionsMenu(true);
        groupref= FirebaseDatabase.getInstance().getReference().child("Groups");
//        recyclerView= view.findViewById(R.id.recycler);
        recyclerView1= view.findViewById(R.id.recycler_groups);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView1.setHasFixedSize(true);

        retrieve_groups();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_group:
//                startActivity(new Intent(getContext(), Apply_Permission.class));
                createGroup();
                return true;
           }
        return false;
    }



    private void retrieve_groups() {
        glist= new FirebaseRecyclerOptions.Builder<Group_List>()
                .setQuery(groupref,Group_List.class).build();
        adapter= new FirebaseRecyclerAdapter<Group_List, Groups_ViewHolder>(glist) {
            @Override
            protected void onBindViewHolder(@NonNull Groups_ViewHolder groups_viewHolder, final  int i, @NonNull Group_List group_list) {
                groups_viewHolder.txtgroup.setText(group_list.getName());
                groups_viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch(i)
                        {
                            case 1: startActivity(new Intent(getContext(),BottomNavigation.class));
                            break;
                        }
                    }
                });

            }

            @NonNull
            @Override
            public Groups_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.groups_layout,parent,false);
                return new Groups_ViewHolder(view);
            }


        };

        adapter.startListening();
        recyclerView1.setAdapter(adapter);

    }

    private void createGroup() {
        AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        builder.setTitle("Enter Group Name");
        final EditText groupnamefield = new EditText(getContext());
        groupnamefield.setHint("e.g. DeCoders Official");
        builder.setView(groupnamefield);
        dbref= FirebaseDatabase.getInstance().getReference();
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String groupname= groupnamefield.getText().toString();
                CreateNewGroup(groupname);
            }
        });
        builder.show();
    }

    private void CreateNewGroup(final String groupname) {
        dbref.child("Groups").child(groupname).setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getContext(), groupname+ "is created!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(), "Search Groups!!!", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStart() {
        super.onStart();
        if(adapter!=null)
            adapter.startListening();
    }

    @Override
    public void onStop() {
        if(adapter!=null)
            adapter.stopListening();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter!=null)
            adapter.startListening();
    }
}
