package com.devbyaxim.govtjobsprepapp.Admin.model;

public class PostModel {
    String filePath,name,type,shortDesc,longDesc,title,year,category,QR;

    public PostModel() {
    }

    public PostModel(String filePath, String name, String type, String shortDesc, String longDesc, String title, String year, String category, String QR) {
        this.filePath = filePath;
        this.name = name;
        this.type = type;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.title = title;
        this.year = year;
        this.category = category;
        this.QR = QR;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQR() {
        return QR;
    }

    public void setQR(String QR) {
        this.QR = QR;
    }
}
