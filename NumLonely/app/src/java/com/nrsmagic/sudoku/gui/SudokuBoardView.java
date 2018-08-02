package com.nrsmagic.sudoku.gui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import com.nrsmagic.sudoku.R.styleable;
import com.nrsmagic.sudoku.game.Cell;
import com.nrsmagic.sudoku.game.CellCollection;
import com.nrsmagic.sudoku.game.CellCollection.OnChangeListener;
import com.nrsmagic.sudoku.game.CellNote;
import com.nrsmagic.sudoku.game.SudokuGame;
import java.util.Collection;
import java.util.Iterator;

public class SudokuBoardView
  extends View
{
  public static final int DEFAULT_BOARD_SIZE = 100;
  private static final int NO_COLOR;
  private boolean mAutoHideTouchedCellHint = true;
  private Paint mBackgroundColorReadOnly;
  private Paint mBackgroundColorSecondary;
  private Paint mBackgroundColorSelected;
  private Paint mBackgroundColorTouched;
  private float mCellHeight;
  private Paint mCellNotePaint;
  private Paint mCellValueInvalidPaint;
  private Paint mCellValuePaint;
  private Paint mCellValueReadonlyPaint;
  private float mCellWidth;
  private CellCollection mCells;
  private SudokuGame mGame;
  private boolean mHighlightTouchedCell = true;
  private boolean mHighlightWrongVals = true;
  private Paint mLinePaint;
  private float mNoteTop;
  private int mNumberLeft;
  private int mNumberTop;
  private OnCellSelectedListener mOnCellSelectedListener;
  private OnCellTappedListener mOnCellTappedListener;
  private boolean mReadonly = false;
  private Paint mSectorLinePaint;
  private int mSectorLineWidth;
  private Cell mSelectedCell;
  private Cell mTouchedCell;
  
  public SudokuBoardView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public SudokuBoardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setFocusable(true);
    setFocusableInTouchMode(true);
    this.mLinePaint = new Paint();
    this.mSectorLinePaint = new Paint();
    this.mCellValuePaint = new Paint();
    this.mCellValueReadonlyPaint = new Paint();
    this.mCellValueInvalidPaint = new Paint();
    this.mCellNotePaint = new Paint();
    this.mBackgroundColorSecondary = new Paint();
    this.mBackgroundColorReadOnly = new Paint();
    this.mBackgroundColorTouched = new Paint();
    this.mBackgroundColorSelected = new Paint();
    this.mCellValuePaint.setAntiAlias(true);
    this.mCellValueReadonlyPaint.setAntiAlias(true);
    this.mCellValueInvalidPaint.setAntiAlias(true);
    this.mCellNotePaint.setAntiAlias(true);
    this.mCellValueInvalidPaint.setColor(-65536);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.SudokuBoardView);
    setLineColor(localTypedArray.getColor(0, -16777216));
    setSectorLineColor(localTypedArray.getColor(1, -16777216));
    setTextColor(localTypedArray.getColor(2, -16777216));
    setTextColorReadOnly(localTypedArray.getColor(3, -16777216));
    setTextColorNote(localTypedArray.getColor(4, -16777216));
    setBackgroundColor(localTypedArray.getColor(5, -1));
    setBackgroundColorSecondary(localTypedArray.getColor(6, 0));
    setBackgroundColorReadOnly(localTypedArray.getColor(7, 0));
    setBackgroundColorTouched(localTypedArray.getColor(8, Color.rgb(50, 50, 255)));
    setBackgroundColorSelected(localTypedArray.getColor(9, 65280));
    localTypedArray.recycle();
  }
  
  private void computeSectorLineWidth(int paramInt1, int paramInt2)
  {
    if (paramInt1 < paramInt2) {}
    for (int i = paramInt1;; i = paramInt2)
    {
      float f1 = getContext().getResources().getDisplayMetrics().density;
      float f2 = i / f1;
      float f3 = 2.0F;
      if (f2 > 150.0F) {
        f3 = 3.0F;
      }
      this.mSectorLineWidth = ((int)(f3 * f1));
      return;
    }
  }
  
  private Cell getCellAtPoint(int paramInt1, int paramInt2)
  {
    int i = paramInt1 - getPaddingLeft();
    int j = (int)((paramInt2 - getPaddingTop()) / this.mCellHeight);
    int k = (int)(i / this.mCellWidth);
    if ((k >= 0) && (k < 9) && (j >= 0) && (j < 9)) {
      return this.mCells.getCell(j, k);
    }
    return null;
  }
  
  private boolean moveCellSelection(int paramInt1, int paramInt2)
  {
    Cell localCell = this.mSelectedCell;
    int i = 0;
    int j = 0;
    if (localCell != null)
    {
      j = paramInt2 + this.mSelectedCell.getRowIndex();
      i = paramInt1 + this.mSelectedCell.getColumnIndex();
    }
    return moveCellSelectionTo(j, i);
  }
  
  private boolean moveCellSelectionTo(int paramInt1, int paramInt2)
  {
    if ((paramInt2 >= 0) && (paramInt2 < 9) && (paramInt1 >= 0) && (paramInt1 < 9))
    {
      this.mSelectedCell = this.mCells.getCell(paramInt1, paramInt2);
      onCellSelected(this.mSelectedCell);
      postInvalidate();
      return true;
    }
    return false;
  }
  
  private void setCellNote(Cell paramCell, CellNote paramCellNote)
  {
    if (paramCell.isEditable())
    {
      if (this.mGame != null) {
        this.mGame.setCellNote(paramCell, paramCellNote);
      }
    }
    else {
      return;
    }
    paramCell.setNote(paramCellNote);
  }
  
  private void setCellValue(Cell paramCell, int paramInt)
  {
    if (paramCell.isEditable())
    {
      if (this.mGame != null) {
        this.mGame.setCellValue(paramCell, paramInt);
      }
    }
    else {
      return;
    }
    paramCell.setValue(paramInt);
  }
  
  public boolean getAutoHideTouchedCellHint()
  {
    return this.mAutoHideTouchedCellHint;
  }
  
  public int getBackgroundColorReadOnly()
  {
    return this.mBackgroundColorReadOnly.getColor();
  }
  
  public int getBackgroundColorSecondary()
  {
    return this.mBackgroundColorSecondary.getColor();
  }
  
  public int getBackgroundColorSelected()
  {
    return this.mBackgroundColorSelected.getColor();
  }
  
  public int getBackgroundColorTouched()
  {
    return this.mBackgroundColorTouched.getColor();
  }
  
  public CellCollection getCells()
  {
    return this.mCells;
  }
  
  public boolean getHighlightTouchedCell()
  {
    return this.mHighlightTouchedCell;
  }
  
  public boolean getHighlightWrongVals()
  {
    return this.mHighlightWrongVals;
  }
  
  public int getLineColor()
  {
    return this.mLinePaint.getColor();
  }
  
  public int getSectorLineColor()
  {
    return this.mSectorLinePaint.getColor();
  }
  
  public Cell getSelectedCell()
  {
    return this.mSelectedCell;
  }
  
  public int getTextColor()
  {
    return this.mCellValuePaint.getColor();
  }
  
  public int getTextColorNote()
  {
    return this.mCellNotePaint.getColor();
  }
  
  public int getTextColorReadOnly()
  {
    return this.mCellValueReadonlyPaint.getColor();
  }
  
  public void hideTouchedCellHint()
  {
    this.mTouchedCell = null;
    postInvalidate();
  }
  
  public boolean isReadOnly()
  {
    return this.mReadonly;
  }
  
  public void moveCellSelectionRight()
  {
    if ((!moveCellSelection(1, 0)) && (!moveCellSelectionTo(1 + this.mSelectedCell.getRowIndex(), 0))) {
      moveCellSelectionTo(0, 0);
    }
    postInvalidate();
  }
  
  protected void onCellSelected(Cell paramCell)
  {
    if (this.mOnCellSelectedListener != null) {
      this.mOnCellSelectedListener.onCellSelected(paramCell);
    }
  }
  
  protected void onCellTapped(Cell paramCell)
  {
    if (this.mOnCellTappedListener != null) {
      this.mOnCellTappedListener.onCellTapped(paramCell);
    }
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i = getWidth() - getPaddingRight();
    int j = getHeight() - getPaddingBottom();
    int k = getPaddingLeft();
    int m = getPaddingTop();
    if (this.mBackgroundColorSecondary.getColor() != 0)
    {
      paramCanvas.drawRect(3.0F * this.mCellWidth, 0.0F, 6.0F * this.mCellWidth, 3.0F * this.mCellWidth, this.mBackgroundColorSecondary);
      paramCanvas.drawRect(0.0F, 3.0F * this.mCellWidth, 3.0F * this.mCellWidth, 6.0F * this.mCellWidth, this.mBackgroundColorSecondary);
      paramCanvas.drawRect(6.0F * this.mCellWidth, 3.0F * this.mCellWidth, 9.0F * this.mCellWidth, 6.0F * this.mCellWidth, this.mBackgroundColorSecondary);
      paramCanvas.drawRect(3.0F * this.mCellWidth, 6.0F * this.mCellWidth, 6.0F * this.mCellWidth, 9.0F * this.mCellWidth, this.mBackgroundColorSecondary);
    }
    int i6;
    float f5;
    float f6;
    float f7;
    int i7;
    if (this.mCells != null)
    {
      if (this.mBackgroundColorReadOnly.getColor() == 0) {
        break label494;
      }
      i6 = 1;
      f5 = this.mCellValuePaint.ascent();
      f6 = this.mCellNotePaint.ascent();
      f7 = this.mCellWidth / 3.0F;
      i7 = 0;
      label237:
      if (i7 < 9) {
        break label500;
      }
      if ((!this.mReadonly) && (this.mSelectedCell != null))
      {
        int i17 = k + Math.round(this.mSelectedCell.getColumnIndex() * this.mCellWidth);
        int i18 = m + Math.round(this.mSelectedCell.getRowIndex() * this.mCellHeight);
        paramCanvas.drawRect(i17, i18, i17 + this.mCellWidth, i18 + this.mCellHeight, this.mBackgroundColorSelected);
      }
      if ((this.mHighlightTouchedCell) && (this.mTouchedCell != null))
      {
        int i15 = k + Math.round(this.mTouchedCell.getColumnIndex() * this.mCellWidth);
        int i16 = m + Math.round(this.mTouchedCell.getRowIndex() * this.mCellHeight);
        paramCanvas.drawRect(i15, m, i15 + this.mCellWidth, j, this.mBackgroundColorTouched);
        paramCanvas.drawRect(k, i16, i, i16 + this.mCellHeight, this.mBackgroundColorTouched);
      }
    }
    int n = 0;
    label437:
    int i1;
    label447:
    int i2;
    int i3;
    int i4;
    if (n > 9)
    {
      i1 = 0;
      if (i1 <= 9) {
        break label865;
      }
      i2 = this.mSectorLineWidth / 2;
      i3 = i2 + this.mSectorLineWidth % 2;
      i4 = 0;
      label476:
      if (i4 <= 9) {
        break label902;
      }
    }
    for (int i5 = 0;; i5 += 3)
    {
      if (i5 > 9)
      {
        return;
        label494:
        i6 = 0;
        break;
        label500:
        int i8 = 0;
        if (i8 >= 9)
        {
          i7++;
          break label237;
        }
        Cell localCell = this.mCells.getCell(i7, i8);
        int i9 = Math.round(i8 * this.mCellWidth + k);
        int i10 = Math.round(i7 * this.mCellHeight + m);
        if ((!localCell.isEditable()) && (i6 != 0) && (this.mBackgroundColorReadOnly.getColor() != 0)) {
          paramCanvas.drawRect(i9, i10, i9 + this.mCellWidth, i10 + this.mCellHeight, this.mBackgroundColorReadOnly);
        }
        int i11 = localCell.getValue();
        Paint localPaint;
        if (i11 != 0) {
          if (localCell.isEditable())
          {
            localPaint = this.mCellValuePaint;
            label642:
            if ((this.mHighlightWrongVals) && (!localCell.isValid())) {
              localPaint = this.mCellValueInvalidPaint;
            }
            paramCanvas.drawText(Integer.toString(i11), i9 + this.mNumberLeft, i10 + this.mNumberTop - f5, localPaint);
          }
        }
        for (;;)
        {
          i8++;
          break;
          localPaint = this.mCellValueReadonlyPaint;
          break label642;
          if (!localCell.getNote().isEmpty())
          {
            Iterator localIterator = localCell.getNote().getNotedNumbers().iterator();
            while (localIterator.hasNext())
            {
              Integer localInteger = (Integer)localIterator.next();
              int i12 = -1 + localInteger.intValue();
              int i13 = i12 % 3;
              int i14 = i12 / 3;
              paramCanvas.drawText(Integer.toString(localInteger.intValue()), 2.0F + (i9 + f7 * i13), i10 + this.mNoteTop - f6 + f7 * i14 - 1.0F, this.mCellNotePaint);
            }
          }
        }
        float f1 = n * this.mCellWidth + k;
        paramCanvas.drawLine(f1, m, f1, j, this.mLinePaint);
        n++;
        break label437;
        label865:
        float f2 = i1 * this.mCellHeight + m;
        paramCanvas.drawLine(k, f2, i, f2, this.mLinePaint);
        i1++;
        break label447;
        label902:
        float f3 = i4 * this.mCellWidth + k;
        paramCanvas.drawRect(f3 - i2, m, f3 + i3, j, this.mSectorLinePaint);
        i4 += 3;
        break label476;
      }
      float f4 = i5 * this.mCellHeight + m;
      paramCanvas.drawRect(k, f4 - i2, i, f4 + i3, this.mSectorLinePaint);
    }
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (!this.mReadonly)
    {
      int i;
      Cell localCell;
      switch (paramInt)
      {
      default: 
        if ((paramInt < 8) || (paramInt > 16)) {
          break label245;
        }
        i = paramInt - 7;
        localCell = this.mSelectedCell;
        if ((paramKeyEvent.isShiftPressed()) || (paramKeyEvent.isAltPressed())) {
          setCellNote(localCell, localCell.getNote().toggleNumber(i));
        }
        break;
      case 19: 
      case 22: 
      case 20: 
      case 21: 
      case 7: 
      case 62: 
      case 67: 
      case 23: 
        do
        {
          do
          {
            return true;
            return moveCellSelection(0, -1);
            return moveCellSelection(1, 0);
            return moveCellSelection(0, 1);
            return moveCellSelection(-1, 0);
          } while (this.mSelectedCell == null);
          if ((paramKeyEvent.isShiftPressed()) || (paramKeyEvent.isAltPressed()))
          {
            setCellNote(this.mSelectedCell, CellNote.EMPTY);
            return true;
          }
          setCellValue(this.mSelectedCell, 0);
          moveCellSelectionRight();
          return true;
        } while (this.mSelectedCell == null);
        onCellTapped(this.mSelectedCell);
        return true;
      }
      setCellValue(localCell, i);
      moveCellSelectionRight();
      return true;
    }
    label245:
    return false;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = MeasureSpec.getMode(paramInt1);
    int j = MeasureSpec.getSize(paramInt1);
    int k = MeasureSpec.getMode(paramInt2);
    int m = MeasureSpec.getSize(paramInt2);
    int n;
    if (i == 1073741824)
    {
      n = j;
      if (k != 1073741824) {
        break label294;
      }
    }
    for (int i1 = m;; i1 = m) {
      label294:
      do
      {
        if (i != 1073741824) {
          n = i1;
        }
        if (k != 1073741824) {
          i1 = n;
        }
        if ((i == Integer.MIN_VALUE) && (n > j)) {
          n = j;
        }
        if ((k == Integer.MIN_VALUE) && (i1 > m)) {
          i1 = m;
        }
        this.mCellWidth = ((n - getPaddingLeft() - getPaddingRight()) / 9.0F);
        this.mCellHeight = ((i1 - getPaddingTop() - getPaddingBottom()) / 9.0F);
        setMeasuredDimension(n, i1);
        float f = 0.75F * this.mCellHeight;
        this.mCellValuePaint.setTextSize(f);
        this.mCellValueReadonlyPaint.setTextSize(f);
        this.mCellValueInvalidPaint.setTextSize(f);
        this.mCellNotePaint.setTextSize(this.mCellHeight / 3.0F);
        this.mNumberLeft = ((int)((this.mCellWidth - this.mCellValuePaint.measureText("9")) / 2.0F));
        this.mNumberTop = ((int)((this.mCellHeight - this.mCellValuePaint.getTextSize()) / 2.0F));
        this.mNoteTop = (this.mCellHeight / 50.0F);
        computeSectorLineWidth(n, i1);
        return;
        n = 100;
        if ((i != Integer.MIN_VALUE) || (n <= j)) {
          break;
        }
        n = j;
        break;
        i1 = 100;
      } while ((k != Integer.MIN_VALUE) || (i1 <= m));
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i;
    int j;
    if (!this.mReadonly)
    {
      i = (int)paramMotionEvent.getX();
      j = (int)paramMotionEvent.getY();
      switch (paramMotionEvent.getAction())
      {
      }
    }
    for (;;)
    {
      postInvalidate();
      if (!this.mReadonly) {
        break;
      }
      return false;
      this.mTouchedCell = getCellAtPoint(i, j);
      continue;
      this.mSelectedCell = getCellAtPoint(i, j);
      invalidate();
      if (this.mSelectedCell != null)
      {
        onCellTapped(this.mSelectedCell);
        onCellSelected(this.mSelectedCell);
      }
      if (this.mAutoHideTouchedCellHint)
      {
        this.mTouchedCell = null;
        continue;
        this.mTouchedCell = null;
      }
    }
    return true;
  }
  
  public void setAutoHideTouchedCellHint(boolean paramBoolean)
  {
    this.mAutoHideTouchedCellHint = paramBoolean;
  }
  
  public void setBackgroundColorReadOnly(int paramInt)
  {
    this.mBackgroundColorReadOnly.setColor(paramInt);
  }
  
  public void setBackgroundColorSecondary(int paramInt)
  {
    this.mBackgroundColorSecondary.setColor(paramInt);
  }
  
  public void setBackgroundColorSelected(int paramInt)
  {
    this.mBackgroundColorSelected.setColor(paramInt);
    this.mBackgroundColorSelected.setAlpha(100);
  }
  
  public void setBackgroundColorTouched(int paramInt)
  {
    this.mBackgroundColorTouched.setColor(paramInt);
    this.mBackgroundColorTouched.setAlpha(100);
  }
  
  public void setCells(CellCollection paramCellCollection)
  {
    this.mCells = paramCellCollection;
    if (this.mCells != null)
    {
      if (!this.mReadonly)
      {
        this.mSelectedCell = this.mCells.getCell(0, 0);
        onCellSelected(this.mSelectedCell);
      }
      this.mCells.addOnChangeListener(new CellCollection.OnChangeListener()
      {
        public void onChange()
        {
          SudokuBoardView.this.postInvalidate();
        }
      });
    }
    postInvalidate();
  }
  
  public void setGame(SudokuGame paramSudokuGame)
  {
    this.mGame = paramSudokuGame;
    setCells(paramSudokuGame.getCells());
  }
  
  public void setHighlightTouchedCell(boolean paramBoolean)
  {
    this.mHighlightTouchedCell = paramBoolean;
  }
  
  public void setHighlightWrongVals(boolean paramBoolean)
  {
    this.mHighlightWrongVals = paramBoolean;
    postInvalidate();
  }
  
  public void setLineColor(int paramInt)
  {
    this.mLinePaint.setColor(paramInt);
  }
  
  public void setOnCellSelectedListener(OnCellSelectedListener paramOnCellSelectedListener)
  {
    this.mOnCellSelectedListener = paramOnCellSelectedListener;
  }
  
  public void setOnCellTappedListener(OnCellTappedListener paramOnCellTappedListener)
  {
    this.mOnCellTappedListener = paramOnCellTappedListener;
  }
  
  public void setReadOnly(boolean paramBoolean)
  {
    this.mReadonly = paramBoolean;
    postInvalidate();
  }
  
  public void setSectorLineColor(int paramInt)
  {
    this.mSectorLinePaint.setColor(paramInt);
  }
  
  public void setTextColor(int paramInt)
  {
    this.mCellValuePaint.setColor(paramInt);
  }
  
  public void setTextColorNote(int paramInt)
  {
    this.mCellNotePaint.setColor(paramInt);
  }
  
  public void setTextColorReadOnly(int paramInt)
  {
    this.mCellValueReadonlyPaint.setColor(paramInt);
  }
  
  public static abstract interface OnCellSelectedListener
  {
    public abstract void onCellSelected(Cell paramCell);
  }
  
  public static abstract interface OnCellTappedListener
  {
    public abstract void onCellTapped(Cell paramCell);
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\SudokuBoardView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */