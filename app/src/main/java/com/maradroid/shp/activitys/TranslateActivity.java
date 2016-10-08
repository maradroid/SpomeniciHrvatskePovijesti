package com.maradroid.shp.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.maradroid.shp.R;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mara on 3/18/16.
 */
public class TranslateActivity extends BaseActivity {

    private static final int SAVE_TAG = 0;
    private static final int SHARE_TAG = 1;

    private EditText etTextToTranslate;
    private TextView tvTranslatedText;

    private String imageName;
    private String inputValue;

    private boolean screenshotEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        initToolbar();
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_translate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;

        } else if (id == R.id.ic_save) {

            if (screenshotEnabled && hasPermissins(SAVE_TAG))
                saveImage();

        } else if (id == R.id.ic_share) {

            if (screenshotEnabled && hasPermissins(SHARE_TAG))
                shareImage();
        }


        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.translator));
        toolbar.setNavigationIcon(R.mipmap.ic_chevron_left_white_36dp);
    }

    private void initViews() {

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "glagoljica.ttf");
        tvTranslatedText = (TextView) findViewById(R.id.translated_tv);
        tvTranslatedText.setTypeface(myTypeface);

        etTextToTranslate = (EditText) findViewById(R.id.text_to_translate_et);
        setEditTextWatcher();

    }

    private void setEditTextWatcher() {

        etTextToTranslate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() > 0) {

                    screenshotEnabled = true;

                    char lastCharacter = editable.charAt(editable.length() - 1);

                    String character = String.valueOf(lastCharacter);
                    character.toLowerCase();

                    if (character.equals("č") || character.equals("ć") || character.equals("ž") || character.equals("š") || character.equals("đ")) {

                        editable.delete(editable.length() - 1, editable.length());
                        Toast.makeText(TranslateActivity.this, getString(R.string.input_error), Toast.LENGTH_SHORT).show();

                    } else {
                        tvTranslatedText.setText(editable.toString());
                    }

                } else {
                    screenshotEnabled = false;
                    tvTranslatedText.setText("");
                }
            }
        });
    }

    public void takeScreenshot(final View v, final int tag) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                String mPath = Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/";

                // create bitmap screen capture
                //View v1 = getWindow().getDecorView().getRootView();
                v.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
                v.setDrawingCacheEnabled(false);

                File file = new File(mPath);

                if (!file.exists()) {
                    file.mkdirs();
                }

                imageName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                String imageFileString = mPath + imageName + ".jpeg";

                File imageFile = new File(imageFileString);

                FileOutputStream outputStream = null;

                try {

                    outputStream = new FileOutputStream(imageFileString);
                    int quality = 100;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                    outputStream.flush();
                    outputStream.close();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (tag == SAVE_TAG) {
                                Toast.makeText(TranslateActivity.this, getString(R.string.image_saved), Toast.LENGTH_SHORT).show();

                            } else if (tag == SHARE_TAG) {
                                share();
                            }
                        }
                    });

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void saveImage() {

        if (imageName == null) {
            inputValue = etTextToTranslate.getText().toString();
            takeScreenshot(tvTranslatedText, SAVE_TAG);

        } else {

            if (!inputValue.equals(etTextToTranslate.getText().toString())) {
                inputValue = etTextToTranslate.getText().toString();
                takeScreenshot(tvTranslatedText, SAVE_TAG);

            } else {
                Toast.makeText(this, getString(R.string.image_saved), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void shareImage() {

        if (imageName == null) {
            inputValue = etTextToTranslate.getText().toString();
            takeScreenshot(tvTranslatedText, SHARE_TAG);

        } else {

            if (!inputValue.equals(etTextToTranslate.getText().toString())) {
                inputValue = etTextToTranslate.getText().toString();
                takeScreenshot(tvTranslatedText, SHARE_TAG);

            } else {
                share();
            }
        }
    }

    private void share() {

        String imagePath = Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/" + imageName + ".jpeg";

        File file = new File(imagePath);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        shareIntent.setType("image*//*");
        startActivity(Intent.createChooser(shareIntent, "Share photo..."));
    }

    private boolean hasPermissins(int tag) {

        boolean hasPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);

        if (!hasPermission) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, tag);

        } else {
            return true;
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case SAVE_TAG:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveImage();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.save_abort), Toast.LENGTH_SHORT).show();
                }

                break;

            case SHARE_TAG:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    shareImage();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.share_abort), Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
