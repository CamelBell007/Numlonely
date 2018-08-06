package com.nrsmagic.sudoku.gui;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.nrsmagic.sudoku.R;

public class SeekBarPreference extends DialogPreference
{
  private int mMax;
  private int mMin;
  private OnSeekBarChangeListener mOnSeekBarChangeListener = new OnSeekBarChangeListener()
  {
    public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
    {
      SeekBarPreference.this.updateValueLabel(paramAnonymousInt);
    }
    
    public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {}
    
    public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar) {}
  };
  private SeekBar mSeekBar;
  private int mValue;
  private String mValueFormat;
  private TextView mValueLabel;
  
  public SeekBarPreference(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public SeekBarPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setDialogLayoutResource(2130903052);
    this.mSeekBar = new SeekBar(paramContext, paramAttributeSet);
    this.mSeekBar.setId(2131230720);
    this.mSeekBar.setOnSeekBarChangeListener(this.mOnSeekBarChangeListener);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.SeekBarPreference);
    setMin(localTypedArray.getInt(0, this.mMin));
    setMax(localTypedArray.getInt(1, this.mMax));
    setValue(localTypedArray.getInt(2, this.mValue));
    setValueFormat(localTypedArray.getString(3));
    localTypedArray.recycle();
  }
  
  private void updateValueLabel(int paramInt)
  {
    int i;
    if (this.mValueLabel != null)
    {
      i = paramInt + this.mMin;
      if ((this.mValueFormat != null) && (this.mValueFormat != ""))
      {
        TextView localTextView = this.mValueLabel;
        String str = this.mValueFormat;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(i);
        localTextView.setText(String.format(str, arrayOfObject));
      }
    }
    else
    {
      return;
    }
    this.mValueLabel.setText(String.valueOf(i));
  }
  
  public int getMax()
  {
    return this.mMax;
  }
  
  public int getMin()
  {
    return this.mMin;
  }
  
  public int getValue()
  {
    return this.mValue;
  }
  
  public String getValueFormat()
  {
    return this.mValueFormat;
  }
  
  protected void onAddSeekBarToDialogView(View paramView, SeekBar paramSeekBar)
  {
    ViewGroup localViewGroup = (ViewGroup)paramView.findViewById(2131230738);
    if (localViewGroup != null) {
      localViewGroup.addView(paramSeekBar, -1, -2);
    }
  }
  
  protected void onBindDialogView(View paramView)
  {
    super.onBindDialogView(paramView);
    this.mValueLabel = ((TextView)paramView.findViewById(2131230739));
    SeekBar localSeekBar = this.mSeekBar;
    localSeekBar.setProgress(getValue() - this.mMin);
    updateValueLabel(localSeekBar.getProgress());
    ViewParent localViewParent = localSeekBar.getParent();
    if (localViewParent != paramView)
    {
      if (localViewParent != null) {
        ((ViewGroup)localViewParent).removeView(localSeekBar);
      }
      onAddSeekBarToDialogView(paramView, localSeekBar);
    }
  }
  
  protected void onDialogClosed(boolean paramBoolean)
  {
    super.onDialogClosed(paramBoolean);
    if (paramBoolean)
    {
      int i = this.mSeekBar.getProgress() + this.mMin;
      if (callChangeListener(Integer.valueOf(i))) {
        setValue(i);
      }
    }
  }
  
  protected Object onGetDefaultValue(TypedArray paramTypedArray, int paramInt)
  {
    return paramTypedArray.getString(paramInt);
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable == null) || (!paramParcelable.getClass().equals(SavedState.class)))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    setValue(localSavedState.value);
  }
  
  protected Parcelable onSaveInstanceState()
  {
    Parcelable localParcelable = super.onSaveInstanceState();
    if (isPersistent()) {
      return localParcelable;
    }
    SavedState localSavedState = new SavedState(localParcelable);
    localSavedState.value = getValue();
    return localSavedState;
  }
  
  protected void onSetInitialValue(boolean paramBoolean, Object paramObject)
  {
    int i = this.mMin;
    if (paramObject != null) {
      i = Integer.parseInt(paramObject.toString());
    }
    if (paramBoolean) {
      i = getPersistedInt(this.mValue);
    }
    setValue(i);
  }
  
  public void setMax(int paramInt)
  {
    this.mMax = paramInt;
    this.mSeekBar.setMax(this.mMax - this.mMin);
    this.mSeekBar.setProgress(this.mMin);
  }
  
  public void setMin(int paramInt)
  {
    this.mMin = paramInt;
    this.mSeekBar.setMax(this.mMax - this.mMin);
    this.mSeekBar.setProgress(this.mMin);
  }
  
  public void setValue(int paramInt)
  {
    boolean bool1 = shouldDisableDependents();
    if (paramInt > this.mMax) {
      this.mValue = this.mMax;
    }
    for (;;)
    {
      persistInt(paramInt);
      boolean bool2 = shouldDisableDependents();
      if (bool2 != bool1) {
        notifyDependencyChange(bool2);
      }
      return;
      if (paramInt < this.mMin) {
        this.mValue = this.mMin;
      } else {
        this.mValue = paramInt;
      }
    }
  }
  
  public void setValueFormat(String paramString)
  {
    this.mValueFormat = paramString;
  }
  
  private static class SavedState
    extends Preference.BaseSavedState
  {
    public static final Creator<SavedState> CREATOR = new Creator()
    {
      public SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new SavedState(paramAnonymousParcel);
      }
      
      public SavedState[] newArray(int paramAnonymousInt)
      {
        return new SavedState[paramAnonymousInt];
      }
    };
    int value;
    
    public SavedState(Parcel paramParcel)
    {
      super();
      this.value = paramParcel.readInt();
    }
    
    public SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.value);
    }
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\SeekBarPreference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */