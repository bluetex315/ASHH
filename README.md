# HyFlex
HyFlex (Hyper-heuristics Flexible framework) is a Java object oriented framework for the implementation and comparison of different iterative general-purpose heuristic search algorithms (also called hyper-heuristics).

See the HyFlex web site for details: http://www.asap.cs.nott.ac.uk/external/chesc2011 - [web archive](https://web.archive.org/web/20210518021139/http://www.asap.cs.nott.ac.uk/external/chesc2011/)

The goal of this project is 
1. to collect hyper-heurisics from CHeSC 2011 (Cross-domain Heuristic Search Challenge) and enable to reproduce the results of the challenge and 
2. to provide problem instance extension of the original HyFlex benchmark
3. to conduct cross domain algorithm selection on the various available Hyper-Heuristics on the extended problem instances

## List of available hyper-heuristics
The following is the list of CHeSC 2011 competition hyper-heuristics that are available in this repository. See details in the `hyflex-hyperheuristics` folder for each hyper-heuristic.

The table shows evaluated solutions for each hyper-heuristic (each run with 120s timeout and 5 repeats) using our unit metric.

![Hyper-heuristics](docs/heatmap_120_5.svg)
