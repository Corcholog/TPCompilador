(module
(import "console" "log" (func $log (param i32 i32)))
(import "js" "mem" (memory 1))
(import "env" "console_log" (func $console_log_i32 (param i32)))
(import "env" "console_log" (func $console_log_f64 (param f64)))
(import "env" "exit" (func $exit))
(global $globalAathosV1 (mut i32)(i32.const 0))
(global $globalAathosV2 (mut i32)(i32.const 0))
(global $globalAathosV3 (mut i32)(i32.const 0))
(global $globalAk (mut i32)(i32.const 0))
(global $globalAi (mut i32)(i32.const 0))
(global $globalAj (mut i32)(i32.const 0))
(global $globalAu (mut f64)(f64.const 0))
(global $globalAt3V1 (mut f64)(f64.const 0))
(global $globalAt3V2 (mut f64)(f64.const 0))
(global $globalAt3V3 (mut f64)(f64.const 0))
(global $globalAt2V1 (mut f64)(f64.const 0))
(global $globalAt2V2 (mut f64)(f64.const 0))
(global $globalAt2V3 (mut f64)(f64.const 0))
(global $globalAt1V1 (mut f64)(f64.const 0))
(global $globalAt1V2 (mut f64)(f64.const 0))
(global $globalAt1V3 (mut f64)(f64.const 0))
(global $globalAporthosV1 (mut i32)(i32.const 0))
(global $globalAporthosV2 (mut i32)(i32.const 0))
(global $globalAporthosV3 (mut i32)(i32.const 0))
(global $globalAc (mut i32)(i32.const 0))
(global $globalAd (mut f64)(f64.const 0))
(global $globalAa (mut i32)(i32.const 0))
(global $globalAb (mut i32)(i32.const 0))
(global $globalAaramisV1 (mut i32)(i32.const 0))
(global $globalAaramisV2 (mut i32)(i32.const 0))
(global $globalAaramisV3 (mut i32)(i32.const 0))
(global $funcionLlamadora (mut i32) (i32.const 0))
(global $AUXOVERFLOW (mut i32) (i32.const 0))
(global $f64auxTripla (mut f64) (f64.const 0))
(global $i32auxTripla (mut i32) (i32.const 0))
(global $f64aux2Tripla (mut f64) (f64.const 0))
(global $i32aux2Tripla (mut i32) (i32.const 0))
(global $f64aux3Tripla (mut f64) (f64.const 0))
(global $i32aux3Tripla (mut i32) (i32.const 0))
(global $AUX1V1i32 (mut i32) (i32.const 0))
(global $AUX1V2i32 (mut i32) (i32.const 0))
(global $AUX1V3i32 (mut i32) (i32.const 0))
(global $AUX2V1i32 (mut i32) (i32.const 0))
(global $AUX2V2i32 (mut i32) (i32.const 0))
(global $AUX2V3i32 (mut i32) (i32.const 0))
(global $AUX1V1f64 (mut f64) (f64.const 0))
(global $AUX1V2f64 (mut f64) (f64.const 0))
(global $AUX1V3f64 (mut f64) (f64.const 0))
(global $AUX2V1f64 (mut f64) (f64.const 0))
(global $AUX2V2f64 (mut f64) (f64.const 0))
(global $AUX2V3f64 (mut f64) (f64.const 0))
(global $AUX3V1f64 (mut f64) (f64.const 0))
(global $AUX3V2f64 (mut f64) (f64.const 0))
(global $AUX3V3f64 (mut f64) (f64.const 0))
(global $AUX3V1i32 (mut i32) (i32.const 0))
(global $AUX3V2i32 (mut i32) (i32.const 0))
(global $AUX3V3i32 (mut i32) (i32.const 0))
(data (i32.const 101)"Error en ejecucion: El resultado de una operacion sin signo dio negativo.")
(data (i32.const 177)"Error en ejecucion: El resultado de una suma de enteros genero overflow.")
(data (i32.const 318)"Error en ejecucion: indice fuera de rango.")
(data (i32.const 363)"Error en ejecucion: se intenta realizar una conversion de flotante negativo a entero sin signo.")
(data (i32.const 252)"Error en ejecucion: se realizo una recursion sobre una funcion.")
(data (i32.const 461) "impresion athos despues de athos{1}:= athos{1} + 1 y athos:= athos + athos * athos")
(data (i32.const 550) "asignacion a elemento de tripla de double")
(data (i32.const 598) "resta double 2.0 - u")
(data (i32.const 625) "tercer elemento, athos")
(data (i32.const 654) "t1{3} := 1.23d-2")
(data (i32.const 677) "mul double 2.0 * u")
(data (i32.const 702) " en el for, pero ejecuto una sola vez porque hay goto")
(data (i32.const 762) "impresion athos deespues de athos{1}:= athos{athos{athos{3}}}  + athos{1} * athos{3}")
(data (i32.const 853) "div tripla t2 / t3 y se guarda en t1")
(data (i32.const 896) "for anidado dentro de un if dentro de un for")
(data (i32.const 947) "pattern lado derecho:")
(data (i32.const 975) "else, pattern dio false")
(data (i32.const 1005) "operaciones de triplas")
(data (i32.const 1034) "suma acctripla t2{3 - 1} + 2.d-23 y se guarda en t1{1}")
(data (i32.const 1095) "segundo elemento, athos{1} + 2")
(data (i32.const 1132) "i es menor 5 por lo que se imprimira 3 veces j")
(data (i32.const 1185) "impresion athos:")
(data (i32.const 1208) "comparacion de triplas")
(data (i32.const 1237) "div ulong 2 / a")
(data (i32.const 1259) " en el for, la variable j es: ")
(data (i32.const 1296) "pattern lado izquierdo:")
(data (i32.const 1326) "resta acctripla t2{3 - 1} - 2.d-23")
(data (i32.const 1367) "operaciones de constantes double y ulongint")
(data (i32.const 1417) "pattern matching con triplas y acceso a las mismas")
(data (i32.const 1474) "primer elemento, athos + athos")
(data (i32.const 1511) "resta ulong 7 - a")
(data (i32.const 1535) "impresion porthos despues de porthos{3}")
(data (i32.const 1581) "suma tripla t2 + t3 y se guarda en t1")
(data (i32.const 1625) "mul ulong 2 * a")
(data (i32.const 1647) "tercer elemento, porthos")
(data (i32.const 1678) "-------------------------------------------------")
(data (i32.const 1734) "t2 tiene los elementos:")
(data (i32.const 1764) "suma ulong 2 + a")
(data (i32.const 1787) " en el for, la variable i es: ")
(data (i32.const 1824) "uso de triplas")
(data (i32.const 1845) "div acctripla t2{3 - 1} / 2.d-23 y se guarda en t1{1}")
(data (i32.const 1905) "i es mayor o igual 5 por lo que se imprimira 3 veces k")
(data (i32.const 1966) "suma double 2.0 + u")
(data (i32.const 1992) "esto no se imprime")
(data (i32.const 2017) "impresion porthos despues de porthos{fun1(fun2(5))}:= athos{fun1(ulongint d)}")
(data (i32.const 2101) "primer elemento, porthos + porthos - porthos")
(data (i32.const 2152) "div double 2.0 / u")
(data (i32.const 2177) "t3 tiene los elementos:")
(data (i32.const 2207) "segundo elemento, aramis{1} + 1")
(data (i32.const 2245) " en el for, la variable k es: ")
(data (i32.const 2282) "t1{3} era")
(data (i32.const 2298) "la comparacion de triplas dio true")
(data (i32.const 2339) "for con salto en primer iteracion")
(data (i32.const 2379) " > ")
(data (i32.const 2389) "mul acctripla t2{3 - 1} * 2.d-23 y se guarda en t1{1}")
(data (i32.const 2449) "mul tripla t2 * t3 y se guarda en t1")
(data (i32.const 2492) "resta tripla t2 - t3 y se guarda en t1")

(global $globalAfun1Aboca (mut i32) (i32.const 0))
(global $globalAfun2Aboca (mut i32) (i32.const 0))
	(global $accesoglobalAt1 (mut i32) (i32.const 1))
	(global $accesoAsigglobalAt1 (mut i32) (i32.const 1))
(global $accesoAsigglobalAt2 (mut i32) (i32.const 1))
(global $accesoglobalAt2 (mut i32) (i32.const 1))
(global $accesoAsigglobalAathos (mut i32) (i32.const 1))
(global $accesoglobalAathos (mut i32) (i32.const 1))
(global $accesoAsigglobalAporthos (mut i32) (i32.const 1))
(global $accesoglobalAporthos (mut i32) (i32.const 1))
(global $accesoglobalAaramis (mut i32) (i32.const 1))
(global $accesoAsigglobalAaramis (mut i32) (i32.const 1))

( func $globalAfun1 (param $globalAfun1Aboca i32) (result i32)
(local $globalAfun1retorno i32)
local.get $globalAfun1Aboca
global.set $globalAfun1Aboca
	i32.const 1
	global.get $funcionLlamadora
	i32.eq
	(if
		(then
			i32.const 252
			i32.const 66
			call $log
			call $exit
		)
	)
	i32.const 0
	local.set $globalAfun1retorno
	global.get $globalAfun1Aboca
	call $console_log_i32
	i32.const 1
	local.set $globalAfun1retorno
	local.get $globalAfun1retorno
	return
	local.get $globalAfun1retorno
)

( func $globalAfun2 (param $globalAfun2Aboca i32) (result i32)
(local $globalAfun2retorno i32)
local.get $globalAfun2Aboca
global.set $globalAfun2Aboca
	i32.const 2
	global.get $funcionLlamadora
	i32.eq
	(if
		(then
			i32.const 252
			i32.const 66
			call $log
			call $exit
		)
	)
	i32.const 0
	local.set $globalAfun2retorno
	i32.const 1
	local.set $globalAfun2retorno
	local.get $globalAfun2retorno
	return
	local.get $globalAfun2retorno
)


(func $main
		(local $comp4 i32)
		(local $comp19 i32)
		(local $comp23 i32)
					(local $comp29 i32)
					(local $comp42 i32)
(local $comp71V1 i32)
(local $comp71V2 i32)
(local $comp71V3 i32)
(local $comp222V1 i32)
(local $comp222V2 i32)
(local $comp222V3 i32)
(local $comp222 i32)
(local $comp225 i32)
(local $comp226V1 i32)
(local $comp226V2 i32)
(local $comp226V3 i32)
(local $comp226 i32)
	(block $globalApepe@

	i32.const 2339
	i32.const 33
	call $log
	i32.const 1678
	i32.const 49
	call $log
	i32.const 1
	global.set $globalAi
	block $endforAFOR1
	loop $FOR1
		global.get $globalAi
		i32.const 10
		i32.ge_u
		local.set $comp4
		local.get $comp4
		br_if $endforAFOR1
		i32.const 702
		i32.const 53
		call $log
		br $globalApepe@
		global.get $globalAi
		i32.const 1
		i32.add
		global.set $AUXOVERFLOW
		global.get $AUXOVERFLOW
		i32.const 0
		i32.lt_s
		(if
			(then
				i32.const 177
				i32.const 75
				call $log
				call $exit
			)
		)
		global.get $AUXOVERFLOW
		global.set $globalAi
		br $FOR1
	end
	end
	i32.const 1992
	i32.const 18
	call $log
	) ;; fin de tag: global:pepe@
	i32.const 1678
	i32.const 49
	call $log
	i32.const 896
	i32.const 44
	call $log
	i32.const 1678
	i32.const 49
	call $log
	i32.const 1
	global.set $globalAi
	block $endforAFOR2
	loop $FOR2
		global.get $globalAi
		i32.const 10
		i32.ge_u
		local.set $comp19
		local.get $comp19
		br_if $endforAFOR2
		i32.const 1787
		i32.const 30
		call $log
		global.get $globalAi
		call $console_log_i32
		global.get $globalAi
		i32.const 5
		i32.lt_u
		local.set $comp23
		local.get $comp23
		(if
			(then
				i32.const 1132
				i32.const 46
				call $log
				i32.const 1
				global.set $globalAj
				block $endforAFOR3
				loop $FOR3
					global.get $globalAj
					i32.const 3
					i32.gt_u
					local.set $comp29
					local.get $comp29
					br_if $endforAFOR3
					i32.const 1259
					i32.const 30
					call $log
					global.get $globalAj
					call $console_log_i32
					global.get $globalAj
					i32.const 1
					i32.add
					global.set $AUXOVERFLOW
					global.get $AUXOVERFLOW
					i32.const 0
					i32.lt_s
					(if
						(then
							i32.const 177
							i32.const 75
							call $log
							call $exit
						)
					)
					global.get $AUXOVERFLOW
					global.set $globalAj
					br $FOR3
				end
				end
			)
			(else
				i32.const 1905
				i32.const 54
				call $log
				i32.const 1
				global.set $globalAk
				block $endforAFOR4
				loop $FOR4
					global.get $globalAk
					i32.const 3
					i32.gt_u
					local.set $comp42
					local.get $comp42
					br_if $endforAFOR4
					i32.const 2245
					i32.const 30
					call $log
					global.get $globalAk
					call $console_log_i32
					global.get $globalAk
					i32.const 1
					i32.add
					global.set $AUXOVERFLOW
					global.get $AUXOVERFLOW
					i32.const 0
					i32.lt_s
					(if
						(then
							i32.const 177
							i32.const 75
							call $log
							call $exit
						)
					)
					global.get $AUXOVERFLOW
					global.set $globalAk
					br $FOR4
				end
				end
			)
		)
		global.get $globalAi
		i32.const 1
		i32.add
		global.set $AUXOVERFLOW
		global.get $AUXOVERFLOW
		i32.const 0
		i32.lt_s
		(if
			(then
				i32.const 177
				i32.const 75
				call $log
				call $exit
			)
		)
		global.get $AUXOVERFLOW
		global.set $globalAi
		br $FOR2
	end
	end
	i32.const 1678
	i32.const 49
	call $log
	i32.const 1824
	i32.const 14
	call $log
	i32.const 550
	i32.const 41
	call $log
	i32.const 2282
	i32.const 9
	call $log
	i32.const 3
	global.set $accesoglobalAt1
	global.get $accesoglobalAt1
	i32.const 1
	i32.eq
	(if
		(then
			global.get $globalAt1V1
			global.set $f64auxTripla
		)
		(else
			global.get $accesoglobalAt1
			i32.const 2
			i32.eq
			(if
				(then
					global.get $globalAt1V2
					global.set $f64auxTripla
				)
				(else
					global.get $accesoglobalAt1
					i32.const 3
					i32.eq
					(if
						(then
							global.get $globalAt1V3
							global.set $f64auxTripla
						)
						(else
							i32.const 318
							i32.const 45
							call $log
							call $exit
						)
					)
				)
			)
		)
	)
global.get $f64auxTripla
call $console_log_f64
i32.const 3
global.set $accesoAsigglobalAt1
global.get $accesoAsigglobalAt1
i32.const 1
i32.eq
(if
	(then
		f64.const 1.2300000000000000e-02
		global.set $globalAt1V1
	)
	(else
		global.get $accesoAsigglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				f64.const 1.2300000000000000e-02
				global.set $globalAt1V2
			)
			(else
				global.get $accesoAsigglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						f64.const 1.2300000000000000e-02
						global.set $globalAt1V3
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
i32.const 654
i32.const 16
call $log
i32.const 3
global.set $accesoglobalAt1
global.get $accesoglobalAt1
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt1V1
		global.set $f64auxTripla
	)
	(else
		global.get $accesoglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt1V2
				global.set $f64auxTripla
			)
			(else
				global.get $accesoglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt1V3
						global.set $f64auxTripla
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
global.get $f64auxTripla
call $console_log_f64
i32.const 3
global.set $accesoAsigglobalAt2
global.get $accesoAsigglobalAt2
i32.const 1
i32.eq
(if
	(then
		f64.const 2.0000000000000000e+00
		global.set $globalAt2V1
	)
	(else
		global.get $accesoAsigglobalAt2
		i32.const 2
		i32.eq
		(if
			(then
				f64.const 2.0000000000000000e+00
				global.set $globalAt2V2
			)
			(else
				global.get $accesoAsigglobalAt2
				i32.const 3
				i32.eq
				(if
					(then
						f64.const 2.0000000000000000e+00
						global.set $globalAt2V3
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
i32.const 1208
i32.const 22
call $log
global.get $globalAt1V1
global.get $globalAt2V1
f64.sub
global.set $AUX1V1f64
global.get $globalAt1V2
global.get $globalAt2V2
f64.sub
global.set $AUX1V2f64
global.get $globalAt1V3
global.get $globalAt2V3
f64.sub
global.set $AUX1V3f64
global.get $globalAt2V1
global.get $globalAt3V1
f64.add
global.set $AUX2V1f64
global.get $globalAt2V2
global.get $globalAt3V2
f64.add
global.set $AUX2V2f64
global.get $globalAt2V3
global.get $globalAt3V3
f64.add
global.set $AUX2V3f64
global.get $AUX1V1f64
global.get $AUX2V1f64
f64.lt
local.set $comp71V1
global.get $AUX1V2f64
global.get $AUX2V2f64
f64.lt
local.set $comp71V2
global.get $AUX1V3f64
global.get $AUX2V3f64
f64.lt
local.set $comp71V3
local.get $comp71V3
local.get $comp71V2
i32.eq
local.get $comp71V1
i32.eq
(if
	(then
		i32.const 2298
		i32.const 34
		call $log
	)
)
i32.const 1678
i32.const 49
call $log
i32.const 1005
i32.const 22
call $log
i32.const 2
global.set $accesoAsigglobalAt2
global.get $accesoAsigglobalAt2
i32.const 1
i32.eq
(if
	(then
		f64.const 1.0000000000000000e+01
		global.set $globalAt2V1
	)
	(else
		global.get $accesoAsigglobalAt2
		i32.const 2
		i32.eq
		(if
			(then
				f64.const 1.0000000000000000e+01
				global.set $globalAt2V2
			)
			(else
				global.get $accesoAsigglobalAt2
				i32.const 3
				i32.eq
				(if
					(then
						f64.const 1.0000000000000000e+01
						global.set $globalAt2V3
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
i32.const 1
global.set $accesoAsigglobalAt1
i32.const 3
i32.const 1
i32.sub
global.set $AUXOVERFLOW
global.get $AUXOVERFLOW
i32.const 0
i32.lt_s
(if
	(then
		i32.const 101
		i32.const 76
		call $log
		call $exit
	)
)
global.get $AUXOVERFLOW
global.set $accesoglobalAt2
global.get $accesoglobalAt2
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt2V1
		global.set $f64auxTripla
	)
	(else
		global.get $accesoglobalAt2
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt2V2
				global.set $f64auxTripla
			)
			(else
				global.get $accesoglobalAt2
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt2V3
						global.set $f64auxTripla
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
global.get $f64auxTripla
f64.const 2.0000000000000000e-23
f64.mul
global.set $f64auxTripla
global.get $accesoAsigglobalAt1
i32.const 1
i32.eq
(if
	(then
		global.get $f64auxTripla
		global.set $globalAt1V1
	)
	(else
		global.get $accesoAsigglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				global.get $f64auxTripla
				global.set $globalAt1V2
			)
			(else
				global.get $accesoAsigglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						global.get $f64auxTripla
						global.set $globalAt1V3
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
i32.const 2389
i32.const 53
call $log
i32.const 1
global.set $accesoglobalAt1
global.get $accesoglobalAt1
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt1V1
		global.set $f64auxTripla
	)
	(else
		global.get $accesoglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt1V2
				global.set $f64auxTripla
			)
			(else
				global.get $accesoglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt1V3
						global.set $f64auxTripla
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
global.get $f64auxTripla
call $console_log_f64
i32.const 1
global.set $accesoAsigglobalAt1
i32.const 3
i32.const 1
i32.sub
global.set $AUXOVERFLOW
global.get $AUXOVERFLOW
i32.const 0
i32.lt_s
(if
	(then
		i32.const 101
		i32.const 76
		call $log
		call $exit
	)
)
global.get $AUXOVERFLOW
global.set $accesoglobalAt2
global.get $accesoglobalAt2
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt2V1
		global.set $f64auxTripla
	)
	(else
		global.get $accesoglobalAt2
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt2V2
				global.set $f64auxTripla
			)
			(else
				global.get $accesoglobalAt2
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt2V3
						global.set $f64auxTripla
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
global.get $f64auxTripla
f64.const 2.0000000000000000e-23
f64.div
global.set $f64auxTripla
global.get $accesoAsigglobalAt1
i32.const 1
i32.eq
(if
	(then
		global.get $f64auxTripla
		global.set $globalAt1V1
	)
	(else
		global.get $accesoAsigglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				global.get $f64auxTripla
				global.set $globalAt1V2
			)
			(else
				global.get $accesoAsigglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						global.get $f64auxTripla
						global.set $globalAt1V3
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
i32.const 1845
i32.const 53
call $log
i32.const 1
global.set $accesoglobalAt1
global.get $accesoglobalAt1
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt1V1
		global.set $f64auxTripla
	)
	(else
		global.get $accesoglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt1V2
				global.set $f64auxTripla
			)
			(else
				global.get $accesoglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt1V3
						global.set $f64auxTripla
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
global.get $f64auxTripla
call $console_log_f64
i32.const 1
global.set $accesoAsigglobalAt1
i32.const 3
i32.const 1
i32.sub
global.set $AUXOVERFLOW
global.get $AUXOVERFLOW
i32.const 0
i32.lt_s
(if
	(then
		i32.const 101
		i32.const 76
		call $log
		call $exit
	)
)
global.get $AUXOVERFLOW
global.set $accesoglobalAt2
global.get $accesoglobalAt2
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt2V1
		global.set $f64auxTripla
	)
	(else
		global.get $accesoglobalAt2
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt2V2
				global.set $f64auxTripla
			)
			(else
				global.get $accesoglobalAt2
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt2V3
						global.set $f64auxTripla
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
global.get $f64auxTripla
f64.const 2.0000000000000000e-23
f64.add
global.set $f64auxTripla
global.get $accesoAsigglobalAt1
i32.const 1
i32.eq
(if
	(then
		global.get $f64auxTripla
		global.set $globalAt1V1
	)
	(else
		global.get $accesoAsigglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				global.get $f64auxTripla
				global.set $globalAt1V2
			)
			(else
				global.get $accesoAsigglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						global.get $f64auxTripla
						global.set $globalAt1V3
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
i32.const 1034
i32.const 54
call $log
i32.const 1
global.set $accesoglobalAt1
global.get $accesoglobalAt1
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt1V1
		global.set $f64auxTripla
	)
	(else
		global.get $accesoglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt1V2
				global.set $f64auxTripla
			)
			(else
				global.get $accesoglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt1V3
						global.set $f64auxTripla
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
global.get $f64auxTripla
call $console_log_f64
i32.const 1
global.set $accesoAsigglobalAt1
i32.const 3
i32.const 1
i32.sub
global.set $AUXOVERFLOW
global.get $AUXOVERFLOW
i32.const 0
i32.lt_s
(if
	(then
		i32.const 101
		i32.const 76
		call $log
		call $exit
	)
)
global.get $AUXOVERFLOW
global.set $accesoglobalAt2
global.get $accesoglobalAt2
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt2V1
		global.set $f64auxTripla
	)
	(else
		global.get $accesoglobalAt2
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt2V2
				global.set $f64auxTripla
			)
			(else
				global.get $accesoglobalAt2
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt2V3
						global.set $f64auxTripla
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
global.get $f64auxTripla
f64.const 2.0000000000000000e-23
f64.sub
global.set $f64auxTripla
global.get $accesoAsigglobalAt1
i32.const 1
i32.eq
(if
	(then
		global.get $f64auxTripla
		global.set $globalAt1V1
	)
	(else
		global.get $accesoAsigglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				global.get $f64auxTripla
				global.set $globalAt1V2
			)
			(else
				global.get $accesoAsigglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						global.get $f64auxTripla
						global.set $globalAt1V3
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
i32.const 1326
i32.const 34
call $log
i32.const 1
global.set $accesoglobalAt1
global.get $accesoglobalAt1
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt1V1
		global.set $f64auxTripla
	)
	(else
		global.get $accesoglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt1V2
				global.set $f64auxTripla
			)
			(else
				global.get $accesoglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt1V3
						global.set $f64auxTripla
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
global.get $f64auxTripla
call $console_log_f64
i32.const 1
global.set $accesoAsigglobalAt2
global.get $accesoAsigglobalAt2
i32.const 1
i32.eq
(if
	(then
		f64.const 1.0000000000000000e+00
		global.set $globalAt2V1
	)
	(else
		global.get $accesoAsigglobalAt2
		i32.const 2
		i32.eq
		(if
			(then
				f64.const 1.0000000000000000e+00
				global.set $globalAt2V2
			)
			(else
				global.get $accesoAsigglobalAt2
				i32.const 3
				i32.eq
				(if
					(then
						f64.const 1.0000000000000000e+00
						global.set $globalAt2V3
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
i32.const 2
global.set $accesoAsigglobalAt2
global.get $accesoAsigglobalAt2
i32.const 1
i32.eq
(if
	(then
		f64.const 2.1300000000000000e-01
		global.set $globalAt2V1
	)
	(else
		global.get $accesoAsigglobalAt2
		i32.const 2
		i32.eq
		(if
			(then
				f64.const 2.1300000000000000e-01
				global.set $globalAt2V2
			)
			(else
				global.get $accesoAsigglobalAt2
				i32.const 3
				i32.eq
				(if
					(then
						f64.const 2.1300000000000000e-01
						global.set $globalAt2V3
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
i32.const 3
global.set $accesoAsigglobalAt2
global.get $accesoAsigglobalAt2
i32.const 1
i32.eq
(if
	(then
		f64.const 5.1000000000000000e+01
		global.set $globalAt2V1
	)
	(else
		global.get $accesoAsigglobalAt2
		i32.const 2
		i32.eq
		(if
			(then
				f64.const 5.1000000000000000e+01
				global.set $globalAt2V2
			)
			(else
				global.get $accesoAsigglobalAt2
				i32.const 3
				i32.eq
				(if
					(then
						f64.const 5.1000000000000000e+01
						global.set $globalAt2V3
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
i32.const 1734
i32.const 23
call $log
global.get $globalAt2V1
call $console_log_f64
global.get $globalAt2V2
call $console_log_f64
global.get $globalAt2V3
call $console_log_f64
global.get $globalAt2V1
global.get $globalAt2V1
f64.add
global.set $AUX1V1f64
global.get $globalAt2V2
global.get $globalAt2V2
f64.add
global.set $AUX1V2f64
global.get $globalAt2V3
global.get $globalAt2V3
f64.add
global.set $AUX1V3f64
global.get $AUX1V1f64
global.set $globalAt3V1
global.get $AUX1V2f64
global.set $globalAt3V2
global.get $AUX1V3f64
global.set $globalAt3V3
i32.const 2177
i32.const 23
call $log
global.get $globalAt3V1
call $console_log_f64
global.get $globalAt3V2
call $console_log_f64
global.get $globalAt3V3
call $console_log_f64
global.get $globalAt2V1
global.get $globalAt3V1
f64.add
global.set $AUX1V1f64
global.get $globalAt2V2
global.get $globalAt3V2
f64.add
global.set $AUX1V2f64
global.get $globalAt2V3
global.get $globalAt3V3
f64.add
global.set $AUX1V3f64
global.get $AUX1V1f64
global.set $globalAt1V1
global.get $AUX1V2f64
global.set $globalAt1V2
global.get $AUX1V3f64
global.set $globalAt1V3
i32.const 1581
i32.const 37
call $log
global.get $globalAt1V1
call $console_log_f64
global.get $globalAt1V2
call $console_log_f64
global.get $globalAt1V3
call $console_log_f64
global.get $globalAt2V1
global.get $globalAt3V1
f64.sub
global.set $AUX1V1f64
global.get $globalAt2V2
global.get $globalAt3V2
f64.sub
global.set $AUX1V2f64
global.get $globalAt2V3
global.get $globalAt3V3
f64.sub
global.set $AUX1V3f64
global.get $AUX1V1f64
global.set $globalAt1V1
global.get $AUX1V2f64
global.set $globalAt1V2
global.get $AUX1V3f64
global.set $globalAt1V3
i32.const 2492
i32.const 38
call $log
global.get $globalAt1V1
call $console_log_f64
global.get $globalAt1V2
call $console_log_f64
global.get $globalAt1V3
call $console_log_f64
global.get $globalAt2V1
global.get $globalAt3V1
f64.mul
global.set $AUX1V1f64
global.get $globalAt2V2
global.get $globalAt3V2
f64.mul
global.set $AUX1V2f64
global.get $globalAt2V3
global.get $globalAt3V3
f64.mul
global.set $AUX1V3f64
global.get $AUX1V1f64
global.set $globalAt1V1
global.get $AUX1V2f64
global.set $globalAt1V2
global.get $AUX1V3f64
global.set $globalAt1V3
i32.const 2449
i32.const 36
call $log
global.get $globalAt1V1
call $console_log_f64
global.get $globalAt1V2
call $console_log_f64
global.get $globalAt1V3
call $console_log_f64
global.get $globalAt2V1
global.get $globalAt3V1
f64.div
global.set $AUX1V1f64
global.get $globalAt2V2
global.get $globalAt3V2
f64.div
global.set $AUX1V2f64
global.get $globalAt2V3
global.get $globalAt3V3
f64.div
global.set $AUX1V3f64
global.get $AUX1V1f64
global.set $globalAt1V1
global.get $AUX1V2f64
global.set $globalAt1V2
global.get $AUX1V3f64
global.set $globalAt1V3
i32.const 853
i32.const 36
call $log
global.get $globalAt1V1
call $console_log_f64
global.get $globalAt1V2
call $console_log_f64
global.get $globalAt1V3
call $console_log_f64
f64.const 2.3000000000000000e+00
global.set $globalAu
i32.const 3
global.set $globalAa
i32.const 1678
i32.const 49
call $log
i32.const 1367
i32.const 43
call $log
i32.const 2
global.get $globalAa
i32.mul
global.set $globalAa
i32.const 1625
i32.const 15
call $log
global.get $globalAa
call $console_log_i32
i32.const 7
global.get $globalAa
i32.sub
global.set $AUXOVERFLOW
global.get $AUXOVERFLOW
i32.const 0
i32.lt_s
(if
	(then
		i32.const 101
		i32.const 76
		call $log
		call $exit
	)
)
global.get $AUXOVERFLOW
global.set $globalAa
i32.const 1511
i32.const 17
call $log
global.get $globalAa
call $console_log_i32
i32.const 2
global.get $globalAa
i32.div_u
global.set $globalAa
i32.const 1237
i32.const 15
call $log
global.get $globalAa
call $console_log_i32
i32.const 2
global.get $globalAa
i32.add
global.set $AUXOVERFLOW
global.get $AUXOVERFLOW
i32.const 0
i32.lt_s
(if
	(then
		i32.const 177
		i32.const 75
		call $log
		call $exit
	)
)
global.get $AUXOVERFLOW
global.set $globalAa
i32.const 1764
i32.const 16
call $log
global.get $globalAa
call $console_log_i32
f64.const 2.0000000000000000e+00
global.get $globalAu
f64.mul
global.set $globalAu
i32.const 677
i32.const 18
call $log
global.get $globalAu
call $console_log_f64
f64.const 2.0000000000000000e+00
global.get $globalAu
f64.sub
global.set $globalAu
i32.const 598
i32.const 20
call $log
global.get $globalAu
call $console_log_f64
f64.const 2.0000000000000000e+00
global.get $globalAu
f64.div
global.set $globalAu
i32.const 2152
i32.const 18
call $log
global.get $globalAu
call $console_log_f64
f64.const 2.0000000000000000e+00
global.get $globalAu
f64.add
global.set $globalAu
i32.const 1966
i32.const 19
call $log
global.get $globalAu
call $console_log_f64
i32.const 1678
i32.const 49
call $log
i32.const 1417
i32.const 50
call $log
i32.const 1
global.set $accesoAsigglobalAathos
global.get $accesoAsigglobalAathos
i32.const 1
i32.eq
(if
	(then
		i32.const 1
		global.set $globalAathosV1
	)
	(else
		global.get $accesoAsigglobalAathos
		i32.const 2
		i32.eq
		(if
			(then
				i32.const 1
				global.set $globalAathosV2
			)
			(else
				global.get $accesoAsigglobalAathos
				i32.const 3
				i32.eq
				(if
					(then
						i32.const 1
						global.set $globalAathosV3
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
i32.const 2
global.set $accesoAsigglobalAathos
global.get $accesoAsigglobalAathos
i32.const 1
i32.eq
(if
	(then
		i32.const 2
		global.set $globalAathosV1
	)
	(else
		global.get $accesoAsigglobalAathos
		i32.const 2
		i32.eq
		(if
			(then
				i32.const 2
				global.set $globalAathosV2
			)
			(else
				global.get $accesoAsigglobalAathos
				i32.const 3
				i32.eq
				(if
					(then
						i32.const 2
						global.set $globalAathosV3
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
i32.const 3
global.set $accesoAsigglobalAathos
global.get $accesoAsigglobalAathos
i32.const 1
i32.eq
(if
	(then
		i32.const 3
		global.set $globalAathosV1
	)
	(else
		global.get $accesoAsigglobalAathos
		i32.const 2
		i32.eq
		(if
			(then
				i32.const 3
				global.set $globalAathosV2
			)
			(else
				global.get $accesoAsigglobalAathos
				i32.const 3
				i32.eq
				(if
					(then
						i32.const 3
						global.set $globalAathosV3
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
i32.const 1185
i32.const 16
call $log
global.get $globalAathosV1
call $console_log_i32
global.get $globalAathosV2
call $console_log_i32
global.get $globalAathosV3
call $console_log_i32
i32.const 1
global.set $accesoAsigglobalAathos
i32.const 3
global.set $accesoglobalAathos
global.get $accesoglobalAathos
i32.const 1
i32.eq
(if
	(then
		global.get $globalAathosV1
		global.set $i32auxTripla
	)
	(else
		global.get $accesoglobalAathos
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAathosV2
				global.set $i32auxTripla
			)
			(else
				global.get $accesoglobalAathos
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAathosV3
						global.set $i32auxTripla
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
global.get $i32auxTripla
global.set $accesoglobalAathos
global.get $accesoglobalAathos
i32.const 1
i32.eq
(if
	(then
		global.get $globalAathosV1
		global.set $i32auxTripla
	)
	(else
		global.get $accesoglobalAathos
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAathosV2
				global.set $i32auxTripla
			)
			(else
				global.get $accesoglobalAathos
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAathosV3
						global.set $i32auxTripla
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
global.get $i32auxTripla
global.set $accesoglobalAathos
global.get $accesoglobalAathos
i32.const 1
i32.eq
(if
	(then
		global.get $globalAathosV1
		global.set $i32auxTripla
	)
	(else
		global.get $accesoglobalAathos
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAathosV2
				global.set $i32auxTripla
			)
			(else
				global.get $accesoglobalAathos
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAathosV3
						global.set $i32auxTripla
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
i32.const 1
global.set $accesoglobalAathos
global.get $accesoglobalAathos
i32.const 1
i32.eq
(if
	(then
		global.get $globalAathosV1
		global.set $i32aux2Tripla
	)
	(else
		global.get $accesoglobalAathos
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAathosV2
				global.set $i32aux2Tripla
			)
			(else
				global.get $accesoglobalAathos
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAathosV3
						global.set $i32aux2Tripla
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
i32.const 3
global.set $accesoglobalAathos
global.get $accesoglobalAathos
i32.const 1
i32.eq
(if
	(then
		global.get $globalAathosV1
		global.set $i32aux3Tripla
	)
	(else
		global.get $accesoglobalAathos
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAathosV2
				global.set $i32aux3Tripla
			)
			(else
				global.get $accesoglobalAathos
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAathosV3
						global.set $i32aux3Tripla
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
global.get $i32aux2Tripla
global.get $i32aux3Tripla
i32.mul
global.get $i32auxTripla
i32.add
global.set $AUXOVERFLOW
global.get $AUXOVERFLOW
i32.const 0
i32.lt_s
(if
	(then
		i32.const 177
		i32.const 75
		call $log
		call $exit
	)
)
global.get $AUXOVERFLOW
global.set $i32auxTripla
global.get $accesoAsigglobalAathos
i32.const 1
i32.eq
(if
	(then
		global.get $i32auxTripla
		global.set $globalAathosV1
	)
	(else
		global.get $accesoAsigglobalAathos
		i32.const 2
		i32.eq
		(if
			(then
				global.get $i32auxTripla
				global.set $globalAathosV2
			)
			(else
				global.get $accesoAsigglobalAathos
				i32.const 3
				i32.eq
				(if
					(then
						global.get $i32auxTripla
						global.set $globalAathosV3
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
i32.const 762
i32.const 84
call $log
global.get $globalAt1V1
call $console_log_f64
global.get $globalAt1V2
call $console_log_f64
global.get $globalAt1V3
call $console_log_f64
i32.const 5
i32.const 0
global.set $funcionLlamadora
call $globalAfun2
i32.const 0
global.set $funcionLlamadora
call $globalAfun1
global.set $accesoAsigglobalAporthos
global.get $globalAd
f64.const 0
f64.lt
(if
	(then
		i32.const 363
		i32.const 98
		call $log
		call $exit
	)
)
global.get $globalAd
i32.trunc_f64_u
i32.const 0
global.set $funcionLlamadora
call $globalAfun1
global.set $accesoglobalAathos
global.get $accesoglobalAathos
i32.const 1
i32.eq
(if
	(then
		global.get $globalAathosV1
		global.set $i32auxTripla
	)
	(else
		global.get $accesoglobalAathos
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAathosV2
				global.set $i32auxTripla
			)
			(else
				global.get $accesoglobalAathos
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAathosV3
						global.set $i32auxTripla
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
global.get $accesoAsigglobalAporthos
i32.const 1
i32.eq
(if
	(then
		global.get $i32auxTripla
		global.set $globalAporthosV1
	)
	(else
		global.get $accesoAsigglobalAporthos
		i32.const 2
		i32.eq
		(if
			(then
				global.get $i32auxTripla
				global.set $globalAporthosV2
			)
			(else
				global.get $accesoAsigglobalAporthos
				i32.const 3
				i32.eq
				(if
					(then
						global.get $i32auxTripla
						global.set $globalAporthosV3
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
i32.const 2017
i32.const 77
call $log
global.get $globalAt2V1
call $console_log_f64
global.get $globalAt2V2
call $console_log_f64
global.get $globalAt2V3
call $console_log_f64
i32.const 3
global.set $accesoAsigglobalAporthos
i32.const 3
global.set $accesoglobalAporthos
global.get $accesoglobalAporthos
i32.const 1
i32.eq
(if
	(then
		global.get $globalAporthosV1
		global.set $i32auxTripla
	)
	(else
		global.get $accesoglobalAporthos
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAporthosV2
				global.set $i32auxTripla
			)
			(else
				global.get $accesoglobalAporthos
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAporthosV3
						global.set $i32auxTripla
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
global.get $accesoAsigglobalAporthos
i32.const 1
i32.eq
(if
	(then
		global.get $i32auxTripla
		global.set $globalAporthosV1
	)
	(else
		global.get $accesoAsigglobalAporthos
		i32.const 2
		i32.eq
		(if
			(then
				global.get $i32auxTripla
				global.set $globalAporthosV2
			)
			(else
				global.get $accesoAsigglobalAporthos
				i32.const 3
				i32.eq
				(if
					(then
						global.get $i32auxTripla
						global.set $globalAporthosV3
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
i32.const 1535
i32.const 39
call $log
global.get $globalAporthosV1
call $console_log_i32
global.get $globalAporthosV2
call $console_log_i32
global.get $globalAporthosV3
call $console_log_i32
i32.const 1
global.set $accesoAsigglobalAathos
i32.const 1
global.set $accesoglobalAathos
global.get $accesoglobalAathos
i32.const 1
i32.eq
(if
	(then
		global.get $globalAathosV1
		global.set $i32auxTripla
	)
	(else
		global.get $accesoglobalAathos
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAathosV2
				global.set $i32auxTripla
			)
			(else
				global.get $accesoglobalAathos
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAathosV3
						global.set $i32auxTripla
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
global.get $i32auxTripla
i32.const 1
i32.add
global.set $AUXOVERFLOW
global.get $AUXOVERFLOW
i32.const 0
i32.lt_s
(if
	(then
		i32.const 177
		i32.const 75
		call $log
		call $exit
	)
)
global.get $AUXOVERFLOW
global.set $i32auxTripla
global.get $accesoAsigglobalAathos
i32.const 1
i32.eq
(if
	(then
		global.get $i32auxTripla
		global.set $globalAathosV1
	)
	(else
		global.get $accesoAsigglobalAathos
		i32.const 2
		i32.eq
		(if
			(then
				global.get $i32auxTripla
				global.set $globalAathosV2
			)
			(else
				global.get $accesoAsigglobalAathos
				i32.const 3
				i32.eq
				(if
					(then
						global.get $i32auxTripla
						global.set $globalAathosV3
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
global.get $globalAathosV1
global.get $globalAathosV1
i32.mul
global.set $AUX1V1i32
global.get $globalAathosV2
global.get $globalAathosV2
i32.mul
global.set $AUX1V2i32
global.get $globalAathosV3
global.get $globalAathosV3
i32.mul
global.set $AUX1V3i32
global.get $AUX1V1i32
global.get $globalAathosV1
i32.add
global.set $AUX1V1i32
global.get $AUX1V2i32
global.get $globalAathosV2
i32.add
global.set $AUX1V2i32
global.get $AUX1V3i32
global.get $globalAathosV3
i32.add
global.set $AUX1V3i32
global.get $AUX1V1i32
global.set $globalAathosV1
global.get $AUX1V2i32
global.set $globalAathosV2
global.get $AUX1V3i32
global.set $globalAathosV3
i32.const 461
i32.const 82
call $log
global.get $globalAathosV1
call $console_log_i32
global.get $globalAathosV2
call $console_log_i32
global.get $globalAathosV3
call $console_log_i32
global.get $globalAathosV1
global.get $globalAathosV1
i32.add
global.set $AUX1V1i32
global.get $globalAathosV2
global.get $globalAathosV2
i32.add
global.set $AUX1V2i32
global.get $globalAathosV3
global.get $globalAathosV3
i32.add
global.set $AUX1V3i32
global.get $globalAporthosV1
global.get $globalAporthosV1
i32.add
global.set $AUX3V1i32
global.get $globalAporthosV2
global.get $globalAporthosV2
i32.add
global.set $AUX3V2i32
global.get $globalAporthosV3
global.get $globalAporthosV3
i32.add
global.set $AUX3V3i32
global.get $AUX3V1i32
global.get $globalAporthosV1
i32.sub
global.set $AUX3V1i32
global.get $AUX3V2i32
global.get $globalAporthosV2
i32.sub
global.set $AUX3V2i32
global.get $AUX3V3i32
global.get $globalAporthosV3
i32.sub
global.set $AUX3V3i32
global.get $AUX1V1i32
global.get $AUX3V1i32
i32.gt_u
local.set $comp222V1
global.get $AUX1V2i32
global.get $AUX3V2i32
i32.gt_u
local.set $comp222V2
global.get $AUX1V3i32
global.get $AUX3V3i32
i32.gt_u
local.set $comp222V3
local.get $comp222V3
local.get $comp222V2
i32.eq
local.get $comp222V1
i32.eq
local.set $comp222
i32.const 1
global.set $accesoglobalAathos
global.get $accesoglobalAathos
i32.const 1
i32.eq
(if
	(then
		global.get $globalAathosV1
		global.set $i32auxTripla
	)
	(else
		global.get $accesoglobalAathos
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAathosV2
				global.set $i32auxTripla
			)
			(else
				global.get $accesoglobalAathos
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAathosV3
						global.set $i32auxTripla
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
global.get $i32auxTripla
i32.const 2
i32.add
global.set $AUXOVERFLOW
global.get $AUXOVERFLOW
i32.const 0
i32.lt_s
(if
	(then
		i32.const 177
		i32.const 75
		call $log
		call $exit
	)
)
global.get $AUXOVERFLOW
i32.const 1
global.set $accesoglobalAaramis
global.get $accesoglobalAaramis
i32.const 1
i32.eq
(if
	(then
		global.get $globalAaramisV1
		global.set $i32auxTripla
	)
	(else
		global.get $accesoglobalAaramis
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAaramisV2
				global.set $i32auxTripla
			)
			(else
				global.get $accesoglobalAaramis
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAaramisV3
						global.set $i32auxTripla
					)
					(else
						i32.const 318
						i32.const 45
						call $log
						call $exit
					)
				)
			)
		)
	)
)
global.get $i32auxTripla
i32.const 1
i32.add
global.set $AUXOVERFLOW
global.get $AUXOVERFLOW
i32.const 0
i32.lt_s
(if
	(then
		i32.const 177
		i32.const 75
		call $log
		call $exit
	)
)
global.get $AUXOVERFLOW
i32.gt_u
local.set $comp225
global.get $globalAathosV1
global.get $globalAporthosV1
i32.gt_u
local.set $comp226V1
global.get $globalAathosV2
global.get $globalAporthosV2
i32.gt_u
local.set $comp226V2
global.get $globalAathosV3
global.get $globalAporthosV3
i32.gt_u
local.set $comp226V3
local.get $comp226V3
local.get $comp226V2
i32.eq
local.get $comp226V1
i32.eq
local.set $comp226
local.get $comp222
local.get $comp225
i32.and
local.get $comp226
i32.and
(if
	(then
		i32.const 1296
		i32.const 23
		call $log
		i32.const 1474
		i32.const 30
		call $log
		global.get $globalAathosV1
		global.get $globalAathosV1
		i32.add
		global.set $AUX1V1i32
		global.get $globalAathosV2
		global.get $globalAathosV2
		i32.add
		global.set $AUX1V2i32
		global.get $globalAathosV3
		global.get $globalAathosV3
		i32.add
		global.set $AUX1V3i32
		global.get $AUX1V1i32
		call $console_log_i32
		global.get $AUX1V2i32
		call $console_log_i32
		global.get $AUX1V3i32
		call $console_log_i32
		i32.const 1095
		i32.const 30
		call $log
		i32.const 1
		global.set $accesoglobalAathos
		global.get $accesoglobalAathos
		i32.const 1
		i32.eq
		(if
			(then
				global.get $globalAathosV1
				global.set $i32auxTripla
			)
			(else
				global.get $accesoglobalAathos
				i32.const 2
				i32.eq
				(if
					(then
						global.get $globalAathosV2
						global.set $i32auxTripla
					)
					(else
						global.get $accesoglobalAathos
						i32.const 3
						i32.eq
						(if
							(then
								global.get $globalAathosV3
								global.set $i32auxTripla
							)
							(else
								i32.const 318
								i32.const 45
								call $log
								call $exit
							)
						)
					)
				)
			)
		)
	global.get $i32auxTripla
	i32.const 2
	i32.add
	global.set $AUXOVERFLOW
	global.get $AUXOVERFLOW
	i32.const 0
	i32.lt_s
	(if
		(then
			i32.const 177
			i32.const 75
			call $log
			call $exit
		)
	)
	global.get $AUXOVERFLOW
	call $console_log_i32
	i32.const 625
	i32.const 22
	call $log
	global.get $globalAathosV1
	call $console_log_i32
	global.get $globalAathosV2
	call $console_log_i32
	global.get $globalAathosV3
	call $console_log_i32
	i32.const 2379
	i32.const 3
	call $log
	i32.const 947
	i32.const 21
	call $log
	i32.const 2101
	i32.const 44
	call $log
	global.get $globalAporthosV1
	global.get $globalAporthosV1
	i32.add
	global.set $AUX1V1i32
	global.get $globalAporthosV2
	global.get $globalAporthosV2
	i32.add
	global.set $AUX1V2i32
	global.get $globalAporthosV3
	global.get $globalAporthosV3
	i32.add
	global.set $AUX1V3i32
	global.get $AUX1V1i32
	global.get $globalAporthosV1
	i32.sub
	global.set $AUX1V1i32
	global.get $AUX1V2i32
	global.get $globalAporthosV2
	i32.sub
	global.set $AUX1V2i32
	global.get $AUX1V3i32
	global.get $globalAporthosV3
	i32.sub
	global.set $AUX1V3i32
	global.get $AUX1V1i32
	call $console_log_i32
	global.get $AUX1V2i32
	call $console_log_i32
	global.get $AUX1V3i32
	call $console_log_i32
	i32.const 2207
	i32.const 31
	call $log
	i32.const 1
	global.set $accesoglobalAaramis
	global.get $accesoglobalAaramis
	i32.const 1
	i32.eq
	(if
		(then
			global.get $globalAaramisV1
			global.set $i32auxTripla
		)
		(else
			global.get $accesoglobalAaramis
			i32.const 2
			i32.eq
			(if
				(then
					global.get $globalAaramisV2
					global.set $i32auxTripla
				)
				(else
					global.get $accesoglobalAaramis
					i32.const 3
					i32.eq
					(if
						(then
							global.get $globalAaramisV3
							global.set $i32auxTripla
						)
						(else
							i32.const 318
							i32.const 45
							call $log
							call $exit
						)
					)
				)
			)
		)
	)
global.get $i32auxTripla
i32.const 1
i32.add
global.set $AUXOVERFLOW
global.get $AUXOVERFLOW
i32.const 0
i32.lt_s
(if
	(then
		i32.const 177
		i32.const 75
		call $log
		call $exit
	)
)
global.get $AUXOVERFLOW
call $console_log_i32
i32.const 1647
i32.const 24
call $log
global.get $globalAporthosV1
call $console_log_i32
global.get $globalAporthosV2
call $console_log_i32
global.get $globalAporthosV3
call $console_log_i32
)
(else
	i32.const 975
	i32.const 23
	call $log
)
)
)
	(export "main" (func $main))
)


