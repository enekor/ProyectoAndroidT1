package com.example.keepneo;

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

public class MainActivity extends AppCompatActivity {

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

    public void newNote(Nota nota){
        notas.add(nota);
    }

    public void notaSinTexto(){
        Toast.makeText(this, "No se puede guardar una nota sin contenido", Toast.LENGTH_SHORT).show();
    }

    private void initComponents(){

        listado = findViewById(R.id.reciclerViewNotas);
        llm = new LinearLayoutManager(this);
        listado.setLayoutManager(llm);

        notas = leerFichero();

        adaptador = new Adaptator(notas);
        listado.setAdapter(adaptador);

    }

    private List<Nota> leerFichero(){
        serialicer = new JSonSerialicer("notas.json",this);
        return serialicer.load();
    }

    private void guardarNotas(){
        serialicer = new JSonSerialicer("notas.json",this);
        serialicer.save(notas);

        adaptador = new Adaptator(notas);
        listado.setAdapter(adaptador);
    }
}