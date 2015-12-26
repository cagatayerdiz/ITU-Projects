# search.py
# ---------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


"""
In search.py, you will implement generic search algorithms which are called by
Pacman agents (in searchAgents.py).
"""

import util

class SearchProblem:
    """
    This class outlines the structure of a search problem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
    """

    def getStartState(self):
        """
        Returns the start state for the search problem.
        """
        util.raiseNotDefined()

    def isGoalState(self, state):
        """
          state: Search state

        Returns True if and only if the state is a valid goal state.
        """
        util.raiseNotDefined()

    def getSuccessors(self, state):
        """
          state: Search state

        For a given state, this should return a list of triples, (successor,
        action, stepCost), where 'successor' is a successor to the current
        state, 'action' is the action required to get there, and 'stepCost' is
        the incremental cost of expanding to that successor.
        """
        util.raiseNotDefined()

    def getCostOfActions(self, actions):
        """
         actions: A list of actions to take

        This method returns the total cost of a particular sequence of actions.
        The sequence must be composed of legal moves.
        """
        util.raiseNotDefined()


def tinyMazeSearch(problem):
    """
    Returns a sequence of moves that solves tinyMaze.  For any other maze, the
    sequence of moves will be incorrect, so only use this for tinyMaze.
    """
    from game import Directions
    s = Directions.SOUTH
    w = Directions.WEST
    return  [s, s, w, s, w, w, s, w]

def depthFirstSearch(problem):
	#DFS is implemented using stack
	generatedNodes = 0
	fringe = util.Stack()
	fringe.push( (problem.getStartState(), [], []) )
	
	while not fringe.isEmpty():
		node, actions, visited = fringe.pop()

		for coordinate, direction, steps in problem.getSuccessors(node):
			if not coordinate in visited:
				if problem.isGoalState(coordinate):
					print "Search nodes generated: %d" % generatedNodes
					return actions + [direction]
				fringe.push((coordinate, actions + [direction], visited + [node] ))
				generatedNodes = generatedNodes + 1
	
	return []
    #util.raiseNotDefined()

def depthFirstSearchTree(problem):
	#DFS is implemented using stack
	generatedNodes = 0
	fringe = util.Stack()
	fringe.push( (problem.getStartState(), []) )
	
	while not fringe.isEmpty():
		node, actions = fringe.pop()

		for coordinate, direction, steps in problem.getSuccessors(node):
			if problem.isGoalState(coordinate):
				print "Search nodes generated: %d" % generatedNodes
				return actions + [direction]
			fringe.push((coordinate, actions + [direction]))
			generatedNodes = generatedNodes + 1

	return []
	
def breadthFirstSearch(problem):
	#BFS is implemented using queue
	generatedNodes = 0
	fringe = util.Queue()
	fringe.push( (problem.getStartState(), []) )

	visited = []
	while not fringe.isEmpty():
		node, actions = fringe.pop()

		for coordinate, direction, steps in problem.getSuccessors(node):
			if not coordinate in visited:
				if problem.isGoalState(coordinate):
					print "Search nodes generated: %d" % generatedNodes
					return actions + [direction]
				fringe.push((coordinate, actions + [direction]))
				visited.append(coordinate)
				generatedNodes = generatedNodes + 1
	
	return []
	#util.raiseNotDefined()

def breadthFirstSearchTree(problem):
	#BFS is implemented using queue
	generatedNodes = 0
	fringe = util.Queue()
	fringe.push( (problem.getStartState(), []) )

	while not fringe.isEmpty():
		node, actions = fringe.pop()

		for coordinate, direction, steps in problem.getSuccessors(node):
				if problem.isGoalState(coordinate):
					print "Search nodes generated: %d" % generatedNodes
					return actions + [direction]
				fringe.push((coordinate, actions + [direction]))
				generatedNodes = generatedNodes + 1
	
	return []
	
def uniformCostSearch(problem):
    """Search the node of least total cost first."""
    "*** YOUR CODE HERE ***"
    util.raiseNotDefined()

def nullHeuristic(state, problem=None):
    """
    A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
    """
    return 0

def admissibleHeuristic(position, problem=None):
	#The Manhattan distance heuristic for admissible heuristic
	xy1 = position
	xy2 = problem.goal
	
	return abs(xy1[0] - xy2[0]) + abs(xy1[1] - xy2[1])
	
def aStarSearchNull(problem, heuristic=nullHeuristic):
	"""Search the node that has the lowest combined cost and heuristic first."""
	generatedNodes = 0
	set = []
	fringe = util.PriorityQueue()
	start = problem.getStartState()
	fringe.push( (start, []), nullHeuristic(start, problem))

	while not fringe.isEmpty():
		node, actions = fringe.pop()

		if problem.isGoalState(node):
			print "Search nodes generated: %d" % generatedNodes
			return actions

		set.append(node)

		for coordinate, direction, cost in problem.getSuccessors(node):
			if not coordinate in set:
				new_actions = actions + [direction]
				score = problem.getCostOfActions(new_actions) + nullHeuristic(coordinate, problem)
				fringe.push( (coordinate, new_actions), score)
				generatedNodes = generatedNodes + 1

	return []
	#util.raiseNotDefined()

def aStarSearchAdmissible(problem, heuristic=admissibleHeuristic):
	"""Search the node that has the lowest combined cost and heuristic first."""
	generatedNodes = 0
	set = []
	fringe = util.PriorityQueue()
	start = problem.getStartState()
	fringe.push( (start, []), admissibleHeuristic(start, problem))

	while not fringe.isEmpty():
		node, actions = fringe.pop()

		if problem.isGoalState(node):
			print "Search nodes generated: %d" % generatedNodes 
			return actions

		set.append(node)

		for coordinate, direction, cost in problem.getSuccessors(node):
			if not coordinate in set:
				new_actions = actions + [direction]
				score = problem.getCostOfActions(new_actions) + admissibleHeuristic(coordinate, problem)
				fringe.push( (coordinate, new_actions), score)
				generatedNodes = generatedNodes + 1

	return []
	#util.raiseNotDefined()
	
	
# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
bfsT = breadthFirstSearchTree
dfsT = depthFirstSearchTree
astarN = aStarSearchNull
astarA = aStarSearchAdmissible
ucs = uniformCostSearch
