(defvar *funcs* nil)
(defun make-multiply-funcs (n)
  (dotimes (i n)
    (push #'(lambda (x) (* x i)) *funcs* )))
