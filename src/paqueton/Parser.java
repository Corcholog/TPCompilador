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
   36,   37,   37,   37,   37,   38,   38,   35,   41,   43,
    9,   44,    9,   45,    9,   39,   39,   40,   40,   46,
   46,   46,   46,   42,   47,   47,   47,   48,   48,   49,
   51,   12,   12,   50,   50,   52,   52,   14,   14,   14,
   53,   53,   15,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   55,   54,   56,   56,   16,   16,   10,   10,
   10,   10,   10,   10,
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
    4,    1,    1,    1,    1,    1,    2,    4,    0,    0,
    9,    0,    7,    0,    7,    1,    1,    3,    1,    2,
    2,    1,    1,    4,    3,    1,    2,    1,    1,    4,
    0,    5,    3,    3,    1,    1,    2,    4,    3,    4,
    1,    1,   10,   10,    9,    9,    8,    9,    9,    8,
    8,    8,    1,    3,    1,    1,    2,    3,    6,    5,
    5,    4,    5,    7,
};
final static short yydefred[] = {                         0,
    1,    0,    0,    3,    0,    0,    0,    0,    0,   82,
   81,    0,    0,    0,   20,    0,    0,   16,   17,   18,
   19,   21,   22,   23,   24,   25,   26,   27,    0,    0,
    0,    0,    2,    0,   13,   14,    0,    0,    0,    0,
    0,  157,    0,    0,  106,    0,    0,    0,    0,    0,
  104,   53,    0,    0,    0,  105,    0,   95,  103,    0,
    0,    0,    0,   15,    5,   79,    0,    0,    0,    0,
    0,    0,    0,  133,    0,    0,    0,  158,    0,    0,
    0,    0,    0,    0,    0,    0,  107,   96,   97,   31,
    0,   56,   57,   58,   59,   60,   61,    0,    0,    0,
    0,    0,    0,    0,  142,  139,    0,    0,    0,    0,
    0,    4,   12,    0,    0,    0,    0,    0,    0,    8,
   10,    0,    0,    0,  135,  108,    0,    0,  153,    0,
    0,   28,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   54,    0,   52,    0,    0,   49,    0,    0,
   93,    0,   94,  140,  138,    0,  162,    0,    0,   80,
   78,    0,    0,    0,    0,  119,    7,  137,  132,    0,
  154,    0,  155,  156,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   90,   89,   92,   91,   65,    0,   73,
    0,   63,   45,    0,   55,   51,  100,   99,  101,   98,
  161,    0,  160,  163,  114,    0,  121,  120,  112,    0,
  134,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   34,    0,   64,   66,    0,   74,    0,   50,    0,  159,
    0,    0,    0,  118,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   35,   36,    0,   62,   72,
   46,  164,    0,  115,  110,  113,    0,    0,  150,    0,
  152,    0,    0,    0,  151,    0,    0,  147,   37,   33,
    0,  129,    0,  126,  128,    0,   70,    0,    0,   68,
  148,    0,  149,  145,    0,  146,    0,    0,  127,  111,
   69,   71,    0,  143,  144,    0,  124,  125,   67,  130,
};
final static short yydgoto[] = {                          3,
   33,    5,    4,   16,   64,   18,   19,   20,   21,   22,
   23,   24,   25,   26,   27,   28,  129,   53,  180,  100,
   84,  181,   85,  182,   55,  147,  148,  149,  196,  191,
  259,   29,   67,   40,   30,   57,   58,   59,   31,  165,
  206,  254,  276,  233,  231,  166,  273,  274,  275,  124,
   75,  125,  108,   80,  130,  176,
};
final static short yysindex[] = {                      -230,
    0,  607,    0,    0,  163,  -25,  -22, -235,  -20,    0,
    0,   11,    7,  -41,    0,  384, -200,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -175, -136,
 -147,  607,    0,  399,    0,    0,  573,  105,  573, -175,
  110,    0,  -74,   -7,    0,  406,   24,  -69,   88,   88,
    0,    0,  165,  582,  -29,    0,  144,    0,    0,   62,
   16,  -70,  638,    0,    0,    0,    1,  573,  -39,  416,
  -15,  653,  118,    0,  100,   79,    1,    0,   22,  546,
  573,  220,  582,  119,  243,  144,    0,    0,    0,    0,
  573,    0,    0,    0,    0,    0,    0,   55,  681,  573,
  708,   36,   66,  254,    0,    0,  118,  256,  -70,  -60,
  239,    0,    0,   45,   46,  118,  281, -101,  678,    0,
    0,  118,   75,   71,    0,    0,   68,   11,    0,   40,
  118,    0,  538,  510,  573,  118,  -19,  144,   -4,  144,
  118,  -22,    0,  448,    0,  271,  -90,    0,   60,   27,
    0,   98,    0,    0,    0,  -52,    0,   86,   94,    0,
    0,  300,  104,  120,  159,    0,    0,    0,    0,  100,
    0,  313,    0,    0,    2,  124,  312,  567,  573,  118,
  342,  340,  118,    0,    0,    0,    0,    0,  111,    0,
  462,    0,    0,  719,    0,    0,    0,    0,    0,    0,
    0,   19,    0,    0,    0, -101,    0,    0,    0, -101,
    0,  346,  -28,   -9,  136,  347,  573,  573,  358,  365,
    0,  573,    0,    0,  730,    0,   60,    0,  349,    0,
  150,  209,  150,    0,  742,  380,  742,   -6,  393,  742,
  177,  396,  742,  397,  400,    0,    0,  118,    0,    0,
    0,    0,  693,    0,    0,    0,  476,  383,    0,  742,
    0,  404,  742,  742,    0,  409,  742,    0,    0,    0,
  407,    0,  369,    0,    0,  150,    0,  178,  484,    0,
    0,  742,    0,    0,  742,    0,  573,  623,    0,    0,
    0,    0,  750,    0,    0,  170,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0, -229,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  431,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  176,    0,
    0,    0,    0,    0,    0,    0,    0,  106,    0,    0,
    0,    0,    0,  -36,    0,    0,    0,    0,    0,    0,
    0,    0,   91,    0,    0,    0,  133,    0,    0,    0,
    0,    0,  455,    0,    0,    0,  148,    0,    0,    0,
  431,  456,  270,    0,    0,    0,  291,    0,    0,    0,
    0,   95,  413,    0,    0,  203,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  423,    0,    0,    0,
    0,    0,    0,    0,    0,  306,    0,    0,  469,    0,
    0,  266,    0,    0,    0,    0,    0,    0,    0,    0,
  413,    0,    0,    0,    0,   69,    0,  229,    0,  255,
   73,    0,    0,    0,    0,    0,  327,    0,  354,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -67,  295,  326,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  432,
    0,    0,   18,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  332,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,   12,   -2,    0,  586,    0,    0,    0,
    0,   -8,    0,    0,    0,    0,  466,  435,   84,  -68,
    0,  130,    0,    0,    0,  289,  348,  355, -179,  244,
  532,  652,  464,    0,   38,  -35,   85,    0,    0,  303,
    0, -219,    0,    0,    0,  318,    0, -180,    0,    0,
    0,  360,    0,    0,  388,  -17,
};
final static int YYTABLESIZE=1026;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         17,
  118,  158,   35,   51,  102,  102,  102,  102,  102,  202,
  102,   86,  237,  256,  228,  134,   34,   38,   62,   43,
   41,  185,  102,  102,  102,  102,    1,   76,   51,   71,
   51,  240,   38,   36,  263,  241,  187,   51,   51,  184,
   51,   51,    2,   70,  115,   42,   60,  251,  117,   56,
   46,   51,   49,   47,  186,   48,  290,   50,   39,   51,
  113,   40,  138,  140,  178,   49,   51,  198,   48,  113,
   50,   51,   51,   65,   56,  109,   56,   39,   39,   39,
   48,   66,   51,   56,   56,  197,   56,   56,  102,   51,
   51,   51,  289,   51,   51,   54,   49,   56,  175,   48,
   39,   50,  106,   49,   47,   56,   48,  298,   50,   38,
   48,  169,   56,   32,  170,   39,  113,   56,   56,   51,
   73,   98,   76,   99,   68,   51,   51,   38,   56,   83,
   69,   32,   48,   88,   89,   56,   56,   56,  200,   56,
   56,   49,   47,  107,   48,   74,   50,  131,  131,   29,
  131,  116,  131,   30,  214,  163,  199,  215,  122,  133,
   98,   51,   99,   54,  131,   56,   10,   11,   78,   51,
   51,   56,   56,   87,  136,   87,   87,   87,   96,   95,
   97,  143,   79,  141,  145,  102,  151,  153,   87,  109,
  103,   87,   87,   87,   87,  238,  157,   10,   11,  209,
  109,  109,  210,  126,  201,   90,   75,   56,   51,   51,
  300,   54,   98,   51,   99,   56,   56,  117,  183,  102,
  102,  102,  102,  102,   61,  102,  102,  102,  102,  236,
  102,  102,  102,  102,  102,  173,  174,  102,   37,  102,
  102,  101,  102,   88,  102,   88,   88,   88,  239,  255,
  272,  262,  210,  122,   56,   56,  114,   87,  120,   56,
  132,   88,   88,   88,   88,  173,  174,   44,   45,   85,
  272,   85,   85,   85,  229,  230,   39,   39,   51,   39,
   44,   45,  127,   10,   11,  272,  135,   85,   85,   85,
   85,  150,   44,   45,  154,   86,  155,   86,   86,   86,
  159,  160,  161,  173,  174,  248,  136,  219,  220,  136,
  137,   44,   45,   86,   86,   86,   86,  104,   44,   45,
  162,  152,   44,   45,   56,  171,   38,   88,   84,  192,
   32,  168,   38,   38,  195,  123,   32,   32,  123,   38,
  205,  105,  203,   32,   44,   45,  244,  245,   29,   77,
  204,  217,   30,   85,   29,   29,   44,   45,   30,   30,
  207,   29,  131,  131,   83,   30,  122,   10,   11,  122,
  296,  213,   42,  131,  131,   43,  208,   92,   93,   86,
   94,  216,  221,  222,  223,   47,  235,  243,   87,   87,
   87,   87,   87,  242,   87,   87,   87,   87,  246,   87,
   87,   87,   87,   87,   75,  247,   87,  252,   87,   87,
   75,   87,   48,   87,   75,   75,   75,   75,    6,    7,
  260,   75,  253,   75,   75,    8,   75,  288,   75,    9,
   10,   11,   12,  264,  266,   32,  267,  269,   13,   14,
  270,  280,   63,   15,  282,   81,  287,   49,   47,  285,
   48,  291,   50,  116,    6,   11,   41,   72,   88,   88,
   88,   88,   88,  141,   88,   88,   88,   88,    9,   88,
   88,   88,   88,   88,  119,   44,   88,   52,   88,   88,
   82,   88,  227,   88,   85,   85,   85,   85,   85,   13,
   85,   85,   85,   85,  193,   85,   85,   85,   85,   85,
  279,  194,   85,   77,   85,   85,  189,   85,  232,   85,
   86,   86,   86,   86,   86,  172,   86,   86,   86,   86,
  225,   86,   86,   86,   86,   86,   84,  234,   86,  211,
   86,   86,   84,   86,  278,   86,   84,   84,   84,   84,
    0,    0,  293,   84,    0,   84,   84,   77,   84,  179,
   84,   49,   47,   77,   48,    0,   50,   77,   77,   77,
   77,    0,   83,    0,   77,    0,   77,   77,   83,   77,
  212,   77,   83,   83,   83,   83,  173,  174,    0,   83,
    0,   83,   83,   47,   83,   46,   83,   49,   47,   47,
   48,    0,   50,   47,   47,   47,   47,   96,   95,   97,
   47,    0,   47,   47,  128,   47,  218,   47,   49,   47,
   48,   48,    0,   50,   49,   47,   48,   48,    0,   50,
   48,   48,   48,   48,   98,    7,   99,   48,    0,   48,
   48,    8,   48,    0,   48,    9,   10,   11,   12,    0,
    7,   96,   95,   97,   13,   14,    8,  271,    0,   15,
    9,   10,   11,   12,    0,    7,    0,    0,    0,   13,
   14,    8,   44,   45,   15,    9,   10,   11,   12,    0,
    0,    0,    7,    0,   13,   14,    0,    0,    8,   15,
    0,    0,    9,   10,   11,   12,  146,   13,    0,    0,
    0,   13,   14,   13,    0,    0,   15,   13,   13,   13,
   13,    0,    0,    0,  142,    0,   13,   13,    0,    0,
    8,   13,  110,  111,    9,    0,    0,   12,  142,    0,
    0,  188,   49,   13,    8,   48,  123,   50,    9,  190,
    0,   12,  142,    0,    0,  224,    0,   13,    8,    0,
  142,    0,    9,    0,    0,   12,    8,    0,    0,  277,
    9,   13,    0,   12,    0,    0,    0,  292,    0,   13,
  156,    0,    0,    0,    0,    0,   44,   45,  261,  164,
    0,  265,    0,    0,  268,    0,  226,    0,    0,  146,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  281,    0,  177,  283,  284,   92,   93,  286,   94,
    0,    0,   44,   45,    0,    0,    0,    0,    0,    0,
  250,    0,    0,  294,    0,    0,  295,    0,    0,    0,
  258,  123,  258,   44,   45,  258,    0,    0,  258,   44,
   45,    0,    0,    0,    0,    0,    0,   91,    0,    0,
   92,   93,  190,   94,    0,  258,    0,    0,  258,  258,
    0,    0,  258,    0,    0,    0,    0,  164,    0,    0,
    0,  164,    6,    7,  226,    0,    0,  258,    0,    8,
  258,    0,    0,    9,   10,   11,   12,    0,  250,    7,
    0,    0,   13,   14,    0,    8,    0,   15,    0,    9,
   10,   11,   12,    0,    7,    0,  297,    0,   13,   14,
    8,  271,    0,   15,    9,   10,   11,   12,    0,    7,
    0,  112,    0,   13,   14,    8,    0,    0,   15,    9,
   10,   11,   12,    0,    0,    0,  121,    0,   13,   14,
    0,    0,    0,   15,    7,    0,  139,   44,   45,    0,
    8,    0,    0,    0,    9,   10,   11,   12,    0,    7,
    0,  167,    0,   13,   14,    8,    0,    0,   15,    9,
   10,   11,   12,    0,  142,    0,    0,    0,   13,   14,
    8,  271,    0,   15,    9,  142,    0,   12,    0,  143,
  144,    8,  145,   13,    0,    9,  142,    0,   12,    0,
    0,  144,    8,  195,   13,    0,    9,    0,  142,   12,
    0,    0,    0,  249,    8,   13,  142,    0,    9,    0,
    0,   12,    8,    0,  257,    0,    9,   13,    0,   12,
    0,    0,    0,  299,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   40,   62,    5,   12,   41,   42,   43,   44,   45,   62,
   47,   47,   41,  233,  194,   84,    5,   40,   60,   40,
  256,   41,   59,   60,   61,   62,  257,  257,   37,   32,
   39,   41,   40,   59,   41,   45,   41,   46,   47,   59,
   49,   50,  273,   32,   44,  281,   40,  227,  278,   12,
   40,   60,   42,   43,   59,   45,  276,   47,   41,   68,
   63,   44,   98,   99,  133,   42,   75,   41,   45,   72,
   47,   80,   81,  274,   37,   60,   39,   60,   61,   62,
   45,  257,   91,   46,   47,   59,   49,   50,  125,   98,
   99,  100,  273,  102,  103,   12,   42,   60,   59,   45,
  123,   47,   41,   42,   43,   68,   45,  288,   47,   41,
   45,   41,   75,   41,   44,  123,  119,   80,   81,  128,
   37,   43,   39,   45,  261,  134,  135,   59,   91,   46,
  278,   59,   45,   49,   50,   98,   99,  100,   41,  102,
  103,   42,   43,   60,   45,   41,   47,   42,   43,   59,
   45,   68,   47,   59,  172,  257,   59,  175,   75,   41,
   43,  170,   45,   80,   81,  128,  268,  269,   59,  178,
  179,  134,  135,   41,   91,   43,   44,   45,   60,   61,
   62,  272,  257,  100,  275,   42,  102,  103,  258,  257,
   47,   59,   60,   61,   62,  213,  257,  268,  269,   41,
  268,  269,   44,  125,  257,   41,   59,  170,  217,  218,
   41,  128,   43,  222,   45,  178,  179,  257,  135,  256,
  257,  258,  259,  260,  266,  262,  263,  264,  265,  258,
  267,  268,  269,  270,  271,  264,  265,  274,  261,  276,
  277,  271,  279,   41,  281,   43,   44,   45,  258,   41,
  253,  258,   44,  170,  217,  218,  256,  125,  274,  222,
   41,   59,   60,   61,   62,  264,  265,  257,  258,   41,
  273,   43,   44,   45,  256,  257,  259,  260,  287,  262,
  257,  258,  261,  268,  269,  288,   44,   59,   60,   61,
   62,  256,  257,  258,   41,   41,   41,   43,   44,   45,
   62,  257,  257,  264,  265,  222,   41,  178,  179,   44,
  256,  257,  258,   59,   60,   61,   62,  256,  257,  258,
   40,  256,  257,  258,  287,  258,  258,  125,   59,   59,
  258,  257,  264,  265,  275,   41,  264,  265,   44,  271,
   41,  280,  257,  271,  257,  258,  217,  218,  258,   59,
  257,   40,  258,  125,  264,  265,  257,  258,  264,  265,
  257,  271,  257,  258,   59,  271,   41,  268,  269,   44,
  287,   59,   41,  268,  269,   44,  257,  259,  260,  125,
  262,  258,   41,   44,  274,   59,   41,   41,  256,  257,
  258,  259,  260,  258,  262,  263,  264,  265,   41,  267,
  268,  269,  270,  271,  257,   41,  274,   59,  276,  277,
  263,  279,   59,  281,  267,  268,  269,  270,  256,  257,
   41,  274,  273,  276,  277,  263,  279,   59,  281,  267,
  268,  269,  270,   41,  258,  273,   41,   41,  276,  277,
   41,   59,   59,  281,   41,   40,   40,   42,   43,   41,
   45,  274,   47,  278,    0,    0,   44,   59,  256,  257,
  258,  259,  260,   41,  262,  263,  264,  265,    0,  267,
  268,  269,  270,  271,   59,   44,  274,   12,  276,  277,
   46,  279,  194,  281,  256,  257,  258,  259,  260,   59,
  262,  263,  264,  265,  147,  267,  268,  269,  270,  271,
  257,  147,  274,   40,  276,  277,   59,  279,  206,  281,
  256,  257,  258,  259,  260,  128,  262,  263,  264,  265,
   59,  267,  268,  269,  270,  271,  257,  210,  274,  170,
  276,  277,  263,  279,   59,  281,  267,  268,  269,  270,
   -1,   -1,   59,  274,   -1,  276,  277,  257,  279,   40,
  281,   42,   43,  263,   45,   -1,   47,  267,  268,  269,
  270,   -1,  257,   -1,  274,   -1,  276,  277,  263,  279,
  258,  281,  267,  268,  269,  270,  264,  265,   -1,  274,
   -1,  276,  277,  257,  279,   40,  281,   42,   43,  263,
   45,   -1,   47,  267,  268,  269,  270,   60,   61,   62,
  274,   -1,  276,  277,   59,  279,   40,  281,   42,   43,
  257,   45,   -1,   47,   42,   43,  263,   45,   -1,   47,
  267,  268,  269,  270,   43,  257,   45,  274,   -1,  276,
  277,  263,  279,   -1,  281,  267,  268,  269,  270,   -1,
  257,   60,   61,   62,  276,  277,  263,  279,   -1,  281,
  267,  268,  269,  270,   -1,  257,   -1,   -1,   -1,  276,
  277,  263,  257,  258,  281,  267,  268,  269,  270,   -1,
   -1,   -1,  257,   -1,  276,  277,   -1,   -1,  263,  281,
   -1,   -1,  267,  268,  269,  270,  101,  257,   -1,   -1,
   -1,  276,  277,  263,   -1,   -1,  281,  267,  268,  269,
  270,   -1,   -1,   -1,  257,   -1,  276,  277,   -1,   -1,
  263,  281,   61,   62,  267,   -1,   -1,  270,  257,   -1,
   -1,  274,   42,  276,  263,   45,   75,   47,  267,  144,
   -1,  270,  257,   -1,   -1,  274,   -1,  276,  263,   -1,
  257,   -1,  267,   -1,   -1,  270,  263,   -1,   -1,  274,
  267,  276,   -1,  270,   -1,   -1,   -1,  274,   -1,  276,
  109,   -1,   -1,   -1,   -1,   -1,  257,  258,  237,  118,
   -1,  240,   -1,   -1,  243,   -1,  191,   -1,   -1,  194,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  260,   -1,  256,  263,  264,  259,  260,  267,  262,
   -1,   -1,  257,  258,   -1,   -1,   -1,   -1,   -1,   -1,
  225,   -1,   -1,  282,   -1,   -1,  285,   -1,   -1,   -1,
  235,  170,  237,  257,  258,  240,   -1,   -1,  243,  257,
  258,   -1,   -1,   -1,   -1,   -1,   -1,  256,   -1,   -1,
  259,  260,  257,  262,   -1,  260,   -1,   -1,  263,  264,
   -1,   -1,  267,   -1,   -1,   -1,   -1,  206,   -1,   -1,
   -1,  210,  256,  257,  279,   -1,   -1,  282,   -1,  263,
  285,   -1,   -1,  267,  268,  269,  270,   -1,  293,  257,
   -1,   -1,  276,  277,   -1,  263,   -1,  281,   -1,  267,
  268,  269,  270,   -1,  257,   -1,  274,   -1,  276,  277,
  263,  279,   -1,  281,  267,  268,  269,  270,   -1,  257,
   -1,  274,   -1,  276,  277,  263,   -1,   -1,  281,  267,
  268,  269,  270,   -1,   -1,   -1,  274,   -1,  276,  277,
   -1,   -1,   -1,  281,  257,   -1,  256,  257,  258,   -1,
  263,   -1,   -1,   -1,  267,  268,  269,  270,   -1,  257,
   -1,  274,   -1,  276,  277,  263,   -1,   -1,  281,  267,
  268,  269,  270,   -1,  257,   -1,   -1,   -1,  276,  277,
  263,  279,   -1,  281,  267,  257,   -1,  270,   -1,  272,
  273,  263,  275,  276,   -1,  267,  257,   -1,  270,   -1,
   -1,  273,  263,  275,  276,   -1,  267,   -1,  257,  270,
   -1,   -1,   -1,  274,  263,  276,  257,   -1,  267,   -1,
   -1,  270,  263,   -1,  273,   -1,  267,  276,   -1,  270,
   -1,   -1,   -1,  274,   -1,  276,
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
"asignacion : triple ASIGN expresion_matematica",
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

//#line 452 "gramatica.y"
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
//#line 888 "Parser.java"
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
				this.tags.get(tags.size()-1).declaracionTag(etiquetaAmbito);
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
				}}
break;
case 33:
//#line 81 "gramatica.y"
{ if(gc.getTerceto(gc.getPosActual()).getOp2().isEmpty()){ErrorHandler.addErrorSemantico("La longitud de los patrones a matchear es distinta.", lex.getLineaInicial());} else { yyval.sval = gc.updateCompAndGenerate(this.inicioPatron, val_peek(3).sval, this.cantPatronIzq, this.cantPatronDer, lex.getLineaInicial());} this.inicioPatron = Integer.MAX_VALUE; yyval.sval = "[" + this.gc.getPosActual() + "]";}
break;
case 34:
//#line 83 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón y el que abre la segunda", lex.getLineaInicial());}
break;
case 35:
//#line 84 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que abre la segunda lista del patrón", lex.getLineaInicial());}
break;
case 36:
//#line 85 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón", lex.getLineaInicial());}
break;
case 37:
//#line 86 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta comparador entre los patrones", lex.getLineaInicial());}
break;
case 38:
//#line 87 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta comparador entre las expresiones", lex.getLineaInicial());}
break;
case 39:
//#line 90 "gramatica.y"
{ this.iniciarPatron(); this.cantPatronIzq++; yyval.sval = gc.addTerceto("COMP", gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
break;
case 40:
//#line 93 "gramatica.y"
{ this.iniciarPatron(); this.cantPatronIzq++;yyval.sval = gc.addTerceto("COMP", gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
break;
case 41:
//#line 94 "gramatica.y"
{this.iniciarPatron(); this.cantPatronIzq=1; yyval.sval = gc.addTerceto("COMP", gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
break;
case 42:
//#line 96 "gramatica.y"
{ this.cantPatronDer++; posPatron = gc.updateAndCheckSize(this.posPatron, gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), lex.getLineaInicial(), this.ts, this.ambitoActual); this.posPatron++;}
break;
case 43:
//#line 99 "gramatica.y"
{ this.cantPatronDer++;posPatron = gc.updateAndCheckSize(this.posPatron, gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), lex.getLineaInicial(), this.ts, this.ambitoActual); this.posPatron++;}
break;
case 44:
//#line 100 "gramatica.y"
{ this.cantPatronDer = 1; posPatron = gc.updateAndCheckSize(this.posPatron, gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), lex.getLineaInicial(), this.ts, this.ambitoActual); this.posPatron++;}
break;
case 45:
//#line 103 "gramatica.y"
{estructurasSintacticas("Se definió una sentencia de control sin else, en la linea: " + lex.getLineaInicial());}
break;
case 47:
//#line 108 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta END_IF con ELSE", lex.getLineaInicial());}
break;
case 48:
//#line 109 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta END_IF", lex.getLineaInicial());}
break;
case 49:
//#line 110 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control del then", lex.getLineaInicial());}
break;
case 50:
//#line 111 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control del ELSE", lex.getLineaInicial());}
break;
case 51:
//#line 112 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control tanto en THEN como ELSE", lex.getLineaInicial());}
break;
case 52:
//#line 117 "gramatica.y"
{
			gc.actualizarBF(gc.getCantTercetos()); 
			gc.pop();
			gc.addTerceto("Label"+gc.getCantTercetos(), "FIN_IF_SOLO", "-");
			}
break;
case 53:
//#line 124 "gramatica.y"
{
			gc.addTerceto("BF", val_peek(0).sval, ""); 
			gc.push(gc.getPosActual());
		}
break;
case 54:
//#line 129 "gramatica.y"
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
//#line 138 "gramatica.y"
{
			int posSig = gc.getCantTercetos();
			gc.actualizarBI(posSig);
			this.gc.addTerceto("Label" + posSig, "endif", "-");
			gc.pop();
			estructurasSintacticas("Se definió una sentencia de control con else, en la linea: " + lex.getLineaInicial());
		}
break;
case 56:
//#line 148 "gramatica.y"
{yyval.sval = ">=";}
break;
case 57:
//#line 149 "gramatica.y"
{yyval.sval = "<=";}
break;
case 58:
//#line 150 "gramatica.y"
{yyval.sval = "!=";}
break;
case 59:
//#line 151 "gramatica.y"
{yyval.sval = "=";}
break;
case 60:
//#line 152 "gramatica.y"
{yyval.sval = "<";}
break;
case 61:
//#line 153 "gramatica.y"
{yyval.sval = ">";}
break;
case 64:
//#line 159 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
break;
case 65:
//#line 160 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
break;
case 66:
//#line 161 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 69:
//#line 167 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
break;
case 70:
//#line 168 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
break;
case 71:
//#line 169 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 74:
//#line 175 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 75:
//#line 179 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 76:
//#line 181 "gramatica.y"
{tipoVar = val_peek(0).sval;}
break;
case 77:
//#line 181 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 78:
//#line 184 "gramatica.y"
{checkRedeclaracion(val_peek(0).sval);}
break;
case 79:
//#line 185 "gramatica.y"
{checkRedeclaracion(val_peek(0).sval);}
break;
case 80:
//#line 186 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta coma en la lista de variables, puede haber parado la compilacion en este punto", lex.getLineaInicial());}
break;
case 81:
//#line 190 "gramatica.y"
{yyval.sval = "double";}
break;
case 82:
//#line 191 "gramatica.y"
{yyval.sval = "ulongint";}
break;
case 83:
//#line 194 "gramatica.y"
{estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());
						yyval.sval = gc.checkTipoAsignacion(val_peek(2).sval, lex.getLineaInicial(), val_peek(0).sval, this.ts,ambitoActual);}
break;
case 84:
//#line 197 "gramatica.y"
{  estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());
					yyval.sval = gc.checkTipoAsignacion(val_peek(2).sval, lex.getLineaInicial(), val_peek(0).sval, this.ts,ambitoActual);
		}
break;
case 85:
//#line 202 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "+",ambitoActual);}
break;
case 86:
//#line 204 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "-",ambitoActual);}
break;
case 87:
//#line 206 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 88:
//#line 209 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 89:
//#line 210 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");}
break;
case 90:
//#line 212 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
break;
case 91:
//#line 214 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");}
break;
case 92:
//#line 216 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
break;
case 93:
//#line 221 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "*",ambitoActual);}
break;
case 94:
//#line 222 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "/",ambitoActual);}
break;
case 95:
//#line 223 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 96:
//#line 225 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 97:
//#line 226 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 98:
//#line 227 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 99:
//#line 229 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 100:
//#line 231 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
break;
case 101:
//#line 233 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
break;
case 102:
//#line 237 "gramatica.y"
{  gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts,ambitoActual);}
break;
case 106:
//#line 243 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 107:
//#line 244 "gramatica.y"
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
//#line 254 "gramatica.y"
{
						   String tipo = "";
						   String idTripla=gc.checkDeclaracion(val_peek(3).sval,lex.getLineaInicial(),this.ts,ambitoActual);
						   if (idTripla != null) {
							this.gc.addTerceto("<=", val_peek(1).sval, "3");
							this.gc.addTerceto(">=", val_peek(1).sval, "1");
							this.gc.addTerceto("AND", "[" + this.gc.getPosActual() + "]", "[" + (this.gc.getPosActual()-1) + "]");				
						    tipo = this.ts.getAtributo(val_peek(3).sval, AccionSemantica.TIPO_BASICO); 
						    }else{
							ErrorHandler.addErrorSemantico( "La tripla " + val_peek(3).sval + " nunca fue declarada.", lex.getLineaInicial()) ; 
							tipo = "error";
						    }

							yyval.sval = gc.addTerceto("ACCESOTRIPLE", val_peek(3).sval, val_peek(1).sval, tipo);}
