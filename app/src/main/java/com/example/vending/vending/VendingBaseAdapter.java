package com.example.vending.vending;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Joubert on 06/05/2015.
 */
public class VendingBaseAdapter  extends BaseAdapter {

    private static ArrayList<Vending> vendingArrayList;

    private LayoutInflater l_Inflater;

    public VendingBaseAdapter(Context context, ArrayList<Vending> results) {
        vendingArrayList = results;
        l_Inflater = LayoutInflater.from(context);
    }

    public void removeItem(Vending vending){
        vendingArrayList.remove(vending);
        notifyDataSetChanged();
    }

    public void addItem(Vending vending) {
        vendingArrayList.add(vending);
        notifyDataSetChanged();
    }

    public int getCount() {
        return vendingArrayList.size();
    }

    public Object getItem(int position) {
        return vendingArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.vending_view, null);
            holder = new ViewHolder();
            holder.txt_vendingName = (TextView) convertView.findViewById(R.id.vendingName);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txt_vendingName.setText((vendingArrayList.get(position).getVendingName()));

        return convertView;
    }

    static class ViewHolder {
        TextView txt_vendingName;
    }

}

