package com.example.viewpagerstrip;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.example.viewpagerstrip.PageStripViewPager.State;
import com.nineoldandroids.view.ViewHelper;

public class MainActivity extends FragmentActivity implements OnScrollListener{
	private SlidingTabLayout slidingTab;
	private SparseArrayCompat<TabHolderScrollingContent> tabHolderScrollingContent = new SparseArrayCompat<TabHolderScrollingContent>();
	private SparseArray<int[]> positionInfo = new SparseArray<int[]>();			// 记录listview当前位置信息
	private PageStripViewPager viewPager;
	private SectionPagerBottomAdapter adapter;
	private ViewGroup header;
	private ImageView imageview;
	private int scrollY;
	private boolean isDragging;
	private boolean isTopHidden;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		header = (ViewGroup) findViewById(R.id.header);
		imageview = (ImageView) findViewById(R.id.imageView1);
		slidingTab = (SlidingTabLayout) findViewById(R.id.tabstrip);
		viewPager = (PageStripViewPager)findViewById(R.id.view_pager);
		adapter = new SectionPagerBottomAdapter(getSupportFragmentManager());
		viewPager.setOffscreenPageLimit(adapter.getCount());
		viewPager.setAdapter(adapter);
		slidingTab.setViewPager(viewPager);
		slidingTab.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int state) {
				scrollY = scrollY == 0 ? header.getHeight():scrollY;
				isDragging = state == ViewPager.SCROLL_STATE_DRAGGING;
			}
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				adjustPosition(position, tabHolderScrollingContent.valueAt(position));
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				super.onPageScrolled(position, positionOffset, positionOffsetPixels);
				if (positionOffsetPixels > 0) {
					int newPosition = viewPager.getState() == State.GOING_RIGHT ? position + 1 : position;
					adjustPosition(newPosition, tabHolderScrollingContent.valueAt(newPosition));
				}
			}
			
			private void adjustPosition(int position,TabHolderScrollingContent fragmentContent) {
				int[] arr = positionInfo.get(position);
				if(isTopHidden){
					fragmentContent.adjustScroll(arr != null ? arr[0] : 1, arr != null ? arr[1] : scrollY);
					ViewHelper.setTranslationY(header, -imageview.getHeight());
				}else{
					positionInfo.clear();
					fragmentContent.adjustScroll(1,scrollY);
				}
				// TODO listView的高度不足隐藏头部时的bug
//				ViewHelper.setTranslationY(header, 0);
//				ViewHelper.setTranslationY(imageview, 0);
//				if(arr != null && arr[0] == 0){
//					ViewHelper.setTranslationY(header, arr[1]);
//				}
			}
		});
	}

	public class SectionPagerBottomAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "Categories", "Home", "Top Paid", "Top Free", "Top Grossing", "Top New Paid", "Top New Free", "Trending" };

		public SectionPagerBottomAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public Fragment getItem(int position) {
			Bundle args = new Bundle();
			args.putInt("index", position);
			ViewPagerListFragment fragemt = new ViewPagerListFragment();
			fragemt.onScrollListener(MainActivity.this);
			fragemt.setArguments(args);
			tabHolderScrollingContent.put(position, fragemt);
			return fragemt;
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pageIdx) {
		if(pageIdx == viewPager.getCurrentItem() && !isDragging){
			if (firstVisibleItem != 0) {
				scrollY = slidingTab.getHeight(); 
				if(ViewHelper.getTranslationY(header) != -imageview.getHeight())
					ViewHelper.setTranslationY(header, -imageview.getHeight());
				positionInfo.put(pageIdx, new int[]{firstVisibleItem,view.getChildAt(0).getTop()});
			}else {
				View firstChild = view.getChildAt(0);
				if (firstChild == null) {
					return;
				}
				positionInfo.put(pageIdx, new int[]{firstVisibleItem,firstChild.getTop()});
//				int maxScrollHeight = header.getHeight() - slidingTab.getHeight();			// 头部滚动空间
//				isTopHidden = header.getHeight() + firstChild.getTop() <= slidingTab.getHeight();
				isTopHidden =  -firstChild.getTop() >= imageview.getHeight();
				
				if (!isTopHidden) {
					scrollY = header.getHeight() + firstChild.getTop();
//					Log.v("DEBUG32","scrollY11 : " +scrollY);
//					int i = -firstChild.getTop() / 2;
					ViewHelper.setTranslationY(imageview,-firstChild.getTop()/2);
//					ViewHelper.setTranslationY(header, -header.getHeight() + scrollY);
					ViewHelper.setTranslationY(header, firstChild.getTop());
				}else{
					scrollY = slidingTab.getHeight(); 
//					Log.v("DEBUG32","scrollY22 : " +scrollY);
//					ViewHelper.setTranslationY(header, -maxScrollHeight);
					ViewHelper.setTranslationY(header, -imageview.getHeight());
				}
			}
				
		}
		
	}
/*	public int getScrollY(AbsListView view) {
		View c = view.getChildAt(0);
		if (c == null) {
			return 0;
		}
		int firstVisiblePosition = view.getFirstVisiblePosition();
		int top = c.getTop();
		Log.v("DEBUG32","top :" + c.getTop());
		int headerHeight = 0;
		if (firstVisiblePosition >= 1) {
			headerHeight = header.getHeight();
		}
		return -top + firstVisiblePosition * c.getHeight() + headerHeight;
	}*/
}
