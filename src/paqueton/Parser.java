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
    0,    0,    2,    2,    2,    1,    1,    1,    1,    1,
    3,    3,    3,    3,    4,    4,    5,    5,    5,    5,
    6,    6,    6,    6,    6,    6,   16,   16,   16,   16,
   17,   17,   17,   17,   17,   17,   17,   20,   22,   22,
   21,   23,   23,   12,   12,   12,   12,   12,   12,   12,
   26,   24,   27,   28,   19,   19,   19,   19,   19,   19,
   25,   25,   25,   25,   25,   30,   30,   30,   30,   30,
   29,   29,   29,    7,   33,    7,   32,   32,   32,   31,
   31,   10,   10,   35,   35,   18,   18,   18,   18,   18,
   18,   18,   18,   36,   36,   36,   36,   36,   36,   36,
   36,   36,   37,   37,   37,   37,   38,   38,   34,   42,
    8,   43,    8,   44,    8,   39,   39,   40,   40,   45,
   45,   45,   45,   46,   41,   47,   47,   47,   48,   48,
   49,   11,   11,   50,   50,   51,   51,   13,   13,   13,
   52,   52,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   54,   53,   55,   55,   15,   15,    9,    9,    9,
    9,    9,    9,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    3,    4,    3,    3,    3,    2,
    3,    1,    2,    2,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    3,    1,    2,    2,
    3,    7,    5,    6,    6,    7,    3,    3,    3,    1,
    3,    3,    1,    5,    7,    4,    4,    4,    6,    5,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    4,    2,    3,    2,    3,    4,    2,    3,    2,    3,
    3,    1,    2,    2,    0,    3,    3,    1,    3,    1,
    1,    3,    3,    1,    1,    3,    3,    1,    2,    4,
    4,    4,    4,    3,    3,    1,    2,    2,    4,    4,
    4,    4,    1,    1,    1,    1,    1,    2,    4,    0,
    8,    0,    7,    0,    7,    1,    1,    3,    1,    2,
    2,    1,    1,    0,    5,    3,    1,    2,    1,    1,
    4,    4,    3,    3,    1,    2,    1,    4,    3,    4,
    1,    1,   10,    9,    9,    8,    9,    9,    8,    8,
    8,    1,    3,    1,    1,    2,    3,    6,    5,    5,
    4,    5,    7,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    0,    0,    0,   81,   80,
    0,    0,    0,    0,   19,    1,    0,   12,   15,   16,
   17,   18,   20,   21,   22,   23,   24,   25,   26,    0,
    0,    0,    0,    0,   13,    0,    0,    0,    0,    0,
  156,    0,    0,  107,    0,    0,    0,    0,    0,  105,
   52,    0,    0,    0,  106,    0,   96,  104,    0,    0,
    0,    0,    0,    0,   14,   78,    0,    0,    0,    0,
    4,    0,   85,    0,   83,  133,    0,  137,    0,  135,
    0,    0,  157,    0,    0,    0,    0,    0,    0,    0,
  108,   97,   98,   30,    0,   55,   56,   57,   58,   59,
   60,    0,    0,    0,    0,    0,    0,    0,    7,    0,
  142,  139,  141,    0,    0,    0,    0,    9,   11,    0,
    0,   82,    0,    0,    3,    0,    0,  132,    0,  109,
    0,    0,  152,    0,   27,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   53,    0,   51,    0,    0,
   48,    0,    0,   94,    0,   95,    6,  140,  138,    0,
  161,    0,    0,   79,   77,    0,    0,    0,    0,  119,
  134,  153,    0,  154,  155,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   91,   90,   93,   92,   64,    0,
   72,    0,   62,   44,    0,   54,   50,  101,  100,  102,
   99,  160,    0,  159,  162,  114,    0,  121,  120,  112,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   33,    0,   63,   65,    0,   73,    0,   49,    0,  158,
  124,  110,  124,  118,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   34,   35,    0,   61,   71,   45,
  163,  115,    0,  124,  113,    0,    0,  149,    0,  151,
    0,    0,    0,  150,    0,  146,   36,   32,    0,  111,
   69,    0,    0,   67,  147,    0,  148,  144,  145,    0,
  130,    0,  127,  129,   68,   70,    0,  143,    0,    0,
  128,   66,    0,  125,  126,  131,
};
final static short yydgoto[] = {                          3,
   16,    4,   17,   65,   19,   20,   21,   22,   23,   24,
   25,   26,   27,   28,   29,  133,   73,   74,  104,   88,
  182,   89,  183,   54,  150,  151,  152,  197,  192,  258,
   30,   67,   39,   31,   78,   56,   57,   58,   32,  169,
  252,  254,  233,  231,  170,  253,  282,  283,  284,   79,
   80,  114,   85,  134,  177,
};
final static short yysindex[] = {                       -98,
  611,  509,    0,    0,  -45,   -8, -205,   27,    0,    0,
  474,  509,   51,  -54,    0,    0,  378,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -229,
 -119, -117,  403,  -94,    0,  510,  -21,   15, -229,  125,
    0,  -50,  -33,    0,  510,   90,  -44,  -12,  -12,    0,
    0,  172,  565,   -6,    0,   39,    0,    0,  418,   17,
  276,  -52, -100,  651,    0,    0,    6,  510,  -35,  666,
    0,   15,    0,  565,    0,    0,   15,    0,  147,    0,
   98,    6,    0,  -20,  480,  215,  565,   67,  222,   39,
    0,    0,    0,    0,   15,    0,    0,    0,    0,    0,
    0,   56,   79,   15,  711,  363,  394,  681,    0,  262,
    0,    0,    0,  267, -100,  -15,  248,    0,    0,   73,
   77,    0,  284,  174,    0,  154,  154,    0,  -30,    0,
  119,  474,    0,  -56,    0,  603,  531,   15,  154,   92,
   39,   99,   39,  154,   -8,    0,  477,    0,  298,  -74,
    0,  103,  112,    0,  122,    0,    0,    0,    0,   -3,
    0,  123,  126,    0,    0,  -25,  128,  130,  223,    0,
    0,    0,  404,    0,    0,  -75,  141,  348,  554,   15,
  154,  364,  362,  154,    0,    0,    0,    0,    0,  137,
    0,  482,    0,    0,  722,    0,    0,    0,    0,    0,
    0,    0,  -51,    0,    0,    0,  230,    0,    0,    0,
  174,  372,    5,  -32,  167,  391,   15,   15,  393,  400,
    0,   15,    0,    0,  451,    0,  103,    0,  385,    0,
    0,    0,    0,    0,  733,  410,  733,  -18,  415,  733,
  424,  733,  427,  429,    0,    0,  154,    0,    0,    0,
    0,    0,  209,    0,    0,  524,  386,    0,  733,    0,
  448,  733,  733,    0,  733,    0,    0,    0,  696,    0,
    0,  217,  556,    0,    0,  733,    0,    0,    0,  456,
    0,  361,    0,    0,    0,    0,  744,    0,  510,  636,
    0,    0,  462,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0, -193,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  226,
    0,    0,    0,  443,    0,    0,    0,    0,    0,    0,
    0,    0,  105,    0,    0,    0,    0,    0,    0,    0,
    0,   21,    0,    0,    0,  133,    0,    0,    0,  443,
    0,    0,    0,  506,    0,    0,  231,    0,    0,  511,
    0,    0,    0,  261,    0,    0,    0,    0,    0,    0,
    0,  291,    0,    0,    0,   95,  465,    0,    0,  159,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  513,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  465,  239,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   30,    0,
  190,    0,  216,   75,    0,    0,    0,    0,    0,  312,
    0,  335,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  281,  287,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  471,    0,    0,  502,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  314,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   70,   -1,    0,  610,    0,    0,    0,    0,
   -7,    0,    0,    0,    0,  515,   38,  678,  -63,    0,
  -62,    0,    0,    0,  349,  395,  397,  -89,  293,  -80,
  680,  512,    0,    7,  -34,    2,  -13,    0,    0,  411,
 -167,    0,    0,    0,  355,    0,    0, -170,    0,    0,
  455,    0,    0,  453, -136,
};
final static int YYTABLESIZE=1020;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   34,   75,  176,   50,  124,   63,   37,  115,  240,   72,
   60,   48,   46,   35,   47,  206,   49,   55,   72,   76,
   48,   46,  262,   47,  137,   49,  113,   66,   50,   50,
   50,   37,   47,  122,   92,   93,  214,   50,   50,  215,
   50,   50,   55,   55,   55,  237,  162,   90,   52,  121,
   40,   55,   55,   50,   55,   55,   48,   46,  203,   47,
   50,   49,  119,   75,   50,  255,   42,   55,  119,   50,
   37,   33,  179,   37,   55,   41,  238,   50,   55,   28,
  106,   59,   86,   55,  117,  107,  270,   50,   37,   38,
   61,   55,  154,  156,   50,   50,   50,   48,   50,   50,
   47,   55,   49,  141,  143,  228,  119,  136,   55,   55,
   55,  291,   55,   55,   38,   31,  219,  220,   31,  295,
   48,   50,   52,   47,   50,   49,  100,   99,  101,   50,
   50,   48,  186,   31,   47,   55,   49,  250,   55,  188,
  102,   68,  103,   55,   55,  103,  103,  103,  103,  103,
  185,  103,  199,   29,  243,  244,  260,  187,    1,  264,
   69,  266,  201,  103,  103,  103,  103,    9,   10,   52,
  198,   50,   50,   88,    2,   88,   88,   88,  275,   71,
  200,  277,  278,   83,  279,   55,   55,  128,  174,  175,
  129,   88,   88,   88,   88,  288,  102,  146,  103,   89,
  148,   89,   89,   89,  229,  230,   84,  174,  175,   50,
   50,   62,   94,   91,   50,    9,   10,   89,   89,   89,
   89,  123,  130,   55,   55,  239,   43,   44,   55,  103,
   86,  167,   86,   86,   86,   43,   44,    9,   10,  261,
  131,  161,    9,   10,   43,   44,    9,   10,   86,   86,
   86,   86,   36,  202,  293,  135,   87,   88,   87,   87,
   87,  120,  236,  210,  105,  138,  211,  281,  174,  175,
  232,   43,   44,  211,   87,   87,   87,   87,   28,  136,
  281,   50,  136,   89,   28,   28,   37,   37,  281,   74,
  109,   28,   37,   37,   37,   55,   37,   37,   37,   37,
   37,   84,  158,   37,   84,   37,   37,  159,   37,  163,
   37,  140,   43,   44,   86,   72,  112,   48,   46,   84,
   47,  123,   49,  166,  123,   96,   97,  122,   98,  164,
  122,   31,   31,  165,  142,   43,   44,   31,   31,   31,
   87,   31,   31,   31,   31,   31,   43,   44,   31,   76,
   31,   31,   29,   31,   41,   31,  193,   42,   29,   29,
  103,  103,  103,  103,  103,   29,  103,  103,  103,  103,
   46,  103,  103,  103,  103,  103,  172,  196,  103,  204,
  103,  103,  205,  103,  208,  103,  209,  217,   88,   88,
   88,   88,   88,   47,   88,   88,   88,   88,  216,   88,
   88,   88,   88,   88,  221,  222,   88,   47,   88,   88,
  223,   88,  235,   88,   89,   89,   89,   89,   89,  290,
   89,   89,   89,   89,  241,   89,   89,   89,   89,   89,
  167,  242,   89,  245,   89,   89,   64,   89,   47,   89,
  246,    9,   10,  251,  274,   86,   86,   86,   86,   86,
  259,   86,   86,   86,   86,  263,   86,   86,   86,   86,
   86,   70,  213,   86,  265,   86,   86,  267,   86,  268,
   86,   87,   87,   87,   87,   87,  108,   87,   87,   87,
   87,  269,   87,   87,   87,   87,   87,   74,  276,   87,
  285,   87,   87,   74,   87,  289,   87,   74,   74,   74,
   74,   12,  296,  116,   74,   10,   74,   74,   40,   74,
    5,   74,    8,   45,   43,   48,   46,   84,   47,   45,
   49,   48,   46,   84,   47,   51,   49,   84,   84,   84,
   84,  110,   43,   44,   84,  190,   84,   84,  132,   84,
  225,   84,   38,  227,  194,   39,  195,   76,  273,   72,
   82,   48,   46,   76,   47,  111,   49,   76,   76,   76,
   76,   38,   38,   38,   76,  234,   76,   76,   46,   76,
  180,   76,   48,   46,   46,   47,  207,   49,   46,   46,
   46,   46,  272,  171,  173,   46,    0,   46,   46,    0,
   46,   47,   46,  218,    0,   48,   46,   47,   47,    0,
   49,   47,   47,   47,   47,    0,    0,  102,   47,  103,
   47,   47,    0,   47,  287,   47,    0,    6,  153,   43,
   44,    0,    0,    7,  100,   99,  101,    8,    9,   10,
   11,    0,    0,    0,    6,    0,   13,   14,    0,  280,
    7,   15,    0,    0,    8,    9,   10,   11,    0,  155,
   43,   44,    0,   13,   14,    0,    0,    0,   15,    6,
    0,  212,  100,   99,  101,    7,    0,  174,  175,    8,
    9,   10,   11,    0,    6,    0,    0,    0,   13,   14,
    7,    0,    0,   15,    8,    9,   10,   11,   53,    0,
    0,    0,    0,   13,   14,    0,    0,    0,   15,   12,
    0,    0,    0,    0,    0,   12,    0,  145,    0,   12,
   12,   12,   12,    7,  149,   81,   77,    8,   12,   12,
   11,    0,   87,   12,  248,    0,   13,    0,    0,    0,
   43,   44,    0,  145,    0,    0,   43,   44,  145,    7,
    0,  116,  117,    8,    7,    0,   11,    0,    8,  126,
  189,   11,   13,    0,  127,  224,  191,   13,    0,    0,
   38,   38,   53,   38,    5,    6,   43,   44,    0,    0,
    0,    7,  139,    0,    0,    8,    9,   10,   11,    0,
  145,  144,    0,    0,   13,   14,    7,   43,   44,   15,
    8,    0,    0,   11,  160,    0,    0,  271,    0,   13,
    0,  226,    0,  168,  149,    0,    0,    0,   77,   53,
   43,   44,  145,    0,  181,  184,    0,    0,    7,    0,
   95,    0,    8,   96,   97,   11,   98,    0,    0,  286,
    0,   13,    0,    0,  249,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  257,  168,  257,    0,    0,  257,
    0,  257,    0,    0,    0,    0,  181,  181,  178,    0,
    0,   96,   97,    0,   98,  191,    5,    6,  257,    0,
    0,  257,  257,    7,  257,    0,    0,    8,    9,   10,
   11,    0,  226,   12,    0,  257,   13,   14,    0,    0,
  168,   15,    6,    0,  181,  181,  249,    0,    7,  247,
    0,    0,    8,    9,   10,   11,    0,    6,    0,  294,
    0,   13,   14,    7,  280,    0,   15,    8,    9,   10,
   11,    0,    6,    0,  118,    0,   13,   14,    7,    0,
    0,   15,    8,    9,   10,   11,    0,    6,    0,  125,
    0,   13,   14,    7,    0,    0,   15,    8,    9,   10,
   11,    0,    6,    0,  157,    0,   13,   14,    7,    0,
    0,   15,    8,    9,   10,   11,    0,  145,    0,    0,
    0,   13,   14,    7,  280,    0,   15,    8,  145,    0,
   11,    0,  146,  147,    7,  148,   13,    0,    8,  145,
    0,   11,    0,    0,  147,    7,  196,   13,    0,    8,
  145,    0,   11,    0,    0,  256,    7,    0,   13,    0,
    8,    0,    0,   11,    0,    0,    0,  292,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          1,
    2,   36,   59,   11,   40,   60,   40,   60,   41,   40,
   12,   42,   43,   59,   45,   41,   47,   11,   40,   41,
   42,   43,   41,   45,   88,   47,   61,  257,   36,   37,
   38,   40,   45,   68,   48,   49,  173,   45,   46,  176,
   48,   49,   36,   37,   38,   41,   62,   46,   11,   44,
  256,   45,   46,   61,   48,   49,   42,   43,   62,   45,
   68,   47,   64,  257,   72,  233,   40,   61,   70,   77,
   41,    2,  136,   44,   68,  281,  213,   85,   72,   59,
   42,   12,   45,   77,  278,   47,  254,   95,   59,  123,
   40,   85,  106,  107,  102,  103,  104,   42,  106,  107,
   45,   95,   47,  102,  103,  195,  108,   41,  102,  103,
  104,  282,  106,  107,  123,   41,  179,  180,   44,  290,
   42,  129,   85,   45,  132,   47,   60,   61,   62,  137,
  138,   42,   41,   59,   45,  129,   47,  227,  132,   41,
   43,  261,   45,  137,  138,   41,   42,   43,   44,   45,
   59,   47,   41,   59,  217,  218,  237,   59,  257,  240,
  278,  242,   41,   59,   60,   61,   62,  268,  269,  132,
   59,  179,  180,   41,  273,   43,   44,   45,  259,  274,
   59,  262,  263,   59,  265,  179,  180,   41,  264,  265,
   44,   59,   60,   61,   62,  276,   43,  272,   45,   41,
  275,   43,   44,   45,  256,  257,  257,  264,  265,  217,
  218,  266,   41,  258,  222,  268,  269,   59,   60,   61,
   62,  257,  125,  217,  218,  258,  257,  258,  222,  125,
   41,  257,   43,   44,   45,  257,  258,  268,  269,  258,
  261,  257,  268,  269,  257,  258,  268,  269,   59,   60,
   61,   62,  261,  257,  289,   41,   41,  125,   43,   44,
   45,  256,  258,   41,  271,   44,   44,  269,  264,  265,
   41,  257,  258,   44,   59,   60,   61,   62,  258,   41,
  282,  289,   44,  125,  264,  265,  257,  258,  290,   59,
  274,  271,  263,  264,  265,  289,  267,  268,  269,  270,
  271,   41,   41,  274,   44,  276,  277,   41,  279,   62,
  281,  256,  257,  258,  125,   40,   41,   42,   43,   59,
   45,   41,   47,   40,   44,  259,  260,   41,  262,  257,
   44,  257,  258,  257,  256,  257,  258,  263,  264,  265,
  125,  267,  268,  269,  270,  271,  257,  258,  274,   59,
  276,  277,  258,  279,   41,  281,   59,   44,  264,  265,
  256,  257,  258,  259,  260,  271,  262,  263,  264,  265,
   59,  267,  268,  269,  270,  271,  258,  275,  274,  257,
  276,  277,  257,  279,  257,  281,  257,   40,  256,  257,
  258,  259,  260,   59,  262,  263,  264,  265,  258,  267,
  268,  269,  270,  271,   41,   44,  274,   45,  276,  277,
  274,  279,   41,  281,  256,  257,  258,  259,  260,   59,
  262,  263,  264,  265,  258,  267,  268,  269,  270,  271,
  257,   41,  274,   41,  276,  277,   59,  279,   45,  281,
   41,  268,  269,   59,   59,  256,  257,  258,  259,  260,
   41,  262,  263,  264,  265,   41,  267,  268,  269,  270,
  271,   59,   59,  274,   41,  276,  277,   41,  279,   41,
  281,  256,  257,  258,  259,  260,   59,  262,  263,  264,
  265,  273,  267,  268,  269,  270,  271,  257,   41,  274,
  274,  276,  277,  263,  279,   40,  281,  267,  268,  269,
  270,   59,   41,  278,  274,    0,  276,  277,   44,  279,
    0,  281,    0,   40,   44,   42,   43,  257,   45,   40,
   47,   42,   43,  263,   45,   11,   47,  267,  268,  269,
  270,  256,  257,  258,  274,   59,  276,  277,   59,  279,
   59,  281,   41,  195,  150,   44,  150,  257,  256,   40,
   39,   42,   43,  263,   45,  280,   47,  267,  268,  269,
  270,   60,   61,   62,  274,  211,  276,  277,  257,  279,
   40,  281,   42,   43,  263,   45,  166,   47,  267,  268,
  269,  270,   59,  129,  132,  274,   -1,  276,  277,   -1,
  279,  257,  281,   40,   -1,   42,   43,  263,   45,   -1,
   47,  267,  268,  269,  270,   -1,   -1,   43,  274,   45,
  276,  277,   -1,  279,   59,  281,   -1,  257,  256,  257,
  258,   -1,   -1,  263,   60,   61,   62,  267,  268,  269,
  270,   -1,   -1,   -1,  257,   -1,  276,  277,   -1,  279,
  263,  281,   -1,   -1,  267,  268,  269,  270,   -1,  256,
  257,  258,   -1,  276,  277,   -1,   -1,   -1,  281,  257,
   -1,  258,   60,   61,   62,  263,   -1,  264,  265,  267,
  268,  269,  270,   -1,  257,   -1,   -1,   -1,  276,  277,
  263,   -1,   -1,  281,  267,  268,  269,  270,   11,   -1,
   -1,   -1,   -1,  276,  277,   -1,   -1,   -1,  281,  257,
   -1,   -1,   -1,   -1,   -1,  263,   -1,  257,   -1,  267,
  268,  269,  270,  263,  105,   38,   37,  267,  276,  277,
  270,   -1,   45,  281,  274,   -1,  276,   -1,   -1,   -1,
  257,  258,   -1,  257,   -1,   -1,  257,  258,  257,  263,
   -1,   62,   63,  267,  263,   -1,  270,   -1,  267,   72,
  274,  270,  276,   -1,   77,  274,  147,  276,   -1,   -1,
  259,  260,   85,  262,  256,  257,  257,  258,   -1,   -1,
   -1,  263,   95,   -1,   -1,  267,  268,  269,  270,   -1,
  257,  104,   -1,   -1,  276,  277,  263,  257,  258,  281,
  267,   -1,   -1,  270,  115,   -1,   -1,  274,   -1,  276,
   -1,  192,   -1,  124,  195,   -1,   -1,   -1,  129,  132,
  257,  258,  257,   -1,  137,  138,   -1,   -1,  263,   -1,
  256,   -1,  267,  259,  260,  270,  262,   -1,   -1,  274,
   -1,  276,   -1,   -1,  225,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  235,  166,  237,   -1,   -1,  240,
   -1,  242,   -1,   -1,   -1,   -1,  179,  180,  256,   -1,
   -1,  259,  260,   -1,  262,  256,  256,  257,  259,   -1,
   -1,  262,  263,  263,  265,   -1,   -1,  267,  268,  269,
  270,   -1,  273,  273,   -1,  276,  276,  277,   -1,   -1,
  211,  281,  257,   -1,  217,  218,  287,   -1,  263,  222,
   -1,   -1,  267,  268,  269,  270,   -1,  257,   -1,  274,
   -1,  276,  277,  263,  279,   -1,  281,  267,  268,  269,
  270,   -1,  257,   -1,  274,   -1,  276,  277,  263,   -1,
   -1,  281,  267,  268,  269,  270,   -1,  257,   -1,  274,
   -1,  276,  277,  263,   -1,   -1,  281,  267,  268,  269,
  270,   -1,  257,   -1,  274,   -1,  276,  277,  263,   -1,
   -1,  281,  267,  268,  269,  270,   -1,  257,   -1,   -1,
   -1,  276,  277,  263,  279,   -1,  281,  267,  257,   -1,
  270,   -1,  272,  273,  263,  275,  276,   -1,  267,  257,
   -1,  270,   -1,   -1,  273,  263,  275,  276,   -1,  267,
  257,   -1,  270,   -1,   -1,  273,  263,   -1,  276,   -1,
  267,   -1,   -1,  270,   -1,   -1,   -1,  274,   -1,  276,
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
"$$1 :",
"declaracion_var : ID $$1 lista_variables",
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
"$$2 :",
"declaracion_fun : tipo_fun FUN ID '(' lista_parametro ')' $$2 cuerpo_funcion_p",
"$$3 :",
"declaracion_fun : tipo_fun FUN '(' lista_parametro ')' $$3 cuerpo_funcion_p",
"$$4 :",
"declaracion_fun : tipo_fun FUN ID '(' ')' $$4 cuerpo_funcion_p",
"tipo_fun : tipo",
"tipo_fun : ID",
"lista_parametro : lista_parametro ',' parametro",
"lista_parametro : parametro",
"parametro : tipo ID",
"parametro : ID ID",
"parametro : tipo",
"parametro : ID",
"$$5 :",
"cuerpo_funcion_p : $$5 BEGIN bloques_funcion ';' END",
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
"for : FOR '(' asignacion_for ';' condicion_for ';' foravanc CTE ')' cuerpo_iteracion",
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

//#line 381 "gramatica.y"
String nombreArchivo;
AnalizadorLexico lex;
TablaSimbolos ts;
GeneradorCodigo gc;
Stack<GeneradorCodigo> gc_funciones;
String tipoVar;
ArrayList<Integer> cantRetornos;
String estructuras;
ArrayList<String> varFors;
String ambitoActual;
Integer inicioPatron;
Integer posPatron;
public Parser(String nombreArchivo, TablaSimbolos t, GeneradorCodigo gc)
{
	this.nombreArchivo=nombreArchivo;
	this.ambitoActual = "";
	this.inicioPatron = Integer.MAX_VALUE;
	this.posPatron = -1;
	this.ts=t;
	this.gc = gc;
	this.gc_funciones = new Stack<GeneradorCodigo>();
	this.gc_funciones.push(gc);
	this.cantRetornos = new ArrayList<>();
	this.estructuras = "Estructuras sintacticas detectadas en el codigo fuente :  \n";
	this.lex= new AnalizadorLexico(nombreArchivo, t, this);
	this.varFors = new ArrayList<>();
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

		ts.addAtributo(val,AccionSemantica.TIPO,tipoVar);
		lex.getLineaInicial();
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
    ErrorHandler.setGeneradorCodigo(p.gc);
    
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
//#line 860 "Parser.java"
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
case 27:
//#line 62 "gramatica.y"
{ yyval.sval = val_peek(1).sval;}
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
{ yyval.sval = gc.addTerceto(val_peek(1).sval, val_peek(2).sval, val_peek(0).sval); gc.checkTipo(gc.getPosActual(), lex.getLineaInicial(), this.ts);}
break;
case 32:
//#line 70 "gramatica.y"
{ if(gc.getTerceto(gc.getPosActual()).getOp2().isEmpty()){ErrorHandler.addErrorSemantico("La longitud de los patrones a matchear es distinta.", lex.getLineaInicial());}else { yyval.sval = gc.updateCompAndGenerate(this.inicioPatron, val_peek(3).sval);} this.inicioPatron = Integer.MAX_VALUE;}
break;
case 33:
//#line 72 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón y el que abre la segunda", lex.getLineaInicial());}
break;
case 34:
//#line 73 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que abre la segunda lista del patrón", lex.getLineaInicial());}
break;
case 35:
//#line 74 "gramatica.y"
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
case 38:
//#line 79 "gramatica.y"
{ this.iniciarPatron(); yyval.sval = gc.addTerceto("COMP", val_peek(0).sval, "");}
break;
case 39:
//#line 82 "gramatica.y"
{ this.iniciarPatron(); yyval.sval = gc.addTerceto("COMP", val_peek(0).sval, "");}
break;
case 40:
//#line 83 "gramatica.y"
{this.iniciarPatron(); yyval.sval = gc.addTerceto("COMP", val_peek(0).sval, "");}
break;
case 41:
//#line 86 "gramatica.y"
{ gc.updateAndCheckSize(this.posPatron, val_peek(0).sval, lex.getLineaInicial(), this.ts); this.posPatron++;}
break;
case 42:
//#line 89 "gramatica.y"
{ gc.updateAndCheckSize(this.posPatron, val_peek(0).sval, lex.getLineaInicial(), this.ts); this.posPatron++;}
break;
case 43:
//#line 90 "gramatica.y"
{ gc.updateAndCheckSize(this.posPatron, val_peek(0).sval, lex.getLineaInicial(), this.ts); this.posPatron++;}
break;
case 44:
//#line 93 "gramatica.y"
{estructurasSintacticas("Se definió una sentencia de control sin else, en la linea: " + lex.getLineaInicial());}
break;
case 46:
//#line 97 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta END_IF con ELSE", lex.getLineaInicial());}
break;
case 47:
//#line 98 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta END_IF", lex.getLineaInicial());}
break;
case 48:
//#line 99 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control del then", lex.getLineaInicial());}
break;
case 49:
//#line 100 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control del ELSE", lex.getLineaInicial());}
break;
case 50:
//#line 101 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control tanto en THEN como ELSE", lex.getLineaInicial());}
break;
case 51:
//#line 106 "gramatica.y"
{
			gc.actualizarBF(gc.getCantTercetos()); 
			gc.pop();
			}
break;
case 52:
//#line 112 "gramatica.y"
{
			gc.addTerceto("BF", val_peek(0).sval, ""); 
			gc.push(gc.getPosActual());
		}
break;
case 53:
//#line 118 "gramatica.y"
{ 
			gc.addTerceto("BI", "", "-"); 
			gc.actualizarBF(gc.getCantTercetos()); 
			gc.pop(); 
			gc.push(gc.getPosActual());
		}
break;
case 54:
//#line 125 "gramatica.y"
{
			gc.actualizarBI(gc.getCantTercetos()); 
			gc.pop();
			estructurasSintacticas("Se definió una sentencia de control con else, en la linea: " + lex.getLineaInicial());
		}
break;
case 58:
//#line 134 "gramatica.y"
{yyval.sval = "=";}
break;
case 59:
//#line 135 "gramatica.y"
{yyval.sval = "<";}
break;
case 60:
//#line 136 "gramatica.y"
{yyval.sval = ">";}
break;
case 63:
//#line 142 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
break;
case 64:
//#line 143 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
break;
case 65:
//#line 144 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 68:
//#line 150 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
break;
case 69:
//#line 151 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
break;
case 70:
//#line 152 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 73:
//#line 158 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 74:
//#line 162 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 75:
//#line 164 "gramatica.y"
{tipoVar = val_peek(0).sval;}
break;
case 76:
//#line 164 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 77:
//#line 167 "gramatica.y"
{checkRedeclaracion(val_peek(0).sval);}
break;
case 78:
//#line 168 "gramatica.y"
{checkRedeclaracion(val_peek(0).sval);}
break;
case 79:
//#line 169 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta coma en la lista de variables, puede haber parado la compilacion en este punto", lex.getLineaInicial());}
break;
case 80:
//#line 173 "gramatica.y"
{yyval.sval = "double";}
break;
case 81:
//#line 174 "gramatica.y"
{yyval.sval = "ulongint";}
break;
case 82:
//#line 177 "gramatica.y"
{estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());
						yyval.sval = gc.addTerceto(":=", val_peek(2).sval, val_peek(0).sval); gc.checkTipoAsignacion(val_peek(2).sval, lex.getLineaInicial(), val_peek(0).sval, this.ts);

					}
break;
case 83:
//#line 181 "gramatica.y"
{  estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());
					yyval.sval = gc.addTerceto(":=", val_peek(2).sval, val_peek(0).sval); gc.checkTipoAsignacion(val_peek(2).sval, lex.getLineaInicial(), val_peek(0).sval, this.ts);
		}
break;
case 86:
//#line 191 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "+");}
break;
case 87:
//#line 192 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "-");}
break;
case 89:
//#line 199 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 90:
//#line 200 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");}
break;
case 91:
//#line 202 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
break;
case 92:
//#line 204 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");}
break;
case 93:
//#line 206 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
break;
case 94:
//#line 212 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "*");}
break;
case 95:
//#line 213 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "/");}
break;
case 97:
//#line 217 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 98:
//#line 218 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 99:
//#line 219 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 100:
//#line 221 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 101:
//#line 223 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
break;
case 102:
//#line 225 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
break;
case 103:
//#line 229 "gramatica.y"
{ gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts);}
break;
case 107:
//#line 235 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 108:
//#line 236 "gramatica.y"
{	if (ts.esUlongInt(val_peek(0).sval)){
					ErrorHandler.addErrorSintactico("se utilizo un Ulongint negativo, son solo positivos", lex.getLineaInicial());
				}
				else {
					ts.convertirNegativo(val_peek(0).sval);
				}
				yyval.sval = val_peek(0).sval;
			}
