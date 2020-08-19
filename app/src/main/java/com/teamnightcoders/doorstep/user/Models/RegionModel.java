package com.teamnightcoders.doorstep.user.Models;

public class RegionModel {

    private String uid;
    private String closed_dueto;
    private String working_time;
    private int user_type;
    private String img_url;
    private String district;
    private String shop_region;
    private String seller_mail;
    private String shop_name;

    public RegionModel() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getClosed_dueto() {
        return closed_dueto;
    }

    public void setClosed_dueto(String closed_dueto) {
        this.closed_dueto = closed_dueto;
    }

    public String getWorking_time() {
        return working_time;
    }

    public void setWorking_time(String working_time) {
        this.working_time = working_time;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getShop_region() {
        return shop_region;
    }

    public void setShop_region(String shop_region) {
        this.shop_region = shop_region;
    }

    public String getSeller_mail() {
        return seller_mail;
    }

    public void setSeller_mail(String seller_mail) {
        this.seller_mail = seller_mail;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }
}
