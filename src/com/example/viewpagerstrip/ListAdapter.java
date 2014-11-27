package com.example.viewpagerstrip;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<String>mList;
	public ListAdapter(Context context,ArrayList<String> list) {
		mContext = context;
		mList = list;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public String getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		View view = convertView;
		if(view == null){
			holder = new ViewHolder();
			view = (View)View.inflate(mContext,R.layout.list_item,null);
			holder.textView = (TextView) view.findViewById(R.id.textView); 
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}
		if(getItem(position) != null){
			holder.textView.setText(getItem(position));
		}
		return view;
	}
	public class ViewHolder{
		public TextView textView;
	}

}
