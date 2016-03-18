---------------------------------------------------------------------
--
-- CAS CS 320, Fall 2014
-- Assignment 4 (skeleton code)
-- Term.hs
--modified by Jiaxing Yan  11/11/2014

data Term =
    Number Integer
  | Abs Term
  | Plus Term Term
  | Mult Term Term


evaluate :: Term -> Integer
evaluate(Number i) = i
evaluate(Abs t) = abs(evaluate(t))
evaluate(Plus t1 t2) =  evaluate(t1)+evaluate(t2)
evaluate(Mult t1 t2) = evaluate(t1) * evaluate(t2)

--eof