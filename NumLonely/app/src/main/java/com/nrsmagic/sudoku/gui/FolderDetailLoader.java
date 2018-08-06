package com.nrsmagic.sudoku.gui;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.nrsmagic.sudoku.db.SudokuDatabase;
import com.nrsmagic.sudoku.game.FolderInfo;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FolderDetailLoader
{
  private static final String TAG = "FolderDetailLoader";
  private Context mContext;
  private SudokuDatabase mDatabase;
  private Handler mGuiHandler;
  private ExecutorService mLoaderService = Executors.newSingleThreadExecutor();
  
  public FolderDetailLoader(Context paramContext)
  {
    this.mContext = paramContext;
    this.mDatabase = new SudokuDatabase(this.mContext);
    this.mGuiHandler = new Handler();
  }
  
  public void destroy()
  {
    this.mLoaderService.shutdownNow();
    this.mDatabase.close();
  }
  
  public void loadDetailAsync(final long paramLong, FolderDetailCallback paramFolderDetailCallback)
  {
    this.mLoaderService.execute(new Runnable()
    {
      public void run()
      {
        try
        {
          final FolderInfo localFolderInfo = FolderDetailLoader.this.mDatabase.getFolderInfoFull(paramLong);
          FolderDetailLoader.this.mGuiHandler.post(new Runnable()
          {
            public void run()
            {
              this.val$loadedCallbackFinal.onLoaded(localFolderInfo);
            }
          });
          return;
        }
        catch (Exception localException)
        {
          Log.e("FolderDetailLoader", "Error occured while loading full folder info.", localException);
        }
      }
    });
  }
  
  public static abstract interface FolderDetailCallback
  {
    public abstract void onLoaded(FolderInfo paramFolderInfo);
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\FolderDetailLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */