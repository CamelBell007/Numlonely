package com.nrsmagic.sudoku.gui.inputmethod;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class IMPopupDialog
  extends Dialog
{
  private View.OnClickListener clearButtonListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      if (IMPopupDialog.this.mTabHost.getCurrentTabTag().equals("number"))
      {
        if (IMPopupDialog.this.mOnNumberEditListener != null) {
          IMPopupDialog.this.mOnNumberEditListener.onNumberEdit(0);
        }
        IMPopupDialog.this.dismiss();
      }
      for (;;)
      {
        return;
        Iterator localIterator = IMPopupDialog.this.mNoteNumberButtons.values().iterator();
        while (localIterator.hasNext())
        {
          ToggleButton localToggleButton = (ToggleButton)localIterator.next();
          localToggleButton.setChecked(false);
          IMPopupDialog.this.mNoteSelectedNumbers.remove(localToggleButton.getTag());
        }
      }
    }
  };
  private View.OnClickListener closeButtonListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      if (IMPopupDialog.this.mOnNoteEditListener != null)
      {
        Integer[] arrayOfInteger = new Integer[IMPopupDialog.this.mNoteSelectedNumbers.size()];
        IMPopupDialog.this.mOnNoteEditListener.onNoteEdit((Integer[])IMPopupDialog.this.mNoteSelectedNumbers.toArray(arrayOfInteger));
      }
      IMPopupDialog.this.dismiss();
    }
  };
  private OnCheckedChangeListener editNoteCheckedChangeListener = new OnCheckedChangeListener()
  {
    public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
    {
      Integer localInteger = (Integer)paramAnonymousCompoundButton.getTag();
      if (paramAnonymousBoolean)
      {
        IMPopupDialog.this.mNoteSelectedNumbers.add(localInteger);
        return;
      }
      IMPopupDialog.this.mNoteSelectedNumbers.remove(localInteger);
    }
  };
  private View.OnClickListener editNumberButtonClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      Integer localInteger = (Integer)paramAnonymousView.getTag();
      if (IMPopupDialog.this.mOnNumberEditListener != null) {
        IMPopupDialog.this.mOnNumberEditListener.onNumberEdit(localInteger.intValue());
      }
      IMPopupDialog.this.dismiss();
    }
  };
  private Context mContext;
  private LayoutInflater mInflater;
  private Map<Integer, ToggleButton> mNoteNumberButtons = new HashMap();
  private Set<Integer> mNoteSelectedNumbers = new HashSet();
  private Map<Integer, Button> mNumberButtons = new HashMap();
  private OnNoteEditListener mOnNoteEditListener;
  private OnNumberEditListener mOnNumberEditListener;
  private int mSelectedNumber;
  private TabHost mTabHost;
  
  public IMPopupDialog(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.mInflater = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
    this.mTabHost = createTabView();
    ((TextView)findViewById(16908310)).setVisibility(8);
    setContentView(this.mTabHost);
  }
  
  private View createEditNoteView()
  {
    View localView = this.mInflater.inflate(2130903048, null);
    this.mNoteNumberButtons.put(Integer.valueOf(1), (ToggleButton)localView.findViewById(2131230724));
    this.mNoteNumberButtons.put(Integer.valueOf(2), (ToggleButton)localView.findViewById(2131230725));
    this.mNoteNumberButtons.put(Integer.valueOf(3), (ToggleButton)localView.findViewById(2131230726));
    this.mNoteNumberButtons.put(Integer.valueOf(4), (ToggleButton)localView.findViewById(2131230727));
    this.mNoteNumberButtons.put(Integer.valueOf(5), (ToggleButton)localView.findViewById(2131230728));
    this.mNoteNumberButtons.put(Integer.valueOf(6), (ToggleButton)localView.findViewById(2131230730));
    this.mNoteNumberButtons.put(Integer.valueOf(7), (ToggleButton)localView.findViewById(2131230731));
    this.mNoteNumberButtons.put(Integer.valueOf(8), (ToggleButton)localView.findViewById(2131230732));
    this.mNoteNumberButtons.put(Integer.valueOf(9), (ToggleButton)localView.findViewById(2131230733));
    Iterator localIterator = this.mNoteNumberButtons.keySet().iterator();
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        ((Button)localView.findViewById(2131230736)).setOnClickListener(this.closeButtonListener);
        ((Button)localView.findViewById(2131230734)).setOnClickListener(this.clearButtonListener);
        return localView;
      }
      Integer localInteger = (Integer)localIterator.next();
      ToggleButton localToggleButton = (ToggleButton)this.mNoteNumberButtons.get(localInteger);
      localToggleButton.setTag(localInteger);
      localToggleButton.setOnCheckedChangeListener(this.editNoteCheckedChangeListener);
    }
  }
  
  private View createEditNumberView()
  {
    View localView = this.mInflater.inflate(2130903049, null);
    this.mNumberButtons.put(Integer.valueOf(1), (Button)localView.findViewById(2131230724));
    this.mNumberButtons.put(Integer.valueOf(2), (Button)localView.findViewById(2131230725));
    this.mNumberButtons.put(Integer.valueOf(3), (Button)localView.findViewById(2131230726));
    this.mNumberButtons.put(Integer.valueOf(4), (Button)localView.findViewById(2131230727));
    this.mNumberButtons.put(Integer.valueOf(5), (Button)localView.findViewById(2131230728));
    this.mNumberButtons.put(Integer.valueOf(6), (Button)localView.findViewById(2131230730));
    this.mNumberButtons.put(Integer.valueOf(7), (Button)localView.findViewById(2131230731));
    this.mNumberButtons.put(Integer.valueOf(8), (Button)localView.findViewById(2131230732));
    this.mNumberButtons.put(Integer.valueOf(9), (Button)localView.findViewById(2131230733));
    Iterator localIterator = this.mNumberButtons.keySet().iterator();
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        ((Button)localView.findViewById(2131230736)).setOnClickListener(this.closeButtonListener);
        ((Button)localView.findViewById(2131230734)).setOnClickListener(this.clearButtonListener);
        return localView;
      }
      Integer localInteger = (Integer)localIterator.next();
      Button localButton = (Button)this.mNumberButtons.get(localInteger);
      localButton.setTag(localInteger);
      localButton.setOnClickListener(this.editNumberButtonClickListener);
    }
  }
  
  private TabHost createTabView()
  {
    TabHost localTabHost = new TabHost(this.mContext, null);
    localTabHost.setLayoutParams(new LayoutParams(-2, -2));
    LinearLayout localLinearLayout = new LinearLayout(this.mContext);
    localLinearLayout.setLayoutParams(new LayoutParams(-2, -2));
    localLinearLayout.setOrientation(1);
    TabWidget localTabWidget = new TabWidget(this.mContext);
    localTabWidget.setId(16908307);
    FrameLayout localFrameLayout = new FrameLayout(this.mContext);
    localFrameLayout.setId(16908305);
    localLinearLayout.addView(localTabWidget);
    localLinearLayout.addView(localFrameLayout);
    localTabHost.addView(localLinearLayout);
    localTabHost.setup();
    final View localView1 = createEditNumberView();
    final View localView2 = createEditNoteView();
    localTabHost.addTab(localTabHost.newTabSpec("number").setIndicator(this.mContext.getString(2131296270)).setContent(new TabContentFactory()
    {
      public View createTabContent(String paramAnonymousString)
      {
        return localView1;
      }
    }));
    localTabHost.addTab(localTabHost.newTabSpec("note").setIndicator(this.mContext.getString(2131296271)).setContent(new TabContentFactory()
    {
      public View createTabContent(String paramAnonymousString)
      {
        return localView2;
      }
    }));
    return localTabHost;
  }
  
  public void highlightNumber(int paramInt)
  {
    int i = this.mContext.getResources().getColor(2131165184);
    if (paramInt == this.mSelectedNumber) {
      ((Button)this.mNumberButtons.get(Integer.valueOf(paramInt))).setTextColor(i);
    }
    for (;;)
    {
      ((ToggleButton)this.mNoteNumberButtons.get(Integer.valueOf(paramInt))).setBackgroundResource(2130837516);
      return;
      ((Button)this.mNumberButtons.get(Integer.valueOf(paramInt))).setBackgroundResource(2130837504);
    }
  }
  
  public void resetButtons()
  {
    Iterator localIterator1 = this.mNumberButtons.values().iterator();
    Iterator localIterator2;
    label39:
    Iterator localIterator3;
    if (!localIterator1.hasNext())
    {
      localIterator2 = this.mNoteNumberButtons.values().iterator();
      if (localIterator2.hasNext()) {
        break label91;
      }
      localIterator3 = this.mNoteNumberButtons.entrySet().iterator();
    }
    for (;;)
    {
      if (!localIterator3.hasNext())
      {
        return;
        ((Button)localIterator1.next()).setBackgroundResource(2130837508);
        break;
        label91:
        ((Button)localIterator2.next()).setBackgroundResource(2130837515);
        break label39;
      }
      Entry localEntry = (Entry)localIterator3.next();
      ((ToggleButton)localEntry.getValue()).setText(localEntry.getKey());
    }
  }
  
  public void setOnNoteEditListener(OnNoteEditListener paramOnNoteEditListener)
  {
    this.mOnNoteEditListener = paramOnNoteEditListener;
  }
  
  public void setOnNumberEditListener(OnNumberEditListener paramOnNumberEditListener)
  {
    this.mOnNumberEditListener = paramOnNumberEditListener;
  }
  
  public void setValueCount(int paramInt1, int paramInt2)
  {
    ((Button)this.mNumberButtons.get(Integer.valueOf(paramInt1))).setText(paramInt1 + " (" + paramInt2 + ")");
  }
  
  public void updateNote(Collection<Integer> paramCollection)
  {
    this.mNoteSelectedNumbers = new HashSet();
    Iterator localIterator2;
    Iterator localIterator1;
    if (paramCollection != null)
    {
      localIterator2 = paramCollection.iterator();
      if (localIterator2.hasNext()) {}
    }
    else
    {
      localIterator1 = this.mNoteNumberButtons.keySet().iterator();
    }
    for (;;)
    {
      if (!localIterator1.hasNext())
      {
        return;
        int i = ((Integer)localIterator2.next()).intValue();
        this.mNoteSelectedNumbers.add(Integer.valueOf(i));
        break;
      }
      Integer localInteger = (Integer)localIterator1.next();
      ((ToggleButton)this.mNoteNumberButtons.get(localInteger)).setChecked(this.mNoteSelectedNumbers.contains(localInteger));
    }
  }
  
  public void updateNumber(Integer paramInteger)
  {
    this.mSelectedNumber = paramInteger.intValue();
    LightingColorFilter localLightingColorFilter = new LightingColorFilter(this.mContext.getResources().getColor(2131165185), 0);
    Iterator localIterator = this.mNumberButtons.entrySet().iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return;
      }
      Entry localEntry = (Entry)localIterator.next();
      Button localButton = (Button)localEntry.getValue();
      if (((Integer)localEntry.getKey()).equals(Integer.valueOf(this.mSelectedNumber)))
      {
        localButton.setTextAppearance(this.mContext, 16973891);
        localButton.getBackground().setColorFilter(localLightingColorFilter);
      }
      else
      {
        localButton.setTextAppearance(this.mContext, 16973898);
        localButton.getBackground().setColorFilter(null);
      }
    }
  }
  
  public static abstract interface OnNoteEditListener
  {
    public abstract boolean onNoteEdit(Integer[] paramArrayOfInteger);
  }
  
  public static abstract interface OnNumberEditListener
  {
    public abstract boolean onNumberEdit(int paramInt);
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\inputmethod\IMPopupDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */