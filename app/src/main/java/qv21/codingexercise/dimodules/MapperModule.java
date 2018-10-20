package qv21.codingexercise.dimodules;

import dagger.Module;
import dagger.Provides;
import qv21.codingexercise.mapper.WellDataMapper;

//A dagger {@link Module} that serves as a factory for the Mapper objects and fully enables dependency injection.
@Module
public class MapperModule {
    @Provides
    public static WellDataMapper provideWellDataMapper() {
        return new WellDataMapper();
    }
}
