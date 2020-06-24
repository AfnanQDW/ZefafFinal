package com.zefaf.zefaffinal.Model;

public class Hajz {
    private String name,price,adress,dese;
    private String link;

    public Hajz(String name, String price, String adress,String dese, String link) {
        this.name = name;
        this.price = price;
        this.adress = adress;
        this.dese = dese;
        this.link = link;
    }

    public Hajz() {
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
