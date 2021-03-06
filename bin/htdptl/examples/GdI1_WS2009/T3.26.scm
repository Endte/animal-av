;; pred: N  ->  N
;; returns the predecessor of n; if n is 0, return 0
(define (pred n)
  (if (> n 0)
      (- n 1)
      0))

;; succ: N -> N
;; returns the successor of n; if negative, return 0
(define (succ n)
  (if (>= n 0)
      (+ n 1)
      0))

;; zero?: N -> boolean
;; returns true if N is 0, else false
;; Already defined in <span class="highlight">HtDP-TL</span>, no need to do it again!

;; Tests
(check-expect (pred 0) 0)
(check-expect (pred -13) 0)
(check-expect (pred 43) 42)
(check-expect (succ 0) 1)
(check-expect (succ -17) 0)
(check-expect (succ 41) 42)