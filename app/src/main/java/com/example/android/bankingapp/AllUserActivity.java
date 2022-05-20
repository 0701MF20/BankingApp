package com.example.android.bankingapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bankingapp.data.BankContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.URI;

import static com.example.android.bankingapp.data.BankContract.BankEntry.CONTENT_URI;

public class AllUserActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, BankAdapter.BankAdapterOnClickHandler, androidx.loader.app.LoaderManager.LoaderCallbacks<> {
   public static int toAccountForTransferTable=0;
           public static  int fromAccountForTransferTable=0;
           public static int transferMoneyFromTransferTable=0;
    private RecyclerView recyclerView;
    private BankAdapter bankAdapter;
    public  Uri intentUri;
    private Cursor mCursor;
    public Bundle bundle;
    public static int type=0;
    public static int to_account_index;
    int trans_money=0;
    private ProgressBar mLoadingIndicator;
    private final String TAG = MainActivity.class.getSimpleName();
    public static final String[] MAIN_FORECAST_PROJECTION = {
            BankContract.BankEntry._Id,
            BankContract.BankEntry.COLUMN_BANK_PEOPLE_NAME,
            BankContract.BankEntry.COLUMN_ACCOUNT_NUMBER,
            BankContract.BankEntry.COLUMN_IFSC_NUMBER,

    };
    private int mPosition = RecyclerView.NO_POSITION;
    public static final int INDEX_PEOPLE_NAME = 1;
    public static final int INDEX_ACCOUNT_NOS = 2;
    public static final int INDEX_IFSC_CODE = 3;

    public static final int INDEX_SNO = 0;
    private static final int ID_BANK_LOADER = 44;
    private static final int ID_SELECT_ACCOUNT = 84;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alluser);
        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllUserActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });


        //To recieve the intents
     //   Intent intent = getIntent();
        //To recieve the data associated with the intent
       // intentUri = intent.getData();
           bundle=getIntent().getExtras();
        if (bundle != null) {
            setTitle(getString(R.string.Choose_Account));
            //i keep loadermanager instance inside this loop so that idf the label is edit the they will show available data otherwise we do not need this

        }



        recyclerView=findViewById(R.id.recylerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setHasFixedSize(true);
    bankAdapter=new BankAdapter(this,this);
   recyclerView.setAdapter(bankAdapter);
      //  showLoading();
        androidx.loader.app.LoaderManager.getInstance(this).initLoader(ID_BANK_LOADER,null,this);
//        LoaderManager.getInstance(this).initLoader(ID_BANK_LOADER,null,this);
//if(intentUri!=null)
//{
//    LoaderManager.getInstance(this).initLoader(ID_SELECT_ACCOUNT, null, this);
//}
    }

    @NonNull
    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
/*
        switch (id) {


                //URI for all rows of weather data in our weather table
                Uri forecastQueryUri = CONTENT_URI;
                // Sort order: Ascending by date
                String sortOrder =  BankContract.BankEntry.COLUMN_ACCOUNT_NUMBER + " ASC";

return new CursorLoader(this,forecastQueryUri,MAIN_FORECAST_PROJECTION,null//BankContract.BankEntry.COLUMN_ACCOUNT_NUMBER,null,sortOrder);
            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }*/
