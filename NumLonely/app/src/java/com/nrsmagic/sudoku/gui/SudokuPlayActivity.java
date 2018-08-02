package com.nrsmagic.sudoku.gui;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.nrsmagic.sudoku.db.SudokuDatabase;
import com.nrsmagic.sudoku.game.SudokuGame;
import com.nrsmagic.sudoku.game.SudokuGame.OnPuzzleSolvedListener;
import com.nrsmagic.sudoku.gui.inputmethod.IMControlPanel;
import com.nrsmagic.sudoku.gui.inputmethod.IMControlPanelStatePersister;
import com.nrsmagic.sudoku.gui.inputmethod.IMNumpad;
import com.nrsmagic.sudoku.gui.inputmethod.IMPopup;
import com.nrsmagic.sudoku.gui.inputmethod.IMSingleNumber;
import com.nrsmagic.sudoku.utils.AndroidUtils;
import com.nrsmagic.sudoku.utils.VersionHelperAndroid11;

public class SudokuPlayActivity
  extends Activity
{
  private static final int DIALOG_CLEAR_NOTES = 3;
  private static final int DIALOG_RESTART = 1;
  private static final int DIALOG_UNDO_TO_CHECKPOINT = 4;
  private static final int DIALOG_WELL_DONE = 2;
  public static final String EXTRA_SUDOKU_ID = "sudoku_id";
  public static final int MENU_ITEM_CLEAR_ALL_NOTES = 2;
  public static final int MENU_ITEM_FILL_IN_NOTES = 3;
  public static final int MENU_ITEM_HELP = 5;
  public static final int MENU_ITEM_RESTART = 1;
  public static final int MENU_ITEM_SETTINGS = 6;
  public static final int MENU_ITEM_SET_CHECKPOINT = 7;
  public static final int MENU_ITEM_UNDO = 4;
  public static final int MENU_ITEM_UNDO_TO_CHECKPOINT = 8;
  private static final int REQUEST_SETTINGS = 1;
  private SudokuDatabase mDatabase;
  private boolean mFillInNotesEnabled = false;
  private boolean mFullScreen;
  private GameTimeFormat mGameTimeFormatter = new GameTimeFormat();
  private GameTimer mGameTimer;
  private Handler mGuiHandler;
  private HintsQueue mHintsQueue;
  private IMControlPanel mIMControlPanel;
  private IMControlPanelStatePersister mIMControlPanelStatePersister;
  private IMNumpad mIMNumpad;
  private IMPopup mIMPopup;
  private IMSingleNumber mIMSingleNumber;
  private ViewGroup mRootLayout;
  private boolean mShowTime = true;
  private SudokuBoardView mSudokuBoard;
  private SudokuGame mSudokuGame;
  private long mSudokuGameID;
  private TextView mTimeLabel;
  private SudokuGame.OnPuzzleSolvedListener onSolvedListener = new SudokuGame.OnPuzzleSolvedListener()
  {
    public void onPuzzleSolved()
    {
      SudokuPlayActivity.this.mSudokuBoard.setReadOnly(true);
      SudokuPlayActivity.this.showDialog(2);
    }
  };
  
  private void refreshActionBarMenu()
  {
    if (Build.VERSION.SDK_INT >= 11) {
      VersionHelperAndroid11.refreshActionBarMenu(this);
    }
  }
  
  private void restartActivity()
  {
    startActivity(getIntent());
    finish();
  }
  
  private static void setMenuItemEnabled(Menu paramMenu, int paramInt, boolean paramBoolean)
  {
    if (paramMenu == null) {}
    MenuItem localMenuItem;
    do
    {
      return;
      localMenuItem = paramMenu.findItem(paramInt);
    } while (localMenuItem == null);
    localMenuItem.setEnabled(paramBoolean);
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    default: 
      return;
    }
    restartActivity();
  }
  
  public void onCreate(Bundle paramBundle)
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
    setContentView(2130903058);
    this.mRootLayout = ((ViewGroup)findViewById(2131230740));
    this.mSudokuBoard = ((SudokuBoardView)findViewById(2131230741));
    this.mTimeLabel = ((TextView)findViewById(2131230752));
    this.mDatabase = new SudokuDatabase(getApplicationContext());
    this.mHintsQueue = new HintsQueue(this);
    this.mGameTimer = new GameTimer();
    this.mGuiHandler = new Handler();
    if (paramBundle == null)
    {
      this.mSudokuGameID = getIntent().getLongExtra("sudoku_id", 0L);
      this.mSudokuGame = this.mDatabase.getSudoku(this.mSudokuGameID);
      if (this.mSudokuGame.getState() != 1) {
        break label412;
      }
      this.mSudokuGame.start();
    }
    for (;;)
    {
      if (this.mSudokuGame.getState() == 2) {
        this.mSudokuBoard.setReadOnly(true);
      }
      this.mSudokuBoard.setGame(this.mSudokuGame);
      this.mSudokuGame.setOnPuzzleSolvedListener(this.onSolvedListener);
      this.mHintsQueue.showOneTimeHint("welcome", 2131296332, 2131296336, new Object[0]);
      this.mIMControlPanel = ((IMControlPanel)findViewById(2131230742));
      this.mIMControlPanel.initialize(this.mSudokuBoard, this.mSudokuGame, this.mHintsQueue);
      this.mIMControlPanelStatePersister = new IMControlPanelStatePersister(this);
      this.mIMPopup = ((IMPopup)this.mIMControlPanel.getInputMethod(0));
      this.mIMSingleNumber = ((IMSingleNumber)this.mIMControlPanel.getInputMethod(1));
      this.mIMNumpad = ((IMNumpad)this.mIMControlPanel.getInputMethod(2));
      return;
      this.mSudokuGame = new SudokuGame();
      this.mSudokuGame.restoreState(paramBundle);
      this.mGameTimer.restoreState(paramBundle);
      break;
      label412:
      if (this.mSudokuGame.getState() == 0) {
        this.mSudokuGame.resume();
      }
    }
  }
  
  protected Dialog onCreateDialog(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return null;
    case 2: 
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(this).setIcon(17301659).setTitle(2131296313);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mGameTimeFormatter.format(this.mSudokuGame.getTime());
      return localBuilder.setMessage(getString(2131296311, arrayOfObject)).setPositiveButton(17039370, null).create();
    case 1: 
      new AlertDialog.Builder(this).setIcon(17301581).setTitle(2131296256).setMessage(2131296312).setPositiveButton(17039379, new OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          SudokuPlayActivity.this.mSudokuGame.reset();
          SudokuPlayActivity.this.mSudokuGame.start();
          SudokuPlayActivity.this.mSudokuBoard.setReadOnly(false);
          if (SudokuPlayActivity.this.mShowTime) {
            SudokuPlayActivity.this.mGameTimer.start();
          }
          SudokuPlayActivity.this.refreshActionBarMenu();
        }
      }).setNegativeButton(17039369, null).create();
    case 3: 
      new AlertDialog.Builder(this).setIcon(17301564).setTitle(2131296256).setMessage(2131296314).setPositiveButton(17039379, new OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          SudokuPlayActivity.this.mSudokuGame.clearAllNotes();
        }
      }).setNegativeButton(17039369, null).create();
    }
    new AlertDialog.Builder(this).setIcon(17301564).setTitle(2131296256).setMessage(2131296391).setPositiveButton(17039379, new OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        SudokuPlayActivity.this.mSudokuGame.undoToCheckpoint();
        SudokuPlayActivity.this.refreshActionBarMenu();
      }
    }).setNegativeButton(17039369, null).create();
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    super.onCreateOptionsMenu(paramMenu);
    paramMenu.add(0, 4, 0, 2131296307).setShortcut('1', 'u').setIcon(17301580);
    paramMenu.add(0, 1, 1, 2131296310).setShortcut('7', 'r').setIcon(17301581);
    paramMenu.add(0, 2, 0, 2131296308).setShortcut('3', 'a').setIcon(17301564);
    if (this.mFillInNotesEnabled) {
      paramMenu.add(0, 3, 0, 2131296309).setIcon(17301566);
    }
    paramMenu.add(0, 5, 1, 2131296334).setShortcut('0', 'h').setIcon(17301568);
    paramMenu.add(0, 6, 1, 2131296315).setShortcut('9', 's').setIcon(17301577);
    paramMenu.add(0, 7, 2, 2131296389);
    paramMenu.add(0, 8, 2, 2131296390);
    Intent localIntent = new Intent(null, getIntent().getData());
    localIntent.addCategory("android.intent.category.ALTERNATIVE");
    paramMenu.addIntentOptions(262144, 0, 0, new ComponentName(this, SudokuPlayActivity.class), null, localIntent, 0, null);
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
      showDialog(1);
      return true;
    case 2: 
      showDialog(3);
      return true;
    case 3: 
      this.mSudokuGame.fillInNotes();
      return true;
    case 4: 
      this.mSudokuGame.undo();
      refreshActionBarMenu();
      return true;
    case 6: 
      Intent localIntent = new Intent();
      localIntent.setClass(this, GameSettingsActivity.class);
      startActivityForResult(localIntent, 1);
      return true;
    case 5: 
      this.mHintsQueue.showHint(2131296334, 2131296341, new Object[0]);
      return true;
    case 7: 
      this.mSudokuGame.setUndoCheckpoint();
      refreshActionBarMenu();
      return true;
    }
    showDialog(4);
    return true;
  }
  
  protected void onPause()
  {
    super.onPause();
    this.mDatabase.updateSudoku(this.mSudokuGame);
    this.mGameTimer.stop();
    this.mIMControlPanel.pause();
    this.mIMControlPanelStatePersister.saveState(this.mIMControlPanel);
  }
  
  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    super.onPrepareOptionsMenu(paramMenu);
    if (this.mSudokuGame != null)
    {
      if (this.mSudokuGame.getState() == 0)
      {
        setMenuItemEnabled(paramMenu, 2, true);
        if (this.mFillInNotesEnabled) {
          setMenuItemEnabled(paramMenu, 3, true);
        }
        setMenuItemEnabled(paramMenu, 4, this.mSudokuGame.hasSomethingToUndo());
        setMenuItemEnabled(paramMenu, 8, this.mSudokuGame.hasUndoCheckpoint());
      }
    }
    else {
      return true;
    }
    setMenuItemEnabled(paramMenu, 2, false);
    if (this.mFillInNotesEnabled) {
      setMenuItemEnabled(paramMenu, 3, false);
    }
    setMenuItemEnabled(paramMenu, 4, false);
    setMenuItemEnabled(paramMenu, 8, false);
    return true;
  }
  
  protected void onResume()
  {
    super.onResume();
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    int i = localSharedPreferences.getInt("screen_border_size", 0);
    this.mRootLayout.setPadding(i, i, i, i);
    this.mFillInNotesEnabled = localSharedPreferences.getBoolean("fill_in_notes_enabled", false);
    this.mSudokuBoard.setHighlightWrongVals(localSharedPreferences.getBoolean("highlight_wrong_values", true));
    this.mSudokuBoard.setHighlightTouchedCell(localSharedPreferences.getBoolean("highlight_touched_cell", true));
    this.mShowTime = localSharedPreferences.getBoolean("show_time", true);
    if (this.mSudokuGame.getState() == 0)
    {
      this.mSudokuGame.resume();
      if (this.mShowTime) {
        this.mGameTimer.start();
      }
    }
    TextView localTextView = this.mTimeLabel;
    if ((this.mFullScreen) && (this.mShowTime)) {}
    for (int j = 0;; j = 8)
    {
      localTextView.setVisibility(j);
      this.mIMPopup.setEnabled(localSharedPreferences.getBoolean("im_popup", false));
      this.mIMSingleNumber.setEnabled(localSharedPreferences.getBoolean("im_single_number", true));
      this.mIMNumpad.setEnabled(localSharedPreferences.getBoolean("im_numpad", true));
      this.mIMNumpad.setMoveCellSelectionOnPress(localSharedPreferences.getBoolean("im_numpad_move_right", false));
      this.mIMPopup.setHighlightCompletedValues(localSharedPreferences.getBoolean("highlight_completed_values", true));
      this.mIMPopup.setShowNumberTotals(localSharedPreferences.getBoolean("show_number_totals", false));
      this.mIMSingleNumber.setHighlightCompletedValues(localSharedPreferences.getBoolean("highlight_completed_values", true));
      this.mIMSingleNumber.setShowNumberTotals(localSharedPreferences.getBoolean("show_number_totals", false));
      this.mIMNumpad.setHighlightCompletedValues(localSharedPreferences.getBoolean("highlight_completed_values", true));
      this.mIMNumpad.setShowNumberTotals(localSharedPreferences.getBoolean("show_number_totals", false));
      this.mIMControlPanel.activateFirstInputMethod();
      this.mIMControlPanelStatePersister.restoreState(this.mIMControlPanel);
      updateTime();
      refreshActionBarMenu();
      return;
    }
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    this.mGameTimer.stop();
    if (this.mSudokuGame.getState() == 0) {
      this.mSudokuGame.pause();
    }
    this.mSudokuGame.saveState(paramBundle);
    this.mGameTimer.saveState(paramBundle);
  }
  
  public void onWindowFocusChanged(boolean paramBoolean)
  {
    super.onWindowFocusChanged(paramBoolean);
    if ((paramBoolean) && (this.mFullScreen)) {
      this.mGuiHandler.postDelayed(new Runnable()
      {
        public void run()
        {
          SudokuPlayActivity.this.getWindow().clearFlags(2048);
          SudokuPlayActivity.this.mRootLayout.requestLayout();
        }
      }, 1000L);
    }
  }
  
  void updateTime()
  {
    if (this.mShowTime)
    {
      setTitle(this.mGameTimeFormatter.format(this.mSudokuGame.getTime()));
      this.mTimeLabel.setText(this.mGameTimeFormatter.format(this.mSudokuGame.getTime()));
      return;
    }
    setTitle(2131296256);
  }
  
  private final class GameTimer
    extends Timer
  {
    GameTimer()
    {
      super();
    }
    
    protected boolean step(int paramInt, long paramLong)
    {
      SudokuPlayActivity.this.updateTime();
      return false;
    }
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\SudokuPlayActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */