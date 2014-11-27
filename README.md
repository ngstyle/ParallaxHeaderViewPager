ParallaxHeaderViewPager
=======================

RelativeLayout hosting a ViewPager

Use a RelativeLayout as our outer container, which hosts the ViewPager and consists of three scrollable fragments. This outer RelativeLayout also hosts the top image and toolbar.

Each of the ViewPager’s fragments implements a callback that is responsible for keeping track of, and updating its scroll position and the top offset within the parent RelativeLayout. As the user scrolls up and down, or side-swipes to a different tab, the callback in the individual ViewPager fragment intelligently adjusts the contained ScrollView’s or ListView’s internal top offset and scroll position to reveal the correct amount of parallaxed image underneath, achieving the visual effect desired.