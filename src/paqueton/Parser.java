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
   27,   35,    8,   36,    8,   37,    8,   32,   32,   33,
   33,   38,   38,   38,   38,   39,   34,   40,   40,   40,
   41,   41,   42,   11,   11,   43,   43,   44,   44,   13,
   13,   13,   45,   45,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   46,   46,   15,   15,    9,    9,    9,
    9,    9,    9,
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
    4,    0,    8,    0,    7,    0,    7,    1,    1,    3,
    1,    2,    2,    1,    1,    0,    5,    3,    1,    2,
    1,    1,    4,    4,    3,    3,    1,    2,    1,    4,
    3,    4,    1,    1,   12,   11,   11,   10,   11,   11,
   10,   10,   10,    1,    1,    2,    3,    6,    5,    5,
    4,    5,    7,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    0,    0,    0,   73,   72,
    0,    0,    0,    0,   19,    1,    0,   12,   15,   16,
   17,   18,   20,   21,   22,   23,   24,   25,   26,    0,
    0,    0,    0,    0,   13,   70,    0,    0,    0,    0,
    0,  146,    0,    0,   99,    0,    0,    0,    0,    0,
   97,    0,    0,    0,   98,    0,   88,   96,    0,    0,
    0,    0,    0,    0,   14,    0,    0,    0,    0,    4,
    0,   77,    0,   75,  125,    0,  129,    0,  127,    0,
    0,    0,  147,    0,    0,    0,    0,    0,    0,  100,
   89,   90,    0,   30,    0,   48,   49,   50,   51,   52,
   53,    0,    0,    0,    0,    0,    0,    7,    0,  134,
  131,  133,    0,    0,    0,    0,    9,   11,   74,    0,
    0,    3,    0,    0,  124,    0,  101,   71,   69,    0,
   27,    0,    0,    0,    0,    0,    0,   45,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   86,    0,   87,
    6,  132,  130,    0,  151,    0,    0,    0,    0,    0,
    0,  111,  126,    0,    0,    0,    0,    0,    0,   47,
   57,    0,   65,    0,   55,    0,   41,   83,   82,   85,
   84,   93,   92,   94,   91,  150,    0,  149,  152,  106,
    0,  113,  112,  104,    0,    0,    0,    0,    0,    0,
    0,   33,   56,   58,    0,   66,   46,    0,    0,  148,
  116,  102,  116,  110,    0,  144,  145,    0,    0,    0,
    0,   34,   35,   54,   64,   42,  153,  107,    0,  116,
  105,    0,    0,    0,    0,    0,   36,   32,    0,  103,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  122,
    0,  119,  121,    0,    0,  141,    0,  143,    0,    0,
    0,  142,    0,  138,    0,    0,  120,   62,    0,    0,
   60,  139,    0,  140,  136,  137,    0,  117,  118,   61,
   63,    0,  135,  123,   59,
};
final static short yydgoto[] = {                          3,
   16,    4,   17,   65,   19,   20,   21,   22,   23,   24,
   25,   26,   27,   28,   29,   52,   72,   73,  104,   87,
   88,  140,  174,  256,   30,   40,   31,   77,   56,   57,
   58,   32,  161,  228,  230,  213,  211,  162,  229,  251,
  252,  253,   78,   79,  113,  219,
};
final static short yysindex[] = {                      -250,
  594,  666,    0,    0,  -24,  -34, -220,   50,    0,    0,
  506,  666,   76,  -26,    0,    0,  382,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -214,
 -196, -200,  399, -151,    0,    0,  512,  360,  681,   -5,
   94,    0,  -97,  -16,    0,  512,  640,  -85,   -3,   -3,
    0,  -96,  152,  528,    0,  134,    0,    0,  421,   20,
  -28,  -50, -103,  702,    0,   -5,  512,  -35,  717,    0,
  681,    0,  528,    0,    0,  681,    0,   25,    0,  -12,
   10,   16,    0,   37,  192,  528,  -15,  218,  134,    0,
    0,    0,  762,    0,  681,    0,    0,    0,    0,    0,
    0,  567,  574,  681,    3,   53,  732,    0,  258,    0,
    0,    0,  260, -103,  -41,  242,    0,    0,    0,  265,
  -83,    0,  106,  106,    0,  326,    0,    0,    0,   54,
    0,  593,  535,  681,   -8,   33,  407,    0,  255,  -84,
  106,   69,  134,   91,  134,  106,   97,    0,  111,    0,
    0,    0,    0,  -40,    0,   63,   74,  -37,   75,   77,
  151,    0,    0,  485,  297,  580,  681,  298,  106,    0,
    0,   67,    0,  455,    0,  773,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -74,    0,    0,    0,
  162,    0,    0,    0,  -83,  506,  -56,  681,  681,  306,
  317,    0,    0,    0,  542,    0,    0,   84,  302,    0,
    0,    0,    0,    0,   32,    0,    0,   -7,  112,  323,
  331,    0,    0,    0,    0,    0,    0,    0,  117,    0,
    0,  338,  -23,  -33,  126,  350,    0,    0,  747,    0,
  784,  355,  784,  -32,  357,  784,  363,  784,  366,    0,
  226,    0,    0,  505,  349,    0,  784,    0,  373,  784,
  784,    0,  784,    0,  512,  687,    0,    0,  145,  534,
    0,    0,  784,    0,    0,    0,  384,    0,    0,    0,
    0,  602,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,  150,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  155,
    0,    0,    0,  438,    0,    0,    0,    0,    0,  266,
    0,    0,    0,   12,    0,    0,    0,    0,    0,    0,
    0,    0,   42,    0,    0,   59,    0,    0,    0,  438,
    0,    0,    0,  431,    0,  293,    0,    0,  435,    0,
    0,    0,  243,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  428,  392,    0,    0,   86,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  439,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  392,  166,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  334,
  180,    0,  118,    0,  153,  205,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  222,  251,
    0,    0,    0,    0,    0,    0,    0,    0,  477,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  367,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  110,   -1,    0,  639,    0,    0,    0,    0,
   -9,    0,    0,    0,    0, -104,    5,  750,  -62,    1,
    0,  264,  188,  487,  715,  416,   38,  -17,  -20,   30,
    0,    0,  294, -149,    0,    0,    0,  269,    0,    0,
 -112,    0,    0,  327,    0, -107,
};
final static int YYTABLESIZE=1060;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   34,   51,  218,  190,  121,   38,    1,  246,  260,  114,
   60,   71,  111,   49,   47,   53,   48,  243,   50,   74,
  156,  187,    2,   38,  133,  132,   89,   51,   51,   51,
  102,   38,  103,   63,   35,   41,   51,   51,   82,   51,
   51,   48,   36,  112,  100,   99,  101,   48,   55,  119,
   85,   51,   95,   95,   95,   95,   95,   51,   95,  197,
   42,   51,  118,  231,   67,  125,   51,  118,  126,  166,
   95,   95,   95,   95,   55,   55,   55,   68,   91,   92,
  240,  143,  145,   55,   55,   51,   55,   55,   39,   43,
  233,  215,   51,   51,   51,   51,   51,   48,   55,   80,
   28,   80,   80,   80,   55,  118,   39,  234,   55,  179,
  235,   33,  127,   55,   39,   61,   51,   80,   80,   80,
   80,   59,   70,   51,   51,  244,   81,  178,   81,   81,
   81,  181,   55,  168,  148,  150,   95,  183,  267,   55,
   55,   55,   55,   55,   81,   81,   81,   81,  102,  180,
  103,  185,   83,  279,   51,  182,   51,   51,   78,   84,
   78,   78,   78,   55,    9,   10,  200,  201,   53,  184,
   55,   55,   90,  159,   93,  105,   78,   78,   78,   78,
  106,  209,  210,   80,    9,   10,   51,  176,   51,   51,
  177,  194,   94,   79,  195,   79,   79,   79,  220,  221,
   53,   55,  212,   55,   55,  195,  128,  216,  217,  128,
   81,   79,   79,   79,   79,  155,  186,    9,   10,  159,
   37,  120,   36,   37,  245,  259,   37,  109,   44,   45,
    9,   10,  131,   55,  242,   55,   55,  250,   37,   62,
  216,  217,   78,   96,   97,   31,   98,  277,   31,  250,
   81,  110,   37,   44,   45,   51,  216,  217,  147,   44,
   45,  134,  115,   31,  250,  115,  128,   95,   95,   95,
   95,   95,  129,   95,   95,   95,   95,   79,   95,   95,
   95,   95,   95,   76,  266,   95,   76,   95,   95,  232,
   95,  114,   95,  108,  114,  216,  217,  130,  152,   28,
  153,   76,   55,  157,  158,   28,   28,  170,  149,   44,
   45,  164,   28,  175,   80,   80,   80,   80,   80,  188,
   80,   80,   80,   80,   68,   80,   80,   80,   80,   80,
  189,  192,   80,  193,   80,   80,  198,   80,  202,   80,
  203,   81,   81,   81,   81,   81,  222,   81,   81,   81,
   81,   67,   81,   81,   81,   81,   81,  223,  226,   81,
  227,   81,   81,  237,   81,   71,   81,   49,   47,  236,
   48,  238,   50,   78,   78,   78,   78,   78,  241,   78,
   78,   78,   78,  247,   78,   78,   78,   78,   78,  239,
  248,   78,   44,   78,   78,  257,   78,  261,   78,   71,
   75,   49,   47,  263,   48,  265,   50,  271,   79,   79,
   79,   79,   79,  273,   79,   79,   79,   79,  280,   79,
   79,   79,   79,   79,  284,   43,   79,  109,   79,   79,
   10,   79,  108,   79,    5,   40,   37,   37,    8,  208,
   64,  270,   37,   37,   37,   66,   37,   37,   37,   37,
   37,  191,  163,   37,    0,   37,   37,   69,   37,    0,
   37,   31,   31,  214,    0,  172,    0,   31,   31,   31,
    0,   31,   31,   31,   31,   31,    0,    0,   31,  107,
   31,   31,    6,   31,    0,   31,   29,    0,    7,    0,
    0,    0,    8,    9,   10,   11,   12,    0,    0,   76,
    0,   13,   14,    0,  249,   76,   15,    0,    0,   76,
   76,   76,   76,  205,    0,    0,   76,   38,   76,   76,
   39,   76,   68,   76,   46,    0,   49,   47,   68,   48,
    0,   50,   68,   68,   68,   68,   38,   38,   38,   68,
    0,   68,   68,  196,   68,   46,   68,   49,   47,   67,
   48,   71,   50,   49,   47,   67,   48,    0,   50,   67,
   67,   67,   67,  269,    0,    0,   67,    0,   67,   67,
  102,   67,  103,   67,  167,    0,   49,   47,    0,   48,
    0,   50,   44,   45,    0,    0,    0,  100,   99,  101,
   44,    0,  282,    9,   10,    0,   44,    0,    0,    0,
   44,   44,   44,   44,    0,    0,    0,   44,   49,   44,
   44,   48,   44,   50,   44,   49,   44,   45,   48,  199,
   50,   49,   47,   43,   48,    0,   50,    9,   10,   43,
    0,    0,    0,   43,   43,   43,   43,    0,    6,    0,
   43,    0,   43,   43,    7,   43,    0,   43,    8,    9,
   10,   11,  100,   99,  101,    6,    0,   13,   14,    0,
    0,    7,   15,  135,    0,    8,    9,   10,   11,    7,
    0,    0,    0,    8,   13,   14,   11,    6,    0,   15,
  171,   49,   13,    7,   48,   29,   50,    8,    9,   10,
   11,   29,   29,    0,   12,    0,   13,   14,   29,    0,
   12,   15,    0,    0,   12,   12,   12,   12,    0,    0,
    0,  135,    0,   12,   12,    0,    0,    7,   12,    0,
    0,    8,   49,   47,   11,   48,    0,   50,  204,  258,
   13,  139,  262,    0,  264,   38,   38,    0,   38,    0,
    0,   44,   45,  272,    0,    0,  274,  275,    0,  276,
    0,    0,   76,    0,    0,    0,    0,    0,    0,  283,
   54,  135,   44,   45,    0,    0,    0,    7,   44,   45,
    0,    8,    0,    0,   11,  173,  115,  116,  268,    0,
   13,    0,    0,   95,    0,    0,   96,   97,   80,   98,
  135,   44,   45,    0,    0,   86,    7,    0,  135,    0,
    8,    0,    0,   11,    7,    0,    0,  281,    8,   13,
    0,   11,  206,    0,  139,  224,    0,   13,    0,    0,
  123,    0,  142,   44,   45,  124,    0,    0,  154,  144,
   44,   45,    0,    0,    0,  160,   44,   45,    0,    0,
   76,    0,    0,  225,  141,    0,    0,    0,  165,    5,
    6,   96,   97,  146,   98,    0,    7,    0,  135,    0,
    8,    9,   10,   11,    7,    0,   12,    0,    8,   13,
   14,   11,  160,    0,   15,  285,    0,   13,    0,  255,
    0,  255,  123,  169,  255,    0,  255,    0,    0,    0,
    0,    0,  173,    0,    0,  255,   44,   45,  255,  255,
    0,  255,    0,    0,    0,    0,    0,    0,  206,  160,
    0,  255,    0,   54,    0,  123,  123,    0,    0,    0,
  225,    5,    6,    0,    0,    0,    0,    0,    7,    0,
    0,    0,    8,    9,   10,   11,    0,   44,   45,    0,
    0,   13,   14,    6,    0,   54,   15,  123,  123,    7,
    0,    0,    0,    8,    9,   10,   11,    0,    6,    0,
  278,    0,   13,   14,    7,  249,    0,   15,    8,    9,
   10,   11,    0,    6,    0,  117,    0,   13,   14,    7,
    0,    0,   15,    8,    9,   10,   11,    0,    6,    0,
  122,    0,   13,   14,    7,    0,    0,   15,    8,    9,
   10,   11,    0,    6,    0,  151,    0,   13,   14,    7,
    0,    0,   15,    8,    9,   10,   11,    0,  135,    0,
    0,    0,   13,   14,    7,  249,    0,   15,    8,  135,
    0,   11,    0,  136,  137,    7,  138,   13,    0,    8,
  135,    0,   11,    0,    0,  137,    7,  207,   13,    0,
    8,    0,    0,   11,    0,    0,  254,    0,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          1,
    2,   11,   59,   41,   40,   40,  257,   41,   41,   60,
   12,   40,   41,   42,   43,   11,   45,   41,   47,   37,
   62,   62,  273,   40,   87,   41,   47,   37,   38,   39,
   43,   40,   45,   60,   59,  256,   46,   47,   44,   49,
   50,   45,  257,   61,   60,   61,   62,   45,   11,   67,
   46,   61,   41,   42,   43,   44,   45,   67,   47,  164,
  281,   71,   64,  213,  261,   41,   76,   69,   44,  132,
   59,   60,   61,   62,   37,   38,   39,  278,   49,   50,
  230,  102,  103,   46,   47,   95,   49,   50,  123,   40,
   59,  196,  102,  103,  104,  105,  106,   45,   61,   41,
   59,   43,   44,   45,   67,  107,  123,  215,   71,   41,
  218,    2,  125,   76,  123,   40,  126,   59,   60,   61,
   62,   12,  274,  133,  134,  233,   41,   59,   43,   44,
   45,   41,   95,  133,  105,  106,  125,   41,  251,  102,
  103,  104,  105,  106,   59,   60,   61,   62,   43,   59,
   45,   41,   59,  266,  164,   59,  166,  167,   41,  257,
   43,   44,   45,  126,  268,  269,  166,  167,  164,   59,
  133,  134,  258,  257,  271,   42,   59,   60,   61,   62,
   47,  256,  257,  125,  268,  269,  196,  272,  198,  199,
  275,   41,   41,   41,   44,   43,   44,   45,  198,  199,
  196,  164,   41,  166,  167,   44,   41,  264,  265,   44,
  125,   59,   60,   61,   62,  257,  257,  268,  269,  257,
   41,  257,  257,   44,  258,  258,  261,  256,  257,  258,
  268,  269,   41,  196,  258,  198,  199,  239,   59,  266,
  264,  265,  125,  259,  260,   41,  262,  265,   44,  251,
  256,  280,  261,  257,  258,  265,  264,  265,  256,  257,
  258,   44,   41,   59,  266,   44,  257,  256,  257,  258,
  259,  260,  257,  262,  263,  264,  265,  125,  267,  268,
  269,  270,  271,   41,   59,  274,   44,  276,  277,  258,
  279,   41,  281,  274,   44,  264,  265,  261,   41,  258,
   41,   59,  265,   62,   40,  264,  265,  275,  256,  257,
  258,  258,  271,   59,  256,  257,  258,  259,  260,  257,
  262,  263,  264,  265,   59,  267,  268,  269,  270,  271,
  257,  257,  274,  257,  276,  277,   40,  279,   41,  281,
  274,  256,  257,  258,  259,  260,   41,  262,  263,  264,
  265,   59,  267,  268,  269,  270,  271,   41,  275,  274,
   59,  276,  277,   41,  279,   40,  281,   42,   43,  258,
   45,   41,   47,  256,  257,  258,  259,  260,   41,  262,
  263,  264,  265,  258,  267,  268,  269,  270,  271,  273,
   41,  274,   59,  276,  277,   41,  279,   41,  281,   40,
   41,   42,   43,   41,   45,   40,   47,   59,  256,  257,
  258,  259,  260,   41,  262,  263,  264,  265,  274,  267,
  268,  269,  270,  271,   41,   59,  274,  278,  276,  277,
    0,  279,  278,  281,    0,   44,  257,  258,    0,  176,
   59,  254,  263,  264,  265,   30,  267,  268,  269,  270,
  271,  158,  126,  274,   -1,  276,  277,   59,  279,   -1,
  281,  257,  258,  195,   -1,   59,   -1,  263,  264,  265,
   -1,  267,  268,  269,  270,  271,   -1,   -1,  274,   59,
  276,  277,  257,  279,   -1,  281,   59,   -1,  263,   -1,
   -1,   -1,  267,  268,  269,  270,   59,   -1,   -1,  257,
   -1,  276,  277,   -1,  279,  263,  281,   -1,   -1,  267,
  268,  269,  270,   59,   -1,   -1,  274,   41,  276,  277,
   44,  279,  257,  281,   40,   -1,   42,   43,  263,   45,
   -1,   47,  267,  268,  269,  270,   60,   61,   62,  274,
   -1,  276,  277,   59,  279,   40,  281,   42,   43,  257,
   45,   40,   47,   42,   43,  263,   45,   -1,   47,  267,
  268,  269,  270,   59,   -1,   -1,  274,   -1,  276,  277,
   43,  279,   45,  281,   40,   -1,   42,   43,   -1,   45,
   -1,   47,  257,  258,   -1,   -1,   -1,   60,   61,   62,
  257,   -1,   59,  268,  269,   -1,  263,   -1,   -1,   -1,
  267,  268,  269,  270,   -1,   -1,   -1,  274,   42,  276,
  277,   45,  279,   47,  281,   42,  257,  258,   45,   40,
   47,   42,   43,  257,   45,   -1,   47,  268,  269,  263,
   -1,   -1,   -1,  267,  268,  269,  270,   -1,  257,   -1,
  274,   -1,  276,  277,  263,  279,   -1,  281,  267,  268,
  269,  270,   60,   61,   62,  257,   -1,  276,  277,   -1,
   -1,  263,  281,  257,   -1,  267,  268,  269,  270,  263,
   -1,   -1,   -1,  267,  276,  277,  270,  257,   -1,  281,
  274,   42,  276,  263,   45,  258,   47,  267,  268,  269,
  270,  264,  265,   -1,  257,   -1,  276,  277,  271,   -1,
  263,  281,   -1,   -1,  267,  268,  269,  270,   -1,   -1,
   -1,  257,   -1,  276,  277,   -1,   -1,  263,  281,   -1,
   -1,  267,   42,   43,  270,   45,   -1,   47,  274,  243,
  276,   93,  246,   -1,  248,  259,  260,   -1,  262,   -1,
   -1,  257,  258,  257,   -1,   -1,  260,  261,   -1,  263,
   -1,   -1,   38,   -1,   -1,   -1,   -1,   -1,   -1,  273,
   11,  257,  257,  258,   -1,   -1,   -1,  263,  257,  258,
   -1,  267,   -1,   -1,  270,  137,   62,   63,  274,   -1,
  276,   -1,   -1,  256,   -1,   -1,  259,  260,   39,  262,
  257,  257,  258,   -1,   -1,   46,  263,   -1,  257,   -1,
  267,   -1,   -1,  270,  263,   -1,   -1,  274,  267,  276,
   -1,  270,  174,   -1,  176,  274,   -1,  276,   -1,   -1,
   71,   -1,  256,  257,  258,   76,   -1,   -1,  114,  256,
  257,  258,   -1,   -1,   -1,  121,  257,  258,   -1,   -1,
  126,   -1,   -1,  205,   95,   -1,   -1,   -1,  256,  256,
  257,  259,  260,  104,  262,   -1,  263,   -1,  257,   -1,
  267,  268,  269,  270,  263,   -1,  273,   -1,  267,  276,
  277,  270,  158,   -1,  281,  274,   -1,  276,   -1,  241,
   -1,  243,  133,  134,  246,   -1,  248,   -1,   -1,   -1,
   -1,   -1,  254,   -1,   -1,  257,  257,  258,  260,  261,
   -1,  263,   -1,   -1,   -1,   -1,   -1,   -1,  270,  195,
   -1,  273,   -1,  164,   -1,  166,  167,   -1,   -1,   -1,
  282,  256,  257,   -1,   -1,   -1,   -1,   -1,  263,   -1,
   -1,   -1,  267,  268,  269,  270,   -1,  257,  258,   -1,
   -1,  276,  277,  257,   -1,  196,  281,  198,  199,  263,
   -1,   -1,   -1,  267,  268,  269,  270,   -1,  257,   -1,
  274,   -1,  276,  277,  263,  279,   -1,  281,  267,  268,
  269,  270,   -1,  257,   -1,  274,   -1,  276,  277,  263,
   -1,   -1,  281,  267,  268,  269,  270,   -1,  257,   -1,
  274,   -1,  276,  277,  263,   -1,   -1,  281,  267,  268,
  269,  270,   -1,  257,   -1,  274,   -1,  276,  277,  263,
   -1,   -1,  281,  267,  268,  269,  270,   -1,  257,   -1,
   -1,   -1,  276,  277,  263,  279,   -1,  281,  267,  257,
   -1,  270,   -1,  272,  273,  263,  275,  276,   -1,  267,
  257,   -1,  270,   -1,   -1,  273,  263,  275,  276,   -1,
  267,   -1,   -1,  270,   -1,   -1,  273,   -1,   -1,  276,
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

