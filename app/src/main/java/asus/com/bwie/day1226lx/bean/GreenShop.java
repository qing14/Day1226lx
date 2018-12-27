package asus.com.bwie.day1226lx.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class GreenShop {
    @Id(autoincrement = false)
    private long pid;
    private double price;
    private String title;
    private String images;
    @Generated(hash = 1397022597)
    public GreenShop(long pid, double price, String title, String images) {
        this.pid = pid;
        this.price = price;
        this.title = title;
        this.images = images;
    }
    @Generated(hash = 601635634)
    public GreenShop() {
    }
    public long getPid() {
        return this.pid;
    }
    public void setPid(long pid) {
        this.pid = pid;
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getImages() {
        return this.images;
    }
    public void setImages(String images) {
        this.images = images;
    }
}
