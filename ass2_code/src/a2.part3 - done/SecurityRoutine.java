import java.util.*;
import java.util.stream.Collectors;

public class SecurityRoutine extends SecurityRoutineBase {

    /* Implement all the necessary methods here */
    private List<Map<AreaBase, AreaBase>> graphList;
    private List<AreaBase> areaBases;
    private List<AreaBase> fathers;
    private List<Integer> check;
    private List<Integer[]> timeStamp;
    private int time = 0;
    /*
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        REMOVE THE MAIN FUNCTION BEFORE SUBMITTING TO THE AUTOGRADER
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        The following main function is provided for simple debugging only

        Note: to enable assertions, you need to add the "-ea" flag to the
        VM options of SecurityRoutine's run configuration
     */
//    public static void main(String[] args) {
//        SecurityRoutine g = new SecurityRoutine();
//        AreaBase areaZ = g.insertArea(new AreaBase("Z"));
//        AreaBase areaA = g.insertArea(new AreaBase("A"));
//        AreaBase areaB = g.insertArea(new AreaBase("B"));
//        AreaBase areaC = g.insertArea(new AreaBase("C"));
//
//        g.addOrder(areaZ, areaA);
//        g.addOrder(areaA, areaB);
//        g.addOrder(areaB, areaC);
//        g.addOrder(areaA, areaC);
//        List<AreaBase> t = g.calculateTotalOrder();
//
//        assert t.stream()
//                .map(AreaBase::getId)
//                .collect(Collectors.toList()).equals(List.of("Z", "A", "B", "C"));
//    }

    public SecurityRoutine() {
        this.graphList = new ArrayList<>();
        this.areaBases = new ArrayList<>();
        this.check = new ArrayList<>();
        this.timeStamp = new ArrayList<>();
        this.fathers = new ArrayList<>();
    }


    /**
     * Adds the given area to the SecurityBase, and returns the added area.
     *
     * @param area area to add
     * @return area that was added
     */
    @Override
    public AreaBase insertArea(AreaBase area) {
        this.areaBases.add(area);
        int index = this.areaBases.indexOf(area);

        Map<AreaBase, AreaBase> mapToAdd = new HashMap<>();
        mapToAdd.put(area, null);
        this.graphList.add(index, mapToAdd);

        this.check.add(index, 0);
        Integer[] arraysToAdd = new Integer[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.timeStamp.add(index, arraysToAdd);

        this.fathers.add(index, null);
        return area;
    }

    /**
     * Adds the order between two areas, where area1 should be checked
     * before area2.
     *
     * @param area1 area that should be checked before
     * @param area2 area that should be checked after
     */
    @Override
    public void addOrder(AreaBase area1, AreaBase area2) {
        int index = this.areaBases.indexOf(area1);
        this.graphList.get(index).put(area1, area2);
    }

    /**
     * Calculates and returns the order in which the areas must be checked
     * by the security team.
     *
     * @return list of areas in order, or null if it is not possible to define
     * the order
     */
    @Override
    public List<AreaBase> calculateTotalOrder() {
        return DFS();
    }

    private List<AreaBase> DFS () {
        List<AreaBase> result = new LinkedList<>();
        for (AreaBase a : this.areaBases) {
            int index = this.areaBases.indexOf(a);
            this.check.set(index,0);
            this.fathers.set(index, null);
        }
        this.time = 0;
        for (AreaBase a : this.areaBases) {
            int index = this.areaBases.indexOf(a);
            if (this.check.get(index) == 0) {
                result.add(DFSVisit(a));
            }
        }
        return result;
    }

    private AreaBase DFSVisit(AreaBase a) {
        this.time += 1;
        int indexA = this.areaBases.indexOf(a);
        Integer[] modified = this.timeStamp.get(indexA);
        modified[0] = this.time;
        this.check.set(indexA, 1);
        Collection<AreaBase> values = this.graphList.get(indexA).values();
        for (AreaBase b : values) {
            if (b != null) {
                int indexB = this.areaBases.indexOf(b);
                if (this.check.get(indexB) == 1) {
                    this.fathers.set(indexB, a);
                    DFSVisit(b);
                }
            }
        }
        this.check.set(indexA, 2);
        this.time += 1;
        modified[1] = this.time;
        return a;
    }
}


