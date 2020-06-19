package com.zefaf.zefaffinal.Model;

public class Hajz {
    private String name,price,adress,dese;
    private int img,imgfav,imgloction;

    public Hajz(String name, String price, String adress,String dese, int img, int imgfav, int imgloction) {
        this.name = name;
        this.price = price;
        this.adress = adress;
        this.dese = dese;
        this.img = img;
        this.imgfav = imgfav;
        this.imgloction = imgloction;
    }

    public Hajz() {
    }

    public int getImgloction() {
        return imgloction;
    }

    public void setImgloction(int imgloction) {
        this.imgloction = imgloction;
    }

    public String getDese() {
        return dese;
    }

    public void setDese(String dese) {
        this.dese = dese;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getImgfav() {
        return imgfav;
    }

    public void setImgfav(int imgfav) {
        this.imgfav = imgfav;
    }

}
