package com.nrsmagic.sudoku.gui;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;
import com.nrsmagic.sudoku.db.SudokuDatabase;
import com.nrsmagic.sudoku.game.FolderInfo;
import com.nrsmagic.sudoku.utils.AndroidUtils;

public class FolderListActivity
  extends ListActivity
{
  private static final int DIALOG_ABOUT = 0;
  private static final int DIALOG_ADD_FOLDER = 1;
  private static final int DIALOG_DELETE_FOLDER = 3;
  private static final int DIALOG_RENAME_FOLDER = 2;
  public static final int MENU_ITEM_ABOUT = 4;
  public static final int MENU_ITEM_ADD = 1;
  public static final int MENU_ITEM_DELETE = 3;
  public static final int MENU_ITEM_EXPORT = 5;
  public static final int MENU_ITEM_EXPORT_ALL = 6;
  public static final int MENU_ITEM_IMPORT = 7;
  public static final int MENU_ITEM_RENAME = 2;
  private static final String TAG = "FolderListActivity";
  private TextView mAddFolderNameInput;
  private Cursor mCursor;
  private SudokuDatabase mDatabase;
  private long mDeleteFolderID;
  private FolderListViewBinder mFolderListBinder;
  private long mRenameFolderID;
  private TextView mRenameFolderNameInput;
  
  private void updateList()
  {
    this.mCursor.requery();
  }
  
  public boolean onContextItemSelected(MenuItem paramMenuItem)
  {
    AdapterView.AdapterContextMenuInfo localAdapterContextMenuInfo;
    try
    {
      localAdapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)paramMenuItem.getMenuInfo();
      switch (paramMenuItem.getItemId())
      {
      case 4: 
      default: 
        return false;
      }
    }
    catch (ClassCastException localClassCastException)
    {
      Log.e("FolderListActivity", "bad menuInfo", localClassCastException);
      return false;
    }
    Intent localIntent = new Intent();
    localIntent.setClass(this, SudokuExportActivity.class);
    localIntent.putExtra("FOLDER_ID", localAdapterContextMenuInfo.id);
    startActivity(localIntent);
    return true;
    this.mRenameFolderID = localAdapterContextMenuInfo.id;
    showDialog(2);
    return true;
    this.mDeleteFolderID = localAdapterContextMenuInfo.id;
    showDialog(3);
    return true;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903043);
    setDefaultKeyMode(2);
    getListView().setOnCreateContextMenuListener(this);
    this.mDatabase = new SudokuDatabase(getApplicationContext());
    this.mCursor = this.mDatabase.getFolderList();
    startManagingCursor(this.mCursor);
    SimpleCursorAdapter localSimpleCursorAdapter = new SimpleCursorAdapter(this, 2130903044, this.mCursor, new String[] { "name", "_id" }, new int[] { 2131230722, 2131230723 });
    this.mFolderListBinder = new FolderListViewBinder(this);
    localSimpleCursorAdapter.setViewBinder(this.mFolderListBinder);
    setListAdapter(localSimpleCursorAdapter);
  }
  
  public void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenuInfo paramContextMenuInfo)
  {
    Cursor localCursor;
    try
    {
      AdapterView.AdapterContextMenuInfo localAdapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)paramContextMenuInfo;
      localCursor = (Cursor)getListAdapter().getItem(localAdapterContextMenuInfo.position);
      if (localCursor == null) {
        return;
      }
    }
    catch (ClassCastException localClassCastException)
    {
      Log.e("FolderListActivity", "bad menuInfo", localClassCastException);
      return;
    }
    paramContextMenu.setHeaderTitle(localCursor.getString(localCursor.getColumnIndex("name")));
    paramContextMenu.add(0, 5, 0, 2131296361);
    paramContextMenu.add(0, 2, 1, 2131296276);
    paramContextMenu.add(0, 3, 2, 2131296277);
  }
  
  protected Dialog onCreateDialog(int paramInt)
  {
    LayoutInflater localLayoutInflater = LayoutInflater.from(this);
    switch (paramInt)
    {
    default: 
      return null;
    case 0: 
      View localView3 = localLayoutInflater.inflate(2130903040, null);
      ((TextView)localView3.findViewById(2131230721)).setText(getString(2131296259, new Object[] { AndroidUtils.getAppVersionName(getApplicationContext()) }));
      return new AlertDialog.Builder(this).setIcon(2130837519).setTitle(2131296256).setView(localView3).setPositiveButton("OK", null).create();
    case 1: 
      View localView2 = localLayoutInflater.inflate(2130903045, null);
      this.mAddFolderNameInput = ((TextView)localView2.findViewById(2131230722));
      new AlertDialog.Builder(this).setIcon(17301555).setTitle(2131296272).setView(localView2).setPositiveButton(2131296266, new OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          FolderListActivity.this.mDatabase.insertFolder(FolderListActivity.this.mAddFolderNameInput.getText().toString().trim(), Long.valueOf(System.currentTimeMillis()));
          FolderListActivity.this.updateList();
        }
      }).setNegativeButton(17039360, null).create();
    case 2: 
      View localView1 = localLayoutInflater.inflate(2130903045, null);
      this.mRenameFolderNameInput = ((TextView)localView1.findViewById(2131230722));
      new AlertDialog.Builder(this).setIcon(17301566).setTitle(2131296278).setView(localView1).setPositiveButton(2131296266, new OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          FolderListActivity.this.mDatabase.updateFolder(FolderListActivity.this.mRenameFolderID, FolderListActivity.this.mRenameFolderNameInput.getText().toString().trim());
          FolderListActivity.this.updateList();
        }
      }).setNegativeButton(17039360, null).create();
    }
    new AlertDialog.Builder(this).setIcon(17301533).setTitle(2131296279).setMessage(2131296280).setPositiveButton(17039379, new OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        FolderListActivity.this.mDatabase.deleteFolder(FolderListActivity.this.mDeleteFolderID);
        FolderListActivity.this.updateList();
      }
    }).setNegativeButton(17039369, null).create();
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    super.onCreateOptionsMenu(paramMenu);
    return true;
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    this.mDatabase.close();
    this.mFolderListBinder.destroy();
  }
  
  protected void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong)
  {
    Intent localIntent = new Intent(this, SudokuListActivity.class);
    localIntent.putExtra("folder_id", paramLong);
    startActivity(localIntent);
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    case 2: 
    case 3: 
    case 5: 
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    case 1: 
      showDialog(1);
      return true;
    case 7: 
      Intent localIntent2 = new Intent();
      localIntent2.setClass(this, FileListActivity.class);
      localIntent2.putExtra("FOLDER_NAME", "/sdcard");
      startActivity(localIntent2);
      return true;
    case 6: 
      Intent localIntent1 = new Intent();
      localIntent1.setClass(this, SudokuExportActivity.class);
      localIntent1.putExtra("FOLDER_ID", -1L);
      startActivity(localIntent1);
      return true;
    }
    showDialog(0);
    return true;
  }
  
  protected void onPrepareDialog(int paramInt, Dialog paramDialog)
  {
    super.onPrepareDialog(paramInt, paramDialog);
    switch (paramInt)
    {
    case 1: 
    default: 
      return;
    case 2: 
      FolderInfo localFolderInfo2 = this.mDatabase.getFolderInfo(this.mRenameFolderID);
      if (localFolderInfo2 != null) {}
      for (String str2 = localFolderInfo2.name;; str2 = "")
      {
        paramDialog.setTitle(getString(2131296278, new Object[] { str2 }));
        this.mRenameFolderNameInput.setText(str2);
        return;
      }
    }
    FolderInfo localFolderInfo1 = this.mDatabase.getFolderInfo(this.mDeleteFolderID);
    if (localFolderInfo1 != null) {}
    for (String str1 = localFolderInfo1.name;; str1 = "")
    {
      paramDialog.setTitle(getString(2131296279, new Object[] { str1 }));
      return;
    }
  }
  
  protected void onRestoreInstanceState(Bundle paramBundle)
  {
    super.onRestoreInstanceState(paramBundle);
    this.mRenameFolderID = paramBundle.getLong("mRenameFolderID");
    this.mDeleteFolderID = paramBundle.getLong("mDeleteFolderID");
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putLong("mRenameFolderID", this.mRenameFolderID);
    paramBundle.putLong("mDeleteFolderID", this.mDeleteFolderID);
  }
  
  protected void onStart()
  {
    super.onStart();
    updateList();
  }
  
  private static class FolderListViewBinder
    implements ViewBinder
  {
    private Context mContext;
    private FolderDetailLoader mDetailLoader;
    
    public FolderListViewBinder(Context paramContext)
    {
      this.mContext = paramContext;
      this.mDetailLoader = new FolderDetailLoader(paramContext);
    }
    
    public void destroy()
    {
      this.mDetailLoader.destroy();
    }
    
    public boolean setViewValue(View paramView, Cursor paramCursor, int paramInt)
    {
      switch (paramView.getId())
      {
      }
      for (;;)
      {
        return true;
        ((TextView)paramView).setText(paramCursor.getString(paramInt));
        continue;
        long l = paramCursor.getLong(paramInt);
        final TextView localTextView = (TextView)paramView;
        localTextView.setText(this.mContext.getString(2131296354));
        this.mDetailLoader.loadDetailAsync(l, new FolderDetailLoader.FolderDetailCallback()
        {
          public void onLoaded(FolderInfo paramAnonymousFolderInfo)
          {
            if (paramAnonymousFolderInfo != null) {
              localTextView.setText(paramAnonymousFolderInfo.getDetail(FolderListViewBinder.this.mContext));
            }
          }
        });
      }
    }
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\FolderListActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */