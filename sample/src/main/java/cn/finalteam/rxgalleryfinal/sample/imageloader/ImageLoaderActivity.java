package cn.finalteam.rxgalleryfinal.sample.imageloader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.Toast;

import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.sample.R;

/**
 * by y on 2017/6/7.
 */

public class ImageLoaderActivity extends AppCompatActivity {

    private AppCompatCheckBox appCompatCheckBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageloader);
        appCompatCheckBox = (AppCompatCheckBox) findViewById(R.id.cb_gif);
        findViewById(R.id.btn_glide).setOnClickListener(v -> start(ImageLoaderType.GLIDE));
        findViewById(R.id.btn_picasso).setOnClickListener(v -> start(ImageLoaderType.PICASSO));
        findViewById(R.id.btn_fresco).setOnClickListener(v -> start(ImageLoaderType.FRESCO));
        findViewById(R.id.btn_universal).setOnClickListener(v -> start(ImageLoaderType.UNIVERSAL));
    }

    private void start(ImageLoaderType imageLoaderType) {
        switch (imageLoaderType) {
            case PICASSO:
            case UNIVERSAL:
                Toast.makeText(getApplicationContext(), imageLoaderType + "不支持Gif", Toast.LENGTH_SHORT).show();
                break;
        }
        RxGalleryFinal
                .with(this)
                .image()
                .radio()
                .gif(appCompatCheckBox.isChecked())
                .imageLoader(imageLoaderType)
                .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                        Toast.makeText(getBaseContext(), "选中了图片路径：" + imageRadioResultEvent.getResult().getOriginalPath(), Toast.LENGTH_SHORT).show();
                    }
                }).openGallery();
    }
}
