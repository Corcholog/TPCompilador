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
    6,    6,    6,    6,    6,    6,    6,   27,   28,   28,
   28,   28,   28,   28,    9,    9,   29,   29,   11,   11,
   11,   11,   11,   11,   30,   30,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   12,   31,   31,    7,    7,    7,    7,    7,    7,
    7,   13,
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
    8,    8,    8,    7,    7,    7,    6,    2,    6,    5,
    5,    5,    5,    5,    4,    3,    2,    1,    4,    3,
    3,    2,    3,    1,    1,    1,   12,   11,   11,   10,
   11,   11,   11,    9,   11,   11,   11,    8,    7,    9,
    7,   11,    1,    1,    6,    5,    5,    5,    5,    4,
    5,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,   84,   53,   54,    0,   55,    0,
    0,    0,   72,   71,    0,    0,    9,    0,    0,    0,
   27,    0,   56,   57,   58,    0,   12,   13,   14,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   69,    0,   82,    8,    0,    0,
    0,    0,   85,    0,   83,  142,    0,    0,    0,    0,
    0,    5,    0,  116,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    3,   11,   15,   16,
   17,   21,   22,   23,   24,   25,   26,    0,   29,    0,
    0,    0,    0,    0,   68,    0,    0,    0,    0,    2,
  106,    0,    0,    0,    0,   65,    0,    0,    0,    0,
    0,    0,    0,    1,  113,    0,  110,    0,    0,    0,
    0,    0,    0,    0,   28,    0,    0,    0,    0,   60,
    0,    0,    0,    0,    0,    0,    0,    0,   80,   81,
    0,  105,   64,    0,    0,    0,    0,    0,    0,    0,
    0,  109,    0,    0,  140,    0,    0,   70,    0,    0,
    0,    0,    0,    0,    0,   62,    0,   46,    0,    0,
    0,    0,   67,  136,    0,    0,    0,    0,    0,  133,
  134,    0,    0,    0,   44,    0,  141,  139,    0,  138,
  137,   98,    0,    0,   40,    0,    0,    0,   59,   61,
   50,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   49,  135,    0,    0,   38,
   37,   39,    0,    0,    0,    0,   97,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  129,
  131,    0,   45,    0,    0,   33,    0,   96,    0,    0,
    0,    0,    0,   94,   95,    0,    0,    0,    0,    0,
    0,  128,    0,    0,    0,    0,   87,    0,   90,   93,
   91,    0,   89,    0,    0,    0,   92,   88,    0,    0,
  130,    0,    0,    0,    0,    0,  124,    0,   86,    0,
    0,    0,    0,    0,  120,    0,    0,    0,    0,    0,
    0,    0,    0,  100,  101,  103,    0,  102,  118,  122,
  125,  126,    0,  119,  127,  123,  121,  132,   99,  117,
};
final static short yydgoto[] = {                          3,
  229,   27,   28,  130,   30,   31,   32,   33,   34,   35,
   36,   37,   38,   39,   40,   41,   42,   76,  131,  167,
   43,   44,   97,   45,   46,   47,  160,  230,  104,   67,
  182,
};
final static short yysindex[] = {                      -229,
  108,  573,    0,  -19,    0,    0,    0, -233,    0, -265,
   -5,  -36,    0,    0,  163,  613,    0,  -40,  -50, -210,
    0, 1171,    0,    0,    0,  658,    0,    0,    0,   11,
   19,   35,   44,   85,  102,  106,  114,  115, -203,   79,
   64, -233, -169, -235,    0,   48,    0,    0,  719,  104,
 -108,  -19,    0,    3,    0,    0, -151, -138,  103, 1219,
 -104,    0,  755,    0,  -38,    3,  134,   72, -151,  140,
  -13,  153,  385,  135,   64,   -2,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  995,    0, -233,
 -233, -233,    3, -233,    0,   -4,  141, -233, -233,    0,
    0,    3, -233,  143,   68,    0,   62,  137,  -65, -153,
  -58, 1226,  995,    0,    0,  160,    0,  212,  -44,  147,
 -172,   18,    3,  175,    0,   39, -233,    3, 1076,    0,
  882,   48,   48,    3,    3,  -31, -172,   24,    0,    0,
    3,    0,    0,   40,  168,  247,  -47,  256, -137,  257,
  852,    0,   74,  -43,    0,   94,  101,    0,  130,  349,
  353,  161, -233,  375,    3,    0, 1035,    0,  149,  131,
  -39,  389,    0,    0,  163,  163,  378,  163,  163,    0,
    0,  185,  -33,  995,    0,  174,    0,    0,  193,    0,
    0,    0,  179,  163,    0,    8,   15,  169,    0,    0,
    0,  181,  -34,  402,  183,  184,  399,  410, 1131,  411,
  412,  420,  995,  218,  202,    0,    0,  784,  419,    0,
    0,    0,   33,  784,  468,  434,    0,  439,  824,  208,
  494,  784, -137, -137,  -18,  995,  425, -137, -137,    0,
    0,  444,    0,  213, -137,    0,  214,    0,  215,  544,
  216, -233,   -6,    0,    0,  223,  225,  228,  234,  995,
  242,    0, -118,  243,  244,  995,    0,  248,    0,    0,
    0,  233,    0,   98,  171,  393,    0,    0,  907,  469,
    0,  470,  471,   -1,  472,  473,    0,  474,    0,  459,
  462,   30,  463,  995,    0,  995,  995,  995,  943,  995,
  995,  995,  995,    0,    0,    0,  467,    0,    0,    0,
    0,    0,  995,    0,    0,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  136,    0,    0,    0,  481,    0,    0,
    0,    0,    0,    0,    0,  541,    0,  483,    0,    0,
    0,    0,    0,    0,    0,  546,    0,    0,    0,   26,
   51,   76,    0, 1270,    0,    0,    0,    0,    0,  -22,
    0,  -30, 1302,    0,    0,  145,    0,    0,  548,    0,
    0,  527,    0,  485,    0,    0,    0,    0,    0,    0,
    0,    0,  549,    0,    0,   13,  492,    0,    0,    0,
 1280,    0,    0,  -21,   90,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -28,  -27,  493,    0,    0,    1,    0,    0,    0,
    0,  512,    0,    0,  436,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  499,    0,    0,    0,    0,
    0,    0,   90,    0,    0,    0,    0,  122,    0,    0,
  500, 1177, 1184,  -26,  503,    0,    0,    0,    0,    0,
  522,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  506,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  151,    0,    0,    0,  507,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  514,    0,    0,    0,    0,
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
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  302,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  113,   93,    0,    4,    0,    0,    0,    0,  433,    0,
    0,    0,    0,  445,   23,  525,    9, -105, 1254,    0,
  341,  -15,    0,   12,   60,   61,  -72,  241,    0,  513,
  -81,
};
final static int YYTABLESIZE=1567;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         65,
   66,  205,  115,   59,   29,   29,  226,  213,  170,   69,
   36,  178,   35,   34,   32,   56,  106,  156,  189,   29,
   50,   95,  260,   52,    5,   18,   50,    1,   36,   29,
   35,   34,   32,  275,  103,  137,   31,   30,  126,  300,
   66,  127,   96,    2,   74,   90,   70,   91,  220,   92,
   19,  127,   29,  115,   57,  221,  196,  197,  127,   66,
   66,   66,   66,  171,  172,   18,   29,   88,  108,   79,
  307,  115,   90,  246,   91,   20,  127,   80,  163,  119,
  120,  124,   74,   92,  158,   18,   18,   18,  306,   98,
   19,   94,  223,   81,   99,   13,   14,  203,   24,   23,
   25,  214,   82,   51,  146,  159,   90,  147,   91,   51,
   19,   19,   19,   26,   49,   20,   13,   14,   78,   89,
  159,  159,  109,   24,   23,   25,  180,  181,   63,  154,
   43,  118,  166,   43,  164,   20,   20,   20,  290,  283,
   90,   78,   91,   83,  101,  180,  181,   22,  105,  132,
  133,  258,  259,  261,  159,   78,  264,  265,  139,  140,
   84,  112,   42,  268,   85,   42,  113,   24,   23,   25,
  200,  202,   86,   87,  117,  125,   63,   63,   63,  121,
   63,  284,   63,  142,  138,   79,  143,   79,   79,   79,
   51,   41,  145,   73,   41,   63,   63,   63,  144,  148,
  152,  195,   60,   79,   79,   79,   79,   29,  157,  222,
  177,  291,  155,  188,  162,   68,   52,    5,   52,    5,
   58,   29,   24,   23,   25,  158,  175,   29,   29,   29,
  180,  181,   29,  204,   29,   29,   13,   14,  225,   64,
   36,   64,   35,   34,   32,  180,  181,  122,   31,   30,
   52,    5,  136,   29,   52,    5,  299,   66,   66,   66,
   66,   66,   66,   66,   52,    5,   66,   66,   66,   66,
   66,   52,    5,  153,   66,  161,   66,   66,   66,   66,
  173,   66,   18,   18,   18,   18,   18,   18,   18,   52,
    5,   18,   18,   18,   18,   18,  174,    6,    7,   18,
    9,   18,   18,   18,   18,  176,   18,   19,   19,   19,
   19,   19,   19,   19,  179,  183,   19,   19,   19,   19,
   19,   78,    6,    7,   19,    9,   19,   19,   19,   19,
  187,   19,   20,   20,   20,   20,   20,   20,   20,   13,
   14,   20,   20,   20,   20,   20,   43,   43,   55,   20,
  190,   20,   20,   20,   20,   55,   20,  191,   55,  110,
    4,    5,   55,  111,    4,    5,    6,    7,    8,    9,
   10,   13,   14,   11,   12,   13,   14,   15,   42,   42,
   16,   17,   55,   18,   19,   20,  192,  158,   21,  193,
   55,  107,   70,   70,   63,   63,   63,   63,   13,   14,
   55,   79,   79,   79,   79,   55,   79,   41,   41,   52,
    5,  194,   55,   70,  198,   79,   55,   52,    5,   52,
    5,    6,    7,  201,    9,   52,    5,   52,    5,  206,
   55,   55,   55,  293,   55,   90,  209,   91,   55,   55,
   53,   22,  212,   55,   24,   23,   25,   53,  216,  217,
   53,  218,   55,  224,   53,  231,  232,  233,  244,   61,
  240,   24,   23,   25,  247,  249,  251,   55,  234,  238,
  239,  256,  257,   22,   53,  242,  243,  245,  252,   13,
   14,  254,   53,  263,  266,  279,  267,  269,  270,  273,
  272,  280,   53,   24,   23,   25,  277,   53,  278,  282,
  285,  286,   55,   55,   53,  288,  289,   22,   53,  296,
  297,  298,  301,  302,  303,   55,   55,  304,   55,   55,
  305,  308,   53,   53,   53,  319,   53,   24,   23,   25,
   53,   53,   54,   22,   55,   53,   55,   55,   55,   76,
   10,  114,   66,   75,   53,    6,   75,    7,    4,   55,
  112,   74,  108,   24,   23,   25,  150,  111,   48,   53,
   63,   73,  107,   55,   47,   52,   93,   63,   63,   63,
   63,   63,   51,   63,  102,  104,    0,  116,    0,    0,
    0,    0,    0,   22,   75,   63,   63,   63,   63,   66,
    0,    0,   55,   55,   53,   53,  123,    0,    0,    0,
  128,    0,    0,   24,   23,   25,    0,   53,   53,    0,
   53,   53,   22,    0,    0,   55,  134,    0,  135,  207,
  208,    0,  210,  211,    0,    0,   53,  141,   53,   53,
   53,    0,   24,   23,   25,    0,    0,    0,  219,    0,
    0,   53,    0,    6,    7,    0,    9,    0,    0,    0,
    0,  165,   22,  237,    0,   53,    0,    0,    4,    5,
    6,    7,    8,    9,   10,    0,    0,   11,   12,   13,
   14,   15,   24,   23,   25,  227,    0,   18,   19,   20,
  228,    0,   21,    0,   53,   53,  123,  123,    0,    0,
    4,    5,    6,    7,    8,    9,   10,   22,    0,   11,
   12,   13,   14,   15,    0,    0,  250,   53,    0,   18,
   19,   20,  228,    0,   21,    0,    0,   24,   23,   25,
  128,  128,  123,    0,    4,    5,    6,    7,    8,    9,
   10,    0,    0,   11,   12,   13,   14,   15,    0,    0,
    0,  248,    0,   18,   19,   20,  228,  128,   21,    0,
    4,    5,    6,    7,    8,    9,   10,    0,   22,   11,
   12,   13,   14,   15,    0,    0,    0,  255,    0,   18,
   19,   20,  228,    0,   21,    0,  274,  276,   24,   23,
   25,    0,    0,   63,   63,   63,   63,   63,   63,    0,
    0,    0,    0,    0,   22,    0,    0,   63,    0,  292,
    4,    5,    6,    7,    8,    9,   10,    0,    0,   11,
   12,   13,   14,   15,   24,   23,   25,  271,    0,   18,
   19,   20,  228,   22,   21,    0,    0,    0,    0,    4,
    5,    6,    7,    8,    9,   10,    0,    0,   11,   12,
   13,   14,   15,   24,   23,   25,   48,    0,   18,   19,
   20,    0,    0,   21,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   22,    0,    0,    0,    0,    0,    4,
    5,    6,    7,    8,    9,   10,    0,    0,   11,   12,
   13,   14,   15,   24,   23,   25,   62,    0,   18,   19,
   20,   22,    0,   21,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   24,   23,   25,    4,    5,    6,    7,    8,    9,
   10,   22,    0,   11,   12,   13,   14,   15,    0,    0,
    0,   77,    0,   18,   19,   20,    0,    0,   21,    0,
    0,   24,   23,   25,    0,    0,   22,  294,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   24,   23,   25,    0,
    0,    0,    0,    0,    0,    4,    5,    6,    7,    8,
    9,   10,   22,  313,   11,   12,   13,   14,   15,    0,
    0,    0,  100,    0,   18,   19,   20,    0,    0,   21,
    0,    0,   24,   23,   25,    0,    0,    0,    0,    0,
    0,    4,    5,    6,    7,    8,    9,   10,    0,    0,
   11,   12,   13,   14,   15,    0,    0,    0,  114,    0,
   18,   19,   20,    0,   22,   21,    0,    0,    0,    0,
    4,    5,    6,    7,    8,    9,   10,    0,    0,   11,
   12,   13,   14,   15,   24,   23,   25,    0,    0,   18,
   19,   20,  228,    0,   21,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   22,    0,    0,    0,    0,    0,
    4,    5,    6,    7,    8,    9,   10,    0,    0,   11,
   12,   13,   14,   15,   24,   23,   25,    0,    0,   18,
   19,   20,  253,    0,   21,    0,    0,    0,   52,    5,
    6,    7,    8,    9,   10,   22,    0,    0,   12,    0,
    0,   15,    0,  184,  129,    0,  185,   18,    0,    0,
    0,    0,   21,    0,    0,   24,   23,   25,   52,    5,
    6,    7,    8,    9,   10,    0,    0,    0,   12,    0,
    0,   15,    0,    0,  129,    0,  168,   18,    0,    0,
    0,    0,   21,   52,    5,    6,    7,    8,    9,   10,
   60,  236,    0,   12,    0,    0,   15,    0,    0,  129,
    0,    0,   18,    0,    0,    0,    0,   21,    0,  235,
   24,   23,   25,    0,    0,    0,    0,    0,    0,   52,
    5,    6,    7,    8,    9,   10,    0,    0,    0,   12,
   72,   73,   15,    0,    0,  129,    0,   77,   18,   77,
   77,   77,    0,   21,   78,    0,   78,   78,   78,    0,
   24,   23,   25,    0,    0,   77,   77,   77,   77,    0,
    0,    0,   78,   78,   78,   78,    0,    0,    0,    0,
    0,   52,    5,    6,    7,    8,    9,   10,   72,   73,
    0,   12,    0,    0,   15,   60,    0,  129,    0,    0,
   18,    0,    0,    0,    0,   21,    0,    0,   24,   23,
   25,    0,    0,    0,  149,   24,   23,   25,    0,    0,
    0,   52,    5,    6,    7,    8,    9,   10,    0,    0,
    0,   12,    0,    0,   15,    0,    0,    0,  199,    0,
   18,   85,   85,    0,   85,   21,   85,    0,    0,    0,
   63,   63,   63,   63,   63,    0,   63,    0,    0,   85,
   85,   85,   52,    5,    6,    7,    8,    9,   10,   63,
   63,   63,   12,   83,   83,   15,   83,    0,   83,    0,
    0,   18,    0,    0,    0,    0,   21,    0,    0,    0,
    0,   83,   83,   83,    0,    0,  151,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  169,    0,    0,   52,    5,    6,
    7,    0,    9,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  186,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   71,    5,    6,
    7,    0,    9,   77,   77,   77,   77,  215,   77,    0,
   78,   78,   78,   78,    0,   78,    0,   77,    0,    0,
    0,    0,    0,    0,   78,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  241,    0,    0,    0,
    0,    0,    0,    0,    0,   52,    5,    6,    7,    0,
    9,    0,   52,    5,    6,    7,    0,    9,    0,  262,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  281,    0,    0,    0,    0,    0,  287,
    0,    0,    0,    0,    0,    0,    0,    0,   85,   85,
    0,   85,  295,    0,    0,    0,   63,   63,   63,   63,
    0,   63,    0,    0,    0,    0,    0,  309,    0,  310,
  311,  312,  314,  315,  316,  317,  318,    0,    0,    0,
   83,   83,    0,   83,    0,    0,  320,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   41,   41,   40,    1,    2,   41,   41,   40,   60,
   41,   59,   41,   41,   41,  281,  125,   62,   62,   16,
   40,  257,   41,  257,  258,    0,   40,  257,   59,   26,
   59,   59,   59,   40,   50,   40,   59,   59,   41,   41,
   40,   44,  278,  273,   22,   43,  257,   45,   41,   41,
    0,   44,   49,   41,   60,   41,  162,  163,   44,   59,
   60,   61,   62,  136,  137,   40,   63,  271,   57,   59,
   41,   59,   43,   41,   45,    0,   44,   59,   40,   68,
   69,   73,   60,   75,  257,   60,   61,   62,   59,   42,
   40,  261,  198,   59,   47,  268,  269,  170,   60,   61,
   62,  183,   59,  123,  258,  121,   43,  261,   45,  123,
   60,   61,   62,    1,    2,   40,  268,  269,   26,   41,
  136,  137,  261,   60,   61,   62,  264,  265,   16,  118,
   41,   60,  129,   44,  126,   60,   61,   62,   41,  258,
   43,   49,   45,   59,   41,  264,  265,   40,  257,   90,
   91,  233,  234,  235,  170,   63,  238,  239,   98,   99,
   59,   59,   41,  245,   59,   44,  271,   60,   61,   62,
  167,   41,   59,   59,   41,   41,   41,   42,   43,   40,
   45,  263,   47,   41,   44,   41,  125,   43,   44,   45,
  123,   41,  258,   41,   44,   60,   61,   62,   62,  258,
   41,   41,   40,   59,   60,   61,   62,  204,   62,   41,
  258,   41,  257,  257,   40,  266,  257,  258,  257,  258,
  257,  218,   60,   61,   62,  257,   59,  224,  225,  226,
  264,  265,  229,  273,  231,  232,  268,  269,  273,  280,
  271,  280,  271,  271,  271,  264,  265,  261,  271,  271,
  257,  258,  257,  250,  257,  258,  258,  257,  258,  259,
  260,  261,  262,  263,  257,  258,  266,  267,  268,  269,
  270,  257,  258,   62,  274,  258,  276,  277,  278,  279,
  257,  281,  257,  258,  259,  260,  261,  262,  263,  257,
  258,  266,  267,  268,  269,  270,  257,  259,  260,  274,
  262,  276,  277,  278,  279,   59,  281,  257,  258,  259,
  260,  261,  262,  263,   59,   59,  266,  267,  268,  269,
  270,  229,  259,  260,  274,  262,  276,  277,  278,  279,
  257,  281,  257,  258,  259,  260,  261,  262,  263,  268,
  269,  266,  267,  268,  269,  270,  257,  258,    8,  274,
  257,  276,  277,  278,  279,   15,  281,  257,   18,  257,
  257,  258,   22,  261,  257,  258,  259,  260,  261,  262,
  263,  268,  269,  266,  267,  268,  269,  270,  257,  258,
  273,  274,   42,  276,  277,  278,  257,  257,  281,   41,
   50,   51,  257,  258,  259,  260,  261,  262,  268,  269,
   60,  257,  258,  259,  260,   65,  262,  257,  258,  257,
  258,   59,   72,  278,   40,  271,   76,  257,  258,  257,
  258,  259,  260,  275,  262,  257,  258,  257,  258,   41,
   90,   91,   92,   41,   94,   43,   59,   45,   98,   99,
    8,   40,  258,  103,   60,   61,   62,   15,  275,  257,
   18,  273,  112,  273,   22,  273,  273,   59,  218,   15,
   41,   60,   61,   62,  224,  225,  226,  127,   59,   59,
   59,  231,  232,   40,   42,  258,  275,   59,   40,  268,
  269,  274,   50,   59,   41,  258,  274,  274,  274,  274,
  250,  258,   60,   60,   61,   62,  274,   65,  274,  258,
  258,  258,  162,  163,   72,  258,  274,   40,   76,   41,
   41,   41,   41,   41,   41,  175,  176,   59,  178,  179,
   59,   59,   90,   91,   92,   59,   94,   60,   61,   62,
   98,   99,    8,   40,  194,  103,  196,  197,  198,   59,
    0,   59,   18,   59,  112,    0,   22,    0,    0,  209,
   59,   59,   41,   60,   61,   62,  112,   59,   59,  127,
  125,   59,   41,  223,   59,   59,   42,   41,   42,   43,
   44,   45,   59,   47,   50,  274,   -1,   65,   -1,   -1,
   -1,   -1,   -1,   40,   60,   59,   60,   61,   62,   65,
   -1,   -1,  252,  253,  162,  163,   72,   -1,   -1,   -1,
   76,   -1,   -1,   60,   61,   62,   -1,  175,  176,   -1,
  178,  179,   40,   -1,   -1,  275,   92,   -1,   94,  175,
  176,   -1,  178,  179,   -1,   -1,  194,  103,  196,  197,
  198,   -1,   60,   61,   62,   -1,   -1,   -1,  194,   -1,
   -1,  209,   -1,  259,  260,   -1,  262,   -1,   -1,   -1,
   -1,  127,   40,  209,   -1,  223,   -1,   -1,  257,  258,
  259,  260,  261,  262,  263,   -1,   -1,  266,  267,  268,
  269,  270,   60,   61,   62,  274,   -1,  276,  277,  278,
  279,   -1,  281,   -1,  252,  253,  162,  163,   -1,   -1,
  257,  258,  259,  260,  261,  262,  263,   40,   -1,  266,
  267,  268,  269,  270,   -1,   -1,  273,  275,   -1,  276,
  277,  278,  279,   -1,  281,   -1,   -1,   60,   61,   62,
  196,  197,  198,   -1,  257,  258,  259,  260,  261,  262,
  263,   -1,   -1,  266,  267,  268,  269,  270,   -1,   -1,
   -1,  274,   -1,  276,  277,  278,  279,  223,  281,   -1,
  257,  258,  259,  260,  261,  262,  263,   -1,   40,  266,
  267,  268,  269,  270,   -1,   -1,   -1,  274,   -1,  276,
  277,  278,  279,   -1,  281,   -1,  252,  253,   60,   61,
   62,   -1,   -1,  257,  258,  259,  260,  261,  262,   -1,
   -1,   -1,   -1,   -1,   40,   -1,   -1,  271,   -1,  275,
  257,  258,  259,  260,  261,  262,  263,   -1,   -1,  266,
  267,  268,  269,  270,   60,   61,   62,  274,   -1,  276,
  277,  278,  279,   40,  281,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,  261,  262,  263,   -1,   -1,  266,  267,
  268,  269,  270,   60,   61,   62,  274,   -1,  276,  277,
  278,   -1,   -1,  281,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   40,   -1,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,  261,  262,  263,   -1,   -1,  266,  267,
  268,  269,  270,   60,   61,   62,  274,   -1,  276,  277,
  278,   40,   -1,  281,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   60,   61,   62,  257,  258,  259,  260,  261,  262,
  263,   40,   -1,  266,  267,  268,  269,  270,   -1,   -1,
   -1,  274,   -1,  276,  277,  278,   -1,   -1,  281,   -1,
   -1,   60,   61,   62,   -1,   -1,   40,   41,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   60,   61,   62,   -1,
   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,
  262,  263,   40,   41,  266,  267,  268,  269,  270,   -1,
   -1,   -1,  274,   -1,  276,  277,  278,   -1,   -1,  281,
   -1,   -1,   60,   61,   62,   -1,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,  260,  261,  262,  263,   -1,   -1,
  266,  267,  268,  269,  270,   -1,   -1,   -1,  274,   -1,
  276,  277,  278,   -1,   40,  281,   -1,   -1,   -1,   -1,
  257,  258,  259,  260,  261,  262,  263,   -1,   -1,  266,
  267,  268,  269,  270,   60,   61,   62,   -1,   -1,  276,
  277,  278,  279,   -1,  281,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   40,   -1,   -1,   -1,   -1,   -1,
  257,  258,  259,  260,  261,  262,  263,   -1,   -1,  266,
  267,  268,  269,  270,   60,   61,   62,   -1,   -1,  276,
  277,  278,  279,   -1,  281,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,  263,   40,   -1,   -1,  267,   -1,
   -1,  270,   -1,  272,  273,   -1,  275,  276,   -1,   -1,
   -1,   -1,  281,   -1,   -1,   60,   61,   62,  257,  258,
  259,  260,  261,  262,  263,   -1,   -1,   -1,  267,   -1,
   -1,  270,   -1,   -1,  273,   -1,  275,  276,   -1,   -1,
   -1,   -1,  281,  257,  258,  259,  260,  261,  262,  263,
   40,   41,   -1,  267,   -1,   -1,  270,   -1,   -1,  273,
   -1,   -1,  276,   -1,   -1,   -1,   -1,  281,   -1,   59,
   60,   61,   62,   -1,   -1,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,  261,  262,  263,   -1,   -1,   -1,  267,
   40,   41,  270,   -1,   -1,  273,   -1,   41,  276,   43,
   44,   45,   -1,  281,   41,   -1,   43,   44,   45,   -1,
   60,   61,   62,   -1,   -1,   59,   60,   61,   62,   -1,
   -1,   -1,   59,   60,   61,   62,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,  260,  261,  262,  263,   40,   41,
   -1,  267,   -1,   -1,  270,   40,   -1,  273,   -1,   -1,
  276,   -1,   -1,   -1,   -1,  281,   -1,   -1,   60,   61,
   62,   -1,   -1,   -1,   59,   60,   61,   62,   -1,   -1,
   -1,  257,  258,  259,  260,  261,  262,  263,   -1,   -1,
   -1,  267,   -1,   -1,  270,   -1,   -1,   -1,  274,   -1,
  276,   42,   43,   -1,   45,  281,   47,   -1,   -1,   -1,
   41,   42,   43,   44,   45,   -1,   47,   -1,   -1,   60,
   61,   62,  257,  258,  259,  260,  261,  262,  263,   60,
   61,   62,  267,   42,   43,  270,   45,   -1,   47,   -1,
   -1,  276,   -1,   -1,   -1,   -1,  281,   -1,   -1,   -1,
   -1,   60,   61,   62,   -1,   -1,  113,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  131,   -1,   -1,  257,  258,  259,
  260,   -1,  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  151,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
  260,   -1,  262,  257,  258,  259,  260,  184,  262,   -1,
  257,  258,  259,  260,   -1,  262,   -1,  271,   -1,   -1,
   -1,   -1,   -1,   -1,  271,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  213,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,   -1,
  262,   -1,  257,  258,  259,  260,   -1,  262,   -1,  236,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  260,   -1,   -1,   -1,   -1,   -1,  266,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  259,  260,
   -1,  262,  279,   -1,   -1,   -1,  257,  258,  259,  260,
   -1,  262,   -1,   -1,   -1,   -1,   -1,  294,   -1,  296,
  297,  298,  299,  300,  301,  302,  303,   -1,   -1,   -1,
  259,  260,   -1,  262,   -1,   -1,  313,
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
"cuerpo_funcion : RET '(' expresion ')' ';'",
"cuerpo_funcion : cuerpo RET '(' ')' ';'",
"cuerpo_funcion : cuerpo RET expresion ')' ';'",
"cuerpo_funcion : cuerpo RET '(' expresion ';'",
"cuerpo_funcion : cuerpo RET '(' expresion ')'",
"invoc_fun : ID '(' param_real ')'",
"invoc_fun : ID '(' ')'",
"param_real : tipo expresion",
"param_real : expresion",
"sald_mensaj : OUTF '(' mensaje ')'",
"sald_mensaj : OUTF mensaje ')'",
"sald_mensaj : OUTF '(' mensaje",
"sald_mensaj : OUTF mensaje",
"sald_mensaj : OUTF '(' ')'",
"sald_mensaj : OUTF",
"mensaje : expresion",
"mensaje : CADMUL",
"for : FOR '(' ID ASIGN CTE ';' condicion ';' foravanc CTE ')' cuerpo_control",
"for : FOR ID ASIGN CTE ';' condicion ';' foravanc CTE ')' cuerpo_control",
"for : FOR '(' ID ASIGN CTE ';' condicion ';' foravanc CTE cuerpo_control",
"for : FOR ID ASIGN CTE ';' condicion ';' foravanc CTE cuerpo_control",
"for : FOR '(' ASIGN CTE ';' condicion ';' foravanc CTE ')' cuerpo_control",
"for : FOR '(' ID CTE ';' condicion ';' foravanc CTE ')' cuerpo_control",
"for : FOR '(' ID ASIGN ';' condicion ';' foravanc CTE ')' cuerpo_control",
"for : FOR '(' ';' condicion ';' foravanc CTE ')' cuerpo_control",
"for : FOR '(' ID ASIGN CTE ';' ';' foravanc CTE ')' cuerpo_control",
"for : FOR '(' ID ASIGN CTE ';' condicion ';' CTE ')' cuerpo_control",
"for : FOR '(' ID ASIGN CTE ';' condicion ';' foravanc ')' cuerpo_control",
"for : FOR '(' ID ASIGN CTE ';' ')' cuerpo_control",
"for : FOR '(' ';' ';' foravanc CTE ')'",
"for : FOR '(' ID ASIGN CTE ';' ';' ')' cuerpo_control",
"for : FOR '(' ';' condicion ';' ')' cuerpo_control",
"for : '(' ID ASIGN CTE ';' condicion ';' foravanc CTE ')' cuerpo_control",
"foravanc : UP",
"foravanc : DOWN",
"declar_tipo_trip : TYPEDEF TRIPLE '<' tipo_basico '>' ID",
"declar_tipo_trip : TRIPLE '<' tipo_basico '>' ID",
"declar_tipo_trip : TYPEDEF '<' tipo_basico '>' ID",
"declar_tipo_trip : TYPEDEF TRIPLE tipo_basico '>' ID",
"declar_tipo_trip : TYPEDEF TRIPLE '<' tipo_basico ID",
"declar_tipo_trip : TYPEDEF TRIPLE tipo_basico ID",
"declar_tipo_trip : TYPEDEF TRIPLE '<' '>' ID",
"goto : GOTO TAG",
};

//#line 206 "gramatica.y"
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
//#line 785 "Parser.java"
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
case 100:
//#line 141 "gramatica.y"
{lex.addErrorLexico("falta el cuerpo en el cuerpo en la funcion declarada"); }
break;
case 101:
//#line 142 "gramatica.y"
{lex.addErrorLexico("falta la expresion en el cuerpo en la funcion declarada"); }
break;
case 102:
//#line 143 "gramatica.y"
{lex.addErrorLexico("falta el parentesis izquierdo en el cuerpo en la funcion declarada"); }
break;
case 103:
//#line 144 "gramatica.y"
{lex.addErrorLexico("falta el parentesis derecho en el cuerpo en la funcion declarada"); }
break;
case 104:
//#line 145 "gramatica.y"
{lex.addErrorLexico("falta el punto y coma en el cuerpo en la funcion declarada"); }
break;
case 106:
//#line 149 "gramatica.y"
{lex.addErrorLexico("falta el parametro real en la invocación"); }
break;
case 110:
//#line 157 "gramatica.y"
{lex.addErrorLexico("falta el parentesis izquierdo del mensaje del OUTF"); }
break;
case 111:
//#line 158 "gramatica.y"
{lex.addErrorLexico("falta el parentesis derecho del mensaje del OUTF"); }
break;
case 112:
//#line 159 "gramatica.y"
{lex.addErrorLexico("faltan ambos parentesis del mensaje del OUTF"); }
break;
case 113:
//#line 160 "gramatica.y"
{lex.addErrorLexico("falta el mensaje del OUTF"); }
break;
case 114:
//#line 161 "gramatica.y"
{lex.addErrorLexico("falta el mensaje y los parentesis del OUTF"); }
break;
case 118:
//#line 168 "gramatica.y"
{lex.addErrorLexico("falta el parentesis izquierdo del FOR"); }
break;
case 119:
//#line 169 "gramatica.y"
{lex.addErrorLexico("falta el parentesis derecho del FOR"); }
break;
case 120:
//#line 170 "gramatica.y"
{lex.addErrorLexico("faltan ambos parentesis del FOR"); }
break;
case 121:
//#line 172 "gramatica.y"
{lex.addErrorLexico("falta el ID del FOR"); }
break;
case 122:
//#line 173 "gramatica.y"
{lex.addErrorLexico("falta la asignacion del FOR"); }
break;
case 123:
//#line 174 "gramatica.y"
{lex.addErrorLexico("falta la constante a asignar del FOR"); }
break;
case 124:
//#line 175 "gramatica.y"
{lex.addErrorLexico("falta todo ID ASIGN CTE del FOR"); }
break;
case 125:
//#line 176 "gramatica.y"
{lex.addErrorLexico("falta la condicion del FOR"); }
break;
case 126:
//#line 177 "gramatica.y"
{lex.addErrorLexico("falta el avance del FOR"); }
break;
case 127:
//#line 178 "gramatica.y"
{lex.addErrorLexico("falta la constante de avance del FOR"); }
break;
case 128:
//#line 179 "gramatica.y"
{lex.addErrorLexico("falta condicion y avance entero del FOR"); }
break;
case 129:
//#line 181 "gramatica.y"
{lex.addErrorLexico("falta asignacion entera y condicion entera del FOR"); }
break;
case 130:
//#line 182 "gramatica.y"
{lex.addErrorLexico("falta condicion entera y avance entero del FOR"); }
break;
case 131:
//#line 183 "gramatica.y"
{lex.addErrorLexico("falta asignacion entera y avance entero del FOR"); }
break;
case 132:
//#line 187 "gramatica.y"
{lex.addErrorLexico("falta el FOR"); }
break;
case 136:
//#line 196 "gramatica.y"
{lex.addErrorLexico("falta TYPEDEF en la declaración del TRIPLE"); }
break;
case 137:
//#line 197 "gramatica.y"
{lex.addErrorLexico("falta TRIPLE en la declaración del TRIPLE"); }
break;
case 138:
//#line 198 "gramatica.y"
{lex.addErrorLexico("falta < en la declaración del TRIPLE"); }
break;
case 139:
//#line 199 "gramatica.y"
{lex.addErrorLexico("falta > en la declaración del TRIPLE"); }
break;
case 140:
//#line 200 "gramatica.y"
{lex.addErrorLexico("falta > y < en la declaración del TRIPLE"); }
break;
case 141:
//#line 201 "gramatica.y"
{lex.addErrorLexico("falta el tipo_basico en la declaración del TRIPLE"); }
break;
//#line 1282 "Parser.java"
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
