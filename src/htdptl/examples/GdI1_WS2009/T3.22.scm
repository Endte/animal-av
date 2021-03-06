;; sum: (listof number) -> number
;; returns the sum of all elements in the list
;; example: (sum (list 3 5 7)) = 15
(define (sum a-list-of-nums)
  (cond
    [(empty? a-list-of-nums) 0]
    [else (+ 
           (first a-list-of-nums) 
           (sum (rest a-list-of-nums)))]))

;; Tests
(check-expect (sum empty) 0)
(check-expect (sum (list 3 5 7)) 15)
(check-expect (sum (list 3 5 7 9 -24)) 0)