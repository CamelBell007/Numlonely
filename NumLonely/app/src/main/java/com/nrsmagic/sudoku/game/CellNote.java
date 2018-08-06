package com.nrsmagic.sudoku.game;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

public class CellNote
{
  public static final CellNote EMPTY = new CellNote();
  private final Set<Integer> mNotedNumbers;
  
  public CellNote()
  {
    this.mNotedNumbers = Collections.unmodifiableSet(new HashSet());
  }
  
  private CellNote(Set<Integer> paramSet)
  {
    this.mNotedNumbers = Collections.unmodifiableSet(paramSet);
  }
  
  public static CellNote deserialize(String paramString)
  {
    HashSet localHashSet = new HashSet();
    StringTokenizer localStringTokenizer;
    if ((paramString != null) && (!paramString.equals(""))) {
      localStringTokenizer = new StringTokenizer(paramString, ",");
    }
    for (;;)
    {
      if (!localStringTokenizer.hasMoreTokens()) {
        return new CellNote(localHashSet);
      }
      String str = localStringTokenizer.nextToken();
      if (!str.equals("-")) {
        localHashSet.add(Integer.valueOf(Integer.parseInt(str)));
      }
    }
  }
  
  public static CellNote fromIntArray(Integer[] paramArrayOfInteger)
  {
    HashSet localHashSet = new HashSet();
    int i = paramArrayOfInteger.length;
    for (int j = 0;; j++)
    {
      if (j >= i) {
        return new CellNote(localHashSet);
      }
      localHashSet.add(paramArrayOfInteger[j]);
    }
  }
  
  public CellNote addNumber(int paramInt)
  {
    if ((paramInt < 1) || (paramInt > 9)) {
      throw new IllegalArgumentException("Number must be between 1-9.");
    }
    HashSet localHashSet = new HashSet(getNotedNumbers());
    localHashSet.add(Integer.valueOf(paramInt));
    return new CellNote(localHashSet);
  }
  
  public CellNote clear()
  {
    return new CellNote();
  }
  
  public Set<Integer> getNotedNumbers()
  {
    return this.mNotedNumbers;
  }
  
  public boolean isEmpty()
  {
    return this.mNotedNumbers.size() == 0;
  }
  
  public CellNote removeNumber(int paramInt)
  {
    if ((paramInt < 1) || (paramInt > 9)) {
      throw new IllegalArgumentException("Number must be between 1-9.");
    }
    HashSet localHashSet = new HashSet(getNotedNumbers());
    localHashSet.remove(Integer.valueOf(paramInt));
    return new CellNote(localHashSet);
  }
  
  public String serialize()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    serialize(localStringBuilder);
    return localStringBuilder.toString();
  }
  
  public void serialize(StringBuilder paramStringBuilder)
  {
    if (this.mNotedNumbers.size() == 0) {
      paramStringBuilder.append("-");
    }
    for (;;)
    {
      return;
      Iterator localIterator = this.mNotedNumbers.iterator();
      while (localIterator.hasNext()) {
        paramStringBuilder.append((Integer)localIterator.next()).append(",");
      }
    }
  }
  
  public CellNote toggleNumber(int paramInt)
  {
    if ((paramInt < 1) || (paramInt > 9)) {
      throw new IllegalArgumentException("Number must be between 1-9.");
    }
    HashSet localHashSet = new HashSet(getNotedNumbers());
    if (localHashSet.contains(Integer.valueOf(paramInt))) {
      localHashSet.remove(Integer.valueOf(paramInt));
    }
    for (;;)
    {
      return new CellNote(localHashSet);
      localHashSet.add(Integer.valueOf(paramInt));
    }
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\game\CellNote.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */