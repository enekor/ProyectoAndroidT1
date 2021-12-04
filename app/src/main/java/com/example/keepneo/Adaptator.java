package com.example.keepneo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adaptator extends RecyclerView.Adapter<Adaptator.ViewHolder> {

    List<Nota> notas;

    public Adaptator (List<Nota> notas){
        this.notas=notas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.texto.setText(notas.get(position).getTexto());
        holder.importante.setChecked(notas.get(position).isImportante());
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private EditText texto;
        private CheckBox importante;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            texto = itemView.findViewById(R.id.noteText);
            importante = itemView.findViewById(R.id.importantCheck);
        }
    }
}
