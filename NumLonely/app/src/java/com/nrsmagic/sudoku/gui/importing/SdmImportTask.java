package com.nrsmagic.sudoku.gui.importing;

import android.net.Uri;
import com.nrsmagic.sudoku.db.SudokuInvalidFormatException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class SdmImportTask
  extends AbstractImportTask
{
  private Uri mUri;
  
  public SdmImportTask(Uri paramUri)
  {
    this.mUri = paramUri;
  }
  
  protected void processImport()
    throws SudokuInvalidFormatException
  {
    importFolder(this.mUri.getLastPathSegment());
    for (;;)
    {
      BufferedReader localBufferedReader2;
      try
      {
        localInputStreamReader = new InputStreamReader(new URL(this.mUri.toString()).openStream());
      }
      catch (MalformedURLException localMalformedURLException)
      {
        InputStreamReader localInputStreamReader;
        BufferedReader localBufferedReader1;
        throw new RuntimeException(localMalformedURLException);
      }
      catch (IOException localIOException)
      {
        throw new RuntimeException(localIOException);
      }
      try
      {
        localBufferedReader1 = new BufferedReader(localInputStreamReader);
        try
        {
          String str = localBufferedReader1.readLine();
          if (str == null)
          {
            if (localBufferedReader1 != null) {
              localBufferedReader1.close();
            }
            return;
          }
          if (!str.equals(""))
          {
            importGame(str);
            continue;
            if (localBufferedReader2 == null) {
              continue;
            }
          }
        }
        finally
        {
          localBufferedReader2 = localBufferedReader1;
        }
      }
      finally
      {
        localBufferedReader2 = null;
      }
    }
    localBufferedReader2.close();
    throw ((Throwable)localObject1);
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\importing\SdmImportTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */