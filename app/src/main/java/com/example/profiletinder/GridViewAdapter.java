package com.example.profiletinder;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import java.util.List;

// Lớp GridViewAdapter là một custom adapter dùng để cung cấp dữ liệu cho GridView trong ứng dụng Android.

public class GridViewAdapter extends BaseAdapter {

    private Context mContext; // Context của ứng dụng.
    private List<String> mData; // Danh sách dữ liệu để hiển thị trong GridView.

    // Constructor của lớp GridViewAdapter, được sử dụng để khởi tạo dữ liệu cho adapter.
    public GridViewAdapter(Context context, List<String> data) {
        mContext = context; // Gán Context được truyền vào cho biến mContext.
        mData = data; // Gán danh sách dữ liệu được truyền vào cho biến mData.
    }

    // Phương thức getCount() được ghi đè từ BaseAdapter để trả về số lượng phần tử trong danh sách dữ liệu.
    @Override
    public int getCount() {
        return mData.size();
    }
    public void updateData(List<String> newData) {
        mData = newData;
        notifyDataSetChanged();
    }


    // Phương thức getItem() được ghi đè từ BaseAdapter để trả về phần tử tại vị trí position trong danh sách dữ liệu.
    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    // Phương thức getItemId() được ghi đè từ BaseAdapter để trả về ID của phần tử tại vị trí position trong danh sách dữ liệu.
    @Override
    public long getItemId(int position) {
        return position;
    }

    // Phương thức getView() được ghi đè từ BaseAdapter để tạo và trả về View cho mỗi phần tử trong GridView.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Kiểm tra nếu convertView chưa tồn tại (null), thì tạo mới bằng cách inflate layout grid_item2.
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item2, parent, false);
        }

        // Lấy tham chiếu đến EditText trong convertView, sau đó thiết lập văn bản cho EditText từ danh sách dữ liệu mData tại vị trí position.
        EditText editText = convertView.findViewById(R.id.editText5);
        editText.setText(mData.get(position));
        // Lắng nghe sự kiện nhập dữ liệu cho EditText
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mData.set(position, charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        // Trả về convertView chứa thông tin của phần tử tại vị trí position trong danh sách dữ liệu.
        return convertView;
    }
}
