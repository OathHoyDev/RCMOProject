package th.co.rcmo.rcmoapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import th.co.rcmo.rcmoapp.Module.mMainProductList;
import th.co.rcmo.rcmoapp.R;

/**
 * Created by Taweesin on 5/17/2016.
 */
public class MainPlantProductAdapter extends BaseAdapter {
    Context mContext;
    List<mMainProductList.mRespBody> productLists;

    public MainPlantProductAdapter( Context mContext , List<mMainProductList.mRespBody> productLists) {
        this.mContext = mContext;
        this.productLists = productLists;
    }

    @Override
    public int getCount() {
        return productLists.size();
    }

    @Override
    public mMainProductList.mRespBody getItem(int position) {
        return productLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.product_listview_row, parent, false);
        }


        mMainProductList.mRespBody productInfo = getItem(position);

        TextView textView = (TextView)convertView.findViewById(R.id.textView1);
        textView.setText(productInfo.getProductName());

        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView1);
        imageView.setBackgroundResource(productInfo.getProductIconImg());

        return convertView;
    }
}
