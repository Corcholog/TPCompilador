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
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    1,    1,    2,    2,    3,    3,    3,    3,    3,    3,
    4,    4,    4,    4,    4,    4,    4,   14,   14,   14,
   14,   15,   15,   15,   15,   15,   15,   15,   15,   15,
   18,   18,   18,   10,   10,   10,   10,   10,   10,   10,
   10,   10,   17,   17,   17,   17,   17,   17,   19,   19,
   20,   20,   21,   21,   21,    5,   23,   23,   22,   22,
   24,   24,    8,    8,    8,    8,   16,   16,   16,   25,
   25,   25,   26,   26,   26,    6,    6,    6,    6,    6,
    6,    6,    6,    6,    6,    6,    6,   27,   28,    9,
   29,   29,   11,   30,   30,   12,   31,   31,    7,   13,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    2,    2,    2,    2,    2,
    2,    1,    1,    1,    2,    2,    2,    1,    1,    1,
    2,    2,    2,    2,    2,    2,    1,    3,    2,    2,
    1,    3,    7,    2,    2,    1,    6,    6,    6,    5,
    3,    2,    1,    5,    7,    4,    4,    3,    6,    5,
    5,    4,    1,    1,    1,    1,    1,    1,    3,    1,
    2,    1,    1,    4,    3,    2,    3,    1,    1,    1,
    1,    1,    3,    2,    2,    1,    3,    3,    1,    3,
    3,    1,    1,    1,    1,    9,    8,    8,    8,    8,
    8,    8,    8,    7,    7,    7,    6,    2,    6,    4,
    2,    1,    4,    1,    1,   12,    1,    1,    6,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,   84,   53,   54,    0,   55,    0,
    0,   72,   71,    0,    0,    9,    0,    0,    0,   27,
    0,   56,   57,   58,    0,   12,   13,   14,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   69,    0,   82,    8,    0,    0,    0,
    0,   85,    0,   83,  110,    0,    0,    5,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    3,   11,   15,
   16,   17,   21,   22,   23,   24,   25,   26,    0,   29,
    0,    0,    0,    0,    0,   68,    0,    0,    0,    0,
    2,    0,    0,    0,    0,   65,    0,    0,    0,    1,
  105,    0,    0,    0,    0,    0,    0,   28,    0,    0,
    0,    0,   60,    0,    0,    0,    0,    0,    0,    0,
    0,   80,   81,    0,  100,   64,    0,    0,  103,    0,
   70,    0,    0,    0,    0,    0,    0,   62,    0,   46,
    0,    0,    0,    0,   67,    0,    0,   44,    0,    0,
   98,    0,   40,    0,    0,    0,   59,   61,   50,    0,
    0,    0,    0,    0,    0,    0,   49,  109,    0,   38,
   37,   39,    0,    0,    0,    0,   97,    0,    0,    0,
    0,    0,   45,    0,   33,    0,   96,    0,    0,    0,
    0,   94,   95,    0,    0,    0,   87,   90,   93,   91,
    0,   89,    0,   92,   88,  107,  108,    0,   86,    0,
    0,    0,    0,   99,  106,
};
final static short yydgoto[] = {                          3,
  178,   26,   27,   28,   29,   30,   31,   32,   52,   34,
   35,   36,   37,   38,   39,   40,   41,   67,  114,  139,
   54,   43,   88,   44,   45,   46,  133,  179,   94,  103,
  208,
};
final static short yysindex[] = {                      -214,
  -40,  101,    0,  -21,    0,    0,    0, -162,    0, -275,
  -17,    0,    0,  910,  126,    0,  -11, -252, -193,    0,
  887,    0,    0,    0,  151,    0,    0,    0,   23,   30,
   49,   56,   60,   83,   84,   85,   86, -161,   82,   64,
 -162, -126, -246,    0,   38,    0,    0,  184, -164, -117,
  -21,    0,   47,    0,    0, -111, -124,    0,  212, -208,
   88,  110,  -32,  362,  112,   64,  -26,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  627,    0,
 -162, -162, -162,   47, -162,    0,   -6,  107, -162, -162,
    0,   47, -162,  113,   32,    0,   35, -104,  627,    0,
    0,   47,  117, -147, -151,   47,  125,    0,   39, -162,
   47,  831,    0,  599,   38,   38,   47,   47,   -3, -151,
  -89,    0,    0,   47,    0,    0,  -88,  568,    0,  109,
    0,  -85,  133,  140, -162,  135,   47,    0,  792,    0,
  -99,  123,  -38,  141,    0,  124,  627,    0,  -83,  -63,
    0,  -77,    0,   -8,   -2,  148,    0,    0,    0,  -76,
  -34,  375,  -75,  -73,  910,  -80,    0,    0,  543,    0,
    0,    0,   33,  543,  416,  443,    0,  468,  -84,  493,
  543,  142,    0,  -72,    0,  -71,    0,  -70,  518,  -69,
  159,    0,    0,  -68,  -66, -137,    0,    0,    0,    0,
  -65,    0, -162,    0,    0,    0,    0,  -51,    0,    3,
  169,  156,  627,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  864,    0,    0,    0,   84,    0,    0,
    0,    0,    0,    0,  248,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  253,    0,    0,    0,   26,   51,
   76,    0,  455,    0,    0,    0,    0,    0,  -57,    0,
  -31,  480,    0,    0,  894,    0,    0,  257,    0,    0,
  872,    0,   83,    0,    0,    0,    0,    0,  267,    0,
    0,    0,    0,    0,  -55,   90,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -29,  -28,   60,    0,    0,    1,    0,    0,
    0,  235,    0,    0,  167,    0,    0,    0,    0,    0,
    0,  240,    0,    0,    0,   90,    0,    0,    0,    0,
  108,    0,    0,  238,  900,  922,  -24,   56,    0,    0,
    0,    0,    0,  265,    0,    0,    0,  256,    0,    0,
    0,    0,    0,    0,    0,    0,  115,    0,    0,    0,
  258,    0,    0,    0,    0,    0,    0,    0,  263,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   66,   -1,    0,  -74,    0,    0,    0,    0, 1188,    0,
    0,    0,    0,    2,  295, 1236,    5,  -78,  -44,    0,
 1162,  -22,    0,  227,   48,   43,  -67,    4,    0,    0,
    0,
};
final static int YYTABLESIZE=1439;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         21,
   66,   31,  163,   30,  113,   55,  176,   96,   64,   36,
   86,   35,   34,   61,  109,   57,   32,  110,   49,   23,
   22,   24,   56,   69,  113,   18,   93,   36,   60,   35,
   34,   87,  170,  120,   32,  110,  142,  138,  171,  113,
   66,  110,    1,  212,   83,   81,   69,   82,   51,    5,
   19,  143,  144,  113,  128,  154,  155,   69,    2,   66,
   66,   66,   66,   62,  158,   18,   25,   48,  107,  141,
   83,  101,  113,  185,  161,   20,  110,  173,  135,   89,
   59,   70,  132,  149,   90,   18,   18,   18,   71,   81,
   19,   82,    4,    5,   51,    5,  132,  132,   23,   22,
   24,   50,  166,   12,   13,  131,   81,   72,   82,   79,
   19,   19,   19,  136,   73,   20,   12,   13,   74,  132,
   12,   13,   80,   23,   22,   24,  206,  207,  115,  116,
   43,  122,  123,   43,   85,   20,   20,   20,  113,   95,
   21,   75,   76,   77,   78,   98,   99,  104,   42,  105,
  121,   42,  108,  125,   50,   41,  127,  129,   41,  126,
   23,   22,   24,  160,  134,   21,  182,  145,  215,  146,
  150,  151,  184,  152,  156,  159,   69,  186,  188,  190,
  153,  164,  165,  194,  195,   23,   22,   24,  172,  192,
   21,  167,  201,  168,  183,  169,  174,  180,  203,  181,
  196,  197,  198,  199,  202,  204,  211,  205,  209,  213,
   23,   22,   24,   31,  214,   30,    4,    5,    6,    7,
    8,    9,   10,   21,   51,    5,   11,   12,   13,   14,
   51,    5,   15,   16,  162,   17,   18,   19,  175,   36,
   20,   35,   34,   23,   22,   24,   32,   10,   51,    5,
  119,   21,    6,  131,   51,    5,    7,   66,   66,   66,
   66,   66,   66,   66,   12,   13,    4,   66,   66,   66,
   66,   23,   22,   24,   66,  102,   66,   66,   66,   66,
  104,   66,   18,   18,   18,   18,   18,   18,   18,   51,
    5,   63,   18,   18,   18,   18,   48,    6,    7,   18,
    9,   18,   18,   18,   18,  101,   18,   19,   19,   19,
   19,   19,   19,   19,   47,   65,   52,   19,   19,   19,
   19,   51,    6,    7,   19,    9,   19,   19,   19,   19,
  130,   19,   20,   20,   20,   20,   20,   20,   20,    0,
    0,    0,   20,   20,   20,   20,   43,   43,    0,   20,
    0,   20,   20,   20,   20,    0,   20,    4,    5,    6,
    7,    8,    9,   10,   42,   42,    0,   11,   12,   13,
   14,   41,   41,    0,   47,    0,   17,   18,   19,  131,
    0,   20,    4,    5,    6,    7,    8,    9,   10,    0,
   12,   13,   11,   12,   13,   14,   51,    5,    0,   58,
    0,   17,   18,   19,   51,    5,   20,    4,    5,    6,
    7,    8,    9,   10,   21,    0,    0,   11,   12,   13,
   14,   23,   22,   24,   68,    0,   17,   18,   19,    0,
    0,   20,    0,    0,   23,   22,   24,    0,    0,    0,
    4,    5,    6,    7,    8,    9,   10,    0,    0,    0,
   11,   12,   13,   14,    0,   21,    0,   91,    0,   17,
   18,   19,    0,    0,   20,    0,    0,    0,    4,    5,
    6,    7,    8,    9,   10,   23,   22,   24,   11,   12,
   13,   14,   21,    0,    0,  100,    0,   17,   18,   19,
    0,    0,   20,    0,    0,    0,   85,   85,    0,   85,
    0,   85,   23,   22,   24,    0,    0,   21,    0,    0,
    0,    0,    0,    0,   85,   85,   85,    0,    0,    0,
    0,   83,   83,    0,   83,    0,   83,   23,   22,   24,
    0,    0,   21,    0,    0,    0,    0,    0,    0,   83,
   83,   83,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   23,   22,   24,    0,    0,   21,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   23,   22,   24,
    0,    0,   21,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   23,   22,   24,    0,    0,   21,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    6,    7,    0,    9,    0,    0,    0,   23,   22,   24,
    0,    4,    5,    6,    7,    8,    9,   10,   21,    0,
    0,   11,   12,   13,   14,    0,    0,    0,  177,    0,
   17,   18,   19,    0,    0,   20,    0,    0,   23,   22,
   24,    0,    0,    0,    0,    0,   21,    0,    0,    0,
    0,    0,    4,    5,    6,    7,    8,    9,   10,    0,
    0,    0,   11,   12,   13,   14,   23,   22,   24,  187,
    0,   17,   18,   19,    0,    0,   20,    0,    0,    4,
    5,    6,    7,    8,    9,   10,    0,    0,    0,   11,
   12,   13,   14,   85,   85,  189,   85,    0,   17,   18,
   19,    0,    0,   20,    4,    5,    6,    7,    8,    9,
   10,    0,    0,    0,   11,   12,   13,   14,   83,   83,
    0,   83,    0,   17,   18,   19,  191,    0,   20,    4,
    5,    6,    7,    8,    9,   10,    0,    0,    0,   11,
   12,   13,   14,    0,    0,    0,  193,    0,   17,   18,
   19,    0,    0,   20,    4,    5,    6,    7,    8,    9,
   10,    0,    0,    0,   11,   12,   13,   14,    0,    0,
    0,  200,    0,   17,   18,   19,    0,    0,   20,    4,
    5,    6,    7,    8,    9,   10,    0,    0,    0,   11,
   12,   13,   14,    0,    0,    0,    0,    0,   17,   18,
   19,    0,    0,   20,   51,    5,    6,    7,    8,    9,
   10,   21,    0,    0,   11,    0,    0,   14,    0,  147,
  112,    0,  148,   17,    0,    0,    0,    0,   20,    0,
    0,   23,   22,   24,    0,   51,    5,    6,    7,    8,
    9,   10,    0,    0,    0,   11,    0,    0,   14,    0,
   21,  112,    0,  140,   17,    0,    0,    0,    0,   20,
    0,    0,    0,   51,    5,    6,    7,    8,    9,   10,
   23,   22,   24,   11,    0,    0,   14,    0,    0,  112,
    0,    0,   17,    0,   63,   63,   63,   20,   63,    0,
   63,    0,   63,   63,   63,   63,   63,    0,   63,    0,
    0,    0,    0,   63,   63,   63,   63,   64,    0,    0,
   63,   63,   63,   63,   79,    0,   79,   79,   79,    0,
   77,    0,   77,   77,   77,    0,   23,   22,   24,   21,
    0,    0,   79,   79,   79,   79,    0,    0,   77,   77,
   77,   77,   78,    0,   78,   78,   78,    0,    0,   23,
   22,   24,    0,    0,    0,    0,    0,    0,    0,    0,
   78,   78,   78,   78,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   51,    5,
    6,    7,    8,    9,   10,    0,    0,    0,   11,    0,
    0,   14,    0,    0,    0,  157,    0,   17,    0,    0,
    0,    0,   20,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   51,    5,    6,
    7,    8,    9,   10,    0,    0,    0,   11,    0,    0,
   14,    0,    0,    0,    0,    0,   17,    0,    0,    0,
    0,   20,    0,    0,    0,    0,    0,    0,    0,    0,
   70,   70,   63,   63,   63,   63,    0,    0,   63,   63,
   63,   63,   63,   63,    0,    0,    0,    0,    0,    0,
    0,   70,   63,   51,    5,    6,    7,    0,    9,    0,
   79,   79,   79,   79,    0,   79,   77,   77,   77,   77,
    0,   77,   42,   42,   79,    0,   51,    5,    6,    7,
   77,    9,    0,    0,    0,    0,   42,    0,   78,   78,
   78,   78,    0,   78,    0,    0,   42,    0,   33,   33,
    0,    0,   78,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   33,    0,    0,    0,    0,    0,    0,   42,
    0,   97,   33,    0,    0,    0,    0,    0,    0,    0,
   42,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   33,    0,    0,    0,    0,
   42,    0,    0,   53,    0,    0,   33,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   66,    0,    0,    0,
   42,    0,    0,    0,    0,    0,   33,    0,    0,    0,
    0,    0,    0,   42,    0,   42,   84,    0,    0,    0,
    0,    0,    0,    0,   92,    0,   33,    0,    0,   42,
    0,    0,    0,    0,    0,  102,    0,    0,  106,   33,
   42,   33,  111,    0,    0,    0,    0,    0,   42,    0,
    0,    0,    0,    0,    0,   33,    0,    0,  117,    0,
  118,    0,    0,   42,    0,    0,   33,    0,  124,    0,
   42,    0,    0,    0,   33,   42,   42,   42,    0,   42,
    0,   42,   42,    0,    0,  137,    0,    0,    0,   33,
   42,    0,    0,    0,    0,    0,   33,    0,    0,    0,
    0,   33,   33,   33,    0,   33,    0,   33,   33,  106,
  106,    0,    0,    0,   42,    0,   33,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  111,
  111,  106,    0,    0,    0,    0,    0,    0,    0,    0,
   33,    0,    0,    0,    0,    0,    0,    0,  111,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  210,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   59,   41,   59,   79,  281,   41,  125,   41,   41,
  257,   41,   41,  266,   41,   14,   41,   44,   40,   60,
   61,   62,   40,   25,   99,    0,   49,   59,   40,   59,
   59,  278,   41,   40,   59,   44,   40,  112,   41,  114,
   40,   44,  257,   41,   40,   43,   48,   45,  257,  258,
    0,  119,  120,  128,   99,  134,  135,   59,  273,   59,
   60,   61,   62,  257,  139,   40,    1,    2,   64,  114,
   66,  280,  147,   41,  142,    0,   44,  156,   40,   42,
   15,   59,  105,  128,   47,   60,   61,   62,   59,   43,
   40,   45,  257,  258,  257,  258,  119,  120,   60,   61,
   62,  123,  147,  268,  269,  257,   43,   59,   45,  271,
   60,   61,   62,  109,   59,   40,  268,  269,   59,  142,
  268,  269,   41,   60,   61,   62,  264,  265,   81,   82,
   41,   89,   90,   44,  261,   60,   61,   62,  213,  257,
   40,   59,   59,   59,   59,  257,  271,   60,   41,   40,
   44,   44,   41,   41,  123,   41,  261,   41,   44,  125,
   60,   61,   62,   41,   40,   40,  165,  257,  213,  258,
   62,  257,  169,   41,   40,  275,  178,  174,  175,  176,
   41,   41,   59,  180,  181,   60,   61,   62,   41,  274,
   40,  275,  189,  257,  275,  273,  273,  273,   40,  273,
   59,  274,  274,  274,  274,  274,  258,  274,  274,   41,
   60,   61,   62,  271,   59,  271,  257,  258,  259,  260,
  261,  262,  263,   40,  257,  258,  267,  268,  269,  270,
  257,  258,  273,  274,  273,  276,  277,  278,  273,  271,
  281,  271,  271,   60,   61,   62,  271,    0,  257,  258,
  257,   40,    0,  257,  257,  258,    0,  257,  258,  259,
  260,  261,  262,  263,  268,  269,    0,  267,  268,  269,
  270,   60,   61,   62,  274,   41,  276,  277,  278,  279,
   41,  281,  257,  258,  259,  260,  261,  262,  263,  257,
  258,  125,  267,  268,  269,  270,   59,  259,  260,  274,
  262,  276,  277,  278,  279,   41,  281,  257,  258,  259,
  260,  261,  262,  263,   59,   21,   59,  267,  268,  269,
  270,   59,  259,  260,  274,  262,  276,  277,  278,  279,
  104,  281,  257,  258,  259,  260,  261,  262,  263,   -1,
   -1,   -1,  267,  268,  269,  270,  257,  258,   -1,  274,
   -1,  276,  277,  278,  279,   -1,  281,  257,  258,  259,
  260,  261,  262,  263,  257,  258,   -1,  267,  268,  269,
  270,  257,  258,   -1,  274,   -1,  276,  277,  278,  257,
   -1,  281,  257,  258,  259,  260,  261,  262,  263,   -1,
  268,  269,  267,  268,  269,  270,  257,  258,   -1,  274,
   -1,  276,  277,  278,  257,  258,  281,  257,  258,  259,
  260,  261,  262,  263,   40,   -1,   -1,  267,  268,  269,
  270,   60,   61,   62,  274,   -1,  276,  277,  278,   -1,
   -1,  281,   -1,   -1,   60,   61,   62,   -1,   -1,   -1,
  257,  258,  259,  260,  261,  262,  263,   -1,   -1,   -1,
  267,  268,  269,  270,   -1,   40,   -1,  274,   -1,  276,
  277,  278,   -1,   -1,  281,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,  263,   60,   61,   62,  267,  268,
  269,  270,   40,   -1,   -1,  274,   -1,  276,  277,  278,
   -1,   -1,  281,   -1,   -1,   -1,   42,   43,   -1,   45,
   -1,   47,   60,   61,   62,   -1,   -1,   40,   -1,   -1,
   -1,   -1,   -1,   -1,   60,   61,   62,   -1,   -1,   -1,
   -1,   42,   43,   -1,   45,   -1,   47,   60,   61,   62,
   -1,   -1,   40,   -1,   -1,   -1,   -1,   -1,   -1,   60,
   61,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   60,   61,   62,   -1,   -1,   40,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   60,   61,   62,
   -1,   -1,   40,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   60,   61,   62,   -1,   -1,   40,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  259,  260,   -1,  262,   -1,   -1,   -1,   60,   61,   62,
   -1,  257,  258,  259,  260,  261,  262,  263,   40,   -1,
   -1,  267,  268,  269,  270,   -1,   -1,   -1,  274,   -1,
  276,  277,  278,   -1,   -1,  281,   -1,   -1,   60,   61,
   62,   -1,   -1,   -1,   -1,   -1,   40,   -1,   -1,   -1,
   -1,   -1,  257,  258,  259,  260,  261,  262,  263,   -1,
   -1,   -1,  267,  268,  269,  270,   60,   61,   62,  274,
   -1,  276,  277,  278,   -1,   -1,  281,   -1,   -1,  257,
  258,  259,  260,  261,  262,  263,   -1,   -1,   -1,  267,
  268,  269,  270,  259,  260,  273,  262,   -1,  276,  277,
  278,   -1,   -1,  281,  257,  258,  259,  260,  261,  262,
  263,   -1,   -1,   -1,  267,  268,  269,  270,  259,  260,
   -1,  262,   -1,  276,  277,  278,  279,   -1,  281,  257,
  258,  259,  260,  261,  262,  263,   -1,   -1,   -1,  267,
  268,  269,  270,   -1,   -1,   -1,  274,   -1,  276,  277,
  278,   -1,   -1,  281,  257,  258,  259,  260,  261,  262,
  263,   -1,   -1,   -1,  267,  268,  269,  270,   -1,   -1,
   -1,  274,   -1,  276,  277,  278,   -1,   -1,  281,  257,
  258,  259,  260,  261,  262,  263,   -1,   -1,   -1,  267,
  268,  269,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,
  278,   -1,   -1,  281,  257,  258,  259,  260,  261,  262,
  263,   40,   -1,   -1,  267,   -1,   -1,  270,   -1,  272,
  273,   -1,  275,  276,   -1,   -1,   -1,   -1,  281,   -1,
   -1,   60,   61,   62,   -1,  257,  258,  259,  260,  261,
  262,  263,   -1,   -1,   -1,  267,   -1,   -1,  270,   -1,
   40,  273,   -1,  275,  276,   -1,   -1,   -1,   -1,  281,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,  263,
   60,   61,   62,  267,   -1,   -1,  270,   -1,   -1,  273,
   -1,   -1,  276,   -1,   41,   42,   43,  281,   45,   -1,
   47,   -1,   41,   42,   43,   44,   45,   -1,   47,   -1,
   -1,   -1,   -1,   60,   61,   62,   40,   41,   -1,   -1,
   59,   60,   61,   62,   41,   -1,   43,   44,   45,   -1,
   41,   -1,   43,   44,   45,   -1,   60,   61,   62,   40,
   -1,   -1,   59,   60,   61,   62,   -1,   -1,   59,   60,
   61,   62,   41,   -1,   43,   44,   45,   -1,   -1,   60,
   61,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   59,   60,   61,   62,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,  263,   -1,   -1,   -1,  267,   -1,
   -1,  270,   -1,   -1,   -1,  274,   -1,  276,   -1,   -1,
   -1,   -1,  281,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
  260,  261,  262,  263,   -1,   -1,   -1,  267,   -1,   -1,
  270,   -1,   -1,   -1,   -1,   -1,  276,   -1,   -1,   -1,
   -1,  281,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  257,  258,  259,  260,  261,  262,   -1,   -1,  257,  258,
  259,  260,  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  278,  271,  257,  258,  259,  260,   -1,  262,   -1,
  257,  258,  259,  260,   -1,  262,  257,  258,  259,  260,
   -1,  262,    1,    2,  271,   -1,  257,  258,  259,  260,
  271,  262,   -1,   -1,   -1,   -1,   15,   -1,  257,  258,
  259,  260,   -1,  262,   -1,   -1,   25,   -1,    1,    2,
   -1,   -1,  271,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   15,   -1,   -1,   -1,   -1,   -1,   -1,   48,
   -1,   50,   25,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   59,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   48,   -1,   -1,   -1,   -1,
   79,   -1,   -1,    8,   -1,   -1,   59,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   21,   -1,   -1,   -1,
   99,   -1,   -1,   -1,   -1,   -1,   79,   -1,   -1,   -1,
   -1,   -1,   -1,  112,   -1,  114,   41,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   49,   -1,   99,   -1,   -1,  128,
   -1,   -1,   -1,   -1,   -1,   60,   -1,   -1,   63,  112,
  139,  114,   67,   -1,   -1,   -1,   -1,   -1,  147,   -1,
   -1,   -1,   -1,   -1,   -1,  128,   -1,   -1,   83,   -1,
   85,   -1,   -1,  162,   -1,   -1,  139,   -1,   93,   -1,
  169,   -1,   -1,   -1,  147,  174,  175,  176,   -1,  178,
   -1,  180,  181,   -1,   -1,  110,   -1,   -1,   -1,  162,
  189,   -1,   -1,   -1,   -1,   -1,  169,   -1,   -1,   -1,
   -1,  174,  175,  176,   -1,  178,   -1,  180,  181,  134,
  135,   -1,   -1,   -1,  213,   -1,  189,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  154,
  155,  156,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  213,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  173,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  203,
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
"prog : ID BEGIN cuerpo END",
"prog : BEGIN cuerpo END",
"prog : ID cuerpo END",
"prog : ID BEGIN cuerpo",
"prog : ID BEGIN END",
"prog : ID cuerpo",
"prog : BEGIN cuerpo",
"prog : BEGIN END",
"prog : ID END",
"prog : ID BEGIN",
"cuerpo : cuerpo sentencia",
"cuerpo : sentencia",
"sentencia : sentec_declar",
"sentencia : sentec_eject",
"sentec_declar : declaracion_var ';'",
"sentec_declar : declaracion_fun ';'",
"sentec_declar : declar_tipo_trip ';'",
"sentec_declar : declaracion_var",
"sentec_declar : declaracion_fun",
"sentec_declar : declar_tipo_trip",
"sentec_eject : asignacion ';'",
"sentec_eject : invoc_fun ';'",
"sentec_eject : seleccion ';'",
"sentec_eject : sald_mensaj ';'",
"sentec_eject : for ';'",
"sentec_eject : goto ';'",
"sentec_eject : TAG",
"condicion : '(' condicion_2 ')'",
"condicion : condicion_2 ')'",
"condicion : '(' condicion_2",
"condicion : condicion_2",
"condicion_2 : expresion comparador expresion",
"condicion_2 : '(' lista_expres ')' comparador '(' lista_expres ')'",
"condicion_2 : comparador expresion",
"condicion_2 : expresion comparador",
"condicion_2 : comparador",
"condicion_2 : '(' lista_expres ')' '(' lista_expres ')'",
"condicion_2 : '(' ')' comparador '(' lista_expres ')'",
"condicion_2 : '(' lista_expres ')' comparador '(' ')'",
"condicion_2 : '(' ')' comparador '(' ')'",
"lista_expres : lista_expres ',' expresion",
"lista_expres : lista_expres expresion",
"lista_expres : expresion",
"seleccion : IF condicion THEN cuerpo_control END_IF",
"seleccion : IF condicion THEN cuerpo_control ELSE cuerpo_control END_IF",
"seleccion : condicion THEN cuerpo_control END_IF",
"seleccion : IF condicion THEN cuerpo_control",
"seleccion : condicion THEN cuerpo_control",
"seleccion : IF condicion THEN cuerpo_control cuerpo_control END_IF",
"seleccion : condicion THEN cuerpo_control cuerpo_control END_IF",
"seleccion : IF condicion THEN cuerpo_control cuerpo_control",
"seleccion : condicion THEN cuerpo_control cuerpo_control",
"comparador : MASI",
"comparador : MENOSI",
"comparador : DIST",
"comparador : '='",
"comparador : '<'",
"comparador : '>'",
"cuerpo_control : BEGIN multip_cuerp_fun END",
"cuerpo_control : sentec_eject",
"multip_cuerp_fun : multip_cuerp_fun sentec_eject",
"multip_cuerp_fun : sentec_eject",
"variable : ID",
"variable : ID '{' variable '}'",
"variable : ID '{' '}'",
"declaracion_var : tipo lista_variables",
"lista_variables : lista_variables ',' ID",
"lista_variables : ID",
"tipo : tipo_basico",
"tipo : ID",
"tipo_basico : DOUBLE",
"tipo_basico : ULONGINT",
"asignacion : variable ASIGN expresion",
"asignacion : variable ASIGN",
"asignacion : ASIGN expresion",
"asignacion : ASIGN",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : variable",
"factor : CTE",
"factor : invoc_fun",
"declaracion_fun : tipo FUN ID '(' parametro ')' BEGIN cuerpo_funcion END",
"declaracion_fun : FUN ID '(' parametro ')' BEGIN cuerpo_funcion END",
"declaracion_fun : tipo FUN '(' parametro ')' BEGIN cuerpo_funcion END",
"declaracion_fun : tipo FUN ID '(' parametro ')' cuerpo_funcion END",
"declaracion_fun : tipo FUN ID '(' ')' BEGIN cuerpo_funcion END",
"declaracion_fun : tipo FUN ID '(' parametro ')' BEGIN END",
"declaracion_fun : tipo FUN ID parametro ')' BEGIN cuerpo_funcion END",
"declaracion_fun : tipo FUN ID '(' parametro BEGIN cuerpo_funcion END",
"declaracion_fun : tipo FUN ID parametro BEGIN cuerpo_funcion END",
"declaracion_fun : tipo FUN ID parametro ')' BEGIN END",
"declaracion_fun : tipo FUN ID '(' parametro BEGIN END",
"declaracion_fun : tipo FUN ID parametro BEGIN END",
"parametro : tipo ID",
"cuerpo_funcion : cuerpo RET '(' expresion ')' ';'",
"invoc_fun : ID '(' param_real ')'",
"param_real : tipo expresion",
"param_real : expresion",
"sald_mensaj : OUTF '(' mensaje ')'",
"mensaje : expresion",
"mensaje : CADMUL",
"for : FOR '(' ID ASIGN CTE ';' condicion ';' foravanc CTE ')' cuerpo_control",
"foravanc : UP",
"foravanc : DOWN",
"declar_tipo_trip : TYPEDEF TRIPLE '<' tipo_basico '>' ID",
"goto : GOTO TAG",
};

//#line 161 "gramatica.y"
String nombreArchivo;
AnalizadorLexico lex;
public Parser(String nombreArchivo, TablaSimbolos t)
{
	this.nombreArchivo=nombreArchivo;
	this.lex= new AnalizadorLexico(nombreArchivo,t);
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

public static void main(String[] args) {
	String prueba= "PruebaGramaticaErrores";
	TablaSimbolos tb= new TablaSimbolos();
	Parser p = new Parser(prueba,tb);
	System.out.println(p.yyparse());
	System.out.println(p.errores());
}
//#line 689 "Parser.java"
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
case 2:
//#line 11 "gramatica.y"
{ lex.addErrorLexico("Falta nombre en prog");}
break;
case 3:
//#line 12 "gramatica.y"
{ lex.addErrorLexico("Falta Begin en prog");}
break;
case 4:
//#line 13 "gramatica.y"
{ lex.addErrorLexico("Falta End en prog");}
break;
case 5:
//#line 14 "gramatica.y"
{ lex.addErrorLexico("Falta Cuerpo en prog");}
break;
case 6:
//#line 15 "gramatica.y"
{ lex.addErrorLexico("Falta begin y end en el prog");}
break;
case 7:
//#line 16 "gramatica.y"
{ lex.addErrorLexico("Falta nombre y end en prog");}
break;
case 8:
//#line 17 "gramatica.y"
{ lex.addErrorLexico("Falta nombre y cuerpo en prog");}
break;
case 9:
//#line 18 "gramatica.y"
{ lex.addErrorLexico("Falta Cuerpo y Begin en prog");}
break;
case 10:
//#line 19 "gramatica.y"
{ lex.addErrorLexico("Falta Cuerpo y end en prog");}
break;
case 15:
//#line 27 "gramatica.y"
{	System.out.println("Se declaro la variable, en linea: " + lex.getLineaInicial()); }
break;
case 16:
//#line 28 "gramatica.y"
{	System.out.println("Se declaro la funcion, en linea: " + lex.getLineaInicial()); }
break;
case 17:
//#line 29 "gramatica.y"
{System.out.println("Se declaro la variable tripla, en linea: " + lex.getLineaInicial()); }
break;
case 18:
//#line 31 "gramatica.y"
{ lex.addErrorLexico("Se declaro la variable, falta ;"); }
break;
case 19:
//#line 32 "gramatica.y"
{	lex.addErrorLexico("Se declaro la funcion, falta ;"); }
break;
case 20:
//#line 33 "gramatica.y"
{lex.addErrorLexico("Se declaro la variable tripla, falta ;"); }
break;
case 21:
//#line 36 "gramatica.y"
{System.out.println("Se realizo una asignacion, en linea: " + lex.getLineaInicial()); }
break;
case 22:
//#line 37 "gramatica.y"
{System.out.println("Se invoco una funcion, en linea: " + lex.getLineaInicial()); }
break;
case 23:
//#line 38 "gramatica.y"
{System.out.println("Se hizo un if, en linea: " + lex.getLineaInicial()); }
break;
case 24:
//#line 39 "gramatica.y"
{System.out.println("Se imprimio un mensaje, en linea: " + lex.getLineaInicial()); }
break;
case 25:
//#line 40 "gramatica.y"
{System.out.println("Se declaro un for, en linea: " + lex.getLineaInicial()); }
break;
case 26:
//#line 41 "gramatica.y"
{System.out.println("Se llamo a una etiqueta goto, en linea: " + lex.getLineaInicial()); }
break;
case 27:
//#line 42 "gramatica.y"
{	System.out.println("Se declaro una etiqueta goto, en linea: " + lex.getLineaInicial()); }
break;
case 29:
//#line 46 "gramatica.y"
{lex.addErrorLexico("falta el ( en la comparacion"); }
break;
case 30:
//#line 47 "gramatica.y"
{lex.addErrorLexico("falta el ) en la comparacion"); }
break;
case 31:
//#line 48 "gramatica.y"
{lex.addErrorLexico("falta el ( y el ) en la comparacion"); }
break;
case 34:
//#line 53 "gramatica.y"
{lex.addErrorLexico("falta la primera expresion en la comparacion"); }
break;
case 35:
//#line 54 "gramatica.y"
{lex.addErrorLexico("falta la segunda expresion en la comparacion"); }
break;
case 36:
//#line 55 "gramatica.y"
{lex.addErrorLexico("faltan lasexpresiones en la comparacion"); }
break;
case 37:
//#line 56 "gramatica.y"
{lex.addErrorLexico("falta la primera lista de elementos en la comparacion"); }
break;
case 38:
//#line 57 "gramatica.y"
{lex.addErrorLexico("falta el comparador"); }
break;
case 39:
//#line 58 "gramatica.y"
{lex.addErrorLexico("falta la segunda lista de elementos en la comparacion"); }
break;
case 40:
//#line 59 "gramatica.y"
{lex.addErrorLexico("faltan las listas de elementos en la comparacion"); }
break;
case 42:
//#line 62 "gramatica.y"
{lex.addErrorLexico("Se esta comparando una lista de expresiones, falta ,"); }
break;
case 46:
//#line 68 "gramatica.y"
{lex.addErrorLexico("falta el if en la seleccion"); }
break;
case 47:
//#line 69 "gramatica.y"
{lex.addErrorLexico("falta el END_IF en la seleccion"); }
break;
case 48:
//#line 70 "gramatica.y"
{lex.addErrorLexico("falta el IF y el END_IF en la seleccion"); }
break;
case 49:
//#line 71 "gramatica.y"
{lex.addErrorLexico("falta el else en la seleccion"); }
break;
case 50:
//#line 72 "gramatica.y"
{lex.addErrorLexico("falta el if y el else en la seleccion"); }
break;
case 51:
//#line 73 "gramatica.y"
{lex.addErrorLexico("falta el end_if y el else en la seleccion"); }
break;
case 52:
//#line 74 "gramatica.y"
{lex.addErrorLexico("falta el if, end_if y el else en la seleccion"); }
break;
case 65:
//#line 92 "gramatica.y"
{lex.addErrorLexico("falta la variable que indica la posicion"); }
break;
case 74:
//#line 107 "gramatica.y"
{lex.addErrorLexico("falta la expresion en la asignacion"); }
break;
case 75:
//#line 108 "gramatica.y"
{lex.addErrorLexico("falta la variable en la asignacion"); }
break;
case 76:
//#line 109 "gramatica.y"
{lex.addErrorLexico("falta la variable y la expresion en la asignacion"); }
break;
case 87:
//#line 125 "gramatica.y"
{lex.addErrorLexico("falta el tipo de la funcion declarada"); }
break;
case 88:
//#line 126 "gramatica.y"
{lex.addErrorLexico("falta el identificador de la funcion declarada"); }
break;
case 89:
//#line 127 "gramatica.y"
{lex.addErrorLexico("falta el begin de la funcion declarada"); }
break;
case 90:
//#line 128 "gramatica.y"
{lex.addErrorLexico("falta el parametro en la funcion declarada"); }
break;
case 91:
//#line 129 "gramatica.y"
{lex.addErrorLexico("falta el cuerpo en la funcion declarada"); }
break;
case 92:
//#line 130 "gramatica.y"
{lex.addErrorLexico("falta el ( en la funcion declarada"); }
break;
case 93:
//#line 131 "gramatica.y"
{lex.addErrorLexico("falta el ) en la funcion declarada"); }
break;
case 94:
//#line 132 "gramatica.y"
{lex.addErrorLexico("falta el ( y el ) en la funcion declarada"); }
break;
case 95:
//#line 133 "gramatica.y"
{lex.addErrorLexico("falta el ( y el cuerpo en la funcion declarada"); }
break;
case 96:
//#line 134 "gramatica.y"
{lex.addErrorLexico("falta el ) y el cuerpo en la funcion declarada"); }
break;
case 97:
//#line 135 "gramatica.y"
{lex.addErrorLexico("falta el ( ) y el cuerpo en la funcion declarada"); }
break;
//#line 1058 "Parser.java"
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
