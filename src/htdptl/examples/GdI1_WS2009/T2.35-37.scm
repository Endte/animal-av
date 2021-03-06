;; a point has a x and a y coordinate,
;; both of which are numbers.
(define-struct point (x y))

;; A shape is either 
;;    a circle structure: 
;;       (make-circle p s)
;;       where p is a point describing the center
;;       and s is a number describing the radius; or 
;;    a square structure:
;;       (make-square nw s)
;;        where nw is the north-west corner point 
;;        and s is a number describing the side length.

(define-struct circle (center radius))
(define-struct square (nw length))

;; Examples: (make-circle (make-point 5 9) 87)
;;           (make-square (make-point 20 5) 5)

;; perimeter : shape ->  number
;; computes the perimeter of a-shape
;; example: (perimeter (make-square (3 7)) is 28
(define (perimeter a-shape)
  (cond
    [(square? a-shape) (* (square-length a-shape) 4)]
    [(circle? a-shape) (* (* 2 (circle-radius a-shape)) pi)]))

;; Tests
(check-expect (perimeter (make-square 3 7)) 28)
(check-within (perimeter (make-circle (make-point 13 17) 20)) 125.6637 0.0001)