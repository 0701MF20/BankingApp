package com.example.android.bankingapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.android.bankingapp.data.BankContract;

import static com.example.android.bankingapp.data.BankContract.BankEntry.CONTENT_URI;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    //THIS IS FOR ID OF THREAD TO BE USED
    public static final int BANK_EDITORIAL=2;
    //Initial condition
    public boolean mDetailHasChanged=false;
    /** fOR COUNT*/
    private static int count=0;
    /** EditText field to enter the Customer's name */
    private EditText mNameEditText;

    /** EditText field to enter the account no's  */
    private EditText mAccountEditText;

    /** EditText field to enter the IFSC's code */
    private EditText mIFSCEditText;

    /** EditText field to enter the mobile No */
    private EditText mMobileEditText;

    /** EditText field to enter the Email Id */
    private EditText mEmailEditText;
    /** EditText field to enter the Customer */
    private EditText mCustomerEditText;

    /** EditText field to enter the pet's gender */
   // private Spinner mGenderSpinner;

    /**
     * Gender of the pet. The possible values are:
     * 0 for unknown gender, 1 for male, 2 for female.
     */
    //private int mGender = 0;
    //for content uri which is recieved with intent
    public Uri IntentContentUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        //To recieve the intents
        Intent intent = getIntent();
        //To recieve the data associated with the intent
        IntentContentUri = intent.getData();

        if (IntentContentUri != null) {
            setTitle(getString(R.string.Edit_Detail));
            //i keep loadermanager instance inside this loop so that idf the label is edit the they will show available data otherwise we do not need this

            LoaderManager.getInstance(this).initLoader(BANK_EDITORIAL, null, this);
        } else {
            setTitle(getString((R.string.Add_DetailUser)));
            //As we are just adding the USER then it does not make sense to delete that
            invalidateOptionsMenu();
        }

        //to start thee loader

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.editTextPersonName);
        mAccountEditText = (EditText) findViewById(R.id.editTextTextAccountNo);
        mIFSCEditText = (EditText) findViewById(R.id.editTextTextIFSCCode);
        mMobileEditText=(EditText)findViewById(R.id.MobileNoseditTextTextPersonName);
        mEmailEditText=(EditText)findViewById(R.id.EmailIdeditTextTextPersonName2);
        mCustomerEditText=(EditText)findViewById(R.id.balanceTextTextPersonName3);
       // mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);

       // setupSpinner();
        //this is a touch listener which is used in order to get to know about the data changed and if it is changed on touch then
        //value is change to true
        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetailHasChanged = true;
                return false;
            }
        };
        //then i am applying the listeners on the different edit text and text spinners to get to set the listeneers on touch that means on change
        mNameEditText.setOnTouchListener(touchListener);
        mAccountEditText.setOnTouchListener(touchListener);
        mMobileEditText.setOnTouchListener(touchListener);
        mEmailEditText.setOnTouchListener(touchListener);
        mCustomerEditText.setOnTouchListener(touchListener);
        mIFSCEditText.setOnTouchListener(touchListener);
