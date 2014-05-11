package com.hackaton.activityadvisor.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.hackaton.activityadvisor.model.Acts;

public class GetActivityRequest extends AsyncTask<Void, Void, String> {
	private static final String TITLE_KEY = "title";
	private static final String DESC_KEY = "description";
	private static final String USER_KEY = "user";
	private static final String ACTIVITY_KEY = "activities";
	OnActivityRequestCallback mListener;
	private ArrayList<Acts> mActivities;
	private String mWhat;
	private String mWhere;
	private Context mContext;
	private ProgressDialog pdia;

	public GetActivityRequest(Context ctx, OnActivityRequestCallback listener, String what, String where) {
		mContext = ctx;
		mListener = listener;
		mActivities = new ArrayList<Acts>();
		mWhat = what;
		mWhere = where;
	}

	protected String getASCIIContentFromEntity(HttpEntity entity)
			throws IllegalStateException, IOException {
		InputStream in = entity.getContent();
		StringBuffer out = new StringBuffer();
		int n = 1;
		while (n > 0) {
			byte[] b = new byte[4096];
			n = in.read(b);
			if (n > 0)
				out.append(new String(b, 0, n));
		}
		return out.toString();
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pdia = new ProgressDialog(mContext);
        pdia.setMessage("Loading...");
        pdia.show(); 
	}

	@Override
	protected String doInBackground(Void... params) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet(
//				"http://lepalaisrose.com/test/search.php?what=1&where=0&uid=1");
				"http://lepalaisrose.com/test/search.php?what="+mWhat+"&where="+mWhere+"&uid=1");
		String error = null;
		String result = "";
		InputStream inputStream = null;
		try {
			HttpResponse response = httpClient.execute(httpGet, localContext);
			HttpEntity entity = response.getEntity();
			// text = getASCIIContentFromEntity(entity);
			// --new
			// receive response as inputStream
			inputStream = response.getEntity().getContent();

			// convert inputstream to string
			if (inputStream != null) {
				result = convertInputStreamToString(inputStream);

				JSONObject json = new JSONObject(result);
				JSONArray activities = json.getJSONArray(ACTIVITY_KEY);
				for (int i = 0; i < activities.length(); i++) {
					JSONObject actNode = activities.getJSONObject(i);
					Acts act = new Acts(actNode.getString(TITLE_KEY),
							actNode.getString(DESC_KEY),
							actNode.getString(USER_KEY));
					mActivities.add(act);
				}
			} else {
				error = "error parsing";
			}

			// till here
		} catch (Exception e) {
			return e.getLocalizedMessage();
		}
		return error;
	}

	private String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

	protected void onPostExecute(String error) {
		//no error
		
		System.out.println("on Post execute");
		if (error == null) {
			mListener.callback(mActivities);
		}
		else {
			System.out.println("error on post execute: " + error);
		}
		pdia.dismiss();
	}

	public interface OnActivityRequestCallback {
		public void callback(ArrayList<Acts> activities);
	}
}
