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

	(global $accesoAsigglobalAt2 (mut i32) (i32.const 1))
	(global $accesoglobalAt2 (mut i32) (i32.const 1))


(func $main
(local $comp2V1 i32)
(local $comp2V2 i32)
(local $comp2V3 i32)

	i32.const 1
	global.set $accesoAsigglobalAt2
	global.get $accesoAsigglobalAt2
	i32.const 1
	i32.eq
	(if
		(then
			i32.const 1
			global.set $globalAt2V1
		)
		(else
			global.get $accesoAsigglobalAt2
			i32.const 2
			i32.eq
			(if
				(then
					i32.const 1
					global.set $globalAt2V2
				)
				(else
					global.get $accesoAsigglobalAt2
					i32.const 3
					i32.eq
					(if
						(then
							i32.const 1
							global.set $globalAt2V3
						)
						(else
							i32.const 243
							i32.const 45
							call $log
							call $exit
						)
					)
				)
			)
		)
	)
global.get $globalAt1V1
global.get $globalAt2V1
i32.ge_u
local.set $comp2V1
global.get $globalAt1V2
global.get $globalAt2V2
i32.ge_u
local.set $comp2V2
global.get $globalAt1V3
global.get $globalAt2V3
i32.ge_u
local.set $comp2V3
local.get $comp2V3
local.get $comp2V2
i32.eq
local.get $comp2V1
i32.eq
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


