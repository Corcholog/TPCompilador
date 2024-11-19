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
(data (i32.const 461) "hola")

	(global $accesoAsigglobalAt1 (mut i32) (i32.const 1))
	(global $accesoglobalAt1 (mut i32) (i32.const 1))


(func $main

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
			i32.const 15
			global.set $globalAt1V1
		)
		(else
			global.get $accesoAsigglobalAt1
			i32.const 2
			i32.eq
			(if
				(then
					i32.const 15
					global.set $globalAt1V2
				)
				(else
					global.get $accesoAsigglobalAt1
					i32.const 3
					i32.eq
					(if
						(then
							i32.const 15
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
	i32.const 461
	i32.const 4
	call $log
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
global.set $accesoAsigglobalAt1
global.get $accesoAsigglobalAt1
i32.const 1
i32.eq
(if
	(then
		i32.const 25
		global.set $globalAt1V1
	)
	(else
		global.get $accesoAsigglobalAt1
		i32.const 2
		i32.eq
		(if
			(then
				i32.const 25
				global.set $globalAt1V2
			)
			(else
				global.get $accesoAsigglobalAt1
				i32.const 3
				i32.eq
				(if
					(then
						i32.const 25
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
global.get $globalAt1V1
call $console_log_i32
global.get $globalAt1V2
call $console_log_i32
global.get $globalAt1V3
call $console_log_i32
)
	(export "main" (func $main))
)


