package com.example.keepneo;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class NewNote {

    private Button boton;
    private CheckBox checkImportante;
    private ImageView importante,noImportante;
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View dialogoView = inflater.inflate(R.layout.dialogo_mostrar_nota,null);

    public NewNote(){

        boton = dialogoView.findViewById(R.id.noteOk);
        checkImportante = dialogoView.findViewById(R.id.importantCheck);
        importante = dialogoView.findViewById(R.id.noteImportant);
        noImportante = dialogoView.findViewById(R.id.noImprt);

        boton.
    }


}
