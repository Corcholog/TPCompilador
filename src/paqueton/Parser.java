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
   46,   46,   46,   47,   42,   48,   48,   48,   49,   49,
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
    4,    1,    1,    1,    1,    1,    2,    4,    0,    0,
    9,    0,    7,    0,    7,    1,    1,    3,    1,    2,
    2,    1,    1,    0,    5,    3,    1,    2,    1,    1,
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
  104,   53,    0,    0,    0,  105,    0,   95,  103,    0,
    0,    0,    0,   15,    5,   79,    0,    0,    0,    0,
    0,    0,    0,  134,    0,    0,    0,  159,    0,    0,
    0,    0,    0,    0,    0,    0,  107,   96,   97,   31,
    0,   56,   57,   58,   59,   60,   61,    0,    0,    0,
    0,    0,    0,    0,  143,  140,    0,    0,    0,    0,
    0,    4,   12,    0,    0,    0,    0,    0,    0,    8,
   10,    0,    0,    0,  136,  108,    0,    0,  154,    0,
    0,   28,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   54,    0,   52,    0,    0,   49,    0,    0,
   93,    0,   94,  141,  139,    0,  163,    0,    0,   80,
   78,    0,    0,    0,    0,  119,    7,  138,  133,    0,
  155,    0,  156,  157,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   90,   89,   92,   91,   65,    0,   73,
    0,   63,   45,    0,   55,   51,  100,   99,  101,   98,
  162,    0,  161,  164,  114,    0,  121,  120,  112,    0,
  135,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   34,    0,   64,   66,    0,   74,    0,   50,    0,  160,
  124,    0,  124,  118,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   35,   36,    0,   62,   72,
   46,  165,  115,    0,  110,  113,    0,    0,  151,    0,
  153,    0,    0,    0,  152,    0,    0,  148,   37,   33,
    0,  124,   70,    0,    0,   68,  149,    0,  150,  146,
    0,  147,    0,  130,    0,  127,  129,  111,   69,   71,
    0,  144,  145,    0,    0,  128,   67,    0,  125,  126,
  131,
};
final static short yydgoto[] = {                          3,
   33,    5,    4,   16,   64,   18,   19,   20,   21,   22,
   23,   24,   25,   26,   27,   28,  129,   53,  180,  100,
   84,  181,   85,  182,   55,  147,  148,  149,  196,  191,
  259,   29,   67,   40,   30,   57,   58,   59,   31,  165,
  206,  253,  272,  233,  231,  166,  254,  285,  286,  287,
  124,   75,  125,  108,   80,  130,  176,
};
final static short yysindex[] = {                      -199,
    0,  656,    0,    0,  541,   46,  -22, -210,   17,    0,
    0,  401,   95,  -41,    0,  379, -194,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -148, -137,
 -132,  656,    0,  394,    0,    0,  113,  128,  113, -148,
  124,    0, -103,  -19,    0,  568,    9,  -59,   -5,   -5,
    0,    0,  150,  577,  -82,    0,   39,    0,    0,   21,
   16, -241,  688,    0,    0,    0,    1,  113,  -39,  410,
  -70,  703,   71,    0,  100,  -10,    1,    0,  -50,  542,
  113,  172,  577,  126,  171,   39,    0,    0,    0,    0,
  113,    0,    0,    0,    0,    0,    0,   55,  627,  113,
  182,   24,   63,  201,    0,    0,   71,  209, -241,  -60,
  197,    0,    0,    4,   30,   71,  263,  -89,  718,    0,
    0,   71,   61,  141,    0,    0,   77,  401,    0,   40,
   71,    0,  592,  650,  113,   71,   -4,   39,   52,   39,
   71,  -22,    0,  462,    0,  277,  -69,    0,   64,   98,
    0,  122,    0,    0,    0,  -52,    0,   81,   85,    0,
    0,  302,   88,   89,  227,    0,    0,    0,    0,  100,
    0,   44,    0,    0, -216,   83,  307,  671,  113,   71,
  309,  304,   71,    0,    0,    0,    0,    0,   78,    0,
  476,    0,    0,  571,    0,    0,    0,    0,    0,    0,
    0,   19,    0,    0,    0,  -89,    0,    0,    0,  -89,
    0,  310,  -28,   -9,  103,  312,  113,  113,  324,  331,
    0,  113,    0,    0,  586,    0,   64,    0,  317,    0,
    0,  251,    0,    0,  519,  336,  519,   -7,  338,  519,
  123,  342,  519,  343,  346,    0,    0,   71,    0,    0,
    0,    0,    0,  121,    0,    0,  484,  340,    0,  519,
    0,  365,  519,  519,    0,  372,  519,    0,    0,    0,
  733,    0,    0,  145,  499,    0,    0,  519,    0,    0,
  519,    0,  380,    0,  364,    0,    0,    0,    0,    0,
  741,    0,    0,  113,  673,    0,    0,  155,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0, -235,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  431,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  143,    0,
    0,    0,    0,    0,    0,    0,    0,  106,    0,    0,
    0,    0,    0,  -36,    0,    0,    0,    0,    0,    0,
    0,    0,   91,    0,    0,    0,  133,    0,    0,    0,
    0,    0,  426,    0,    0,    0,  148,    0,    0,    0,
  431,  428,  270,    0,    0,    0,  285,    0,    0,    0,
    0,  102,  386,    0,    0,  203,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  390,    0,    0,    0,
    0,    0,    0,    0,    0,  300,    0,    0,  432,    0,
    0,  253,    0,    0,    0,    0,    0,    0,    0,    0,
  386,    0,    0,    0,    0,   66,    0,  229,    0,  255,
   69,    0,    0,    0,    0,    0,  323,    0,  349,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -67,  266,  281,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  389,
    0,    0,  534,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  282,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,   12,   -2,    0,  636,    0,    0,    0,
    0,   -8,    0,    0,    0,    0,  422,  396,   84,  -68,
    0,  258,    0,    0,    0,  241,  293,  303, -180,  190,
  524,  659,  411,    0,   38,   35,   29,    0,    0,  250,
    0, -213,    0,    0,    0,  254,    0,    0, -173,    0,
    0,    0,  308,    0,    0,  353, -160,
};
final static int YYTABLESIZE=1017;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         17,
  118,  158,   35,   51,  102,  102,  102,  102,  102,  202,
  102,  214,  237,  228,  215,  134,   34,   38,   62,  256,
   38,   76,  102,  102,  102,  102,   10,   11,   51,   71,
   51,  240,   98,  263,   99,  241,  185,   51,   51,   48,
   51,   51,  117,   70,  115,   41,  251,  173,  174,   56,
   49,   51,  238,   48,  184,   50,   43,    1,  288,   51,
  113,  106,   49,   47,  178,   48,   51,   50,   48,  113,
   42,   51,   51,    2,   56,  109,   56,   88,   89,   65,
  102,   86,   51,   56,   56,  103,   56,   56,  102,   51,
   51,   51,  187,   51,   51,   54,   49,   56,  175,   48,
   39,   50,  213,   39,   36,   56,   38,   48,   66,   32,
  186,  296,   56,   98,  126,   99,  113,   56,   56,   51,
   73,  300,   76,   68,   38,   51,   51,   32,   56,   83,
  151,  153,  138,  140,   60,   56,   56,   56,  198,   56,
   56,   49,   47,  107,   48,   69,   50,  132,  132,   29,
  132,  116,  132,   79,   49,   47,  197,   48,  122,   50,
   30,   51,  200,   54,  131,   56,  133,  163,   74,   51,
   51,   56,   56,   87,  136,   87,   87,   87,   10,   11,
  199,  169,   78,  141,  170,   96,   95,   97,  101,  109,
   90,   87,   87,   87,   87,  301,  157,   98,   87,   99,
  109,  109,  143,  120,  201,  145,   75,   56,   51,   51,
  127,   54,  132,   51,  135,   56,   56,  117,  183,  102,
  102,  102,  102,  102,   61,  102,  102,  102,  102,  236,
  102,  102,  102,  102,  102,  173,  174,  102,   37,  102,
  102,  154,  102,   88,  102,   88,   88,   88,  239,  155,
  262,   44,   45,  122,   56,   56,  114,   87,  159,   56,
  160,   88,   88,   88,   88,   44,   45,  209,  284,   85,
  210,   85,   85,   85,  229,  230,  104,   44,   45,  150,
   44,   45,  284,   10,   11,   51,  161,   85,   85,   85,
   85,  255,  284,  137,  210,   86,  137,   86,   86,   86,
  105,  212,  162,  173,  174,  248,  123,  173,  174,  123,
  137,   44,   45,   86,   86,   86,   86,  168,  152,   44,
   45,  122,   42,   38,  122,   43,   32,   88,   84,   38,
   38,   56,   32,   32,  171,  192,   38,  203,  195,   32,
  216,  204,  205,   77,  207,  208,  217,  222,   29,  221,
  235,  223,  243,   85,   29,   29,   44,   45,   83,   30,
  242,   29,  132,  132,  246,   30,   30,   10,   11,   44,
   45,  247,   30,  132,  132,  252,  260,  298,  264,   86,
  266,   47,  267,  269,   92,   93,  270,   94,   87,   87,
   87,   87,   87,  271,   87,   87,   87,   87,  276,   87,
   87,   87,   87,   87,   75,  278,   87,   48,   87,   87,
   75,   87,  281,   87,   75,   75,   75,   75,  289,  294,
  116,   75,  295,   75,   75,    6,   75,   11,   75,   41,
  142,    9,   44,   52,  227,  219,  220,   63,  142,  193,
   46,   82,   49,   47,    8,   48,  275,   50,    9,  194,
   77,   12,   72,  143,  144,  232,  145,   13,   88,   88,
   88,   88,   88,  234,   88,   88,   88,   88,  119,   88,
   88,   88,   88,   88,  244,  245,   88,  211,   88,   88,
  172,   88,    0,   88,   85,   85,   85,   85,   85,   13,
   85,   85,   85,   85,    0,   85,   85,   85,   85,   85,
    0,    0,   85,    0,   85,   85,    0,   85,    0,   85,
   86,   86,   86,   86,   86,    0,   86,   86,   86,   86,
  189,   86,   86,   86,   86,   86,   84,    0,   86,    0,
   86,   86,   84,   86,  225,   86,   84,   84,   84,   84,
    0,   77,  274,   84,    0,   84,   84,   77,   84,    0,
   84,   77,   77,   77,   77,    0,   83,  291,   77,    0,
   77,   77,   83,   77,    0,   77,   83,   83,   83,   83,
    0,    0,    0,   83,   39,   83,   83,   40,   83,   47,
   83,   46,    0,   49,   47,   47,   48,    0,   50,   47,
   47,   47,   47,   39,   39,   39,   47,    0,   47,   47,
  128,   47,    0,   47,    0,   48,    0,   81,    0,   49,
   47,   48,   48,    0,   50,   48,   48,   48,   48,   98,
    7,   99,   48,    0,   48,   48,    8,   48,    0,   48,
    9,   10,   11,   12,    0,    7,   96,   95,   97,   13,
   14,    8,  283,    0,   15,    9,   10,   11,   12,    0,
    7,   96,   95,   97,   13,   14,    8,   44,   45,   15,
    9,   10,   11,   12,    0,    0,    7,    0,   49,   13,
   14,   48,    8,   50,   15,    0,    9,   10,   11,   12,
    0,    0,    0,    0,    0,   13,   14,   13,    0,  179,
   15,   49,   47,   13,   48,    0,   50,   13,   13,   13,
   13,    0,    0,    0,    0,    0,   13,   13,    0,    0,
  218,   13,   49,   47,    0,   48,    0,   50,  142,  110,
  111,    0,    0,    0,    8,    0,    0,    0,    9,    0,
    0,   12,  142,  123,    0,  188,  146,   13,    8,    0,
  142,    0,    9,    0,    0,   12,    8,    0,    0,  224,
    9,   13,    0,   12,    0,  142,    0,  273,    0,   13,
  261,    8,    0,  265,    0,    9,  268,  156,   12,    0,
    0,    0,  290,    0,   13,  142,  164,    0,    0,  190,
    0,    8,    0,  277,    0,    9,  279,  280,   12,    0,
  282,  257,   39,   39,   13,   39,    6,    7,   44,   45,
    0,  292,    0,    8,  293,    0,    0,    9,   10,   11,
   12,    0,    0,   32,    0,    0,   13,   14,    0,    0,
    0,   15,    0,    0,   44,   45,  226,  142,  123,  146,
    0,    0,   91,    8,    0,   92,   93,    9,   94,    0,
   12,    0,  142,  144,    0,  195,   13,  177,    8,    0,
   92,   93,    9,   94,    0,   12,    0,    0,    0,  249,
  250,   13,    0,    0,  164,    0,    0,    0,  164,    0,
  258,    0,  258,    0,    0,  258,    0,    0,  258,    0,
    0,    0,  139,   44,   45,    0,    0,    0,    0,    0,
    0,    0,  190,    0,    0,  258,    0,    0,  258,  258,
    0,    0,  258,    0,    0,    0,   44,   45,    0,    0,
  226,    6,    7,  258,    0,    0,  258,    0,    8,    0,
    0,    0,    9,   10,   11,   12,  250,   44,   45,    7,
    0,   13,   14,    0,    0,    8,   15,    0,    0,    9,
   10,   11,   12,    0,    7,    0,  299,    0,   13,   14,
    8,  283,    0,   15,    9,   10,   11,   12,    0,    7,
    0,  112,    0,   13,   14,    8,    0,    0,   15,    9,
   10,   11,   12,    0,    7,    0,  121,    0,   13,   14,
    8,    0,    0,   15,    9,   10,   11,   12,    0,    7,
    0,  167,    0,   13,   14,    8,    0,  142,   15,    9,
   10,   11,   12,    8,    0,    0,    0,    9,   13,   14,
   12,  283,    0,   15,  297,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   40,   62,    5,   12,   41,   42,   43,   44,   45,   62,
   47,  172,   41,  194,  175,   84,    5,   40,   60,  233,
   40,  257,   59,   60,   61,   62,  268,  269,   37,   32,
   39,   41,   43,   41,   45,   45,   41,   46,   47,   45,
   49,   50,  278,   32,   44,  256,  227,  264,  265,   12,
   42,   60,  213,   45,   59,   47,   40,  257,  272,   68,
   63,   41,   42,   43,  133,   45,   75,   47,   45,   72,
  281,   80,   81,  273,   37,   60,   39,   49,   50,  274,
   42,   47,   91,   46,   47,   47,   49,   50,  125,   98,
   99,  100,   41,  102,  103,   12,   42,   60,   59,   45,
  123,   47,   59,  123,   59,   68,   41,   45,  257,   41,
   59,  285,   75,   43,  125,   45,  119,   80,   81,  128,
   37,  295,   39,  261,   59,  134,  135,   59,   91,   46,
  102,  103,   98,   99,   40,   98,   99,  100,   41,  102,
  103,   42,   43,   60,   45,  278,   47,   42,   43,   59,
   45,   68,   47,  257,   42,   43,   59,   45,   75,   47,
   59,  170,   41,   80,   81,  128,   41,  257,   41,  178,
  179,  134,  135,   41,   91,   43,   44,   45,  268,  269,
   59,   41,   59,  100,   44,   60,   61,   62,  271,  257,
   41,   59,   60,   61,   62,   41,  257,   43,  258,   45,
  268,  269,  272,  274,  257,  275,   59,  170,  217,  218,
  261,  128,   41,  222,   44,  178,  179,  257,  135,  256,
  257,  258,  259,  260,  266,  262,  263,  264,  265,  258,
  267,  268,  269,  270,  271,  264,  265,  274,  261,  276,
  277,   41,  279,   41,  281,   43,   44,   45,  258,   41,
  258,  257,  258,  170,  217,  218,  256,  125,   62,  222,
  257,   59,   60,   61,   62,  257,  258,   41,  271,   41,
   44,   43,   44,   45,  256,  257,  256,  257,  258,  256,
  257,  258,  285,  268,  269,  294,  257,   59,   60,   61,
   62,   41,  295,   41,   44,   41,   44,   43,   44,   45,
  280,  258,   40,  264,  265,  222,   41,  264,  265,   44,
  256,  257,  258,   59,   60,   61,   62,  257,  256,  257,
  258,   41,   41,  258,   44,   44,  258,  125,   59,  264,
  265,  294,  264,  265,  258,   59,  271,  257,  275,  271,
  258,  257,   41,   59,  257,  257,   40,   44,  258,   41,
   41,  274,   41,  125,  264,  265,  257,  258,   59,  258,
  258,  271,  257,  258,   41,  264,  265,  268,  269,  257,
  258,   41,  271,  268,  269,   59,   41,  294,   41,  125,
  258,   59,   41,   41,  259,  260,   41,  262,  256,  257,
  258,  259,  260,  273,  262,  263,  264,  265,   59,  267,
  268,  269,  270,  271,  257,   41,  274,   59,  276,  277,
  263,  279,   41,  281,  267,  268,  269,  270,  274,   40,
  278,  274,   59,  276,  277,    0,  279,    0,  281,   44,
   41,    0,   44,   12,  194,  178,  179,   59,  257,  147,
   40,   46,   42,   43,  263,   45,  257,   47,  267,  147,
   40,  270,   59,  272,  273,  206,  275,  276,  256,  257,
  258,  259,  260,  210,  262,  263,  264,  265,   59,  267,
  268,  269,  270,  271,  217,  218,  274,  170,  276,  277,
  128,  279,   -1,  281,  256,  257,  258,  259,  260,   59,
  262,  263,  264,  265,   -1,  267,  268,  269,  270,  271,
   -1,   -1,  274,   -1,  276,  277,   -1,  279,   -1,  281,
  256,  257,  258,  259,  260,   -1,  262,  263,  264,  265,
   59,  267,  268,  269,  270,  271,  257,   -1,  274,   -1,
  276,  277,  263,  279,   59,  281,  267,  268,  269,  270,
   -1,  257,   59,  274,   -1,  276,  277,  263,  279,   -1,
  281,  267,  268,  269,  270,   -1,  257,   59,  274,   -1,
  276,  277,  263,  279,   -1,  281,  267,  268,  269,  270,
   -1,   -1,   -1,  274,   41,  276,  277,   44,  279,  257,
  281,   40,   -1,   42,   43,  263,   45,   -1,   47,  267,
  268,  269,  270,   60,   61,   62,  274,   -1,  276,  277,
   59,  279,   -1,  281,   -1,  257,   -1,   40,   -1,   42,
   43,  263,   45,   -1,   47,  267,  268,  269,  270,   43,
  257,   45,  274,   -1,  276,  277,  263,  279,   -1,  281,
  267,  268,  269,  270,   -1,  257,   60,   61,   62,  276,
  277,  263,  279,   -1,  281,  267,  268,  269,  270,   -1,
  257,   60,   61,   62,  276,  277,  263,  257,  258,  281,
  267,  268,  269,  270,   -1,   -1,  257,   -1,   42,  276,
  277,   45,  263,   47,  281,   -1,  267,  268,  269,  270,
   -1,   -1,   -1,   -1,   -1,  276,  277,  257,   -1,   40,
  281,   42,   43,  263,   45,   -1,   47,  267,  268,  269,
  270,   -1,   -1,   -1,   -1,   -1,  276,  277,   -1,   -1,
   40,  281,   42,   43,   -1,   45,   -1,   47,  257,   61,
   62,   -1,   -1,   -1,  263,   -1,   -1,   -1,  267,   -1,
   -1,  270,  257,   75,   -1,  274,  101,  276,  263,   -1,
  257,   -1,  267,   -1,   -1,  270,  263,   -1,   -1,  274,
  267,  276,   -1,  270,   -1,  257,   -1,  274,   -1,  276,
  237,  263,   -1,  240,   -1,  267,  243,  109,  270,   -1,
   -1,   -1,  274,   -1,  276,  257,  118,   -1,   -1,  144,
   -1,  263,   -1,  260,   -1,  267,  263,  264,  270,   -1,
  267,  273,  259,  260,  276,  262,  256,  257,  257,  258,
   -1,  278,   -1,  263,  281,   -1,   -1,  267,  268,  269,
  270,   -1,   -1,  273,   -1,   -1,  276,  277,   -1,   -1,
   -1,  281,   -1,   -1,  257,  258,  191,  257,  170,  194,
   -1,   -1,  256,  263,   -1,  259,  260,  267,  262,   -1,
  270,   -1,  257,  273,   -1,  275,  276,  256,  263,   -1,
  259,  260,  267,  262,   -1,  270,   -1,   -1,   -1,  274,
  225,  276,   -1,   -1,  206,   -1,   -1,   -1,  210,   -1,
  235,   -1,  237,   -1,   -1,  240,   -1,   -1,  243,   -1,
   -1,   -1,  256,  257,  258,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  257,   -1,   -1,  260,   -1,   -1,  263,  264,
   -1,   -1,  267,   -1,   -1,   -1,  257,  258,   -1,   -1,
  275,  256,  257,  278,   -1,   -1,  281,   -1,  263,   -1,
   -1,   -1,  267,  268,  269,  270,  291,  257,  258,  257,
   -1,  276,  277,   -1,   -1,  263,  281,   -1,   -1,  267,
  268,  269,  270,   -1,  257,   -1,  274,   -1,  276,  277,
  263,  279,   -1,  281,  267,  268,  269,  270,   -1,  257,
   -1,  274,   -1,  276,  277,  263,   -1,   -1,  281,  267,
  268,  269,  270,   -1,  257,   -1,  274,   -1,  276,  277,
  263,   -1,   -1,  281,  267,  268,  269,  270,   -1,  257,
   -1,  274,   -1,  276,  277,  263,   -1,  257,  281,  267,
  268,  269,  270,  263,   -1,   -1,   -1,  267,  276,  277,
  270,  279,   -1,  281,  274,   -1,  276,
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
"$$7 :",
"cuerpo_funcion_p : $$7 BEGIN bloques_funcion ';' END",
"bloques_funcion : bloques_funcion ';' bloque_funcion",
"bloques_funcion : bloque_funcion",
"bloques_funcion : bloques_funcion bloque_funcion",
"bloque_funcion : retorno",
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

//#line 445 "gramatica.y"
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
int cantPatronIzq;
int cantPatronDer;
public Parser(String nombreArchivo, TablaSimbolos t, GeneradorCodigo gc)
{
	this.nombreArchivo=nombreArchivo;
	this.funcionActual = "";
	this.ambitoActual = "global";
	this.inicioPatron = Integer.MAX_VALUE;
	this.posPatron = -1;
	this.cantPatronIzq = 0;
	this.cantPatronDer = 0;
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
				yyval.sval = gc.addTerceto(val_peek(1).sval, gc.checkDeclaracion(val_peek(2).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual)); 					gc.checkTipo(gc.getPosActual(), lex.getLineaInicial(), this.ts, this.ambitoActual, val_peek(1).sval);}
break;
case 33:
//#line 76 "gramatica.y"
{ if(gc.getTerceto(gc.getPosActual()).getOp2().isEmpty()){ErrorHandler.addErrorSemantico("La longitud de los patrones a matchear es distinta.", lex.getLineaInicial());} else { yyval.sval = gc.updateCompAndGenerate(this.inicioPatron, val_peek(3).sval, this.cantPatronIzq, this.cantPatronDer, lex.getLineaInicial());} this.inicioPatron = Integer.MAX_VALUE; yyval.sval = "[" + this.gc.getPosActual() + "]";}
break;
case 34:
//#line 78 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón y el que abre la segunda", lex.getLineaInicial());}
break;
case 35:
//#line 79 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que abre la segunda lista del patrón", lex.getLineaInicial());}
break;
case 36:
//#line 80 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón", lex.getLineaInicial());}
break;
case 37:
//#line 81 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta comparador entre los patrones", lex.getLineaInicial());}
break;
case 38:
//#line 82 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta comparador entre las expresiones", lex.getLineaInicial());}
break;
case 39:
//#line 85 "gramatica.y"
{ this.iniciarPatron(); this.cantPatronIzq++; yyval.sval = gc.addTerceto("COMP", gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
break;
case 40:
//#line 88 "gramatica.y"
{ this.iniciarPatron(); this.cantPatronIzq++;yyval.sval = gc.addTerceto("COMP", gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
break;
case 41:
//#line 89 "gramatica.y"
{this.iniciarPatron(); this.cantPatronIzq=1; yyval.sval = gc.addTerceto("COMP", gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
break;
case 42:
//#line 91 "gramatica.y"
{ this.cantPatronDer++; posPatron = gc.updateAndCheckSize(this.posPatron, gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), lex.getLineaInicial(), this.ts, this.ambitoActual); this.posPatron++;}
break;
case 43:
//#line 94 "gramatica.y"
{ this.cantPatronDer++;posPatron = gc.updateAndCheckSize(this.posPatron, gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), lex.getLineaInicial(), this.ts, this.ambitoActual); this.posPatron++;}
break;
case 44:
//#line 95 "gramatica.y"
{ this.cantPatronDer = 1; posPatron = gc.updateAndCheckSize(this.posPatron, gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), lex.getLineaInicial(), this.ts, this.ambitoActual); this.posPatron++;}
break;
case 45:
//#line 98 "gramatica.y"
{estructurasSintacticas("Se definió una sentencia de control sin else, en la linea: " + lex.getLineaInicial());}
break;
case 47:
//#line 103 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta END_IF con ELSE", lex.getLineaInicial());}
break;
case 48:
//#line 104 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta END_IF", lex.getLineaInicial());}
break;
case 49:
//#line 105 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control del then", lex.getLineaInicial());}
break;
case 50:
//#line 106 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control del ELSE", lex.getLineaInicial());}
break;
case 51:
//#line 107 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control tanto en THEN como ELSE", lex.getLineaInicial());}
break;
case 52:
//#line 112 "gramatica.y"
{
			gc.actualizarBF(gc.getCantTercetos()); 
			gc.pop();
			}
break;
case 53:
//#line 118 "gramatica.y"
{
			gc.addTerceto("BF", val_peek(0).sval, ""); 
			gc.push(gc.getPosActual());
		}
break;
case 54:
//#line 123 "gramatica.y"
{ 
			gc.addTerceto("BI", "", "-"); 
			int posSig = gc.getCantTercetos();
			gc.actualizarBF(posSig); 
			gc.pop(); 
			gc.push(gc.getPosActual());
			this.gc.addTerceto("Label" + posSig, "-", "-");
		}
break;
case 55:
//#line 132 "gramatica.y"
{
			int posSig = gc.getCantTercetos();
			gc.actualizarBI(posSig);
			this.gc.addTerceto("Label" + posSig, "-", "-");
			gc.pop();
			estructurasSintacticas("Se definió una sentencia de control con else, en la linea: " + lex.getLineaInicial());
		}
break;
case 56:
//#line 142 "gramatica.y"
{yyval.sval = ">=";}
break;
case 57:
//#line 143 "gramatica.y"
{yyval.sval = "<=";}
break;
case 58:
//#line 144 "gramatica.y"
{yyval.sval = "!=";}
break;
case 59:
//#line 145 "gramatica.y"
{yyval.sval = "=";}
break;
case 60:
//#line 146 "gramatica.y"
{yyval.sval = "<";}
break;
case 61:
//#line 147 "gramatica.y"
{yyval.sval = ">";}
break;
case 64:
//#line 153 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
break;
case 65:
//#line 154 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
break;
case 66:
//#line 155 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 69:
//#line 161 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
break;
case 70:
//#line 162 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
break;
case 71:
//#line 163 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 74:
//#line 169 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 75:
//#line 173 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 76:
//#line 175 "gramatica.y"
{tipoVar = val_peek(0).sval;}
break;
case 77:
//#line 175 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 78:
//#line 178 "gramatica.y"
{checkRedeclaracion(val_peek(0).sval);}
break;
case 79:
//#line 179 "gramatica.y"
{checkRedeclaracion(val_peek(0).sval);}
break;
case 80:
//#line 180 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta coma en la lista de variables, puede haber parado la compilacion en este punto", lex.getLineaInicial());}
break;
case 81:
//#line 184 "gramatica.y"
{yyval.sval = "double";}
break;
case 82:
//#line 185 "gramatica.y"
{yyval.sval = "ulongint";}
break;
case 83:
//#line 188 "gramatica.y"
{estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());
						yyval.sval = gc.checkTipoAsignacion(val_peek(2).sval, lex.getLineaInicial(), val_peek(0).sval, this.ts,ambitoActual);}
break;
case 84:
//#line 191 "gramatica.y"
{  estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());
					yyval.sval = gc.checkTipoAsignacion(val_peek(2).sval, lex.getLineaInicial(), val_peek(0).sval, this.ts,ambitoActual);
		}
break;
case 85:
//#line 196 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "+",ambitoActual);}
break;
case 86:
//#line 198 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "-",ambitoActual);}
break;
case 87:
//#line 200 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 88:
//#line 203 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 89:
//#line 204 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");}
break;
case 90:
//#line 206 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
break;
case 91:
//#line 208 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");}
break;
case 92:
//#line 210 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
break;
case 93:
//#line 215 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "*",ambitoActual);}
break;
case 94:
//#line 216 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "/",ambitoActual);}
break;
case 95:
//#line 217 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 96:
//#line 219 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 97:
//#line 220 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 98:
//#line 221 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 99:
//#line 223 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 100:
//#line 225 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
break;
case 101:
//#line 227 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
break;
case 102:
//#line 231 "gramatica.y"
{  gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts,ambitoActual);}
break;
case 106:
//#line 237 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 107:
//#line 238 "gramatica.y"
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
//#line 248 "gramatica.y"
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
//#line 264 "gramatica.y"
{ if (esEmbebido(val_peek(1).sval)){ErrorHandler.addErrorSemantico("No se puede declarar una funcion con un ID con tipos embebidos.", lex.getLineaInicial());}
					else {
					this.checkRedFuncion(val_peek(1).sval, val_peek(3).sval);
					this.funcionActual = val_peek(1).sval; 
					}
					}
break;
case 110:
//#line 269 "gramatica.y"
{ 
								this.cantRetornos.add(0); 
								this.gc_funciones.push(this.ts.getGCFuncion(val_peek(4).sval)); 
								this.gc = this.gc_funciones.peek(); 
								this.ambitoActual += ":" + val_peek(4).sval;
								this.tags.add(new ControlTagAmbito());
								}
break;
case 111:
//#line 275 "gramatica.y"
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
//#line 285 "gramatica.y"
{ this.cantRetornos.add(0);}
break;
case 113:
//#line 285 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta nombre de la funcion declarada", lex.getLineaInicial());
													this.checkRet("");
													}
break;
case 114:
//#line 288 "gramatica.y"
{ this.cantRetornos.add(0);}
break;
case 115:
//#line 288 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el parametro en la declaracion de la funcion", lex.getLineaInicial());
							    this.checkRet(val_peek(4).sval);
							    if (esEmbebido(val_peek(4).sval)){ErrorHandler.addErrorSemantico("No se puede declarar una funcion con un ID con tipos embebidos.", lex.getLineaInicial());}
							   }
break;
case 118:
//#line 298 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Se declaró más de un parametro", lex.getLineaInicial());}
break;
case 120:
//#line 303 "gramatica.y"
{ 
this.ts.addClave(this.ambitoActual + ":" + val_peek(0).sval);
String id_param = gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual);
this.ts.addAtributo(id_param,AccionSemantica.TIPO, val_peek(1).sval); this.ts.addAtributo(id_param,AccionSemantica.USO,"nombre parametro"); this.ts.addAtributo(gc.checkDeclaracion(funcionActual, lex.getLineaInicial(), this.ts, this.ambitoActual), AccionSemantica.PARAMETRO, id_param); this.ts.addAtributo(id_param, AccionSemantica.TIPO, val_peek(1).sval); estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 121:
//#line 307 "gramatica.y"
{ this.ts.addAtributo(ambitoActual+":"+val_peek(0).sval,AccionSemantica.TIPO, val_peek(1).sval); this.ts.addAtributo(ambitoActual+":"+val_peek(0).sval,AccionSemantica.USO,"nombre parametro"); this.ts.addAtributo(ambitoActual+":"+funcionActual, AccionSemantica.PARAMETRO, val_peek(0).sval); estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 122:
//#line 309 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el nombre del parametro", lex.getLineaInicial());}
break;
case 123:
//#line 310 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el nombre del parametro o el tipo", lex.getLineaInicial());}
break;
case 124:
//#line 313 "gramatica.y"
{ts.addClave(yylval.sval);}
break;
case 128:
//#line 321 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 131:
//#line 328 "gramatica.y"
{ this.cantRetornos.set(this.cantRetornos.size()-1, this.cantRetornos.get(this.cantRetornos.size()-1) + 1); 
					yyval.sval = gc.addTerceto("RET", val_peek(1).sval, "");		
		}
break;
case 132:
//#line 333 "gramatica.y"
{funcionActual = val_peek(1).sval; }
break;
case 133:
//#line 333 "gramatica.y"
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
//#line 349 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de parámetros en la invocación a la función", lex.getLineaInicial());}
break;
case 135:
//#line 353 "gramatica.y"
{ yyval.sval = val_peek(0).sval;ErrorHandler.addErrorSintactico("Se utilizó más de un parámetro para invocar a la función", lex.getLineaInicial());}
break;
case 136:
//#line 354 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 137:
//#line 357 "gramatica.y"
{yyval.sval = val_peek(0).sval; gc.checkParamReal(val_peek(0).sval, lex.getLineaInicial(), this.ts, funcionActual,ambitoActual);}
break;
case 138:
//#line 358 "gramatica.y"
{
if(!this.ts.getAtributo(gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), AccionSemantica.TIPO).equals(val_peek(1).sval)){yyval.sval = gc.addTerceto("TO".concat(val_peek(1).sval), gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts, this.ambitoActual), "");}
if(!this.ts.getAtributo(this.ts.getAtributo(gc.checkDeclaracion(funcionActual, lex.getLineaInicial(), this.ts, ambitoActual), AccionSemantica.PARAMETRO), AccionSemantica.TIPO).equals(val_peek(1).sval)){ ErrorHandler.addErrorSemantico("El tipo del parametro real no coincide con el tipo del parametro formal.", lex.getLineaInicial());}}
break;
case 139:
//#line 363 "gramatica.y"
{yyval.sval = gc.addTerceto("OUTF", val_peek(1).sval, "");}
break;
case 140:
//#line 365 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el mensaje del OUTF", lex.getLineaInicial());}
break;
case 141:
//#line 366 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Parámetro invalido del OUTF", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 144:
//#line 374 "gramatica.y"
{	estructurasSintacticas("Se declaró un bucle FOR en la linea: " + lex.getLineaInicial()); 
				String var = this.varFors.get(this.varFors.size()-1);
				if(!this.ts.getAtributo(val_peek(2).sval, AccionSemantica.TIPO).equals(AccionSemantica.ULONGINT)){
					ErrorHandler.addErrorSemantico("La constante de avance no es de tipo entero.", lex.getLineaInicial()); 
					gc.addTerceto("+", gc.checkDeclaracion(var, lex.getLineaInicial(), this.ts, this.ambitoActual), String.valueOf(val_peek(3).ival * Double.parseDouble(val_peek(2).sval)));
				} else {
					gc.addTerceto("+", gc.checkDeclaracion(var, lex.getLineaInicial(), this.ts, this.ambitoActual), String.valueOf(val_peek(3).ival * Integer.parseInt(val_peek(2).sval)));
				}		
				this.varFors.remove(this.varFors.size()-1);
				gc.addTerceto("BI", val_peek(5).sval, "");
				gc.actualizarBF(gc.getCantTercetos());
				gc.pop();
				this.gc.addTerceto("Label" + this.gc.getCantTercetos(), "-", "-");
		}
break;
case 145:
//#line 389 "gramatica.y"
{ ErrorHandler.addErrorSintactico("No se puede utilizar una constante negativa, en su lugar se debe utilizar el avance descendiente DOWN.", lex.getLineaInicial());}
break;
case 146:
//#line 390 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 147:
//#line 391 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma entre asignacion y condicion", lex.getLineaInicial());}
break;
case 148:
//#line 392 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Faltan todos los punto y coma del for", lex.getLineaInicial());}
break;
case 149:
//#line 393 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta UP/DOWN", lex.getLineaInicial());}
break;
case 150:
//#line 394 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN", lex.getLineaInicial());}
break;
case 151:
//#line 395 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 152:
//#line 396 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 153:
//#line 397 "gramatica.y"
{ { ErrorHandler.addErrorSintactico("Falta UP/DOWN, su valor, y punto y coma entre condicion y avance", lex.getLineaInicial());}}
break;
case 154:
//#line 401 "gramatica.y"
{ yyval.sval = val_peek(0).sval;
				gc.addTerceto("BF", val_peek(0).sval, "");
				gc.push(gc.getPosActual());
			}
break;
case 155:
//#line 407 "gramatica.y"
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
				this.gc.addTerceto("Label" + this.gc.getCantTercetos(), "-", "-");
				}
break;
case 156:
//#line 421 "gramatica.y"
{yyval.ival = 1;}
break;
case 157:
//#line 422 "gramatica.y"
{yyval.ival = -1;}
break;
case 158:
//#line 425 "gramatica.y"
{ yyval.sval = gc.addTerceto("GOTO", ambitoActual + ":" + val_peek(0).sval,"");
			     this.ts.addAtributo(val_peek(0).sval,AccionSemantica.USO,"nombre etiqueta");
			     this.tags.get(tags.size()-1).huboGoto(this.ambitoActual+":"+val_peek(0).sval);
			}
break;
case 159:
//#line 430 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta la etiqueta en el GOTO, en caso de faltar también el punto y coma es posible que no compile el resto del programa o lo haga mal.", lex.getLineaInicial());
				lex.setErrorHandlerToken(";");}
break;
case 160:
//#line 436 "gramatica.y"
{this.ts.addClave(val_peek(0).sval); this.ts.addAtributo(val_peek(0).sval,AccionSemantica.USO,"nombre de tipo tripla"); estructurasSintacticas("Se declaró un tipo TRIPLE con el ID: " + val_peek(0).sval + " en la linea:" + lex.getLineaInicial()); this.ts.addAtributo(val_peek(0).sval, "tipotripla", val_peek(2).sval); this.ts.addAtributo(val_peek(0).sval, "tipo", val_peek(2).sval);}
break;
case 161:
//#line 438 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta < en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 162:
//#line 439 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta > en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 163:
//#line 440 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta > y < en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 164:
//#line 441 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta la palabra clave TRIPLE", lex.getLineaInicial());}
break;
case 165:
//#line 442 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el ID de la tripla definida.", lex.getLineaInicial());}
break;
//#line 1707 "Parser.java"
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
