package com.example.favoritepicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int COLOR_CODE = 0;
    private static final int CONTACT_CODE = 1;
    private String mainColor = "BLANC";
    private String mainContact = "À choisir xD";
//    private String mainContactTel = "+33 ...";
    private static final String URL_POKEMON = "https://www.dragonflycave.com/favorite.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mainColor = savedInstanceState.getString("color");
        }

        setBackgroundColor();
        setFavoriteContact();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("color", mainColor);
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case COLOR_CODE:
                    assert data != null;
                    mainColor = data.getStringExtra("color");
                    setBackgroundColor();
                    break;
                case CONTACT_CODE:
                    assert data != null;
                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        mainContact = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        // Récupération du numéro de téléphone mobile // marche pas
//                        String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
//                        Cursor phones = managedQuery(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
//                        while(phones.moveToNext()){
//                            String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                            int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
//                            switch (type){
//                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
//                                    mainContactTel = number;
//                                    break;
//                                default:
//                                    break;
//                            }
//                        }
                    }
                    setFavoriteContact();
                    break;
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void setFavoriteContact(){
        TextView printContact = (TextView) findViewById(R.id.info_contact);
        printContact.setText(mainContact /* + " (" + mainContactTel + ")"*/);
    }

    private void setBackgroundColor(){
        int alpha = 255;
        switch (mainColor) {
            case "RED" :
                getWindow().getDecorView().setBackgroundColor(Color.argb(alpha, 230, 38, 0));
                break;
            case "GREEN" :
                getWindow().getDecorView().setBackgroundColor(Color.argb(alpha, 51, 153, 0));
                break;
            case "BLUE" :
                getWindow().getDecorView().setBackgroundColor(Color.argb(alpha, 0, 115, 230));
                break;
            case "ORANGE" :
                getWindow().getDecorView().setBackgroundColor(Color.argb(alpha, 255, 144, 25));
                break;
            default:
                getWindow().getDecorView().setBackgroundColor(Color.argb(alpha, 255, 255, 255));
                break;
        }
        TextView printColor = (TextView) findViewById(R.id.info_color);
        printColor.setText(mainColor);
    }

    public void loadPokemonPage(View view){
        Intent page = new Intent(Intent.ACTION_VIEW);
        page.setData(Uri.parse(URL_POKEMON));
        startActivity(page);
    }

    public void openColorPicker(View view) {
        Intent colorPicker = new Intent(this, ColorActivity.class);
        startActivityForResult(colorPicker, COLOR_CODE);
    }

    public void getFavoriteContact(View view){
        Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(pickContact, CONTACT_CODE);
    }
}