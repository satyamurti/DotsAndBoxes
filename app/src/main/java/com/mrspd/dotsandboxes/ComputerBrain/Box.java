package com.mrspd.dotsandboxes.ComputerBrain;

class Box {
    private boolean left;
    private boolean top;
    private boolean right;
    private boolean bottom;

    Box(boolean l, boolean t, boolean r, boolean b) {
        this.left = l;
        this.top = t;
        this.right = r;
        this.bottom = b;

        boolean occupied = (l && t && r && b);
    }

    int occupiedLineCount() {
        int count = 0;

        if (this.left) count++;
        if (this.right) count++;
        if (this.top) count++;
        if (this.bottom)
            count++;

        return count;
    }
}
