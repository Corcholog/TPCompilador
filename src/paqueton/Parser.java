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
//#line 21 "Parser.java"




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
    3,    3,    3,    4,    4,    5,    5,    5,    5,    6,
    6,    6,    6,    6,    6,   16,   16,   16,   16,   17,
   17,   17,   17,   20,   21,   21,   12,   12,   12,   12,
   12,   12,   12,   19,   19,   19,   19,   19,   19,   22,
   22,   22,   22,   22,   24,   24,   24,   24,   24,   23,
   23,   23,    7,    7,   26,   26,   26,   25,   25,   10,
   10,   28,   28,   18,   18,   18,   18,   29,   29,   29,
   29,   29,   29,   30,   30,   30,   30,   31,   31,   27,
   35,    8,    8,    8,   32,   32,   33,   33,   36,   36,
   36,   36,   37,   34,   38,   38,   38,   39,   39,   40,
   11,   41,   41,   13,   13,   13,   42,   42,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   43,   43,   15,
   15,    9,    9,    9,    9,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    3,    4,    3,    3,    3,    2,
    3,    1,    2,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    3,    1,    2,    2,    3,
    3,    3,    3,    5,    3,    1,    5,    7,    6,    4,
    4,    6,    5,    1,    1,    1,    1,    1,    1,    4,
    2,    3,    2,    3,    4,    2,    3,    2,    3,    3,
    1,    2,    2,    2,    3,    1,    3,    1,    1,    3,
    3,    1,    1,    3,    3,    1,    2,    3,    3,    1,
    3,    2,    2,    1,    1,    1,    1,    1,    2,    4,
    0,    8,    6,    6,    1,    1,    3,    1,    2,    2,
    1,    1,    0,    5,    3,    1,    2,    1,    1,    4,
    4,    2,    1,    4,    3,    4,    1,    1,   12,   11,
   11,   10,   11,   11,   10,   10,   10,    1,    1,    2,
    3,    6,    5,    5,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    0,    0,   69,   68,    0,
    0,    0,    0,   18,    1,    0,   12,   14,   15,   16,
   17,   19,   20,   21,   22,   23,   24,   25,    0,    0,
    0,    0,    0,   66,    0,    0,    0,    0,    0,  130,
    0,    0,   88,    0,    0,    0,    0,    0,   86,    0,
    0,    0,    0,   87,    0,   80,   85,    0,    0,    0,
    0,    0,   13,    0,    0,    0,    0,    4,    0,   73,
    0,   71,    0,  113,    0,    0,    0,    0,  131,    0,
    0,    0,    0,    0,   89,   82,   83,    0,   29,    0,
   44,   45,   46,   47,   48,   49,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    7,    0,  118,  115,  117,
    0,    0,    0,    9,   11,   70,    0,    0,    3,    0,
    0,  111,   90,   67,   65,    0,   26,    0,    0,    0,
    0,   41,    0,    0,    0,    0,    0,    0,   32,   31,
   81,   78,   79,    6,  116,  114,    0,  135,    0,    0,
    0,    0,    0,   98,    0,    0,   43,   53,    0,   61,
    0,   51,    0,   37,  134,    0,  133,  103,    0,  100,
   99,  103,    0,    0,    0,   34,   52,   54,    0,   62,
   42,    0,  132,   94,    0,   91,   93,   97,    0,  128,
  129,    0,    0,   50,   60,   38,    0,  103,    0,    0,
    0,    0,    0,    0,  109,    0,  106,  108,   92,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  107,
    0,    0,  125,    0,  127,    0,    0,    0,  126,    0,
  122,    0,  104,  105,   58,    0,    0,   56,  123,    0,
  124,  120,  121,  110,   57,   59,    0,  119,   55,
};
final static short yydgoto[] = {                          3,
   15,    4,   16,   63,   18,   19,   20,   21,   22,   23,
   24,   25,   26,   27,   28,   50,   70,   71,   99,   53,
   83,  134,  161,  223,   29,   38,   30,   72,   55,   56,
   57,   31,  153,  184,  198,  154,  185,  206,  207,  208,
   75,  111,  193,
};
final static short yysindex[] = {                      -234,
  631,  700,    0,    0,   82, -246,  -14,    0,    0,  155,
  700,   15, -226,    0,    0,  278,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -208, -198,
 -205,  300, -184,    0,  422,  149,  445,    3,   33,    0,
 -158,  -26,    0,  422,  411, -152,  168,  168,    0, -160,
   76,  482,  414,    0,  -11,    0,    0,  316, -150,   88,
  -12,  648,    0,    3,  422,  -23,  663,    0,  445,    0,
  482,    0,  445,    0,   86,  -13, -125, -120,    0, -122,
  104,  482,  105,  -11,    0,    0,    0, -106,    0,  445,
    0,    0,    0,    0,    0,    0,  411,  411,  445,  114,
  114,  168,  168,  168,  685,    0,  121,    0,    0,    0,
  122, -187,  -51,    0,    0,    0,  133, -181,    0,   29,
   29,    0,    0,    0,    0,  -83,    0,  445,   21, -101,
  345,    0,  117, -215,   29,  -11,  -11,   29,    0,    0,
    0,    0,    0,    0,    0,    0,  -50,    0,  -80,  140,
  -79,  -78,   39,    0,  113,   95,    0,    0,  -94,    0,
  360,    0,  408,    0,    0,  -75,    0,    0,   45,    0,
    0,    0, -181,  155,   67,    0,    0,    0,  493,    0,
    0,  -90,    0,    0,  -87,    0,    0,    0,  -54,    0,
    0, -123,  -71,    0,    0,    0,  544,    0,  147,  -33,
  -19,  -68,  152,  159,    0,  253,    0,    0,    0,  530,
  160,  530,   -8,  162,  530,  167,  530,  422,  572,    0,
  365,  150,    0,  530,    0,  173,  530,  530,    0,  530,
    0,  223,    0,    0,    0,   -4,  381,    0,    0,  530,
    0,    0,    0,    0,    0,    0,  580,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    6,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   17,    0,
    0,    0,  337,    0,    0,    0,    0,  153,    0,    0,
    0,  -41,    0,    0,    0,    0,    0,    0,    0,    0,
  248,    0,    0,    0,  -16,    0,    0,    0,  337,    0,
    0,  275,    0,  178,    0,    0,  281,    0,    0,    0,
  124,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  385,  245,    0,    9,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  306,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  245,
  259,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  203,   84,   34,   59,  109,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   57,   66,    0,    0,    0,  265,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  228,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   13,  549,    0,  655,    0,    0,    0,    0,
  650,    0,    0,    0,    0, -118,   -3,  689,  261,   47,
    0,  157,  129,  494,  -27,  296,  729,    2,  -32,   11,
    0,    0,  184, -156,    0,  183,    0,    0, -155,    0,
    0,    0,  -84,
};
final static int YYTABLESIZE=981;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         84,
   84,   84,   84,   84,  200,   84,   51,  212,   73,   39,
  149,  166,   84,   36,   32,  187,  118,   84,   84,   84,
   84,  215,    1,   58,   76,   41,   76,   76,   76,   97,
  103,   98,  227,  113,   40,  104,  175,   74,    2,   61,
   81,  209,   76,   76,   76,   76,   78,  112,   34,   77,
  220,   77,   77,   77,   60,  189,  163,   86,   87,  164,
   36,  110,   65,  234,  136,  137,  116,   77,   77,   77,
   77,   97,   66,   98,   74,  151,   74,   74,   74,  172,
    8,    9,  173,   84,  147,  186,    8,    9,  173,   68,
  152,   79,   74,   74,   74,   74,   37,  102,   80,   75,
  102,   75,   75,   75,  201,   85,  101,  202,   76,  101,
   88,  123,  141,  142,  143,  213,   89,   75,   75,   75,
   75,   36,  152,  106,   33,  192,  122,   69,  109,   47,
   45,  124,   46,   77,   48,  176,  125,   97,  126,   98,
  190,  191,   33,   37,  127,  152,  139,  140,  128,   30,
  129,   51,   44,   69,   47,   45,    6,   46,   74,   48,
    7,  145,  146,   10,   72,  130,  131,   30,  132,   12,
   51,  174,  150,  157,  155,  162,  167,  170,  171,  177,
  168,  183,   72,   75,  196,  197,  203,  210,   69,  216,
   47,   45,  217,   46,   44,   48,   47,   45,  218,   46,
  224,   48,  228,  199,   37,  148,  165,  230,  238,  190,
  191,   64,   46,  240,   84,   84,   84,   84,   84,  232,
   84,   84,   84,   84,  211,   84,   84,   84,   84,   84,
  190,  191,   84,  117,   84,   84,   63,   84,  214,   84,
   76,   76,   76,   76,  102,   76,   76,   76,   76,  226,
   76,   76,   76,   76,   76,    8,    9,   76,   77,   76,
   76,   40,   76,  244,   76,   77,   77,   77,   77,  245,
   77,   77,   77,   77,   10,   77,   77,   77,   77,   77,
    5,   35,   77,   96,   77,   77,   39,   77,   36,   77,
   74,   74,   74,   74,   95,   74,   74,   74,   74,  112,
   74,   74,   74,   74,   74,    8,   27,   74,   35,   74,
   74,  219,   74,  101,   74,   75,   75,   75,   75,  182,
   75,   75,   75,   75,   64,   75,   75,   75,   75,   75,
  190,  191,   75,  169,   75,   75,   62,   75,   34,   75,
   33,   33,   35,  107,   42,   43,   33,   33,   33,  237,
   33,   33,   33,   33,   33,  188,    0,   33,   67,   33,
   33,    0,   33,    0,   33,   30,   30,  108,    0,   42,
   43,   30,   30,   30,  105,   30,   30,   30,   30,   30,
   72,    0,   30,    0,   30,   30,   72,   30,    0,   30,
   72,   72,   72,   72,    0,   12,  151,   72,    0,   72,
   72,    0,   72,  159,   72,   42,   43,    8,    9,   64,
    0,   42,   43,    0,    0,   64,    8,    9,  179,   64,
   64,   64,   64,  236,   42,   43,   64,    0,   64,   64,
    0,   64,    0,   64,   63,    0,    0,    0,    0,  247,
   63,    0,    0,   28,   63,   63,   63,   63,    0,    0,
    0,   63,   47,   63,   63,   46,   63,   48,   63,   40,
    0,   69,    0,   47,   45,   40,   46,    0,   48,   40,
   40,   40,   40,   95,   94,   96,   40,    0,   40,   40,
    0,   40,    0,   40,   39,    0,   47,   45,    0,   46,
   39,   48,    0,    0,   39,   39,   39,   39,    0,    0,
    0,   39,    0,   39,   39,   27,   39,    0,   39,    5,
    0,   27,   27,    0,    0,    6,    0,    0,   27,    7,
    8,    9,   10,    0,   97,    0,   98,    0,   12,   13,
    0,  204,    0,   14,    5,    0,    0,    0,    0,    0,
    6,   95,   94,   96,    7,    8,    9,   10,    0,   17,
   33,    0,    0,   12,   13,    0,    5,    0,   14,   59,
    0,    0,    6,    0,    0,    0,    7,    8,    9,   10,
    0,    0,    5,    0,    0,   12,   13,    0,    6,    0,
   14,    0,    7,    8,    9,   10,    0,    0,    0,    0,
    0,   12,   13,   12,    0,    0,   14,    0,    0,   12,
    0,  129,    0,   12,   12,   12,   12,    6,    0,    0,
  115,    7,   12,   12,   10,  115,  129,   12,  158,    0,
   12,  129,    6,    0,    0,    0,    7,    6,    0,   10,
    0,    7,    0,  178,   10,   12,    0,  129,  235,    0,
   12,    0,   28,    6,    0,    0,    0,    7,   28,   28,
   10,    0,    0,  115,  246,   28,   12,    0,    0,   49,
    0,    0,    0,    0,  129,    0,    0,   42,   43,  100,
    6,    0,   91,   92,    7,   93,    0,   10,   42,   43,
  131,    0,  181,   12,   49,   49,   49,    0,    0,    0,
    0,    0,    0,   49,   49,    0,   49,   49,   52,    0,
    0,   42,   43,    0,    0,  225,    0,    0,  229,   49,
  231,    0,    0,    0,   49,    0,    0,  239,   49,    0,
  241,  242,   49,  243,    0,   76,    0,    0,    0,    0,
    0,    0,   82,  248,    0,    0,    0,   90,   54,   49,
   91,   92,  133,   93,    0,  205,   49,   49,   49,  129,
    0,   49,   49,   49,  205,    6,    0,  120,    0,    7,
    0,  121,   10,   54,   54,   54,  194,  205,   12,    0,
    0,    0,   54,   54,    0,   54,   54,   49,  135,    0,
    0,    0,    0,    0,    0,  160,  129,  138,   54,    0,
    0,    0,    6,   54,    0,    0,    7,   54,    0,   10,
    5,   54,  221,    0,   49,   12,    6,    0,    0,    0,
    7,    8,    9,   10,    0,  180,  156,  133,   54,   12,
   13,    0,  204,   49,   14,   54,   54,   54,    5,    0,
   54,   54,   54,  195,    6,    0,  129,    0,    7,    8,
    9,   10,    6,   52,    0,  233,    7,   12,   13,   10,
  204,    0,   14,  249,    0,   12,   54,    0,    0,    0,
    0,    0,   52,    0,  222,    0,  222,   49,    0,  222,
    0,  222,    0,    0,    0,  160,    0,    0,  222,    0,
    0,  222,  222,   54,  222,    0,    0,    5,    0,    0,
    0,  180,    0,    6,  222,    0,    0,    7,    8,    9,
   10,  195,   54,   11,    5,    0,   12,   13,    0,    0,
    6,   14,    0,    0,    7,    8,    9,   10,    0,    5,
    0,  114,    0,   12,   13,    6,    0,    0,   14,    7,
    8,    9,   10,    0,    0,    0,  119,    0,   12,   13,
    0,    5,    0,   14,    0,    0,   54,    6,    0,    0,
    0,    7,    8,    9,   10,    0,    5,    0,  144,    0,
   12,   13,    6,    0,    0,   14,    7,    8,    9,   10,
    0,    0,    0,    0,    0,   12,   13,    0,    0,    0,
   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   59,   47,   10,   41,   36,  256,
   62,   62,   45,   40,    2,  172,   40,   59,   60,   61,
   62,   41,  257,   11,   41,   40,   43,   44,   45,   43,
   42,   45,   41,   61,  281,   47,  155,   36,  273,  266,
   44,  198,   59,   60,   61,   62,   44,   60,  257,   41,
  206,   43,   44,   45,   40,  174,  272,   47,   48,  275,
   40,   60,  261,  219,   97,   98,   65,   59,   60,   61,
   62,   43,  278,   45,   41,  257,   43,   44,   45,   41,
  268,  269,   44,  125,  112,   41,  268,  269,   44,  274,
  118,   59,   59,   60,   61,   62,  123,   41,  257,   41,
   44,   43,   44,   45,  189,  258,   41,  192,  125,   44,
  271,  125,  102,  103,  104,  200,   41,   59,   60,   61,
   62,   40,  150,  274,   41,   59,   41,   40,   41,   42,
   43,  257,   45,  125,   47,   41,  257,   43,  261,   45,
  264,  265,   59,  123,   41,  173,  100,  101,   44,   41,
  257,  155,   40,   40,   42,   43,  263,   45,  125,   47,
  267,   41,   41,  270,   41,  272,  273,   59,  275,  276,
  174,   59,   40,  275,  258,   59,  257,  257,  257,  274,
   41,  257,   59,  125,  275,  273,  258,   41,   40,  258,
   42,   43,   41,   45,   40,   47,   42,   43,   40,   45,
   41,   47,   41,  258,  123,  257,  257,   41,   59,  264,
  265,   59,   45,   41,  256,  257,  258,  259,  260,  218,
  262,  263,  264,  265,  258,  267,  268,  269,  270,  271,
  264,  265,  274,  257,  276,  277,   59,  279,  258,  281,
  257,  258,  259,  260,  256,  262,  263,  264,  265,  258,
  267,  268,  269,  270,  271,  268,  269,  274,  256,  276,
  277,   59,  279,   41,  281,  257,  258,  259,  260,  274,
  262,  263,  264,  265,    0,  267,  268,  269,  270,  271,
    0,  261,  274,  278,  276,  277,   59,  279,   44,  281,
  257,  258,  259,  260,  278,  262,  263,  264,  265,   41,
  267,  268,  269,  270,  271,    0,   59,  274,   44,  276,
  277,   59,  279,   53,  281,  257,  258,  259,  260,  163,
  262,  263,  264,  265,   29,  267,  268,  269,  270,  271,
  264,  265,  274,  150,  276,  277,   59,  279,  257,  281,
  257,  258,  261,  256,  257,  258,  263,  264,  265,  221,
  267,  268,  269,  270,  271,  173,   -1,  274,   59,  276,
  277,   -1,  279,   -1,  281,  257,  258,  280,   -1,  257,
  258,  263,  264,  265,   59,  267,  268,  269,  270,  271,
  257,   -1,  274,   -1,  276,  277,  263,  279,   -1,  281,
  267,  268,  269,  270,   -1,   59,  257,  274,   -1,  276,
  277,   -1,  279,   59,  281,  257,  258,  268,  269,  257,
   -1,  257,  258,   -1,   -1,  263,  268,  269,   59,  267,
  268,  269,  270,   59,  257,  258,  274,   -1,  276,  277,
   -1,  279,   -1,  281,  257,   -1,   -1,   -1,   -1,   59,
  263,   -1,   -1,   59,  267,  268,  269,  270,   -1,   -1,
   -1,  274,   42,  276,  277,   45,  279,   47,  281,  257,
   -1,   40,   -1,   42,   43,  263,   45,   -1,   47,  267,
  268,  269,  270,   60,   61,   62,  274,   -1,  276,  277,
   -1,  279,   -1,  281,  257,   -1,   42,   43,   -1,   45,
  263,   47,   -1,   -1,  267,  268,  269,  270,   -1,   -1,
   -1,  274,   -1,  276,  277,  258,  279,   -1,  281,  257,
   -1,  264,  265,   -1,   -1,  263,   -1,   -1,  271,  267,
  268,  269,  270,   -1,   43,   -1,   45,   -1,  276,  277,
   -1,  279,   -1,  281,  257,   -1,   -1,   -1,   -1,   -1,
  263,   60,   61,   62,  267,  268,  269,  270,   -1,    1,
    2,   -1,   -1,  276,  277,   -1,  257,   -1,  281,   11,
   -1,   -1,  263,   -1,   -1,   -1,  267,  268,  269,  270,
   -1,   -1,  257,   -1,   -1,  276,  277,   -1,  263,   -1,
  281,   -1,  267,  268,  269,  270,   -1,   -1,   -1,   -1,
   -1,  276,  277,  257,   -1,   -1,  281,   -1,   -1,  263,
   -1,  257,   -1,  267,  268,  269,  270,  263,   -1,   -1,
   62,  267,  276,  277,  270,   67,  257,  281,  274,   -1,
  276,  257,  263,   -1,   -1,   -1,  267,  263,   -1,  270,
   -1,  267,   -1,  274,  270,  276,   -1,  257,  274,   -1,
  276,   -1,  258,  263,   -1,   -1,   -1,  267,  264,  265,
  270,   -1,   -1,  105,  274,  271,  276,   -1,   -1,   10,
   -1,   -1,   -1,   -1,  257,   -1,   -1,  257,  258,  256,
  263,   -1,  259,  260,  267,  262,   -1,  270,  257,  258,
  273,   -1,  275,  276,   35,   36,   37,   -1,   -1,   -1,
   -1,   -1,   -1,   44,   45,   -1,   47,   48,   10,   -1,
   -1,  257,  258,   -1,   -1,  212,   -1,   -1,  215,   60,
  217,   -1,   -1,   -1,   65,   -1,   -1,  224,   69,   -1,
  227,  228,   73,  230,   -1,   37,   -1,   -1,   -1,   -1,
   -1,   -1,   44,  240,   -1,   -1,   -1,  256,   10,   90,
  259,  260,   88,  262,   -1,  197,   97,   98,   99,  257,
   -1,  102,  103,  104,  206,  263,   -1,   69,   -1,  267,
   -1,   73,  270,   35,   36,   37,  274,  219,  276,   -1,
   -1,   -1,   44,   45,   -1,   47,   48,  128,   90,   -1,
   -1,   -1,   -1,   -1,   -1,  131,  257,   99,   60,   -1,
   -1,   -1,  263,   65,   -1,   -1,  267,   69,   -1,  270,
  257,   73,  273,   -1,  155,  276,  263,   -1,   -1,   -1,
  267,  268,  269,  270,   -1,  161,  128,  163,   90,  276,
  277,   -1,  279,  174,  281,   97,   98,   99,  257,   -1,
  102,  103,  104,  179,  263,   -1,  257,   -1,  267,  268,
  269,  270,  263,  155,   -1,  274,  267,  276,  277,  270,
  279,   -1,  281,  274,   -1,  276,  128,   -1,   -1,   -1,
   -1,   -1,  174,   -1,  210,   -1,  212,  218,   -1,  215,
   -1,  217,   -1,   -1,   -1,  221,   -1,   -1,  224,   -1,
   -1,  227,  228,  155,  230,   -1,   -1,  257,   -1,   -1,
   -1,  237,   -1,  263,  240,   -1,   -1,  267,  268,  269,
  270,  247,  174,  273,  257,   -1,  276,  277,   -1,   -1,
  263,  281,   -1,   -1,  267,  268,  269,  270,   -1,  257,
   -1,  274,   -1,  276,  277,  263,   -1,   -1,  281,  267,
  268,  269,  270,   -1,   -1,   -1,  274,   -1,  276,  277,
   -1,  257,   -1,  281,   -1,   -1,  218,  263,   -1,   -1,
   -1,  267,  268,  269,  270,   -1,  257,   -1,  274,   -1,
  276,  277,  263,   -1,   -1,  281,  267,  268,  269,  270,
   -1,   -1,   -1,   -1,   -1,  276,  277,   -1,   -1,   -1,
  281,
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
"condicion_2 : patron comparador patron",
"condicion_2 : patron error patron",
"condicion_2 : expresion_matematica error expresion_matematica",
"patron : '(' lista_patron ',' expresion_matematica ')'",
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
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"termino : termino error factor",
"termino : '*' factor",
"termino : '/' factor",
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
"invoc_fun : ID '(' param_real ')'",
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
};

//#line 299 "gramatica.y"
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
	String prueba= "PruebaGramaticaErrores";
	TablaSimbolos tb= new TablaSimbolos();
	Parser p = new Parser(prueba,tb);
	int valido = p.yyparse();
	System.out.println(p.lex.getListaTokens());
	System.out.println("\n" + p.estructuras);	
	System.out.println("Errores y Warnings detectados del codigo fuente :  \n" + p.errores());
	System.out.println("Contenido de la tabla de simbolos:  \n" + tb);
	if (valido == 0) {
		System.out.println("Se analizo todo el codigo fuente");
	}
	else {
		System.out.println("No se analizo completamente el codigo fuente , debido a uno o mas errores inesperados");
	}
	
}
//#line 671 "Parser.java"
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
//#line 9 "gramatica.y"
{ estructurasSintacticas("Se declaró el programa: " + val_peek(1).sval);}
break;
case 2:
//#line 11 "gramatica.y"
{ lex.addErrorSintactico("Falta el nombre del programa");}
break;
case 4:
//#line 16 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma");}
break;
case 5:
//#line 17 "gramatica.y"
{ lex.addErrorSintactico("Falta delimitador del programa");}
break;
case 7:
//#line 23 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma");}
break;
case 8:
//#line 24 "gramatica.y"
{ lex.addErrorSintactico("Falta delimitador END del programa");}
break;
case 9:
//#line 25 "gramatica.y"
{ lex.addErrorSintactico("Falta delimitador BEGIN del programa");}
break;
case 10:
//#line 26 "gramatica.y"
{ lex.addErrorSintactico("Falta delimitador BEGIN y END del programa");}
break;
case 13:
//#line 32 "gramatica.y"
{lex.addErrorSintactico("Falta punto y coma");}
break;
case 17:
//#line 41 "gramatica.y"
{	estructurasSintacticas("Se declaro la funcion, en linea: " + lex.getLineaInicial()); }
break;
case 18:
//#line 42 "gramatica.y"
{	estructurasSintacticas("Se declaro una etiqueta goto, en linea: " + lex.getLineaInicial()); }
break;
case 23:
//#line 49 "gramatica.y"
{estructurasSintacticas("Se imprimio un mensaje, en linea: " + lex.getLineaInicial()); }
break;
case 25:
//#line 51 "gramatica.y"
{estructurasSintacticas("Se llamo a una etiqueta goto, en linea: " + lex.getLineaInicial()); }
break;
case 27:
//#line 57 "gramatica.y"
{ lex.addErrorSintactico("Falta de paréntesis en la condición");}
break;
case 28:
//#line 58 "gramatica.y"
{ lex.addErrorSintactico("Falta de paréntesis derecho en la condición");}
break;
case 29:
//#line 59 "gramatica.y"
{ lex.addErrorSintactico("Falta de paréntesis izquierdo en la condición");}
break;
case 32:
//#line 65 "gramatica.y"
{ lex.addErrorSintactico("Falta comparador entre los patrones");}
break;
case 33:
//#line 66 "gramatica.y"
{ lex.addErrorSintactico("Falta comparador entre las expresiones");}
break;
case 37:
//#line 83 "gramatica.y"
{estructurasSintacticas("Se definió una sentencia de control sin else, en la linea: " + lex.getLineaInicial());}
break;
case 38:
//#line 84 "gramatica.y"
{estructurasSintacticas("Se definió una sentencia de control con else, en la linea: " + lex.getLineaInicial());}
break;
case 39:
//#line 86 "gramatica.y"
{ lex.addErrorSintactico("Falta END_IF con ELSE");}
break;
case 40:
//#line 87 "gramatica.y"
{ lex.addErrorSintactico("Falta END_IF");}
break;
case 41:
//#line 88 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de control del then");}
break;
case 42:
//#line 89 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de control del ELSE");}
break;
case 43:
//#line 90 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de control tanto en THEN como ELSE");}
break;
case 52:
//#line 104 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo del control");}
break;
case 53:
//#line 105 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo del control");}
break;
case 54:
//#line 106 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma");}
break;
case 57:
//#line 112 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de la iteracion");}
break;
case 58:
//#line 113 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de la iteracion");}
break;
case 59:
//#line 114 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma");}
break;
case 62:
//#line 120 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma");}
break;
case 63:
//#line 124 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 64:
//#line 126 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 65:
//#line 129 "gramatica.y"
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
case 66:
//#line 138 "gramatica.y"
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
case 67:
//#line 147 "gramatica.y"
{ lex.addErrorSintactico("Falta coma en la lista de variables, puede haber parado la compilacion en este punto");}
break;
case 70:
//#line 155 "gramatica.y"
{estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 71:
//#line 156 "gramatica.y"
{estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 77:
//#line 170 "gramatica.y"
{ lex.addErrorSintactico("Falta operando izquierdo");}
break;
case 81:
//#line 180 "gramatica.y"
{ lex.addErrorSintactico("Falta operador en el término");}
break;
case 82:
//#line 181 "gramatica.y"
{ lex.addErrorSintactico("Falta operando izquierdo");}
break;
case 83:
//#line 182 "gramatica.y"
{ lex.addErrorSintactico("Falta operando izquierdo");}
break;
case 91:
//#line 201 "gramatica.y"
{ this.cantRetornos.add(0); }
break;
case 92:
//#line 201 "gramatica.y"
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
case 93:
//#line 212 "gramatica.y"
{ lex.addErrorSintactico("Falta nombre de la funcion declarada");}
break;
case 94:
//#line 213 "gramatica.y"
{ lex.addErrorSintactico("Falta el parametro en la declaracion de la funcion");}
break;
case 97:
//#line 220 "gramatica.y"
{ lex.addErrorSintactico("Se declaró más de un parametro");}
break;
case 99:
//#line 223 "gramatica.y"
{estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 100:
//#line 224 "gramatica.y"
{estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 101:
//#line 226 "gramatica.y"
{ lex.addErrorSintactico("Falta el nombre del parametro");}
break;
case 102:
//#line 227 "gramatica.y"
{ lex.addErrorSintactico("Falta el nombre del parametro o el tipo");}
break;
case 103:
//#line 230 "gramatica.y"
{ts.addClave(yylval.sval);}
break;
case 107:
//#line 239 "gramatica.y"
{lex.addErrorSintactico("Falta punto y coma");}
break;
case 110:
//#line 246 "gramatica.y"
{ this.cantRetornos.set(this.cantRetornos.size()-1, this.cantRetornos.get(this.cantRetornos.size()-1) + 1); }
break;
case 111:
//#line 249 "gramatica.y"
{estructurasSintacticas("Se invocó a la función: " + val_peek(3).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 115:
//#line 261 "gramatica.y"
{ lex.addErrorSintactico("Falta el mensaje del OUTF");}
break;
case 116:
//#line 262 "gramatica.y"
{ lex.addErrorSintactico("Parámetro invalido del OUTF");}
break;
case 119:
//#line 269 "gramatica.y"
{estructurasSintacticas("Se declaró un bucle FOR en la linea: " + lex.getLineaInicial());}
break;
case 120:
//#line 271 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma entre condicion y avance");}
break;
case 121:
//#line 272 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma entre asignacion y condicion");}
break;
case 122:
//#line 273 "gramatica.y"
{ lex.addErrorSintactico("Faltan todos los punto y coma del for");}
break;
case 123:
//#line 274 "gramatica.y"
{ lex.addErrorSintactico("Falta UP/DOWN");}
break;
case 124:
//#line 275 "gramatica.y"
{lex.addErrorSintactico("Falta valor del UP/DOWN");}
break;
case 125:
//#line 276 "gramatica.y"
{ lex.addErrorSintactico("Falta UP/DOWN y punto y coma entre condicion y avance");}
break;
case 126:
//#line 277 "gramatica.y"
{ lex.addErrorSintactico("Falta valor del UP/DOWN y punto y coma entre condicion y avance");}
break;
case 127:
//#line 278 "gramatica.y"
{ { lex.addErrorSintactico("Falta UP/DOWN, su valor, y punto y coma entre condicion y avance");}}
break;
case 131:
//#line 287 "gramatica.y"
{lex.addErrorSintactico("falta la etiqueta en el GOTO, en caso de faltar también el punto y coma es posible que no compile el resto del programa o lo haga mal.");}
break;
case 132:
//#line 292 "gramatica.y"
{System.out.println("Se declaró un tipo TRIPLE con el ID: " + val_peek(0).sval + " en la linea:" + lex.getLineaInicial());}
break;
case 133:
//#line 294 "gramatica.y"
{lex.addErrorSintactico("falta < en la declaración del TRIPLE"); }
break;
case 134:
//#line 295 "gramatica.y"
{lex.addErrorSintactico("falta > en la declaración del TRIPLE"); }
break;
case 135:
//#line 296 "gramatica.y"
{lex.addErrorSintactico("falta > y < en la declaración del TRIPLE"); }
break;
//#line 1134 "Parser.java"
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
