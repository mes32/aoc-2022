(ns aoc-2022.day-02.day-02
  (:gen-class)
  (:require [aoc-2022.utils :refer [read-resource]]))

(defn score-round [[p1-choice p2-choice]]
  (+ (case p2-choice
       "X" 1
       "Y" 2
       "Z" 3)
     (case p1-choice
       "A" (case p2-choice
             "X" 3
             "Y" 6
             "Z" 0)
       "B" (case p2-choice
             "X" 0
             "Y" 3
             "Z" 6)
       "C" (case p2-choice
             "X" 6
             "Y" 0
             "Z" 3))))

(defn day-02 []
 (let [strategy-guide (->> "day_02.txt"
                           read-resource
                           (map #(clojure.string/split % #" ")))
       part1 (->> strategy-guide
                  (map score-round)
                  (apply +))]
   (do (println "Part 1:" part1))))
