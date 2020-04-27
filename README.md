# FifteenPuzzleSolver
A tool for solving 15 Puzzle using different graph search strategies.

### Implemented search strategies:
  - Depth-first Search
  - Breadth-first Search
  - A* (using Manhattan or Hamming distance as a heuristic function)

### Usage:
<code>java -jar 15puzzle.jar &lt;strategy&gt; &lt;strategy_parameter&gt; &lt;input_file&gt; &lt;output_file&gt; &lt;info_file&gt;</code>
  
### Arguments:
<pre>
&lt;strategy&gt; - chosen search strategy, options: bfs, dfs, astr

&lt;strategy_parameter&gt; - parameter which depends on chosen strategy
    for astr - heuristic function, options: hamm, manh
    for bfs/dfs - search direction order, e.g. LDRU

&lt;input_file&gt; - file which contains the puzzle to solve

&lt;output_file&gt; - file in which the program will save the solution

&lt;info_file&gt; - file in which the program will save addition information about the solution
</pre>
