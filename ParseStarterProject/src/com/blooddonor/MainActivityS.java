package com.blooddonor;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.starter.R;

/**
 * Created by claudiu.haidu on 8/6/2015.
 */
public class MainActivityS extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_s);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        String label1 = getResources().getString(R.string.label1);
        ActionBar.Tab tab = actionBar.newTab();
        tab.setText(label1);
        TabListener<Tab1Fragment> t1 = new TabListener<Tab1Fragment>(this,label1,Tab1Fragment.class);
        tab.setTabListener(t1);
        actionBar.addTab(tab);

        String label2 = getResources().getString(R.string.label2);
        tab= actionBar.newTab();
        tab.setText(label2);
        TabListener<Tab2Fragment> t2 = new TabListener<Tab2Fragment>(this,label2,Tab2Fragment.class);
        tab.setTabListener(t2);
        actionBar.addTab(tab);

        String label3 = getResources().getString(R.string.label3);
        tab= actionBar.newTab();
        tab.setText(label3);
        TabListener<Tab3Fragment> t3 = new TabListener<Tab3Fragment>(this,label3,Tab3Fragment.class);
        tab.setTabListener(t3);
        actionBar.addTab(tab);

        String label4 = getResources().getString(R.string.label4);
        tab= actionBar.newTab();
        tab.setText(label4);
        TabListener<Tab4Fragment> t4 = new TabListener<Tab4Fragment>(this,label4,Tab4Fragment.class);
        tab.setTabListener(t4);
        actionBar.addTab(tab);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class TabListener<T extends Fragment> implements ActionBar.TabListener{
        private Fragment mFragment;
        private final Activity mActivity;
        private final String mTag;
        private final Class<T> mClass;

        public TabListener(Activity activity,String tag, Class<T> clz)
        {
            mActivity = activity;
            mTag = tag;
            mClass = clz;
        }
        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

            if (mFragment ==null) {
                mFragment = Fragment.instantiate(mActivity, mClass.getName());
                ft.add(android.R.id.content, mFragment, mTag);
            }
            else {
                ft.attach(mFragment);
            }
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            if (mFragment !=null) {
                ft.detach(mFragment);
            }

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {


        }
    }
}
