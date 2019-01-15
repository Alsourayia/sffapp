package com.example.aalsyyed.progbar.listeners;


import com.example.aalsyyed.progbar.StateProgressBar;
import com.example.aalsyyed.progbar.components.StateItem;

/**
 * Created by Kofi Gyan on 2/20/2018.
 */

public interface OnStateItemClickListener {

    void onStateItemClick(StateProgressBar stateProgressBar, StateItem stateItem, int stateNumber, boolean isCurrentState);

}
