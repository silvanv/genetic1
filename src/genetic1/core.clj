(ns genetic1.core
  (:gen-class))

; individual

(def gene-size 24)
(def gene-fn #(int (rand 2)))
(def genes-fn #(repeatedly gene-size gene-fn))

(defn generate-individual
  "Generate a new random individual."
  []
  (genes-fn))

; fitness calculations

(defn calc-fitness
  "Calculate the fitness of an individual relative to a given solution."
  [solution individual]
  (reduce + (map #(if (= %1 %2) 1 0) solution individual)))

(defn calc-max-fitness
  "Calculate the maximal fitness on a solution."
  [solution]
  (calc-fitness solution solution))

(defn fitter-individual
  "Select the fitter of two individuals."
  [solution individual-a individual-b]
  (let [fitness-a (calc-fitness solution individual-a)
        fitness-b (calc-fitness solution individual-b)]
    (if (> fitness-a fitness-b) individual-a individual-b)))

; population

(defn generate-population
  "Generate a population of individuals."
  [n]
  (repeatedly n generate-individual))

(defn get-fittest
  "Get the fittest individual out of a population."
  [solution population]
  (reduce #(fitter-individual solution %1 %2) population))

; algorithm

(def mutation-rate 0.025)
(def uniform-rate 0.5)
(def tournament-size 12)

(defn mutation
  "Do the genetic mutation operator."
  [individual]
  (map #(if (>= mutation-rate (rand)) (if (= % 1) 0 1) %) individual))

(defn crossover
  "Do the genetic uniform crossover operator."
  [individual-a individual-b]
  (map #(if (>= uniform-rate (rand)) %1 %2) individual-a individual-b))

(defn build-subset
  "Build a subset of a population for the crossover."
  [n population]
  (let [t (min n (count population))]
    (repeatedly t #(nth population (int (rand t))))))

(defn crossover-selection
  "Get the fittest individual from subset of a population."
  [solution population]
  (get-fittest solution (build-subset tournament-size population)))

(defn evolve-population
  "Evolve a population.
  The fittest individual will be preserved."
  [solution population]
  (conj
    (vec
      (repeatedly
        (dec (count population))
        #(let [individual-a (crossover-selection solution population)
               individual-b (crossover-selection solution population)]
          (mutation (crossover individual-a individual-b)))))
    (get-fittest solution population)))

(defn darwin
  "Do the evolution."
  [population-size solution]
  (let [max-fitness (calc-max-fitness solution)]
    (loop [population (generate-population population-size)
           result (repeat gene-size 0)
           iteration 0]
      (if (< (calc-fitness solution result) (calc-max-fitness solution))
        (let [evolved-population (evolve-population solution population)
              fittest (get-fittest solution evolved-population)
              fitness (calc-fitness solution fittest)
              i (inc iteration)]
          (println (pr-str i '- fitness '/ max-fitness '- fittest))
          (recur evolved-population fittest i))
        result))))

; main

(defn -main []
  (darwin 10 [1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1]))
