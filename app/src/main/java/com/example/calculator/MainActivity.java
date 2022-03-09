package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.calculator.databinding.ActivityMainBinding;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityMainBinding binding;
    private String equal = "0";
    private ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
    private boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    @Override
    public void onClick(View v) {
        if(equal.equals("0") || status)
            equal = "";
        status = false;
        String text = ((Button)v).getText().toString();
        switch (text) {
            case "0": case "1": case "2": case "3": case "4": case "5": case "6": case "7": case "8": case "9":
                equal = equal + text;
                break;
            case "+": case "-": case "/": case "x": case ".":
                char lastChar = equal.charAt(equal.length() - 1);
                if(lastChar < '0' || lastChar > '9') {
                    equal = equal.substring(0, equal.length() - 1);
                }
                equal = equal + (!text.equals("x") ? text : "*");
                break;
            case "DEL":
                if(equal.length() > 0)
                    equal = equal.substring(0, equal.length() - 1);
                break;
            case "=":
                status = true;
                try {
                    double res = (double) engine.eval(equal);
                    binding.equal.setText(equal + " = " + res);
                    equal = "" + res;
                }
                catch (Exception e) {
                }
                break;
            case "+/-":
                break;
            case "C":
                equal = "0";
                break;
        }
        binding.result.setText(equal);
    }
}