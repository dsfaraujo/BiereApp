package com.example.eleves.tp2_biere;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by eleves on 2017-04-13.
 */

public class Biere implements Serializable {

    private  int id;
    private  String nomBiere;
    private  String nomBrasserie;
    private  String typeBiere;
    private  String couleur;
    private  float rating;
    public static ArrayList<String> listTypeBiere;
    public static ArrayList<String> listNomBrasserie;
    byte[] photo;



    /*public void addTypeBiere (String autoCompleteTextView) {

        if (listTypeBiere == null) {
            listTypeBiere = new ArrayList<>();
        }
        listTypeBiere.add(autoCompleteTextView);
    }*/

    /*public void addBrasserie (String autoCompleteTextView) {

        if (listNomBrasserie == null) {
            listNomBrasserie = new ArrayList<>();
        }
        listNomBrasserie.add(autoCompleteTextView);
    }*/

    public Biere(int id, String nomBiere, String nomBrasserie, String typeBiere, String couleur, float rating, byte[] photo) {

        this.id = id;
        this.nomBiere = nomBiere;
        this.nomBrasserie = nomBrasserie;
        this.typeBiere = typeBiere;
        this.couleur = couleur;
        this.rating = rating;
        this.photo = photo;
    }

    public Biere(){

    }



    @Override
    public String toString() {
        return /*"ID: " + id + '\n' +*/
                //nomBiere + '\n' +
                "Brasserie: " + nomBrasserie + '\n' +
                "Type: " + typeBiere + '\n'
                /*"Couleur = " + couleur + '\n' +
                "Rating = " + rating + '\n'*/;
    }


    public  int getId() {
        return id;
    }

    public  void setId(int id_biere) {
        this.id = id_biere;
    }

    public String getNomBiere() {
        return nomBiere;
    }

    public void setNomBiere(String nomBiere) {
        this.nomBiere = nomBiere;
    }

    public String getNomBrasserie() {
        return nomBrasserie;
    }

    public String setNomBrasserie(String nomBrasserie) {
        this.nomBrasserie = nomBrasserie;
        return nomBrasserie;
    }

    public String getTypeBiere() {
        return typeBiere;
    }

    public String setTypeBiere(String typeBiere) {
        this.typeBiere = typeBiere;
        return typeBiere;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    /*public ArrayList<String> getListNomBrasserie() {
        return listNomBrasserie;
    }

    public void setListNomBrasserie(ArrayList<String> listNomBrasserie) {
        this.listNomBrasserie = listNomBrasserie;
    }

    public ArrayList<String> getListTypeBiere() {
        return listTypeBiere;
    }

    public void setListTypeBiere(ArrayList<String> listTypeBiere) {
        this.listTypeBiere = listTypeBiere;
    }
*/
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}