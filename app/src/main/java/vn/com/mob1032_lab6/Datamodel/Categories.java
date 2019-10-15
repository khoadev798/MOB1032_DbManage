package vn.com.mob1032_lab6.Datamodel;

public class Categories {
    int category_id;
    String category_name;
    String display_name;

    public Categories(String category_name, String display_name) {
        this.category_name = category_name;
        this.display_name = display_name;
    }

    public Categories(int category_id, String category_name, String display_name) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.display_name = display_name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
}
