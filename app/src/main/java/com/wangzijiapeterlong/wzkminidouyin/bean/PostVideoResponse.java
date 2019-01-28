package com.wangzijiapeterlong.wzkminidouyin.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author Xavier.S
 * @date 2019.01.18 17:53
 */
public class PostVideoResponse {

    @SerializedName("url") private String url;
    @SerializedName("success") private boolean success;
    @SerializedName("item")
    private Feed item ;
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override public String toString() {
        return "PostVideoResponse{" +
                "playUrl='" + url + '\'' +
                ", success=" + success +
                '}';
    }
}
