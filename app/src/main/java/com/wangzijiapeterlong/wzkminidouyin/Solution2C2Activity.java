package com.wangzijiapeterlong.wzkminidouyin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.wangzijiapeterlong.wzkminidouyin.bean.Feed;
import com.wangzijiapeterlong.wzkminidouyin.bean.PostVideoResponse;
import com.wangzijiapeterlong.wzkminidouyin.network.IMiniDouyinService;
import com.wangzijiapeterlong.wzkminidouyin.network.RetrofitManager;
import com.wangzijiapeterlong.wzkminidouyin.utils.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Solution2C2Activity extends AppCompatActivity {

    private VideoView videoView;
    private static final int REQUEST_VIDEO_CAPTURE = 1;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_VIDEO = 2;
    private static final String TAG = "Solution2C2Activity";
    private RecyclerView mRv;
    private List<Feed> mFeeds = new ArrayList<>();
    public Uri mSelectedImage;
    private Uri mSelectedVideo;
    public Button mBtn;
    public Button ok;
    private Button mBtnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution2_c2);
        initBtns();
        doit();
    }
private  void doit(){
    ok=findViewById(R.id.btn_picture);
    videoView = findViewById(R.id.img);
    ok.setOnClickListener(new View.OnClickListener(){
        @Override public void onClick(View v) {
           /* if (ContextCompat.checkSelfPermission(Solution2C2Activity.this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(Solution2C2Activity.this,
                    Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                    ) {
                ActivityCompat.requestPermissions(Solution2C2Activity.this,
                        new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA},
                        REQUEST_VIDEO_CAPTURE);
            } else {
                takeVideo();
            }*/
            ActivityCompat.requestPermissions(Solution2C2Activity.this,
                    new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA},
                    REQUEST_VIDEO_CAPTURE);
           takeVideo();
        }
    });
}
    private void takeVideo() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_VIDEO_CAPTURE: {
                //todo 判断权限是否已经授予

                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    takeVideo();
                    break;
                }


            }
        }
    }
    private void initBtns() {
        mBtn = findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                String s = mBtn.getText().toString();
                if (getString(R.string.select_an_image).equals(s)) {
                    chooseImage();
                } else if (getString(R.string.select_a_video).equals(s)) {
                    chooseVideo();
                } else if (getString(R.string.post_it).equals(s)) {
                    if (mSelectedVideo != null && mSelectedImage != null) {
                        postVideo();
                    } else {
                        throw new IllegalArgumentException("error data uri, mSelectedVideo = "+mSelectedVideo+", mSelectedImage = "+mSelectedImage);
                    }
                } else if ((getString(R.string.success_try_refresh).equals(s))) {
                    mBtn.setText(R.string.select_an_image);
                }
            }
        });

       // mBtnRefresh = findViewById(R.id.btn_refresh);
    }

    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE);
    }


    public void chooseVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"),
                PICK_VIDEO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            //todo 播放刚才录制的视频
            Uri videoUri = data.getData();
            videoView.setVideoURI(videoUri);
            videoView.start();
        }
        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");

        if (resultCode == RESULT_OK && null != data) {

            if (requestCode == PICK_IMAGE) {
                mSelectedImage = data.getData();
                Log.d(TAG, "selectedImage = " + mSelectedImage);
                mBtn.setText(R.string.select_a_video);
            } else if (requestCode == PICK_VIDEO) {
                mSelectedVideo = data.getData();
                Uri videoUri = data.getData();
                videoView.setVideoURI(videoUri);
                videoView.start();
                Log.d(TAG, "mSelectedVideo = " + mSelectedVideo);
                mBtn.setText(R.string.post_it);
            }
        }
    }

    private MultipartBody.Part getMultipartFromUri(String name, Uri uri) {
        // if NullPointerException thrown, try to allow storage permission in system settings
        File f = new File(ResourceUtils.getRealPath(Solution2C2Activity.this, uri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
        return MultipartBody.Part.createFormData(name, f.getName(), requestFile);
    }

    private void postVideo() {
        mBtn.setText("POSTING...");
        mBtn.setEnabled(false);
        RetrofitManager.get(IMiniDouyinService.HOST).create(IMiniDouyinService.class).createVideo("1120171199",
                "www", getMultipartFromUri("cover_image", mSelectedImage), getMultipartFromUri("video", mSelectedVideo)).enqueue(new Callback<PostVideoResponse>() {
            @Override
            public void onResponse(Call<PostVideoResponse> call, Response<PostVideoResponse> response) {
                Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + response.body() + "]");
                String toast;
                if (response.isSuccessful()) {
                    toast = "Post Success!";
                    mBtn.setText(R.string.success_try_refresh);
                } else {
                    Log.d(TAG, "onResponse() called with: response.errorBody() = [" + response.errorBody() + "]");
                    toast = "Post Failure...";
                    mBtn.setText(R.string.post_it);
                }
                Toast.makeText(Solution2C2Activity.this, toast, Toast.LENGTH_LONG).show();
                mBtn.setEnabled(true);
            }

            @Override public void onFailure(Call<PostVideoResponse> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                Toast.makeText(Solution2C2Activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                mBtn.setText(R.string.post_it);
                mBtn.setEnabled(true);
            }
        });
    }
}
