package com.example.eleves.tp2_biere;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class BiereActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, Serializable {

    public Biere biere = new Biere();
    private ImageView viewImage;
    private Spinner spinner;
    public String nomBiere;
    public ArrayList<String> listTypeBiere;
    public ArrayList<String> listNomBrasserie;
    ArrayAdapter<String> adapterBrasserie;
    ArrayAdapter<String> adapterTBiere;
    AutoCompleteTextView autoCompBrasserie;
    AutoCompleteTextView autoCompTypeBiere;

    public static final String FICHIER_PNG = "fichier.png";
    SQLiteHandler handler;
    String nomBiereSelectionnee;
    int idBiereSelectionnee;


    Button bSauvegarder;
    Button bEffacer;
    Button bAjouter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biere);

        // Titre de l'activite
        setTitle("BIÈRE?!");

        // Initialisation de l'acces a la base de donnees
        handler = new SQLiteHandler(this);

        // On recueille les donnees envoyees par la page precedente
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        Biere biereSelectionne = (Biere) b.get("Biere");
        nomBiereSelectionnee = biereSelectionne.getNomBiere();
        idBiereSelectionnee = handler.getIdBiere(nomBiereSelectionnee);

        // On montre les infos de la bière selectionnée
        EditText ed = (EditText)findViewById(R.id.editText);
        AutoCompleteTextView ac1 = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView2);
        AutoCompleteTextView ac2 = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView4);
        Spinner sp = (Spinner)findViewById(R.id.spinner);
        RatingBar rb = (RatingBar)findViewById(R.id.ratingBar);
        ed.setText(biereSelectionne.getNomBiere());
        ac1.setText(biereSelectionne.getNomBrasserie());
        ac2.setText(biereSelectionne.getTypeBiere());
        rb.setRating(biereSelectionne.getRating());

        int positionArray = getPositionCouleurSpinner(biereSelectionne);
        sp.setSelection(positionArray);
        setButonVisible();

        //photo image
//        try{
//            InputStream is = new FileInputStream(FICHIER_PNG);
//            Bitmap bm = BitmapFactory.decodeStream(is);
//            ImageView imageView = (ImageView) findViewById(R.id.imageView);
//            imageView.setImageBitmap(bm);
//            is.close();
//        }
//        catch (Exception ex){
//
//        }

        // Auto-complete
        listTypeBiere = lireFichierTypeBiereEmulator();
        listNomBrasserie = lireFichierBrasserieEmulator();

        autoCompBrasserie = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
        adapterBrasserie = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listNomBrasserie);
        autoCompBrasserie.setThreshold(1);
        autoCompBrasserie.setAdapter(adapterBrasserie);

        autoCompTypeBiere = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView4);
        adapterTBiere = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listTypeBiere);
        autoCompTypeBiere.setThreshold(1);
        autoCompTypeBiere.setAdapter(adapterTBiere);

        spinner = (Spinner) findViewById(R.id.spinner);
        viewImage = (ImageView) findViewById(R.id.imageView);
        spinner.setOnItemSelectedListener(listener);
        biere.setNomBiere(nomBiere);
        addListenerOnRatingBar();
    }

    public int getPositionCouleurSpinner(Biere biereSelectionne) {
        List<String> couleurs = handler.getCouleursBiere();
        int positionArray = 0;

        for(int i = 0; i < couleurs.size(); i++)
        {
            if(couleurs.get(i).equals(biereSelectionne.getCouleur()))
            {
                positionArray = i;
                break;
            }

        }
        return positionArray;
    }

    public void setButonVisible() {
        bSauvegarder = (Button)findViewById(R.id.button);
        bEffacer = (Button)findViewById(R.id.button2);
        bAjouter = (Button)findViewById(R.id.button6);

        List<Biere> listeBieres = new ArrayList<Biere>();
        listeBieres = handler.getAllBieres();
        boolean idDansListe = false;
        for(int i = 1; i <= listeBieres.size(); i++)
        {
            if(idBiereSelectionnee == i)
            {
                idDansListe = true;
            }
        }
        if(idDansListe)
        {
            bSauvegarder.setVisibility(View.VISIBLE);
            bEffacer.setVisibility(View.VISIBLE);
            bAjouter.setVisibility(View.GONE);
        }
        else
        {
            bSauvegarder.setVisibility(View.GONE);
            bEffacer.setVisibility(View.GONE);
            bAjouter.setVisibility(View.VISIBLE);
        }


    }


    AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (i == 0) {
                viewImage.setBackgroundColor(Color.WHITE);
                biere.setCouleur("Non Défini");
            }
            if (i == 1) {
                viewImage.setBackgroundColor(Color.parseColor("#F7F1B8"));
                biere.setCouleur("Blanche");
            }
            if (i == 2) {
                viewImage.setBackgroundColor(Color.parseColor("#FFC933"));
                biere.setCouleur("Blonde");
            }
            if (i == 3) {
                viewImage.setBackgroundColor(Color.parseColor("#AB5605"));
                biere.setCouleur("Brune");
            }
            if (i == 4) {
                viewImage.setBackgroundColor(Color.parseColor("#38291B"));
                biere.setCouleur("Noire");
            }
            if (i == 5) {
                viewImage.setBackgroundColor(Color.parseColor("#CB0516"));
                biere.setCouleur("Rousse");
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };




    private void addListenerOnRatingBar() {
        RatingBar rate = (RatingBar) findViewById(R.id.ratingBar);

        rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                biere.setRating(rating);

            }
        });
    }


    public ArrayList<String> lireFichierBrasserieEmulator() {

        ArrayList<String> listNomBrasseriEmulator = new ArrayList<>();

        try {
            FileInputStream fis = openFileInput("fichierBrasseriesEmul.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            listNomBrasseriEmulator = (ArrayList<String>) ois.readObject();
            ois.close();
            fis.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error");
        }

        return listNomBrasseriEmulator;
    }

    public ArrayList<String> lireFichierTypeBiereEmulator() {

        ArrayList<String> listTypeBiereEmulator = new ArrayList<>();

        try {
            FileInputStream fis = openFileInput("fichierTypeBiereEmul.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            listTypeBiereEmulator = (ArrayList<String>) ois.readObject();
            ois.close();
            fis.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error");
        }

        return listTypeBiereEmulator;
    }



    public void EffacerBiere(View view) {
        handler.effacerBiere(idBiereSelectionnee);

        Toast.makeText(getApplicationContext(), "Bière effacée!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void ModifierBiere(View view) {

        EditText ed = (EditText)findViewById(R.id.editText);
        AutoCompleteTextView ac1 = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView2);
        AutoCompleteTextView ac2 = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView4);
        Spinner sp = (Spinner)findViewById(R.id.spinner);
        RatingBar rb = (RatingBar)findViewById(R.id.ratingBar);


        handler.modifierBiere(idBiereSelectionnee, ed.getText().toString(), ac1.getText().toString(),
                ac2.getText().toString(), sp.getSelectedItem().toString(), rb.getRating());

        Toast.makeText(getApplicationContext(), "Bière modiffiée!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void AjouterBiere(View view) {
        EditText ed = (EditText)findViewById(R.id.editText);
        AutoCompleteTextView ac1 = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView2);
        AutoCompleteTextView ac2 = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView4);
        Spinner sp = (Spinner)findViewById(R.id.spinner);
        RatingBar rb = (RatingBar)findViewById(R.id.ratingBar);

        List<Biere> listeBieres = new ArrayList<Biere>();
        listeBieres = handler.getAllBieres();
        int taille = listeBieres.size()+1;

        handler.ajouterBiere(taille, ed.getText().toString(), ac1.getText().toString(),
                ac2.getText().toString(), sp.getSelectedItem().toString(), rb.getRating());

        Toast.makeText(getApplicationContext(), "Bière ajoutée!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    int REQUEST_IMAGE_CAPTURE = 1;

    public void onClickPhoto(View view){
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePhotoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(imageBitmap);

            try {
                OutputStream os = openFileOutput("fichier.png", MODE_PRIVATE);
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.close();
            } catch (Exception ex) {

            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}