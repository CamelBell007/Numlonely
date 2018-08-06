package com.nrsmagic.sudoku.gui.inputmethod;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import java.util.Iterator;
import java.util.List;

public class IMControlPanelStatePersister
{
  private static final String PREFIX = IMControlPanel.class.getName();
  private SharedPreferences mPreferences;
  
  public IMControlPanelStatePersister(Context paramContext)
  {
    this.mPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
  }
  
  public void restoreState(IMControlPanel paramIMControlPanel)
  {
    int i = new StateBundle(this.mPreferences, PREFIX + ".", false).getInt("activeMethodIndex", 0);
    if (i != -1) {
      paramIMControlPanel.activateInputMethod(i);
    }
    Iterator localIterator = paramIMControlPanel.getInputMethods().iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return;
      }
      InputMethod localInputMethod = (InputMethod)localIterator.next();
      localInputMethod.onRestoreState(new StateBundle(this.mPreferences, PREFIX + "." + localInputMethod.getInputMethodName(), false));
    }
  }
  
  public void saveState(IMControlPanel paramIMControlPanel)
  {
    StateBundle localStateBundle1 = new StateBundle(this.mPreferences, PREFIX + ".", true);
    localStateBundle1.putInt("activeMethodIndex", paramIMControlPanel.getActiveMethodIndex());
    localStateBundle1.commit();
    Iterator localIterator = paramIMControlPanel.getInputMethods().iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return;
      }
      InputMethod localInputMethod = (InputMethod)localIterator.next();
      StateBundle localStateBundle2 = new StateBundle(this.mPreferences, PREFIX + "." + localInputMethod.getInputMethodName(), true);
      localInputMethod.onSaveState(localStateBundle2);
      localStateBundle2.commit();
    }
  }
  
  public static class StateBundle
  {
    private final boolean mEditable;
    private final Editor mPrefEditor;
    private final SharedPreferences mPreferences;
    private final String mPrefix;
    
    public StateBundle(SharedPreferences paramSharedPreferences, String paramString, boolean paramBoolean)
    {
      this.mPreferences = paramSharedPreferences;
      this.mPrefix = paramString;
      this.mEditable = paramBoolean;
      if (this.mEditable)
      {
        this.mPrefEditor = paramSharedPreferences.edit();
        return;
      }
      this.mPrefEditor = null;
    }
    
    public void commit()
    {
      if (!this.mEditable) {
        throw new IllegalStateException("StateBundle is not editable");
      }
      this.mPrefEditor.commit();
    }
    
    public boolean getBoolean(String paramString, boolean paramBoolean)
    {
      return this.mPreferences.getBoolean(this.mPrefix + paramString, paramBoolean);
    }
    
    public float getFloat(String paramString, float paramFloat)
    {
      return this.mPreferences.getFloat(this.mPrefix + paramString, paramFloat);
    }
    
    public int getInt(String paramString, int paramInt)
    {
      return this.mPreferences.getInt(this.mPrefix + paramString, paramInt);
    }
    
    public String getString(String paramString1, String paramString2)
    {
      return this.mPreferences.getString(this.mPrefix + paramString1, paramString2);
    }
    
    public void putBoolean(String paramString, boolean paramBoolean)
    {
      if (!this.mEditable) {
        throw new IllegalStateException("StateBundle is not editable");
      }
      this.mPrefEditor.putBoolean(this.mPrefix + paramString, paramBoolean);
    }
    
    public void putFloat(String paramString, float paramFloat)
    {
      if (!this.mEditable) {
        throw new IllegalStateException("StateBundle is not editable");
      }
      this.mPrefEditor.putFloat(this.mPrefix + paramString, paramFloat);
    }
    
    public void putInt(String paramString, int paramInt)
    {
      if (!this.mEditable) {
        throw new IllegalStateException("StateBundle is not editable");
      }
      this.mPrefEditor.putInt(this.mPrefix + paramString, paramInt);
    }
    
    public void putString(String paramString1, String paramString2)
    {
      if (!this.mEditable) {
        throw new IllegalStateException("StateBundle is not editable");
      }
      this.mPrefEditor.putString(this.mPrefix + paramString1, paramString2);
    }
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\inputmethod\IMControlPanelStatePersister.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */