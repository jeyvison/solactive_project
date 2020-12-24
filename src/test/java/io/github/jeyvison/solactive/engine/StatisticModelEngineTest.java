package io.github.jeyvison.solactive.engine;

import io.github.jeyvison.solactive.model.StatisticModel;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class StatisticModelEngineTest {

    private CountDownLatch countDownLatch = new CountDownLatch(6);

    @Test
    void getStatistics() throws InterruptedException{

        List<Thread> threads = new ArrayList<>();

        StatisticModelEngine statisticModelEngine = new StatisticModelEngine();

        threads.add(new Thread(() -> {
            statisticModelEngine.compute(100.0, System.currentTimeMillis() - 3000);
            countDownLatch.countDown();
        }));

        threads.add(new Thread(() -> {
            statisticModelEngine.compute(200.0, System.currentTimeMillis() - 3000);
            countDownLatch.countDown();
        }));

        threads.add(new Thread(() -> {
            statisticModelEngine.compute(100.0, System.currentTimeMillis() - 2000);
            countDownLatch.countDown();
        }));

        threads.add(new Thread(() -> {
            statisticModelEngine.compute(200.0, System.currentTimeMillis() - 2000);
            countDownLatch.countDown();
        }));

        threads.add(new Thread(() -> {
            statisticModelEngine.compute(100.0, System.currentTimeMillis());
            countDownLatch.countDown();
        }));

        threads.add(new Thread(() -> {
            statisticModelEngine.compute(200.0, System.currentTimeMillis());
            countDownLatch.countDown();
        }));

        threads.forEach(Thread::run);

        countDownLatch.await(1, TimeUnit.SECONDS);

        StatisticModel statisticModel = statisticModelEngine.getStatisticModel();

        Assert.assertEquals(900.0, statisticModel.getSum(), 0);
        Assert.assertEquals(150.0, statisticModel.getAverage(),0);
        Assert.assertEquals(200.0, statisticModel.getMax(),0);
        Assert.assertEquals(100.0, statisticModel.getMin(),0);
    }
}