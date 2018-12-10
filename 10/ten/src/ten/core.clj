(ns ten.core
  (:gen-class)
  (:require [clojure.java.io :as io] )
  (:require [clojure.string :as str] ))

(def test-input (str/split (slurp "input/test.txt") #"\n") )
(def real-input (str/split (slurp "input/input.txt") #"\n") )

(defn drop-edges [s]
  (let [l (count s)]
  (subs s 1 (- l 1))))  

(defn parse-line [line]
  (let [
    split-line (str/split line #"=")
    nums-from-csv-line #(map read-string (map str/trim (str/split % #", ")))
    position (nums-from-csv-line (apply str (drop 10 (first (str/split line #"> velocity")))))
    velocity (nums-from-csv-line (drop-edges (second (str/split line #" velocity="))))
  ]
  (cons position [velocity]) ))

(defn parse-stars [input]
  (map parse-line input))

(defn -main []
  (println (count real-input))
  (println (parse-line "position=<-41660, -20869> velocity=<-4, -2>") ))
