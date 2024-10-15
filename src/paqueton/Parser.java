//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"
	package paqueton;
	import java.io.*;
	import java.util.ArrayList;
	
//#line 22 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ID=257;
public final static short CTE=258;
public final static short MASI=259;
public final static short MENOSI=260;
public final static short ASIGN=261;
public final static short DIST=262;
public final static short GOTO=263;
public final static short UP=264;
public final static short DOWN=265;
public final static short TRIPLE=266;
public final static short FOR=267;
public final static short ULONGINT=268;
public final static short DOUBLE=269;
public final static short IF=270;
public final static short THEN=271;
public final static short ELSE=272;
public final static short BEGIN=273;
public final static short END=274;
public final static short END_IF=275;
public final static short OUTF=276;
public final static short TYPEDEF=277;
public final static short FUN=278;
public final static short RET=279;
public final static short CADMUL=280;
public final static short TAG=281;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    2,    2,    2,    1,    1,    1,    1,    1,
    3,    3,    3,    3,    4,    4,    5,    5,    5,    5,
    6,    6,    6,    6,    6,    6,   16,   16,   16,   16,
   17,   17,   17,   17,   17,   17,   17,   20,   21,   21,
   12,   12,   12,   12,   12,   12,   12,   24,   22,   25,
   26,   19,   19,   19,   19,   19,   19,   23,   23,   23,
   23,   23,   28,   28,   28,   28,   28,   27,   27,   27,
    7,    7,    7,   31,   32,   32,   30,   30,   30,   29,
   29,   10,   10,   34,   34,   18,   18,   18,   18,   18,
   18,   18,   18,   35,   35,   35,   35,   35,   35,   35,
   35,   35,   36,   36,   36,   36,   37,   37,   33,   41,
    8,   42,    8,   43,    8,   38,   38,   39,   39,   44,
   44,   44,   44,   45,   40,   46,   46,   46,   47,   47,
   48,   11,   11,   49,   49,   50,   50,   13,   13,   13,
   51,   51,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   52,   52,   15,   15,    9,    9,    9,    9,    9,
    9,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    3,    4,    3,    3,    3,    2,
    3,    1,    2,    2,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    3,    1,    2,    2,
    3,    7,    5,    6,    6,    7,    3,    3,    3,    1,
    5,    7,    4,    4,    4,    6,    5,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    4,    2,    3,
    2,    3,    4,    2,    3,    2,    3,    3,    1,    2,
    2,    3,    1,    1,    3,    1,    3,    1,    3,    1,
    1,    3,    3,    1,    1,    3,    3,    1,    2,    4,
    4,    4,    4,    3,    3,    1,    2,    2,    4,    4,
    4,    4,    1,    1,    1,    1,    1,    2,    4,    0,
    8,    0,    7,    0,    7,    1,    1,    3,    1,    2,
    2,    1,    1,    0,    5,    3,    1,    2,    1,    1,
    4,    4,    3,    3,    1,    2,    1,    4,    3,    4,
    1,    1,   12,   11,   11,   10,   11,   11,   10,   10,
   10,    1,    1,    2,    3,    6,    5,    5,    4,    5,
    7,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    0,    0,    0,   81,   80,
    0,    0,    0,    0,   19,    1,    0,   12,   15,   16,
   17,   18,   20,   21,   22,   23,   24,   25,   26,    0,
   73,    0,    0,    0,    0,    0,   13,    0,    0,    0,
    0,    0,  154,    0,    0,  107,    0,    0,    0,    0,
    0,  105,   49,    0,    0,    0,  106,    0,   96,  104,
    0,    0,    0,    0,    0,    0,   14,   78,    0,    0,
    0,    0,    0,    4,    0,   85,    0,   83,  133,    0,
  137,    0,  135,    0,    0,  155,    0,    0,    0,    0,
    0,    0,  108,   97,   98,   30,    0,   52,   53,   54,
   55,   56,   57,    0,    0,    0,    0,    0,    0,    0,
    7,    0,  142,  139,  141,    0,    0,    0,    0,    9,
   11,    0,    0,   75,   82,    0,    0,    3,    0,    0,
  132,    0,  109,    0,   27,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   50,    0,   48,    0,    0,
   45,    0,    0,   94,    0,   95,    6,  140,  138,    0,
  159,    0,    0,   79,   77,    0,    0,    0,    0,  119,
  134,    0,    0,    0,    0,    0,    0,   91,   90,   93,
   92,   61,    0,   69,    0,   59,   41,    0,   51,   47,
  101,  100,  102,   99,  158,    0,  157,  160,  114,    0,
  121,  120,  112,    0,    0,    0,    0,    0,    0,    0,
   33,   60,   62,    0,   70,    0,   46,    0,  156,  124,
  110,  124,  118,    0,  152,  153,    0,    0,    0,    0,
   34,   35,   58,   68,   42,  161,  115,    0,  124,  113,
    0,    0,    0,    0,    0,   36,   32,    0,  111,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  130,    0,
  127,  129,    0,    0,  149,    0,  151,    0,    0,    0,
  150,    0,  146,    0,    0,  128,   66,    0,    0,   64,
  147,    0,  148,  144,  145,    0,  125,  126,   65,   67,
    0,  143,  131,   63,
};
final static short yydgoto[] = {                          3,
   16,    4,   17,   67,   19,   20,   21,   22,   23,   24,
   25,   26,   27,   28,   29,   53,   76,   77,  106,   90,
   91,   56,  150,  151,  152,  190,  185,  265,   30,   69,
   31,   32,   33,   81,   58,   59,   60,   34,  169,  237,
  239,  222,  220,  170,  238,  260,  261,  262,   82,   83,
  116,  228,
};
final static short yysindex[] = {                      -242,
  622,  637,    0,    0,  -47,   -6, -212,   35,    0,    0,
  458,  637,   63,  -20,    0,    0,  436,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -237,
    0,   64, -131, -129,  451, -105,    0,  538,  408, -237,
  589,  134,    0, -114,  -30,    0,  538,  452,  -44,   14,
   14,    0,    0,  178,  568,  -69,    0,   45,    0,    0,
  467,  -21,  280,  -56,  -94,  717,    0,    0,    2,    6,
  538,  -13,  732,    0,  589,    0,  568,    0,    0,  589,
    0,   83,    0,    2,   31,    0,   -5,  216,  568,   84,
  229,   45,    0,    0,    0,    0,  589,    0,    0,    0,
    0,    0,    0,   59,  593,  589,  680,  233,  251,  747,
    0,  234,    0,    0,    0,  236,  -94,  -48,  217,    0,
    0,   28,   44,    0,    0,  263,  149,    0,  158,  158,
    0,  385,    0,   49,    0,  608,  559,  589,  158,   27,
   45,   72,   45,  158,    3,    0,  490,    0,  252, -135,
    0,   39,   98,    0,  100,    0,    0,    0,    0,  -46,
    0,   62,   69,    0,    0,   -8,   77,   82,  123,    0,
    0,  414,  301,  580,  589,  304,  158,    0,    0,    0,
    0,    0,   78,    0,  498,    0,    0,  777,    0,    0,
    0,    0,    0,    0,    0,  133,    0,    0,    0,  127,
    0,    0,    0,  149,  458,  249,  589,  589,  328,  330,
    0,    0,    0,  645,    0,   39,    0,  324,    0,    0,
    0,    0,    0,  511,    0,    0,  173,  137,  351,  356,
    0,    0,    0,    0,    0,    0,    0,  138,    0,    0,
  359,  -28,   -4,  162,  390,    0,    0,  762,    0,  -76,
  398,  -76,    4,  405,  -76,  411,  -76,  418,    0,  421,
    0,    0,  510,  401,    0,  -76,    0,  438,  -76,  -76,
    0,  -76,    0,  538,  702,    0,    0,  188,  531,    0,
    0,  -76,    0,    0,    0,  441,    0,    0,    0,    0,
  788,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,  166,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  209,
    0,  277,    0,    0,    0,  482,    0,    0,    0,    0,
    0,    0,    0,    0,   91,    0,    0,    0,    0,    0,
    0,    0,    0,   18,    0,    0,    0,  -36,    0,    0,
    0,  482,    0,    0,    0,  485,    0,    0,  298,    0,
    0,    0,  492,    0,    0,    0,   61,    0,    0,    0,
    0,    0,    0,  319,    0,    0,    0,  409,  449,    0,
    0,  117,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  496,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  449,  129,
    0,    0,    0,    0,    0,    0,    0,    0,   23,    0,
  145,    0,  207,  254,    0,    0,    0,    0,    0,  340,
    0,  382,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  139,  151,    0,    0,
    0,    0,    0,    0,    0,    0,   50,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   51,   -1,    0,  672,    0,    0,    0,    0,
   -9,    0,    0,    0,    0, -155,  161,  759,  -84,  -53,
    0,    0,  314,  354,  365, -169,  243,  563,  744,  476,
    0,    0,   10,  -35,  -26,  255,    0,    0,  361, -204,
    0,    0,    0,  316,    0,    0, -205,    0,    0,  397,
    0, -144,
};
final static int YYTABLESIZE=1064;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   36,   52,   78,  117,   88,  137,   88,   88,   88,   39,
   62,   37,  252,  162,    1,  196,  206,  240,  217,   68,
   57,   92,   88,   88,   88,   88,  127,  115,   52,   52,
    2,   52,  199,   39,  249,  125,  255,   52,   52,   65,
   52,   52,   39,   42,  269,  123,  235,   57,   57,  224,
   57,  174,   35,   52,  276,   40,   57,   57,   49,   57,
   57,   52,   61,   37,  121,   52,   37,  179,   43,  288,
   52,  121,   57,  104,   44,  105,   28,  141,  143,  243,
   57,   37,  244,  176,   57,  178,  108,   52,   88,   57,
   38,  109,   41,   39,   52,   52,   52,  253,   52,   52,
   50,   84,   63,   49,   84,   51,   57,   70,  121,   38,
   38,   38,  181,   57,   57,   57,   41,   57,   57,   84,
  209,  210,   52,  131,  136,   41,  132,   52,   52,   71,
  180,  103,  103,  103,  103,  103,  146,  103,  192,  148,
  194,   57,   87,  102,  101,  103,   57,   57,   72,  103,
  103,  103,  103,  229,  230,  133,  191,   89,  193,   89,
   89,   89,   52,  203,   52,   52,  204,  221,   74,  136,
  204,   54,  136,    9,   10,   89,   89,   89,   89,  123,
  145,   57,  123,   57,   57,   86,    7,   86,   86,   86,
    8,  122,   86,   11,  122,   52,  263,   52,   52,   13,
  104,  107,  105,   86,   86,   86,   86,   88,  161,   76,
  195,    9,   10,   93,   57,  103,   57,   57,   96,   88,
   88,   88,   88,   88,   76,   88,   88,   88,   88,  251,
   88,   88,   88,   88,   88,  225,  226,   88,  286,   88,
   88,   89,   88,  126,   88,   64,  259,   87,  167,   87,
   87,   87,  111,  254,   38,  134,  135,  122,  259,    9,
   10,  268,  124,   38,   52,   87,   87,   87,   87,   86,
   45,   46,  138,  259,  158,   28,  159,   49,  163,   37,
   37,   28,   28,   57,  164,   37,   37,   37,   28,   37,
   37,   37,   37,   37,   31,   49,   37,   31,   37,   37,
  165,   37,  166,   37,   94,   95,  172,  227,   38,   38,
  186,   38,   31,  189,  140,   45,   46,   84,  197,   75,
  114,   50,   48,   84,   49,  198,   51,   84,   84,   84,
   84,   87,   54,  201,   84,   74,   84,   84,  202,   84,
  207,   84,   98,   99,  211,  100,  103,  103,  103,  103,
  103,  212,  103,  103,  103,  103,   71,  103,  103,  103,
  103,  103,  154,  156,  103,   54,  103,  103,  231,  103,
  232,  103,   89,   89,   89,   89,   89,   72,   89,   89,
   89,   89,  236,   89,   89,   89,   89,   89,  218,  219,
   89,  246,   89,   89,  245,   89,  247,   89,   43,  250,
   86,   86,   86,   86,   86,  167,   86,   86,   86,   86,
  248,   86,   86,   86,   86,   86,    9,   10,   86,  256,
   86,   86,   76,   86,   75,   86,   50,   48,   76,   49,
  257,   51,   76,   76,   76,   76,  225,  226,  266,   76,
   44,   76,   76,  117,   76,  270,   76,   75,   79,   50,
   48,  272,   49,   47,   51,   50,   48,  274,   49,  280,
   51,  289,   87,   87,   87,   87,   87,   29,   87,   87,
   87,   87,  205,   87,   87,   87,   87,   87,  282,  275,
   87,  293,   87,   87,   10,   87,  116,   87,  153,   45,
   46,    5,   40,   50,   66,    8,   49,   47,   51,   50,
   48,  216,   49,  187,   51,  279,  155,   45,   46,   73,
   31,   31,  225,  226,  188,   84,   31,   31,   31,  223,
   31,   31,   31,   31,   31,  110,  200,   31,  171,   31,
   31,    0,   31,   74,   31,  112,   45,   46,    0,   74,
   12,    0,    0,   74,   74,   74,   74,    0,  183,    0,
   74,    0,   74,   74,   71,   74,  214,   74,    0,  113,
   71,    0,    0,    0,   71,   71,   71,   71,  278,  242,
    0,   71,    0,   71,   71,   72,   71,   75,   71,   50,
   48,   72,   49,    0,   51,   72,   72,   72,   72,  291,
    0,    0,   72,    0,   72,   72,   43,   72,  175,   72,
   50,   48,   43,   49,    0,   51,   43,   43,   43,   43,
  104,    0,  105,   43,    0,   43,   43,    0,   43,  208,
   43,   50,   48,    0,   49,    0,   51,  102,  101,  103,
   50,   48,    0,   49,   50,   51,    0,   49,   44,   51,
    0,   45,   46,    0,   44,    0,    0,    0,   44,   44,
   44,   44,    9,   10,    0,   44,    0,   44,   44,    0,
   44,    0,   44,    0,   45,   46,   29,  102,  101,  103,
   45,   46,   29,   29,    0,    9,   10,    6,    0,   29,
    0,    0,    0,    7,    0,    0,    0,    8,    9,   10,
   11,    0,    6,    0,    0,    0,   13,   14,    7,  258,
    0,   15,    8,    9,   10,   11,    0,    6,   45,   46,
    0,   13,   14,    7,   45,   46,   15,    8,    9,   10,
   11,    0,    0,    6,    0,    0,   13,   14,    0,    7,
    0,   15,    0,    8,    9,   10,   11,    0,   12,    0,
    0,    0,   13,   14,   12,    0,  145,   15,   12,   12,
   12,   12,    7,    0,  145,    0,    8,   12,   12,   11,
    7,    0,   12,  182,    8,   13,  145,   11,  241,   55,
    0,  213,    7,   13,  225,  226,    8,    0,  149,   11,
    0,    0,   80,  277,    0,   13,    0,  145,    0,    0,
    0,    0,    0,    7,   45,   46,    0,    8,    0,   85,
   11,    0,    0,    0,  290,   89,   13,  118,  119,    0,
    0,    0,    0,    0,  267,   45,   46,  271,  184,  273,
    0,    0,    0,   97,    0,    0,   98,   99,  281,  100,
    0,  283,  284,  129,  285,    0,   45,   46,  130,    0,
    0,    0,    0,    0,  292,   45,   46,    0,  142,   45,
   46,    0,    0,    0,    0,  139,  215,    0,    0,  149,
  160,    0,    0,  173,  144,    0,   98,   99,    0,  100,
  168,    0,    0,    0,    0,   80,    0,    5,    6,    0,
    0,    0,    0,    0,    7,  234,    0,    0,    8,    9,
   10,   11,    5,    6,   12,  129,  177,   13,   14,    7,
    0,  145,   15,    8,    9,   10,   11,    7,    0,  168,
    0,    8,   13,   14,   11,    0,    0,   15,  233,    0,
   13,  264,    0,  264,    0,    0,  264,    0,  264,    0,
   55,    0,  129,  129,  184,    0,  145,  264,    0,    0,
  264,  264,    7,  264,    0,    0,    8,  168,    0,   11,
  215,  146,  147,  264,  148,   13,    0,    0,    6,    0,
    0,    0,  234,   55,    7,  129,  129,    0,    8,    9,
   10,   11,    0,    6,    0,  287,    0,   13,   14,    7,
  258,    0,   15,    8,    9,   10,   11,    0,    6,    0,
  120,    0,   13,   14,    7,    0,    0,   15,    8,    9,
   10,   11,    0,    6,    0,  128,    0,   13,   14,    7,
    0,    0,   15,    8,    9,   10,   11,    0,    6,    0,
  157,    0,   13,   14,    7,    0,    0,   15,    8,    9,
   10,   11,    0,  145,    0,    0,    0,   13,   14,    7,
  258,    0,   15,    8,  145,    0,   11,    0,    0,  147,
    7,  189,   13,    0,    8,    0,    0,   11,    0,    0,
    0,  294,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          1,
    2,   11,   38,   60,   41,   90,   43,   44,   45,   40,
   12,   59,   41,   62,  257,   62,  172,  222,  188,  257,
   11,   48,   59,   60,   61,   62,   40,   63,   38,   39,
  273,   41,   41,   40,  239,   71,   41,   47,   48,   60,
   50,   51,   40,  256,   41,   44,  216,   38,   39,  205,
   41,  136,    2,   63,  260,   62,   47,   48,   45,   50,
   51,   71,   12,   41,   66,   75,   44,   41,  281,  275,
   80,   73,   63,   43,   40,   45,   59,  104,  105,  224,
   71,   59,  227,  137,   75,   59,   42,   97,  125,   80,
   41,   47,  123,   44,  104,  105,  106,  242,  108,  109,
   42,   41,   40,   45,   44,   47,   97,   44,  110,   60,
   61,   62,   41,  104,  105,  106,  123,  108,  109,   59,
  174,  175,  132,   41,   41,  123,   44,  137,  138,  261,
   59,   41,   42,   43,   44,   45,  272,   47,   41,  275,
   41,  132,  257,   60,   61,   62,  137,  138,  278,   59,
   60,   61,   62,  207,  208,  125,   59,   41,   59,   43,
   44,   45,  172,   41,  174,  175,   44,   41,  274,   41,
   44,   11,   44,  268,  269,   59,   60,   61,   62,   41,
  257,  172,   44,  174,  175,   41,  263,   43,   44,   45,
  267,   41,   59,  270,   44,  205,  273,  207,  208,  276,
   43,  271,   45,   59,   60,   61,   62,   47,  257,   44,
  257,  268,  269,  258,  205,  125,  207,  208,   41,  256,
  257,  258,  259,  260,   59,  262,  263,  264,  265,  258,
  267,  268,  269,  270,  271,  264,  265,  274,  274,  276,
  277,  125,  279,  257,  281,  266,  248,   41,  257,   43,
   44,   45,  274,  258,  261,  261,   41,  256,  260,  268,
  269,  258,  257,  261,  274,   59,   60,   61,   62,  125,
  257,  258,   44,  275,   41,  258,   41,   45,   62,  257,
  258,  264,  265,  274,  257,  263,  264,  265,  271,  267,
  268,  269,  270,  271,   41,   45,  274,   44,  276,  277,
  257,  279,   40,  281,   50,   51,  258,   59,  259,  260,
   59,  262,   59,  275,  256,  257,  258,  257,  257,   40,
   41,   42,   43,  263,   45,  257,   47,  267,  268,  269,
  270,  125,  172,  257,  274,   59,  276,  277,  257,  279,
   40,  281,  259,  260,   41,  262,  256,  257,  258,  259,
  260,  274,  262,  263,  264,  265,   59,  267,  268,  269,
  270,  271,  108,  109,  274,  205,  276,  277,   41,  279,
   41,  281,  256,  257,  258,  259,  260,   59,  262,  263,
  264,  265,   59,  267,  268,  269,  270,  271,  256,  257,
  274,   41,  276,  277,  258,  279,   41,  281,   59,   41,
  256,  257,  258,  259,  260,  257,  262,  263,  264,  265,
  273,  267,  268,  269,  270,  271,  268,  269,  274,  258,
  276,  277,  257,  279,   40,  281,   42,   43,  263,   45,
   41,   47,  267,  268,  269,  270,  264,  265,   41,  274,
   59,  276,  277,  278,  279,   41,  281,   40,   41,   42,
   43,   41,   45,   40,   47,   42,   43,   40,   45,   59,
   47,  274,  256,  257,  258,  259,  260,   59,  262,  263,
  264,  265,   59,  267,  268,  269,  270,  271,   41,   59,
  274,   41,  276,  277,    0,  279,  278,  281,  256,  257,
  258,    0,   44,   42,   59,    0,   45,   40,   47,   42,
   43,  188,   45,  150,   47,  263,  256,  257,  258,   59,
  257,  258,  264,  265,  150,   40,  263,  264,  265,  204,
  267,  268,  269,  270,  271,   59,  166,  274,  132,  276,
  277,   -1,  279,  257,  281,  256,  257,  258,   -1,  263,
   59,   -1,   -1,  267,  268,  269,  270,   -1,   59,   -1,
  274,   -1,  276,  277,  257,  279,   59,  281,   -1,  280,
  263,   -1,   -1,   -1,  267,  268,  269,  270,   59,   59,
   -1,  274,   -1,  276,  277,  257,  279,   40,  281,   42,
   43,  263,   45,   -1,   47,  267,  268,  269,  270,   59,
   -1,   -1,  274,   -1,  276,  277,  257,  279,   40,  281,
   42,   43,  263,   45,   -1,   47,  267,  268,  269,  270,
   43,   -1,   45,  274,   -1,  276,  277,   -1,  279,   40,
  281,   42,   43,   -1,   45,   -1,   47,   60,   61,   62,
   42,   43,   -1,   45,   42,   47,   -1,   45,  257,   47,
   -1,  257,  258,   -1,  263,   -1,   -1,   -1,  267,  268,
  269,  270,  268,  269,   -1,  274,   -1,  276,  277,   -1,
  279,   -1,  281,   -1,  257,  258,  258,   60,   61,   62,
  257,  258,  264,  265,   -1,  268,  269,  257,   -1,  271,
   -1,   -1,   -1,  263,   -1,   -1,   -1,  267,  268,  269,
  270,   -1,  257,   -1,   -1,   -1,  276,  277,  263,  279,
   -1,  281,  267,  268,  269,  270,   -1,  257,  257,  258,
   -1,  276,  277,  263,  257,  258,  281,  267,  268,  269,
  270,   -1,   -1,  257,   -1,   -1,  276,  277,   -1,  263,
   -1,  281,   -1,  267,  268,  269,  270,   -1,  257,   -1,
   -1,   -1,  276,  277,  263,   -1,  257,  281,  267,  268,
  269,  270,  263,   -1,  257,   -1,  267,  276,  277,  270,
  263,   -1,  281,  274,  267,  276,  257,  270,  258,   11,
   -1,  274,  263,  276,  264,  265,  267,   -1,  107,  270,
   -1,   -1,   39,  274,   -1,  276,   -1,  257,   -1,   -1,
   -1,   -1,   -1,  263,  257,  258,   -1,  267,   -1,   41,
  270,   -1,   -1,   -1,  274,   47,  276,   64,   65,   -1,
   -1,   -1,   -1,   -1,  252,  257,  258,  255,  147,  257,
   -1,   -1,   -1,  256,   -1,   -1,  259,  260,  266,  262,
   -1,  269,  270,   75,  272,   -1,  257,  258,   80,   -1,
   -1,   -1,   -1,   -1,  282,  257,  258,   -1,  256,  257,
  258,   -1,   -1,   -1,   -1,   97,  185,   -1,   -1,  188,
  117,   -1,   -1,  256,  106,   -1,  259,  260,   -1,  262,
  127,   -1,   -1,   -1,   -1,  132,   -1,  256,  257,   -1,
   -1,   -1,   -1,   -1,  263,  214,   -1,   -1,  267,  268,
  269,  270,  256,  257,  273,  137,  138,  276,  277,  263,
   -1,  257,  281,  267,  268,  269,  270,  263,   -1,  166,
   -1,  267,  276,  277,  270,   -1,   -1,  281,  274,   -1,
  276,  250,   -1,  252,   -1,   -1,  255,   -1,  257,   -1,
  172,   -1,  174,  175,  263,   -1,  257,  266,   -1,   -1,
  269,  270,  263,  272,   -1,   -1,  267,  204,   -1,  270,
  279,  272,  273,  282,  275,  276,   -1,   -1,  257,   -1,
   -1,   -1,  291,  205,  263,  207,  208,   -1,  267,  268,
  269,  270,   -1,  257,   -1,  274,   -1,  276,  277,  263,
  279,   -1,  281,  267,  268,  269,  270,   -1,  257,   -1,
  274,   -1,  276,  277,  263,   -1,   -1,  281,  267,  268,
  269,  270,   -1,  257,   -1,  274,   -1,  276,  277,  263,
   -1,   -1,  281,  267,  268,  269,  270,   -1,  257,   -1,
  274,   -1,  276,  277,  263,   -1,   -1,  281,  267,  268,
  269,  270,   -1,  257,   -1,   -1,   -1,  276,  277,  263,
  279,   -1,  281,  267,  257,   -1,  270,   -1,   -1,  273,
  263,  275,  276,   -1,  267,   -1,   -1,  270,   -1,   -1,
   -1,  274,   -1,  276,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=281;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"ID","CTE","MASI","MENOSI","ASIGN","DIST",
"GOTO","UP","DOWN","TRIPLE","FOR","ULONGINT","DOUBLE","IF","THEN","ELSE",
"BEGIN","END","END_IF","OUTF","TYPEDEF","FUN","RET","CADMUL","TAG",
};
final static String yyrule[] = {
"$accept : prog",
"prog : ID cuerpo",
"prog : cuerpo_error",
"cuerpo_error : BEGIN sentencias ';' END",
"cuerpo_error : BEGIN sentencia END",
"cuerpo_error : BEGIN sentencias ';'",
"cuerpo : BEGIN sentencias ';' END",
"cuerpo : BEGIN sentencia END",
"cuerpo : BEGIN sentencias ';'",
"cuerpo : sentencias ';' END",
"cuerpo : sentencias ';'",
"sentencias : sentencias ';' sentencia",
"sentencias : sentencia",
"sentencias : error ';'",
"sentencias : sentencias sentencia",
"sentencia : sentec_declar",
"sentencia : sentec_eject",
"sentec_declar : declaracion_var",
"sentec_declar : declaracion_fun",
"sentec_declar : TAG",
"sentec_declar : declar_tipo_trip",
"sentec_eject : asignacion",
"sentec_eject : invoc_fun",
"sentec_eject : seleccion",
"sentec_eject : sald_mensaj",
"sentec_eject : for",
"sentec_eject : goto",
"condicion : '(' condicion_2 ')'",
"condicion : condicion_2",
"condicion : '(' condicion_2",
"condicion : condicion_2 ')'",
"condicion_2 : expresion_matematica comparador expresion_matematica",
"condicion_2 : '(' patron ')' comparador '(' patron ')'",
"condicion_2 : '(' patron comparador patron ')'",
"condicion_2 : '(' patron ')' comparador patron ')'",
"condicion_2 : '(' patron comparador '(' patron ')'",
"condicion_2 : '(' patron ')' error '(' patron ')'",
"condicion_2 : expresion_matematica error expresion_matematica",
"patron : lista_patron ',' expresion_matematica",
"lista_patron : lista_patron ',' expresion_matematica",
"lista_patron : expresion_matematica",
"seleccion : IF condicion_punto_control THEN cuerpo_control sinelse_punto_control",
"seleccion : IF condicion_punto_control THEN cuerpo_control else_punto_control cuerpo_control endif_punto_control",
"seleccion : IF condicion_punto_control THEN cuerpo_control",
"seleccion : IF condicion_punto_control THEN else_punto_control",
"seleccion : IF condicion_punto_control THEN sinelse_punto_control",
"seleccion : IF condicion_punto_control THEN cuerpo_control else_punto_control endif_punto_control",
"seleccion : IF condicion_punto_control THEN else_punto_control endif_punto_control",
"sinelse_punto_control : END_IF",
"condicion_punto_control : condicion",
"else_punto_control : ELSE",
"endif_punto_control : END_IF",
"comparador : MASI",
"comparador : MENOSI",
"comparador : DIST",
"comparador : '='",
"comparador : '<'",
"comparador : '>'",
"cuerpo_control : BEGIN multip_cuerp_fun ';' END",
"cuerpo_control : sentec_eject ';'",
"cuerpo_control : BEGIN ';' END",
"cuerpo_control : BEGIN END",
"cuerpo_control : BEGIN multip_cuerp_fun END",
"cuerpo_iteracion : BEGIN multip_cuerp_fun ';' END",
"cuerpo_iteracion : sentec_eject ';'",
"cuerpo_iteracion : BEGIN ';' END",
"cuerpo_iteracion : BEGIN END",
"cuerpo_iteracion : BEGIN multip_cuerp_fun END",
"multip_cuerp_fun : multip_cuerp_fun ';' sentec_eject",
"multip_cuerp_fun : sentec_eject",
"multip_cuerp_fun : multip_cuerp_fun sentec_eject",
"declaracion_var : tipo lista_variables",
"declaracion_var : ID '>' lista_variables",
"declaracion_var : embed",
"embed : lista_variables_e",
"lista_variables_e : lista_variables_e ',' ID",
"lista_variables_e : ID",
"lista_variables : lista_variables ',' ID",
"lista_variables : ID",
"lista_variables : lista_variables error ID",
"tipo : DOUBLE",
"tipo : ULONGINT",
"asignacion : triple ASIGN expresion",
"asignacion : ID ASIGN expresion",
"expresion : expresion_matematica",
"expresion : condicion_2",
"expresion_matematica : expresion_matematica '+' termino",
"expresion_matematica : expresion_matematica '-' termino",
"expresion_matematica : termino",
"expresion_matematica : '+' termino",
"expresion_matematica : expresion_matematica '+' error ')'",
"expresion_matematica : expresion_matematica '+' error ';'",
"expresion_matematica : expresion_matematica '-' error ')'",
"expresion_matematica : expresion_matematica '-' error ';'",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"termino : '*' factor",
"termino : '/' factor",
"termino : termino '/' error ')'",
"termino : termino '*' error ')'",
"termino : termino '*' error ';'",
"termino : termino '/' error ';'",
"factor : ID",
"factor : constante",
"factor : invoc_fun",
"factor : triple",
"constante : CTE",
"constante : '-' CTE",
"triple : ID '{' expresion_matematica '}'",
"$$1 :",
"declaracion_fun : tipo_fun FUN ID '(' lista_parametro ')' $$1 cuerpo_funcion_p",
"$$2 :",
"declaracion_fun : tipo_fun FUN '(' lista_parametro ')' $$2 cuerpo_funcion_p",
"$$3 :",
"declaracion_fun : tipo_fun FUN ID '(' ')' $$3 cuerpo_funcion_p",
"tipo_fun : tipo",
"tipo_fun : ID",
"lista_parametro : lista_parametro ',' parametro",
"lista_parametro : parametro",
"parametro : tipo ID",
"parametro : ID ID",
"parametro : tipo",
"parametro : ID",
"$$4 :",
"cuerpo_funcion_p : $$4 BEGIN bloques_funcion ';' END",
"bloques_funcion : bloques_funcion ';' bloque_funcion",
"bloques_funcion : bloque_funcion",
"bloques_funcion : bloques_funcion bloque_funcion",
"bloque_funcion : retorno",
"bloque_funcion : sentencia",
"retorno : RET '(' expresion ')'",
"invoc_fun : ID '(' lista_parametro_real ')'",
"invoc_fun : ID '(' ')'",
"lista_parametro_real : lista_parametro_real ',' param_real",
"lista_parametro_real : param_real",
"param_real : tipo expresion_matematica",
"param_real : expresion",
"sald_mensaj : OUTF '(' mensaje ')'",
"sald_mensaj : OUTF '(' ')'",
"sald_mensaj : OUTF '(' error ')'",
"mensaje : expresion",
"mensaje : CADMUL",
"for : FOR '(' ID ASIGN CTE ';' condicion ';' foravanc CTE ')' cuerpo_iteracion",
"for : FOR '(' ID ASIGN CTE ';' condicion foravanc CTE ')' cuerpo_iteracion",
"for : FOR '(' ID ASIGN CTE condicion ';' foravanc CTE ')' cuerpo_iteracion",
"for : FOR '(' ID ASIGN CTE condicion foravanc CTE ')' cuerpo_iteracion",
"for : FOR '(' ID ASIGN CTE ';' condicion ';' CTE ')' cuerpo_iteracion",
"for : FOR '(' ID ASIGN CTE ';' condicion ';' foravanc ')' cuerpo_iteracion",
"for : FOR '(' ID ASIGN CTE ';' condicion CTE ')' cuerpo_iteracion",
"for : FOR '(' ID ASIGN CTE ';' condicion foravanc ')' cuerpo_iteracion",
"for : FOR '(' ID ASIGN CTE ';' condicion ';' ')' cuerpo_iteracion",
"foravanc : UP",
"foravanc : DOWN",
"goto : GOTO TAG",
"goto : GOTO error ';'",
"declar_tipo_trip : TYPEDEF TRIPLE '<' tipo '>' ID",
"declar_tipo_trip : TYPEDEF TRIPLE tipo '>' ID",
"declar_tipo_trip : TYPEDEF TRIPLE '<' tipo ID",
"declar_tipo_trip : TYPEDEF TRIPLE tipo ID",
"declar_tipo_trip : TYPEDEF '<' tipo '>' ID",
"declar_tipo_trip : TYPEDEF TRIPLE '<' tipo '>' error ';'",
};

//#line 354 "gramatica.y"
String nombreArchivo;
AnalizadorLexico lex;
TablaSimbolos ts;
GeneradorCodigo gc;
String tipoVar;
ArrayList<Integer> cantRetornos;
String estructuras;
public Parser(String nombreArchivo, TablaSimbolos t, GeneradorCodigo gc)
{
	this.nombreArchivo=nombreArchivo;
	this.ts=t;
	this.gc = gc;
	this.cantRetornos = new ArrayList<>();
	this.estructuras = "Estructuras sintacticas detectadas en el codigo fuente :  \n";
	this.lex= new AnalizadorLexico(nombreArchivo, t, this);
}
String yyerror(String a) {
	return a;
}
String errores() {
	return ErrorHandler.getErrores();
}
int yylex() {
	return lex.yylex();
}

boolean esEmbebido(String sval){
	char firstC=sval.charAt(0);
	if ( firstC =='x' || firstC =='y' || firstC =='z' || firstC == 'd' ) {
		return true;
	}
	return false;
}

boolean varRedeclarada(){
	char firstC=yylval.sval.charAt(0);
	if ( (tipoVar.equals(AccionSemantica.ULONGINT) && (firstC =='x' || firstC =='y' || firstC =='z')) || (tipoVar.equals(AccionSemantica.DOUBLE) && (firstC == 'd')) ) {
		return true;
	}
	return false;
}

boolean redeclaracionTipoErroneo(){
	char firstC=yylval.sval.charAt(0);
	if ( (tipoVar.equals(AccionSemantica.DOUBLE) && (firstC =='x' || firstC =='y' || firstC =='z')) || (tipoVar.equals(AccionSemantica.ULONGINT) && (firstC == 'd')) ) {
		return true;
	}
	return false;
}

void checkRedeclaracion(String val){
	if (varRedeclarada()){
		ErrorHandler.addErrorSemantico("Se redeclaro el tipo de la variable", lex.getLineaInicial());
	}
	else if (redeclaracionTipoErroneo()){
		ErrorHandler.addErrorSemantico("Se redeclaro el tipo de la variable y con tipos erroneos", lex.getLineaInicial());
	}
	else {
		ts.addClave(val);
		ts.addAtributo(val,AccionSemantica.TIPO,tipoVar);lex.getLineaInicial();
	}
}

void checkRedeclaracionFuncion(){
	if (varRedeclarada()){
		ErrorHandler.addErrorSemantico("Se redeclaro el tipo de la funcion", lex.getLineaInicial());
	}
	else if (redeclaracionTipoErroneo()){
		ErrorHandler.addErrorSemantico("Se redeclaro el tipo de la funcion y con tipos erroneos", lex.getLineaInicial());
	}
}

void estructurasSintacticas(String estructura){
	estructuras += estructura + "\n";
}

void checkRet(String nombreFuncion) {
	if (!nombreFuncion.isEmpty()) {
		if (this.cantRetornos.get(this.cantRetornos.size()-1) > 0){
			estructurasSintacticas("Se declaró la función: " + nombreFuncion);
			ts.addClave(nombreFuncion);
			ts.addAtributo(nombreFuncion,AccionSemantica.TIPO,AccionSemantica.FUNCION);
			ts.addAtributo(nombreFuncion,AccionSemantica.TIPORETORNO,tipoVar);
		} else {
			ErrorHandler.addErrorSintactico("Falta el retorno de la función: " + nombreFuncion, lex.getLineaInicial());
		}
		
	} else {
		if (this.cantRetornos.get(this.cantRetornos.size()-1) == 0){
			ErrorHandler.addErrorSintactico("Falta el retorno de la función", lex.getLineaInicial());
		}
	}
	this.cantRetornos.remove(this.cantRetornos.size()-1);
}

public static String getNombreVariable(int numero) {
    switch (numero) {
        case YYERRCODE: return "YYERRCODE";
        case ID: return "ID";
        case CTE: return "CTE";
        case MASI: return ">=";
        case MENOSI: return "<=";
        case ASIGN: return ":=";
        case DIST: return "!=";
        case GOTO: return "GOTO";
        case UP: return "UP";
        case DOWN: return "DOWN";
        case TRIPLE: return "TRIPLE";
        case FOR: return "FOR";
        case ULONGINT: return "ULONGINT";
        case DOUBLE: return "DOUBLE";
        case IF: return "IF";
        case THEN: return "THEN";
        case ELSE: return "ELSE";
        case BEGIN: return "BEGIN";
        case END: return "END";
        case END_IF: return "END_IF";
        case OUTF: return "OUTF";
        case TYPEDEF: return "TYPEDEF";
        case FUN: return "FUN";
        case RET: return "RET";
        case CADMUL: return "CADMUL";
        case TAG: return "TAG";
        default:
            // Si el número está en el rango ASCII (0-255), convierte a carácter
            if (numero >= 0 && numero <= 255) {
            	char num = (char) numero;
                return String.valueOf(num);
            } else {
                return null;
            }
    }
}

public static void main(String[] args) {
    // Verificamos que el nombre de "prueba" sea pasado como argumento
/*
    if (args.length < 1) {
        System.out.println("Por favor, proporciona el nombre de la prueba como argumento.");
        return;
    }

    // Tomamos el primer argumento como el valor de prueba
    String prueba = args[0];*/
    String prueba = "pruebaCodigoSemantica";
    TablaSimbolos tb = new TablaSimbolos();
    GeneradorCodigo gc = new GeneradorCodigo();
    Parser p = new Parser(prueba, tb, gc);
    
    int valido = p.yyparse();
    
    System.out.println(p.lex.getListaTokens());
    System.out.println("\n" + p.estructuras);	
    System.out.println("Errores y Warnings detectados del codigo fuente :  \n" + p.errores());
    System.out.println("Contenido de la tabla de simbolos:  \n" + tb);
    System.out.println("Codigo intermedio en tercetos: " + gc);
    
    if (valido == 0) {
        System.out.println("Se analizo todo el codigo fuente");
    } else {
        System.out.println("No se analizo completamente el codigo fuente, debido a uno o mas errores inesperados");
    }
}
//#line 846 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 10 "gramatica.y"
{ estructurasSintacticas("Se declaró el programa: " + val_peek(1).sval);}
break;
case 2:
//#line 12 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el nombre del programa", lex.getLineaInicial());}
break;
case 4:
//#line 17 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 5:
//#line 18 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta delimitador del programa", lex.getLineaInicial());}
break;
case 7:
//#line 24 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 8:
//#line 25 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta delimitador END del programa", lex.getLineaInicial());}
break;
case 9:
//#line 26 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta delimitador BEGIN del programa", lex.getLineaInicial());}
break;
case 10:
//#line 27 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta delimitador BEGIN y END del programa", lex.getLineaInicial());}
break;
case 13:
//#line 32 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Sentencia inválida.", lex.getLineaInicial());}
break;
case 14:
//#line 34 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 18:
//#line 43 "gramatica.y"
{	estructurasSintacticas("Se declaro la funcion, en linea: " + lex.getLineaInicial()); }
break;
case 19:
//#line 44 "gramatica.y"
{	if(esEmbebido(val_peek(0).sval)){
			ErrorHandler.addErrorSemantico("No se puede declarar una etiqueta que tenga tipos embebidos", lex.getLineaInicial());
			} else {
				estructurasSintacticas("Se declaro una etiqueta goto, en linea: " + lex.getLineaInicial());
			}
		}
break;
case 24:
//#line 56 "gramatica.y"
{estructurasSintacticas("Se imprimio un mensaje, en linea: " + lex.getLineaInicial()); }
break;
case 26:
//#line 58 "gramatica.y"
{estructurasSintacticas("Se llamo a una etiqueta goto, en linea: " + lex.getLineaInicial()); }
break;
case 28:
//#line 64 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de paréntesis en la condición", lex.getLineaInicial());}
break;
case 29:
//#line 65 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de paréntesis derecho en la condición", lex.getLineaInicial());}
break;
case 30:
//#line 66 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de paréntesis izquierdo en la condición", lex.getLineaInicial());}
break;
case 31:
//#line 69 "gramatica.y"
{ yyval.sval = gc.addTerceto(val_peek(1).sval, val_peek(2).sval, val_peek(0).sval);}
break;
case 32:
//#line 70 "gramatica.y"
{ yyval.sval = gc.addTerceto(val_peek(3).sval, val_peek(5).sval, val_peek(1).sval);}
break;
case 33:
//#line 71 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón y el que abre la segunda", lex.getLineaInicial());}
break;
case 34:
//#line 72 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que abre la segunda lista del patrón", lex.getLineaInicial());}
break;
case 35:
//#line 73 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón", lex.getLineaInicial());}
break;
case 36:
//#line 75 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta comparador entre los patrones", lex.getLineaInicial());}
break;
case 37:
//#line 76 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta comparador entre las expresiones", lex.getLineaInicial());}
break;
case 41:
//#line 86 "gramatica.y"
{estructurasSintacticas("Se definió una sentencia de control sin else, en la linea: " + lex.getLineaInicial());}
break;
case 43:
//#line 89 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta END_IF con ELSE", lex.getLineaInicial());}
break;
case 44:
//#line 90 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta END_IF", lex.getLineaInicial());}
break;
case 45:
//#line 91 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control del then", lex.getLineaInicial());}
break;
case 46:
//#line 92 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control del ELSE", lex.getLineaInicial());}
break;
case 47:
//#line 93 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control tanto en THEN como ELSE", lex.getLineaInicial());}
break;
case 48:
//#line 98 "gramatica.y"
{gc.actualizarBF(gc.getCantTercetos()); 
			gc.pop();}
break;
case 49:
//#line 101 "gramatica.y"
{
			gc.addTerceto("BF", val_peek(0).sval, ""); 
			gc.push(gc.getPosActual());
		}
break;
case 50:
//#line 107 "gramatica.y"
{ 
			gc.addTerceto("BI", "", "-"); 
			gc.actualizarBF(gc.getCantTercetos()); 
			gc.pop(); 
			gc.push(gc.getPosActual());
		}
break;
case 51:
//#line 114 "gramatica.y"
{
			gc.actualizarBI(gc.getPosActual()); 
			gc.pop();
			estructurasSintacticas("Se definió una sentencia de control con else, en la linea: " + lex.getLineaInicial());
		}
break;
case 55:
//#line 123 "gramatica.y"
{yyval.sval = "=";}
break;
case 56:
//#line 124 "gramatica.y"
{yyval.sval = "<";}
break;
case 57:
//#line 125 "gramatica.y"
{yyval.sval = ">";}
break;
case 60:
//#line 131 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
break;
case 61:
//#line 132 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
break;
case 62:
//#line 133 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 65:
//#line 139 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
break;
case 66:
//#line 140 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
break;
case 67:
//#line 141 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 70:
//#line 147 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 71:
//#line 151 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 72:
//#line 153 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 73:
//#line 154 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 75:
//#line 161 "gramatica.y"
{if(!esEmbebido(val_peek(0).sval)){ErrorHandler.addErrorSemantico("No se puede omitir el tipo en declaracion de tipos no-embebidos.", lex.getLineaInicial());};}
break;
case 76:
//#line 162 "gramatica.y"
{if(!esEmbebido(val_peek(0).sval)){ErrorHandler.addErrorSemantico("No se puede omitir el tipo en declaracion de tipos no-embebidos.", lex.getLineaInicial());};}
break;
case 77:
//#line 166 "gramatica.y"
{checkRedeclaracion(val_peek(0).sval);}
break;
case 78:
//#line 167 "gramatica.y"
{checkRedeclaracion(val_peek(0).sval);}
break;
case 79:
//#line 168 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta coma en la lista de variables, puede haber parado la compilacion en este punto", lex.getLineaInicial());}
break;
case 82:
//#line 176 "gramatica.y"
{estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());
						
					}
break;
case 83:
//#line 179 "gramatica.y"
{estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());
					yyval.sval = gc.addTerceto(":=", val_peek(2).sval, val_peek(0).sval);
		}
break;
case 86:
//#line 189 "gramatica.y"
{yyval.sval = gc.addTerceto("+", val_peek(2).sval, val_peek(0).sval);}
break;
case 87:
//#line 190 "gramatica.y"
{yyval.sval = gc.addTerceto("-", val_peek(2).sval, val_peek(0).sval);}
break;
case 89:
//#line 195 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 90:
//#line 196 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");}
break;
case 91:
//#line 198 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
break;
case 92:
//#line 200 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");}
break;
case 93:
//#line 202 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
break;
case 94:
//#line 208 "gramatica.y"
{yyval.sval = gc.addTerceto("*", val_peek(2).sval, val_peek(0).sval);}
break;
case 95:
//#line 209 "gramatica.y"
{yyval.sval = gc.addTerceto("/", val_peek(2).sval, val_peek(0).sval);}
break;
case 97:
//#line 213 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 98:
//#line 214 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 99:
//#line 215 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 100:
//#line 217 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 101:
//#line 219 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
break;
case 102:
//#line 221 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
break;
case 108:
//#line 232 "gramatica.y"
{	if (ts.esUlongInt(val_peek(0).sval)){
					ErrorHandler.addErrorSintactico("se utilizo un Ulongint negativo, son solo positivos", lex.getLineaInicial());
				}
				else {
					ts.convertirNegativo(val_peek(0).sval);
				}
			}
break;
case 110:
//#line 244 "gramatica.y"
{ this.cantRetornos.add(0); }
break;
case 111:
//#line 244 "gramatica.y"
{this.checkRet(val_peek(5).sval);
								if (esEmbebido(val_peek(5).sval)){ErrorHandler.addErrorSemantico("No se puede declarar una funcion con un ID con tipos embebidos.", lex.getLineaInicial());};
							}
break;
case 112:
//#line 247 "gramatica.y"
{ this.cantRetornos.add(0); }
break;
case 113:
//#line 247 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta nombre de la funcion declarada", lex.getLineaInicial());
													this.checkRet("");
													}
break;
case 114:
//#line 250 "gramatica.y"
{ this.cantRetornos.add(0);}
break;
case 115:
//#line 250 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el parametro en la declaracion de la funcion", lex.getLineaInicial());
							    this.checkRet(val_peek(4).sval);
							    if (esEmbebido(val_peek(4).sval)){ErrorHandler.addErrorSemantico("No se puede declarar una funcion con un ID con tipos embebidos.", lex.getLineaInicial());}

							   }
break;
case 118:
//#line 261 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Se declaró más de un parametro", lex.getLineaInicial());}
break;
case 120:
//#line 266 "gramatica.y"
{estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 121:
//#line 267 "gramatica.y"
{estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 122:
//#line 269 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el nombre del parametro", lex.getLineaInicial());}
break;
case 123:
//#line 270 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el nombre del parametro o el tipo", lex.getLineaInicial());}
break;
case 124:
//#line 273 "gramatica.y"
{ts.addClave(yylval.sval);}
break;
case 128:
//#line 281 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 131:
//#line 288 "gramatica.y"
{ this.cantRetornos.set(this.cantRetornos.size()-1, this.cantRetornos.get(this.cantRetornos.size()-1) + 1); 
					yyval.sval = gc.addTerceto("RET", val_peek(1).sval, "");		
		}
break;
case 132:
//#line 293 "gramatica.y"
{estructurasSintacticas("Se invocó a la función: " + val_peek(3).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 133:
//#line 295 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de parámetros en la invocación a la función", lex.getLineaInicial());}
break;
case 134:
//#line 299 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Se utilizó más de un parámetro para invocar a la función", lex.getLineaInicial());}
break;
case 138:
//#line 310 "gramatica.y"
{yyval.sval = gc.addTerceto("OUTF", val_peek(1).sval, "");}
break;
case 139:
//#line 312 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el mensaje del OUTF", lex.getLineaInicial());}
break;
case 140:
//#line 313 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Parámetro invalido del OUTF", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 143:
//#line 321 "gramatica.y"
{estructurasSintacticas("Se declaró un bucle FOR en la linea: " + lex.getLineaInicial());}
break;
case 144:
//#line 323 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 145:
//#line 324 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma entre asignacion y condicion", lex.getLineaInicial());}
break;
case 146:
//#line 325 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Faltan todos los punto y coma del for", lex.getLineaInicial());}
break;
case 147:
//#line 326 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta UP/DOWN", lex.getLineaInicial());}
break;
case 148:
//#line 327 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN", lex.getLineaInicial());}
break;
case 149:
//#line 328 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 150:
//#line 329 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 151:
//#line 330 "gramatica.y"
{ { ErrorHandler.addErrorSintactico("Falta UP/DOWN, su valor, y punto y coma entre condicion y avance", lex.getLineaInicial());}}
break;
case 154:
//#line 337 "gramatica.y"
{ yyval.sval = gc.addTerceto("GOTO", val_peek(0).sval,"");}
break;
case 155:
//#line 339 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta la etiqueta en el GOTO, en caso de faltar también el punto y coma es posible que no compile el resto del programa o lo haga mal.", lex.getLineaInicial());
				lex.setErrorHandlerToken(";");}
break;
case 156:
//#line 345 "gramatica.y"
{estructurasSintacticas("Se declaró un tipo TRIPLE con el ID: " + val_peek(0).sval + " en la linea:" + lex.getLineaInicial());}
break;
case 157:
//#line 347 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta < en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 158:
//#line 348 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta > en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 159:
//#line 349 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta > y < en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 160:
//#line 350 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta la palabra clave TRIPLE", lex.getLineaInicial());}
break;
case 161:
//#line 351 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el ID de la tripla definida.", lex.getLineaInicial());}
break;
//#line 1471 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
