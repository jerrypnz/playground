(defun my-reduce (func data &optional initval)
  (let ((result initval))
    (dolist (item data)
      (setf result (funcall func item result)))
    result))

(format t "Plus all: ~d~%" (my-reduce #'+ '(1 2 3 4) 0))
(format t "Multiply all: ~d~%" (my-reduce #'* '(1 2 3 4) 1))
(format t "Find max: ~d~%" (my-reduce #'(lambda (x y) (if (> x y) x y)) '(1 0 5 9 11 3) -1))
