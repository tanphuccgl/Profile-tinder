package com.example.profiletinder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.profiletinder.R;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private Bitmap[] bitmaps;
    private Drawable placeholder; // Đối tượng Drawable để giữ ảnh placeholder
    private List<String> mImagePaths;

    public ImageAdapter(Context context,List<String> imagePaths) {
        this.context = context;
        bitmaps = new Bitmap[9];
        mImagePaths = imagePaths;
// Tạo mảng 9 ô chứa ảnh

        // Đặt ảnh placeholder (nếu có)
        placeholder = context.getResources().getDrawable(R.drawable.placeholder_image); // Thay thế placeholder_image bằng ảnh bạn muốn sử dụng làm placeholder
    }

    public void setBitmapAtPosition(Bitmap bitmap, int position) {
        if (position >= 0 && position < bitmaps.length) {
            bitmaps[position] = bitmap;
        }
    }

    @Override
    public int getCount() {
        return bitmaps.length;
    }

    @Override
    public Object getItem(int position) {
        return bitmaps[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(180, 200));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            imageView = (ImageView) convertView;
        }

        // Kiểm tra nếu bitmap tại vị trí này không null thì hiển thị bitmap, ngược lại hiển thị ảnh placeholder
        if (bitmaps[position] != null) {


            imageView.setImageBitmap(bitmaps[position] );
        } else {
            imageView.setImageDrawable(placeholder);
        }

        return imageView;
    }
}
