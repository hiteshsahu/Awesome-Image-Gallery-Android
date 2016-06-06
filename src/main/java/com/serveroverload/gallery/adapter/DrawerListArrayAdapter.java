package com.serveroverload.gallery.adapter;

import java.util.ArrayList;

import com.example.test.R;
import com.serveroverload.gallery.ui.customeview.TextAwesome;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class DrawerListArrayAdapter.
 */
public class DrawerListArrayAdapter extends ArrayAdapter<String> {

	/** The context. */
	private final Context context;

	/** The values. */
	private final String[] values;

	/** The default tone. */
	private int defaultTone = Color.parseColor("#bf360c");

	/** The tone. */
	private float tone = 1.0f;

	/** The tones. */
	private ArrayList<Integer> tones = new ArrayList<Integer>();
	private ArrayList<Integer> icons = new ArrayList<Integer>();

	private ViewHolder holder;

	private void getTone() {

		tones.add(Color.parseColor("#757575"));
		tones.add(Color.parseColor("#616161"));

		tones.add(Color.parseColor("#757575"));
		tones.add(Color.parseColor("#616161"));

		tones.add(Color.parseColor("#757575"));
		tones.add(Color.parseColor("#616161"));

		tones.add(Color.parseColor("#757575"));

	}

	private void getIcons() {

		icons.add(R.string.fa_folder_open);
		icons.add(R.string.fa_paint_brush);
		icons.add(R.string.fa_magic);
		icons.add(R.string.fa_magnet);
		icons.add(R.string.fa_bookmark);
		icons.add(R.string.fa_gears);
		icons.add(R.string.fa_clipboard);

	}

	/**
	 * Instantiates a new drawer list array adapter.
	 *
	 * @param context
	 *            the context
	 * @param values
	 *            the values
	 */
	public DrawerListArrayAdapter(Context context, String[] values) {
		super(context, -1, values);
		this.context = context;
		this.values = values;

		getTone();
		getIcons();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View rowView = convertView;
		if (convertView == null) {
			LayoutInflater inflator = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflator
					.inflate(R.layout.drawer_list_item, parent, false);
			holder = new ViewHolder();
			holder.title = ((TextView) rowView.findViewById(android.R.id.title));
			holder.icon = (TextAwesome) rowView.findViewById(R.id.icon);

			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}

		rowView.setBackgroundColor(tones.get(position));
		holder.title.setText(values[position]);
		holder.icon.setText(icons.get(position));

		return rowView;
	}

	class ViewHolder {
		TextView title;
		TextAwesome icon;
	}
}
