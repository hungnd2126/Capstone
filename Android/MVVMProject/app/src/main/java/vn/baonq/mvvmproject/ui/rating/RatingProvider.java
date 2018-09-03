package vn.baonq.mvvmproject.ui.rating;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RatingProvider {
    @ContributesAndroidInjector(modules = RatingModule.class)
    abstract RatingDialog provideRatingDialogFactory();
}
