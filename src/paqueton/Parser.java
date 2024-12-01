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
	import java.nio.file.Paths;
	import java.util.Stack;
//#line 23 "Parser.java"




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
    6,    7,    7,    7,    7,    7,    7,    7,   18,   18,
   18,   18,   19,   19,   19,   19,   19,   19,   19,   22,
   24,   24,   23,   25,   25,   13,   13,   13,   13,   13,
   13,   13,   28,   26,   29,   30,   21,   21,   21,   21,
   21,   21,   27,   27,   27,   27,   27,   32,   32,   32,
   32,   32,   31,   31,   31,    8,   35,    8,   34,   34,
   34,   33,   33,   11,   11,   20,   20,   20,   20,   20,
   20,   20,   20,   37,   37,   37,   37,   37,   37,   37,
   37,   37,   38,   38,   38,   38,   39,   39,   36,   40,
   43,   45,    9,   46,    9,   47,    9,   41,   41,   42,
   42,   48,   48,   48,   48,   44,   49,   49,   49,   50,
   16,   52,   12,   12,   51,   51,   53,   53,   14,   14,
   14,   54,   54,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   56,   55,   57,   57,   17,   17,   10,
   10,   10,   10,   10,   10,
};
final static short yylen[] = {                            2,
    0,    3,    1,    4,    3,    3,    4,    3,    3,    3,
    2,    3,    1,    2,    2,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    3,    1,
    2,    2,    3,    7,    5,    6,    6,    7,    3,    3,
    3,    1,    3,    3,    1,    5,    7,    4,    4,    4,
    6,    5,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    4,    2,    3,    2,    3,    4,    2,    3,
    2,    3,    3,    1,    2,    2,    0,    3,    3,    1,
    3,    1,    1,    3,    3,    3,    3,    1,    2,    4,
    4,    4,    4,    3,    3,    1,    2,    2,    4,    4,
    4,    4,    1,    1,    1,    1,    1,    2,    4,    4,
    0,    0,    9,    0,    7,    0,    7,    1,    1,    3,
    1,    2,    2,    1,    1,    4,    3,    1,    2,    1,
    4,    0,    5,    3,    3,    1,    1,    2,    4,    3,
    4,    1,    1,   10,   10,    9,    9,    8,    9,    9,
    8,    8,    8,    1,    3,    1,    1,    2,    3,    6,
    5,    5,    4,    5,    7,
};
final static short yydefred[] = {                         0,
    1,    0,    0,    3,    0,    0,    0,    0,    0,   83,
   82,    0,    0,    0,    0,   20,    0,    0,   16,   17,
   18,   19,   21,   22,   23,   24,   25,   26,   27,   28,
    0,    0,    0,    0,    2,    0,   13,   14,    0,    0,
    0,    0,    0,  158,    0,    0,  107,    0,    0,    0,
    0,    0,  105,   54,    0,    0,    0,    0,   96,  104,
  106,    0,    0,    0,    0,    0,   15,    5,   80,    0,
    0,    0,    0,    0,    0,  134,    0,    0,    0,  159,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  108,
   97,   98,   32,    0,   57,   58,   59,   60,   61,   62,
    0,    0,    0,    0,    0,    0,    0,  143,  140,    0,
    0,    0,    0,    0,    0,    4,   12,    0,    0,    0,
    0,    0,    8,    0,   10,    0,    0,    0,  136,  109,
    0,    0,  154,    0,    0,    0,   29,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   55,    0,   53,
    0,    0,   50,    0,    0,   94,    0,   95,  141,  139,
    0,  163,    0,    0,  131,   81,   79,    0,    0,    0,
    0,  121,    7,    0,  133,    0,  155,    0,  156,  157,
    0,    0,  110,    0,    0,    0,    0,    0,    0,    0,
   91,   90,   93,   92,   66,    0,   74,    0,   64,   46,
    0,   56,   52,  101,  100,  102,   99,  162,    0,  161,
  164,  116,    0,  123,  122,  114,    0,  135,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   35,    0,   65,
   67,    0,   75,    0,   51,    0,  160,    0,    0,    0,
  120,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   36,   37,    0,   63,   73,   47,  165,    0,
  117,  112,  115,    0,    0,  151,    0,  153,    0,    0,
    0,  152,    0,    0,  148,   38,   34,  130,    0,  128,
    0,   71,    0,    0,   69,  149,    0,  150,  146,    0,
  147,    0,  129,  113,   70,   72,    0,  144,  145,  126,
  127,   68,
};
final static short yydgoto[] = {                          3,
   35,    5,    4,   17,   67,   19,   20,   21,   22,   23,
   24,   25,   26,   27,   28,   29,   30,  133,   55,  187,
  103,   87,  188,   88,  189,   57,  152,  153,  154,  203,
  198,  266,   31,   70,   42,   32,   58,   59,   60,   61,
   33,  171,  213,  261,  281,  240,  238,  172,  279,  280,
  128,   77,  129,  111,   82,  134,  182,
};
final static short yysindex[] = {                      -167,
    0,  594,    0,    0,  -75,   11,  -23, -225,   49,    0,
    0,  445,   62,  -51,   87,    0,  273, -137,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -109, -102, -117,  594,    0,  294,    0,    0,  129,  127,
  129, -109,  107,    0,  -87,  -26,    0,  456,  422,  -85,
  -11,  -11,    0,    0,  142,  524,  -84,   35,    0,    0,
    0,  109,  -58, -221,  129,  666,    0,    0,    0,   -3,
  129,  -39,  125,  689,  110,    0,  115,  -24,   -3,    0,
  -70,  395,  129,  129,  148,  524,  416,  153,   35,    0,
    0,    0,    0,  129,    0,    0,    0,    0,    0,    0,
  288,  582,  129,  534,   18,  381,  183,    0,    0,  110,
  224, -221,  -49,  208,   85,    0,    0,   10,   40,  110,
  259, -165,    0,  704,    0,  110,  129,   74,    0,    0,
   44,  445,    0,  -48,   -5,  110,    0,  631,  492,  129,
  110,    2,   35,   17,   35,  110,  -23,    0,  341,    0,
  245, -156,    0,   41,   19,    0,   46,    0,    0,    0,
  -44,    0,   65,   66,    0,    0,    0,  284,   71,   77,
   88,    0,    0,  110,    0,  115,    0,  401,    0,    0,
  -86,   83,    0,  306,  513,  129,  110,  314,  308,  110,
    0,    0,    0,    0,    0,   84,    0,  356,    0,    0,
  749,    0,    0,    0,    0,    0,    0,    0,  -71,    0,
    0,    0, -165,    0,    0,    0, -165,    0,  322,  -29,
  -15,  106,  330,  129,  129,  333,  335,    0,  129,    0,
    0,  -67,    0,   41,    0,  318,    0,  105,   90,  105,
    0,  355,  340,  355,  -13,  357,  355,  138,  364,  355,
  370,  372,    0,    0,  110,    0,    0,    0,    0,  734,
    0,    0,    0,  384,  361,    0,  355,    0,  387,  355,
  355,    0,  392,  355,    0,    0,    0,    0,  311,    0,
  105,    0,  167,  407,    0,    0,  355,    0,    0,  355,
    0,  719,    0,    0,    0,    0,  551,    0,    0,    0,
    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0, -207,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  326,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  175,    0,    0,    0,    0,    0,    0,    0,    0,  122,
    0,    0,    0,    0,    0,  -37,    0,    0,    0,    0,
    0,    0,    0,    0,  301,    0,    0,   -8,    0,    0,
    0,    0,    0,    0,    0,  455,    0,    0,    0,  140,
    0,    0,    0,  465,  155,    0,    0,    0,  182,    0,
    0,    0,    0,    0,  385,  426,    0,    0,   24,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  430,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  205,
    0,    0,    0,  480,    0,   92,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  426,    0,    0,    0,    0,
   14,    0,   50,    0,   80,  104,    0,    0,    0,    0,
    0,  237,    0,  252,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -161,   94,  102,
    0,    0,    0,  103,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  439,    0,    0,  386,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  136,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,   15,   -2,    0,  654,    0,    0,    0,
    0,  660,    0,    0,    0,    0,    0,  477,  443,  740,
  -72,    0, -111,    0,    0,    0,  292,  343,  345, -172,
  238,  486,  652,  466,    0,    0,  -22,   -7,    0,    0,
    0,  297,    0, -224,    0,    0,    0,  295,    0, -233,
    0,    0,  347,    0,    0,  393, -139,
};
final static int YYTABLESIZE=1028;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
  122,  112,   37,  103,  103,  103,  103,  103,   64,  103,
  181,  244,  163,   40,  139,  263,   40,  209,  101,   36,
  102,  103,  103,  103,  103,  247,   89,  270,  235,  248,
   43,   37,   88,   50,   88,   88,   88,  101,  221,  102,
  119,  222,  192,   91,   92,  293,   10,   11,   73,   77,
   88,   88,   88,   88,   39,   44,  294,  194,  301,  205,
  191,  258,   50,  117,   89,  185,   89,   89,   89,   38,
  119,  117,   39,  226,  227,  193,  105,  204,  143,  145,
  245,  106,   89,   89,   89,   89,  207,  103,   45,    1,
   86,  169,   86,   86,   86,  111,   83,  156,  158,   41,
  130,   62,   10,   11,  206,    2,  111,  111,   86,   86,
   86,   86,  251,  252,  175,  148,   88,  176,  150,  183,
   87,  117,   87,   87,   87,  165,   65,  101,  216,  102,
  262,  217,  137,  217,  125,  137,   68,  125,   87,   87,
   87,   87,  124,  138,   33,  124,  138,   69,   89,  109,
   51,   49,  101,   50,  102,   52,   51,   49,   71,   50,
   72,   52,   33,  132,  132,   80,  132,   76,  132,   81,
   51,   49,   90,   50,   86,   52,   43,  179,  180,   44,
    6,    7,   93,  124,  236,  237,  104,    8,  137,  147,
  131,    9,   10,   11,   12,    8,  140,   34,   76,    9,
   13,   14,   12,   15,   87,   16,  256,  162,   13,   10,
   11,   15,  208,   85,   63,  179,  180,  121,  103,  103,
  103,  103,  103,  159,  103,  103,  103,  103,  243,  103,
  103,  103,  103,  103,  179,  180,  103,   39,  103,  103,
   78,  103,  246,  103,  269,   46,   47,   88,   88,   88,
   88,   88,  118,   88,   88,   88,   88,  278,   88,   88,
   88,   88,   88,   84,  160,   88,  166,   88,   88,  164,
   88,   39,   88,  155,   46,   47,  278,   39,   39,   89,
   89,   89,   89,   89,   39,   89,   89,   89,   89,  278,
   89,   89,   89,   89,   89,   48,  167,   89,  168,   89,
   89,  177,   89,  199,   89,   86,   86,   86,   86,   86,
   49,   86,   86,   86,   86,  202,   86,   86,   86,   86,
   86,  210,  211,   86,  212,   86,   86,  214,   86,   51,
   86,   66,   50,  215,   52,   87,   87,   87,   87,   87,
  223,   87,   87,   87,   87,  224,   87,   87,   87,   87,
   87,  229,   74,   87,  228,   87,   87,  230,   87,   30,
   87,   33,  242,  249,  107,   46,   47,   33,   33,  292,
  250,   46,   47,  253,   33,  254,  259,  260,  132,  132,
  267,    7,   10,   11,   13,   46,   47,    8,  108,  132,
  132,    9,   10,   11,   12,  273,   76,  271,  123,  196,
   13,   14,   76,   15,  274,   16,   76,   76,   76,   76,
  276,   85,  277,   76,  232,   76,   76,   85,   76,  285,
   76,   85,   85,   85,   85,   50,   40,  287,   85,   41,
   85,   85,  290,   85,   48,   85,   51,   49,   78,   50,
  295,   52,  283,   31,   78,   40,   40,   40,   78,   78,
   78,   78,  118,  132,    6,   78,  138,   78,   78,  220,
   78,   84,   78,   51,   11,  297,   50,   84,   52,   42,
  142,   84,   84,   84,   84,   99,   98,  100,   84,    9,
   84,   84,   45,   84,   48,   84,   51,   49,   54,   50,
   85,   52,  234,   48,  200,   84,  201,   51,   49,   48,
   50,  284,   52,   48,   48,   48,   48,   79,   49,  239,
   48,  241,   48,   48,   49,   48,    0,   48,   49,   49,
   49,   49,  218,    0,  178,   49,    0,   49,   49,    7,
   49,  186,   49,   51,   49,    8,   50,    0,   52,    9,
   10,   11,   12,  142,   46,   47,    0,    0,   13,   14,
    7,   15,  225,   16,   51,   49,    8,   50,   30,   52,
    9,   10,   11,   12,   30,   30,  101,    7,  102,   13,
   14,   30,   15,    8,   16,    0,    0,    9,   10,   11,
   12,    0,   13,   99,   98,  100,   13,   14,   13,   15,
    0,   16,   13,   13,   13,   13,    0,  147,    0,    0,
    0,   13,   13,    8,   13,    0,   13,    9,    0,    0,
   12,  147,  147,    0,  195,    0,   13,    8,    8,   15,
    0,    9,    9,   51,   12,   12,   50,  264,   52,  231,
   13,   13,    0,   15,   15,    0,  157,   46,   47,    0,
  147,    0,   31,    0,   40,   40,    8,   40,   31,   31,
    9,   46,   47,   12,    0,   31,    0,  282,  219,   13,
    0,    0,   15,  147,  179,  180,    0,    0,    0,    8,
    0,   53,    0,    9,   95,   96,   12,   97,   46,   47,
  296,    0,   13,    0,    0,   15,    0,    0,    0,    0,
   99,   98,  100,    0,    0,    0,    0,    0,   53,    0,
   53,   46,   47,    0,    0,    0,    0,   53,   53,    0,
   53,   53,   46,   47,  113,  114,    0,    0,    0,    0,
    0,   53,    0,    0,   53,    0,    0,    0,  127,  268,
   53,    0,  272,    0,    0,  275,   53,    0,    0,    0,
    0,   53,   53,   53,    0,    0,    0,    0,   46,   47,
    0,   56,  286,   53,    0,  288,  289,  151,    0,  291,
   53,   53,   53,  161,   53,   53,    0,    0,    0,   46,
   47,    0,  298,  170,    0,  299,    0,    0,   75,   94,
   78,    0,   95,   96,    0,   97,   53,   86,    0,    0,
  147,   53,    0,    0,    0,    0,    8,    0,   53,   53,
    9,  110,  197,   12,  115,  148,  149,  147,  150,   13,
  120,    0,   15,    8,    0,    0,  126,    9,    0,    0,
   12,   56,  135,  136,  302,    0,   13,  127,    0,   15,
    0,    0,    0,  141,    0,   53,    0,  144,   46,   47,
    0,    0,  146,    0,   53,   53,    0,    0,    0,    6,
    7,  233,    0,    0,  151,    0,    8,    0,    0,    0,
    9,   10,   11,   12,  170,    0,  174,    0,  170,   13,
   14,   56,   15,    0,   16,    0,    0,    0,    0,  190,
    0,    0,    0,   53,   53,  257,  184,    0,   53,   95,
   96,    0,   97,    0,    0,  265,    0,  265,    0,    0,
  265,    0,    0,  265,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  126,    0,  197,    0,    0,
  265,    0,    7,  265,  265,    0,    0,  265,    8,    0,
    0,    0,    9,   10,   11,   12,    0,  233,    0,  116,
  265,   13,   14,  265,   15,    7,   16,    0,    0,    0,
  257,    8,    0,    0,    0,    9,   10,   11,   12,    0,
    7,    0,  125,    0,   13,   14,    8,   15,  255,   16,
    9,   10,   11,   12,    0,    7,    0,  173,    0,   13,
   14,    8,   15,    0,   16,    9,   10,   11,   12,    0,
    7,    0,  300,    0,   13,   14,    8,   15,    0,   16,
    9,   10,   11,   12,    0,  147,    0,    0,    0,   13,
   14,    8,   15,    0,   16,    9,    0,    0,   12,    0,
    0,  149,    0,  202,   13,    0,    0,   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   40,   60,    5,   41,   42,   43,   44,   45,   60,   47,
   59,   41,   62,   40,   87,  240,   40,   62,   43,    5,
   45,   59,   60,   61,   62,   41,   49,   41,  201,   45,
  256,   34,   41,   45,   43,   44,   45,   43,  178,   45,
   44,  181,   41,   51,   52,  279,  268,  269,   34,  257,
   59,   60,   61,   62,   41,  281,  281,   41,  292,   41,
   59,  234,   45,   66,   41,  138,   43,   44,   45,   59,
  278,   74,   59,  185,  186,   59,   42,   59,  101,  102,
  220,   47,   59,   60,   61,   62,   41,  125,   40,  257,
   41,  257,   43,   44,   45,  257,  123,  105,  106,  123,
  125,   40,  268,  269,   59,  273,  268,  269,   59,   60,
   61,   62,  224,  225,   41,  272,  125,   44,  275,  125,
   41,  124,   43,   44,   45,   41,   40,   43,   41,   45,
   41,   44,   41,   44,   41,   44,  274,   44,   59,   60,
   61,   62,   41,   41,   41,   44,   44,  257,  125,   41,
   42,   43,   43,   45,   45,   47,   42,   43,  261,   45,
  278,   47,   59,   42,   43,   59,   45,   41,   47,  257,
   42,   43,  258,   45,  125,   47,   41,  264,  265,   44,
  256,  257,   41,   59,  256,  257,  271,  263,   41,  257,
  261,  267,  268,  269,  270,  263,   44,  273,   59,  267,
  276,  277,  270,  279,  125,  281,  274,  257,  276,  268,
  269,  279,  257,   59,  266,  264,  265,  257,  256,  257,
  258,  259,  260,   41,  262,  263,  264,  265,  258,  267,
  268,  269,  270,  271,  264,  265,  274,  261,  276,  277,
   59,  279,  258,  281,  258,  257,  258,  256,  257,  258,
  259,  260,  256,  262,  263,  264,  265,  260,  267,  268,
  269,  270,  271,   59,   41,  274,  257,  276,  277,   62,
  279,  258,  281,  256,  257,  258,  279,  264,  265,  256,
  257,  258,  259,  260,  271,  262,  263,  264,  265,  292,
  267,  268,  269,  270,  271,   59,  257,  274,   40,  276,
  277,  258,  279,   59,  281,  256,  257,  258,  259,  260,
   59,  262,  263,  264,  265,  275,  267,  268,  269,  270,
  271,  257,  257,  274,   41,  276,  277,  257,  279,   42,
  281,   59,   45,  257,   47,  256,  257,  258,  259,  260,
  258,  262,  263,  264,  265,   40,  267,  268,  269,  270,
  271,   44,   59,  274,   41,  276,  277,  274,  279,   59,
  281,  258,   41,  258,  256,  257,  258,  264,  265,   59,
   41,  257,  258,   41,  271,   41,   59,  273,  257,  258,
   41,  257,  268,  269,   59,  257,  258,  263,  280,  268,
  269,  267,  268,  269,  270,  258,  257,   41,  274,   59,
  276,  277,  263,  279,   41,  281,  267,  268,  269,  270,
   41,  257,   41,  274,   59,  276,  277,  263,  279,   59,
  281,  267,  268,  269,  270,   45,   41,   41,  274,   44,
  276,  277,   41,  279,   40,  281,   42,   43,  257,   45,
  274,   47,   59,   59,  263,   60,   61,   62,  267,  268,
  269,  270,  278,   59,    0,  274,   41,  276,  277,   59,
  279,  257,  281,   42,    0,   59,   45,  263,   47,   44,
   41,  267,  268,  269,  270,   60,   61,   62,  274,    0,
  276,  277,   44,  279,   40,  281,   42,   43,   12,   45,
   48,   47,  201,  257,  152,   40,  152,   42,   43,  263,
   45,  264,   47,  267,  268,  269,  270,   42,  257,  213,
  274,  217,  276,  277,  263,  279,   -1,  281,  267,  268,
  269,  270,  176,   -1,  132,  274,   -1,  276,  277,  257,
  279,   40,  281,   42,   43,  263,   45,   -1,   47,  267,
  268,  269,  270,  256,  257,  258,   -1,   -1,  276,  277,
  257,  279,   40,  281,   42,   43,  263,   45,  258,   47,
  267,  268,  269,  270,  264,  265,   43,  257,   45,  276,
  277,  271,  279,  263,  281,   -1,   -1,  267,  268,  269,
  270,   -1,  257,   60,   61,   62,  276,  277,  263,  279,
   -1,  281,  267,  268,  269,  270,   -1,  257,   -1,   -1,
   -1,  276,  277,  263,  279,   -1,  281,  267,   -1,   -1,
  270,  257,  257,   -1,  274,   -1,  276,  263,  263,  279,
   -1,  267,  267,   42,  270,  270,   45,  273,   47,  274,
  276,  276,   -1,  279,  279,   -1,  256,  257,  258,   -1,
  257,   -1,  258,   -1,  259,  260,  263,  262,  264,  265,
  267,  257,  258,  270,   -1,  271,   -1,  274,  258,  276,
   -1,   -1,  279,  257,  264,  265,   -1,   -1,   -1,  263,
   -1,   12,   -1,  267,  259,  260,  270,  262,  257,  258,
  274,   -1,  276,   -1,   -1,  279,   -1,   -1,   -1,   -1,
   60,   61,   62,   -1,   -1,   -1,   -1,   -1,   39,   -1,
   41,  257,  258,   -1,   -1,   -1,   -1,   48,   49,   -1,
   51,   52,  257,  258,   63,   64,   -1,   -1,   -1,   -1,
   -1,   62,   -1,   -1,   65,   -1,   -1,   -1,   77,  244,
   71,   -1,  247,   -1,   -1,  250,   77,   -1,   -1,   -1,
   -1,   82,   83,   84,   -1,   -1,   -1,   -1,  257,  258,
   -1,   12,  267,   94,   -1,  270,  271,  104,   -1,  274,
  101,  102,  103,  112,  105,  106,   -1,   -1,   -1,  257,
  258,   -1,  287,  122,   -1,  290,   -1,   -1,   39,  256,
   41,   -1,  259,  260,   -1,  262,  127,   48,   -1,   -1,
  257,  132,   -1,   -1,   -1,   -1,  263,   -1,  139,  140,
  267,   62,  149,  270,   65,  272,  273,  257,  275,  276,
   71,   -1,  279,  263,   -1,   -1,   77,  267,   -1,   -1,
  270,   82,   83,   84,  274,   -1,  276,  176,   -1,  279,
   -1,   -1,   -1,   94,   -1,  176,   -1,  256,  257,  258,
   -1,   -1,  103,   -1,  185,  186,   -1,   -1,   -1,  256,
  257,  198,   -1,   -1,  201,   -1,  263,   -1,   -1,   -1,
  267,  268,  269,  270,  213,   -1,  127,   -1,  217,  276,
  277,  132,  279,   -1,  281,   -1,   -1,   -1,   -1,  140,
   -1,   -1,   -1,  224,  225,  232,  256,   -1,  229,  259,
  260,   -1,  262,   -1,   -1,  242,   -1,  244,   -1,   -1,
  247,   -1,   -1,  250,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  176,   -1,  264,   -1,   -1,
  267,   -1,  257,  270,  271,   -1,   -1,  274,  263,   -1,
   -1,   -1,  267,  268,  269,  270,   -1,  284,   -1,  274,
  287,  276,  277,  290,  279,  257,  281,   -1,   -1,   -1,
  297,  263,   -1,   -1,   -1,  267,  268,  269,  270,   -1,
  257,   -1,  274,   -1,  276,  277,  263,  279,  229,  281,
  267,  268,  269,  270,   -1,  257,   -1,  274,   -1,  276,
  277,  263,  279,   -1,  281,  267,  268,  269,  270,   -1,
  257,   -1,  274,   -1,  276,  277,  263,  279,   -1,  281,
  267,  268,  269,  270,   -1,  257,   -1,   -1,   -1,  276,
  277,  263,  279,   -1,  281,  267,   -1,   -1,  270,   -1,
   -1,  273,   -1,  275,  276,   -1,   -1,  279,
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
"cuerpo : BEGIN sentencias END",
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
"sentec_eject : retorno",
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
"bloque_funcion : sentencia",
"retorno : RET '(' expresion_matematica ')'",
"$$7 :",
"invoc_fun : ID '(' $$7 lista_parametro_real ')'",
"invoc_fun : ID '(' ')'",
"lista_parametro_real : lista_parametro_real ',' param_real",
"lista_parametro_real : param_real",
"param_real : expresion_matematica",
"param_real : tipo expresion_matematica",
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

//#line 483 "gramatica.y"
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
String idFuncion;
Integer inicioPatron;
Integer posPatron;
Integer cantPatronIzq;
Integer cantPatronDer;
Integer cantFors;
public Parser(String nombreArchivo, TablaSimbolos t, GeneradorCodigo gc)
{
	this.nombreArchivo=nombreArchivo;
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
        System.out.println("Ingrese el nombre del archivo sin extension. , el archivo debe estar en TPCompilador/src/codes");
        return;
    }

    // Tomamos el primer argumento como el valor de prueba
    String prueba = args[0];
    */
    String prueba = "casosValidos";
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
	p.gw = new GeneradorWasm(p.ts, p.gc, "assembly");
	p.gw.traducir();
	System.out.println("Se debe realizar la conversion del archivo: wat2wasm assembly.wat -o assembly.wasm . En TPCompilador/wat2wasm/bin");

    }
    System.out.println(ErrorHandler.erroresTraduccion());
    
    if (valido == 0) {
        System.out.println("Se analizo todo el codigo fuente");
    } else {
        System.out.println("No se analizo completamente el codigo fuente, debido a uno o mas errores inesperados");
    }
}
//#line 894 "Parser.java"
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
//#line 11 "gramatica.y"
{this.ts.addClave(val_peek(0).sval); this.ts.addAtributo(val_peek(0).sval,AccionSemantica.USO,"nombre programa"); }
break;
case 2:
//#line 11 "gramatica.y"
{ tags.get(this.tags.size()-1).tagsValidos(lex.getLineaInicial()); estructurasSintacticas("Se declaró el programa: " + val_peek(2).sval); }
break;
case 3:
//#line 13 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el nombre del programa", lex.getLineaInicial());}
break;
case 5:
//#line 18 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 6:
//#line 19 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta delimitador del programa", lex.getLineaInicial());}
break;
case 8:
//#line 24 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma prueba", lex.getLineaInicial());}
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
case 28:
//#line 64 "gramatica.y"
{estructurasSintacticas("Se llamo a una etiqueta goto, en linea: " + lex.getLineaInicial()); }
break;
case 29:
//#line 68 "gramatica.y"
{ yyval.sval = val_peek(1).sval;}
break;
case 30:
//#line 70 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de paréntesis en la condición", lex.getLineaInicial());}
break;
case 31:
//#line 71 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de paréntesis derecho en la condición", lex.getLineaInicial());}
break;
case 32:
//#line 72 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de paréntesis izquierdo en la condición", lex.getLineaInicial());}
break;
case 33:
//#line 75 "gramatica.y"
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
case 34:
//#line 85 "gramatica.y"
{ if(gc.getTerceto(gc.getPosActual()).getOp2().isEmpty()){ErrorHandler.addErrorSemantico("La longitud de los patrones a matchear es distinta.", lex.getLineaInicial());} else { yyval.sval = gc.updateCompAndGenerate(this.inicioPatron, val_peek(3).sval, this.cantPatronIzq, this.cantPatronDer, lex.getLineaInicial());} this.inicioPatron = Integer.MAX_VALUE; yyval.sval = "[" + this.gc.getPosActual() + "]";}
break;
case 35:
//#line 87 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón y el que abre la segunda", lex.getLineaInicial());}
break;
case 36:
//#line 88 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que abre la segunda lista del patrón", lex.getLineaInicial());}
break;
case 37:
//#line 89 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón", lex.getLineaInicial());}
break;
case 38:
//#line 90 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta comparador entre los patrones", lex.getLineaInicial());}
break;
case 39:
//#line 91 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta comparador entre las expresiones", lex.getLineaInicial());}
break;
case 40:
//#line 94 "gramatica.y"
{ this.iniciarPatron(); this.cantPatronIzq++; yyval.sval = gc.addTerceto("COMP", gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
break;
case 41:
//#line 97 "gramatica.y"
{

this.iniciarPatron(); this.cantPatronIzq++;yyval.sval = gc.addTerceto("COMP", gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
break;
case 42:
//#line 100 "gramatica.y"
{
				
		gc.addTerceto("PATRON", "-", "-");
		this.iniciarPatron(); this.cantPatronIzq=1; yyval.sval = gc.addTerceto("COMP", gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
break;
case 43:
//#line 106 "gramatica.y"
{
this.cantPatronDer++; posPatron = gc.updateAndCheckSize(this.posPatron, gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), lex.getLineaInicial(), this.ts, this.ambitoActual); this.posPatron++;}
break;
case 44:
//#line 110 "gramatica.y"
{ this.cantPatronDer++;posPatron = gc.updateAndCheckSize(this.posPatron, gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), lex.getLineaInicial(), this.ts, this.ambitoActual); this.posPatron++;}
break;
case 45:
//#line 112 "gramatica.y"
{
this.cantPatronDer = 1; posPatron = gc.updateAndCheckSize(this.posPatron, gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), lex.getLineaInicial(), this.ts, this.ambitoActual); this.posPatron++;}
break;
case 46:
//#line 116 "gramatica.y"
{estructurasSintacticas("Se definió una sentencia de control sin else, en la linea: " + lex.getLineaInicial());}
break;
case 48:
//#line 121 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta END_IF con ELSE", lex.getLineaInicial());}
break;
case 49:
//#line 122 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta END_IF", lex.getLineaInicial());}
break;
case 50:
//#line 123 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control del then", lex.getLineaInicial());}
break;
case 51:
//#line 124 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control del ELSE", lex.getLineaInicial());}
break;
case 52:
//#line 125 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control tanto en THEN como ELSE", lex.getLineaInicial());}
break;
case 53:
//#line 130 "gramatica.y"
{
			gc.actualizarBF(gc.getCantTercetos()); 
			gc.pop();
			gc.addTerceto("Label"+gc.getCantTercetos(), "FIN_IF_SOLO", "-");
			}
break;
case 54:
//#line 137 "gramatica.y"
{
			gc.addTerceto("inicioif", "-", "-");
			gc.addTerceto("BF", val_peek(0).sval, ""); 
			gc.push(gc.getPosActual());
		}
break;
case 55:
//#line 143 "gramatica.y"
{ 
			gc.addTerceto("BI", "", "-"); 
			int posSig = gc.getCantTercetos();
			gc.actualizarBF(posSig); 
			gc.pop(); 
			gc.push(gc.getPosActual());
			this.gc.addTerceto("Label" + posSig, "else", "-");
		}
break;
case 56:
//#line 152 "gramatica.y"
{
			int posSig = gc.getCantTercetos();
			gc.actualizarBI(posSig);
			this.gc.addTerceto("Label" + posSig, "endif", "-");
			gc.pop();
			estructurasSintacticas("Se definió una sentencia de control con else, en la linea: " + lex.getLineaInicial());
		}
break;
case 57:
//#line 162 "gramatica.y"
{yyval.sval = ">=";}
break;
case 58:
//#line 163 "gramatica.y"
{yyval.sval = "<=";}
break;
case 59:
//#line 164 "gramatica.y"
{yyval.sval = "!=";}
break;
case 60:
//#line 165 "gramatica.y"
{yyval.sval = "=";}
break;
case 61:
//#line 166 "gramatica.y"
{yyval.sval = "<";}
break;
case 62:
//#line 167 "gramatica.y"
{yyval.sval = ">";}
break;
case 65:
//#line 173 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
break;
case 66:
//#line 174 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
break;
case 67:
//#line 175 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 70:
//#line 181 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
break;
case 71:
//#line 182 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
break;
case 72:
//#line 183 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 75:
//#line 189 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 76:
//#line 193 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 77:
//#line 195 "gramatica.y"
{tipoVar = val_peek(0).sval;}
break;
case 78:
//#line 195 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 79:
//#line 198 "gramatica.y"
{checkRedeclaracion(val_peek(0).sval);}
break;
case 80:
//#line 199 "gramatica.y"
{checkRedeclaracion(val_peek(0).sval);}
break;
case 81:
//#line 200 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta coma en la lista de variables, puede haber parado la compilacion en este punto", lex.getLineaInicial());}
break;
case 82:
//#line 204 "gramatica.y"
{yyval.sval = "double";}
break;
case 83:
//#line 205 "gramatica.y"
{yyval.sval = "ulongint";}
break;
case 84:
//#line 208 "gramatica.y"
{estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());
						yyval.sval = gc.checkTipoAsignacion(val_peek(2).sval, lex.getLineaInicial(), val_peek(0).sval, this.ts,ambitoActual);}
break;
case 85:
//#line 211 "gramatica.y"
{  estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());
					yyval.sval = gc.checkTipoAsignacion(val_peek(2).sval, lex.getLineaInicial(), val_peek(0).sval, this.ts,ambitoActual);
		}
break;
case 86:
//#line 216 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "+",ambitoActual);}
break;
case 87:
//#line 218 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "-",ambitoActual);}
break;
case 88:
//#line 220 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 89:
//#line 223 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 90:
//#line 224 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");}
break;
case 91:
//#line 226 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
break;
case 92:
//#line 228 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");}
break;
case 93:
//#line 230 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
break;
case 94:
//#line 235 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "*",ambitoActual);}
break;
case 95:
//#line 236 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "/",ambitoActual);}
break;
case 96:
//#line 237 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 97:
//#line 239 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 98:
//#line 240 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 99:
//#line 241 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 100:
//#line 243 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 101:
//#line 245 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
break;
case 102:
//#line 247 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
break;
case 103:
//#line 251 "gramatica.y"
{  gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts,ambitoActual);}
break;
case 107:
//#line 257 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 108:
//#line 258 "gramatica.y"
{	if (ts.esUlongInt(val_peek(0).sval)){
					ErrorHandler.addErrorSintactico("se utilizo un Ulongint negativo, son solo positivos", lex.getLineaInicial());
				}
				else {
					ts.convertirNegativo(val_peek(0).sval);
					yyval.sval = "-" + val_peek(0).sval;
				}
				
			}
break;
case 109:
//#line 269 "gramatica.y"
{String tipo = gc.getTipoAccesoTripla(val_peek(1).sval, this.ts);
						   if(tipo != "ulongint"){ErrorHandler.addErrorSintactico("Se intento acceder con un tipo distinto a entero a la tripla.", lex.getLineaInicial());}

							String idTripla=gc.checkDeclaracion(val_peek(3).sval,lex.getLineaInicial(),this.ts,ambitoActual);
						   	if (idTripla != null) {		
						   	 	tipo = this.ts.getAtributo(idTripla, AccionSemantica.TIPO_BASICO); 
						   	 }else{
								ErrorHandler.addErrorSemantico( "La tripla " + idTripla + " nunca fue declarada.", lex.getLineaInicial()) ; 
								tipo = "error";
						  	  }
								yyval.sval = gc.addTerceto("ASIGTRIPLA", idTripla, val_peek(1).sval, tipo);
							}
break;
case 110:
//#line 283 "gramatica.y"
{String tipo = gc.getTipoAccesoTripla(val_peek(1).sval, this.ts);
						   if(tipo != "ulongint"){ErrorHandler.addErrorSintactico("Se intento acceder con un tipo distinto a entero a la tripla.", lex.getLineaInicial());}											String idTripla=gc.checkDeclaracion(val_peek(3).sval,lex.getLineaInicial(),this.ts,ambitoActual);
						   	if (idTripla != null) {		
						   	 	tipo = this.ts.getAtributo(idTripla, AccionSemantica.TIPO_BASICO); 
						   	 }else{
								ErrorHandler.addErrorSemantico( "La tripla " + idTripla + " nunca fue declarada.", lex.getLineaInicial()) ; 
								tipo = "error";
						  	  }
							 yyval.sval = gc.addTerceto("ACCESOTRIPLE", idTripla, val_peek(1).sval, tipo);
							}
break;
case 111:
//#line 295 "gramatica.y"
{ if (esEmbebido(val_peek(1).sval)){ErrorHandler.addErrorSemantico("No se puede declarar una funcion con un ID con tipos embebidos.", lex.getLineaInicial());}
					else {
					this.checkRedFuncion(val_peek(1).sval, val_peek(3).sval);
					this.ambitoActual += ":" + val_peek(1).sval;
					}
					}
break;
case 112:
//#line 300 "gramatica.y"
{ 
								this.cantRetornos.add(0); 
								this.gc_funciones.push(this.ts.getGCFuncion(this.ambitoActual)); 
								this.gc = this.gc_funciones.peek(); 
								this.tags.add(new ControlTagAmbito());
								}
break;
case 113:
//#line 305 "gramatica.y"
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
case 114:
//#line 315 "gramatica.y"
{ this.cantRetornos.add(0);}
break;
case 115:
//#line 315 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta nombre de la funcion declarada", lex.getLineaInicial());
													this.checkRet("");
													}
break;
case 116:
//#line 318 "gramatica.y"
{ this.cantRetornos.add(0);}
break;
case 117:
//#line 318 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el parametro en la declaracion de la funcion", lex.getLineaInicial());
							    this.checkRet(val_peek(4).sval);
							    if (esEmbebido(val_peek(4).sval)){ErrorHandler.addErrorSemantico("No se puede declarar una funcion con un ID con tipos embebidos.", lex.getLineaInicial());}
							   }
