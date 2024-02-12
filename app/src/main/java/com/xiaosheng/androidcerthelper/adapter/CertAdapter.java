package com.xiaosheng.androidcerthelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xiaosheng.androidcerthelper.R;
import com.xiaosheng.androidcerthelper.entiy.CertInfo;

import java.util.List;

public class CertAdapter extends ArrayAdapter<CertInfo> {
    private int resourceId;
    public CertAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
    public CertAdapter(Context context, int resource, List<CertInfo> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CertInfo book = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

            viewHolder = new ViewHolder();

            viewHolder.title = (TextView)view.findViewById(R.id.tv_list_title);
            viewHolder.expireTm = (TextView)view.findViewById(R.id.expire_tm);
            viewHolder.issuer = (TextView)view.findViewById(R.id.issr);

            view.setTag(viewHolder);//将viewHolder存储在view中
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag(); //重新获取viewHolder
        }

        viewHolder.title.setText(book.getTitle());
        viewHolder.expireTm.setText(String.valueOf(book.getExpireTm()));
        viewHolder.issuer.setText(book.getIssuer());

        return view;
    }
    class ViewHolder{
        TextView title;
        TextView expireTm;
        TextView issuer;
    }

}
