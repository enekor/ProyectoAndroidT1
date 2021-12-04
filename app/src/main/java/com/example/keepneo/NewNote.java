package com.example.keepneo;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class NewNote extends DialogFragment {

    private Button boton;
    private CheckBox checkImportante;
    private ImageView importante,noImportante;
    private EditText texto;
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View dialogoView = inflater.inflate(R.layout.note,null);

    public NewNote(){

        boton = dialogoView.findViewById(R.id.noteOk);
        checkImportante = dialogoView.findViewById(R.id.importantCheck);
        importante = dialogoView.findViewById(R.id.noteImportant);
        noImportante = dialogoView.findViewById(R.id.noteNotInportant);
        texto = dialogoView.findViewById(R.id.noteText);

        noImportante.setVisibility(View.VISIBLE);
        importante.setVisibility(View.GONE);

        checkImportante.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    noImportante.setVisibility(View.VISIBLE);
                    importante.setVisibility(View.GONE);
                }else{
                    noImportante.setVisibility(View.GONE);
                    importante.setVisibility(View.VISIBLE);
                }
            }
        });

        boton.setOnClickListener(new View.OnClickListener() {

            MainActivity main = (MainActivity)  getActivity();
            @Override
            public void onClick(View v) {
                if(texto.getText().equals("")){
                    main.notaSinTexto();
                }else{
                    main.newNote(new Nota(texto.getText().toString(),checkImportante.isChecked()));
                    dismiss();
                }
            }
        });
    }


}
