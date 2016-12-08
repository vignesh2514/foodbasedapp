package com.v7ench.askchitvish;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vicky on 17/11/16.
 */

public class Subcategory implements Parcelable{
    private String id;
    private String topic_name;
    private String short_desc;
    private String ingredients1;
    private String method1;
    private String order_no;
    private String times_viewed;
    private String active;
    private String extra;
    private String images;
    private String audio;
    private String video;
    private String favon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getShort_desc() {
        return short_desc;
    }

    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }

    public String getIngredients1() {
        return ingredients1;
    }

    public void setIngredients1(String ingredients1) {
        this.ingredients1 = ingredients1;
    }

    public String getMethod1() {
        return method1;
    }

    public void setMethod1(String method1) {
        this.method1 = method1;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getTimes_viewed() {
        return times_viewed;
    }

    public void setTimes_viewed(String times_viewed) {
        this.times_viewed = times_viewed;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getFavon() {
        return favon;
    }

    public void setFavon(String favon) {
        this.favon = favon;
    }

    public static Creator<Subcategory> getCREATOR() {
        return CREATOR;
    }

    protected Subcategory(Parcel in) {
        id = in.readString();
        topic_name = in.readString();
        short_desc = in.readString();
        ingredients1 = in.readString();
        method1 = in.readString();
        order_no = in.readString();
        times_viewed = in.readString();
        active = in.readString();
        extra = in.readString();
        images = in.readString();
        audio = in.readString();
        video = in.readString();
        favon = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(topic_name);
        dest.writeString(short_desc);
        dest.writeString(ingredients1);
        dest.writeString(method1);
        dest.writeString(order_no);
        dest.writeString(times_viewed);
        dest.writeString(active);
        dest.writeString(extra);
        dest.writeString(images);
        dest.writeString(audio);
        dest.writeString(video);
        dest.writeString(favon);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Subcategory> CREATOR = new Creator<Subcategory>() {
        @Override
        public Subcategory createFromParcel(Parcel in) {
            return new Subcategory(in);
        }

        @Override
        public Subcategory[] newArray(int size) {
            return new Subcategory[size];
        }
    };
}
