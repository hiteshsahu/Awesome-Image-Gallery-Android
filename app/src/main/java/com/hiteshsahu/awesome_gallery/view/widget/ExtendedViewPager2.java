//package com.hiteshsahu.awesome_gallery.view.widget;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.viewpager2.widget.ViewPager2;
//
//import com.hiteshsahu.awesome_gallery.R;
//
//
//public class ExtendedViewPager2 extends ViewPager2 {
//
//
//    public ExtendedViewPager2(@NonNull Context context) {
//        super(context);
//    }
//
//    public ExtendedViewPager2(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public ExtendedViewPager2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    public ExtendedViewPager2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent event) {
//        TouchImageView touchImageView = findViewById(R.id.touchImageView);
//        if (touchImageView != null) {
//            requestDisallowInterceptTouchEvent(false);
//        }
//
//        return super.onInterceptTouchEvent(event);
//    }
//
//
//}
