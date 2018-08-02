package com.nrsmagic.sudoku.game;

import java.util.StringTokenizer;

public class Cell
{
  private CellCollection mCellCollection;
  private final Object mCellCollectionLock = new Object();
  private CellGroup mColumn;
  private int mColumnIndex = -1;
  private boolean mEditable;
  private CellNote mNote;
  private CellGroup mRow;
  private int mRowIndex = -1;
  private CellGroup mSector;
  private boolean mValid;
  private int mValue;
  
  public Cell()
  {
    this(0, new CellNote(), true, true);
  }
  
  public Cell(int paramInt)
  {
    this(paramInt, new CellNote(), true, true);
  }
  
  private Cell(int paramInt, CellNote paramCellNote, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((paramInt < 0) || (paramInt > 9)) {
      throw new IllegalArgumentException("Value must be between 0-9.");
    }
    this.mValue = paramInt;
    this.mNote = paramCellNote;
    this.mEditable = paramBoolean1;
    this.mValid = paramBoolean2;
  }
  
  public static Cell deserialize(String paramString)
  {
    return deserialize(new StringTokenizer(paramString, "|"));
  }
  
  public static Cell deserialize(StringTokenizer paramStringTokenizer)
  {
    Cell localCell = new Cell();
    localCell.setValue(Integer.parseInt(paramStringTokenizer.nextToken()));
    localCell.setNote(CellNote.deserialize(paramStringTokenizer.nextToken()));
    localCell.setEditable(Boolean.valueOf(paramStringTokenizer.nextToken().equals("1")));
    return localCell;
  }
  
  private void onChange()
  {
    synchronized (this.mCellCollectionLock)
    {
      if (this.mCellCollection != null) {
        this.mCellCollection.onChange();
      }
      return;
    }
  }
  
  public CellGroup getColumn()
  {
    return this.mColumn;
  }
  
  public int getColumnIndex()
  {
    return this.mColumnIndex;
  }
  
  public CellNote getNote()
  {
    return this.mNote;
  }
  
  public CellGroup getRow()
  {
    return this.mRow;
  }
  
  public int getRowIndex()
  {
    return this.mRowIndex;
  }
  
  public CellGroup getSector()
  {
    return this.mSector;
  }
  
  public int getValue()
  {
    return this.mValue;
  }
  
  protected void initCollection(CellCollection paramCellCollection, int paramInt1, int paramInt2, CellGroup paramCellGroup1, CellGroup paramCellGroup2, CellGroup paramCellGroup3)
  {
    synchronized (this.mCellCollectionLock)
    {
      this.mCellCollection = paramCellCollection;
      this.mRowIndex = paramInt1;
      this.mColumnIndex = paramInt2;
      this.mSector = paramCellGroup1;
      this.mRow = paramCellGroup2;
      this.mColumn = paramCellGroup3;
      paramCellGroup1.addCell(this);
      paramCellGroup2.addCell(this);
      paramCellGroup3.addCell(this);
      return;
    }
  }
  
  public boolean isEditable()
  {
    return this.mEditable;
  }
  
  public boolean isValid()
  {
    return this.mValid;
  }
  
  public String serialize()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    serialize(localStringBuilder);
    return localStringBuilder.toString();
  }
  
  public void serialize(StringBuilder paramStringBuilder)
  {
    paramStringBuilder.append(this.mValue).append("|");
    if ((this.mNote == null) || (this.mNote.isEmpty()))
    {
      paramStringBuilder.append("-").append("|");
      if (!this.mEditable) {
        break label85;
      }
    }
    label85:
    for (String str = "1";; str = "0")
    {
      paramStringBuilder.append(str).append("|");
      return;
      this.mNote.serialize(paramStringBuilder);
      paramStringBuilder.append("|");
      break;
    }
  }
  
  public void setEditable(Boolean paramBoolean)
  {
    this.mEditable = paramBoolean.booleanValue();
    onChange();
  }
  
  public void setNote(CellNote paramCellNote)
  {
    this.mNote = paramCellNote;
    onChange();
  }
  
  public void setValid(Boolean paramBoolean)
  {
    this.mValid = paramBoolean.booleanValue();
    onChange();
  }
  
  public void setValue(int paramInt)
  {
    if ((paramInt < 0) || (paramInt > 9)) {
      throw new IllegalArgumentException("Value must be between 0-9.");
    }
    this.mValue = paramInt;
    onChange();
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\game\Cell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */