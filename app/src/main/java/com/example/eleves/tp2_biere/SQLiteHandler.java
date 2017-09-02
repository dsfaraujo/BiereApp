package com.example.eleves.tp2_biere;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by eleves on 2017-04-20.
 */

public class SQLiteHandler extends SQLiteAssetHelper {

    // Version de la base de données
    private static final int DATABASE_VERSION = 3;

    // Nom de la base de données
    public static final String DATABASE_NAME = "dbBiere2";

    // ************************* BIÈRE ************************************
    // Nom de la table: Biere
    private static final String TABLE_BIERE = "Biere";

    // Nom des attributs Biere
    private static final String ID_BIERE = "id_BIERE";
    private static final String NOM_BIERE = "nom";
    private static final String NOM_BRASSERIE_BIERE = "brasserie";
    private static final String TYPE_BIERE = "type";
    private static final String COULEUR_BIERE = "couleur";
    private static final String EVALUATION_BIERE = "evaluation";
    private static final String PHOTO_BIERE = "photo";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    public List<Biere> getAllBieres() {
        List<Biere> listeBieres = new ArrayList<Biere>();

        final String selectQuery = "SELECT * FROM " + TABLE_BIERE;
        System.out.println("Query Select All = " + selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                Biere biere = new Biere();

                biere.setId(Integer.parseInt(cursor.getString(0)));
                biere.setNomBiere(cursor.getString(1));
                biere.setNomBrasserie(cursor.getString(2));
                biere.setTypeBiere(cursor.getString(3));
                biere.setCouleur(cursor.getString(4));
                biere.setRating(Float.parseFloat(cursor.getString(5)));

//                byte []image = cursor.getBlob(6);

//                if(cursor.getBlob(6) == null)
//                {
//                    System.out.println("Image null");
//                }
                //BitmapFactory.decodeByteArray(image, 0, image.length);
//                biere.setPhoto(image);
//

                //System.out.println("Biere: /n" + biere);

                listeBieres.add(biere);


            } while (cursor.moveToNext());
        }
        db.close();
        return listeBieres;
    }

    public int getIdBiere(String nomBiere) {
        int id = 0;

        final String selectQuery = "SELECT " + ID_BIERE + " FROM " + TABLE_BIERE +
                " WHERE  " + NOM_BIERE + " = " + "\"" + nomBiere + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);



        if (cursor.moveToFirst()) {
            do {
                Biere biere = new Biere();

                biere.setId(Integer.parseInt(cursor.getString(0)));
                id = biere.getId();

            } while (cursor.moveToNext());
        }


        return id;
    }

    public List<String> getCouleursBiere()
    {
        List<String> listeCouleurs = new ArrayList<String>();
        listeCouleurs.add(" - Sélectionnez - ");
        listeCouleurs.add("Blanche");
        listeCouleurs.add("Blonde");
        listeCouleurs.add("Brune");
        listeCouleurs.add("Noire");
        listeCouleurs.add("Rousse");

        return listeCouleurs;
    }

    public void ajouterBiere(int taille, String nomBiere, String brasserie, String type, String couleur, float evaluation)
    {
        final String selectQuery = "INSERT INTO " + TABLE_BIERE + " VALUES (" +
                taille +
                ", " + "\"" + nomBiere + "\"" +
                ", " + "\"" + brasserie + "\"" +
                ", " + "\"" + type + "\"" +
                ", " + "\"" + couleur + "\"" +
                ", " + evaluation +
                ", " + null + ")";

        System.out.println("Query Insert = " + selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(selectQuery);
        db.close();

    }

    public void modifierBiere(int id_biere, String nomBiere, String brasserie, String type, String couleur, float evaluation)
    {
        final String selectQuery = "UPDATE " + TABLE_BIERE + " SET " +
                NOM_BIERE + " = " + "\"" + nomBiere + "\"" + ", " +
                NOM_BRASSERIE_BIERE + " = " + "\"" + brasserie + "\"" + ", " +
                TYPE_BIERE + " = " + "\"" + type + "\"" + ", " +
                COULEUR_BIERE + " = " + "\"" + couleur + "\"" + ", " +
                EVALUATION_BIERE + " = " + evaluation +
                " WHERE " + ID_BIERE + " = " + id_biere;

        System.out.println("Query Update = " + selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(selectQuery);
        db.close();

    }

    public void effacerBiere(int id_biere)
    {
        final String selectQuery = "DELETE FROM " + TABLE_BIERE + " WHERE " + ID_BIERE + " = " + id_biere;

        System.out.println("Query Delete = " + selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(selectQuery);
        db.close();


    }



}