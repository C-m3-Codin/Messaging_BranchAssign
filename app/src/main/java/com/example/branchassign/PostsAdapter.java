package com.example.branchassign;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private  final RecyclerViewInterface recyclerViewInterface;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public PostsAdapter(Map<String, List<Message>> postList,RecyclerViewInterface recyclerViewInterface) {
        postList.size();

//        Collections.sort(postList, Comparator.comparing(s -> s.getThread_id()));
        this.postList = postList;

        this.recyclerViewInterface = recyclerViewInterface;

    }

    private Map<String, List<Message>> postList;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem,parent,false);


        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


//        holder.tvTitle.setText(postList.get(position).getTime().getYear()+" ");
        ;

        String[] asd = postList.keySet().toArray(new String[postList.size()]);
        String name = postList.get(asd[position]).get(0).getAgent_id()==null?"Client threadId: " + postList.get(asd[position]).get(0).getThreadId():"Agent : ";

        holder.tvTitle.setText(name);

        holder.tvbody.setText(postList.get(asd[position]).get(postList.get(asd[position]).size()-1).getBody());
        holder.time.setText(postList.get(asd[position]).get(postList.get(asd[position]).size()-1).getTime().format(DateTimeFormatter.ISO_LOCAL_TIME));

//        holder.time.setText("a");



//        holder.itemView.setOnClickListener(view->{
//
//        });





    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvbody;
        TextView time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.titleMessage);
            tvbody = itemView.findViewById(R.id.bodyMessage);
            time = itemView.findViewById(R.id.textViewTime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface!=null){
                        int pos = getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
