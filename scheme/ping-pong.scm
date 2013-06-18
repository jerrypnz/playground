#lang racket

(define (ping)
  (let loop ((n 1))
    (display (format "ping ~A\n" n))
    (call/cc (lambda (k)
               (set! *ping-cont* k)
               (*pong-cont*)))
    (when (< n 5) (loop (+ n 1)))))

(define (pong)
  (let loop ((n 1))
    (display (format "pong ~A\n" n))
    (call/cc (lambda (k)
               (set! *pong-cont* k)
               (*ping-cont*)))
    (when (< n 5) (loop (+ n 1)))))

(define *ping-cont* ping)
(define *pong-cont* pong)

(ping)