(ns ten.core-test
  (:require [clojure.test :refer :all]
            [ten.core :refer :all]))

(deftest parse-line-test-a
  (testing "Parses lines correctly - A"
    (is (= 
      (parse-line "position=<-41660, -20869> velocity=<-4, -2>")
      (list (list -41660 -20869) (list -4 -2))))))

(deftest parse-line-test-b
  (testing "Parses lines correctly - B"
    (is (= 
      (parse-line "position=< 41617,  10491> velocity=<-4, -1>")
      (list (list 41617 10491) (list -4 -1))))))

(deftest parse-line-test-c
  (testing "Parses lines correctly - C"
    (is (= 
      (parse-line "position=<-41669, 10244> velocity=< 4,  1>")
      (list (list -41669 10244) (list 4 1))))))

(deftest parse-line-test-d
  (testing "Parses lines correctly - D"
    (is (= 
      (parse-line "position=<-41123, 20123> velocity=< 0,  3>")
      (list (list -41123 20123) (list 0 3))))))
