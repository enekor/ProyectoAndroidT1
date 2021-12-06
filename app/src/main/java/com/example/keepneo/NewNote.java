package com.example.keepneo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class NewNote extends DialogFragment {

    private Button boton;
    private CheckBox checkImportante;
    private ImageView importante,noImportante;
    private EditText texto;
    private MainActivity main;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View dialogoView = inflater.inflate(R.layout.note,null);

        boton = dialogoView.findViewById(R.id.noteOk);
        checkImportante = dialogoView.findViewById(R.id.importantCheck);
        importante = dialogoView.findViewById(R.id.noteImportant);
        noImportante = dialogoView.findViewById(R.id.noteNotInportant);
        texto = dialogoView.findViewById(R.id.noteText);

        noImportante.setVisibility(View.VISIBLE);
        importante.setVisibility(View.GONE);

        builder.setView(dialogoView).setMessage("nueva nota");

        /**
         * cuando le des al checkBox se cambia de forma la estrella de importante
         */
        checkImportante.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    importante.setVisibility(View.VISIBLE);
                    noImportante.setVisibility(View.GONE);
                } else {
                    importante.setVisibility(View.GONE);
                    noImportante.setVisibility(View.VISIBLE);
                }
            }
        });

        /**
         * cuando le das click al boton se almacenan los datos en un objeto nota y se pasan al main por el metodo de nueva nota
         */
        boton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                main =  (MainActivity) getActivity();
                if (texto.getText().toString().equals("")) {
                    main.notaSinTexto();
                } else {
                    Nota nota = new Nota();
                    nota.setTexto(texto.getText().toString());
                    nota.setImportante(checkImportante.isChecked());
                    main.newNote(nota);
                    dismiss();
                }
            }
        });

        return builder.create();
    }
}