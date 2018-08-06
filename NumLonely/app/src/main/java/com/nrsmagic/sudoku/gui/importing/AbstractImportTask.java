package com.nrsmagic.sudoku.gui.importing;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.nrsmagic.sudoku.db.SudokuDatabase;
import com.nrsmagic.sudoku.db.SudokuImportParams;
import com.nrsmagic.sudoku.db.SudokuInvalidFormatException;
import com.nrsmagic.sudoku.game.FolderInfo;

public abstract class AbstractImportTask
  extends AsyncTask<Void, Integer, Boolean>
{
  static final int NUM_OF_PROGRESS_UPDATES = 20;
  protected Context mContext;
  private SudokuDatabase mDatabase;
  private FolderInfo mFolder;
  private int mFolderCount;
  private int mGameCount;
  private String mImportError;
  private SudokuImportParams mImportParams = new SudokuImportParams();
  private boolean mImportSuccessful;
  private OnImportFinishedListener mOnImportFinishedListener;
  private ProgressBar mProgressBar;
  
  private Boolean processImportInternal()
  {
    this.mImportSuccessful = true;
    long l1 = System.currentTimeMillis();
    this.mDatabase = new SudokuDatabase(this.mContext);
    try
    {
      this.mDatabase.beginTransaction();
      processImport();
      this.mDatabase.setTransactionSuccessful();
    }
    catch (SudokuInvalidFormatException localSudokuInvalidFormatException)
    {
      for (;;)
      {
        setError(this.mContext.getString(2131296352));
        this.mDatabase.endTransaction();
        this.mDatabase.close();
        this.mDatabase = null;
      }
    }
    finally
    {
      this.mDatabase.endTransaction();
      this.mDatabase.close();
      this.mDatabase = null;
    }
    if ((this.mFolderCount == 0) && (this.mGameCount == 0))
    {
      setError(this.mContext.getString(2131296350));
      return Boolean.valueOf(false);
    }
    long l2 = System.currentTimeMillis();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Float.valueOf((float)(l2 - l1) / 1000.0F);
    Log.i("OpenSudoku", String.format("Imported in %f seconds.", arrayOfObject));
    return Boolean.valueOf(this.mImportSuccessful);
  }
  
  protected void appendToFolder(String paramString)
  {
    if (this.mDatabase == null) {
      throw new IllegalStateException("Database is not opened.");
    }
    this.mFolderCount = (1 + this.mFolderCount);
    this.mFolder = null;
    this.mFolder = this.mDatabase.findFolder(paramString);
    if (this.mFolder == null) {
      this.mFolder = this.mDatabase.insertFolder(paramString, Long.valueOf(System.currentTimeMillis()));
    }
  }
  
  protected Boolean doInBackground(Void... paramVarArgs)
  {
    try
    {
      Boolean localBoolean = processImportInternal();
      return localBoolean;
    }
    catch (Exception localException)
    {
      Log.e("OpenSudoku", "Exception occurred during import.", localException);
      setError(this.mContext.getString(2131296351));
    }
    return Boolean.valueOf(false);
  }
  
  protected void importFolder(String paramString)
  {
    importFolder(paramString, System.currentTimeMillis());
  }
  
  protected void importFolder(String paramString, long paramLong)
  {
    if (this.mDatabase == null) {
      throw new IllegalStateException("Database is not opened.");
    }
    this.mFolderCount = (1 + this.mFolderCount);
    this.mFolder = this.mDatabase.insertFolder(paramString, Long.valueOf(paramLong));
  }
  
  protected void importGame(SudokuImportParams paramSudokuImportParams)
    throws SudokuInvalidFormatException
  {
    if (this.mDatabase == null) {
      throw new IllegalStateException("Database is not opened.");
    }
    this.mDatabase.importSudoku(this.mFolder.id, paramSudokuImportParams);
  }
  
  protected void importGame(String paramString)
    throws SudokuInvalidFormatException
  {
    this.mImportParams.clear();
    this.mImportParams.data = paramString;
    importGame(this.mImportParams);
  }
  
  public void initialize(Context paramContext, ProgressBar paramProgressBar)
  {
    this.mContext = paramContext;
    this.mProgressBar = paramProgressBar;
  }
  
  protected void onPostExecute(Boolean paramBoolean)
  {
    if (paramBoolean.booleanValue()) {
      if (this.mFolderCount == 1)
      {
        Context localContext3 = this.mContext;
        Context localContext4 = this.mContext;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = this.mFolder.name;
        Toast.makeText(localContext3, localContext4.getString(2131296349, arrayOfObject2), 1).show();
      }
    }
    for (;;)
    {
      if (this.mOnImportFinishedListener != null)
      {
        long l = -1L;
        if (this.mFolderCount == 1) {
          l = this.mFolder.id;
        }
        this.mOnImportFinishedListener.onImportFinished(paramBoolean.booleanValue(), l);
      }
      return;
      if (this.mFolderCount > 1)
      {
        Context localContext1 = this.mContext;
        Context localContext2 = this.mContext;
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(this.mFolderCount);
        Toast.makeText(localContext1, localContext2.getString(2131296363, arrayOfObject1), 1).show();
        continue;
        Toast.makeText(this.mContext, this.mImportError, 1).show();
      }
    }
  }
  
  protected void onProgressUpdate(Integer... paramVarArgs)
  {
    if (paramVarArgs.length == 2) {
      this.mProgressBar.setMax(paramVarArgs[1].intValue());
    }
    this.mProgressBar.setProgress(paramVarArgs[0].intValue());
  }
  
  protected abstract void processImport()
    throws SudokuInvalidFormatException;
  
  protected void setError(String paramString)
  {
    this.mImportError = paramString;
    this.mImportSuccessful = false;
  }
  
  public void setOnImportFinishedListener(OnImportFinishedListener paramOnImportFinishedListener)
  {
    this.mOnImportFinishedListener = paramOnImportFinishedListener;
  }
  
  public static abstract interface OnImportFinishedListener
  {
    public abstract void onImportFinished(boolean paramBoolean, long paramLong);
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\importing\AbstractImportTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */