// Austin Patel
// 4/9/2017
// CreateAssignmentInterface.java

package visuals;

import javax.swing.*;

public class CreateAssignmentInterface extends JPanel implements Tab {

    public CreateAssignmentInterface() {
        add(new JLabel("Create Assignment Interface"));
    }

    @Override
    public String getTabName() {
        return "Create Assignment";
    }

    @Override
    public String getTabImage() {
        return "assignment_icon.png";
    }

    @Override
    public void onTabSelected() {

    }
}
