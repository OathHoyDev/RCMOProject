package th.go.oae.rcmo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;
import java.util.List;
import th.go.oae.rcmo.API.RequestServices;
import th.go.oae.rcmo.API.ResponseAPI;
import th.go.oae.rcmo.Model.UserModel;
import th.go.oae.rcmo.Module.mRegister;
import th.go.oae.rcmo.Util.BitMapHelper;
import th.go.oae.rcmo.Util.ServiceInstance;
import th.go.oae.rcmo.Util.Util;
import th.go.oae.rcmo.View.DialogChoice;

public class EditUserActivity extends Activity {


    public static UserModel userInfo = new UserModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        findViewById(R.id.mainEditUserLayout).setBackground(new BitmapDrawable(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.bg_total, 300, 400)));
        setUI();
        setAction();

    }


    private void setUI() {

        Holder.inputName      = (EditText) findViewById(R.id.inputName);
        Holder.inputSirName   = (EditText) findViewById(R.id.inputSirName);
        Holder.inputUsername  = (TextView) findViewById(R.id.inputUsername);
        Holder.inputEmail     = (EditText) findViewById(R.id.inputEmail);

        Holder.inputName.setText(userInfo.userFirstName);
        Holder.inputSirName.setText(userInfo.userLastName);
        Holder.inputUsername.setText(userInfo.userLogin);
        Holder.inputEmail.setText(userInfo.userEmail);

    }
    private void setAction() {


        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //change password
        findViewById(R.id.textLinkChangePass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(EditUserActivity.this, ChangePasswordActivity.class)
                     .putExtra("fname",userInfo.userFirstName)
                     .putExtra("lname",userInfo.userLastName)
                      .putExtra("email",userInfo.userEmail)
                );


            }
        });

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Holder.inputName.setBackgroundResource(R.drawable.white_cut_conner_valid);
                Holder.inputSirName.setBackgroundResource(R.drawable.white_cut_conner_valid);
               // Holder.inputUsername.setBackgroundResource(R.drawable.white_cut_conner_valid);
                Holder.inputEmail.setBackgroundResource(R.drawable.white_cut_conner_valid);

                if ( Holder.inputName.length() > 0
                        &&  Holder.inputSirName.length()>0
                        &&  Holder.inputUsername.length()>0
                        && Holder.inputEmail.length()>0){

                    SharedPreferences sp = getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
                    String userId = sp.getString(ServiceInstance.sp_userId, "0");
                    API_EditUser(  userId
                            , Holder.inputUsername.getText().toString()
                            , Holder.inputName.getText().toString()
                            , Holder.inputSirName.getText().toString()
                            , Holder.inputEmail.getText().toString());

                }else{
                    boolean isFocus = false;
                    String errorMsg = "";
                    if(!( Holder.inputName.length() > 0)) {
                        errorMsg += "- ชื่อ \n";
                        Holder.inputName.setBackgroundResource(R.drawable.white_cut_conner_invalid);

                        if(!isFocus) {
                            Holder.inputName.requestFocus();
                            isFocus = true;
                        }
                    }

                    if (!( Holder.inputSirName.length() > 0)){
                        errorMsg += "- นามสกุล \n";
                        Holder.inputSirName.setBackgroundResource(R.drawable.white_cut_conner_invalid);
                        if(!isFocus) {
                            Holder.inputSirName.requestFocus();
                            isFocus = true;
                        }
                    }

                    if (!( Holder.inputEmail.length() > 0)){
                        errorMsg += "- อีเมล์ ";
                        Holder.inputEmail.setBackgroundResource(R.drawable.white_cut_conner_invalid);
                        if(!isFocus) {
                            Holder.inputEmail.requestFocus();
                            isFocus = true;
                        }
                    }
                    new DialogChoice(EditUserActivity.this)
                            .ShowOneChoice("กรุณากรอกข้อมูล", errorMsg);


                }

                /*
                Context context = getApplicationContext();
                CharSequence text = "ปรับปรุงข้อมูลผู้ใช้งานสำเร็จ";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                */
            }
        });



        //change logout
        findViewById(R.id.textLinkLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();

                //Util.showDialogAndDismiss(EditUserActivity.this,"ออกจากระบบสำเร็จ");



                final android.app.Dialog dialog =   new DialogChoice(EditUserActivity.this).Show("ออกจากระบบสำเร็จ","");
                final Handler handler  = new Handler();
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Intent  i= new Intent(EditUserActivity.this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }
                };
                handler.postDelayed(runnable, ServiceInstance.DISMISS_DURATION_MS);


              /*  startActivity(new Intent(LoginActivity.this, WebActivity.class)
                        .putExtra("link", "http://www.google.co.th/"));
                        */
            }
        });


        //tutorial
        findViewById(R.id.btnHowto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogChoice(EditUserActivity.this)
                        .ShowTutorial("g4");

            }
        });


    }


    private void API_EditUser(
             final String userId
            ,final String username
            ,final String name
            ,final String sirName
            ,final String email

    ) {
/*
        1.SaveFlag (บังคับ)
        2.UserID (บังคับกรณี SaveFlag=2)
        3.UserLogin (บังคับกรณี SaveFlag=1 /SaverFlag=2 ไม่ต้องส่งมา ห้ามเปลี่ยน userLogin)
        4.UserPwd (บังคับกรณี SaveFlag=1 /SaveFlag=2 ส่งมาเมื่อเปลี่ยน PWD) ** field นี้เข้ารหัส
        5.UserFirstName (บังคับใส่)
        6.UserLastName (บังคับใส่)
        7.UserEmail (ไม่บังคับ)
        8.ImeiCode
        */
        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {


                mRegister register = (mRegister) obj;
                List<mRegister.mRespBody> mRegisterBodyLists = register.getRespBody();


                if (mRegisterBodyLists.size() != 0) {

                    Util.showDialogAndDismiss(EditUserActivity.this,"ปรับปรุงข้อมูลผู้ใช้งานสำเร็จ");
                }

            }

            @Override
            public void callbackError(int code, String errorMsg) {

                if(code == 4){
                    Holder.inputEmail.setBackgroundResource(R.drawable.white_cut_conner_invalid);
                    Holder.inputEmail.requestFocus();
                }
                new DialogChoice(EditUserActivity.this).ShowOneChoice(errorMsg,"");
            }
        }).API_Request(false, RequestServices.ws_saveRegister +
                "?SaveFlag=" + 2 +
                "&UserID=" + userId +
                "&UserLogin=" +
                "&UserPwd=" +
                "&UserFirstName=" + name +
                "&UserLastName=" + sirName +
                "&UserEmail=" + email +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(EditUserActivity.this)
        );
    }

    /*
    private  void toastDisplayCustom_API(String msg){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        TextView text = (TextView) layout.findViewById(R.id.toast_label);
        text.setText(msg);

        toast.show();
    }
*/
    private static class  Holder {
        static  EditText  inputName,inputSirName,inputEmail;
        static  TextView inputUsername;
    }
}
