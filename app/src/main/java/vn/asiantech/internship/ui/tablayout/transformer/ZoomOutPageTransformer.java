package vn.asiantech.internship.ui.tablayout.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * ZoomOutPageTransformer created by Thanh Thien
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);

        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            float min_scale = 0.85f;
            float scaleFactor = Math.max(min_scale, 1 - Math.abs(position));
            float verticalMargin = pageHeight * (1 - scaleFactor) / 2;
            float horizontalMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horizontalMargin - verticalMargin / 2);
            } else {
                view.setTranslationX(-horizontalMargin + verticalMargin / 2);
            }

            // Scale the page down (between min_scale and 1)
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            // Fade the page relative to its size.
            float min_alpha = 0.5f;
            view.setAlpha(min_alpha +
                    (scaleFactor - min_scale) /
                            (1 - min_scale) * (1 - min_alpha));

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}
