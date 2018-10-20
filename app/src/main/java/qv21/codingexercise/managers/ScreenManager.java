package qv21.codingexercise.managers;


import qv21.codingexercise.views.Screen;

public interface ScreenManager {
    <T extends Screen> Screen getScreenFromClass(final Class<T> screenClass);
}
