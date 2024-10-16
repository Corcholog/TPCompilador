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
    0,    0,    2,    2,    2,    1,    1,    1,    1,    1,
    3,    3,    3,    3,    4,    4,    5,    5,    5,    5,
    6,    6,    6,    6,    6,    6,   16,   16,   16,   16,
   17,   17,   17,   17,   17,   17,   17,   20,   21,   21,
   12,   12,   12,   12,   12,   12,   12,   24,   22,   25,
   26,   19,   19,   19,   19,   19,   19,   23,   23,   23,
   23,   23,   28,   28,   28,   28,   28,   27,   27,   27,
    7,    7,    7,   31,   32,   32,   30,   30,   30,   29,
   29,   10,   10,   34,   34,   18,   18,   18,   18,   18,
   18,   18,   18,   35,   35,   35,   35,   35,   35,   35,
   35,   35,   36,   36,   36,   36,   37,   37,   33,   41,
    8,   42,    8,   43,    8,   38,   38,   39,   39,   44,
   44,   44,   44,   45,   40,   46,   46,   46,   47,   47,
   48,   11,   11,   49,   49,   50,   50,   13,   13,   13,
   51,   51,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   53,   52,   54,   54,   15,   15,    9,    9,    9,
    9,    9,    9,
};
final static short yylen[] = {                            2,
    2,    1,    4,    3,    3,    4,    3,    3,    3,    2,
    3,    1,    2,    2,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    3,    1,    2,    2,
    3,    7,    5,    6,    6,    7,    3,    3,    3,    1,
    5,    7,    4,    4,    4,    6,    5,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    4,    2,    3,
    2,    3,    4,    2,    3,    2,    3,    3,    1,    2,
    2,    3,    1,    1,    3,    1,    3,    1,    3,    1,
    1,    3,    3,    1,    1,    3,    3,    1,    2,    4,
    4,    4,    4,    3,    3,    1,    2,    2,    4,    4,
    4,    4,    1,    1,    1,    1,    1,    2,    4,    0,
    8,    0,    7,    0,    7,    1,    1,    3,    1,    2,
    2,    1,    1,    0,    5,    3,    1,    2,    1,    1,
    4,    4,    3,    3,    1,    2,    1,    4,    3,    4,
    1,    1,   10,    9,    9,    8,    9,    9,    8,    8,
    8,    1,    3,    1,    1,    2,    3,    6,    5,    5,
    4,    5,    7,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    0,    0,    0,   81,   80,
    0,    0,    0,    0,   19,    1,    0,   12,   15,   16,
   17,   18,   20,   21,   22,   23,   24,   25,   26,    0,
   73,    0,    0,    0,    0,    0,   13,    0,    0,    0,
    0,    0,  156,    0,    0,  107,    0,    0,    0,    0,
    0,  105,   49,    0,    0,    0,  106,    0,   96,  104,
    0,    0,    0,    0,    0,    0,   14,   78,    0,    0,
    0,    0,    0,    4,    0,   85,    0,   83,  133,    0,
  137,    0,  135,    0,    0,  157,    0,    0,    0,    0,
    0,    0,    0,  108,   97,   98,   30,    0,   52,   53,
   54,   55,   56,   57,    0,    0,    0,    0,    0,    0,
    0,    7,    0,  142,  139,  141,    0,    0,    0,    0,
    9,   11,    0,    0,   75,   82,    0,    0,    3,    0,
    0,  132,    0,  109,    0,    0,  152,    0,   27,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   50,
    0,   48,    0,    0,   45,    0,    0,   94,    0,   95,
    6,  140,  138,    0,  161,    0,    0,   79,   77,    0,
    0,    0,    0,  119,  134,  153,    0,  154,  155,    0,
    0,    0,    0,    0,    0,    0,   91,   90,   93,   92,
   61,    0,   69,    0,   59,   41,    0,   51,   47,  101,
  100,  102,   99,  160,    0,  159,  162,  114,    0,  121,
  120,  112,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   33,   60,   62,    0,   70,    0,   46,    0,
  158,  124,  110,  124,  118,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   34,   35,   58,   68,   42,
  163,  115,    0,  124,  113,    0,    0,  149,    0,  151,
    0,    0,    0,  150,    0,  146,   36,   32,    0,  111,
   66,    0,    0,   64,  147,    0,  148,  144,  145,    0,
  130,    0,  127,  129,   65,   67,    0,  143,    0,    0,
  128,   63,    0,  125,  126,  131,
};
final static short yydgoto[] = {                          3,
   16,    4,   17,   67,   19,   20,   21,   22,   23,   24,
   25,   26,   27,   28,   29,  137,   76,   77,  107,   91,
   92,   56,  154,  155,  156,  199,  194,  258,   30,   69,
   31,   32,   33,   81,   58,   59,   60,   34,  173,  252,
  254,  234,  232,  174,  253,  282,  283,  284,   82,   83,
  117,   88,  138,  181,
};
final static short yysindex[] = {                      -181,
  568,  680,    0,    0,   -7,    5, -212,   38,    0,    0,
  345,  680,   66,  -29,    0,    0,  289,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -234,
    0,   -4, -198, -165,  461, -152,    0,  550,    6, -234,
   13,   84,    0,  -91,  -14,    0,  550,  118,  -86,   -2,
   -2,    0,    0,  135,   69,  -74,    0,  122,    0,    0,
  476,  -70,  -33,   90,  -66,  696,    0,    0,  -17,  -31,
  550,  -19,  712,    0,   13,    0,   69,    0,    0,   13,
    0,  145,    0,  -17,  -10,    0,    4,  483,  216,   69,
  138,  225,  122,    0,    0,    0,    0,   13,    0,    0,
    0,    0,    0,    0,   28,  173,   13,  637,  106,  184,
  727,    0,  232,    0,    0,    0,  242,  -66,  -38,  234,
    0,    0,   37,   47,    0,    0,  269,  -84,    0,   16,
   16,    0,  -27,    0,   63,  345,    0,   23,    0,  604,
  582,   13,   16,   75,  122,   82,  122,   16,  -15,    0,
  512,    0,  271,  -79,    0,   62,  108,    0,  112,    0,
    0,    0,    0,    2,    0,   99,  132,    0,    0,  -24,
  136,  142,  274,    0,    0,    0,  343,    0,    0,  155,
  137,  325,  638,   13,  367,   16,    0,    0,    0,    0,
    0,  139,    0,  513,    0,    0,  757,    0,    0,    0,
    0,    0,    0,    0,  182,    0,    0,    0,  337,    0,
    0,    0,  -84,  384,  -37,  -36,  169,  402,   13,   13,
  403,  404,    0,    0,    0,  768,    0,   62,    0,  375,
    0,    0,    0,    0,    0,  585,  405,  585,    9,  416,
  585,  422,  585,  423,  425,    0,    0,    0,    0,    0,
    0,    0,  196,    0,    0,  535,  412,    0,  585,    0,
  442,  585,  585,    0,  585,    0,    0,    0,  742,    0,
    0,  215,  547,    0,    0,  585,    0,    0,    0,  450,
    0,  440,    0,    0,    0,    0,  780,    0,  550,  608,
    0,    0,  451,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,  238,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  222,
    0,  264,    0,    0,    0,  491,    0,    0,    0,    0,
    0,    0,    0,    0,   43,    0,    0,    0,    0,    0,
    0,    0,    0,  239,    0,    0,    0,   76,    0,    0,
    0,  491,    0,    0,    0,  502,    0,    0,  363,    0,
    0,    0,  509,    0,    0,    0,  342,    0,    0,    0,
    0,    0,    0,  378,    0,    0,    0,    0,  290,  469,
    0,    0,  147,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  518,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  469,
  350,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  103,    0,  191,    0,  217,  310,    0,    0,
    0,    0,    0,  393,    0,  419,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  391,  392,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   33,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  140,   -1,    0,  665,    0,    0,    0,    0,
   -9,    0,    0,    0,    0,  511,  500,  715,  -57,  530,
    0,    0,  327,  382,  383, -191,  273,  -82,  755,  499,
    0,    0,  107,  -35,   20,   30,    0,    0,  374, -143,
    0,    0,    0,  336,    0,    0, -183,    0,    0,  418,
    0,    0,  417, -158,
};
final static int YYTABLESIZE=1056;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   36,   52,   78,  238,  241,  229,   75,  115,   50,   48,
   62,   49,   75,   51,   50,   48,  208,   49,  216,   51,
  128,  217,   68,  166,   39,   39,  124,  116,   52,   52,
   65,   52,  105,  141,  106,  126,  250,   52,   52,   70,
   52,   52,   49,   42,   39,   75,   79,   50,   48,  262,
   49,   37,   51,   52,   50,   48,  239,   49,  105,   51,
  106,   52,   71,  205,  122,   52,   40,   93,   43,   50,
   52,  122,   49,   38,   51,    1,   39,   44,   52,   95,
   96,  180,  183,  103,  103,  103,  103,  103,   52,  103,
  255,    2,   38,   38,   38,   52,   52,   52,  291,   52,
   52,  103,  103,  103,  103,   63,  295,   41,   41,  122,
  270,  105,   72,  106,  134,  188,   88,   57,   88,   88,
   88,   74,  190,   52,  145,  147,   52,   41,  103,  102,
  104,   52,   52,  187,   88,   88,   88,   88,  158,  160,
  189,   35,   86,   37,   57,   57,   37,   57,  201,  118,
   49,   61,  203,   57,   57,  260,   57,   57,  264,   50,
  266,   37,   49,  109,   51,   87,  200,  103,  110,   57,
  202,   94,  171,   52,   52,   97,  275,   57,  140,  277,
  278,   57,  279,    9,   10,  132,   57,   89,  133,   89,
   89,   89,  150,  288,   57,  152,  108,  103,  102,  104,
   88,    9,   10,  112,   57,   89,   89,   89,   89,   52,
   52,   57,   57,   57,   50,   57,   57,   49,  165,   51,
  237,  240,  113,   45,   46,  125,  178,  179,   49,   45,
   46,   86,  171,   86,   86,   86,   64,  127,  123,   57,
    9,   10,   57,    9,   10,   38,  114,   57,   57,   86,
   86,   86,   86,  293,   45,   46,  139,   87,  204,   87,
   87,   87,   45,   46,  135,   38,  261,  281,  142,   45,
   46,   89,  162,    9,   10,   87,   87,   87,   87,   52,
  281,   76,  163,  144,   45,   46,  178,  179,  281,   57,
   57,   38,   38,  168,   38,  167,   76,   28,  103,  103,
  103,  103,  103,  169,  103,  103,  103,  103,  170,  103,
  103,  103,  103,  103,  212,   86,  103,  213,  103,  103,
  176,  103,   74,  103,   98,   57,   57,   99,  100,  195,
  101,   88,   88,   88,   88,   88,  198,   88,   88,   88,
   88,   87,   88,   88,   88,   88,   88,   66,   29,   88,
   31,   88,   88,   31,   88,  206,   88,    9,   10,   37,
   37,  157,   45,   46,  219,   37,   37,   37,   31,   37,
   37,   37,   37,   37,   45,   46,   37,  233,   37,   37,
  213,   37,   84,   37,   47,   84,   50,   48,  207,   49,
  136,   51,  210,  136,  218,   57,   99,  100,  211,  101,
   84,  215,   89,   89,   89,   89,   89,  223,   89,   89,
   89,   89,  224,   89,   89,   89,   89,   89,  178,  179,
   89,   71,   89,   89,  236,   89,  242,   89,  146,   45,
   46,  123,  122,  251,  123,  122,   72,  230,  231,  159,
   45,   46,  243,  246,  247,  259,   86,   86,   86,   86,
   86,   43,   86,   86,   86,   86,  263,   86,   86,   86,
   86,   86,  265,  267,   86,  268,   86,   86,  269,   86,
  274,   86,   87,   87,   87,   87,   87,   44,   87,   87,
   87,   87,  276,   87,   87,   87,   87,   87,  285,  289,
   87,  296,   87,   87,   76,   87,   28,   87,  290,  116,
   76,   10,   28,   28,   76,   76,   76,   76,    5,   28,
   54,   76,   40,   76,   76,  117,   76,    8,   76,   73,
   74,   53,   47,  228,   50,   48,   74,   49,  273,   51,
   74,   74,   74,   74,  111,  196,  197,   74,   84,   74,
   74,  136,   74,  209,   74,    6,   89,   29,  235,   12,
  175,    7,  177,   29,   29,    8,    9,   10,   11,    0,
   29,    0,    0,    0,   13,   14,   31,   31,    0,   15,
  192,  226,   31,   31,   31,    0,   31,   31,   31,   31,
   31,    0,    0,   31,    0,   31,   31,   54,   31,   75,
   31,   50,   48,  272,   49,    0,   51,    0,   84,    0,
  214,   45,   46,    0,   84,  287,  178,  179,   84,   84,
   84,   84,    0,    0,    0,   84,    0,   84,   84,   71,
   84,  184,   84,   50,   48,   71,   49,    0,   51,   71,
   71,   71,   71,    0,   72,   54,   71,    0,   71,   71,
   72,   71,    0,   71,   72,   72,   72,   72,    0,   43,
    0,   72,    0,   72,   72,   43,   72,    0,   72,   43,
   43,   43,   43,  103,  102,  104,   43,    0,   43,   43,
  185,   43,    0,   43,    0,   44,    0,  220,    0,   50,
   48,   44,   49,    0,   51,   44,   44,   44,   44,    0,
    0,    0,   44,    0,   44,   44,    6,   44,    0,   44,
    0,    0,    7,    0,    0,    0,    8,    9,   10,   11,
    0,    0,  221,  222,    0,   13,   14,    6,  280,    0,
   15,    0,    0,    7,    0,   55,    0,    8,    9,   10,
   11,    0,    6,    0,    0,    0,   13,   14,    7,   45,
   46,   15,    8,    9,   10,   11,    0,   12,  244,  245,
    0,   13,   14,   12,    0,   85,   15,   12,   12,   12,
   12,   90,    0,    0,    0,    0,   12,   12,  149,  149,
    0,   12,  153,    0,    7,    7,    0,    0,    8,    8,
    0,   11,   11,    0,    0,  191,  225,   13,   13,  130,
    0,  149,    0,   80,  131,    0,    0,    7,    0,    0,
    0,    8,   55,  149,   11,    0,   45,   46,  271,    7,
   13,    0,  143,    8,    0,  193,   11,    0,  119,  120,
  286,  148,   13,    5,    6,    0,    0,    0,    0,    0,
    7,    0,    0,    0,    8,    9,   10,   11,   45,   46,
   12,  149,    0,   13,   14,    0,    0,    7,   15,    0,
   55,    8,    0,    0,   11,  130,  186,  256,  227,  182,
   13,  153,   99,  100,    6,  101,    0,    0,    0,    0,
    7,    0,  164,    0,    8,    9,   10,   11,    0,    0,
    0,  294,  172,   13,   14,    0,  280,   80,   15,    0,
  249,    0,    0,  149,   45,   46,    0,  130,  130,    7,
  257,    0,  257,    8,    0,  257,   11,  257,  150,  151,
    0,  152,   13,    0,    0,    0,    0,    0,    0,    0,
  193,    0,    0,  257,  172,    0,  257,  257,    0,  257,
    0,    0,    0,  130,  130,    5,    6,  227,    0,    0,
  257,    0,    7,    0,    0,    0,    8,    9,   10,   11,
    0,  249,    6,    0,    0,   13,   14,    0,    7,    0,
   15,    0,    8,    9,   10,   11,    0,  172,    6,  121,
    0,   13,   14,    0,    7,    0,   15,    0,    8,    9,
   10,   11,    0,    6,    0,  129,    0,   13,   14,    7,
    0,    0,   15,    8,    9,   10,   11,    0,    6,    0,
  161,    0,   13,   14,    7,    0,    0,   15,    8,    9,
   10,   11,    0,  149,    0,    0,    0,   13,   14,    7,
  280,    0,   15,    8,  149,    0,   11,    0,    0,  151,
    7,  198,   13,    0,    8,    0,  149,   11,    0,    0,
    0,  248,    7,   13,    0,    0,    8,    0,    0,   11,
    0,    0,    0,  292,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          1,
    2,   11,   38,   41,   41,  197,   40,   41,   42,   43,
   12,   45,   40,   47,   42,   43,   41,   45,  177,   47,
   40,  180,  257,   62,   40,   40,   44,   63,   38,   39,
   60,   41,   43,   91,   45,   71,  228,   47,   48,   44,
   50,   51,   45,  256,   40,   40,   41,   42,   43,   41,
   45,   59,   47,   63,   42,   43,  215,   45,   43,   47,
   45,   71,  261,   62,   66,   75,   62,   48,  281,   42,
   80,   73,   45,   41,   47,  257,   44,   40,   88,   50,
   51,   59,  140,   41,   42,   43,   44,   45,   98,   47,
  234,  273,   60,   61,   62,  105,  106,  107,  282,  109,
  110,   59,   60,   61,   62,   40,  290,  123,  123,  111,
  254,   43,  278,   45,  125,   41,   41,   11,   43,   44,
   45,  274,   41,  133,  105,  106,  136,  123,   60,   61,
   62,  141,  142,   59,   59,   60,   61,   62,  109,  110,
   59,    2,   59,   41,   38,   39,   44,   41,   41,   60,
   45,   12,   41,   47,   48,  238,   50,   51,  241,   42,
  243,   59,   45,   42,   47,  257,   59,  125,   47,   63,
   59,  258,  257,  183,  184,   41,  259,   71,   41,  262,
  263,   75,  265,  268,  269,   41,   80,   41,   44,   43,
   44,   45,  272,  276,   88,  275,  271,   60,   61,   62,
  125,  268,  269,  274,   98,   59,   60,   61,   62,  219,
  220,  105,  106,  107,   42,  109,  110,   45,  257,   47,
  258,  258,  256,  257,  258,  257,  264,  265,   45,  257,
  258,   41,  257,   43,   44,   45,  266,  257,  256,  133,
  268,  269,  136,  268,  269,  261,  280,  141,  142,   59,
   60,   61,   62,  289,  257,  258,   41,   41,  257,   43,
   44,   45,  257,  258,  261,  261,  258,  269,   44,  257,
  258,  125,   41,  268,  269,   59,   60,   61,   62,  289,
  282,   44,   41,  256,  257,  258,  264,  265,  290,  183,
  184,  259,  260,  257,  262,   62,   59,   59,  256,  257,
  258,  259,  260,  257,  262,  263,  264,  265,   40,  267,
  268,  269,  270,  271,   41,  125,  274,   44,  276,  277,
  258,  279,   59,  281,  256,  219,  220,  259,  260,   59,
  262,  256,  257,  258,  259,  260,  275,  262,  263,  264,
  265,  125,  267,  268,  269,  270,  271,   59,   59,  274,
   41,  276,  277,   44,  279,  257,  281,  268,  269,  257,
  258,  256,  257,  258,   40,  263,  264,  265,   59,  267,
  268,  269,  270,  271,  257,  258,  274,   41,  276,  277,
   44,  279,   41,  281,   40,   44,   42,   43,  257,   45,
   41,   47,  257,   44,  258,  289,  259,  260,  257,  262,
   59,   59,  256,  257,  258,  259,  260,   41,  262,  263,
  264,  265,  274,  267,  268,  269,  270,  271,  264,  265,
  274,   59,  276,  277,   41,  279,  258,  281,  256,  257,
  258,   41,   41,   59,   44,   44,   59,  256,  257,  256,
  257,  258,   41,   41,   41,   41,  256,  257,  258,  259,
  260,   59,  262,  263,  264,  265,   41,  267,  268,  269,
  270,  271,   41,   41,  274,   41,  276,  277,  273,  279,
   59,  281,  256,  257,  258,  259,  260,   59,  262,  263,
  264,  265,   41,  267,  268,  269,  270,  271,  274,   40,
  274,   41,  276,  277,  257,  279,  258,  281,   59,  278,
  263,    0,  264,  265,  267,  268,  269,  270,    0,  271,
   11,  274,   44,  276,  277,  278,  279,    0,  281,   59,
  257,   11,   40,  197,   42,   43,  263,   45,  256,   47,
  267,  268,  269,  270,   59,  154,  154,  274,   40,  276,
  277,   59,  279,  170,  281,  257,   47,  258,  213,   59,
  133,  263,  136,  264,  265,  267,  268,  269,  270,   -1,
  271,   -1,   -1,   -1,  276,  277,  257,  258,   -1,  281,
   59,   59,  263,  264,  265,   -1,  267,  268,  269,  270,
  271,   -1,   -1,  274,   -1,  276,  277,   88,  279,   40,
  281,   42,   43,   59,   45,   -1,   47,   -1,  257,   -1,
  258,  257,  258,   -1,  263,   59,  264,  265,  267,  268,
  269,  270,   -1,   -1,   -1,  274,   -1,  276,  277,  257,
  279,   40,  281,   42,   43,  263,   45,   -1,   47,  267,
  268,  269,  270,   -1,  257,  136,  274,   -1,  276,  277,
  263,  279,   -1,  281,  267,  268,  269,  270,   -1,  257,
   -1,  274,   -1,  276,  277,  263,  279,   -1,  281,  267,
  268,  269,  270,   60,   61,   62,  274,   -1,  276,  277,
  141,  279,   -1,  281,   -1,  257,   -1,   40,   -1,   42,
   43,  263,   45,   -1,   47,  267,  268,  269,  270,   -1,
   -1,   -1,  274,   -1,  276,  277,  257,  279,   -1,  281,
   -1,   -1,  263,   -1,   -1,   -1,  267,  268,  269,  270,
   -1,   -1,  183,  184,   -1,  276,  277,  257,  279,   -1,
  281,   -1,   -1,  263,   -1,   11,   -1,  267,  268,  269,
  270,   -1,  257,   -1,   -1,   -1,  276,  277,  263,  257,
  258,  281,  267,  268,  269,  270,   -1,  257,  219,  220,
   -1,  276,  277,  263,   -1,   41,  281,  267,  268,  269,
  270,   47,   -1,   -1,   -1,   -1,  276,  277,  257,  257,
   -1,  281,  108,   -1,  263,  263,   -1,   -1,  267,  267,
   -1,  270,  270,   -1,   -1,  274,  274,  276,  276,   75,
   -1,  257,   -1,   39,   80,   -1,   -1,  263,   -1,   -1,
   -1,  267,   88,  257,  270,   -1,  257,  258,  274,  263,
  276,   -1,   98,  267,   -1,  151,  270,   -1,   64,   65,
  274,  107,  276,  256,  257,   -1,   -1,   -1,   -1,   -1,
  263,   -1,   -1,   -1,  267,  268,  269,  270,  257,  258,
  273,  257,   -1,  276,  277,   -1,   -1,  263,  281,   -1,
  136,  267,   -1,   -1,  270,  141,  142,  273,  194,  256,
  276,  197,  259,  260,  257,  262,   -1,   -1,   -1,   -1,
  263,   -1,  118,   -1,  267,  268,  269,  270,   -1,   -1,
   -1,  274,  128,  276,  277,   -1,  279,  133,  281,   -1,
  226,   -1,   -1,  257,  257,  258,   -1,  183,  184,  263,
  236,   -1,  238,  267,   -1,  241,  270,  243,  272,  273,
   -1,  275,  276,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  256,   -1,   -1,  259,  170,   -1,  262,  263,   -1,  265,
   -1,   -1,   -1,  219,  220,  256,  257,  273,   -1,   -1,
  276,   -1,  263,   -1,   -1,   -1,  267,  268,  269,  270,
   -1,  287,  257,   -1,   -1,  276,  277,   -1,  263,   -1,
  281,   -1,  267,  268,  269,  270,   -1,  213,  257,  274,
   -1,  276,  277,   -1,  263,   -1,  281,   -1,  267,  268,
  269,  270,   -1,  257,   -1,  274,   -1,  276,  277,  263,
   -1,   -1,  281,  267,  268,  269,  270,   -1,  257,   -1,
  274,   -1,  276,  277,  263,   -1,   -1,  281,  267,  268,
  269,  270,   -1,  257,   -1,   -1,   -1,  276,  277,  263,
  279,   -1,  281,  267,  257,   -1,  270,   -1,   -1,  273,
  263,  275,  276,   -1,  267,   -1,  257,  270,   -1,   -1,
   -1,  274,  263,  276,   -1,   -1,  267,   -1,   -1,  270,
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
"declaracion_var : ID '>' lista_variables",
"declaracion_var : embed",
"embed : lista_variables_e",
"lista_variables_e : lista_variables_e ',' ID",
"lista_variables_e : ID",
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

//#line 380 "gramatica.y"
String nombreArchivo;
AnalizadorLexico lex;
TablaSimbolos ts;
GeneradorCodigo gc;
Stack<GeneradorCodigo> gc_funciones;
String tipoVar;
ArrayList<Integer> cantRetornos;
String estructuras;
ArrayList<String> varFors;
public Parser(String nombreArchivo, TablaSimbolos t, GeneradorCodigo gc)
{
	this.nombreArchivo=nombreArchivo;
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
		ts.addAtributo(val,AccionSemantica.TIPO,tipoVar);lex.getLineaInicial();
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
//#line 853 "Parser.java"
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
{	if(esEmbebido(val_peek(0).sval)){
			ErrorHandler.addErrorSemantico("No se puede declarar una etiqueta que tenga tipos embebidos", lex.getLineaInicial());
			} else {
				estructurasSintacticas("Se declaro una etiqueta goto, en linea: " + lex.getLineaInicial());
			}
		}
break;
case 24:
//#line 56 "gramatica.y"
{estructurasSintacticas("Se imprimio un mensaje, en linea: " + lex.getLineaInicial()); }
break;
case 26:
//#line 58 "gramatica.y"
{estructurasSintacticas("Se llamo a una etiqueta goto, en linea: " + lex.getLineaInicial()); }
break;
case 27:
//#line 62 "gramatica.y"
{ yyval.sval = val_peek(1).sval;}
break;
case 28:
//#line 64 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de paréntesis en la condición", lex.getLineaInicial());}
break;
case 29:
//#line 65 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de paréntesis derecho en la condición", lex.getLineaInicial());}
break;
case 30:
//#line 66 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de paréntesis izquierdo en la condición", lex.getLineaInicial());}
break;
case 31:
//#line 69 "gramatica.y"
{ yyval.sval = gc.addTerceto(val_peek(1).sval, val_peek(2).sval, val_peek(0).sval);}
break;
case 32:
//#line 70 "gramatica.y"
{ yyval.sval = gc.addTerceto(val_peek(3).sval, "{" + val_peek(5).sval + "}", "{" + val_peek(1).sval + "}");}
break;
case 33:
//#line 72 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón y el que abre la segunda", lex.getLineaInicial());}
break;
case 34:
//#line 73 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que abre la segunda lista del patrón", lex.getLineaInicial());}
break;
case 35:
//#line 74 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta parentesis que cierra la primer lista del patrón", lex.getLineaInicial());}
break;
case 36:
//#line 75 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta comparador entre los patrones", lex.getLineaInicial());}
break;
case 37:
//#line 76 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta comparador entre las expresiones", lex.getLineaInicial());}
break;
case 38:
//#line 79 "gramatica.y"
{ yyval.sval = val_peek(2).sval + "," + val_peek(0).sval; }
break;
case 39:
//#line 82 "gramatica.y"
{ yyval.sval = val_peek(2).sval + "," + val_peek(0).sval; }
break;
case 40:
//#line 83 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 41:
//#line 86 "gramatica.y"
{estructurasSintacticas("Se definió una sentencia de control sin else, en la linea: " + lex.getLineaInicial());}
break;
case 43:
//#line 90 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta END_IF con ELSE", lex.getLineaInicial());}
break;
case 44:
//#line 91 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta END_IF", lex.getLineaInicial());}
break;
case 45:
//#line 92 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control del then", lex.getLineaInicial());}
break;
case 46:
//#line 93 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control del ELSE", lex.getLineaInicial());}
break;
case 47:
//#line 94 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de control tanto en THEN como ELSE", lex.getLineaInicial());}
break;
case 48:
//#line 99 "gramatica.y"
{
			gc.actualizarBF(gc.getCantTercetos()); 
			gc.pop();
			}
break;
case 49:
//#line 105 "gramatica.y"
{
			gc.addTerceto("BF", val_peek(0).sval, ""); 
			gc.push(gc.getPosActual());
		}
break;
case 50:
//#line 111 "gramatica.y"
{ 
			gc.addTerceto("BI", "", "-"); 
			gc.actualizarBF(gc.getCantTercetos()); 
			gc.pop(); 
			gc.push(gc.getPosActual());
		}
break;
case 51:
//#line 118 "gramatica.y"
{
			gc.actualizarBI(gc.getCantTercetos()); 
			gc.pop();
			estructurasSintacticas("Se definió una sentencia de control con else, en la linea: " + lex.getLineaInicial());
		}
break;
case 55:
//#line 127 "gramatica.y"
{yyval.sval = "=";}
break;
case 56:
//#line 128 "gramatica.y"
{yyval.sval = "<";}
break;
case 57:
//#line 129 "gramatica.y"
{yyval.sval = ">";}
break;
case 60:
//#line 135 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
break;
case 61:
//#line 136 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo del control", lex.getLineaInicial());}
break;
case 62:
//#line 137 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 65:
//#line 143 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
break;
case 66:
//#line 144 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el cuerpo de la iteracion", lex.getLineaInicial());}
break;
case 67:
//#line 145 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 70:
//#line 151 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 71:
//#line 155 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 72:
//#line 157 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 73:
//#line 158 "gramatica.y"
{estructurasSintacticas("Se declararon variables en la linea: " + lex.getLineaInicial());}
break;
case 75:
//#line 165 "gramatica.y"
{if(!esEmbebido(val_peek(0).sval)){ErrorHandler.addErrorSemantico("No se puede omitir el tipo en declaracion de tipos no-embebidos.", lex.getLineaInicial());};}
break;
case 76:
//#line 166 "gramatica.y"
{if(!esEmbebido(val_peek(0).sval)){ErrorHandler.addErrorSemantico("No se puede omitir el tipo en declaracion de tipos no-embebidos.", lex.getLineaInicial());};}
break;
case 77:
//#line 170 "gramatica.y"
{checkRedeclaracion(val_peek(0).sval);}
break;
case 78:
//#line 171 "gramatica.y"
{checkRedeclaracion(val_peek(0).sval);}
break;
case 79:
//#line 172 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta coma en la lista de variables, puede haber parado la compilacion en este punto", lex.getLineaInicial());}
break;
case 82:
//#line 180 "gramatica.y"
{estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());
						yyval.sval = gc.addTerceto(":=", val_peek(2).sval, val_peek(0).sval);
					}
break;
case 83:
//#line 183 "gramatica.y"
{estructurasSintacticas("Se realizó una asignación a la variable: " + val_peek(2).sval + " en la linea: " + lex.getLineaInicial());
					yyval.sval = gc.addTerceto(":=", val_peek(2).sval, val_peek(0).sval);
		}
break;
case 86:
//#line 193 "gramatica.y"
{yyval.sval = gc.addTerceto("+", val_peek(2).sval, val_peek(0).sval);}
break;
case 87:
//#line 194 "gramatica.y"
{yyval.sval = gc.addTerceto("-", val_peek(2).sval, val_peek(0).sval);}
break;
case 89:
//#line 199 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 90:
//#line 200 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");}
break;
case 91:
//#line 202 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
break;
case 92:
//#line 204 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(")");}
break;
case 93:
//#line 206 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
							lex.setErrorHandlerToken(";");}
break;
case 94:
//#line 212 "gramatica.y"
{yyval.sval = gc.addTerceto("*", val_peek(2).sval, val_peek(0).sval);}
break;
case 95:
//#line 213 "gramatica.y"
{yyval.sval = gc.addTerceto("/", val_peek(2).sval, val_peek(0).sval);}
break;
case 97:
//#line 217 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 98:
//#line 218 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando izquierdo", lex.getLineaInicial());}
break;
case 99:
//#line 219 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 100:
//#line 221 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 101:
//#line 223 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
break;
case 102:
//#line 225 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta operando derecho", lex.getLineaInicial());
					lex.setErrorHandlerToken(";");}
break;
case 108:
//#line 236 "gramatica.y"
{	if (ts.esUlongInt(val_peek(0).sval)){
					ErrorHandler.addErrorSintactico("se utilizo un Ulongint negativo, son solo positivos", lex.getLineaInicial());
				}
				else {
					ts.convertirNegativo(val_peek(0).sval);
				}
			}
break;
case 109:
//#line 245 "gramatica.y"
{yyval.sval = gc.addTerceto("ACCESOTRIPLE", val_peek(3).sval, val_peek(1).sval);}
break;
case 110:
//#line 248 "gramatica.y"
{ this.cantRetornos.add(0); this.gc_funciones.push(this.ts.getGCFuncion(val_peek(3).sval)); this.gc = this.gc_funciones.peek();}
break;
case 111:
//#line 248 "gramatica.y"
{this.checkRet(val_peek(5).sval);
								this.gc_funciones.pop();
								this.gc = this.gc_funciones.peek();
								if (esEmbebido(val_peek(5).sval)){ErrorHandler.addErrorSemantico("No se puede declarar una funcion con un ID con tipos embebidos.", lex.getLineaInicial());}										;
							}
break;
case 112:
//#line 253 "gramatica.y"
{ this.cantRetornos.add(0);}
break;
case 113:
//#line 253 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta nombre de la funcion declarada", lex.getLineaInicial());
													this.checkRet("");
													}
break;
case 114:
//#line 256 "gramatica.y"
{ this.cantRetornos.add(0);}
break;
case 115:
//#line 256 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el parametro en la declaracion de la funcion", lex.getLineaInicial());
							    this.checkRet(val_peek(4).sval);
							    if (esEmbebido(val_peek(4).sval)){ErrorHandler.addErrorSemantico("No se puede declarar una funcion con un ID con tipos embebidos.", lex.getLineaInicial());}
							   }
break;
case 118:
//#line 266 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Se declaró más de un parametro", lex.getLineaInicial());}
break;
case 120:
//#line 271 "gramatica.y"
{estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 121:
//#line 272 "gramatica.y"
{estructurasSintacticas("Se declaró el parámetro: " + val_peek(0).sval + " en la linea: " + lex.getLineaInicial());}
break;
case 122:
//#line 274 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el nombre del parametro", lex.getLineaInicial());}
break;
case 123:
//#line 275 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el nombre del parametro o el tipo", lex.getLineaInicial());}
break;
case 124:
//#line 278 "gramatica.y"
{ts.addClave(yylval.sval);}
break;
case 128:
//#line 286 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta punto y coma", lex.getLineaInicial());}
break;
case 131:
//#line 293 "gramatica.y"
{ this.cantRetornos.set(this.cantRetornos.size()-1, this.cantRetornos.get(this.cantRetornos.size()-1) + 1); 
					yyval.sval = gc.addTerceto("RET", val_peek(1).sval, "");		
		}
break;
case 132:
//#line 298 "gramatica.y"
{estructurasSintacticas("Se invocó a la función: " + val_peek(3).sval + " en la linea: " + lex.getLineaInicial());
							yyval.sval = gc.addTerceto("INVOC_FUN", val_peek(3).sval, val_peek(1).sval);
		}
break;
case 133:
//#line 302 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta de parámetros en la invocación a la función", lex.getLineaInicial());}
break;
case 134:
//#line 306 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Se utilizó más de un parámetro para invocar a la función", lex.getLineaInicial());}
break;
case 135:
//#line 307 "gramatica.y"
{ yyval.sval = val_peek(0).sval;}
break;
case 136:
//#line 310 "gramatica.y"
{yyval.sval = gc.addTerceto("TO".concat(val_peek(1).sval), val_peek(0).sval, "");}
break;
case 137:
//#line 311 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 138:
//#line 317 "gramatica.y"
{yyval.sval = gc.addTerceto("OUTF", val_peek(1).sval, "");}
break;
case 139:
//#line 319 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el mensaje del OUTF", lex.getLineaInicial());}
break;
case 140:
//#line 320 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Parámetro invalido del OUTF", lex.getLineaInicial());
					lex.setErrorHandlerToken(")");}
break;
case 143:
//#line 328 "gramatica.y"
{	estructurasSintacticas("Se declaró un bucle FOR en la linea: " + lex.getLineaInicial());
													String var = this.varFors.get(this.varFors.size()-1);
													gc.addTerceto("+", var, String.valueOf(val_peek(3).ival * Integer.parseInt(val_peek(2).sval)));
													this.varFors.remove(this.varFors.size()-1);
													gc.addTerceto("BI", val_peek(5).sval, "");
													gc.actualizarBF(gc.getCantTercetos());
													gc.pop();
		}
break;
case 144:
//#line 337 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 145:
//#line 338 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta punto y coma entre asignacion y condicion", lex.getLineaInicial());}
break;
case 146:
//#line 339 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Faltan todos los punto y coma del for", lex.getLineaInicial());}
break;
case 147:
//#line 340 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta UP/DOWN", lex.getLineaInicial());}
break;
case 148:
//#line 341 "gramatica.y"
{ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN", lex.getLineaInicial());}
break;
case 149:
//#line 342 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 150:
//#line 343 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta valor del UP/DOWN y punto y coma entre condicion y avance", lex.getLineaInicial());}
break;
case 151:
//#line 344 "gramatica.y"
{ { ErrorHandler.addErrorSintactico("Falta UP/DOWN, su valor, y punto y coma entre condicion y avance", lex.getLineaInicial());}}
break;
case 152:
//#line 348 "gramatica.y"
{ yyval.sval = val_peek(0).sval;
				gc.addTerceto("BF", val_peek(0).sval, "");
				gc.push(gc.getPosActual());
			}
break;
case 153:
//#line 354 "gramatica.y"
{ gc.addTerceto(":=", val_peek(2).sval, val_peek(0).sval);
				this.varFors.add(val_peek(2).sval);
				}
break;
case 154:
//#line 359 "gramatica.y"
{yyval.ival = 1;}
break;
case 155:
//#line 360 "gramatica.y"
{yyval.ival = -1;}
break;
case 156:
//#line 363 "gramatica.y"
{ yyval.sval = gc.addTerceto("GOTO", val_peek(0).sval,"");}
break;
case 157:
//#line 365 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta la etiqueta en el GOTO, en caso de faltar también el punto y coma es posible que no compile el resto del programa o lo haga mal.", lex.getLineaInicial());
				lex.setErrorHandlerToken(";");}
break;
case 158:
//#line 371 "gramatica.y"
{estructurasSintacticas("Se declaró un tipo TRIPLE con el ID: " + val_peek(0).sval + " en la linea:" + lex.getLineaInicial());}
break;
case 159:
//#line 373 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta < en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 160:
//#line 374 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta > en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 161:
//#line 375 "gramatica.y"
{ErrorHandler.addErrorSintactico("falta > y < en la declaración del TRIPLE", lex.getLineaInicial()); }
break;
case 162:
//#line 376 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta la palabra clave TRIPLE", lex.getLineaInicial());}
break;
case 163:
//#line 377 "gramatica.y"
{ ErrorHandler.addErrorSintactico("Falta el ID de la tripla definida.", lex.getLineaInicial());}
break;
//#line 1543 "Parser.java"
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
