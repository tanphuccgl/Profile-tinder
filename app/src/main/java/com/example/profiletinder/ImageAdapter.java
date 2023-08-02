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

// Lớp ImageAdapter là một custom adapter dùng để hiển thị danh sách các ảnh trong GridView.

public class ImageAdapter extends BaseAdapter {

    private Context context; // Context của ứng dụng.
    private Bitmap[] bitmaps; // Mảng chứa các bitmap ảnh.
    private Drawable placeholder; // Đối tượng Drawable để giữ ảnh placeholder.
    private List<String> mImagePaths; // Danh sách đường dẫn của các ảnh.

    // Constructor của lớp ImageAdapter, được sử dụng để khởi tạo dữ liệu cho adapter.
    public ImageAdapter(Context context, List<String> imagePaths) {
        this.context = context; // Gán Context được truyền vào cho biến context.
        bitmaps = new Bitmap[9]; // Khởi tạo mảng bitmaps có kích thước 9, chứa 9 ảnh.
        mImagePaths = imagePaths; // Gán danh sách đường dẫn của các ảnh cho biến mImagePaths.

        // Đặt ảnh placeholder (nếu có) bằng ảnh từ resource drawable.
        placeholder = context.getResources().getDrawable(R.drawable.placeholder_image); // Thay thế placeholder_image bằng ảnh bạn muốn sử dụng làm placeholder.
    }

    // Phương thức setBitmapAtPosition() dùng để đặt một bitmap vào vị trí đã chỉ định trong mảng bitmaps.
    public void setBitmapAtPosition(Bitmap bitmap, int position) {
        if (position >= 0 && position < bitmaps.length) {
            bitmaps[position] = bitmap;
        }
    }

    // Phương thức getCount() được ghi đè từ BaseAdapter để trả về số lượng phần tử trong danh sách ảnh.
    @Override
    public int getCount() {
        return bitmaps.length;
    }

    // Phương thức getItem() được ghi đè từ BaseAdapter để trả về phần tử tại vị trí position trong danh sách ảnh.
    @Override
    public Object getItem(int position) {
        return bitmaps[position];
    }

    // Phương thức getItemId() được ghi đè từ BaseAdapter để trả về ID của phần tử tại vị trí position trong danh sách ảnh.
    @Override
    public long getItemId(int position) {
        return position;
    }

    // Phương thức getView() được ghi đè từ BaseAdapter để tạo và trả về View cho mỗi phần tử trong GridView.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // Nếu convertView chưa tồn tại (null), tạo mới một ImageView.
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(180, 200)); // Đặt kích thước cho ImageView.
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER); // Đặt kiểu scale cho ImageView.
        } else {
            imageView = (ImageView) convertView; // Nếu convertView đã tồn tại, tái sử dụng lại ImageView đã có.
        }

        // Kiểm tra nếu bitmap tại vị trí này không null thì hiển thị bitmap, ngược lại hiển thị ảnh placeholder.
        if (bitmaps[position] != null) {
            imageView.setImageBitmap(bitmaps[position]); // Hiển thị bitmap tại vị trí này trong ImageView.
        } else {
            imageView.setImageDrawable(placeholder); // Hiển thị ảnh placeholder trong ImageView.
        }

        // Trả về ImageView chứa thông tin của phần tử tại vị trí position trong danh sách ảnh.
        return imageView;
    }
}
