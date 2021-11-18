import java.util.*;
import java.util.stream.Collectors;

public class Airport extends AirportBase {
    /**
     * Creates a new AirportBase instance with the given capacity.
     *
     * @param capacity capacity of the airport shuttles
     *                 (same for all shuttles)
     */
    private airportGraph airportGraph;
    public Airport(int capacity) {
        super(capacity);
        this.airportGraph = new airportGraph();
    }

    /**
     * Given a terminal and a shuttle, returns the other terminal that the
     * shuttle travels between.
     *
     * @param shuttle  shuttle to look for opposite terminal on
     * @param terminal terminal to find opposite of
     * @return opposite terminal or null if the shuttle is not incident to
     * the given terminal
     */
    @Override
    public TerminalBase opposite(ShuttleBase shuttle, TerminalBase terminal) {
        return this.airportGraph.opposite(shuttle,terminal);
    }

    /**
     * Adds the given terminal to the airport, and returns the added terminal.
     *
     * @param terminal terminal to add
     * @return terminal that was added
     */
    @Override
    public TerminalBase insertTerminal(TerminalBase terminal) {
        this.airportGraph.addTerminal(terminal);
        return terminal;
    }

    /**
     * Creates and returns a new shuttle connecting origin to destination.
     * All shuttles are bidirectional.
     *
     * @param origin      origin terminal of shuttle
     * @param destination destination terminal of shuttle
     * @param time        time it takes to go from origin to destination, in minutes
     * @return newly created shuttle
     */
    @Override
    public ShuttleBase insertShuttle(TerminalBase origin, TerminalBase destination, int time) {
        ShuttleBase add = new Shuttle(origin, destination, time);
        this.airportGraph.addShuttle(add);
        return add;
    }

    /**
     * Removes the given terminal and all of its incident shuttles from
     * the airport. All shuttles going to/from the given terminal should
     * be removed.
     *
     * @param terminal terminal to remove
     * @return true if removed successfully, false otherwise (if the terminal
     * was not in the airport)
     */
    @Override
    public boolean removeTerminal(TerminalBase terminal) {
        return this.airportGraph.removeTerminal(terminal);
    }

    /**
     * Removes the given shuttle from the airport.
     *
     * @param shuttle shuttle to remove
     * @return true if removed successfully, false otherwise (if the shuttle
     * was not in the airport)
     */
    @Override
    public boolean removeShuttle(ShuttleBase shuttle) {
        return this.airportGraph.removeShuttle(shuttle);
    }

    /**
     * Returns a list of all shuttles incident to the given terminal.
     *
     * @param terminal terminal to find incident shuttles of
     * @return list of incident shuttles
     */
    @Override
    public List<ShuttleBase> outgoingShuttles(TerminalBase terminal) {
        return this.airportGraph.outgoingShuttles(terminal);
    }

    /**
     * Returns the shortest path between the given origin and destination
     * terminals. The shortest path is the path that requires the least number
     * of shuttles.
     * <p>
     * The returned Path consists of a list of terminals in the path, and the
     * total time spent travelling along the path. The first element of the
     * Path's terminal list should be the given origin terminal, and the last
     * element should be the given destination terminal. Any intermediate
     * terminals in the path should appear in the list in the order travelled.
     *
     * @param origin      the starting terminal
     * @param destination the destination terminal
     * @return Path instance containing the list of terminals and the total
     * time taken in the path, or null if destination is not reachable from
     * origin
     */
    @Override
    public Path findShortestPath(TerminalBase origin, TerminalBase destination) {
        return this.airportGraph.leastShuttles(origin,destination);
    }

    /**
     * Returns the fastest path between the given origin and destination
     * terminals. The fastest path has the lowest total time spent travelling
     * and waiting.
     * <p>
     * The returned Path consists of a list of terminals in the path, and the
     * total time spent travelling along the path. The first element of the
     * Path's terminal list should be the given origin terminal, and the last
     * element should be the given destination terminal. Any intermediate
     * terminals in the path should appear in the list in the order travelled.
     *
     * @param origin      the starting terminal
     * @param destination the destination terminal
     * @return Path instance containing the list of terminals and the total
     * time taken in the path, or null if destination is not reachable from
     * origin
     */
    @Override
    public Path findFastestPath(TerminalBase origin, TerminalBase destination) {
        return this.airportGraph.shortestShuttles(origin,destination);
    }

    /* Implement all the necessary methods of the Airport here */

    static class Terminal extends TerminalBase {
        /**
         * Creates a new TerminalBase instance with the given terminal ID
         * and waiting time.
         *
         * @param id          terminal ID
         * @param waitingTime waiting time for the terminal, in minutes
         * @param check
         * @param distance
         * @param father
         */

        private int check;
        private int distance;
        private TerminalBase father;

        public Terminal(String id, int waitingTime) {
            super(id, waitingTime);
            this.check = 0;
            this.distance = Integer.MAX_VALUE;
            this.father = null;
        }
        /* Implement all the necessary methods of the Terminal here */
        public int getCheck () {
            return this.check;
        }

        public int getDistance (){
            return this.distance;
        }

        public TerminalBase getFather () {
            return this.father;
        }

        public void setCheck (int i) {
            this.check = i;
        }

        public void setDistance (int i) {
            this.distance = i;
        }

        public void setFather (TerminalBase i) {
            this.father = i;
        }
    }

    static class Shuttle extends ShuttleBase {
        /**
         * Creates a new ShuttleBase instance, travelling from origin to
         * destination and requiring 'time' minutes to travel.
         *
         * @param origin      origin terminal
         * @param destination destination terminal
         * @param time        time required to travel, in minutes
         */

        private int capacity;
        private boolean check;
        public Shuttle(TerminalBase origin, TerminalBase destination, int time) {
            super(origin, destination, time);
            this.capacity = 0;
            this.check = false;
        }
        public void setCapacity (int i) {
            this.capacity = i;
        }
        public void setCheck (boolean t) {
            this.check = t;
        }
        public int getCapacity() {
            return this.capacity;
        }
        public boolean getCheck() {
            return this.check;
        }
        /* Implement all the necessary methods of the Shuttle here */
    }

    /*
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        REMOVE THE MAIN FUNCTION BEFORE SUBMITTING TO THE AUTOGRADER
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        The following main function is provided for simple debugging only

        Note: to enable assertions, you need to add the "-ea" flag to the
        VM options of Airport's run configuration
     */
//    public static void main(String[] args) {
//        Airport a = new Airport(3);
//        Terminal terminalA = (Terminal) a.insertTerminal(new Terminal("A", 1));
//        Terminal terminalB = (Terminal) a.insertTerminal(new Terminal("B", 3));
//        Terminal terminalC = (Terminal) a.insertTerminal(new Terminal("C", 4));
//        Terminal terminalD = (Terminal) a.insertTerminal(new Terminal("D", 2));
//
//        Shuttle shuttle1 = (Shuttle) a.insertShuttle(terminalA, terminalB, 2);
//        Shuttle shuttle2 = (Shuttle) a.insertShuttle(terminalA, terminalC, 5);
//        Shuttle shuttle3 = (Shuttle) a.insertShuttle(terminalA, terminalD, 18);
//        Shuttle shuttle4 = (Shuttle) a.insertShuttle(terminalB, terminalD, 8);
//        Shuttle shuttle5 = (Shuttle) a.insertShuttle(terminalC, terminalD, 15);
//
//
//        // Opposite
//        assert a.opposite(shuttle1, terminalA).getId().equals("B");
//
//        // Outgoing Shuttles
//        assert a.outgoingShuttles(terminalA).stream()
//                .map(ShuttleBase::getTime)
//                .collect(Collectors.toList()).containsAll(List.of(2, 5, 18));
//
//        // Remove Terminal
//        a.removeTerminal(terminalC);
//        assert a.outgoingShuttles(terminalA).stream()
//                .map(ShuttleBase::getTime)
//                .collect(Collectors.toList()).containsAll(List.of(2, 18));
//
//
//        // Shortest path
//        Path shortestPath = a.findShortestPath(terminalA, terminalD);
//        assert shortestPath.terminals.stream()
//                .map(TerminalBase::getId)
//                .collect(Collectors.toList()).equals(List.of("A", "D"));
//        assert shortestPath.time == 19;
//
//        // Fastest path
//        Path fastestPath = a.findFastestPath(terminalA, terminalD);
//        assert fastestPath.terminals.stream()
//                .map(TerminalBase::getId)
//                .collect(Collectors.toList()).equals(List.of("A", "B", "D"));
//        assert fastestPath.time == 14;
//    }

}

class airportGraph {
    private List<Map<AirportBase.TerminalBase, AirportBase.ShuttleBase>> graphList;
    private List<AirportBase.TerminalBase> terminals;
    private List<AirportBase.ShuttleBase> shuttles;

    airportGraph() {
        this.graphList = new ArrayList<>();
        this.terminals = new ArrayList<>();
        this.shuttles = new ArrayList<>();
    }

    private Map<AirportBase.TerminalBase, AirportBase.ShuttleBase> terminalInfo (AirportBase.TerminalBase terminal) {
        if (!this.terminals.contains(terminal)) {
            return null;
        } else {
            int index = this.terminals.indexOf(terminal);
            return this.graphList.get(index);
        }
    }

    public AirportBase.TerminalBase opposite (AirportBase.ShuttleBase shuttle, AirportBase.TerminalBase terminal) {
        if (!this.shuttles.contains(shuttle) || !this.terminals.contains(terminal) ) {
            return null;
        } else {
            AirportBase.TerminalBase start = shuttle.getOrigin();
            AirportBase.TerminalBase end = shuttle.getDestination();
            if (terminal.equals(start)) {
                return end;
            } else if (terminal.equals(end)) {
                return start;
            } else {
                return null;
            }
        }
    }

    public List<AirportBase.ShuttleBase> outgoingShuttles(AirportBase.TerminalBase terminal) {
        if (!this.terminals.contains(terminal)) {
            return null;
        } else {
            List<AirportBase.ShuttleBase> result = new ArrayList<>();
            Map<AirportBase.TerminalBase, AirportBase.ShuttleBase> info = terminalInfo(terminal);
            if (info != null) {
                for (Map.Entry<AirportBase.TerminalBase, AirportBase.ShuttleBase> entry: info.entrySet()) {
                    result.add(entry.getValue());
                }
                return result;
            } else {
                return null;
            }

        }
    }

    public void addTerminal (AirportBase.TerminalBase terminal) {
        this.terminals.add(terminal);
        int index = this.terminals.indexOf(terminal);
        Map<AirportBase.TerminalBase, AirportBase.ShuttleBase> terminalShuttleMap = new HashMap<>();
        this.graphList.add(index, terminalShuttleMap);
    }

    public void addShuttle (AirportBase.ShuttleBase shuttle) {
        this.shuttles.add(shuttle);
        AirportBase.TerminalBase start = shuttle.getOrigin();
        AirportBase.TerminalBase end = shuttle.getDestination();
        this.graphList.get(this.terminals.indexOf(start)).put(end, shuttle);
//        this.graphList.get(this.terminals.indexOf(end)).put(start, shuttle);
    }

    public Boolean removeTerminal (AirportBase.TerminalBase terminal) {
        if (!this.terminals.contains(terminal)) {
            return false;
        } else {
            int index = this.terminals.indexOf(terminal);
            Map<AirportBase.TerminalBase, AirportBase.ShuttleBase> removalInfo  = this.graphList.get(index);

            this.terminals.remove(index);
            this.graphList.remove(index);

//            List<AirportBase.TerminalBase> terminalBaseRemoval = new ArrayList<>();
            List<AirportBase.ShuttleBase> shuttleBaseRemoval = new ArrayList<>();
            for (Map.Entry<AirportBase.TerminalBase, AirportBase.ShuttleBase> entry : removalInfo.entrySet()) {
//                terminalBaseRemoval.add(entry.getKey());
                shuttleBaseRemoval.add(entry.getValue());
            }

//            for (AirportBase.TerminalBase terminalBase : terminalBaseRemoval) {
//                int indexToRemove = this.terminals.indexOf(terminalBase);
//                this.graphList.get(indexToRemove).remove(terminalBase);
//            }

            for (AirportBase.ShuttleBase shuttleBase : shuttleBaseRemoval) {
                this.shuttles.remove(shuttleBase);
            }

            List<AirportBase.ShuttleBase> endShuttle = new ArrayList<>();
            for (AirportBase.ShuttleBase shuttle : this.shuttles) {
                if (shuttle.getDestination().equals(terminal)) {
                   endShuttle.add(shuttle);
                }
            }

            for (AirportBase.ShuttleBase shuttle : endShuttle) {
                removeShuttle(shuttle);
            }
            return true;
        }
    }

    public boolean removeShuttle (AirportBase.ShuttleBase shuttle) {
        if (!this.shuttles.contains(shuttle)) {
            return false;
        } else {
            AirportBase.TerminalBase start = shuttle.getOrigin();
            AirportBase.TerminalBase end = shuttle.getDestination();

            this.shuttles.remove(shuttle);

            this.graphList.get(this.terminals.indexOf(start)).remove(end);
//            this.graphList.get(this.terminals.indexOf(end)).remove(start);

            return true;
        }

    }



