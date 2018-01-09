package com.test.booktestnotification.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.booktestnotification.Activity.MainActivity;
import com.test.booktestnotification.Main2Activity;
import com.test.booktestnotification.R;

public class AdapterFav extends RecyclerView.Adapter<AdapterFav.myViewHolder> {
    public static Context context;



    public AdapterFav(Context context) {
        this.context = context;

    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.item_card, parent, false));

    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        holder.txt_name.setText(MainActivity.favorite.get(position).getName());
        String imgAddress = MainActivity.favorite.get(position).getImg_adrress();
        int id = MainActivity.context.getResources().getIdentifier(imgAddress,"drawable",MainActivity.context.getPackageName());
        holder.avatar.setImageResource(id);
        holder.layout_item.setId(position);
        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = view.getId();
                Intent intent = new Intent(MainActivity.context, Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name","favorite");
                intent.putExtra("id",position+"");
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return MainActivity.favorite.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name;
        ImageView avatar;
        LinearLayout layout_item;


        public myViewHolder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_item);
            avatar = itemView.findViewById(R.id.avatar);
            layout_item = itemView.findViewById(R.id.layout_item);



        }
    }

}