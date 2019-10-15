package vn.com.mob1032_lab6.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.com.mob1032_lab6.Datamodel.Products;
import vn.com.mob1032_lab6.MainActivity;

public class productDAO {
    public static final String TABLE_PRODUCT_NAME = "products";
    public static final String COLUMN_NAME_PRODUCT_ID = "product_id";
    public static final String COLUMN_PRODUCT_IMAGE = "product_image";
    public static final String COLUMN_PRODUCT_NAME = "product_name";
    public static final String COLUMN_CATEGORY_ID = "category_id";
    public static final String COLUMN_NAME_PRODUCT_PRICE = "product_price";

    public static final String CREATE_PRODUCT_TABLE =
            "CREATE TABLE "+TABLE_PRODUCT_NAME+" (" +
                    COLUMN_NAME_PRODUCT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_PRODUCT_IMAGE+" VARCHAR, " +
                    COLUMN_PRODUCT_NAME+" VARCHAR NOT NULL, " +
                    COLUMN_CATEGORY_ID+" INTEGER NOT NULL, " +
                    COLUMN_NAME_PRODUCT_PRICE + " DOUBLE NOT NULL, " +
                    "FOREIGN KEY("+COLUMN_CATEGORY_ID+") REFERENCES categories(category_id))";
    public static final String DROP_PRODUCTS = "DROP TABLE "+TABLE_PRODUCT_NAME;
    public static final String DELETE_PRODUCTDATA = "DELETE FROM "+TABLE_PRODUCT_NAME;
    SQLiteDatabase db;
    //Constructor
    public productDAO(SQLiteDatabase db) {
        this.db = db;
    }

    //Methods
    public void addProduct(Products product){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, product.getProduct_name());
        values.put(COLUMN_CATEGORY_ID, product.getCategory_id());
        values.put(COLUMN_PRODUCT_IMAGE, product.getProduct_image());
        values.put(COLUMN_NAME_PRODUCT_PRICE,product.getProduct_price());
        db.insert(TABLE_PRODUCT_NAME, null, values);
        Log.d("OK","Save succeeded!");
    }
    public List<Products> getAllProducts(){
        List<Products> listProducts = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_PRODUCT_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Products product = new Products();
                product.setProduct_id(cursor.getInt(0));
                product.setProduct_image(cursor.getString(1));
                product.setProduct_name(cursor.getString(2));
                product.setCategory_id(cursor.getInt(3));
                product.setProduct_price(cursor.getDouble(4));
                listProducts.add(product);
            }while(cursor.moveToNext());
        }
        return listProducts;
    }
    public Products getProduct(int id){
        Products product = new Products();
        String[] columns = {COLUMN_NAME_PRODUCT_ID, COLUMN_PRODUCT_IMAGE,
                COLUMN_PRODUCT_NAME, COLUMN_CATEGORY_ID, COLUMN_NAME_PRODUCT_PRICE};
        String selection = COLUMN_NAME_PRODUCT_ID + " = ?";
        String[] selectionArgs = {id+""};
        Cursor cursor = db.query(TABLE_PRODUCT_NAME, columns, selection, selectionArgs,null,null,null);
        if(cursor.moveToNext()){
            return new Products(cursor.getInt(0),
                    cursor.getString(1),cursor.getString(2),
                    cursor.getInt(3),cursor.getDouble(4));
        }
        return null;
    }
    public int updateProduct(Products product){
        ContentValues contentValues = new ContentValues();
        String selection = COLUMN_NAME_PRODUCT_ID + " = ?";
        String[] selectionArgs = {product.getProduct_id()+""};

        contentValues.put(COLUMN_PRODUCT_IMAGE, product.getProduct_image());
        contentValues.put(COLUMN_PRODUCT_NAME,product.getProduct_name());
        contentValues.put(COLUMN_CATEGORY_ID,product.getCategory_id());
        contentValues.put(COLUMN_NAME_PRODUCT_PRICE, product.getProduct_price());

        return db.update(TABLE_PRODUCT_NAME, contentValues,selection,selectionArgs);
    }
    public int deleteProduct(int id){
        String selection = COLUMN_NAME_PRODUCT_ID + " = ?";
        String[] selectionArgs = {id+""};
        return db.delete(TABLE_PRODUCT_NAME,selection,selectionArgs);
    }
}
