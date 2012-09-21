package com.shivgadhia.android.tomato.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.shivgadhia.android.tomato.R;
import com.shivgadhia.android.tomato.fragments.GridFragment;

public class TomatoActivity extends Activity {
	private GridFragment mGridFragment;
	
	private OnQueryTextListener queryListener = new OnQueryTextListener() {
		
		@Override
		public boolean onQueryTextSubmit(String query) {
			//Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
			mGridFragment.searchWithCriteria(query);
			return false;
		}
		
		@Override
		public boolean onQueryTextChange(String newText) {
			// TODO Auto-generated method stub
			return false;
		}
	};

	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        FragmentManager fm = getFragmentManager();
        
        mGridFragment = new GridFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_content, mGridFragment);
        ft.commit();
 
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar_home, menu);
		SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
		searchView.setOnQueryTextListener(queryListener );
		searchView.setIconifiedByDefault(false);
		return super.onCreateOptionsMenu(menu);
	}

}



/* attribution :
“Tomato” symbol used on the app icon is by Alessandro Suraci, from thenounproject.com collection.
*/