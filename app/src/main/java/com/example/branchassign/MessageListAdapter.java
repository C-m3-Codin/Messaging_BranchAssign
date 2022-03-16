package com.example.branchassign;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.List;
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ReceivedMessageHolder> {
//    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
//    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private Context mContext;
    private List<Message> mMessageList;

    public MessageListAdapter(Context context, List<Message> messageList) {
        mContext = context;
        mMessageList = messageList;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        Message message = (Message) mMessageList.get(position);
        return  1;


    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public ReceivedMessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dm_message_client, parent, false);;


        return new ReceivedMessageHolder(view);
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(ReceivedMessageHolder holder, int position) {
        Message message = (Message) mMessageList.get(position);




        holder.messageText.setText(message.getBody());
        holder.timeText.setText(message.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE).toString());
        holder.monthName.setText(message.getTime().format(DateTimeFormatter.BASIC_ISO_DATE).toString());
        holder.nameText.setText(message.getAgent_id()==null?"Client " + message.getThreadId():"Agent " + message.getThreadId());

    }



    public class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText ;
TextView peopleName;
        TextView timeText;

        TextView nameText ;
        TextView monthName;


        ReceivedMessageHolder(View itemView) {
            super(itemView);
//            peopleName = (TextView)itemView.findViewById(R.id.text_gchat_user_other);
monthName=(TextView)itemView.findViewById(R.id.text_gchat_date_other);
            messageText = (TextView) itemView.findViewById(R.id.text_gchat_message_other);
            timeText = (TextView) itemView.findViewById(R.id.text_gchat_timestamp_other);
            nameText = (TextView) itemView.findViewById(R.id.text_gchat_user_other);
//            profileImage = (ImageView) itemView.findViewById(R.id.text_gchat_user_other);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        void bind(Message message) {
            System.out.println("got here from there"+message.getBody());

            messageText.setText(message.getBody());
//            nameText.setText(message.);

            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getTime().format(DateTimeFormatter.ISO_DATE_TIME).toString());



//            nameText.setText(message.getSender().getNickname());

            // Insert the profile image from the URL into the ImageView.
//            Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
        }
    }
}