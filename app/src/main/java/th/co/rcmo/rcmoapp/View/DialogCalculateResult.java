package th.co.rcmo.rcmoapp.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.StringBuilderPrinter;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.neopixl.pixlui.components.textview.TextView;

import th.co.rcmo.rcmoapp.CalculateResultActivity;
import th.co.rcmo.rcmoapp.Model.UserPlotModel;
import th.co.rcmo.rcmoapp.Model.calculate.CalculateResultModel;
import th.co.rcmo.rcmoapp.R;
import th.co.rcmo.rcmoapp.RegisterActivity;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.Util.Util;


public class DialogCalculateResult {

    public static int OK = 0;
    public static int CANCEL = 1;
    Context context;
    OnSelectChoiceListener onSelectChoiceListener;

    public static UserPlotModel userPlotModel;
    public static CalculateResultModel calculateResultModel;

    public interface OnSelectChoiceListener {
        void OnSelect(int choice);
    }

    public DialogCalculateResult(Context c, OnSelectChoiceListener onSelectChoiceListener) {
        this.context = c;
        this.onSelectChoiceListener = onSelectChoiceListener;
    }

    public DialogCalculateResult(Context c) {
        this.context = c;
    }


    public android.app.Dialog Show() {
        final android.app.Dialog dialog = new android.app.Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_calculate_result);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

        ImageView iconResult = (ImageView) dialog.findViewById(R.id.calResultProfitLossImage);
        TextView txResultString = (TextView) dialog.findViewById(R.id.txResultString);
        TextView txResult = (TextView) dialog.findViewById(R.id.txResult);
        TextView txResultValue = (TextView) dialog.findViewById(R.id.txResultValue);

        ImageButton btnShowReport = (ImageButton) dialog.findViewById(R.id.btnShowReport);

        if (calculateResultModel.calculateResult > 0) {
            iconResult.setBackgroundResource(R.drawable.ic_profit);
            txResultString.setText("ดีใจด้วย");
            txResult.setText("คุณได้กำไร");
            txResultValue.setText("จำนวน " + String.format("%,.2f", calculateResultModel.calculateResult) + " บาท");
        } else {
            iconResult.setBackgroundResource(R.drawable.ic_profit);
            txResultString.setText("ดีใจด้วย");
            txResult.setText("คุณได้กำไร");
            txResultValue.setText("จำนวน " + String.format("%,.2f", calculateResultModel.calculateResult) + " บาท");
        }

        btnShowReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalculateResultActivity.calculateResultModel = calculateResultModel;
                CalculateResultActivity.userPlotModel = userPlotModel;
                context.startActivity(new Intent(context, CalculateResultActivity.class));
            }
        });

        dialog.show();


        return dialog;
    }


    public void ShowOneChoice(String t, String msg) {
        final android.app.Dialog dialog = new android.app.Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_choice);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

        TextView title = (TextView) dialog.findViewById(R.id.title);
        TextView detail = (TextView) dialog.findViewById(R.id.message);
        TextView btn_ok = (TextView) dialog.findViewById(R.id.ok);

        // dialog.findViewById(R.id.cancel).setVisibility(View.GONE);
        //dialog.findViewById(R.id.line).setVisibility(View.GONE);
        if (t.length() == 0) title.setVisibility(View.GONE);
        else title.setText(t);
        detail.setText(msg);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSelectChoiceListener != null)
                    onSelectChoiceListener.OnSelect(OK);
                dialog.dismiss();
            }
        });


        dialog.show();


    }

    public void ShowTwoChoice(String t, String msg) {

        final android.app.Dialog dialog = new android.app.Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_two_choice);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView title = (TextView) dialog.findViewById(R.id.title);
        TextView detail = (TextView) dialog.findViewById(R.id.message);
        TextView btn_cancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView btn_ok = (TextView) dialog.findViewById(R.id.ok);

        if (t.length() == 0) title.setVisibility(View.GONE);
        else title.setText(t);
        detail.setText(msg);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSelectChoiceListener != null)
                    onSelectChoiceListener.OnSelect(OK);
                dialog.dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSelectChoiceListener != null)
                    onSelectChoiceListener.OnSelect(CANCEL);
                dialog.cancel();
            }
        });
        dialog.show();

    }


    public void ShowAppLink() {
        final android.app.Dialog dialog = new android.app.Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_app_link);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

        wmlp.gravity = Gravity.TOP | Gravity.LEFT;

        ImageView dialogGotoOtherApp = (ImageView) dialog.findViewById(R.id.dialogGotoOtherApp);
        ImageView imgOicApp = (ImageView) dialog.findViewById(R.id.imgOicApp);
        ImageView imgEconApp = (ImageView) dialog.findViewById(R.id.imgEconApp);


        dialogGotoOtherApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSelectChoiceListener != null)
                    onSelectChoiceListener.OnSelect(OK);
                dialog.dismiss();
            }
        });
        imgOicApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.onLunchAnotherApp(context, ServiceInstance.OIC_PACKAGE_NAME);
                dialog.dismiss();
            }
        });
        imgEconApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.onLunchAnotherApp(context, ServiceInstance.ECON_PACKAGE_NAME);
                dialog.dismiss();
            }
        });
        // wmlp.x = 100;   //x position
        //wmlp.y = 100;   //y position

//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

        /*
        TextView title =(TextView) dialog.findViewById(R.id.title);
        TextView detail = (TextView)dialog.findViewById(R.id.message);
        TextView btn_ok = (TextView)dialog.findViewById(R.id.ok);

        // dialog.findViewById(R.id.cancel).setVisibility(View.GONE);
        //dialog.findViewById(R.id.line).setVisibility(View.GONE);
        if(t.length()==0) title.setVisibility(View.GONE);
        else title.setText(t);
        detail.setText(msg);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onSelectChoiceListener!=null)
                    onSelectChoiceListener.OnSelect(OK);
                dialog.dismiss();
            }
        });

*/
        dialog.show();


    }

        /*
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onSelectChoiceListener!=null)
                    onSelectChoiceListener.OnSelect(CANCEL);
                dialog.cancel();
            }
        });
        */
    //   dialog.show();

}
//    public void ShowOneChoice(String title, String message){
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                context);
//
//        if(title.length()!=0)
//            alertDialogBuilder.setTitle(title);
//
//        alertDialogBuilder
//                .setMessage(message)
//                .setCancelable(false)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                       if(onSelectChoiceListener!=null)
//                           onSelectChoiceListener.OnSelect(OK);
//                    }
//                });
//
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//    }

//    public  void ShowTwoChoice(String title,String message){
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                context);
//
//        if(title.length()!=0)
//            alertDialogBuilder.setTitle(title);
//
//        alertDialogBuilder
//                .setMessage(message)
//                .setCancelable(false)
//                .setPositiveButton("ตกลง",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int id) {
//                        onSelectChoiceListener.OnSelect(OK);
//                    }
//                })
//                .setNegativeButton("ยกเลิก",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int id) {
//                        onSelectChoiceListener.OnSelect(CANCEL);
//                        dialog.cancel();
//                    }
//                });
//
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//    }

//}