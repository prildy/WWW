package com.hackaton.activityadvisor.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class AddActivityRequest extends AsyncTask<Void, Void, String> {
	OnActivityRequestCallback mListener;
	private String mWhat;
	private String mWhere;
	private Context mContext;
	private ProgressDialog pdia;
	private String mTitle;
	private String mDesc;

	public AddActivityRequest(Context ctx, OnActivityRequestCallback listener, String what, String where, String title, String desc) {
		mContext = ctx;
		mListener = listener;
		mWhat = what;
		mWhere = where;
		mTitle = title;
		mDesc = desc;
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
				"http://lepalaisrose.com/test/create.php?what="+mWhat+"&where="+mWhere+"&uid=2"+"&title="+mTitle+"&description="+ mDesc);
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
			} else {
				error = "error parsing";
			}

			System.out.println("result: " + result);
			if (!result.endsWith("ok")) {
				error = "error inserting file";
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
		System.out.println("on Post execute error: " + error);
		pdia.dismiss();
		mListener.callback(error);
	}
	
	public interface OnActivityRequestCallback {
		public void callback(String error);
	}
}
