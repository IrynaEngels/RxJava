package home.rxjavatest;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

/**
 * Created by java on 06.09.2017.
 */

public class ControllerTest {

    private boolean flag;

    public Observable<Boolean> isFlag() {
        return Observable.just(flag);
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Observable<List<Shoes>> getShoes(boolean isFlag) {

        List<Shoes> shoesList = new ArrayList<>();

        if (isFlag) {

            return Observable.just(shoesList)
                    .doOnNext(guns -> guns.add(new Shoes(39, "white", 7, false, false)))
                    .doOnNext(guns -> guns.add(new Shoes(36, "blue", 3, true, false)))
                    .doOnNext(guns -> guns.add(new Shoes(38, "red", 10, false, true)));
        } else {
            return Observable.error(new NullPointerException("error"));
        }

    }
}
