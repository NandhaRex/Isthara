package com.tao.isthara.Model;

public class Issue_list_item {

    private int id;
    private String title;
    private String category;
    private String subcategory;
    private String ticketNo;
    private String date;
    private String status;
    private String desc;
    private String property;
    private String bedname;
    private String roomno;
    private int rating;

    public Issue_list_item(int id, String title, String cate, String date, String ticketNo, String status, String subCategory, String desc, String property, String bedname, String roomno, int rating) {
        this.id = id;
        this.title = title;
        this.category = cate;
        this.date = date;
        this.ticketNo = ticketNo;
        this.status = status;
        this.subcategory = subCategory;
        this.desc = desc;
        this.property = property;
        this.bedname = bedname;
        this.roomno = roomno;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public String getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public String getBedname() {
        return bedname;
    }

    public String getRoomno() {
        return roomno;
    }

    public String getProperty() {
        return property;
    }

    public int getRating() {
        return rating;
    }

}
