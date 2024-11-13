(module

(import "console" "log" (func $log (param i32 i32)))
(import "js" "mem" (memory 1))
(global $globalAa (mut i32)(i32.const 0))
(global $globalAb (mut i32)(i32.const 0))
	
(func $main
	(local $comp2 i32)
	i32.const 2
	global.set $globalAa
	i32.const 4
	global.set $globalAb
	global.get $globalAa
	global.get $globalAb
	i32.gt_u
	local.set $comp2
	local.get $comp2
	(if
		(then
			global.get $globalAa
			i32.const 0
			call $log
		)
		(else
			global.get $globalAb
			i32.const 0
			call $log
		)
	)
)
	(export "main" (func $main))
)
