package com.solodroid.androidnewsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.solodroid.androidnewsapp.adapter.Adapter_Recent;
import com.solodroid.androidnewsapp.utils.Constant;
import com.solodroid.androidnewsapp.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class News_Recent extends Fragment {

    ListView lst_latest;
    List<ItemLatest> arrayOfLatestnews;
    Adapter_Recent objAdapter;
    AlertDialogManager alert = new AlertDialogManager();

    private ItemLatest objAllBean;
    private int columnWidth;
    JsonUtils util;
    int textlength = 0;
    private SwipeRefreshLayout swipeContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.news_recent, container, false);

        setHasOptionsMenu(true);

        lst_latest = (ListView) v.findViewById(R.id.list_news);

        arrayOfLatestnews = new ArrayList<ItemLatest>();

        //push down to refresh
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                objAdapter.clear();
                if (JsonUtils.isNetworkAvailable(getActivity())) {
                    new MyTask().execute(Constant.LATEST_URL);
                } else {
                    showToast("No Network Connection!!!");
                    alert.showAlertDialog(getActivity(), "Internet Connection Error",
                            "Please connect to working Internet connection", false);
                }
                swipeContainer.setRefreshing(false);

            }


        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);



        util = new JsonUtils(getActivity());

        if (JsonUtils.isNetworkAvailable(getActivity())) {
            new MyTask().execute(Constant.LATEST_URL);
        } else {
            showToast("No Network Connection!!!");
            alert.showAlertDialog(getActivity(), "Internet Connection Error",
                    "Please connect to working Internet connection", false);
        }

        return v;
    }

    private class MyTask extends AsyncTask<String, Void, String> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return JsonUtils.getJSONString(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (null != pDialog && pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (null == result || result.length() == 0) {
                showToast("Server Connection Error");
                // alert.showAlertDialog(getActivity(),
                // "Server Connection Error",
                // "May Server Under Maintaines Or Low Network", false);

            } else {

                try {
                    JSONObject mainJson = new JSONObject(result);
                    JSONArray jsonArray = mainJson
                            .getJSONArray(Constant.CATEGORY_ARRAY_NAME);
                    JSONObject iteam_post =  null, objJson = null, objAds = null;
                    for (int i = 0; i < jsonArray.length(); i = i + 2) {
                        iteam_post = jsonArray.getJSONObject(i);
                        objJson = iteam_post.getJSONObject(Constant.CATEGORY_ITEM_POST);
                        ItemLatest objItem = new ItemLatest();

                        // objItem.setCategoryImage(objJson.getString(Constant.CATEGORY_ITEM_IMAGE));
                        objItem.setCatId(objJson
                                .getString(Constant.CATEGORY_ITEM_CAT_ID));
                        objItem.setNewsImage(objJson
                                .getString(Constant.CATEGORY_ITEM_NEWSIMAGE));
                        objItem.setNewsHeading(objJson
                                .getString(Constant.CATEGORY_ITEM_NEWSHEADING));
                        objItem.setNewsDate(objJson
                                .getString(Constant.CATEGORY_ITEM_NEWSDATE));
                        objItem.setNewSource(objJson
                                .getString(Constant.CATEGORY_ITEM_NEWSSOURCE));

                        if (i + 1 < jsonArray.length()) {
                            iteam_post = jsonArray.getJSONObject(i + 1);
                            objJson = iteam_post.getJSONObject(Constant.CATEGORY_ITEM_POST);

                            // objItem.setCategoryImage(objJson.getString(Constant.CATEGORY_ITEM_IMAGE));
                            objItem.setCatId2(objJson
                                    .getString(Constant.CATEGORY_ITEM_CAT_ID));
                            objItem.setNewsImage2(objJson
                                    .getString(Constant.CATEGORY_ITEM_NEWSIMAGE));
                            objItem.setNewsHeading2(objJson
                                    .getString(Constant.CATEGORY_ITEM_NEWSHEADING));
                            objItem.setNewsDate2(objJson
                                    .getString(Constant.CATEGORY_ITEM_NEWSDATE));
                            objItem.setNewSource2(objJson
                                    .getString(Constant.CATEGORY_ITEM_NEWSSOURCE));
                            objItem.setData_type("news");

                            if (!iteam_post.isNull(Constant.CATEGORY_ITEM_ADS)){
                                objAds = iteam_post.getJSONObject(Constant.CATEGORY_ITEM_ADS);
                                String aid = objAds.getString("aid");
                                String ads_type = objAds.getString("ads_type");
                                String ads_unit_id = objAds.getString("ads_unit_id");
                                String ads_heading = objAds.getString("ads_heading");
                                String ads_image = objAds.getString("ads_img");
                                String ads_target_url = objAds.getString("target_url");
                                String ads_active = objAds.getString("ads_active");
                                Log.d("jsonObjLenght: ","" + objAds);
                                ItemLatest objItem_ads = new ItemLatest(aid, ads_type, ads_unit_id, ads_heading, ads_image, ads_target_url,ads_active);
                                objItem_ads.setData_type("ads");

                                arrayOfLatestnews.add(objItem_ads);//add ads to arrayOfLatestnews
                            }

                        }
                        arrayOfLatestnews.add(objItem);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setAdapterToListview();

                Adapter_Recent myAdapterRecent = (Adapter_Recent) lst_latest.getAdapter();

                myAdapterRecent.setItemLatest(new Adapter_Recent.IItemLatest() {
                    @Override
                    public void getItemLatest(int coln, int position) {
                        // TODO Auto-generated method stub
                        objAllBean = arrayOfLatestnews.get(position);
                        Intent intplay = new Intent(getActivity(),
                                NewsDetailActivity.class);
                        int pos = 0;
                        if (coln == 1) {
                              pos = Integer.parseInt(objAllBean.getCatId());
                        } else {
                              pos = Integer.parseInt(objAllBean.getCatId2());
                        }
                        Log.d("Tinh.nh", "objAllBean.getCatId()  : " + pos);
                            intplay.putExtra("NEWS_ID", pos);
                            startActivity(intplay);
                    }
                });
            }
        }
    }

    public void setAdapterToListview() {
        objAdapter = new Adapter_Recent(getActivity(), R.layout.row_home_news,
                arrayOfLatestnews, columnWidth);
        lst_latest.setAdapter(objAdapter);
    }

    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);

        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView)
                MenuItemCompat.getActionView(menu.findItem(R.id.search));

        final MenuItem searchMenuItem = menu.findItem(R.id.search);

        searchView
                .setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        // TODO Auto-generated method stub
                        if (!hasFocus) {
                            searchMenuItem.collapseActionView();
                            searchView.setQuery("", false);
                        }
                    }
                });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:

                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}