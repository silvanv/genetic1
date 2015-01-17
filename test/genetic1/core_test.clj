(ns genetic1.core-test
  (:require [clojure.test :refer :all]
            [genetic1.core :refer :all]))

(deftest a-test
  (testing "darwin failed"
    (is (=
          (darwin (generate-population 10) [1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1])
          [1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1]))))
