package com.wangzijiapeterlong.wzkminidouyin.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Xavier.S
 * @date 2019.01.20 14:17
 */
public class FeedResponse {

    /**
     * feeds : [{"studentId":"3226778888","userName":"fake_user","imageUrl":"http://10.95.44.60:8080/minidouyin/image?path=32336667/aha/1547963737229/Screenshot_2019-01-20-12-49-37-834_com.ss.android.ugc.aweme.png","videoUrl":null},{"studentId":"3226778888","userName":"fake_user","imageUrl":"http://10.95.44.60:8080/minidouyin/image?path=32336667/aha/1547963957558/Screenshot_2018-09-13-19-11-54-188_com.ss.android.ugc.aweme.png","videoUrl":null},{"studentId":"3226778888","userName":"fake_user","imageUrl":"http://10.95.44.60:8080/minidouyin/image?path=32336667/aha/1547963986917/Screenshot_2019-01-20-12-49-37-834_com.ss.android.ugc.aweme.png","videoUrl":null},{"studentId":"3226778888","userName":"fake_user","imageUrl":"http://10.95.44.60:8080/minidouyin/image?path=32336667/aha/1547964011550/Screenshot_2019-01-20-12-52-14-680_com.ss.android.ugc.aweme.png","videoUrl":null},{"studentId":"3226778888","userName":"fake_user","imageUrl":"http://10.95.44.60:8080/minidouyin/image?path=32336667/aha/1547964056873/Screenshot_2019-01-20-12-52-26-291_com.ss.android.ugc.aweme.png","videoUrl":null}]
     * success : true
     */

    @SerializedName("success") private boolean success;
    @SerializedName("feeds") private List<Feed> feeds;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Feed> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
    }

    @Override public String toString() {
        return "FeedResponse{" +
                " success=" + success +
                ", feeds=" + feeds +
                '}';
    }
}
