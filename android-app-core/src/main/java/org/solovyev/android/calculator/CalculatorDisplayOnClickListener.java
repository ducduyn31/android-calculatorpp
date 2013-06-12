package org.solovyev.android.calculator;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import org.jetbrains.annotations.NotNull;
import org.solovyev.android.menu.ContextMenuBuilder;
import org.solovyev.android.menu.ListContextMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Solovyev_S
 * Date: 21.09.12
 * Time: 10:58
 */
public class CalculatorDisplayOnClickListener implements View.OnClickListener {

    @NotNull
    private final FragmentActivity activity;

    public CalculatorDisplayOnClickListener(@NotNull FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        if (v instanceof CalculatorDisplayView) {
            final CalculatorDisplay cd = Locator.getInstance().getDisplay();

            final CalculatorDisplayViewState displayViewState = cd.getViewState();

            if (displayViewState.isValid()) {
                final List<CalculatorDisplayMenuItem> filteredMenuItems = new ArrayList<CalculatorDisplayMenuItem>(CalculatorDisplayMenuItem.values().length);
                for (CalculatorDisplayMenuItem menuItem : CalculatorDisplayMenuItem.values()) {
                    if (menuItem.isItemVisible(displayViewState)) {
                        filteredMenuItems.add(menuItem);
                    }
                }

                if (!filteredMenuItems.isEmpty()) {
                    ContextMenuBuilder.newInstance(activity, "display-menu", ListContextMenu.newInstance(filteredMenuItems)).build(displayViewState).show();
                }

            } else {
                final String errorMessage = displayViewState.getErrorMessage();
                if (errorMessage != null) {
                    Locator.getInstance().getCalculator().fireCalculatorEvent(CalculatorEventType.show_evaluation_error, errorMessage, activity);
                }
            }
        }
    }
}
