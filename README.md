# PlagiarismDetection

Software to compare similarity between two java files. 

https://tinyurl.com/y9eevsat

Drag and drop the files you want to compare and click run plagiarism. Result will be shown in the same page with different parts of similar code in two files highlighted with different colors.


## Design

Two given files are parsed using a JavaParser and ASTs are compared. To compare ASTs I am using a network flow algorithm - Bipartite Matching. Each node of the AST is compared with a similar node of the other AST and maximum subtree matching is found for each node. Final score for root node is calculated as 
                                      sim = 2* |M| / Va + Vb

where M is the size of matched edges, Va is number of nodes in first AST and Vb is number of nodes in second AST.

Fibonacci heap is used instead of priority queue to speed up the algorighm. 
