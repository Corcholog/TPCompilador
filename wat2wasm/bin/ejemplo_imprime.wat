(module
(import "console" "log" (func $log (param i32 i32)))
(import "js" "mem" (memory 1))
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
(data (i32.const 279) " goooooood amimir ")



(func $main
	(local $accesoAsigglobalAt2 i32)
	(local $accesoglobalAt2 i32)
(local $comp8 i32)

	i32.const 1
	global.set $globalAa
	i32.const 1
	local.set $accesoAsigglobalAt2
	i32.const 2
	i32.const 3
	i32.mul
	global.set $i32auxTripla
	global.get $i32auxTripla
	i32.const 3
	i32.sub
	global.set $i32auxTripla
	global.get $i32auxTripla
	global.set $AUXNEG
	global.get $AUXNEG
	i32.const 0
	i32.lt_s
	(if
		(then
			i32.const 101
			i32.const 73
			call $log
		)
	)
	global.get $AUXNEG
	local.set $accesoglobalAt2
	local.get $accesoglobalAt2
	i32.const 1
	i32.eq
	(if
		(then
			global.get $globalAt2V1
			global.set $i32auxTripla
		)
		(else
			local.get $accesoglobalAt2
			i32.const 2
			i32.eq
			(if
				(then
					global.get $globalAt2V2
					global.set $i32auxTripla
				)
				(else
					local.get $accesoglobalAt2
					i32.const 3
					i32.eq
					(if
						(then
							global.get $globalAt2V3
							global.set $i32auxTripla
						)
						(else
							i32.const 237
							i32.const 42
							call $log
						)
					)
				)
			)
		)
	)
global.get $i32auxTripla
i32.const 1
i32.add
global.set $i32auxTripla
global.get $i32auxTripla
local.get $accesoAsigglobalAt2
i32.const 1
i32.eq
(if
	(then
		global.get $i32auxTripla
		global.set $globalAt2V1
	)
	(else
		local.get $accesoAsigglobalAt2
		i32.const 2
		i32.eq
		(if
			(then
				global.get $i32auxTripla
				global.set $globalAt2V2
			)
			(else
				local.get $accesoAsigglobalAt2
				i32.const 3
				i32.eq
				(if
					(then
						global.get $i32auxTripla
						global.set $globalAt2V3
					)
					(else
						i32.const 237
						i32.const 42
						call $log
					)
				)
			)
		)
	)
)
global.set $i32auxTripla
i32.const 1
local.set $accesoglobalAt2
local.get $accesoglobalAt2
i32.const 1
i32.eq
(if
	(then
		global.get $globalAt2V1
		global.set $i32auxTripla
	)
	(else
		local.get $accesoglobalAt2
		i32.const 2
		i32.eq
		(if
			(then
				global.get $globalAt2V2
				global.set $i32auxTripla
			)
			(else
				local.get $accesoglobalAt2
				i32.const 3
				i32.eq
				(if
					(then
						global.get $globalAt2V3
						global.set $i32auxTripla
					)
					(else
						i32.const 237
						i32.const 42
						call $log
					)
				)
			)
		)
	)
)
global.get $i32auxTripla
i32.const 0
i32.gt_u
local.set $comp8
local.get $comp8
(if
	(then
		i32.const 279
		i32.const 18
		call $log
	)
)
)
	(export "main" (func $main))
)


