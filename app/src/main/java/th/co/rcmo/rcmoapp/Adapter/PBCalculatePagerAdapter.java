package th.co.rcmo.rcmoapp.Adapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.List;

import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Model.UserPlotModel;
import th.co.rcmo.rcmoapp.Module.mGetVariable;
import th.co.rcmo.rcmoapp.PBProdDetailCalculateFment;
import th.co.rcmo.rcmoapp.PBProdDetailCalculateFmentA;
import th.co.rcmo.rcmoapp.PBProdDetailMapFment;
import th.co.rcmo.rcmoapp.PBProdDetailStandradFment;
import th.co.rcmo.rcmoapp.ProductDetailCalculateFragmentA;
import th.co.rcmo.rcmoapp.ProductDetailCalculateFragmentB;
import th.co.rcmo.rcmoapp.ProductDetailCalculateFragmentC;
import th.co.rcmo.rcmoapp.ProductDetailCalculateFragmentD;
import th.co.rcmo.rcmoapp.ProductDetailMapFragment;

/**
 * Created by Taweesin on 24/6/2559.
 */
public class PBCalculatePagerAdapter extends FragmentStatePagerAdapter {

    UserPlotModel userPlotModel;
    Context context;


    public PBCalculatePagerAdapter(FragmentManager fm , UserPlotModel userPlotModel) {
        super(fm);
        this.userPlotModel = userPlotModel;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag=null;
        switch (position){
            case 0:
                frag=new PBProdDetailStandradFment();
                break;
            case 1:
                if ("A".equalsIgnoreCase(userPlotModel.getFormularCode())) {
                    frag = new PBProdDetailCalculateFmentA();
                }else{
                    frag = new PBProdDetailCalculateFment();
                }
                /*
                if ("A".equalsIgnoreCase(userPlotModel.getFormularCode())){
                    frag=new ProductDetailCalculateFragmentA();
                }else if ("B".equalsIgnoreCase(userPlotModel.getFormularCode())){
                    frag=new ProductDetailCalculateFragmentB();
                }else if ("C".equalsIgnoreCase(userPlotModel.getFormularCode())){
                    frag=new ProductDetailCalculateFragmentC();
                }else if ("D".equalsIgnoreCase(userPlotModel.getFormularCode())){
                    frag=new ProductDetailCalculateFragmentD();
                }else {
                    frag = new PBProdDetailCalculateFment();
                }
                */
                break;
            case 2:
                frag=new ProductDetailMapFragment();
                //frag=new PBProdDetailMapFment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title=" ";
        switch (position){
            case 0:
                title="ค่ามาตรฐาน";
                break;
            case 1:
                title="คำนวนต้นทุน";
                break;
            case 2:
                title="แผนที่";
                break;
        }

        return title;
    }

}
