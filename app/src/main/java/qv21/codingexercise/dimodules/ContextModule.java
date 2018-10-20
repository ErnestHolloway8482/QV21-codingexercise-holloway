package qv21.codingexercise.dimodules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import qv21.codingexercise.application.QV21Application;

@Module
public class ContextModule {
    @Singleton
    @Provides
    public Context provideApplicationContext() {
        return QV21Application.getInstance();
    }
}