//        String[] projections={
//                BankContract.BankEntry._Id,
//                BankContract.BankEntry.COLUMN_BANK_PEOPLE_NAME,
//                BankContract.BankEntry.COLUMN_ACCOUNT_NUMBER,
//                BankContract.BankEntry.COLUMN_IFSC_NUMBER};
      //  switch (id) {
            //case ID_BANK_LOADER:
                return new CursorLoader(this,BankContract.BankEntry.CONTENT_URI,MAIN_FORECAST_PROJECTION,null,null,null);
           // break;
         //   case ID_SELECT_ACCOUNT:

        //        break;
      //      default:
     //           throw new RuntimeException("Loader Not Implemented: " + id);
   //     }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<D> loader, D data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<D> loader) {

    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        bankAdapter.swapCursor(data);
        if (mPosition == RecyclerView.NO_POSITION)
            mPosition = 0;
        recyclerView.smoothScrollToPosition(mPosition);
        if (data.getCount() != 0)
            showWeatherDataView();
    }

    @Override
    public void onLoaderReset(@NonNull android.content.Loader<Cursor> loader) {
        bankAdapter.swapCursor(null);
    }

    private void showWeatherDataView() {
        /* hide the loading indicator */
     //   mLoadingIndicator.setVisibility(View.INVISIBLE);
        /* weather data is visible */
        recyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the loading indicator visible and hide the weather View
     */
    private void showLoading() {
        /* hide the weather data */
        recyclerView.setVisibility(View.INVISIBLE);
        /* show the loading indicator */
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(int account) {
   //    type=bundle.getInt("type");
        /*&&type==0*/
        if(bundle!=null)
        {

             trans_money=bundle.getInt("transfer_amount");
            transferMoneyFromTransferTable=trans_money;
            Log.e("TransferActivity","transf"+transferMoneyFromTransferTable);

            //TODO:(2)
           // int amount=Integer.parseInt(intentUri.getLastPathSegment());

            //TO ACCOUNT INDEX
            to_account_index=account;
   //         Uri Content_to_uri=ContentUris.withAppendedId(CONTENT_URI,to_account_index);


//            Uri intenturiss=ContentUris.withAppendedId(CONTENT_URI,(long)to_account_index);
//            Cursor pointCursor=getContentResolver().query(intenturiss,null,null,null,null);
//         String name1=pointCursor.getString(AllUserActivity.INDEX_PEOPLE_NAME);
//            Log.e("AllUserActivity","NAME IS::::::::"+name1);
//            if(pointCursor==null)
//            {
//                Toast.makeText(this,"Money is not deducted from Account"+rowsUpdated,Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this,"Money is updated"+rowsUpdated,Toast.LENGTH_SHORT).show();
//            }



////TO ACCOUNT INDEX
//            int to_account_index=account;
//mCursor=getContentResolver().query()
//FROM_ACCOUNT FEATURE
            int from_account=bundle.getInt("account_from");
            int from_account_index=bundle.getInt("Index_Of_From_Account");
            int from_account_balance_total=bundle.getInt("From_account_balance");
            //TODO:mAKE IT MORE FLEXIBLE
            from_account_balance_total=from_account_balance_total-trans_money;
            int from_code_ifsc=bundle.getInt("ifsc_code");
            String from_nos_contact=bundle.getString("from_contact_nos");
     //       int from_nos_contact=bundle.getInt("from_contact_nos");
            String from_id_email=bundle.getString("email_Id_From");
            String from_user_name=bundle.getString("from_name_user");
fromAccountForTransferTable=from_account;

  //          Uri contentUriForUpdated=Uri.withAppendedPath(BankContract.C
//TRANSFER
            Log.e("AllUserActivity","Transfer Money:::"+trans_money);
//FROM ACCOUNT
            Log.e("AllUserActivity","FROM_ACCOUNT_NO"+from_account);
            Log.e("AllUserActivity","FROM_ACCOUNT_INDEX"+from_account_index);
            Log.e("AllUserActivity","FROM_ACCOUNT_CURRENT_BALANCE"+from_account_balance_total);


            Log.e("AllUserActivity","TO_ACCOUNT_INDEX"+to_account_index);

            //FROM TABLE
           ContentValues valuesfrom=new ContentValues();
            valuesfrom.put(BankContract.BankEntry.COLUMN_BANK_PEOPLE_NAME,from_user_name);
            valuesfrom.put(BankContract.BankEntry.COLUMN_ACCOUNT_NUMBER,from_account);
            valuesfrom.put(BankContract.BankEntry.COLUMN_IFSC_NUMBER,from_code_ifsc);
            valuesfrom.put(BankContract.BankEntry.COLUMN_BANK_PEOPLE_EMAIL,from_id_email);
            valuesfrom.put(BankContract.BankEntry.COLUMN_BANK_PEOPLE_MOBILE_NUMBER,from_nos_contact);
            valuesfrom.put(BankContract.BankEntry.COLUMN_TOTAL_BALANCE,from_account_balance_total);


            Uri intenturi=ContentUris.withAppendedId(CONTENT_URI,(long)from_account_index);
            Log.e("UserAllActivity","IntentUri"+intenturi);
            int rowsUpdated=getContentResolver().update(intenturi,valuesfrom,null,null);
            if(rowsUpdated==0)
            {
                Toast.makeText(this,"Money is not deducted from Account"+rowsUpdated,Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"Money is updated"+rowsUpdated,Toast.LENGTH_SHORT).show();
            }
            Log.e("AllUserActivity","where are you");
            CharSequence title=getTitle();
        //testing
            if(title==getString(R.string.Choose_Account))
            {
                Log.e("AllUserActivity","I am fine");

                //       Intent CustomerDetailIntent = new Intent(AllUserActivity.this,DetailActivity.class);
                Uri uri_for_account_sec_clicked= CONTENT_URI.buildUpon().appendPath(Integer.toString(account)).build();
                Log.e("AllUserActivity","to_account::::"+Integer.toString(account));

                Cursor cursor1=getContentResolver().query(uri_for_account_sec_clicked,null,BankContract.BankEntry._Id+"=?",new String[]{String.valueOf(ContentUris.parseId(uri_for_account_sec_clicked))},null);
              cursor1.moveToPosition(0);
              int nameColIndex=cursor1.getColumnIndex(BankContract.BankEntry.COLUMN_BANK_PEOPLE_NAME);
              int emailColIndex=cursor1.getColumnIndex(BankContract.BankEntry.COLUMN_BANK_PEOPLE_EMAIL);
                int mobileColIndex=cursor1.getColumnIndex(BankContract.BankEntry.COLUMN_BANK_PEOPLE_MOBILE_NUMBER);
                int AcColIndex=cursor1.getColumnIndex(BankContract.BankEntry.COLUMN_ACCOUNT_NUMBER);
                int ifscColIndex=cursor1.getColumnIndex(BankContract.BankEntry.COLUMN_IFSC_NUMBER);
                int balColIndex=cursor1.getColumnIndex(BankContract.BankEntry.COLUMN_TOTAL_BALANCE);

                String toName=cursor1.getString(nameColIndex);
                String toEmail=cursor1.getString(emailColIndex);
                String toMobile=cursor1.getString(mobileColIndex);
             //   int toMobile=cursor1.getInt(mobileColIndex);
                int toIfsc=cursor1.getInt(ifscColIndex);
                int toBalance=cursor1.getInt(balColIndex);
                int toAcc=cursor1.getInt(AcColIndex);
                toBalance=toBalance+trans_money;
                toAccountForTransferTable=toAcc;
                Log.e("UserAllActivity","To balance:::::"+toBalance);
                //TO TABLE
                ContentValues valuesTo=new ContentValues();
                valuesTo.put(BankContract.BankEntry.COLUMN_BANK_PEOPLE_NAME,toName);
                valuesTo.put(BankContract.BankEntry.COLUMN_ACCOUNT_NUMBER,toAcc);
                valuesTo.put(BankContract.BankEntry.COLUMN_IFSC_NUMBER,toIfsc);
                valuesTo.put(BankContract.BankEntry.COLUMN_BANK_PEOPLE_EMAIL,toEmail);
                valuesTo.put(BankContract.BankEntry.COLUMN_BANK_PEOPLE_MOBILE_NUMBER,toMobile);
                valuesTo.put(BankContract.BankEntry.COLUMN_TOTAL_BALANCE,toBalance);

              //  Uri intenturi=ContentUris.withAppendedId(CONTENT_URI,(long)from_account_index);
              //  Log.e("UserAllActivity","IntentUri"+intenturi);
                int torowsUpdated=getContentResolver().update(uri_for_account_sec_clicked,valuesTo,null,null);
                if(torowsUpdated==0)
                {
                    Toast.makeText(this,"Money is not updated in receiver account"+torowsUpdated,Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,"Money is updated in receiver account"+torowsUpdated,Toast.LENGTH_SHORT).show();
                }


           //     Log.e("AllUserActivity","POSITION::::"+ cursor1.getPosition());
             //   Log.e("AllUserActivity","POSITION::::"+ name23);


                //   CustomerDetailIntent.setData(uri_for_account_clicked);
               // startActivity(CustomerDetailIntent);
            }
            Log.e("AllUserActivity","TRU::::"+title);

    //        type=1;
      Intent i5=new Intent(AllUserActivity.this,MainActivity.class);
     startActivity(i5);
     // bundle=null;
        }else
        {

//            if(title==getString(R.string.Choose_Account))
//            {
//                Log.e("AllUserActivity","TRUE");
//            }
   //         Log.e("AllUserActivity","Kuch toh gadbad h daya");
            Intent CustomerDetailIntent = new Intent(AllUserActivity.this,DetailActivity.class);
            Uri uri_for_account_clicked= CONTENT_URI.buildUpon().appendPath(Integer.toString(account)).build();
            CustomerDetailIntent.setData(uri_for_account_clicked);
            startActivity(CustomerDetailIntent);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_alluser, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {

            case R.id.action_transfer_menu_item:
                Intent transferMenuIntent=new Intent(AllUserActivity.this,TransferActivity.class);
                startActivity(transferMenuIntent);
            break;
                default:
                    return super.onOptionsItemSelected(item);

        }
        return super.onOptionsItemSelected(item);
    }
}
