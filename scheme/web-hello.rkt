#lang racket

(require web-server/servlet
         web-server/servlet-env)

(define (hello-app req)
  (response/xexpr
   `(html (head (title "Hello Racket!"))
          (body (h1 "Hello Racket!")
                (p "Welcome to the wonderful Lisp/Scheme/Racket world!")))))


(display "Hello")
