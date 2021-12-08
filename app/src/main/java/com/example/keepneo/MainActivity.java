package com.example.keepneo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClick {

    private List<Nota> notas;
    private RecyclerView listado;
    private Adaptator adaptador;
    private LinearLayoutManager llm;
    private ConstraintLayout layout;
    private Switch oscuroCheck;

    private boolean modoOscuro = true;
    private JSonSerialicer serialicer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        listeners();
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
            NewNote nn = new NewNote(modoOscuro);
            nn.show(getSupportFragmentManager(),null);
        }
        return false;
    }

    @Override
    public void onClick(int posicion) {
        //Toast.makeText(this, ""+posicion, Toast.LENGTH_SHORT).show();
        ViewNote vn = new ViewNote(notas.get(posicion),posicion,modoOscuro);
        vn.show(getSupportFragmentManager(),null);
    }

    @Override
    public void onLongClick(int posicion) {
        borrarObjeto(posicion).show();
    }

    /**
     * inicializacion de componentes
     */
    private void initComponents(){

        listado = findViewById(R.id.reciclerViewNotas);
        llm = new LinearLayoutManager(this);
        listado.setLayoutManager(llm);

        layout = findViewById(R.id.layoutMain);
        oscuroCheck = findViewById(R.id.modoOscuro);
        oscuroCheck.setChecked(modoOscuro);

        notas = leerFichero();

        cambiarModo();
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
        adaptador = new Adaptator(notas, this,this.modoOscuro);
        listado.setAdapter(adaptador);
    }

    private AlertDialog borrarObjeto(int posicion) {
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

        return alerta.create();
    }

    private void cambiarModo() {
       // Toast.makeText(MainActivity.this, ""+modoOscuro, Toast.LENGTH_SHORT).show();

        if(this.modoOscuro){
            //Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();
            layout.setBackgroundColor(Color.rgb(40,43,48));
            listado.setBackgroundColor(Color.rgb(40,43,48));
            oscuroCheck.setTextColor(Color.WHITE);
        }else{
            //Toast.makeText(this, "false", Toast.LENGTH_SHORT).show();
            layout.setBackgroundColor(Color.WHITE);
            listado.setBackgroundColor(Color.WHITE);
            oscuroCheck.setTextColor(Color.BLACK);
        }
        setAdaptador();
    }

    private void listeners(){
        oscuroCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                modoOscuro = isChecked;

                cambiarModo();
                //setAdaptador();
            }
        });
    }
}