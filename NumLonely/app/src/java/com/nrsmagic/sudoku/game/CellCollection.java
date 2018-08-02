package com.nrsmagic.sudoku.game;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CellCollection
{
  private static Pattern DATA_PATTERN_VERSION_1 = Pattern.compile("^version: 1\\n((?#value)\\d\\|(?#note)((\\d,)+|-)\\|(?#editable)[01]\\|){0,81}$");
  private static Pattern DATA_PATTERN_VERSION_PLAIN;
  public static int DATA_VERSION_1 = 0;
  public static int DATA_VERSION_PLAIN = 0;
  public static final int SUDOKU_SIZE = 9;
  private Cell[][] mCells;
  private final List<OnChangeListener> mChangeListeners = new ArrayList();
  private CellGroup[] mColumns;
  private boolean mOnChangeEnabled = true;
  private CellGroup[] mRows;
  private CellGroup[] mSectors;
  
  static
  {
    DATA_VERSION_1 = 1;
    DATA_PATTERN_VERSION_PLAIN = Pattern.compile("^\\d{81}$");
  }
  
  private CellCollection(Cell[][] paramArrayOfCell)
  {
    this.mCells = paramArrayOfCell;
    initCollection();
  }
  
  public static CellCollection createDebugGame()
  {
    Cell[][] arrayOfCell = new Cell[9][];
    Cell[] arrayOfCell1 = new Cell[9];
    arrayOfCell1[0] = new Cell();
    arrayOfCell1[1] = new Cell();
    arrayOfCell1[2] = new Cell();
    arrayOfCell1[3] = new Cell(4);
    arrayOfCell1[4] = new Cell(5);
    arrayOfCell1[5] = new Cell(6);
    arrayOfCell1[6] = new Cell(7);
    arrayOfCell1[7] = new Cell(8);
    arrayOfCell1[8] = new Cell(9);
    arrayOfCell[0] = arrayOfCell1;
    Cell[] arrayOfCell2 = new Cell[9];
    arrayOfCell2[0] = new Cell();
    arrayOfCell2[1] = new Cell();
    arrayOfCell2[2] = new Cell();
    arrayOfCell2[3] = new Cell(7);
    arrayOfCell2[4] = new Cell(8);
    arrayOfCell2[5] = new Cell(9);
    arrayOfCell2[6] = new Cell(1);
    arrayOfCell2[7] = new Cell(2);
    arrayOfCell2[8] = new Cell(3);
    arrayOfCell[1] = arrayOfCell2;
    Cell[] arrayOfCell3 = new Cell[9];
    arrayOfCell3[0] = new Cell();
    arrayOfCell3[1] = new Cell();
    arrayOfCell3[2] = new Cell();
    arrayOfCell3[3] = new Cell(1);
    arrayOfCell3[4] = new Cell(2);
    arrayOfCell3[5] = new Cell(3);
    arrayOfCell3[6] = new Cell(4);
    arrayOfCell3[7] = new Cell(5);
    arrayOfCell3[8] = new Cell(6);
    arrayOfCell[2] = arrayOfCell3;
    Cell[] arrayOfCell4 = new Cell[9];
    arrayOfCell4[0] = new Cell(2);
    arrayOfCell4[1] = new Cell(3);
    arrayOfCell4[2] = new Cell(4);
    arrayOfCell4[3] = new Cell();
    arrayOfCell4[4] = new Cell();
    arrayOfCell4[5] = new Cell();
    arrayOfCell4[6] = new Cell(8);
    arrayOfCell4[7] = new Cell(9);
    arrayOfCell4[8] = new Cell(1);
    arrayOfCell[3] = arrayOfCell4;
    Cell[] arrayOfCell5 = new Cell[9];
    arrayOfCell5[0] = new Cell(5);
    arrayOfCell5[1] = new Cell(6);
    arrayOfCell5[2] = new Cell(7);
    arrayOfCell5[3] = new Cell();
    arrayOfCell5[4] = new Cell();
    arrayOfCell5[5] = new Cell();
    arrayOfCell5[6] = new Cell(2);
    arrayOfCell5[7] = new Cell(3);
    arrayOfCell5[8] = new Cell(4);
    arrayOfCell[4] = arrayOfCell5;
    Cell[] arrayOfCell6 = new Cell[9];
    arrayOfCell6[0] = new Cell(8);
    arrayOfCell6[1] = new Cell(9);
    arrayOfCell6[2] = new Cell(1);
    arrayOfCell6[3] = new Cell();
    arrayOfCell6[4] = new Cell();
    arrayOfCell6[5] = new Cell();
    arrayOfCell6[6] = new Cell(5);
    arrayOfCell6[7] = new Cell(6);
    arrayOfCell6[8] = new Cell(7);
    arrayOfCell[5] = arrayOfCell6;
    Cell[] arrayOfCell7 = new Cell[9];
    arrayOfCell7[0] = new Cell(3);
    arrayOfCell7[1] = new Cell(4);
    arrayOfCell7[2] = new Cell(5);
    arrayOfCell7[3] = new Cell(6);
    arrayOfCell7[4] = new Cell(7);
    arrayOfCell7[5] = new Cell(8);
    arrayOfCell7[6] = new Cell(9);
    arrayOfCell7[7] = new Cell(1);
    arrayOfCell7[8] = new Cell(2);
    arrayOfCell[6] = arrayOfCell7;
    Cell[] arrayOfCell8 = new Cell[9];
    arrayOfCell8[0] = new Cell(6);
    arrayOfCell8[1] = new Cell(7);
    arrayOfCell8[2] = new Cell(8);
    arrayOfCell8[3] = new Cell(9);
    arrayOfCell8[4] = new Cell(1);
    arrayOfCell8[5] = new Cell(2);
    arrayOfCell8[6] = new Cell(3);
    arrayOfCell8[7] = new Cell(4);
    arrayOfCell8[8] = new Cell(5);
    arrayOfCell[7] = arrayOfCell8;
    Cell[] arrayOfCell9 = new Cell[9];
    arrayOfCell9[0] = new Cell(9);
    arrayOfCell9[1] = new Cell(1);
    arrayOfCell9[2] = new Cell(2);
    arrayOfCell9[3] = new Cell(3);
    arrayOfCell9[4] = new Cell(4);
    arrayOfCell9[5] = new Cell(5);
    arrayOfCell9[6] = new Cell(6);
    arrayOfCell9[7] = new Cell(7);
    arrayOfCell9[8] = new Cell(8);
    arrayOfCell[8] = arrayOfCell9;
    CellCollection localCellCollection = new CellCollection(arrayOfCell);
    localCellCollection.markFilledCellsAsNotEditable();
    return localCellCollection;
  }
  
  public static CellCollection createEmpty()
  {
    Cell[][] arrayOfCell = (Cell[][])Array.newInstance(Cell.class, new int[] { 9, 9 });
    int i = 0;
    if (i >= 9) {
      return new CellCollection(arrayOfCell);
    }
    for (int j = 0;; j++)
    {
      if (j >= 9)
      {
        i++;
        break;
      }
      arrayOfCell[i][j] = new Cell();
    }
  }
  
  public static CellCollection deserialize(String paramString)
  {
    String[] arrayOfString = paramString.split("\n");
    if (arrayOfString.length == 0) {
      throw new IllegalArgumentException("Cannot deserialize Sudoku, data corrupted.");
    }
    if (arrayOfString[0].equals("version: 1")) {
      return deserialize(new StringTokenizer(arrayOfString[1], "|"));
    }
    return fromString(paramString);
  }
  
  public static CellCollection deserialize(StringTokenizer paramStringTokenizer)
  {
    Cell[][] arrayOfCell = (Cell[][])Array.newInstance(Cell.class, new int[] { 9, 9 });
    int i = 0;
    int j = 0;
    for (;;)
    {
      if ((!paramStringTokenizer.hasMoreTokens()) || (i >= 9)) {
        return new CellCollection(arrayOfCell);
      }
      arrayOfCell[i][j] = Cell.deserialize(paramStringTokenizer);
      j++;
      if (j == 9)
      {
        i++;
        j = 0;
      }
    }
  }
  
  public static CellCollection fromString(String paramString)
  {
    Cell[][] arrayOfCell = (Cell[][])Array.newInstance(Cell.class, new int[] { 9, 9 });
    int i = 0;
    int k;
    for (int j = 0;; j++)
    {
      if (j >= 9) {
        return new CellCollection(arrayOfCell);
      }
      k = 0;
      if (k < 9) {
        break;
      }
    }
    label57:
    int m = paramString.length();
    int n = 0;
    label72:
    Cell localCell;
    if (i >= m)
    {
      localCell = new Cell();
      localCell.setValue(n);
      if (n != 0) {
        break label162;
      }
    }
    label162:
    for (boolean bool = true;; bool = false)
    {
      localCell.setEditable(Boolean.valueOf(bool));
      arrayOfCell[j][k] = localCell;
      k++;
      break;
      i++;
      if ((paramString.charAt(i - 1) < '0') || (paramString.charAt(i - 1) > '9')) {
        break label57;
      }
      n = '￐' + paramString.charAt(i - 1);
      break label72;
    }
  }
  
  private void initCollection()
  {
    this.mRows = new CellGroup[9];
    this.mColumns = new CellGroup[9];
    this.mSectors = new CellGroup[9];
    int j;
    for (int i = 0;; i++)
    {
      if (i >= 9)
      {
        j = 0;
        if (j < 9) {
          break;
        }
        return;
      }
      this.mRows[i] = new CellGroup();
      this.mColumns[i] = new CellGroup();
      this.mSectors[i] = new CellGroup();
    }
    for (int k = 0;; k++)
    {
      if (k >= 9)
      {
        j++;
        break;
      }
      this.mCells[j][k].initCollection(this, j, k, this.mSectors[(3 * (k / 3) + j / 3)], this.mRows[k], this.mColumns[j]);
    }
  }
  
  public static boolean isValid(String paramString, int paramInt)
  {
    if (paramInt == DATA_VERSION_PLAIN) {
      return DATA_PATTERN_VERSION_PLAIN.matcher(paramString).matches();
    }
    if (paramInt == DATA_VERSION_1) {
      return DATA_PATTERN_VERSION_1.matcher(paramString).matches();
    }
    throw new IllegalArgumentException("Unknown version: " + paramInt);
  }
  
  public void addOnChangeListener(OnChangeListener paramOnChangeListener)
  {
    if (paramOnChangeListener == null) {
      throw new IllegalArgumentException("The listener is null.");
    }
    synchronized (this.mChangeListeners)
    {
      if (this.mChangeListeners.contains(paramOnChangeListener)) {
        throw new IllegalStateException("Listener " + paramOnChangeListener + "is already registered.");
      }
    }
    this.mChangeListeners.add(paramOnChangeListener);
  }
  
  public Cell getCell(int paramInt1, int paramInt2)
  {
    return this.mCells[paramInt1][paramInt2];
  }
  
  public Cell[][] getCells()
  {
    return this.mCells;
  }
  
  public Map<Integer, Integer> getValuesUseCount()
  {
    HashMap localHashMap = new HashMap();
    int j;
    for (int i = 1;; i++)
    {
      if (i > 9)
      {
        j = 0;
        if (j < 9) {
          break;
        }
        return localHashMap;
      }
      localHashMap.put(Integer.valueOf(i), Integer.valueOf(0));
    }
    for (int k = 0;; k++)
    {
      if (k >= 9)
      {
        j++;
        break;
      }
      int m = getCell(j, k).getValue();
      if (m != 0) {
        localHashMap.put(Integer.valueOf(m), Integer.valueOf(1 + ((Integer)localHashMap.get(Integer.valueOf(m))).intValue()));
      }
    }
  }
  
  public boolean isCompleted()
  {
    int i = 0;
    if (i >= 9) {
      return true;
    }
    for (int j = 0;; j++)
    {
      if (j >= 9)
      {
        i++;
        break;
      }
      Cell localCell = this.mCells[i][j];
      if ((localCell.getValue() == 0) || (!localCell.isValid())) {
        return false;
      }
    }
  }
  
  public boolean isEmpty()
  {
    int i = 0;
    if (i >= 9) {
      return true;
    }
    for (int j = 0;; j++)
    {
      if (j >= 9)
      {
        i++;
        break;
      }
      if (this.mCells[i][j].getValue() != 0) {
        return false;
      }
    }
  }
  
  public void markAllCellsAsEditable()
  {
    int i = 0;
    if (i >= 9) {
      return;
    }
    for (int j = 0;; j++)
    {
      if (j >= 9)
      {
        i++;
        break;
      }
      this.mCells[i][j].setEditable(Boolean.valueOf(true));
    }
  }
  
  public void markAllCellsAsValid()
  {
    this.mOnChangeEnabled = false;
    int i = 0;
    if (i >= 9)
    {
      this.mOnChangeEnabled = true;
      onChange();
      return;
    }
    for (int j = 0;; j++)
    {
      if (j >= 9)
      {
        i++;
        break;
      }
      this.mCells[i][j].setValid(Boolean.valueOf(true));
    }
  }
  
  public void markFilledCellsAsNotEditable()
  {
    int j;
    for (int i = 0;; i++)
    {
      if (i >= 9) {
        return;
      }
      j = 0;
      if (j < 9) {
        break;
      }
    }
    Cell localCell = this.mCells[i][j];
    if (localCell.getValue() == 0) {}
    for (boolean bool = true;; bool = false)
    {
      localCell.setEditable(Boolean.valueOf(bool));
      j++;
      break;
    }
  }
  
  protected void onChange()
  {
    if (this.mOnChangeEnabled) {
      synchronized (this.mChangeListeners)
      {
        Iterator localIterator = this.mChangeListeners.iterator();
        if (!localIterator.hasNext()) {
          return;
        }
        ((OnChangeListener)localIterator.next()).onChange();
      }
    }
  }
  
  public void removeOnChangeListener(OnChangeListener paramOnChangeListener)
  {
    if (paramOnChangeListener == null) {
      throw new IllegalArgumentException("The listener is null.");
    }
    synchronized (this.mChangeListeners)
    {
      if (!this.mChangeListeners.contains(paramOnChangeListener)) {
        throw new IllegalStateException("Listener " + paramOnChangeListener + " was not registered.");
      }
    }
    this.mChangeListeners.remove(paramOnChangeListener);
  }
  
  public String serialize()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    serialize(localStringBuilder);
    return localStringBuilder.toString();
  }
  
  public void serialize(StringBuilder paramStringBuilder)
  {
    paramStringBuilder.append("version: 1\n");
    int i = 0;
    if (i >= 9) {
      return;
    }
    for (int j = 0;; j++)
    {
      if (j >= 9)
      {
        i++;
        break;
      }
      this.mCells[i][j].serialize(paramStringBuilder);
    }
  }
  
  public boolean validate()
  {
    int i = 0;
    boolean bool = true;
    markAllCellsAsValid();
    this.mOnChangeEnabled = false;
    CellGroup[] arrayOfCellGroup1 = this.mRows;
    int j = arrayOfCellGroup1.length;
    int k = 0;
    CellGroup[] arrayOfCellGroup2;
    int n;
    label46:
    CellGroup[] arrayOfCellGroup3;
    int i1;
    if (k >= j)
    {
      arrayOfCellGroup2 = this.mColumns;
      int m = arrayOfCellGroup2.length;
      n = 0;
      if (n < m) {
        break label99;
      }
      arrayOfCellGroup3 = this.mSectors;
      i1 = arrayOfCellGroup3.length;
    }
    for (;;)
    {
      if (i >= i1)
      {
        this.mOnChangeEnabled = true;
        onChange();
        return bool;
        if (!arrayOfCellGroup1[k].validate()) {
          bool = false;
        }
        k++;
        break;
        label99:
        if (!arrayOfCellGroup2[n].validate()) {
          bool = false;
        }
        n++;
        break label46;
      }
      if (!arrayOfCellGroup3[i].validate()) {
        bool = false;
      }
      i++;
    }
  }
  
  public static abstract interface OnChangeListener
  {
    public abstract void onChange();
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\game\CellCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */