package home.rxjavatest;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    @BindView(R2.id.tv_1)
    EditText tv1;
    @BindView(R2.id.tv_2)
    EditText tv2;
    @BindView(R2.id.tv_3)
    EditText tv3;

    @BindView(R2.id.fab)
    FloatingActionButton fab;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ControllerTest controllerTest = new ControllerTest();
        controllerTest.setFlag(true);

        controllerTest.isFlag()
                .flatMap(controllerTest::getShoes)
                .flatMap(shoesList -> Observable.fromIterable(shoesList))
                .filter(shoes -> shoes.getSize() < 38)
                .toList()
                .toObservable()
                .subscribe(shoes -> {
                }, throwable -> {
                });

        fab.setOnClickListener(view -> showDialog());
    }

    @Override
    protected void onResume() {
        super.onResume();
        disposables.add(Observable.combineLatest(
                RxViewEvents
                        .getTextChangeObservable(tv1)
                        .map(value -> !TextUtils.isEmpty(value))
//                        .doOnNext(this::checkAndSetError)
                ,
                RxViewEvents
                        .getTextChangeObservable(tv2)
                        .map(value -> !TextUtils.isEmpty(value))
//                        .doOnNext(value -> checkAndSetError(value, errorLanguages, txtLanguagesText))
                ,
                RxViewEvents
                        .getTextChangeObservable(tv3)
                        .map(value -> value.matches("\\+\\d{4}"))
//                        .doOnNext(value -> checkAndSetError(value, errorPhone, txtPhoneText))
                ,
                (f1, f2, f3) -> f1 && f2 && f3)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setSaveButtonVisible)
        );
    }

    private void setSaveButtonVisible(boolean flag) {
        fab.setEnabled(flag);
    }

    private void showDialog() {
        //       View viewTitle = activity.getLayoutInflater().inflate(R.layout.item_text_view_title, null, false);
        //       TextView title = viewTitle.findViewById(R.id.title);
//        title.setText(R.string.delete_profile);
//        title.setText(" ");

//        View v = activity.getLayoutInflater().inflate(R.layout.dialog_message, null, false);
//        TextView text = v.findViewById(R.id.txt_message);
//        text.setText(txt);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Hello");
        builder.setMessage("Message");
//        builder.setCustomTitle(viewTitle);
//        builder.setView(v);
        builder.setCancelable(true);

        builder.setNegativeButton("Cancel", (dialogInterface, i) ->
        {
        });
        builder.setPositiveButton("Ok", (dialogInterface, i) ->
        {
        });
        builder.show();
    }

    private void showDataPicker() {

        Calendar date = Calendar.getInstance();

        new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {

            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, monthOfYear);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Log.d("TAG", new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date.getTime()));
        },
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))
                .show();
    }


    @Override
    protected void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