    private void BFS (AirportBase.TerminalBase start) {
        AirportBase.Path result = new AirportBase.Path(new ArrayList<>(),0);
        for (AirportBase.TerminalBase t : this.terminals) {
            ((Airport.Terminal)t).setDistance(Integer.MAX_VALUE);
            ((Airport.Terminal)t).setCheck(0);
            ((Airport.Terminal)t).setFather(null);
        }
        ((Airport.Terminal)start).setCheck(1);
        ((Airport.Terminal)start).setDistance(0);
        Queue<Airport.Terminal> terminalQueue= new LinkedList<>();
        terminalQueue.add((Airport.Terminal)start);
        while (!terminalQueue.isEmpty()) {
            Airport.Terminal examining = terminalQueue.remove();
            for (AirportBase.ShuttleBase i : outgoingShuttles (examining)) {
                Airport.Terminal t = (Airport.Terminal)i.getDestination();
                if (t.getCheck() == 0) {
                    t.setCheck(1);
                    t.setDistance(examining.getDistance() + 1);
                    t.setFather(examining);
                    terminalQueue.add(t);
                }
            }
            examining.setCheck(2);
        }
    }

    public AirportBase.Path leastShuttles (AirportBase.TerminalBase start, AirportBase.TerminalBase end) {
        BFS(start);
        Airport.Terminal t = (Airport.Terminal) end;
        if (t.getDistance() == Integer.MAX_VALUE) {
            return null;
        } else {
            AirportBase.Path path = new AirportBase.Path(new ArrayList<>(), 0);
            path.terminals.add(t);
            path.time += t.getWaitingTime();
            while (t.getFather() != null){
                AirportBase.TerminalBase father = t.getFather();
                Airport.Shuttle shuttle = (Airport.Shuttle)this.graphList.get(this.terminals.indexOf(father)).get(t);
                shuttle.setCapacity(shuttle.getCapacity() - 1);
                if (shuttle.getCapacity() == 0) {
                    removeShuttle(shuttle);
                }
                path.time += shuttle.getTime();
                t = (Airport.Terminal) father;
                path.terminals.add(t);
                path.time += t.getWaitingTime();
            }
            Collections.reverse(path.terminals);
            return path;
        }
    }

    private void relax(AirportBase.TerminalBase a, AirportBase.TerminalBase b, int edgeWeight) {
        if (((Airport.Terminal)b).getDistance() >
                ((Airport.Terminal)a).getDistance() + edgeWeight + b.getWaitingTime()) {
            ((Airport.Terminal)b).setDistance(((Airport.Terminal)a).getDistance() + edgeWeight + b.getWaitingTime());
            ((Airport.Terminal)b).setFather(a);
        }
    }


    public void dijkstra(AirportBase.TerminalBase start) {
        PriorityQueue<Airport.Terminal> terminalQueue= new PriorityQueue<Airport.Terminal>(
                5, Comparator.comparing(Airport.Terminal::getDistance));
        for (AirportBase.TerminalBase t : this.terminals) {
            ((Airport.Terminal)t).setDistance(Integer.MAX_VALUE);
            ((Airport.Terminal)t).setFather(null);
            if (t.equals(start)) {
                ((Airport.Terminal)start).setDistance(start.getWaitingTime());
            }
            terminalQueue.add((Airport.Terminal)t);
        }

        while (!terminalQueue.isEmpty()) {
            Airport.Terminal terminal = terminalQueue.poll();
            Map<AirportBase.TerminalBase, AirportBase.ShuttleBase> slot =
                    this.graphList.get(this.terminals.indexOf(terminal));
            if (slot.size()!= 0) {
                for (Map.Entry<AirportBase.TerminalBase, AirportBase.ShuttleBase> entry : slot.entrySet()) {
                    relax(terminal, entry.getKey(), entry.getValue().getTime());
                }
            }
        }

    }

    public AirportBase.Path shortestShuttles (AirportBase.TerminalBase start, AirportBase.TerminalBase end) {
        BFS(start);
        Airport.Terminal t = (Airport.Terminal) end;
        if (t.getDistance() == Integer.MAX_VALUE) {
            return null;
        } else {
            dijkstra(start);

            AirportBase.Path path = new AirportBase.Path(new ArrayList<>(), 0);
            t.setDistance(t.getDistance() - t.getWaitingTime());

            path.terminals.add(t);
            path.time += t.getDistance();

            while (t.getFather() != null){
                AirportBase.TerminalBase father = t.getFather();
                Airport.Shuttle shuttle = (Airport.Shuttle)this.graphList.get(this.terminals.indexOf(father)).get(t);

                shuttle.setCapacity(shuttle.getCapacity() - 1);
                if (shuttle.getCapacity() == 0) {
                    removeShuttle(shuttle);
                }

                t = (Airport.Terminal) father;
                path.terminals.add(t);
                path.time += t.getDistance();
        }
            Collections.reverse(path.terminals);
            return path;
        }
    }
}