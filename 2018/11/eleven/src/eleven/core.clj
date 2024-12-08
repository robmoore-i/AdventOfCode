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

(defn max-scoring-kernel-top-down-iteration [grid current-max-score kernel-sizes]
  (let [
    candidate-kernel-size (first kernel-sizes)

    kernel-score (max-kernel-score grid candidate-kernel-size)
    score-discriminator (int (Math/sqrt (/ (first kernel-score) 4))) ;; NB. 'int always rounds down

    remaining-kernel-sizes (filter #(> % score-discriminator) (drop 1 kernel-sizes))
    exceeds-current-max-score (> (first kernel-score) (first current-max-score))
    new-max-score (if exceeds-current-max-score (append candidate-kernel-size kernel-score) current-max-score)
  ]
  (list new-max-score remaining-kernel-sizes)))

(defn max-scoring-kernel-top-down [grid-serial-number side-length max-kernel-size]
  (let [
    grid (build-grid grid-serial-number side-length)
    kernel-sizes (reverse (range 3 (+ max-kernel-size 1)))
    find-max-score (partial max-scoring-kernel-top-down-iteration grid)

    iter-one (find-max-score '(0 1 1 0) kernel-sizes)
    iter #(if (= 0 (count (second %))) % (apply find-max-score %))
    n-iterations (- (count kernel-sizes) 5)
    final-result ((apply comp (repeat n-iterations iter)) iter-one)
  ]
  final-result))

;; Bottom up max scoring kernel strategy
;; In this, we move the kernel from the top left corner of the grid downwards,
;; using memoization (dynamic programming) to reduce computation.
;; This is way too complicated for me to attempt to implement in Clojure.
;; I will do this in Kotlin or Python at some point.

;; Let K(x, y, n) be the kernel score at (x, y) for kernel size n.
;; Let C(x, y, n) be the sum of the column starting at (x, y) descending by n
;; Let R(x, y, n) be the sum of the row starting at (x, y) moving rightwards by n
;; For all (x, y):
;; K(x, y, n + 1) = K(x, y, n) + C(x + n - 1, y, n - 1) + R(x, y + n - 1, n)

;; We can also economically calculate the functions C and R using the grid, G:
;; There are multiple ways we can try to reduce computation here.
;; C(x, y, n) = C(x, y, n - 1) + G(x, y + n - 1)
;; R(x, y, n) = R(x, y, n - 1) + G(x + n - 1, y)
;; C(x, y, n) = C(x, y - 1, n) + G(x, y + n - 1) - G(x, y - 1)
;; R(x, y, n) = R(x - 1, y, n) + G(x + n - 1, y) - G(x - 1, y)
;; We can use a memoization strategy here as well but it will be more
;; complicated because there are more free variables.

;; When searching in kernels of size n having completed a search of n - 1, if
;; the current max scoring kernel has score S, then we can disregard
;; all points (x, y) such that 4(2n - 1) < S - K(x, y, n - 1)
;; However, this means that for all points (x, y), we have to store the largest
;; kernel size for which we have calculated its value. Whenever we're looking to
;; reduce our search space in kernel size n, we check if there is a chance that
;; the n-kernel at (x, y) has a chance of being the largest by considering all
;; unseen values for that kernel to be 4.

;; Algorithm
;; Score the 3x3 kernels exhaustively. From this, create a map:
;; M: (x, y) -> (n, score)
;; n is the largest kernel for which (x, y) has been accurately scored
;; score is the score of (x, y) using an n-kernel
;; For each (x, y) when scoring with n-kernels and where the highest score is S:
;;   get M(x, y).
;;   get the maximum potential value of the uncalculated region:
;;     4 * Sum 2m - 1 for all m such that M(x, y)[n] < m and m <= n.
;;   get the maximum possible value for K(x, y, n), max-K(n)
;;     = K(x, y, M(x, y)[n]) + the maximum potential value of the uncalculated region
;;     = M(x, y)[score] + the maximum potential value of the uncalculated region
;;   if max-K(n) < S, continue without doing anything
;;   else determine and save K(x, y, n)
;;     get the true value of the uncalculated region, R
;;       For all m such that M(x, y)[n] < m and m <= n:
;;         + C(x + m - 1, y, m - 1) + R(x, y + m - 1, m)
;;     K(x, y, n) = R + M(x, y)[score]
;;     save M(x, y) = (n, K(x, y, n))
;;     if K(x, y, n) > S, set S = K(x, y, n).
;;   continue to the next point

(def answerp1 (max-grid-score 3031 300 3))

(defn -main []
  (println (max-grid-score 3031 300 5)))
