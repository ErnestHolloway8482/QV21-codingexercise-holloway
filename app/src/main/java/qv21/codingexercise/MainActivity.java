package qv21.codingexercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(instance==null){
            instance = this;
        }
    }

    public static MainActivity getInstance() {
         return instance;
    }
}
