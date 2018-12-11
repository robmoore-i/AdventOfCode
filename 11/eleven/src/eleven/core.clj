(ns eleven.core
  (:gen-class)
  (:require [clojure.java.io :as io] )
  (:require [clojure.string :as str] ))

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

(defn -main []
  (println 0 ))