break;
case 109:
//#line 246 "gramatica.y"
{yyval.sval = gc.addTerceto("ACCESOTRIPLE", val_peek(3).sval, val_peek(1).sval, this.ts.getAtributo(val_peek(3).sval, "tipo"));}
break;
case 110:
//#line 249 "gramatica.y"
{ this.cantRetornos.add(0); this.gc_funciones.push(this.ts.getGCFuncion(val_peek(3).sval)); this.gc = this.gc_funciones.peek();}
break;
case 111:
//#line 249 "gramatica.y"
{this.checkRet(val_peek(5).sval);
								this.gc_funciones.pop();
								this.gc = this.gc_funciones.peek();
								if (esEmbebido(val_peek(5).sval)){ErrorHandler.addErrorSemantico("No se puede declarar una funcion con un ID con tipos embebidos.", lex.getLineaInicial());}										;
							}
break;
case 112:
//#line 254 "gramatica.y"
{ this.cantRetornos.add(0);}
break;
case 113:
//#line 254 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta nombre de la funcion declarada", lex.getLineaInicial());
													this.checkRet("");
													}
break;
case 114:
//#line 257 "gramatica.y"
{ this.cantRetornos.add(0);}
break;
case 115:
//#line 257 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el parametro en la declaracion de la funcion", lex.getLineaInicial());
							    this.checkRet(val_peek(4).sval);
							    if (esEmbebido(val_peek(4).sval)){ErrorHandler.addErrorSemantico("No se puede declarar una funcion con un ID con tipos embebidos.", lex.getLineaInicial());}
							   }
