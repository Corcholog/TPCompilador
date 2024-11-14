(module
(import "console" "log" (func $log (param i32 i32)))
(import "js" "mem" (memory 1))
(global $globalAfuncion1Apepe (mut i32)(i32.const 0))
(global $globalAi (mut i32)(i32.const 0))
(global $globalAt3V1 (mut f64)(f64.const 0))
(global $globalAt3V2 (mut f64)(f64.const 0))
(global $globalAt3V3 (mut f64)(f64.const 0))
(global $globalAt2V1 (mut f64)(f64.const 0))
(global $globalAt2V2 (mut f64)(f64.const 0))
(global $globalAt2V3 (mut f64)(f64.const 0))
(global $globalAt1V1 (mut f64)(f64.const 0))
(global $globalAt1V2 (mut f64)(f64.const 0))
(global $globalAt1V3 (mut f64)(f64.const 0))
(global $globalAe (mut f64)(f64.const 0))
(global $globalAf (mut f64)(f64.const 0))
(global $globalAc (mut i32)(i32.const 0))
(global $globalAa (mut i32)(i32.const 0))
(global $globalAb (mut i32)(i32.const 0))
(data (i32.const 101) " en el for")
(data (i32.const 118) "b era  		menor que  		a")


( func $globalAfuncion1Afuncion2 (param $globalAfuncion1Afuncion2Aparametro2 i32) (result f64)
	(local $globalAfuncion1Afuncion2retorno f64)
	f64.const 0
	local.set $globalAfuncion1Afuncion2retorno
	f64.const 3.0
	local.set $globalAfuncion1Afuncion2retorno
	local.get $globalAfuncion1Afuncion2retorno
)

( func $globalAfuncion1 (param $globalAfuncion1Aparametro f64) (result i32)
	(local $globalAfuncion1retorno i32)
	i32.const 0
	local.set $globalAfuncion1retorno
	i32.const 3
	local.set $globalAfuncion1retorno
	local.get $globalAfuncion1retorno
)


(func $main
	(local $comp5 i32)
	(local $comp12 i32)
		(local $comp21 i32)
	(local $comp29 i32)
	(local $comp30 i32)
	(local $comp31 i32)
	(local $comp38 i32)
	(local $comp39 i32)

	f64.const 3.0
	call $globalAfuncion1
	global.set $globalAa
	f64.const 3.0
	call $globalAfuncion1
	global.set $globalAb
	global.get $globalAa
	global.get $globalAb
	i32.gt_u
	local.set $comp5
	local.get $comp5
	(if
		(then
			global.get $globalAb
			global.set $globalAa
		)
		(else
			global.get $globalAa
			global.set $globalAb
		)
	)
	global.get $globalAb
	global.get $globalAa
	i32.gt_u
	local.set $comp12
	local.get $comp12
	(if
		(then
			global.get $globalAa
			global.set $globalAb
			i32.const 118
			i32.const 23
			call $log
			global.get $globalAa
			global.get $globalAb
			i32.add
			i32.const 0
			call $log
		)
	)
	i32.const 1
	global.set $globalAi
	block $endforAFOR1
	loop $FOR1
		global.get $globalAi
		i32.const 10
		i32.gt_u
		local.set $comp21
		local.get $comp21
		br_if $endforAFOR1
		i32.const 101
		i32.const 10
		call $log
		global.get $globalAi
		i32.const 1
		i32.add
		br $FOR1
	end
	end
	global.get $globalAa
	i32.const 1
	i32.ge_u
	local.set $comp29
	local.get $comp29
	global.get $globalAb
	i32.const 2
	i32.ge_u
	local.set $comp30
	local.get $comp30
	global.get $globalAc
	i32.const 3
	i32.const 4
	i32.add
	i32.ge_u
	local.set $comp31
	local.get $comp31
	local.get $comp29
	local.get $comp30
	i32.and
	local.get $comp31
	i32.and
	(if
		(then
			i32.const 18
			i32.const 0
			call $log
		)
	)
	i32.const 0
	i32.const 3
	i32.ge_u
	local.set $comp38
)
	(export "main" (func $main))
)


