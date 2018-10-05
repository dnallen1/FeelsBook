package com.example.feelsbook;

/* This class contains all Emotion, comment, and date associated with the selected emotion
    It also compares the dates of the objects in order to sort all the comments by date
    Inputs: String emotion, comment, date
 */
public class Comment implements Comparable<Comment>{
    protected String emotion;
    protected String comment;
    protected String date;

    public Comment(String emotion, String comment, String date) {
        this.emotion = emotion;
        this.comment = comment;
        this.date = date;
    }

    public String getEmotion() {
        return this.emotion;
    }

    public String getComment() {
        return this.comment;
    }

    public String getDate() {
        return this.date;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /* Author: Domchi
    Date: May 8, 2011
    Title: Sort objects in ArrayList by date?
    Source: https://stackoverflow.com/questions/5927109/sort-objects-in-arraylist-by-date
    */
    @Override
    public int compareTo(Comment sortComment) {
        return getDate().compareTo(sortComment.getDate());
    }
}
