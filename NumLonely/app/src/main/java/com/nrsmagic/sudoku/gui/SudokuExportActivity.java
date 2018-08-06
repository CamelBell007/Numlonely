package com.nrsmagic.sudoku.gui;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.nrsmagic.sudoku.db.SudokuDatabase;
import com.nrsmagic.sudoku.game.FolderInfo;
import com.nrsmagic.sudoku.gui.exporting.FileExportTask;
import com.nrsmagic.sudoku.gui.exporting.FileExportTask.OnExportFinishedListener;
import com.nrsmagic.sudoku.gui.exporting.FileExportTaskParams;
import com.nrsmagic.sudoku.gui.exporting.FileExportTaskResult;
import java.io.File;
import java.util.Date;

public class SudokuExportActivity
  extends Activity
{
  public static final long ALL_FOLDERS = -1L;
  private static final int DIALOG_FILE_EXISTS = 1;
  private static final int DIALOG_PROGRESS = 2;
  public static final String EXTRA_FOLDER_ID = "FOLDER_ID";
  private static final String TAG = SudokuExportActivity.class.getSimpleName();
  private EditText mDirectoryEdit;
  private FileExportTaskParams mExportParams;
  private FileExportTask mFileExportTask;
  private EditText mFileNameEdit;
  private View.OnClickListener mOnSaveClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      SudokuExportActivity.this.exportToFile();
    }
  };
  private Button mSaveButton;
  
  private void exportToFile()
  {
    if (!new File("/sdcard").exists())
    {
      Toast.makeText(this, 2131296376, 1);
      finish();
    }
    if (new File(this.mDirectoryEdit.getText().toString(), this.mFileNameEdit.getText().toString() + ".opensudoku").exists())
    {
      showDialog(1);
      return;
    }
    startExportToFileTask();
  }
  
  private void startExportToFileTask()
  {
    this.mFileExportTask.setOnExportFinishedListener(new OnExportFinishedListener()
    {
      public void onExportFinished(FileExportTaskResult paramAnonymousFileExportTaskResult)
      {
        SudokuExportActivity.this.dismissDialog(2);
        if (paramAnonymousFileExportTaskResult.successful)
        {
          SudokuExportActivity localSudokuExportActivity1 = SudokuExportActivity.this;
          SudokuExportActivity localSudokuExportActivity2 = SudokuExportActivity.this;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = paramAnonymousFileExportTaskResult.file;
          Toast.makeText(localSudokuExportActivity1, localSudokuExportActivity2.getString(2131296377, arrayOfObject), 0).show();
        }
        for (;;)
        {
          SudokuExportActivity.this.finish();
          return;
          Toast.makeText(SudokuExportActivity.this, SudokuExportActivity.this.getString(2131296366), 1).show();
        }
      }
    });
    String str1 = this.mDirectoryEdit.getText().toString();
    String str2 = this.mFileNameEdit.getText().toString();
    this.mExportParams.file = new File(str1, str2 + ".opensudoku");
    showDialog(2);
    FileExportTask localFileExportTask = this.mFileExportTask;
    FileExportTaskParams[] arrayOfFileExportTaskParams = new FileExportTaskParams[1];
    arrayOfFileExportTaskParams[0] = this.mExportParams;
    localFileExportTask.execute(arrayOfFileExportTaskParams);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903054);
    this.mFileNameEdit = ((EditText)findViewById(2131230743));
    this.mDirectoryEdit = ((EditText)findViewById(2131230744));
    this.mSaveButton = ((Button)findViewById(2131230745));
    this.mSaveButton.setOnClickListener(this.mOnSaveClickListener);
    this.mFileExportTask = new FileExportTask(this);
    this.mExportParams = new FileExportTaskParams();
    Intent localIntent = getIntent();
    String str1;
    String str2;
    if (localIntent.hasExtra("FOLDER_ID"))
    {
      this.mExportParams.folderID = Long.valueOf(localIntent.getLongExtra("FOLDER_ID", -1L));
      str1 = DateFormat.format("yyyy-MM-dd", new Date()).toString();
      if (this.mExportParams.folderID.longValue() != -1L) {
        break label196;
      }
      str2 = "all-folders-" + str1;
    }
    for (;;)
    {
      this.mFileNameEdit.setText(str2);
      return;
      Log.d(TAG, "No 'FOLDER_ID' extra provided, exiting.");
      finish();
      return;
      label196:
      SudokuDatabase localSudokuDatabase = new SudokuDatabase(getApplicationContext());
      FolderInfo localFolderInfo = localSudokuDatabase.getFolderInfo(this.mExportParams.folderID.longValue());
      if (localFolderInfo == null)
      {
        String str3 = TAG;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = this.mExportParams.folderID;
        Log.d(str3, String.format("Folder with id %s not found, exiting.", arrayOfObject));
        finish();
        return;
      }
      str2 = localFolderInfo.name + "-" + str1;
      localSudokuDatabase.close();
    }
  }
  
  protected Dialog onCreateDialog(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return null;
    case 1: 
      new AlertDialog.Builder(this).setTitle(2131296360).setMessage(2131296378).setPositiveButton(17039379, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          SudokuExportActivity.this.startExportToFileTask();
        }
      }).setNegativeButton(17039369, null).create();
    }
    ProgressDialog localProgressDialog = new ProgressDialog(this);
    localProgressDialog.setIndeterminate(true);
    localProgressDialog.setTitle(2131296256);
    localProgressDialog.setMessage(getString(2131296365));
    return localProgressDialog;
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\SudokuExportActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */