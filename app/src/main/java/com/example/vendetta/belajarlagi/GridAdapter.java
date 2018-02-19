package com.example.vendetta.belajarlagi;

/**
 * Created by vendetta on 19/02/18.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {

    private final Context mContext;
    private final GridFunction[] func;

    public GridAdapter(Context context, GridFunction[] func) {
        this.mContext = context;
        this.func = func;
    }

    @Override
    public int getCount() {
        return func.length;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final GridFunction fun = func[position];

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.grid_view, null);

            final TextView nameTextView = (TextView)convertView.findViewById(R.id.textview_name);
            final TextView authorTextView = (TextView)convertView.findViewById(R.id.textview_author);

            final ViewHolder viewHolder = new ViewHolder(nameTextView, authorTextView);
            convertView.setTag(viewHolder);
        }

        final ViewHolder viewHolder = (ViewHolder)convertView.getTag();

        viewHolder.nameTextView.setText(fun.getName());
        viewHolder.authorTextView.setText(fun.getAuthor());

        return convertView;
    }

    private class ViewHolder {
        private final TextView nameTextView;
        private final TextView authorTextView;

        public ViewHolder(TextView nameTextView, TextView authorTextView) {
            this.nameTextView = nameTextView;
            this.authorTextView = authorTextView;
        }
    }

}