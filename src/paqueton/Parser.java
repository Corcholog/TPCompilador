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
   15,   15,   56,   55,   57,   57,   16,   16,   10,   10,
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
    2,    1,    1,    0,    5,    3,    1,    2,    1,    1,
    4,    0,    5,    3,    3,    1,    2,    1,    4,    3,
    4,    1,    1,   10,    9,    9,    8,    9,    9,    8,
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
    0,    0,    0,  134,    0,    0,    0,  158,    0,    0,
    0,    0,    0,    0,    0,    0,  107,   96,   97,   31,
    0,   56,   57,   58,   59,   60,   61,    0,    0,    0,
    0,    0,    0,    0,  143,  140,    0,    0,    0,    0,
    0,    4,   12,    0,    0,    0,    0,    0,    0,    8,
   10,    0,    0,    0,  136,  108,    0,    0,  153,    0,
    0,   28,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   54,    0,   52,    0,    0,   49,    0,    0,
   93,    0,   94,  141,  139,    0,  162,    0,    0,   80,
   78,    0,    0,    0,    0,  119,    7,    0,  133,    0,
  154,    0,  155,  156,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   90,   89,   92,   91,   65,    0,   73,
    0,   63,   45,    0,   55,   51,  100,   99,  101,   98,
  161,    0,  160,  163,  114,    0,  121,  120,  112,    0,
  135,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   34,    0,   64,   66,    0,   74,    0,   50,    0,  159,
  124,    0,  124,  118,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   35,   36,    0,   62,   72,   46,
  164,  115,    0,  110,  113,    0,    0,  150,    0,  152,
    0,    0,    0,  151,    0,  147,   37,   33,    0,  124,
   70,    0,    0,   68,  148,    0,  149,  145,  146,    0,
  130,    0,  127,  129,  111,   69,   71,    0,  144,    0,
    0,  128,   67,    0,  125,  126,  131,
};
final static short yydgoto[] = {                          3,
   33,    5,    4,   16,   64,   18,   19,   20,   21,   22,
   23,   24,   25,   26,   27,   28,  129,   53,  180,  100,
   84,  181,   85,  182,   55,  147,  148,  149,  196,  191,
  258,   29,   67,   40,   30,   57,   58,   59,   31,  165,
  206,  252,  270,  233,  231,  166,  253,  282,  283,  284,
  124,   75,  125,  108,   80,  130,  176,
};
final static short yysindex[] = {                      -237,
    0,  593,    0,    0,  529,  -51,    7, -170,   28,    0,
    0,  435,   49,  -55,    0,  373, -177,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -152, -140,
 -150,  593,    0,  388,    0,    0,   12,  107,   12, -152,
   76,    0, -105,  -15,    0,  531,  427, -100,   -5,   -5,
    0,    0,  133,  577,  -87,    0,   27,    0,    0,  -19,
  -28, -126,  546,    0,    0,    0,    2,   12,  -34,  405,
  -59,  639,  157,    0,   57,    6,    2,    0,   -7,  567,
   12,  223,  577,    3,  222,   27,    0,    0,    0,    0,
   12,    0,    0,    0,    0,    0,    0,  342,  520,   12,
  692,   21,   48,  230,    0,    0,  157,  232, -126,  -60,
  241,    0,    0,   63,   65,  157,  290,   39,  662,    0,
    0,  157,   12,   68,    0,    0,   74,  435,    0,   19,
  157,    0,  572,  660,   12,  157,   55,   27,  124,   27,
  157,    7,    0,  447,    0,  278, -118,    0,   80,  127,
    0,  139,    0,    0,    0,  -50,    0,  103,  109,    0,
    0,  310,  128,  138,  141,    0,    0,  157,    0,   57,
    0,  -52,    0,    0,  -75,  106,  360,  666,   12,  157,
  347,  362,  157,    0,    0,    0,    0,    0,  135,    0,
  459,    0,    0,  703,    0,    0,    0,    0,    0,    0,
    0,   56,    0,    0,    0,   39,    0,    0,    0,   39,
    0,  371,  -40,  -32,  156,  380,   12,   12,  385,  392,
    0,   12,    0,    0, -107,    0,   80,    0,  376,    0,
    0,  147,    0,    0,  714,  397,  714,  -30,  399,  714,
  411,  714,  417,  418,    0,    0,  157,    0,    0,    0,
    0,    0,  188,    0,    0,  471,  407,    0,  714,    0,
  429,  714,  714,    0,  714,    0,    0,    0,  677,    0,
    0,  194,  486,    0,    0,  714,    0,    0,    0,  431,
    0,  348,    0,    0,    0,    0,    0,  725,    0,   12,
  618,    0,    0,  254,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0, -199,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  420,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  195,    0,
    0,    0,    0,    0,    0,    0,    0,  104,    0,    0,
    0,    0,    0,  134,    0,    0,    0,    0,    0,    0,
    0,    0,   23,    0,    0,    0,  160,    0,    0,    0,
    0,    0,  476,    0,    0,    0,   66,    0,    0,    0,
  420,  481,  100,    0,    0,    0,  272,    0,    0,    0,
    0,   94,  439,    0,    0,  186,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  443,    0,    0,    0,
    0,    0,    0,    0,    0,  287,    0,    0,  485,    0,
    0,  280,    0,    0,    0,    0,    0,    0,    0,    0,
  439,    0,    0,    0,    0,  -22,    0,  231,    0,  257,
  -14,    0,    0,    0,    0,    0,  312,    0,  327,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   70,  300,  309,    0,    0,    0,  334,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  442,
    0,    0,  -27,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  339,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,   16,   -2,    0,  617,    0,    0,    0,
    0,   -8,    0,    0,    0,    0,  480,  451,   64,  -71,
    0,  131,    0,    0,    0,  315,  356,  357, -184,  255,
  528,  637,  483,    0,   38,   24,   31,    0,    0,  326,
    0, -217,    0,    0,    0,  333,    0,    0, -175,    0,
    0,    0,  367,    0,    0,  419, -157,
};
final static int YYTABLESIZE=1001;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         17,
  237,  158,   35,   51,   62,  118,  213,   36,  240,  228,
  262,  202,  134,   39,  214,  255,   40,  215,   38,    1,
   34,  106,   49,   47,   38,   48,   32,   50,   51,   71,
   51,  109,   39,   39,   39,    2,   38,   51,   51,   48,
   51,   51,  250,  133,   32,  115,   38,   70,   98,   56,
   99,   51,  285,   49,   47,  238,   48,   76,   50,   51,
  113,  178,   96,   95,   97,   48,   51,   43,  102,  113,
   86,   51,   51,  103,   56,   54,   56,  175,  117,   88,
   89,   29,   51,   56,   56,   41,   56,   56,   60,   51,
   51,   51,   48,   51,   51,  185,   65,   56,   49,   47,
   73,   48,   76,   50,   66,   56,  292,   39,  169,   83,
   42,  170,   56,  184,   51,  296,  113,   56,   56,   51,
   68,  138,  140,  107,   75,   51,   51,   69,   56,   39,
  126,  116,  151,  153,   78,   56,   56,   56,  122,   56,
   56,   10,   11,   54,  131,  132,  132,   74,  132,  142,
  132,   79,   30,  143,  136,    8,  145,   87,   84,    9,
   56,   51,   12,  141,  187,   56,  248,  198,   13,   51,
   51,   56,   56,   90,  102,  102,  102,  102,  102,  200,
  102,  209,  186,  101,  210,  197,  168,  254,  173,  174,
  210,   54,  102,  102,  102,  102,  157,  199,  183,   98,
   87,   99,   87,   87,   87,  212,  201,   56,   51,   51,
   61,  173,  174,   51,  120,   56,   56,  236,   87,   87,
   87,   87,  117,  173,  174,  239,   88,  261,   88,   88,
   88,   39,   39,  122,   39,   38,  104,   44,   45,   10,
   11,   38,   38,   32,   88,   88,   88,   88,   38,   32,
   32,   44,   45,  127,   56,   56,   32,  114,  102,   56,
  105,   92,   93,  132,   94,  135,  281,   37,   44,   45,
  154,   85,  155,   85,   85,   85,  150,   44,   45,  281,
   29,   51,  173,  174,   87,  247,   29,   29,  281,   85,
   85,   85,   85,   29,  297,  163,   98,   86,   99,   86,
   86,   86,  159,  152,   44,   45,   10,   11,  219,  220,
   88,  229,  230,   44,   45,   86,   86,   86,   86,  160,
  138,  161,   75,  138,   10,   11,  109,   56,   75,  162,
   77,  171,   75,   75,   75,   75,  192,  109,  109,   75,
  123,   75,   75,  123,   75,   83,   75,  243,  244,  122,
  205,   30,  122,  294,  195,   85,   84,   30,   30,  203,
  132,  132,   84,  216,   30,  204,   84,   84,   84,   84,
   47,  132,  132,   84,  137,   84,   84,  137,   84,   42,
   84,   86,   43,   49,  207,   48,   48,  221,   50,  102,
  102,  102,  102,  102,  208,  102,  102,  102,  102,  217,
  102,  102,  102,  102,  102,  222,  291,  102,  223,  102,
  102,  235,  102,  241,  102,   87,   87,   87,   87,   87,
  242,   87,   87,   87,   87,  245,   87,   87,   87,   87,
   87,   63,  246,   87,  251,   87,   87,  259,   87,  263,
   87,   88,   88,   88,   88,   88,   72,   88,   88,   88,
   88,  265,   88,   88,   88,   88,   88,  267,  268,   88,
  269,   88,   88,  119,   88,  274,   88,  286,   49,  276,
  290,   48,  116,   50,   46,    6,   49,   47,   13,   48,
   11,   50,   41,  142,    9,   44,   85,   85,   85,   85,
   85,   52,   85,   85,   85,   85,   82,   85,   85,   85,
   85,   85,  193,  194,   85,  189,   85,   85,  227,   85,
  273,   85,   86,   86,   86,   86,   86,  225,   86,   86,
   86,   86,   77,   86,   86,   86,   86,   86,   77,  272,
   86,  232,   86,   86,   77,   86,  211,   86,   77,   77,
   77,   77,  234,   83,  288,   77,  172,   77,   77,   83,
   77,    0,   77,   83,   83,   83,   83,    0,    0,    0,
   83,   49,   83,   83,   48,   83,   50,   83,   47,    0,
   81,    0,   49,   47,   47,   48,    0,   50,   47,   47,
   47,   47,    0,   48,    0,   47,    0,   47,   47,   48,
   47,    0,   47,   48,   48,   48,   48,  137,   44,   45,
   48,    0,   48,   48,    7,   48,   46,   48,   49,   47,
    8,   48,    0,   50,    9,   10,   11,   12,    0,   98,
    0,   99,    0,   13,   14,  128,  280,    0,   15,    7,
    0,   96,   95,   97,    0,    8,   96,   95,   97,    9,
   10,   11,   12,    0,    7,    0,    0,    0,   13,   14,
    8,    0,    0,   15,    9,   10,   11,   12,    0,    0,
    0,    7,    0,   13,   14,    0,    0,    8,   15,    0,
    0,    9,   10,   11,   12,    0,   13,    0,    0,    0,
   13,   14,   13,   44,   45,   15,   13,   13,   13,   13,
    0,   44,   45,    0,    0,   13,   13,  110,  111,  179,
   13,   49,   47,  142,   48,  218,   50,   49,   47,    8,
   48,  123,   50,    9,    0,  142,   12,  146,    0,    0,
  188,    8,   13,    0,    0,    9,    0,  142,   12,    0,
    0,    0,  224,    8,   13,    0,    0,    9,    0,    0,
   12,    0,  142,    0,  271,  156,   13,    0,    8,    0,
    0,    0,    9,    0,  164,   12,    0,    0,    0,  287,
  190,   13,    0,    0,  260,    0,    0,  264,    0,  266,
    0,    0,    0,    0,    0,  139,   44,   45,    0,    0,
    0,    0,    0,    0,    6,    7,  275,   44,   45,  277,
  278,    8,  279,    0,    0,    9,   10,   11,   12,    0,
    0,   32,    7,  289,   13,   14,  123,  226,    8,   15,
  146,    0,    9,   10,   11,   12,    0,    0,    0,  112,
    0,   13,   14,   44,   45,    0,   15,  177,    0,    0,
   92,   93,   91,   94,    0,   92,   93,    0,   94,    0,
    0,  249,  164,    0,    0,    0,  164,    0,    6,    7,
    0,  257,    0,  257,    0,    8,  257,    0,  257,    9,
   10,   11,   12,    0,    0,    0,    0,    0,   13,   14,
    0,    0,  190,   15,    7,  257,    0,    0,  257,  257,
    8,  257,    0,    0,    9,   10,   11,   12,    0,  226,
    0,  295,  257,   13,   14,    7,  280,    0,   15,    0,
    0,    8,    0,    0,  249,    9,   10,   11,   12,    0,
    0,    0,  121,    0,   13,   14,   44,   45,    7,   15,
    0,    0,   44,   45,    8,    0,    0,    0,    9,   10,
   11,   12,    0,    7,    0,  167,    0,   13,   14,    8,
    0,    0,   15,    9,   10,   11,   12,    0,  142,    0,
    0,    0,   13,   14,    8,  280,    0,   15,    9,  142,
    0,   12,    0,  143,  144,    8,  145,   13,    0,    9,
  142,    0,   12,    0,    0,  144,    8,  195,   13,    0,
    9,  142,    0,   12,    0,    0,  256,    8,    0,   13,
    0,    9,    0,    0,   12,    0,    0,    0,  293,    0,
   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   41,   62,    5,   12,   60,   40,   59,   59,   41,  194,
   41,   62,   84,   41,  172,  233,   44,  175,   41,  257,
    5,   41,   42,   43,   40,   45,   41,   47,   37,   32,
   39,   60,   60,   61,   62,  273,   59,   46,   47,   45,
   49,   50,  227,   41,   59,   44,   40,   32,   43,   12,
   45,   60,  270,   42,   43,  213,   45,  257,   47,   68,
   63,  133,   60,   61,   62,   45,   75,   40,   42,   72,
   47,   80,   81,   47,   37,   12,   39,   59,  278,   49,
   50,   59,   91,   46,   47,  256,   49,   50,   40,   98,
   99,  100,   45,  102,  103,   41,  274,   60,   42,   43,
   37,   45,   39,   47,  257,   68,  282,  123,   41,   46,
  281,   44,   75,   59,  123,  291,  119,   80,   81,  128,
  261,   98,   99,   60,   59,  134,  135,  278,   91,  123,
  125,   68,  102,  103,   59,   98,   99,  100,   75,  102,
  103,  268,  269,   80,   81,   42,   43,   41,   45,  257,
   47,  257,   59,  272,   91,  263,  275,  258,   59,  267,
  123,  170,  270,  100,   41,  128,  274,   41,  276,  178,
  179,  134,  135,   41,   41,   42,   43,   44,   45,   41,
   47,   41,   59,  271,   44,   59,  123,   41,  264,  265,
   44,  128,   59,   60,   61,   62,  257,   59,  135,   43,
   41,   45,   43,   44,   45,  258,  257,  170,  217,  218,
  266,  264,  265,  222,  274,  178,  179,  258,   59,   60,
   61,   62,  257,  264,  265,  258,   41,  258,   43,   44,
   45,  259,  260,  170,  262,  258,  256,  257,  258,  268,
  269,  264,  265,  258,   59,   60,   61,   62,  271,  264,
  265,  257,  258,  261,  217,  218,  271,  256,  125,  222,
  280,  259,  260,   41,  262,   44,  269,  261,  257,  258,
   41,   41,   41,   43,   44,   45,  256,  257,  258,  282,
  258,  290,  264,  265,  125,  222,  264,  265,  291,   59,
   60,   61,   62,  271,   41,  257,   43,   41,   45,   43,
   44,   45,   62,  256,  257,  258,  268,  269,  178,  179,
  125,  256,  257,  257,  258,   59,   60,   61,   62,  257,
   41,  257,  257,   44,  268,  269,  257,  290,  263,   40,
   59,  258,  267,  268,  269,  270,   59,  268,  269,  274,
   41,  276,  277,   44,  279,   59,  281,  217,  218,   41,
   41,  258,   44,  290,  275,  125,  257,  264,  265,  257,
  257,  258,  263,  258,  271,  257,  267,  268,  269,  270,
   59,  268,  269,  274,   41,  276,  277,   44,  279,   41,
  281,  125,   44,   42,  257,   59,   45,   41,   47,  256,
  257,  258,  259,  260,  257,  262,  263,  264,  265,   40,
  267,  268,  269,  270,  271,   44,   59,  274,  274,  276,
  277,   41,  279,  258,  281,  256,  257,  258,  259,  260,
   41,  262,  263,  264,  265,   41,  267,  268,  269,  270,
  271,   59,   41,  274,   59,  276,  277,   41,  279,   41,
  281,  256,  257,  258,  259,  260,   59,  262,  263,  264,
  265,   41,  267,  268,  269,  270,  271,   41,   41,  274,
  273,  276,  277,   59,  279,   59,  281,  274,   42,   41,
   40,   45,  278,   47,   40,    0,   42,   43,   59,   45,
    0,   47,   44,   41,    0,   44,  256,  257,  258,  259,
  260,   12,  262,  263,  264,  265,   46,  267,  268,  269,
  270,  271,  147,  147,  274,   59,  276,  277,  194,  279,
  256,  281,  256,  257,  258,  259,  260,   59,  262,  263,
  264,  265,   40,  267,  268,  269,  270,  271,  257,   59,
  274,  206,  276,  277,  263,  279,  170,  281,  267,  268,
  269,  270,  210,  257,   59,  274,  128,  276,  277,  263,
  279,   -1,  281,  267,  268,  269,  270,   -1,   -1,   -1,
  274,   42,  276,  277,   45,  279,   47,  281,  257,   -1,
   40,   -1,   42,   43,  263,   45,   -1,   47,  267,  268,
  269,  270,   -1,  257,   -1,  274,   -1,  276,  277,  263,
  279,   -1,  281,  267,  268,  269,  270,  256,  257,  258,
  274,   -1,  276,  277,  257,  279,   40,  281,   42,   43,
  263,   45,   -1,   47,  267,  268,  269,  270,   -1,   43,
   -1,   45,   -1,  276,  277,   59,  279,   -1,  281,  257,
   -1,   60,   61,   62,   -1,  263,   60,   61,   62,  267,
  268,  269,  270,   -1,  257,   -1,   -1,   -1,  276,  277,
  263,   -1,   -1,  281,  267,  268,  269,  270,   -1,   -1,
   -1,  257,   -1,  276,  277,   -1,   -1,  263,  281,   -1,
   -1,  267,  268,  269,  270,   -1,  257,   -1,   -1,   -1,
  276,  277,  263,  257,  258,  281,  267,  268,  269,  270,
   -1,  257,  258,   -1,   -1,  276,  277,   61,   62,   40,
  281,   42,   43,  257,   45,   40,   47,   42,   43,  263,
   45,   75,   47,  267,   -1,  257,  270,  101,   -1,   -1,
  274,  263,  276,   -1,   -1,  267,   -1,  257,  270,   -1,
   -1,   -1,  274,  263,  276,   -1,   -1,  267,   -1,   -1,
  270,   -1,  257,   -1,  274,  109,  276,   -1,  263,   -1,
   -1,   -1,  267,   -1,  118,  270,   -1,   -1,   -1,  274,
  144,  276,   -1,   -1,  237,   -1,   -1,  240,   -1,  242,
   -1,   -1,   -1,   -1,   -1,  256,  257,  258,   -1,   -1,
   -1,   -1,   -1,   -1,  256,  257,  259,  257,  258,  262,
  263,  263,  265,   -1,   -1,  267,  268,  269,  270,   -1,
   -1,  273,  257,  276,  276,  277,  170,  191,  263,  281,
  194,   -1,  267,  268,  269,  270,   -1,   -1,   -1,  274,
   -1,  276,  277,  257,  258,   -1,  281,  256,   -1,   -1,
  259,  260,  256,  262,   -1,  259,  260,   -1,  262,   -1,
   -1,  225,  206,   -1,   -1,   -1,  210,   -1,  256,  257,
   -1,  235,   -1,  237,   -1,  263,  240,   -1,  242,  267,
  268,  269,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,
   -1,   -1,  256,  281,  257,  259,   -1,   -1,  262,  263,
  263,  265,   -1,   -1,  267,  268,  269,  270,   -1,  273,
   -1,  274,  276,  276,  277,  257,  279,   -1,  281,   -1,
   -1,  263,   -1,   -1,  288,  267,  268,  269,  270,   -1,
   -1,   -1,  274,   -1,  276,  277,  257,  258,  257,  281,
   -1,   -1,  257,  258,  263,   -1,   -1,   -1,  267,  268,
  269,  270,   -1,  257,   -1,  274,   -1,  276,  277,  263,
   -1,   -1,  281,  267,  268,  269,  270,   -1,  257,   -1,
   -1,   -1,  276,  277,  263,  279,   -1,  281,  267,  257,
   -1,  270,   -1,  272,  273,  263,  275,  276,   -1,  267,
  257,   -1,  270,   -1,   -1,  273,  263,  275,  276,   -1,
  267,  257,   -1,  270,   -1,   -1,  273,  263,   -1,  276,
   -1,  267,   -1,   -1,  270,   -1,   -1,   -1,  274,   -1,
  276,
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
"param_real : tipo expresion_matematica",
"param_real : expresion_matematica",
"sald_mensaj : OUTF '(' mensaje ')'",
"sald_mensaj : OUTF '(' ')'",
"sald_mensaj : OUTF '(' error ')'",
"mensaje : expresion_matematica",
"mensaje : CADMUL",
"for : FOR '(' asignacion_for ';' condicion_for ';' foravanc CTE ')' cuerpo_iteracion",
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

//#line 393 "gramatica.y"
String nombreArchivo;
AnalizadorLexico lex;
TablaSimbolos ts;
GeneradorCodigo gc;
Stack<GeneradorCodigo> gc_funciones;
String tipoVar;
ArrayList<Integer> cantRetornos;
String estructuras;
ArrayList<String> varFors;
String ambitoActual;
String funcionActual;
Integer inicioPatron;
Integer posPatron;
public Parser(String nombreArchivo, TablaSimbolos t, GeneradorCodigo gc)
{
	this.nombreArchivo=nombreArchivo;
	this.funcionActual = "";
	this.ambitoActual = "";
	this.inicioPatron = Integer.MAX_VALUE;
	this.posPatron = -1;
	this.ts=t;
	this.gc = gc;
	this.gc_funciones = new Stack<GeneradorCodigo>();
	this.gc_funciones.push(gc);
	this.cantRetornos = new ArrayList<>();
	this.estructuras = "Estructuras sintacticas detectadas en el codigo fuente :  \n";
	this.lex= new AnalizadorLexico(nombreArchivo, t, this);
	this.varFors = new ArrayList<>();
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
	System.out.println("Ambito antes: " + ambitoActual);
        if (index != -1) {
            this.ambitoActual = this.ambitoActual.substring(0, index); // Retorna todo hasta el ":"
        }
	System.out.println("Ambito despues: " + ambitoActual);
}

boolean esEmbebido(String sval){
	char firstC=sval.charAt(0);
	if ( firstC =='x' || firstC =='y' || firstC =='z' || firstC == 'd' ) {
		return true;
	}
	return false;
}

boolean varRedeclarada(){
	char firstC=yylval.sval.charAt(0);
	if ( (tipoVar.equals(AccionSemantica.ULONGINT) && (firstC =='x' || firstC =='y' || firstC =='z')) || (tipoVar.equals(AccionSemantica.DOUBLE) && (firstC == 'd')) ) {
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
	if (varRedeclarada()){
		ErrorHandler.addErrorSemantico("Se redeclaro el tipo de la variable", lex.getLineaInicial());
	}
	else if (redeclaracionTipoErroneo()){
		ErrorHandler.addErrorSemantico("Se redeclaro el tipo de la variable y con tipos erroneos", lex.getLineaInicial());
	}
	else {
		ts.addClave(val);
		ts.addAtributo(val,AccionSemantica.TIPO,tipoVar);
		if(!tipoVar.equals(AccionSemantica.DOUBLE) && !tipoVar.equals(AccionSemantica.ULONGINT)){
			if(!this.ts.getAtributo(tipoVar, AccionSemantica.TIPO).equals("")){
				ts.addAtributo(val, AccionSemantica.TIPO_BASICO, this.ts.getAtributo(tipoVar, AccionSemantica.TIPO));
			} else {
				ErrorHandler.addErrorSemantico("No existe la tripla con el ID " + tipoVar, lex.getLineaInicial());
			}
		}
		lex.getLineaInicial();
	}
}

void checkRedeclaracionFuncion(){
	if (varRedeclarada()){
		ErrorHandler.addErrorSemantico("Se redeclaro el tipo de la funcion", lex.getLineaInicial());
	}
	else if (redeclaracionTipoErroneo()){
		ErrorHandler.addErrorSemantico("Se redeclaro el tipo de la funcion y con tipos erroneos", lex.getLineaInicial());
	}
}

void estructurasSintacticas(String estructura){
	estructuras += estructura + "\n";
}

void checkRet(String nombreFuncion) {
	if (!nombreFuncion.isEmpty()) {
		if (this.cantRetornos.get(this.cantRetornos.size()-1) > 0){
			estructurasSintacticas("Se declaró la función: " + nombreFuncion);
			ts.addClave(nombreFuncion);
			ts.addAtributo(nombreFuncion,AccionSemantica.TIPO,tipoVar);
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
    
    System.out.println(p.lex.getListaTokens());
    System.out.println("\n" + p.estructuras);	
    System.out.println("Errores y Warnings detectados del codigo fuente :  \n" + p.errores());
    System.out.println("Contenido de la tabla de simbolos:  \n" + tb);
    System.out.println("Codigo intermedio en tercetos: " + gc);
    
    if (valido == 0) {
        System.out.println("Se analizo todo el codigo fuente");
    } else {
        System.out.println("No se analizo completamente el codigo fuente, debido a uno o mas errores inesperados");
    }
}
//#line 875 "Parser.java"
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
{this.ambitoActual = val_peek(0).sval;}
break;
case 2:
//#line 10 "gramatica.y"
{ estructurasSintacticas("Se declaró el programa: " + val_peek(2).sval); }
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
			}
		}
break;
case 25:
//#line 56 "gramatica.y"
{estructurasSintacticas("Se imprimio un mensaje, en linea: " + lex.getLineaInicial()); }
break;
case 27:
//#line 58 "gramatica.y"
{estructurasSintacticas("Se llamo a una etiqueta goto, en linea: " + lex.getLineaInicial()); }
break;
case 28:
//#line 62 "gramatica.y"
{ yyval.sval = val_peek(1).sval;}
break;
case 29:
//#line 64 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de paréntesis en la condición", lex.getLineaInicial());}
break;
case 30:
//#line 65 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de paréntesis derecho en la condición", lex.getLineaInicial());}
break;
case 31:
//#line 66 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de paréntesis izquierdo en la condición", lex.getLineaInicial());}
break;
case 32:
//#line 69 "gramatica.y"
{ yyval.sval = gc.addTerceto(val_peek(1).sval, val_peek(2).sval, val_peek(0).sval); gc.checkTipo(gc.getPosActual(), lex.getLineaInicial(), this.ts);}
break;
case 33:
//#line 70 "gramatica.y"
{ if(gc.getTerceto(gc.getPosActual()).getOp2().isEmpty()){ErrorHandler.addErrorSemantico("La longitud de los patrones a matchear es distinta.", lex.getLineaInicial());}else { yyval.sval = gc.updateCompAndGenerate(this.inicioPatron, val_peek(3).sval);} this.inicioPatron = Integer.MAX_VALUE;}
break;
case 34:
//#line 72 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón y el que abre la segunda", lex.getLineaInicial());}
break;
case 35:
//#line 73 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que abre la segunda lista del patrón", lex.getLineaInicial());}
break;
case 36:
//#line 74 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón", lex.getLineaInicial());}
break;
case 37:
//#line 75 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta comparador entre los patrones", lex.getLineaInicial());}
break;
case 38:
//#line 76 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta comparador entre las expresiones", lex.getLineaInicial());}
break;
case 39:
//#line 79 "gramatica.y"
{ this.iniciarPatron(); yyval.sval = gc.addTerceto("COMP", val_peek(0).sval, "");}
break;
case 40:
//#line 82 "gramatica.y"
{ this.iniciarPatron(); yyval.sval = gc.addTerceto("COMP", val_peek(0).sval, "");}
break;
case 41:
//#line 83 "gramatica.y"
{this.iniciarPatron(); yyval.sval = gc.addTerceto("COMP", val_peek(0).sval, "");}
break;
case 42:
//#line 86 "gramatica.y"
{ gc.updateAndCheckSize(this.posPatron, val_peek(0).sval, lex.getLineaInicial(), this.ts); this.posPatron++;}
break;
case 43:
//#line 89 "gramatica.y"
{ gc.updateAndCheckSize(this.posPatron, val_peek(0).sval, lex.getLineaInicial(), this.ts); this.posPatron++;}
break;
case 44:
//#line 90 "gramatica.y"
{ gc.updateAndCheckSize(this.posPatron, val_peek(0).sval, lex.getLineaInicial(), this.ts); this.posPatron++;}
break;
case 45:
//#line 93 "gramatica.y"
{estructurasSintacticas("Se definió una sentencia de control sin else, en la linea: " + lex.getLineaInicial());}
break;
case 47:
//#line 97 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta END_IF con ELSE", lex.getLineaInicial());}
break;
case 48:
//#line 98 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta END_IF", lex.getLineaInicial());}
break;
case 49:
//#line 99 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control del then", lex.getLineaInicial());}
break;
case 50:
//#line 100 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control del ELSE", lex.getLineaInicial());}
break;
case 51:
//#line 101 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control tanto en THEN como ELSE", lex.getLineaInicial());}
break;
case 52:
//#line 106 "gramatica.y"
{
			gc.actualizarBF(gc.getCantTercetos()); 
			gc.pop();
			}
break;
case 53:
//#line 112 "gramatica.y"
{
			gc.addTerceto("BF", val_peek(0).sval, ""); 
			gc.push(gc.getPosActual());
		}
break;
case 54:
//#line 118 "gramatica.y"
{ 
			gc.addTerceto("BI", "", "-"); 
			gc.actualizarBF(gc.getCantTercetos()); 
			gc.pop(); 
			gc.push(gc.getPosActual());
		}
break;
case 55:
//#line 125 "gramatica.y"
{
			gc.actualizarBI(gc.getCantTercetos()); 
			gc.pop();
			estructurasSintacticas("Se definió una sentencia de control con else, en la linea: " + lex.getLineaInicial());
		}
break;
case 59:
//#line 134 "gramatica.y"
{yyval.sval = "=";}
break;
case 60:
//#line 135 "gramatica.y"
{yyval.sval = "<";}
break;
case 61:
//#line 136 "gramatica.y"
{yyval.sval = ">";}
break;
case 64:
//#line 142 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
break;
case 65:
//#line 143 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
break;
case 66:
//#line 144 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 69:
//#line 150 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
break;
case 70:
//#line 151 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
break;
case 71:
//#line 152 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 74:
//#line 158 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 75:
//#line 162 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 76:
//#line 164 "gramatica.y"
{tipoVar = val_peek(0).sval;}
break;
case 77:
//#line 164 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 78:
//#line 167 "gramatica.y"
{checkRedeclaracion(val_peek(0).sval);}
break;
case 79:
//#line 168 "gramatica.y"
{checkRedeclaracion(val_peek(0).sval);}
break;
case 80:
//#line 169 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta coma en la lista de variables, puede haber parado la compilacion en este punto", lex.getLineaInicial());}
break;
case 81:
//#line 173 "gramatica.y"
{yyval.sval = "double";}
break;
case 82:
//#line 174 "gramatica.y"
{yyval.sval = "ulongint";}
break;
case 83:
//#line 177 "gramatica.y"
{estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());
						yyval.sval = gc.addTerceto(":=", val_peek(2).sval, val_peek(0).sval); gc.checkTipoAsignacion(val_peek(2).sval, lex.getLineaInicial(), val_peek(0).sval, this.ts);

					}
break;
case 84:
//#line 181 "gramatica.y"
{  estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());
					yyval.sval = gc.addTerceto(":=", val_peek(2).sval, val_peek(0).sval); gc.checkTipoAsignacion(val_peek(2).sval, lex.getLineaInicial(), val_peek(0).sval, this.ts);
		}
break;
case 85:
//#line 187 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "+");}
break;
case 86:
//#line 188 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "-");}
break;
case 88:
//#line 195 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 89:
//#line 196 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");}
break;
case 90:
//#line 198 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
break;
case 91:
//#line 200 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");}
break;
case 92:
//#line 202 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
break;
case 93:
//#line 208 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "*");}
break;
case 94:
//#line 209 "gramatica.y"
{yyval.sval = gc.checkTipoExpresion(val_peek(2).sval, val_peek(0).sval, lex.getLineaInicial(), this.ts, "/");}
break;
case 96:
//#line 213 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 97:
//#line 214 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 98:
//#line 215 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 99:
//#line 217 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 100:
//#line 219 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
break;
case 101:
//#line 221 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
break;
case 102:
//#line 225 "gramatica.y"
{ gc.checkDeclaracion(val_peek(0).sval, lex.getLineaInicial(), this.ts);}
break;
case 106:
//#line 231 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 107:
//#line 232 "gramatica.y"
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
//#line 242 "gramatica.y"
{String tipo = this.ts.getAtributo(val_peek(3).sval, AccionSemantica.TIPO_BASICO); if(tipo.equals("")){ ErrorHandler.addErrorSemantico( "La tripla " + val_peek(3).sval + " nunca fue declarada.", lex.getLineaInicial()) ; tipo = "error";}yyval.sval = gc.addTerceto("ACCESOTRIPLE", val_peek(3).sval, val_peek(1).sval, tipo);}
break;
case 109:
//#line 245 "gramatica.y"
{this.funcionActual = val_peek(1).sval;}
break;
case 110:
//#line 245 "gramatica.y"
{ this.cantRetornos.add(0); this.gc_funciones.push(this.ts.getGCFuncion(val_peek(4).sval)); this.gc = this.gc_funciones.peek(); this.ambitoActual += ":" + val_peek(4).sval;}
break;
case 111:
//#line 245 "gramatica.y"
{
								
								tipoVar = val_peek(8).sval; this.checkRet(val_peek(6).sval);
								this.gc_funciones.pop();
								this.gc = this.gc_funciones.peek();
								if (esEmbebido(val_peek(6).sval)){ErrorHandler.addErrorSemantico("No se puede declarar una funcion con un ID con tipos embebidos.", lex.getLineaInicial());} this.cambiarAmbito();
							}
break;
case 112:
//#line 252 "gramatica.y"
{ this.cantRetornos.add(0);}
break;
case 113:
//#line 252 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta nombre de la funcion declarada", lex.getLineaInicial());
													this.checkRet("");
													}
break;
case 114:
//#line 255 "gramatica.y"
{ this.cantRetornos.add(0);}
break;
case 115:
//#line 255 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el parametro en la declaracion de la funcion", lex.getLineaInicial());
							    this.checkRet(val_peek(4).sval);
							    if (esEmbebido(val_peek(4).sval)){ErrorHandler.addErrorSemantico("No se puede declarar una funcion con un ID con tipos embebidos.", lex.getLineaInicial());}
							   }
break;
case 118:
//#line 265 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Se declaró más de un parametro", lex.getLineaInicial());}
break;
case 120:
//#line 270 "gramatica.y"
{ this.ts.addAtributo(val_peek(0).sval,AccionSemantica.TIPO, val_peek(1).sval); this.ts.addAtributo(funcionActual, AccionSemantica.PARAMETRO, val_peek(0).sval); estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 121:
//#line 271 "gramatica.y"
{ this.ts.addAtributo(val_peek(0).sval,AccionSemantica.TIPO, val_peek(1).sval); this.ts.addAtributo(funcionActual, AccionSemantica.PARAMETRO, val_peek(0).sval); estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 122:
//#line 273 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el nombre del parametro", lex.getLineaInicial());}
break;
case 123:
//#line 274 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el nombre del parametro o el tipo", lex.getLineaInicial());}
break;
case 124:
//#line 277 "gramatica.y"
{ts.addClave(yylval.sval);}
break;
case 128:
//#line 285 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 131:
//#line 292 "gramatica.y"
{ this.cantRetornos.set(this.cantRetornos.size()-1, this.cantRetornos.get(this.cantRetornos.size()-1) + 1); 
					yyval.sval = gc.addTerceto("RET", val_peek(1).sval, "");		
		}
break;
case 132:
//#line 297 "gramatica.y"
{funcionActual = val_peek(1).sval; }
break;
case 133:
//#line 297 "gramatica.y"
{ 
							estructurasSintacticas("Se invocó a la función: " + val_peek(4).sval + " en la linea: " + lex.getLineaInicial());
							String tipo = this.ts.getAtributo(val_peek(4).sval, AccionSemantica.TIPO);
							if(tipo.equals("")){
								ErrorHandler.addErrorSemantico("La funcion invocada " + val_peek(4).sval + " no existe.", lex.getLineaInicial());
								tipo = "error";
							}
							yyval.sval = gc.addTerceto("INVOC_FUN", val_peek(4).sval, val_peek(2).sval, tipo);
		}
break;
case 134:
//#line 307 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de parámetros en la invocación a la función", lex.getLineaInicial());}
break;
case 135:
//#line 311 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Se utilizó más de un parámetro para invocar a la función", lex.getLineaInicial());}
break;
case 136:
//#line 312 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 137:
//#line 315 "gramatica.y"
{ yyval.sval = gc.addTerceto("TO".concat(val_peek(1).sval), val_peek(0).sval, val_peek(1).sval); if(!this.ts.getAtributo(this.ts.getAtributo(funcionActual, AccionSemantica.PARAMETRO), AccionSemantica.TIPO).equals(val_peek(1).sval)){ ErrorHandler.addErrorSemantico("El tipo del parametro real no coincide con el tipo del parametro formal.", lex.getLineaInicial());}}
break;
case 138:
//#line 316 "gramatica.y"
{yyval.sval = val_peek(0).sval; gc.checkParamReal(val_peek(0).sval, lex.getLineaInicial(), this.ts, funcionActual);}
break;
case 139:
//#line 322 "gramatica.y"
{yyval.sval = gc.addTerceto("OUTF", val_peek(1).sval, "");}
break;
case 140:
//#line 324 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el mensaje del OUTF", lex.getLineaInicial());}
break;
case 141:
//#line 325 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Parámetro invalido del OUTF", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 144:
//#line 333 "gramatica.y"
{	estructurasSintacticas("Se declaró un bucle FOR en la linea: " + lex.getLineaInicial()); 
				String var = this.varFors.get(this.varFors.size()-1);
				if(!this.ts.getAtributo(val_peek(7).sval, AccionSemantica.TIPO).equals(AccionSemantica.ULONGINT)){
					ErrorHandler.addErrorSemantico("La constante de avance no es de tipo entero.", lex.getLineaInicial()); 
					gc.addTerceto("+", var, String.valueOf(val_peek(3).ival * Double.parseDouble(val_peek(2).sval)));
				} else {
					gc.addTerceto("+", var, String.valueOf(val_peek(3).ival * Integer.parseInt(val_peek(2).sval)));
				}		
				this.varFors.remove(this.varFors.size()-1);
				gc.addTerceto("BI", val_peek(5).sval, "");
				gc.actualizarBF(gc.getCantTercetos());
				gc.pop();
		}
break;
case 145:
//#line 347 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 146:
//#line 348 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma entre asignacion y condicion", lex.getLineaInicial());}
break;
case 147:
//#line 349 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Faltan todos los punto y coma del for", lex.getLineaInicial());}
break;
case 148:
//#line 350 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta UP/DOWN", lex.getLineaInicial());}
break;
case 149:
//#line 351 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN", lex.getLineaInicial());}
break;
case 150:
//#line 352 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 151:
//#line 353 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 152:
//#line 354 "gramatica.y"
{ { ErrorHandler.addErrorSintactico("Falta UP/DOWN, su valor, y punto y coma entre condicion y avance", lex.getLineaInicial());}}
break;
case 153:
//#line 358 "gramatica.y"
{ yyval.sval = val_peek(0).sval;
				gc.addTerceto("BF", val_peek(0).sval, "");
				gc.push(gc.getPosActual());
			}
break;
case 154:
//#line 364 "gramatica.y"
{
				if(!this.ts.getAtributo(val_peek(2).sval, AccionSemantica.TIPO).equals(AccionSemantica.ULONGINT)){ErrorHandler.addErrorSemantico("La constante asignada a " + val_peek(2).sval + " no es de tipo entero.", lex.getLineaInicial());}
				if(!this.ts.getAtributo(val_peek(0).sval, AccionSemantica.TIPO).equals(AccionSemantica.ULONGINT)){ErrorHandler.addErrorSemantico("La constante " + val_peek(0).sval + " no es de tipo entero.", lex.getLineaInicial());}
				gc.addTerceto(":=", val_peek(2).sval, val_peek(0).sval);
				this.varFors.add(val_peek(2).sval);
				}
break;
case 155:
//#line 372 "gramatica.y"
{yyval.ival = 1;}
break;
case 156:
//#line 373 "gramatica.y"
{yyval.ival = -1;}
break;
case 157:
//#line 376 "gramatica.y"
{ yyval.sval = gc.addTerceto("GOTO", val_peek(0).sval,"");}
break;
case 158:
//#line 378 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta la etiqueta en el GOTO, en caso de faltar también el punto y coma es posible que no compile el resto del programa o lo haga mal.", lex.getLineaInicial());
				lex.setErrorHandlerToken(";");}
break;
case 159:
//#line 384 "gramatica.y"
{estructurasSintacticas("Se declaró un tipo TRIPLE con el ID: " + val_peek(0).sval + " en la linea:" + lex.getLineaInicial()); this.ts.addAtributo(val_peek(0).sval, "tipotripla", val_peek(2).sval); this.ts.addAtributo(val_peek(0).sval, "tipo", val_peek(2).sval);}
break;
case 160:
//#line 386 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta < en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 161:
//#line 387 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta > en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 162:
//#line 388 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta > y < en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 163:
//#line 389 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta la palabra clave TRIPLE", lex.getLineaInicial());}
break;
case 164:
//#line 390 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el ID de la tripla definida.", lex.getLineaInicial());}
break;
//#line 1615 "Parser.java"
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
