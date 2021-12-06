package com.example.keepneo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adaptator extends RecyclerView.Adapter<Adaptator.ViewHolder> {

    private List<Nota> notas;
    private OnClick onClick;

    public Adaptator (List<Nota> notas,OnClick onClick){

        this.notas=notas;
        this.onClick=onClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_preview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(notas.get(position).getTexto().length()<20){
            holder.texto.setText(notas.get(position).getTexto());
        }else{
            String texto = notas.get(position).getTexto().substring(0,19);
            holder.texto.setText(texto +"...");
        }
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

        private TextView texto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            texto = itemView.findViewById(R.id.previewTitle);
        }

        @Override
        public void onClick(View v) {
            int posicion = getAdapterPosition();
            onClick.onClick(posicion);
        }

        @Override
        public boolean onLongClick(View v) {
            int posicion = getAdapterPosition();
            onClick.onLongClick(posicion);
            return false;
        }
    }
}