break;
case 119:
//#line 325 "gramatica.y"
{ErrorHandler.addErrorSintactico("No se permite utilizar un tipo definido por el usuario como retorno", lex.getLineaInicial());}
break;
case 120:
//#line 328 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Se declaró más de un parametro", lex.getLineaInicial());}
break;
case 122:
//#line 333 "gramatica.y"
{ 
			this.ts.addClave(this.ambitoActual + ":" + val_peek(0).sval);
			String id_param = gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual);
			this.ts.addAtributo(id_param,AccionSemantica.TIPO, val_peek(1).sval); this.ts.addAtributo(id_param,AccionSemantica.USO,"nombre parametro"); this.ts.addAtributo(gc.checkDeclaracion(this.ambitoActual, lex.getLineaInicial(),this.ts, this.ambitoActual), AccionSemantica.PARAMETRO, id_param); 
			this.ts.addAtributo(id_param, AccionSemantica.TIPO, val_peek(1).sval); estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " +			lex.getLineaInicial());}
break;
case 123:
//#line 338 "gramatica.y"
{ ErrorHandler.addErrorSintactico("No se permite una tripla como parametro", lex.getLineaInicial());}
break;
case 124:
//#line 340 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el nombre del parametro", lex.getLineaInicial());}
break;
case 125:
//#line 341 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el tipo o se intento utilizar una tripla sin nombre.", lex.getLineaInicial());}
break;
case 129:
//#line 351 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 131:
//#line 357 "gramatica.y"
{ this.cantRetornos.set(this.cantRetornos.size()-1, this.cantRetornos.get(this.cantRetornos.size()-1) + 1); 
					String expresion = gc.checkDeclaracion(val_peek(1).sval, lex.getLineaInicial(), this.ts, this.ambitoActual);
					gc.checkTipoRetorno(expresion, ambitoActual,this.ts,lex.getLineaInicial());
					yyval.sval = gc.addTerceto("RET", expresion, "");		
		}