break;
case 109:
//#line 270 "gramatica.y"
{ if (esEmbebido(val_peek(1).sval)){ErrorHandler.addErrorSemantico("No se puede declarar una funcion con un ID con tipos embebidos.", lex.getLineaInicial());}
					else {
					this.checkRedFuncion(val_peek(1).sval, val_peek(3).sval);
					this.funcionActual = val_peek(1).sval; 
					this.ambitoActual += ":" + val_peek(1).sval;
					}
					}
break;
case 110:
//#line 276 "gramatica.y"
{ 
								this.cantRetornos.add(0); 
								this.gc_funciones.push(this.ts.getGCFuncion(this.ambitoActual)); 
								this.gc = this.gc_funciones.peek(); 
								this.tags.add(new ControlTagAmbito());
								}
break;
case 111:
//#line 281 "gramatica.y"
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
case 112:
//#line 291 "gramatica.y"
{ this.cantRetornos.add(0);}
break;
case 113:
//#line 291 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta nombre de la funcion declarada", lex.getLineaInicial());
													this.checkRet("");
													}
break;
case 114:
//#line 294 "gramatica.y"
{ this.cantRetornos.add(0);}
break;
case 115:
//#line 294 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el parametro en la declaracion de la funcion", lex.getLineaInicial());
							    this.checkRet(val_peek(4).sval);
							    if (esEmbebido(val_peek(4).sval)){ErrorHandler.addErrorSemantico("No se puede declarar una funcion con un ID con tipos embebidos.", lex.getLineaInicial());}
							   }
break;
case 118:
//#line 304 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Se declaró más de un parametro", lex.getLineaInicial());}
break;
case 120:
//#line 309 "gramatica.y"
{ 
			this.ts.addClave(this.ambitoActual + ":" + val_peek(0).sval);
			String id_param = gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual);
			this.ts.addAtributo(id_param,AccionSemantica.TIPO, val_peek(1).sval); this.ts.addAtributo(id_param,AccionSemantica.USO,"nombre parametro"); this.ts.addAtributo(gc.checkDeclaracion(funcionActual, lex.getLineaInicial(), 				this.ts, this.ambitoActual), AccionSemantica.PARAMETRO, id_param); 
			this.ts.addAtributo(id_param, AccionSemantica.TIPO, val_peek(1).sval); estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " +			lex.getLineaInicial());}
break;
case 121:
//#line 314 "gramatica.y"
{ this.ts.addAtributo(ambitoActual+":"+val_peek(0).sval,AccionSemantica.TIPO, val_peek(1).sval); this.ts.addAtributo(ambitoActual+":"+val_peek(0).sval,AccionSemantica.USO,"nombre parametro"); this.ts.addAtributo(ambitoActual+":"+funcionActual, AccionSemantica.PARAMETRO, val_peek(0).sval); estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 122:
//#line 316 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el nombre del parametro", lex.getLineaInicial());}
break;
case 123:
//#line 317 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el nombre del parametro o el tipo", lex.getLineaInicial());}
break;
case 127:
//#line 327 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 130:
//#line 334 "gramatica.y"
{ this.cantRetornos.set(this.cantRetornos.size()-1, this.cantRetornos.get(this.cantRetornos.size()-1) + 1); 
					yyval.sval = gc.addTerceto("RET", gc.checkDeclaracion(val_peek(1).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");		
		}
break;
case 131:
//#line 339 "gramatica.y"
{funcionActual = val_peek(1).sval; }
break;
case 132:
//#line 339 "gramatica.y"
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
case 133:
//#line 355 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de parámetros en la invocación a la función", lex.getLineaInicial());}
break;
case 134:
//#line 359 "gramatica.y"
{ yyval.sval = val_peek(0).sval;ErrorHandler.addErrorSintactico("Se utilizó más de un parámetro para invocar a la función", lex.getLineaInicial());}
break;
case 135:
//#line 360 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 136:
//#line 363 "gramatica.y"
{yyval.sval = val_peek(0).sval; gc.checkParamReal(val_peek(0).sval, lex.getLineaInicial(), this.ts, funcionActual,ambitoActual);}
break;
case 137:
//#line 364 "gramatica.y"
{
if(!this.ts.getAtributo(gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), AccionSemantica.TIPO).equals(val_peek(1).sval)){yyval.sval = gc.addTerceto("TO".concat(val_peek(1).sval), gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
if(!this.ts.getAtributo(this.ts.getAtributo(gc.checkDeclaracion(funcionActual, lex.getLineaInicial(), this.ts, ambitoActual), AccionSemantica.PARAMETRO), AccionSemantica.TIPO).equals(val_peek(1).sval)){ ErrorHandler.addErrorSemantico("El tipo del parametro real no coincide con el tipo del parametro formal.", lex.getLineaInicial());}}
break;
case 138:
//#line 369 "gramatica.y"
{yyval.sval = gc.addTerceto("OUTF", val_peek(1).sval, "");}
break;
case 139:
//#line 371 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el mensaje del OUTF", lex.getLineaInicial());}
break;
case 140:
//#line 372 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Parámetro invalido del OUTF", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 141:
//#line 376 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 142:
//#line 377 "gramatica.y"
{ System.out.println("CAMUL: " + val_peek(0).sval); yyval.sval = val_peek(0).sval;}
break;
case 143:
//#line 380 "gramatica.y"
{	estructurasSintacticas("Se declaró un bucle FOR en la linea: " + lex.getLineaInicial()); 
				String var = this.varFors.get(this.varFors.size()-1);
				if(!this.ts.getAtributo(val_peek(2).sval, AccionSemantica.TIPO).equals(AccionSemantica.ULONGINT)){
					ErrorHandler.addErrorSemantico("La constante de avance no es de tipo entero.", lex.getLineaInicial()); 
					gc.addTerceto("+", gc.checkDeclaracion(var, lex.getLineaInicial(), this.ts, this.ambitoActual), String.valueOf(val_peek(3).ival * Double.parseDouble(val_peek(2).sval)));
				} else {
					gc.addTerceto("+", gc.checkDeclaracion(var, lex.getLineaInicial(), this.ts, this.ambitoActual), String.valueOf(val_peek(3).ival * Integer.parseInt(val_peek(2).sval)), "ulongint");
				}		
				this.varFors.remove(this.varFors.size()-1);
				gc.addTerceto("BI", "["+String.valueOf(Integer.parseInt(val_peek(5).sval.substring(1, val_peek(5).sval.length()-1))-1)+"]", "");
				gc.actualizarBF(gc.getCantTercetos());
				gc.pop();
				this.gc.addTerceto("Label" + this.gc.getCantTercetos(), "endfor", "FOR"+this.cantFors); 
		}
break;
case 144:
//#line 395 "gramatica.y"
{ ErrorHandler.addErrorSintactico("No se puede utilizar una constante negativa, en su lugar se debe utilizar el avance descendiente DOWN.", lex.getLineaInicial());}
break;
case 145:
//#line 396 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 146:
//#line 397 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma entre asignacion y condicion", lex.getLineaInicial());}
break;
case 147:
//#line 398 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Faltan todos los punto y coma del for", lex.getLineaInicial());}
break;
case 148:
//#line 399 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta UP/DOWN", lex.getLineaInicial());}
break;
case 149:
//#line 400 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN", lex.getLineaInicial());}
break;
case 150:
//#line 401 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 151:
//#line 402 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 152:
//#line 403 "gramatica.y"
{ { ErrorHandler.addErrorSintactico("Falta UP/DOWN, su valor, y punto y coma entre condicion y avance", lex.getLineaInicial());}}
break;
case 153:
//#line 407 "gramatica.y"
{ yyval.sval = val_peek(0).sval;
				gc.addTerceto("BF", val_peek(0).sval, "");
				gc.push(gc.getPosActual());
			}
break;
case 154:
//#line 413 "gramatica.y"
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
case 155:
//#line 428 "gramatica.y"
{yyval.ival = 1;}
break;
case 156:
//#line 429 "gramatica.y"
{yyval.ival = -1;}
break;
case 157:
//#line 432 "gramatica.y"
{ yyval.sval = gc.addTerceto("GOTO", ambitoActual + ":" + val_peek(0).sval,"");
			     this.ts.addAtributo(val_peek(0).sval,AccionSemantica.USO,"nombre etiqueta");
			     this.tags.get(tags.size()-1).huboGoto(this.ambitoActual+":"+val_peek(0).sval);
			}
break;
case 158:
//#line 437 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta la etiqueta en el GOTO, en caso de faltar también el punto y coma es posible que no compile el resto del programa o lo haga mal.", lex.getLineaInicial());
				lex.setErrorHandlerToken(";");}
break;
case 159:
//#line 443 "gramatica.y"
{this.ts.addClave(val_peek(0).sval); this.ts.addAtributo(val_peek(0).sval,AccionSemantica.USO,"nombre de tipo tripla"); estructurasSintacticas("Se declaró un tipo TRIPLE con el ID: " + val_peek(0).sval + " en la linea:" + lex.getLineaInicial()); this.ts.addAtributo(val_peek(0).sval, "tipotripla", val_peek(2).sval); this.ts.addAtributo(val_peek(0).sval, "tipo", val_peek(2).sval);}
break;
case 160:
//#line 445 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta < en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 161:
//#line 446 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta > en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 162:
//#line 447 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta > y < en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 163:
//#line 448 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta la palabra clave TRIPLE", lex.getLineaInicial());}
break;
case 164:
//#line 449 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el ID de la tripla definida.", lex.getLineaInicial());}
break;
//#line 1719 "Parser.java"
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
