package com.example.hadan.todolist;

import java.util.Date;

public class Note {
    private int id;
    private String title;
    private String content;
    private Date dateCreate;

    public int getId(){
        return id;
    }

    public void setId(int ID){
        id = ID;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String Tile){
        title = Tile;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String Content){
        content = Content;
    }

    public Date getDateCreate(){
        return dateCreate;
    }

    public void setDateCreate(Date date){
        dateCreate = date;
    }

    public Note(){

    }

    public Note(int id, String tile, String content, Date dateCreate){
        this.id = id;
        this.title = tile;
        this.content = content;
        this.dateCreate = dateCreate;
    }

    public Note(int id, String tile, String content){
        this.id = id;
        this.title = tile;
        this.content = content;
    }
}
