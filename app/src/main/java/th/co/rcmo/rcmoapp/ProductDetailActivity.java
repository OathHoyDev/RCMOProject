package th.co.rcmo.rcmoapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Model.ProductDetailModel;
import th.co.rcmo.rcmoapp.Model.UserPlotModel;
import th.co.rcmo.rcmoapp.Module.mGetPlotDetail;
import th.co.rcmo.rcmoapp.Module.mProduct;
import th.co.rcmo.rcmoapp.Util.CalculateConstant;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.View.DialogChoice;

public class ProductDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static UserPlotModel userPlotModel;
    private String productType;
    private Bundle bundle;
    Context context;

//    public ProductDetailModel getProductDetailModel() {
//        return productDetailModel;
//    }
//
//    public void setProductDetailModel(ProductDetailModel productDetailModel) {
//        this.productDetailModel = productDetailModel;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_detail);
        context = getBaseContext();

//        userPlotModel = new UserPlotModel();

        // For Test
        //testDataMethod();

        setAction();

        setUI();




    }

    private void setAction(){

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }



    private void createBundleForFragment(){
        bundle = new Bundle();
        bundle.putString("fisheryType" , userPlotModel.getFisheryType());
        bundle.putString("prdID" , userPlotModel.getPrdID());
        bundle.putString("productType", userPlotModel.getPrdGrpID());
        bundle.putString("plodID", userPlotModel.getPlotID());

        if ("".equalsIgnoreCase(userPlotModel.getTamCode())){
            bundle.putString("suitFlag" , "2");
        }else{
            bundle.putString("suitFlag" , "1");
        }

        bundle.putString("prdGrpID" , userPlotModel.getPrdGrpID());

        bundle.putString("tamCode" , userPlotModel.getTamCode());
        bundle.putString("ampCode" , userPlotModel.getAmpCode());
        bundle.putString("provCode" , userPlotModel.getProvCode());
        bundle.putString("userID" , userPlotModel.getUserID());
    }

    private void setUI(){

        if (userPlotModel.getUserID() != null && !"".equalsIgnoreCase(userPlotModel.getUserID())) {
            API_getPlotDetail(userPlotModel.getPlotID());
        }else{
            API_getProduct(userPlotModel.getPrdID() , userPlotModel.getPrdGrpID() , "0");

            createBundleForFragment();

            initialTab();
        }


    }

//    private void testDataMethod(){
//
//        userPlotModel.setPlotID("7");
//        userPlotModel.setTamCode("2");
//        userPlotModel.setAmpCode("2");
//        userPlotModel.setProvCode("14");
//    }

    private void initialTab(){

        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.BLACK);
        }

        RelativeLayout headerLayout = (RelativeLayout) findViewById(R.id.headerLayout);
        switch (userPlotModel.getPrdGrpID()){
            case CalculateConstant.PRODUCT_TYPE_PLANT:
                headerLayout.setBackgroundResource(R.color.RcmoPlantBG);
                break;
            case CalculateConstant.PRODUCT_TYPE_ANIMAL:
                headerLayout.setBackgroundResource(R.color.RcmoAnimalBG);
                break;
            case CalculateConstant.PRODUCT_TYPE_FISH:
                headerLayout.setBackgroundResource(R.color.RcmoFishBG);
                break;
        }

        //setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);



        viewPager = (ViewPager) findViewById(R.id.viewpager);
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);

        ProductDetailStandardFragment productDetailStandardFragment = new ProductDetailStandardFragment();
        productDetailStandardFragment.setArguments(bundle);
        adapter.addFragment(productDetailStandardFragment);

        ProductDetailCalculateFragmentD productDetailCalculateFragmentD = new ProductDetailCalculateFragmentD();
        productDetailCalculateFragmentD.setArguments(bundle);
        adapter.addFragment(productDetailCalculateFragmentD);

        ProductDetailMapFragment productDetailMapFragment = new ProductDetailMapFragment();
        productDetailMapFragment.setArguments(bundle);
        adapter.addFragment(productDetailMapFragment);

        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());


        /*
         tabLayout.addTab(tabLayout.newTab().setCustomView(adapter.getTabView(0)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(adapter.getTabView(1)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(adapter.getTabView(2)));

         */

     //   tabLayout.setBackgroundResource(android.R.color.transparent);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

        //tabLayout.setBackgroundResource(R.drawable.action_tab_plant);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.setCurrentItem(1);
        tabLayout.getTabAt(1).getCustomView().setSelected(true);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        Context context;

        public ViewPagerAdapter(FragmentManager manager, Context context) {

            super(manager);
            this.context = context;
        }

        public View getTabView(final int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View v = LayoutInflater.from(context).inflate(R.layout.custom_product_detail_tab, null);
            TextView tabImage = (TextView) v.findViewById(R.id.tabImage);
            FrameLayout tabLayout = (FrameLayout) v.findViewById(R.id.tabLayout);
            //tabImage.setBackgroundResource(R.drawable.bg_tab_green_dark);

            switch (userPlotModel.getPrdGrpID()){
                case CalculateConstant.PRODUCT_TYPE_PLANT:
                    tabLayout.setBackgroundResource(R.drawable.action_tab_plant);

                    switch (position){

                        case 0:

                            tabImage.setText("ค่ามาตรฐาน");
                            //tabImage.setBackgroundResource(R.drawable.action_tab_plant_standard);
                            break;
                        case 1:
                            tabImage.setText("คำนวนต้นทุน");
                            //tabImage.setBackgroundResource(R.drawable.action_tab_plant_calculate);
                            break;
                        case 2:
                            tabImage.setText("แผนที่");
                           // tabImage.setBackgroundResource(R.drawable.action_tab_plant_map);
                            break;
                    }
                    break;
                case CalculateConstant.PRODUCT_TYPE_ANIMAL:
                    tabLayout.setBackgroundResource(R.drawable.action_tab_animal);
                    switch (position){

                        case 0:
                            tabImage.setText("ค่ามาตรฐาน");
                           // tabImage.setBackgroundResource(R.drawable.action_tab_animal_standard);
                            break;
                        case 1:
                            tabImage.setText("คำนวนต้นทุน");
                           // tabImage.setBackgroundResource(R.drawable.action_tab_animal_calculate);
                            break;
                        case 2:
                            tabImage.setText("แผนที่");
                          //  tabImage.setBackgroundResource(R.drawable.action_tab_animal_map);
                            break;
                    }
                    break;
                case CalculateConstant.PRODUCT_TYPE_FISH:
                    tabLayout.setBackgroundResource(R.drawable.action_tab_fish);
                    switch (position){
                        case 0:
                            tabImage.setText("ค่ามาตรฐาน");
                          //  tabImage.setBackgroundResource(R.drawable.action_tab_fish_standard);
                            break;
                        case 1:
                            tabImage.setText("คำนวนต้นทุน");
                          //  tabImage.setBackgroundResource(R.drawable.action_tab_fish_calculate);
                            break;
                        case 2:
                            tabImage.setText("แผนที่");
                          //  tabImage.setBackgroundResource(R.drawable.action_tab_fish_map);
                            break;
                    }
                    break;
            }


            return v;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);

        }
    }

    private void API_getPlotDetail(String plodID) {
        /**
         1.TamCode (ไม่บังคับใส่)
         2.AmpCode (บังคับใส่)
         3.ProvCode (บังคับใส่)
         */
        new ResponseAPI(context, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mGetPlotDetail mPlotDetail = (mGetPlotDetail) obj;
                List<mGetPlotDetail.mRespBody> mPlotDetailBodyLists = mPlotDetail.getRespBody();

                if (mPlotDetailBodyLists.size() != 0) {

                    userPlotModel.setTamCode(mPlotDetailBodyLists.get(0).getTamCode());
                    userPlotModel.setAmpCode(mPlotDetailBodyLists.get(0).getAmpCode());
                    userPlotModel.setProvCode(mPlotDetailBodyLists.get(0).getProvCode());

                    API_getProduct(mPlotDetailBodyLists.get(0).getPrdID() , mPlotDetailBodyLists.get(0).getPrdGrpID() , mPlotDetailBodyLists.get(0).getPlantGrpID());

                    createBundleForFragment();

                    initialTab();
                }


            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getPlotDetail +
                "?PlotID=" + plodID +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(context));

    }

    private void API_getProduct(String prdID , String prdGrpID , String plantGrpID) {

        new ResponseAPI(context, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mProduct mProduct = (mProduct) obj;
                List<mProduct.mRespBody> mProductBodyLists = mProduct.getRespBody();

                if (mProductBodyLists.size() != 0) {

                    RelativeLayout headerLayout  =     (RelativeLayout)findViewById(R.id.headerLayout);
                    com.neopixl.pixlui.components.textview.TextView titleText = (com.neopixl.pixlui.components.textview.TextView)headerLayout.findViewById(R.id.titleLable);
                    titleText.setText(mProductBodyLists.get(0).getPrdName());

                }


            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getProduct +
                "?PrdID=" + prdID +
                "&PrdGrpID=" + prdGrpID +
                "&PlantGrpID=" + plantGrpID);

    }

}