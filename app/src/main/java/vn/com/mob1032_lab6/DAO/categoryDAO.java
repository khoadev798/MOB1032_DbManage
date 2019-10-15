package vn.com.mob1032_lab6.DAO;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.com.mob1032_lab6.Datamodel.Categories;

public class categoryDAO {
    public static final String TABLE_CATEGORY_NAME = "categories";
    public static final String CATEGORY_COLUMN_CATEGORY_ID = "category_id";
    public static final String CATEGORY_COLUMN_CATEGORY_NAME = "category_name";
    public static final String CATEGORY_COLUMN_DISPLAY_NAME = "display_name";

    public static final String CREATE_CATEGORY_TABLE =
            "CREATE TABLE "+TABLE_CATEGORY_NAME+" (" +
                    CATEGORY_COLUMN_CATEGORY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CATEGORY_COLUMN_CATEGORY_NAME+" VARCHAR NOT NULL, " +
                    CATEGORY_COLUMN_DISPLAY_NAME+" VARCHAR NOT NULL" +
                    ")";
    public static final String DROP_CATEGORIES = "DROP TABLE "+TABLE_CATEGORY_NAME;
    public static final String DELETE_CATEGORYDATA = "DELETE FROM " + TABLE_CATEGORY_NAME;
    SQLiteDatabase db;

    public categoryDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public void addCategory(Categories cateogory){
        ContentValues values = new ContentValues();
        values.put(CATEGORY_COLUMN_CATEGORY_NAME,cateogory.getCategory_name());
        values.put(CATEGORY_COLUMN_DISPLAY_NAME,cateogory.getDisplay_name());
        db.insert(TABLE_CATEGORY_NAME,null, values);
    }
    public List<Categories> getAllCategories(){
        List categories = new ArrayList<>();
        String[] columns = {CATEGORY_COLUMN_CATEGORY_ID,CATEGORY_COLUMN_CATEGORY_NAME,CATEGORY_COLUMN_DISPLAY_NAME};

        Cursor cursor = db.query(TABLE_CATEGORY_NAME, columns,null,null,null,null,null);
        while (cursor.moveToNext()){
            categories.add(new Categories(cursor.getInt(0),
                    cursor.getString(1),cursor.getString(2)));
        }
        return categories;
    }
    public Categories getCategory(int id){
        String[]columns = {CATEGORY_COLUMN_CATEGORY_ID,CATEGORY_COLUMN_CATEGORY_NAME,CATEGORY_COLUMN_DISPLAY_NAME};
        String selection = CATEGORY_COLUMN_CATEGORY_ID+"= ?";
        String[] selectionArgs = {id+""};
        Cursor cursor = db.query(TABLE_CATEGORY_NAME,columns,selection,selectionArgs,null,null,null);
        if(cursor.moveToNext()){
            return new Categories(cursor.getInt(0),
                    cursor.getString(1),cursor.getString(2));
        }
        return  null;
    }
    public int updateCategory(Categories category){
        ContentValues contentValues = new ContentValues();
        String selection = CATEGORY_COLUMN_CATEGORY_ID + " = ?";
        String[] selectionArgs = {category.getCategory_id() +""};
        contentValues.put(CATEGORY_COLUMN_CATEGORY_NAME, category.getCategory_name());
        contentValues.put(CATEGORY_COLUMN_DISPLAY_NAME, category.getDisplay_name());
        return db.update(TABLE_CATEGORY_NAME, contentValues, selection,selectionArgs);
    }
    public int deleteCategory(int id){
        String selection = CATEGORY_COLUMN_CATEGORY_ID+" = ?";
        String[] selectionArgs = {id+""};

        return db.delete(TABLE_CATEGORY_NAME,selection,selectionArgs);
    }
}
