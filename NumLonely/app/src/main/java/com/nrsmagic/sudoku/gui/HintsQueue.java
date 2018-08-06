package com.nrsmagic.sudoku.gui;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import java.util.LinkedList;
import java.util.Queue;

public class HintsQueue
{
  private static final String PREF_FILE_NAME = "hints";
  private Context mContext;
  private OnClickListener mHintClosed = new OnClickListener()
  {
    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {}
  };
  private AlertDialog mHintDialog;
  private Queue<Message> mMessages;
  private boolean mOneTimeHintsEnabled;
  private SharedPreferences mPrefs;
  
  public HintsQueue(Context paramContext)
  {
    this.mContext = paramContext;
    this.mPrefs = this.mContext.getSharedPreferences("hints", 0);
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    localSharedPreferences.registerOnSharedPreferenceChangeListener(new OnSharedPreferenceChangeListener()
    {
      public void onSharedPreferenceChanged(SharedPreferences paramAnonymousSharedPreferences, String paramAnonymousString)
      {
        if (paramAnonymousString.equals("show_hints")) {
          HintsQueue.this.mOneTimeHintsEnabled = paramAnonymousSharedPreferences.getBoolean("show_hints", true);
        }
      }
    });
    this.mOneTimeHintsEnabled = localSharedPreferences.getBoolean("show_hints", true);
    this.mHintDialog = new Builder(paramContext).setIcon(17301569).setTitle(2131296333).setMessage("").setPositiveButton(2131296264, this.mHintClosed).create();
    this.mHintDialog.setOnDismissListener(new OnDismissListener()
    {
      public void onDismiss(DialogInterface paramAnonymousDialogInterface)
      {
        HintsQueue.this.processQueue();
      }
    });
    this.mMessages = new LinkedList();
  }
  
  private void addHint(Message paramMessage)
  {
    synchronized (this.mMessages)
    {
      this.mMessages.add(paramMessage);
    }
    synchronized (this.mHintDialog)
    {
      if (!this.mHintDialog.isShowing()) {
        processQueue();
      }
      return;
      localObject1 = finally;
      throw ((Throwable)localObject1);
    }
  }
  
  private void processQueue()
  {
    synchronized (this.mMessages)
    {
      Message localMessage = (Message)this.mMessages.poll();
      if (localMessage != null) {
        showHintDialog(localMessage);
      }
      return;
    }
  }
  
  private void showHintDialog(Message paramMessage)
  {
    synchronized (this.mHintDialog)
    {
      this.mHintDialog.setTitle(this.mContext.getString(paramMessage.titleResID));
      this.mHintDialog.setMessage(this.mContext.getText(paramMessage.messageResID));
      this.mHintDialog.show();
      return;
    }
  }
  
  public boolean legacyHintsWereDisplayed()
  {
    boolean bool1 = this.mPrefs.getBoolean("hint_2131099727", false);
    boolean bool2 = false;
    if (bool1)
    {
      boolean bool3 = this.mPrefs.getBoolean("hint_2131099730", false);
      bool2 = false;
      if (bool3)
      {
        boolean bool4 = this.mPrefs.getBoolean("hint_2131099726", false);
        bool2 = false;
        if (bool4)
        {
          boolean bool5 = this.mPrefs.getBoolean("hint_2131099729", false);
          bool2 = false;
          if (bool5)
          {
            boolean bool6 = this.mPrefs.getBoolean("hint_2131099728", false);
            bool2 = false;
            if (bool6) {
              bool2 = true;
            }
          }
        }
      }
    }
    return bool2;
  }
  
  public void pause()
  {
    if (this.mHintDialog != null) {
      this.mHintDialog.cancel();
    }
  }
  
  public void resetOneTimeHints()
  {
    Editor localEditor = this.mPrefs.edit();
    localEditor.clear();
    localEditor.commit();
  }
  
  public void showHint(int paramInt1, int paramInt2, Object... paramVarArgs)
  {
    Message localMessage = new Message(null);
    localMessage.titleResID = paramInt1;
    localMessage.messageResID = paramInt2;
    addHint(localMessage);
  }
  
  public void showOneTimeHint(String paramString, int paramInt1, int paramInt2, Object... paramVarArgs)
  {
    if ((!this.mOneTimeHintsEnabled) || (legacyHintsWereDisplayed())) {}
    String str;
    do
    {
      return;
      str = "hint_" + paramString;
    } while (this.mPrefs.getBoolean(str, false));
    showHint(paramInt1, paramInt2, paramVarArgs);
    Editor localEditor = this.mPrefs.edit();
    localEditor.putBoolean(str, true);
    localEditor.commit();
  }
  
  private static class Message
  {
    int messageResID;
    int titleResID;
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\HintsQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */