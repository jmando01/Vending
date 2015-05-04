package com.example.vending.vending;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Joubert on 03/05/2015.
 */
public class VendingBaseAdapter  extends BaseAdapter {

    private static ArrayList<VendingItem> vendingItemArrayList;

    private Integer[] imgid = {
            R.drawable.roimage,
            //R.drawable.ic_priva,
            //R.drawable.p2,
            //R.drawable.bb5,
            //R.drawable.bb6,
            //R.drawable.d1
    };

    private LayoutInflater l_Inflater;

    public VendingBaseAdapter(Context context, ArrayList<VendingItem> results) {
        vendingItemArrayList = results;
        l_Inflater = LayoutInflater.from(context);
    }

    public void removeItem(VendingItem item){
        vendingItemArrayList.remove(item);
        notifyDataSetChanged();
    }

    public void addItem(VendingItem item) {
        vendingItemArrayList.add(item);
        notifyDataSetChanged();
    }

    public int getCount() {
        return vendingItemArrayList.size();
    }

    public Object getItem(int position) {
        return vendingItemArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.vending_item_view, null);
            holder = new ViewHolder();
            holder.txt_itemName = (TextView) convertView.findViewById(R.id.itemName);
            holder.txt_itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
            holder.txt_itemCounter = (TextView) convertView.findViewById(R.id.itemCounter);
           holder.itemImage = (ImageView) convertView.findViewById(R.id.itemImage);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txt_itemName.setText((vendingItemArrayList.get(position).getItemName()));
        holder.txt_itemPrice.setText(vendingItemArrayList.get(position).getItemPrice());
        holder.txt_itemCounter.setText(vendingItemArrayList.get(position).getItemCounter());
        holder.itemImage.setImageResource(imgid[vendingItemArrayList.get(position).getItemImage() - 1]);
        //holder.itemImage.setImageBitmap(Bitmap.createScaledBitmap(Connect.db.getPhotoDBByUserName(itemDetailsrrayList.get(position).getName()).getPhoto(), 125, 125, false));

        return convertView;
    }

    static class ViewHolder {
        TextView txt_itemCounter;
        TextView txt_itemName;
        TextView txt_itemPrice;
        ImageView itemImage;
    }

}
