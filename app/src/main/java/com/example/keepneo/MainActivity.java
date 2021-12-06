package com.example.keepneo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClick {

    private List<Nota> notas;
    private RecyclerView listado;
    private Adaptator adaptador;
    private LinearLayoutManager llm;

    private JSonSerialicer serialicer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    @Override
    protected void onPause() {
        super.onPause();
        guardarNotas();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.newNote){
            NewNote nn = new NewNote();
            nn.show(getSupportFragmentManager(),"nueva nota");
        }
        return false;
    }

    @Override
    public void onClick(int posicion) {
        Toast.makeText(this, posicion, Toast.LENGTH_SHORT).show();
        ViewNote vn = new ViewNote(notas.get(posicion),posicion);
        vn.show(getSupportFragmentManager(),"Ver nota");
    }

    @Override
    public void onLongClick(int posicion) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);

        alerta.setMessage("se procedera a borrar la nota")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notas.remove(posicion);
                        setAdaptador();
                    }
                })
                .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        setAdaptador();
    }

    /**
     * inicializacion de componentes
     */
    private void initComponents(){

        listado = findViewById(R.id.reciclerViewNotas);
        llm = new LinearLayoutManager(this);
        listado.setLayoutManager(llm);

        notas = leerFichero();

        setAdaptador();

    }

    /**
     * adicion de una nueva nota a la lista de notas
     * @param nota nota a guardar
     */
    public void newNote(Nota nota){
        notas.add(nota);
        setAdaptador();
    }

    /**
     * guardamos la noa editada
     * @param nota editada
     * @param position donde se va a guardar (posicion de la nota original)
     */
    public void saveNote(Nota nota, int position){

        notas.set(position,nota);
        setAdaptador();
    }

    /**
     * saca un Toast si la nota a guardar no tiene texto asociado
     */
    public void notaSinTexto(){
        Toast.makeText(this, "No se puede guardar una nota sin contenido", Toast.LENGTH_SHORT).show();
    }

    /**
     * recuperamos las notas guardadas en la base de datos JSon en memoria
     * @return la lisat de notas almacenada en el JSon
     */
    private List<Nota> leerFichero(){
        serialicer = new JSonSerialicer("notas.json",this);
        return serialicer.load();
    }

    /**
     * guardar las notas en el JSon
     */
    private void guardarNotas(){
        serialicer = new JSonSerialicer("notas.json",this);
        serialicer.save(notas);

        setAdaptador();
    }

    /**
     * crea un nuevo adaptador con la lista de notas actualizada y la setea como adaptador del RecyclerView
     */
    private void setAdaptador() {
        adaptador = new Adaptator(notas, this);
        listado.setAdapter(adaptador);
    }
}