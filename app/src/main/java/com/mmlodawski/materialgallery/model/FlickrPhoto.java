/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.model;
import com.google.gson.annotations.SerializedName;

public class FlickrPhoto {

    @SerializedName("id")
    private String id;

    @SerializedName("owner")
    private String owner;

    @SerializedName("secret")
    private String secret;

    @SerializedName("server")
    private String server;

    @SerializedName("farm")
    private int farm;

    @SerializedName("title")
    private String title;

    @SerializedName("ispublic")
    private int isPublic;

    @SerializedName("isfriend")
    private int isFriend;

    @SerializedName("isfamily")
    private int isFamily;

    public void setId(String id) {
        this.id = id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }

    public void setIsFamily(int isFamily) {
        this.isFamily = isFamily;
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getSecret() {
        return secret;
    }

    public String getServer() {
        return server;
    }

    public int getFarm() {
        return farm;
    }

    public String getTitle() {
        return title;
    }

    public int isPublic() {
        return isPublic;
    }

    public int isFriend() {
        return isFriend;
    }

    public int isFamily() {
        return isFamily;
    }
}
