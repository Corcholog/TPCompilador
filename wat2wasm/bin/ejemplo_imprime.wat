(module
(import "console" "log" (func $log (param i32 i32)))
(import "js" "mem" (memory 1))
(import "env" "console_log" (func $console_log_i32 (param i32)))
(import "env" "console_log" (func $console_log_f64 (param f64)))
(import "env" "exit" (func $exit))
(global $globalAd (mut f64)(f64.const 0))
(global $globalAx (mut i32)(i32.const 0))
(global $funcionLlamadora (mut i32) (i32.const 0))
(global $AUXNEG (mut i32) (i32.const 0))
(global $f64auxTripla (mut f64) (f64.const 0))
(global $i32auxTripla (mut i32) (i32.const 0))
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
(data (i32.const 243)"Error en ejecucion: indice fuera de rango.")
(data (i32.const 288)"Error en ejecucion: se intenta realizar una conversion de flotante negativo a entero sin signo.")
(data (i32.const 177)"Error en ejecucion: se realizo una recursion sobre una funcion.")

(global $globalAfuncion1Ak (mut i32) (i32.const 0))

( func $globalAfuncion1 (param $globalAfuncion1Ak i32) (result i32)
	(local $globalAfuncion1retorno i32)
local.get $globalAfuncion1Ak
global.set $globalAfuncion1Ak
	i32.const 1
	global.get $funcionLlamadora
	i32.eq
	(if
		(then
			i32.const 177
			i32.const 66
			call $log
			call $exit
		)
	)
	i32.const 0
	local.set $globalAfuncion1retorno
	global.get $globalAfuncion1Ak
	local.set $globalAfuncion1retorno
	local.get $globalAfuncion1retorno
	return
	local.get $globalAfuncion1retorno
)


(func $main

	f64.const -2.4000000000000000e+00
	global.set $globalAd
	global.get $globalAd
	f64.const 0
	f64.lt
	(if
		(then
			i32.const 288
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
	global.set $globalAx
)
	(export "main" (func $main))
)


