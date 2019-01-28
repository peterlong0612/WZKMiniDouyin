package com.wangzijiapeterlong.wzkminidouyin.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author Xavier.S
 * @date 2019.01.20 14:18
 */
public class Feed {

    /**
     * student_id : 32336667
     * user_name : aha
     * image_url : http://10.95.44.60:8080/minidouyin/storage/image?path=32336667/aha/1547985040823/Screenshot_2019-01-20-12-49-37-834_com.ss.android.ugc.aweme.png
     * video_url : http://10.95.44.60:8080/minidouyin/storage/video?path=32336667/aha/1547985040824/b063fc96c6fd7a570180b6acccd7569d.mp4
     */

    @SerializedName("student_id") private String studentId;
    @SerializedName("user_name") private String userName;
    @SerializedName("image_url") private String imageUrl;
    @SerializedName("video_url") private String videoUrl;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
