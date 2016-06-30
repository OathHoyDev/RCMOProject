package th.co.rcmo.rcmoapp.API;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import th.co.rcmo.rcmoapp.Model.STDVarModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaAModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaBModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaCModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaDModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaEModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaFModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaGModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaHModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaIModel;
import th.co.rcmo.rcmoapp.Module.mGetVariable;
import th.co.rcmo.rcmoapp.Module.mVarPlanA;
import th.co.rcmo.rcmoapp.Module.mVarPlanB;
import th.co.rcmo.rcmoapp.Module.mVarPlanD;
import th.co.rcmo.rcmoapp.Module.mVarPlanE;
import th.co.rcmo.rcmoapp.Module.mVarPlanF;
import th.co.rcmo.rcmoapp.Module.mVarPlanG;
import th.co.rcmo.rcmoapp.Module.mVarPlanH;
import th.co.rcmo.rcmoapp.Module.mVarPlanI;
import th.co.rcmo.rcmoapp.ProductDetailStandardListCustomAdapter;
import th.co.rcmo.rcmoapp.R;
import th.co.rcmo.rcmoapp.Util.CalculateConstant;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;

/**
 * Created by Taweesin on 25/6/2559.
 */
public class ProductService {
   static String TAG = "displayStandardValue";
   static String[] B_StdDisplay = {"D","O","CS"};
   static String[] C_StdDisplay = {"D","O","CS","CA"};
   static String[] F_StdDisplay = {"D","O"};
   static String[] H_StdDisplay = {"D"};
   static String[] G_StdDisplay = {"DH","DD","OH","OD"};
   static String[] KB_StdDisplay = {"DP","OP"};
   static String[] KC_StdDisplay = {"DB","OB"};

    public static  List<STDVarModel>  prepareSTDVarList(mGetVariable.mRespBody var , String fisheryType){

        Log.d("prepareSTDVarList", "FormularCode : " + var.getFormularCode()+" fisheryType :"+fisheryType);

        String formularCode = var.getFormularCode();

       List<STDVarModel> stdVarModelList= new ArrayList<STDVarModel>();
       String tempVar[] = null;
       java.lang.reflect.Method method;;
       String varValue = "";
       String[] keyDisplay_Type = null;
       Hashtable hashValue_Type = null;
       //tempVar[0] = variable name in thai
       //tempVar[1] = variable unit in thai

        switch (formularCode) {

            case "A":
            case "B":
               keyDisplay_Type = B_StdDisplay;
               hashValue_Type  = CalculateConstant.PB_CALCULATE_STANDARD_CONST_AB;
               break;

            case "C":
               keyDisplay_Type = C_StdDisplay;
               hashValue_Type  = CalculateConstant.PB_CALCULATE_STANDARD_CONST_C;
               break;

            case "D":
            case "E":
            case "F":

               keyDisplay_Type = F_StdDisplay;
               hashValue_Type  = CalculateConstant.PB_CALCULATE_STANDARD_CONST_DEF;
               break;

            case "H":

               keyDisplay_Type = H_StdDisplay;
               hashValue_Type  = CalculateConstant.PB_CALCULATE_STANDARD_CONST_DEF;
               break;

            case "G":

               keyDisplay_Type =G_StdDisplay;
               hashValue_Type  = CalculateConstant.PB_CALCULATE_STANDARD_CONST_G;
               break;

            case "I":
            case "J":
            case "K":
                if(ServiceInstance.FISHERY_TYPE_KC.equals(fisheryType)) {
                    keyDisplay_Type = KC_StdDisplay;
                }else{
                    keyDisplay_Type = KB_StdDisplay;
                }

               hashValue_Type  =CalculateConstant.PB_CALCULATE_STANDARD_CONST_IJK;

                break;
        }

       if(keyDisplay_Type != null && hashValue_Type != null) {
          for (String key : keyDisplay_Type) {
             try {
                method = var.getClass().getMethod("get" + key);
                varValue = (String) method.invoke(var);
             } catch (Exception e) {
                varValue = "";
             }
             Log.d(TAG, ((String[])(hashValue_Type.get(key)))[0] + " : " + varValue);
              Log.d(TAG, "Key  : " +key);
             tempVar = (String[]) hashValue_Type.get(key);
             stdVarModelList.add(new STDVarModel(tempVar[0], varValue, tempVar[1]));
          }
       }else{
          stdVarModelList.add(new STDVarModel("ขออภัยอยู่ระหว่างพัฒนา","", ""));
       }
        //standardList = (ListView) v.findViewById(R.id.listView);
        //standardList.setAdapter(new ProductDetailStandardListCustomAdapter(context , productType , stdName,stdValue,stdUnit));
      return stdVarModelList;
    }

    public static String genJsonPlanVariable(FormulaAModel aModel){
        mVarPlanA  varA = new mVarPlanA();
        varA.setAttraDokbia(aModel.AttraDokbia);
        varA.setKaDoolae(aModel.KaDoolae);
        varA.setKaGebGeaw(aModel.KaGebGeaw);
        varA.setKaNardPlangTDin(aModel.KaNardPlangTDin);
        varA.setKaPan(aModel.KaPan);
        varA.setKaPluk(aModel.KaPluk);
        varA.setKaPuy(aModel.KaPuy);
        varA.setKaRang(aModel.KaRang);
        varA.setKaSermOuppakorn(aModel.KaSermOuppakorn);
        varA.setKaSiaOkardLongtoon(aModel.KaSiaOkardLongtoon);
        varA.setKaSiaOkardOuppakorn(aModel.KaSiaOkardOuppakorn);
        varA.setKaTreamDin(aModel.KaTreamDin);
        varA.setKaWassadu(aModel.KaWassadu);
        varA.setKaWassaduUn(aModel.KaWassaduUn);
        varA.setKaYaplab(aModel.KaYaplab);
        varA.setRaka(aModel.predictPrice);
        varA.setPonPalid(aModel.PonPalid);
        varA.setKaChaoTDin(aModel.KaChaoTDin);


        //jsonPlanA = TextUtils.htmlEncode(jsonPlanA);
       String json =(new Gson().toJson(varA));
        String value = "";
       try {

           byte ptext[] = json.getBytes("ISO-8859-1");
           value = new String(ptext, "UTF-8");
           Log.d("Test "," -----------------------------> "+value);
       }catch(Exception e){
         e.printStackTrace();
       }

        Log.d("GSON","Json -> "+json);

        return value;
    }

    public static String genJsonPlanVariable(FormulaBModel model){
        mVarPlanB var = new mVarPlanB();
        var.setYear(model.Year);
        var.setAttraDokbia(model.AttraDokbia);
        var.setKaDoolae(model.KaDoolae);
        var.setKaGebGeaw(model.KaGebGeaw);
        var.setKaNardPlangTDin(model.KaNardPlangTDin);
        var.setKaPan(model.KaPan);
        var.setKaPluk(model.KaPluk);
        var.setKaPuy(model.KaPuy);
        var.setKaRang(model.KaRang);
        var.setKaSermOuppakorn(model.KaSermOuppakorn);
        var.setKaSiaOkardLongtoon(model.KaSiaOkardLongtoon);
        var.setKaSiaOkardOuppakorn(model.KaSiaOkardOuppakorn);
        var.setKaTreamDin(model.KaTreamDin);
        var.setKaWassadu(model.KaWassadu);
        var.setKaWassaduUn(model.KaWassaduUn);
        var.setKaYaplab(model.KaYaplab);
        var.setRaka(model.predictPrice);
        var.setPonPalid(model.PonPalid);
        var.setKaChaoTDin(model.KaChaoTDin);


        //jsonPlanA = TextUtils.htmlEncode(jsonPlanA);
        String json =(new Gson().toJson(var));
        String value = "";
        try {

            byte ptext[] = json.getBytes("ISO-8859-1");
            value = new String(ptext, "UTF-8");
            Log.d("Test "," -----------------------------> "+value);
        }catch(Exception e){
            e.printStackTrace();
        }

        Log.d("GSON","Json -> "+json);

        return value;
    }

    public static String genJsonPlanVariable(FormulaCModel model){
        mVarPlanB var = new mVarPlanB();

        var.setAttraDokbia(model.AttraDokbia);
        var.setKaDoolae(model.KaDoolae);
        var.setKaGebGeaw(model.KaGebGeaw);
        var.setKaNardPlangTDin(model.KaNardPlangTDin);
        var.setKaPan(model.KaPan);
        var.setKaPluk(model.KaPluk);
        var.setKaPuy(model.KaPuy);
        var.setKaRang(model.KaRang);
        var.setKaSermOuppakorn(model.KaSermOuppakorn);
        var.setKaSiaOkardLongtoon(model.KaSiaOkardLongtoon);
        var.setKaSiaOkardOuppakorn(model.KaSiaOkardOuppakorn);
        var.setKaTreamDin(model.KaTreamDin);
        var.setKaWassadu(model.KaWassadu);
        var.setKaWassaduUn(model.KaWassaduUn);
        var.setKaYaplab(model.KaYaplab);
        var.setRaka(model.predictPrice);
        var.setPonPalid(model.PonPalid);
        var.setKaChaoTDin(model.KaChaoTDin);



        //jsonPlanA = TextUtils.htmlEncode(jsonPlanA);
        String jsonPlanA =(new Gson().toJson(var));
        String value = "";
        try {

            byte ptext[] = jsonPlanA.getBytes("ISO-8859-1");
            value = new String(ptext, "UTF-8");
            Log.d("Test "," -----------------------------> "+value);
        }catch(Exception e){
            e.printStackTrace();
        }

        Log.d("GSON","Json -> "+jsonPlanA);

        return value;
    }

    public static String genJsonPlanVariable(FormulaDModel model){
        mVarPlanD var = new mVarPlanD();
        var.KaAHan      = model.KaAHan;
        var.KaYa        = model.KaYa;
        var.KaRangGgan  =  model.KaRangGgan;
        var.KaNamKaFai  = model.KaNamKaFai;
        var.KaNamMan    = model.KaNamMan ;
        var.KaWassaduSinPleung =  model.KaWassaduSinPleung ;
        var.KaSomRongRaun = model.KaSomRongRaun ;
        var.KaChoaTDin = model.KaChoaTDin  ;

        var.NamNakChaLia = model.NamNakChaLia ;
        var.JumNounTuaTKai = model.JumNounTuaTKai;
        var.RakaTKai =  model.RakaTKai ;
        var.RaYaWeRaLeang = model.RaYaWeRaLeang;

        var.RermLeang = model.RermLeang;
        var.RakaReamLeang =  model.RakaReamLeang;


        //jsonPlanA = TextUtils.htmlEncode(jsonPlanA);
        String json =(new Gson().toJson(var));
        String value = "";
        try {

            byte ptext[] = json.getBytes("ISO-8859-1");
            value = new String(ptext, "UTF-8");
            Log.d("Test "," -----------------------------> "+value);
        }catch(Exception e){
            e.printStackTrace();
        }

        Log.d("GSON","Json -> "+json);

        return value;
    }

    public static String genJsonPlanVariable(FormulaEModel model){
        mVarPlanE var = new mVarPlanE();
        var.KaAHan      = model.KaAHan;
        var.KaYa        = model.KaYa;
        var.KaRangGgan  =  model.KaRangGgan;
        var.KaNamKaFai  = model.KaNamKaFai;
        var.KaNamMan    = model.KaNamMan ;
        var.KaWassaduSinPleung =  model.KaWassaduSinPleung ;
        var.KaSomRongRaun = model.KaSomRongRaun ;
        var.KaChoaTDin = model.KaChoaTDin  ;

        var.RakaTKai =  model.RakaTKai ;
        var.RaYaWeRaLeang = model.RaYaWeRaLeang;

        var.RermLeang = model.RermLeang;
        var.RakaReamLeang =  model.RakaReamLeang;


        //jsonPlanA = TextUtils.htmlEncode(jsonPlanA);
        String json =(new Gson().toJson(var));
        String value = "";
        try {

            byte ptext[] = json.getBytes("ISO-8859-1");
            value = new String(ptext, "UTF-8");
            Log.d("Test "," -----------------------------> "+value);
        }catch(Exception e){
            e.printStackTrace();
        }

        Log.d("GSON","Json -> "+json);

        return value;
    }


    public static String genJsonPlanVariable(FormulaFModel model){
        mVarPlanF var = new mVarPlanF();
        var.KaAHan = model.KaAHan;
        var.KaYa   = model.KaYa;
        var.KaRangGgan = model.KaRangGgan;
        var.KaNamKaFai = model.KaNamKaFai;
        var.KaNamMan = model.KaNamMan;
        var.KaWassaduSinPleung = model.KaWassaduSinPleung;
        var.KaSomRongRaun =model.KaSomRongRaun;
        var.KaChoaTDin = model.KaChoaTDin;

        var.KaiTDaiTangTaeRoem = model.KaiTDaiTangTaeRoem;
        var.RakaTKai = model.RakaTKai;
        var.PonPloyDai =   model.PonPloyDai;
        var.RaYaWeRaLeang = model.RaYaWeRaLeang;

        var.RermLeang =model.RermLeang;
        var.RakaReamLeang =model.RakaReamLeang;


        //jsonPlanA = TextUtils.htmlEncode(jsonPlanA);
        String json =(new Gson().toJson(var));
        String value = "";
        try {

            byte ptext[] = json.getBytes("ISO-8859-1");
            value = new String(ptext, "UTF-8");
            Log.d("Test "," -----------------------------> "+value);
        }catch(Exception e){
            e.printStackTrace();
        }

        Log.d("GSON","Json -> "+json);

        return value;
    }


    public static String genJsonPlanVariable(FormulaHModel model){
        mVarPlanH var = new mVarPlanH();

        var.JumnuanTKai = model.JumnuanTKai;
        var.KaPan       = model.KaPan;
        var.KaAHanKon   = model.KaAHanKon;
        var.KaAKanYab   = model.KaAKanYab;
        var.KaNamKaFai  = model.KaNamKaFai;
        var.KaYa        = model.KaYa;
        var.KaRang      = model.KaRang;
        var.KaNamKaFai  = model.KaNamKaFai;
        var.KaChoaTDin  = model.KaChoaTDin;
        var.KaSiaOkardLongtoon  = model.KaSiaOkardLongtoon;
        var.NumnukChalia = model.NumnukChalia;
        var.RakaChalia   = model.RakaChalia;
        var.RayaWera     = model.RayaWera;
        var.RermLeang       = model.RermLeang;
        var.NumnukRermLeang = model.NumnukRermLeang;


        //jsonPlanA = TextUtils.htmlEncode(jsonPlanA);
        String json =(new Gson().toJson(var));
        String value = "";
        try {

            byte ptext[] = json.getBytes("ISO-8859-1");
            value = new String(ptext, "UTF-8");
            Log.d("Test "," -----------------------------> "+value);
        }catch(Exception e){
            e.printStackTrace();
        }

        Log.d("GSON","Json -> "+json);

        return value;
    }

    public static String genJsonPlanVariable(FormulaIModel model){
        mVarPlanI var = new mVarPlanI();

        var.Rai = model.Rai;
        var.Ngan = model.Ngan;
        var.TarangWa = model.TarangWa;

        var.rookKung = model.rookKung;

        var.RakaTuaLa = model.RakaTuaLa;
        var.KaAHan = model.KaAHan;
        var.KaYa = model.KaYa;
        var.KaSankemee = model.KaSankemee;
        var.KaNamMan = model.KaNamMan;
        var.KaFai = model.KaFai;
        var.KaRokRain = model.KaRokRain;
        var.KaLeang = model.KaLeang;
        var.KaJub = model.KaJub;
        var.KaSomsamOuppakorn = model.KaSomsamOuppakorn;
        var.KaChaiJay = model.KaChaiJay;
        var.KaChaoTDin = model.KaChaoTDin;

        var.PonPalidKung = model.PonPalidKung;
        var.RakaChalia = model.RakaChalia;
        var.RayaWelaTeeLeang = model.RayaWelaTeeLeang;

        var.KaSermOuppakorn = model.KaSermOuppakorn;
        var.KaSiaOkardOuppakorn = model.KaSiaOkardOuppakorn;



        //jsonPlanA = TextUtils.htmlEncode(jsonPlanA);
        String json =(new Gson().toJson(var));
        String value = "";
        try {

            byte ptext[] = json.getBytes("ISO-8859-1");
            value = new String(ptext, "UTF-8");
            Log.d("Test "," -----------------------------> "+value);
        }catch(Exception e){
            e.printStackTrace();
        }

        Log.d("GSON","Json -> "+json);

        return value;
    }

    public static String genJsonPlanVariable(FormulaGModel model){
        mVarPlanG var = new mVarPlanG();

        var.ParimanNumnom =  model.ParimanNumnom;

        var.KoRakRakGerd        =  model.KoRakRakGerd;
        var.Ko1_2               =  model.Ko1_2;
        var.Ko2                 =  model.Ko2;
        var.MaeKoReedNom        =  model.MaeKoReedNom;

        var.KaReedNom           =  model.KaReedNom;
        var.KaRangReang         =  model.KaRangReang;

        var.KaPasomPan          =  model.KaPasomPan;
        var.KaAHan              =  model.KaAHan;
        var.KaAHanYab           =  model.KaAHanYab;
        var.KaYa                =  model.KaYa;
        var.KaNamKaFai          =  model.KaNamKaFai;
        var.KaNamMan            =  model.KaNamMan;
        var.KaWassaduSinPleung  =  model.KaWassaduSinPleung;
        var.KaSomsamOuppakorn   =  model.KaSomsamOuppakorn;
        var.KaKonsong           =  model.KaKonsong;
        var.KaChaiJay           =  model.KaChaiJay;
        var.PerKaNamKaFai          =  model.PerKaNamKaFai;
        var.PerKaNamMan            =  model.PerKaNamMan;
        var.PerKaWassaduSinPleung  =  model.PerKaWassaduSinPleung;
        var.PerKaSomsamOuppakorn   =  model.PerKaSomsamOuppakorn;
        var.PerKaChaiJay           =  model.PerKaChaiJay;
        var.KaChaoTDin           =  model.KaChaoTDin;
        var.Raka                =  model.RakaTkai;
        var.JumuanMaeKo          =  model.JumuanMaeKo;


        //jsonPlanA = TextUtils.htmlEncode(jsonPlanA);
        String json =(new Gson().toJson(var));
        String value = "";
        try {

            byte ptext[] = json.getBytes("ISO-8859-1");
            value = new String(ptext, "UTF-8");
            Log.d("Test "," -----------------------------> "+value);
        }catch(Exception e){
            e.printStackTrace();
        }

        Log.d("GSON","Json -> "+json);

        return value;
    }






}
