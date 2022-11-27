# HyFlex
HyFlex (Hyper-heuristics Flexible framework) is a Java object oriented framework for the implementation and comparison of different iterative general-purpose heuristic search algorithms (also called hyper-heuristics).

See the HyFlex web site for details: http://www.asap.cs.nott.ac.uk/external/chesc2011 - [web archive](https://web.archive.org/web/20210518021139/http://www.asap.cs.nott.ac.uk/external/chesc2011/)

The goal of this project is 1. to collect hyper-heurisics from CHeSC 2011 (Cross-domain Heuristic Search Challenge) and enable to reproduce the results of the challenge and 2. to provide problem instance extension of the original HyFlex benchmark

Hyper-heuristic implementers might find this environment helpful for comparing own results with other approaches.

## Unit metric
We have developed a metric that assigns a number from the unit interval `<0,1>` (that's why we call it the unit metric) to the hyper-heuristic's results quality.

The lower bound value of the interval corresponds to the quality of an easily obtainable solution (e.g. a greedy solution) and the upper bound is the quality of the optimal solution. 

The more the hyper-heuristic gets the evaluation closer to `1.0` the better results it provides closer to the optimal solutions.

On the other hand, closer to the `0.0` value the hyper-heuristic is not much better than a greedy solution generator.

The overall unit metric value is an aggregation of partial evaluations.
First, solutions for each problem instance (provided by the hyper-heuristic) are evaluated and mapped onto the unit interval. 
Second, the problem evaluation is an aggregation of the problem instances evaluations obtained in the previous step. The weighted mean is calculated with weights corresponding to instance sizes.
Finally, the overall value is obtained as a simple mean of the problem evaluations. See the formula below.

![Unit-metric](docs/unit-metric/unit-metric-formula.svg)

## List of available hyper-heuristics
The following is the list of CHeSC 2011 competition hyper-heuristics that are available in this repository. See details in the `hyflex-hyperheuristics` folder for each hyper-heuristic.

The table shows evaluated solutions for each hyper-heuristic (each run with 120s timeout and 5 repeats) using our unit metric.

![Hyper-heuristics](docs/heatmap_120_5.svg)
