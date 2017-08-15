package com.solodroid.androidnewsapp.adapter;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.VideoController;
import com.solodroid.androidnewsapp.ItemLatest;
import com.solodroid.androidnewsapp.R;
import com.solodroid.androidnewsapp.utils.Constant;
import com.solodroid.androidnewsapp.utils.ImageLoader;
import com.solodroid.androidnewsapp.utils.Utils;

import java.util.List;

public class Adapter_Recent extends ArrayAdapter<ItemLatest> {

    private Activity activity;
    private List<ItemLatest> itemsLatest;
    private ItemLatest objLatestBean;
    private int row;
    public ImageLoader imageLoader;
    public ImageLoader imageLoader2;
    public ImageLoader imageLoader_ads;

    LayoutInflater inflat;
    RelativeLayout mAdView_layout;
    VideoController mVideoController;
    AdView adView;
    int width = Utils.getScreenWidth();
    int height = Utils.getScreenWidth()*9/16;

    public Adapter_Recent(Activity act, int resource, List<ItemLatest> arrayList, int columnWidth) {
        super(act, resource, arrayList);
        this.activity = act;
        this.row = resource;
        this.itemsLatest = arrayList;
        imageLoader = new ImageLoader(activity);
        imageLoader2 = new ImageLoader(activity);
        imageLoader_ads = new ImageLoader(activity);
        inflat = LayoutInflater.from(act);
    }

    @Override
    public void clear() {
        itemsLatest.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<ItemLatest> list) {
        list.addAll(list);
        notifyDataSetChanged();
    }

    private View.OnClickListener mListener;

    public void setRow1OnClickListener(View.OnClickListener listener) {
        mListener = listener;
    }

    private IItemLatest mItemlatest;

    public void setItemLatest(IItemLatest itemlatest) {
        mItemlatest = itemlatest;
    }

    static public interface IItemLatest {
        public void getItemLatest(int coln, int position);
    }

    private View mView;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        ViewHolder holder;


        objLatestBean = itemsLatest.get(position);

        if (objLatestBean.getData_type() == "news") {
            if (view == null) {
                view = inflat.inflate(R.layout.row_home_news, null);
                //LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //view = inflater.inflate(row, null);

                holder = new ViewHolder();
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }


            RelativeLayout item1Layout = (RelativeLayout) view.findViewById(R.id.item_news1);
            item1Layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mItemlatest != null) {
                        mItemlatest.getItemLatest(1, position);
                    }
                }
            });

            RelativeLayout item2Layout = (RelativeLayout) view.findViewById(R.id.item_news2);
            item2Layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemlatest != null) {
                        mItemlatest.getItemLatest(2, position);
                    }
                }
            });

            if ((itemsLatest == null) || ((position + 1) > itemsLatest.size()))
                return view;

            holder.txt_newsheadinglatest = (TextView) view.findViewById(R.id.txt_heading_title);
            holder.txt_newsdatelatest = (TextView) view.findViewById(R.id.txt_time_update);
            holder.txt_newssource = (TextView) view.findViewById(R.id.txt_src_news);
            holder.img_newslatest = (ImageView) view.findViewById(R.id.img_newslistlatest);

            holder.txt_newsheadinglatest.setText(objLatestBean.getNewsHeading().toString());
            holder.txt_newsdatelatest.setText(Utils.convertTime(this.activity , objLatestBean.getNewsDate().toString()));
            holder.txt_newssource.setText(objLatestBean.getNewSource().toString());

            imageLoader.DisplayImage(Constant.SERVER_IMAGE_NEWSLIST_THUMBS + objLatestBean.getNewsImage().toString(), holder.img_newslatest);

            holder.txt_newsheadinglatest2 = (TextView) view.findViewById(R.id.txt_heading_title2);
            holder.txt_newsdatelatest2 = (TextView) view.findViewById(R.id.txt_time_update2);
            holder.txt_newssource2 = (TextView) view.findViewById(R.id.txt_src_news2);
            holder.img_newslatest2 = (ImageView) view.findViewById(R.id.img_newslistlatest2);
            //fill data for item 2 in a news_row
            try {
                holder.txt_newsheadinglatest2.setText(objLatestBean.getNewsHeading2().toString());
                holder.txt_newsdatelatest2.setText(Utils.convertTime(this.activity , objLatestBean.getNewsDate2().toString()));
                holder.txt_newssource2.setText(objLatestBean.getNewSource2().toString());
                imageLoader2.DisplayImage(Constant.SERVER_IMAGE_NEWSLIST_THUMBS + objLatestBean.getNewsImage2().toString(), holder.img_newslatest2);

            } catch (NullPointerException e) {
                // do something other
            }
        } else {

            if("admob".equalsIgnoreCase(objLatestBean.getAds_type())) {
                if (view == null) {
                    view = inflat.inflate(R.layout.layout_ads_admob, null);
                    //LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    //view = inflater.inflate(row, null);
                    mAdView_layout = (RelativeLayout) view.findViewById(R.id.adView);

                    adView = new AdView(activity);
                    adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
                    adView.setAdUnitId(objLatestBean.getAds_unit_id());
                    mAdView_layout.addView(adView);
                    AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
                    adView.loadAd(adRequest);

                    holder = new ViewHolder();
                    view.setTag(holder);
                } else {
                    holder = (ViewHolder) view.getTag();
                }

            } else if("video".equalsIgnoreCase(objLatestBean.getAds_type())){
                if (view == null) {
                    view = inflat.inflate(R.layout.layout_ads_video, null);

                    String frameVideo = "<html><body>Video From YouTube<br><iframe width=\""+width+"\" height=\""+height+"\"  src=\""
                                        + objLatestBean.getTarget_url()+"\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

                    WebView displayYoutubeVideo = (WebView) view.findViewById(R.id.ads_video);
                    displayYoutubeVideo.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            return false;
                        }
                    });
                    WebSettings webSettings = displayYoutubeVideo.getSettings();
                    webSettings.setJavaScriptEnabled(true);

                    displayYoutubeVideo.loadData(frameVideo, "text/html", "utf-8");

                    System.out.print("Url_video: " + frameVideo);
                } else {
                    holder = (ViewHolder) view.getTag();
                }

            } else if("normal".equalsIgnoreCase(objLatestBean.getAds_type())){
                if (view == null) {
                    view = inflat.inflate(R.layout.layout_ads_post_native, null);
                    //LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    //view = inflater.inflate(row, null);

                    String url_ads = objLatestBean.getTarget_url();

                    WebView webView_native_ads = (WebView) view.findViewById(R.id.webview_native_ads);
                    webView_native_ads.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            return false;
                        }
                    });

                    webView_native_ads.loadUrl(url_ads);
                    holder = new ViewHolder();
                    view.setTag(holder);
                } else {
                    holder = (ViewHolder) view.getTag();
                }
            }
        }
        return view;
    }

    public class ViewHolder {

        public ImageView img_newslatest;
        public TextView txt_newsheadinglatest;
        public TextView txt_newsdatelatest;
        public TextView txt_newssource;

        public ImageView img_newslatest2;
        public TextView txt_newsheadinglatest2;
        public TextView txt_newsdatelatest2;
        public TextView txt_newssource2;

    }
}
