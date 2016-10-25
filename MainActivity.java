package com.missioncritical.lab3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final int TAKE_AVATAR_CAMERA_REQUEST = 1;
    Button btnLeft, btnRight, btnUp, btnDown;
    ImageButton avatar;

    RelativeLayout screen_bounds;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen_bounds = (RelativeLayout)findViewById(R.id.screen_bounds);

        initAvatar();


        avatar = (ImageButton)findViewById(R.id.ImageButton_Avatar);

        btnLeft = (Button)findViewById(R.id.btnLeft);

        btnRight = (Button)findViewById(R.id.btnRight);

        btnUp = (Button)findViewById(R.id.btnUp);

        btnDown = (Button)findViewById(R.id.btnDown);


        /*for testing purposes:

            float width = avatar.getLeft();
            float height = avatar.getRight();
            float xCoord = avatar.getBottom();
            float yCoord = avatar.getTop();
            int screenWidth = screen_bounds.getWidth();
            int screenHeight = screen_bounds.getHeight();
            StringBuilder sb = new StringBuilder();
            sb.append(width + " ").append(height + " ").append(xCoord + " ").append(yCoord + " ").append(screenWidth + " ").append(screenHeight + " ");

         */



        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //location on screen
                    //int loc[] = {0,0};
                    //avatar.getLocationOnScreen(loc);


                    float y = avatar.getY();
                    //check if the avatar goes past the buttons
                    if(y > 1320.0){
                        Toast.makeText(MainActivity.this, "Out of bounds", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        y = y + 20.0f;
                        avatar.setY(y);
                    }

            }
        });


        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                        float y = avatar.getY();
                        if(y > 0){
                            y = y - 20.0f;

                            avatar.setY(y);
                        }

            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  num = avatar.getRight();
               // num = num++;
                //avatar.setX(num);
                float x = avatar.getX();
                if(x < 860){
                    x = x + 20.0f;
                    avatar.setX(x);
                }

            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                float x = avatar.getX();
                if(x > 0){
                    x = x - 20.0f;
                    avatar.setX(x);
                }

            }
        });


    }


    private void initAvatar() {

        ImageButton avatarButton = (ImageButton)findViewById(R.id.ImageButton_Avatar);



        avatarButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(Intent.createChooser(cameraIntent, "Take your photo"),
                        TAKE_AVATAR_CAMERA_REQUEST);
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        //as long as you are returing from the camera
        if(requestCode == TAKE_AVATAR_CAMERA_REQUEST)
        {
            //if they cancelled taking their picture don't attempt to save it
            if(resultCode == Activity.RESULT_CANCELED) return;

            if(resultCode == Activity.RESULT_OK)
            {
                //get data from camera and turn it into a bitmap
                Bitmap cameraPic = (Bitmap)intent.getExtras().get("data");
                if(cameraPic == null) return;

                saveAvatar(cameraPic);
            }
        }
    }

    private void saveAvatar(Bitmap cameraPic) {

        String avatarFile = "avatar.png";

        try
        {
            cameraPic.compress(Bitmap.CompressFormat.PNG, 75, openFileOutput(avatarFile, MODE_PRIVATE));
        }
        catch(Exception e)
        {
            return;
        }

        //makes a new file and gives you the path
        Uri avatarUri = Uri.fromFile(new File(this.getFilesDir(), avatarFile));


        //save avatar to button
        avatar.setImageURI(null);
        avatar.setImageURI(avatarUri);


    }




}
