(ns eleven.core
  (:gen-class)
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str])
  (:require [clojure.math.combinatorics :as combo]) )

(def input 3031)

(defn hundreds-digit [x]
  (mod (quot x 100) 10))

(defn cell-power-level [grid-serial-number x y]
  (let [
    rack-id (+ x 10)
    stage-two (* rack-id y)
    stage-three (+ stage-two grid-serial-number)
    stage-four (* stage-three rack-id)
    stage-five (hundreds-digit stage-four)
    stage-six (- stage-five 5)
    power-level stage-six
  ]
  power-level))

(defn matrix [l]
  (apply vector (map (partial apply vector) l)))

(defn vector-partition [n l]
  (let [list-partitioned (partition n l)]
  (matrix list-partitioned)))

(defn build-grid [grid-serial-number side-length]
  (let [
    coordinate-range (range 1 (+ side-length 1))
    grid (combo/cartesian-product coordinate-range coordinate-range)
    power-level (partial apply (partial cell-power-level grid-serial-number))
    cell-power-levels (map power-level grid)
    vector-power-grid (vector-partition side-length cell-power-levels)
  ]
  vector-power-grid ))

;; Where [x y] marks the top left corner of the 3x3 kernel.
(defn score-kernel [grid x y]
  (let [
    x-coordinates (range x (+ x 3))
    y-coordinates (range y (+ y 3))
    indices (matrix (combo/cartesian-product x-coordinates y-coordinates))
    power-values (map (partial get-in grid) indices)
    score (reduce + power-values)
  ]
  score))

(defn -main []
  (println (build-grid 42 5) )
  (println (score-kernel (build-grid 42 5) 0 0) ) )
