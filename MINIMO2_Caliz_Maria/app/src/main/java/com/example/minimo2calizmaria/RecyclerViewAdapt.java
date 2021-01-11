package com.example.minimo2calizmaria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minimo2calizmaria.models.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapt extends RecyclerView.Adapter<RecyclerViewAdapt.MyViewHolder> {

    private List<Repositories> listaRepos;
    private Repositories repositorio;
    private Context context;

    public RecyclerViewAdapt(Context cntx, List<Repositories> repos){
        context = cntx;
        listaRepos = repos;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.repositories, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.repo.setText(listaRepos.get(position).getName());
        holder.language.setText(listaRepos.get(position).getLanguage());



    }

    @Override
    public int getItemCount() {
        return listaRepos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView repo;
        TextView language;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            language = itemView.findViewById(R.id.language);
            repo = itemView.findViewById(R.id.nameRepo);
        }
    }






}
