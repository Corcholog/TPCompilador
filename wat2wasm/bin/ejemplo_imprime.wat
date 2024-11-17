(module
(import "console" "log" (func $log (param i32 i32)))
(import "js" "mem" (memory 1))
(import "env" "console_log" (func $console_log_i32 (param i32)))
(import "env" "console_log" (func $console_log_f64 (param f64)))
(import "env" "exit" (func $exit))
(global $globalAa (mut f64)(f64.const 0))
(global $globalAb (mut f64)(f64.const 0))
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
(data (i32.const 174)"Error en ejecucion: se realizo una recursion sobre una funcion.")
(data (i32.const 237)"Error en ejecucion: indice fuera de rango.")
(data (i32.const 279)"Error en ejecucion: no se puede recursionar una funcion.")
(data (i32.const 335)"Error en ejecucion: se intenta realizar una conversion de flotante negativo a entero sin signo.")



(func $main

	f64.const 1.3000000000000000e+01
	global.set $globalAb
	f64.const 1.3000000000000000e-01
	global.set $globalAa
	global.get $globalAa
	global.get $globalAb
	f64.add
	call $console_log_f64
)
	(export "main" (func $main))
)


