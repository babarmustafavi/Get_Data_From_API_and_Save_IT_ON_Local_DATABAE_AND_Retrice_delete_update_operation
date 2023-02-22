package com.example.multipletask;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterMainactivity extends RecyclerView.Adapter<AdapterMainactivity.ViewHolder>{
    private Context context;
    private ArrayList<RetriveDataModel> jsonModels;

    public AdapterMainactivity(Context context, ArrayList<RetriveDataModel> jsonModels) {
        this.context = context;
        this.jsonModels = jsonModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_for_main_activity_adapter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RetriveDataModel model=jsonModels.get(position);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
              //  Bundle args = new Bundle();
                Intent intent=new Intent(context,DataShowActivity.class);
                intent.putExtra("title", jsonModels.get(position).getTitle());
                context.startActivity(intent);
//                Fragment fragment = new BlankFragmentB();
//                args.putString("title", jsonModels.get(position).getTitle());
//                fragment.setArguments(args);
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frgmentloader,fragment).addToBackStack(null).commit();
////
            }
        });

        holder.title.setText(jsonModels.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return jsonModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.to);
            linearLayout=itemView.findViewById(R.id.mainl);
        }
    }
}