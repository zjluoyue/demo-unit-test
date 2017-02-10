package com.zjluoyue.concurrency.comparison;

import com.zjluoyue.arrays.CountingGenerator;
import com.zjluoyue.containers.MapData;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Jia on 2017/2/10.
 */
abstract class MapTest extends Tester<Map<Integer, Integer>> {

    MapTest(String testId, int nReaders, int nWriters) {
        super(testId, nReaders, nWriters);
    }

    class Reader extends TestTask {
        long result = 0;

        void test() {
            for (int i = 0; i < testCycles; i++) {
                for (int index = 0; index < containerSize; index++) {
                    result += testContainer.get(index);
                }
            }
        }

        @Override
        void putResults() {
            readResult += result;
            readTime += duration;
        }
    }

    class Writer extends TestTask {
        @Override
        void test() {
            for (int i = 0; i < testCycles; i++) {
                for (int index = 0; index < containerSize; index++) {
                    testContainer.put(index, writeData[index]);
                }
            }
        }

        @Override
        void putResults() {
            writeTime += duration;
        }
    }

    @Override
    void startReadersAndWriters() {
        for (int i = 0; i < nReaders; i++) {
            exec.execute(new Reader());
        }
        for (int i = 0; i < nWriters; i++) {
            exec.execute(new Writer());
        }
    }
}

class SynchronizedHashMapTest extends MapTest {

    SynchronizedHashMapTest(int nReaders, int nWriters) {
        super("Synched HashMap", nReaders, nWriters);
    }

    @Override
    Map<Integer, Integer> containerInitializer() {
        return Collections.synchronizedMap(
                new HashMap<Integer, Integer>(
                        MapData.map(
                                new CountingGenerator.Integer(),
                                new CountingGenerator.Integer(),
                                containerSize)));
    }
}

class ConcurrentHashMapTest extends MapTest {

    @Override
    Map<Integer, Integer> containerInitializer() {
        return new ConcurrentHashMap<Integer, Integer>(
                MapData.map(
                        new CountingGenerator.Integer(),
                        new CountingGenerator.Integer(),
                        containerSize));
    }

    public ConcurrentHashMapTest(int nReaders, int nWriters) {
        super("ConcurrentHashMap", nReaders, nWriters);
    }
}
public class MapComparisons {
    public static void main(String[] args) {
        Tester.initMain(args);
        new SynchronizedHashMapTest(10, 0);
        new SynchronizedHashMapTest(9, 1);
        new SynchronizedHashMapTest(5, 5);
        new ConcurrentHashMapTest(10, 0);
        new ConcurrentHashMapTest(9, 1);
        new ConcurrentHashMapTest(5, 5);
        Tester.exec.shutdown();
    }
}