//        mGenderSpinner.setOnTouchListener(touchListener);

    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
  /*  private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = PetContract.PetEntry.GENDER_MALE; // Male
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = PetContract.PetEntry.GENDER_FEMALE; // Female
                    } else {
                        mGender = PetContract.PetEntry.GENDER_UNKNOWN; // Unknown
                    }
                }
            }
            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 0; // Unknown
            }
        });
    }
*/
    /**
     * Get user input from editor and save new pet into database.
     */
    private void insertPet() {

        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String name = mNameEditText.getText().toString().trim();
        int AccountNo = Integer.parseInt(mAccountEditText.getText().toString().trim());
       int IFSCNo = Integer.parseInt(mAccountEditText.getText().toString().trim());
       String emailId=mEmailEditText.getText().toString().trim();
       int mobile=Integer.parseInt(mMobileEditText.getText().toString().trim());
       int customerbalance=Integer.parseInt(mCustomerEditText.getText().toString().trim());
        //int weights=0;
        //If empty then i means it will save 0 in database by default
      /*  if(TextUtils.isEmpty(mWeightEditText.getText().toString().trim()))
        {
            weights=0;
        }
        else {
            //here we are extracting the weights
            weights = Integer.parseInt(mWeightEditText.getText().toString().trim());
        }*/
        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        //content value
        ContentValues values2 = new ContentValues();
        values2.put(BankContract.BankEntry.COLUMN_BANK_PEOPLE_NAME, name);
        values2.put(BankContract.BankEntry.COLUMN_IFSC_NUMBER, IFSCNo);
        values2.put(BankContract.BankEntry.COLUMN_ACCOUNT_NUMBER,AccountNo);
        values2.put(BankContract.BankEntry.COLUMN_TOTAL_BALANCE,customerbalance);
        values2.put(BankContract.BankEntry.COLUMN_BANK_PEOPLE_EMAIL,emailId);
        values2.put(BankContract.BankEntry.COLUMN_BANK_PEOPLE_MOBILE_NUMBER,mobile);
        //if intent content is null that means we need to add the pet data
        if (IntentContentUri == null)
        {
            //for inserting content values
            Uri urii = getContentResolver().insert(CONTENT_URI, values2);
            if (urii == null) {
                // If the new content URI is null, then there was an error with insertion.

                Toast.makeText(this, R.string.User_iNSERTION_ERROR, Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, R.string.User_iNSERTION_SUCCESSFUL, Toast.LENGTH_SHORT).show();
            }
        } else {
            //it will gives nos of row that are catually updated.
            int rowUpdated=getContentResolver().update(IntentContentUri,values2,null,null);
            //nothing is updated this means that
            if(rowUpdated==0)
            {
                Toast.makeText(this,"Editor update failed"+rowUpdated,Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"User updated"+rowUpdated,Toast.LENGTH_SHORT).show();
            }
        }
    }
    //For deleting the pet it is actiually the function which we used to delte the pet record and clearly visible
    //that this method is not connected with the Database directly it taking help of content rsolver to access content provider whic will able access the database

    private void petDelete()
    {
        getContentResolver().delete(IntentContentUri,
                null,
                null);
        //After Delete return back to the AllUserActivity with updated records after deletion
        NavUtils.navigateUpFromSameTask(this);
    }
    //Create a customized Delete Alert Dialog Box
    private void showDeleteDialogBox()
    {
        //AlertDialog.Builder create it and assign a title,message,setPositive,setNegative
        AlertDialog.Builder builderForDelete=new AlertDialog.Builder(this);
        builderForDelete.setTitle(R.string.warning_title);
        builderForDelete.setMessage("Do you really want to delete this pet?");
        //For not deleting
        builderForDelete.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog!=null)
                {
                    dialog.dismiss();
                }
            }
        });
        //FOR DELETING
        builderForDelete.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(EditorActivity.this,R.string.Individual_User_Delete,Toast.LENGTH_SHORT).show();
                petDelete();
            }
        });
        //creation of alert dialog box and also show that
        AlertDialog deleteAlertDialog=builderForDelete.create();
        deleteAlertDialog.show();
    }
    //creation of customized dialog box for giving warning if we left after righting(In this touchListener plays important role in it)
    private void showNoHasChangedDialogBox(DialogInterface.OnClickListener discardOnClickListener)
    {
        //AlterDialog.Builder method is actually used to call different method for dialog box for example setMessage,setPOSITIVEBUTTON
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
//for title
        builder.setTitle(R.string.warning_title);
        //this method is for shoeing the message alertDialog
        builder.setMessage("Discard your changes and quit editing?");
        //for +ve message
        builder.setPositiveButton("DISCARD",discardOnClickListener);
        //for negative message
        builder.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog!=null)
                {
                    dialog.dismiss();
                }
            }
        });
        //to create the dialog box
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    //this is for the  top back arrow or backUpHomeEnable bUTTON
    @Override
    public void onBackPressed() {
        //If nothing is changed in edit text and then we press back button then in that case it will return to the parent acitvity that is catalog activity
        if(!mDetailHasChanged) {
            super.onBackPressed();
            return;
        }
        DialogInterface.OnClickListener discardOnClickListener=new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //for just simily finish it or not save the et details
                finish();
            }
        };
        showNoHasChangedDialogBox(discardOnClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);

        return true;
    }
    //just override the prepare option menu to play with menu items
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //mandatory part if i want to want to rmove the menu item then we have to follow this along with the invalidOptionMenu()
        // If this is a new pet, hide the "Delete" menu item.
        if(IntentContentUri==null)
        {
            MenuItem item1=menu.findItem(R.id.action_delete);
            item1.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_saved:
                //solved the bugs
               /* if((TextUtils.isEmpty(mNameEditText.getText().toString()))&&TextUtils.isEmpty(mIFSCEditText.getText().toString())&&TextUtils.isEmpty(mAccountEditText.getText().toString()))
                {
                    Toast.makeText(this,R.string.mandatory,Toast.LENGTH_SHORT).show();
                    Intent i2=new Intent(this,AllUserActivity.class);
                    startActivity(i2);
                }
                else
                {
                    //for inserting row of information
                    insertPet();
                }*/
                insertPet();
                Intent i2=new Intent(this,AllUserActivity.class);
                startActivity(i2);
                //finish or exit to this activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                if(IntentContentUri!=null)
                {
                    showDeleteDialogBox();
                }
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                //<-
                // Navigate back to parent activity (CatalogActivity)
                //NavUtils.navigateUpFromSameTask(this);
                //if nothing has changed in pet details then
                if(!mDetailHasChanged)
                {
                    NavUtils.navigateUpFromSameTask(this);
                    break;
                }
                //this is actually tellinh what to do when something changes .i.e on discard
                DialogInterface.OnClickListener discardOnClickListeners=new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //it will navigate from current acivity t paret of the the activity
                        NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    }
                };
                showNoHasChangedDialogBox(discardOnClickListeners);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//creation of loader just like the catalog activity to have secodany thread for this work
        String[] projections=new String[]{
                BankContract.BankEntry._Id,
                BankContract.BankEntry.COLUMN_BANK_PEOPLE_NAME,
                BankContract.BankEntry.COLUMN_ACCOUNT_NUMBER,
                BankContract.BankEntry.COLUMN_IFSC_NUMBER,
                BankContract.BankEntry.COLUMN_BANK_PEOPLE_EMAIL,
                BankContract.BankEntry.COLUMN_BANK_PEOPLE_MOBILE_NUMBER,
                BankContract.BankEntry.COLUMN_TOTAL_BALANCE
        };

        Log.e("EditorActivity","Intent uri inside insert loader"+IntentContentUri);
        return new CursorLoader(this,
                IntentContentUri,
                projections,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if(cursor.getCount()<1||cursor==null)
        {
            return;
        }
        if(cursor.moveToFirst()) {
            Log.e("EditorActivity","Cursor count:"+cursor.getCount());
            int nameIndex = cursor.getColumnIndex(BankContract.BankEntry.COLUMN_BANK_PEOPLE_NAME);
            int accountIndex = cursor.getColumnIndex(BankContract.BankEntry.COLUMN_ACCOUNT_NUMBER);
            int ifscIndex = cursor.getColumnIndex(BankContract.BankEntry.COLUMN_IFSC_NUMBER);
            int emailIndex=cursor.getColumnIndex(BankContract.BankEntry.COLUMN_BANK_PEOPLE_EMAIL);
            int balanceIndex=cursor.getColumnIndex(BankContract.BankEntry.COLUMN_TOTAL_BALANCE);
            int mobileindex=cursor.getColumnIndex(BankContract.BankEntry.COLUMN_BANK_PEOPLE_MOBILE_NUMBER);
//            int weightPetIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_WEIGHT);
            String nameCustomer = cursor.getString(nameIndex);
      //      String breedPet = cursor.getString(accountIndex);
            int ifscCode = cursor.getInt(ifscIndex);
            int accountCode = cursor.getInt(accountIndex);
            String email=cursor.getString(emailIndex);
            int mobile=cursor.getInt(mobileindex);
            int balance=cursor.getInt(balanceIndex);

            //necessary to use ""+name pet because otherwise it would invoke error because sometime we are actually provingint wvalue and int value need to be converted to string by using string concatenation

            mNameEditText.setText(""+nameCustomer);
            mIFSCEditText.setText(""+ifscCode);
            mAccountEditText.setText(""+accountCode);
            mEmailEditText.setText(email);
            mCustomerEditText.setText(balance+"");
            mMobileEditText.setText(mobile+"");
//            mGenderSpinner.setSelection(genderPet);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mNameEditText.setText("");
        mIFSCEditText.setText("");
        mAccountEditText.setText("");
        mMobileEditText.setText("");
        mEmailEditText.setText("");
        mCustomerEditText.setText("");
      //  mGenderSpinner.setSelection(0);
    }
}
