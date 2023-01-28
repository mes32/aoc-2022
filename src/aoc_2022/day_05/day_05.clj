(ns aoc-2022.day-05.day-05
  (:gen-class)
  (:require [aoc-2022.utils :refer [read-resource]]))

(defn push-to-stacks! [stacks n c]
  (swap! stacks 
         (fn [s]
           (update-in s [n] #(conj % c)))))

(defn get-from-stacks [stacks n]
  (-> @stacks (nth n) first))

(defn pop-from-stacks! [stacks n]
  (swap! stacks 
         (fn [s]
           (update-in s [n] #(drop 1 %)))))

(defn get-top-layer [stacks]
  (clojure.string/join (for [stack @stacks]
                         (-> stack first str))))

(defn init-stacks [raw-data]
  (let [reversed-data (reverse raw-data)
        num-stacks (-> reversed-data (nth 1) (clojure.string/split #" ") last Integer/parseInt)
        stacks (atom (vec (repeat num-stacks '())))
        crates-data (drop 2 reversed-data)]
    (do 
       (dorun (map (fn [crates-row]
                     (doseq [i (range 0 num-stacks)]
                       (let [crate-index (-> i (* 4) (+ 1))
                             char-at-index (get crates-row crate-index)]
                         (when (not= char-at-index \space)
                           (push-to-stacks! stacks i char-at-index)))))
                   crates-data))
      stacks)))

(defn get-instruction [row]
  (let [raw-ints (-> row (clojure.string/split #"move | from | to "))]
    {:count (-> raw-ints (nth 1) Integer/parseInt)
     :from (-> raw-ints (nth 2) Integer/parseInt (- 1))
     :to (-> raw-ints (nth 3) Integer/parseInt (- 1))}))

(defn apply-instruction! [instruction stacks]
  (let [count (:count instruction)
        from-index (:from instruction)
        to-index (:to instruction)]
    (doseq [i (range 0 count)]
      (let [char-to-move (get-from-stacks stacks from-index)]
        (when (and (some? char-to-move)
                   (not= char-to-move \space))
          (do (pop-from-stacks! stacks from-index))
              (push-to-stacks! stacks to-index char-to-move))))))

(defn apply-instruction-2! [instruction stacks]
  (let [count (:count instruction)
        from-index (:from instruction)
        to-index (:to instruction)
        chars-to-move (reverse (vec (take count (nth @stacks from-index))))]
    (do
      (doseq [i (range 0 count)]
        (pop-from-stacks! stacks from-index))
      (doseq [c chars-to-move]
        (push-to-stacks! stacks to-index c)))))

(defn day-05 []
 (let [stacks (->> "day_05.txt"
                   read-resource
                   (filter #(not (clojure.string/starts-with? % "move")))
                   init-stacks)
       instructions (->> "day_05.txt"
                         read-resource
                         (filter #(clojure.string/starts-with? % "move"))
                         (map get-instruction))
       _ (doseq [instruction instructions]
           (apply-instruction! instruction stacks))
       part1 (get-top-layer stacks)
       stacks-2 (->> "day_05.txt"
                     read-resource
                     (filter #(not (clojure.string/starts-with? % "move")))
                     init-stacks)
        _ (doseq [instruction instructions]
           (apply-instruction-2! instruction stacks-2))
       part2 (get-top-layer stacks-2)]
   (do (println "Part 1:" part1)
       (println "Part 2:" part2))))
