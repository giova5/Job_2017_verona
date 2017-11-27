package com.emis.job2017.models;

import java.net.URL;
import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by jo5 on 02/11/17.
 */

public class NewsModel extends RealmObject {

    private int idArticle;
    private boolean notification;
    private String title;
    private String content;
    private String contentNoHtml;
    private String preview;
    private String author;
    private long date;
    private String link;

    public NewsModel(){
    }

    public NewsModel(int idArticle, boolean notification, String title, String content, String contentNoHtml, String preview, String author, long date, String link){
        this.idArticle = idArticle;
        this.notification = notification;
        this.title = title;
        this.content = content;
        this.contentNoHtml = contentNoHtml;
        this.preview = preview;
        this.author = author;
        this.date = date;
        this.link = link;
    }

    public boolean getNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentNoHtml() {
        return contentNoHtml;
    }

    public void setContentNoHtml(String contentNoHtml) {
        this.contentNoHtml = contentNoHtml;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    /*
* Avoids issues with Realm's thread policy
* */
    public static NewsModel cloneObject(NewsModel newsModel) {
        return new NewsModel(
                newsModel.getIdArticle(),
                newsModel.getNotification(),
                newsModel.getTitle(),
                newsModel.getContent(),
                newsModel.getContentNoHtml(),
                newsModel.getPreview(),
                newsModel.getAuthor(),
                newsModel.getDate(),
                newsModel.getLink()
        );
    }
}
