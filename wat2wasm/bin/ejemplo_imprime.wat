(module

(import "console" "log" (func $log (param i32 i32)))
  (import "js" "mem" (memory 1))
  (data (i32.const 101) "Hola")  
;; loop
;; int a = 1; 
;; while (i > 0) {
;;   a = a * i;
;;   i = i - 1;
;; }
(func $f7 (param i64) (result i64)
 (local i64)
 i64.const 1
 local.set 1 
 (block
           local.get 0
           i64.eqz
           br_if 0
           (loop
           i32.const 101
           i32.const 4
           call $log       
           local.get 1
           local.get 0
           i64.mul
           local.set 1
           local.get 0
           i64.const -1
           i64.add
           local.set 0
           local.get 0
           ;;local.tee 0
           i64.eqz
           br_if 1
           br 0))
       local.get 1)

(func $main (result i32)    
    (local $w i32)
    (local $s i32)
    i32.const 7
    local.set $w        
    local.get $w      ;; rem f2 f3   
    i64.extend_i32_s  ;; add f7
    call $f7 
    i32.wrap_i64 ;; add f7
    local.set $s 
    ;;i32.const 101
    ;;i32.const 4
    ;;call $log       
    local.get $s        
    ) 

(export "main"  (func $main))
)

