(ns ten.core
  (:gen-class)
  (:require [clojure.java.io :as io] )
  (:require [clojure.string :as str] ))

(def input (str/split (slurp "input/test.txt") #"\n") )

(defn -main []
  (println (count input))
  (println (nth input 0)) )
