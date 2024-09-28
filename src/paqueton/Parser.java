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
    0,    0,    1,    1,    2,    2,    2,    3,    3,    4,
    4,    4,    4,    5,    5,    5,    5,    5,    5,   15,
   15,   15,   15,   16,   16,   16,   19,   20,   20,   20,
   11,   11,   11,   11,   11,   11,   11,   18,   18,   18,
   18,   18,   18,   21,   21,   21,   21,   21,   23,   23,
   23,   23,   23,   22,   22,   22,    6,    6,   25,   25,
   24,   26,   26,    9,    9,   28,   28,   17,   17,   17,
   29,   29,   29,   30,   30,   30,   30,   31,   27,    7,
    7,    7,   32,   32,   34,   34,   34,   34,   35,   33,
   36,   36,   36,   37,   37,   38,   38,   10,   39,   39,
   39,   12,   12,   40,   40,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   41,   41,   14,    8,    8,    8,
    8,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    3,    1,    2,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    3,
    1,    2,    2,    3,    3,    2,    3,    3,    1,    2,
    5,    7,    6,    4,    4,    6,    5,    1,    1,    1,
    1,    1,    1,    4,    2,    3,    2,    3,    4,    2,
    3,    2,    3,    3,    1,    2,    2,    2,    3,    1,
    1,    1,    1,    3,    3,    1,    1,    3,    3,    1,
    3,    3,    1,    1,    1,    1,    1,    1,    4,    7,
    6,    6,    3,    1,    2,    2,    1,    1,    0,    5,
    3,    1,    2,    1,    1,    4,    3,    4,    2,    1,
    2,    4,    3,    1,    1,   12,   11,   11,   10,   11,
   11,   10,   10,   10,    1,    1,    2,    6,    5,    5,
    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    1,    0,    0,    0,   63,   62,
    0,    0,    0,   12,    0,    6,    8,    9,   10,   11,
   13,   14,   15,   16,   17,   18,   19,    0,    0,    0,
   60,    0,    0,    0,    0,  117,    0,    0,   78,    0,
   76,    0,    0,    0,    0,   77,    0,   73,   75,    0,
    0,    0,    7,    0,    0,    0,    0,   67,    0,   65,
    0,    0,   61,  100,    0,    0,    0,    0,    0,    0,
    0,    0,   23,   38,   39,   40,   41,   42,   43,    0,
    0,    0,    0,   26,    0,    0,  105,  103,  104,    0,
    0,    0,    3,    5,    0,    0,   64,    0,    0,    0,
   98,   79,   59,    0,   20,   27,    0,    0,    0,    0,
    0,   35,    0,    0,    0,    0,    0,   25,   71,   72,
  102,    0,  121,    0,    0,    0,    0,    0,   84,    0,
    0,   37,   47,    0,   55,    0,   45,    0,   31,  120,
    0,  119,   89,    0,   86,   85,   89,    0,    0,    0,
   46,   48,    0,   56,   36,    0,  118,   82,    0,   89,
   81,   83,    0,  115,  116,    0,    0,   44,   54,   32,
    0,   80,    0,    0,    0,    0,    0,    0,    0,   95,
    0,   92,   94,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   93,    0,    0,  112,    0,  114,
    0,    0,    0,  113,    0,  109,    0,   97,   90,   91,
   52,    0,    0,   50,  110,    0,  111,  107,  108,   96,
   51,   53,    0,  106,   49,
};
final static short yydgoto[] = {                          3,
    4,   15,  180,   17,  197,   19,   20,   21,   22,   23,
   24,   25,   26,   27,   42,   58,   59,   82,   45,   71,
  114,  136,  198,   28,   35,   29,   30,   60,   47,   48,
   49,  128,  158,  129,  159,  181,  182,  183,   65,   90,
  167,
};
final static short yysindex[] = {                      -230,
 -258,  377,    0,    0,    0,   40, -244,   17,    0,    0,
  163,   48, -166,    0,  212,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -165, -172, -133,
    0,  242,   94, -194,   77,    0, -126,  -28,    0,  242,
    0, -134,  102,  390,   -2,    0,   14,    0,    0,  109,
  -58,  361,    0,   77,   -6,  242, -194,    0,  390,    0,
  117, -194,    0,    0,  105,   -3, -109,  -96,  127,  390,
  244, -174,    0,    0,    0,    0,    0,    0,    0, -194,
 -194, -194,  129,    0, -194, -194,    0,    0,    0,  132,
 -150,  -55,    0,    0,  136, -191,    0,   69,   69,   69,
    0,    0,    0,  -77,    0,    0, -194,   69,  -26,  -82,
  249,    0,  135, -193,   14,   14,   69,    0,    0,    0,
    0,  -45,    0,  -57,   26,  -53,  -48,   43,    0,  147,
   69,    0,    0,  -75,    0,  271,    0,  -93,    0,    0,
  -36,    0,    0,   63,    0,    0,    0, -191,  163,  -49,
    0,    0,  -79,    0,    0,  -67,    0,    0,  -35,    0,
    0,    0,  -51,    0,    0, -113,  -12,    0,    0,    0,
  161,    0,  222,  -32,    7,   18,  255,  270,  242,    0,
  101,    0,    0, -140,  274, -140,   32,  280, -140,  282,
 -140,  242,  295,  116,    0,  286,  279,    0, -140,    0,
  299, -140, -140,    0, -140,    0,  314,    0,    0,    0,
    0,   86,  306,    0,    0, -140,    0,    0,    0,    0,
    0,    0,  -78,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  115,    0,
    0,    0,    0,    0,  131,    0,    0,  402,    0,    0,
    0,    0,  266,    0,    0,    0,  -40,    0,    0,    0,
    0,  376,    0,  146,    0,    0,    0,    0,   50,    0,
  425,    0,    0,    0,    0,    0,    0,    0,  294,  433,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  433,  105,  340,
    0,    0,    0,    0,    0,    0,    0,  450,    0,    0,
    0,    0,    0,   65,  -15,   10,   35,    0,    0,    0,
    0,    0,    0,    0,    0,   67,   72,    0,    0,    0,
  454,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   80,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  386,    0,   16,    0,  404,    0,    0,    0,    0,  663,
    0,    0,    0,    0, -117,   -5,  550,  346,  -21,    0,
  258,  206,  -44,  -22,  383,  -10,  718,  -17,   73,   81,
    0,  292, -111,  278,    0,    0, -129,    0,    0,    0,
  -34,
};
final static int YYTABLESIZE=910;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         70,
   70,   91,   70,   70,   70,   43,  124,  174,  186,  166,
   62,   33,  150,   33,    2,   64,  141,   16,   70,   70,
   70,   70,   63,   84,   68,   68,    1,   68,   68,   68,
   53,  163,   89,   96,   69,  161,   36,   57,   97,   80,
   92,   81,    2,   68,   68,   68,   68,  189,  172,   69,
   69,  195,   69,   69,   69,   85,   37,   78,   77,   79,
   86,  118,   38,   39,  210,  126,  143,   94,   69,   69,
   69,   69,  202,  127,   24,   24,    9,   10,  138,   33,
  122,  139,  109,  147,   70,   63,  148,   50,    7,   66,
   66,   31,    8,   24,   34,   11,   34,  110,  111,   51,
  112,   12,  127,  160,   34,   55,  148,   88,   66,   68,
   88,   80,   87,   81,   63,   87,  109,    9,   10,   33,
   67,  102,    7,   34,   43,  127,    8,   56,  175,   11,
   68,  176,  196,   57,   69,   12,   72,   63,   33,  187,
  179,  200,   73,   43,  204,  101,  206,  103,   57,   88,
  164,  165,  115,  116,  215,  179,   33,  217,  218,  194,
  219,  193,   34,  109,  104,  119,  120,  105,   57,    7,
   58,  224,  121,    8,  207,  125,   11,  109,  109,  111,
  130,  155,   12,    7,    7,   57,   40,    8,    8,   58,
   11,   11,  132,  137,  168,  225,   12,   12,  151,  142,
  179,  123,   40,  145,   57,  149,  173,  170,  146,    9,
   10,  140,  164,  165,  164,  165,   70,   70,   70,   70,
  157,   70,   70,   70,   70,  185,   70,   70,   70,   70,
   70,  164,  165,   70,   32,   70,   70,  171,   70,   34,
   70,   68,   68,   68,   68,  177,   68,   68,   68,   68,
   95,   68,   68,   68,   68,   68,   74,   75,   68,   76,
   68,   68,  184,   68,  188,   68,   69,   69,   69,   69,
   52,   69,   69,   69,   69,  190,   69,   69,   69,   69,
   69,   57,  126,   69,  106,   69,   69,  107,   69,  201,
   69,   24,   24,    9,   10,  191,   31,   24,   24,   24,
   32,   24,   24,   24,   24,   24,   66,  134,   24,  192,
   24,   24,   66,   24,  199,   24,   66,   66,   66,   66,
  203,   34,  205,   66,   21,   66,   66,   34,   66,  153,
   66,   34,   34,   34,   34,  208,   33,  214,   34,  216,
   34,   34,   33,   34,  212,   34,   33,   33,   33,   33,
   61,   39,   22,   33,  220,   33,   33,    6,   33,  221,
   33,    9,   10,    7,  223,   38,   39,    8,    9,   10,
   11,   61,    6,   38,   39,    4,   12,   13,    7,  178,
   99,   14,    8,    9,   10,   11,    5,   58,   87,  209,
   83,   12,   13,   58,  178,  156,   14,   58,   58,   58,
   58,  213,   57,   38,   39,   18,   58,   58,   57,   58,
   54,   58,   57,   57,   57,   57,  144,    6,   18,   38,
   39,   57,   57,    7,   57,  162,   57,    8,    9,   10,
   11,    0,   80,    0,   81,    0,   12,   13,    0,  178,
    0,   14,   74,   74,   74,   74,   74,    0,   74,   78,
   77,   79,    0,    0,    0,   18,    0,    0,    0,    0,
   74,   74,   74,   74,    0,   74,   74,   74,    6,   74,
    0,   74,    0,   29,    7,  113,   29,    0,    8,    9,
   10,   11,    0,    0,   74,   74,   74,   12,   13,    0,
   30,    0,   14,   30,   28,    0,    0,   28,   38,   39,
   38,   39,    0,    0,    0,  109,    0,    0,    0,    0,
    0,    7,    0,    0,  135,    8,    0,    0,   11,    0,
    0,    0,  133,   21,   12,    0,   74,  109,    0,   21,
   21,    0,    0,    7,    0,    0,   21,    8,    0,  154,
   11,  113,  109,    0,  152,    0,   12,    0,    7,    0,
    0,   22,    8,    0,    0,   11,  169,   22,   22,  211,
   44,   12,  109,    0,   22,    0,    0,    0,    7,    0,
    0,    0,    8,    0,   18,   11,    0,    0,    0,  222,
    0,   12,    0,   66,   18,    0,    0,    0,    0,   70,
    0,    0,    0,    0,    0,    0,    0,   18,    0,  135,
    0,    0,    0,    0,    0,    0,   98,    0,    0,    0,
   99,  100,    0,    0,    0,    0,  154,    6,    0,    0,
  108,    0,    0,    7,    0,    0,  169,    8,    9,   10,
   11,  117,    0,    6,   93,    0,   12,   13,    0,    7,
    0,   14,    0,    8,    9,   10,   11,    0,   74,   75,
    0,   76,   12,   13,    0,    0,  131,   14,   74,   74,
   74,   74,    0,   74,   74,   74,   74,    0,   74,   74,
   74,   74,   74,   41,    0,   74,    0,   74,   74,   44,
   74,    0,   74,   74,   74,    0,   74,    0,    0,   29,
   29,    0,    0,    0,   41,   41,   41,    0,   44,    0,
    0,    0,   41,    0,    0,    0,   30,   30,    0,    0,
   28,   28,   41,    0,    0,    0,    0,    0,   41,   41,
    0,    0,    0,   41,   41,    0,    0,    0,   46,    0,
    0,    0,    0,   41,    0,    0,    0,    0,    0,    0,
    0,    0,   41,   41,   41,    0,    0,   41,   41,   46,
   46,   46,    0,    0,    0,    0,    0,   46,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   46,    0,   41,
    0,    0,    0,   46,   46,    0,    0,    0,   46,   46,
    0,    0,    0,    0,    0,    0,    0,    0,   46,    0,
    0,    0,   41,    0,    0,    0,    0,   46,   46,   46,
    0,    0,   46,   46,    0,    0,    0,    0,    0,    0,
    0,   41,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   46,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   41,    0,    0,    0,    0,    0,   46,    0,    0,
    0,    0,    0,    0,   41,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   46,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   46,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   46,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   41,   60,   43,   44,   45,   11,   62,   59,   41,   59,
   33,   40,  130,   40,  273,   33,   62,    2,   59,   60,
   61,   62,   33,   45,   40,   41,  257,   43,   44,   45,
   15,  149,   50,   40,   40,  147,  281,   40,   56,   43,
   51,   45,  273,   59,   60,   61,   62,   41,  160,   40,
   41,  181,   43,   44,   45,   42,   40,   60,   61,   62,
   47,   83,  257,  258,  194,  257,   41,   52,   59,   60,
   61,   62,   41,   96,   40,   41,  268,  269,  272,   40,
   91,  275,  257,   41,  125,   96,   44,   40,  263,   40,
   41,  257,  267,   59,  123,  270,  123,  272,  273,  266,
  275,  276,  125,   41,   40,  278,   44,   41,   59,  125,
   44,   43,   41,   45,  125,   44,  257,  268,  269,   40,
   44,  125,  263,   59,  130,  148,  267,  261,  163,  270,
  257,  166,  273,   40,  125,  276,  271,  148,   59,  174,
   40,  186,   41,  149,  189,   41,  191,  257,   40,   41,
  264,  265,   80,   81,  199,   40,   40,  202,  203,   59,
  205,  179,  123,  257,  261,   85,   86,   41,   40,  263,
   40,  216,   41,  267,  192,   40,  270,  257,  257,  273,
  258,  275,  276,  263,  263,   40,   40,  267,  267,   59,
  270,  270,  275,   59,  274,  274,  276,  276,  274,  257,
   40,  257,   40,  257,   59,   59,  258,  275,  257,  268,
  269,  257,  264,  265,  264,  265,  257,  258,  259,  260,
  257,  262,  263,  264,  265,  258,  267,  268,  269,  270,
  271,  264,  265,  274,  261,  276,  277,  273,  279,  123,
  281,  257,  258,  259,  260,  258,  262,  263,  264,  265,
  257,  267,  268,  269,  270,  271,  259,  260,  274,  262,
  276,  277,   41,  279,  258,  281,  257,  258,  259,  260,
   59,  262,  263,  264,  265,  258,  267,  268,  269,  270,
  271,   40,  257,  274,   41,  276,  277,   44,  279,  258,
  281,  257,  258,  268,  269,   41,  257,  263,  264,  265,
  261,  267,  268,  269,  270,  271,  257,   59,  274,   40,
  276,  277,  263,  279,   41,  281,  267,  268,  269,  270,
   41,  257,   41,  274,   59,  276,  277,  263,  279,   59,
  281,  267,  268,  269,  270,   41,  257,   59,  274,   41,
  276,  277,  263,  279,   59,  281,  267,  268,  269,  270,
  257,  258,   59,  274,   41,  276,  277,  257,  279,  274,
  281,  268,  269,  263,   59,  257,  258,  267,  268,  269,
  270,  257,  257,  257,  258,    0,  276,  277,  263,  279,
   41,  281,  267,  268,  269,  270,    1,  257,  280,  274,
   45,  276,  277,  263,  279,  138,  281,  267,  268,  269,
  270,  196,  257,  257,  258,    2,  276,  277,  263,  279,
   28,  281,  267,  268,  269,  270,  125,  257,   15,  257,
  258,  276,  277,  263,  279,  148,  281,  267,  268,  269,
  270,   -1,   43,   -1,   45,   -1,  276,  277,   -1,  279,
   -1,  281,   41,   42,   43,   44,   45,   -1,   47,   60,
   61,   62,   -1,   -1,   -1,   52,   -1,   -1,   -1,   -1,
   59,   60,   61,   62,   -1,   41,   42,   43,  257,   45,
   -1,   47,   -1,   41,  263,   72,   44,   -1,  267,  268,
  269,  270,   -1,   -1,   60,   61,   62,  276,  277,   -1,
   41,   -1,  281,   44,   41,   -1,   -1,   44,  257,  258,
  257,  258,   -1,   -1,   -1,  257,   -1,   -1,   -1,   -1,
   -1,  263,   -1,   -1,  111,  267,   -1,   -1,  270,   -1,
   -1,   -1,  274,  258,  276,   -1,  125,  257,   -1,  264,
  265,   -1,   -1,  263,   -1,   -1,  271,  267,   -1,  136,
  270,  138,  257,   -1,  274,   -1,  276,   -1,  263,   -1,
   -1,  258,  267,   -1,   -1,  270,  153,  264,  265,  274,
   11,  276,  257,   -1,  271,   -1,   -1,   -1,  263,   -1,
   -1,   -1,  267,   -1,  171,  270,   -1,   -1,   -1,  274,
   -1,  276,   -1,   34,  181,   -1,   -1,   -1,   -1,   40,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  194,   -1,  196,
   -1,   -1,   -1,   -1,   -1,   -1,   57,   -1,   -1,   -1,
   61,   62,   -1,   -1,   -1,   -1,  213,  257,   -1,   -1,
   71,   -1,   -1,  263,   -1,   -1,  223,  267,  268,  269,
  270,   82,   -1,  257,  274,   -1,  276,  277,   -1,  263,
   -1,  281,   -1,  267,  268,  269,  270,   -1,  259,  260,
   -1,  262,  276,  277,   -1,   -1,  107,  281,  257,  258,
  259,  260,   -1,  262,  263,  264,  265,   -1,  267,  268,
  269,  270,  271,   11,   -1,  274,   -1,  276,  277,  130,
  279,   -1,  281,  259,  260,   -1,  262,   -1,   -1,  257,
  258,   -1,   -1,   -1,   32,   33,   34,   -1,  149,   -1,
   -1,   -1,   40,   -1,   -1,   -1,  257,  258,   -1,   -1,
  257,  258,   50,   -1,   -1,   -1,   -1,   -1,   56,   57,
   -1,   -1,   -1,   61,   62,   -1,   -1,   -1,   11,   -1,
   -1,   -1,   -1,   71,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   80,   81,   82,   -1,   -1,   85,   86,   32,
   33,   34,   -1,   -1,   -1,   -1,   -1,   40,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   50,   -1,  107,
   -1,   -1,   -1,   56,   57,   -1,   -1,   -1,   61,   62,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   71,   -1,
   -1,   -1,  130,   -1,   -1,   -1,   -1,   80,   81,   82,
   -1,   -1,   85,   86,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  149,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  107,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  179,   -1,   -1,   -1,   -1,   -1,  130,   -1,   -1,
   -1,   -1,   -1,   -1,  192,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  149,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  179,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  192,
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
"prog : cuerpo",
"cuerpo : BEGIN sentencias ';' END",
"cuerpo : BEGIN sentencias ';'",
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
"condicion_2 : patron patron",
"patron : '(' lista_patron ')'",
"lista_patron : lista_patron ',' expresion_matematica",
"lista_patron : expresion_matematica",
"lista_patron : lista_patron expresion_matematica",
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
"retorno : '(' expresion ')'",
"invoc_fun : ID '(' param_real ')'",
"param_real : tipo expresion_matematica",
"param_real : expresion",
"param_real : ID expresion_matematica",
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

//#line 279 "gramatica.y"
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
//#line 632 "Parser.java"
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
case 7:
//#line 22 "gramatica.y"
{lex.addErrorSintactico("Falta punto y coma");}
break;
case 11:
//#line 30 "gramatica.y"
{	estructurasSintacticas("Se declaro la funcion, en linea: " + lex.getLineaInicial()); }
break;
case 12:
//#line 31 "gramatica.y"
{	estructurasSintacticas("Se declaro una etiqueta goto, en linea: " + lex.getLineaInicial()); }
break;
case 17:
//#line 38 "gramatica.y"
{estructurasSintacticas("Se imprimio un mensaje, en linea: " + lex.getLineaInicial()); }
break;
case 19:
//#line 40 "gramatica.y"
{estructurasSintacticas("Se llamo a una etiqueta goto, en linea: " + lex.getLineaInicial()); }
break;
case 21:
//#line 46 "gramatica.y"
{ lex.addErrorSintactico("Falta de paréntesis en la condición");}
break;
case 22:
//#line 47 "gramatica.y"
{ lex.addErrorSintactico("Falta de paréntesis derecho en la condición");}
break;
case 23:
//#line 48 "gramatica.y"
{ lex.addErrorSintactico("Falta de paréntesis izquierdo en la condición");}
break;
case 30:
//#line 68 "gramatica.y"
{ lex.addErrorSintactico("Falta coma en la lista de variables del pattern matching");}
break;
case 31:
//#line 71 "gramatica.y"
{estructurasSintacticas("Se definió una sentencia de control sin else, en la linea: " + lex.getLineaInicial());}
break;
case 32:
//#line 72 "gramatica.y"
{estructurasSintacticas("Se definió una sentencia de control con else, en la linea: " + lex.getLineaInicial());}
break;
case 33:
//#line 74 "gramatica.y"
{ lex.addErrorSintactico("Falta END_IF con ELSE");}
break;
case 34:
//#line 75 "gramatica.y"
{ lex.addErrorSintactico("Falta END_IF");}
break;
case 35:
//#line 76 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de control del then");}
break;
case 36:
//#line 77 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de control del ELSE");}
break;
case 37:
//#line 78 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de control tanto en THEN como ELSE");}
break;
case 46:
//#line 92 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo del control")}
break;
case 47:
//#line 93 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo del control")}
break;
case 48:
//#line 94 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma");}
break;
case 51:
//#line 100 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de la iteracion")}
break;
case 52:
//#line 101 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de la iteracion")}
break;
case 53:
//#line 102 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma");}
break;
case 56:
//#line 108 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma");}
break;
case 57:
//#line 112 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 58:
//#line 113 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 59:
//#line 116 "gramatica.y"
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
case 60:
//#line 125 "gramatica.y"
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
case 64:
//#line 144 "gramatica.y"
{estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 65:
//#line 145 "gramatica.y"
{estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 80:
//#line 192 "gramatica.y"
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
//#line 201 "gramatica.y"
{ lex.addErrorSintactico("Falta nombre de la funcion declarada");}
break;
case 82:
//#line 202 "gramatica.y"
{ lex.addErrorSintactico("Falta el parametro en la declaracion de la funcion");}
break;
case 83:
//#line 205 "gramatica.y"
{ lex.addErrorSintactico("Se declaró más de un parametro");}
break;
case 85:
//#line 208 "gramatica.y"
{estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 86:
//#line 209 "gramatica.y"
{estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 87:
//#line 211 "gramatica.y"
{ lex.addErrorSintactico("Falta el nombre del parametro");}
break;
case 88:
//#line 212 "gramatica.y"
{ lex.addErrorSintactico("Falta el nombre del parametro o el tipo");}
break;
case 89:
//#line 215 "gramatica.y"
{ts.addClave(yylval.sval);}
break;
case 93:
//#line 223 "gramatica.y"
{lex.addErrorSintactico("Falta punto y coma");}
break;
case 96:
//#line 230 "gramatica.y"
{ this.cantRetornos++;}
break;
case 97:
//#line 231 "gramatica.y"
{}
break;
case 98:
//#line 234 "gramatica.y"
{estructurasSintacticas("Se invocó a la función: " + val_peek(3).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 103:
//#line 244 "gramatica.y"
{ lex.addErrorSintactico("Falta el mensaje del OUTF");}
break;
case 106:
//#line 251 "gramatica.y"
{estructurasSintacticas("Se declaró un bucle FOR en la linea: " + lex.getLineaInicial());}
break;
case 107:
//#line 253 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma entre condicion y avance");}
break;
case 108:
//#line 254 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma entre asignacion y condicion");}
break;
case 109:
//#line 255 "gramatica.y"
{ lex.addErrorSintactico("Faltan todos los punto y coma del for");}
break;
case 110:
//#line 256 "gramatica.y"
{ lex.addErrorSintactico("Falta UP/DOWN");}
break;
case 111:
//#line 257 "gramatica.y"
{lex.addErrorSintactico("Falta valor del UP/DOWN");}
break;
case 112:
//#line 258 "gramatica.y"
{ lex.addErrorSintactico("Falta UP/DOWN y punto y coma entre condicion y avance");}
break;
case 113:
//#line 259 "gramatica.y"
{ lex.addErrorSintactico("Falta valor del UP/DOWN y punto y coma entre condicion y avance");}
break;
case 114:
//#line 260 "gramatica.y"
{ { lex.addErrorSintactico("Falta UP/DOWN, su valor, y punto y coma entre condicion y avance");}}
break;
case 118:
//#line 272 "gramatica.y"
{System.out.println("Se declaró un tipo TRIPLE con el ID: " + val_peek(0).sval + " en la linea:" + lex.getLineaInicial());}
break;
case 119:
//#line 274 "gramatica.y"
{lex.addErrorLexico("falta < en la declaración del TRIPLE"); }
break;
case 120:
//#line 275 "gramatica.y"
{lex.addErrorLexico("falta > en la declaración del TRIPLE"); }
break;
case 121:
//#line 276 "gramatica.y"
{lex.addErrorLexico("falta > y < en la declaración del TRIPLE"); }
break;
//#line 1040 "Parser.java"
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
