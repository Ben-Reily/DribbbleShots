package com.benreily.zzzonked.model.pojo;

import android.text.Html;
import android.text.Spanned;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DribbbleShotModel extends RealmObject {

    @Expose
    @SerializedName("id")
    @PrimaryKey
    private long id;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("images")
    private ImagesModel images;

    @Expose
    @SerializedName("animated")
    private boolean animated;


    public String getDescriptionNoHtml() {
        if (this.description == null) {
            return "";
        }

        Spanned spanned;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            spanned = Html.fromHtml(getDescription(), Html.FROM_HTML_MODE_LEGACY);
        } else {
            spanned = Html.fromHtml(getDescription());
        }
        return spanned.toString();
    }


    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImagesModel getImages() {
        return images;
    }

    public void setImages(ImagesModel images) {
        this.images = images;
    }
}
