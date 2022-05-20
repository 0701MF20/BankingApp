package com.example.android.bankingapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.android.bankingapp.data.BankContract;

import static com.example.android.bankingapp.AllUserActivity.to_account_index;
import static com.example.android.bankingapp.AllUserActivity.type;
import static com.example.android.bankingapp.data.BankContract.BankEntry.CONTENT_URI;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
  public static int fromAccountIndex;
    public static final String[] WEATHER_DETAIL_PROJECTION =new String[] {  BankContract.BankEntry._Id,
            BankContract.BankEntry.COLUMN_BANK_PEOPLE_NAME,
            BankContract.BankEntry.COLUMN_ACCOUNT_NUMBER,
    BankContract.BankEntry.COLUMN_IFSC_NUMBER,
    BankContract.BankEntry.COLUMN_BANK_PEOPLE_EMAIL,
    BankContract.BankEntry.COLUMN_BANK_PEOPLE_MOBILE_NUMBER,
    BankContract.BankEntry.COLUMN_TOTAL_BALANCE,
    };

private TextView nameTextView;
    private TextView AccountTextView;
    private TextView ifscTextView;
    private TextView emailTextView;
    private TextView mobileNoTextView;
    private TextView totalBalanceTextView;
    private Button transferButton;
    private Uri mUri;
    private static final int ID_DETAIL_LOADER = 353;
    //Money to transfer
    public static int transferAmount=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
        nameTextView=findViewById(R.id.nametextView);
        ifscTextView=findViewById(R.id.IfsctextView3);
        AccountTextView=findViewById(R.id.AccounttextView4);
        emailTextView=findViewById(R.id.emailIdtextView8);
        mobileNoTextView=findViewById(R.id.mobileIdtextView9);
        totalBalanceTextView=findViewById(R.id.balancetextView10);
transferButton=findViewById(R.id.transferbutton2);
        mUri = getIntent().getData();
        Log.e("DetailActivity","Uri "+mUri);
        fromAccountIndex=(int)ContentUris.parseId(mUri);
        Log.e("DetailActivity","from index "+fromAccountIndex);

        transferButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      /*  DialogInterface.OnClickListener discardOnClickListener=new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //for just simily finish it or not save the et details
                finish();
            }
        };
        enterMoneyForTransfer(discardOnClickListener);*/
        //Method 1

        AlertDialog.Builder builder2 =new AlertDialog.Builder(DetailActivity.this);
// Set up the input
        final EditText editTransferMoney = new EditText(DetailActivity.this);
        builder2.setTitle("Enter the Amount");
        builder2.setView(editTransferMoney);
        LinearLayout layoutName=new LinearLayout(DetailActivity.this);
        layoutName.setOrientation(LinearLayout.VERTICAL);
        layoutName.addView(editTransferMoney);
        builder2.setView(layoutName);
        builder2.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder2.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