//#line 321 "gramatica.y"
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
	return ErrorHandler.getErrores();
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
//#line 788 "Parser.java"
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
{ ErrorHandler.addErrorSintactico("Falta de paréntesis en la condición", lex.getLineaInicial());}
break;
case 29:
//#line 60 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de paréntesis derecho en la condición", lex.getLineaInicial());}
break;
case 30:
//#line 61 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de paréntesis izquierdo en la condición", lex.getLineaInicial());}
break;
case 33:
//#line 66 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón y el que abre la segunda", lex.getLineaInicial());}
break;
case 34:
//#line 67 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que abre la segunda lista del patrón", lex.getLineaInicial());}
break;
case 35:
//#line 68 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón", lex.getLineaInicial());}
break;
case 36:
//#line 70 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta comparador entre los patrones", lex.getLineaInicial());}
break;
case 37:
//#line 71 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta comparador entre las expresiones", lex.getLineaInicial());}
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
{ ErrorHandler.addErrorSintactico("Falta END_IF con ELSE", lex.getLineaInicial());}
break;
case 44:
//#line 85 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta END_IF", lex.getLineaInicial());}
break;
case 45:
//#line 86 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control del then", lex.getLineaInicial());}
break;
case 46:
//#line 87 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control del ELSE", lex.getLineaInicial());}
break;
case 47:
//#line 88 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control tanto en THEN como ELSE", lex.getLineaInicial());}
break;
case 56:
//#line 102 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
break;
case 57:
//#line 103 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
break;
case 58:
//#line 104 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 61:
//#line 110 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
break;
case 62:
//#line 111 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
break;
case 63:
//#line 112 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 66:
//#line 118 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
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
							ErrorHandler.addErrorSintactico("se declaro la variable "+ val_peek(0).sval + " que difiere del tipo declarado: " + tipoVar, lex.getLineaInicial());
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
		      	ErrorHandler.addErrorSintactico("se declaro la variable "+ val_peek(0).sval + " que difiere del tipo declarado: " + tipoVar, lex.getLineaInicial());
		      }
		}
break;
case 71:
//#line 145 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta coma en la lista de variables, puede haber parado la compilacion en este punto", lex.getLineaInicial());}
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
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 82:
//#line 169 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");}
break;
case 83:
//#line 171 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
break;
case 84:
//#line 173 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");}
break;
case 85:
//#line 175 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
break;
case 89:
//#line 186 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 90:
//#line 187 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 91:
//#line 188 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 92:
//#line 190 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 93:
//#line 192 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
break;
case 94:
//#line 194 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
break;
case 100:
//#line 205 "gramatica.y"
{	if (ts.esUlongInt(val_peek(0).sval)){
					ErrorHandler.addErrorSintactico("se utilizo un Ulongint negativo, son solo positivos", lex.getLineaInicial());
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
{this.checkRet(val_peek(5).sval);}
break;
case 104:
//#line 218 "gramatica.y"
{ this.cantRetornos.add(0); }
break;
case 105:
//#line 218 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta nombre de la funcion declarada", lex.getLineaInicial());
													this.checkRet("");
													}
break;
case 106:
//#line 221 "gramatica.y"
{ this.cantRetornos.add(0);}
break;
case 107:
//#line 221 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el parametro en la declaracion de la funcion", lex.getLineaInicial());
							    this.checkRet(val_peek(4).sval);
							   }
break;
case 110:
//#line 230 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Se declaró más de un parametro", lex.getLineaInicial());}
break;
case 112:
//#line 235 "gramatica.y"
{estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 113:
//#line 236 "gramatica.y"
{estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 114:
//#line 238 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el nombre del parametro", lex.getLineaInicial());}
break;
case 115:
//#line 239 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el nombre del parametro o el tipo", lex.getLineaInicial());}
break;
case 116:
//#line 242 "gramatica.y"
{ts.addClave(yylval.sval);}
break;
case 120:
//#line 250 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 123:
//#line 257 "gramatica.y"
{ this.cantRetornos.set(this.cantRetornos.size()-1, this.cantRetornos.get(this.cantRetornos.size()-1) + 1); }
break;
case 124:
//#line 260 "gramatica.y"
{estructurasSintacticas("Se invocó a la función: " + val_peek(3).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 125:
//#line 262 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de parámetros en la invocación a la función", lex.getLineaInicial());}
break;
case 126:
//#line 266 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Se utilizó más de un parámetro para invocar a la función", lex.getLineaInicial());}
break;
case 131:
//#line 279 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el mensaje del OUTF", lex.getLineaInicial());}
break;
case 132:
//#line 280 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Parámetro invalido del OUTF", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 135:
//#line 288 "gramatica.y"
{estructurasSintacticas("Se declaró un bucle FOR en la linea: " + lex.getLineaInicial());}
break;
case 136:
//#line 290 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 137:
//#line 291 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma entre asignacion y condicion", lex.getLineaInicial());}
break;
case 138:
//#line 292 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Faltan todos los punto y coma del for", lex.getLineaInicial());}
break;
case 139:
//#line 293 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta UP/DOWN", lex.getLineaInicial());}
break;
case 140:
//#line 294 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN", lex.getLineaInicial());}
break;
case 141:
//#line 295 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 142:
//#line 296 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 143:
//#line 297 "gramatica.y"
{ { ErrorHandler.addErrorSintactico("Falta UP/DOWN, su valor, y punto y coma entre condicion y avance", lex.getLineaInicial());}}
break;
case 147:
//#line 306 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta la etiqueta en el GOTO, en caso de faltar también el punto y coma es posible que no compile el resto del programa o lo haga mal.", lex.getLineaInicial());
				lex.setErrorHandlerToken(";");}
break;
case 148:
//#line 312 "gramatica.y"
{estructurasSintacticas("Se declaró un tipo TRIPLE con el ID: " + val_peek(0).sval + " en la linea:" + lex.getLineaInicial());}
break;
case 149:
//#line 314 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta < en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 150:
//#line 315 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta > en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 151:
//#line 316 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta > y < en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 152:
//#line 317 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta la palabra clave TRIPLE", lex.getLineaInicial());}
break;
case 153:
//#line 318 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el ID de la tripla definida.", lex.getLineaInicial());}
break;
//#line 1333 "Parser.java"
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
