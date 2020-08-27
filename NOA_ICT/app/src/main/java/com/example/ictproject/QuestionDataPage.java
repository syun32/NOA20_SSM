package com.example.ictproject;

public class QuestionDataPage {
    String title;
    int idx;

    public QuestionDataPage(String title, int idx){
        this.title = title;
        this.idx = idx;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) {
        this.title = title;
    }

}
