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
    0,    1,    1,    2,    2,    3,    3,    3,    4,    4,
    4,    4,    4,    4,   14,   14,   17,   17,   10,   10,
   16,   16,   16,   16,   18,   18,   19,   19,   20,   20,
    5,   22,   22,   21,   21,   21,    8,   15,   15,   15,
   23,   23,   23,   24,   24,   24,    6,   25,   26,    9,
   27,   27,   11,   28,   28,   12,   29,   29,    7,   13,
};
final static short yylen[] = {                            2,
    4,    2,    1,    1,    1,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    5,    9,    3,    1,    5,    7,
    1,    1,    1,    1,    3,    1,    2,    1,    1,    4,
    2,    3,    1,    1,    1,    1,    3,    3,    3,    1,
    3,    3,    1,    1,    1,    1,    9,    2,    5,    4,
    2,    1,    4,    1,    1,   12,    1,    1,    6,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,   34,   35,    0,    0,
    0,    0,    3,    4,    5,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   60,    0,
    0,    0,    0,    0,    1,    2,    6,    7,    8,    9,
   10,   11,   12,   13,   14,    0,   33,    0,    0,   45,
   46,    0,   44,    0,    0,   43,    0,    0,    0,    0,
    0,    0,    0,    0,   55,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   50,   30,    0,    0,
    0,   21,   22,   23,   24,    0,    0,   26,    0,   53,
   36,    0,    0,   32,    0,    0,   41,   42,    0,    0,
    0,    0,   28,    0,    0,   19,    0,    0,    0,    0,
    0,    0,   15,   25,   27,    0,   59,   48,    0,    0,
    0,   20,    0,    0,    0,    0,    0,   57,   58,    0,
    0,    0,   47,    0,   16,    0,    0,    0,   56,   49,
};
final static short yydgoto[] = {                          2,
   12,   13,   14,   15,   16,   17,   18,   19,   51,   21,
   22,   23,   24,   32,   80,   86,   81,   89,  104,   53,
   26,   49,   55,   56,  109,  127,   57,   67,  130,
};
final static short yysindex[] = {                      -234,
 -240,    0, -105,  -12, -235,   10,    0,    0,   20,   51,
 -158, -130,    0,    0,    0,   53,   67,   71,   82,   84,
   89,   91,   96,  102, -102, -209, -169,  -80,    0,  -79,
  -35,  -92, -213,  120,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -91,    0,  -75,  137,    0,
    0,   76,    0,  -91,   50,    0,  142,   62,   59,  -74,
  -12,  -91,  -19, -205,    0,   76,  145, -140,   76,  148,
  -68,  -91,  -91,   76,  -91,  -91,    0,    0,  -67,   76,
   35,    0,    0,    0,    0,  -91, -204,    0, -174,    0,
    0,  128, -140,    0,   50,   50,    0,    0,  133,  -58,
  -91,   32,    0, -154, -205,    0,  -64,  -63,  154,   20,
  156,   76,    0,    0,    0,  -78,    0,    0,  -73,  139,
  -91,    0, -105,  -95,   61, -183,  -71,    0,    0,  -59,
  164,  166,    0,  167,    0,  -91, -205,  108,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  -41,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  150,    0,
    0,  169,    0,    0,  -29,    0,    0,   86,    0,    0,
  -34,    0,    0,    0,    0,  171,    0,    0,   53,    0,
    0,    0,    0,  172,    0,    0,    0,    0,    0,   63,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -24,   -5,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   90,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   92,    6,    0,  -23,    0,    0,    0,    0,   31,    0,
    0,    0,    0,   97,   24,  114,   98,  -76,    0,   19,
   22,    0,  101,  100,    0,    0,    0,    0,    0,
};
final static int YYTABLESIZE=257;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         29,
   29,   29,   85,   29,   62,   29,   29,   29,   29,   29,
   29,   40,   29,   40,   40,   40,   38,   36,   38,   38,
   38,   25,    1,   72,   29,   73,   29,   27,  116,   40,
   25,   40,    3,   20,   38,   39,   38,   39,   39,   39,
   88,   85,   20,   61,   50,   29,   59,   47,   54,   30,
   52,   61,   61,   39,   63,   39,   66,    5,    5,   31,
  139,    6,    6,  103,    9,    9,   65,   87,   48,   69,
   10,   10,  113,    4,   72,  100,   73,   74,  101,    5,
  115,   88,   25,    6,    7,    8,    9,    4,   50,   92,
   33,   75,   10,   11,   20,  132,   76,  105,    7,    8,
  106,  131,   61,   18,  101,   25,   18,   34,    5,  102,
   28,   37,    6,   88,  108,    9,   91,   20,   72,  114,
   73,   10,   25,   25,  112,   38,    4,    7,    8,   39,
   17,   36,    5,   17,   20,   20,    6,    7,    8,    9,
   40,   25,   41,   35,   25,   10,   11,   42,  140,   43,
   72,    4,   73,   20,   44,   25,   20,    5,   46,  138,
   45,    6,    7,    8,    9,   61,   50,   20,  128,  129,
   10,   11,   95,   96,   97,   98,   58,   60,   64,   68,
   71,   70,   77,   78,   28,   90,   79,   93,   94,  107,
   99,  110,  117,  118,  119,  121,  122,  124,  134,  123,
   82,   83,  133,   84,  135,  136,  120,  137,   31,   52,
   29,   54,   51,  111,  126,   36,   36,    0,  125,   29,
    0,   61,   50,    0,   29,   29,   29,   29,    0,   40,
   40,    0,   40,    0,   38,   38,   36,   38,    0,   82,
   83,    0,   84,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   39,   39,    0,   39,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   61,   45,   40,   47,   41,   42,   43,   44,
   45,   41,   47,   43,   44,   45,   41,   12,   43,   44,
   45,    3,  257,   43,   59,   45,   61,   40,  105,   59,
   12,   61,  273,    3,   59,   41,   61,   43,   44,   45,
   64,   61,   12,  257,  258,  281,   28,  257,   27,   40,
   27,  257,  257,   59,   31,   61,   33,  263,  263,   40,
  137,  267,  267,   87,  270,  270,  280,  273,  278,   46,
  276,  276,   41,  257,   43,   41,   45,   54,   44,  263,
  104,  105,   64,  267,  268,  269,  270,  257,  258,   68,
   40,   42,  276,  277,   64,  279,   47,  272,  268,  269,
  275,   41,  257,   41,   44,   87,   44,  266,  263,   86,
  123,   59,  267,  137,   93,  270,  257,   87,   43,  274,
   45,  276,  104,  105,  101,   59,  257,  268,  269,   59,
   41,  126,  263,   44,  104,  105,  267,  268,  269,  270,
   59,  123,   59,  274,  126,  276,  277,   59,   41,   59,
   43,  257,   45,  123,   59,  137,  126,  263,  261,  136,
   59,  267,  268,  269,  270,  257,  258,  137,  264,  265,
  276,  277,   72,   73,   75,   76,  257,  257,  271,   60,
   44,  257,   41,  125,  123,   41,  261,   40,  257,   62,
  258,   59,  257,  257,   41,   40,  275,   59,  258,  273,
  259,  260,  274,  262,   41,   40,  110,   41,   59,   41,
  125,   41,   41,  100,  123,  257,  258,   -1,  121,  261,
   -1,  257,  258,   -1,  259,  260,  261,  262,   -1,  259,
  260,   -1,  262,   -1,  259,  260,  278,  262,   -1,  259,
  260,   -1,  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  259,  260,   -1,  262,
};
}
final static short YYFINAL=2;
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
"cuerpo : cuerpo sentencia",
"cuerpo : sentencia",
"sentencia : sentec_declar",
"sentencia : sentec_eject",
"sentec_declar : declaracion_var ';'",
"sentec_declar : declaracion_fun ';'",
"sentec_declar : declar_tipo_trip ';'",
"sentec_eject : asignacion ';'",
"sentec_eject : invoc_fun ';'",
"sentec_eject : seleccion ';'",
"sentec_eject : sald_mensaj ';'",
"sentec_eject : for ';'",
"sentec_eject : goto ';'",
"condicion : '(' expresion comparador expresion ')'",
"condicion : '(' '(' lista_expres ')' comparador '(' lista_expres ')' ')'",
"lista_expres : lista_expres ',' expresion",
"lista_expres : expresion",
"seleccion : IF condicion THEN cuerpo_control END_IF",
"seleccion : IF condicion THEN cuerpo_control ELSE cuerpo_control END_IF",
"comparador : MASI",
"comparador : MENOSI",
"comparador : DIST",
"comparador : '='",
"cuerpo_control : BEGIN multip_cuerp_fun END",
"cuerpo_control : sentec_eject",
"multip_cuerp_fun : multip_cuerp_fun sentec_eject",
"multip_cuerp_fun : sentec_eject",
"variable : ID",
"variable : ID '{' variable '}'",
"declaracion_var : tipo lista_variables",
"lista_variables : lista_variables ',' ID",
"lista_variables : ID",
"tipo : ULONGINT",
"tipo : DOUBLE",
"tipo : ID",
"asignacion : variable ASIGN expresion",
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
"parametro : tipo ID",
"cuerpo_funcion : cuerpo RET '(' expresion ')'",
"invoc_fun : ID '(' param_real ')'",
"param_real : tipo expresion",
"param_real : expresion",
"sald_mensaj : OUTF '(' mensaje ')'",
"mensaje : expresion",
"mensaje : CADMUL",
"for : FOR '(' ID ASIGN CTE ';' condicion ';' foravanc CTE ')' cuerpo_control",
"foravanc : UP",
"foravanc : DOWN",
"declar_tipo_trip : TYPEDEF TRIPLE '<' tipo '>' ID",
"goto : GOTO TAG",
};

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

String yyerror(String aaaaa) {
	return aaaaa;
}


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
