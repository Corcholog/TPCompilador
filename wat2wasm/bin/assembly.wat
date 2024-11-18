(module
(import "console" "log" (func $log (param i32 i32)))
(import "js" "mem" (memory 1))
(import "env" "console_log" (func $console_log_i32 (param i32)))
(import "env" "console_log" (func $console_log_f64 (param f64)))
(import "env" "exit" (func $exit))
(global $globalAt3V1 (mut i32)(i32.const 0))
(global $globalAt3V2 (mut i32)(i32.const 0))
(global $globalAt3V3 (mut i32)(i32.const 0))
(global $globalAt2V1 (mut i32)(i32.const 0))
(global $globalAt2V2 (mut i32)(i32.const 0))
(global $globalAt2V3 (mut i32)(i32.const 0))
(global $globalAt1V1 (mut i32)(i32.const 0))
(global $globalAt1V2 (mut i32)(i32.const 0))
(global $globalAt1V3 (mut i32)(i32.const 0))
(global $globalAd (mut f64)(f64.const 0))
(global $globalAa (mut i32)(i32.const 0))
(global $globalAb (mut i32)(i32.const 0))
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
(data (i32.const 101)"Error en ejecucion: El resultado de una operacion sin signo dio negativo.")
(data (i32.const 177)"Error en ejecucion: El resultado de una suma de enteros genero overflow.")
(data (i32.const 318)"Error en ejecucion: indice fuera de rango.")
(data (i32.const 363)"Error en ejecucion: se intenta realizar una conversion de flotante negativo a entero sin signo.")
(data (i32.const 252)"Error en ejecucion: se realizo una recursion sobre una funcion.")
(data (i32.const 461) "else")

(global $globalAfuncion2Aboca (mut i32) (i32.const 0))
(global $globalAfuncion1Aboca (mut i32) (i32.const 0))
	(global $accesoAsigglobalAt1 (mut i32) (i32.const 1))
	(global $accesoglobalAt1 (mut i32) (i32.const 1))
(global $accesoAsigglobalAt2 (mut i32) (i32.const 1))
(global $accesoglobalAt2 (mut i32) (i32.const 1))
(global $accesoglobalAt3 (mut i32) (i32.const 1))
(global $accesoAsigglobalAt3 (mut i32) (i32.const 1))

( func $globalAfuncion2 (param $globalAfuncion2Aboca i32) (result i32)
(local $globalAfuncion2retorno i32)
local.get $globalAfuncion2Aboca
global.set $globalAfuncion2Aboca
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
	local.set $globalAfuncion2retorno
	i32.const 1
	local.set $globalAfuncion2retorno
	local.get $globalAfuncion2retorno
	return
	local.get $globalAfuncion2retorno
)

( func $globalAfuncion1 (param $globalAfuncion1Aboca i32) (result i32)
(local $globalAfuncion1retorno i32)
local.get $globalAfuncion1Aboca
global.set $globalAfuncion1Aboca
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
	local.set $globalAfuncion1retorno
	global.get $globalAfuncion1Aboca
	call $console_log_i32
	i32.const 1
	local.set $globalAfuncion1retorno
	local.get $globalAfuncion1retorno
	return
	local.get $globalAfuncion1retorno
)


(func $main
(local $comp25V1 i32)
(local $comp25V2 i32)
(local $comp25V3 i32)
(local $comp25 i32)
(local $comp28 i32)
(local $comp34 i32)

	f64.const 1.0000000000000000e+00
	global.set $globalAd
	i32.const 1
	global.set $accesoAsigglobalAt1
	global.get $accesoAsigglobalAt1
	i32.const 1
	i32.eq
	(if
		(then
			i32.const 1
			global.set $globalAt1V1
		)
		(else
			global.get $accesoAsigglobalAt1
			i32.const 2
			i32.eq
			(if
				(then
					i32.const 1
					global.set $globalAt1V2
				)
				(else
					global.get $accesoAsigglobalAt1
					i32.const 3
					i32.eq
					(if
						(then
							i32.const 1
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
	i32.const 2
	global.set $accesoAsigglobalAt1
	global.get $accesoAsigglobalAt1
	i32.const 1
	i32.eq
	(if
		(then
			i32.const 2
			global.set $globalAt1V1
		)
		(else
			global.get $accesoAsigglobalAt1
			i32.const 2
			i32.eq
			(if
				(then
					i32.const 2
					global.set $globalAt1V2
				)
				(else
					global.get $accesoAsigglobalAt1
					i32.const 3
					i32.eq
					(if
						(then
							i32.const 2
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
	i32.const 3
	global.set $accesoAsigglobalAt1
	global.get $accesoAsigglobalAt1
	i32.const 1
	i32.eq
	(if
		(then
			i32.const 3
			global.set $globalAt1V1
		)
		(else
			global.get $accesoAsigglobalAt1
			i32.const 2
			i32.eq
			(if
				(then
					i32.const 3
					global.set $globalAt1V2
				)
				(else
					global.get $accesoAsigglobalAt1
					i32.const 3
					i32.eq
					(if
						(then
							i32.const 3
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
	i32.const 1
	global.set $accesoAsigglobalAt1
	i32.const 3
	global.set $accesoglobalAt1
	global.get $accesoglobalAt1
	i32.const 1
	i32.eq
	(if
		(then
			global.get $globalAt1V1
			global.set $i32auxTripla
		)
		(else
			global.get $accesoglobalAt1
			i32.const 2
			i32.eq
			(if
				(then
					global.get $globalAt1V2
					global.set $i32auxTripla
				)
				(else
					global.get $accesoglobalAt1
					i32.const 3
					i32.eq
					(if
						(then
							global.get $globalAt1V3
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
global.set $accesoglobalAt1
global.get $accesoglobalAt1
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt1V1
		global.set $i32auxTripla
	)
	(else
		global.get $accesoglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt1V2
				global.set $i32auxTripla
			)
			(else
				global.get $accesoglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt1V3
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
global.set $accesoglobalAt1
global.get $accesoglobalAt1
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt1V1
		global.set $i32auxTripla
	)
	(else
		global.get $accesoglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt1V2
				global.set $i32auxTripla
			)
			(else
				global.get $accesoglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt1V3
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
global.set $accesoglobalAt1
global.get $accesoglobalAt1
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt1V1
		global.set $i32aux2Tripla
	)
	(else
		global.get $accesoglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt1V2
				global.set $i32aux2Tripla
			)
			(else
				global.get $accesoglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt1V3
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
global.set $accesoglobalAt1
global.get $accesoglobalAt1
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt1V1
		global.set $i32aux3Tripla
	)
	(else
		global.get $accesoglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt1V2
				global.set $i32aux3Tripla
			)
			(else
				global.get $accesoglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt1V3
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
global.get $accesoAsigglobalAt1
i32.const 1
i32.eq
(if
	(then
		global.get $i32auxTripla
		global.set $globalAt1V1
	)
	(else
		global.get $accesoAsigglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				global.get $i32auxTripla
				global.set $globalAt1V2
			)
			(else
				global.get $accesoAsigglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						global.get $i32auxTripla
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
i32.const 5
i32.const 0
global.set $funcionLlamadora
call $globalAfuncion2
i32.const 0
global.set $funcionLlamadora
call $globalAfuncion1
global.set $accesoAsigglobalAt2
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
call $globalAfuncion1
global.set $accesoglobalAt1
global.get $accesoglobalAt1
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt1V1
		global.set $i32auxTripla
	)
	(else
		global.get $accesoglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt1V2
				global.set $i32auxTripla
			)
			(else
				global.get $accesoglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt1V3
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
global.get $accesoAsigglobalAt2
i32.const 1
i32.eq
(if
	(then
		global.get $i32auxTripla
		global.set $globalAt2V1
	)
	(else
		global.get $accesoAsigglobalAt2
		i32.const 2
		i32.eq
		(if
			(then
				global.get $i32auxTripla
				global.set $globalAt2V2
			)
			(else
				global.get $accesoAsigglobalAt2
				i32.const 3
				i32.eq
				(if
					(then
						global.get $i32auxTripla
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
global.get $globalAt2V1
call $console_log_i32
global.get $globalAt2V2
call $console_log_i32
global.get $globalAt2V3
call $console_log_i32
global.get $globalAt1V1
global.get $globalAt2V1
i32.gt_u
local.set $comp25V1
global.get $globalAt1V2
global.get $globalAt2V2
i32.gt_u
local.set $comp25V2
global.get $globalAt1V3
global.get $globalAt2V3
i32.gt_u
local.set $comp25V3
local.get $comp25V3
local.get $comp25V2
i32.eq
local.get $comp25V1
i32.eq
local.set $comp25
i32.const 1000
i32.const 0
global.set $funcionLlamadora
call $globalAfuncion1
global.set $accesoglobalAt1
global.get $accesoglobalAt1
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt1V1
		global.set $i32auxTripla
	)
	(else
		global.get $accesoglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt1V2
				global.set $i32auxTripla
			)
			(else
				global.get $accesoglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt1V3
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
global.set $accesoglobalAt1
global.get $accesoglobalAt1
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt1V1
		global.set $i32auxTripla
	)
	(else
		global.get $accesoglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt1V2
				global.set $i32auxTripla
			)
			(else
				global.get $accesoglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt1V3
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
i32.gt_u
local.set $comp28
i32.const 1
global.set $accesoglobalAt1
global.get $accesoglobalAt1
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt1V1
		global.set $i32auxTripla
	)
	(else
		global.get $accesoglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt1V2
				global.set $i32auxTripla
			)
			(else
				global.get $accesoglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt1V3
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
i32.const 5
i32.mul
i32.const 2
global.set $accesoglobalAt1
global.get $accesoglobalAt1
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt1V1
		global.set $i32auxTripla
	)
	(else
		global.get $accesoglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt1V2
				global.set $i32auxTripla
			)
			(else
				global.get $accesoglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt1V3
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
global.set $accesoglobalAt1
global.get $accesoglobalAt1
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt1V1
		global.set $i32auxTripla
	)
	(else
		global.get $accesoglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt1V2
				global.set $i32auxTripla
			)
			(else
				global.get $accesoglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt1V3
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
global.set $accesoglobalAt3
global.get $accesoglobalAt3
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt3V1
		global.set $i32auxTripla
	)
	(else
		global.get $accesoglobalAt3
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt3V2
				global.set $i32auxTripla
			)
			(else
				global.get $accesoglobalAt3
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt3V3
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
global.set $accesoglobalAt2
global.get $accesoglobalAt2
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt2V1
		global.set $i32auxTripla
	)
	(else
		global.get $accesoglobalAt2
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt2V2
				global.set $i32auxTripla
			)
			(else
				global.get $accesoglobalAt2
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt2V3
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
local.set $comp34
local.get $comp25
local.get $comp28
i32.and
local.get $comp34
i32.and
(if
	(then
		global.get $globalAt1V1
		call $console_log_i32
		global.get $globalAt1V2
		call $console_log_i32
		global.get $globalAt1V3
		call $console_log_i32
	)
	(else
		i32.const 461
		i32.const 4
		call $log
	)
)
)
	(export "main" (func $main))
)


