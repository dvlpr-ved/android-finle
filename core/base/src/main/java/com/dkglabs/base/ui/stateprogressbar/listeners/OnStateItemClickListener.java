package com.dkglabs.base.ui.stateprogressbar.listeners;


import com.dkglabs.base.ui.stateprogressbar.StateProgressBar;
import com.dkglabs.base.ui.stateprogressbar.components.StateItem;

/**
 * Created by Kofi Gyan on 2/20/2018.
 */

public interface OnStateItemClickListener {

    void onStateItemClick(StateProgressBar stateProgressBar, StateItem stateItem, int stateNumber, boolean isCurrentState);

}
