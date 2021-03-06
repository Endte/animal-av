;; a point has an x and a y coordinate,
;; both of which are numbers.
(define-struct point (x y))

;; a pixel-2 is either
;;  1. a number
;;  2. a point-structure

;; distance-to-0: pixel-2 -> number
;; determines the distance to (0,0) of the given pixel-2
;; example: (distance-to-0 5) is 5
(define (distance-to-0 a-pixel)
  (cond
    [(number? a-pixel) a-pixel]
    [(point? a-pixel) 
     (sqrt (+ (sqr (point-x a-pixel))
              (sqr (point-y a-pixel))))]))

;; Tests
(check-expect (distance-to-0 7) 7)
(check-within (distance-to-0 (make-point 17 42)) 45.31 0.0001)