#lang racket

(require web-server/http
         web-server/managers/none
         web-server/servlet
         web-server/servlet-env)

(provide interface-version manager say-hello)

(define interface-version 'v2)

(define manager
  (create-none-manager
   (lambda (req)
     (response/xexpr
      `(html (head (title "No Continuation"))
             (body (h3 "No Continuation Found Here.")))))))

(define (say-hello req)
  (let* ((name-req
          (send/suspend
           (lambda (k-url)
             (response/xexpr
              `(html (head (title "Enter Your Name"))
                     (body
                      (form ([action ,k-url]
                             [method "POST"])
                            (p "Enter Your Name: "
                               (input ([name "name"]))
                               (input ([type "submit"]))))))))))
         (name (extract-binding/single
                'name
                (request-bindings name-req))))
    (send/finish
     (response/xexpr
      `(html (head (title ,(string-append "Hello, " name)))
             (body
              (h1 (string-append "Hello to you, dear " ,name))))))))

(define (start-server)
  (serve/servlet say-hello
                 #:servlet-path "/say-hello"
                 #:quit? true))
