package com.example.akash.MNNITMarketplace;

public class b_ooks {
    private String Image_uri;
    private String Title;
    private String Price;

    public b_ooks(){

    }

    public b_ooks(String image_uri, String title, String price){
        Price = price;
        Title = title;
        Image_uri = image_uri;
    }
    public String getImage(){
        return Image_uri;
    }
    public void setImage(String image){
        Image_uri = image;
    }
    public String getTitle(){
        return Title;
    }
    public void setTitle(String title){
        Title = title;
    }
    public String getPrice(){
        return Price;
    }
    public void setPrice(String price){
        Price = price;
    }

}
