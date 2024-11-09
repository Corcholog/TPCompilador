(module

(import "console" "log" (func $log (param i32 i32)))
	(import "js" "mem" (memory 1))
		
	(func $main
		i32.const 1
		local.set $global:x
		(loop $FOR1)
		local.get $global:x
		i32.const 10
		i32.ge
		local.set $comp2
		br_if $endfor:FOR1
		i32.const 2
		i32.const 3
		i32.mul
		i32.const 0
		call $log
		local.get $global:x
		i32.const 1
		i32.add
		br $FOR1
		($endfor:FOR1)
	)
)
