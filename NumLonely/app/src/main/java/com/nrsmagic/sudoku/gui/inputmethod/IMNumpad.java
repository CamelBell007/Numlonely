package com.nrsmagic.sudoku.gui.inputmethod;

import android.content.Context;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class IMNumpad
  extends InputMethod
{
  private static final int MODE_EDIT_NOTE = 1;
  private static final int MODE_EDIT_VALUE;
  private int mEditMode = 0;
  private boolean mHighlightCompletedValues = true;
  private OnClickListener mNumberButtonClick = new OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      if (IMNumpad.this.mGame.isCompleted()) {}
      int i;
      Cell localCell;
      do
      {
        do
        {
          do
          {
            do
            {
              return;
              i = ((Integer)paramAnonymousView.getTag()).intValue();
              localCell = IMNumpad.this.mSelectedCell;
            } while (localCell == null);
            switch (IMNumpad.this.mEditMode)
            {
            default: 
              return;
            }
          } while ((i < 0) || (i > 9));
          IMNumpad.this.mGame.setCellValue(localCell, i);
        } while (!IMNumpad.this.isMoveCellSelectionOnPress());
        IMNumpad.this.mBoard.moveCellSelectionRight();
        return;
        if (i == 0)
        {
          IMNumpad.this.mGame.setCellNote(localCell, CellNote.EMPTY);
          return;
        }
      } while ((i <= 0) || (i > 9));
      IMNumpad.this.mGame.setCellNote(localCell, localCell.getNote().toggleNumber(i));
    }
  };
  private Map<Integer, Button> mNumberButtons;
  private CellCollection.OnChangeListener mOnCellsChangeListener = new CellCollection.OnChangeListener()
  {
    public void onChange()
    {
      if (IMNumpad.this.mActive) {
        IMNumpad.this.update();
      }
    }
  };
  private Cell mSelectedCell;
  private boolean mShowNumberTotals = false;
  private ImageButton mSwitchNumNoteButton;
  private boolean moveCellSelectionOnPress = true;
  
  private void update()
  {
    Iterator localIterator2;
    label79:
    Iterator localIterator1;
    switch (this.mEditMode)
    {
    default: 
      Map localMap;
      if (!this.mHighlightCompletedValues)
      {
        boolean bool = this.mShowNumberTotals;
        localMap = null;
        if (!bool) {}
      }
      else
      {
        localMap = this.mGame.getCells().getValuesUseCount();
      }
      if (this.mHighlightCompletedValues)
      {
        localIterator2 = localMap.entrySet().iterator();
        if (localIterator2.hasNext()) {}
      }
      else if (this.mShowNumberTotals)
      {
        localIterator1 = localMap.entrySet().iterator();
      }
      break;
    }
    for (;;)
    {
      if (!localIterator1.hasNext())
      {
        return;
        this.mSwitchNumNoteButton.setImageResource(2130837520);
        break;
        this.mSwitchNumNoteButton.setImageResource(2130837521);
        break;
        Entry localEntry2 = (Entry)localIterator2.next();
        if (((Integer)localEntry2.getValue()).intValue() >= 9) {}
        Button localButton;
        for (int i = 1;; i = 0)
        {
          localButton = (Button)this.mNumberButtons.get(localEntry2.getKey());
          if (i == 0) {
            break label217;
          }
          localButton.setBackgroundResource(2130837504);
          break;
        }
        label217:
        localButton.setBackgroundResource(2130837508);
        break label79;
      }
      Entry localEntry1 = (Entry)localIterator1.next();
      ((Button)this.mNumberButtons.get(localEntry1.getKey())).setText(localEntry1.getKey() + " (" + localEntry1.getValue() + ")");
    }
  }
  
  protected View createControlPanelView()
  {
    View localView = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903046, null);
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
            IMNumpad localIMNumpad = IMNumpad.this;
            if (IMNumpad.this.mEditMode == 0) {}
            for (int i = 1;; i = 0)
            {
              localIMNumpad.mEditMode = i;
              IMNumpad.this.update();
              return;
            }
          }
        });
        return localView;
      }
      Integer localInteger = (Integer)localIterator.next();
      Button localButton = (Button)this.mNumberButtons.get(localInteger);
      localButton.setTag(localInteger);
      localButton.setOnClickListener(this.mNumberButtonClick);
    }
  }
  
  public String getAbbrName()
  {
    return this.mContext.getString(2131296329);
  }
  
  public int getHelpResID()
  {
    return 2131296339;
  }
  
  public boolean getHighlightCompletedValues()
  {
    return this.mHighlightCompletedValues;
  }
  
  public int getNameResID()
  {
    return 2131296325;
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
  
  public boolean isMoveCellSelectionOnPress()
  {
    return this.moveCellSelectionOnPress;
  }
  
  protected void onActivated()
  {
    update();
    this.mSelectedCell = this.mBoard.getSelectedCell();
  }
  
  protected void onCellSelected(Cell paramCell)
  {
    this.mSelectedCell = paramCell;
  }
  
  protected void onRestoreState(IMControlPanelStatePersister.StateBundle paramStateBundle)
  {
    this.mEditMode = paramStateBundle.getInt("editMode", 0);
    if (isInputMethodViewCreated()) {
      update();
    }
  }
  
  protected void onSaveState(IMControlPanelStatePersister.StateBundle paramStateBundle)
  {
    paramStateBundle.putInt("editMode", this.mEditMode);
  }
  
  public void setHighlightCompletedValues(boolean paramBoolean)
  {
    this.mHighlightCompletedValues = paramBoolean;
  }
  
  public void setMoveCellSelectionOnPress(boolean paramBoolean)
  {
    this.moveCellSelectionOnPress = paramBoolean;
  }
  
  public void setShowNumberTotals(boolean paramBoolean)
  {
    this.mShowNumberTotals = paramBoolean;
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\inputmethod\IMNumpad.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */