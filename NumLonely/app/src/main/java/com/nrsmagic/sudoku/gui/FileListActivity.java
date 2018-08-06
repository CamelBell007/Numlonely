package com.nrsmagic.sudoku.gui;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileListActivity
  extends ListActivity
{
  private static final int DIALOG_IMPORT_FILE = 0;
  public static final String EXTRA_FOLDER_NAME = "FOLDER_NAME";
  public static final String ITEM_KEY_DETAIL = "detail";
  public static final String ITEM_KEY_FILE = "file";
  public static final String ITEM_KEY_NAME = "name";
  private Context mContext = this;
  private String mDirectory;
  private List<Map<String, Object>> mList;
  private File mSelectedFile;
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903041);
    setDefaultKeyMode(2);
    getListView().setOnCreateContextMenuListener(this);
    String str = getIntent().getStringExtra("FOLDER_NAME");
    File localFile1 = new File(str);
    File[] arrayOfFile1 = localFile1.listFiles(new FileFilter()
    {
      public boolean accept(File paramAnonymousFile)
      {
        return (paramAnonymousFile.isDirectory()) && (!paramAnonymousFile.isHidden()) && (paramAnonymousFile.canRead());
      }
    });
    File[] arrayOfFile2 = localFile1.listFiles(new FileFilter()
    {
      public boolean accept(File paramAnonymousFile)
      {
        return (paramAnonymousFile.isFile()) && (!paramAnonymousFile.isHidden()) && (paramAnonymousFile.canRead()) && ((paramAnonymousFile.getName().endsWith(".opensudoku")) || (paramAnonymousFile.getName().endsWith(".sdm")));
      }
    });
    Arrays.sort(arrayOfFile1);
    Arrays.sort(arrayOfFile2);
    this.mList = new ArrayList();
    if (localFile1.getParentFile() != null)
    {
      HashMap localHashMap1 = new HashMap();
      localHashMap1.put("file", localFile1.getParentFile());
      localHashMap1.put("name", "..");
      this.mList.add(localHashMap1);
    }
    int i = arrayOfFile1.length;
    int j = 0;
    java.text.DateFormat localDateFormat1;
    java.text.DateFormat localDateFormat2;
    int k;
    if (j >= i)
    {
      localDateFormat1 = android.text.format.DateFormat.getDateFormat(this);
      localDateFormat2 = android.text.format.DateFormat.getTimeFormat(this);
      k = arrayOfFile2.length;
    }
    for (int m = 0;; m++)
    {
      if (m >= k)
      {
        String[] arrayOfString = { "name", "detail" };
        int[] arrayOfInt = { 2131230722, 2131230723 };
        SimpleAdapter localSimpleAdapter = new SimpleAdapter(this, this.mList, 2130903044, arrayOfString, arrayOfInt);
        localSimpleAdapter.setViewBinder(new FileListViewBinder(null));
        setListAdapter(localSimpleAdapter);
        return;
        File localFile2 = arrayOfFile1[j];
        HashMap localHashMap2 = new HashMap();
        localHashMap2.put("file", localFile2);
        localHashMap2.put("name", localFile2.getName());
        this.mList.add(localHashMap2);
        j++;
        break;
      }
      File localFile3 = arrayOfFile2[m];
      HashMap localHashMap3 = new HashMap();
      localHashMap3.put("file", localFile3);
      localHashMap3.put("name", localFile3.getName());
      Date localDate = new Date(localFile3.lastModified());
      localHashMap3.put("detail", localDateFormat1.format(localDate) + " " + localDateFormat2.format(localDate));
      this.mList.add(localHashMap3);
    }
  }
  
  protected Dialog onCreateDialog(int paramInt)
  {
    LayoutInflater.from(this);
    switch (paramInt)
    {
    default: 
      return null;
    }
    new AlertDialog.Builder(this).setIcon(17301589).setTitle(2131296273).setPositiveButton(2131296273, new OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        File localFile = FileListActivity.this.mSelectedFile;
        Intent localIntent = new Intent(FileListActivity.this.mContext, ImportSudokuActivity.class);
        localIntent.setData(Uri.fromFile(localFile));
        FileListActivity.this.startActivity(localIntent);
      }
    }).setNegativeButton(17039360, null).create();
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
  }
  
  protected void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong)
  {
    File localFile = (File)((Map)this.mList.get((int)paramLong)).get("file");
    if (localFile.isFile())
    {
      this.mSelectedFile = localFile;
      showDialog(0);
    }
    while (!localFile.isDirectory()) {
      return;
    }
    Intent localIntent = new Intent();
    localIntent.setClass(this, FileListActivity.class);
    localIntent.putExtra("FOLDER_NAME", localFile.getAbsolutePath());
    startActivity(localIntent);
  }
  
  protected void onPrepareDialog(int paramInt, Dialog paramDialog)
  {
    super.onPrepareDialog(paramInt, paramDialog);
    switch (paramInt)
    {
    default: 
      return;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mSelectedFile.getName();
    paramDialog.setTitle(getString(2131296274, arrayOfObject));
  }
  
  protected void onRestoreInstanceState(Bundle paramBundle)
  {
    super.onRestoreInstanceState(paramBundle);
    this.mDirectory = paramBundle.getString("mDirectory");
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putString("mDirectory", this.mDirectory);
  }
  
  protected void onStart()
  {
    super.onStart();
  }
  
  private static class FileListViewBinder
    implements ViewBinder
  {
    public boolean setViewValue(View paramView, Object paramObject, String paramString)
    {
      switch (paramView.getId())
      {
      }
      do
      {
        return false;
      } while (paramObject != null);
      ((TextView)paramView).setVisibility(4);
      return true;
    }
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\FileListActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */