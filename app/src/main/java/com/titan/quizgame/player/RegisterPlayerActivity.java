package com.titan.quizgame.player;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.titan.quizgame.R;
import com.titan.quizgame.player.models.Board;
import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.util.constants.ActivityCode;
import com.titan.quizgame.util.constants.ActivityRequestCode;
import com.titan.quizgame.util.Image;
import com.titan.quizgame.util.ImageCropConstants;
import com.titan.quizgame.util.Permissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegisterPlayerActivity extends AppCompatActivity implements ImageListener{


    @BindView(R.id.txt_inp_lyt_name)
    TextInputLayout txt_inp_lyt_name;

    @BindView(R.id.txt_inp_lyt_message)
    TextInputLayout txt_inp_lyt_message;


    @BindView(R.id.txt_difficulty)
    TextView txt_difficulty;

    @BindView(R.id.txt_score)
    TextView txt_score;

    @BindView(R.id.txt_category)
    TextView txt_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profile);

        ButterKnife.bind(this);

        getIncomingIntent();
    }


    private void getIncomingIntent(){

        Intent intent = getIntent();
        Category category = intent.getExtras().getParcelable(ActivityCode.EXTRA_CATEGORY);

        txt_difficulty.setText(intent.getStringExtra(ActivityCode.EXTRA_DIFFICULTY));
        txt_score.setText(intent.getIntExtra(ActivityCode.EXTRA_SCORE, 0) + "");
        txt_category.setText(category.getName());
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


    @OnClick(R.id.btn_save)
    public void onSaveButtonClick(View view) {

        if (txt_inp_lyt_name.getEditText().getText().toString().trim().isEmpty()) {

            Intent resultIntent = new Intent();
            setResult(RESULT_CANCELED, resultIntent);
            finish();
        }
        else{

            Intent intent = getIntent();
            String difficulty = intent.getStringExtra(ActivityCode.EXTRA_DIFFICULTY);
            Category category = intent.getExtras().getParcelable(ActivityCode.EXTRA_CATEGORY);
            int score = intent.getIntExtra(ActivityCode.EXTRA_SCORE, 0);

            //viewModel.saveScore(new Player(txt_inp_lyt_name.getEditText().getText().toString()), new Score(score, category.getId(), difficulty, txt_inp_lyt_name.getEditText().getText().toString()));
        }
    }

    @OnClick(R.id.img_plus)
    public void onImagePlusButtonClick(View view) {
        onProfileImageClick();
    }



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