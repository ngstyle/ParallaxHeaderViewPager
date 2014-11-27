package com.example.viewpagerstrip;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class PageStripViewPager extends ViewPager {
	public enum State {
		IDLE,
		GOING_LEFT,
		GOING_RIGHT
	}
	private State mState;
	private int oldPage;
	public PageStripViewPager(Context context) {
		super(context);
	}

	public PageStripViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		if (mState == State.IDLE && positionOffset > 0) {
			oldPage = getCurrentItem();
			mState = position == oldPage ? State.GOING_RIGHT : State.GOING_LEFT;
		}
		boolean goingRight = position == oldPage;				
		if (mState == State.GOING_RIGHT && !goingRight)
			mState = State.GOING_LEFT;
		else if (mState == State.GOING_LEFT && goingRight)
			mState = State.GOING_RIGHT;
		float effectOffset = isSmall(positionOffset) ? 0 : positionOffset;
		super.onPageScrolled(position, positionOffset, positionOffsetPixels);
		if (effectOffset == 0) {
			mState = State.IDLE;
		}
	}
	private boolean isSmall(float positionOffset) {
		return Math.abs(positionOffset) < 0.0001;
	}
	public State getState(){
		return mState;
	}
}
