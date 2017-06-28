package vn.asiantech.internship.ui.viewpager;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.asiantech.internship.R;

/**
 * Created by Hai on 6/28/2017.
 */
class CPagerAdapter extends PagerAdapter {
    private int[] mImageResources;

    CPagerAdapter(int[] imageResources) {
        mImageResources = imageResources;
    }

    @Override
    public int getCount() {
        return mImageResources.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_viewpager_c, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imgViewC);
        imageView.setImageResource(mImageResources[position]);
        container.addView(view);
        return view;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
