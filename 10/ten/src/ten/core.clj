(ns ten.core
  (:gen-class)
  (:require [clojure.java.io :as io] )
  (:require [clojure.string :as str] ))

(def test-input (str/split (slurp "input/test.txt") #"\n") )
(def real-input (str/split (slurp "input/input.txt") #"\n") )

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

(defn -main []
  (println (count real-input))
  (println (take 5 (parse-stars real-input)) ))
