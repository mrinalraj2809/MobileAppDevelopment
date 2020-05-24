package com.example.mrinal_raj;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private List<MessageModel>         messageList;
    private int                        userType;
    private String                     flag;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView             outgoingMsgContent;
        public TextView             outgoingmsgTime;

        public TextView             incomingMsgContent;
        public TextView             incomingmsgTime;

        public ImageView            sendersProfileImage;
        public TextView             sendersName;

        public ImageView            sendImage;
        public ImageView            receiveImage;

        LinearLayout                incomingLinearLayout;
        RelativeLayout              outgoingRelativeLayout;




        public MyViewHolder(View view) {
            super(view);
//            mAuth = FirebaseAuth.getInstance();

            outgoingMsgContent              = (TextView) view.findViewById(R.id.t_Message_out);
            incomingMsgContent              = (TextView) view.findViewById(R.id.t_Message_in);
            outgoingmsgTime                 = (TextView) view.findViewById(R.id.t_time_out);
            incomingmsgTime                 = (TextView) view.findViewById(R.id.t_time_in);
            sendersName                     = (TextView) view.findViewById(R.id.t_senders_name);

            sendImage                       = view.findViewById(R.id.senderImageView);
            receiveImage                    = view.findViewById(R.id.recieverImageView);

            incomingLinearLayout            = view.findViewById(R.id.custom_incoming_message);
            outgoingRelativeLayout          = view.findViewById(R.id.custom_outgoing_message);


        }
    }
    public MessageAdapter(List<MessageModel> message_modelList) {
        this.messageList = message_modelList;
    }
    public MessageAdapter(MessageModel messageModel)
    {
        messageList.add(messageModel);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_message_layout,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        mAuth = FirebaseAuth.getInstance();

        MessageModel message = messageList.get(position);
        //this fn puts the data onto the each view holder.
        flag = message.getType();
        if(flag.equals("receiveText") ) {
            holder.incomingLinearLayout.setVisibility(View.VISIBLE);
            holder.outgoingRelativeLayout.setVisibility(View.GONE);
            holder.receiveImage.setVisibility(View.GONE);
            holder.sendImage.setVisibility(View.GONE);
            holder.incomingMsgContent.setText(message.getMessage_content());
            holder.incomingmsgTime.setText(message.getSent_date_time());
//            holder.sendersProfileImage.setImageURI(Uri.parse(message.getSenders_profile_pic()));
//            Picasso
//                    .get()
//                    .load(message.getSenders_profile_pic())
//                    .transform(new CircleTransform())
//                    .into(holder.sendersProfileImage);
            //}
            //holder.sendersProfileImage.setText(user.getSenders_profile_pic());
        }
        else if (flag.equals("sendText"))
        {
            holder.outgoingRelativeLayout.setVisibility(View.VISIBLE);
            holder.incomingLinearLayout.setVisibility(View.GONE);
            holder.receiveImage.setVisibility(View.GONE);
            holder.sendImage.setVisibility(View.GONE);
            holder.outgoingMsgContent.setText(message.getMessage_content());
            holder.outgoingmsgTime.setText(message.getSent_date_time());
//            holder.sendersName.setText(message.getSenders_name());
//            holder.sendersProfileImage.setImageURI(Uri.parse(message.getSenders_profile_pic()));
//            Picasso
//                    .get()
//                    .load(message.getSenders_profile_pic())
//                    .into(holder.sendersProfileImage);
        }
        else if (flag.equals("receiveImage"))
        {
            holder.incomingLinearLayout.setVisibility(View.VISIBLE);
            holder.outgoingRelativeLayout.setVisibility(View.GONE);
            holder.incomingMsgContent.setVisibility(View.GONE);
            holder.outgoingMsgContent.setVisibility(View.GONE);
            holder.sendImage.setVisibility(View.GONE);
            holder.receiveImage.setVisibility(View.VISIBLE);
            holder.receiveImage.setImageResource(R.drawable.index);
            holder.outgoingmsgTime.setText(message.getSent_date_time());


        }
        else if (flag.equals("sendImage"))
        {
            holder.outgoingRelativeLayout.setVisibility(View.VISIBLE);
            holder.incomingLinearLayout.setVisibility(View.GONE);
            holder.incomingMsgContent.setVisibility(View.GONE);
            holder.outgoingMsgContent.setVisibility(View.GONE);
            holder.sendImage.setVisibility(View.VISIBLE);
            holder.receiveImage.setVisibility(View.GONE);
            holder.sendImage.setImageResource(R.drawable.index);
            holder.outgoingmsgTime.setText(message.getSent_date_time());


        }

    }
    //
    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
