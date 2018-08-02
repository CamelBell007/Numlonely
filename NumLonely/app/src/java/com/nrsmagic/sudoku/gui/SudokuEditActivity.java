package com.nrsmagic.sudoku.gui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.nrsmagic.sudoku.db.SudokuDatabase;
import com.nrsmagic.sudoku.game.CellCollection;
import com.nrsmagic.sudoku.game.SudokuGame;
import com.nrsmagic.sudoku.gui.inputmethod.IMControlPanel;
import com.nrsmagic.sudoku.gui.inputmethod.InputMethod;
import com.nrsmagic.sudoku.utils.AndroidUtils;
import java.util.Iterator;
import java.util.List;

public class SudokuEditActivity
  extends Activity
{
  public static final String EXTRA_FOLDER_ID = "folder_id";
  public static final String EXTRA_SUDOKU_ID = "sudoku_id";
  public static final int MENU_ITEM_CANCEL = 2;
  public static final int MENU_ITEM_SAVE = 1;
  private static final int STATE_CANCEL = 2;
  private static final int STATE_EDIT = 0;
  private static final int STATE_INSERT = 1;
  private static final String TAG = "SudokuEditActivity";
  private SudokuBoardView mBoard;
  private SudokuDatabase mDatabase;
  private long mFolderID;
  private boolean mFullScreen;
  private SudokuGame mGame;
  private Handler mGuiHandler;
  private IMControlPanel mInputMethods;
  private ViewGroup mRootLayout;
  private int mState;
  private long mSudokuID;
  
  private void savePuzzle()
  {
    this.mGame.getCells().markFilledCellsAsNotEditable();
    switch (this.mState)
    {
    default: 
      return;
    case 0: 
      this.mDatabase.updateSudoku(this.mGame);
      Toast.makeText(getApplicationContext(), 2131296348, 0).show();
      return;
    }
    this.mGame.setCreated(System.currentTimeMillis());
    this.mDatabase.insertSudoku(this.mFolderID, this.mGame);
    Toast.makeText(getApplicationContext(), 2131296347, 0).show();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Display localDisplay = getWindowManager().getDefaultDisplay();
    if (((localDisplay.getWidth() == 240) || (localDisplay.getWidth() == 320)) && ((localDisplay.getHeight() == 240) || (localDisplay.getHeight() == 320)))
    {
      requestWindowFeature(1);
      getWindow().setFlags(1024, 1024);
      this.mFullScreen = true;
    }
    AndroidUtils.setThemeFromPreferences(this);
    setContentView(2130903053);
    this.mRootLayout = ((ViewGroup)findViewById(2131230740));
    this.mBoard = ((SudokuBoardView)findViewById(2131230741));
    this.mDatabase = new SudokuDatabase(getApplicationContext());
    this.mGuiHandler = new Handler();
    Intent localIntent = getIntent();
    String str = localIntent.getAction();
    label214:
    Iterator localIterator;
    if ("android.intent.action.EDIT".equals(str))
    {
      this.mState = 0;
      if (localIntent.hasExtra("sudoku_id"))
      {
        this.mSudokuID = localIntent.getLongExtra("sudoku_id", 0L);
        if (paramBundle == null) {
          break label403;
        }
        this.mGame = new SudokuGame();
        this.mGame.restoreState(paramBundle);
        this.mBoard.setGame(this.mGame);
        this.mInputMethods = ((IMControlPanel)findViewById(2131230742));
        this.mInputMethods.initialize(this.mBoard, this.mGame, null);
        localIterator = this.mInputMethods.getInputMethods().iterator();
      }
    }
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        this.mInputMethods.getInputMethod(2).setEnabled(true);
        this.mInputMethods.activateInputMethod(2);
        return;
        throw new IllegalArgumentException(String.format("Extra with key '%s' is required.", new Object[] { "sudoku_id" }));
        if ("android.intent.action.INSERT".equals(str))
        {
          this.mState = 1;
          this.mSudokuID = 0L;
          if (localIntent.hasExtra("folder_id"))
          {
            this.mFolderID = localIntent.getLongExtra("folder_id", 0L);
            break;
          }
          throw new IllegalArgumentException(String.format("Extra with key '%s' is required.", new Object[] { "folder_id" }));
        }
        Log.e("SudokuEditActivity", "Unknown action, exiting.");
        finish();
        return;
        label403:
        if (this.mSudokuID != 0L)
        {
          this.mGame = this.mDatabase.getSudoku(this.mSudokuID);
          this.mGame.getCells().markAllCellsAsEditable();
          break label214;
        }
        this.mGame = SudokuGame.createEmptyGame();
        break label214;
      }
      ((InputMethod)localIterator.next()).setEnabled(false);
    }
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    paramMenu.add(0, 1, 0, 2131296266).setShortcut('1', 's').setIcon(17301582);
    paramMenu.add(0, 2, 1, 17039360).setShortcut('3', 'c').setIcon(17301560);
    Intent localIntent = new Intent(null, getIntent().getData());
    localIntent.addCategory("android.intent.category.ALTERNATIVE");
    paramMenu.addIntentOptions(262144, 0, 0, new ComponentName(this, SudokuEditActivity.class), null, localIntent, 0, null);
    return true;
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    this.mDatabase.close();
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    case 1: 
      finish();
      return true;
    }
    this.mState = 2;
    finish();
    return true;
  }
  
  protected void onPause()
  {
    super.onPause();
    if ((isFinishing()) && (this.mState != 2) && (!this.mGame.getCells().isEmpty())) {
      savePuzzle();
    }
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    this.mGame.saveState(paramBundle);
  }
  
  public void onWindowFocusChanged(boolean paramBoolean)
  {
    super.onWindowFocusChanged(paramBoolean);
    if ((paramBoolean) && (this.mFullScreen)) {
      this.mGuiHandler.postDelayed(new Runnable()
      {
        public void run()
        {
          SudokuEditActivity.this.getWindow().clearFlags(2048);
          SudokuEditActivity.this.mRootLayout.requestLayout();
        }
      }, 1000L);
    }
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\SudokuEditActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */