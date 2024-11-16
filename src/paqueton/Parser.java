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
	import java.util.Stack;
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
    2,    0,    0,    3,    3,    3,    1,    1,    1,    1,
    1,    4,    4,    4,    4,    5,    5,    6,    6,    6,
    6,    7,    7,    7,    7,    7,    7,   17,   17,   17,
   17,   18,   18,   18,   18,   18,   18,   18,   21,   23,
   23,   22,   24,   24,   13,   13,   13,   13,   13,   13,
   13,   27,   25,   28,   29,   20,   20,   20,   20,   20,
   20,   26,   26,   26,   26,   26,   31,   31,   31,   31,
   31,   30,   30,   30,    8,   34,    8,   33,   33,   33,
   32,   32,   11,   11,   19,   19,   19,   19,   19,   19,
   19,   19,   36,   36,   36,   36,   36,   36,   36,   36,
   36,   37,   37,   37,   37,   38,   38,   35,   39,   42,
   44,    9,   45,    9,   46,    9,   40,   40,   41,   41,
   47,   47,   47,   47,   43,   48,   48,   48,   49,   49,
   50,   52,   12,   12,   51,   51,   53,   53,   14,   14,
   14,   54,   54,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   56,   55,   57,   57,   16,   16,   10,
   10,   10,   10,   10,   10,
};
final static short yylen[] = {                            2,
    0,    3,    1,    4,    3,    3,    4,    3,    3,    3,
    2,    3,    1,    2,    2,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    3,    1,    2,
    2,    3,    7,    5,    6,    6,    7,    3,    3,    3,
    1,    3,    3,    1,    5,    7,    4,    4,    4,    6,
    5,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    4,    2,    3,    2,    3,    4,    2,    3,    2,
    3,    3,    1,    2,    2,    0,    3,    3,    1,    3,
    1,    1,    3,    3,    3,    3,    1,    2,    4,    4,
    4,    4,    3,    3,    1,    2,    2,    4,    4,    4,
    4,    1,    1,    1,    1,    1,    2,    4,    4,    0,
    0,    9,    0,    7,    0,    7,    1,    1,    3,    1,
    2,    2,    1,    1,    4,    3,    1,    2,    1,    1,
    4,    0,    5,    3,    3,    1,    1,    2,    4,    3,
    4,    1,    1,   10,   10,    9,    9,    8,    9,    9,
    8,    8,    8,    1,    3,    1,    1,    2,    3,    6,
    5,    5,    4,    5,    7,
};
final static short yydefred[] = {                         0,
    1,    0,    0,    3,    0,    0,    0,    0,    0,   82,
   81,    0,    0,    0,   20,    0,    0,   16,   17,   18,
   19,   21,   22,   23,   24,   25,   26,   27,    0,    0,
    0,    0,    2,    0,   13,   14,    0,    0,    0,    0,
    0,  158,    0,    0,  106,    0,    0,    0,    0,    0,
  104,   53,    0,    0,    0,    0,   95,  103,  105,    0,
    0,    0,    0,   15,    5,   79,    0,    0,    0,    0,
    0,    0,    0,  134,    0,    0,    0,  159,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  107,   96,   97,
   31,    0,   56,   57,   58,   59,   60,   61,    0,    0,
    0,    0,    0,    0,    0,  143,  140,    0,    0,    0,
    0,    0,    4,   12,    0,    0,    0,    0,    0,    0,
    8,   10,    0,    0,    0,  136,  108,    0,    0,  154,
    0,    0,    0,   28,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   54,    0,   52,    0,    0,   49,
    0,    0,   93,    0,   94,  141,  139,    0,  163,    0,
    0,   80,   78,    0,    0,    0,    0,  120,    7,  138,
  133,    0,  155,    0,  156,  157,    0,    0,  109,    0,
    0,    0,    0,    0,    0,    0,   90,   89,   92,   91,
   65,    0,   73,    0,   63,   45,    0,   55,   51,  100,
   99,  101,   98,  162,    0,  161,  164,  115,    0,  122,
  121,  113,    0,  135,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   34,    0,   64,   66,    0,   74,    0,
   50,    0,  160,    0,    0,    0,  119,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   35,   36,
    0,   62,   72,   46,  165,    0,  116,  111,  114,    0,
    0,  151,    0,  153,    0,    0,    0,  152,    0,    0,
  148,   37,   33,    0,  130,    0,  127,  129,    0,   70,
    0,    0,   68,  149,    0,  150,  146,    0,  147,    0,
    0,  128,  112,   69,   71,    0,  144,  145,    0,  125,
  126,   67,  131,
};
final static short yydgoto[] = {                          3,
   33,    5,    4,   16,   64,   18,   19,   20,   21,   22,
   23,   24,   25,   26,   27,   28,  130,   53,  183,  101,
   85,  184,   86,  185,   55,  149,  150,  151,  199,  194,
  262,   29,   67,   40,   30,   56,   57,   58,   59,   31,
  167,  209,  257,  279,  236,  234,  168,  276,  277,  278,
  125,   75,  126,  109,   80,  131,  178,
};
final static short yysindex[] = {                      -186,
    0,  535,    0,    0,  495,    5,   15, -200,   -7,    0,
    0,  417,   59,  -55,    0,  323, -223,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -182, -158,
 -147,  535,    0,  344,    0,    0,  614,   79,  614, -182,
   81,    0,  -94,  -12,    0,  521,  634,  -86,  -11,  -11,
    0,    0,  152,  590,  -72,   94,    0,    0,    0,  -35,
  -59, -245,  620,    0,    0,    0,    4,  614,  -31,  361,
  -74,  636,  273,    0,  -29,    1,    4,    0,  -56,  498,
  614,  614,  183,  590,  129,  186,   94,    0,    0,    0,
    0,  614,    0,    0,    0,    0,    0,    0,  618,  624,
  614,  473,   21,  170,  190,    0,    0,  273,  212, -245,
  -51,  199,    0,    0,   23,   48,  273,  270, -122,  651,
    0,    0,  273,   67,  154,    0,    0,   10,  417,    0,
  -57,   14,  273,    0,  469,  542,  614,  273,  125,   94,
  138,   94,  273,   15,    0,   -1,    0,  252,   11,    0,
   60,  184,    0,  222,    0,    0,    0,  -30,    0,   72,
   84,    0,    0,  295,   86,   93,  244,    0,    0,    0,
    0,  -29,    0,    6,    0,    0,  -97,   97,    0,  322,
  563,  614,  273,  326,  320,  273,    0,    0,    0,    0,
    0,   95,    0,  410,    0,    0,  552,    0,    0,    0,
    0,    0,    0,    0,   38,    0,    0,    0, -122,    0,
    0,    0, -122,    0,  330,  -14,  -26,  119,  353,  614,
  614,  355,  367,    0,  614,    0,    0, -105,    0,   60,
    0,  356,    0,  156,  255,  156,    0,  510,  383,  510,
  -20,  389,  510,  195,  400,  510,  407,  422,    0,    0,
  273,    0,    0,    0,    0,  666,    0,    0,    0,  425,
  399,    0,  510,    0,  430,  510,  510,    0,  432,  510,
    0,    0,    0,  438,    0,  302,    0,    0,  156,    0,
  214,  440,    0,    0,  510,    0,    0,  510,    0,  614,
  588,    0,    0,    0,    0,  674,    0,    0,  151,    0,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0, -159,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  377,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  208,    0,
    0,    0,    0,    0,    0,    0,    0,   35,    0,    0,
    0,    0,    0,   63,    0,    0,    0,    0,    0,    0,
    0,    0,   26,    0,    0,   89,    0,    0,    0,    0,
    0,    0,  493,    0,    0,    0,  198,    0,    0,    0,
  377,  501,  213,    0,    0,    0,  228,    0,    0,    0,
    0,    0,   43,  459,    0,    0,  116,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  467,    0,    0,
    0,    0,    0,    0,    0,    0,  243,    0,    0,  514,
    0,    0,  265,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  459,    0,    0,    0,    0,  -16,    0,  142,
    0,  175,   -6,    0,    0,    0,    0,    0,  258,    0,
  279,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -113,  373,  381,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  474,    0,    0,   53,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  406,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,   17,   -2,    0,  547,    0,    0,    0,
    0,   -8,    0,    0,    0,    0,  504,  477,    8,  -49,
    0,  -39,    0,    0,    0,  336,  395,  401, -180,  291,
  472,  513,  512,    0,    0,   54,   13,    0,    0,    0,
  345,    0, -210,    0,    0,    0,  349,    0, -164,    0,
    0,    0,  404,    0,    0,  444, -137,
};
final static int YYTABLESIZE=950;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         17,
  110,  177,   35,   51,   62,  107,   49,   47,  119,   48,
  160,   50,   49,   47,  243,   48,  231,   50,  244,   54,
  266,   34,   10,   11,   38,  259,  240,   38,   51,   71,
   51,  205,   43,   48,   32,  136,  217,   51,   51,  218,
   51,   51,   38,   99,   73,  100,   76,  116,   70,  254,
   65,   51,   32,   84,   38,   41,   99,  192,  100,   51,
  114,   89,   90,   36,  216,   48,   51,  108,  293,  114,
    1,   51,   51,   51,   66,  117,  132,  132,  241,  132,
   42,  132,  123,   51,   29,  181,    2,   54,  132,  133,
   51,   51,   51,   39,   51,   51,   40,   76,   60,  138,
   87,   30,   68,  102,  102,  102,  102,  102,  143,  102,
   81,  292,   39,   39,   39,  153,  155,  114,  118,   74,
   51,  102,  102,  102,  102,  127,  301,   51,   51,   87,
   69,   87,   87,   87,  165,  103,   54,   39,  179,   78,
  104,  222,  223,  110,  186,   10,   11,   87,   87,   87,
   87,  144,  140,  142,  110,  110,   88,    8,   88,   88,
   88,    9,   79,   51,   12,  188,  175,  176,  252,  135,
   13,   88,   51,   51,   88,   88,   88,   88,  190,  123,
  247,  248,   85,  187,   85,   85,   85,  102,   97,   96,
   98,  303,   91,   99,  171,  100,  189,  172,  102,  121,
   85,   85,   85,   85,  128,  159,  175,  176,   10,   11,
   61,   51,   51,   87,   48,   86,   51,   86,   86,   86,
  105,   44,   45,  134,  201,  118,  204,   44,   45,  137,
  156,  242,  251,   86,   86,   86,   86,  265,   10,   11,
   88,   38,  200,  239,  106,   44,   45,   38,   38,  175,
  176,   32,  157,  275,   38,  144,   75,   32,   32,  115,
  161,    8,  203,  215,   32,    9,   85,  173,   12,  175,
  176,   84,  191,  275,   13,   37,  152,   44,   45,  162,
  202,   51,  145,   29,  212,  147,   77,  213,  275,   29,
   29,  132,  132,  232,  233,  258,   29,  299,  213,   86,
   30,   83,  132,  132,  163,  137,   30,   30,  137,  164,
  195,   39,   39,   30,   39,   99,   47,  100,  102,  102,
  102,  102,  102,  170,  102,  102,  102,  102,  206,  102,
  102,  102,  102,  102,  198,  208,  102,   48,  102,  102,
  207,  102,  210,  102,   87,   87,   87,   87,   87,  211,
   87,   87,   87,   87,  219,   87,   87,   87,   87,   87,
  291,  220,   87,  225,   87,   87,  224,   87,  226,   87,
  238,   88,   88,   88,   88,   88,  245,   88,   88,   88,
   88,   63,   88,   88,   88,   88,   88,   93,   94,   88,
   95,   88,   88,  246,   88,  249,   88,   85,   85,   85,
   85,   85,   72,   85,   85,   85,   85,  250,   85,   85,
   85,   85,   85,  124,  255,   85,  124,   85,   85,  120,
   85,  123,   85,  263,  123,  154,   44,   45,  256,  267,
   86,   86,   86,   86,   86,   13,   86,   86,   86,   86,
  270,   86,   86,   86,   86,   86,   42,  272,   86,   43,
   86,   86,  269,   86,   75,   86,   46,  283,   49,   47,
   75,   48,  273,   50,   75,   75,   75,   75,  228,   84,
  285,   75,  288,   75,   75,   84,   75,  290,   75,   84,
   84,   84,   84,  281,   77,  117,   84,  294,   84,   84,
   77,   84,    6,   84,   77,   77,   77,   77,  296,   83,
   11,   77,   41,   77,   77,   83,   77,  142,   77,   83,
   83,   83,   83,    9,   47,   52,   83,   44,   83,   83,
   47,   83,   83,   83,   47,   47,   47,   47,   97,   96,
   98,   47,  230,   47,   47,   48,   47,   46,   47,   49,
   47,   48,   48,  196,   50,   48,   48,   48,   48,  197,
  282,   77,   48,  235,   48,   48,  129,   48,    7,   48,
   82,  237,   49,   47,    8,   48,    0,   50,    9,   10,
   11,   12,  174,  111,  112,  214,    0,   13,   14,    7,
  274,  182,   15,   49,   47,    8,   48,  124,   50,    9,
   10,   11,   12,    0,    0,    0,    0,    0,   13,   14,
    7,    0,  221,   15,   49,   47,    8,   48,    0,   50,
    9,   10,   11,   12,    0,    0,    0,    7,    0,   13,
   14,    0,  158,    8,   15,    0,    0,    9,   10,   11,
   12,  166,   99,   13,  100,    0,   13,   14,    0,   13,
    0,   15,    0,   13,   13,   13,   13,    0,  148,   97,
   96,   98,   13,   13,    0,   49,   47,   13,   48,   49,
   50,    0,   48,    0,   50,   49,  144,    0,   48,    0,
   50,    0,    8,   44,   45,   49,    9,    0,   48,   12,
   50,  144,    0,  227,  124,   13,    0,    8,    0,    0,
    0,    9,  193,    0,   12,    0,  144,    0,  280,    0,
   13,    0,    8,    0,    0,    0,    9,    0,    0,   12,
    0,  264,    0,  295,  268,   13,    0,  271,    0,    0,
    0,  166,    0,    0,  180,  166,    0,   93,   94,  144,
   95,    0,    0,    0,  284,    8,    0,  286,  287,    9,
  229,  289,   12,  148,  145,  146,    0,  147,   13,    0,
    6,    7,    0,    0,   44,   45,  297,    8,    0,  298,
    0,    9,   10,   11,   12,    0,  144,   32,    0,    0,
   13,   14,    8,    0,  253,   15,    9,   44,   45,   12,
    0,    0,  260,    0,  261,   13,  261,    0,    0,  261,
    6,    7,  261,    0,    0,    0,    0,    8,   44,   45,
    0,    9,   10,   11,   12,    0,  193,    0,  144,  261,
   13,   14,  261,  261,    8,   15,  261,    0,    9,   44,
   45,   12,    0,    0,  146,    0,  198,   13,  229,    0,
    0,  261,    0,    0,  261,    0,    0,    0,    0,    0,
    0,    0,  253,    0,    7,   92,    0,    0,   93,   94,
    8,   95,    0,    0,    9,   10,   11,   12,    0,    0,
    0,  300,    0,   13,   14,    0,  274,    0,   15,    0,
   44,   45,    0,  139,   44,   45,    7,    0,    0,  141,
   44,   45,    8,    0,    0,    0,    9,   10,   11,   12,
   44,   45,    7,  113,    0,   13,   14,    0,    8,    0,
   15,    0,    9,   10,   11,   12,    0,    7,    0,  122,
    0,   13,   14,    8,    0,    0,   15,    9,   10,   11,
   12,    0,    7,    0,  169,    0,   13,   14,    8,    0,
  144,   15,    9,   10,   11,   12,    8,    0,    0,    0,
    9,   13,   14,   12,  274,    0,   15,  302,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   60,   59,    5,   12,   60,   41,   42,   43,   40,   45,
   62,   47,   42,   43,   41,   45,  197,   47,   45,   12,
   41,    5,  268,  269,   41,  236,   41,   40,   37,   32,
   39,   62,   40,   45,   41,   85,  174,   46,   47,  177,
   49,   50,   59,   43,   37,   45,   39,   44,   32,  230,
  274,   60,   59,   46,   40,  256,   43,   59,   45,   68,
   63,   49,   50,   59,   59,   45,   75,   60,  279,   72,
  257,   80,   81,   82,  257,   68,   42,   43,  216,   45,
  281,   47,   75,   92,   59,  135,  273,   80,   81,   82,
   99,  100,  101,   41,  103,  104,   44,  257,   40,   92,
   47,   59,  261,   41,   42,   43,   44,   45,  101,   47,
  123,  276,   60,   61,   62,  103,  104,  120,  278,   41,
  129,   59,   60,   61,   62,  125,  291,  136,  137,   41,
  278,   43,   44,   45,  257,   42,  129,  123,  125,   59,
   47,  181,  182,  257,  137,  268,  269,   59,   60,   61,
   62,  257,   99,  100,  268,  269,   41,  263,   43,   44,
   45,  267,  257,  172,  270,   41,  264,  265,  274,   41,
  276,  258,  181,  182,   59,   60,   61,   62,   41,  172,
  220,  221,   41,   59,   43,   44,   45,  125,   60,   61,
   62,   41,   41,   43,   41,   45,   59,   44,  271,  274,
   59,   60,   61,   62,  261,  257,  264,  265,  268,  269,
  266,  220,  221,  125,   45,   41,  225,   43,   44,   45,
  256,  257,  258,   41,   41,  257,  257,  257,  258,   44,
   41,  258,  225,   59,   60,   61,   62,  258,  268,  269,
  125,  258,   59,  258,  280,  257,  258,  264,  265,  264,
  265,  258,   41,  256,  271,  257,   59,  264,  265,  256,
   62,  263,   41,  258,  271,  267,  125,  258,  270,  264,
  265,   59,  274,  276,  276,  261,  256,  257,  258,  257,
   59,  290,  272,  258,   41,  275,   59,   44,  291,  264,
  265,  257,  258,  256,  257,   41,  271,  290,   44,  125,
  258,   59,  268,  269,  257,   41,  264,  265,   44,   40,
   59,  259,  260,  271,  262,   43,   59,   45,  256,  257,
  258,  259,  260,  257,  262,  263,  264,  265,  257,  267,
  268,  269,  270,  271,  275,   41,  274,   59,  276,  277,
  257,  279,  257,  281,  256,  257,  258,  259,  260,  257,
  262,  263,  264,  265,  258,  267,  268,  269,  270,  271,
   59,   40,  274,   44,  276,  277,   41,  279,  274,  281,
   41,  256,  257,  258,  259,  260,  258,  262,  263,  264,
  265,   59,  267,  268,  269,  270,  271,  259,  260,  274,
  262,  276,  277,   41,  279,   41,  281,  256,  257,  258,
  259,  260,   59,  262,  263,  264,  265,   41,  267,  268,
  269,  270,  271,   41,   59,  274,   44,  276,  277,   59,
  279,   41,  281,   41,   44,  256,  257,  258,  273,   41,
  256,  257,  258,  259,  260,   59,  262,  263,  264,  265,
   41,  267,  268,  269,  270,  271,   41,   41,  274,   44,
  276,  277,  258,  279,  257,  281,   40,   59,   42,   43,
  263,   45,   41,   47,  267,  268,  269,  270,   59,  257,
   41,  274,   41,  276,  277,  263,  279,   40,  281,  267,
  268,  269,  270,   59,  257,  278,  274,  274,  276,  277,
  263,  279,    0,  281,  267,  268,  269,  270,   59,  257,
    0,  274,   44,  276,  277,  263,  279,   41,  281,  267,
  268,  269,  270,    0,  257,   12,  274,   44,  276,  277,
  263,  279,   46,  281,  267,  268,  269,  270,   60,   61,
   62,  274,  197,  276,  277,  257,  279,   40,  281,   42,
   43,  263,   45,  149,   47,  267,  268,  269,  270,  149,
  260,   40,  274,  209,  276,  277,   59,  279,  257,  281,
   40,  213,   42,   43,  263,   45,   -1,   47,  267,  268,
  269,  270,  129,   61,   62,  172,   -1,  276,  277,  257,
  279,   40,  281,   42,   43,  263,   45,   75,   47,  267,
  268,  269,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,
  257,   -1,   40,  281,   42,   43,  263,   45,   -1,   47,
  267,  268,  269,  270,   -1,   -1,   -1,  257,   -1,  276,
  277,   -1,  110,  263,  281,   -1,   -1,  267,  268,  269,
  270,  119,   43,  257,   45,   -1,  276,  277,   -1,  263,
   -1,  281,   -1,  267,  268,  269,  270,   -1,  102,   60,
   61,   62,  276,  277,   -1,   42,   43,  281,   45,   42,
   47,   -1,   45,   -1,   47,   42,  257,   -1,   45,   -1,
   47,   -1,  263,  257,  258,   42,  267,   -1,   45,  270,
   47,  257,   -1,  274,  172,  276,   -1,  263,   -1,   -1,
   -1,  267,  146,   -1,  270,   -1,  257,   -1,  274,   -1,
  276,   -1,  263,   -1,   -1,   -1,  267,   -1,   -1,  270,
   -1,  240,   -1,  274,  243,  276,   -1,  246,   -1,   -1,
   -1,  209,   -1,   -1,  256,  213,   -1,  259,  260,  257,
  262,   -1,   -1,   -1,  263,  263,   -1,  266,  267,  267,
  194,  270,  270,  197,  272,  273,   -1,  275,  276,   -1,
  256,  257,   -1,   -1,  257,  258,  285,  263,   -1,  288,
   -1,  267,  268,  269,  270,   -1,  257,  273,   -1,   -1,
  276,  277,  263,   -1,  228,  281,  267,  257,  258,  270,
   -1,   -1,  273,   -1,  238,  276,  240,   -1,   -1,  243,
  256,  257,  246,   -1,   -1,   -1,   -1,  263,  257,  258,
   -1,  267,  268,  269,  270,   -1,  260,   -1,  257,  263,
  276,  277,  266,  267,  263,  281,  270,   -1,  267,  257,
  258,  270,   -1,   -1,  273,   -1,  275,  276,  282,   -1,
   -1,  285,   -1,   -1,  288,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  296,   -1,  257,  256,   -1,   -1,  259,  260,
  263,  262,   -1,   -1,  267,  268,  269,  270,   -1,   -1,
   -1,  274,   -1,  276,  277,   -1,  279,   -1,  281,   -1,
  257,  258,   -1,  256,  257,  258,  257,   -1,   -1,  256,
  257,  258,  263,   -1,   -1,   -1,  267,  268,  269,  270,
  257,  258,  257,  274,   -1,  276,  277,   -1,  263,   -1,
  281,   -1,  267,  268,  269,  270,   -1,  257,   -1,  274,
   -1,  276,  277,  263,   -1,   -1,  281,  267,  268,  269,
  270,   -1,  257,   -1,  274,   -1,  276,  277,  263,   -1,
  257,  281,  267,  268,  269,  270,  263,   -1,   -1,   -1,
  267,  276,  277,  270,  279,   -1,  281,  274,   -1,  276,
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
"$$1 :",
"prog : ID $$1 cuerpo",
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
"condicion_2 : '(' patron_izq ')' comparador '(' patron_der ')'",
"condicion_2 : '(' patron_izq comparador patron_der ')'",
"condicion_2 : '(' patron_izq ')' comparador patron_der ')'",
"condicion_2 : '(' patron_izq comparador '(' patron_der ')'",
"condicion_2 : '(' patron_izq ')' error '(' patron_der ')'",
"condicion_2 : expresion_matematica error expresion_matematica",
"patron_izq : lista_patron_izq ',' expresion_matematica",
"lista_patron_izq : lista_patron_izq ',' expresion_matematica",
"lista_patron_izq : expresion_matematica",
"patron_der : lista_patron_der ',' expresion_matematica",
"lista_patron_der : lista_patron_der ',' expresion_matematica",
"lista_patron_der : expresion_matematica",
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
"$$2 :",
"declaracion_var : ID $$2 lista_variables",
"lista_variables : lista_variables ',' ID",
"lista_variables : ID",
"lista_variables : lista_variables error ID",
"tipo : DOUBLE",
"tipo : ULONGINT",
"asignacion : triple_asig ASIGN expresion_matematica",
"asignacion : ID ASIGN expresion_matematica",
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
"triple_asig : ID '{' expresion_matematica '}'",
"triple : ID '{' expresion_matematica '}'",
"$$3 :",
"$$4 :",
"declaracion_fun : tipo_fun FUN ID '(' $$3 lista_parametro ')' $$4 cuerpo_funcion_p",
"$$5 :",
"declaracion_fun : tipo_fun FUN '(' lista_parametro ')' $$5 cuerpo_funcion_p",
"$$6 :",
"declaracion_fun : tipo_fun FUN ID '(' ')' $$6 cuerpo_funcion_p",
"tipo_fun : tipo",
"tipo_fun : ID",
"lista_parametro : lista_parametro ',' parametro",
"lista_parametro : parametro",
"parametro : tipo ID",
"parametro : ID ID",
"parametro : tipo",
"parametro : ID",
"cuerpo_funcion_p : BEGIN bloques_funcion ';' END",
"bloques_funcion : bloques_funcion ';' bloque_funcion",
"bloques_funcion : bloque_funcion",
"bloques_funcion : bloques_funcion bloque_funcion",
"bloque_funcion : retorno",
"bloque_funcion : sentencia",
"retorno : RET '(' expresion_matematica ')'",
"$$7 :",
"invoc_fun : ID '(' $$7 lista_parametro_real ')'",
"invoc_fun : ID '(' ')'",
"lista_parametro_real : lista_parametro_real ',' param_real",
"lista_parametro_real : param_real",
"param_real : expresion_matematica",
"param_real : tipo ID",
"sald_mensaj : OUTF '(' mensaje ')'",
"sald_mensaj : OUTF '(' ')'",
"sald_mensaj : OUTF '(' error ')'",
"mensaje : expresion_matematica",
"mensaje : CADMUL",
"for : FOR '(' asignacion_for ';' condicion_for ';' foravanc CTE ')' cuerpo_iteracion",
"for : FOR '(' asignacion_for ';' condicion_for foravanc '-' CTE ')' cuerpo_iteracion",
"for : FOR '(' asignacion_for ';' condicion_for foravanc CTE ')' cuerpo_iteracion",
"for : FOR '(' asignacion_for condicion_for ';' foravanc CTE ')' cuerpo_iteracion",
"for : FOR '(' asignacion_for condicion_for foravanc CTE ')' cuerpo_iteracion",
"for : FOR '(' asignacion_for ';' condicion_for ';' CTE ')' cuerpo_iteracion",
"for : FOR '(' asignacion_for ';' condicion_for ';' foravanc ')' cuerpo_iteracion",
"for : FOR '(' asignacion_for ';' condicion_for CTE ')' cuerpo_iteracion",
"for : FOR '(' asignacion_for ';' condicion_for foravanc ')' cuerpo_iteracion",
"for : FOR '(' asignacion_for ';' condicion_for ';' ')' cuerpo_iteracion",
"condicion_for : condicion",
"asignacion_for : ID ASIGN CTE",
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

//#line 468 "gramatica.y"
String nombreArchivo;
AnalizadorLexico lex;
TablaSimbolos ts;
GeneradorCodigo gc;
GeneradorWasm gw;
Stack<GeneradorCodigo> gc_funciones;
String tipoVar;
ArrayList<Integer> cantRetornos;
ArrayList<ControlTagAmbito> tags; 
String estructuras;
ArrayList<String> varFors;
String ambitoActual;
String funcionActual;
Integer inicioPatron;
Integer posPatron;
Integer cantPatronIzq;
Integer cantPatronDer;
Integer cantFors;
public Parser(String nombreArchivo, TablaSimbolos t, GeneradorCodigo gc)
{
	this.nombreArchivo=nombreArchivo;
	this.funcionActual = "";
	this.ambitoActual = "global";
	this.inicioPatron = Integer.MAX_VALUE;
	this.posPatron = -1;
	this.cantPatronIzq = 0;
	this.cantPatronDer = 0;
	this.cantFors = 0;
	this.ts=t;
	this.gc = gc;
	this.gc_funciones = new Stack<GeneradorCodigo>();
	this.gc_funciones.push(gc);
	this.cantRetornos = new ArrayList<>();
	this.estructuras = "Estructuras sintacticas detectadas en el codigo fuente :  \n";
	this.lex= new AnalizadorLexico(nombreArchivo, t, this);
	this.varFors = new ArrayList<>();
	this.tags = new ArrayList<>();
	this.tags.add(new ControlTagAmbito());
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

void iniciarPatron(){
	if(this.inicioPatron > gc.getCantTercetos()){
		this.inicioPatron = gc.getCantTercetos();
		this.posPatron = this.inicioPatron;
	}
}

void cambiarAmbito(){
	int index = this.ambitoActual.lastIndexOf(":");
        if (index != -1) {
            this.ambitoActual = this.ambitoActual.substring(0, index); // Retorna todo hasta el ":"
        }
}

boolean esEmbebido(String sval){
	char firstC=sval.charAt(0);
	if ( firstC =='x' || firstC =='y' || firstC =='z' || firstC == 'd' ) {
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
	String varAmbito = ambitoActual +":"+val;
	if (esEmbebido(val)){
		ErrorHandler.addErrorSemantico("Se redeclaro el tipo de la variable", lex.getLineaInicial());
	}
	else if (redeclaracionTipoErroneo()){
		ErrorHandler.addErrorSemantico("Se redeclaro el tipo de la variable y con tipos erroneos", lex.getLineaInicial());
	}
	else if (this.ts.estaEnTablaSimbolos(varAmbito)){
		ErrorHandler.addErrorSemantico("Se redeclaro la variable en el ambito: " +ambitoActual + " " , lex.getLineaInicial());	
	}
	else {
		ts.addClave(varAmbito);
		ts.addAtributo(varAmbito,AccionSemantica.TIPO,tipoVar);
		ts.addAtributo(varAmbito,AccionSemantica.USO,"nombre variable");
		if(!tipoVar.equals(AccionSemantica.DOUBLE) && !tipoVar.equals(AccionSemantica.ULONGINT)){
			if(!this.ts.getAtributo(tipoVar, AccionSemantica.TIPO).equals("")){
				ts.addAtributo(varAmbito, AccionSemantica.TIPO_BASICO, this.ts.getAtributo(tipoVar, AccionSemantica.TIPO));
			} else {
				ErrorHandler.addErrorSemantico("No existe la tripla con el ID " + tipoVar, lex.getLineaInicial());
			}
		}
	}
}

void estructurasSintacticas(String estructura){
	estructuras += estructura + "\n";
}

void checkRet(String nombreFuncion) {
	if (!nombreFuncion.isEmpty()) {
		if (this.cantRetornos.get(this.cantRetornos.size()-1) > 0){
			estructurasSintacticas("Se declaró la función: " + nombreFuncion);
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

void checkRedFuncion(String nombre, String tipo){
	String funcionAmbito = ambitoActual+":"+nombre;
	if(this.ts.estaEnTablaSimbolos(funcionAmbito)){
		ErrorHandler.addErrorSemantico("Funcion redeclarada "+nombre, lex.getLineaInicial());	
	}
	else {
		this.ts.addClave(funcionAmbito);
		ts.addAtributo(funcionAmbito,AccionSemantica.USO,"nombre funcion");
		this.ts.addAtributo(funcionAmbito, AccionSemantica.TIPO, tipo);
	}
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
    ErrorHandler.setGeneradorCodigo(p.gc);
    
    int valido = p.yyparse();
    
    //System.out.println(p.lex.getListaTokens());
    //System.out.println("\n" + p.estructuras);	
    System.out.println("Errores y Warnings detectados del codigo fuente :  \n" + p.errores());
    System.out.println("Contenido de la tabla de simbolos:  \n" + tb);
    System.out.println("Codigo intermedio en tercetos: " + gc);
    if(ErrorHandler.huboError() == 0){
	p.gw = new GeneradorWasm(p.ts, p.gc, "salida");
	p.gw.traducir();
    }
    
    if (valido == 0) {
        System.out.println("Se analizo todo el codigo fuente");
    } else {
        System.out.println("No se analizo completamente el codigo fuente, debido a uno o mas errores inesperados");
    }
}
//#line 876 "Parser.java"
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
{this.ts.addClave(val_peek(0).sval); this.ts.addAtributo(val_peek(0).sval,AccionSemantica.USO,"nombre programa"); }
break;
case 2:
//#line 10 "gramatica.y"
{ tags.get(this.tags.size()-1).tagsValidos(lex.getLineaInicial()); estructurasSintacticas("Se declaró el programa: " + val_peek(2).sval); }
break;
case 3:
//#line 12 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el nombre del programa", lex.getLineaInicial());}
break;
case 5:
//#line 17 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 6:
//#line 18 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta delimitador del programa", lex.getLineaInicial());}
break;
case 8:
//#line 24 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 9:
//#line 25 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta delimitador END del programa", lex.getLineaInicial());}
break;
case 10:
//#line 26 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta delimitador BEGIN del programa", lex.getLineaInicial());}
break;
case 11:
//#line 27 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta delimitador BEGIN y END del programa", lex.getLineaInicial());}
break;
case 14:
//#line 32 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Sentencia inválida.", lex.getLineaInicial());}
break;
case 15:
//#line 34 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 19:
//#line 43 "gramatica.y"
{	estructurasSintacticas("Se declaro la funcion, en linea: " + lex.getLineaInicial()); }
break;
case 20:
//#line 44 "gramatica.y"
{	if(esEmbebido(val_peek(0).sval)){
			ErrorHandler.addErrorSemantico("No se puede declarar una etiqueta que tenga tipos embebidos", lex.getLineaInicial());
			} else {
				estructurasSintacticas("Se declaro una etiqueta goto, en linea: " + lex.getLineaInicial());
				String etiquetaAmbito=ambitoActual+":"+val_peek(0).sval;
				this.ts.addClave(etiquetaAmbito);
				this.ts.addAtributo(etiquetaAmbito,AccionSemantica.USO,"nombre de tag");
				this.tags.get(tags.size()-1).declaracionTag(etiquetaAmbito, lex.getLineaInicial());
				gc.addTerceto("TAG", etiquetaAmbito, "-");
			}
		}
break;
case 25:
//#line 61 "gramatica.y"
{estructurasSintacticas("Se imprimio un mensaje, en linea: " + lex.getLineaInicial()); }
break;
case 27:
//#line 63 "gramatica.y"
{estructurasSintacticas("Se llamo a una etiqueta goto, en linea: " + lex.getLineaInicial()); }
break;
case 28:
//#line 67 "gramatica.y"
{ yyval.sval = val_peek(1).sval;}
break;
case 29:
//#line 69 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de paréntesis en la condición", lex.getLineaInicial());}
break;
case 30:
//#line 70 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de paréntesis derecho en la condición", lex.getLineaInicial());}
break;
case 31:
//#line 71 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de paréntesis izquierdo en la condición", lex.getLineaInicial());}
break;
case 32:
//#line 74 "gramatica.y"
{ 
				String op1 = gc.checkDeclaracion(val_peek(2).sval, lex.getLineaInicial(), this.ts, this.ambitoActual);
				String op2 = gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual);
				if(op1 != null && op2 != null){
					yyval.sval = gc.addTerceto(val_peek(1).sval, op1, op2);
					gc.checkTipo(gc.getPosActual(), lex.getLineaInicial(), this.ts, this.ambitoActual, val_peek(1).sval);
				}else {
					yyval.sval = null;
				}
			 }
break;
case 33:
//#line 84 "gramatica.y"
{ if(gc.getTerceto(gc.getPosActual()).getOp2().isEmpty()){ErrorHandler.addErrorSemantico("La longitud de los patrones a matchear es distinta.", lex.getLineaInicial());} else { yyval.sval = gc.updateCompAndGenerate(this.inicioPatron, val_peek(3).sval, this.cantPatronIzq, this.cantPatronDer, lex.getLineaInicial());} this.inicioPatron = Integer.MAX_VALUE; yyval.sval = "[" + this.gc.getPosActual() + "]";}
break;
case 34:
//#line 86 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón y el que abre la segunda", lex.getLineaInicial());}
break;
case 35:
//#line 87 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que abre la segunda lista del patrón", lex.getLineaInicial());}
break;
case 36:
//#line 88 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón", lex.getLineaInicial());}
break;
case 37:
//#line 89 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta comparador entre los patrones", lex.getLineaInicial());}
break;
case 38:
//#line 90 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta comparador entre las expresiones", lex.getLineaInicial());}
break;
case 39:
//#line 93 "gramatica.y"
{ this.iniciarPatron(); this.cantPatronIzq++; yyval.sval = gc.addTerceto("COMP", gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
break;
case 40:
//#line 96 "gramatica.y"
{ this.iniciarPatron(); this.cantPatronIzq++;yyval.sval = gc.addTerceto("COMP", gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
break;
case 41:
//#line 97 "gramatica.y"
{this.iniciarPatron(); this.cantPatronIzq=1; yyval.sval = gc.addTerceto("COMP", gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
break;
case 42:
//#line 99 "gramatica.y"
{ this.cantPatronDer++; posPatron = gc.updateAndCheckSize(this.posPatron, gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), lex.getLineaInicial(), this.ts, this.ambitoActual); this.posPatron++;}
break;
case 43:
//#line 102 "gramatica.y"
{ this.cantPatronDer++;posPatron = gc.updateAndCheckSize(this.posPatron, gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), lex.getLineaInicial(), this.ts, this.ambitoActual); this.posPatron++;}
break;
case 44:
//#line 103 "gramatica.y"
{ this.cantPatronDer = 1; posPatron = gc.updateAndCheckSize(this.posPatron, gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), lex.getLineaInicial(), this.ts, this.ambitoActual); this.posPatron++;}
break;
case 45:
//#line 106 "gramatica.y"
{estructurasSintacticas("Se definió una sentencia de control sin else, en la linea: " + lex.getLineaInicial());}
break;
case 47:
//#line 111 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta END_IF con ELSE", lex.getLineaInicial());}
break;
case 48:
//#line 112 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta END_IF", lex.getLineaInicial());}
break;
case 49:
//#line 113 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control del then", lex.getLineaInicial());}
break;
case 50:
//#line 114 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control del ELSE", lex.getLineaInicial());}
break;
case 51:
//#line 115 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control tanto en THEN como ELSE", lex.getLineaInicial());}
break;
case 52:
//#line 120 "gramatica.y"
{
			gc.actualizarBF(gc.getCantTercetos()); 
			gc.pop();
			gc.addTerceto("Label"+gc.getCantTercetos(), "FIN_IF_SOLO", "-");
			}
break;
case 53:
//#line 127 "gramatica.y"
{
			gc.addTerceto("BF", val_peek(0).sval, ""); 
			gc.push(gc.getPosActual());
		}
break;
case 54:
//#line 132 "gramatica.y"
{ 
			gc.addTerceto("BI", "", "-"); 
			int posSig = gc.getCantTercetos();
			gc.actualizarBF(posSig); 
			gc.pop(); 
			gc.push(gc.getPosActual());
			this.gc.addTerceto("Label" + posSig, "else", "-");
		}
break;
case 55:
//#line 141 "gramatica.y"
{
			int posSig = gc.getCantTercetos();
			gc.actualizarBI(posSig);
			this.gc.addTerceto("Label" + posSig, "endif", "-");
			gc.pop();
			estructurasSintacticas("Se definió una sentencia de control con else, en la linea: " + lex.getLineaInicial());
		}
break;
case 56:
//#line 151 "gramatica.y"
{yyval.sval = ">=";}
break;
case 57:
//#line 152 "gramatica.y"
{yyval.sval = "<=";}
break;
case 58:
//#line 153 "gramatica.y"
{yyval.sval = "!=";}
break;
case 59:
//#line 154 "gramatica.y"
{yyval.sval = "=";}
break;
case 60:
//#line 155 "gramatica.y"
{yyval.sval = "<";}
break;
case 61:
//#line 156 "gramatica.y"
{yyval.sval = ">";}
break;
case 64:
//#line 162 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
break;
case 65:
//#line 163 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
break;
case 66:
//#line 164 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 69:
//#line 170 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
break;
case 70:
//#line 171 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
break;
case 71:
//#line 172 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 74:
//#line 178 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 75:
//#line 182 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 76:
//#line 184 "gramatica.y"
{tipoVar = val_peek(0).sval;}
break;
case 77:
//#line 184 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 78:
//#line 187 "gramatica.y"
{checkRedeclaracion(val_peek(0).sval);}
break;
case 79:
//#line 188 "gramatica.y"
{checkRedeclaracion(val_peek(0).sval);}
break;
case 80:
//#line 189 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta coma en la lista de variables, puede haber parado la compilacion en este punto", lex.getLineaInicial());}
break;
case 81:
//#line 193 "gramatica.y"
{yyval.sval = "double";}
break;
case 82:
//#line 194 "gramatica.y"
{yyval.sval = "ulongint";}
break;
case 83:
//#line 197 "gramatica.y"
{estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());
						yyval.sval = gc.checkTipoAsignacion(val_peek(2).sval, lex.getLineaInicial(), val_peek(0).sval, this.ts,ambitoActual);}
break;
case 84:
//#line 200 "gramatica.y"
{  estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());
					yyval.sval = gc.checkTipoAsignacion(val_peek(2).sval, lex.getLineaInicial(), val_peek(0).sval, this.ts,ambitoActual);
		}
break;
case 85:
//#line 205 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "+",ambitoActual);}
break;
case 86:
//#line 207 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "-",ambitoActual);}
break;
case 87:
//#line 209 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 88:
//#line 212 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 89:
//#line 213 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");}
break;
case 90:
//#line 215 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
break;
case 91:
//#line 217 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");}
break;
case 92:
//#line 219 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
break;
case 93:
//#line 224 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "*",ambitoActual);}
break;
case 94:
//#line 225 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "/",ambitoActual);}
break;
case 95:
//#line 226 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 96:
//#line 228 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 97:
//#line 229 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 98:
//#line 230 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 99:
//#line 232 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 100:
//#line 234 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
break;
case 101:
//#line 236 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
break;
case 102:
//#line 240 "gramatica.y"
{  gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts,ambitoActual);}
break;
case 106:
//#line 246 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 107:
//#line 247 "gramatica.y"
{	if (ts.esUlongInt(val_peek(0).sval)){
					ErrorHandler.addErrorSintactico("se utilizo un Ulongint negativo, son solo positivos", lex.getLineaInicial());
				}
				else {
					ts.convertirNegativo(val_peek(0).sval);
				}
				yyval.sval = val_peek(0).sval;
			}
break;
case 108:
//#line 257 "gramatica.y"
{String tipo = gc.getTipoAccesoTripla(val_peek(1).sval, this.ts);
						   if(tipo != "ulongint"){ErrorHandler.addErrorSintactico("Se intento acceder con un tipo distinto a entero a la tripla.", lex.getLineaInicial());}

							String idTripla=gc.checkDeclaracion(val_peek(3).sval,lex.getLineaInicial(),this.ts,ambitoActual);
						   	if (idTripla != null) {		
						   	 	tipo = this.ts.getAtributo(idTripla, AccionSemantica.TIPO_BASICO); 
						   	 }else{
								ErrorHandler.addErrorSemantico( "La tripla " + idTripla + " nunca fue declarada.", lex.getLineaInicial()) ; 
								tipo = "error";
						  	  }
								yyval.sval = yyval.sval = gc.addTerceto("ASIGTRIPLA", idTripla, val_peek(1).sval, tipo);
							}
break;
case 109:
//#line 271 "gramatica.y"
{String tipo = gc.getTipoAccesoTripla(val_peek(1).sval, this.ts);
						   if(tipo != "ulongint"){ErrorHandler.addErrorSintactico("Se intento acceder con un tipo distinto a entero a la tripla.", lex.getLineaInicial());}											String idTripla=gc.checkDeclaracion(val_peek(3).sval,lex.getLineaInicial(),this.ts,ambitoActual);
						   	if (idTripla != null) {		
						   	 	tipo = this.ts.getAtributo(idTripla, AccionSemantica.TIPO_BASICO); 
						   	 }else{
								ErrorHandler.addErrorSemantico( "La tripla " + idTripla + " nunca fue declarada.", lex.getLineaInicial()) ; 
								tipo = "error";
						  	  }
								yyval.sval = yyval.sval = gc.addTerceto("ACCESOTRIPLE", idTripla, val_peek(1).sval, tipo);
							}
break;
case 110:
//#line 283 "gramatica.y"
{ if (esEmbebido(val_peek(1).sval)){ErrorHandler.addErrorSemantico("No se puede declarar una funcion con un ID con tipos embebidos.", lex.getLineaInicial());}
					else {
					this.checkRedFuncion(val_peek(1).sval, val_peek(3).sval);
					this.funcionActual = val_peek(1).sval; 
					this.ambitoActual += ":" + val_peek(1).sval;
					}
					}
break;
case 111:
//#line 289 "gramatica.y"
{ 
								this.cantRetornos.add(0); 
								this.gc_funciones.push(this.ts.getGCFuncion(this.ambitoActual)); 
								this.gc = this.gc_funciones.peek(); 
								this.tags.add(new ControlTagAmbito());
								}
break;
case 112:
//#line 294 "gramatica.y"
{
								tipoVar = val_peek(8).sval; 
								this.checkRet(val_peek(6).sval);
								this.gc_funciones.pop();
								this.gc = this.gc_funciones.peek();
								tags.get(this.tags.size()-1).tagsValidos(lex.getLineaInicial());
								tags.remove(this.tags.size()-1);
								this.cambiarAmbito();
							}
break;
case 113:
//#line 304 "gramatica.y"
{ this.cantRetornos.add(0);}
break;
case 114:
//#line 304 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta nombre de la funcion declarada", lex.getLineaInicial());
													this.checkRet("");
													}
break;
case 115:
//#line 307 "gramatica.y"
{ this.cantRetornos.add(0);}
break;
case 116:
//#line 307 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el parametro en la declaracion de la funcion", lex.getLineaInicial());
							    this.checkRet(val_peek(4).sval);
							    if (esEmbebido(val_peek(4).sval)){ErrorHandler.addErrorSemantico("No se puede declarar una funcion con un ID con tipos embebidos.", lex.getLineaInicial());}
							   }
break;
case 118:
//#line 314 "gramatica.y"
{ErrorHandler.addErrorSintactico("No se permite utilizar un tipo definido por el usuario como retorno", lex.getLineaInicial());}
break;
case 119:
//#line 317 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Se declaró más de un parametro", lex.getLineaInicial());}
break;
case 121:
//#line 322 "gramatica.y"
{ 
			this.ts.addClave(this.ambitoActual + ":" + val_peek(0).sval);
			String id_param = gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual);
			this.ts.addAtributo(id_param,AccionSemantica.TIPO, val_peek(1).sval); this.ts.addAtributo(id_param,AccionSemantica.USO,"nombre parametro"); this.ts.addAtributo(gc.checkDeclaracion(funcionActual, lex.getLineaInicial(), 				this.ts, this.ambitoActual), AccionSemantica.PARAMETRO, id_param); 
			this.ts.addAtributo(id_param, AccionSemantica.TIPO, val_peek(1).sval); estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " +			lex.getLineaInicial());}
break;
case 122:
//#line 327 "gramatica.y"
{ ErrorHandler.addErrorSintactico("No se permite una tripla como parametro", lex.getLineaInicial());}
break;
case 123:
//#line 329 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el nombre del parametro", lex.getLineaInicial());}
break;
case 124:
//#line 330 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el tipo o se intento utilizar una tripla sin nombre.", lex.getLineaInicial());}
break;
case 128:
//#line 340 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 131:
//#line 347 "gramatica.y"
{ this.cantRetornos.set(this.cantRetornos.size()-1, this.cantRetornos.get(this.cantRetornos.size()-1) + 1); 
					yyval.sval = gc.addTerceto("RET", gc.checkDeclaracion(val_peek(1).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");		
		}
break;
case 132:
//#line 352 "gramatica.y"
{funcionActual = val_peek(1).sval; }
break;
case 133:
//#line 352 "gramatica.y"
{ 
							estructurasSintacticas("Se invocó a la función: " + val_peek(4).sval + " en la linea: " + lex.getLineaInicial());
							String tipo = "";	
							String idFunc = gc.checkDeclaracion(val_peek(4).sval,lex.getLineaInicial(),this.ts,ambitoActual);
							if(idFunc != null){
								System.out.println("F invocada es: " + idFunc + "y es de tipo: " + this.ts.getAtributo(idFunc, AccionSemantica.TIPO));
								tipo = this.ts.getAtributo(idFunc, AccionSemantica.TIPO);
							}
							else {
								ErrorHandler.addErrorSemantico("La funcion invocada " + val_peek(4).sval + " no existe.", lex.getLineaInicial());
								tipo = "error";
							}
							System.out.println("El tipo de la funcion invocada es: " + tipo);
							yyval.sval = gc.addTerceto("INVOC_FUN", this.ambitoActual + ":" + this.funcionActual, val_peek(1).sval, tipo);/*porque $4? :c*/
		}
break;
case 134:
//#line 368 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de parámetros en la invocación a la función", lex.getLineaInicial());}
break;
case 135:
//#line 372 "gramatica.y"
{ yyval.sval = val_peek(0).sval;ErrorHandler.addErrorSintactico("Se utilizó más de un parámetro para invocar a la función", lex.getLineaInicial());}
break;
case 136:
//#line 373 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 137:
//#line 376 "gramatica.y"
{yyval.sval = val_peek(0).sval; gc.checkParamReal(val_peek(0).sval, lex.getLineaInicial(), this.ts,ambitoActual);}
break;
case 138:
//#line 377 "gramatica.y"
{
if(!this.ts.getAtributo(gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), AccionSemantica.TIPO).equals(val_peek(1).sval)){yyval.sval = gc.addTerceto("TO".concat(val_peek(1).sval), gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
if(!this.ts.getAtributo(this.ts.getAtributo(gc.checkDeclaracion(funcionActual, lex.getLineaInicial(), this.ts, ambitoActual), AccionSemantica.PARAMETRO), AccionSemantica.TIPO).equals(val_peek(1).sval)){ ErrorHandler.addErrorSemantico("El tipo del parametro real no coincide con el tipo del parametro formal.", lex.getLineaInicial());}}
break;
case 139:
//#line 382 "gramatica.y"
{yyval.sval = gc.addTerceto("OUTF", val_peek(1).sval, "");}
break;
case 140:
//#line 384 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el mensaje del OUTF", lex.getLineaInicial());}
break;
case 141:
//#line 385 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Parámetro invalido del OUTF", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 142:
//#line 389 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 143:
//#line 390 "gramatica.y"
{ System.out.println("CAMUL: " + val_peek(0).sval); yyval.sval = val_peek(0).sval;}
break;
case 144:
//#line 393 "gramatica.y"
{	estructurasSintacticas("Se declaró un bucle FOR en la linea: " + lex.getLineaInicial()); 
				String var = this.varFors.get(this.varFors.size()-1);
				if(!this.ts.getAtributo(val_peek(2).sval, AccionSemantica.TIPO).equals(AccionSemantica.ULONGINT)){
					ErrorHandler.addErrorSemantico("La constante de avance no es de tipo entero.", lex.getLineaInicial()); 
					gc.addTerceto("+", gc.checkDeclaracion(var, lex.getLineaInicial(), this.ts, this.ambitoActual), String.valueOf(val_peek(3).ival * Double.parseDouble(val_peek(2).sval)));
				} else {
					gc.addTerceto("+", gc.checkDeclaracion(var, lex.getLineaInicial(), this.ts, this.ambitoActual), String.valueOf(val_peek(3).ival * Integer.parseInt(val_peek(2).sval)), "ulongint");
				}		
				this.varFors.remove(this.varFors.size()-1);
				if(val_peek(5).sval != null){
					gc.addTerceto("BI", "["+String.valueOf(Integer.parseInt(val_peek(5).sval.substring(1, val_peek(5).sval.length()-1))-1)+"]", "");
					gc.actualizarBF(gc.getCantTercetos());
					gc.pop();
					this.gc.addTerceto("Label" + this.gc.getCantTercetos(), "endfor", "FOR"+this.cantFors); 
				}
				
		}
break;
case 145:
//#line 411 "gramatica.y"
{ ErrorHandler.addErrorSintactico("No se puede utilizar una constante negativa, en su lugar se debe utilizar el avance descendiente DOWN.", lex.getLineaInicial());}
break;
case 146:
//#line 412 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 147:
//#line 413 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma entre asignacion y condicion", lex.getLineaInicial());}
break;
case 148:
//#line 414 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Faltan todos los punto y coma del for", lex.getLineaInicial());}
break;
case 149:
//#line 415 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta UP/DOWN", lex.getLineaInicial());}
break;
case 150:
//#line 416 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN", lex.getLineaInicial());}
break;
case 151:
//#line 417 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 152:
//#line 418 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 153:
//#line 419 "gramatica.y"
{ { ErrorHandler.addErrorSintactico("Falta UP/DOWN, su valor, y punto y coma entre condicion y avance", lex.getLineaInicial());}}
break;
case 154:
//#line 423 "gramatica.y"
{ yyval.sval = val_peek(0).sval;
				gc.addTerceto("BF", val_peek(0).sval, "");
				gc.push(gc.getPosActual());
			}
break;
case 155:
//#line 429 "gramatica.y"
{String varFor = gc.checkDeclaracion(val_peek(2).sval,lex.getLineaInicial(),this.ts,ambitoActual);
				if (varFor != null){
					if(!this.ts.getAtributo(varFor, AccionSemantica.TIPO).equals(AccionSemantica.ULONGINT)){ErrorHandler.addErrorSemantico("La variable " + val_peek(2).sval + " no es de tipo entero.", lex.getLineaInicial());}
					gc.addTerceto(":=",varFor, val_peek(0).sval);
				}
				else{
					gc.addTerceto(":=", val_peek(2).sval, val_peek(0).sval);
				}
				if(!this.ts.getAtributo(val_peek(0).sval, AccionSemantica.TIPO).equals(AccionSemantica.ULONGINT)){ErrorHandler.addErrorSemantico("La constante " + val_peek(0).sval + " no es de tipo entero.", lex.getLineaInicial());}
				this.varFors.add(val_peek(2).sval);
				this.cantFors++;
				this.gc.addTerceto("Label" + this.gc.getCantTercetos(), "FOR"+this.cantFors, "-");
				}
break;
case 156:
//#line 444 "gramatica.y"
{yyval.ival = 1;}
break;
case 157:
//#line 445 "gramatica.y"
{yyval.ival = -1;}
break;
case 158:
//#line 448 "gramatica.y"
{ yyval.sval = gc.addTerceto("GOTO", ambitoActual + ":" + val_peek(0).sval,"");
			     this.ts.addAtributo(val_peek(0).sval,AccionSemantica.USO,"nombre etiqueta");
			     this.tags.get(tags.size()-1).huboGoto(this.ambitoActual+":"+val_peek(0).sval);
			}
break;
case 159:
//#line 453 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta la etiqueta en el GOTO, en caso de faltar también el punto y coma es posible que no compile el resto del programa o lo haga mal.", lex.getLineaInicial());
				lex.setErrorHandlerToken(";");}
break;
case 160:
//#line 459 "gramatica.y"
{this.ts.addClave(val_peek(0).sval); this.ts.addAtributo(val_peek(0).sval,AccionSemantica.USO,"nombre de tipo tripla"); estructurasSintacticas("Se declaró un tipo TRIPLE con el ID: " + val_peek(0).sval + " en la linea:" + lex.getLineaInicial()); this.ts.addAtributo(val_peek(0).sval, "tipotripla", val_peek(2).sval); this.ts.addAtributo(val_peek(0).sval, "tipo", val_peek(2).sval);}
break;
case 161:
//#line 461 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta < en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 162:
//#line 462 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta > en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 163:
//#line 463 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta > y < en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 164:
//#line 464 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta la palabra clave TRIPLE", lex.getLineaInicial());}
break;
case 165:
//#line 465 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el ID de la tripla definida.", lex.getLineaInicial());}
break;
//#line 1728 "Parser.java"
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
