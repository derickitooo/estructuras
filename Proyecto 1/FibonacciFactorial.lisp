(write-line "Factorial")
(defun factorial (n)
  (if (<= n 1)
      1
      (* n (factorial (- n 1)))))

(defun calcular-factorial ()
  (format t "Ingrese un número para calcular su factorial: ")
  (let ((numero (read)))
    (format t "El factorial de ~a es: ~a~%" numero (factorial numero))))

(calcular-factorial)

(write-line "Serie de Fibonacci")
(defun fibonacci (n)
  (if (<= n 1)
      n
      (+ (fibonacci (- n 1))
         (fibonacci (- n 2)))))

(defun calcular-fibonacci ()
  (format t "Ingrese el término de la serie de Fibonacci que desea calcular: ")
  (let ((termino (read)))
    (format t "El término ~a de la serie de Fibonacci es: ~a~%" termino (fibonacci termino))))

(calcular-fibonacci)



