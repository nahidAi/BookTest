package com.test.booktestnotification;



public class Quote {
    public String name;
    public String content;
    public String more;
    public String img_adrress;
    public int id;

    public Quote(String name, String content, String more, String img_adrress, int id) {
        this.name = name;
        this.content = content;
        this.more = more;
        this.img_adrress = img_adrress;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public String getImg_adrress() {
        return img_adrress;
    }

    public void setImg_adrress(String img_adrress) {
        this.img_adrress = img_adrress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
