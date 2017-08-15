package com.solodroid.androidnewsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.solodroid.androidnewsapp.adapter.Adapter_News_By_Category;
import com.solodroid.androidnewsapp.utils.Constant;
import com.solodroid.androidnewsapp.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsListByCategoryActivity extends ActionBarActivity {

	ListView lsv_cat;
	List<ItemNewsByCategory> arrayOfNewsList;
	Adapter_News_By_Category objAdapter;
	AlertDialogManager alert = new AlertDialogManager();
	private ItemNewsByCategory objAllBean;
	private int columnWidth;
	JsonUtils util;
	int textlength = 0;
    Toolbar toolbar;

    private ProgressBar progressdialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_list_all);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Constant.CATEGORY_TITLE);
		
		lsv_cat=(ListView)findViewById(R.id.lsv_cat_item);

        progressdialog = (ProgressBar) findViewById(R.id.progressBar);

		arrayOfNewsList=new ArrayList<ItemNewsByCategory>();

		Intent i = getIntent();
		String news_id = i.getStringExtra("cat_id");

		util=new JsonUtils(getApplicationContext());

		if (JsonUtils.isNetworkAvailable(NewsListByCategoryActivity.this)) {
			new MyTask().execute(Constant.CATEGORY_ITEM_URL + news_id);
		} else {
			showToast("No Network Connection!!!");
			alert.showAlertDialog(NewsListByCategoryActivity.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);
		}

		lsv_cat.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub

				objAllBean=arrayOfNewsList.get(position);
				int pos=Integer.parseInt(objAllBean.getCatId());

				Intent intplay=new Intent(getApplicationContext(),NewsDetailActivity.class);
				intplay.putExtra("NEWS_ID", pos);
				startActivity(intplay);
			}
		});
	}

	private	class MyTask extends AsyncTask<String, Void, String> {

//		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
            progressdialog.setVisibility(View.VISIBLE);
//			pDialog = new ProgressDialog(News_All_List.this);
//			pDialog.setMessage("Loading...");
//			pDialog.setCancelable(false);
//			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			return JsonUtils.getJSONString(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

            if (null != progressdialog && progressdialog.isShown()) {
                progressdialog.setVisibility(View.INVISIBLE);
            }

			if (null == result || result.length() == 0) {
				showToast("Server Connection Error");
				alert.showAlertDialog(NewsListByCategoryActivity.this, "Server Connection Error",
						"May Server Under Maintaines Or Low Network", false);
			} else {
				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray("NewsByCat");
					JSONObject iteam_post =  null, objJson = null, objAds = null;
                    Log.d("arrJson:", jsonArray.toString());
					for (int i = 0; i < jsonArray.length(); i++) {

						iteam_post = jsonArray.getJSONObject(i);
						objJson = iteam_post.getJSONObject(Constant.CATEGORY_ITEM_POST);
						Log.d("objJson:", iteam_post.toString());
						ItemNewsByCategory objItem = new ItemNewsByCategory();

						objItem.setCId(objJson.getString(Constant.CATEGORY_ITEM_CID));
						objItem.setCategoryName(objJson.getString(Constant.CATEGORY_ITEM_NAME));
						objItem.setCategoryImage(objJson.getString(Constant.CATEGORY_ITEM_IMAGE));
						objItem.setCatId(objJson.getString(Constant.CATEGORY_ITEM_CAT_ID));
						objItem.setNewSource(objJson.getString(Constant.CATEGORY_ITEM_NEWSSOURCE));
						objItem.setNewsImage(objJson.getString(Constant.CATEGORY_ITEM_NEWSIMAGE));
						objItem.setNewsHeading(objJson.getString(Constant.CATEGORY_ITEM_NEWSHEADING));
						objItem.setNewsDate(objJson.getString(Constant.CATEGORY_ITEM_NEWSDATE));
                        objItem.setData_type(Constant.CATEGORY_ITEM_POST);

                        if (!iteam_post.isNull(Constant.CATEGORY_ITEM_ADS)){
                            objAds = iteam_post.getJSONObject(Constant.CATEGORY_ITEM_ADS);
                            String aid = objAds.getString("aid");
                            String ads_type = objAds.getString("ads_type");
                            String ads_unit_id = objAds.getString("ads_unit_id");
                            String ads_heading = objAds.getString("ads_heading");
                            String ads_image = objAds.getString("ads_img");
                            String ads_target_url = objAds.getString("target_url");
                            String ads_active = objAds.getString("ads_active");
                            ItemNewsByCategory objItem_ads = new ItemNewsByCategory(aid, ads_type, ads_unit_id, ads_heading, ads_image, ads_target_url,ads_active);
                            objItem_ads.setData_type(Constant.CATEGORY_ITEM_ADS);

                            arrayOfNewsList.add(objItem_ads);//add ads to arrayOfLatestnews
                        }
						arrayOfNewsList.add(objItem);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				setAdapterToListview();
			}
		}
	}

	public void setAdapterToListview() {
		objAdapter = new Adapter_News_By_Category(NewsListByCategoryActivity.this, R.layout.lsv_item_favorite,
				arrayOfNewsList,columnWidth);
		lsv_cat.setAdapter(objAdapter);
	}

	public void showToast(String msg) {
		Toast.makeText(NewsListByCategoryActivity.this, msg, Toast.LENGTH_LONG).show();
	}


	@Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // TODO Auto-generated method stub

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_search, menu);


        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView)
                MenuItemCompat.getActionView(menu.findItem(R.id.search));

        final MenuItem searchMenuItem = menu.findItem(R.id.search);

		searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus) {
					searchMenuItem.collapseActionView();
					searchView.setQuery("", false);
				}
			}
		});

		searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextChange(String newText) {

				textlength=newText.length();
				arrayOfNewsList.clear();
				setAdapterToListview();
				return false;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				// Do something
				return true;
			}
		});

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {

		case android.R.id.home:
			onBackPressed();
			return true;

		case R.id.refresh:
        	finish();
        	startActivity(getIntent());
        	overridePendingTransition(R.anim.open_next, R.anim.close_next);

    		return true;

		case R.id.menu_favorite:
			startActivity(new Intent(getApplicationContext(), NewsFavoriteActivity.class));
			return true;

		case R.id.menu_about:
			Intent about = new Intent(getApplicationContext(), AboutUsActivity.class);
			startActivity(about);
			return true;

		case R.id.menu_moreapp:
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse(getString(R.string.play_more_apps))));
			return true;

		case R.id.menu_rateapp:
			final String appName = getPackageName();
			try {
				startActivity(new Intent(Intent.ACTION_VIEW,
						Uri.parse("market://details?id=" + appName)));
			} catch (android.content.ActivityNotFoundException anfe) {
				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("http://play.google.com/store/apps/details?id="
								+ appName)));
			}
			return true;

		default:
			return super.onOptionsItemSelected(menuItem);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
