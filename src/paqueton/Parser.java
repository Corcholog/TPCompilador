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
//#line 20 "Parser.java"




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
    0,    0,    1,    1,    1,    1,    2,    2,    2,    3,
    3,    4,    4,    4,    4,    5,    5,    5,    5,    5,
    5,   15,   15,   16,   16,   16,   19,   20,   20,   11,
   11,   11,   11,   11,   11,   11,   18,   18,   18,   18,
   18,   18,   21,   21,   21,   21,   21,   23,   23,   23,
   23,   23,   22,   22,   22,    6,    6,   25,   25,   24,
   26,   26,    9,    9,   28,   28,   17,   17,   17,   29,
   29,   29,   30,   30,   30,   30,   31,   31,   27,    7,
    7,    7,   32,   32,   34,   34,   34,   34,   35,   33,
   36,   36,   36,   37,   37,   38,   10,   39,   39,   12,
   12,   40,   40,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   41,   41,   14,    8,    8,    8,    8,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    3,    2,    3,    1,    2,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    3,    2,    3,    3,    2,    3,    3,    1,    5,
    7,    6,    4,    4,    6,    5,    1,    1,    1,    1,
    1,    1,    4,    2,    3,    2,    3,    4,    2,    3,
    2,    3,    3,    1,    2,    2,    2,    3,    1,    1,
    1,    1,    3,    3,    1,    1,    3,    3,    1,    3,
    3,    1,    1,    1,    1,    1,    1,    2,    4,    7,
    6,    6,    3,    1,    2,    2,    1,    1,    0,    5,
    3,    1,    2,    1,    1,    4,    4,    2,    1,    4,
    3,    1,    1,   12,   11,   11,   10,   11,   11,   10,
   10,   10,    1,    1,    2,    6,    5,    5,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   62,   61,    0,    0,    0,    0,   14,
    0,    2,    0,    8,   10,   11,   12,   13,   15,   16,
   17,   18,   19,   20,   21,    0,    0,    0,    0,    0,
    0,    0,    1,    0,  115,    0,    0,    0,    0,    0,
    0,    0,    0,    9,   59,    0,    0,    0,    0,   77,
    0,    0,   75,   66,    0,    0,   76,   64,    0,   72,
   74,    0,   60,   99,    0,    0,    0,    0,    0,    0,
    0,    0,  103,  101,  102,    0,    0,    0,    5,    7,
    0,    0,   63,    0,    0,   78,   37,   38,   39,   40,
   41,   42,    0,    0,    0,    0,   26,    0,    0,    0,
   97,   79,   58,    0,   22,    0,    0,    0,   34,    0,
    0,    3,  100,    0,  119,    0,    0,    0,    0,    0,
   84,   27,    0,    0,    0,    0,   25,   70,   71,    0,
   36,   46,    0,   54,    0,   44,    0,   30,  118,    0,
  117,   89,    0,   86,   85,   89,    0,    0,    0,    0,
   45,   47,    0,   55,   35,    0,  116,   82,    0,   89,
   81,   83,    0,  113,  114,    0,    0,   43,   53,   31,
    0,   80,    0,    0,    0,    0,    0,    0,   95,    0,
   92,   94,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   93,    0,    0,  110,    0,  112,    0,    0,
    0,  111,    0,  107,    0,   90,   91,   51,    0,    0,
   49,  108,    0,  109,  105,  106,   96,   50,   52,    0,
  104,   48,
};
final static short yydgoto[] = {                         11,
   12,   13,   14,   15,   16,   17,   18,   19,   20,   21,
   22,   23,   24,   25,   38,   54,   55,   95,   56,   85,
  111,  135,  196,   26,   34,   27,   28,   58,   59,   60,
   61,  120,  158,  121,  159,  180,  181,  182,   65,   76,
  167,
};
final static short yysindex[] = {                       380,
  -40, -270,  -25,    0,    0,   17,  495,   37, -191,    0,
    0,    0,  254,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -169, -171, -147,   83,   88,
  132,  125,    0,   72,    0, -128,   88, -140,   83,  276,
  -23,  -54,  441,    0,    0,   72,  -35,   88,  -32,    0,
  125, -126,    0,    0,  408,  113,    0,    0,   -6,    0,
    0,  125,    0,    0,   95,  -12, -120, -123,  101,  408,
  -79,  457,    0,    0,    0,  105, -176,  -53,    0,    0,
  107, -205,    0,   23,   -2,    0,    0,    0,    0,    0,
    0,    0,  125,  125,  125,  108,    0,  125,  125,   23,
    0,    0,    0, -108,    0,  -21, -124,  -58,    0,   90,
 -214,    0,    0,  -50,    0, -103,  -37, -100,  -95,   40,
    0,    0,  125,   -6,   -6,   23,    0,    0,    0,  -24,
    0,    0, -110,    0,  262,    0,  308,    0,    0,  -92,
    0,    0,   41,    0,    0,    0, -205,   23,   17,  127,
    0,    0,  503,    0,    0, -106,    0,    0, -102,    0,
    0,    0,   68,    0,    0, -166,  -91,    0,    0,    0,
  472,    0,  138,  -39,  -34,  -78,  140,  126,    0,  179,
    0,    0,  511,  141,  511,   -7,  142,  511,  146,  511,
   88,  355,    0,  292,  130,    0,  511,    0,  149,  511,
  511,    0,  511,    0,  151,    0,    0,    0,  -74,  306,
    0,    0,  511,    0,    0,    0,    0,    0,    0,  523,
    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -62,    0,  117,    0,
    0,    0,    0,  204,    0,    0,    0,    0,    0,    0,
    0,    0,  201,    0,    0,  229,    0,    0,  -15,    0,
    0,    0,    0,    0,  100,    0,    0,    0,   10,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  224,    0,
    0,  202,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   45,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  162,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  139,    0,    0,    0,    0,    0,    0,   46,   67,    0,
    0,    0,    0,   35,   60,   85,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   71,    0,    0,
    0,    0,    0,    0,    0,  154,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
  207,  203,  419,    0,  493,    0,    0,    0,    0,  510,
    0,    0,    0,    0, -109,  174,   11,  183,  -46,    0,
  109,   64,  -45,   -8,  234,  -17,  565,  -28,   24,   26,
    0,  148, -122,  124,    0,    0, -143,    0,    0,    0,
 -107,
};
final static int YYTABLESIZE=799;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         31,
  133,  185,   64,  142,   82,   77,  188,   31,  116,   97,
   35,  140,   75,   63,   36,   37,   51,   74,   31,   83,
  150,   52,   62,  161,   78,   73,   73,   73,   73,   73,
   93,   73,   94,  200,  149,   98,  193,  172,  122,  163,
   99,  123,   66,   73,   73,   73,   73,   70,  207,  127,
   69,  118,   69,   69,   69,  175,   37,  137,  176,  114,
  138,   84,    4,    5,   63,   93,  186,   94,   69,   69,
   69,   69,  100,  119,   42,   67,   41,   67,   67,   67,
  146,  160,   32,  147,  147,   29,   88,   45,   29,   88,
   32,    4,    5,   67,   67,   67,   67,  164,  165,   63,
   68,   32,   68,   68,   68,  126,   47,   87,  119,   73,
   87,   28,  102,   48,   28,   67,  124,  125,   68,   68,
   68,   68,   31,  128,  129,   24,  174,   51,   68,   63,
   71,   86,   52,  148,   69,  101,  103,  104,  119,  198,
   65,  105,  202,   24,  204,  113,  117,   51,  136,  130,
  131,  212,   51,  141,  214,  215,  144,  216,   65,   67,
   59,  145,  205,  151,  157,  191,  177,  221,  170,   52,
  171,   51,   91,   90,   92,   59,   52,  106,  183,  189,
  190,  197,  201,    2,   68,  166,  203,    3,  211,  213,
    6,  217,  107,  108,   60,  109,    8,   33,  106,  218,
    6,    4,   98,  115,    2,   32,  139,   33,    3,   40,
   69,    6,   32,    4,    5,  132,   29,    8,  184,  118,
   30,   81,    2,  187,  164,  165,    3,    4,    5,    6,
    4,    5,    7,   49,   50,    8,    9,  192,   96,   30,
   10,   73,   73,   73,   73,  156,   73,   73,   73,   73,
  199,   73,   73,   73,   73,   73,   73,  210,   73,   46,
   73,   73,   57,   73,  143,   73,   69,   69,   69,   69,
  162,   69,   69,   69,   69,    0,   69,   69,   69,   69,
   69,    0,   23,   69,    0,   69,   69,   56,   69,    0,
   69,   67,   67,   67,   67,    0,   67,   67,   67,   67,
    0,   67,   67,   67,   67,   67,    0,    0,   67,    0,
   67,   67,   43,   67,    0,   67,   68,   68,   68,   68,
  153,   68,   68,   68,   68,  173,   68,   68,   68,   68,
   68,  164,  165,   68,   72,   68,   68,    0,   68,   45,
   68,   24,   24,   30,   49,   50,    0,   24,   24,   24,
  209,   24,   24,   24,   24,   24,   65,    0,   24,    0,
   24,   24,   65,   24,  220,   24,   65,   65,   65,   65,
    0,   87,   88,   65,   89,   65,   65,    0,   65,   59,
   65,   49,   50,   59,   59,   59,   59,    0,   49,   50,
  164,  165,   59,   59,    0,   33,    0,   59,    0,    4,
    5,   33,    0,    0,    0,   33,   33,   33,   33,    0,
   32,    0,   33,    0,   33,   33,   32,   33,    0,   33,
   32,   32,   32,   32,    0,    0,    0,   32,    0,   32,
   32,   44,   32,    0,   32,   39,    0,    0,    0,    0,
    0,    2,    0,    0,    0,    3,    4,    5,    6,    0,
   93,    0,   94,    0,    8,    9,    0,  178,   44,   10,
   57,   80,    0,    0,    0,    0,   57,   91,   90,   92,
   57,   57,   57,   57,    0,    0,    0,    0,    0,   57,
   57,   23,   57,    0,   57,   56,    0,   23,   23,    0,
   80,   56,    0,    0,   23,   56,   56,   56,   56,    0,
    0,    0,    0,    0,   56,   56,    0,   56,    0,   56,
   39,    0,    0,    0,    0,    0,    2,    0,  106,    0,
    3,    4,    5,    6,    2,    0,    0,    0,    3,    8,
    9,    6,   39,    0,   10,  152,    0,    8,    2,   53,
   53,   53,    3,    4,    5,    6,   53,    0,  106,    0,
   53,    8,    9,    0,    2,    0,   10,   53,    3,    0,
   53,    6,  106,  110,  106,  208,    0,    8,    2,    0,
    2,   53,    3,    0,    3,    6,    0,    6,    0,  219,
  108,    8,  155,    8,    0,    0,    0,    0,    0,  179,
    0,    0,    0,    0,   57,   57,   57,    0,  179,    0,
  134,   57,   53,   53,   53,   57,    0,   53,   53,    0,
  179,   39,   57,    0,    0,   57,    0,    2,    0,    0,
    0,    3,    4,    5,    6,    0,   57,  154,  206,  110,
    8,    9,   53,  178,    0,   10,    1,    0,    0,    0,
    0,    0,    2,    0,    0,  169,    3,    4,    5,    6,
    0,    0,    7,    0,    0,    8,    9,   57,   57,   57,
   10,    0,   57,   57,    0,    0,   87,   88,    0,   89,
    0,    0,    0,    0,    0,  195,    0,  195,    0,    0,
  195,    0,  195,    0,    0,    0,  134,   57,    0,  195,
    0,    0,  195,  195,    0,  195,    0,   39,    0,    0,
   53,    0,  154,    2,    0,  195,    0,    3,    4,    5,
    6,    0,  169,   39,   79,    0,    8,    9,    0,    2,
    0,   10,    0,    3,    4,    5,    6,    0,   39,    0,
  112,    0,    8,    9,    2,    0,    0,   10,    3,    4,
    5,    6,    0,    0,    0,    0,    0,    8,    9,    0,
  178,   39,   10,    0,    0,   57,    0,    2,    0,  106,
    0,    3,    4,    5,    6,    2,    0,  106,    0,    3,
    8,    9,    6,    2,    0,   10,  168,    3,    8,  106,
    6,    0,    0,  194,    0,    2,    8,    0,    0,    3,
    0,    0,    6,    0,    0,    0,  222,    0,    8,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   59,   41,   31,   41,   40,   60,   41,   40,   62,   56,
  281,   62,   41,   31,   40,   40,   40,   41,   40,   48,
  130,   45,   31,  146,   42,   41,   42,   43,   44,   45,
   43,   47,   45,   41,   59,   42,  180,  160,   41,  149,
   47,   44,   32,   59,   60,   61,   62,   37,  192,   96,
   41,  257,   43,   44,   45,  163,   40,  272,  166,   77,
  275,   51,  268,  269,   82,   43,  174,   45,   59,   60,
   61,   62,   62,   82,  266,   41,   40,   43,   44,   45,
   41,   41,  123,   44,   44,   41,   41,  257,   44,   44,
  123,  268,  269,   59,   60,   61,   62,  264,  265,  117,
   41,  123,   43,   44,   45,   95,  278,   41,  117,  125,
   44,   41,  125,  261,   44,   44,   93,   94,   59,   60,
   61,   62,   40,   98,   99,   41,   59,   40,  257,  147,
  271,  258,   45,  123,  125,   41,  257,  261,  147,  185,
   41,   41,  188,   59,  190,   41,   40,   40,   59,  258,
  275,  197,   40,  257,  200,  201,  257,  203,   59,  125,
   44,  257,  191,  274,  257,   40,  258,  213,  275,   45,
  273,   40,   60,   61,   62,   59,   45,  257,   41,  258,
   41,   41,   41,  263,  125,   59,   41,  267,   59,   41,
  270,   41,  272,  273,  257,  275,  276,   59,  257,  274,
    0,    0,   41,  257,  263,  123,  257,    1,  267,    7,
   37,  270,   59,  268,  269,  274,  257,  276,  258,  257,
  261,  257,  263,  258,  264,  265,  267,  268,  269,  270,
  268,  269,  273,  257,  258,  276,  277,   59,   56,  261,
  281,  257,  258,  259,  260,  137,  262,  263,  264,  265,
  258,  267,  268,  269,  270,  271,  280,  194,  274,   26,
  276,  277,   59,  279,  117,  281,  257,  258,  259,  260,
  147,  262,  263,  264,  265,   -1,  267,  268,  269,  270,
  271,   -1,   59,  274,   -1,  276,  277,   59,  279,   -1,
  281,  257,  258,  259,  260,   -1,  262,  263,  264,  265,
   -1,  267,  268,  269,  270,  271,   -1,   -1,  274,   -1,
  276,  277,   59,  279,   -1,  281,  257,  258,  259,  260,
   59,  262,  263,  264,  265,  258,  267,  268,  269,  270,
  271,  264,  265,  274,   59,  276,  277,   -1,  279,  257,
  281,  257,  258,  261,  257,  258,   -1,  263,  264,  265,
   59,  267,  268,  269,  270,  271,  257,   -1,  274,   -1,
  276,  277,  263,  279,   59,  281,  267,  268,  269,  270,
   -1,  259,  260,  274,  262,  276,  277,   -1,  279,  263,
  281,  257,  258,  267,  268,  269,  270,   -1,  257,  258,
  264,  265,  276,  277,   -1,  257,   -1,  281,   -1,  268,
  269,  263,   -1,   -1,   -1,  267,  268,  269,  270,   -1,
  257,   -1,  274,   -1,  276,  277,  263,  279,   -1,  281,
  267,  268,  269,  270,   -1,   -1,   -1,  274,   -1,  276,
  277,   13,  279,   -1,  281,  257,   -1,   -1,   -1,   -1,
   -1,  263,   -1,   -1,   -1,  267,  268,  269,  270,   -1,
   43,   -1,   45,   -1,  276,  277,   -1,  279,   40,  281,
  257,   43,   -1,   -1,   -1,   -1,  263,   60,   61,   62,
  267,  268,  269,  270,   -1,   -1,   -1,   -1,   -1,  276,
  277,  258,  279,   -1,  281,  257,   -1,  264,  265,   -1,
   72,  263,   -1,   -1,  271,  267,  268,  269,  270,   -1,
   -1,   -1,   -1,   -1,  276,  277,   -1,  279,   -1,  281,
  257,   -1,   -1,   -1,   -1,   -1,  263,   -1,  257,   -1,
  267,  268,  269,  270,  263,   -1,   -1,   -1,  267,  276,
  277,  270,  257,   -1,  281,  274,   -1,  276,  263,   30,
   31,   32,  267,  268,  269,  270,   37,   -1,  257,   -1,
   41,  276,  277,   -1,  263,   -1,  281,   48,  267,   -1,
   51,  270,  257,   71,  257,  274,   -1,  276,  263,   -1,
  263,   62,  267,   -1,  267,  270,   -1,  270,   -1,  274,
  273,  276,  275,  276,   -1,   -1,   -1,   -1,   -1,  171,
   -1,   -1,   -1,   -1,   30,   31,   32,   -1,  180,   -1,
  108,   37,   93,   94,   95,   41,   -1,   98,   99,   -1,
  192,  257,   48,   -1,   -1,   51,   -1,  263,   -1,   -1,
   -1,  267,  268,  269,  270,   -1,   62,  135,  274,  137,
  276,  277,  123,  279,   -1,  281,  257,   -1,   -1,   -1,
   -1,   -1,  263,   -1,   -1,  153,  267,  268,  269,  270,
   -1,   -1,  273,   -1,   -1,  276,  277,   93,   94,   95,
  281,   -1,   98,   99,   -1,   -1,  259,  260,   -1,  262,
   -1,   -1,   -1,   -1,   -1,  183,   -1,  185,   -1,   -1,
  188,   -1,  190,   -1,   -1,   -1,  194,  123,   -1,  197,
   -1,   -1,  200,  201,   -1,  203,   -1,  257,   -1,   -1,
  191,   -1,  210,  263,   -1,  213,   -1,  267,  268,  269,
  270,   -1,  220,  257,  274,   -1,  276,  277,   -1,  263,
   -1,  281,   -1,  267,  268,  269,  270,   -1,  257,   -1,
  274,   -1,  276,  277,  263,   -1,   -1,  281,  267,  268,
  269,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,   -1,
  279,  257,  281,   -1,   -1,  191,   -1,  263,   -1,  257,
   -1,  267,  268,  269,  270,  263,   -1,  257,   -1,  267,
  276,  277,  270,  263,   -1,  281,  274,  267,  276,  257,
  270,   -1,   -1,  273,   -1,  263,  276,   -1,   -1,  267,
   -1,   -1,  270,   -1,   -1,   -1,  274,   -1,  276,
};
}
final static short YYFINAL=11;
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
"prog : cuerpo",
"cuerpo : BEGIN sentencias ';' END",
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
"condicion : '(' condicion_2",
"condicion_2 : expresion_matematica comparador expresion_matematica",
"condicion_2 : patron comparador patron",
"condicion_2 : patron patron",
"patron : '(' lista_patron ')'",
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
"tipo : tipo_basico",
"tipo_basico : DOUBLE",
"tipo_basico : ULONGINT",
"asignacion : triple ASIGN expresion",
"asignacion : ID ASIGN expresion",
"expresion : expresion_matematica",
"expresion : condicion_2",
"expresion_matematica : expresion_matematica '+' termino",
"expresion_matematica : expresion_matematica '-' termino",
"expresion_matematica : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : constante",
"factor : invoc_fun",
"factor : triple",
"constante : CTE",
"constante : '-' CTE",
"triple : ID '{' expresion_matematica '}'",
"declaracion_fun : tipo_basico FUN ID '(' lista_parametro ')' cuerpo_funcion_p",
"declaracion_fun : tipo_basico FUN '(' lista_parametro ')' cuerpo_funcion_p",
"declaracion_fun : tipo_basico FUN ID '(' ')' cuerpo_funcion_p",
"lista_parametro : lista_parametro ',' parametro",
"lista_parametro : parametro",
"parametro : tipo ID",
"parametro : ID ID",
"parametro : tipo",
"parametro : ID",
"$$1 :",
"cuerpo_funcion_p : $$1 BEGIN bloques_funcion ';' END",
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
"declar_tipo_trip : TYPEDEF TRIPLE '<' tipo_basico '>' ID",
"declar_tipo_trip : TYPEDEF TRIPLE tipo_basico '>' ID",
"declar_tipo_trip : TYPEDEF TRIPLE '<' tipo_basico ID",
"declar_tipo_trip : TYPEDEF TRIPLE tipo_basico ID",
};

//#line 275 "gramatica.y"
String nombreArchivo;
AnalizadorLexico lex;
TablaSimbolos ts;
String tipoVar;
int cantRetornos;
String estructuras;
public Parser(String nombreArchivo, TablaSimbolos t)
{
	this.nombreArchivo=nombreArchivo;
	this.ts=t;
	this.cantRetornos = 0;
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
//#line 606 "Parser.java"
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
//#line 8 "gramatica.y"
{ estructurasSintacticas("Se declaró el programa: " + val_peek(1).sval);}
break;
case 2:
//#line 10 "gramatica.y"
{ lex.addErrorSintactico("Falta el nombre del programa");}
break;
case 4:
//#line 14 "gramatica.y"
{ lex.addErrorSintactico("Falta delimitador del programa");}
break;
case 5:
//#line 15 "gramatica.y"
{}
break;
case 6:
//#line 16 "gramatica.y"
{}
break;
case 9:
//#line 22 "gramatica.y"
{lex.addErrorSintactico("Falta punto y coma");}
break;
case 13:
//#line 30 "gramatica.y"
{	estructurasSintacticas("Se declaro la funcion, en linea: " + lex.getLineaInicial()); }
break;
case 14:
//#line 31 "gramatica.y"
{	estructurasSintacticas("Se declaro una etiqueta goto, en linea: " + lex.getLineaInicial()); }
break;
case 19:
//#line 38 "gramatica.y"
{estructurasSintacticas("Se imprimio un mensaje, en linea: " + lex.getLineaInicial()); }
break;
case 21:
//#line 40 "gramatica.y"
{estructurasSintacticas("Se llamo a una etiqueta goto, en linea: " + lex.getLineaInicial()); }
break;
case 23:
//#line 49 "gramatica.y"
{ lex.addErrorSintactico("Falta de paréntesis derecho en la condición");}
break;
case 30:
//#line 74 "gramatica.y"
{estructurasSintacticas("Se definió una sentencia de control sin else, en la linea: " + lex.getLineaInicial());}
break;
case 31:
//#line 75 "gramatica.y"
{estructurasSintacticas("Se definió una sentencia de control con else, en la linea: " + lex.getLineaInicial());}
break;
case 32:
//#line 77 "gramatica.y"
{ lex.addErrorSintactico("Falta END_IF con ELSE");}
break;
case 33:
//#line 78 "gramatica.y"
{ lex.addErrorSintactico("Falta END_IF");}
break;
case 34:
//#line 79 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de control del then");}
break;
case 35:
//#line 80 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de control del ELSE");}
break;
case 36:
//#line 81 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de control tanto en THEN como ELSE");}
break;
case 45:
//#line 95 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo del control")}
break;
case 46:
//#line 96 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo del control")}
break;
case 47:
//#line 97 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma");}
break;
case 50:
//#line 103 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de la iteracion")}
break;
case 51:
//#line 104 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de la iteracion")}
break;
case 52:
//#line 105 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma");}
break;
case 55:
//#line 111 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma");}
break;
case 56:
//#line 115 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 57:
//#line 116 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 58:
//#line 119 "gramatica.y"
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
case 59:
//#line 128 "gramatica.y"
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
case 63:
//#line 147 "gramatica.y"
{estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 64:
//#line 148 "gramatica.y"
{estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 80:
//#line 189 "gramatica.y"
{
										if (this.cantRetornos > 0){
											estructurasSintacticas("Se declaró la función: " + val_peek(4).sval);
											ts.addClave(val_peek(4).sval);
											ts.addAtributo(val_peek(4).sval,AccionSemantica.TIPO,AccionSemantica.FUNCION);
											ts.addAtributo(val_peek(4).sval,AccionSemantica.TIPORETORNO,tipoVar);
										}
									}
break;
case 81:
//#line 198 "gramatica.y"
{ lex.addErrorSintactico("Falta nombre de la funcion declarada");}
break;
case 82:
//#line 199 "gramatica.y"
{ lex.addErrorSintactico("Falta el parametro en la declaracion de la funcion");}
break;
case 83:
//#line 202 "gramatica.y"
{ lex.addErrorSintactico("Se declaró más de un parametro");}
break;
case 85:
//#line 205 "gramatica.y"
{estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 86:
//#line 206 "gramatica.y"
{estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 87:
//#line 208 "gramatica.y"
{ lex.addErrorSintactico("Falta el nombre del parametro");}
break;
case 88:
//#line 209 "gramatica.y"
{ lex.addErrorSintactico("Falta el nombre del parametro o el tipo");}
break;
case 89:
//#line 212 "gramatica.y"
{ts.addClave(yylval.sval);}
break;
case 93:
//#line 218 "gramatica.y"
{lex.addErrorSintactico("Falta punto y coma");}
break;
case 96:
//#line 225 "gramatica.y"
{ this.cantRetornos++; }
break;
case 97:
//#line 228 "gramatica.y"
{estructurasSintacticas("Se invocó a la función: " + val_peek(3).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 101:
//#line 240 "gramatica.y"
{ lex.addErrorSintactico("Falta el mensaje del OUTF");}
break;
case 104:
//#line 247 "gramatica.y"
{estructurasSintacticas("Se declaró un bucle FOR en la linea: " + lex.getLineaInicial());}
break;
case 105:
//#line 249 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma entre condicion y avance");}
break;
case 106:
//#line 250 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma entre asignacion y condicion");}
break;
case 107:
//#line 251 "gramatica.y"
{ lex.addErrorSintactico("Faltan todos los punto y coma del for");}
break;
case 108:
//#line 252 "gramatica.y"
{ lex.addErrorSintactico("Falta UP/DOWN");}
break;
case 109:
//#line 253 "gramatica.y"
{lex.addErrorSintactico("Falta valor del UP/DOWN");}
break;
case 110:
//#line 254 "gramatica.y"
{ lex.addErrorSintactico("Falta UP/DOWN y punto y coma entre condicion y avance");}
break;
case 111:
//#line 255 "gramatica.y"
{ lex.addErrorSintactico("Falta valor del UP/DOWN y punto y coma entre condicion y avance");}
break;
case 112:
//#line 256 "gramatica.y"
{ { lex.addErrorSintactico("Falta UP/DOWN, su valor, y punto y coma entre condicion y avance");}}
break;
case 116:
//#line 268 "gramatica.y"
{System.out.println("Se declaró un tipo TRIPLE con el ID: " + val_peek(0).sval + " en la linea:" + lex.getLineaInicial());}
break;
case 117:
//#line 270 "gramatica.y"
{lex.addErrorLexico("falta < en la declaración del TRIPLE"); }
break;
case 118:
//#line 271 "gramatica.y"
{lex.addErrorLexico("falta > en la declaración del TRIPLE"); }
break;
case 119:
//#line 272 "gramatica.y"
{lex.addErrorLexico("falta > y < en la declaración del TRIPLE"); }
break;
//#line 1006 "Parser.java"
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
