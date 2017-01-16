package com.zjluoyue.typeinfo.factory;

import java.util.ArrayList;
import java.util.List;

import static com.zjluoyue.typeinfo.ClassInitialization.rand;

/**
 * Created by zjluoyue on 2016/10/14.
 */
public class Part {
    static List<Factory<? extends Part>> partFactory =
            new ArrayList<Factory<? extends Part>>();

    static {
        partFactory.add(new FuelFillter.Factory());
        partFactory.add(new FanBelt.Factory());
    }
    public String toString() {
        return getClass().getSimpleName();
    }

    public static Part create() {
        int n = rand.nextInt(partFactory.size());
        return partFactory.get(n).create();
    }

    static class FanBelt extends Part{
        public static class Factory
                implements com.zjluoyue.typeinfo.factory.Factory<FanBelt> {
            public FanBelt create() {
                return new FanBelt();
            }
        }
    }

    static class FuelFillter extends Part{
        public static class Factory
                implements com.zjluoyue.typeinfo.factory.Factory<FuelFillter> {
            public FuelFillter create() {
                return new FuelFillter();
            }
        }
    }
}
