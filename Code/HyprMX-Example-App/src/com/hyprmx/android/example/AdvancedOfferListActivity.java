package com.hyprmx.android.example;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyprmx.android.sdk.HyprMXHelper;
import com.hyprmx.android.sdk.api.data.Offer;
import com.hyprmx.android.sdk.api.data.OffersAvailableResponse;
import com.hyprmx.android.sdk.utility.OfferHolder.OnOffersAvailableResponseReceivedListener;

public class AdvancedOfferListActivity extends Activity {

	private static final String OFFERS = "offers";
	private static final String TAG = "AdvancedOfferListActivity";
	private ArrayList<Offer> _offers;
	private boolean _requestingOffers;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advanced_offer_list);
		Button button = (Button)findViewById(R.id.request_offers);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				_requestOffers();
			}
		});

		if(savedInstanceState != null && savedInstanceState.containsKey(OFFERS)) {
			_offers = (ArrayList<Offer>)savedInstanceState.getSerializable(OFFERS);
			if (_offers != null) {
				_offersReceived(_offers);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
	}

	protected void _requestOffers() {
		if (false == _requestingOffers) {
			_requestingOffers = true;
			HyprMXHelper.getInstance().getOffers(new OnOffersAvailableResponseReceivedListener() {
				
				@Override
				public void onOffersAvailable(OffersAvailableResponse arg0) {
					_requestingOffers = false;
					_offersReceived(arg0.getOffersAvailable());
				}

				@Override
				public void onNoOffersAvailable(OffersAvailableResponse arg0) {
					_requestingOffers = false;
					Toast toast = Toast.makeText(AdvancedOfferListActivity.this, "No Offers", Toast.LENGTH_LONG);
					toast.show();
				}

				@Override
				public void onError(int arg0) {
					_requestingOffers = false;
					Toast toast = Toast.makeText(AdvancedOfferListActivity.this, "Error", Toast.LENGTH_LONG);
					toast.show();
				}
			});
		} else {
			Toast toast = Toast.makeText(this, "Already requesting offers", Toast.LENGTH_LONG);
			toast.show();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(OFFERS, _offers);
	}

	private void _offersReceived(List<Offer> offers) {
		_offers = (ArrayList<Offer>)offers;
		Log.v(TAG, "Offers Received: " + offers);
		ListView list = (ListView)findViewById(R.id.offer_list);
		Log.v(TAG, "List: " + list);
		list.setAdapter(new OfferArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, _offers));
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Offer offer = (Offer)arg0.getAdapter().getItem(position);
				HyprMXHelper.getInstance().displayOffer(AdvancedOfferListActivity.this, offer);
			}
		});

		Button displayOffersButton = (Button)findViewById(R.id.display_offer_list);
		displayOffersButton.setEnabled(true);
		displayOffersButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				_displayOffers();
			}
		});

	}

	protected void _displayOffers() {
		HyprMXHelper.getInstance().displayOfferList(this, _offers);
	}

	private static class OfferArrayAdapter extends ArrayAdapter<Offer> {
		private int _layoutId;
		private int _textViewId;

		@SuppressWarnings("SameParameterValue")
        public OfferArrayAdapter(Context context, int resource, int textViewResourceId, List<Offer> objects) {
			super(context, resource, textViewResourceId, objects);
			_layoutId = resource;
			_textViewId = textViewResourceId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
		 * android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if(view == null) {
				view = LayoutInflater.from(getContext()).inflate(_layoutId, parent, false);
			}
			TextView textView = (TextView)view.findViewById(_textViewId);
			textView.setText(getItem(position).getTitle());
			return view;
		}

	}

}
