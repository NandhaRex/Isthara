package com.tao.isthara.Factory.Tools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

   // fragments to instantiate in the viewpager
   private List<Fragment> fragments;
   
   // constructor
   public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
      super(fm);
      this.fragments = fragments;
   }
   
   // return access to fragment from position, required override
   @Override
   public Fragment getItem(int position) {
      return this.fragments.get(position);
   }

   // number of fragments in list, required override
   @Override
   public int getCount() {
      return this.fragments.size();
   }

/* (non-Javadoc)
 * @see android.support.v4.view.PagerAdapter#getItemPosition(java.lang.Object)
 */
@Override
public int getItemPosition(Object object) {
	// TODO Auto-generated method stub
	return super.getItemPosition(object);
	
}

/* (non-Javadoc)
 * @see android.support.v4.view.PagerAdapter#getPageTitle(int)
 */
@Override
public CharSequence getPageTitle(int position) {
	// TODO Auto-generated method stub
	
	return super.getPageTitle(position);
}




}
