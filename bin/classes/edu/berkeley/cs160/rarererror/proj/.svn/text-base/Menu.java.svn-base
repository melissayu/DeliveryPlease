package edu.berkeley.cs160.rarererror.proj;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Menu extends Activity {
	private ArrayList<MenuItem> menulist;	
	private MenuListAdapter menuAdapter;
	private ListView menulistview;
	private String restName;
	
	private MenuListAdapter orderListAdapter;
	private ListView currOrderListView;
	
	private TextView subtotalTextView;
	private TextView taxTextView;
	private TextView totalTextView;
	private OrderObject order;
	
	private Button section1btn;
	private Button section2btn;
	private Button section3btn;
	private Button section4btn;
	private Button requestOrderBtn;
	private Button testEnterLocBtn;
	
	private ImageView homeIcon;
	private AlertDialog.Builder builder;
	private AlertDialog alert;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Bundle b = this.getIntent().getExtras();
        restName = b.getString("name");
        order = new OrderObject(restName);
        
        subtotalTextView = (TextView) findViewById(R.id.menu_subtotal_text);
        taxTextView = (TextView) findViewById(R.id.menu_tax_text);
        totalTextView = (TextView) findViewById(R.id.menu_total_text);
        
                
        requestOrderBtn = (Button) findViewById(R.id.menuOrderButton);
        homeIcon = (ImageView) findViewById(R.id.menuHomeIcon);
        section1btn = (Button) findViewById(R.id.menuTabButton1);
        section2btn = (Button) findViewById(R.id.menuTabButton2);
        section3btn = (Button) findViewById(R.id.menuTabButton3);
        section4btn = (Button) findViewById(R.id.menuTabButton4);
        menulistview = (ListView) findViewById(R.id.menuList);
        menulist = new ArrayList<MenuItem>();
        menuAdapter = new MenuListAdapter(this, R.layout.menulistitem, menulist);
        menulistview.setAdapter(menuAdapter);
        showList1();
        
        currOrderListView = (ListView) findViewById(R.id.menuOrderList);
        orderListAdapter = new MenuListAdapter(this, R.layout.currentorderlistitem, order.orderList);
        currOrderListView.setAdapter(orderListAdapter);
        
		builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to cancel this \norder and go back to the main menu?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        		Intent i = new Intent(Menu.this, DeliveryPlease.class);
		        		startActivity(i);

		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		alert = builder.create();

        homeIcon.setOnClickListener(new OnClickListener(){
        	public void onClick (View v){
        		alert.show();
        	}
        });
        section1btn.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		showList1();
        		menuAdapter.notifyDataSetChanged();
        	}
        });
        section2btn.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		showList2();
        		menuAdapter.notifyDataSetChanged();
        	}
        });
        section3btn.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		showList3();
        		menuAdapter.notifyDataSetChanged();
        	}
        });
        section4btn.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		showList4();
        		menuAdapter.notifyDataSetChanged();
        	}
        });
        requestOrderBtn.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){

        		
        		WebService webService = new WebService("http://deliveryplease.iriscouch.com/orderrequests/");
		        JSONObject jsonobj = new JSONObject();
		        SharedPreferences settings = getSharedPreferences("UserPreferences", MODE_PRIVATE);
		        
		        try {
		        	jsonobj.put("items", order.itemList());
					jsonobj.put("name", settings.getString("UserName", "username"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		        //Get JSON response from server the "" are where the method name would normally go if needed example
		        // webService.webGet("getMoreAllerts", params);
		        //String response = webService.webPost(jsonobj);
        		
        		Intent i = new Intent(Menu.this, EnterLocation.class);

        		i.putExtra("orderobject", order);
        		startActivity(i);
        	}
        });
        
        menulistview.setOnItemClickListener(new OnItemClickListener(){
        	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
        		MenuItem selectedItem = menulist.get(position).clone();
        		order.add(selectedItem);
        		order.updatePrice();
        		subtotalTextView.setText(order.subtotalPriceText);
        		taxTextView.setText(order.taxPriceText);
        		totalTextView.setText(order.totalPriceText);
        		orderListAdapter.notifyDataSetChanged();

                if(order.orderList.size()==0){
                    requestOrderBtn.setEnabled(false);
                }else{
                	requestOrderBtn.setEnabled(true);
                }
        	}
        });

        currOrderListView.setOnItemClickListener(new OnItemClickListener(){
        	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
        		MenuItem selectedItem = order.orderList.get(position);
        		order.remove(selectedItem);
        		order.updatePrice();
        		subtotalTextView.setText(order.subtotalPriceText);
        		taxTextView.setText(order.taxPriceText);
        		totalTextView.setText(order.totalPriceText);

        		orderListAdapter.notifyDataSetChanged();

                if(order.orderList.size()==0){
                    requestOrderBtn.setEnabled(false);
                }else{
                	requestOrderBtn.setEnabled(true);
                }

        	}
        });

	}
	
	public void showList1(){
		menulist.clear();
		MenuItem app1 = new MenuItem("Chicken Satay", 5.50, 0);
		MenuItem app2 = new MenuItem("Spring Roll", 2.40, 0);
		MenuItem app3 = new MenuItem("Prawn Roll", 3.40, 0);
		menulist.add(app1);
		menulist.add(app2);
		menulist.add(app3);
	}
	public void showList2(){
		menulist.clear();
		MenuItem cur1 = new MenuItem("Green Curry", 7.85, 0);
		MenuItem cur2 = new MenuItem("Red Curry", 7.85, 0);
		MenuItem cur3 = new MenuItem("Yellow Curry", 7.85, 0);
		MenuItem cur4 = new MenuItem("Seafood Curry", 9.75, 0);
		MenuItem cur5 = new MenuItem("Panang Curry", 7.85, 0);
		menulist.add(cur1);
		menulist.add(cur2);
		menulist.add(cur3);
		menulist.add(cur4);
		menulist.add(cur5);
	}
	public void showList3(){
		menulist.clear();
		MenuItem spe1 = new MenuItem("Pineapple Fried Rice", 8.50, 1);
		MenuItem spe2 = new MenuItem("Pad Thai", 7.50, 1);
		MenuItem spe3 = new MenuItem("Pad See Ew", 7.50, 1);
		MenuItem spe4 = new MenuItem("Chicken Basil", 6.95, 1);
		menulist.add(spe1);
		menulist.add(spe2);
		menulist.add(spe3);
		menulist.add(spe4);
	}
	public void showList4(){
		menulist.clear();
		MenuItem bev1 = new MenuItem("Iced Tea", 2.50, 1);
		MenuItem bev2 = new MenuItem("Coke", 1.75, 1);
		MenuItem bev3 = new MenuItem("Sprite", 1.75, 1);
		menulist.add(bev1);
		menulist.add(bev2);
		menulist.add(bev3);
	}
	
}
