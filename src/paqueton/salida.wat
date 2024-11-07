(module

	( func $t1 (param $ i32) (result i32)
		i32.const 0
		local.set $t1retorno
		f64.const 2.0
		local.set $global:t1:parametro
		local.get $global:t1:parametro
		local.set $global:t1:gerardo
		local.get $global:t1:parametro
		local.set $t1retorno
		local.get $t1retorno
	)

	local.get $global:a
	local.get $global:j
	i32.const 2
	i32.const 3
	i32.mul
	i32.sub
	i32.le
	local.set $comp0
	local.get $global:b
	local.get $global:e
	i32.const 3
	i32.add
	i32.le
	local.set $comp1
	i32.const 1
	i32.const 12
	i32.mul
	local.get $global:c
	i32.add
	i32.const 3
	i32.le
	local.set $comp4
	i32.const 2
	i32.const 3
	i32.add
	local.get $global:b
	i32.const 2
	i32.mul
	i32.le
	local.set $comp6
	local.get $comp0
local.get $comp1
i32.and
	local.get $comp4
i32.and
	local.get $comp6
i32.and
	local.get $global:b
	local.set $global:a
	local.get $global:a
	i32.const 2
	i32.add
	local.set $global:b
	i32.const 3
	local.set $global:a
	f64.const 2.0
	local.get $global:gepeto
	f64.mul
	f64.const 2.0
	f64.add
	call $global:t1
	local.set $global:c
	local.set $global:t2
	)
