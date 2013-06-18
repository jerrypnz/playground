#lang racket

(require web-server/http
         web-server/managers/none
         web-server/servlet
         web-server/servlet-env)

(provide interface-version manager star-polling-app)

(define interface-version 'v2)

(define manager
  (create-none-manager
   (lambda (req)
     (response/xexpr
      `(html (head (title "No Continuation"))
             (body (h3 "No Continuation Found Here.")))))))

(define boys '("Tom Cruise" "Matt Damon" "Brad Pitt" "Johnny Depp"))
(define girls '("Anne Hathaway" "Natalie Portman" "Jessica Alba" "Liv Tyler"))
(define poll-results (make-hash (map (lambda (name)
                                       (cons name 0))
                                     (append boys girls))))

(define (get-user-gender)
  (let ((req (send/suspend
              (lambda (k-url)
                (response/xexpr
                 `(html (head (title "Select Gender"))
                        (body
                         (form ([action ,k-url]
                                [method "POST"])
                               (h3 "You are a "
                                   (input ([name "gender"]
                                           [type "radio"]
                                           [value "male"]
                                           [checked "true"]))
                                   "boy or a "
                                   (input ([name "gender"]
                                           [type "radio"]
                                           [value "female"]))
                                   "girl?"
                                   (input ([type "submit"])))))))))))
    (if (equal? "male"
                (extract-binding/single 'gender
                                        (request-bindings req)))
        #t
        #f)))

(define (select-favorite-star star-list)
  (let ((req (send/suspend
              (lambda (k-url)
                (response/xexpr
                 `(html
                   (head (title "Select Your Favorite Star"))
                   (body
                    (h3 "Select your favorite star from the following list:")
                    (form ([action ,k-url]
                           [method "POST"])
                          ,@(map (lambda (star)
                                   `(p (input ([name "star"]
                                               [type "radio"]
                                               [value ,star]))
                                       ,star))
                                 star-list)
                          (input ([type "submit"]))))))))))
    (let ((star-name (extract-binding/single 'star
                                             (request-bindings req))))
      (hash-update! poll-results
                    star-name
                    add1
                    (lambda () 1)))))

(define (show-poll-result)
  (let ((star-list (sort (hash->list poll-results)
                         >
                         #:key cdr)))
    (send/finish
     (response/xexpr
      `(html
        (head (title "Polling Results"))
        (body
         (h3 "Polling results")
         (table
          (thead
           (tr (td "Name") (td "Count")))
          (tbody
           ,@(map (lambda (poll-record)
                    `(tr (td ,(car poll-record))
                         (td ,(format "~a" (cdr poll-record)))))
                  star-list)))
         (p (a ([href "/poll"]) "Back"))))))))

(define (star-polling-app request)
  (let ((is-boy (get-user-gender)))
    (if is-boy
        (select-favorite-star girls)
        (select-favorite-star boys))
    (show-poll-result)))

(define (start-server)
  (serve/servlet star-polling-app
                 #:servlet-path "/poll"
                 #:quit? true))

(start-server)

