package com.cxht.app;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;


public class BaseFragmentActivity extends FragmentActivity {
    public ActionBar actionBar;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        super.onCreate(savedInstanceState);

        /* ÏÔÊ¾App icon×ó²àµÄback¼ü */
        actionBar = getActionBar( );

        // actionBar.setBackgroundDrawable(arg0)
        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId( )) {
            case android.R.id.home:
                this.finish( );
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