////there is need something to add here
                transferAmount=Integer.parseInt(editTransferMoney.getText().toString());
               int fromAccount=Integer.parseInt(AccountTextView.getText().toString());
               int from_account_balance=Integer.parseInt(totalBalanceTextView.getText().toString());
               String from_emaiL_id=emailTextView.getText().toString();
               String from_name=nameTextView.getText().toString();
               int contact_nos=Integer.parseInt(mobileNoTextView.getText().toString());
               int ifsc_code=Integer.parseInt(ifscTextView.getText().toString());
                Intent i4=new Intent(DetailActivity.this,AllUserActivity.class);
          Bundle bundle=new Bundle();
          bundle.putInt("transfer_amount",transferAmount);
          bundle.putInt("account_from",fromAccount);
          bundle.putInt("Index_Of_From_Account",fromAccountIndex);
          bundle.putInt("From_account_balance",from_account_balance);
          bundle.putString("email_Id_From",from_emaiL_id);
          bundle.putString("from_name_user",from_name);
          bundle.putInt("ifsc_code",ifsc_code);
          bundle.putInt("from_contact_nos",contact_nos);
       //   bundle.putInt("type",0);


            //    Uri uri_for_transfer_amount= CONTENT_URI.buildUpon().appendPath(Integer.toString(transferAmount)).build();
             //   Uri uri_for_trans_amount_and_account= CONTENT_URI.buildUpon().appendPath(Integer.toString(transferAmount)).appendPath(Integer.toString(fromAccount)).build();
     //           i4.setData(CONTENT_URI.buildUpon().appendPath(Integer.toString(transferAmount)).build());
             i4.putExtras(bundle);
                startActivity(i4);
             //   Log.e("Transfer Amount","transfer Amount is"+transferAmount+"uRIIII:::"+uri_for_transfer_amount);
                Log.e("Transfer Amount","transfer Amount is::::::"+transferAmount+"::::::::From account:::::::"+fromAccount);
            }
        });
        builder2.show();
    }
});
//        mUri = getIntent().getData();
//        Log.e("DetailActivity","Uri "+mUri);
        if (mUri == null)
            throw new NullPointerException("URI for DetailActivity cannot be null");

        LoaderManager.getInstance(this).initLoader(ID_DETAIL_LOADER, null, this);
    }
    private void enterMoneyForTransfer(DialogInterface.OnClickListener dialogInterface)
    {
//Method 1

        AlertDialog.Builder builder2 =new AlertDialog.Builder(this);
// Set up the input
        final EditText editTransferMoney = new EditText(DetailActivity.this);
        builder2.setTitle("Enter the Amount");
        builder2.setView(editTransferMoney);
        LinearLayout layoutName=new LinearLayout(this);
        layoutName.setOrientation(LinearLayout.VERTICAL);
        layoutName.addView(editTransferMoney);
        builder2.setView(layoutName);
        builder2.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder2.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                transferAmount=Integer.parseInt(editTransferMoney.getText().toString());
                Log.e("Transfer Amount","transfer Amount is"+transferAmount);
            }
        });


    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {


//long fromaccountIndex= ContentUris.parseId(mUri);
//fromAccountIndex=(int)fromaccountIndex;

        Log.e("DetailActivity","ID is:"+id+"  muri   "+mUri+"FROM ACCOUNT INDEX::::"+fromAccountIndex);


        switch (id) {

            case ID_DETAIL_LOADER:
                return new CursorLoader(this,
                        mUri,
                        WEATHER_DETAIL_PROJECTION,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader Not Implemented: " +id);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        boolean cursorHasValidData = false;
        if (data != null && data.moveToFirst()) {
            /* if we have valid data, continue on to bind the data to the UI */
            cursorHasValidData = true;
        }

        if (!cursorHasValidData) {
            /*if no data to display, simply return and do nothing */
            return;
        }
        if(data.getCount()<1||data==null)
        {
            return;
        }
        if(data.moveToFirst()) {

            /***************** Name *****************/
            int nameIndex = data.getColumnIndex(BankContract.BankEntry.COLUMN_BANK_PEOPLE_NAME);

            String nameId = data.getString(nameIndex);
            nameTextView.setText(nameId);
            nameTextView.setContentDescription(nameId);
            /*****************Account No *****************/
            int accIndex = data.getColumnIndex(BankContract.BankEntry.COLUMN_ACCOUNT_NUMBER);
            int accountNo = data.getInt(accIndex);
            AccountTextView.setText(accountNo + "");
            AccountTextView.setContentDescription(accountNo + "");
            /*****************IFSC Code *****************/
            int IFSCIndex = data.getColumnIndex(BankContract.BankEntry.COLUMN_IFSC_NUMBER);
            int IfscCode = data.getInt(IFSCIndex);
            ifscTextView.setText(IfscCode + "");
            ifscTextView.setContentDescription(IfscCode + "");
            /*****************Mobile *****************/
            int mobileIndex = data.getColumnIndex(BankContract.BankEntry.COLUMN_BANK_PEOPLE_MOBILE_NUMBER);
            String mobileno = data.getString(mobileIndex);
            mobileNoTextView.setText(mobileno + "");
            mobileNoTextView.setContentDescription(mobileno + "");
            /*****************Email Id *****************/
            int emailIndex = data.getColumnIndex(BankContract.BankEntry.COLUMN_BANK_PEOPLE_EMAIL);
            String emailId = data.getString(emailIndex);
            emailTextView.setText(emailId + "");
            emailTextView.setContentDescription(emailId + "");
            /*****************BALANCE *****************/
            int balanceIndex = data.getColumnIndex(BankContract.BankEntry.COLUMN_TOTAL_BALANCE);
            int balance;
           /* if(type==1)
            {
                type=0;
             balance=data.getInt(balanceIndex)+transferAmount;

                ContentValues valuesfrom=new ContentValues();
                valuesfrom.put(BankContract.BankEntry.COLUMN_BANK_PEOPLE_NAME,nameId);
                valuesfrom.put(BankContract.BankEntry.COLUMN_ACCOUNT_NUMBER,accountNo);
                valuesfrom.put(BankContract.BankEntry.COLUMN_IFSC_NUMBER,IfscCode);
                valuesfrom.put(BankContract.BankEntry.COLUMN_BANK_PEOPLE_EMAIL,emailId);
                valuesfrom.put(BankContract.BankEntry.COLUMN_BANK_PEOPLE_MOBILE_NUMBER,mobileno);
                valuesfrom.put(BankContract.BankEntry.COLUMN_TOTAL_BALANCE,balance);

                Uri intenturi=ContentUris.withAppendedId(CONTENT_URI,(long)to_account_index);
                int rowsUpdated=getContentResolver().update(intenturi,valuesfrom,null,null);
                if(rowsUpdated==0)
                {
                    Toast.makeText(this,"Money is not updated in this Account"+rowsUpdated,Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,"Money is updated in this account"+rowsUpdated,Toast.LENGTH_SHORT).show();
                }


            }*/
        //    else {
                balance = data.getInt(balanceIndex);
      //      }
            totalBalanceTextView.setText(balance + "");
            totalBalanceTextView.setContentDescription(balance + "");





        }
        /* Store the forecast summary String in our forecast summary field to share later */
//        mForecastSummary = String.format("%s - %s - %s/%s",
//                dateText, description, highString, lowString);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        nameTextView.setText("");
        AccountTextView.setText("");
        ifscTextView.setText("");
        emailTextView.setText("");
        mobileNoTextView.setText("");
        totalBalanceTextView.setText("");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_edit, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {

            case R.id.change_detail:
                Intent changeDetailMenuIntent=new Intent(DetailActivity.this,EditorActivity.class);
                Uri CurrentUri= ContentUris.withAppendedId(BankContract.BankEntry.CONTENT_URI,fromAccountIndex);

                changeDetailMenuIntent.setData(CurrentUri);
                startActivity(changeDetailMenuIntent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
