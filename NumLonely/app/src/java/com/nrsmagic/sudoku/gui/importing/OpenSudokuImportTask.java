package com.nrsmagic.sudoku.gui.importing;

import android.content.Context;
import android.net.Uri;
import com.nrsmagic.sudoku.db.SudokuImportParams;
import com.nrsmagic.sudoku.db.SudokuInvalidFormatException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class OpenSudokuImportTask
  extends AbstractImportTask
{
  private Uri mUri;
  
  public OpenSudokuImportTask(Uri paramUri)
  {
    this.mUri = paramUri;
  }
  
  private void importV1(XmlPullParser paramXmlPullParser)
    throws XmlPullParserException, IOException, SudokuInvalidFormatException
  {
    int i = paramXmlPullParser.getEventType();
    String str = "";
    if (i == 1) {
      return;
    }
    if (i == 2)
    {
      str = paramXmlPullParser.getName();
      if (str.equals("game")) {
        importGame(paramXmlPullParser.getAttributeValue(null, "data"));
      }
    }
    for (;;)
    {
      i = paramXmlPullParser.next();
      break;
      if (i == 3) {
        str = "";
      } else if ((i == 4) && (str.equals("name"))) {
        importFolder(paramXmlPullParser.getText());
      }
    }
  }
  
  private void importV2(XmlPullParser paramXmlPullParser)
    throws XmlPullParserException, IOException, SudokuInvalidFormatException
  {
    int i = paramXmlPullParser.getEventType();
    String str = "";
    SudokuImportParams localSudokuImportParams = new SudokuImportParams();
    if (i == 1) {
      return;
    }
    if (i == 2)
    {
      str = paramXmlPullParser.getName();
      if (str.equals("folder")) {
        importFolder(paramXmlPullParser.getAttributeValue(null, "name"), parseLong(paramXmlPullParser.getAttributeValue(null, "created"), System.currentTimeMillis()));
      }
    }
    for (;;)
    {
      i = paramXmlPullParser.next();
      break;
      if (str.equals("game"))
      {
        localSudokuImportParams.clear();
        localSudokuImportParams.created = parseLong(paramXmlPullParser.getAttributeValue(null, "created"), System.currentTimeMillis());
        localSudokuImportParams.state = parseLong(paramXmlPullParser.getAttributeValue(null, "state"), 1L);
        localSudokuImportParams.time = parseLong(paramXmlPullParser.getAttributeValue(null, "time"), 0L);
        localSudokuImportParams.lastPlayed = parseLong(paramXmlPullParser.getAttributeValue(null, "last_played"), 0L);
        localSudokuImportParams.data = paramXmlPullParser.getAttributeValue(null, "data");
        localSudokuImportParams.note = paramXmlPullParser.getAttributeValue(null, "note");
        importGame(localSudokuImportParams);
        continue;
        if (i == 3) {
          str = "";
        } else if (i == 4) {
          str.equals("name");
        }
      }
    }
  }
  
  private void importXml(Reader paramReader)
    throws SudokuInvalidFormatException
  {
    BufferedReader localBufferedReader = new BufferedReader(paramReader);
    int i;
    label103:
    label153:
    do
    {
      try
      {
        XmlPullParserFactory localXmlPullParserFactory = XmlPullParserFactory.newInstance();
        localXmlPullParserFactory.setNamespaceAware(false);
        localXmlPullParser = localXmlPullParserFactory.newPullParser();
        localXmlPullParser.setInput(localBufferedReader);
        i = localXmlPullParser.getEventType();
      }
      catch (XmlPullParserException localXmlPullParserException)
      {
        for (;;)
        {
          XmlPullParser localXmlPullParser;
          String str;
          throw new RuntimeException(localXmlPullParserException);
          setError("Unknown version of data.");
        }
      }
      catch (IOException localIOException)
      {
        throw new RuntimeException(localIOException);
      }
      if (i == 2)
      {
        if (!localXmlPullParser.getName().equals("opensudoku")) {
          break label153;
        }
        str = localXmlPullParser.getAttributeValue(null, "version");
        if (str != null) {
          break label103;
        }
        importV1(localXmlPullParser);
      }
      for (;;)
      {
        i = localXmlPullParser.next();
        break;
        if (str.equals("2"))
        {
          importV2(localXmlPullParser);
        }
        else
        {
          setError(this.mContext.getString(2131296352));
          return;
        }
      }
    } while (i != 1);
  }
  
  private long parseLong(String paramString, long paramLong)
  {
    if (paramString != null) {
      paramLong = Long.parseLong(paramString);
    }
    return paramLong;
  }
  
  /* Error */
  protected void processImport()
    throws SudokuInvalidFormatException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 13	com/nrsmagic/sudoku/gui/importing/OpenSudokuImportTask:mUri	Landroid/net/Uri;
    //   4: invokevirtual 181	android/net/Uri:getScheme	()Ljava/lang/String;
    //   7: ldc -73
    //   9: invokevirtual 41	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   12: ifeq +38 -> 50
    //   15: new 185	java/io/InputStreamReader
    //   18: dup
    //   19: aload_0
    //   20: getfield 159	com/nrsmagic/sudoku/gui/importing/OpenSudokuImportTask:mContext	Landroid/content/Context;
    //   23: invokevirtual 189	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   26: aload_0
    //   27: getfield 13	com/nrsmagic/sudoku/gui/importing/OpenSudokuImportTask:mUri	Landroid/net/Uri;
    //   30: invokevirtual 195	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
    //   33: invokespecial 198	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   36: astore 4
    //   38: aload_0
    //   39: aload 4
    //   41: invokespecial 200	com/nrsmagic/sudoku/gui/importing/OpenSudokuImportTask:importXml	(Ljava/io/Reader;)V
    //   44: aload 4
    //   46: invokevirtual 203	java/io/InputStreamReader:close	()V
    //   49: return
    //   50: new 185	java/io/InputStreamReader
    //   53: dup
    //   54: new 205	java/net/URI
    //   57: dup
    //   58: aload_0
    //   59: getfield 13	com/nrsmagic/sudoku/gui/importing/OpenSudokuImportTask:mUri	Landroid/net/Uri;
    //   62: invokevirtual 181	android/net/Uri:getScheme	()Ljava/lang/String;
    //   65: aload_0
    //   66: getfield 13	com/nrsmagic/sudoku/gui/importing/OpenSudokuImportTask:mUri	Landroid/net/Uri;
    //   69: invokevirtual 208	android/net/Uri:getSchemeSpecificPart	()Ljava/lang/String;
    //   72: aload_0
    //   73: getfield 13	com/nrsmagic/sudoku/gui/importing/OpenSudokuImportTask:mUri	Landroid/net/Uri;
    //   76: invokevirtual 211	android/net/Uri:getFragment	()Ljava/lang/String;
    //   79: invokespecial 214	java/net/URI:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   82: invokevirtual 218	java/net/URI:toURL	()Ljava/net/URL;
    //   85: invokevirtual 224	java/net/URL:openStream	()Ljava/io/InputStream;
    //   88: invokespecial 198	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   91: astore 4
    //   93: goto -55 -> 38
    //   96: astore 5
    //   98: aload 4
    //   100: invokevirtual 203	java/io/InputStreamReader:close	()V
    //   103: aload 5
    //   105: athrow
    //   106: astore_3
    //   107: new 147	java/lang/RuntimeException
    //   110: dup
    //   111: aload_3
    //   112: invokespecial 150	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   115: athrow
    //   116: astore_2
    //   117: new 147	java/lang/RuntimeException
    //   120: dup
    //   121: aload_2
    //   122: invokespecial 150	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   125: athrow
    //   126: astore_1
    //   127: new 147	java/lang/RuntimeException
    //   130: dup
    //   131: aload_1
    //   132: invokespecial 150	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   135: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	136	0	this	OpenSudokuImportTask
    //   126	6	1	localURISyntaxException	java.net.URISyntaxException
    //   116	6	2	localIOException	IOException
    //   106	6	3	localMalformedURLException	java.net.MalformedURLException
    //   36	63	4	localInputStreamReader	java.io.InputStreamReader
    //   96	8	5	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   38	44	96	finally
    //   0	38	106	java/net/MalformedURLException
    //   44	49	106	java/net/MalformedURLException
    //   50	93	106	java/net/MalformedURLException
    //   98	106	106	java/net/MalformedURLException
    //   0	38	116	java/io/IOException
    //   44	49	116	java/io/IOException
    //   50	93	116	java/io/IOException
    //   98	106	116	java/io/IOException
    //   0	38	126	java/net/URISyntaxException
    //   44	49	126	java/net/URISyntaxException
    //   50	93	126	java/net/URISyntaxException
    //   98	106	126	java/net/URISyntaxException
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\importing\OpenSudokuImportTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */