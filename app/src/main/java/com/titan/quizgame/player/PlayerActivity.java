package com.titan.quizgame.player;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.titan.quizgame.R;
import com.titan.quizgame.util.Permissions;

public class PlayerActivity extends AppCompatActivity implements ImageListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);


        ((CircularImageView) findViewById(R.id.img_plus)).setOnClickListener(btn_notificacao_OnClickListener);
    }


    @Override
    public void imageAction() {
        ImagePickerActivity.showImagePickerOptions(this, new PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                //launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                //launchGalleryIntent();
            }
        });
    }


    CircularImageView.OnClickListener btn_notificacao_OnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            onProfileImageClick();

        }
    };

    void onProfileImageClick() {
        Permissions.requestCaptureImagePermission(this, this);
    }

}
