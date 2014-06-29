package org.overflow.ui.component.custom;

import org.overflow.ui.component.WLabel;
import org.overflow.ui.component.WPanel;
import org.overflow.ui.lang.action.UIAction;
import org.overflow.ui.lang.layout.manager.FlowLayout;
import org.overflow.util.profiling.SystemProfiler;

/**
 * Author: Tom
 * Date: 18/06/14
 * Time: 02:43
 */
public class WProfilerPanel extends WPanel {

    private final WLabel scriptCpu;
    private final WLabel averageCpu;

    public WProfilerPanel(final SystemProfiler profiler) {
        this.setLayoutManager(new FlowLayout(FlowLayout.VERTICAL_FLOW,3));
        this.submitWidget(this.scriptCpu = new WLabel().setText("  Current: 0%"));
        this.submitWidget(this.averageCpu = new WLabel().setText("Average: 0%"));
        this.submitAction(new UIAction(1000, true) {
            @Override
            public void update() {
                scriptCpu.setText(String.format(" Current: %.2f %%", profiler.getScriptCpuUsage()));
                averageCpu.setText(String.format("Average: %.2f %%", profiler.getScriptAverageCpuUsage()));
            }
        });
    }
}
