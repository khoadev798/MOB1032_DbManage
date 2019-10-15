package vn.com.mob1032_lab6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import vn.com.mob1032_lab6.DAO.categoryDAO;
import vn.com.mob1032_lab6.DAO.productDAO;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "QLBH";
    public static final int DB_VERSION = 1;
    Context context;
    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(categoryDAO.CREATE_CATEGORY_TABLE);
        db.execSQL(productDAO.CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void hello(){
        Toast.makeText(context,"Hello",Toast.LENGTH_LONG).show();
    }
    public void reset(SQLiteDatabase db){
        db.execSQL(categoryDAO.DROP_CATEGORIES);
        db.execSQL(categoryDAO.CREATE_CATEGORY_TABLE);
        
    }
}
