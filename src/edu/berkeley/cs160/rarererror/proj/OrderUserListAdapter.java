package edu.berkeley.cs160.rarererror.proj;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class OrderUserListAdapter extends ArrayAdapter<UserObject>{
	private ArrayList<UserObject> userlist;
	private int textViewResource;
	
	public OrderUserListAdapter(Context context, int textViewResourceId, ArrayList<UserObject> list) {
        super(context, textViewResourceId, list);
        this.userlist = list;
        textViewResource = textViewResourceId;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.orderuserlistitem, null);
            }
            UserObject item = userlist.get(position);
            if (item != null) {
            		
                    TextView username = (TextView) v.findViewById(R.id.Username);
                    RatingBar ratingbar = (RatingBar) v.findViewById(R.id.ratingbar);
                    TextView timesrated = (TextView) v.findViewById(R.id.TimesRated);
                    TextView fee = (TextView) v.findViewById(R.id.Fee);
                    TextView ETA = (TextView) v.findViewById(R.id.ETA);

                   	username.setText(item.getUsername());
                   	ratingbar.setIsIndicator(true);
                   	ratingbar.setRating((float) item.getRating());
                   	timesrated.setText(Integer.toString(item.getNumRatings()));
            }
            
            return v;
    }
}