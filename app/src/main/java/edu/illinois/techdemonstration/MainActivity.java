package edu.illinois.techdemonstration;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.Environment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    static final int IMAGE_GALLERY_REQUEST = 6;
    static final int CAMERA_REQUEST = 2;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button camera = (Button) findViewById(R.id.button);
        final Button gallery = (Button) findViewById(R.id.button2);
        imageView = (ImageView) findViewById(R.id.imageView);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                // Address of the image.
                Uri imageURI = data.getData();
                // get the input stream based on the URI.
                assert imageURI != null;
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageURI);
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    // show the image to the user.
                    imageView.setImageBitmap(image);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void getImageInGallery(View view) {

        Intent imagePicker = new Intent(Intent.ACTION_PICK);

        // Tutorial video where I borrowed this fragment of code.
        // https://www.youtube.com/watch?v=wBuWqqBWziU by Brandon Jones.

        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();

        Uri data = Uri.parse(pictureDirectoryPath);
        imagePicker.setDataAndType(data, "image/*");

        startActivityForResult(imagePicker, IMAGE_GALLERY_REQUEST);
    }


}
