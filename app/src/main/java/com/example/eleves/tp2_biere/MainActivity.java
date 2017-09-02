package com.example.eleves.tp2_biere;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<Biere> adapter;
    List<Biere> bieres = new ArrayList<Biere>();
    SQLiteHandler handler;
    //ImageView imageView;
    //int idCouleur;
    //String couleurSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation de l'acces a la base de donnees
        handler = new SQLiteHandler(this);
        final ListView listView = (ListView) findViewById(R.id.ListeBiere);
        bieres = handler.getAllBieres();

        adapter = new ArrayAdapter<Biere>(this, R.layout.liste, R.id.titre, bieres){

            public Biere getItem(int position){
                return bieres.get(position);
            }

            @Override
            public View getView(int position, View convertView,  ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                TextView text1 = (TextView)  v.findViewById(R.id.titre);
                TextView text2 = (TextView)  v.findViewById(R.id.donnees);
                Biere b =bieres.get(position);
                text1.setText(b.getNomBiere());
                text2.setText(b.toString());
                ImageView imageView = (ImageView) v.findViewById(R.id.icon);

                    if (b.getCouleur().equals("Blanche")) {
                        imageView.setBackgroundColor(Color.parseColor("#F7F1B8"));

                    }
                    else if (b.getCouleur().equals("Blonde")) {

                        imageView.setBackgroundColor(Color.parseColor("#FFC933"));
                    }
                    else if (b.getCouleur().equals("Brune")) {

                        imageView.setBackgroundColor(Color.parseColor("#AB5605"));
                    }
                    else if (b.getCouleur().equals("Noire")) {

                        imageView.setBackgroundColor(Color.parseColor("#38291B"));
                    }
                    else if (b.getCouleur().equals("Rousse")) {

                        imageView.setBackgroundColor(Color.parseColor("#CB0516"));
                    }

                return  v;
            }

        };

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView < ? > adapterView, View view, int i, long l){

                Biere biere = (Biere)adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, BiereActivity.class);
                intent.putExtra("Biere", biere);
                startActivity(intent);

            }
        });
        // Titre de l'activite
        setTitle("BIÈRE?!");


    }

    public void ajouterBiere(View view) {

        Intent intent = getIntent();
        Biere biere = new Biere();
        intent = new Intent(this, BiereActivity.class);
        intent.putExtra("Biere", biere);
        startActivity(intent);

    }

}