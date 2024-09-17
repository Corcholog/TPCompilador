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
    4,    4,    4,    4,    4,   14,   14,   17,   17,   10,
   10,   16,   16,   16,   16,   16,   16,   18,   18,   19,
   19,   20,   20,    5,   22,   22,   21,   21,   21,    8,
   15,   15,   15,   23,   23,   23,   24,   24,   24,    6,
   25,   26,    9,   27,   27,   11,   28,   28,   12,   29,
   29,    7,   13,
};
final static short yylen[] = {                            2,
    4,    2,    1,    1,    1,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    1,    5,    9,    3,    1,    5,
    7,    1,    1,    1,    1,    1,    1,    3,    1,    2,
    1,    1,    4,    2,    3,    1,    1,    1,    1,    3,
    3,    3,    1,    3,    3,    1,    1,    1,    1,    9,
    2,    6,    4,    2,    1,    4,    1,    1,   12,    1,
    1,    6,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,   37,   38,    0,    0,
    0,   15,    0,    3,    4,    5,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   63,
    0,    0,    0,    0,    0,    1,    2,    6,    7,    8,
    9,   10,   11,   12,   13,   14,    0,   36,    0,    0,
   48,   49,    0,   47,    0,    0,   46,    0,    0,    0,
    0,    0,    0,    0,    0,   58,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   53,   33,    0,
    0,    0,   22,   23,   24,   25,   26,   27,    0,    0,
   29,    0,   56,   39,    0,    0,   35,    0,    0,   44,
   45,    0,    0,    0,    0,   31,    0,    0,   20,    0,
    0,    0,    0,    0,    0,   16,   28,   30,    0,   62,
   51,    0,    0,    0,   21,    0,    0,    0,    0,    0,
   60,   61,    0,    0,    0,   50,    0,   17,    0,    0,
    0,   59,    0,   52,
};
final static short yydgoto[] = {                          2,
   13,   14,   15,   16,   17,   18,   19,   20,   52,   22,
   23,   24,   25,   33,   81,   89,   82,   92,  107,   54,
   27,   50,   56,   57,  112,  130,   58,   68,  133,
};
final static short yysindex[] = {                      -235,
 -244,    0,  -94,  -22, -202,    6,    0,    0,   50,   55,
 -161,    0, -185,    0,    0,    0,   61,   87,   99,  101,
  105,  107,  120,  126,  127, -147, -201,  -68,  -86,    0,
  -65,  -35,  -78, -205,  134,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -189,    0,  -62,  152,
    0,    0,   90,    0, -189,   24,    0,  157,   76,   78,
  -57,  -22, -189,    2, -160,    0,   90,  164, -170,   90,
  166,  -50, -189, -189,   90, -189, -189,    0,    0,  -49,
   90,   68,    0,    0,    0,    0,    0,    0, -189,  -79,
    0, -157,    0,    0,  146, -170,    0,   24,   24,    0,
    0,  151,  -12, -189,   59,    0, -140, -160,    0,  -46,
  -45,  172,   50,  174,   90,    0,    0,    0,  -60,    0,
    0,  -55,  160, -189,    0,  -94, -191,  118, -125,  -53,
    0,    0,  -26,  183,  189,    0,  193,    0, -189, -160,
  112,    0,  180,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  -41,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  181,
    0,    0,  200,    0,    0,  -29,    0,    0,  117,    0,
    0,  -34,    0,    0,    0,    0,  202,    0,    0,   99,
    0,    0,    0,    0,  203,    0,    0,    0,    0,    0,
  124,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -24,   -2,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  136,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  119,  -10,    0,  -14,    0,    0,    0,    0,   21,    0,
    0,    0,    0,  133,   33,  148,  125,  -85,    0,   41,
   12,    0,   51,   63,    0,    0,    0,    0,    0,
};
final static int YYTABLESIZE=264;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         32,
   32,   32,   37,   32,   63,   32,   32,   32,   32,   32,
   32,   43,   32,   43,   43,   43,   41,   28,   41,   41,
   41,    1,  119,   21,   32,   32,   32,   32,    3,   43,
   43,   43,   43,   21,   41,   41,   41,   41,   42,   55,
   42,   42,   42,   26,   73,   31,   74,   87,   86,   88,
   91,   62,   51,   26,  142,   48,   42,   42,   42,   42,
   53,   87,   86,   88,   64,   76,   67,   62,   51,   60,
   77,    4,  131,  132,   66,  106,   49,    5,   30,   70,
   95,    6,    7,    8,    9,   21,   94,   75,   36,   32,
   10,   11,  118,   91,   34,   12,   62,    7,    8,  116,
   29,   73,    5,   74,   35,   26,    6,  111,  103,    9,
   21,  104,   90,   47,  108,   10,   62,  109,   37,   38,
   12,  105,    5,   98,   99,   91,    6,   21,   21,    9,
   26,    4,   73,  117,   74,   10,  115,    5,  100,  101,
   12,    6,    7,    8,    9,   39,   21,   26,   26,   21,
   10,   11,  143,  135,   73,   12,   74,   40,  134,   41,
   21,  104,    4,   42,   19,   43,   26,   19,    5,   26,
   59,  141,    6,    7,    8,    9,   18,   62,   44,   18,
   26,   10,   11,    5,   45,   46,   12,    6,    4,   51,
    9,   61,   65,   69,   71,   72,   10,   78,   29,    7,
    8,   12,   79,   80,   93,   96,   97,  110,  102,  113,
  120,  121,  122,  124,  125,   39,   39,  126,  127,   32,
  136,   62,   51,  138,   32,   32,   32,   32,  139,   43,
   43,  137,   43,  140,   41,   41,   39,   41,  144,   34,
   55,   32,   57,   54,  129,  123,   83,   84,  128,   85,
  114,    0,    0,    0,    0,    0,   42,   42,    0,   42,
   83,   84,    0,   85,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   13,   45,   40,   47,   41,   42,   43,   44,
   45,   41,   47,   43,   44,   45,   41,   40,   43,   44,
   45,  257,  108,    3,   59,   60,   61,   62,  273,   59,
   60,   61,   62,   13,   59,   60,   61,   62,   41,   28,
   43,   44,   45,    3,   43,   40,   45,   60,   61,   62,
   65,  257,  258,   13,  140,  257,   59,   60,   61,   62,
   28,   60,   61,   62,   32,   42,   34,  257,  258,   29,
   47,  257,  264,  265,  280,   90,  278,  263,  281,   47,
   69,  267,  268,  269,  270,   65,  257,   55,  274,   40,
  276,  277,  107,  108,   40,  281,  257,  268,  269,   41,
  123,   43,  263,   45,  266,   65,  267,   96,   41,  270,
   90,   44,  273,  261,  272,  276,  257,  275,  129,   59,
  281,   89,  263,   73,   74,  140,  267,  107,  108,  270,
   90,  257,   43,  274,   45,  276,  104,  263,   76,   77,
  281,  267,  268,  269,  270,   59,  126,  107,  108,  129,
  276,  277,   41,  279,   43,  281,   45,   59,   41,   59,
  140,   44,  257,   59,   41,   59,  126,   44,  263,  129,
  257,  139,  267,  268,  269,  270,   41,  257,   59,   44,
  140,  276,  277,  263,   59,   59,  281,  267,  257,  258,
  270,  257,  271,   60,  257,   44,  276,   41,  123,  268,
  269,  281,  125,  261,   41,   40,  257,   62,  258,   59,
  257,  257,   41,   40,  275,  257,  258,  273,   59,  261,
  274,  257,  258,   41,  259,  260,  261,  262,   40,  259,
  260,  258,  262,   41,  259,  260,  278,  262,   59,   59,
   41,  125,   41,   41,  126,  113,  259,  260,  124,  262,
  103,   -1,   -1,   -1,   -1,   -1,  259,  260,   -1,  262,
  259,  260,   -1,  262,
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
"sentec_eject : TAG",
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
"comparador : '<'",
"comparador : '>'",
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

String yyerror(String a) {
	return a;
}

int yylex() {
	return AnalizadorLexico.pruebaLex();
}

public static void main(String[] args) {
	Parser p = new Parser();
	System.out.println(p.yyparse());
}

}
//################### END OF CLASS ##############################
