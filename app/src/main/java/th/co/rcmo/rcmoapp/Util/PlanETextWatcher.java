package th.co.rcmo.rcmoapp.Util;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import th.co.rcmo.rcmoapp.PBProdDetailCalculateFmentD;
import th.co.rcmo.rcmoapp.PBProdDetailCalculateFmentE;

/**
 * Created by Taweesin on 28/6/2559.
 */
public class PlanETextWatcher implements TextWatcher {


    private boolean hasFractionalPart;
    PBProdDetailCalculateFmentE.ViewHolder h;
    private String name;
    private EditText et ;
    boolean changetext = true;

    public PlanETextWatcher(EditText editText , PBProdDetailCalculateFmentE.ViewHolder h, String name) {
        changetext = true;
        hasFractionalPart = false;
        this.h = h;
        this.name = name;
        this.et = editText;

    }

    public PlanETextWatcher(PBProdDetailCalculateFmentE.ViewHolder h, String name) {

        hasFractionalPart = false;
        this.h = h;
        this.name = name;
        changetext = false;

    }



    @SuppressWarnings("unused")
    private static final String TAG = "NumberTextWatcher";

    @Override
    public void afterTextChanged(Editable s) {

if( changetext ) {
    et.removeTextChangedListener(this);
    try {
        String originalString = s.toString();

        Long longval;
        if (originalString.contains(",")) {
            originalString = originalString.replaceAll(",", "");
        }
        longval = Long.parseLong(originalString);

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###,###,###");
        String formattedString = formatter.format(longval);

        //setting text after format to EditText
        et.setText(formattedString);
        et.setSelection(et.getText().length());
        et.addTextChangedListener(this);
    } catch (NumberFormatException nfe) {
        // nfe.printStackTrace();
    }
}
        //formulaAModel.calculate();
        double value = 0;
        if(name.contains("KaPan")) {
            value =   (Util.strToDoubleDefaultZero(h.txStartPrice.getText().toString()))
                    * (Util.strToDoubleDefaultZero(h.txStartUnit.getText().toString()));


            h.group1_item_1.setText(Util.dobbleToStringNumber(value));
        }
        /*
        if(name.contains("DieRate")) {
           value =   ((Util.strToDoubleDefaultZero(h.txStartUnit.getText().toString())) - (Util.strToDoubleDefaultZero(h.group3_item_2.getText().toString())));
          //  Log.d("Test ","-----> "+value);
                        value =  value/(Util.strToDoubleDefaultZero(h.txStartUnit.getText().toString())) * 100;
            Log.d("Test ","2-----> "+value);

            if(value<0){
                h.group2_item_1.setText("100%");
            }else {
                h.group2_item_1.setText(Util.dobbleToStringNumberWithClearDigit(value) + "%");
            }

        }

        if(name.contains("NamNakTKai")){
           value =  Util.strToDoubleDefaultZero(h.group3_item_1.getText().toString())
                   *Util.strToDoubleDefaultZero(h.group3_item_2.getText().toString()) ;
            h.group3_item_3.setText(Util.dobbleToStringNumber(value));
        }
        */
        if(name.contains("costKaSiaOkardRongRaun")){
            value = Util.strToDoubleDefaultZero(h.group1_item_1.getText().toString());
            value+= Util.strToDoubleDefaultZero(h.group1_item_2.getText().toString());
            value+= Util.strToDoubleDefaultZero(h.group1_item_3.getText().toString());
            value+= Util.strToDoubleDefaultZero(h.group1_item_4.getText().toString())/30.42*Util.strToDoubleDefaultZero(h.group3_item_2.getText().toString());
            value+= Util.strToDoubleDefaultZero(h.group1_item_5.getText().toString())/30.42*Util.strToDoubleDefaultZero(h.group3_item_2.getText().toString());
            value+= Util.strToDoubleDefaultZero(h.group1_item_6.getText().toString())/30.42*Util.strToDoubleDefaultZero(h.group3_item_2.getText().toString());
            value+= Util.strToDoubleDefaultZero(h.group1_item_7.getText().toString());
            value+= Util.strToDoubleDefaultZero(h.group1_item_8.getText().toString());

            value = value*0.0675/365*Util.strToDoubleDefaultZero(h.group3_item_2.getText().toString());
            h.group3_item_3.setText(Util.dobbleToStringNumber(value));
        }



    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}