package com.titan.quizgame.player;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.titan.quizgame.R;
import com.titan.quizgame.player.models.Board;
import com.titan.quizgame.util.ActivityRequestCode;
import com.titan.quizgame.util.Image;
import com.titan.quizgame.util.ImageCropConstants;
import com.titan.quizgame.util.Permissions;

import butterknife.BindView;


public class PlayerProfileActivity extends AppCompatActivity implements ImageListener{


    @BindView(R.id.txt_name)
    TextView txt_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profile);

        ((CircularImageView) findViewById(R.id.img_plus)).setOnClickListener(btn_notificacao_OnClickListener);
    }



    private void fillProfile(Board board){

        /*
        img_profile
                txt_name
        profile_desc
                txt_difficulty
        txt_score
                txt_category
                */

    }

    @Override
    public void imageAction() {
        ImagePickerActivity.showImagePickerOptions(this, new PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }


    private void launchCameraIntent() {

        Intent intent = new Intent(this, ImagePickerActivity.class);
        intent.putExtra(ImageCropConstants.INTENT_IMAGE_PICKER_OPTION, ActivityRequestCode.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImageCropConstants.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImageCropConstants.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImageCropConstants.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImageCropConstants.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImageCropConstants.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImageCropConstants.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, ActivityRequestCode.REQUEST_IMAGE);
    }


    private void launchGalleryIntent() {
        Intent intent = new Intent(this, ImagePickerActivity.class);
        intent.putExtra(ImageCropConstants.INTENT_IMAGE_PICKER_OPTION, ActivityRequestCode.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImageCropConstants.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImageCropConstants.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImageCropConstants.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, ActivityRequestCode.REQUEST_IMAGE);
    }


    CircularImageView.OnClickListener btn_notificacao_OnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            onProfileImageClick();

        }
    };

    void onProfileImageClick() {
        Permissions.requestImagePermission(this, this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ActivityRequestCode.REQUEST_IMAGE) {

            if (resultCode == RESULT_OK) {

                Uri uri = data.getParcelableExtra("path");

                // You can update this bitmap to your server
                Bitmap bitmap = Image.getBitmap(this, uri);

                // loading profile image from local cache
                //loadProfile(uri.toString());

            }
        }
    }


}
