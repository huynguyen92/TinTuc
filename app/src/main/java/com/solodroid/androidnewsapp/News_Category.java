package com.solodroid.androidnewsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.solodroid.androidnewsapp.adapter.Adapter_Category;
import com.solodroid.androidnewsapp.utils.Constant;
import com.solodroid.androidnewsapp.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class News_Category extends Fragment {

    GridView lsv_allnews;
    List<ItemAllNews> arrayOfAllnews;
    Adapter_Category objAdapter;
    AlertDialogManager alert = new AlertDialogManager();
    private ItemAllNews objAllBean;

    int textlength = 0;
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.news_category, container,false);

        lsv_allnews=(GridView)v.findViewById(R.id.gridView1);

        arrayOfAllnews=new ArrayList<ItemAllNews>();

        setHasOptionsMenu(true);



        lsv_allnews.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                objAllBean=arrayOfAllnews.get(position);
                int Catid=objAllBean.getCategoryId();
                Constant.CATEGORY_IDD=objAllBean.getCategoryId();
                Log.e("cat_id", "" + Catid);
                Constant.CATEGORY_TITLE=objAllBean.getCategoryName();

                Intent intcat=new Intent(getActivity(),NewsListByCategoryActivity.class);
                intcat.putExtra("cat_id", ""+objAllBean.getCategoryId());
                startActivity(intcat);
            }
        });


        if (JsonUtils.isNetworkAvailable(getActivity())) {
            new MyTask().execute(Constant.CATEGORY_URL);
        } else {
            showToast("No Network Connection!!!");
            alert.showAlertDialog(getActivity(), "Internet Connection Error",
                    "Please connect to working Internet connection", false);
        }

        return v;
    }

    private	class MyTask extends AsyncTask<String, Void, String> {

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
                showToast("No data found from web!!!");

            } else {

                try {
                    JSONObject mainJson = new JSONObject(result);
                    JSONArray jsonArray = mainJson.getJSONArray(Constant.CATEGORY_ARRAY_NAME);
                    JSONObject objJson = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);

                        ItemAllNews objItem = new ItemAllNews();
                        objItem.setCategoryName(objJson.getString(Constant.CATEGORY_NAME));
                        objItem.setCategoryId(objJson.getInt(Constant.CATEGORY_CID));
                        objItem.setCategoryImageurl(objJson.getString(Constant.CATEGORY_IMAGE));
                        arrayOfAllnews.add(objItem);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setAdapterToListview();
            }

        }
    }

    public void setAdapterToListview() {
        objAdapter = new Adapter_Category(getActivity(), R.layout.lsv_item_category,
                arrayOfAllnews);
        lsv_allnews.setAdapter(objAdapter);
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