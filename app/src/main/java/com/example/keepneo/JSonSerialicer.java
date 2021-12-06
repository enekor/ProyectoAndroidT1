package com.example.keepneo;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSonSerialicer {
    private String fileName;
    private Context contexto;
    private Gson gson;

    JSonSerialicer(String fileName, Context contexto) {
        this.fileName = fileName;
        this.contexto = contexto;
    }

    /**
     * escribe en un JSon en memeoria un backup de las notas para el sigiente inicio de sesion poder recuperarlas
     * @param notas a guardar
     */
    public void save(List<Nota> notas){
        gson = new Gson();
        String json = gson.toJson(notas);
        Writer writer = null;

        try{
            OutputStream out = contexto.openFileOutput(fileName,contexto.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * recupera las notas guardadas en el JSon en la sesion anterior
     * @return la lista de notas almacenadas en el JSon
     */
    public List<Nota> load(){
        List<Nota> returner = null;

        BufferedReader reader = null;

        try{
            reader= new BufferedReader(new InputStreamReader(contexto.openFileInput(fileName)));
            gson = new Gson();
            Type type = new TypeToken<ArrayList<Nota>> (){}.getType();
            returner= gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(returner==null){
            return new ArrayList<>();
        }
        return returner;
    }
}
