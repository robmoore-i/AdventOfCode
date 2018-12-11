(ns eleven.core-test
  (:require [clojure.test :refer :all]
            [eleven.core :refer :all]))

(deftest a-test
  (testing "Getting the power level of a coordinate"
    (is (= (- 5) (cell-power-level 57 122 79)))
    (is (= 0 (cell-power-level 39 217 196)))
    (is (= 4 (cell-power-level 71 101 153)))
    (is (= 4 (cell-power-level 8 3 5)))))
