package com.wywm.superconsole.Functions;

import java.util.LinkedList;
import java.util.List;

public class ListAsc {

    public static List<Troops> getNumAsc() {
        List<Troops> ascList = new LinkedList<Troops>();
        ascList.addAll(XmlGrab.getTroops());
        ascList.sort((u1, u2) -> u1.getId() - u2.getId());
        return ascList;
    }
}
