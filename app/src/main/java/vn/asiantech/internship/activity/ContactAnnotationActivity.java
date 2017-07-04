package vn.asiantech.internship.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapter.ContactAdapter;
import vn.asiantech.internship.apis.ContactApi;
import vn.asiantech.internship.models.Contact;

/**
 * Created by ducle on 04/07/2017.
 * contain a RecyclerView to show list contact
 */
@EActivity(R.layout.activity_contact)
public class ContactAnnotationActivity extends AppCompatActivity {
    private static final String TAG = "tag111";
    @ViewById(R.id.recyclerViewContact)
    RecyclerView mRecyclerViewContact;
    private ProgressDialog mProgressDialog;
    private static final String URL = ContactApi.URL;
    private List<Contact> mContacts;

    @AfterViews
    void init() {
        mContacts = new ArrayList<>();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait ... ");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        setUpdate();
    }

    @Background
    void setUpdate() {
        getContacts();
        setList();
    }

    @UiThread
    void setList() {
        closeProgressDialog();
        setRecyclerView();
    }

    private void setRecyclerView() {
        ContactAdapter mContactAdapter = new ContactAdapter(mContacts);
        mRecyclerViewContact.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewContact.setAdapter(mContactAdapter);
    }

    private void closeProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void getContacts() {
        HttpHandler httpHandler = new HttpHandler();
        String json = httpHandler.makeServiceCall(URL);
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonContact = jsonArray.getJSONObject(i);
                    String name = jsonContact.getString("name");
                    String email = jsonContact.getString("email");
                    JSONObject jsonNumber = jsonContact.getJSONObject("phone");
                    String number = jsonNumber.getString("mobile");
                    Contact contact = new Contact(name, email, number);
                    mContacts.add(contact);
                }
            } catch (JSONException e) {
                Log.d(TAG, "JSONException: " + e.getMessage());
            }
        }
    }
}