break;
case 118:
//#line 267 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Se declaró más de un parametro", lex.getLineaInicial());}
break;
case 120:
//#line 272 "gramatica.y"
{estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 121:
//#line 273 "gramatica.y"
{estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 122:
//#line 275 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el nombre del parametro", lex.getLineaInicial());}
break;
case 123:
//#line 276 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el nombre del parametro o el tipo", lex.getLineaInicial());}
break;
case 124:
//#line 279 "gramatica.y"
{ts.addClave(yylval.sval);}
break;
case 128:
//#line 287 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 131:
//#line 294 "gramatica.y"
{ this.cantRetornos.set(this.cantRetornos.size()-1, this.cantRetornos.get(this.cantRetornos.size()-1) + 1); 
					yyval.sval = gc.addTerceto("RET", val_peek(1).sval, "");		
		}
break;
case 132:
//#line 299 "gramatica.y"
{estructurasSintacticas("Se invocó a la función: " + val_peek(3).sval + " en la linea: " + lex.getLineaInicial());
							yyval.sval = gc.addTerceto("INVOC_FUN", val_peek(3).sval, val_peek(1).sval, this.ts.getAtributo(val_peek(3).sval, AccionSemantica.TIPO));
		}
break;
case 133:
//#line 303 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de parámetros en la invocación a la función", lex.getLineaInicial());}
break;
case 134:
//#line 307 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Se utilizó más de un parámetro para invocar a la función", lex.getLineaInicial());}
break;
case 135:
//#line 308 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 136:
//#line 311 "gramatica.y"
{yyval.sval = gc.addTerceto("TO".concat(val_peek(1).sval), val_peek(0).sval, "");}
break;
case 137:
//#line 312 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 138:
//#line 318 "gramatica.y"
{yyval.sval = gc.addTerceto("OUTF", val_peek(1).sval, "");}
break;
case 139:
//#line 320 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el mensaje del OUTF", lex.getLineaInicial());}
break;
case 140:
//#line 321 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Parámetro invalido del OUTF", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 143:
//#line 329 "gramatica.y"
{	estructurasSintacticas("Se declaró un bucle FOR en la linea: " + lex.getLineaInicial());
													String var = this.varFors.get(this.varFors.size()-1);
													gc.addTerceto("+", var, String.valueOf(val_peek(3).ival * Integer.parseInt(val_peek(2).sval)));
													this.varFors.remove(this.varFors.size()-1);
													gc.addTerceto("BI", val_peek(5).sval, "");
													gc.actualizarBF(gc.getCantTercetos());
													gc.pop();
		}
break;
case 144:
//#line 338 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 145:
//#line 339 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma entre asignacion y condicion", lex.getLineaInicial());}
break;
case 146:
//#line 340 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Faltan todos los punto y coma del for", lex.getLineaInicial());}
break;
case 147:
//#line 341 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta UP/DOWN", lex.getLineaInicial());}
break;
case 148:
//#line 342 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN", lex.getLineaInicial());}
break;
case 149:
//#line 343 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 150:
//#line 344 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 151:
//#line 345 "gramatica.y"
{ { ErrorHandler.addErrorSintactico("Falta UP/DOWN, su valor, y punto y coma entre condicion y avance", lex.getLineaInicial());}}
break;
case 152:
//#line 349 "gramatica.y"
{ yyval.sval = val_peek(0).sval;
				gc.addTerceto("BF", val_peek(0).sval, "");
				gc.push(gc.getPosActual());
			}
break;
case 153:
//#line 355 "gramatica.y"
{ gc.addTerceto(":=", val_peek(2).sval, val_peek(0).sval);
				this.varFors.add(val_peek(2).sval);
				}
break;
case 154:
//#line 360 "gramatica.y"
{yyval.ival = 1;}
break;
case 155:
//#line 361 "gramatica.y"
{yyval.ival = -1;}
break;
case 156:
//#line 364 "gramatica.y"
{ yyval.sval = gc.addTerceto("GOTO", val_peek(0).sval,"");}
break;
case 157:
//#line 366 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta la etiqueta en el GOTO, en caso de faltar también el punto y coma es posible que no compile el resto del programa o lo haga mal.", lex.getLineaInicial());
				lex.setErrorHandlerToken(";");}
break;
case 158:
//#line 372 "gramatica.y"
{estructurasSintacticas("Se declaró un tipo TRIPLE con el ID: " + val_peek(0).sval + " en la linea:" + lex.getLineaInicial()); this.ts.addAtributo(val_peek(0).sval, "tipotripla", val_peek(2).sval); this.ts.addAtributo(val_peek(0).sval, "tipo", val_peek(2).sval);}
break;
case 159:
//#line 374 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta < en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 160:
//#line 375 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta > en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 161:
//#line 376 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta > y < en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 162:
//#line 377 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta la palabra clave TRIPLE", lex.getLineaInicial());}
break;
case 163:
//#line 378 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el ID de la tripla definida.", lex.getLineaInicial());}
break;
//#line 1572 "Parser.java"
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
