import exceptions.DirectionException;
import model.Direction;
import model.Node;
import solution.Astar;
import solution.BFS;
import solution.DFS;
import solution.Solution;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Main {
//    private static void printResult(Node result) {
//        if (result != null) {
//            System.out.println("(" + result.getPath().size() + ") " + Direction.listToString(result.getPath()));
//            Solution.printState(result.getState());
//        }
//
//        else {
//            System.out.println(-1);
//        }
//    }

    private static void createFiles(Path path) throws IOException {
        if (path.getParent() != null && !Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }

        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }

    /**********************************************************
    Arguments:
        0 - strategy
                dfs, bfs, astar
        1 - strategy parameter
                dfs/bfs: search direction order, e. g. LDRU
                astar: metrics - hamm/manh
        2 - input file
        3 - output file
        4 - additional info output file
    **********************************************************/
    public static void main(String[] args) {
        //args size check
            if (args.length < 5) {
                if (args.length == 3) {
                    args = Arrays.copyOf(args, 5);
                    args[3] = "out";
                    args[4] = "stats";
                }

                else {
                    System.err.println("Za mało argumentów!");
                    return;
                }
            }

        //strategy check
            if (!args[0].equals("dfs") && !args[0].equals("bfs") && !args[0].equals("astr")) {
                System.err.println("Nieprawidłowa strategia!");
                return;
            }

        List<Direction> order = null;

        //dfs, bfs - check parameter
            if (!args[0].equals("astr")) {
                try {
                    order = Direction.listFromString(args[1]);
                }

                catch (DirectionException e) {
                    System.err.println("Nieprawidłowy porządek przeszukiwania sąsiedztwa!");
                    return;
                }
            }

        //astar - check parameter
            else if (!args[1].equals("hamm") && !args[1].equals("manh")) {
                System.err.println("Nieprawidłowa metryka!");
                return;
            }

        //input file check
            Node puzzle = null;

            try {
                List<String> lines = Files.readAllLines(Paths.get(args[2]), StandardCharsets.UTF_8);
                puzzle = Node.fromStringList(lines);
            }

            catch (IOException e) {
                System.err.println("Nie znaleziono pliku wejściowego!");
                return;
            }

        //output file check
            Path outputPath = Paths.get(args[3]);

            try {
                createFiles(outputPath);
            }

            catch (IOException e) {
                System.err.println("Nie można utworzyć pliku wyjściowego!");
                return;
            }

        //additional output file check
            Path infoPath = Paths.get(args[4]);

            try {
                createFiles(infoPath);
            }

            catch (IOException e) {
                System.err.println("Nie można utworzyć dodatkowego pliku wyjściowego!");
                return;
            }

        Node result;
        long beginTime, endTime;
        int visitedNodeCount, processedNodeCount, reachedDepth;

        //START
            if (args[0].equals("astr")) {
                var algorithm = new Astar();

                result = algorithm.solve(puzzle, args[1]);
                beginTime = algorithm.getBeginTime();
                endTime = algorithm.getEndTime();

                visitedNodeCount = algorithm.getVisitedNodesCount();
                processedNodeCount = algorithm.getProcessedNodesCount();
                reachedDepth = algorithm.getReachedDepth();
            }

            else {
                var algorithm = args[0].equals("dfs") ? new DFS() : new BFS();

                result = algorithm.solve(puzzle, order);
                beginTime = algorithm.getBeginTime();
                endTime = algorithm.getEndTime();

                visitedNodeCount = algorithm.getVisitedNodesCount();
                processedNodeCount = 0;
                reachedDepth = algorithm.getReachedDepth();
            }

        //output
            try {
                if (result != null) {
                    var path = result.getPath();
                    Files.writeString(outputPath, path.size() + "\n" + Direction.listToString(path));
                }

                else {
                    Files.writeString(outputPath, "-1");
                }
            }

            catch (IOException e) {
                System.err.println("Zapis do pliku wyjściowego nieudany!");
            }

        //additional output
        double time = -1;

            try {
                var infoBuilder = new StringBuilder();

                var depth = (result != null) ? result.getPath().size() : -1;
                infoBuilder.append(depth).append("\n");

                for (int v : List.of(visitedNodeCount, processedNodeCount, reachedDepth)) {
                    infoBuilder.append(v).append("\n");
                }

                time = TimeUnit.MICROSECONDS.convert(endTime - beginTime, TimeUnit.NANOSECONDS) / 1000.0;
                Files.writeString(infoPath, infoBuilder.append(String.format("%.03f", time)));
            }

            catch (IOException e) {
                System.err.println("Zapis do dodatkowego pliku wyjściowego nieudany!");
            }

        System.out.println(new StringBuilder("Gotowe! ").append(String.format("%.03f", time)).append("ms"));
    }
}
