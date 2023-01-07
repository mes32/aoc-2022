(ns aoc-2022.day-02.day-02
  (:gen-class)
  (:require [aoc-2022.utils :refer [read-resource]]))

(def loses-to
  {"A" "Z" ;; rock > scissors
   "B" "X" ;; paper > rock
   "C" "Y" ;; scissors > paper
  })

(def ties-to
  {"A" "X" ;; rock == rock
   "B" "Y" ;; paper == paper
   "C" "Z" ;; scissors == scissors
  })

(def wins-to
  {"A" "Y" ;; rock < paper
   "B" "Z" ;; paper < scisors
   "C" "X" ;; scissors < rock
  })

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

(defn choose-strategy [[p1-choice intended-outcome]]
  (let [p2-choice (case intended-outcome
                    "X" (loses-to p1-choice)
                    "Y" (ties-to p1-choice)
                    "Z" (wins-to p1-choice))]
    [p1-choice p2-choice]))

(defn day-02 []
 (let [strategy-guide (->> "day_02.txt"
                           read-resource
                           (map #(clojure.string/split % #" ")))
       part1 (->> strategy-guide
                  (map score-round)
                  (apply +))
       part2 (->> strategy-guide
                  (map choose-strategy)
                  (map score-round)
                  (apply +))]
   (do (println "Part 1:" part1)
       (println "Part 2:" part2))))