break;
case 132:
//#line 364 "gramatica.y"
{idFuncion = gc.checkDeclaracion(val_peek(1).sval,lex.getLineaInicial(),this.ts,ambitoActual);}
break;
case 133:
//#line 364 "gramatica.y"
{ 
							estructurasSintacticas("Se invocó a la función: " + val_peek(4).sval + " en la linea: " + lex.getLineaInicial());
							String tipo = "";	
							String idFunc = gc.checkDeclaracion(val_peek(4).sval,lex.getLineaInicial(),this.ts, this.ambitoActual);
							if(idFunc != null){
								tipo = this.ts.getAtributo(idFunc, AccionSemantica.TIPO);
							}
							else {
								ErrorHandler.addErrorSemantico("La funcion invocada " + val_peek(4).sval + " no existe.", lex.getLineaInicial());
								tipo = "error";
							}
							yyval.sval = gc.addTerceto("INVOC_FUN",idFunc, gc.checkDeclaracion(val_peek(1).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), tipo);/*porque $4? :c*/
		}
break;
case 134:
//#line 378 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de parámetros en la invocación a la función", lex.getLineaInicial());}
break;
case 135:
//#line 382 "gramatica.y"
{ yyval.sval = val_peek(0).sval;ErrorHandler.addErrorSintactico("Se utilizó más de un parámetro para invocar a la función", lex.getLineaInicial());}
break;
case 136:
//#line 383 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 137:
//#line 386 "gramatica.y"
{yyval.sval = val_peek(0).sval; gc.checkParamReal(val_peek(0).sval, lex.getLineaInicial(), this.ts, ambitoActual,idFuncion);}
break;
case 138:
//#line 387 "gramatica.y"
{
			String tipoId = gc.checkConversion(val_peek(0).sval,lex.getLineaInicial(),this.ts,ambitoActual);	
			if (!tipoId.equals("")){
				if(!tipoId.equals(val_peek(1).sval)){yyval.sval = gc.addTerceto("TO".concat(val_peek(1).sval), gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");} else { ErrorHandler.addWarningSemantico("Se intenta realizar una conversion innecesaria. No se realizará.", lex.getLineaInicial()); yyval.sval = val_peek(0).sval; gc.checkParamReal(val_peek(0).sval, lex.getLineaInicial(), this.ts, ambitoActual,idFuncion);}
				if(!this.ts.getAtributo(this.ts.getAtributo(idFuncion, AccionSemantica.PARAMETRO), AccionSemantica.TIPO).equals(val_peek(1).sval)){ ErrorHandler.addErrorSemantico("El tipo del parametro real no coincide con el tipo del parametro formal.", lex.getLineaInicial());}}
			}
break;
case 139:
//#line 395 "gramatica.y"
{yyval.sval = gc.addTerceto("OUTF", val_peek(1).sval, "");}
break;
case 140:
//#line 397 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el mensaje del OUTF", lex.getLineaInicial());}
break;
case 141:
//#line 398 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Parámetro invalido del OUTF", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 142:
//#line 402 "gramatica.y"
{ yyval.sval = gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual);}
break;
case 143:
//#line 403 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 144:
//#line 406 "gramatica.y"
{	estructurasSintacticas("Se declaró un bucle FOR en la linea: " + lex.getLineaInicial()); 
				String var = this.varFors.get(this.varFors.size()-1);
				if(!this.ts.getAtributo(val_peek(2).sval, AccionSemantica.TIPO).equals(AccionSemantica.ULONGINT)){
					ErrorHandler.addErrorSemantico("La constante de avance no es de tipo entero.", lex.getLineaInicial()); 
					gc.addTerceto("+", gc.checkDeclaracion(var, lex.getLineaInicial(), this.ts, this.ambitoActual), String.valueOf(val_peek(3).ival * Double.parseDouble(val_peek(2).sval)));
				} else {
					var = gc.checkDeclaracion(var, lex.getLineaInicial(), this.ts, this.ambitoActual);
					gc.addTerceto(":=", var, gc.addTerceto("+", var, String.valueOf(val_peek(3).ival * Integer.parseInt(val_peek(2).sval)), "ulongint"), "ulongint");
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
//#line 425 "gramatica.y"
{ ErrorHandler.addErrorSintactico("No se puede utilizar una constante negativa, en su lugar se debe utilizar el avance descendiente DOWN.", lex.getLineaInicial());}
break;
case 146:
//#line 426 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 147:
//#line 427 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma entre asignacion y condicion", lex.getLineaInicial());}
break;
case 148:
//#line 428 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Faltan todos los punto y coma del for", lex.getLineaInicial());}
break;
case 149:
//#line 429 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta UP/DOWN", lex.getLineaInicial());}
break;
case 150:
//#line 430 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN", lex.getLineaInicial());}
break;
case 151:
//#line 431 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 152:
//#line 432 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 153:
//#line 433 "gramatica.y"
{ { ErrorHandler.addErrorSintactico("Falta UP/DOWN, su valor, y punto y coma entre condicion y avance", lex.getLineaInicial());}}
break;
case 154:
//#line 437 "gramatica.y"
{ yyval.sval = val_peek(0).sval;
				gc.invertirCondicion(val_peek(0).sval);
				gc.addTerceto("BF", val_peek(0).sval, "");
				gc.push(gc.getPosActual());
			}
break;
case 155:
//#line 444 "gramatica.y"
{String varFor = gc.checkDeclaracion(val_peek(2).sval,lex.getLineaInicial(),this.ts,ambitoActual);
				if (varFor != null){
					if(!this.ts.getAtributo(varFor, AccionSemantica.TIPO).equals(AccionSemantica.ULONGINT)){ErrorHandler.addErrorSemantico("La variable " + val_peek(2).sval + " no es de tipo entero.", lex.getLineaInicial());}
					gc.addTerceto(":=", varFor, val_peek(0).sval, this.ts.getAtributo(val_peek(0).sval, AccionSemantica.TIPO));
				}
				else{
					gc.addTerceto(":=", val_peek(2).sval, val_peek(0).sval );
				}
				if(!this.ts.getAtributo(val_peek(0).sval, AccionSemantica.TIPO).equals(AccionSemantica.ULONGINT)){ErrorHandler.addErrorSemantico("La constante " + val_peek(0).sval + " no es de tipo entero.", lex.getLineaInicial());}
				this.varFors.add(val_peek(2).sval);
				this.cantFors++;
				this.gc.addTerceto("Label" + this.gc.getCantTercetos(), "FOR"+this.cantFors, "-");
				}
break;
case 156:
//#line 459 "gramatica.y"
{yyval.ival = 1;}
break;
case 157:
//#line 460 "gramatica.y"
{yyval.ival = -1;}
break;
case 158:
//#line 463 "gramatica.y"
{ yyval.sval = gc.addTerceto("GOTO", ambitoActual + ":" + val_peek(0).sval,"");
			     this.ts.addAtributo(val_peek(0).sval,AccionSemantica.USO,"nombre etiqueta");
			     this.tags.get(tags.size()-1).huboGoto(this.ambitoActual+":"+val_peek(0).sval);
			}
break;
case 159:
//#line 468 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta la etiqueta en el GOTO, en caso de faltar también el punto y coma es posible que no compile el resto del programa o lo haga mal.", lex.getLineaInicial());
				lex.setErrorHandlerToken(";");}
break;
case 160:
//#line 474 "gramatica.y"
{this.ts.addClave(val_peek(0).sval); this.ts.addAtributo(val_peek(0).sval,AccionSemantica.USO,"nombre de tipo tripla"); estructurasSintacticas("Se declaró un tipo TRIPLE con el ID: " + val_peek(0).sval + " en la linea:" + lex.getLineaInicial()); this.ts.addAtributo(val_peek(0).sval, "tipotripla", val_peek(2).sval); this.ts.addAtributo(val_peek(0).sval, "tipo", val_peek(2).sval);}
break;
case 161:
//#line 476 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta < en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 162:
//#line 477 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta > en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 163:
//#line 478 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta > y < en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 164:
//#line 479 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta la palabra clave TRIPLE", lex.getLineaInicial());}
break;
case 165:
//#line 480 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el ID de la tripla definida.", lex.getLineaInicial());}
break;
//#line 1759 "Parser.java"
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
