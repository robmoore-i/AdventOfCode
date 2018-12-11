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

(defn build-grid [grid-serial-number side-length]
  (let [
    coordinate-range (range 1 (+ side-length 1))
    grid (combo/cartesian-product coordinate-range coordinate-range)
    power-level (partial apply (partial cell-power-level grid-serial-number))
    cell-power-levels (map power-level grid)
    power-annotated-grid (partition side-length cell-power-levels)
  ]
  power-annotated-grid ))

(defn -main []
  (println (build-grid 42 5) ))
