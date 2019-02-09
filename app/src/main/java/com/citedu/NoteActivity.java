package com.citedu;

import android.content.DialogInterface;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import android.widget.ShareActionProvider;
import android.widget.Toast;




public class NoteActivity extends AppCompatActivity {


    private EditText mEtTitle;
    private EditText mEtContent;

    private String mNoteFileName;
    private  Note mLoadedNote;

    private EditText editTitle;
    private EditText editContent;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mEtTitle = (EditText) findViewById(R.id.note_et_title);
        mEtContent = (EditText) findViewById(R.id.note_et_content);


        mNoteFileName = getIntent().getStringExtra("NOTE_FILE");

        if (mNoteFileName != null && !mNoteFileName.isEmpty()) {
            mLoadedNote = Utilities.getNoteByName(this, mNoteFileName);

            if(mLoadedNote != null){
                mEtTitle.setText(mLoadedNote.getTitle());
                mEtContent.setText(mLoadedNote.getContent());

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_new, menu);
        return super.onCreateOptionsMenu(menu);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_note_save:
                saveNote();
                break;

            case R.id.action_note_delete:
                deleteNote();
                break;

            case R.id.action_note_share:

                if(mEtTitle.getText().toString().trim().isEmpty() || mEtContent.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(this, "please enter a title and content", Toast.LENGTH_SHORT).show();
                }else {

                    Intent i = new Intent(android.content.Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, mEtTitle.getText().toString() + ".txt");
                    i.putExtra(Intent.EXTRA_TEXT, mEtTitle.getText().toString());
                    i.putExtra(Intent.EXTRA_TEXT, mEtContent.getText().toString());

                    startActivity(Intent.createChooser(i, "share via"));
                    Toast.makeText(getApplicationContext(), "sharing your note", Toast.LENGTH_SHORT).show();
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    private void saveNote() {
        Note note;

        if(mEtTitle.getText().toString().trim().isEmpty() || mEtContent.getText().toString().trim().isEmpty())
        {
            Toast.makeText(this, "please enter a title and content", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mLoadedNote == null) {

            note = new Note(System.currentTimeMillis(), mEtTitle.getText().toString()
                    , mEtContent.getText().toString());
        } else {
            note = new Note(mLoadedNote.getDateTime(), mEtTitle.getText().toString()
                    , mEtContent.getText().toString());
        }

        if (Utilities.saveNote(this, note)) {
            Toast.makeText(this, "your note is saved", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "note cannot be saved", Toast.LENGTH_SHORT).show();
        }
        finish();

    }

    private void deleteNote() {
        if(mLoadedNote == null) {
            finish();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("delete")
                    .setMessage("you are about to delete " + mEtTitle.getText().toString() + ", are you sure?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Utilities.deleteNote(getApplicationContext()
                                    , mLoadedNote.getDateTime() + Utilities.FILE_EXTENSION);
                            Toast.makeText(getApplicationContext()
                                    , mEtTitle.getText().toString() + " is deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("no", null)
                    .setCancelable(false);

            dialog.show();


        }


    }
}
