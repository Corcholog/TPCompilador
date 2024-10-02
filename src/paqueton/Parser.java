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
   17,   17,   17,   17,   17,   17,   20,   21,   21,   12,
   12,   12,   12,   12,   12,   12,   19,   19,   19,   19,
   19,   19,   22,   22,   22,   22,   22,   24,   24,   24,
   24,   24,   23,   23,   23,    7,    7,   26,   26,   26,
   25,   25,   10,   10,   28,   28,   18,   18,   18,   18,
   18,   18,   18,   18,   29,   29,   29,   29,   29,   29,
   29,   29,   29,   30,   30,   30,   30,   31,   31,   27,
   35,    8,    8,    8,   32,   32,   33,   33,   36,   36,
   36,   36,   37,   34,   38,   38,   38,   39,   39,   40,
   11,   11,   41,   41,   42,   42,   13,   13,   13,   43,
   43,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   44,   44,   15,   15,    9,    9,    9,    9,    9,    9,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    3,    4,    3,    3,    3,    2,
    3,    1,    2,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    3,    1,    2,    2,    3,
    7,    5,    6,    6,    7,    3,    3,    3,    1,    5,
    7,    6,    4,    4,    6,    5,    1,    1,    1,    1,
    1,    1,    4,    2,    3,    2,    3,    4,    2,    3,
    2,    3,    3,    1,    2,    2,    2,    3,    1,    3,
    1,    1,    3,    3,    1,    1,    3,    3,    1,    2,
    4,    4,    4,    4,    3,    3,    1,    2,    2,    4,
    4,    4,    4,    1,    1,    1,    1,    1,    2,    4,
    0,    8,    6,    6,    1,    1,    3,    1,    2,    2,
    1,    1,    0,    5,    3,    1,    2,    1,    1,    4,
    4,    3,    3,    1,    2,    1,    4,    3,    4,    1,
    1,   12,   11,   11,   10,   11,   11,   10,   10,   10,
    1,    1,    2,    3,    6,    5,    5,    4,    5,    7,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    0,    0,   72,   71,    0,
    0,    0,    0,   18,    1,    0,   12,   14,   15,   16,
   17,   19,   20,   21,   22,   23,   24,   25,    0,    0,
    0,    0,    0,   69,    0,    0,    0,    0,    0,  143,
    0,    0,   98,    0,    0,    0,    0,    0,   96,    0,
    0,    0,   97,    0,   87,   95,    0,    0,    0,    0,
    0,    0,   13,    0,    0,    0,    0,    4,    0,   76,
    0,   74,  122,    0,  126,    0,  124,    0,    0,    0,
  144,    0,    0,    0,    0,    0,    0,   99,   88,   89,
    0,   29,    0,   47,   48,   49,   50,   51,   52,    0,
    0,    0,    0,    0,    0,    7,    0,  131,  128,  130,
    0,    0,    0,    0,    9,   11,   73,    0,    0,    3,
    0,    0,  121,    0,  100,   70,   68,    0,   26,    0,
    0,    0,    0,    0,    0,   44,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   85,    0,   86,    6,  129,
  127,    0,  148,    0,    0,    0,    0,    0,    0,  108,
  123,    0,    0,    0,    0,    0,    0,   46,   56,    0,
   64,    0,   54,    0,   40,   82,   81,   84,   83,   92,
   91,   93,   90,  147,    0,  146,  149,  113,    0,  110,
  109,  113,    0,    0,    0,    0,    0,    0,    0,   32,
   55,   57,    0,   65,   45,    0,    0,  145,  104,    0,
  101,  103,  107,    0,  141,  142,    0,    0,    0,    0,
   33,   34,   53,   63,   41,  150,    0,  113,    0,    0,
    0,    0,    0,   35,   31,    0,  119,    0,  116,  118,
  102,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  117,    0,    0,  138,    0,  140,    0,    0,    0,
  139,    0,  135,    0,  114,  115,   61,    0,    0,   59,
  136,    0,  137,  133,  134,  120,   60,   62,    0,  132,
   58,
};
final static short yydgoto[] = {                          3,
   15,    4,   16,   63,   18,   19,   20,   21,   22,   23,
   24,   25,   26,   27,   28,   50,   70,   71,  102,   85,
   86,  138,  172,  255,   29,   38,   30,   75,   54,   55,
   56,   31,  159,  209,  228,  160,  210,  238,  239,  240,
   76,   77,  111,  218,
};
final static short yysindex[] = {                      -195,
  710,  809,    0,    0,  -34, -217,   12,    0,    0,  167,
  809,   27,  -48,    0,    0,  472,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -222, -220,
 -204,  487, -184,    0,  451,  -38,  154,  -11,   35,    0,
 -171,  -10,    0,  451,  321, -156,  114,  114,    0, -163,
   71,  685,    0,   83,    0,    0,  502, -146,  -24,  -47,
 -142,  732,    0,  -11,  451,  -29,  764,    0,  154,    0,
  685,    0,    0,  154,    0,   93,    0,   -5, -119, -113,
    0, -103,  134,  685,  143,  119,   83,    0,    0,    0,
  -87,    0,  154,    0,    0,    0,    0,    0,    0,  430,
  653,  154,  161,  180,  779,    0,  140,    0,    0,    0,
  141, -142,  -42,  128,    0,    0,    0,  151, -169,    0,
   73,   73,    0,  122,    0,    0,    0,  -66,    0,  661,
  598,  154,   -4,  -75,  252,    0,  149, -130,   73,    6,
   83,   16,   83,   73,   17,    0,   32,    0,    0,    0,
    0,  -28,    0,  -55,  -46,  -33,  -41,  -40,  102,    0,
    0,  758,  198,  604,  154,  223,   73,    0,    0,   -3,
    0,  545,    0,  574,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -84,    0,    0,    0,  110,    0,
    0,    0, -169,  167,   96,  154,  154,  244,  247,    0,
    0,    0,  624,    0,    0,   15,  238,    0,    0,   41,
    0,    0,    0,  203,    0,    0,  -86,   51,  275,  282,
    0,    0,    0,    0,    0,    0,  794,    0,  287,   18,
  -19,   76,  296,    0,    0,  300,    0,  457,    0,    0,
    0,  632,  301,  632,    1,  336,  632,  337,  632,  451,
  603,    0,  553,  322,    0,  632,    0,  341,  632,  632,
    0,  632,    0,  344,    0,    0,    0,  115,  569,    0,
    0,  632,    0,    0,    0,    0,    0,    0,  817,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  121,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  123,    0,
    0,    0,  518,    0,    0,    0,    0,  295,    0,    0,
    0,  -16,    0,    0,    0,    0,    0,    0,    0,    0,
  249,    0,    0,   10,    0,    0,    0,  518,    0,    0,
    0,  394,    0,  400,    0,    0,  406,    0,    0,    0,
  385,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  276,  363,    0,    0,   36,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  409,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  363,  112,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  415,  329,    0,
   62,    0,   88,  356,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  116,  127,    0,    0,
    0,    0,    0,    0,    0,    0,  133,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  436,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    0,   99,   -1,    0,  693,    0,    0,    0,    0,
  339,    0,    0,    0,    0, -138,    4,  832,  -70,  -82,
    0,  236,  163,  283,  773,  391,  386,   52,   -8,   37,
    0,    0,  258, -160,    0,  234,    0,    0, -188,    0,
    0,  304,    0,  -78,
};
final static int YYTABLESIZE=1093;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         17,
   33,   69,   73,   47,   45,   36,   46,  188,   48,   58,
  119,   61,  112,   51,  131,   69,  109,   47,   45,  154,
   46,  247,   48,  195,   94,   94,   94,   94,   94,   36,
   94,  212,   80,  185,   34,   36,   87,  100,   39,  101,
   65,  259,   94,   94,   94,   94,  177,   83,  166,  252,
   79,   41,   79,   79,   79,  214,  179,  181,  244,  164,
  116,    1,  266,   40,  176,  116,   59,  241,   79,   79,
   79,   79,  183,   66,  178,  180,   80,    2,   80,   80,
   80,  198,  199,   89,   90,   82,   72,  157,   37,   68,
  182,  141,  143,   81,   80,   80,   80,   80,    8,    9,
   32,   88,   77,  116,   77,   77,   77,   91,   94,   57,
  110,   92,   37,  219,  220,  100,  117,  101,   37,  125,
   77,   77,   77,   77,  103,    8,    9,  106,   78,  104,
   78,   78,   78,  123,   79,  231,  124,  126,  232,  146,
  148,  174,  192,  127,  175,  193,   78,   78,   78,   78,
  211,  245,  125,  193,  217,  125,  112,  128,   46,  112,
   80,   69,  132,   47,   45,   51,   46,  111,   48,  133,
  111,  207,  208,   37,  129,    6,   38,  215,  216,    7,
  150,  151,   10,  130,  134,  135,   77,  136,   12,  155,
  156,  162,   37,   37,   37,   47,   45,   51,   46,  168,
   48,  186,   98,   97,   99,   46,   44,  173,   47,   45,
  187,   46,   78,   48,  153,  190,  191,   60,   42,   43,
    8,    9,   34,  157,   46,  237,   35,  118,  184,    8,
    9,  107,   42,   43,    8,    9,  237,  196,  246,   94,
   94,   94,   94,   94,   79,   94,   94,   94,   94,  237,
   94,   94,   94,   94,   94,  108,   35,   94,  258,   94,
   94,  230,   94,  200,   94,   79,   79,   79,   79,   79,
  201,   79,   79,   79,   79,  243,   79,   79,   79,   79,
   79,  215,  216,   79,  221,   79,   79,  222,   79,  225,
   79,   80,   80,   80,   80,   80,  226,   80,   80,   80,
   80,  264,   80,   80,   80,   80,   80,   27,  233,   80,
  170,   80,   80,  227,   80,  234,   80,   77,   77,   77,
   77,   77,  235,   77,   77,   77,   77,  242,   77,   77,
   77,   77,   77,  248,   28,   77,  249,   77,   77,  250,
   77,  256,   77,   78,   78,   78,   78,   78,   49,   78,
   78,   78,   78,   67,   78,   78,   78,   78,   78,  215,
  216,   78,   47,   78,   78,   46,   78,   48,   78,   36,
   42,   43,   36,   49,   49,   49,  260,  262,   42,   43,
  270,  272,   49,   49,  276,   49,   49,   36,  277,    8,
    9,   37,   37,   10,   37,   53,   30,   49,  106,   30,
  105,   94,   95,   49,   96,    5,   39,   49,    8,  206,
   42,   43,   49,  189,   30,  269,  145,   42,   43,   64,
   53,   53,   53,   42,   43,   75,  213,  161,   75,   53,
   53,   49,   53,   53,    0,  147,   42,   43,   49,   49,
   49,   49,   49,   75,   53,    0,    0,    0,    0,    0,
   53,    0,    0,    0,   53,    0,    0,    0,   66,   53,
  229,    0,   49,    0,    0,    0,  215,  216,    0,   49,
   49,   47,    0,   43,   46,    0,   48,    0,   53,    0,
    0,    0,    0,    0,    0,   53,   53,   53,   53,   53,
   69,    0,   47,   45,   42,   46,    0,   48,    0,    0,
   49,    0,   49,   49,    0,    0,   27,    0,  133,   53,
    0,    0,   27,   27,    6,  251,   53,   53,    7,   27,
    0,   10,    0,    0,    0,  169,  257,   12,    0,  261,
   62,  263,   49,   28,   49,   49,    0,    0,  271,   28,
   28,  273,  274,    0,  275,   67,   28,   53,    0,   53,
   53,   67,    0,    0,  280,    0,    0,   67,    0,    0,
  105,   67,   67,   67,   67,    0,    0,    0,   67,    0,
   67,   67,    0,   67,    0,   67,   12,   42,   43,   53,
    0,   53,   53,    0,    0,   36,   36,    0,   49,    0,
    0,   36,   36,   36,    0,   36,   36,   36,   36,   36,
    0,    0,   36,  203,   36,   36,    0,   36,    0,   36,
    0,  268,   30,   30,    0,    0,    0,    0,   30,   30,
   30,    0,   30,   30,   30,   30,   30,  279,    0,   30,
    0,   30,   30,    0,   30,   53,   30,  165,    0,   47,
   45,   75,   46,  197,   48,   47,   45,   75,   46,    0,
   48,   75,   75,   75,   75,    0,   66,    0,   75,    0,
   75,   75,   66,   75,    0,   75,   66,   66,   66,   66,
    0,   43,    0,   66,    0,   66,   66,   43,   66,    0,
   66,   43,   43,   43,   43,  140,   42,   43,   43,    0,
   43,   43,   42,   43,   47,   43,    0,   46,   42,   48,
    0,    0,   42,   42,   42,   42,    0,   42,   43,   42,
    0,   42,   42,    5,   42,    0,   42,    0,    0,    6,
   98,   97,   99,    7,    8,    9,   10,  100,    5,  101,
    0,    0,   12,   13,    6,  236,    0,   14,    7,    8,
    9,   10,    0,    5,   98,   97,   99,   12,   13,    6,
    0,    0,   14,    7,    8,    9,   10,    0,    5,    0,
    0,    0,   12,   13,    6,    0,    0,   14,    7,    8,
    9,   10,    0,    0,   12,    0,    0,   12,   13,    0,
   12,    0,   14,  137,   12,   12,   12,   12,    0,    0,
    0,    0,    0,   12,   12,    0,    0,   44,   12,   47,
   45,  133,   46,    0,   48,    0,    0,    6,   74,  133,
    0,    7,    0,    0,   10,    6,  194,    0,  202,    7,
   12,    0,   10,    0,    0,  133,  267,  171,   12,    0,
  133,    6,  113,  114,    0,    7,    6,    0,   10,    0,
    7,   52,  278,   10,   12,    0,  135,    0,  205,   12,
    0,    0,    0,    0,   42,   43,    0,    0,    0,    5,
   42,   43,    0,    0,  204,    6,  137,    0,   78,    7,
    8,    9,   10,    0,    0,   84,  265,    0,   12,   13,
  133,  236,    0,   14,  152,    0,    6,    0,  133,    0,
    7,  158,    0,   10,    6,  224,   74,  223,    7,   12,
  121,   10,    0,    0,  253,  122,    0,   12,  142,   42,
   43,    0,    0,    0,    0,    0,  163,    0,    0,   94,
   95,    0,   96,    0,  139,    0,    0,    0,  158,    0,
    0,    0,    0,  144,  254,    0,  254,    0,    0,  254,
   93,  254,    0,   94,   95,  171,   96,    0,  254,    0,
    0,  254,  254,    0,  254,    0,    0,    0,    0,    0,
    0,  204,  121,  167,  254,  158,    5,    0,    0,    0,
    0,  224,    6,    0,    0,    0,    7,    8,    9,   10,
    0,    0,   11,    0,    0,   12,   13,    0,    5,    0,
   14,    0,    0,   52,    6,  121,  121,    0,    7,    8,
    9,   10,    0,    0,    0,  115,    0,   12,   13,    0,
    0,    0,   14,    0,   42,   43,    0,    0,    0,    0,
    5,    0,    0,    0,    0,   52,    6,  121,  121,    0,
    7,    8,    9,   10,    0,    5,    0,  120,    0,   12,
   13,    6,    0,    0,   14,    7,    8,    9,   10,    0,
    5,    0,  149,    0,   12,   13,    6,    0,    0,   14,
    7,    8,    9,   10,    0,    5,    0,    0,    0,   12,
   13,    6,  236,  133,   14,    7,    8,    9,   10,    6,
    0,    0,    0,    7,   12,   13,   10,    0,    0,   14,
  281,    0,   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          1,
    2,   40,   41,   42,   43,   40,   45,   41,   47,   11,
   40,   60,   60,   10,   85,   40,   41,   42,   43,   62,
   45,   41,   47,  162,   41,   42,   43,   44,   45,   40,
   47,  192,   44,   62,  257,   40,   45,   43,  256,   45,
  261,   41,   59,   60,   61,   62,   41,   44,  131,  238,
   41,   40,   43,   44,   45,  194,   41,   41,   41,  130,
   62,  257,  251,  281,   59,   67,   40,  228,   59,   60,
   61,   62,   41,  278,   59,   59,   41,  273,   43,   44,
   45,  164,  165,   47,   48,  257,   35,  257,  123,  274,
   59,  100,  101,   59,   59,   60,   61,   62,  268,  269,
    2,  258,   41,  105,   43,   44,   45,  271,  125,   11,
   59,   41,  123,  196,  197,   43,   65,   45,  123,  125,
   59,   60,   61,   62,   42,  268,  269,  274,   41,   47,
   43,   44,   45,   41,  125,  214,   44,  257,  217,  103,
  104,  272,   41,  257,  275,   44,   59,   60,   61,   62,
   41,  230,   41,   44,   59,   44,   41,  261,   45,   44,
  125,   40,   44,   42,   43,  162,   45,   41,   47,  257,
   44,  256,  257,   41,   41,  263,   44,  264,  265,  267,
   41,   41,  270,   41,  272,  273,  125,  275,  276,   62,
   40,  258,   60,   61,   62,   42,   43,  194,   45,  275,
   47,  257,   60,   61,   62,   45,   40,   59,   42,   43,
  257,   45,  125,   47,  257,  257,  257,  266,  257,  258,
  268,  269,  257,  257,   45,  227,  261,  257,  257,  268,
  269,  256,  257,  258,  268,  269,  238,   40,  258,  256,
  257,  258,  259,  260,  256,  262,  263,  264,  265,  251,
  267,  268,  269,  270,  271,  280,  261,  274,  258,  276,
  277,   59,  279,   41,  281,  256,  257,  258,  259,  260,
  274,  262,  263,  264,  265,  258,  267,  268,  269,  270,
  271,  264,  265,  274,   41,  276,  277,   41,  279,  275,
  281,  256,  257,  258,  259,  260,   59,  262,  263,  264,
  265,  250,  267,  268,  269,  270,  271,   59,  258,  274,
   59,  276,  277,  273,  279,   41,  281,  256,  257,  258,
  259,  260,   41,  262,  263,  264,  265,   41,  267,  268,
  269,  270,  271,  258,   59,  274,   41,  276,  277,   40,
  279,   41,  281,  256,  257,  258,  259,  260,   10,  262,
  263,  264,  265,   59,  267,  268,  269,  270,  271,  264,
  265,  274,   42,  276,  277,   45,  279,   47,  281,   41,
  257,  258,   44,   35,   36,   37,   41,   41,  257,  258,
   59,   41,   44,   45,   41,   47,   48,   59,  274,  268,
  269,  259,  260,    0,  262,   10,   41,   59,  278,   44,
  278,  259,  260,   65,  262,    0,   44,   69,    0,  174,
  257,  258,   74,  156,   59,  253,  256,  257,  258,   29,
   35,   36,   37,  257,  258,   41,  193,  124,   44,   44,
   45,   93,   47,   48,   -1,  256,  257,  258,  100,  101,
  102,  103,  104,   59,   59,   -1,   -1,   -1,   -1,   -1,
   65,   -1,   -1,   -1,   69,   -1,   -1,   -1,   59,   74,
  258,   -1,  124,   -1,   -1,   -1,  264,  265,   -1,  131,
  132,   42,   -1,   59,   45,   -1,   47,   -1,   93,   -1,
   -1,   -1,   -1,   -1,   -1,  100,  101,  102,  103,  104,
   40,   -1,   42,   43,   59,   45,   -1,   47,   -1,   -1,
  162,   -1,  164,  165,   -1,   -1,  258,   -1,  257,  124,
   -1,   -1,  264,  265,  263,   59,  131,  132,  267,  271,
   -1,  270,   -1,   -1,   -1,  274,  244,  276,   -1,  247,
   59,  249,  194,  258,  196,  197,   -1,   -1,  256,  264,
  265,  259,  260,   -1,  262,   59,  271,  162,   -1,  164,
  165,  257,   -1,   -1,  272,   -1,   -1,  263,   -1,   -1,
   59,  267,  268,  269,  270,   -1,   -1,   -1,  274,   -1,
  276,  277,   -1,  279,   -1,  281,   59,  257,  258,  194,
   -1,  196,  197,   -1,   -1,  257,  258,   -1,  250,   -1,
   -1,  263,  264,  265,   -1,  267,  268,  269,  270,  271,
   -1,   -1,  274,   59,  276,  277,   -1,  279,   -1,  281,
   -1,   59,  257,  258,   -1,   -1,   -1,   -1,  263,  264,
  265,   -1,  267,  268,  269,  270,  271,   59,   -1,  274,
   -1,  276,  277,   -1,  279,  250,  281,   40,   -1,   42,
   43,  257,   45,   40,   47,   42,   43,  263,   45,   -1,
   47,  267,  268,  269,  270,   -1,  257,   -1,  274,   -1,
  276,  277,  263,  279,   -1,  281,  267,  268,  269,  270,
   -1,  257,   -1,  274,   -1,  276,  277,  263,  279,   -1,
  281,  267,  268,  269,  270,  256,  257,  258,  274,   -1,
  276,  277,  257,  279,   42,  281,   -1,   45,  263,   47,
   -1,   -1,  267,  268,  269,  270,   -1,  257,  258,  274,
   -1,  276,  277,  257,  279,   -1,  281,   -1,   -1,  263,
   60,   61,   62,  267,  268,  269,  270,   43,  257,   45,
   -1,   -1,  276,  277,  263,  279,   -1,  281,  267,  268,
  269,  270,   -1,  257,   60,   61,   62,  276,  277,  263,
   -1,   -1,  281,  267,  268,  269,  270,   -1,  257,   -1,
   -1,   -1,  276,  277,  263,   -1,   -1,  281,  267,  268,
  269,  270,   -1,   -1,  257,   -1,   -1,  276,  277,   -1,
  263,   -1,  281,   91,  267,  268,  269,  270,   -1,   -1,
   -1,   -1,   -1,  276,  277,   -1,   -1,   40,  281,   42,
   43,  257,   45,   -1,   47,   -1,   -1,  263,   36,  257,
   -1,  267,   -1,   -1,  270,  263,   59,   -1,  274,  267,
  276,   -1,  270,   -1,   -1,  257,  274,  135,  276,   -1,
  257,  263,   60,   61,   -1,  267,  263,   -1,  270,   -1,
  267,   10,  274,  270,  276,   -1,  273,   -1,  275,  276,
   -1,   -1,   -1,   -1,  257,  258,   -1,   -1,   -1,  257,
  257,  258,   -1,   -1,  172,  263,  174,   -1,   37,  267,
  268,  269,  270,   -1,   -1,   44,  274,   -1,  276,  277,
  257,  279,   -1,  281,  112,   -1,  263,   -1,  257,   -1,
  267,  119,   -1,  270,  263,  203,  124,  274,  267,  276,
   69,  270,   -1,   -1,  273,   74,   -1,  276,  256,  257,
  258,   -1,   -1,   -1,   -1,   -1,  256,   -1,   -1,  259,
  260,   -1,  262,   -1,   93,   -1,   -1,   -1,  156,   -1,
   -1,   -1,   -1,  102,  242,   -1,  244,   -1,   -1,  247,
  256,  249,   -1,  259,  260,  253,  262,   -1,  256,   -1,
   -1,  259,  260,   -1,  262,   -1,   -1,   -1,   -1,   -1,
   -1,  269,  131,  132,  272,  193,  257,   -1,   -1,   -1,
   -1,  279,  263,   -1,   -1,   -1,  267,  268,  269,  270,
   -1,   -1,  273,   -1,   -1,  276,  277,   -1,  257,   -1,
  281,   -1,   -1,  162,  263,  164,  165,   -1,  267,  268,
  269,  270,   -1,   -1,   -1,  274,   -1,  276,  277,   -1,
   -1,   -1,  281,   -1,  257,  258,   -1,   -1,   -1,   -1,
  257,   -1,   -1,   -1,   -1,  194,  263,  196,  197,   -1,
  267,  268,  269,  270,   -1,  257,   -1,  274,   -1,  276,
  277,  263,   -1,   -1,  281,  267,  268,  269,  270,   -1,
  257,   -1,  274,   -1,  276,  277,  263,   -1,   -1,  281,
  267,  268,  269,  270,   -1,  257,   -1,   -1,   -1,  276,
  277,  263,  279,  257,  281,  267,  268,  269,  270,  263,
   -1,   -1,   -1,  267,  276,  277,  270,   -1,   -1,  281,
  274,   -1,  276,
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

//#line 319 "gramatica.y"
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
	String prueba= "programaAreconocer";
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
//#line 722 "Parser.java"
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
//#line 64 "gramatica.y"
{ lex.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón y el que abre la segunda");}
break;
case 33:
//#line 65 "gramatica.y"
{ lex.addErrorSintactico("Falta parentesis que abre la segunda lista del patrón");}
break;
case 34:
//#line 66 "gramatica.y"
{ lex.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón");}
break;
case 35:
//#line 68 "gramatica.y"
{ lex.addErrorSintactico("Falta comparador entre los patrones");}
break;
case 36:
//#line 69 "gramatica.y"
{ lex.addErrorSintactico("Falta comparador entre las expresiones");}
break;
case 40:
//#line 82 "gramatica.y"
{estructurasSintacticas("Se definió una sentencia de control sin else, en la linea: " + lex.getLineaInicial());}
break;
case 41:
//#line 83 "gramatica.y"
{estructurasSintacticas("Se definió una sentencia de control con else, en la linea: " + lex.getLineaInicial());}
break;
case 42:
//#line 85 "gramatica.y"
{ lex.addErrorSintactico("Falta END_IF con ELSE");}
break;
case 43:
//#line 86 "gramatica.y"
{ lex.addErrorSintactico("Falta END_IF");}
break;
case 44:
//#line 87 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de control del then");}
break;
case 45:
//#line 88 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de control del ELSE");}
break;
case 46:
//#line 89 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de control tanto en THEN como ELSE");}
break;
case 55:
//#line 103 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo del control");}
break;
case 56:
//#line 104 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo del control");}
break;
case 57:
//#line 105 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma");}
break;
case 60:
//#line 111 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de la iteracion");}
break;
case 61:
//#line 112 "gramatica.y"
{ lex.addErrorSintactico("Falta el cuerpo de la iteracion");}
break;
case 62:
//#line 113 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma");}
break;
case 65:
//#line 119 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma");}
break;
case 66:
//#line 123 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 67:
//#line 125 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 68:
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
case 69:
//#line 137 "gramatica.y"
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
//#line 146 "gramatica.y"
{ lex.addErrorSintactico("Falta coma en la lista de variables, puede haber parado la compilacion en este punto");}
break;
case 73:
//#line 154 "gramatica.y"
{estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 74:
//#line 155 "gramatica.y"
{estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 80:
//#line 169 "gramatica.y"
{ lex.addErrorSintactico("Falta operando izquierdo");}
break;
case 81:
//#line 170 "gramatica.y"
{ lex.addErrorSintactico("Falta operando derecho");}
break;
case 82:
//#line 171 "gramatica.y"
{ lex.addErrorSintactico("Falta operando derecho");}
break;
case 83:
//#line 172 "gramatica.y"
{ lex.addErrorSintactico("Falta operando derecho");}
break;
case 84:
//#line 173 "gramatica.y"
{ lex.addErrorSintactico("Falta operando derecho");}
break;
case 88:
//#line 183 "gramatica.y"
{ lex.addErrorSintactico("Falta operando izquierdo");}
break;
case 89:
//#line 184 "gramatica.y"
{ lex.addErrorSintactico("Falta operando izquierdo");}
break;
case 90:
//#line 185 "gramatica.y"
{ lex.addErrorSintactico("Falta operando derecho");}
break;
case 91:
//#line 186 "gramatica.y"
{ lex.addErrorSintactico("Falta operando derecho");}
break;
case 92:
//#line 187 "gramatica.y"
{ lex.addErrorSintactico("Falta operando derecho");}
break;
case 93:
//#line 188 "gramatica.y"
{ lex.addErrorSintactico("Falta operando derecho");}
break;
case 99:
//#line 198 "gramatica.y"
{	System.out.println("EEEEEEEEEEEEEEEEEEEEE" + "\n" + lex.getLineaInicial());
				if (ts.esUlongInt(val_peek(0).sval)){
					lex.addErrorSintactico("se utilizo un Ulongint negativo, son solo positivos");
				}
				else {
					ts.convertirNegativo(val_peek(0).sval);
				}
			}
break;
case 101:
//#line 211 "gramatica.y"
{ this.cantRetornos.add(0); }
break;
case 102:
//#line 211 "gramatica.y"
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
case 103:
//#line 222 "gramatica.y"
{ lex.addErrorSintactico("Falta nombre de la funcion declarada");}
break;
case 104:
//#line 223 "gramatica.y"
{ lex.addErrorSintactico("Falta el parametro en la declaracion de la funcion");}
break;
case 107:
//#line 230 "gramatica.y"
{ lex.addErrorSintactico("Se declaró más de un parametro");}
break;
case 109:
//#line 235 "gramatica.y"
{estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 110:
//#line 236 "gramatica.y"
{estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 111:
//#line 238 "gramatica.y"
{ lex.addErrorSintactico("Falta el nombre del parametro");}
break;
case 112:
//#line 239 "gramatica.y"
{ lex.addErrorSintactico("Falta el nombre del parametro o el tipo");}
break;
case 113:
//#line 242 "gramatica.y"
{ts.addClave(yylval.sval);}
break;
case 117:
//#line 250 "gramatica.y"
{lex.addErrorSintactico("Falta punto y coma");}
break;
case 120:
//#line 257 "gramatica.y"
{ this.cantRetornos.set(this.cantRetornos.size()-1, this.cantRetornos.get(this.cantRetornos.size()-1) + 1); }
break;
case 121:
//#line 260 "gramatica.y"
{estructurasSintacticas("Se invocó a la función: " + val_peek(3).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 122:
//#line 262 "gramatica.y"
{ lex.addErrorSintactico("Falta de parámetros en la invocación a la función");}
break;
case 123:
//#line 266 "gramatica.y"
{ lex.addErrorSintactico("Se utilizó más de un parámetro para invocar ala función");}
break;
case 128:
//#line 279 "gramatica.y"
{ lex.addErrorSintactico("Falta el mensaje del OUTF");}
break;
case 129:
//#line 280 "gramatica.y"
{ lex.addErrorSintactico("Parámetro invalido del OUTF");}
break;
case 132:
//#line 287 "gramatica.y"
{estructurasSintacticas("Se declaró un bucle FOR en la linea: " + lex.getLineaInicial());}
break;
case 133:
//#line 289 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma entre condicion y avance");}
break;
case 134:
//#line 290 "gramatica.y"
{ lex.addErrorSintactico("Falta punto y coma entre asignacion y condicion");}
break;
case 135:
//#line 291 "gramatica.y"
{ lex.addErrorSintactico("Faltan todos los punto y coma del for");}
break;
case 136:
//#line 292 "gramatica.y"
{ lex.addErrorSintactico("Falta UP/DOWN");}
break;
case 137:
//#line 293 "gramatica.y"
{lex.addErrorSintactico("Falta valor del UP/DOWN");}
break;
case 138:
//#line 294 "gramatica.y"
{ lex.addErrorSintactico("Falta UP/DOWN y punto y coma entre condicion y avance");}
break;
case 139:
//#line 295 "gramatica.y"
{ lex.addErrorSintactico("Falta valor del UP/DOWN y punto y coma entre condicion y avance");}
break;
case 140:
//#line 296 "gramatica.y"
{ { lex.addErrorSintactico("Falta UP/DOWN, su valor, y punto y coma entre condicion y avance");}}
break;
case 144:
//#line 305 "gramatica.y"
{lex.addErrorSintactico("falta la etiqueta en el GOTO, en caso de faltar también el punto y coma es posible que no compile el resto del programa o lo haga mal.");}
break;
case 145:
//#line 310 "gramatica.y"
{System.out.println("Se declaró un tipo TRIPLE con el ID: " + val_peek(0).sval + " en la linea:" + lex.getLineaInicial());}
break;
case 146:
//#line 312 "gramatica.y"
{lex.addErrorSintactico("falta < en la declaración del TRIPLE"); }
break;
case 147:
//#line 313 "gramatica.y"
{lex.addErrorSintactico("falta > en la declaración del TRIPLE"); }
break;
case 148:
//#line 314 "gramatica.y"
{lex.addErrorSintactico("falta > y < en la declaración del TRIPLE"); }
break;
case 149:
//#line 315 "gramatica.y"
{ lex.addErrorSintactico("Falta la palabra clave TRIPLE");}
break;
case 150:
//#line 316 "gramatica.y"
{ lex.addErrorSintactico("Falta el ID de la tripla definida.");}
break;
//#line 1252 "Parser.java"
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
