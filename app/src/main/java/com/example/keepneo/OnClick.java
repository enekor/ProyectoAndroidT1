package com.example.keepneo;

import androidx.constraintlayout.widget.ConstraintLayout;

public interface OnClick {

    /**
     * que hacer cuando se de click a la nota del RecyclerView
     * @param posicion de la nota en relacion a la lista donde se almacenan
     */
    public void onClick(int posicion);

    /**
     * que hacer cuando se mantiene pulsado a la nota del RecyclerView
     * @param posicion de la nota en relacion a la lista donde se almacenan
     */
    public void onLongClick(int posicion, ConstraintLayout layout);
}