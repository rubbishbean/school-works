---------------------------------------------------------------------
--
-- CAS CS 320, Fall 2014
-- Assignment 4 (skeleton code)
-- Tree.hs
-- modified by Jiaxing Yan   11/11/2014

data Tree =
    Leaf
  | Twig
  | Branch Tree Tree Tree
-- 3a
  deriving (Eq,Show)
-- 3b
twigs :: Tree -> Integer
twigs(Twig) = 1
twigs(Leaf) = 0
twigs(Branch x y z) = twigs(x) + twigs(y) + twigs(z)
-- 3c
branches :: Tree -> Integer
branches(Leaf) = 0
branches(Twig) = 0
branches(Branch x y z) = 1 + branches(x) + branches(y) + branches(z)
-- 3d
height :: Tree -> Integer
height(Leaf) = 0 
height(Twig) = 1
height(Branch x y z) = 1 + max (max (height x) (height y)) (height z)
-- 3e
perfect :: Tree -> Bool
perfect(Leaf) = False
perfect(Twig) = False
perfect(Branch Leaf Leaf Leaf) = True
perfect(Branch x y z) = perfect(x) && perfect(y) && perfect(z) && height(x) == height(y) && height(z) == height(x)
-- 3f
degenerate :: Tree -> Bool
degenerate(Leaf) = True
degenerate(Twig) = True
degenerate(Branch x y z) = ((branches(x) == 0 && branches(y) == 0)||(branches(x) == 0 && branches(z) == 0)||(branches(y) == 0 && branches(z) == 0)) &&((degenerate(x) && degenerate(y)) || (degenerate(x) && degenerate(z)) || (degenerate(y) && degenerate(z)))
-- 3g
infinite :: Tree
infinite = (Branch infinite infinite infinite)
--eof