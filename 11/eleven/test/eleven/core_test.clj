(ns eleven.core-test
  (:require [clojure.test :refer :all]
            [eleven.core :refer :all]))

(deftest can-get-power-level-of-a-point
  (testing "Getting the power level of a coordinate"
    (is (= (- 5) (cell-power-level 57 122 79)))
    (is (= 0 (cell-power-level 39 217 196)))
    (is (= 4 (cell-power-level 71 101 153)))
    (is (= 4 (cell-power-level 8 3 5)))))

(deftest can-score-a-kernel
  (testing "Can score a kernel"
    (is (= 30 (score-kernel 3 (build-grid 42 100) 21 61)))
    (is (= 29 (score-kernel 3 (build-grid 18 50) 33 45)))))

(deftest can-get-max-grid-score-and-position
  (testing "Can find the maximum scoring kernel and its position among all grid kernels"
    (is (= '(30 21 61) (max-grid-score 42 65 3)))
    (is (= '(29 33 45) (max-grid-score 18 50 3)))
    (is (= '(30 21 76) (max-grid-score 3031 300 3)))))

(deftest can-get-max-grid-score-and-position-for-full-kernel
  (testing "Can find the kernel score where kernel size equals side length"
    (is (= '(-47490 1 1) (max-grid-score 3031 300 300)))))

(deftest can-get-max-scoring-kernel-size
  (testing "Can find the kernel size where its score is maximised"
    (is (= (list (list 59 241 119 5) (list 4)) (max-scoring-kernel 3031 300 5)))))