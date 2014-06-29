package org.overflow;

import org.overflow.ui.Screen;
import org.overflow.ui.component.*;
import org.overflow.ui.component.custom.WProfilerPanel;
import org.overflow.ui.component.custom.WStopwatch;
import org.overflow.ui.lang.Widget;
import org.overflow.ui.lang.action.UIAction;
import org.overflow.ui.lang.event.InputHandler;
import org.overflow.ui.lang.graphics.skin.resource.ResourceLocale;
import org.overflow.ui.lang.layout.manager.FlowLayout;
import org.overflow.util.profiling.SystemProfiler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Author: Tom
 * Date: 12/06/14
 * Time: 21:58
 */
public class UITest extends Canvas implements Runnable {

    private final ThreadGroup threadGroup = new ThreadGroup("Panel 1");
    private final SystemProfiler profiler = new SystemProfiler(threadGroup);

    private Screen screen;

    public static void main(final String... args) throws IOException, InterruptedException {
        // System.setProperty("sun.java2d.opengl", "True");
        JFrame frame = new JFrame("Widget Test Pane");
        frame.setLocation(20, 485);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        UITest testPane = new UITest();
        frame.setLayout(new BorderLayout());
        frame.add(testPane, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.pack();
        new Thread(testPane.threadGroup, testPane).start();
    }

    public UITest() {
        this.setMinimumSize(new Dimension(500, 500));
        this.setPreferredSize(getMinimumSize());
        this.setBackground(Color.WHITE);
    }

    public void buildWidgets() {
        try {
            this.screen = new Screen().attachComponent(this);
            this.screen.submitWidget(new WProfilerPanel(profiler).setYOffset(400));
            for (int x = 0; x < 1; x++) {
                final WFrame frame = new WFrame().setXOffset(x * 10).setYOffset(x * 10);
                frame.getTitle().setText("Title Pane");
                final WProgressBar bar = new WProgressBar();
                bar.submitAction(new UIAction(5000, true) {
                    int i = 1;

                    @Override
                    public void update() {
                        bar.setPercentage(i++ % 100);
                    }
                });
                frame.getContentPanel().submitWidget(new WPanel(ResourceLocale.NIL).setLayoutManager(new FlowLayout()).submitWidget(new WLabel().setText("Runtime: ")).submitWidget(new WStopwatch(new AtomicLong(System.currentTimeMillis() + 60000))));
                frame.getContentPanel().submitWidget(bar);
                final WTabPanel panel = new WTabPanel();
                for (int i = 0; i < 2; i++) {
                    try {
                        final WProgressBar progressBar = new WProgressBar();
                        progressBar.submitAction(new UIAction(1000, true) {
                            int i = 0;

                            @Override
                            public void update() {
                                progressBar.setPercentage(i++ % 100);
                            }
                        });
                        panel.submitTab("Tab: " + i, new WPanel().setLayoutManager(new FlowLayout(FlowLayout.VERTICAL_FLOW, 5)).setYOffset(50).
                                submitWidget(new WLabel().setText("Tab: " + i)).
                                submitWidget(progressBar).
                                submitWidget(new WCheckbox()).
                                submitWidget(new WButton().submitWidget(new WLabel().setText("Widget Button " + i)).submitMouseHandler(new InputHandler.Mouse(1) {
                                    @Override
                                    protected boolean execute(Widget widget, MouseEvent event) {
                                        if (event.getID() == MouseEvent.MOUSE_CLICKED)
                                            System.out.println("Button pressed");
                                        return true;
                                    }
                                })));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                frame.getContentPanel().submitWidget(panel);
                screen.submitWidget(frame);
            }
            screen.submitWidget(new WLabel().setXOffset(50).setYOffset(200).setText("Test Label").setFont(new Font(Font.SERIF, Font.PLAIN, 32)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            buildWidgets();
            addMouseListener(screen);
            addMouseMotionListener(screen);
            addKeyListener(screen);
            createBufferStrategy(2);
            while (this.isVisible()) {
                BufferStrategy strat = getBufferStrategy();
                if (strat != null) {
                    Graphics2D g = (Graphics2D) strat.getDrawGraphics();
                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, getWidth(), getHeight());
                    g.setColor(Color.BLACK);
                    screen.render(g);
                    strat.show();
                }
                try {
                    Thread.sleep(33);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

