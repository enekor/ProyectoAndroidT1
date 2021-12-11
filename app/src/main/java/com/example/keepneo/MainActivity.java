package com.example.keepneo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClick {

    private List<Nota> notas;
    private RecyclerView listado;
    private Adaptator adaptador;
    private LinearLayoutManager llm;
    private ConstraintLayout layout;
    private Toolbar toolbar;
    private Animation blink;
    private ImageView newNote;

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
    public void onClick(int posicion) {
        //Toast.makeText(this, ""+posicion, Toast.LENGTH_SHORT).show();
        ViewNote vn = new ViewNote(notas.get(posicion),posicion,leerModo(),false);
        vn.show(getSupportFragmentManager(),null);
    }

    @Override
    public void onLongClick(int posicion, ConstraintLayout layout) {

        borrarObjeto(posicion,layout).show();
    }

    /**
     * inicializacion de componentes
     */
    private void initComponents(){

        listado = findViewById(R.id.reciclerViewNotas);
        llm = new LinearLayoutManager(this);
        listado.setLayoutManager(llm);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        layout = findViewById(R.id.layoutMain);
        newNote = findViewById(R.id.newNote);

        notas = leerFichero();
        blink = AnimationUtils.loadAnimation(this,R.anim.seleccion_prolongada);

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
        adaptador = new Adaptator(notas, this,leerModo());
        listado.setAdapter(adaptador);
    }

    private AlertDialog borrarObjeto(int posicion, ConstraintLayout layout) {

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        layout.startAnimation(blink);

        alerta.setMessage("Se procedera a borrar la nota")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notas.remove(posicion);
                        layout.clearAnimation();
                        setAdaptador();
                    }
                })
                .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        layout.clearAnimation();
                    }
                });

        alerta.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                layout.clearAnimation();
            }
        });
        return alerta.create();
    }

    /**
     * cambia de modo de color (oscuro o no) dependiendo de la sharedPreference oscuro,
     * que determina el modo actual
     */
    private void cambiarModo() {

        if(leerModo()){
            layout.setBackgroundColor(Color.rgb(40,43,48));
            listado.setBackgroundColor(Color.rgb(40,43,48));
            //oscuroCheck.setTextColor(Color.WHITE);
        }else{
            layout.setBackgroundColor(Color.rgb(240,240,240));
            listado.setBackgroundColor(Color.rgb(240,240,240));
            //oscuroCheck.setTextColor(Color.BLACK);
        }
        setAdaptador();
    }

    /**
     * donde se almacenan los listener de los botones (en este caso solo 1)
     */
    private void listeners(){
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewNote vn = new ViewNote(null,-1,leerModo(),true);
                vn.show(getSupportFragmentManager(),null);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu menu = new Menu(leerModo());
                menu.show(getSupportFragmentManager(),null);
            }
        });
    }

    /**
     * guarda en una shared preference el modo preferido por el usuario para poder persistirlo
     * en el siguiente inicio de la aplicacion
     * @param modoOscuro el modo actual a guardar
     */
    private void guardarModo(boolean modoOscuro){
        SharedPreferences preferencias = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putBoolean("oscuro", modoOscuro);
        editor.apply();
    }

    /**
     * lee de la shared preference el modo preferido y lo returnea
     * @return el modo preferido por el usuario, siendo true oscuro y false blanco
     */
    private boolean leerModo(){
        SharedPreferences preferencias = getSharedPreferences("preferencias",Context.MODE_PRIVATE);
        return preferencias.getBoolean("oscuro",true);
    }

    /**
     * dependiendo del objeto pasado por parametro actuara con el intent de una manera u otra, a no ser que sea un booleano, que en ese caso,
     * al ser el cambio de modo de color, llamara al metodo encargado de ello
     * @param o instruccion a seguir
     */
    public void intent(Object o){
        Intent intent = new Intent(this,MenuActivity.class);
        if(o.equals(true) || o.equals(false)){
            guardarModo((boolean)o);
        }else if(o.equals("about")){
            //...
        }else if(o.equals(("help"))){
            //...
        }
    }
}