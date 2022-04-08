package com.mostafamohamed.Chat.Notifications;

public class Data {
    private String user;
    private int icon;
    private String body;
    private String title;
    private String sented;

    public Data(String user,int icon,String body,String title,String sented){
        this.user=user;
        this.icon=icon;
        this.body=body;
        this.title=title;
        this.sented=sented;
    }
    public Data(){

    }

    public String getUser() {
        return user;
    }

    public int getIcon() {
        return icon;
    }

    public String getBody() {
        return body;
    }

    public String getTitle() {
        return title;
    }

    public String getSented() {
        return sented;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setSented(String sented) {
        this.sented = sented;
    }
}
