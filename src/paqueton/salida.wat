(module
(import "console" "log" (func $log (param i32 i32)))
(import "js" "mem" (memory 1))
(import "env" "console_log" (func $console_log_i32 (param i32)))
(import "env" "console_log" (func $console_log_f64 (param f64)))
(import "env" "exit" (func $exit))
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
(data (i32.const 386) " goooooood amimir ")



(func $main
	(local $comp1 i32)
	(local $comp2 i32)
	(local $comp3 i32)

	f64.const 2,0000000000000000e+00
	f64.const 2,0000000000000000e+00
	f64.le
	local.set $comp1
	local.get $comp1
	i32.const 3
	i32.const 3
	i32.le_u
	local.set $comp2
	local.get $comp2
	f64.const -2,0000000000000000e-13
	f64.const -2,0000000000000000e-13
	f64.le
	local.set $comp3
	local.get $comp3
	local.get $comp1
	local.get $comp2
	i32.and
	local.get $comp3
	i32.and
	(if
		(then
			i32.const 386
			i32.const 18
			call $log
		)
	)
)
	(export "main" (func $main))
)


