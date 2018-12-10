(ns ten.core
  (:gen-class)
  (:require [clojure.java.io :as io] )
  (:require [clojure.string :as str] ))

(def test-input (str/split (slurp "input/test.txt") #"\n") )
(def real-input (str/split (slurp "input/input.txt") #"\n") )
(def input test-input)

(defn drop-edges [s]
  (subs s 1 (- (count s) 1)))

(defn parse-line [line]
  (let [
    split-line (str/split line #"> velocity=")
    nums-from-csv-line #(map read-string (map str/trim (str/split % #", ")))
    position (nums-from-csv-line (apply str (drop 10 (first split-line))))
    velocity (nums-from-csv-line (drop-edges (second split-line)))
  ]
  (cons position [velocity]) ))

(defn parse-stars [input]
  (map parse-line input))

(defn get-positions [stars]
  (map first stars))

(defn get-velocities [stars]
  (map second stars))

(defn get-bounds [stars]
  (let [
    positions (get-positions stars)
    max-x (apply max (map first positions))
    min-x (apply min (map first positions))
    max-y (apply max (map second positions))
    min-y (apply min (map second positions))
  ]
  (list max-x min-x max-y min-y)))

(defn make-positions-all-positive [stars]
  (let [
    bounds (get-bounds stars)
    x-move (- (second bounds))
    y-move (- (nth bounds 3))
    ;; Translates a position (x y) such that it is sure to be positive
    make-positive #(list (+ x-move (first %)) (+ y-move (second %)))
    positions (get-positions stars)
  ]
  (map make-positive positions)))

(defn print-stars [stars]
  (let [
    positions (make-positions-all-positive stars)
    bounds (get-bounds positions)
    width (first bounds)
    height (nth bounds 2)
  ]
  5))

(defn -main []
  (println (count input))
  (println (print-stars (parse-stars input))   ))
