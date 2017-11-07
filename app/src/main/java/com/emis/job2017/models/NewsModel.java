package com.emis.job2017.models;

import java.net.URL;
import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by jo5 on 02/11/17.
 */

public class NewsModel extends RealmObject {

    private int idArticle;
    private String title;
    private String content;
    private String contentNoHtml;
    private String preview;
    private String author;
    private Date date;
    private String link;

    public NewsModel(){
    }

    public NewsModel(int idArticle, String title, String content, String contentNoHtml, String preview, String author, Date date, String link){
        this.idArticle = idArticle;
        this.title = title;
        this.content = content;
        this.contentNoHtml = contentNoHtml;
        this.preview = preview;
        this.author = author;
        this.date = date;
        this.link = link;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
