package com.example.multipletask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SqliteDatabase extends SQLiteOpenHelper {

    static final String daatabaseName = "sqlitee.db";
    Context context;

    public SqliteDatabase(@Nullable Context context) {
        super(context, daatabaseName, null, 118);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String w = "create table userdaata " +
                "(id integer primary key autoincrement, title text,year text)";
        db.execSQL(w);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists userdaata");
        onCreate(db);
//

    }
    public void insertOrUpdateTheIncomeAndSavings(String username, String email, String pass){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+"userdaata",null);
        if(cursor.moveToNext()){
            //update the values of first row here
            SQLiteDatabase db = this.getWritableDatabase();
            int id=1;

            ContentValues c = new ContentValues();

            c.put("name", username);
            c.put("email", email);
            c.put("pass", pass);

           // Toast.makeText(context, "update", Toast.LENGTH_SHORT).show();
            //long r = db.update("userinformations", c, " id =?", new String[]{userid});
            long r = db.update("userdaata", c,  " id =?",new String[]{"1"});

        }else{
            //insert the value here
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues c = new ContentValues();
            c.put("name", username);
            c.put("email", email);
            c.put("pass", pass);
           // Toast.makeText(context, "first", Toast.LENGTH_SHORT).show();
            long r = db.insert("userdaata", null, c);
        }

        if(cursor!=null){
            cursor.close();
        }
        sqLiteDatabase.close();

    }

    public boolean inserData(String title,String year) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("title", title);
        c.put("year", year);
        long r = db.insert("userdaata", null, c);

        if (r == -1) {
          //  Toast.makeText(context, "data send noooot", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, "data send localdatabase ", Toast.LENGTH_SHORT).show();
            return true;

        }


    }
    public ArrayList<RetriveDataModel> readdata() {

        // on below line we are creating ae
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        //Cursor cursorCourses=db.rawQuery("SELECT * FROM userdaata where id= ?"  ,  new String[]{"1"});
        Cursor cursorCourses=db.rawQuery("SELECT * FROM userdaata "  , null);

        ArrayList<RetriveDataModel> spacificDateDataModelClass = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
//
            do {
                // on below line we are adding the data from cursor to our array list.
                spacificDateDataModelClass.add(new RetriveDataModel(cursorCourses.getString(1),
                        cursorCourses.getString(2)
                ));



            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return spacificDateDataModelClass;

    }
    public ArrayList<RetriveDataModel> readdatadouble(String title) {

        // on below line we are creating ae
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

       // Toast.makeText(context, ""+title, Toast.LENGTH_SHORT).show();
        Cursor cursorCourses=db.rawQuery("SELECT * FROM userdaata where title= ?"  ,  new String[]{title});

        ArrayList<RetriveDataModel> spacificDateDataModelClass = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
//
            do {
                // on below line we are adding the data from cursor to our array list.
                spacificDateDataModelClass.add(new RetriveDataModel(cursorCourses.getString(1),
                        cursorCourses.getString(2)
                ));



            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return spacificDateDataModelClass;

    }

    public boolean deleteData(String title){

        SQLiteDatabase db=this.getWritableDatabase();
      //  Toast.makeText(context, "delete="+title, Toast.LENGTH_SHORT).show();
        // Cursor cursor=db.rawQuery("select * from userinfo where email=?",   new String[]{ email});

        long r=db.delete("userdaata"," title =?",new String[]{ title});
        if (r==-1){
            Toast.makeText(context, "Error Found try again!", Toast.LENGTH_SHORT).show();
            return false;

        }else {
           Toast.makeText(context, "deleted successful", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
    public boolean updateData(String year, String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        Toast.makeText(context, "update="+title, Toast.LENGTH_SHORT).show();
        ContentValues c = new ContentValues();
       // c.put("title", titlenew);
        c.put("year", year);
              //long r = db.update("userinformations", c, " id =?", new String[]{userid});
        long r = db.update("userdaata", c,  "title =?",new String[]{title});

        if (r == -1) {
           /// Toast.makeText(context, "Error Found try again!", Toast.LENGTH_SHORT).show();
            return false;

        } else {
          //  Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
            return true;

        }

    }


}