package com.example.viewpagerstrip;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;


public class ViewPagerListFragment extends Fragment implements TabHolderScrollingContent{
	private ArrayList<String> mItems = new ArrayList<String>();
	private ListView list;
	private OnScrollListener mListener;
//	private StickyListHeaderView stickyListHeaderView;
	private View headerView;
	private int index;
	private boolean isTouch_Scroll;
	private ListAdapter adapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		index = getArguments().getInt("index");
/*		mItems.add("역지사지");
		mItems.add("연목구어");
		mItems.add("연하고질");
		mItems.add("염량세태");
		mItems.add("우후죽순");
		mItems.add("화사첨족");
		mItems.add("천태만상");
		mItems.add("어부지리");
		mItems.add("일거일동");
		mItems.add("이구동성");
		mItems.add("삼라만상");
		mItems.add("일거일득");
		mItems.add("일문일답");
		mItems.add("일목십항");
		mItems.add("일사일생");
		mItems.add("사면팔방");
		mItems.add("십중팔구");
		mItems.add("호가호위");
		mItems.add("천근만근");
		mItems.add("천차만별");
		mItems.add("가가호호");
		mItems.add("가인박명");
		mItems.add("간난신고");*/

		for (int i = 0; i < index* 5 + 10; i++) {
			mItems.add("这是第" + i + "条数据");
		}
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final Activity a = getActivity();
		View view =  getView();
		list = (ListView)view.findViewById(R.id.listView1);
		list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		headerView = (View) LayoutInflater.from(a).inflate(R.layout.view_fake_header, list, false);
		list.addHeaderView(headerView);
//		final int index = getArguments().getInt("index");
		adapter = new ListAdapter(getActivity(), mItems);
		list.setAdapter(adapter);
		list.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				Log.v("DEBUG32","리스트뷰스크롤");
				if(isTouch_Scroll)
					mListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount,index);
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
//				if(scrollState != SCROLL_STATE_IDLE){
//					isTouch_Scroll = true;
//				}else{
//					isTouch_Scroll = false;
//				}
				isTouch_Scroll = scrollState != SCROLL_STATE_IDLE;
				Log.v("DEBUG32","리스트뷰스크롤상태:"+scrollState);
			}
		});
	}

	@Override
	public void adjustScroll(int position,int tabBarTop) {
/*		if (tabBarTop == 0 && list.getFirstVisiblePosition() >= 1) {
			return;
		}*/
		list.setSelectionFromTop(position, tabBarTop);
	}
	public void onScrollListener(OnScrollListener listener){
		this.mListener = listener;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}
}
