package qv21.codingexercise.dimodules;

import dagger.Module;
import dagger.Provides;
import qv21.codingexercise.mapper.WellDataMapper;

@Module
public class MapperModule {
    @Provides
    public static WellDataMapper provideWellDataMapper(){
        return new WellDataMapper();
    }
}
