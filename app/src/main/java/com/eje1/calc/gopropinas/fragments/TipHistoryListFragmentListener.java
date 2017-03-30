package com.eje1.calc.gopropinas.fragments;

import com.eje1.calc.gopropinas.models.TipRecord;

/**
 * Created by Bryan G on 18/03/2017.
 */
public interface TipHistoryListFragmentListener {
    void addToList(TipRecord record);
    void clearList();
}
