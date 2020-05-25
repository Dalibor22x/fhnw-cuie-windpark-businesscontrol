package cuie.dalibor22x;

import java.util.function.Function;

import javafx.scene.control.SkinBase;

public enum SkinType {
    DEFAULT_TYPE(CantonSkin::new);

    private final Function<CantonControl, SkinBase<CantonControl>> factory;

    SkinType(Function<CantonControl, SkinBase<CantonControl>> factory) {
        this.factory = factory;
    }

    public Function<CantonControl, SkinBase<CantonControl>> getFactory() {
        return factory;
    }

}
