package com.wywm.superconsole.Functions;

import java.util.LinkedList;
import java.util.List;

public class ListDesc {

    public static List<Troops> getNumDes() {
        List<Troops> desList = new LinkedList<Troops>();
        desList.addAll(XmlGrab.getTroops());
        desList.sort((u1, u2) -> u2.getId() - u1.getId());
        return desList;
    }
}
