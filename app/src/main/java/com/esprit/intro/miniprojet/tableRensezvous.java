package com.esprit.intro.miniprojet;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
public class tableRensezvous {
    public  static final String DataBase_name="rdv.db";
    public  static final int DataBase_version=1;
    public  static final String Table_match="Rendez_vous";
    public  static final String Column_1="titre";
    public  static final String Column_2="date";
    public  static final String Column_3="approuve";
    public  static final String Column_4="id_patient";
    public  static final String Column_5="nompatient";
    public  static final String Column_6="prenompatient";
    public  static final String Column_7="detailrdv";
    public  static final String Column_8="idrdv";
    public  static final String Column_9="dateann";
    public  static final String Column_10="urlimage";
    public  static final String Column_11="numtel";
    public  static final String Column_12="proff";
    private SQLiteDatabase bdd;

    private Databaseceate maBaseSQLite;


    Context context;

    public Context getContext() {
        return context;
    }

    public tableRensezvous(Context context){

        maBaseSQLite = new Databaseceate(context,DataBase_name, null,DataBase_version);}

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertcontact(RendezVous match){
        //Création d'un ContentValues (fonctionne comme une HashMap)
         ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(Column_1,match.getTitre());
        values.put(Column_2,match.getDate());
        values.put(Column_3, match.getApprouve());
        values.put(Column_4,match.getId_patient());
        values.put(Column_5,match.getNompatient());
        values.put(Column_6, match.getPrenompatient());
        values.put(Column_7,match.getDetailrdv());
        values.put(Column_8,match.getId());
        values.put(Column_9,match.getDateann());
        values.put(Column_10,match.getImg());
        values.put(Column_11,match.getNumtelp());
        values.put(Column_12,match.getProfessp());

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(Table_match, null, values);
    }


    public Cursor getallContacts(){
        Cursor c = bdd.rawQuery("" +"SELECT * FROM Rendez_vous ORDER BY ID DESC LIMIT 10;",null);
        return c;
    }

    public Cursor getallpatient(long id){
        Cursor c = bdd.rawQuery("" +"SELECT * FROM Rendez_vous WHERE idrdv="+id+";",null);
        return c;
    }

    public Cursor getallconsultations(long iduser){
        Cursor c = bdd.rawQuery("" +"SELECT * FROM Rendez_vous WHERE id_patient="+iduser+";",null);
        return c;
    }

















}
