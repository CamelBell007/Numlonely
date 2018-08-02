package com.nrsmagic.sudoku.utils;

import java.util.Collection;
import java.util.Iterator;

public class StringUtils
{
  public static String join(Collection<?> paramCollection, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = paramCollection.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return localStringBuilder.toString();
      }
      localStringBuilder.append(localIterator.next());
      if (localIterator.hasNext()) {
        localStringBuilder.append(paramString);
      }
    }
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\utils\StringUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */