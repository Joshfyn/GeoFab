package com.example.jadeegbe.geofab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;

import java.util.LinkedList;
import java.util.List;

public class FabDatabaseHelper extends SQLiteOpenHelper {

    private static final String DatabaseTAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "estimoteNearable.db";
    private static final String DB_TABLE_NAME = "EsimoteNearablesdb";

    //Declare columns names
    private static final String Col1 = "ID";
    private static final String Col2 = "timestamp";
    private static final String Col3 = "estimoteIdentifier";
    private static final String Col4 = "estimoteRSSI";


    public FabDatabaseHelper(Context context, String name) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTable = "CREATE TABLE " + DB_TABLE_NAME + "( " +
                Col1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Col2 + " INTEGER, " +
                Col3 + " TEXT, " +
                Col4 + " INTEGER " +
                ");";

        sqLiteDatabase.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        // you can implement here migration process
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME);
        this.onCreate(sqLiteDatabase);

    }


    //add a new row to the database
    public void addData(EstimotePackets estimotePackets) {

        ContentValues contentValues = new ContentValues();

        if (estimotePackets.get_estimoteRSSI() != null || estimotePackets.get_estimoteRSSI().isEmpty() && estimotePackets.getEstimoteIdentifier() != null || estimotePackets.getEstimoteIdentifier().isEmpty()){

            contentValues.put(Col2, estimotePackets.get_timestamp());
            contentValues.put(Col3, estimotePackets.getEstimoteIdentifier());
            contentValues.put(Col4, estimotePackets.get_estimoteRSSI());
        }



        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(DB_TABLE_NAME, null, contentValues);
        if (estimotePackets.get_estimoteRSSI() == null || estimotePackets.get_estimoteRSSI().isEmpty() && estimotePackets.getEstimoteIdentifier() == null || estimotePackets.getEstimoteIdentifier().isEmpty()){
            return;
        }
        sqLiteDatabase.close();


        Log.d(DatabaseTAG, "addData: Adding " + estimotePackets.get_timestamp() + " to " + DB_TABLE_NAME);
        Log.d(DatabaseTAG, "addData: Adding " + estimotePackets.getEstimoteIdentifier() + " to " + DB_TABLE_NAME);
        Log.d(DatabaseTAG, "addData: Adding " + estimotePackets.get_estimoteRSSI() + " to " + DB_TABLE_NAME);

    }

    //delete all data in the database
    public void deleteAllData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME);

    }

    //print out all the Estimote Package
    public List<EstimotePackets> allPackets() {
        List<EstimotePackets> estimotePackets = new LinkedList<EstimotePackets>();
        String query = "SELECT  * FROM " + DB_TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        EstimotePackets estimotePacket = null;

        if (cursor.moveToFirst()) {
            do {
                estimotePacket = new EstimotePackets();
                estimotePacket.set_id(Integer.parseInt(cursor.getString(0)));
                estimotePacket.set_timestamp(cursor.getString(1));
                estimotePacket.setEstimoteIdentifier(cursor.getString(2));
                estimotePacket.set_estimoteRSSI(cursor.getString(3));
                estimotePackets.add(estimotePacket);

                Log.d(DatabaseTAG, estimotePacket.toString());
            } while (cursor.moveToNext());
        }

        return estimotePackets;
    }


}
