package com.nrsmagic.sudoku.gui.inputmethod;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import com.nrsmagic.sudoku.game.Cell;
import com.nrsmagic.sudoku.game.CellCollection;
import com.nrsmagic.sudoku.game.CellCollection.OnChangeListener;
import com.nrsmagic.sudoku.game.CellNote;
import com.nrsmagic.sudoku.game.SudokuGame;
import com.nrsmagic.sudoku.gui.HintsQueue;
import com.nrsmagic.sudoku.gui.SudokuBoardView;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class IMSingleNumber
  extends InputMethod
{
  private static final int MODE_EDIT_NOTE = 1;
  private static final int MODE_EDIT_VALUE;
  private int mEditMode = 0;
  private Handler mGuiHandler = new Handler();
  private boolean mHighlightCompletedValues = true;
  private OnClickListener mNumberButtonClicked = new OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      IMSingleNumber.this.mSelectedNumber = ((Integer)paramAnonymousView.getTag()).intValue();
      IMSingleNumber.this.update();
    }
  };
  private Map<Integer, Button> mNumberButtons;
  private CellCollection.OnChangeListener mOnCellsChangeListener = new CellCollection.OnChangeListener()
  {
    public void onChange()
    {
      if (IMSingleNumber.this.mActive) {
        IMSingleNumber.this.update();
      }
    }
  };
  private int mSelectedNumber = 1;
  private boolean mShowNumberTotals = false;
  private ImageButton mSwitchNumNoteButton;
  
  private void update()
  {
    switch (this.mEditMode)
    {
    }
    for (;;)
    {
      this.mGuiHandler.postDelayed(new Runnable()
      {
        public void run()
        {
          Iterator localIterator1 = IMSingleNumber.this.mNumberButtons.values().iterator();
          int i;
          Iterator localIterator3;
          label110:
          Iterator localIterator2;
          if (!localIterator1.hasNext())
          {
            Map localMap;
            if (!IMSingleNumber.this.mHighlightCompletedValues)
            {
              boolean bool = IMSingleNumber.this.mShowNumberTotals;
              localMap = null;
              if (!bool) {}
            }
            else
            {
              localMap = IMSingleNumber.this.mGame.getCells().getValuesUseCount();
            }
            if (IMSingleNumber.this.mHighlightCompletedValues)
            {
              i = IMSingleNumber.this.mContext.getResources().getColor(2131165184);
              localIterator3 = localMap.entrySet().iterator();
              if (localIterator3.hasNext()) {
                break label263;
              }
            }
            if (IMSingleNumber.this.mShowNumberTotals) {
              localIterator2 = localMap.entrySet().iterator();
            }
          }
          for (;;)
          {
            if (!localIterator2.hasNext())
            {
              return;
              Button localButton1 = (Button)localIterator1.next();
              localButton1.setBackgroundResource(2130837508);
              if (localButton1.getTag().equals(Integer.valueOf(IMSingleNumber.this.mSelectedNumber)))
              {
                localButton1.setTextAppearance(IMSingleNumber.this.mContext, 16973891);
                LightingColorFilter localLightingColorFilter = new LightingColorFilter(IMSingleNumber.this.mContext.getResources().getColor(2131165185), 0);
                localButton1.getBackground().setColorFilter(localLightingColorFilter);
                break;
              }
              localButton1.setTextAppearance(IMSingleNumber.this.mContext, 16973898);
              localButton1.getBackground().setColorFilter(null);
              break;
              label263:
              Entry localEntry2 = (Entry)localIterator3.next();
              if (((Integer)localEntry2.getValue()).intValue() >= 9) {}
              Button localButton3;
              for (int j = 1;; j = 0)
              {
                if (j == 0) {
                  break label360;
                }
                localButton3 = (Button)IMSingleNumber.this.mNumberButtons.get(localEntry2.getKey());
                if (!localButton3.getTag().equals(Integer.valueOf(IMSingleNumber.this.mSelectedNumber))) {
                  break label362;
                }
                localButton3.setTextColor(i);
                break;
              }
              label360:
              break label110;
              label362:
              localButton3.setBackgroundResource(2130837504);
              break label110;
            }
            Entry localEntry1 = (Entry)localIterator2.next();
            Button localButton2 = (Button)IMSingleNumber.this.mNumberButtons.get(localEntry1.getKey());
            if (!localButton2.getTag().equals(Integer.valueOf(IMSingleNumber.this.mSelectedNumber))) {
              localButton2.setText(localEntry1.getKey() + " (" + localEntry1.getValue() + ")");
            } else {
              localButton2.setText(localEntry1.getKey());
            }
          }
        }
      }, 100L);
      return;
      this.mSwitchNumNoteButton.setImageResource(2130837520);
      continue;
      this.mSwitchNumNoteButton.setImageResource(2130837521);
    }
  }
  
  protected View createControlPanelView()
  {
    View localView = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903050, null);
    this.mNumberButtons = new HashMap();
    this.mNumberButtons.put(Integer.valueOf(1), (Button)localView.findViewById(2131230724));
    this.mNumberButtons.put(Integer.valueOf(2), (Button)localView.findViewById(2131230725));
    this.mNumberButtons.put(Integer.valueOf(3), (Button)localView.findViewById(2131230726));
    this.mNumberButtons.put(Integer.valueOf(4), (Button)localView.findViewById(2131230727));
    this.mNumberButtons.put(Integer.valueOf(5), (Button)localView.findViewById(2131230728));
    this.mNumberButtons.put(Integer.valueOf(6), (Button)localView.findViewById(2131230730));
    this.mNumberButtons.put(Integer.valueOf(7), (Button)localView.findViewById(2131230731));
    this.mNumberButtons.put(Integer.valueOf(8), (Button)localView.findViewById(2131230732));
    this.mNumberButtons.put(Integer.valueOf(9), (Button)localView.findViewById(2131230733));
    this.mNumberButtons.put(Integer.valueOf(0), (Button)localView.findViewById(2131230734));
    Iterator localIterator = this.mNumberButtons.keySet().iterator();
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        this.mSwitchNumNoteButton = ((ImageButton)localView.findViewById(2131230729));
        this.mSwitchNumNoteButton.setOnClickListener(new OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            IMSingleNumber localIMSingleNumber = IMSingleNumber.this;
            if (IMSingleNumber.this.mEditMode == 0) {}
            for (int i = 1;; i = 0)
            {
              localIMSingleNumber.mEditMode = i;
              IMSingleNumber.this.update();
              return;
            }
          }
        });
        return localView;
      }
      Integer localInteger = (Integer)localIterator.next();
      Button localButton = (Button)this.mNumberButtons.get(localInteger);
      localButton.setTag(localInteger);
      localButton.setOnClickListener(this.mNumberButtonClicked);
    }
  }
  
  public String getAbbrName()
  {
    return this.mContext.getString(2131296331);
  }
  
  public int getHelpResID()
  {
    return 2131296338;
  }
  
  public boolean getHighlightCompletedValues()
  {
    return this.mHighlightCompletedValues;
  }
  
  public int getNameResID()
  {
    return 2131296323;
  }
  
  public boolean getShowNumberTotals()
  {
    return this.mShowNumberTotals;
  }
  
  protected void initialize(Context paramContext, IMControlPanel paramIMControlPanel, SudokuGame paramSudokuGame, SudokuBoardView paramSudokuBoardView, HintsQueue paramHintsQueue)
  {
    super.initialize(paramContext, paramIMControlPanel, paramSudokuGame, paramSudokuBoardView, paramHintsQueue);
    paramSudokuGame.getCells().addOnChangeListener(this.mOnCellsChangeListener);
  }
  
  protected void onActivated()
  {
    update();
  }
  
  protected void onCellTapped(Cell paramCell)
  {
    int i = this.mSelectedNumber;
    switch (this.mEditMode)
    {
    }
    do
    {
      do
      {
        do
        {
          return;
          if (i == 0)
          {
            this.mGame.setCellNote(paramCell, CellNote.EMPTY);
            return;
          }
        } while ((i <= 0) || (i > 9));
        this.mGame.setCellNote(paramCell, paramCell.getNote().toggleNumber(i));
        return;
      } while ((i < 0) || (i > 9));
      if (((Button)this.mNumberButtons.get(Integer.valueOf(i))).isEnabled()) {
        break;
      }
    } while (i != paramCell.getValue());
    this.mGame.setCellValue(paramCell, 0);
    return;
    if (i == paramCell.getValue()) {
      i = 0;
    }
    this.mGame.setCellValue(paramCell, i);
  }
  
  protected void onRestoreState(IMControlPanelStatePersister.StateBundle paramStateBundle)
  {
    this.mSelectedNumber = paramStateBundle.getInt("selectedNumber", 1);
    this.mEditMode = paramStateBundle.getInt("editMode", 0);
    if (isInputMethodViewCreated()) {
      update();
    }
  }
  
  protected void onSaveState(IMControlPanelStatePersister.StateBundle paramStateBundle)
  {
    paramStateBundle.putInt("selectedNumber", this.mSelectedNumber);
    paramStateBundle.putInt("editMode", this.mEditMode);
  }
  
  public void setHighlightCompletedValues(boolean paramBoolean)
  {
    this.mHighlightCompletedValues = paramBoolean;
  }
  
  public void setShowNumberTotals(boolean paramBoolean)
  {
    this.mShowNumberTotals = paramBoolean;
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\inputmethod\IMSingleNumber.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */