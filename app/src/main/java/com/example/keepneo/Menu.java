package com.example.keepneo;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class Menu extends DialogFragment implements View.OnClickListener{

    private ImageView oscuroImage,aboutImage, helpImage;
    private TextView oscuroText,aboutText,helpText;
    private ConstraintLayout layoutMenu;
    private boolean oscuro;

    public Menu(boolean oscuro){
        this.oscuro = oscuro;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        LayoutInflater layout = getActivity().getLayoutInflater();
        View dialogView = layout.inflate(R.layout.menu,null);

        oscuroImage = dialogView.findViewById(R.id.modoOscuroIcon);
        aboutImage = dialogView.findViewById(R.id.aboutUsIcon);
        helpImage = dialogView.findViewById(R.id.howUseIcon);
        oscuroText = dialogView.findViewById(R.id.modoOscuroText);
        aboutText = dialogView.findViewById(R.id.aboutUsText);
        helpText = dialogView.findViewById(R.id.howUseText);
        layoutMenu = dialogView.findViewById(R.id.layoutMenu);

        cambiarColores();

        builder.setView(dialogView);

        return  builder.show();
    }


    @Override
    public void onClick(View v) {
        MainActivity main = (MainActivity) getActivity();
        switch (v.getId()){
            case R.id.modoOscuroIcon:
            case R.id.modoOscuroText:{
                oscuro = !oscuro;
                cambiarColores();
                main.intent(oscuro);
                dismiss();
                break;
            }
            case R.id.aboutUsIcon:
            case R.id.aboutUsText:{
                main.intent("about");
                dismiss();
                break;
            }
            case R.id.howUseText:
            case R.id.howUseIcon:{
                main.intent("help");
                dismiss();
                break;
            }
        }
    }

    private void cambiarColores() {
        if(oscuro){
            layoutMenu.setBackgroundColor(Color.rgb(40,43,48));
            oscuroText.setTextColor(Color.WHITE);
            aboutText.setTextColor(Color.WHITE);
            helpText.setTextColor(Color.WHITE);
        }else{
            layoutMenu.setBackgroundColor(Color.rgb(240,240,240));
            oscuroText.setTextColor(Color.BLACK);
            aboutText.setTextColor(Color.BLACK);
            helpText.setTextColor(Color.BLACK);
        }
    }
}
