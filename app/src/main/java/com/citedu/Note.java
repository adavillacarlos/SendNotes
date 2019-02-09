package com.citedu;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Note implements Serializable {
    private long mDateTime;
    private String mTitle;
    private String mContent;

    public Note(long dateTime, String title, String content) {
        this.mDateTime = dateTime;
        this.mTitle = title;
        this.mContent = content;
    }
    public void setDateTime(long DateTime) {
        this.mDateTime = DateTime;
    }
    public void setTitle(String Title) {
        this.mTitle = Title;
    }
    public void setContent(String Content) {
        this.mContent = Content;
    }


    public long getDateTime() {
        return mDateTime;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public String getDateTimeFormatted(Context context){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"
                , context.getResources().getConfiguration().locale);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(mDateTime));
    }
}
