package com.test.booktestnotification.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.booktestnotification.Quote;
import com.test.booktestnotification.R;

import java.util.List;

public class AdapterBigPerson extends RecyclerView.Adapter<AdapterBigPerson.myViewHolder> {
    public static Context context;
    private List<Quote> lists;

    public AdapterBigPerson(Context context, List<Quote> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        Quote quote = lists.get(position);
        holder.txt_name.setText(quote.getName());



    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name;


        public myViewHolder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_item);



        }
    }

}
