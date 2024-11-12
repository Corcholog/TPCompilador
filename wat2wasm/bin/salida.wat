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
(global $comp0 (mut i32)(i32.const 0))
	
(func $main
	
	i32.const 2
        global.set $globalAa
        i32.const 4
        global.set $globalAb
        global.get $globalAa
        global.get $globalAb
	i32.lt_u
	global.set $comp0
	global.get $comp0
	(if
		(then
			i32.const 5
			global.set $globalAa
			i32.const 101
     			i32.const 4
      			call $log
		)
	
	(else
		i32.const 2
		global.set $globalAb
	)
	)
)
	(export "main" (func $main))
)