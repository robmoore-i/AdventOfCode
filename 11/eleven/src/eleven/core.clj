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

(defn self-cross [l]
  (combo/cartesian-product l l))

(defn build-grid [grid-serial-number side-length]
  (let [
    coordinate-range (range 1 (+ side-length 1))
    grid (self-cross coordinate-range)
    power-level (partial apply (partial cell-power-level grid-serial-number))
    cell-power-levels (map power-level grid)
    vector-power-grid (vector-partition side-length cell-power-levels)
  ]
  vector-power-grid))

;; Where [x y] marks the top left corner of the 3x3 kernel in 1-based coordinates.
(defn score-kernel [kernel-size grid x y]
  (let [
    x-coordinates (range (- x 1) (+ x (- kernel-size 1)))
    y-coordinates (range (- y 1) (+ y (- kernel-size 1)))
    indices (matrix (combo/cartesian-product x-coordinates y-coordinates))
    power-values (map (partial get-in grid) indices)
    score (reduce + power-values)
  ]
  score))

(defn max-index [vec]
  (first (apply max-key second (map-indexed vector vec))))

(defn max-kernel-score [grid kernel-size]
  (let [
    coordinate-range (range 1 (- (count grid) (- kernel-size 2)))
    kernel-markers (matrix (self-cross coordinate-range))
    kernel-scores (map (partial apply (partial score-kernel kernel-size grid)) kernel-markers)
    max-kernel-score-idx (max-index kernel-scores)
    max-kernel-score-markers (nth kernel-markers max-kernel-score-idx)
    score (nth kernel-scores max-kernel-score-idx)
  ]
  (cons score max-kernel-score-markers)))

(defn max-grid-score [grid-serial-number side-legth kernel-size]
  (max-kernel-score (build-grid grid-serial-number side-legth) kernel-size))

(defn append [x coll]
  (apply conj (list x) (reverse coll)))

(defn max-scoring-kernel-iteration [grid current-max-score kernel-sizes]
  (let [
    candidate-kernel-size (first kernel-sizes)

    kernel-score (max-kernel-score grid candidate-kernel-size)
    score-discriminator (int (Math/sqrt (/ (first kernel-score) 4))) ;; NB. 'int always rounds down

    remaining-kernel-sizes (filter #(> % score-discriminator) (drop 1 kernel-sizes))
    exceeds-current-max-score (> (first kernel-score) (first current-max-score))
    new-max-score (if exceeds-current-max-score (append candidate-kernel-size kernel-score) current-max-score)
  ]
  (list new-max-score remaining-kernel-sizes)))

(defn max-scoring-kernel [grid-serial-number side-length max-kernel-size]
  (let [
    grid (build-grid grid-serial-number side-length)
    kernel-sizes (reverse (range 3 (+ max-kernel-size 1)))
    find-max-score (partial max-scoring-kernel-iteration grid)

    iter-one (find-max-score '(0 1 1 0) kernel-sizes)
    iter #(if (= 0 (count (second %))) % (apply find-max-score %))
    n-iterations (- (count kernel-sizes) 5)
    final-result ((apply comp (repeat n-iterations iter)) iter-one)
  ]
  final-result))

(def answerp1 (max-grid-score 3031 300 3))

(defn -main []
  (println (max-scoring-kernel 3031 300 5)))
