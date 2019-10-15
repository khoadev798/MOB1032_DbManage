package vn.com.mob1032_lab6.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import vn.com.mob1032_lab6.Datamodel.Products;
import vn.com.mob1032_lab6.R;

public class CustomAdapter extends ArrayAdapter {

    private Context context;
    private int resource;
    private List<Products> listProducts;
    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Products> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listProducts = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_product, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            viewHolder.tv_source = (TextView) convertView.findViewById(R.id.tv_source);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_category = (TextView) convertView.findViewById(R.id.tv_category);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Products product = listProducts.get(position);
        viewHolder.tv_id.setText(String.valueOf(product.getProduct_id()));
        viewHolder.tv_source.setText(product.getProduct_image());
        viewHolder.tv_name.setText(product.getProduct_name());
        viewHolder.tv_category.setText(String.valueOf(product.getCategory_id()));
        viewHolder.tv_price.setText(String.valueOf(product.getProduct_price()));
        return convertView;
    }

    public class ViewHolder{
        private TextView tv_id;
        private TextView tv_source;
        private TextView tv_name;
        private TextView tv_category;
        private TextView tv_price;
    }
}
