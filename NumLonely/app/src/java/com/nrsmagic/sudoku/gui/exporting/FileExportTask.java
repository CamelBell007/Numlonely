package com.nrsmagic.sudoku.gui.exporting;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import java.io.IOException;
import org.xmlpull.v1.XmlSerializer;

public class FileExportTask
  extends AsyncTask<FileExportTaskParams, Integer, Void>
{
  private Context mContext;
  private Handler mGuiHandler;
  private OnExportFinishedListener mOnExportFinishedListener;
  
  public FileExportTask(Context paramContext)
  {
    this.mContext = paramContext;
    this.mGuiHandler = new Handler();
  }
  
  private void attribute(XmlSerializer paramXmlSerializer, String paramString1, Cursor paramCursor, String paramString2)
    throws IllegalArgumentException, IllegalStateException, IOException
  {
    String str = paramCursor.getString(paramCursor.getColumnIndex(paramString2));
    if (str != null) {
      paramXmlSerializer.attribute("", paramString1, str);
    }
  }
  
  /* Error */
  private FileExportTaskResult saveToFile(FileExportTaskParams paramFileExportTaskParams)
  {
    // Byte code:
    //   0: aload_1
    //   1: getfield 60	com/nrsmagic/sudoku/gui/exporting/FileExportTaskParams:folderID	Ljava/lang/Long;
    //   4: ifnonnull +20 -> 24
    //   7: aload_1
    //   8: getfield 63	com/nrsmagic/sudoku/gui/exporting/FileExportTaskParams:sudokuID	Ljava/lang/Long;
    //   11: ifnonnull +13 -> 24
    //   14: new 31	java/lang/IllegalArgumentException
    //   17: dup
    //   18: ldc 65
    //   20: invokespecial 68	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   23: athrow
    //   24: aload_1
    //   25: getfield 60	com/nrsmagic/sudoku/gui/exporting/FileExportTaskParams:folderID	Ljava/lang/Long;
    //   28: ifnull +20 -> 48
    //   31: aload_1
    //   32: getfield 63	com/nrsmagic/sudoku/gui/exporting/FileExportTaskParams:sudokuID	Ljava/lang/Long;
    //   35: ifnull +13 -> 48
    //   38: new 31	java/lang/IllegalArgumentException
    //   41: dup
    //   42: ldc 65
    //   44: invokespecial 68	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   47: athrow
    //   48: aload_1
    //   49: getfield 72	com/nrsmagic/sudoku/gui/exporting/FileExportTaskParams:file	Ljava/io/File;
    //   52: ifnonnull +13 -> 65
    //   55: new 31	java/lang/IllegalArgumentException
    //   58: dup
    //   59: ldc 74
    //   61: invokespecial 68	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   64: athrow
    //   65: invokestatic 80	java/lang/System:currentTimeMillis	()J
    //   68: lstore_2
    //   69: new 82	com/nrsmagic/sudoku/gui/exporting/FileExportTaskResult
    //   72: dup
    //   73: invokespecial 83	com/nrsmagic/sudoku/gui/exporting/FileExportTaskResult:<init>	()V
    //   76: astore 4
    //   78: aload 4
    //   80: iconst_0
    //   81: putfield 87	com/nrsmagic/sudoku/gui/exporting/FileExportTaskResult:successful	Z
    //   84: aconst_null
    //   85: astore 5
    //   87: aconst_null
    //   88: astore 6
    //   90: aconst_null
    //   91: astore 7
    //   93: new 89	java/io/File
    //   96: dup
    //   97: aload_1
    //   98: getfield 72	com/nrsmagic/sudoku/gui/exporting/FileExportTaskParams:file	Ljava/io/File;
    //   101: invokevirtual 93	java/io/File:getParent	()Ljava/lang/String;
    //   104: invokespecial 94	java/io/File:<init>	(Ljava/lang/String;)V
    //   107: astore 8
    //   109: aload 8
    //   111: invokevirtual 98	java/io/File:exists	()Z
    //   114: istore 16
    //   116: aconst_null
    //   117: astore 6
    //   119: aconst_null
    //   120: astore 5
    //   122: aconst_null
    //   123: astore 7
    //   125: iload 16
    //   127: ifne +9 -> 136
    //   130: aload 8
    //   132: invokevirtual 101	java/io/File:mkdirs	()Z
    //   135: pop
    //   136: aload 4
    //   138: aload_1
    //   139: getfield 72	com/nrsmagic/sudoku/gui/exporting/FileExportTaskParams:file	Ljava/io/File;
    //   142: putfield 102	com/nrsmagic/sudoku/gui/exporting/FileExportTaskResult:file	Ljava/io/File;
    //   145: new 104	com/nrsmagic/sudoku/db/SudokuDatabase
    //   148: dup
    //   149: aload_0
    //   150: getfield 18	com/nrsmagic/sudoku/gui/exporting/FileExportTask:mContext	Landroid/content/Context;
    //   153: invokespecial 106	com/nrsmagic/sudoku/db/SudokuDatabase:<init>	(Landroid/content/Context;)V
    //   156: astore 18
    //   158: aload_1
    //   159: getfield 60	com/nrsmagic/sudoku/gui/exporting/FileExportTaskParams:folderID	Ljava/lang/Long;
    //   162: ifnull +228 -> 390
    //   165: aload 18
    //   167: aload_1
    //   168: getfield 60	com/nrsmagic/sudoku/gui/exporting/FileExportTaskParams:folderID	Ljava/lang/Long;
    //   171: invokevirtual 111	java/lang/Long:longValue	()J
    //   174: invokevirtual 115	com/nrsmagic/sudoku/db/SudokuDatabase:exportFolder	(J)Landroid/database/Cursor;
    //   177: astore 6
    //   179: iconst_1
    //   180: istore 20
    //   182: invokestatic 121	android/util/Xml:newSerializer	()Lorg/xmlpull/v1/XmlSerializer;
    //   185: astore 21
    //   187: new 123	java/io/BufferedWriter
    //   190: dup
    //   191: new 125	java/io/FileWriter
    //   194: dup
    //   195: aload 4
    //   197: getfield 102	com/nrsmagic/sudoku/gui/exporting/FileExportTaskResult:file	Ljava/io/File;
    //   200: iconst_0
    //   201: invokespecial 128	java/io/FileWriter:<init>	(Ljava/io/File;Z)V
    //   204: invokespecial 131	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
    //   207: astore 22
    //   209: aload 21
    //   211: aload 22
    //   213: invokeinterface 134 2 0
    //   218: aload 21
    //   220: ldc -120
    //   222: iconst_1
    //   223: invokestatic 142	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   226: invokeinterface 146 3 0
    //   231: aload 21
    //   233: ldc 47
    //   235: ldc -108
    //   237: invokeinterface 152 3 0
    //   242: pop
    //   243: aload 21
    //   245: ldc 47
    //   247: ldc -102
    //   249: ldc -100
    //   251: invokeinterface 52 4 0
    //   256: pop
    //   257: ldc2_w 157
    //   260: lstore 25
    //   262: aload 6
    //   264: invokeinterface 161 1 0
    //   269: ifne +145 -> 414
    //   272: iload 20
    //   274: ifeq +24 -> 298
    //   277: lload 25
    //   279: ldc2_w 157
    //   282: lcmp
    //   283: ifeq +15 -> 298
    //   286: aload 21
    //   288: ldc 47
    //   290: ldc -93
    //   292: invokeinterface 166 3 0
    //   297: pop
    //   298: aload 21
    //   300: ldc 47
    //   302: ldc -108
    //   304: invokeinterface 166 3 0
    //   309: pop
    //   310: aload 6
    //   312: ifnull +10 -> 322
    //   315: aload 6
    //   317: invokeinterface 169 1 0
    //   322: aload 18
    //   324: ifnull +8 -> 332
    //   327: aload 18
    //   329: invokevirtual 170	com/nrsmagic/sudoku/db/SudokuDatabase:close	()V
    //   332: aload 22
    //   334: ifnull +8 -> 342
    //   337: aload 22
    //   339: invokevirtual 173	java/io/Writer:close	()V
    //   342: invokestatic 80	java/lang/System:currentTimeMillis	()J
    //   345: lstore 28
    //   347: iconst_1
    //   348: anewarray 175	java/lang/Object
    //   351: astore 30
    //   353: aload 30
    //   355: iconst_0
    //   356: lload 28
    //   358: lload_2
    //   359: lsub
    //   360: l2f
    //   361: ldc -80
    //   363: fdiv
    //   364: invokestatic 181	java/lang/Float:valueOf	(F)Ljava/lang/Float;
    //   367: aastore
    //   368: ldc -73
    //   370: ldc -71
    //   372: aload 30
    //   374: invokestatic 191	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   377: invokestatic 197	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   380: pop
    //   381: aload 4
    //   383: iconst_1
    //   384: putfield 87	com/nrsmagic/sudoku/gui/exporting/FileExportTaskResult:successful	Z
    //   387: aload 4
    //   389: areturn
    //   390: aload 18
    //   392: aload_1
    //   393: getfield 63	com/nrsmagic/sudoku/gui/exporting/FileExportTaskParams:sudokuID	Ljava/lang/Long;
    //   396: invokevirtual 111	java/lang/Long:longValue	()J
    //   399: invokevirtual 115	com/nrsmagic/sudoku/db/SudokuDatabase:exportFolder	(J)Landroid/database/Cursor;
    //   402: astore 19
    //   404: aload 19
    //   406: astore 6
    //   408: iconst_0
    //   409: istore 20
    //   411: goto -229 -> 182
    //   414: iload 20
    //   416: ifeq +100 -> 516
    //   419: lload 25
    //   421: aload 6
    //   423: aload 6
    //   425: ldc -57
    //   427: invokeinterface 41 2 0
    //   432: invokeinterface 203 2 0
    //   437: lcmp
    //   438: ifeq +78 -> 516
    //   441: lload 25
    //   443: ldc2_w 157
    //   446: lcmp
    //   447: ifeq +15 -> 462
    //   450: aload 21
    //   452: ldc 47
    //   454: ldc -93
    //   456: invokeinterface 166 3 0
    //   461: pop
    //   462: aload 6
    //   464: aload 6
    //   466: ldc -57
    //   468: invokeinterface 41 2 0
    //   473: invokeinterface 203 2 0
    //   478: lstore 25
    //   480: aload 21
    //   482: ldc 47
    //   484: ldc -93
    //   486: invokeinterface 152 3 0
    //   491: pop
    //   492: aload_0
    //   493: aload 21
    //   495: ldc -51
    //   497: aload 6
    //   499: ldc -49
    //   501: invokespecial 209	com/nrsmagic/sudoku/gui/exporting/FileExportTask:attribute	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Landroid/database/Cursor;Ljava/lang/String;)V
    //   504: aload_0
    //   505: aload 21
    //   507: ldc -45
    //   509: aload 6
    //   511: ldc -43
    //   513: invokespecial 209	com/nrsmagic/sudoku/gui/exporting/FileExportTask:attribute	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Landroid/database/Cursor;Ljava/lang/String;)V
    //   516: aload 6
    //   518: aload 6
    //   520: ldc -41
    //   522: invokeinterface 41 2 0
    //   527: invokeinterface 45 2 0
    //   532: ifnull -270 -> 262
    //   535: aload 21
    //   537: ldc 47
    //   539: ldc -39
    //   541: invokeinterface 152 3 0
    //   546: pop
    //   547: aload_0
    //   548: aload 21
    //   550: ldc -45
    //   552: aload 6
    //   554: ldc -45
    //   556: invokespecial 209	com/nrsmagic/sudoku/gui/exporting/FileExportTask:attribute	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Landroid/database/Cursor;Ljava/lang/String;)V
    //   559: aload_0
    //   560: aload 21
    //   562: ldc -37
    //   564: aload 6
    //   566: ldc -37
    //   568: invokespecial 209	com/nrsmagic/sudoku/gui/exporting/FileExportTask:attribute	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Landroid/database/Cursor;Ljava/lang/String;)V
    //   571: aload_0
    //   572: aload 21
    //   574: ldc -35
    //   576: aload 6
    //   578: ldc -35
    //   580: invokespecial 209	com/nrsmagic/sudoku/gui/exporting/FileExportTask:attribute	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Landroid/database/Cursor;Ljava/lang/String;)V
    //   583: aload_0
    //   584: aload 21
    //   586: ldc -33
    //   588: aload 6
    //   590: ldc -33
    //   592: invokespecial 209	com/nrsmagic/sudoku/gui/exporting/FileExportTask:attribute	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Landroid/database/Cursor;Ljava/lang/String;)V
    //   595: aload_0
    //   596: aload 21
    //   598: ldc -41
    //   600: aload 6
    //   602: ldc -41
    //   604: invokespecial 209	com/nrsmagic/sudoku/gui/exporting/FileExportTask:attribute	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Landroid/database/Cursor;Ljava/lang/String;)V
    //   607: aload_0
    //   608: aload 21
    //   610: ldc -31
    //   612: aload 6
    //   614: ldc -29
    //   616: invokespecial 209	com/nrsmagic/sudoku/gui/exporting/FileExportTask:attribute	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Landroid/database/Cursor;Ljava/lang/String;)V
    //   619: aload 21
    //   621: ldc 47
    //   623: ldc -39
    //   625: invokeinterface 166 3 0
    //   630: pop
    //   631: goto -369 -> 262
    //   634: astore 9
    //   636: aload 22
    //   638: astore 7
    //   640: aload 18
    //   642: astore 5
    //   644: ldc -73
    //   646: ldc -27
    //   648: aload 9
    //   650: invokestatic 233	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   653: pop
    //   654: aload 4
    //   656: iconst_0
    //   657: putfield 87	com/nrsmagic/sudoku/gui/exporting/FileExportTaskResult:successful	Z
    //   660: aload 6
    //   662: ifnull +10 -> 672
    //   665: aload 6
    //   667: invokeinterface 169 1 0
    //   672: aload 5
    //   674: ifnull +8 -> 682
    //   677: aload 5
    //   679: invokevirtual 170	com/nrsmagic/sudoku/db/SudokuDatabase:close	()V
    //   682: aload 7
    //   684: ifnull -297 -> 387
    //   687: aload 7
    //   689: invokevirtual 173	java/io/Writer:close	()V
    //   692: aload 4
    //   694: areturn
    //   695: astore 14
    //   697: ldc -73
    //   699: ldc -27
    //   701: aload 14
    //   703: invokestatic 233	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   706: pop
    //   707: aload 4
    //   709: iconst_0
    //   710: putfield 87	com/nrsmagic/sudoku/gui/exporting/FileExportTaskResult:successful	Z
    //   713: aload 4
    //   715: areturn
    //   716: astore 10
    //   718: aload 6
    //   720: ifnull +10 -> 730
    //   723: aload 6
    //   725: invokeinterface 169 1 0
    //   730: aload 5
    //   732: ifnull +8 -> 740
    //   735: aload 5
    //   737: invokevirtual 170	com/nrsmagic/sudoku/db/SudokuDatabase:close	()V
    //   740: aload 7
    //   742: ifnull +8 -> 750
    //   745: aload 7
    //   747: invokevirtual 173	java/io/Writer:close	()V
    //   750: aload 10
    //   752: athrow
    //   753: astore 11
    //   755: ldc -73
    //   757: ldc -27
    //   759: aload 11
    //   761: invokestatic 233	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   764: pop
    //   765: aload 4
    //   767: iconst_0
    //   768: putfield 87	com/nrsmagic/sudoku/gui/exporting/FileExportTaskResult:successful	Z
    //   771: aload 4
    //   773: areturn
    //   774: astore 32
    //   776: ldc -73
    //   778: ldc -27
    //   780: aload 32
    //   782: invokestatic 233	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   785: pop
    //   786: aload 4
    //   788: iconst_0
    //   789: putfield 87	com/nrsmagic/sudoku/gui/exporting/FileExportTaskResult:successful	Z
    //   792: aload 4
    //   794: areturn
    //   795: astore 10
    //   797: aload 18
    //   799: astore 5
    //   801: aconst_null
    //   802: astore 7
    //   804: goto -86 -> 718
    //   807: astore 10
    //   809: aload 22
    //   811: astore 7
    //   813: aload 18
    //   815: astore 5
    //   817: goto -99 -> 718
    //   820: astore 9
    //   822: aconst_null
    //   823: astore 6
    //   825: aconst_null
    //   826: astore 5
    //   828: aconst_null
    //   829: astore 7
    //   831: goto -187 -> 644
    //   834: astore 9
    //   836: aload 18
    //   838: astore 5
    //   840: aconst_null
    //   841: astore 7
    //   843: goto -199 -> 644
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	846	0	this	FileExportTask
    //   0	846	1	paramFileExportTaskParams	FileExportTaskParams
    //   68	291	2	l1	long
    //   76	717	4	localFileExportTaskResult	FileExportTaskResult
    //   85	754	5	localObject1	Object
    //   88	736	6	localObject2	Object
    //   91	751	7	localObject3	Object
    //   107	24	8	localFile	java.io.File
    //   634	15	9	localIOException1	IOException
    //   820	1	9	localIOException2	IOException
    //   834	1	9	localIOException3	IOException
    //   716	35	10	localObject4	Object
    //   795	1	10	localObject5	Object
    //   807	1	10	localObject6	Object
    //   753	7	11	localIOException4	IOException
    //   695	7	14	localIOException5	IOException
    //   114	12	16	bool	boolean
    //   156	681	18	localSudokuDatabase	com.nrsmagic.sudoku.db.SudokuDatabase
    //   402	3	19	localCursor	Cursor
    //   180	235	20	i	int
    //   185	435	21	localXmlSerializer	XmlSerializer
    //   207	603	22	localBufferedWriter	java.io.BufferedWriter
    //   260	219	25	l2	long
    //   345	12	28	l3	long
    //   351	22	30	arrayOfObject	Object[]
    //   774	7	32	localIOException6	IOException
    // Exception table:
    //   from	to	target	type
    //   209	257	634	java/io/IOException
    //   262	272	634	java/io/IOException
    //   286	298	634	java/io/IOException
    //   298	310	634	java/io/IOException
    //   419	441	634	java/io/IOException
    //   450	462	634	java/io/IOException
    //   462	516	634	java/io/IOException
    //   516	631	634	java/io/IOException
    //   687	692	695	java/io/IOException
    //   93	116	716	finally
    //   130	136	716	finally
    //   136	158	716	finally
    //   644	660	716	finally
    //   745	750	753	java/io/IOException
    //   337	342	774	java/io/IOException
    //   158	179	795	finally
    //   182	209	795	finally
    //   390	404	795	finally
    //   209	257	807	finally
    //   262	272	807	finally
    //   286	298	807	finally
    //   298	310	807	finally
    //   419	441	807	finally
    //   450	462	807	finally
    //   462	516	807	finally
    //   516	631	807	finally
    //   93	116	820	java/io/IOException
    //   130	136	820	java/io/IOException
    //   136	158	820	java/io/IOException
    //   158	179	834	java/io/IOException
    //   182	209	834	java/io/IOException
    //   390	404	834	java/io/IOException
  }
  
  protected Void doInBackground(FileExportTaskParams... paramVarArgs)
  {
    int i = paramVarArgs.length;
    for (int j = 0;; j++)
    {
      if (j >= i) {
        return null;
      }
      final FileExportTaskResult localFileExportTaskResult = saveToFile(paramVarArgs[j]);
      this.mGuiHandler.post(new Runnable()
      {
        public void run()
        {
          if (FileExportTask.this.mOnExportFinishedListener != null) {
            FileExportTask.this.mOnExportFinishedListener.onExportFinished(localFileExportTaskResult);
          }
        }
      });
    }
  }
  
  public OnExportFinishedListener getOnExportFinishedListener()
  {
    return this.mOnExportFinishedListener;
  }
  
  public void setOnExportFinishedListener(OnExportFinishedListener paramOnExportFinishedListener)
  {
    this.mOnExportFinishedListener = paramOnExportFinishedListener;
  }
  
  public static abstract interface OnExportFinishedListener
  {
    public abstract void onExportFinished(FileExportTaskResult paramFileExportTaskResult);
  }
}


/* Location:              E:\反编译工程\反编译工具包\反编译工具包\dex2jar-0.0.9.15\dex2jar-0.0.9.15\项目APP\数独 1.1.8\classes_dex2jar.jar!\com\nrsmagic\sudoku\gui\exporting\FileExportTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */