package com.example.android1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.ButtonBarLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CharSequence number_amount;
    private MONEY first_money;
    private MONEY second_money;
    private double number_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        TextView textView = findViewById(R.id.text_hello);
//        AppCompatButton appCompatButton = findViewById(R.id.button);
//
//        appCompatButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                textView.setText("Bienvenue à toi, personnage mystère...");
//                textView.setTextColor(Color.BLUE);
//                textView.setTextSize(20f);
//            }
//        });

        // INITIALISATION DES VARIABLES POUR LES CHAMPS
        TextView amount = findViewById(R.id.input_amount);
        TextView result = findViewById(R.id.text_result);

        RadioGroup first_group = findViewById(R.id.radio_group_convert_start);
        RadioButton first_euro = findViewById(R.id.radio_button_euro_start);
        first_euro.setChecked(true);

        RadioGroup second_group = findViewById(R.id.radio_group_convert_end);
        RadioButton second_dollars = findViewById(R.id.radio_button_dollars_end);
        second_dollars.setChecked(true);

        Button submit = findViewById(R.id.convert_submit);

        // INITIALISATION VARIABLES DE CALCUL
        this.first_money = MONEY.EURO;
        this.second_money = MONEY.DOLLARS;

        // APPLICATION DES CHANGEMENTS DU FORM DANS NOS VARIABLES DE CALCULS
        first_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                doOnGroupRadioChanged(i, TYPE.START);
            }
        });

        second_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                doOnGroupRadioChanged(i, TYPE.END);
            }
        });

        // SOUMISSION DU FORMULAIRE :
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number_amount = amount.getText();
                // CALCUL DU RESULTAT
                doCalculResult();
                // ON AFFICHE LE RESULTAT
                result.setText(textResult());
            }
        });


    }

    private void doOnGroupRadioChanged(int checkId, TYPE type){
//        int checkedRadioId = group.getCheckedRadioButtonId();
//        logger("Checked ID " + checkId);
//        logger("Checked ID " + checkedRadioId);
//        logger("Boolean ID " + (checkId == checkedRadioId)); // true

        MONEY money;
        if(checkId == R.id.radio_button_euro_start || checkId == R.id.radio_button_euro_end){
            money = MONEY.EURO;
        } else if(checkId == R.id.radio_button_dollars_start || checkId == R.id.radio_button_dollars_end){
            money = MONEY.DOLLARS;
        } else {
            money = MONEY.OTHER;
        }

        if(type == TYPE.START){
            this.first_money = money;
        } else {
            this.second_money = money;
        }

    }

    private enum MONEY {
        // OTHER est devenu Livre sterling
        EURO, DOLLARS, OTHER
    }

    private enum TYPE {
        START, END
    }

    private void logger(String message){
        String LOGTAG = "App.DEBUG";
        Log.i(LOGTAG, message);
    }

    private void doCalculResult(){
        String str = number_amount.toString();
        if(str.equals("")){
            number_result = 0;
        } else {
            number_result = Double.parseDouble(str);
        }
        if(first_money == MONEY.EURO){
            if(second_money == MONEY.DOLLARS){
                number_result *= 1.0283;
            } else if(second_money == MONEY.OTHER){
                number_result *= 0.87;
            }
        } else if (first_money == MONEY.DOLLARS){
            if(second_money == MONEY.EURO){
                number_result *= 0.9726;
            } else if(second_money == MONEY.OTHER){
                number_result *= 0.84;
            }
        } else {
            if(second_money == MONEY.EURO){
                number_result *= 1.15;
            } else if(second_money == MONEY.DOLLARS){
                number_result *= 1.19;
            }
        }
    }

    private String enumMoneyToString(MONEY money){
        switch (money){
            case EURO:
                return "EUR";
            case DOLLARS:
                return "USD";
            default:
                return "LB";
        }
    }

    private String textResult(){
        return number_amount + " " + enumMoneyToString(first_money) + " = " + number_result + " " + enumMoneyToString(second_money);
    }
}