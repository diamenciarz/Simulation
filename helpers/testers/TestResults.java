package helpers.testers;

public class TestResults {
    public TestResults(int repetitions, long totalTime) {
        this.repetitions = repetitions;
        this.totalTime = totalTime;
    }

    int repetitions;
    long totalTime;
}
