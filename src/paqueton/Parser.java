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
    6,    7,    7,    7,    7,    7,    7,    7,   18,   18,
   18,   18,   22,   19,   19,   19,   19,   19,   19,   19,
   23,   25,   25,   24,   26,   26,   13,   13,   13,   13,
   13,   13,   13,   29,   27,   30,   31,   21,   21,   21,
   21,   21,   21,   28,   28,   28,   28,   28,   33,   33,
   33,   33,   33,   32,   32,   32,    8,   36,    8,   35,
   35,   35,   34,   34,   11,   11,   20,   20,   20,   20,
   20,   20,   20,   20,   38,   38,   38,   38,   38,   38,
   38,   38,   38,   39,   39,   39,   39,   40,   40,   37,
   41,   44,   46,    9,   47,    9,   48,    9,   42,   42,
   43,   43,   49,   49,   49,   49,   45,   50,   50,   50,
   51,   16,   53,   12,   12,   52,   52,   54,   54,   14,
   14,   14,   55,   55,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   57,   56,   58,   58,   17,   17,
   10,   10,   10,   10,   10,   10,
};
final static short yylen[] = {                            2,
    0,    3,    1,    4,    3,    3,    4,    3,    3,    3,
    2,    3,    1,    2,    2,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    3,    1,
    2,    2,    0,    4,    7,    5,    6,    6,    7,    3,
    3,    3,    1,    3,    3,    1,    5,    7,    4,    4,
    4,    6,    5,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    4,    2,    3,    2,    3,    4,    2,
    3,    2,    3,    3,    1,    2,    2,    0,    3,    3,
    1,    3,    1,    1,    3,    3,    3,    3,    1,    2,
    4,    4,    4,    4,    3,    3,    1,    2,    2,    4,
    4,    4,    4,    1,    1,    1,    1,    1,    2,    4,
    4,    0,    0,    9,    0,    7,    0,    7,    1,    1,
    3,    1,    2,    2,    1,    1,    4,    3,    1,    2,
    1,    4,    0,    5,    3,    3,    1,    1,    2,    4,
    3,    4,    1,    1,   10,   10,    9,    9,    8,    9,
    9,    8,    8,    8,    1,    3,    1,    1,    2,    3,
    6,    5,    5,    4,    5,    7,
};
final static short yydefred[] = {                         0,
    1,    0,    0,    3,    0,    0,    0,    0,    0,   84,
   83,    0,    0,    0,    0,   20,    0,    0,   16,   17,
   18,   19,   21,   22,   23,   24,   25,   26,   27,   28,
    0,    0,    0,    0,    2,    0,   13,   14,    0,    0,
    0,    0,    0,  159,    0,    0,  108,    0,    0,    0,
    0,    0,  106,   55,    0,    0,    0,    0,   97,  105,
  107,    0,    0,    0,    0,    0,   15,    5,   81,    0,
    0,    0,    0,    0,    0,    0,  135,    0,    0,    0,
  160,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  109,   98,   99,   32,    0,   58,   59,   60,   61,   62,
   63,    0,    0,   33,    0,    0,    0,    0,  144,  141,
    0,    0,    0,    0,    0,    0,    4,   12,    0,    0,
    0,    0,    0,    0,    8,   10,    0,    0,    0,  137,
  110,    0,    0,  155,    0,    0,    0,   29,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   56,    0,
   54,    0,    0,   51,    0,    0,   95,    0,   96,  142,
  140,    0,  164,    0,    0,  132,   82,   80,    0,    0,
    0,    0,  122,    7,  139,  134,    0,  156,    0,  157,
  158,    0,    0,  111,    0,    0,    0,    0,    0,    0,
    0,   92,   91,   94,   93,    0,   67,    0,   75,    0,
   65,   47,    0,   57,   53,  102,  101,  103,  100,  163,
    0,  162,  165,  117,    0,  124,  123,  115,    0,  136,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   36,
    0,   66,   68,    0,   76,    0,   52,    0,  161,    0,
    0,    0,  121,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   37,   38,    0,   64,   74,   48,
  166,    0,  118,  113,  116,    0,    0,  152,    0,  154,
    0,    0,    0,  153,    0,    0,  149,   39,   35,  131,
    0,  129,    0,   72,    0,    0,   70,  150,    0,  151,
  147,    0,  148,    0,  130,  114,   71,   73,    0,  145,
  146,  127,  128,   69,
};
final static short yydgoto[] = {                          3,
   35,    5,    4,   17,   67,   19,   20,   21,   22,   23,
   24,   25,   26,   27,   28,   29,   30,  134,   55,  188,
  104,  147,   88,  189,   89,  190,   57,  153,  154,  155,
  205,  200,  268,   31,   70,   42,   32,   58,   59,   60,
   61,   33,  172,  215,  263,  283,  242,  240,  173,  281,
  282,  129,   78,  130,  112,   83,  135,  183,
};
final static short yysindex[] = {                      -144,
    0,  629,    0,    0,  577,  -45,  -20, -220,    2,    0,
    0,  138,   28,  -42,   30,    0,  280, -195,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -168, -163, -172,  629,    0,  297,    0,    0,  424,   91,
  424, -168,   84,    0, -110,  -27,    0,  478,  455, -104,
  204,  204,    0,    0,  120,  508, -113,   98,    0,    0,
    0,   24,   17, -132,  424,  652,    0,    0,    0,   -1,
  424,   10,  314, -111,  667,    6,    0,  123,  -28,   -1,
    0,  -94,  117,  424,  424,  141,  508,   54,  148,   98,
    0,    0,    0,    0,  424,    0,    0,    0,    0,    0,
    0,  412,  445,    0,  -73,  -44,  274,  157,    0,    0,
    6,  164, -132,  -53,  185,  103,    0,    0,   26,   27,
    6,  169, -130,  682,    0,    0,    6,   52,  128,    0,
    0,   57,  138,    0,  -48,  -24,    6,    0,  541,  499,
  424,    6,    3,   98,   53,   98,  424,  -20,    0,  159,
    0,  252, -154,    0,   42,   61,    0,   69,    0,    0,
    0,  -46,    0,   68,   73,    0,    0,    0,  295,   80,
   85,  147,    0,    0,    0,    0,  123,    0,  -57,    0,
    0,  -78,   86,    0,  311,  569,  424,    6,  322,  324,
    6,    0,    0,    0,    0,    6,    0,  108,    0,  215,
    0,    0,  549,    0,    0,    0,    0,    0,    0,    0,
   21,    0,    0,    0, -130,    0,    0,    0, -130,    0,
  345,  -29,   18,  139,  353,  424,  424,  357,  361,    0,
  424,    0,    0,  720,    0,   42,    0,  348,    0,  150,
  152,  150,    0,  603,  370,  603,  -15,  390,  603,  176,
  396,  603,  399,  400,    0,    0,    6,    0,    0,    0,
    0,  712,    0,    0,    0,  365,  385,    0,  603,    0,
  404,  603,  603,    0,  409,  603,    0,    0,    0,    0,
  329,    0,  150,    0,  177,  380,    0,    0,  603,    0,
    0,  603,    0,  697,    0,    0,    0,    0,  735,    0,
    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0, -226,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  350,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  174,    0,    0,    0,    0,    0,    0,    0,    0,  132,
    0,    0,    0,    0,    0,  -37,    0,    0,    0,    0,
    0,    0,    0,    0,  384,    0,    0,   -6,    0,    0,
    0,    0,    0,    0,    0,  463,    0,    0,    0,  136,
    0,    0,    0,  350,  465,  151,    0,    0,    0,  179,
    0,    0,    0,    0,    0,  434,  429,    0,    0,   31,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  427,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  207,    0,    0,  479,    0,    0,  228,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  429,    0,    0,    0,
    0,  112,    0,   64,    0,   90,    0,    0,    0,    0,
    0,    0,  238,    0,  259,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -127,  262,
  321,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  436,    0,    0,
  -14,    0,    0,    0,    0,  114,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  343,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,   77,   -2,    0,  615,    0,    0,    0,
    0,  612,    0,    0,    0,    0,    0,  484,  450,  646,
  -54,    0,    0, -146,    0,    0,    0,  296,  351,  356,
 -158,  237,  502,  659,  468,    0,    0,  -16,  -23,    0,
    0,    0,  298,    0, -184,    0,    0,    0,  292,    0,
 -177,    0,    0,  347,    0,    0,  401, -122,
};
final static int YYTABLESIZE=1014;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   50,  222,   37,  104,  104,  104,  104,  104,  164,  104,
  182,  246,   40,   38,  102,  211,  103,   64,  102,   40,
  103,  104,  104,  104,  104,  272,   41,   92,   93,   42,
   78,   74,   90,  140,   89,   43,   89,   89,   89,  228,
  229,   45,  120,  193,  237,   41,   41,   41,  102,  123,
  103,  120,   89,   89,   89,   89,  223,  265,  249,  224,
   44,  192,  250,  118,  110,   51,   49,   62,   50,   65,
   52,   90,  118,   90,   90,   90,  113,  260,   68,  253,
  254,   36,  157,  159,  186,  144,  146,  104,   69,   90,
   90,   90,   90,  195,  139,   84,  131,   71,  296,  247,
  184,  207,   41,  295,   87,   72,   87,   87,   87,  209,
   73,  194,    1,  100,   99,  101,  303,  149,   89,  206,
  151,  118,   87,   87,   87,   87,  170,  208,    2,  112,
   88,   77,   88,   88,   88,   10,   11,   10,   11,  106,
  112,  112,   81,  166,  107,  102,   82,  103,   88,   88,
   88,   88,   40,   91,   34,   90,   48,  105,   51,   49,
   94,   50,  125,   52,   51,   49,  132,   50,  176,   52,
   40,  177,   34,  133,  133,  133,  133,   48,  133,   51,
   49,  138,   50,  148,   52,  180,  181,  218,   87,    8,
  219,  141,  264,    9,   77,  219,   12,  160,  149,  150,
  221,  151,   13,  163,  161,   15,  180,  181,  169,   86,
  210,  156,   46,   47,   88,  180,  181,  198,  104,  104,
  104,  104,  104,   63,  104,  104,  104,  104,  245,  104,
  104,  104,  104,  104,  180,  181,  104,   79,  104,  104,
   39,  104,  271,  104,   41,   41,  165,   41,   50,   89,
   89,   89,   89,   89,  119,   89,   89,   89,   89,  280,
   89,   89,   89,   89,   89,   85,  122,   89,  138,   89,
   89,  138,   89,  234,   89,  248,  238,  239,  280,  108,
   46,   47,  167,  168,   10,   11,   90,   90,   90,   90,
   90,  280,   90,   90,   90,   90,   49,   90,   90,   90,
   90,   90,  126,  109,   90,  126,   90,   90,  175,   90,
  201,   90,   96,   97,  178,   98,  204,   50,   50,   87,
   87,   87,   87,   87,  212,   87,   87,   87,   87,  213,
   87,   87,   87,   87,   87,  214,  216,   87,   66,   87,
   87,  217,   87,  225,   87,   88,   88,   88,   88,   88,
  226,   88,   88,   88,   88,   75,   88,   88,   88,   88,
   88,  125,  230,   88,  125,   88,   88,  231,   88,   40,
   88,   34,  124,   46,   47,   40,   40,   34,   34,   46,
   47,  232,   40,   44,   34,  244,   45,  294,  133,  133,
   10,   11,   77,  252,   46,   47,  251,  255,   77,  133,
  133,  256,   77,   77,   77,   77,  261,   86,   13,   77,
  269,   77,   77,   86,   77,  148,   77,   86,   86,   86,
   86,    8,  262,  285,   86,    9,   86,   86,   12,   86,
  273,   86,  197,  275,   13,   79,  276,   15,  299,  278,
  279,   79,   30,  287,  289,   79,   79,   79,   79,  292,
  297,  119,   79,   51,   79,   79,   50,   79,   52,   79,
   46,   47,    6,   85,   11,   51,   49,  143,   50,   85,
   52,  148,   43,   85,   85,   85,   85,    8,    9,   46,
   85,    9,   85,   85,   12,   85,   51,   85,  233,   50,
   13,   52,   31,   15,   49,   54,   51,   86,  236,   50,
   49,   52,  286,  202,   49,   49,   49,   49,  203,   80,
  243,   49,  241,   49,   49,   50,   49,   85,   49,   51,
   49,   50,   50,  220,   52,   50,   50,   50,   50,  158,
   46,   47,   50,  179,   50,   50,    7,   50,  187,   50,
   51,   49,    8,   50,    0,   52,    9,   10,   11,   12,
  102,    0,  103,    7,    0,   13,   14,    0,   15,    8,
   16,    0,    0,    9,   10,   11,   12,  100,   99,  101,
    7,    0,   13,   14,    0,   15,    8,   16,    0,    0,
    9,   10,   11,   12,    0,    7,    0,    0,    0,   13,
   14,    8,   15,    0,   16,    9,   10,   11,   12,    0,
  100,   99,  101,    0,   13,   14,   13,   15,  227,   16,
   51,   49,   13,   50,    0,   52,   13,   13,   13,   13,
    0,  148,    0,   53,    0,   13,   13,    8,   13,    0,
   13,    9,    0,    0,   12,    0,  148,    0,  284,    0,
   13,   30,    8,   15,    0,    0,    9,   30,   30,   12,
   53,    0,   53,  298,   30,   13,    0,   56,   15,   53,
   53,    0,   53,   53,    0,    0,    0,  143,   46,   47,
    0,    0,    0,   53,    0,    0,   53,    0,    0,    0,
   46,   47,   53,    0,   76,    0,   79,    0,    0,   53,
    0,   31,    0,   87,   53,   53,   53,   31,   31,    0,
  145,   46,   47,    0,   31,    0,   53,  111,    0,    0,
  116,   46,   47,   53,   53,    0,  121,   53,   53,  152,
    0,  114,  115,  127,    0,    0,    0,    0,   56,  136,
  137,    0,    0,    0,   46,   47,  128,    0,    0,    0,
  142,    0,    0,    0,   53,    0,    0,  270,    0,    0,
  274,   53,   53,  277,    0,   46,   47,    0,   53,    0,
    0,    0,    0,   95,  199,    0,   96,   97,    0,   98,
  288,  162,    0,  290,  291,    0,    0,  293,   56,    0,
    0,  171,    0,    0,    0,    0,  191,    0,   53,    0,
  300,    0,  196,  301,    0,    0,  185,   53,   53,   96,
   97,    0,   98,    0,    0,  148,    0,    0,    0,    0,
    0,    8,    0,    0,  235,    9,    0,  152,   12,    0,
    0,  150,  127,  204,   13,   46,   47,   15,    0,    0,
    0,    0,    6,    7,    0,  128,    0,   53,   53,    8,
    0,    0,   53,    9,   10,   11,   12,    0,  259,   34,
    0,    0,   13,   14,    0,   15,    0,   16,  267,  148,
  267,    0,    0,  267,    0,    8,  267,    0,    0,    9,
    0,    0,   12,  171,    0,  266,  257,  171,   13,    0,
  199,   15,    0,  267,    6,    7,  267,  267,    0,    0,
  267,    8,    0,    0,    0,    9,   10,   11,   12,    0,
  235,    0,    0,  267,   13,   14,  267,   15,    7,   16,
    0,    0,    0,  259,    8,    0,    0,    0,    9,   10,
   11,   12,    0,    7,    0,  117,    0,   13,   14,    8,
   15,    0,   16,    9,   10,   11,   12,    0,    7,    0,
  126,    0,   13,   14,    8,   15,    0,   16,    9,   10,
   11,   12,    0,    7,    0,  174,    0,   13,   14,    8,
   15,    0,   16,    9,   10,   11,   12,    0,    7,    0,
  302,    0,   13,   14,    8,   15,  148,   16,    9,   10,
   11,   12,    8,    0,    0,    0,    9,   13,   14,   12,
   15,  148,   16,  258,    0,   13,    0,    8,   15,    0,
    0,    9,    0,    0,   12,    0,    0,    0,  304,    0,
   13,    0,    0,   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   45,   59,    5,   41,   42,   43,   44,   45,   62,   47,
   59,   41,   40,   59,   43,   62,   45,   60,   43,   40,
   45,   59,   60,   61,   62,   41,   41,   51,   52,   44,
  257,   34,   49,   88,   41,  256,   43,   44,   45,  186,
  187,   40,   44,   41,  203,   60,   61,   62,   43,   40,
   45,  278,   59,   60,   61,   62,  179,  242,   41,  182,
  281,   59,   45,   66,   41,   42,   43,   40,   45,   40,
   47,   41,   75,   43,   44,   45,   60,  236,  274,  226,
  227,    5,  106,  107,  139,  102,  103,  125,  257,   59,
   60,   61,   62,   41,   41,  123,  125,  261,  283,  222,
  125,   41,  123,  281,   41,  278,   43,   44,   45,   41,
   34,   59,  257,   60,   61,   62,  294,  272,  125,   59,
  275,  124,   59,   60,   61,   62,  257,   59,  273,  257,
   41,   41,   43,   44,   45,  268,  269,  268,  269,   42,
  268,  269,   59,   41,   47,   43,  257,   45,   59,   60,
   61,   62,   41,  258,   41,  125,   40,  271,   42,   43,
   41,   45,  274,   47,   42,   43,  261,   45,   41,   47,
   59,   44,   59,   42,   43,   59,   45,   40,   47,   42,
   43,   41,   45,  257,   47,  264,  265,   41,  125,  263,
   44,   44,   41,  267,   59,   44,  270,   41,  272,  273,
  258,  275,  276,  257,   41,  279,  264,  265,   40,   59,
  257,  256,  257,  258,  125,  264,  265,   59,  256,  257,
  258,  259,  260,  266,  262,  263,  264,  265,  258,  267,
  268,  269,  270,  271,  264,  265,  274,   59,  276,  277,
  261,  279,  258,  281,  259,  260,   62,  262,   45,  256,
  257,  258,  259,  260,  256,  262,  263,  264,  265,  262,
  267,  268,  269,  270,  271,   59,  257,  274,   41,  276,
  277,   44,  279,   59,  281,  258,  256,  257,  281,  256,
  257,  258,  257,  257,  268,  269,  256,  257,  258,  259,
  260,  294,  262,  263,  264,  265,   59,  267,  268,  269,
  270,  271,   41,  280,  274,   44,  276,  277,  257,  279,
   59,  281,  259,  260,  258,  262,  275,   59,   45,  256,
  257,  258,  259,  260,  257,  262,  263,  264,  265,  257,
  267,  268,  269,  270,  271,   41,  257,  274,   59,  276,
  277,  257,  279,  258,  281,  256,  257,  258,  259,  260,
   40,  262,  263,  264,  265,   59,  267,  268,  269,  270,
  271,   41,   41,  274,   44,  276,  277,   44,  279,  258,
  281,  258,   59,  257,  258,  264,  265,  264,  265,  257,
  258,  274,  271,   41,  271,   41,   44,   59,  257,  258,
  268,  269,  257,   41,  257,  258,  258,   41,  263,  268,
  269,   41,  267,  268,  269,  270,   59,  257,   59,  274,
   41,  276,  277,  263,  279,  257,  281,  267,  268,  269,
  270,  263,  273,   59,  274,  267,  276,  277,  270,  279,
   41,  281,  274,  258,  276,  257,   41,  279,   59,   41,
   41,  263,   59,   59,   41,  267,  268,  269,  270,   41,
  274,  278,  274,   42,  276,  277,   45,  279,   47,  281,
  257,  258,    0,  257,    0,   42,   43,   41,   45,  263,
   47,  257,   44,  267,  268,  269,  270,  263,    0,   44,
  274,  267,  276,  277,  270,  279,   42,  281,  274,   45,
  276,   47,   59,  279,  257,   12,   42,   48,  203,   45,
  263,   47,  266,  153,  267,  268,  269,  270,  153,   42,
  219,  274,  215,  276,  277,  257,  279,   40,  281,   42,
   43,  263,   45,  177,   47,  267,  268,  269,  270,  256,
  257,  258,  274,  133,  276,  277,  257,  279,   40,  281,
   42,   43,  263,   45,   -1,   47,  267,  268,  269,  270,
   43,   -1,   45,  257,   -1,  276,  277,   -1,  279,  263,
  281,   -1,   -1,  267,  268,  269,  270,   60,   61,   62,
  257,   -1,  276,  277,   -1,  279,  263,  281,   -1,   -1,
  267,  268,  269,  270,   -1,  257,   -1,   -1,   -1,  276,
  277,  263,  279,   -1,  281,  267,  268,  269,  270,   -1,
   60,   61,   62,   -1,  276,  277,  257,  279,   40,  281,
   42,   43,  263,   45,   -1,   47,  267,  268,  269,  270,
   -1,  257,   -1,   12,   -1,  276,  277,  263,  279,   -1,
  281,  267,   -1,   -1,  270,   -1,  257,   -1,  274,   -1,
  276,  258,  263,  279,   -1,   -1,  267,  264,  265,  270,
   39,   -1,   41,  274,  271,  276,   -1,   12,  279,   48,
   49,   -1,   51,   52,   -1,   -1,   -1,  256,  257,  258,
   -1,   -1,   -1,   62,   -1,   -1,   65,   -1,   -1,   -1,
  257,  258,   71,   -1,   39,   -1,   41,   -1,   -1,   78,
   -1,  258,   -1,   48,   83,   84,   85,  264,  265,   -1,
  256,  257,  258,   -1,  271,   -1,   95,   62,   -1,   -1,
   65,  257,  258,  102,  103,   -1,   71,  106,  107,  105,
   -1,   63,   64,   78,   -1,   -1,   -1,   -1,   83,   84,
   85,   -1,   -1,   -1,  257,  258,   78,   -1,   -1,   -1,
   95,   -1,   -1,   -1,  133,   -1,   -1,  246,   -1,   -1,
  249,  140,  141,  252,   -1,  257,  258,   -1,  147,   -1,
   -1,   -1,   -1,  256,  150,   -1,  259,  260,   -1,  262,
  269,  113,   -1,  272,  273,   -1,   -1,  276,  133,   -1,
   -1,  123,   -1,   -1,   -1,   -1,  141,   -1,  177,   -1,
  289,   -1,  147,  292,   -1,   -1,  256,  186,  187,  259,
  260,   -1,  262,   -1,   -1,  257,   -1,   -1,   -1,   -1,
   -1,  263,   -1,   -1,  200,  267,   -1,  203,  270,   -1,
   -1,  273,  177,  275,  276,  257,  258,  279,   -1,   -1,
   -1,   -1,  256,  257,   -1,  177,   -1,  226,  227,  263,
   -1,   -1,  231,  267,  268,  269,  270,   -1,  234,  273,
   -1,   -1,  276,  277,   -1,  279,   -1,  281,  244,  257,
  246,   -1,   -1,  249,   -1,  263,  252,   -1,   -1,  267,
   -1,   -1,  270,  215,   -1,  273,  231,  219,  276,   -1,
  266,  279,   -1,  269,  256,  257,  272,  273,   -1,   -1,
  276,  263,   -1,   -1,   -1,  267,  268,  269,  270,   -1,
  286,   -1,   -1,  289,  276,  277,  292,  279,  257,  281,
   -1,   -1,   -1,  299,  263,   -1,   -1,   -1,  267,  268,
  269,  270,   -1,  257,   -1,  274,   -1,  276,  277,  263,
  279,   -1,  281,  267,  268,  269,  270,   -1,  257,   -1,
  274,   -1,  276,  277,  263,  279,   -1,  281,  267,  268,
  269,  270,   -1,  257,   -1,  274,   -1,  276,  277,  263,
  279,   -1,  281,  267,  268,  269,  270,   -1,  257,   -1,
  274,   -1,  276,  277,  263,  279,  257,  281,  267,  268,
  269,  270,  263,   -1,   -1,   -1,  267,  276,  277,  270,
  279,  257,  281,  274,   -1,  276,   -1,  263,  279,   -1,
   -1,  267,   -1,   -1,  270,   -1,   -1,   -1,  274,   -1,
  276,   -1,   -1,  279,
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
"sentec_eject : retorno",
"sentec_eject : goto",
"condicion : '(' condicion_2 ')'",
"condicion : condicion_2",
"condicion : '(' condicion_2",
"condicion : condicion_2 ')'",
"$$2 :",
"condicion_2 : expresion_matematica comparador $$2 expresion_matematica",
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
"$$3 :",
"declaracion_var : ID $$3 lista_variables",
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
"$$4 :",
"$$5 :",
"declaracion_fun : tipo_fun FUN ID '(' $$4 lista_parametro ')' $$5 cuerpo_funcion_p",
"$$6 :",
"declaracion_fun : tipo_fun FUN '(' lista_parametro ')' $$6 cuerpo_funcion_p",
"$$7 :",
"declaracion_fun : tipo_fun FUN ID '(' ')' $$7 cuerpo_funcion_p",
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
"$$8 :",
"invoc_fun : ID '(' $$8 lista_parametro_real ')'",
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

//#line 488 "gramatica.y"
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

    if (args.length < 1) {
        System.out.println("Por favor, proporciona el nombre de la prueba como argumento.");
        return;
    }

    // Tomamos el primer argumento como el valor de prueba
    String prueba = args[0];
    //String prueba = "pruebaCodigoSemantica";
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
	
        String filePath = Paths.get(System.getProperty("user.dir"))
                .getParent() 
                .resolve("wat2wasm")
                .resolve("bin")
                .resolve("assembly" + ".wat")
                .toString();

        // Ruta de salida para el archivo .wasm
        String outputFilePath = filePath.replace(".wat", ".wasm");

        // Comando para ejecutar wat2wasm
        String comando = "wat2wasm \"" + filePath + "\" -o \"" + outputFilePath + "\"";

        try {
            // Construcción del proceso
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("cmd.exe", "/c", comando);

            // Iniciar el proceso
            Process process = processBuilder.start();

            // Esperar a que termine el proceso
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Conversión a wasm exitosa: " + outputFilePath);
            } else {
                System.err.println("Error al convertir el archivo WAT a WASM.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    System.out.println(ErrorHandler.erroresTraduccion());
    
    if (valido == 0) {
        System.out.println("Se analizo todo el codigo fuente");
    } else {
        System.out.println("No se analizo completamente el codigo fuente, debido a uno o mas errores inesperados");
    }
}
//#line 923 "Parser.java"
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
				if(gc.esTercetoTripla(val_peek(1).sval, this.ts)){gc.addTerceto("FINLADOIZQ", "-", "-");}
				
				}
break;
case 34:
//#line 78 "gramatica.y"
{ 
				String op1 = gc.checkDeclaracion(val_peek(3).sval, lex.getLineaInicial(), this.ts, this.ambitoActual);
				String op2 = gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual);
				if(op1 != null && op2 != null){
					yyval.sval = gc.addTerceto(val_peek(2).sval, op1, op2);
					gc.checkTipo(gc.getPosActual(), lex.getLineaInicial(), this.ts, this.ambitoActual, val_peek(2).sval);
				}else {
					yyval.sval = null;
				}
			 }
break;
case 35:
//#line 88 "gramatica.y"
{ if(gc.getTerceto(gc.getPosActual()).getOp2().isEmpty()){ErrorHandler.addErrorSemantico("La longitud de los patrones a matchear es distinta.", lex.getLineaInicial());} else { yyval.sval = gc.updateCompAndGenerate(this.inicioPatron, val_peek(3).sval, this.cantPatronIzq, this.cantPatronDer, lex.getLineaInicial());} this.inicioPatron = Integer.MAX_VALUE; yyval.sval = "[" + this.gc.getPosActual() + "]";}
break;
case 36:
//#line 90 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón y el que abre la segunda", lex.getLineaInicial());}
break;
case 37:
//#line 91 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que abre la segunda lista del patrón", lex.getLineaInicial());}
break;
case 38:
//#line 92 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón", lex.getLineaInicial());}
break;
case 39:
//#line 93 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta comparador entre los patrones", lex.getLineaInicial());}
break;
case 40:
//#line 94 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta comparador entre las expresiones", lex.getLineaInicial());}
break;
case 41:
//#line 97 "gramatica.y"
{ this.iniciarPatron(); this.cantPatronIzq++; yyval.sval = gc.addTerceto("COMP", gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
break;
case 42:
//#line 100 "gramatica.y"
{ 
	if(gc.esTercetoTripla(val_peek(0).sval, this.ts)){ErrorHandler.addErrorSemantico("No se permite utilizar operaciones entre triplas para un pattern matching.", lex.getLineaInicial());}

this.iniciarPatron(); this.cantPatronIzq++;yyval.sval = gc.addTerceto("COMP", gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
break;
case 43:
//#line 104 "gramatica.y"
{
		if(gc.esTercetoTripla(val_peek(0).sval, this.ts)){ErrorHandler.addErrorSemantico("No se permite utilizar operaciones entre triplas para un pattern matching.", lex.getLineaInicial());}
				
		
		gc.addTerceto("PATRON", "-", "-");
		this.iniciarPatron(); this.cantPatronIzq=1; yyval.sval = gc.addTerceto("COMP", gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
break;
case 44:
//#line 111 "gramatica.y"
{ 
		if(gc.esTercetoTripla(val_peek(0).sval, this.ts)){ErrorHandler.addErrorSemantico("No se permite utilizar operaciones entre triplas para un pattern matching.", lex.getLineaInicial());}

this.cantPatronDer++; posPatron = gc.updateAndCheckSize(this.posPatron, gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), lex.getLineaInicial(), this.ts, this.ambitoActual); this.posPatron++;}
break;
case 45:
//#line 117 "gramatica.y"
{ this.cantPatronDer++;posPatron = gc.updateAndCheckSize(this.posPatron, gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), lex.getLineaInicial(), this.ts, this.ambitoActual); this.posPatron++;}
break;
case 46:
//#line 118 "gramatica.y"
{ 
		if(gc.esTercetoTripla(val_peek(0).sval, this.ts)){ErrorHandler.addErrorSemantico("No se permite utilizar operaciones entre triplas para un pattern matching.", lex.getLineaInicial());}
this.cantPatronDer = 1; posPatron = gc.updateAndCheckSize(this.posPatron, gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), lex.getLineaInicial(), this.ts, this.ambitoActual); this.posPatron++;}
break;
case 47:
//#line 123 "gramatica.y"
{estructurasSintacticas("Se definió una sentencia de control sin else, en la linea: " + lex.getLineaInicial());}
break;
case 49:
//#line 128 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta END_IF con ELSE", lex.getLineaInicial());}
break;
case 50:
//#line 129 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta END_IF", lex.getLineaInicial());}
break;
case 51:
//#line 130 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control del then", lex.getLineaInicial());}
break;
case 52:
//#line 131 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control del ELSE", lex.getLineaInicial());}
break;
case 53:
//#line 132 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control tanto en THEN como ELSE", lex.getLineaInicial());}
break;
case 54:
//#line 137 "gramatica.y"
{
			gc.actualizarBF(gc.getCantTercetos()); 
			gc.pop();
			gc.addTerceto("Label"+gc.getCantTercetos(), "FIN_IF_SOLO", "-");
			}
break;
case 55:
//#line 144 "gramatica.y"
{
			gc.addTerceto("inicioif", "-", "-");
			gc.addTerceto("BF", val_peek(0).sval, ""); 
			gc.push(gc.getPosActual());
		}
break;
case 56:
//#line 150 "gramatica.y"
{ 
			gc.addTerceto("BI", "", "-"); 
			int posSig = gc.getCantTercetos();
			gc.actualizarBF(posSig); 
			gc.pop(); 
			gc.push(gc.getPosActual());
			this.gc.addTerceto("Label" + posSig, "else", "-");
		}
break;
case 57:
//#line 159 "gramatica.y"
{
			int posSig = gc.getCantTercetos();
			gc.actualizarBI(posSig);
			this.gc.addTerceto("Label" + posSig, "endif", "-");
			gc.pop();
			estructurasSintacticas("Se definió una sentencia de control con else, en la linea: " + lex.getLineaInicial());
		}
break;
case 58:
//#line 169 "gramatica.y"
{yyval.sval = ">=";}
break;
case 59:
//#line 170 "gramatica.y"
{yyval.sval = "<=";}
break;
case 60:
//#line 171 "gramatica.y"
{yyval.sval = "!=";}
break;
case 61:
//#line 172 "gramatica.y"
{yyval.sval = "=";}
break;
case 62:
//#line 173 "gramatica.y"
{yyval.sval = "<";}
break;
case 63:
//#line 174 "gramatica.y"
{yyval.sval = ">";}
break;
case 66:
//#line 180 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
break;
case 67:
//#line 181 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
break;
case 68:
//#line 182 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 71:
//#line 188 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
break;
case 72:
//#line 189 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
break;
case 73:
//#line 190 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 76:
//#line 196 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 77:
//#line 200 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 78:
//#line 202 "gramatica.y"
{tipoVar = val_peek(0).sval;}
break;
case 79:
//#line 202 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 80:
//#line 205 "gramatica.y"
{checkRedeclaracion(val_peek(0).sval);}
break;
case 81:
//#line 206 "gramatica.y"
{checkRedeclaracion(val_peek(0).sval);}
break;
case 82:
//#line 207 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta coma en la lista de variables, puede haber parado la compilacion en este punto", lex.getLineaInicial());}
break;
case 83:
//#line 211 "gramatica.y"
{yyval.sval = "double";}
break;
case 84:
//#line 212 "gramatica.y"
{yyval.sval = "ulongint";}
break;
case 85:
//#line 215 "gramatica.y"
{estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());
						yyval.sval = gc.checkTipoAsignacion(val_peek(2).sval, lex.getLineaInicial(), val_peek(0).sval, this.ts,ambitoActual);}
break;
case 86:
//#line 218 "gramatica.y"
{  estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());
					yyval.sval = gc.checkTipoAsignacion(val_peek(2).sval, lex.getLineaInicial(), val_peek(0).sval, this.ts,ambitoActual);
		}
break;
case 87:
//#line 223 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "+",ambitoActual);}
break;
case 88:
//#line 225 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "-",ambitoActual);}
break;
case 89:
//#line 227 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 90:
//#line 230 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 91:
//#line 231 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");}
break;
case 92:
//#line 233 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
break;
case 93:
//#line 235 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");}
break;
case 94:
//#line 237 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
break;
case 95:
//#line 242 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "*",ambitoActual);}
break;
case 96:
//#line 243 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "/",ambitoActual);}
break;
case 97:
//#line 244 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 98:
//#line 246 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 99:
//#line 247 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 100:
//#line 248 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 101:
//#line 250 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 102:
//#line 252 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
break;
case 103:
//#line 254 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
break;
case 104:
//#line 258 "gramatica.y"
{  gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts,ambitoActual);}
break;
case 108:
//#line 264 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 109:
//#line 265 "gramatica.y"
{	if (ts.esUlongInt(val_peek(0).sval)){
					ErrorHandler.addErrorSintactico("se utilizo un Ulongint negativo, son solo positivos", lex.getLineaInicial());
				}
				else {
					ts.convertirNegativo(val_peek(0).sval);
					yyval.sval = "-" + val_peek(0).sval;
				}
				
			}
break;
case 110:
//#line 276 "gramatica.y"
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
case 111:
//#line 290 "gramatica.y"
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
case 112:
//#line 302 "gramatica.y"
{ if (esEmbebido(val_peek(1).sval)){ErrorHandler.addErrorSemantico("No se puede declarar una funcion con un ID con tipos embebidos.", lex.getLineaInicial());}
					else {
					this.checkRedFuncion(val_peek(1).sval, val_peek(3).sval);
					this.ambitoActual += ":" + val_peek(1).sval;
					}
					}
break;
case 113:
//#line 307 "gramatica.y"
{ 
								this.cantRetornos.add(0); 
								this.gc_funciones.push(this.ts.getGCFuncion(this.ambitoActual)); 
								this.gc = this.gc_funciones.peek(); 
								this.tags.add(new ControlTagAmbito());
								}
break;
case 114:
//#line 312 "gramatica.y"
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
case 115:
//#line 322 "gramatica.y"
{ this.cantRetornos.add(0);}
break;
case 116:
//#line 322 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta nombre de la funcion declarada", lex.getLineaInicial());
													this.checkRet("");
													}
break;
case 117:
//#line 325 "gramatica.y"
{ this.cantRetornos.add(0);}
break;
case 118:
//#line 325 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el parametro en la declaracion de la funcion", lex.getLineaInicial());
							    this.checkRet(val_peek(4).sval);
							    if (esEmbebido(val_peek(4).sval)){ErrorHandler.addErrorSemantico("No se puede declarar una funcion con un ID con tipos embebidos.", lex.getLineaInicial());}
							   }
break;
case 120:
//#line 332 "gramatica.y"
{ErrorHandler.addErrorSintactico("No se permite utilizar un tipo definido por el usuario como retorno", lex.getLineaInicial());}
break;
case 121:
//#line 335 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Se declaró más de un parametro", lex.getLineaInicial());}
break;
case 123:
//#line 340 "gramatica.y"
{ 
			this.ts.addClave(this.ambitoActual + ":" + val_peek(0).sval);
			String id_param = gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual);
			this.ts.addAtributo(id_param,AccionSemantica.TIPO, val_peek(1).sval); this.ts.addAtributo(id_param,AccionSemantica.USO,"nombre parametro"); this.ts.addAtributo(gc.checkDeclaracion(this.ambitoActual, lex.getLineaInicial(),this.ts, this.ambitoActual), AccionSemantica.PARAMETRO, id_param); 
			this.ts.addAtributo(id_param, AccionSemantica.TIPO, val_peek(1).sval); estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " +			lex.getLineaInicial());}
break;
case 124:
//#line 345 "gramatica.y"
{ ErrorHandler.addErrorSintactico("No se permite una tripla como parametro", lex.getLineaInicial());}
break;
case 125:
//#line 347 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el nombre del parametro", lex.getLineaInicial());}
break;
case 126:
//#line 348 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el tipo o se intento utilizar una tripla sin nombre.", lex.getLineaInicial());}
break;
case 130:
//#line 358 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 132:
//#line 364 "gramatica.y"
{ this.cantRetornos.set(this.cantRetornos.size()-1, this.cantRetornos.get(this.cantRetornos.size()-1) + 1); 
					String expresion = gc.checkDeclaracion(val_peek(1).sval, lex.getLineaInicial(), this.ts, this.ambitoActual);
					gc.checkTipoRetorno(expresion, ambitoActual,this.ts,lex.getLineaInicial());
					yyval.sval = gc.addTerceto("RET", expresion, "");		
		}
break;
case 133:
//#line 371 "gramatica.y"
{idFuncion = gc.checkDeclaracion(val_peek(1).sval,lex.getLineaInicial(),this.ts,ambitoActual);}
break;
case 134:
//#line 371 "gramatica.y"
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
case 135:
//#line 385 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de parámetros en la invocación a la función", lex.getLineaInicial());}
break;
case 136:
//#line 389 "gramatica.y"
{ yyval.sval = val_peek(0).sval;ErrorHandler.addErrorSintactico("Se utilizó más de un parámetro para invocar a la función", lex.getLineaInicial());}
break;
case 137:
//#line 390 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 138:
//#line 393 "gramatica.y"
{yyval.sval = val_peek(0).sval; gc.checkParamReal(val_peek(0).sval, lex.getLineaInicial(), this.ts, ambitoActual,idFuncion);}
break;
case 139:
//#line 394 "gramatica.y"
{
if(!this.ts.getAtributo(gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), AccionSemantica.TIPO).equals(val_peek(1).sval)){yyval.sval = gc.addTerceto("TO".concat(val_peek(1).sval), gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");} else { ErrorHandler.addWarningSemantico("Se intenta realizar una conversion innecesaria. No se realizará.", lex.getLineaInicial()); yyval.sval = val_peek(0).sval; gc.checkParamReal(val_peek(0).sval, lex.getLineaInicial(), this.ts, ambitoActual,idFuncion);}

if(!this.ts.getAtributo(this.ts.getAtributo(idFuncion, AccionSemantica.PARAMETRO), AccionSemantica.TIPO).equals(val_peek(1).sval)){ ErrorHandler.addErrorSemantico("El tipo del parametro real no coincide con el tipo del parametro formal.", lex.getLineaInicial());}}
break;
case 140:
//#line 400 "gramatica.y"
{yyval.sval = gc.addTerceto("OUTF", val_peek(1).sval, "");}
break;
case 141:
//#line 402 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el mensaje del OUTF", lex.getLineaInicial());}
break;
case 142:
//#line 403 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Parámetro invalido del OUTF", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 143:
//#line 407 "gramatica.y"
{ yyval.sval = gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual);}
break;
case 144:
//#line 408 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 145:
//#line 411 "gramatica.y"
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
case 146:
//#line 430 "gramatica.y"
{ ErrorHandler.addErrorSintactico("No se puede utilizar una constante negativa, en su lugar se debe utilizar el avance descendiente DOWN.", lex.getLineaInicial());}
break;
case 147:
//#line 431 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 148:
//#line 432 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma entre asignacion y condicion", lex.getLineaInicial());}
break;
case 149:
//#line 433 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Faltan todos los punto y coma del for", lex.getLineaInicial());}
break;
case 150:
//#line 434 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta UP/DOWN", lex.getLineaInicial());}
break;
case 151:
//#line 435 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN", lex.getLineaInicial());}
break;
case 152:
//#line 436 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 153:
//#line 437 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 154:
//#line 438 "gramatica.y"
{ { ErrorHandler.addErrorSintactico("Falta UP/DOWN, su valor, y punto y coma entre condicion y avance", lex.getLineaInicial());}}
break;
case 155:
//#line 442 "gramatica.y"
{ yyval.sval = val_peek(0).sval;
				gc.invertirCondicion(val_peek(0).sval);
				gc.addTerceto("BF", val_peek(0).sval, "");
				gc.push(gc.getPosActual());
			}
break;
case 156:
//#line 449 "gramatica.y"
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
case 157:
//#line 464 "gramatica.y"
{yyval.ival = 1;}
break;
case 158:
//#line 465 "gramatica.y"
{yyval.ival = -1;}
break;
case 159:
//#line 468 "gramatica.y"
{ yyval.sval = gc.addTerceto("GOTO", ambitoActual + ":" + val_peek(0).sval,"");
			     this.ts.addAtributo(val_peek(0).sval,AccionSemantica.USO,"nombre etiqueta");
			     this.tags.get(tags.size()-1).huboGoto(this.ambitoActual+":"+val_peek(0).sval);
			}
break;
case 160:
//#line 473 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta la etiqueta en el GOTO, en caso de faltar también el punto y coma es posible que no compile el resto del programa o lo haga mal.", lex.getLineaInicial());
				lex.setErrorHandlerToken(";");}
break;
case 161:
//#line 479 "gramatica.y"
{this.ts.addClave(val_peek(0).sval); this.ts.addAtributo(val_peek(0).sval,AccionSemantica.USO,"nombre de tipo tripla"); estructurasSintacticas("Se declaró un tipo TRIPLE con el ID: " + val_peek(0).sval + " en la linea:" + lex.getLineaInicial()); this.ts.addAtributo(val_peek(0).sval, "tipotripla", val_peek(2).sval); this.ts.addAtributo(val_peek(0).sval, "tipo", val_peek(2).sval);}
break;
case 162:
//#line 481 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta < en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 163:
//#line 482 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta > en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 164:
//#line 483 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta > y < en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 165:
//#line 484 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta la palabra clave TRIPLE", lex.getLineaInicial());}
break;
case 166:
//#line 485 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el ID de la tripla definida.", lex.getLineaInicial());}
break;
//#line 1799 "Parser.java"
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
