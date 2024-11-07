local.get $2
local.get $3
i32.mul
local.get $global:j
i32.sub
local.get $global:a
i32.le
local.set $comp0
local.get $global:e
local.get $3
i32.add
local.get $global:b
i32.le
local.set $comp1
local.get $1
local.get $12
i32.mul
local.get $global:c
i32.add
local.get $3
i32.le
local.set $comp4
local.get $2
local.get $3
i32.add
local.get $global:b
local.get $2
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
local.get $2
i32.add
local.set $global:b
local.get $3
local.set $global:a
local.get $global:a
f64_convert_i32_s
local.set $global:c
local.set $global:t2
