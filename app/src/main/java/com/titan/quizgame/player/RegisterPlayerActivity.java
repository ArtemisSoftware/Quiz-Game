package com.titan.quizgame.player;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.google.android.material.textfield.TextInputLayout;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.titan.quizgame.BaseActivity;
import com.titan.quizgame.R;
import com.titan.quizgame.network.ImgurUpload;
import com.titan.quizgame.player.models.Player;
import com.titan.quizgame.player.models.Score;
import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.ui.Resource;
import com.titan.quizgame.util.UIMessages;
import com.titan.quizgame.util.constants.ActivityCode;
import com.titan.quizgame.util.Image;
import com.titan.quizgame.util.ImageCropConstants;
import com.titan.quizgame.util.Permissions;
import com.titan.quizgame.util.viewmodel.ViewModelProviderFactory;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;


public class RegisterPlayerActivity extends BaseActivity implements ImageListener{


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

    @BindView(R.id.img_profile)
    CircularImageView img_profile;


    private PlayerViewModel viewModel;

    private ImgurUpload upload;

    @Inject
    ViewModelProviderFactory providerFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profile);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this, providerFactory).get(PlayerViewModel.class);
        upload = new ImgurUpload();

        getIncomingIntent();
        subscribeObservers();
    }


    private void getIncomingIntent(){

        Intent intent = getIntent();
        Category category = intent.getExtras().getParcelable(ActivityCode.EXTRA_CATEGORY);

        txt_difficulty.setText(intent.getStringExtra(ActivityCode.EXTRA_DIFFICULTY));
        txt_score.setText(intent.getIntExtra(ActivityCode.EXTRA_SCORE, 0) + "");
        txt_category.setText(category.getName());
    }



    private void subscribeObservers() {

        viewModel.observePlayers().observe(this, new Observer<Resource>() {
            @Override
            public void onChanged(Resource resource) {


                Timber.d("onChanged: " + resource.toString());

                switch (resource.status){

                    case SUCCESS:

                        finishRegister(RESULT_OK);
                        break;

                    case ERROR:

                        break;

                }
            }
        });
    }



    private void finishRegister(int result) {

        Closure method = new Closure() {
            @Override
            public void exec() {
                Intent resultIntent = new Intent();
                setResult(result, resultIntent);
                finish();
            }
        };

        UIMessages.success(pDialog, "Quiz", "Score saved", method);
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
        intent.putExtra(ImageCropConstants.INTENT_IMAGE_PICKER_OPTION, ActivityCode.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImageCropConstants.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImageCropConstants.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImageCropConstants.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImageCropConstants.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImageCropConstants.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImageCropConstants.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, ActivityCode.REQUEST_IMAGE);
    }


    private void launchGalleryIntent() {
        Intent intent = new Intent(this, ImagePickerActivity.class);
        intent.putExtra(ImageCropConstants.INTENT_IMAGE_PICKER_OPTION, ActivityCode.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImageCropConstants.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImageCropConstants.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImageCropConstants.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, ActivityCode.REQUEST_IMAGE);
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


            upload.title = txt_inp_lyt_name.getEditText().getText().toString();
            upload.description = txt_inp_lyt_message.getEditText().getText().toString();


            viewModel.saveScore(upload, new Player(txt_inp_lyt_name.getEditText().getText().toString()), new Score(score, category.getId(), difficulty, txt_inp_lyt_name.getEditText().getText().toString()));
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

        if (requestCode == ActivityCode.REQUEST_IMAGE) {

            if (resultCode == RESULT_OK) {

                Uri uri = data.getParcelableExtra("path");

                // You can update this bitmap to your server
                Bitmap bitmap = Image.getBitmap(this, uri);

                img_profile.setImageBitmap(bitmap);

                getFilePath(uri);


            }
        }
    }


    private void getFilePath(Uri uri) {
        String filePath = Image.getPath(this, uri);
        //Safety check to prevent null pointer exception
        if (filePath == null || filePath.isEmpty()) return;
        upload.image = new File(filePath);
        Timber.d("FilePath: " + filePath);
    }


}
