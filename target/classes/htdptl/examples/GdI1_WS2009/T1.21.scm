(define (area-of-disk r)
  (* 3.14 (* r r)))

;; area-of-ring: num num -> num
;; determine the area of a ring
;; example: (area-of-ring 5 3)
(define (area-of-ring outer inner)
  (- (area-of-disk outer)
       (area-of-disk inner)))

;; Test
(check-within (area-of-ring 5 3) 50.24 0.5)