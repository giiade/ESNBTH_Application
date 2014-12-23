package se.ESNBTH.esnbth.ListView;

public class RowItem {

    private String shop_name;
    private int shop_pic_id;
    private String localisation;

    public RowItem(String shop_name, int shop_pic_id, String localisation) {

        this.shop_name = shop_name;
        this.shop_pic_id = shop_pic_id;
        this.localisation = localisation;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public int getShop_pic_id() {
        return shop_pic_id;
    }

    public void setShop_pic_id(int shop_pic_id) {
        this.shop_pic_id = shop_pic_id;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }


}