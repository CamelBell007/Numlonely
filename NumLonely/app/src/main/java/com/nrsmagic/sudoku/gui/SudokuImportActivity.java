package com.nrsmagic.sudoku.gui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ProgressBar;
import com.nrsmagic.sudoku.gui.importing.AbstractImportTask;
import com.nrsmagic.sudoku.gui.importing.AbstractImportTask.OnImportFinishedListener;
import com.nrsmagic.sudoku.gui.importing.ExtrasImportTask;
import com.nrsmagic.sudoku.gui.importing.OpenSudokuImportTask;
import com.nrsmagic.sudoku.gui.importing.SdmImportTask;

public class SudokuImportActivity
  extends Activity
{
  public static final String EXTRA_APPEND_TO_FOLDER = "APPEND_TO_FOLDER";
  public static final String EXTRA_FOLDER_NAME = "FOLDER_NAME";
  public static final String EXTRA_GAMES = "GAMES";
  private static final String TAG = "ImportSudokuActivity";
  private OnImportFinishedListener mOnImportFinishedListener = new OnImportFinishedListener()
  {
    public void onImportFinished(boolean paramAnonymousBoolean, long paramAnonymousLong)
    {
      if (paramAnonymousBoolean)
      {
        if (paramAnonymousLong != -1L) {
          break label44;
        }
        Intent localIntent1 = new Intent(SudokuImportActivity.this, FolderListActivity.class);
        SudokuImportActivity.this.startActivity(localIntent1);
      }
      for (;;)
      {
        SudokuImportActivity.this.finish();
        return;
        label44:
        Intent localIntent2 = new Intent(SudokuImportActivity.this, SudokuListActivity.class);
        localIntent2.putExtra("folder_id", paramAnonymousLong);
        SudokuImportActivity.this.startActivity(localIntent2);
      }
    }
  };
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(3);
    setContentView(2130903051);
    getWindow().setFeatureDrawableResource(3, 2130837519);
    ProgressBar localProgressBar = (ProgressBar)findViewById(2131230737);
    Intent localIntent = getIntent();
    Uri localUri = localIntent.getData();
    Object localObject;
    if (localUri != null) {
      if (("application/x-opensudoku".equals(localIntent.getType())) || (localUri.toString().endsWith(".opensudoku"))) {
        localObject = new OpenSudokuImportTask(localUri);
      }
    }
    for (;;)
    {
      ((AbstractImportTask)localObject).initialize(this, localProgressBar);
      ((AbstractImportTask)localObject).setOnImportFinishedListener(this.mOnImportFinishedListener);
      ((AbstractImportTask)localObject).execute(new Void[0]);
      return;
      if (localUri.toString().endsWith(".sdm"))
      {
        localObject = new SdmImportTask(localUri);
      }
      else
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = localIntent.getType();
        arrayOfObject[1] = localUri;
        Log.e("ImportSudokuActivity", String.format("Unknown type of data provided (mime-type=%s; uri=%s), exiting.", arrayOfObject));
        finish();
        return;
        if (localIntent.getStringExtra("FOLDER_NAME") == null) {
          break;
        }
        localObject = new ExtrasImportTask(localIntent.getStringExtra("FOLDER_NAME"), localIntent.getStringExtra("GAMES"), localIntent.getBooleanExtra("APPEND_TO_FOLDER", false));
      }
    }
    Log.e("ImportSudokuActivity", "No data provided, exiting.");
    finish();
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\SudokuImportActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */