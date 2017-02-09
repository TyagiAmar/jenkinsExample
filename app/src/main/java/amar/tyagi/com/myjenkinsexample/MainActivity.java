package amar.tyagi.com.myjenkinsexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myException();
    }

    void myException()
    {
        throw  new NullPointerException("bhasad");
    }


}
