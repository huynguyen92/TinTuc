package com.solodroid.androidnewsapp.adapter;

import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.solodroid.androidnewsapp.utils.Constant;
import com.solodroid.androidnewsapp.utils.ImageLoader;
import com.solodroid.androidnewsapp.ItemNewsByCategory;
import com.solodroid.androidnewsapp.R;
import com.solodroid.androidnewsapp.utils.Utils;

import java.util.List;

public class Adapter_News_By_Category extends ArrayAdapter<ItemNewsByCategory>{
	
	private ActionBarActivity activity;
	private List<ItemNewsByCategory> itemsnewslist;
	private ItemNewsByCategory objnewslistBean;
	private int row;
	public ImageLoader imageLoader;
	LayoutInflater inflat;
	 
	 public Adapter_News_By_Category(ActionBarActivity act, int resource, List<ItemNewsByCategory> arrayList, int columnWidth) {
			super(act, resource, arrayList);
			this.activity = act;
			this.row = resource;
			this.itemsnewslist = arrayList;
			imageLoader=new ImageLoader(activity);
		    inflat = LayoutInflater.from(act);
			 
		}
	 @Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder holder;
			if (view == null) {
				/*LayoutInflater inflater = (LayoutInflater) activity
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(row, null);*/
				view = inflat.inflate(R.layout.lsv_item_favorite, null);

				holder = new ViewHolder();
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			if ((itemsnewslist == null) || ((position + 1) > itemsnewslist.size()))
				return view;

			objnewslistBean = itemsnewslist.get(position);

			holder.txt_newsheading=(TextView)view.findViewById(R.id.txt_newslistheadingfav);
			holder.txt_newsdate=(TextView)view.findViewById(R.id.txt_newslistdatefav);
		 	holder.txt_source=(TextView)view.findViewById(R.id.txt_source);

			holder.img_news=(ImageView)view.findViewById(R.id.img_newslistfav);
			holder.txt_newsheading.setText(objnewslistBean.getNewsHeading().toString());
		 	holder.txt_source.setText(objnewslistBean.getNewSource().toString());
			holder.txt_newsdate.setText(Utils.convertTime(this.activity, objnewslistBean.getNewsDate().toString()));
			imageLoader.DisplayImage(Constant.SERVER_IMAGE_NEWSLIST_THUMBS+objnewslistBean.getNewsImage().toString(), holder.img_news);
			return view;
		}

		public class ViewHolder {
		 
			public ImageView img_news;
			public  TextView txt_newsheading;
			public  TextView txt_newsdate;
			public  TextView txt_source;
		}
}
