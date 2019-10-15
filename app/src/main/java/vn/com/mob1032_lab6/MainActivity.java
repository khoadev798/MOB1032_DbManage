package vn.com.mob1032_lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import vn.com.mob1032_lab6.Adapter.CustomAdapter;
import vn.com.mob1032_lab6.DAO.categoryDAO;
import vn.com.mob1032_lab6.DAO.productDAO;
import vn.com.mob1032_lab6.Datamodel.Categories;
import vn.com.mob1032_lab6.Datamodel.Products;

import static vn.com.mob1032_lab6.DAO.productDAO.*;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private EditText txt_ProductName;
    private EditText txt_Price;
    private EditText txt_Category;
    private EditText txt_Source;
    private Button btn_Save;
    private Button btn_Find;
    private Button btn_Delete;
    private Button btn_Update;
    private ListView lv_main;
    private CustomAdapter customAdapter;
    private List<Products> productList;
    private int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapComp();
        final String tag = "Check";
        DbHelper dbHelper = new DbHelper(this);
        dbHelper.hello();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.d(tag, db.getVersion() + "");
        //Reset all table
        dbHelper.reset(db);
        //3 Categories
        Categories category1 = new Categories("Xe máy", "Xe máy xịn");
        Categories category2 = new Categories("Xe hơi", "Xe hơi xịn");
        Categories category3 = new Categories("Máy bay", "Máy bay xịn");
        //Add new categories
        categoryDAO categoryDAO = new categoryDAO(db);
        categoryDAO.addCategory(category1);
        categoryDAO.addCategory(category2);
        categoryDAO.addCategory(category3);
        final productDAO productDAO = new productDAO(db);

        productList = productDAO.getAllProducts();
        setAdapter();
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Products product = createProduct();
                if(product!=null){
                productDAO.addProduct(product);}
                productList.clear();
                productList.addAll(productDAO.getAllProducts());
                setAdapter();
            }
        });

        lv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,  int position, long id) {
                lv_main.setSelected(true);
                /*Toast toast = Toast.makeText(MainActivity.this, "Pos : " +position+ " / id : " + id, Toast.LENGTH_SHORT);
                toast.show();*/
                Products product = productList.get(position);
                i=product.getProduct_id();
                txt_ProductName.setText(product.getProduct_name());
                txt_Source.setText(product.getProduct_image());
                txt_Category.setText(product.getCategory_id()+"");
                txt_Price.setText(product.getProduct_price()+"");
            }
        });
        btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productDAO.deleteProduct(i);
                productList.clear();
                productList.addAll(productDAO.getAllProducts());
                setAdapter();

            }
        });
        btn_Update.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Products product = productList.get(i-1);
                product.setProduct_name(txt_ProductName.getText().toString());
                product.setProduct_image(txt_Source.getText().toString());
                product.setCategory_id(Integer.parseInt(txt_Category.getText().toString()));
                product.setProduct_price(Double.parseDouble(txt_Price.getText().toString()));

                productDAO.updateProduct(product);
                productList.clear();
                productList.addAll(productDAO.getAllProducts());
                setAdapter();
            }
        });
    }


    public void mapComp() {
        if (txt_ProductName == null)
            txt_ProductName = findViewById(R.id.txt_ProductName);
        if (txt_Category == null)
            txt_Category = findViewById(R.id.txt_Category);
        if (txt_Price == null)
            txt_Price = findViewById(R.id.txt_Price);
        if (txt_Source == null)
            txt_Source= findViewById(R.id.txt_Source);
        if (btn_Save == null)
            btn_Save = findViewById(R.id.btn_Save);
        if(lv_main == null)
            lv_main = findViewById(R.id.lv_main);
        if(btn_Find == null)
            btn_Find = findViewById(R.id.btn_Find);
        if(btn_Delete==null)
            btn_Delete = findViewById(R.id.btn_Delete);
        if(btn_Update==null)
            btn_Update = findViewById(R.id.btn_Update);
    }
    public Products createProduct(){
        String name = txt_ProductName.getText().toString();
        String source = txt_Source.getText().toString();
        int category = Integer.parseInt(String.valueOf(txt_Category.getText()));
        double price = Double.parseDouble(String.valueOf(txt_Price.getText()));
        Products product = new Products(source, name, category, price);
        return product;
    }
    private void setAdapter(){
        if(customAdapter==null){
            customAdapter = new CustomAdapter(this, R.layout.item_listview_product, productList);
            lv_main.setAdapter(customAdapter);
        }else{
            customAdapter.notifyDataSetChanged();
            lv_main.setSelection(customAdapter.getCount()-1);
        }
    }
    /*private void showDetails(){
        int i = lv_main.getSelectedItemPosition();
        Products product = productList.get(i);
        txt_ProductName.setText(product.getProduct_name());
        txt_Source.setText(product.getProduct_image());
        txt_Category.setText(product.getCategory_id()+"");
        txt_Price.setText(product.getProduct_price()+"");}*/

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

