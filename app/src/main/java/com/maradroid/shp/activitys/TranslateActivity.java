package com.maradroid.shp.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.maradroid.shp.R;

import java.io.File;

/**
 * Created by mara on 3/18/16.
 */
public class TranslateActivity extends Activity {

    private EditText textToTranslateET;
    private TextView translatedTextTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        initViews();

    }

    private void initViews() {

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "glagoljica.ttf");
        translatedTextTV = (TextView) findViewById(R.id.translated_tv);
        translatedTextTV.setTypeface(myTypeface);

        textToTranslateET = (EditText) findViewById(R.id.text_to_translate_et);

    }

    public void translateText(View v) {

        String inputText = textToTranslateET.getText().toString();
        translatedTextTV.setText(inputText);
    }

    public void takeScreenshot(View v) {

        /*String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

        // create bitmap screen capture
        View v1 = getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);

        File imageFile = new File(mPath);

        FileOutputStream outputStream = new FileOutputStream(imageFile);
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
        outputStream.flush();
        outputStream.close();*/
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }
}
