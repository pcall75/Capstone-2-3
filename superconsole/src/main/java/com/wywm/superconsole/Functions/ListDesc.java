package com.wywm.superconsole.Functions;

import java.util.LinkedList;
import java.util.List;

//Gets the XML list in Descending order and stores it in a new linked-list
public class ListDesc {

    public static List<Troops> getNumDes() {
        List<Troops> desList = new LinkedList<Troops>();
        desList.addAll(XmlGrab.getTroops());
        desList.sort((u1, u2) -> u2.getId() - u1.getId());
        return desList;
    }
}
