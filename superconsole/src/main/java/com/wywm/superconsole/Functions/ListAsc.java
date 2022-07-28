package com.wywm.superconsole.Functions;

import java.util.LinkedList;
import java.util.List;

//Gets the XML list in Ascending order and stores it in a new linked-list
public class ListAsc {

    public static List<Troops> getNumAsc() {
        List<Troops> ascList = new LinkedList<Troops>();
        ascList.addAll(XmlGrab.getTroops());
        ascList.sort((u1, u2) -> u1.getId() - u2.getId());
        return ascList;
    }
}
