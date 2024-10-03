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
   12,   12,   12,   12,   12,   12,   12,   19,   19,   19,
   19,   19,   19,   22,   22,   22,   22,   22,   24,   24,
   24,   24,   24,   23,   23,   23,    7,    7,   26,   26,
   26,   25,   25,   10,   10,   28,   28,   18,   18,   18,
   18,   18,   18,   18,   18,   29,   29,   29,   29,   29,
   29,   29,   29,   29,   30,   30,   30,   30,   31,   31,
   27,   35,    8,    8,    8,   32,   32,   33,   33,   36,
   36,   36,   36,   37,   34,   38,   38,   38,   39,   39,
   40,   11,   11,   41,   41,   42,   42,   13,   13,   13,
   43,   43,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   44,   44,   15,   15,    9,    9,    9,    9,    9,
    9,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    3,    4,    3,    3,    3,    2,
    3,    1,    2,    2,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    3,    1,    2,    2,
    3,    7,    5,    6,    6,    7,    3,    3,    3,    1,
    5,    7,    6,    4,    4,    6,    5,    1,    1,    1,
    1,    1,    1,    4,    2,    3,    2,    3,    4,    2,
    3,    2,    3,    3,    1,    2,    2,    2,    3,    1,
    3,    1,    1,    3,    3,    1,    1,    3,    3,    1,
    2,    4,    4,    4,    4,    3,    3,    1,    2,    2,
    4,    4,    4,    4,    1,    1,    1,    1,    1,    2,
    4,    0,    8,    6,    6,    1,    1,    3,    1,    2,
    2,    1,    1,    0,    5,    3,    1,    2,    1,    1,
    4,    4,    3,    3,    1,    2,    1,    4,    3,    4,
    1,    1,   12,   11,   11,   10,   11,   11,   10,   10,
   10,    1,    1,    2,    3,    6,    5,    5,    4,    5,
    7,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    0,    0,    0,   73,   72,
    0,    0,    0,    0,   19,    1,    0,   12,   15,   16,
   17,   18,   20,   21,   22,   23,   24,   25,   26,    0,
    0,    0,    0,    0,   13,   70,    0,    0,    0,    0,
    0,  144,    0,    0,   99,    0,    0,    0,    0,    0,
   97,    0,    0,    0,   98,    0,   88,   96,    0,    0,
    0,    0,    0,    0,   14,    0,    0,    0,    0,    4,
    0,   77,    0,   75,  123,    0,  127,    0,  125,    0,
    0,    0,  145,    0,    0,    0,    0,    0,    0,  100,
   89,   90,    0,   30,    0,   48,   49,   50,   51,   52,
   53,    0,    0,    0,    0,    0,    0,    7,    0,  132,
  129,  131,    0,    0,    0,    0,    9,   11,   74,    0,
    0,    3,    0,    0,  122,    0,  101,   71,   69,    0,
   27,    0,    0,    0,    0,    0,    0,   45,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   86,    0,   87,
    6,  130,  128,    0,  149,    0,    0,    0,    0,    0,
    0,  109,  124,    0,    0,    0,    0,    0,    0,   47,
   57,    0,   65,    0,   55,    0,   41,   83,   82,   85,
   84,   93,   92,   94,   91,  148,    0,  147,  150,  114,
    0,  111,  110,  114,    0,    0,    0,    0,    0,    0,
    0,   33,   56,   58,    0,   66,   46,    0,    0,  146,
  105,    0,  102,  104,  108,    0,  142,  143,    0,    0,
    0,    0,   34,   35,   54,   64,   42,  151,    0,  114,
    0,    0,    0,    0,    0,   36,   32,    0,  120,    0,
  117,  119,  103,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  118,    0,    0,  139,    0,  141,    0,
    0,    0,  140,    0,  136,    0,  115,  116,   62,    0,
    0,   60,  137,    0,  138,  134,  135,  121,   61,   63,
    0,  133,   59,
};
final static short yydgoto[] = {                          3,
   16,    4,   17,   65,   19,   20,   21,   22,   23,   24,
   25,   26,   27,   28,   29,   52,   72,   73,  104,   87,
   88,  140,  174,  257,   30,   40,   31,   77,   56,   57,
   58,   32,  161,  211,  230,  162,  212,  240,  241,  242,
   78,   79,  113,  220,
};
final static short yysindex[] = {                       -92,
  673,  695,    0,    0,  -39,  -13, -149,    3,    0,    0,
  375,  695,   21,  -42,    0,    0,  400,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -231,
 -172, -197,  416, -173,    0,    0,  512,  258,  438,  -31,
   57,    0, -134,   29,    0,  512,   -3, -102,   37,   37,
    0, -103,  156,  629,    0,  -25,    0,    0,  431, -104,
  -35,  -51,  -73,  725,    0,  -31,  512,  -17,  740,    0,
  438,    0,  629,    0,    0,  438,    0,  -20,    0,  -12,
  -49,  -44,    0,  -32,  178,  629,  118,  217,  -25,    0,
    0,    0,  537,    0,  438,    0,    0,    0,    0,    0,
    0,  593,  602,  438,    2,  129,  755,    0,  221,    0,
    0,    0,  226,  -73,    8,  216,    0,    0,    0,  245,
  -69,    0,  117,  117,    0,  326,    0,    0,    0,   46,
    0,  644,  533,  438,   -8,   39,  462,    0,  253,  -89,
  117,   49,  -25,   94,  -25,  117,   95,    0,  108,    0,
    0,    0,    0,   16,    0,   63,   77,  -22,   80,   82,
  265,    0,    0,  506,  301,  564,  438,  306,  117,    0,
    0,   85,    0,  478,    0,  570,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -50,    0,    0,    0,
  267,    0,    0,    0,  -69,  375,   67,  438,  438,  323,
  329,    0,    0,    0,  454,    0,    0,   99,  324,    0,
    0,  109,    0,    0,    0,  -55,    0,    0,  -15,  126,
  352,  357,    0,    0,    0,    0,    0,    0,  770,    0,
  364,  -38,  -16,  152,  366,    0,    0,  372,    0,  385,
    0,    0,    0,  785,  373,  785,   -7,  380,  785,  382,
  785,  512,  710,    0,  505,  365,    0,  785,    0,  384,
  785,  785,    0,  785,    0,  391,    0,    0,    0,  142,
  529,    0,    0,  785,    0,    0,    0,    0,    0,    0,
  793,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,  148,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  159,
    0,    0,    0,  446,    0,    0,    0,    0,    0,  293,
    0,    0,    0,   12,    0,    0,    0,    0,    0,    0,
    0,    0,  233,    0,    0,   59,    0,    0,    0,  446,
    0,    0,    0,  443,    0,  322,    0,    0,  449,    0,
    0,    0,  266,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  238,  402,    0,    0,   86,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  451,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  402,  317,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  345,
  197,    0,  132,    0,  171,  243,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  331,  335,
    0,    0,    0,    0,    0,    0,    0,    0,    4,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  360,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  149,   -1,    0,  650,    0,    0,    0,    0,
   -9,    0,    0,    0,    0,  -30,    5,  749,  -72,  -87,
    0,  277,  201,  616,  715,  427,   38,   61,   36,  -14,
    0,    0,  300, -180,    0,  268,    0,    0,  -90,    0,
    0,  343,    0,  -34,
};
final static int YYTABLESIZE=1069;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   34,   51,  246,  232,   71,  111,   49,   47,  114,   48,
   60,   50,   82,  214,  133,   53,  105,   63,  190,   35,
  125,  106,  121,  126,  249,   36,   38,   51,   51,   51,
  102,   38,  103,  261,   91,   92,   51,   51,   49,   51,
   51,   48,   43,   50,   38,  168,   48,   39,   55,  243,
   85,   51,   95,   95,   95,   95,   95,   51,   95,  166,
   61,   51,  118,   38,   38,   38,   51,  118,   38,  156,
   95,   95,   95,   95,   55,   55,   55,  187,  200,  201,
   68,   48,   89,   55,   55,   51,   55,   55,   67,  179,
  148,  150,   51,   51,   51,   51,   51,   74,   55,   80,
   70,   80,   80,   80,   55,  118,   41,  178,   55,   39,
  221,  222,  127,   55,   39,   83,   51,   80,   80,   80,
   80,  112,   84,   51,   51,  219,   81,  119,   81,   81,
   81,   42,   55,  197,  181,  183,   95,  143,  145,   55,
   55,   55,   55,   55,   81,   81,   81,   81,  185,  254,
   33,   39,  180,  182,   51,   90,   51,   51,  132,  102,
   59,  103,  268,   55,    1,  216,  184,   93,   53,  108,
   55,   55,   78,   48,   78,   78,   78,  100,   99,  101,
    2,  233,  176,   80,  234,  177,   51,  159,   51,   51,
   78,   78,   78,   78,    9,   10,   94,  247,    9,   10,
   53,   55,  231,   55,   55,  209,  210,  128,  217,  218,
   81,   79,  129,   79,   79,   79,    9,   10,  131,  245,
  109,   44,   45,   62,   81,  217,  218,  239,  130,   79,
   79,   79,   79,   55,  159,   55,   55,   37,  239,  120,
   37,  248,   51,   36,  110,    9,   10,   37,  217,  218,
  260,  239,   37,   44,   45,   37,   78,  147,   44,   45,
  134,  152,   38,   38,  155,   38,  153,   95,   95,   95,
   95,   95,  186,   95,   95,   95,   95,  157,   95,   95,
   95,   95,   95,   31,  158,   95,   31,   95,   95,   55,
   95,   28,   95,   44,   45,   79,   29,   71,   75,   49,
   47,   31,   48,  164,   50,  194,   76,  213,  195,   76,
  195,  175,  266,  170,   80,   80,   80,   80,   80,  188,
   80,   80,   80,   80,   76,   80,   80,   80,   80,   80,
  217,  218,   80,  189,   80,   80,  192,   80,  193,   80,
  198,   81,   81,   81,   81,   81,  202,   81,   81,   81,
   81,   68,   81,   81,   81,   81,   81,  126,  203,   81,
  126,   81,   81,  223,   81,   71,   81,   49,   47,  224,
   48,  113,   50,  227,  113,  112,   96,   97,  112,   98,
   67,  229,  228,  235,  149,   44,   45,   78,   78,   78,
   78,   78,  236,   78,   78,   78,   78,  237,   78,   78,
   78,   78,   78,   44,  244,   78,  251,   78,   78,  250,
   78,  252,   78,  258,   46,  279,   49,   47,   43,   48,
  262,   50,  264,  272,  274,  107,   79,   79,   79,   79,
   79,  278,   79,   79,   79,   79,  106,   79,   79,   79,
   79,   79,   10,  253,   79,   40,   79,   79,    5,   79,
    8,   79,  208,   37,   37,  271,   66,  191,   64,   37,
   37,   37,  215,   37,   37,   37,   37,   37,  163,    0,
   37,    0,   37,   37,   69,   37,    0,   37,    0,   49,
   47,    0,   48,    0,   50,    0,    0,    0,    0,  107,
   28,    0,    0,    0,    0,   29,   28,   28,    0,   31,
   31,   29,   29,   28,   12,   31,   31,   31,   29,   31,
   31,   31,   31,   31,   44,   45,   31,    0,   31,   31,
  172,   31,   76,   31,    0,    9,   10,    0,   76,    0,
    0,    0,   76,   76,   76,   76,  205,    0,    0,   76,
    0,   76,   76,    0,   76,   46,   76,   49,   47,   68,
   48,   71,   50,   49,   47,   68,   48,    0,   50,   68,
   68,   68,   68,  270,  196,    0,   68,    0,   68,   68,
    0,   68,  167,   68,   49,   47,    0,   48,   67,   50,
    0,    0,   44,   45,   67,    0,    0,  281,   67,   67,
   67,   67,    0,    9,   10,   67,    0,   67,   67,    0,
   67,   44,   67,  199,    0,   49,   47,   44,   48,    0,
   50,   44,   44,   44,   44,    0,   43,    0,   44,    0,
   44,   44,   43,   44,    0,   44,   43,   43,   43,   43,
    0,   44,   45,   43,   49,   43,   43,   48,   43,   50,
   43,    6,    0,   49,    0,    0,   48,    7,   50,    0,
    0,    8,    9,   10,   11,    0,    6,    0,    0,    0,
   13,   14,    7,  238,    0,   15,    8,    9,   10,   11,
    0,  102,    6,  103,    0,   13,   14,    0,    7,    0,
   15,    0,    8,    9,   10,   11,    0,    6,  100,   99,
  101,   13,   14,    7,   44,   45,   15,    8,    9,   10,
   11,    0,   12,  100,   99,  101,   13,   14,   12,    0,
  135,   15,   12,   12,   12,   12,    7,    0,  135,    0,
    8,   12,   12,   11,    7,    0,   12,  225,    8,   13,
    0,   11,    0,    0,  135,  171,    0,   13,    0,    0,
    7,    0,  139,    0,    8,    0,    0,   11,    0,    0,
    0,  204,   76,   13,    0,    0,    0,    0,    0,   54,
    0,  135,   44,   45,    0,    0,    0,    7,   44,   45,
    0,    8,    0,    0,   11,    0,  115,  116,  269,    0,
   13,    0,    0,    0,    0,  135,  173,   80,    0,   44,
   45,    7,    0,  135,   86,    8,    0,    0,   11,    7,
    0,    0,  280,    8,   13,    0,   11,    0,  136,  137,
    0,  138,   13,    0,    0,    0,    0,    0,    0,  123,
   44,   45,    0,  206,  124,  139,  135,    0,  154,    0,
    0,    0,    7,    0,    0,  160,    8,    0,    0,   11,
   76,    0,  137,  141,  207,   13,    0,    0,  142,   44,
   45,    0,  146,    0,  226,    0,    0,  144,   44,   45,
    0,  259,    0,    0,  263,    0,  265,    0,    0,    0,
    0,    0,  160,  273,    0,    0,  275,  276,    0,  277,
    0,  123,  169,    0,   95,    0,    0,   96,   97,  282,
   98,    0,    0,  256,    0,  256,    0,    0,  256,  165,
  256,    0,   96,   97,  173,   98,    0,  256,    0,  160,
  256,  256,   54,  256,  123,  123,    0,    0,    0,    0,
  206,    0,    0,  256,    0,    0,    0,    0,    5,    6,
  226,    0,    0,    0,    0,    7,    0,    0,    0,    8,
    9,   10,   11,    0,   54,   12,  123,  123,   13,   14,
    5,    6,    0,   15,    0,    0,    0,    7,    0,    0,
    0,    8,    9,   10,   11,    0,    6,    0,    0,    0,
   13,   14,    7,    0,    0,   15,    8,    9,   10,   11,
    0,    6,    0,  267,    0,   13,   14,    7,  238,    0,
   15,    8,    9,   10,   11,    0,    6,    0,  117,    0,
   13,   14,    7,    0,    0,   15,    8,    9,   10,   11,
    0,    6,    0,  122,    0,   13,   14,    7,    0,    0,
   15,    8,    9,   10,   11,    0,    6,    0,  151,    0,
   13,   14,    7,    0,    0,   15,    8,    9,   10,   11,
    0,  135,    0,    0,    0,   13,   14,    7,  238,  135,
   15,    8,    0,    0,   11,    7,    0,  255,    0,    8,
   13,    0,   11,    0,    0,    0,  283,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          1,
    2,   11,   41,   59,   40,   41,   42,   43,   60,   45,
   12,   47,   44,  194,   87,   11,   42,   60,   41,   59,
   41,   47,   40,   44,   41,  257,   40,   37,   38,   39,
   43,   40,   45,   41,   49,   50,   46,   47,   42,   49,
   50,   45,   40,   47,   41,  133,   45,   44,   11,  230,
   46,   61,   41,   42,   43,   44,   45,   67,   47,  132,
   40,   71,   64,   60,   61,   62,   76,   69,   40,   62,
   59,   60,   61,   62,   37,   38,   39,   62,  166,  167,
  278,   45,   47,   46,   47,   95,   49,   50,  261,   41,
  105,  106,  102,  103,  104,  105,  106,   37,   61,   41,
  274,   43,   44,   45,   67,  107,  256,   59,   71,  123,
  198,  199,  125,   76,  123,   59,  126,   59,   60,   61,
   62,   61,  257,  133,  134,   59,   41,   67,   43,   44,
   45,  281,   95,  164,   41,   41,  125,  102,  103,  102,
  103,  104,  105,  106,   59,   60,   61,   62,   41,  240,
    2,  123,   59,   59,  164,  258,  166,  167,   41,   43,
   12,   45,  253,  126,  257,  196,   59,  271,  164,  274,
  133,  134,   41,   45,   43,   44,   45,   60,   61,   62,
  273,  216,  272,  125,  219,  275,  196,  257,  198,  199,
   59,   60,   61,   62,  268,  269,   41,  232,  268,  269,
  196,  164,  258,  166,  167,  256,  257,  257,  264,  265,
  125,   41,  257,   43,   44,   45,  268,  269,   41,  258,
  256,  257,  258,  266,  256,  264,  265,  229,  261,   59,
   60,   61,   62,  196,  257,  198,  199,   41,  240,  257,
   44,  258,  252,  257,  280,  268,  269,  261,  264,  265,
  258,  253,  261,  257,  258,   59,  125,  256,  257,  258,
   44,   41,  259,  260,  257,  262,   41,  256,  257,  258,
  259,  260,  257,  262,  263,  264,  265,   62,  267,  268,
  269,  270,  271,   41,   40,  274,   44,  276,  277,  252,
  279,   59,  281,  257,  258,  125,   59,   40,   41,   42,
   43,   59,   45,  258,   47,   41,   41,   41,   44,   44,
   44,   59,  252,  275,  256,  257,  258,  259,  260,  257,
  262,  263,  264,  265,   59,  267,  268,  269,  270,  271,
  264,  265,  274,  257,  276,  277,  257,  279,  257,  281,
   40,  256,  257,  258,  259,  260,   41,  262,  263,  264,
  265,   59,  267,  268,  269,  270,  271,   41,  274,  274,
   44,  276,  277,   41,  279,   40,  281,   42,   43,   41,
   45,   41,   47,  275,   44,   41,  259,  260,   44,  262,
   59,  273,   59,  258,  256,  257,  258,  256,  257,  258,
  259,  260,   41,  262,  263,  264,  265,   41,  267,  268,
  269,  270,  271,   59,   41,  274,   41,  276,  277,  258,
  279,   40,  281,   41,   40,  274,   42,   43,   59,   45,
   41,   47,   41,   59,   41,  278,  256,  257,  258,  259,
  260,   41,  262,  263,  264,  265,  278,  267,  268,  269,
  270,  271,    0,   59,  274,   44,  276,  277,    0,  279,
    0,  281,  176,  257,  258,  255,   30,  158,   59,  263,
  264,  265,  195,  267,  268,  269,  270,  271,  126,   -1,
  274,   -1,  276,  277,   59,  279,   -1,  281,   -1,   42,
   43,   -1,   45,   -1,   47,   -1,   -1,   -1,   -1,   59,
  258,   -1,   -1,   -1,   -1,  258,  264,  265,   -1,  257,
  258,  264,  265,  271,   59,  263,  264,  265,  271,  267,
  268,  269,  270,  271,  257,  258,  274,   -1,  276,  277,
   59,  279,  257,  281,   -1,  268,  269,   -1,  263,   -1,
   -1,   -1,  267,  268,  269,  270,   59,   -1,   -1,  274,
   -1,  276,  277,   -1,  279,   40,  281,   42,   43,  257,
   45,   40,   47,   42,   43,  263,   45,   -1,   47,  267,
  268,  269,  270,   59,   59,   -1,  274,   -1,  276,  277,
   -1,  279,   40,  281,   42,   43,   -1,   45,  257,   47,
   -1,   -1,  257,  258,  263,   -1,   -1,   59,  267,  268,
  269,  270,   -1,  268,  269,  274,   -1,  276,  277,   -1,
  279,  257,  281,   40,   -1,   42,   43,  263,   45,   -1,
   47,  267,  268,  269,  270,   -1,  257,   -1,  274,   -1,
  276,  277,  263,  279,   -1,  281,  267,  268,  269,  270,
   -1,  257,  258,  274,   42,  276,  277,   45,  279,   47,
  281,  257,   -1,   42,   -1,   -1,   45,  263,   47,   -1,
   -1,  267,  268,  269,  270,   -1,  257,   -1,   -1,   -1,
  276,  277,  263,  279,   -1,  281,  267,  268,  269,  270,
   -1,   43,  257,   45,   -1,  276,  277,   -1,  263,   -1,
  281,   -1,  267,  268,  269,  270,   -1,  257,   60,   61,
   62,  276,  277,  263,  257,  258,  281,  267,  268,  269,
  270,   -1,  257,   60,   61,   62,  276,  277,  263,   -1,
  257,  281,  267,  268,  269,  270,  263,   -1,  257,   -1,
  267,  276,  277,  270,  263,   -1,  281,  274,  267,  276,
   -1,  270,   -1,   -1,  257,  274,   -1,  276,   -1,   -1,
  263,   -1,   93,   -1,  267,   -1,   -1,  270,   -1,   -1,
   -1,  274,   38,  276,   -1,   -1,   -1,   -1,   -1,   11,
   -1,  257,  257,  258,   -1,   -1,   -1,  263,  257,  258,
   -1,  267,   -1,   -1,  270,   -1,   62,   63,  274,   -1,
  276,   -1,   -1,   -1,   -1,  257,  137,   39,   -1,  257,
  258,  263,   -1,  257,   46,  267,   -1,   -1,  270,  263,
   -1,   -1,  274,  267,  276,   -1,  270,   -1,  272,  273,
   -1,  275,  276,   -1,   -1,   -1,   -1,   -1,   -1,   71,
  257,  258,   -1,  174,   76,  176,  257,   -1,  114,   -1,
   -1,   -1,  263,   -1,   -1,  121,  267,   -1,   -1,  270,
  126,   -1,  273,   95,  275,  276,   -1,   -1,  256,  257,
  258,   -1,  104,   -1,  205,   -1,   -1,  256,  257,  258,
   -1,  246,   -1,   -1,  249,   -1,  251,   -1,   -1,   -1,
   -1,   -1,  158,  258,   -1,   -1,  261,  262,   -1,  264,
   -1,  133,  134,   -1,  256,   -1,   -1,  259,  260,  274,
  262,   -1,   -1,  244,   -1,  246,   -1,   -1,  249,  256,
  251,   -1,  259,  260,  255,  262,   -1,  258,   -1,  195,
  261,  262,  164,  264,  166,  167,   -1,   -1,   -1,   -1,
  271,   -1,   -1,  274,   -1,   -1,   -1,   -1,  256,  257,
  281,   -1,   -1,   -1,   -1,  263,   -1,   -1,   -1,  267,
  268,  269,  270,   -1,  196,  273,  198,  199,  276,  277,
  256,  257,   -1,  281,   -1,   -1,   -1,  263,   -1,   -1,
   -1,  267,  268,  269,  270,   -1,  257,   -1,   -1,   -1,
  276,  277,  263,   -1,   -1,  281,  267,  268,  269,  270,
   -1,  257,   -1,  274,   -1,  276,  277,  263,  279,   -1,
  281,  267,  268,  269,  270,   -1,  257,   -1,  274,   -1,
  276,  277,  263,   -1,   -1,  281,  267,  268,  269,  270,
   -1,  257,   -1,  274,   -1,  276,  277,  263,   -1,   -1,
  281,  267,  268,  269,  270,   -1,  257,   -1,  274,   -1,
  276,  277,  263,   -1,   -1,  281,  267,  268,  269,  270,
   -1,  257,   -1,   -1,   -1,  276,  277,  263,  279,  257,
  281,  267,   -1,   -1,  270,  263,   -1,  273,   -1,  267,
  276,   -1,  270,   -1,   -1,   -1,  274,   -1,  276,
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
"seleccion : IF condicion THEN cuerpo_control END_IF",
"seleccion : IF condicion THEN cuerpo_control ELSE cuerpo_control END_IF",
"seleccion : IF condicion THEN cuerpo_control ELSE cuerpo_control",
"seleccion : IF condicion THEN cuerpo_control",
"seleccion : IF condicion THEN END_IF",
"seleccion : IF condicion THEN cuerpo_control ELSE END_IF",
"seleccion : IF condicion THEN ELSE END_IF",
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
"declaracion_var : ID lista_variables",
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
"declaracion_fun : tipo_fun FUN '(' lista_parametro ')' cuerpo_funcion_p",
"declaracion_fun : tipo_fun FUN ID '(' ')' cuerpo_funcion_p",
"tipo_fun : tipo",
"tipo_fun : ID",
"lista_parametro : lista_parametro ',' parametro",
"lista_parametro : parametro",
"parametro : tipo ID",
"parametro : ID ID",
"parametro : tipo",
"parametro : ID",
"$$2 :",
"cuerpo_funcion_p : $$2 BEGIN bloques_funcion ';' END",
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

//#line 327 "gramatica.y"
String nombreArchivo;
AnalizadorLexico lex;
TablaSimbolos ts;
String tipoVar;
ArrayList<Integer> cantRetornos;
String estructuras;
public Parser(String nombreArchivo, TablaSimbolos t)
{
	this.nombreArchivo=nombreArchivo;
	this.ts=t;
	this.cantRetornos = new ArrayList<>();
	this.estructuras = "Estructuras sintacticas detectadas en el codigo fuente :  \n";
	this.lex= new AnalizadorLexico(nombreArchivo, t, this);
}

String yyerror(String a) {
	return a;
}
String errores() {
	return lex.getErrores();
}
int yylex() {
	return lex.yylex();
}

boolean matcheanTipos(){
	char firstC=yylval.sval.charAt(0);
	if ( (tipoVar.equals(AccionSemantica.DOUBLE) && (firstC =='x' || firstC =='y' || firstC =='z')) || (tipoVar.equals(AccionSemantica.ULONGINT) && (firstC == 'd')) ) {
		return false;
	}
	return true;
}

void estructurasSintacticas(String estructura){
	estructuras += estructura + "\n";
}

public static void main(String[] args) {
    // Verificamos que el nombre de "prueba" sea pasado como argumento
    if (args.length < 1) {
        System.out.println("Por favor, proporciona el nombre de la prueba como argumento.");
        return;
    }

    // Tomamos el primer argumento como el valor de prueba
    String prueba = args[0];
    
    TablaSimbolos tb = new TablaSimbolos();
    Parser p = new Parser(prueba, tb);
    int valido = p.yyparse();
    
    System.out.println(p.lex.getListaTokens());
    System.out.println("\n" + p.estructuras);	
    System.out.println("Errores y Warnings detectados del codigo fuente :  \n" + p.errores());
    System.out.println("Contenido de la tabla de simbolos:  \n" + tb);
    
    if (valido == 0) {
        System.out.println("Se analizo todo el codigo fuente");
    } else {
        System.out.println("No se analizo completamente el codigo fuente, debido a uno o mas errores inesperados");
    }
}
//#line 728 "Parser.java"
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
{ lex.addErrorSintactico("Falta el nombre del programa");}
break;
case 4:
//#line 17 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma");}
break;
case 5:
//#line 18 "gramatica.y"
{ lex.addErrorSintactico("Falta delimitador del programa");}
break;
case 7:
//#line 24 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma");}
break;
case 8:
//#line 25 "gramatica.y"
{ lex.addErrorSintactico("Falta delimitador END del programa");}
break;
case 9:
//#line 26 "gramatica.y"
{ lex.addErrorSintactico("Falta delimitador BEGIN del programa");}
break;
case 10:
//#line 27 "gramatica.y"
{ lex.addErrorSintactico("Falta delimitador BEGIN y END del programa");}
break;
case 13:
//#line 32 "gramatica.y"
{ lex.addErrorSintactico("Sentencia inválida.");}
break;
case 14:
//#line 34 "gramatica.y"
{lex.addErrorSintactico("Falta punto y coma");}
break;
case 18:
//#line 43 "gramatica.y"
{	estructurasSintacticas("Se declaro la funcion, en linea: " + lex.getLineaInicial()); }
break;
case 19:
//#line 44 "gramatica.y"
{	estructurasSintacticas("Se declaro una etiqueta goto, en linea: " + lex.getLineaInicial()); }
break;
case 24:
//#line 51 "gramatica.y"
{estructurasSintacticas("Se imprimio un mensaje, en linea: " + lex.getLineaInicial()); }
break;
case 26:
//#line 53 "gramatica.y"
{estructurasSintacticas("Se llamo a una etiqueta goto, en linea: " + lex.getLineaInicial()); }
break;
case 28:
//#line 59 "gramatica.y"
{ lex.addErrorSintactico("Falta de paréntesis en la condición");}
break;
case 29:
//#line 60 "gramatica.y"
{ lex.addErrorSintactico("Falta de paréntesis derecho en la condición");}
break;
case 30:
//#line 61 "gramatica.y"
{ lex.addErrorSintactico("Falta de paréntesis izquierdo en la condición");}
break;
case 33:
//#line 66 "gramatica.y"
{ lex.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón y el que abre la segunda");}
break;
case 34:
//#line 67 "gramatica.y"
{ lex.addErrorSintactico("Falta parentesis que abre la segunda lista del patrón");}
break;
case 35:
//#line 68 "gramatica.y"
{ lex.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón");}
break;
case 36:
//#line 70 "gramatica.y"
{ lex.addErrorSintactico("Falta comparador entre los patrones");}
break;
case 37:
//#line 71 "gramatica.y"
{ lex.addErrorSintactico("Falta comparador entre las expresiones");}
break;
case 41:
//#line 81 "gramatica.y"
{estructurasSintacticas("Se definió una sentencia de control sin else, en la linea: " + lex.getLineaInicial());}
break;
case 42:
//#line 82 "gramatica.y"
{estructurasSintacticas("Se definió una sentencia de control con else, en la linea: " + lex.getLineaInicial());}
break;
case 43:
//#line 84 "gramatica.y"
{ lex.addErrorSintactico("Falta END_IF con ELSE");}
break;
case 44:
//#line 85 "gramatica.y"
{ lex.addErrorSintactico("Falta END_IF");}
break;
case 45:
//#line 86 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de control del then");}
break;
case 46:
//#line 87 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de control del ELSE");}
break;
case 47:
//#line 88 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de control tanto en THEN como ELSE");}
break;
case 56:
//#line 102 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo del control");}
break;
case 57:
//#line 103 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo del control");}
break;
case 58:
//#line 104 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma");}
break;
case 61:
//#line 110 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de la iteracion");}
break;
case 62:
//#line 111 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de la iteracion");}
break;
case 63:
//#line 112 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma");}
break;
case 66:
//#line 118 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma");}
break;
case 67:
//#line 122 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 68:
//#line 124 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 69:
//#line 127 "gramatica.y"
{      
						if (matcheanTipos()){
							ts.addClave(val_peek(0).sval);
							ts.addAtributo(val_peek(0).sval,AccionSemantica.TIPO,tipoVar);
						}
						else {
							lex.addErrorSintactico("se declaro la variable "+ val_peek(0).sval + " que difiere del tipo declarado: " + tipoVar);
						}
					}
break;
case 70:
//#line 136 "gramatica.y"
{  
			if (matcheanTipos()){
				ts.addClave(val_peek(0).sval);
				ts.addAtributo(val_peek(0).sval,AccionSemantica.TIPO,tipoVar);
		      } 
		      else {
		      	lex.addErrorSintactico("se declaro la variable "+ val_peek(0).sval + " que difiere del tipo declarado: " + tipoVar);
		      }
		}
break;
case 71:
//#line 145 "gramatica.y"
{ lex.addErrorSintactico("Falta coma en la lista de variables, puede haber parado la compilacion en este punto");}
break;
case 74:
//#line 153 "gramatica.y"
{estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 75:
//#line 154 "gramatica.y"
{estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 81:
//#line 168 "gramatica.y"
{ lex.addErrorSintactico("Falta operando izquierdo");}
break;
case 82:
//#line 169 "gramatica.y"
{ lex.addErrorSintactico("Falta operando derecho");
							lex.setErrorHandlerToken(")");}
break;
case 83:
//#line 171 "gramatica.y"
{ lex.addErrorSintactico("Falta operando derecho");
							lex.setErrorHandlerToken(";");}
break;
case 84:
//#line 173 "gramatica.y"
{ lex.addErrorSintactico("Falta operando derecho");
							lex.setErrorHandlerToken(")");}
break;
case 85:
//#line 175 "gramatica.y"
{ lex.addErrorSintactico("Falta operando derecho");
							lex.setErrorHandlerToken(";");}
break;
case 89:
//#line 186 "gramatica.y"
{ lex.addErrorSintactico("Falta operando izquierdo");}
break;
case 90:
//#line 187 "gramatica.y"
{ lex.addErrorSintactico("Falta operando izquierdo");}
break;
case 91:
//#line 188 "gramatica.y"
{ lex.addErrorSintactico("Falta operando derecho");
					lex.setErrorHandlerToken(")");}
break;
case 92:
//#line 190 "gramatica.y"
{ lex.addErrorSintactico("Falta operando derecho");
					lex.setErrorHandlerToken(")");}
break;
case 93:
//#line 192 "gramatica.y"
{ lex.addErrorSintactico("Falta operando derecho");
					lex.setErrorHandlerToken(";");}
break;
case 94:
//#line 194 "gramatica.y"
{ lex.addErrorSintactico("Falta operando derecho");
					lex.setErrorHandlerToken(";");}
break;
case 100:
//#line 205 "gramatica.y"
{	if (ts.esUlongInt(val_peek(0).sval)){
					lex.addErrorSintactico("se utilizo un Ulongint negativo, son solo positivos");
				}
				else {
					ts.convertirNegativo(val_peek(0).sval);
				}
			}
break;
case 102:
//#line 217 "gramatica.y"
{ this.cantRetornos.add(0); }
break;
case 103:
//#line 217 "gramatica.y"
{
										if (this.cantRetornos.get(this.cantRetornos.size()-1) > 0){
											estructurasSintacticas("Se declaró la función: " + val_peek(5).sval);
											ts.addClave(val_peek(5).sval);
											ts.addAtributo(val_peek(5).sval,AccionSemantica.TIPO,AccionSemantica.FUNCION);
											ts.addAtributo(val_peek(5).sval,AccionSemantica.TIPORETORNO,tipoVar);
										} else {
											lex.addErrorSintactico("Falta el retorno de la función: " + val_peek(5).sval);
										}
										this.cantRetornos.remove(this.cantRetornos.size()-1);
									}
break;
case 104:
//#line 228 "gramatica.y"
{ lex.addErrorSintactico("Falta nombre de la funcion declarada");}
break;
case 105:
//#line 229 "gramatica.y"
{ lex.addErrorSintactico("Falta el parametro en la declaracion de la funcion");}
break;
case 108:
//#line 236 "gramatica.y"
{ lex.addErrorSintactico("Se declaró más de un parametro");}
break;
case 110:
//#line 241 "gramatica.y"
{estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 111:
//#line 242 "gramatica.y"
{estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 112:
//#line 244 "gramatica.y"
{ lex.addErrorSintactico("Falta el nombre del parametro");}
break;
case 113:
//#line 245 "gramatica.y"
{ lex.addErrorSintactico("Falta el nombre del parametro o el tipo");}
break;
case 114:
//#line 248 "gramatica.y"
{ts.addClave(yylval.sval);}
break;
case 118:
//#line 256 "gramatica.y"
{lex.addErrorSintactico("Falta punto y coma");}
break;
case 121:
//#line 263 "gramatica.y"
{ this.cantRetornos.set(this.cantRetornos.size()-1, this.cantRetornos.get(this.cantRetornos.size()-1) + 1); }
break;
case 122:
//#line 266 "gramatica.y"
{estructurasSintacticas("Se invocó a la función: " + val_peek(3).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 123:
//#line 268 "gramatica.y"
{ lex.addErrorSintactico("Falta de parámetros en la invocación a la función");}
break;
case 124:
//#line 272 "gramatica.y"
{ lex.addErrorSintactico("Se utilizó más de un parámetro para invocar ala función");}
break;
case 129:
//#line 285 "gramatica.y"
{ lex.addErrorSintactico("Falta el mensaje del OUTF");}
break;
case 130:
//#line 286 "gramatica.y"
{ lex.addErrorSintactico("Parámetro invalido del OUTF");
					lex.setErrorHandlerToken(")");}
break;
case 133:
//#line 294 "gramatica.y"
{estructurasSintacticas("Se declaró un bucle FOR en la linea: " + lex.getLineaInicial());}
break;
case 134:
//#line 296 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma entre condicion y avance");}
break;
case 135:
//#line 297 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma entre asignacion y condicion");}
break;
case 136:
//#line 298 "gramatica.y"
{ lex.addErrorSintactico("Faltan todos los punto y coma del for");}
break;
case 137:
//#line 299 "gramatica.y"
{ lex.addErrorSintactico("Falta UP/DOWN");}
break;
case 138:
//#line 300 "gramatica.y"
{lex.addErrorSintactico("Falta valor del UP/DOWN");}
break;
case 139:
//#line 301 "gramatica.y"
{ lex.addErrorSintactico("Falta UP/DOWN y punto y coma entre condicion y avance");}
break;
case 140:
//#line 302 "gramatica.y"
{ lex.addErrorSintactico("Falta valor del UP/DOWN y punto y coma entre condicion y avance");}
break;
case 141:
//#line 303 "gramatica.y"
{ { lex.addErrorSintactico("Falta UP/DOWN, su valor, y punto y coma entre condicion y avance");}}
break;
case 145:
//#line 312 "gramatica.y"
{lex.addErrorSintactico("falta la etiqueta en el GOTO, en caso de faltar también el punto y coma es posible que no compile el resto del programa o lo haga mal.");
				lex.setErrorHandlerToken(";");}
break;
case 146:
//#line 318 "gramatica.y"
{System.out.println("Se declaró un tipo TRIPLE con el ID: " + val_peek(0).sval + " en la linea:" + lex.getLineaInicial());}
break;
case 147:
//#line 320 "gramatica.y"
{lex.addErrorSintactico("falta < en la declaración del TRIPLE"); }
break;
case 148:
//#line 321 "gramatica.y"
{lex.addErrorSintactico("falta > en la declaración del TRIPLE"); }
break;
case 149:
//#line 322 "gramatica.y"
{lex.addErrorSintactico("falta > y < en la declaración del TRIPLE"); }
break;
case 150:
//#line 323 "gramatica.y"
{ lex.addErrorSintactico("Falta la palabra clave TRIPLE");}
break;
case 151:
//#line 324 "gramatica.y"
{ lex.addErrorSintactico("Falta el ID de la tripla definida.");}
break;
//#line 1271 "Parser.java"
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
