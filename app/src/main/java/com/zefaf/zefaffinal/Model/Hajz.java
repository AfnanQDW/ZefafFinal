package com.zefaf.zefaffinal.Model;

public class Hajz {
    private String name, price, adress, dese;
    private String link;
    private String link2;

    public Hajz(String name, String price, String adress, String dese, String link, String link2) {
        this.name = name;
        this.price = price;
        this.adress = adress;
        this.dese = dese;
        this.link = link;
        this.link2 = link2;
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

    public String getLink2() {
        return link2;
    }

    public void setLink2(String link2) {
        this.link2 = link2;
    }
}
