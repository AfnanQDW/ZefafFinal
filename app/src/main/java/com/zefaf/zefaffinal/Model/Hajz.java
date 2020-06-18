package com.zefaf.zefaffinal.Model;

public class Hajz {
    private String succss;
    private String time;
    private int img;

    public Hajz(String succss, String time, int img) {
        this.succss = succss;
        this.time = time;
        this.img = img;
    }

    public String getSuccss() {
        return succss;
    }

    public void setSuccss(String name) {
        this.succss = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String email) {
        this.time = email;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
