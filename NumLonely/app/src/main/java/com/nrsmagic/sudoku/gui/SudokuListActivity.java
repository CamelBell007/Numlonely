package com.nrsmagic.sudoku.gui;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;
import com.nrsmagic.sudoku.db.SudokuDatabase;
import com.nrsmagic.sudoku.game.CellCollection;
import com.nrsmagic.sudoku.game.FolderInfo;
import com.nrsmagic.sudoku.game.SudokuGame;
import com.nrsmagic.sudoku.utils.AndroidUtils;
import java.text.DateFormat;
import java.util.Date;

public class SudokuListActivity
  extends ListActivity
{
  private static final int DIALOG_DELETE_PUZZLE = 0;
  private static final int DIALOG_EDIT_NOTE = 2;
  private static final int DIALOG_FILTER = 3;
  private static final int DIALOG_RESET_PUZZLE = 1;
  public static final String EXTRA_FOLDER_ID = "folder_id";
  private static final String FILTER_STATE_NOT_STARTED = "filter1";
  private static final String FILTER_STATE_PLAYING = "filter0";
  private static final String FILTER_STATE_SOLVED = "filter2";
  public static final int MENU_ITEM_DELETE = 3;
  public static final int MENU_ITEM_EDIT = 2;
  public static final int MENU_ITEM_EDIT_NOTE = 6;
  public static final int MENU_ITEM_PLAY = 4;
  public static final int MENU_ITEM_RESET = 5;
  private static final String TAG = "SudokuListActivity";
  private SimpleCursorAdapter mAdapter;
  private Cursor mCursor;
  private SudokuDatabase mDatabase;
  private long mDeletePuzzleID;
  private TextView mEditNoteInput;
  private long mEditNotePuzzleID;
  private TextView mFilterStatus;
  private FolderDetailLoader mFolderDetailLoader;
  private long mFolderID;
  private SudokuListFilter mListFilter;
  private long mResetPuzzleID;
  
  private void playSudoku(long paramLong)
  {
    Intent localIntent = new Intent(this, SudokuPlayActivity.class);
    localIntent.putExtra("sudoku_id", paramLong);
    startActivity(localIntent);
  }
  
  private void updateFilterStatus()
  {
    if ((this.mListFilter.showStateCompleted) && (this.mListFilter.showStateNotStarted) && (this.mListFilter.showStatePlaying))
    {
      this.mFilterStatus.setVisibility(8);
      return;
    }
    TextView localTextView = this.mFilterStatus;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mListFilter;
    localTextView.setText(getString(2131296306, arrayOfObject));
    this.mFilterStatus.setVisibility(0);
  }
  
  private void updateList()
  {
    updateTitle();
    updateFilterStatus();
    if (this.mCursor != null) {
      stopManagingCursor(this.mCursor);
    }
    this.mCursor = this.mDatabase.getSudokuList(this.mFolderID, this.mListFilter);
    startManagingCursor(this.mCursor);
    this.mAdapter.changeCursor(this.mCursor);
  }
  
  private void updateTitle()
  {
    setTitle(this.mDatabase.getFolderInfo(this.mFolderID).name);
    this.mFolderDetailLoader.loadDetailAsync(this.mFolderID, new FolderDetailLoader.FolderDetailCallback()
    {
      public void onLoaded(FolderInfo paramAnonymousFolderInfo)
      {
        if (paramAnonymousFolderInfo != null) {
          SudokuListActivity.this.setTitle(paramAnonymousFolderInfo.name + " - " + paramAnonymousFolderInfo.getDetail(SudokuListActivity.this.getApplicationContext()));
        }
      }
    });
  }
  
  public boolean onContextItemSelected(MenuItem paramMenuItem)
  {
    AdapterView.AdapterContextMenuInfo localAdapterContextMenuInfo;
    try
    {
      localAdapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)paramMenuItem.getMenuInfo();
      switch (paramMenuItem.getItemId())
      {
      default: 
        return false;
      }
    }
    catch (ClassCastException localClassCastException)
    {
      Log.e("SudokuListActivity", "bad menuInfo", localClassCastException);
      return false;
    }
    playSudoku(localAdapterContextMenuInfo.id);
    return true;
    Intent localIntent = new Intent(this, SudokuEditActivity.class);
    localIntent.setAction("android.intent.action.EDIT");
    localIntent.putExtra("sudoku_id", localAdapterContextMenuInfo.id);
    startActivity(localIntent);
    return true;
    this.mDeletePuzzleID = localAdapterContextMenuInfo.id;
    showDialog(0);
    return true;
    this.mEditNotePuzzleID = localAdapterContextMenuInfo.id;
    showDialog(2);
    return true;
    this.mResetPuzzleID = localAdapterContextMenuInfo.id;
    showDialog(1);
    return true;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    AndroidUtils.setThemeFromPreferences(this);
    setContentView(2130903055);
    this.mFilterStatus = ((TextView)findViewById(2131230746));
    getListView().setOnCreateContextMenuListener(this);
    setDefaultKeyMode(2);
    this.mDatabase = new SudokuDatabase(getApplicationContext());
    this.mFolderDetailLoader = new FolderDetailLoader(getApplicationContext());
    Intent localIntent = getIntent();
    if (localIntent.hasExtra("folder_id"))
    {
      this.mFolderID = localIntent.getLongExtra("folder_id", 0L);
      SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
      this.mListFilter = new SudokuListFilter(getApplicationContext());
      this.mListFilter.showStateNotStarted = localSharedPreferences.getBoolean("filter1", true);
      this.mListFilter.showStatePlaying = localSharedPreferences.getBoolean("filter0", true);
      this.mListFilter.showStateCompleted = localSharedPreferences.getBoolean("filter2", true);
      this.mAdapter = new SimpleCursorAdapter(this, 2130903056, null, new String[] { "data", "state", "time", "last_played", "created", "puzzle_note" }, new int[] { 2131230741, 2131230747, 2131230748, 2131230749, 2131230750, 2131230751 });
      this.mAdapter.setViewBinder(new SudokuListViewBinder(this));
      updateList();
      setListAdapter(this.mAdapter);
      return;
    }
    Log.d("SudokuListActivity", "No 'folder_id' extra provided, exiting.");
    finish();
  }
  
  public void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenuInfo paramContextMenuInfo)
  {
    try
    {
      AdapterView.AdapterContextMenuInfo localAdapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)paramContextMenuInfo;
      if ((Cursor)getListAdapter().getItem(localAdapterContextMenuInfo.position) == null) {
        return;
      }
    }
    catch (ClassCastException localClassCastException)
    {
      Log.e("SudokuListActivity", "bad menuInfo", localClassCastException);
      return;
    }
    paramContextMenu.setHeaderTitle("Puzzle");
    paramContextMenu.add(0, 4, 0, 2131296302);
    paramContextMenu.add(0, 6, 1, 2131296271);
    paramContextMenu.add(0, 5, 2, 2131296303);
    paramContextMenu.add(0, 2, 3, 2131296304);
    paramContextMenu.add(0, 3, 4, 2131296305);
  }
  
  protected Dialog onCreateDialog(int paramInt)
  {
    final SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    switch (paramInt)
    {
    default: 
      return null;
    case 0: 
      new AlertDialog.Builder(this).setIcon(17301533).setTitle("Puzzle").setMessage(2131296300).setPositiveButton(17039379, new OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          SudokuListActivity.this.mDatabase.deleteSudoku(SudokuListActivity.this.mDeletePuzzleID);
          SudokuListActivity.this.updateList();
        }
      }).setNegativeButton(17039369, null).create();
    case 2: 
      View localView = LayoutInflater.from(this).inflate(2130903057, null);
      this.mEditNoteInput = ((TextView)localView.findViewById(2131230751));
      new AlertDialog.Builder(this).setIcon(17301555).setTitle(2131296271).setView(localView).setPositiveButton(2131296266, new OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          SudokuGame localSudokuGame = SudokuListActivity.this.mDatabase.getSudoku(SudokuListActivity.this.mEditNotePuzzleID);
          localSudokuGame.setNote(SudokuListActivity.this.mEditNoteInput.getText().toString());
          SudokuListActivity.this.mDatabase.updateSudoku(localSudokuGame);
          SudokuListActivity.this.updateList();
        }
      }).setNegativeButton(17039360, null).create();
    case 1: 
      new AlertDialog.Builder(this).setIcon(17301581).setTitle("Puzzle").setMessage(2131296301).setPositiveButton(17039379, new OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          SudokuGame localSudokuGame = SudokuListActivity.this.mDatabase.getSudoku(SudokuListActivity.this.mResetPuzzleID);
          if (localSudokuGame != null)
          {
            localSudokuGame.reset();
            SudokuListActivity.this.mDatabase.updateSudoku(localSudokuGame);
          }
          SudokuListActivity.this.updateList();
        }
      }).setNegativeButton(17039369, null).create();
    }
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this).setIcon(17301591).setTitle(2131296298);
    boolean[] arrayOfBoolean = new boolean[3];
    arrayOfBoolean[0] = this.mListFilter.showStateNotStarted;
    arrayOfBoolean[1] = this.mListFilter.showStatePlaying;
    arrayOfBoolean[2] = this.mListFilter.showStateCompleted;
    localBuilder.setMultiChoiceItems(2131099648, arrayOfBoolean, new OnMultiChoiceClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        switch (paramAnonymousInt)
        {
        default: 
          return;
        case 0: 
          SudokuListActivity.this.mListFilter.showStateNotStarted = paramAnonymousBoolean;
          return;
        case 1: 
          SudokuListActivity.this.mListFilter.showStatePlaying = paramAnonymousBoolean;
          return;
        }
        SudokuListActivity.this.mListFilter.showStateCompleted = paramAnonymousBoolean;
      }
    }).setPositiveButton(17039370, new OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        localSharedPreferences.edit().putBoolean("filter1", SudokuListActivity.this.mListFilter.showStateNotStarted).putBoolean("filter0", SudokuListActivity.this.mListFilter.showStatePlaying).putBoolean("filter2", SudokuListActivity.this.mListFilter.showStateCompleted).commit();
        SudokuListActivity.this.updateList();
      }
    }).setNegativeButton(17039360, new OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {}
    }).create();
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    super.onCreateOptionsMenu(paramMenu);
    getMenuInflater().inflate(2131427328, paramMenu);
    Intent localIntent = new Intent(null, getIntent().getData());
    localIntent.addCategory("android.intent.category.ALTERNATIVE");
    paramMenu.addIntentOptions(262144, 0, 0, new ComponentName(this, SudokuListActivity.class), null, localIntent, 0, null);
    return true;
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    this.mDatabase.close();
    this.mFolderDetailLoader.destroy();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((isTaskRoot()) && (paramInt == 4))
    {
      Intent localIntent = new Intent();
      localIntent.setClass(this, FolderListActivity.class);
      startActivity(localIntent);
      finish();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  protected void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong)
  {
    playSudoku(paramLong);
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    case 2131230754: 
      Intent localIntent = new Intent(this, SudokuEditActivity.class);
      localIntent.setAction("android.intent.action.INSERT");
      localIntent.putExtra("folder_id", this.mFolderID);
      startActivity(localIntent);
      return true;
    }
    showDialog(3);
    return true;
  }
  
  protected void onPrepareDialog(int paramInt, Dialog paramDialog)
  {
    super.onPrepareDialog(paramInt, paramDialog);
    switch (paramInt)
    {
    default: 
      return;
    }
    SudokuGame localSudokuGame = new SudokuDatabase(getApplicationContext()).getSudoku(this.mEditNotePuzzleID);
    this.mEditNoteInput.setText(localSudokuGame.getNote());
  }
  
  protected void onRestoreInstanceState(Bundle paramBundle)
  {
    super.onRestoreInstanceState(paramBundle);
    this.mDeletePuzzleID = paramBundle.getLong("mDeletePuzzleID");
    this.mResetPuzzleID = paramBundle.getLong("mResetPuzzleID");
    this.mEditNotePuzzleID = paramBundle.getLong("mEditNotePuzzleID");
  }
  
  protected void onResume()
  {
    super.onResume();
    updateTitle();
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putLong("mDeletePuzzleID", this.mDeletePuzzleID);
    paramBundle.putLong("mResetPuzzleID", this.mResetPuzzleID);
    paramBundle.putLong("mEditNotePuzzleID", this.mEditNotePuzzleID);
  }
  
  private static class SudokuListViewBinder
    implements ViewBinder
  {
    private Context mContext;
    private DateFormat mDateTimeFormatter = DateFormat.getDateTimeInstance(3, 3);
    private GameTimeFormat mGameTimeFormatter = new GameTimeFormat();
    private DateFormat mTimeFormatter = DateFormat.getTimeInstance(3);
    
    public SudokuListViewBinder(Context paramContext)
    {
      this.mContext = paramContext;
    }
    
    private String getDateAndTimeForHumans(long paramLong)
    {
      Date localDate1 = new Date(paramLong);
      Date localDate2 = new Date(System.currentTimeMillis());
      Date localDate3 = new Date(localDate2.getYear(), localDate2.getMonth(), localDate2.getDate());
      Date localDate4 = new Date(System.currentTimeMillis() - 86400000L);
      if (localDate1.after(localDate3))
      {
        Context localContext3 = this.mContext;
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = this.mTimeFormatter.format(localDate1);
        return localContext3.getString(2131296293, arrayOfObject3);
      }
      if (localDate1.after(localDate4))
      {
        Context localContext2 = this.mContext;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = this.mTimeFormatter.format(localDate1);
        return localContext2.getString(2131296294, arrayOfObject2);
      }
      Context localContext1 = this.mContext;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = this.mDateTimeFormatter.format(localDate1);
      return localContext1.getString(2131296295, arrayOfObject1);
    }
    
    public boolean setViewValue(View paramView, Cursor paramCursor, int paramInt)
    {
      int i = paramCursor.getInt(paramCursor.getColumnIndex("state"));
      switch (paramView.getId())
      {
      case 2131230742: 
      case 2131230743: 
      case 2131230744: 
      case 2131230745: 
      case 2131230746: 
      default: 
      case 2131230741: 
      case 2131230747: 
      case 2131230748: 
        for (;;)
        {
          return true;
          String str5 = paramCursor.getString(paramInt);
          try
          {
            CellCollection localCellCollection2 = CellCollection.deserialize(str5);
            localCellCollection1 = localCellCollection2;
          }
          catch (Exception localException)
          {
            for (;;)
            {
              SudokuBoardView localSudokuBoardView;
              long l4 = paramCursor.getLong(paramCursor.getColumnIndex("_id"));
              Object[] arrayOfObject3 = new Object[1];
              arrayOfObject3[0] = Long.valueOf(l4);
              Log.e("SudokuListActivity", String.format("Exception occurred when deserializing puzzle with id %s.", arrayOfObject3), localException);
              CellCollection localCellCollection1 = null;
            }
          }
          localSudokuBoardView = (SudokuBoardView)paramView;
          localSudokuBoardView.setReadOnly(true);
          localSudokuBoardView.setFocusable(false);
          ((SudokuBoardView)paramView).setCells(localCellCollection1);
          continue;
          TextView localTextView5 = (TextView)paramView;
          Object localObject = null;
          switch (i)
          {
          case 1: 
          default: 
            if (localObject != null) {
              break;
            }
          }
          for (int i1 = 8;; i1 = 0)
          {
            localTextView5.setVisibility(i1);
            localTextView5.setText((CharSequence)localObject);
            if (i != 2) {
              break label307;
            }
            localTextView5.setTextColor(Color.rgb(187, 187, 187));
            break;
            localObject = this.mContext.getString(2131296288);
            break label224;
            localObject = this.mContext.getString(2131296289);
            break label224;
          }
          localTextView5.setTextColor(Color.rgb(255, 255, 255));
          continue;
          long l3 = paramCursor.getLong(paramInt);
          TextView localTextView4 = (TextView)paramView;
          boolean bool3 = l3 < 0L;
          String str4 = null;
          if (bool3) {
            str4 = this.mGameTimeFormatter.format(l3);
          }
          if (str4 == null) {}
          for (int n = 8;; n = 0)
          {
            localTextView4.setVisibility(n);
            localTextView4.setText(str4);
            if (i != 2) {
              break label422;
            }
            localTextView4.setTextColor(Color.rgb(187, 187, 187));
            break;
          }
          localTextView4.setTextColor(Color.rgb(255, 255, 255));
        }
      case 2131230749: 
        long l2 = paramCursor.getLong(paramInt);
        TextView localTextView3 = (TextView)paramView;
        boolean bool2 = l2 < 0L;
        String str3 = null;
        if (bool2)
        {
          Context localContext2 = this.mContext;
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = getDateAndTimeForHumans(l2);
          str3 = localContext2.getString(2131296291, arrayOfObject2);
        }
        if (str3 == null) {}
        for (int m = 8;; m = 0)
        {
          localTextView3.setVisibility(m);
          localTextView3.setText(str3);
          break;
        }
      case 2131230750: 
        label224:
        label307:
        label422:
        long l1 = paramCursor.getLong(paramInt);
        TextView localTextView2 = (TextView)paramView;
        boolean bool1 = l1 < 0L;
        String str2 = null;
        if (bool1)
        {
          Context localContext1 = this.mContext;
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = getDateAndTimeForHumans(l1);
          str2 = localContext1.getString(2131296292, arrayOfObject1);
        }
        if (str2 == null) {}
        for (int k = 4;; k = 0)
        {
          localTextView2.setVisibility(k);
          localTextView2.setText(str2);
          break;
        }
      }
      String str1 = paramCursor.getString(paramInt);
      TextView localTextView1 = (TextView)paramView;
      if ((str1 == null) || (str1.trim() == ""))
      {
        ((TextView)paramView).setVisibility(8);
        label668:
        if ((str1 != null) && (!str1.trim().equals(""))) {
          break label719;
        }
      }
      label719:
      for (int j = 8;; j = 0)
      {
        localTextView1.setVisibility(j);
        localTextView1.setText(str1);
        break;
        ((TextView)paramView).setText(str1);
        break label668;
      }
    }
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\SudokuListActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */