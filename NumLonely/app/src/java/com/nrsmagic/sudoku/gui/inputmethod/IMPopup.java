package com.nrsmagic.sudoku.gui.inputmethod;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.LayoutInflater;
import android.view.View;
import com.nrsmagic.sudoku.game.Cell;
import com.nrsmagic.sudoku.game.CellCollection;
import com.nrsmagic.sudoku.game.CellNote;
import com.nrsmagic.sudoku.game.SudokuGame;
import com.nrsmagic.sudoku.gui.SudokuBoardView;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class IMPopup
  extends InputMethod
{
  private IMPopupDialog mEditCellDialog;
  private boolean mHighlightCompletedValues = true;
  private IMPopupDialog.OnNoteEditListener mOnNoteEditListener = new IMPopupDialog.OnNoteEditListener()
  {
    public boolean onNoteEdit(Integer[] paramAnonymousArrayOfInteger)
    {
      if (IMPopup.this.mSelectedCell != null) {
        IMPopup.this.mGame.setCellNote(IMPopup.this.mSelectedCell, CellNote.fromIntArray(paramAnonymousArrayOfInteger));
      }
      return true;
    }
  };
  private IMPopupDialog.OnNumberEditListener mOnNumberEditListener = new IMPopupDialog.OnNumberEditListener()
  {
    public boolean onNumberEdit(int paramAnonymousInt)
    {
      if ((paramAnonymousInt != -1) && (IMPopup.this.mSelectedCell != null)) {
        IMPopup.this.mGame.setCellValue(IMPopup.this.mSelectedCell, paramAnonymousInt);
      }
      return true;
    }
  };
  private OnDismissListener mOnPopupDismissedListener = new OnDismissListener()
  {
    public void onDismiss(DialogInterface paramAnonymousDialogInterface)
    {
      IMPopup.this.mBoard.hideTouchedCellHint();
    }
  };
  private Cell mSelectedCell;
  private boolean mShowNumberTotals = false;
  
  private void ensureEditCellDialog()
  {
    if (this.mEditCellDialog == null)
    {
      this.mEditCellDialog = new IMPopupDialog(this.mContext);
      this.mEditCellDialog.setOnNumberEditListener(this.mOnNumberEditListener);
      this.mEditCellDialog.setOnNoteEditListener(this.mOnNoteEditListener);
      this.mEditCellDialog.setOnDismissListener(this.mOnPopupDismissedListener);
    }
  }
  
  protected View createControlPanelView()
  {
    return ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903047, null);
  }
  
  public String getAbbrName()
  {
    return this.mContext.getString(2131296330);
  }
  
  public int getHelpResID()
  {
    return 2131296337;
  }
  
  public boolean getHighlightCompletedValues()
  {
    return this.mHighlightCompletedValues;
  }
  
  public int getNameResID()
  {
    return 2131296321;
  }
  
  public boolean getShowNumberTotals()
  {
    return this.mShowNumberTotals;
  }
  
  protected void onActivated()
  {
    this.mBoard.setAutoHideTouchedCellHint(false);
  }
  
  protected void onCellTapped(Cell paramCell)
  {
    this.mSelectedCell = paramCell;
    if (paramCell.isEditable())
    {
      ensureEditCellDialog();
      this.mEditCellDialog.resetButtons();
      this.mEditCellDialog.updateNumber(Integer.valueOf(paramCell.getValue()));
      this.mEditCellDialog.updateNote(paramCell.getNote().getNotedNumbers());
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
      Iterator localIterator2;
      Iterator localIterator1;
      if (this.mHighlightCompletedValues)
      {
        localIterator2 = localMap.entrySet().iterator();
        if (localIterator2.hasNext()) {}
      }
      else if (this.mShowNumberTotals)
      {
        localIterator1 = localMap.entrySet().iterator();
      }
      for (;;)
      {
        if (!localIterator1.hasNext())
        {
          this.mEditCellDialog.show();
          return;
          Entry localEntry2 = (Entry)localIterator2.next();
          if (((Integer)localEntry2.getValue()).intValue() < 9) {
            break;
          }
          this.mEditCellDialog.highlightNumber(((Integer)localEntry2.getKey()).intValue());
          break;
        }
        Entry localEntry1 = (Entry)localIterator1.next();
        this.mEditCellDialog.setValueCount(((Integer)localEntry1.getKey()).intValue(), ((Integer)localEntry1.getValue()).intValue());
      }
    }
    this.mBoard.hideTouchedCellHint();
  }
  
  protected void onDeactivated()
  {
    this.mBoard.setAutoHideTouchedCellHint(true);
  }
  
  protected void onPause()
  {
    if (this.mEditCellDialog != null) {
      this.mEditCellDialog.cancel();
    }
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


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\inputmethod\IMPopup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */