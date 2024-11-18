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
(global $globalAa (mut i32)(i32.const 0))
(global $globalAb (mut i32)(i32.const 0))
(global $funcionLlamadora (mut i32) (i32.const 0))
(global $AUXOVERFLOW (mut i32) (i32.const 0))
(global $f64auxTripla (mut f64) (f64.const 0))
(global $i32auxTripla (mut i32) (i32.const 0))
(global $f64aux2Tripla (mut f64) (f64.const 0))
(global $i32aux2Tripla (mut i32) (i32.const 0))
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

	(global $accesoAsigglobalAt3 (mut i32) (i32.const 1))
	(global $accesoglobalAt3 (mut i32) (i32.const 1))
(global $accesoAsigglobalAt1 (mut i32) (i32.const 1))
(global $accesoglobalAt1 (mut i32) (i32.const 1))


(func $main
(local $comp17V1 i32)
(local $comp17V2 i32)
(local $comp17V3 i32)

	i32.const 1
	global.set $accesoAsigglobalAt3
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
global.get $accesoAsigglobalAt3
i32.const 1
i32.eq
(if
	(then
		global.get $i32auxTripla
		global.set $globalAt3V1
	)
	(else
		global.get $accesoAsigglobalAt3
		i32.const 2
		i32.eq
		(if
			(then
				global.get $i32auxTripla
				global.set $globalAt3V2
			)
			(else
				global.get $accesoAsigglobalAt3
				i32.const 3
				i32.eq
				(if
					(then
						global.get $i32auxTripla
						global.set $globalAt3V3
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
global.set $accesoAsigglobalAt3
global.get $accesoAsigglobalAt3
i32.const 1
i32.eq
(if
	(then
		i32.const 2
		global.set $globalAt3V1
	)
	(else
		global.get $accesoAsigglobalAt3
		i32.const 2
		i32.eq
		(if
			(then
				i32.const 2
				global.set $globalAt3V2
			)
			(else
				global.get $accesoAsigglobalAt3
				i32.const 3
				i32.eq
				(if
					(then
						i32.const 2
						global.set $globalAt3V3
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
global.set $accesoAsigglobalAt3
global.get $accesoAsigglobalAt3
i32.const 1
i32.eq
(if
	(then
		i32.const 3
		global.set $globalAt3V1
	)
	(else
		global.get $accesoAsigglobalAt3
		i32.const 2
		i32.eq
		(if
			(then
				i32.const 3
				global.set $globalAt3V2
			)
			(else
				global.get $accesoAsigglobalAt3
				i32.const 3
				i32.eq
				(if
					(then
						i32.const 3
						global.set $globalAt3V3
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
global.get $globalAt1V1
global.get $globalAt2V1
i32.add
global.set $AUX1V1i32
global.get $globalAt1V2
global.get $globalAt2V2
i32.add
global.set $AUX1V2i32
global.get $globalAt1V3
global.get $globalAt2V3
i32.add
global.set $AUX1V3i32
global.get $globalAt3V1
global.get $globalAt3V1
i32.mul
global.set $AUX2V1i32
global.get $globalAt3V2
global.get $globalAt3V2
i32.mul
global.set $AUX2V2i32
global.get $globalAt3V3
global.get $globalAt3V3
i32.mul
global.set $AUX2V3i32
global.get $AUX2V1i32
global.get $globalAt2V1
i32.add
global.set $AUX2V1i32
global.get $AUX2V2i32
global.get $globalAt2V2
i32.add
global.set $AUX2V2i32
global.get $AUX2V3i32
global.get $globalAt2V3
i32.add
global.set $AUX2V3i32
global.get $AUX1V1f64
global.get $AUX2V1f64
f64.lt
local.set $comp17V1
global.get $AUX1V2f64
global.get $AUX2V2f64
f64.lt
local.set $comp17V2
global.get $AUX1V3f64
global.get $AUX2V3f64
f64.lt
local.set $comp17V3
local.get $comp17V3
local.get $comp17V2
i32.eq
local.get $comp17V1
i32.eq
(if
	(then
		global.get $globalAt1V1
		call $console_log_i32
		global.get $globalAt1V2
		call $console_log_i32
		global.get $globalAt1V3
		call $console_log_i32
		global.get $globalAt3V1
		call $console_log_i32
		global.get $globalAt3V2
		call $console_log_i32
		global.get $globalAt3V3
		call $console_log_i32
	)
)
)
	(export "main" (func $main))
)


