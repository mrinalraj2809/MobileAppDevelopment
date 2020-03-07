package com.example.loginact;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter {

    Context context;
    int resource;
    List<Club> lst;
    LayoutInflater layoutInflater;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    String choice;
    Club ci;

    public CustomAdapter(Context context,int resource,List<Club> lst)
    {
        super(context,resource,lst);
        this.context=context;
        this.resource=resource;
        this.lst=lst;
        layoutInflater= (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ci = lst.get(position);
        final View cview = convertView;
        final Holder holder;
        if(convertView==null)
        {    holder=new Holder();
            LayoutInflater view=layoutInflater.from(context);
            convertView=view.inflate(resource,null,false);
            holder.clubName=(TextView)convertView.findViewById(R.id.textView_ClubName);
            holder.objective=convertView.findViewById(R.id.textView_ClubObjective);
            holder.joinClub=(Button)convertView.findViewById(R.id.joinbutton);
            convertView.setTag(holder);

        }
        else
        {
            holder=(Holder) convertView.getTag();

        }

        final String clubname = ci.clubName;
        final String useremailid = FirebaseAuth.getInstance().getCurrentUser().getEmail();

////        ref = FirebaseDatabase.getInstance().getReference().child("Clubs");
////        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot ds: dataSnapshot.getChildren()
//                     ) {
//
//                    //Toast.makeText(getContext(),ds.child("members").toString(),Toast.LENGTH_SHORT).show();
//
//                    List<String> mem = new ArrayList<>();
//
//
//                        mem = (List<String>) ds.child("members").getValue();
//
//                        Toast.makeText(getContext(),"matches",Toast.LENGTH_SHORT).show();
//                        if(mem!=null)
//                        for (String email : mem
//                        ) {
//                            Toast.makeText(getContext(),email + useremailid,Toast.LENGTH_SHORT).show();
//                            if (useremailid.equals(email)) {
//                                holder.joinClub.setText("Already a Member");
//                                Toast.makeText(getContext(),"Inside",Toast.LENGTH_SHORT).show();
//                            }
//                            else{
//                                holder.joinClub.setText("Join Club");
//                            }
//
//                        }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//

        holder.joinClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



//                final CharSequence[] items = {"as a Guest","as a Member"};
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle("Select your choice to join");
//                builder.setItems(items, new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        Toast.makeText(getContext(),"Choosen :" + items[which],Toast.LENGTH_SHORT).show();
//                        choice = items[which].toString();
//                    }
//
//                });
//
//
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//                builder.show();
//                        Context contx = view.getParent().
//                    final Dialog dialogdetail = new Dialog(getContext());
//                    dialogdetail.setContentView(R.layout.dialog);
//
//                    cview.dialogdetail.show();
//
//                    Button member = (Button) dialogdetail.findViewById(R.id.member_choice);
//                    Button guest = (Button) dialogdetail.findViewById(R.id.guest_choice);
//
//                    member.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            Toast.makeText(getContext(),"As a member",Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                    guest.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            Toast.makeText(getContext(),"As a guest",Toast.LENGTH_SHORT).show();
//                        }
//                    });
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //Toast.makeText(context, "you have selected"+lst.get(position).getStaffname(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(),Member_join.class);

                Bundle b = new Bundle();
                b.putString("clubname",lst.get(position).clubName);
                intent.putExtras(b);
                getContext().startActivity(intent);


            }
        });
        holder.clubName.setText(ci.clubName);
        holder.objective.setText(ci.objective);


        return convertView;


    }
}


class Holder{
    TextView clubName;
    TextView objective;
    Button joinClub;

}






