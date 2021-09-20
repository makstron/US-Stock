package com.klim.windowsmanager;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

public class Test {

    abstract static class PageBackground<D extends Drawable> {
        abstract D createForeground();
        abstract void onDraw(D drawable, float passedDistance, float of);
    }

    class Shadow extends PageBackground <ColorDrawable> {
        @Override
        public ColorDrawable createForeground() {
            return new ColorDrawable(Color.argb(1, 0, 0, 0));
        }

        @Override
        public void onDraw(ColorDrawable drawable, float passedDistance, float of) {
            System.out.println("!!!!!!!@@@@@@ " + drawable.getAlpha());
        }
    }

    PageBackground background;

//    @SuppressWarnings("unchecked")
    void draw(PageBackground background) {
        Drawable bg = background.createForeground();
        background.onDraw(bg, 0, 10);
    }


    public void gogogo() {
        background = new Shadow();
        draw();
    }

}
