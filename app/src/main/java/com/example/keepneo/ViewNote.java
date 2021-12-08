package com.example.keepneo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class ViewNote extends DialogFragment {

    private CheckBox importante;
    private EditText texto;
    private ImageView isImportant, isNotImportant;
    private Button guardar;
    private Nota note;
    private ConstraintLayout layout;

    private MainActivity main;
    private int posicion;
    private boolean oscuro;

    public ViewNote(Nota note,int posicion,boolean oscuro){
        this.note=note;
        this.posicion = posicion;
        this.oscuro = oscuro;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View dialogoView = inflater.inflate(R.layout.note,null);

        importante = dialogoView.findViewById(R.id.importantCheck);
        importante.setChecked(note.isImportante());
        texto = dialogoView.findViewById(R.id.noteText);
        texto.setText(note.getTexto());
        isImportant = dialogoView.findViewById(R.id.noteImportant);
        isNotImportant = dialogoView.findViewById(R.id.noteNotInportant);
        guardar = dialogoView.findViewById(R.id.noteOk);
        layout = dialogoView.findViewById(R.id.layoutNota);

        if(oscuro){
            layout.setBackgroundColor(Color.rgb(40,43,48));
            texto.setTextColor(Color.WHITE);
        }else{
            layout.setBackgroundColor(Color.WHITE);
            texto.setTextColor(Color.BLACK);
        }

        if(importante.isChecked()){
            isImportant.setVisibility(View.VISIBLE);
            isNotImportant.setVisibility(View.GONE);
        }else{
            isImportant.setVisibility(View.GONE);
            isNotImportant.setVisibility(View.VISIBLE);
        }

        builder.setView(dialogoView);

        onActions();

        return builder.show();
    }

    private void onActions(){
        importante.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isImportant.setVisibility(View.VISIBLE);
                    isNotImportant.setVisibility(View.GONE);
                }else{
                    isImportant.setVisibility(View.GONE);
                    isNotImportant.setVisibility(View.VISIBLE);
                }
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main = (MainActivity) getActivity();

                Nota nota = new Nota(texto.getText().toString(),importante.isChecked());
                main.saveNote(nota,posicion);

                dismiss();
            }
        });
    }
}
