(module

(import "console" "log" (func $log (param i32 i32)))
(import "js" "mem" (memory 1))
(global $globalAt3V1 (mut f64)(f64.const 0))
(global $globalAt3V2 (mut f64)(f64.const 0))
(global $globalAt3V3 (mut f64)(f64.const 0))
(global $globalAt2V1 (mut f64)(f64.const 0))
(global $globalAt2V2 (mut f64)(f64.const 0))
(global $globalAt2V3 (mut f64)(f64.const 0))
(global $globalAt1V1 (mut f64)(f64.const 0))
(global $globalAt1V2 (mut f64)(f64.const 0))
(global $globalAt1V3 (mut f64)(f64.const 0))
(global $globalAa (mut i32)(i32.const 0))
(global $globalAb (mut i32)(i32.const 0))
	
(func $main
	local.get $globalAa
	local.get $globalAb
	i32.gt_u
	(local $comp0 i32)
	local.set $comp0
	local.get $comp0
	(if
		(then
			i32.const 5
			local.set $globalAa
		)
	)
	local.get $comp5
	local.get $comp4
	i32.and
	local.get $t1V0
	f64.const 8.0
	local.set $globalAt1
)
	(export "main" (func $main))
)
